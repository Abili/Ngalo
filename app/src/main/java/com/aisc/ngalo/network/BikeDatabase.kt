package com.aisc.ngalo.network

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aisc.ngalo.models.Bike

@Database(entities = [Bike::class], version = 1, exportSchema = false)
abstract class BikeDatabase : RoomDatabase() {
    abstract fun bikeDao(): BikeDao
}