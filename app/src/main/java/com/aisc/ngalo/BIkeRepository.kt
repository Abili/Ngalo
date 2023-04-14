package com.aisc.ngalo

import android.content.Context
import androidx.room.Room
import com.aisc.ngalo.models.Bike
import com.aisc.ngalo.network.BikeDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.*


class BikeRepository private constructor(private val db: BikeDatabase) {

    companion object {
        @Volatile
        private var instance: BikeRepository? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                val db = Room.databaseBuilder(context, BikeDatabase::class.java, "bikes_db")
                    .build()
                instance ?: BikeRepository(db).also { instance = it }
            }
    }

    private val firebaseRef = FirebaseDatabase.getInstance().getReference("bikes")

    fun getAllBikes() = db.bikeDao().getAll()

    fun fetchFromFirebase() {
        CoroutineScope(Dispatchers.IO).launch {
            //val lastTimestamp = getLastTimestamp()
            firebaseRef
//                .orderByChild("timestamp")
//                .startAt(lastTimestamp.toString())
                .addValueEventListener(object:ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
//                        val bikeList = snapshot.children.map {
//                            it.getValue(Bike::class.java)
//                        }
//                        bikeList.forEach { bike ->
//                            insert(bike!!)
//                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
        }
    }

//    private fun getLastTimestamp(): Long {
//        return db.bikeDao().getLastTimestamp(
//
//        )
//    }


    // CRUD methods
    fun insert(bike: Bike) = db.bikeDao().insert(bike)
    fun update(bike: Bike) = db.bikeDao().update(bike)
    fun delete(bike: Bike) = db.bikeDao().delete(bike)
}
