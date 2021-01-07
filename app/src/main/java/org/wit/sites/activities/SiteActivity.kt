package org.wit.sites.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.sites.R
import kotlinx.android.synthetic.main.activity_site.*
import org.jetbrains.anko.toast
import org.wit.sites.models.SiteModel

class SiteActivity : AppCompatActivity(), AnkoLogger {

    var site = SiteModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site)
        info("Site Activity started..")

        btnAdd.setOnClickListener() {
            site.title = siteTitle.text.toString()
            if (site.title.isNotEmpty()) {
                info("add Button Pressed: " + site.title)
            }
            else {
                toast ("Please Enter a title")
            }
        }

    }
}

