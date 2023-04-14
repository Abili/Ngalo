package com.aisc.ngalo;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001\u000bB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006J\u0012\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/aisc/ngalo/BikeViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/aisc/ngalo/BikeRepository;", "(Lcom/aisc/ngalo/BikeRepository;)V", "fetchData", "", "getAllBikes", "Landroidx/lifecycle/LiveData;", "", "Lcom/aisc/ngalo/models/Bike;", "BikeViewModelFactory", "app_debug"})
public final class BikeViewModel extends androidx.lifecycle.ViewModel {
    private final com.aisc.ngalo.BikeRepository repository = null;
    
    public BikeViewModel(@org.jetbrains.annotations.NotNull
    com.aisc.ngalo.BikeRepository repository) {
        super();
    }
    
    public final void fetchData() {
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.util.List<com.aisc.ngalo.models.Bike>> getAllBikes() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J%\u0010\u0005\u001a\u0002H\u0006\"\b\b\u0000\u0010\u0006*\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00060\tH\u0016\u00a2\u0006\u0002\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/aisc/ngalo/BikeViewModel$BikeViewModelFactory;", "Landroidx/lifecycle/ViewModelProvider$NewInstanceFactory;", "repository", "Lcom/aisc/ngalo/BikeRepository;", "(Lcom/aisc/ngalo/BikeRepository;)V", "create", "T", "Landroidx/lifecycle/ViewModel;", "modelClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", "app_debug"})
    public static final class BikeViewModelFactory extends androidx.lifecycle.ViewModelProvider.NewInstanceFactory {
        private final com.aisc.ngalo.BikeRepository repository = null;
        
        public BikeViewModelFactory(@org.jetbrains.annotations.NotNull
        com.aisc.ngalo.BikeRepository repository) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        @java.lang.Override
        public <T extends androidx.lifecycle.ViewModel>T create(@org.jetbrains.annotations.NotNull
        java.lang.Class<T> modelClass) {
            return null;
        }
    }
}