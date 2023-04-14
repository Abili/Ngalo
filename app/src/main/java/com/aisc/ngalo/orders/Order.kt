package com.aisc.ngalo.orders

import com.aisc.ngalo.LocationObject
import com.google.firebase.database.ServerValue

data class Order(
    val id: String,
    val description: String? = "",
    val imageUrl: String? = "",
    val location: LocationObject? = null,
    val userName: String? = "",
    val userImageUrl: String? = "",
    val timeOfOrder: String? = "¬",
    val category: String? = ""
)
