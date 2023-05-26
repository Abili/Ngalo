package com.aisc.ngalo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.*;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.aisc.ngalo.databinding.ActivityBikeRepairBinding;
import com.bumptech.glide.Glide;
import com.firebase.geofire.GeoFire;
import com.google.android.gms.location.*;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import java.io.IOException;
import java.util.*;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u00bc\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0006\b\u0007\u0018\u0000 `2\u00020\u00012\u00020\u0002:\u0001`B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010E\u001a\u00020FH\u0002J\b\u0010G\u001a\u00020FH\u0002J&\u0010H\u001a\u0004\u0018\u00010I2\u0006\u0010J\u001a\u00020K2\b\u0010\u0019\u001a\u0004\u0018\u00010\f2\b\u0010L\u001a\u0004\u0018\u00010\fH\u0002J\"\u0010M\u001a\u00020F2\u0006\u0010N\u001a\u00020O2\u0006\u0010P\u001a\u00020O2\b\u0010Q\u001a\u0004\u0018\u00010\u0006H\u0014J\u0012\u0010R\u001a\u00020F2\b\u0010S\u001a\u0004\u0018\u00010TH\u0015J\u0010\u0010U\u001a\u00020F2\u0006\u0010V\u001a\u000201H\u0016J+\u0010W\u001a\u00020F2\u0006\u0010N\u001a\u00020O2\f\u0010X\u001a\b\u0012\u0004\u0012\u00020\f0Y2\u0006\u0010Z\u001a\u00020[H\u0016\u00a2\u0006\u0002\u0010\\J\b\u0010]\u001a\u00020FH\u0002J\b\u0010^\u001a\u00020FH\u0002J\u0012\u0010_\u001a\u00020F2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0002R\u0016\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u00020\fX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00140\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0019\u001a\u00020\fX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u000e\"\u0004\b\u001b\u0010\u0010R\u000e\u0010\u001c\u001a\u00020\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u001f\u001a\u0004\u0018\u00010\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001a\u0010$\u001a\u00020%X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\'\"\u0004\b(\u0010)R\u001c\u0010*\u001a\u0004\u0018\u00010+X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\u0010\u00100\u001a\u0004\u0018\u000101X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u00102\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u00103\u001a\u0004\u0018\u000104X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u00105\u001a\u0004\u0018\u000106X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u00107\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u00108\u001a\u0004\u0018\u000109X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u00020\fX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020<X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010=\u001a\u0004\u0018\u00010\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010>\u001a\u0004\u0018\u00010?X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010@\u001a\u00020<X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bA\u0010B\"\u0004\bC\u0010D\u00a8\u0006a"}, d2 = {"Lcom/aisc/ngalo/BikeRepair;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/google/android/gms/maps/OnMapReadyCallback;", "()V", "activityResultLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "binding", "Lcom/aisc/ngalo/databinding/ActivityBikeRepairBinding;", "currentLocation", "Lcom/aisc/ngalo/LocationObject;", "descriptionOfProblems", "", "getDescriptionOfProblems", "()Ljava/lang/String;", "setDescriptionOfProblems", "(Ljava/lang/String;)V", "destination", "destinationLocation", "downloadUrl", "Landroid/net/Uri;", "imageUri", "Landroidx/compose/runtime/MutableState;", "latitude", "", "location", "getLocation", "setLocation", "longitude", "mFusedLocationClient", "Lcom/google/android/gms/location/FusedLocationProviderClient;", "mLastLocation", "getMLastLocation", "()Lcom/aisc/ngalo/LocationObject;", "setMLastLocation", "(Lcom/aisc/ngalo/LocationObject;)V", "mLocationCallback", "Lcom/google/android/gms/location/LocationCallback;", "getMLocationCallback", "()Lcom/google/android/gms/location/LocationCallback;", "setMLocationCallback", "(Lcom/google/android/gms/location/LocationCallback;)V", "mLocationRequest", "Lcom/google/android/gms/location/LocationRequest;", "getMLocationRequest", "()Lcom/google/android/gms/location/LocationRequest;", "setMLocationRequest", "(Lcom/google/android/gms/location/LocationRequest;)V", "mMap", "Lcom/google/android/gms/maps/GoogleMap;", "mPatientRideId", "mRepairRequestsRef", "Lcom/google/firebase/database/DatabaseReference;", "mapFragment", "Lcom/google/android/gms/maps/SupportMapFragment;", "pickupLocation", "pickupMarker", "Lcom/google/android/gms/maps/model/Marker;", "repairRequests", "requestBol", "", "requestService", "selectedLocation", "Lcom/google/android/gms/maps/model/LatLng;", "zoomUpdated", "getZoomUpdated", "()Z", "setZoomUpdated", "(Z)V", "checkLocationPermission", "", "fetchLocationName", "generateBitmap", "Landroid/graphics/Bitmap;", "context", "Landroid/content/Context;", "duration", "onActivityResult", "requestCode", "", "resultCode", "data", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onMapReady", "googleMap", "onRequestPermissionsResult", "permissions", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "openImageFiles", "setCurrentLocation", "uploadToFirebase", "Companion", "app_debug"})
public final class BikeRepair extends androidx.appcompat.app.AppCompatActivity implements com.google.android.gms.maps.OnMapReadyCallback {
    private com.google.android.gms.maps.GoogleMap mMap;
    @org.jetbrains.annotations.Nullable
    private com.aisc.ngalo.LocationObject mLastLocation;
    @org.jetbrains.annotations.Nullable
    private com.google.android.gms.location.LocationRequest mLocationRequest;
    private final java.lang.String repairRequests = "RepaireRequests";
    private com.google.android.gms.location.FusedLocationProviderClient mFusedLocationClient;
    private boolean requestBol = false;
    private com.google.android.gms.maps.model.Marker pickupMarker;
    private com.google.android.gms.maps.SupportMapFragment mapFragment;
    private final java.lang.String destination = null;
    private final java.lang.String requestService = null;
    private com.aisc.ngalo.LocationObject pickupLocation;
    private com.aisc.ngalo.LocationObject currentLocation;
    private com.aisc.ngalo.LocationObject destinationLocation;
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
    private com.google.android.gms.maps.model.LatLng selectedLocation;
    private boolean zoomUpdated = false;
    @org.jetbrains.annotations.NotNull
    private com.google.android.gms.location.LocationCallback mLocationCallback;
    @org.jetbrains.annotations.NotNull
    public static final com.aisc.ngalo.BikeRepair.Companion Companion = null;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    public static final int CAMERA_REQUEST_CODE = 1;
    public static final int GALLERY_REQUEST_CODE = 2;
    
    public BikeRepair() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.aisc.ngalo.LocationObject getMLastLocation() {
        return null;
    }
    
    public final void setMLastLocation(@org.jetbrains.annotations.Nullable
    com.aisc.ngalo.LocationObject p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.google.android.gms.location.LocationRequest getMLocationRequest() {
        return null;
    }
    
    public final void setMLocationRequest(@org.jetbrains.annotations.Nullable
    com.google.android.gms.location.LocationRequest p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDescriptionOfProblems() {
        return null;
    }
    
    public final void setDescriptionOfProblems(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getLocation() {
        return null;
    }
    
    public final void setLocation(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    @android.annotation.SuppressLint(value = {"ClickableViewAccessibility"})
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    public final boolean getZoomUpdated() {
        return false;
    }
    
    public final void setZoomUpdated(boolean p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.google.android.gms.location.LocationCallback getMLocationCallback() {
        return null;
    }
    
    public final void setMLocationCallback(@org.jetbrains.annotations.NotNull
    com.google.android.gms.location.LocationCallback p0) {
    }
    
    private final void setCurrentLocation() {
    }
    
    private final android.graphics.Bitmap generateBitmap(android.content.Context context, java.lang.String location, java.lang.String duration) {
        return null;
    }
    
    private final void uploadToFirebase(android.net.Uri downloadUrl) {
    }
    
    private final void openImageFiles() {
    }
    
    @java.lang.Override
    public void onMapReady(@org.jetbrains.annotations.NotNull
    com.google.android.gms.maps.GoogleMap googleMap) {
    }
    
    private final void checkLocationPermission() {
    }
    
    @java.lang.Override
    public void onRequestPermissionsResult(int requestCode, @org.jetbrains.annotations.NotNull
    java.lang.String[] permissions, @org.jetbrains.annotations.NotNull
    int[] grantResults) {
    }
    
    @java.lang.Override
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable
    android.content.Intent data) {
    }
    
    private final void fetchLocationName() {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/aisc/ngalo/BikeRepair$Companion;", "", "()V", "AUTOCOMPLETE_REQUEST_CODE", "", "CAMERA_REQUEST_CODE", "GALLERY_REQUEST_CODE", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}