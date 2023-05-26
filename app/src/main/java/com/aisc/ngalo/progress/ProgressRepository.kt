package com.aisc.ngalo.progress

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aisc.ngalo.Repair
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


    suspend fun orderPreparing(): String? {
        return withContext(Dispatchers.IO) {
            var recieved: String? = null
            val progressRef = FirebaseDatabase.getInstance().reference.child("progress")
            progressRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (progSnap in snapshot.children) {
                        val orderRec = snapshot.child("preparing").getValue(String::class.java)
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


    suspend fun orderReady(): Repair? {
        return withContext(Dispatchers.IO) {
            var recieved: Repair? = null
            val progressRef =
                FirebaseDatabase.getInstance().reference.child("RepaireRequests")
                    .child("completed")
            progressRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (progSnap in snapshot.children) {
                        val category = snapshot.child("category").getValue(String::class.java)
                        val time = snapshot.child("timeOfOrder").getValue(String::class.java)
                        val desc = snapshot.child("description").getValue(String::class.java)
                        val image = snapshot.child("imageUrl").getValue(String::class.java)
                        recieved = Repair(null, image!!, desc!!, null, time, category)
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