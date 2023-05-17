package com.aisc.ngalo.helpers

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.launch

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private val locationRepository: LocationRepository = LocationRepository(this.getApplication())

    fun updateCurrentLocationName(): LiveData<String> {
        val currentLocationName = MutableLiveData<String>()
        viewModelScope.launch {
            currentLocationName.value = locationRepository.getCurrentLocationName()
        }
        return currentLocationName
    }

    fun updateCurrentLocationCordinates(): LiveData<String> {
        val currentLocationCoordinates = MutableLiveData<String>()
        viewModelScope.launch {
            currentLocationCoordinates.value = locationRepository.getCurrentLocationCoordinates().toString()
        }
        return currentLocationCoordinates
    }

    private val placesClient: PlacesClient by lazy {
        Places.createClient(application)
    }

    private fun getAddress(latLng: LatLng) {
        val request = FindCurrentPlaceRequest.newInstance(listOf(Place.Field.NAME))
        if (ActivityCompat.checkSelfPermission(
                getApplication(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getApplication(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        placesClient.findCurrentPlace(request).addOnSuccessListener { response ->
            val placeLikelihood = response.placeLikelihoods.firstOrNull()
            placeLikelihood?.place?.name?.let {
                // Do something with the name of the current place
            }
        }.addOnFailureListener {
            // Handle the exception
        }
    }

    companion object {
        const val TAG = "LocationRepository"
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}
