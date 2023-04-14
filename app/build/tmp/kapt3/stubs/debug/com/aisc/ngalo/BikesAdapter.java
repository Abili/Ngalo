package com.aisc.ngalo;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0017B\u0005\u00a2\u0006\u0002\u0010\u0003J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0006J\u0006\u0010\f\u001a\u00020\nJ\b\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u000eH\u0016J\u0018\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000eH\u0016J\u000e\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/aisc/ngalo/BikesAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/aisc/ngalo/ViewHolder;", "()V", "bikes", "", "Lcom/aisc/ngalo/models/Bike;", "listener", "Lcom/aisc/ngalo/BikesAdapter$OnCartUpdatedListener;", "add", "", "bike", "clear", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setOnCartUpdatedListener", "OnCartUpdatedListener", "app_debug"})
public final class BikesAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.aisc.ngalo.ViewHolder> {
    private com.aisc.ngalo.BikesAdapter.OnCartUpdatedListener listener;
    private final java.util.List<com.aisc.ngalo.models.Bike> bikes = null;
    
    public BikesAdapter() {
        super();
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
    public com.aisc.ngalo.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull
    com.aisc.ngalo.ViewHolder holder, int position) {
    }
    
    @java.lang.Override
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\u0006"}, d2 = {"Lcom/aisc/ngalo/BikesAdapter$OnCartUpdatedListener;", "", "onCartUpdated", "", "count", "", "app_debug"})
    public static abstract interface OnCartUpdatedListener {
        
        public abstract void onCartUpdated(int count);
    }
}