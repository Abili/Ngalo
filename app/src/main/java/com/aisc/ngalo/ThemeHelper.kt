package com.aisc.ngalo

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

object ThemeHelper {
    const val LIGHT_MODE = "light"
    const val DARK_MODE = "dark"

    fun applyTheme(theme: String) {
        when (theme) {
            LIGHT_MODE -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            DARK_MODE -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    fun applySystemTheme(context: Context) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }
}
