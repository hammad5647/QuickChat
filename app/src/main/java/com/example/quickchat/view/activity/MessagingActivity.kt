package com.example.quickchat.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quickchat.MainActivity.Companion.myData
import com.example.quickchat.R
import com.example.quickchat.data.helper.AuthHelper.Companion.authHelper
import com.example.quickchat.data.helper.DataBaseHelper.Companion.dbHelper
import com.example.quickchat.data.models.ChatDocModel
import com.example.quickchat.data.models.MessageDataModel
import com.example.quickchat.databinding.ActivityMessagingBinding
import com.example.quickchat.view.adapter.MessageAdapter
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MessagingActivity : AppCompatActivity() {
    private lateinit var userId: String
    lateinit var userName: String
    private lateinit var binding: ActivityMessagingBinding
    private lateinit var adapter: MessageAdapter
    private var messageList = mutableListOf<MessageDataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessagingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        clickInit()
        getIntentData()
        adapter = MessageAdapter(messageList)
        binding.messageRv.adapter = adapter


    }

    fun getIntentData() {
        val intent = intent
        userName = intent.getStringExtra("userName").toString()
        userId = intent.getStringExtra("userUid").toString()
        binding.msgUserName.text = userName
    }

    @SuppressLint("NotifyDataSetChanged")
    @OptIn(DelicateCoroutinesApi::class)
    private fun clickInit() {
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.sendBtn.setOnClickListener {
            GlobalScope.launch {
                val message = binding.mssgeinputTxt.text.toString()

                val uid1 = mutableMapOf<String, String>()
                val uid2 = mutableMapOf<String, String>()

                uid1["uid"] = userId
                uid1["name"] = userName
                uid2["uid"] = authHelper.user!!.uid
                uid2["name"] = myData.userFirstName.toString()

                var uids = mutableListOf(userId, authHelper.user!!.uid)

                val mssgeModel = MessageDataModel(userId, "", message)

                dbHelper.sendMessage(
                    userId,
                    mssgeModel = mssgeModel,
                    docModel = ChatDocModel(uid1, uid2, uids))


            }
            binding.mssgeinputTxt.setText("")
        }
    }
}