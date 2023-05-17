package com.aisc.ngalo.helpers

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.aisc.ngalo.admin.LocationService
import com.google.android.gms.location.*

class LocationHelper(context: Context) {

    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    val locationService = LocationService(context)

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(callback: (Location?) -> Unit) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    callback(location)
                    locationService.startLocationUpdates()
                } else {
                    val locationRequest = LocationRequest.create().apply {
                        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                        interval = 0
                        fastestInterval = 0
                        numUpdates = 1
                    }

                    val locationCallback = object : LocationCallback() {
                        override fun onLocationResult(p0: LocationResult) {
                            p0 ?: return
                            callback(p0.locations.first())

                        }
                    }

                    fusedLocationClient.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        Looper.getMainLooper()
                    )
                }
            }
    }
}
