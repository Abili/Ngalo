package com.aisc.ngalo.reciept

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ReceiptsRepository {
    // Function to retrieve the download URL from Firebase Database
    fun getDownloadUrl(onRecieptsLoaded: (List<Reciept>) -> Unit) {
        val reciepts = mutableListOf<Reciept>()
        val databaseReference = FirebaseDatabase.getInstance().reference.child("receipts")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (recieptShot in snapshot.children) {
                    val recieptUrl =
                        recieptShot.child("downloadUrl").getValue(String::class.java)
                    val date = recieptShot.child("date").getValue(String::class.java)
                    val time = recieptShot.child("time").getValue(String::class.java)
                    val username = recieptShot.child("username").getValue(String::class.java)
                    val rec = Reciept(
                        recieptUrl,
                        username,
                        date,
                        time,
                    )
                    reciepts.add(rec)
                }
                onRecieptsLoaded(reciepts)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}






