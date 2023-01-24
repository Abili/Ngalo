package com.aisc.ngalo.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "mylobikesmodels")
data class BikeModel(
    @PrimaryKey
    val id: String,
    val imageUrl: String = "",
    val name: String = "",
    val price: String = "",
) : Serializable {
    constructor() : this(id = "", imageUrl = "", name = "", price = "")
}
