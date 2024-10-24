package com.example.quickchat.data.helper

import android.util.Log
import com.example.quickchat.data.helper.AuthHelper.Companion.authHelper
import com.example.quickchat.data.models.ChatDocModel
import com.example.quickchat.data.models.MessageDataModel
import com.example.quickchat.data.models.UserDataModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

@Suppress("NAME_SHADOWING")
class DataBaseHelper {
    companion object {
        var dbHelper = DataBaseHelper()
    }

    private var db = FirebaseFirestore.getInstance()

    fun insertUser(userModel: UserDataModel) {
        db.collection("user").document(authHelper.user!!.uid).set(userModel)
        Log.e("chatModel", "insertUser: ${userModel.toString()}")
    }

    suspend fun readMyData(): UserDataModel {
        val snapshot = db.collection("user").document(authHelper.user!!.uid).get().await()

        val uid = snapshot.id
        val firstname = snapshot.data?.get("userFirstName")
        val lastName = snapshot.data?.get("userLastName")
        val email = snapshot.data?.get("userEmail")
        val mobile = snapshot.data?.get("userPhone")



        return  UserDataModel(uid, firstname.toString(), lastName.toString(), email.toString(), mobile.toString())
    }

    private suspend fun checkChatDoc(clientUid: String): QuerySnapshot? {
        val docs = db.collection("chat")
            .whereNotEqualTo("uids", listOf(authHelper.user!!.uid, clientUid))
            .get().await()
        if (docs.documents.size == 0) {
            val docs = db.collection("chat")
                .whereEqualTo("uids", listOf(authHelper.user!!.uid, clientUid))
                .get().await()
            return docs
        }
        return docs
    }

    suspend fun sendMessage(
        clientId: String,
        mssgeModel: MessageDataModel,
        docModel: ChatDocModel
    ) {
        val docId: String?
        val docs = checkChatDoc(clientId)
        if (docs!!.documents.size == 0) {
            val refDoc = db.collection("chat")
                .add(docModel).await()
            docId = refDoc.id
        } else {
            docId = docs.documents[0].id
        }
        db.collection("chat").document(docId).collection("message").add(mssgeModel)
    }

    suspend fun readUser(): MutableList<UserDataModel> {

        val list = mutableListOf<UserDataModel>()
        val snapshot =
            db.collection("user").whereNotEqualTo("uid", authHelper.user!!.uid).get().await()

        for (i in snapshot.documents) {
            val uid = i.id
            val firstname = i.data?.get("userFirstName")
            val lastName = i.data?.get("userLastName")
            val email = i.data?.get("userEmail")
            val mobile = i.data?.get("userPhone")

            val user = UserDataModel(
                uid,
                userFirstName = firstname.toString(),
                userLastName = lastName.toString(),
                userEmail = email.toString(),
                userPhone = mobile.toString()
            )
            list.add(user)
            Log.e("chatModel", "readUser: $list")
        }
        return list
    }
//
//    fun readMessage(){
//        var mssge = db.collection("chat").
//        }
//    }

    fun insertMessage(messageModel: MessageDataModel) {
        db.collection("chat ").document().collection("message").document(messageModel.senderUid)
            .set(messageModel)
    }
}
