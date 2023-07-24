// AdminOUserActivity.kt
package com.aisc.ngalo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aisc.ngalo.admin.AdminPanel
import com.aisc.ngalo.databinding.ActivityAdminOrUserBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class AdminOUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminOrUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminOrUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val curUID = FirebaseAuth.getInstance().uid
        val UID = FirebaseAuth.getInstance().uid
        if (curUID != "sRXHm5IeopQm5r2DCZ08FQVikN23") {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

     fun onAdminCardClick(view: View) {
         startActivity(Intent(this, AdminPanel::class.java))
        Snackbar.make(view.rootView, "Admin Selected", Toast.LENGTH_SHORT).show()
    }

     fun onUsersCardClick(view: View) {
         startActivity(Intent(this, HomeActivity::class.java))
         Snackbar.make(view.rootView, "User Selected", Toast.LENGTH_SHORT).show()
    }

}
