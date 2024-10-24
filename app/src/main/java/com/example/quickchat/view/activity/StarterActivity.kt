package com.example.quickchat.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quickchat.R
import com.example.quickchat.databinding.ActivityStarterBinding

class StarterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityStarterBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityStarterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.startupSignUpBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.startUpLoginBtn.setOnClickListener{
            startActivity(Intent(this, SignInActivity::class.java))
        }

    }
}