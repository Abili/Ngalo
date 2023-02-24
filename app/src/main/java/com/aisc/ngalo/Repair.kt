package com.aisc.ngalo

class Repair(
    var id: String = "",
    var imageUrl: String,
    var description: String = "",
    var latLng: Location? = null
) {
    constructor() : this(id = "", imageUrl = "", description = "", latLng = null)
}