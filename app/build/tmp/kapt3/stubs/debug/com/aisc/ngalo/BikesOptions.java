package com.aisc.ngalo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.aisc.ngalo.cart.CartActivity;
import com.aisc.ngalo.cart.CartItem;
import com.aisc.ngalo.cart.CartRepository;
import com.aisc.ngalo.cart.CartViewModel;
import com.aisc.ngalo.cart.CartViewModelFactory;
import com.aisc.ngalo.databinding.ActivityBikesOptionsBinding;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004B\u0005\u00a2\u0006\u0002\u0010\u0005J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0012\u0010\u0014\u001a\u00020\u00132\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0014R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u001b\u0010\f\u001a\u00020\r8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0017"}, d2 = {"Lcom/aisc/ngalo/BikesOptions;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/aisc/ngalo/BikesForPurchaseAdapter$OnCartItemAddedListener;", "Lcom/aisc/ngalo/BikesForHireAdapter$OnCartItemAddedListener;", "Lcom/aisc/ngalo/BikesPartsAdapter$OnCartItemAddedListener;", "()V", "bikesAdapter", "Lcom/aisc/ngalo/BikesAdapter;", "bikesForPurchaseAdapter", "Lcom/aisc/ngalo/BikesForPurchaseAdapter;", "binding", "Lcom/aisc/ngalo/databinding/ActivityBikesOptionsBinding;", "cartViewModel", "Lcom/aisc/ngalo/cart/CartViewModel;", "getCartViewModel", "()Lcom/aisc/ngalo/cart/CartViewModel;", "cartViewModel$delegate", "Lkotlin/Lazy;", "onCartItemAdded", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"})
public final class BikesOptions extends androidx.appcompat.app.AppCompatActivity implements com.aisc.ngalo.BikesForPurchaseAdapter.OnCartItemAddedListener, com.aisc.ngalo.BikesForHireAdapter.OnCartItemAddedListener, com.aisc.ngalo.BikesPartsAdapter.OnCartItemAddedListener {
    private com.aisc.ngalo.databinding.ActivityBikesOptionsBinding binding;
    private com.aisc.ngalo.BikesAdapter bikesAdapter;
    private com.aisc.ngalo.BikesForPurchaseAdapter bikesForPurchaseAdapter;
    private final kotlin.Lazy cartViewModel$delegate = null;
    
    public BikesOptions() {
        super();
    }
    
    private final com.aisc.ngalo.cart.CartViewModel getCartViewModel() {
        return null;
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override
    public void onCartItemAdded() {
    }
}