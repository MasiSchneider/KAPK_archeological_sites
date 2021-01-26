package org.wit.sites.models.json.sites

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.sites.helpers.*
import org.wit.sites.models.SiteModel
import org.wit.sites.models.SiteStore
import java.util.*

val JSON_FILE = "sites.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<SiteModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class SiteJSONStore : SiteStore, AnkoLogger {

    val context: Context
    var sites = mutableListOf<SiteModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<SiteModel> {
        return sites
    }

    override fun create(site: SiteModel) {
        site.id = generateRandomId()
        sites.add(site)
        serialize()
    }


    override fun update(site: SiteModel) {
        val sitesList = findAll() as ArrayList<SiteModel>
        var foundSite: SiteModel? = sitesList.find { p -> p.id == site.id}
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
            foundSite.location = site.location
        }
        serialize()
    }

    override fun delete(site: SiteModel) {
        val foundSite: SiteModel? = sites.find { it.id == site.id }
        sites.remove(foundSite)
        serialize()
    }

    override fun findById(id:Long) : SiteModel? {
        val foundSite: SiteModel? = sites.find { it.id == id }
        return foundSite
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(sites, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        sites = Gson().fromJson(jsonString, listType)
    }

    override fun clear() {
        sites.clear()
    }
}