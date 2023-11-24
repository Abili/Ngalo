package com.aisc.ngalo.purchases

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "itemspurchased")
data class ItemsPurchased(
    @PrimaryKey val id: String? = "",
    val purchase_name: String? = "",
    val purchase_price: Int? = null,
    val purchase_imageUrl: String? = "",
    val grandtotal: String? = "",
    val transportfares: String? = "",
    var quantity: Int? = null,
    val userlocation: String? = "",
    val pickupLocation: String? = "",
    val time: String? = "",
    val description:String?="",
    val category:String?=""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(purchase_name)
        parcel.writeValue(purchase_price)
        parcel.writeString(purchase_imageUrl)
        parcel.writeValue(transportfares)
        parcel.writeValue(quantity)
        parcel.writeValue(userlocation)
        parcel.writeValue(pickupLocation)
        parcel.writeString(time)
        parcel.writeString(description)
        parcel.writeString(category)
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

