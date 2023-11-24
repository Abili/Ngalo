package com.aisc.ngalo.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class OrdersViewModel : ViewModel() {
    private val ordersRepository = OrdersRepository()
    private val _requests = MutableLiveData<List<Order>>()
    private val requestCountLiveData = MutableLiveData<Int>()
    val completedRequests: LiveData<List<Order>> get() = _requests

    private val uid = FirebaseAuth.getInstance().uid

    fun loadRepaireRequests() {
        ordersRepository.getAllRepairRequests() { requestLists ->
            _requests.value = requestLists
        }
    }

    fun deleteItem(id: String) {
        ordersRepository.completedOrder(id)

    }


    fun orderCount() {
        viewModelScope.launch {
            ordersRepository.getOrdersCount() {
                requestCountLiveData.postValue(it)
            }
        }
    }

    fun observeorderCount(): LiveData<Int> {
        return requestCountLiveData
    }
}

