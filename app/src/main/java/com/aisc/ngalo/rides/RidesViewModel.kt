package com.aisc.ngalo.rides

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class RidesViewModel(application: Application) : AndroidViewModel(application) {
    private val ridesRepository = RidesRepository()
    private val _rides = MutableLiveData<List<Ride>>()
    val rides: LiveData<List<Ride>> get() = _rides


    fun loadAllRides() {
        ridesRepository.getAllRides { rideList ->
            _rides.value = rideList
        }
    }
    fun loadUserRides() {
        ridesRepository.getUserRides { rideList ->
            _rides.value = rideList
        }
    }
}