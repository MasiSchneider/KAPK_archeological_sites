package org.wit.sites.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.sites.models.SiteModel

class MainApp : Application(), AnkoLogger {

    val sites = ArrayList<SiteModel>()

    override fun onCreate() {
        super.onCreate()
        info("Sites App started")
    }
}