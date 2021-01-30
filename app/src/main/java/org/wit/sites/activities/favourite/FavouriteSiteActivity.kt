package org.wit.sites.activities.favourite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_site_list.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.uiThread
import org.wit.sites.R
import org.wit.sites.activities.site.SiteActivity
import org.wit.sites.activities.SiteAdapter
import org.wit.sites.activities.SiteListener
import org.wit.sites.main.MainApp
import org.wit.sites.models.SiteModel

class FavouriteSiteActivity : AppCompatActivity(), SiteListener {
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        loadSites()
    }

    private fun loadSites() {
        doAsync {
            val sites = app.sites.findAll().filter { it.favourite}
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