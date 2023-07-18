package com.aisc.ngalo.helpers

import com.google.android.gms.maps.model.LatLng
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.model.DirectionsResult

fun fetchRoute(
    origin: LatLng,
    destination: LatLng,
    apiKey: String,
    callback: (DirectionsResult) -> Unit
) {
    val geoApiContext = GeoApiContext.Builder()
        .apiKey(apiKey)
        .build()

    val directionsApiRequest = DirectionsApi.newRequest(geoApiContext)
        .origin(com.google.maps.model.LatLng(origin.latitude, origin.longitude))
        .destination(com.google.maps.model.LatLng(destination.latitude, destination.longitude))
        .alternatives(true) // Include alternative routes

    val directionsResult = directionsApiRequest.await()
    callback(directionsResult)
}
