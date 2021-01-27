package org.wit.sites.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.sites.models.SiteStore
import org.wit.sites.models.UserModel
import org.wit.sites.models.UserStore
import org.wit.sites.models.json.sites.SiteJSONStore
import org.wit.sites.models.json.users.UserJSONStore


class MainApp : Application(), AnkoLogger {
    lateinit var sites: SiteStore
    lateinit var user: UserModel

    override fun onCreate() {
        super.onCreate()
        sites = SiteJSONStore(applicationContext)
        info("Sites App started")
    }
}