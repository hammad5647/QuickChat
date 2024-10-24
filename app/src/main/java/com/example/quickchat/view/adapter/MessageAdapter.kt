package com.example.quickchat.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quickchat.R
import com.example.quickchat.data.models.MessageDataModel
import com.example.quickchat.databinding.MessageSampleBinding

class MessageAdapter(private var messages: MutableList<MessageDataModel>): RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = MessageSampleBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_sample, parent, false)
        return MessageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.binding.message.text = message.message

    }
}