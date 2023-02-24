package com.aisc.ngalo

data class Location(
    val name: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null
) {
    constructor() : this(name = "", latitude = null, longitude =null)
}
