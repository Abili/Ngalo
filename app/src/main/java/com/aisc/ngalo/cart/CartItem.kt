package com.aisc.ngalo.cart

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartItems")
data class CartItem(
    @PrimaryKey val id: String? = "",
    val name: String? = "",
    val price: Int? = null,
    val imageUrl: String? = "",
    var quantity: Int? = null,
    var position: Int = -1,
    var description: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    )

//    fun getTotalPrice(): Int {
//        return price!! * quantity!!.toInt()
//    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "name" to name,
            "price" to price,
            "imageUrl" to imageUrl,
            "quantity" to quantity,
            "position" to position,
            "description" to description
        )
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeValue(price)
        parcel.writeString(imageUrl)
        parcel.writeInt(quantity!!)
        parcel.writeInt(position)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CartItem> {
        override fun createFromParcel(parcel: Parcel): CartItem {
            return CartItem(parcel)
        }

        override fun newArray(size: Int): Array<CartItem?> {
            return arrayOfNulls(size)
        }
    }
}
