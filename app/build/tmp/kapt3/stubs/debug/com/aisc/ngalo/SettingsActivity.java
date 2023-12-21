package com.aisc.ngalo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0007B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0014\u00a8\u0006\b"}, d2 = {"Lcom/aisc/ngalo/SettingsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "SettingsFragment", "app_debug"})
public final class SettingsActivity extends androidx.appcompat.app.AppCompatActivity {
    
    public SettingsActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002J\u0012\u0010\n\u001a\u00020\u00072\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0002J\u001c\u0010\r\u001a\u00020\u00072\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\tH\u0016J\b\u0010\u0011\u001a\u00020\u0007H\u0016J\u0010\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u001c\u0010\u0015\u001a\u00020\u00072\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\tH\u0016J\u0012\u0010\u0019\u001a\u00020\u00072\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u0005H\u0002J\b\u0010\u001d\u001a\u00020\u0007H\u0002J\u0010\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u001f\u001a\u00020\tH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/aisc/ngalo/SettingsActivity$SettingsFragment;", "Landroidx/preference/PreferenceFragmentCompat;", "Landroid/content/SharedPreferences$OnSharedPreferenceChangeListener;", "()V", "accountManagementClicked", "", "applyTheme", "", "theme", "", "deleteUserData", "currentUser", "Lcom/google/firebase/auth/FirebaseUser;", "onCreatePreferences", "savedInstanceState", "Landroid/os/Bundle;", "rootKey", "onDestroy", "onDisplayPreferenceDialog", "preference", "Landroidx/preference/Preference;", "onSharedPreferenceChanged", "sharedPreferences", "Landroid/content/SharedPreferences;", "key", "setPreferenceScreen", "preferenceScreen", "Landroidx/preference/PreferenceScreen;", "showDeleteAccountConfirmationDialog", "signOutUser", "storeDeletionRequest", "uid", "app_debug"})
    public static final class SettingsFragment extends androidx.preference.PreferenceFragmentCompat implements android.content.SharedPreferences.OnSharedPreferenceChangeListener {
        private boolean accountManagementClicked = false;
        
        public SettingsFragment() {
            super();
        }
        
        @java.lang.Override
        public void onCreatePreferences(@org.jetbrains.annotations.Nullable
        android.os.Bundle savedInstanceState, @org.jetbrains.annotations.Nullable
        java.lang.String rootKey) {
        }
        
        private final boolean showDeleteAccountConfirmationDialog() {
            return false;
        }
        
        private final void deleteUserData(com.google.firebase.auth.FirebaseUser currentUser) {
        }
        
        private final void signOutUser() {
        }
        
        private final void storeDeletionRequest(java.lang.String uid) {
        }
        
        @java.lang.Override
        public void onSharedPreferenceChanged(@org.jetbrains.annotations.Nullable
        android.content.SharedPreferences sharedPreferences, @org.jetbrains.annotations.Nullable
        java.lang.String key) {
        }
        
        private final void applyTheme(java.lang.String theme) {
        }
        
        @java.lang.Override
        public void onDestroy() {
        }
        
        @java.lang.Override
        public void onDisplayPreferenceDialog(@org.jetbrains.annotations.NotNull
        androidx.preference.Preference preference) {
        }
        
        @java.lang.Override
        public void setPreferenceScreen(@org.jetbrains.annotations.Nullable
        androidx.preference.PreferenceScreen preferenceScreen) {
        }
    }
}