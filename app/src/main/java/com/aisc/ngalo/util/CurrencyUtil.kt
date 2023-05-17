package com.aisc.ngalo.util

import java.text.NumberFormat
import java.util.*

object CurrencyUtil {
    fun formatCurrency(amount: Int, currencyCode: String): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault())
        formatter.currency = Currency.getInstance(currencyCode)
        return formatter.format(amount)
    }
}
