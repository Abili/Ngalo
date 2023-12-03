package com.aisc.ngalo

class Repair(
    var id: String? = "",
    var imageUrl: String,
    var description: String = "",
    var latLng: LocationObject? = null,
    val requestTime: String? = "",
    val category: String? = "",
    val phoneNumber: String? = ""
) {
    constructor() : this(
        id = "",
        imageUrl = "",
        description = "",
        latLng = null,
        requestTime = "",
        category = "",
        phoneNumber = ""
    )
}