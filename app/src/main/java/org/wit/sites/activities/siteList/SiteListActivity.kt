package org.wit.sites.activities.siteList

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_site_list.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.intentFor


import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.uiThread
import org.wit.sites.R
import org.wit.sites.activities.Settings.SettingsActivity
import org.wit.sites.activities.SiteAdapter
import org.wit.sites.activities.SiteListener
import org.wit.sites.activities.favourite.FavouriteSiteActivity
import org.wit.sites.activities.login.LoginAcitivity
import org.wit.sites.activities.map.SiteMapsActivity
import org.wit.sites.activities.site.SiteActivity
import org.wit.sites.main.MainApp
import org.wit.sites.models.SiteModel

class SiteListActivity : AppCompatActivity(), SiteListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_list)
        app = application as MainApp

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            toolbar.title = "${title}: ${user.email}"
        }

        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        loadSites()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> startActivityForResult<SiteActivity>(0)
            R.id.item_settings -> startActivityForResult<SettingsActivity>(0)
            R.id.item_logout -> {
                FirebaseAuth.getInstance().signOut()
                startActivityForResult<LoginAcitivity>(0)
            }
            R.id.item_map -> startActivityForResult<SiteMapsActivity>(0)
            R.id.item_favourites -> startActivityForResult<FavouriteSiteActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadSites() {
        doAsync {
            val sites = app.sites.findAll()
            uiThread {
                showSites(sites)
            }
        }
    }

    fun showSites(sites: List<SiteModel>) {
        recyclerView.adapter = SiteAdapter(sites, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadSites()
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSiteClick(site: SiteModel) {
        startActivityForResult(intentFor<SiteActivity>().putExtra("site_edit", site), 0)
    }
}

