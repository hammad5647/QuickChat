package com.example.quickchat.domain

import com.example.quickchat.data.helper.AuthHelper
import com.example.quickchat.data.helper.DataBaseHelper.Companion.dbHelper
import com.example.quickchat.data.models.UserDataModel

class ChatRepository {
    companion object {
        var chatRepository = ChatRepository()
    }

    private var authHelper = AuthHelper()

    suspend fun getSignIn(email: String, password: String) = authHelper.signIn(email = email, password = password)
    suspend fun getSignUp(email: String, password: String) = authHelper.signUp(email = email, password = password)

    fun insertUser(userDataModel: UserDataModel) = dbHelper.insertUser(userDataModel)
    suspend fun readUser():MutableList<UserDataModel> = dbHelper.readUser()
}