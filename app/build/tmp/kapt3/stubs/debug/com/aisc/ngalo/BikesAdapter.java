package com.aisc.ngalo;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.aisc.ngalo.cart.CartItem;
import com.aisc.ngalo.cart.CartViewModel;
import com.aisc.ngalo.databinding.BikeItemBinding;
import com.aisc.ngalo.models.Bike;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import kotlinx.coroutines.Dispatchers;
import java.util.UUID;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0003\"#$B\u000f\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u00a2\u0006\u0002\u0010\u0005J\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\bJ\u0006\u0010\u0017\u001a\u00020\u0015J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u001c\u0010\u001a\u001a\u00020\u00152\n\u0010\u001b\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u001c\u001a\u00020\u0019H\u0016J\u001c\u0010\u001d\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0019H\u0016J\u000e\u0010!\u001a\u00020\u00152\u0006\u0010\t\u001a\u00020\nR\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0005\u00a8\u0006%"}, d2 = {"Lcom/aisc/ngalo/BikesAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/aisc/ngalo/BikesAdapter$ViewHolder;", "viewModel", "Lcom/aisc/ngalo/cart/CartViewModel;", "(Lcom/aisc/ngalo/cart/CartViewModel;)V", "bikes", "", "Lcom/aisc/ngalo/models/Bike;", "listener", "Lcom/aisc/ngalo/BikesAdapter$OnCartUpdatedListener;", "onCartItemAddedListener", "Lcom/aisc/ngalo/BikesAdapter$OnCartItemAddedListener;", "getOnCartItemAddedListener", "()Lcom/aisc/ngalo/BikesAdapter$OnCartItemAddedListener;", "setOnCartItemAddedListener", "(Lcom/aisc/ngalo/BikesAdapter$OnCartItemAddedListener;)V", "getViewModel", "()Lcom/aisc/ngalo/cart/CartViewModel;", "setViewModel", "add", "", "bike", "clear", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setOnCartUpdatedListener", "OnCartItemAddedListener", "OnCartUpdatedListener", "ViewHolder", "app_debug"})
public final class BikesAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.aisc.ngalo.BikesAdapter.ViewHolder> {
    @org.jetbrains.annotations.Nullable
    private com.aisc.ngalo.cart.CartViewModel viewModel;
    private com.aisc.ngalo.BikesAdapter.OnCartUpdatedListener listener;
    @org.jetbrains.annotations.Nullable
    private com.aisc.ngalo.BikesAdapter.OnCartItemAddedListener onCartItemAddedListener;
    private final java.util.List<com.aisc.ngalo.models.Bike> bikes = null;
    
    public BikesAdapter(@org.jetbrains.annotations.Nullable
    com.aisc.ngalo.cart.CartViewModel viewModel) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.aisc.ngalo.cart.CartViewModel getViewModel() {
        return null;
    }
    
    public final void setViewModel(@org.jetbrains.annotations.Nullable
    com.aisc.ngalo.cart.CartViewModel p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.aisc.ngalo.BikesAdapter.OnCartItemAddedListener getOnCartItemAddedListener() {
        return null;
    }
    
    public final void setOnCartItemAddedListener(@org.jetbrains.annotations.Nullable
    com.aisc.ngalo.BikesAdapter.OnCartItemAddedListener p0) {
    }
    
    public final void add(@org.jetbrains.annotations.NotNull
    com.aisc.ngalo.models.Bike bike) {
    }
    
    public final void clear() {
    }
    
    public final void setOnCartUpdatedListener(@org.jetbrains.annotations.NotNull
    com.aisc.ngalo.BikesAdapter.OnCartUpdatedListener listener) {
    }
    
    @org.jetbrains.annotations.NotNull
    @java.lang.Override
    public com.aisc.ngalo.BikesAdapter.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull
    com.aisc.ngalo.BikesAdapter.ViewHolder holder, int position) {
    }
    
    @java.lang.Override
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\u0006"}, d2 = {"Lcom/aisc/ngalo/BikesAdapter$OnCartUpdatedListener;", "", "onCartUpdated", "", "count", "", "app_debug"})
    public static abstract interface OnCartUpdatedListener {
        
        public abstract void onCartUpdated(int count);
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&\u00a8\u0006\u0004"}, d2 = {"Lcom/aisc/ngalo/BikesAdapter$OnCartItemAddedListener;", "", "onCartItemAdded", "", "app_debug"})
    public static abstract interface OnCartItemAddedListener {
        
        public abstract void onCartItemAdded();
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eR\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/aisc/ngalo/BikesAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "listener", "Lcom/aisc/ngalo/BikesAdapter$OnCartUpdatedListener;", "(Lcom/aisc/ngalo/BikesAdapter;Landroid/view/View;Lcom/aisc/ngalo/BikesAdapter$OnCartUpdatedListener;)V", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "binding", "Lcom/aisc/ngalo/databinding/BikeItemBinding;", "bind", "", "bike", "Lcom/aisc/ngalo/models/Bike;", "app_debug"})
    public final class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final com.aisc.ngalo.BikesAdapter.OnCartUpdatedListener listener = null;
        private com.google.firebase.auth.FirebaseAuth auth;
        private final com.aisc.ngalo.databinding.BikeItemBinding binding = null;
        
        public ViewHolder(@org.jetbrains.annotations.NotNull
        android.view.View itemView, @org.jetbrains.annotations.Nullable
        com.aisc.ngalo.BikesAdapter.OnCartUpdatedListener listener) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull
        com.aisc.ngalo.models.Bike bike) {
        }
    }
}