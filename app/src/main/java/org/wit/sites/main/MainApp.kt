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
    lateinit var users: UserJSONStore

    override fun onCreate() {
        super.onCreate()
        sites = SiteJSONStore(applicationContext)
        users = UserJSONStore(applicationContext)
        info("Sites App started")
    }
}