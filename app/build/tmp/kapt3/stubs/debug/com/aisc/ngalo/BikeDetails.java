package com.aisc.ngalo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.aisc.ngalo.cart.CartActivity;
import com.aisc.ngalo.cart.CartItem;
import com.aisc.ngalo.cart.CartViewModel;
import com.aisc.ngalo.databinding.ActivityBikeDetailsBinding;
import com.aisc.ngalo.models.Bike;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import kotlinx.coroutines.Dispatchers;
import java.util.UUID;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0002J\u0012\u0010\u0016\u001a\u00020\u00152\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0014J\u0018\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\fH\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/aisc/ngalo/BikeDetails;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/aisc/ngalo/databinding/ActivityBikeDetailsBinding;", "getBinding", "()Lcom/aisc/ngalo/databinding/ActivityBikeDetailsBinding;", "setBinding", "(Lcom/aisc/ngalo/databinding/ActivityBikeDetailsBinding;)V", "cartViewModel", "Lcom/aisc/ngalo/cart/CartViewModel;", "counter", "", "getCounter", "()I", "setCounter", "(I)V", "orders", "", "Lcom/aisc/ngalo/cart/CartItem;", "onCartItemAdded", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "updateCartItemsQuantity", "itemId", "", "quantity", "Companion", "app_debug"})
public final class BikeDetails extends androidx.appcompat.app.AppCompatActivity {
    private int counter = 0;
    private com.aisc.ngalo.cart.CartViewModel cartViewModel;
    private final java.util.List<com.aisc.ngalo.cart.CartItem> orders = null;
    public com.aisc.ngalo.databinding.ActivityBikeDetailsBinding binding;
    @org.jetbrains.annotations.NotNull
    public static final com.aisc.ngalo.BikeDetails.Companion Companion = null;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.Class<com.aisc.ngalo.models.Bike> EXTRA_BIKE = null;
    
    public BikeDetails() {
        super();
    }
    
    public final int getCounter() {
        return 0;
    }
    
    public final void setCounter(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.aisc.ngalo.databinding.ActivityBikeDetailsBinding getBinding() {
        return null;
    }
    
    public final void setBinding(@org.jetbrains.annotations.NotNull
    com.aisc.ngalo.databinding.ActivityBikeDetailsBinding p0) {
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void updateCartItemsQuantity(java.lang.String itemId, int quantity) {
    }
    
    private final void onCartItemAdded() {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lcom/aisc/ngalo/BikeDetails$Companion;", "", "()V", "EXTRA_BIKE", "Ljava/lang/Class;", "Lcom/aisc/ngalo/models/Bike;", "getEXTRA_BIKE", "()Ljava/lang/Class;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.Class<com.aisc.ngalo.models.Bike> getEXTRA_BIKE() {
            return null;
        }
    }
}