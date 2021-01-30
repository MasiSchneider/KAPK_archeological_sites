package org.wit.sites.activities.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.sites.R
import org.wit.sites.activities.siteList.SiteListActivity
import org.wit.sites.main.MainApp
import org.wit.sites.models.firebase.SiteFireStore

class LoginAcitivity : AppCompatActivity(), AnkoLogger {
    lateinit var app: MainApp
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore: SiteFireStore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        progressBar.visibility = View.GONE

        toolbar.title = title
        setSupportActionBar(toolbar)

        app = application as MainApp
        if (app.sites is SiteFireStore) {
            fireStore = app.sites as SiteFireStore
        }

        signUp.setOnClickListener {
            var email = email.text.toString()
            var password = password.text.toString()
            if (email == "" || password == "") {
                toast("Please provide email + password")
            } else {
                showProgress()
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        fireStore!!.fetchSites {
                            hideProgress()
                            startActivityForResult<SiteListActivity>(0)
                        }
                        hideProgress()
                        startActivityForResult<SiteListActivity>(0)
                    }
                    else
                    {
                        hideProgress()
                        toast("Sign Up Failed: ${task.exception?.message}")
                    }
                }

            }
        }


        logIn.setOnClickListener {
            showProgress()
            val email = email.text.toString()
            val password = password.text.toString()
            if (email == "" || password == "") {
                toast("Please provide email + password")
            }
            else {
                showProgress()
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        if (fireStore != null) {
                            fireStore!!.fetchSites {
                                hideProgress()
                                var a=app.sites
                                startActivityForResult<SiteListActivity>(0)
                            }
                        } else {
                            hideProgress()
                            startActivityForResult<SiteListActivity>(0)
                        }
                    } else {
                        hideProgress()
                        toast("Sign Up Failed: ${task.exception?.message}")
                    }
                }
            }
        }
    }


    fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgress() {
        progressBar.visibility = View.GONE
    }


}