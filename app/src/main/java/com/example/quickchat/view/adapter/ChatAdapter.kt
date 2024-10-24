package com.example.quickchat.view.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quickchat.R
import com.example.quickchat.data.models.MessageDataModel
import com.example.quickchat.databinding.ChatSampleBinding
import com.example.quickchat.view.activity.MessagingActivity

class ChatAdapter(private var chats: MutableList<MessageDataModel>) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = ChatSampleBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_sample, parent, false)
        return ChatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.binding.chatSampleView.setOnClickListener {
            val intent = Intent(holder.itemView.context, MessagingActivity::class.java)
            holder.itemView.context.startActivity(intent)
        }
    }
}