package com.aisc.ngalo.admin

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.util.Locale

class LocationService(private val context: Context) {


    private var currentLocationName: String = ""
    private var fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    val locationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = 0
        fastestInterval = 0
        numUpdates = 1
    }

    fun getCurrentLocationName(): String {
        return currentLocationName
    }



    // ... rest of the class code
     fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the missing permissions
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                PERMISSIONS_REQUEST_LOCATION
            )
            return
        }
        // Permissions already granted, start location updates
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    p0 ?: return
                    for (location in p0.locations) {
                        updateCurrentLocation(location)
                    }
                }
            },
            Looper.getMainLooper()
        )
    }


    private fun updateCurrentLocation(location: Location) {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)

        currentLocationName = addresses?.get(0)!!.getAddressLine(0)
    }

    companion object {
        private const val PERMISSIONS_REQUEST_LOCATION = 1
    }

}

