package com.aisc.ngalo.reciept

import android.os.Parcel
import android.os.Parcelable

data class Reciept(
    val recieptUrl: String? = "",
    val username: String? = "",
    val date: String? = "",
    val time: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(recieptUrl)
        parcel.writeString(username)
        parcel.writeString(date)
        parcel.writeString(time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Reciept> {
        override fun createFromParcel(parcel: Parcel): Reciept {
            return Reciept(parcel)
        }

        override fun newArray(size: Int): Array<Reciept?> {
            return arrayOfNulls(size)
        }
    }
}
