package com.aisc.ngalo

import android.app.Application
import com.aisc.ngalo.cart.CartRepository
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NgaloApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Enable Firebase database persistence
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }

    // Lazy initialization of AppDatabase
    val appDatabase: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }

    // Lazy initialization of CartRepository
    val cartRepository: CartRepository by lazy {
        CartRepository(appDatabase.cartItemDao())
    }
}


