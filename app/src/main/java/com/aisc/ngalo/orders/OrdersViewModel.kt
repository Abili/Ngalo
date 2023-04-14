package com.aisc.ngalo.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class OrdersViewModel : ViewModel() {
    val uid = FirebaseAuth.getInstance().uid
    private val ordersRef = FirebaseDatabase.getInstance().reference.child("RepaireRequests")
        .child(uid!!)

    val ordersFlow: Flow<List<Order>> = flow {
        val ordersSnapshot = ordersRef.get().await()
        val orders = ordersSnapshot.children.mapNotNull { snapshot ->
            snapshot.getValue<Order>()
        }
        emit(orders)
    }

    val ordersLiveData: LiveData<List<Order>> = liveData {
        emitSource(ordersFlow.asLiveData())
    }
}
