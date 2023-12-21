package com.aisc.ngalo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0002J\u0012\u0010\u0011\u001a\u00020\r2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0014J\b\u0010\u0014\u001a\u00020\rH\u0002J\u0018\u0010\u0015\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u000fH\u0002J\u0010\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\u000fH\u0002J\u0010\u0010\u0019\u001a\u00020\r2\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0016\u0010\u001c\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fJ\u0012\u0010\u001d\u001a\u00020\r2\b\u0010\u001e\u001a\u0004\u0018\u00010\u000bH\u0002J\u0010\u0010\u001f\u001a\u00020\r2\u0006\u0010 \u001a\u00020\u000bH\u0002R\u0016\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006!"}, d2 = {"Lcom/aisc/ngalo/MyAccount;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "activityResultLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "binding", "Lcom/aisc/ngalo/databinding/ActivityMyAccountBinding;", "userImage", "Landroid/net/Uri;", "handleUpdate", "", "fieldName", "", "editedValue", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "openImageFiles", "showDialog", "value", "showSnackbar", "message", "updateUI", "user", "Lcom/aisc/ngalo/models/User;", "updateUserInfo", "uploadImageToDatabase", "downloadUrl", "uploadImageToStorage", "imageUri", "app_debug"})
public final class MyAccount extends androidx.appcompat.app.AppCompatActivity {
    private com.aisc.ngalo.databinding.ActivityMyAccountBinding binding;
    private com.google.firebase.auth.FirebaseAuth auth;
    private android.net.Uri userImage;
    private androidx.activity.result.ActivityResultLauncher<android.content.Intent> activityResultLauncher;
    
    public MyAccount() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void updateUI(com.aisc.ngalo.models.User user) {
    }
    
    private final void showDialog(java.lang.String fieldName, java.lang.String value) {
    }
    
    private final void handleUpdate(java.lang.String fieldName, java.lang.String editedValue) {
    }
    
    public final void updateUserInfo(@org.jetbrains.annotations.NotNull
    java.lang.String fieldName, @org.jetbrains.annotations.NotNull
    java.lang.String editedValue) {
    }
    
    private final void openImageFiles() {
    }
    
    private final void uploadImageToStorage(android.net.Uri imageUri) {
    }
    
    private final void uploadImageToDatabase(android.net.Uri downloadUrl) {
    }
    
    private final void showSnackbar(java.lang.String message) {
    }
}