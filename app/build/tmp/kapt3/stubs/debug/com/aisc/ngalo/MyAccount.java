package com.aisc.ngalo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.aisc.ngalo.databinding.ActivityMyAccountBinding;
import com.aisc.ngalo.models.User;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.yalantis.ucrop.UCrop;
import java.io.File;
import java.util.*;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\"\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0005H\u0014J\u0012\u0010\u001d\u001a\u00020\u00152\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0014J\b\u0010 \u001a\u00020\u0015H\u0002J\u0010\u0010!\u001a\u00020\u00152\u0006\u0010\u000e\u001a\u00020\u000fH\u0002R\u0016\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\tX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\""}, d2 = {"Lcom/aisc/ngalo/MyAccount;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "activityResultLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "binding", "Lcom/aisc/ngalo/databinding/ActivityMyAccountBinding;", "getBinding", "()Lcom/aisc/ngalo/databinding/ActivityMyAccountBinding;", "setBinding", "(Lcom/aisc/ngalo/databinding/ActivityMyAccountBinding;)V", "downloadUrl", "Landroid/net/Uri;", "imageUri", "Landroidx/compose/runtime/MutableState;", "getImageUri", "()Landroidx/compose/runtime/MutableState;", "Snacker", "", "snackerText", "", "onActivityResult", "requestCode", "", "resultCode", "data", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "openImageFiles", "uploadToFirebase", "app_debug"})
public final class MyAccount extends androidx.appcompat.app.AppCompatActivity {
    public com.aisc.ngalo.databinding.ActivityMyAccountBinding binding;
    private com.google.firebase.auth.FirebaseAuth auth;
    @org.jetbrains.annotations.NotNull
    private final androidx.compose.runtime.MutableState<android.net.Uri> imageUri = null;
    private android.net.Uri downloadUrl;
    private androidx.activity.result.ActivityResultLauncher<android.content.Intent> activityResultLauncher;
    
    public MyAccount() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.aisc.ngalo.databinding.ActivityMyAccountBinding getBinding() {
        return null;
    }
    
    public final void setBinding(@org.jetbrains.annotations.NotNull
    com.aisc.ngalo.databinding.ActivityMyAccountBinding p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.compose.runtime.MutableState<android.net.Uri> getImageUri() {
        return null;
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void uploadToFirebase(android.net.Uri downloadUrl) {
    }
    
    private final void openImageFiles() {
    }
    
    @java.lang.Override
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable
    android.content.Intent data) {
    }
    
    private final void Snacker(java.lang.String snackerText) {
    }
}