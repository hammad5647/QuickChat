package com.example.quickchat

import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quickchat.data.helper.AuthHelper.Companion.authHelper
import com.example.quickchat.data.helper.DataBaseHelper.Companion.dbHelper
import com.example.quickchat.data.models.MessageDataModel
import com.example.quickchat.data.models.UserDataModel
import com.example.quickchat.databinding.ActivityMainBinding
import com.example.quickchat.view.activity.MoreUsersActivity
import com.example.quickchat.view.activity.ProfileActivity
import com.example.quickchat.view.activity.StarterActivity
import com.example.quickchat.view.adapter.ChatAdapter
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var myData: UserDataModel
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var chatAdapter: ChatAdapter
    private var chatModel = mutableListOf<MessageDataModel>()
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        clickInit()

        chatAdapter = ChatAdapter(chatModel)
        binding.rvMessages.adapter = chatAdapter

        GlobalScope.launch {
            myData = dbHelper.readMyData()
        }
    }

    private fun clickInit() {
        binding.optionsBtn.setOnClickListener {
            popMenu()
        }
        binding.profileBtn.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        binding.moreUsersBtn.setOnClickListener {
            startActivity(Intent(this, MoreUsersActivity::class.java))
        }
    }

    private fun popMenu() {
        val popupMenu = PopupMenu(this, binding.profileBtn)
        popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.logOut -> {
                    authHelper.signOut()
                    startActivity(Intent(this, StarterActivity::class.java))
                    finish()
                    true
                }
                else -> {
                    false
                }
            }
        }
        popupMenu.show()
    }
}