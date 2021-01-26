package org.wit.sites.activities.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.sites.R
import org.wit.sites.activities.SiteListActivity
import org.wit.sites.main.MainApp
import org.wit.sites.models.UserModel
import org.wit.sites.models.UserStore
import org.wit.sites.models.json.users.UserJSONStore

class LoginAcitivity : AppCompatActivity(), AnkoLogger {
    lateinit var app: MainApp
    lateinit var users: UserJSONStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        progressBar.visibility = View.GONE

        app = application as MainApp
        users = UserJSONStore(applicationContext)

        signUp.setOnClickListener {
            if (email.text.toString() == "" || password.text.toString() == "") {
                toast("Please provide email + password")
            } else {
                var user = UserModel()
                user.email = email.text.toString()
                user.password = password.text.toString()
                user = users.create(user)
                if(user.id != 0L)
                {
                    app.user = user
                    startActivityForResult<SiteListActivity>(0)
                }
                else
                    toast("Email already in use")
            }
        }

        logIn.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            if (email == "" || password == "") {
                toast("Please provide email + password")
            }
            else {
                var foundUser=users.findByEmailPassword(email, password)
                if(foundUser==null)
                    toast("Authentification failed")
                else
                {
                    app.user = foundUser
                    startActivityForResult<SiteListActivity>(0)
                }

            }
        }
    }


}