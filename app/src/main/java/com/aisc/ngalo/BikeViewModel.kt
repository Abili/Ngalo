package com.aisc.ngalo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aisc.ngalo.models.Bike
import kotlinx.coroutines.launch

class BikeViewModel(private val repository: BikeRepository) : ViewModel() {


    class BikeViewModelFactory(private val repository: BikeRepository) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BikeViewModel(repository) as T
        }
    }

    fun fetchData() {
        viewModelScope.launch {
            repository.fetchFromFirebase()
        }
    }

    fun getAllBikes(): LiveData<List<Bike>> = repository.getAllBikes()
}

