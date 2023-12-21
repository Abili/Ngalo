package com.aisc.ngalo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aisc.ngalo.databinding.ActivityMyAccountBinding
import com.aisc.ngalo.models.User
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch

class MyAccount : AppCompatActivity() {
    private lateinit var binding: ActivityMyAccountBinding
    private lateinit var auth: FirebaseAuth
    private var userImage: Uri? = null
    private var activityResultLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        val currentUserUID = auth.currentUser!!.uid
        val userRef = FirebaseDatabase.getInstance().reference.child("users").child(currentUserUID)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                user?.let { updateUI(it) }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled, if needed
            }
        })

        openImageFiles()

        binding.userProfile.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher?.launch(intent)
        }

        binding.username.setOnClickListener {
            showDialog("username", binding.username.text.toString())
        }

        binding.userPhone.setOnClickListener {
            showDialog("phone", binding.userPhone.text.toString())
        }

        binding.userEmail.setOnClickListener {
            showDialog("email", binding.userEmail.text.toString())
        }
    }

    private fun updateUI(user: User) {
        binding.username.text = user.username
        binding.userPhone.text = user.phone
        binding.userEmail.text = user.email

        if (user.imageUrl!!.isNotEmpty()) {
            Glide.with(binding.root).load(user.imageUrl).into(binding.userProfile)
        } else {
            Glide.with(binding.root).load(R.drawable.placeholder_with).into(binding.userProfile)
        }
    }

    private fun showDialog(fieldName: String, value: String) {
        val dialogView = layoutInflater.inflate(R.layout.update, null)
        val values = dialogView.findViewById<EditText>(R.id.editvalues)
        values.setText(value)

        AlertDialog.Builder(this)
            .setTitle("Update $fieldName")
            .setView(dialogView)
            .setPositiveButton("Yes") { _, _ -> handleUpdate(fieldName, values.text.toString()) }
            .setNegativeButton("No", null)
            .show()
    }

    private fun handleUpdate(fieldName: String, editedValue: String) {
        // Check if the edited value is not empty
        if (editedValue.isNotEmpty()) {
            // Update the corresponding TextView with the edited value
            when (fieldName) {
                "username" -> binding.username.text = editedValue
                "phone" -> binding.userPhone.text = editedValue
                "email" -> binding.userEmail.text = editedValue
            }

            // Update the user's information in Firebase
            updateUserInfo(fieldName, editedValue)
        } else {
            Toast.makeText(this, "Field Cannot Be Empty", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateUserInfo(fieldName: String, editedValue: String) {
        // Implement the logic to update the user's information in Firebase
        // For example, you can use the Firebase Realtime Database API here
        // Update the corresponding field in the user's node
        // This could involve updating the user's information in a "users" node in the database
        // Make sure to use the user's unique identifier (UID) to locate and update the correct user

        val users =
            FirebaseDatabase.getInstance().reference.child("users").child(auth.currentUser!!.uid)

        // Create a Map to store the field name and its corresponding edited value
        val updateMap = HashMap<String, Any>()
        updateMap[fieldName] = editedValue

        // Use updateChildren to update the specific field in the user's node
        users.updateChildren(updateMap).addOnSuccessListener {
            // Handle success, if needed
            Toast.makeText(this, "$fieldName UpDated", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            // Handle failure, if needed
            Toast.makeText(this, "$fieldName UpDate Failed, Try again", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openImageFiles() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    userImage = result.data?.data!!
                    Glide.with(binding.userProfile).load(userImage).centerCrop()
                        .placeholder(R.drawable.placeholder_with).into(binding.userProfile)

                    // Upload the profile picture to Firebase Storage
                    uploadImageToStorage(userImage!!)
                }
            }
    }

    private fun uploadImageToStorage(imageUri: Uri) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("userimages/${imageUri.lastPathSegment}")
        val uploadTask = imageRef.putFile(imageUri)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let { throw it }
            }
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Image uploaded successfully, get the download URL
                val downloadUrl = task.result
                // Save downloadUrl to the database
                uploadImageToDatabase(downloadUrl)
            } else {
                showSnackbar("Failed To Create Profile !!")
            }
        }
    }

    private fun uploadImageToDatabase(downloadUrl: Uri?) {
        val userId = auth.currentUser!!.uid
        val imageUrl = downloadUrl?.toString() ?: ""

        // Create a map to update only the imageUrl field
        val updateMap = mapOf("imageUrl" to imageUrl)

        // Update the user's node with the new imageUrl
        val userRef = FirebaseDatabase.getInstance().reference.child("users").child(userId)

        userRef.updateChildren(updateMap).addOnCompleteListener {
            showSnackbar("Profile Updated")

            lifecycleScope.launch {
                val handler = Handler()
                handler.postDelayed({
                    showSnackbar("Updating Image...")
                }, 0)
            }
        }.addOnFailureListener {
            showSnackbar("Profile Update Failed, Try again")
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}
