package com.aisc.ngalo;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u00b0\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u0000 R2\u00020\u00012\u00020\u0002:\u0001RB\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u00109\u001a\u00020:H\u0002J\"\u0010;\u001a\u00020:2\u0006\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020=2\b\u0010?\u001a\u0004\u0018\u00010\u0006H\u0014J\u0012\u0010@\u001a\u00020:2\b\u0010A\u001a\u0004\u0018\u00010BH\u0015J\u0010\u0010C\u001a\u00020:2\u0006\u0010D\u001a\u00020+H\u0016J+\u0010E\u001a\u00020:2\u0006\u0010<\u001a\u00020=2\f\u0010F\u001a\b\u0012\u0004\u0012\u00020\n0G2\u0006\u0010H\u001a\u00020IH\u0016\u00a2\u0006\u0002\u0010JJ\b\u0010K\u001a\u00020:H\u0002J2\u0010L\u001a\u00020:2\u0006\u0010M\u001a\u00020\n2\u0006\u0010N\u001a\u00020O2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u00152\b\u0010P\u001a\u0004\u0018\u00010\nH\u0002J\u0012\u0010Q\u001a\u00020:2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0002R\u0016\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u00020\nX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0010\u0010\u000f\u001a\u0004\u0018\u00010\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\u00020\nX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\f\"\u0004\b\u0018\u0010\u000eR\u000e\u0010\u0019\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u000e\u0010\"\u001a\u00020#X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010$\u001a\u0004\u0018\u00010%X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\'\"\u0004\b(\u0010)R\u0010\u0010*\u001a\u0004\u0018\u00010+X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010,\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010-\u001a\u0004\u0018\u00010.X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010/\u001a\u0004\u0018\u000100X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u00101\u001a\u0004\u0018\u000102X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u00103\u001a\u0004\u0018\u000104X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020\nX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u00106\u001a\u000207X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u00108\u001a\u0004\u0018\u00010\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006S"}, d2 = {"Lcom/aisc/ngalo/BikeRepair;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/google/android/gms/maps/OnMapReadyCallback;", "()V", "activityResultLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "binding", "Lcom/aisc/ngalo/databinding/ActivityBikeRepairBinding;", "descriptionOfProblems", "", "getDescriptionOfProblems", "()Ljava/lang/String;", "setDescriptionOfProblems", "(Ljava/lang/String;)V", "destination", "downloadUrl", "Landroid/net/Uri;", "imageUri", "Landroidx/compose/runtime/MutableState;", "latitude", "", "location", "getLocation", "setLocation", "longitude", "mFusedLocationClient", "Lcom/google/android/gms/location/FusedLocationProviderClient;", "mLastLocation", "Landroid/location/Location;", "getMLastLocation", "()Landroid/location/Location;", "setMLastLocation", "(Landroid/location/Location;)V", "mLocationCallback", "Lcom/google/android/gms/location/LocationCallback;", "mLocationRequest", "Lcom/google/android/gms/location/LocationRequest;", "getMLocationRequest", "()Lcom/google/android/gms/location/LocationRequest;", "setMLocationRequest", "(Lcom/google/android/gms/location/LocationRequest;)V", "mMap", "Lcom/google/android/gms/maps/GoogleMap;", "mPatientRideId", "mRepairRequestsRef", "Lcom/google/firebase/database/DatabaseReference;", "mapFragment", "Lcom/google/android/gms/maps/SupportMapFragment;", "pickupLocation", "Lcom/google/android/gms/maps/model/LatLng;", "pickupMarker", "Lcom/google/android/gms/maps/model/Marker;", "repairRequests", "requestBol", "", "requestService", "checkLocationPermission", "", "onActivityResult", "requestCode", "", "resultCode", "data", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onMapReady", "googleMap", "onRequestPermissionsResult", "permissions", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "openImageFiles", "showRequestBottomSheetDialog", "userId", "geoFire", "Lcom/firebase/geofire/GeoFire;", "name", "uploadToFirebase", "Companion", "app_debug"})
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
    private com.google.firebase.database.DatabaseReference mRepairRequestsRef;
    private java.lang.String mPatientRideId;
    private com.aisc.ngalo.databinding.ActivityBikeRepairBinding binding;
    private final androidx.compose.runtime.MutableState<android.net.Uri> imageUri = null;
    private android.net.Uri downloadUrl;
    private androidx.activity.result.ActivityResultLauncher<android.content.Intent> activityResultLauncher;
    public java.lang.String descriptionOfProblems;
    public java.lang.String location;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private com.google.android.gms.location.LocationCallback mLocationCallback;
    @org.jetbrains.annotations.NotNull()
    public static final com.aisc.ngalo.BikeRepair.Companion Companion = null;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    public static final int CAMERA_REQUEST_CODE = 1;
    public static final int GALLERY_REQUEST_CODE = 2;
    
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
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDescriptionOfProblems() {
        return null;
    }
    
    public final void setDescriptionOfProblems(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLocation() {
        return null;
    }
    
    public final void setLocation(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @android.annotation.SuppressLint(value = {"ClickableViewAccessibility"})
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void uploadToFirebase(android.net.Uri downloadUrl) {
    }
    
    private final void showRequestBottomSheetDialog(java.lang.String userId, com.firebase.geofire.GeoFire geoFire, double latitude, double longitude, java.lang.String name) {
    }
    
    private final void openImageFiles() {
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
    
    @java.lang.Override()
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data) {
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/aisc/ngalo/BikeRepair$Companion;", "", "()V", "AUTOCOMPLETE_REQUEST_CODE", "", "CAMERA_REQUEST_CODE", "GALLERY_REQUEST_CODE", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}