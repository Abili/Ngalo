package com.aisc.ngalo.util

import java.text.NumberFormat
import java.util.*

object CurrencyUtil {
    fun formatCurrency(amount: Int, currencyCode: String): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault())
        formatter.currency = Currency.getInstance(currencyCode)
        formatter.maximumFractionDigits = 0  // Set maximum fraction digits to 0
        val formattedAmount = formatter.format(amount)
        return formattedAmount.replace(currencyCode, "Ugx ") // Add space between currency symbol and amount
    }

}
