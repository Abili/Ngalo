package com.aisc.ngalo.progress

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aisc.ngalo.Repair
import kotlinx.coroutines.launch

class ProgressViewModel(application: Application) : AndroidViewModel(application) {
    private val progressRepository: ProgressRepository = ProgressRepository(this.getApplication())

    fun orderRecieved(): LiveData<String> {
        val progress = MutableLiveData<String>()
        viewModelScope.launch {
            progress.value = progressRepository.orderRecieved()
        }
        return progress
    }

    fun orderReady(): LiveData<Repair> {
        val progress = MutableLiveData<Repair>()
        viewModelScope.launch {
            progress.value = progressRepository.orderReady()
        }
        return progress
    }

}