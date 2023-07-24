package com.aisc.ngalo.reciept

import android.os.Parcel
import android.os.Parcelable

data class ReceiptItem(
    val itemName: String?="",
    val quantity: String?="",
    val price: String?="",
    val itemImage: String?= "",
    val tp: String?= ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(itemName)
        parcel.writeString(quantity)
        parcel.writeString(price)
        parcel.writeString(itemImage)
        parcel.writeString(itemImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReceiptItem> {
        override fun createFromParcel(parcel: Parcel): ReceiptItem {
            return ReceiptItem(parcel)
        }

        override fun newArray(size: Int): Array<ReceiptItem?> {
            return arrayOfNulls(size)
        }
    }
}