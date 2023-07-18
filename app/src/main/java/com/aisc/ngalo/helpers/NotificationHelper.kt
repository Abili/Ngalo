package com.aisc.ngalo.helpers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ShortcutManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.WorkerThread
import androidx.core.content.getSystemService
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.aisc.ngalo.R
import com.aisc.ngalo.notifications.NotificationsViewModel

/**
 * Handles all operations related to [Notification].
 */
class NotificationHelper(private val context: Context) {
    var notificationsViewModel =
        ViewModelProvider(context as ViewModelStoreOwner)[NotificationsViewModel::class.java]

    companion object {
        /**
         * The notification channel for messages. This is used for showing Bubbles.
         */
        private const val CHANNEL_NEW_MESSAGES = "new_messages"

        private const val REQUEST_CONTENT = 1
        private const val REQUEST_BUBBLE = 2
    }

    private val notificationManager: NotificationManager =
        context.getSystemService() ?: throw IllegalStateException()

    private val shortcutManager: ShortcutManager =
        context.getSystemService() ?: throw IllegalStateException()

    fun setUpNotificationChannels() {
        if (notificationManager.getNotificationChannel(CHANNEL_NEW_MESSAGES) == null) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_NEW_MESSAGES,
                    context.getString(R.string.channel_new_messages),
                    // The importance must be IMPORTANCE_HIGH to show Bubbles.
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = context.getString(R.string.channel_new_messages_description)
                }
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    @WorkerThread
    fun showNotification() {
        // Check Firebase for progress and confirmation from admin
        // Retrieve progress and confirmation information from Firebase node

        val progress = getProgressFromFirebase() // Retrieve progress from Firebase
        val isApproved = isOrderApprovedByAdmin() // Check if order is approved by admin

        val builder = Notification.Builder(context, CHANNEL_NEW_MESSAGES)
            // A notification can be shown as a bubble by calling setBubbleMetadata()
            // The user can turn off the bubble in system settings. In that case, this notification
            // is shown as a normal notification instead of a bubble. Make sure that this
            // notification works as a normal notification as well.
            .setContentTitle("Order Status")
            .setSmallIcon(R.drawable.ngalo_logo)
            .setCategory(Notification.CATEGORY_MESSAGE)
            .setShowWhen(true)
            .setWhen(System.currentTimeMillis()) // Use appropriate timestamp

        // Add progress information to the notification
        builder.style = Notification.BigTextStyle().bigText(progress)

        // Add confirmation information to the notification if approved
        if (isApproved) {
            builder.addAction(
                Notification.Action.Builder(
                    null, "Order Approved", null
                ).build()
            )
        }

        notificationManager.notify(1, builder.build())
    }

    fun dismissNotification(id: Int) {
        notificationManager.cancel(id)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun canBubble(): Boolean {
        val channel = notificationManager.getNotificationChannel(
            CHANNEL_NEW_MESSAGES
        )
        return notificationManager.areBubblesAllowed() || channel?.canBubble() == true
    }

    // Dummy functions to simulate retrieving progress and approval status from Firebase
    private fun getProgressFromFirebase(): String {

        var notifContent: String? = null
        notificationsViewModel.loadNotifications()
        notificationsViewModel.notifications.observe(context as LifecycleOwner) {
            notifContent = it.content.toString()
        }
        return notifContent!!
    }

    private fun isOrderApprovedByAdmin(): Boolean {
        return true // Return true if order is approved by admin, false otherwise
    }
}
