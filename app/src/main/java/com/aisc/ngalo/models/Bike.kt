package com.aisc.ngalo.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "mylobikes")
data class Bike(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "",
    val imageUrl: String? = "",
    val name: String? = "",
    val price: String? = "",
    val description: String? = "",
    val options: Category?,
    val quantity: Int? = 1,
    val position: Int? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("options"),

        )

    constructor() : this(
        id = "",
        imageUrl = "",
        name = "",
        price = "",
        description = "",
        options = null,
        quantity = null,
        position = null
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(imageUrl)
        parcel.writeString(name)
        parcel.writeString(price)
        parcel.writeString(description)
        parcel.writeInt(quantity!!)
        parcel.writeInt(position!!)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Bike> {
        override fun createFromParcel(parcel: Parcel): Bike {
            return Bike(parcel)
        }

        override fun newArray(size: Int): Array<Bike?> {
            return arrayOfNulls(size)
        }
    }
}



