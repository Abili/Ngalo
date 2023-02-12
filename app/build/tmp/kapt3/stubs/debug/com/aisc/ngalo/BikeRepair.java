package com.aisc.ngalo;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010(\u001a\u00020)H\u0002J\u0012\u0010*\u001a\u00020)2\b\u0010+\u001a\u0004\u0018\u00010,H\u0014J\u0010\u0010-\u001a\u00020)2\u0006\u0010.\u001a\u00020\u001bH\u0016J+\u0010/\u001a\u00020)2\u0006\u00100\u001a\u0002012\f\u00102\u001a\b\u0012\u0004\u0012\u00020\u0007032\u0006\u00104\u001a\u000205H\u0016\u00a2\u0006\u0002\u00106J\b\u00107\u001a\u00020)H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010 X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010!\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010#X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0007X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020&X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\'\u001a\u0004\u0018\u00010\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00068"}, d2 = {"Lcom/aisc/ngalo/BikeRepair;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/google/android/gms/maps/OnMapReadyCallback;", "()V", "binding", "Lcom/aisc/ngalo/databinding/ActivityBikeRepairBinding;", "destination", "", "destinationLatLng", "Lcom/google/android/gms/maps/model/LatLng;", "mFusedLocationClient", "Lcom/google/android/gms/location/FusedLocationProviderClient;", "mLastLocation", "Landroid/location/Location;", "getMLastLocation", "()Landroid/location/Location;", "setMLastLocation", "(Landroid/location/Location;)V", "mLocationCallback", "Lcom/google/android/gms/location/LocationCallback;", "mLocationRequest", "Lcom/google/android/gms/location/LocationRequest;", "getMLocationRequest", "()Lcom/google/android/gms/location/LocationRequest;", "setMLocationRequest", "(Lcom/google/android/gms/location/LocationRequest;)V", "mMap", "Lcom/google/android/gms/maps/GoogleMap;", "mPatientRideId", "mRepairRequestsRef", "Lcom/google/firebase/database/DatabaseReference;", "mapFragment", "Lcom/google/android/gms/maps/SupportMapFragment;", "pickupLocation", "pickupMarker", "Lcom/google/android/gms/maps/model/Marker;", "repairRequests", "requestBol", "", "requestService", "checkLocationPermission", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onMapReady", "googleMap", "onRequestPermissionsResult", "requestCode", "", "permissions", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "showRequestBottomSheetDialog", "app_debug"})
public final class BikeRepair extends androidx.appcompat.app.AppCompatActivity implements com.google.android.gms.maps.OnMapReadyCallback {
    private com.google.android.gms.maps.GoogleMap mMap;
    @org.jetbrains.annotations.Nullable()
    private android.location.Location mLastLocation;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.gms.location.LocationRequest mLocationRequest;
    private final java.lang.String repairRequests = "RepaireRequests";
    private com.google.android.gms.location.FusedLocationProviderClient mFusedLocationClient;
    private com.google.android.gms.maps.model.LatLng pickupLocation;
    private boolean requestBol = false;
    private com.google.android.gms.maps.model.Marker pickupMarker;
    private com.google.android.gms.maps.SupportMapFragment mapFragment;
    private final java.lang.String destination = null;
    private final java.lang.String requestService = null;
    private com.google.android.gms.maps.model.LatLng destinationLatLng;
    private com.google.firebase.database.DatabaseReference mRepairRequestsRef;
    private java.lang.String mPatientRideId;
    private com.aisc.ngalo.databinding.ActivityBikeRepairBinding binding;
    private com.google.android.gms.location.LocationCallback mLocationCallback;
    
    public BikeRepair() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.location.Location getMLastLocation() {
        return null;
    }
    
    public final void setMLastLocation(@org.jetbrains.annotations.Nullable()
    android.location.Location p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.google.android.gms.location.LocationRequest getMLocationRequest() {
        return null;
    }
    
    public final void setMLocationRequest(@org.jetbrains.annotations.Nullable()
    com.google.android.gms.location.LocationRequest p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void showRequestBottomSheetDialog() {
    }
    
    @java.lang.Override()
    public void onMapReady(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.GoogleMap googleMap) {
    }
    
    private final void checkLocationPermission() {
    }
    
    @java.lang.Override()
    public void onRequestPermissionsResult(int requestCode, @org.jetbrains.annotations.NotNull()
    java.lang.String[] permissions, @org.jetbrains.annotations.NotNull()
    int[] grantResults) {
    }
}