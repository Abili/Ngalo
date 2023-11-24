package com.aisc.ngalo.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey val id: String = "",
    val name: String? = "",
    val price: Int? = null,
    val imageUrl: String? = "",
    var quantity: Int? = null,
    var position: Int = -1,
    var description: String? = "",
    var category: String? = ""
) {


    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "name" to name,
            "price" to price,
            "imageUrl" to imageUrl,
            "quantity" to quantity,
            "position" to position,
            "description" to description,
            "category" to category
        )
    }


}
