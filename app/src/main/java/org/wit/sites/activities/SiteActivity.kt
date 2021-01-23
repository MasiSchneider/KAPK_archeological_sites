package org.wit.sites.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.sites.R
import kotlinx.android.synthetic.main.activity_site.*
import org.jetbrains.anko.toast
import org.wit.sites.helpers.readImage
import org.wit.sites.helpers.readImageFromPath
import org.wit.sites.helpers.showImagePicker
import org.wit.sites.main.MainApp
import org.wit.sites.models.SiteModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class SiteActivity : AppCompatActivity(), AnkoLogger {

    var site = SiteModel()
    lateinit var app: MainApp
    var edit = false
    val IMAGE1_REQUEST = 1
    val IMAGE2_REQUEST = 2
    val IMAGE3_REQUEST = 3
    val IMAGE4_REQUEST = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site)

        app = application as MainApp

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        supportActionBar?.setDisplayHomeAsUpEnabled(true) // up support
        info("Site Activity started..")


        if(intent.hasExtra("site_edit")) {
            edit = true
            site = intent.extras?.getParcelable<SiteModel>("site_edit")!!
            siteTitle.setText(site.title)
            description.setText(site.description)

            SiteImage1.setImageBitmap(readImageFromPath(this,site.image1))
            SiteImage2.setImageBitmap(readImageFromPath(this,site.image2))
            SiteImage3.setImageBitmap(readImageFromPath(this,site.image3))
            SiteImage4.setImageBitmap(readImageFromPath(this,site.image4))

            if(site.image1 != "")
                chooseImage1.setText(R.string.change_site_image)
            if(site.image2 != "")
                chooseImage2.setText(R.string.change_site_image)
            if(site.image3 != "")
                chooseImage3.setText(R.string.change_site_image)
            if(site.image4 != "")
                chooseImage4.setText(R.string.change_site_image)
            if(site.visited)
            {
                checkBoxVisited.setChecked(true)
                dateVisted.setVisibility(View.VISIBLE)
                dateVisted.setText(site.visitedDate)
            }

        }


        chooseImage1.setOnClickListener {
            showImagePicker(this,IMAGE1_REQUEST)
        }
        chooseImage2.setOnClickListener {
            showImagePicker(this,IMAGE2_REQUEST)
        }
        chooseImage3.setOnClickListener {
            showImagePicker(this,IMAGE3_REQUEST)
        }
        chooseImage4.setOnClickListener {
            showImagePicker(this,IMAGE4_REQUEST)
        }

        checkBoxVisited.setOnClickListener {
            site.visited=!site.visited
            if(site.visited)
            {
                dateVisted.setVisibility(View.VISIBLE)
                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val currentDate = sdf.format(Date())
                site.visitedDate = currentDate
                dateVisted.setText(currentDate)
            }
            else
            {
                dateVisted.setVisibility(View.INVISIBLE)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_site, menu)
        if (edit && menu != null) menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_delete -> {
                app.sites.delete(site)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
            R.id.item_save -> {
                if (siteTitle.text.toString().isEmpty()) {
                    toast(R.string.enter_site_title)
                } else {
                    site.title = siteTitle.text.toString()
                    site.description = description.text.toString()
                    if (edit) {
                        app.sites.update(site)
                    }
                    else {
                        app.sites.create(site)
                    }
                }
                setResult(RESULT_OK)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE1_REQUEST -> {
                if (data != null) {
                    site.image1 = data.getData().toString()
                    SiteImage1.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage1.setText(R.string.change_site_image)
                }
            }
            IMAGE2_REQUEST -> {
                if (data != null) {
                    site.image2 = data.getData().toString()
                    SiteImage2.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage2.setText(R.string.change_site_image)
                }
            }
            IMAGE3_REQUEST -> {
                if (data != null) {
                    site.image3 = data.getData().toString()
                    SiteImage3.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage3.setText(R.string.change_site_image)
                }
            }
            IMAGE4_REQUEST -> {
                if (data != null) {
                    site.image4 = data.getData().toString()
                    SiteImage4.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage4.setText(R.string.change_site_image)
                }
            }
        }
    }
}

