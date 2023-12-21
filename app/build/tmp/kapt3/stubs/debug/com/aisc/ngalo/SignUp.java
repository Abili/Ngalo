package com.aisc.ngalo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.aisc.ngalo.auth.PhoneAuthHelper;
import com.aisc.ngalo.databinding.ActivitySignUpBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.hbb20.CountryCodePicker;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u0000 \"2\u00020\u0001:\u0001\"B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0002J\b\u0010\u000f\u001a\u00020\u000eH\u0002J\u0012\u0010\u0010\u001a\u00020\f2\b\u0010\u0011\u001a\u0004\u0018\u00010\tH\u0002J\u0012\u0010\u0012\u001a\u00020\f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0014J\u0010\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u000eH\u0002J\u0010\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\b\u0010\u001a\u001a\u00020\fH\u0002J\u001c\u0010\u001b\u001a\u00020\f2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0003J\u0010\u0010 \u001a\u00020\f2\u0006\u0010!\u001a\u00020\u001fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0010\u0012\f\u0012\n \n*\u0004\u0018\u00010\t0\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"Lcom/aisc/ngalo/SignUp;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "binding", "Lcom/aisc/ngalo/databinding/ActivitySignUpBinding;", "signInLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "kotlin.jvm.PlatformType", "firebaseAuthWithGoogle", "", "idToken", "", "getSignInMethod", "handleSignInResult", "data", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "signIn", "method", "signInWithGoogle", "context", "Landroid/content/Context;", "signOut", "updateUI", "currentUser", "Lcom/google/firebase/auth/FirebaseUser;", "downloadUrl", "Landroid/net/Uri;", "uploadImageToStorage", "imageUri", "Companion", "app_debug"})
public final class SignUp extends androidx.appcompat.app.AppCompatActivity {
    private com.aisc.ngalo.databinding.ActivitySignUpBinding binding;
    private com.google.firebase.auth.FirebaseAuth auth;
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> signInLauncher = null;
    @org.jetbrains.annotations.NotNull
    public static final com.aisc.ngalo.SignUp.Companion Companion = null;
    private static final java.lang.String TAG = "MainActivity";
    
    public SignUp() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void signInWithGoogle(android.content.Context context) {
    }
    
    private final void signIn(java.lang.String method) {
    }
    
    private final void handleSignInResult(android.content.Intent data) {
    }
    
    private final void firebaseAuthWithGoogle(java.lang.String idToken) {
    }
    
    private final void uploadImageToStorage(android.net.Uri imageUri) {
    }
    
    @android.annotation.SuppressLint(value = {"StringFormatInvalid"})
    private final void updateUI(com.google.firebase.auth.FirebaseUser currentUser, android.net.Uri downloadUrl) {
    }
    
    private final void signOut() {
    }
    
    private final java.lang.String getSignInMethod() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/aisc/ngalo/SignUp$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}