package com.example.quickchat.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quickchat.MainActivity
import com.example.quickchat.R
import com.example.quickchat.data.helper.AuthHelper.Companion.authHelper
import com.example.quickchat.data.models.UserDataModel
import com.example.quickchat.databinding.ActivityInsertDetailBinding
import com.example.quickchat.viewModel.ChatViewModel.Companion.chatViewModel

class InsertDetailActivity : AppCompatActivity() {

    var email: String = ""
    lateinit var binding: ActivityInsertDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityInsertDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var intent = intent
        email = intent.getStringExtra("email").toString()
        binding.emailText.setText(email)
        clickInit()
    }

    private fun clickInit() {
        binding.submitBtn.setOnClickListener {
            val firstName = binding.firstnameEditText.text.toString()
            val lastName = binding.lastNameTxt.text.toString()
            val userMobile = binding.mobileTxt.text.toString()
            binding.emailText.setText(email)
//           val email = getIntent.getStringExtra("email").toString()
//
//            binding.emailText.setText(email)

            if (firstName.isEmpty()) {
                binding.nameInputLayout.error = "Please Enter First Name"

            } else if (lastName.isEmpty()) {
                binding.lastNameInputLayout.error = "Please Enter Last Name"
            }
            else if (userMobile.length in 10..10){
                binding.mobileInputLayout.error = "Please Enter Valid Mobile Number"
            }
            else if (userMobile.isEmpty()) {
                binding.mobileInputLayout.error = "Please Enter Mobile Number"
            } else {
                binding.nameInputLayout.error = null
                binding.lastNameInputLayout.error = null
                binding.mobileInputLayout.error = null
                val userModel = UserDataModel(
                    userFirstName = firstName,
                    userLastName = lastName,
                    userEmail = email,
                    userPhone = userMobile,
                    uid = authHelper.user!!.uid
                )
                chatViewModel.insertUser(userModel)
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}