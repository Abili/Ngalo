package com.aisc.ngalo.helpers

import com.aisc.ngalo.completed.Completed
import com.aisc.ngalo.orders.Order
import com.aisc.ngalo.purchases.PurchaseItem
import com.aisc.ngalo.usersorders.UserOrder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TimeConverter {
    fun dateSimpleDateFormatPair(order: UserOrder): Pair<Date, SimpleDateFormat> {
        val timestamp = order.getTimestampLong()
        val date = Date(timestamp)
        val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        return Pair(date, timeFormat)

    }

    fun dateSimpleDateFormatPair(order: Completed): Pair<Date, SimpleDateFormat> {
        val timestamp = order.getTimestampLong()
        val date = Date(timestamp)
        val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        return Pair(date, timeFormat)
    }
    fun dateSimpleDateFormatPair(order: Order): Pair<Date, SimpleDateFormat> {
        val timestamp = order.getTimestampLong()
        val date = Date(timestamp)
        val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        return Pair(date, timeFormat)
    }
    fun dateSimpleDateFormatPair(order: PurchaseItem): Pair<Date, SimpleDateFormat> {
        val timestamp = order.time
        val date = Date(timestamp!!.toLong())
        val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        return Pair(date, timeFormat)
    }
}