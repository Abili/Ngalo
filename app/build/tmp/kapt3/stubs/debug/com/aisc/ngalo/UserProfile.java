package com.aisc.ngalo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.aisc.ngalo.auth.VerifyPhone;
import com.aisc.ngalo.databinding.ActivityUserProfileBinding;
import com.aisc.ngalo.models.User;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u0010\u0016\u001a\u00020\u0015H\u0002J\b\u0010\u0017\u001a\u00020\u0015H\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\f\u001a\u00020\rH\u0002J\b\u0010\u001a\u001a\u00020\u0015H\u0002J\u0012\u0010\u001b\u001a\u00020\u00152\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0014J\b\u0010\u001e\u001a\u00020\u0015H\u0002J\u0010\u0010\u001f\u001a\u00020\u00152\u0006\u0010 \u001a\u00020\rH\u0002J\u0012\u0010!\u001a\u00020\u00152\b\u0010\"\u001a\u0004\u0018\u00010\u0010H\u0002J \u0010#\u001a\u00020\u00152\u0006\u0010\u0013\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\rH\u0002J\b\u0010$\u001a\u00020\u0015H\u0002R\u0016\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/aisc/ngalo/UserProfile;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "activityResultLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "binding", "Lcom/aisc/ngalo/databinding/ActivityUserProfileBinding;", "database", "Lcom/google/firebase/database/FirebaseDatabase;", "email", "", "firstName", "imageUri", "Landroid/net/Uri;", "lastName", "phone", "username", "handleGoogleSignUp", "", "handlePhoneSignUp", "handleRegistration", "isValidEmail", "", "noImage", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "openImageFiles", "showSnackbar", "s", "uploadImage", "downloadUri", "validateInput", "verifyEmail", "app_debug"})
public final class UserProfile extends androidx.appcompat.app.AppCompatActivity {
    private com.aisc.ngalo.databinding.ActivityUserProfileBinding binding;
    private com.google.firebase.auth.FirebaseAuth auth;
    private android.net.Uri imageUri;
    private com.google.firebase.database.FirebaseDatabase database;
    private androidx.activity.result.ActivityResultLauncher<android.content.Intent> activityResultLauncher;
    private java.lang.String username;
    private java.lang.String email;
    private java.lang.String phone;
    private java.lang.String firstName;
    private java.lang.String lastName;
    
    public UserProfile() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void verifyEmail() {
    }
    
    private final void showSnackbar(java.lang.String s) {
    }
    
    private final void handlePhoneSignUp() {
    }
    
    private final void handleGoogleSignUp() {
    }
    
    private final void openImageFiles() {
    }
    
    private final void uploadImage(android.net.Uri downloadUri) {
    }
    
    private final void noImage() {
    }
    
    private final boolean isValidEmail(java.lang.String email) {
        return false;
    }
    
    private final void validateInput(java.lang.String username, java.lang.String email, java.lang.String phone) {
    }
    
    private final void handleRegistration() {
    }
}