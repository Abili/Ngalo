package com.aisc.ngalo.reciept

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aisc.ngalo.purchases.PurchaseItem


class ReceiptsViewModel(application: Application) : AndroidViewModel(application) {
    private val receiptsRepository = ReceiptsRepository()

    private val _reciept = MutableLiveData<List<Reciept>>()
    val reciept: MutableLiveData<List<Reciept>> get() = _reciept

    // Function to get the download URL from the repository
    fun getDownloadUrl() {
        receiptsRepository.getDownloadUrl {
            _reciept.value = it
        }

    }
}
