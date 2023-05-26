package com.aisc.ngalo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.aisc.ngalo.databinding.ActivityBikesScreenBinding;
import com.aisc.ngalo.models.Bike;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import kotlinx.coroutines.Dispatchers;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0014J\b\u0010\u000f\u001a\u00020\fH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/aisc/ngalo/BikesScreen;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "bikeViewModel", "Lcom/aisc/ngalo/BikeViewModel;", "bikesAdapter", "Lcom/aisc/ngalo/BikesAdapter;", "binding", "Lcom/aisc/ngalo/databinding/ActivityBikesScreenBinding;", "firebaseRef", "Lcom/google/firebase/database/DatabaseReference;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "app_debug"})
public final class BikesScreen extends androidx.appcompat.app.AppCompatActivity {
    private com.aisc.ngalo.databinding.ActivityBikesScreenBinding binding;
    private com.aisc.ngalo.BikeViewModel bikeViewModel;
    private com.aisc.ngalo.BikesAdapter bikesAdapter;
    private com.google.firebase.database.DatabaseReference firebaseRef;
    
    public BikesScreen() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override
    protected void onDestroy() {
    }
}