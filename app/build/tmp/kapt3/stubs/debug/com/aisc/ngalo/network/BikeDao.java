package com.aisc.ngalo.network;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.aisc.ngalo.models.Bike;

@androidx.room.Dao
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00072\u0006\u0010\b\u001a\u00020\tH\'J\u0014\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000b0\u0007H\'J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0010\u0010\r\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'\u00a8\u0006\u000e"}, d2 = {"Lcom/aisc/ngalo/network/BikeDao;", "", "delete", "", "bike", "Lcom/aisc/ngalo/models/Bike;", "get", "Landroidx/lifecycle/LiveData;", "id", "", "getAll", "", "insert", "update", "app_debug"})
public abstract interface BikeDao {
    
    @androidx.room.Insert
    public abstract void insert(@org.jetbrains.annotations.NotNull
    com.aisc.ngalo.models.Bike bike);
    
    @androidx.room.Update
    public abstract void update(@org.jetbrains.annotations.NotNull
    com.aisc.ngalo.models.Bike bike);
    
    @androidx.room.Delete
    public abstract void delete(@org.jetbrains.annotations.NotNull
    com.aisc.ngalo.models.Bike bike);
    
    @org.jetbrains.annotations.NotNull
    @androidx.room.Query(value = "SELECT * FROM mylobikes")
    public abstract androidx.lifecycle.LiveData<java.util.List<com.aisc.ngalo.models.Bike>> getAll();
    
    @org.jetbrains.annotations.NotNull
    @androidx.room.Query(value = "SELECT * FROM mylobikes WHERE id = :id")
    public abstract androidx.lifecycle.LiveData<com.aisc.ngalo.models.Bike> get(long id);
}