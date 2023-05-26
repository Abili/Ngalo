package com.aisc.ngalo.completed

import com.aisc.ngalo.LocationObject

data class Completed(
    val id: String,
    val description: String? = "",
    val imageUrl: String? = "",
    val location: LocationObject? = null,
    val userName: String? = "",
    val userImageUrl: String? = "",
    val timeOfOrder:String?=""
) {
    fun getTimestampLong(): Long {
        return timeOfOrder?.toLong() ?: 0
    }
}
