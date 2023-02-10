package com.aisc.ngalo.models

class User(
    var id: String = "",
    var imageUrl: String,
    var username: String = "",
    var phone: String = "",
    var email: String? = ""
) {
    constructor() : this(id = "", imageUrl = "", username = "", phone = "", email = "")
}
