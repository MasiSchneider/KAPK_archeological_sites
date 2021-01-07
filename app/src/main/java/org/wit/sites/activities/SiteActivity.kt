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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site)

        app = application as MainApp

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        info("Site Activity started..")


        btnAdd.setOnClickListener() {
            site.title = siteTitle.text.toString()
            site.description = description.text.toString()

            if (site.title.isNotEmpty()) {
                app.sites.add(site.copy())
                info("add Button Pressed: ${site}")
                for (i in app.sites.indices) {
                    info("Site[$i]:${app.sites[i]}")
                }
                setResult(RESULT_OK)
                finish()
            }
            else {
                toast ("Please Enter a title")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_site, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

