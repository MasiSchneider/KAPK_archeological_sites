package org.wit.sites.activities

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_site.*
import kotlinx.android.synthetic.main.activity_site_list.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.sites.R
import org.wit.sites.main.MainApp
import org.wit.sites.models.json.users.UserJSONStore


class SettingsActivity : AppCompatActivity(), AnkoLogger {
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        app = application as MainApp

        setSupportActionBar(toolbarSettings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var mySites = app.sites.findAllWithId(app.user.id)
        var markedCount = mySites.filter { it.visited }.count()
        NumberOfSites.text = "Number of sites: " + mySites.count().toString()
        NumberOfSitesVisited.text = "Number marked: " + markedCount.toString()

        buttonChangeCredentials.setOnClickListener {
            editTextTextEmailAddressNew.visibility= View.VISIBLE
            editTextPasswordOld.visibility= View.VISIBLE
            editTextPasswordNew.visibility= View.VISIBLE
            buttonSaveCredentials.visibility= View.VISIBLE
        }

        buttonSaveCredentials.setOnClickListener {
            if(editTextTextEmailAddressNew.text.isNotEmpty())
            {
                var success = app.users.changeEmail(app.user.id,editTextTextEmailAddressNew.text.toString())
                if(success)
                    toast("Successfully changed Email")
                else
                    toast("Email already exists")
            }
            if(editTextPasswordNew.text.isNotEmpty())
            {
                if(editTextPasswordOld.text.toString() == app.user.password)
                {
                    var success = app.users.changePassword(app.user.id,editTextPasswordNew.text.toString())
                    if(success)
                        toast("Successfully changed password")
                    else
                        toast("Failed to change password")
                }
                else
                {
                    toast("Old password wrong")
                }
            }
        }
    }

}