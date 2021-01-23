package org.wit.sites.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

class SiteActivity : AppCompatActivity(), AnkoLogger {

    var site = SiteModel()
    lateinit var app: MainApp
    var edit = false
    val IMAGE1_REQUEST = 1
    val IMAGE2_REQUEST = 2

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
            if(site.image1 != "")
                chooseImage1.setText(R.string.change_site_image)
            if(site.image2 != "")
                chooseImage2.setText(R.string.change_site_image)
        }

        chooseImage1.setOnClickListener {
            info("Select image 1")
            showImagePicker(this,IMAGE1_REQUEST)
        }
        chooseImage2.setOnClickListener {
            info("Select image 2")
            showImagePicker(this,IMAGE2_REQUEST)
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
                    chooseImage1.setText(R.string.change_site_image)
                }
            }
        }
    }
}

