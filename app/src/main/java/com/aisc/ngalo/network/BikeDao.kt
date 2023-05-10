package com.aisc.ngalo.network

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aisc.ngalo.models.Bike

@Dao
interface BikeDao {
    @Insert
    fun insert(bike: Bike)

//    @Query("SELECT timestamp FROM mylobikes ORDER BY timestamp DESC LIMIT 1")
//    fun getLastTimestamp(): Long

    @Update
    fun update(bike: Bike)



    @Delete
    fun delete(bike: Bike)

    @Query("SELECT * FROM mylobikes")
    fun getAll(): LiveData<List<Bike>>

    @Query("SELECT * FROM mylobikes WHERE id = :id")
    fun get(id: Long): LiveData<Bike>
}
