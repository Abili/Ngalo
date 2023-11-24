package com.aisc.ngalo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import com.aisc.ngalo.cart.CartActivity;
import com.aisc.ngalo.cart.CartRepository;
import com.aisc.ngalo.cart.CartViewModel;
import com.aisc.ngalo.cart.CartViewModelFactory;
import com.aisc.ngalo.databinding.ActivityHomeBinding;
import com.aisc.ngalo.rides.RidesActivity;
import com.aisc.ngalo.usersorders.History;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0017"}, d2 = {"Lcom/aisc/ngalo/HomeActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/aisc/ngalo/databinding/ActivityHomeBinding;", "getBinding", "()Lcom/aisc/ngalo/databinding/ActivityHomeBinding;", "setBinding", "(Lcom/aisc/ngalo/databinding/ActivityHomeBinding;)V", "cartRepository", "Lcom/aisc/ngalo/cart/CartRepository;", "getCartRepository", "()Lcom/aisc/ngalo/cart/CartRepository;", "cartViewModel", "Lcom/aisc/ngalo/cart/CartViewModel;", "getCartViewModel", "()Lcom/aisc/ngalo/cart/CartViewModel;", "cartViewModel$delegate", "Lkotlin/Lazy;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"})
public final class HomeActivity extends androidx.appcompat.app.AppCompatActivity {
    public com.aisc.ngalo.databinding.ActivityHomeBinding binding;
    private final kotlin.Lazy cartViewModel$delegate = null;
    
    public HomeActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.aisc.ngalo.databinding.ActivityHomeBinding getBinding() {
        return null;
    }
    
    public final void setBinding(@org.jetbrains.annotations.NotNull
    com.aisc.ngalo.databinding.ActivityHomeBinding p0) {
    }
    
    private final com.aisc.ngalo.cart.CartRepository getCartRepository() {
        return null;
    }
    
    private final com.aisc.ngalo.cart.CartViewModel getCartViewModel() {
        return null;
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
}