package com.example.quickchat.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quickchat.R
import com.example.quickchat.data.helper.AuthHelper.Companion.authHelper
import com.example.quickchat.databinding.ActivitySignInBinding
import com.example.quickchat.viewModel.ChatViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val viewModel by viewModels<ChatViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignInBinding.inflate(layoutInflater)
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
        binding.signInBtn.setOnClickListener {
            val email = binding.emailText.text.toString()
            val password = binding.passwordTxt.text.toString()

            if (email.isEmpty()) {
                binding.emailInputLayout.error = "Enter Email"
                binding.emailText.requestFocus()
                return@setOnClickListener
            } else if (password.isEmpty()) {
                binding.passwordInputLayout.error = "Enter Password"
                binding.passwordTxt.requestFocus()
                return@setOnClickListener
            } else {
                binding.emailInputLayout.error = null
                binding.passwordInputLayout.error = null

                GlobalScope.launch {

                    val signInData = viewModel.signInData(email, password)
                    if (signInData == "Success") {
                        authHelper.getCurrentUser()
                        withContext(Dispatchers.Main) {
                            val intent =
                                Intent(this@SignInActivity, InsertDetailActivity::class.java)
                            intent.putExtra("email",email)
                            startActivity(intent)
                            Toast.makeText(this@SignInActivity, "$signInData", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@SignInActivity, "$signInData", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
        binding.createNewAccount.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}