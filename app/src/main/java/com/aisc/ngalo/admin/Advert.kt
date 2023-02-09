package com.aisc.ngalo.admin

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aisc.ngalo.models.Category
import java.io.Serializable


@Entity(tableName = "ngaloads")
data class Advert(
    @PrimaryKey
    val id: String = "",
    val imageUrl: String = "",
    val name: String = ""
) : Serializable {
    constructor() : this(
        id = "",
        imageUrl = "",
        name = ""
    )
}



