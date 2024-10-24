package com.example.quickchat.data.models

data class ChatDocModel(
    var uid1: MutableMap<String,String>,
    var uid2: MutableMap<String,String>,
    var uids: MutableList<String>
)