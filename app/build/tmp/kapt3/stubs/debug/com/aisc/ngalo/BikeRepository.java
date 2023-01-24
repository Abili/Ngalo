package com.aisc.ngalo;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0004\b\u0007\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\bJ\u0012\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u000e0\rJ\u000e\u0010\u000f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u0010\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/aisc/ngalo/BikeRepository;", "", "db", "Lcom/aisc/ngalo/network/BikeDatabase;", "(Lcom/aisc/ngalo/network/BikeDatabase;)V", "firebaseRef", "Lcom/google/firebase/database/DatabaseReference;", "delete", "", "bike", "Lcom/aisc/ngalo/models/Bike;", "fetchFromFirebase", "getAllBikes", "Landroidx/lifecycle/LiveData;", "", "insert", "update", "Companion", "app_debug"})
public final class BikeRepository {
    private final com.aisc.ngalo.network.BikeDatabase db = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.aisc.ngalo.BikeRepository.Companion Companion = null;
    @kotlin.jvm.Volatile()
    private static volatile com.aisc.ngalo.BikeRepository instance;
    private final com.google.firebase.database.DatabaseReference firebaseRef = null;
    
    private BikeRepository(com.aisc.ngalo.network.BikeDatabase db) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.aisc.ngalo.models.Bike>> getAllBikes() {
        return null;
    }
    
    public final void fetchFromFirebase() {
    }
    
    public final void insert(@org.jetbrains.annotations.NotNull()
    com.aisc.ngalo.models.Bike bike) {
    }
    
    public final void update(@org.jetbrains.annotations.NotNull()
    com.aisc.ngalo.models.Bike bike) {
    }
    
    public final void delete(@org.jetbrains.annotations.NotNull()
    com.aisc.ngalo.models.Bike bike) {
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/aisc/ngalo/BikeRepository$Companion;", "", "()V", "instance", "Lcom/aisc/ngalo/BikeRepository;", "getInstance", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.aisc.ngalo.BikeRepository getInstance(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}