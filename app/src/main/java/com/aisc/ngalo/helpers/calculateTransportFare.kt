package com.aisc.ngalo.helpers

fun calculateTransportFare(distanceInKm: Int): Int {
    val ratePerKm = 500
    return distanceInKm * ratePerKm
}
