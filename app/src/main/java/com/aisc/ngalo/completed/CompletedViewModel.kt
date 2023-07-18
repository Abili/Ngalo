package com.aisc.ngalo.completed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


class CompletedViewModel  : ViewModel() {
    private val completedRepository = CompletedRepository()
    private val _completedRequests = MutableLiveData<List<Completed>>()
    val completedRequests: LiveData<List<Completed>> get() = _completedRequests

    private val uid = FirebaseAuth.getInstance().uid

    fun loadCompletedRequests() {
        completedRepository.getCompletedRequests() { completedList ->
            _completedRequests.value = completedList
        }
    }
}
