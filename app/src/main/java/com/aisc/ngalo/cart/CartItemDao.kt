package com.aisc.ngalo.cart

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItem)

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): LiveData<List<CartItem>>
}
