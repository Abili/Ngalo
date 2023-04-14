package com.aisc.ngalo;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u0000 ;2\u00020\u0001:\u0001;B\u0005\u00a2\u0006\u0002\u0010\u0002J\"\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020-2\b\u0010/\u001a\u0004\u0018\u00010\u0005H\u0016J$\u00100\u001a\u0002012\u0006\u00102\u001a\u0002032\b\u00104\u001a\u0004\u0018\u0001052\b\u00106\u001a\u0004\u0018\u000107H\u0017J\b\u00108\u001a\u00020+H\u0002J\b\u00109\u001a\u00020+H\u0002J\u0012\u0010:\u001a\u00020+2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0002R\u0016\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u0019X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u000e\u0010\u001e\u001a\u00020\u001fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082.\u00a2\u0006\u0002\n\u0000R\u0016\u0010\"\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001f0#X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010&\u001a\u00020\u0019X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\'\u0010\u001b\"\u0004\b(\u0010\u001dR\u000e\u0010)\u001a\u00020%X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006<"}, d2 = {"Lcom/aisc/ngalo/RequestBottomSheetDialog;", "Lcom/google/android/material/bottomsheet/BottomSheetDialogFragment;", "()V", "activityResultLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "bikeRepair", "Lcom/aisc/ngalo/BikeRepair;", "getBikeRepair", "()Lcom/aisc/ngalo/BikeRepair;", "setBikeRepair", "(Lcom/aisc/ngalo/BikeRepair;)V", "binding", "Lcom/aisc/ngalo/databinding/BikerepairBottomSheetBinding;", "getBinding", "()Lcom/aisc/ngalo/databinding/BikerepairBottomSheetBinding;", "setBinding", "(Lcom/aisc/ngalo/databinding/BikerepairBottomSheetBinding;)V", "customDialogBinding", "Lcom/aisc/ngalo/databinding/ImagesDialogBinding;", "getCustomDialogBinding", "()Lcom/aisc/ngalo/databinding/ImagesDialogBinding;", "setCustomDialogBinding", "(Lcom/aisc/ngalo/databinding/ImagesDialogBinding;)V", "descriptionOfProblems", "", "getDescriptionOfProblems", "()Ljava/lang/String;", "setDescriptionOfProblems", "(Ljava/lang/String;)V", "downloadUrl", "Landroid/net/Uri;", "fusedLocationClient", "Lcom/google/android/gms/location/FusedLocationProviderClient;", "imageUri", "Landroidx/compose/runtime/MutableState;", "latitude", "", "location", "getLocation", "setLocation", "longitude", "onActivityResult", "", "requestCode", "", "resultCode", "data", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "openImageFiles", "showRequestBottomSheetDialog", "uploadToFirebase", "Companion", "app_debug"})
public final class RequestBottomSheetDialog extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {
    public com.aisc.ngalo.databinding.BikerepairBottomSheetBinding binding;
    public com.aisc.ngalo.BikeRepair bikeRepair;
    public java.lang.String descriptionOfProblems;
    public java.lang.String location;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private final androidx.compose.runtime.MutableState<android.net.Uri> imageUri = null;
    private android.net.Uri downloadUrl;
    public com.aisc.ngalo.databinding.ImagesDialogBinding customDialogBinding;
    private androidx.activity.result.ActivityResultLauncher<android.content.Intent> activityResultLauncher;
    private com.google.android.gms.location.FusedLocationProviderClient fusedLocationClient;
    @org.jetbrains.annotations.NotNull
    public static final com.aisc.ngalo.RequestBottomSheetDialog.Companion Companion = null;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    public static final int CAMERA_REQUEST_CODE = 1;
    public static final int GALLERY_REQUEST_CODE = 2;
    
    public RequestBottomSheetDialog() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.aisc.ngalo.databinding.BikerepairBottomSheetBinding getBinding() {
        return null;
    }
    
    public final void setBinding(@org.jetbrains.annotations.NotNull
    com.aisc.ngalo.databinding.BikerepairBottomSheetBinding p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.aisc.ngalo.BikeRepair getBikeRepair() {
        return null;
    }
    
    public final void setBikeRepair(@org.jetbrains.annotations.NotNull
    com.aisc.ngalo.BikeRepair p0) {
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
    
    @org.jetbrains.annotations.NotNull
    public final com.aisc.ngalo.databinding.ImagesDialogBinding getCustomDialogBinding() {
        return null;
    }
    
    public final void setCustomDialogBinding(@org.jetbrains.annotations.NotNull
    com.aisc.ngalo.databinding.ImagesDialogBinding p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    @android.annotation.SuppressLint(value = {"ClickableViewAccessibility", "CutPasteId"})
    @java.lang.Override
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    private final void uploadToFirebase(android.net.Uri downloadUrl) {
    }
    
    @java.lang.Override
    public void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable
    android.content.Intent data) {
    }
    
    private final void showRequestBottomSheetDialog() {
    }
    
    private final void openImageFiles() {
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/aisc/ngalo/RequestBottomSheetDialog$Companion;", "", "()V", "AUTOCOMPLETE_REQUEST_CODE", "", "CAMERA_REQUEST_CODE", "GALLERY_REQUEST_CODE", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}