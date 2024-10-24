package com.example.quickchat.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quickchat.R
import com.example.quickchat.data.models.UserDataModel
import com.example.quickchat.databinding.ActivitySignUpBinding
import com.example.quickchat.viewModel.ChatViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel by viewModels<ChatViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        clickInit()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun clickInit() {
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.signUpBtn.setOnClickListener {
            val email = binding.emailText.text.toString()
            val password = binding.passwordTxt.text.toString()

            if (email.isEmpty()) {
                binding.emailInputLayout.error = "Please Enter Email"
            } else if (password.isEmpty()) {
                binding.passwordInputLayout.error = "Please Enter Password"
            } else {
                binding.emailInputLayout.error = null
                binding.passwordInputLayout.error = null

                GlobalScope.launch {
                    val signUpData = viewModel.signUpData(email, password)
                    if (signUpData == "Success") {
                        withContext(Dispatchers.Main) {
                            startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
                            val model = UserDataModel(userEmail = email)
                            Log.d("Insert", "clickInit:============ $model ")
                            //dbHelper.insertUser(model)
                            Log.e("Success", "clickInit: $it")
                            Toast.makeText(this@SignUpActivity, "$signUpData", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@SignUpActivity, "$signUpData", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}