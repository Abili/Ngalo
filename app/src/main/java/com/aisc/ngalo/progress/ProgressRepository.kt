package com.aisc.ngalo.progress

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProgressRepository(context: Context) {

    suspend fun orderRecieved(): String? {
        return withContext(Dispatchers.IO) {
            var recieved: String? = null
            val progressRef = FirebaseDatabase.getInstance().reference.child("progress")
            progressRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (progSnap in snapshot.children) {
                        val orderRec = snapshot.child("recieved").getValue(String::class.java)
                        recieved = orderRec
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })


            recieved
        }
    }
}