package com.example.quickchat.data.helper

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthHelper {
    companion object {
        var authHelper = AuthHelper()
    }

    private var auth = FirebaseAuth.getInstance()
    private var message: String? = null
    var user: FirebaseUser? = null

    suspend fun signUp(email: String, password: String): String? {
        try {
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                Log.e("TAG", "signUp: $it")
                message = "Success"
            }.addOnFailureListener {
                message = it.message
            }.await()
        } catch (e: FirebaseAuthException) {
            message = "${e.message}"
        }
        return message
    }

    suspend fun signIn(email: String, password: String): String? {
        try {
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                message = "Success"
                Log.e("TAG", "signIn: $it")
            }.addOnFailureListener {
                message = it.message
            }.await()
        } catch (e: FirebaseAuthException) {
            message = "${e.message}"
        }
        return message
    }


    fun getCurrentUser() {
        user = auth.currentUser
    }

    fun signOut() {
        auth.signOut()
    }
}