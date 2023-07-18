package com.aisc.ngalo.purchases

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class PurchasesViewModel(application: Application) : AndroidViewModel(application) {

    private val purchasesRepository = PurchasesRepository()
    private val _requests = MutableLiveData<List<PurchaseItem>>()
    private val _userPurchases = MutableLiveData<List<ItemsPurchased>>()
    private val _users = MutableLiveData<List<PurchaseItem>>()
    private val purchaseCountLiveData = MutableLiveData<Int>()
    val purchases: LiveData<List<PurchaseItem>> get() = _requests
    val userPurchases: LiveData<List<ItemsPurchased>> get() = _userPurchases
    val users: LiveData<List<PurchaseItem>> get() = _users

    private val uid = FirebaseAuth.getInstance().uid

    fun loadPurchasedItems() {
        purchasesRepository.getAllPurchases {
            _requests.value = it
        }
    }

    fun loadUserPurchasedItems() {
        purchasesRepository.getUserPurchases {
            _requests.value = it
        }
    }

    private val purchasesLiveData = MutableLiveData<List<ItemsPurchased>>()

    fun loadUserPurchasedItems(time:String, itemsId: String) {
        purchasesRepository.items(time,itemsId) { completedList ->
            purchasesLiveData.value = completedList
        }
    }

    fun getPurchasedItems(): LiveData<List<ItemsPurchased>> {
        return purchasesLiveData
    }


    fun userGroups() {
        purchasesRepository.getUserGroup {
            _users.value = it
        }
    }

    fun getuserId(): LiveData<String> {
        val userid = MutableLiveData<String>()
        viewModelScope.launch {
            userid.value = purchasesRepository.userId()
        }
        return userid
    }

    fun emptyPurchases() {
        purchasesRepository.emptyPurchase()

    }

    fun numberOfItems() {
        purchasesRepository.getPurchaseCount {
            purchaseCountLiveData.postValue(it)
        }


    }
}
