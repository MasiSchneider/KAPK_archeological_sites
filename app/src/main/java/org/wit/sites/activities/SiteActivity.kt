package org.wit.sites.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.sites.R
import kotlinx.android.synthetic.main.activity_site.*
import org.jetbrains.anko.toast
import org.wit.sites.main.MainApp
import org.wit.sites.models.SiteModel

class SiteActivity : AppCompatActivity(), AnkoLogger {

    var site = SiteModel()
    lateinit var app: MainApp
    var edit = false

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
}

