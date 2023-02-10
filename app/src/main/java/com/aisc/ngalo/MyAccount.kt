package com.aisc.ngalo

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
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
    lateinit var binding: ActivityMyAccountBinding
    private lateinit var auth: FirebaseAuth
    val imageUri = mutableStateOf<Uri?>(null)
    private lateinit var downloadUrl: Uri
    private var activityResultLauncher: ActivityResultLauncher<Intent>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUserUID = FirebaseAuth.getInstance().currentUser!!.uid
        val userRef = FirebaseDatabase
            .getInstance()
            .reference
            .child("users")
            .child(currentUserUID)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    val user = childSnapshot.getValue(User::class.java)
                    binding.username.text = user!!.username
                    binding.userPhone.text = user.phone
                    binding.userEmail.text = user.email
                    if (user.imageUrl.isNotEmpty()) {
                        Glide.with(binding.root)
                            .load(user.imageUrl)
                            .into(binding.userProfile)
                    } else {
                        Glide.with(binding.root)
                            .load(R.drawable.placeholder_with)
                            .into(binding.userProfile)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Snackbar.make(binding.root, error.message, Snackbar.LENGTH_SHORT).show()
            }

        })

        binding.editProfileImage.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher!!.launch(intent)
        }
    }

    private fun openAudioFiles() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    imageUri.value = result.data!!.data
                    Glide.with(binding.editProfileImage)
                        .load(imageUri.value)
                        .centerCrop()
                        .placeholder(R.drawable.placeholder_with)
                        .into(binding.editProfileImage)

                }
            }
    }

    private fun uploadImage(imageUri: Uri) {
        val user = User(
            FirebaseAuth.getInstance().currentUser!!.uid,
            downloadUrl.toString(),
            null,
            null,
            null
        )

        // Add the playlist to the Firebase database
        val usrRef = FirebaseDatabase.getInstance().reference.child("users")
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        user.id = userId

        usrRef.child(userId).push().setValue(user)
            .addOnCompleteListener {
                Snackbar.make(
                    binding.root,
                    "Profile Updated",
                    Snackbar.LENGTH_SHORT
                )
                    .show()

                lifecycleScope.launch {
                    val handler = Handler()
                    handler.postDelayed({
                        binding.progressBar.visibility = View.VISIBLE
                        startActivity(Intent(this@MyAccount, HomeActivity::class.java))
                    }, 5000)
                }

            }.addOnFailureListener {
                Snackbar.make(
                    binding.root,
                    it.message.toString(),
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            }

        val usersRef = FirebaseDatabase.getInstance().reference
        // check if the email already exists in the Firebase Database
        usersRef.orderByChild("users").equalTo(userId).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // email already exists

                    binding.progressBar.visibility = View.VISIBLE
                    downloadUrl = imageUri.value!!
                    //val downloadUrl = it.metadata!!.reference!!.downloadUrl

                    val filePath = downloadUrl

                    val storageRef = FirebaseStorage.getInstance().reference
                    val imageRef = storageRef.child("userimages/" + filePath.lastPathSegment)
                    val uploadTask = imageRef.putFile(filePath)

                    uploadTask.continueWithTask { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                throw it
                            }
                        }
                        imageRef.downloadUrl
                    }.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val downloadUrl = task.result
                            // save downloadUrl to database
                            uploadImage(downloadUrl)
                        } else {
                            // Handle failure
                            Snackbar.make(binding.root, "Uploading...", Snackbar.LENGTH_SHORT)
                                .show()
                        }


                    }


                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })
        return true
    }

}
