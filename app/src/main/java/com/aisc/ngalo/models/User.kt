package com.aisc.ngalo.models

class User(
    var id: String = "",
    var imageUrl: String? = null,
    var username: String? = "",
    var phone: String? = "",
    var email: String? = "",
    val category: String? = ""
) {
    constructor() : this(
        id = "", imageUrl = "", username = "", phone = "", email = "", category = ""
    )
}
