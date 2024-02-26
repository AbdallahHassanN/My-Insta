package com.example.myinsta.repository.chatRepo

import com.example.myinsta.common.Constants.CHAT_ID
import com.example.myinsta.common.Constants.COLLECTION_CHATS
import com.example.myinsta.common.Constants.COLLECTION_MESSAGES
import com.example.myinsta.common.Constants.COLLECTION_USERS
import com.example.myinsta.common.Constants.CONTENT
import com.example.myinsta.common.Constants.CREATED_AT
import com.example.myinsta.common.Constants.ERROR
import com.example.myinsta.common.Constants.LAST_MESSAGE
import com.example.myinsta.common.Constants.LAST_MESSAGE_TIME
import com.example.myinsta.common.Constants.SENDER_ID
import com.example.myinsta.models.ChatUserList
import com.example.myinsta.models.User
import com.example.myinsta.response.Resource
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ChatRepository {
    override fun sendMessage(
        senderId: String,
        senderUsername: String,
        receiverId: String,
        receiverUsername: String,
        message: String,
        chatId: String
    ): Flow<Resource<String>> = callbackFlow {
        val msgObj = mutableMapOf<String, Any>()
        msgObj[CONTENT] = message
        msgObj[CREATED_AT] = FieldValue.serverTimestamp()
        msgObj[SENDER_ID] = senderId

        val chatDoc = firestore.collection(COLLECTION_CHATS).document(chatId)
        msgObj[CHAT_ID] = chatId

        val senderChatDoc = firestore.collection(COLLECTION_USERS)
            .document(senderId)
            .collection(COLLECTION_CHATS)
            .document(chatDoc.id)

        val receiverChatDoc = firestore.collection(COLLECTION_USERS)
            .document(receiverId)
            .collection(COLLECTION_CHATS)
            .document(chatDoc.id)

        val msgDoc = chatDoc.collection(COLLECTION_MESSAGES).document()

        val newSenderMessage = ChatUserList(
            id = chatDoc.id, friendUsername = receiverUsername, lastMessage = message,
            imgUrl = receiverId, lastMessageTime = com.google.firebase.Timestamp.now()
        )
        val newReceiverMessage = ChatUserList(
            id = chatDoc.id, friendUsername = senderUsername, lastMessage = message,
            imgUrl = senderId, lastMessageTime = com.google.firebase.Timestamp.now()
        )
        firestore.runTransaction { transaction ->
            transaction.set(
                msgDoc,
                msgObj
            )
            transaction.set(
                senderChatDoc,
                newSenderMessage
            )
            transaction.set(
                receiverChatDoc,
                newReceiverMessage
            )
            transaction.update(
                receiverChatDoc,
                LAST_MESSAGE, message
            )
            transaction.update(
                receiverChatDoc, LAST_MESSAGE_TIME,
                FieldValue.serverTimestamp()
            )
            null
        }.addOnSuccessListener {
            trySend(Resource.Success(chatDoc.id))
        }.addOnFailureListener {
            trySend(Resource.Error(it.message ?: ERROR))
        }
        awaitClose()
    }

    override fun getChats(userId: String): Flow<Resource<List<ChatUserList>>> = callbackFlow {
        val snapshotListener = firestore.collection(COLLECTION_USERS)
            .document(userId)
            .collection(COLLECTION_CHATS)
            .addSnapshotListener { snapshot, e ->
                if (snapshot != null) {
                    val chats = snapshot.toObjects(ChatUserList::class.java)
                    trySend(Resource.Success(chats))
                } else {
                    trySend(Resource.Error(e?.message ?: ERROR))
                }
            }
        awaitClose {
            snapshotListener.remove()
        }
    }
}