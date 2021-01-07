package org.wit.sites.activities

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


import kotlinx.android.synthetic.main.activity_site.view.*
import kotlinx.android.synthetic.main.activity_site_list.*
import kotlinx.android.synthetic.main.card_site.view.*
import kotlinx.android.synthetic.main.card_site.view.description
import kotlinx.android.synthetic.main.card_site.view.siteTitle



import org.jetbrains.anko.startActivityForResult
import org.wit.sites.R
import org.wit.sites.main.MainApp
import org.wit.sites.models.SiteModel

class SiteListActivity : AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_list)
        app = application as MainApp
        toolbar.title = title
        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = SiteAdapter(app.sites)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> startActivityForResult<SiteActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }
}

