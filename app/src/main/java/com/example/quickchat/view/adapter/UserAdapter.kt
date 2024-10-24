package com.example.quickchat.view.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quickchat.R
import com.example.quickchat.data.models.UserDataModel
import com.example.quickchat.databinding.UserSampleBinding
import com.example.quickchat.view.activity.MessagingActivity

class UserAdapter(private var userList : MutableList<UserDataModel>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding = UserSampleBinding.bind(itemView)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list : MutableList<UserDataModel>){
        userList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_sample,parent,false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binding.userNameTxt.text = userList[position].userFirstName
        holder.binding.userEmail.text = userList[position].userEmail

        holder.binding.userSample.setOnClickListener{
            val intent = Intent(holder.itemView.context,MessagingActivity::class.java)
            intent.putExtra("userName",userList[position].userFirstName)
            intent.putExtra("userEmail",userList[position].userEmail)
            intent.putExtra("userUid",userList[position].uid)
            holder.itemView.context.startActivity(intent)

        }
    }
}