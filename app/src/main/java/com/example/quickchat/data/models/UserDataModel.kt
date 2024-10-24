package com.example.quickchat.data.models

data class UserDataModel(

    var uid: String? = null,
    var userFirstName: String? = null,
    var userLastName: String? = null,
    var userEmail: String,
    var userPhone : String? = null
)