package com.aisc.ngalo.helpers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import com.aisc.ngalo.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class LocationRepository(private val context: Context) {

    private val fusedLocationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }
    private val placesClient: PlacesClient by lazy {
        Places.createClient(context)
    }

    /**
     * Returns the name of the user's current location based on their device's GPS coordinates.
     * This function must be called from a coroutine or a suspending function.
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
    suspend fun getCurrentLocationName2(): String? = withContext(Dispatchers.IO) {
        // Get the last known location from the FusedLocationProviderClient
        val location: Location? = fusedLocationProviderClient.lastLocation.await()

        // Use a Geocoder to get the name of the location based on the GPS coordinates
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(location?.latitude ?: 0.0, location?.longitude ?: 0.0, 1)

        // Return the name of the location (or null if it couldn't be determined)
        addresses?.get(0)?.getAddressLine(0)
    }

    /**
     * Returns the name of the user's current location based on their device's GPS coordinates.
     * This function must be called from a coroutine or a suspending function.
     */
    suspend fun getCurrentLocationName(): String? {
        return withContext(Dispatchers.IO) {
            var locationName: String? = null
            try {
                val fusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(context)
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    val location = fusedLocationProviderClient.lastLocation.await()
                        ?: // Location not found
                        return@withContext null
                    val geo = Geocoder(context, Locale.getDefault())
                    val addresses = geo.getFromLocation(location.latitude, location.longitude, 1)
                    if (addresses!!.isEmpty()) {
                        locationName = context.getString(R.string.waiting_for_location)
                    } else {
                        addresses[0]?.let { address ->
                            locationName = when {
                                address.thoroughfare == null -> address.locality
                                address.locality == null -> "Unknown Location"
                                else -> "${address.locality}, ${address.thoroughfare}"
                            }
                        }
                    }
                } else {
                    // Permission is not granted
                    // You can show a message to the user to request the permission
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error getting current location name", e)
            }
            locationName
        }
    }

    suspend fun getCurrentLocationCoordinates(): String? {
        return withContext(Dispatchers.IO) {
            var coordinates: String? = null
            try {
                val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    val location = fusedLocationProviderClient.lastLocation.await()
                    coordinates =
                        "${location.latitude}, ${location.longitude}"

                } else {
                    // Permission is not granted
                    // You can show a message to the user to request the permission
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error getting current location coordinates", e)
            }
            coordinates
        }
    }



    companion object {
        const val TAG = "LocationRepository"
    }
}
