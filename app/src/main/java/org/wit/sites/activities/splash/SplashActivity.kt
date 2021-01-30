package org.wit.sites.activities.splash

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import org.wit.sites.R
import org.wit.sites.activities.login.LoginAcitivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        object : CountDownTimer(1500, 4000) {
            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
                startActivity(Intent(applicationContext, LoginAcitivity::class.java))
            }
        }.start()
    }
}