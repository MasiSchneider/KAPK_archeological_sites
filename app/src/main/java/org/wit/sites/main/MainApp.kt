package org.wit.sites.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.sites.models.SiteModel
import org.wit.sites.models.SiteStore
import org.wit.sites.models.json.SiteJSONStore

class MainApp : Application(), AnkoLogger {

    lateinit var sites: SiteStore

    override fun onCreate() {
        super.onCreate()
        sites = SiteJSONStore(applicationContext)
        info("Sites App started")
    }
}