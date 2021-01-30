package org.wit.sites.activities

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_site.*
import kotlinx.android.synthetic.main.activity_site_list.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivityForResult
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

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            toolbarSettings.title = "${title}: ${user.email}"
        }

        var mySites = app.sites.findAll()
        var markedCount = mySites.filter { it.visited }.count()
        NumberOfSites.text = "Number of sites: " + mySites.count().toString()
        NumberOfSitesVisited.text = "Number marked: " + markedCount.toString()

        buttonChangeCredentials.setOnClickListener {
            editTextTextEmailAddressNew.visibility = View.VISIBLE
            editTextPasswordNew.visibility = View.VISIBLE
            buttonSaveCredentials.visibility = View.VISIBLE
        }

        buttonSaveCredentials.setOnClickListener {
            if (editTextTextEmailAddressNew.text.isNotEmpty()) {
                user?.updateEmail(editTextTextEmailAddressNew.text.toString())?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        toast("Email changed successfully")
                    } else {
                        toast("Email change failed: ${task.exception?.message}")
                    }
                }
            }

            if (editTextPasswordNew.text.isNotEmpty()) {
                user?.updatePassword(editTextPasswordNew.text.toString())?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        toast("Password changed successfully")
                    } else {
                        toast("Password change failed: ${task.exception?.message}")
                    }
                }
            }
        }
    }
}