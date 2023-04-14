package com.aisc.ngalo;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0007\u0018\u0000 #2\u00020\u0001:\u0001#B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0012\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0014J\b\u0010\u001e\u001a\u00020\u001bH\u0002J\u0010\u0010\u001f\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020\rH\u0002J\u0018\u0010!\u001a\u00020\u00192\u0006\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\"\u001a\u00020\u00192\u0006\u0010\u0016\u001a\u00020\u000fH\u0002R\u0016\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2 = {"Lcom/aisc/ngalo/UserProfile;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "activityResultLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "binding", "Lcom/aisc/ngalo/databinding/ActivityUserProfileBinding;", "database", "Lcom/google/firebase/database/FirebaseDatabase;", "downloadUrl", "Landroid/net/Uri;", "email", "", "firstName", "imageUri", "Landroidx/compose/runtime/MutableState;", "getImageUri", "()Landroidx/compose/runtime/MutableState;", "lastName", "phone", "username", "isValidEmail", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "openAudioFiles", "uploadImage", "downloadUri", "validateInput", "validatePhone", "Companion", "app_debug"})
public final class UserProfile extends androidx.appcompat.app.AppCompatActivity {
    private com.aisc.ngalo.databinding.ActivityUserProfileBinding binding;
    private com.google.firebase.auth.FirebaseAuth auth;
    @org.jetbrains.annotations.NotNull
    private final androidx.compose.runtime.MutableState<android.net.Uri> imageUri = null;
    private android.net.Uri downloadUrl;
    private com.google.firebase.database.FirebaseDatabase database;
    private androidx.activity.result.ActivityResultLauncher<android.content.Intent> activityResultLauncher;
    private java.lang.String username;
    private java.lang.String email;
    private java.lang.String phone;
    private java.lang.String firstName;
    private java.lang.String lastName;
    @org.jetbrains.annotations.NotNull
    public static final com.aisc.ngalo.UserProfile.Companion Companion = null;
    private static final int REQUEST_CODE_SELECT_SONGS = 3;
    private static final java.lang.String TAG = "HomeActivity";
    
    public UserProfile() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.compose.runtime.MutableState<android.net.Uri> getImageUri() {
        return null;
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void openAudioFiles() {
    }
    
    private final void uploadImage(android.net.Uri downloadUri) {
    }
    
    private final boolean isValidEmail(java.lang.String email) {
        return false;
    }
    
    private final boolean validateInput(java.lang.String username, java.lang.String email) {
        return false;
    }
    
    private final boolean validatePhone(java.lang.String phone) {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/aisc/ngalo/UserProfile$Companion;", "", "()V", "REQUEST_CODE_SELECT_SONGS", "", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}