package com.example.quickchat.viewModel

import androidx.lifecycle.ViewModel
import com.example.quickchat.data.models.UserDataModel
import com.example.quickchat.domain.ChatRepository.Companion.chatRepository

class ChatViewModel : ViewModel() {
    companion object{
        var chatViewModel = ChatViewModel()
    }
    suspend fun signUpData(email: String, password: String): String? {
        val signUpResult = chatRepository.getSignUp(email, password)
        return signUpResult
    }

    suspend fun signInData(email: String, password: String): String? {
        val signInResult = chatRepository.getSignIn(email, password)
        return signInResult
    }
    suspend fun readUserData() : MutableList<UserDataModel> = chatRepository.readUser()
    fun insertUser(userDataModel: UserDataModel) {
        chatRepository.insertUser(userDataModel)
    }

}