package com.aisc.ngalo.notifications

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NotificationRepository {
    private val notifRef = FirebaseDatabase.getInstance().reference.child("notifications")

    fun getNotificationOrderReady(
        uid: String,
        onCompletedRequestsLoaded: (MutableLiveData<Notification>) -> Unit
    ) {
        notifRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val completedList = MutableLiveData<Notification>()
                if (snapshot.exists()) {
                    for (childSnapshot in snapshot.children) {
                        val content = childSnapshot.child("content").getValue(String::class.java)
                        val order = Notification(
                            content
                        )

                        completedList.postValue(order)
                    }
                }

                // Invoke the callback with the completed list
                onCompletedRequestsLoaded(completedList)
            }


            override fun onCancelled(error: DatabaseError) {
                // Handle the onCancelled event if needed
            }
        })
    }
}

