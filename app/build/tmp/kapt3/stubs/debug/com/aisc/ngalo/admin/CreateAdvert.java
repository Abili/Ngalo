package com.aisc.ngalo.admin;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0018\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0005H\u0002J\u0010\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u000fH\u0002J\"\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\b\u0010\u0012\u001a\u0004\u0018\u00010\u0005H\u0014J\u0012\u0010\u0019\u001a\u00020\r2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0014J\b\u0010\u001c\u001a\u00020\rH\u0002J\u0010\u0010\u001d\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\tH\u0002R\u0016\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/aisc/ngalo/admin/CreateAdvert;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "activityResultLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "binding", "Lcom/aisc/ngalo/databinding/ActivityUploadsBinding;", "downloadUrl", "Landroid/net/Uri;", "imageUri", "Landroidx/compose/runtime/MutableState;", "Snacker", "", "snackerText", "", "getSelectedImages", "", "data", "notification", "adName", "onActivityResult", "requestCode", "", "resultCode", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "openImageFiles", "uploadToFirebase", "app_debug"})
public final class CreateAdvert extends androidx.appcompat.app.AppCompatActivity {
    private com.aisc.ngalo.databinding.ActivityUploadsBinding binding;
    private androidx.activity.result.ActivityResultLauncher<android.content.Intent> activityResultLauncher;
    private final androidx.compose.runtime.MutableState<android.net.Uri> imageUri = null;
    private android.net.Uri downloadUrl;
    
    public CreateAdvert() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void uploadToFirebase(android.net.Uri downloadUrl) {
    }
    
    private final void notification(java.lang.String adName) {
    }
    
    private final void openImageFiles() {
    }
    
    @java.lang.Override()
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data) {
    }
    
    private final java.util.List<java.lang.String> getSelectedImages(android.content.Intent data) {
        return null;
    }
    
    private final void Snacker(java.lang.String snackerText) {
    }
}