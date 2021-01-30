package org.wit.sites.models.firebase


import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.jetbrains.anko.AnkoLogger
import org.wit.sites.helpers.readImageFromPath
import org.wit.sites.models.SiteModel
import org.wit.sites.models.SiteStore
import org.wit.sites.models.json.sites.generateRandomId
import java.io.ByteArrayOutputStream
import java.io.File

class SiteFireStore(val context: Context) : SiteStore, AnkoLogger {
    val sites = ArrayList<SiteModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference
    lateinit var st: StorageReference

    override fun findAll(): List<SiteModel> {
        return sites
    }

    override fun findById(id: Long): SiteModel? {
        val foundSite: SiteModel? = sites.find { s -> s.id == id }
        return foundSite
    }

    override fun create(site: SiteModel) {
        site.id= generateRandomId()
        val key = db.child("users").child(userId).child("sites").push().key
        key?.let {
            site.fbId = key
            sites.add(site)
            db.child("users").child(userId).child("sites").child(key).setValue(site)
            updateImages(site)
        }
    }

    override fun update(site: SiteModel) {
        var foundSite: SiteModel? = sites.find { s -> s.fbId == site.fbId }
        if (foundSite != null) {
            foundSite.title = site.title
            foundSite.description = site.description
            foundSite.image1 = site.image1
            foundSite.image2 = site.image2
            foundSite.image3 = site.image3
            foundSite.image4 = site.image4
            foundSite.visited = site.visited
            foundSite.visitedDate = site.visitedDate
            foundSite.notes = site.notes
            foundSite.rating = site.rating
            foundSite.location = site.location
        }
        db.child("users").child(userId).child("sites").child(site.fbId).setValue(site)
        updateImages(site)
    }

    override fun delete(site: SiteModel) {
        db.child("users").child(userId).child("sites").child(site.fbId).removeValue()
        sites.remove(site)
    }

    override fun clear() {
        sites.clear()
    }

    fun fetchSites(sitesReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot!!.children.mapNotNullTo(sites) { it.getValue<SiteModel>(SiteModel::class.java) }
                sitesReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        st = FirebaseStorage.getInstance().reference
        sites.clear()
        db.child("users").child(userId).child("sites").addListenerForSingleValueEvent(valueEventListener)
    }

    fun updateImages(site: SiteModel) {
        val images = listOf(site.image1, site.image2, site.image3, site.image4)
        for (image in images)
        {
            if ((image.length) > 0 && (image[0] != 'h')) {
                if (image != "") {
                    val fileName = File(image)
                    val imageName = fileName.getName()

                    var imageRef = st.child(userId + '/' + imageName)
                    val baos = ByteArrayOutputStream()
                    val bitmap = readImageFromPath(context, image)

                    bitmap?.let {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                        val data = baos.toByteArray()
                        val uploadTask = imageRef.putBytes(data)
                        uploadTask.addOnFailureListener {
                            println(it.message)
                        }.addOnSuccessListener { taskSnapshot ->
                            taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                                if(image==site.image1)
                                    site.image1 = it.toString()
                                if(image==site.image2)
                                    site.image2 = it.toString()
                                if(image==site.image3)
                                    site.image3 = it.toString()
                                if(image==site.image4)
                                    site.image4 = it.toString()
                                db.child("users").child(userId).child("sites").child(site.fbId).setValue(site)
                            }
                        }
                    }
                }
            }

        }


    }

}