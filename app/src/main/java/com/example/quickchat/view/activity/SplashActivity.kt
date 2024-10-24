package com.example.quickchat.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quickchat.MainActivity
import com.example.quickchat.R
import com.example.quickchat.data.helper.AuthHelper.Companion.authHelper

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        authHelper.getCurrentUser()


        Handler(Looper.getMainLooper()).postDelayed({
            authHelper.getCurrentUser()
            if (authHelper.user == null) {

                val intent = Intent(this, StarterActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            finish()
        }, 3000)
    }
}