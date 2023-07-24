package com.aisc.ngalo.purchases

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purchaseitem")
data class PurchaseItem(
    @PrimaryKey val id: String? = "",
    val userimage: String? = "",
    val purchase_name: String? = "",
    val purchase_price: Int? = null,
    val purchase_imageUrl: String? = "",
    val user_name: String? = "",
    val user_location: String? = "",
    val pickup_location: String? = "",
    var quantity: Int? = null,
    val grandTotal: String? = "",
    val transport: String? = "",
    val time: String? = "",
    val paymentMethod: String? = "",
    val contact: String? = "",
    val description: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(userimage)
        parcel.writeString(purchase_name)
        parcel.writeValue(purchase_price)
        parcel.writeString(purchase_imageUrl)
        parcel.writeString(user_name)
        parcel.writeString(user_location)
        parcel.writeString(pickup_location)
        parcel.writeValue(quantity)
        parcel.writeString(grandTotal)
        parcel.writeString(transport)
        parcel.writeString(time)
        parcel.writeString(paymentMethod)
        parcel.writeString(contact)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PurchaseItem> {
        override fun createFromParcel(parcel: Parcel): PurchaseItem {
            return PurchaseItem(parcel)
        }

        override fun newArray(size: Int): Array<PurchaseItem?> {
            return arrayOfNulls(size)
        }
    }

}

