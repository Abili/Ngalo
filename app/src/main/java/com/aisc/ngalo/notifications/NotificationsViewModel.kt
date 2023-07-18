package com.aisc.ngalo.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth


class NotificationsViewModel : ViewModel() {
    private val notificationRepository = NotificationRepository()
    private val _notificationRequest = MutableLiveData<Notification>()
    val notifications: MutableLiveData<Notification> get() = _notificationRequest

    private val uid = FirebaseAuth.getInstance().uid

    fun loadNotifications() {
        notificationRepository.getNotificationOrderReady(uid!!) { completedList ->
            _notificationRequest.value = completedList.value
        }
    }
}
