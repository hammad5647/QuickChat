package com.example.quickchat.view.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quickchat.R
import com.example.quickchat.data.models.UserDataModel
import com.example.quickchat.databinding.ActivityMoreUsersBinding
import com.example.quickchat.view.adapter.UserAdapter
import com.example.quickchat.viewModel.ChatViewModel.Companion.chatViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoreUsersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoreUsersBinding
    private lateinit var adapter: UserAdapter
    private var list = mutableListOf<UserDataModel>()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMoreUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        clickInit()
        adapter = UserAdapter(list)
        binding.rvMoreUser.adapter = adapter

        GlobalScope.launch {
           list = chatViewModel.readUserData()
            withContext(Dispatchers.Main) {
                adapter.updateList(list)
                Log.e("USERS", "onCreate==================================: $list", )
            }
        }
    }
    private fun clickInit() {
        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}