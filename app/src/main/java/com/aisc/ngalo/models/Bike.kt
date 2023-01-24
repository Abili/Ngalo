package com.aisc.ngalo.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "mylobikes")
data class Bike(
    @PrimaryKey
    val id: String = "",
    val imageUrl: String = "",
    val name: String = "",
    val price: String = "",
    val description: String = "",
    val options: Category?
) : Serializable {
    constructor() : this(
        id = "",
        imageUrl = "",
        name = "",
        price = "",
        description = "",
        options = null
    )
}



