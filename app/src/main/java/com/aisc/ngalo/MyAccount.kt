package com.aisc.ngalo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
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
import com.yalantis.ucrop.UCrop
import java.io.File
import java.util.*

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
                val user = snapshot.getValue(User::class.java)
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

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        binding.editProfileImage.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher!!.launch(intent)
        }

        openImageFiles()


    }

    private fun uploadToFirebase(downloadUrl: Uri) {

//        val bike = User(
//            FirebaseAuth.getInstance().currentUser!!.uid,
//            downloadUrl.toString(),
//            null.toString(),
//            null.toString(),
//            null,
//        )
        if (downloadUrl.toString()
                .isNotEmpty()
        ) {
            val imagesRef =
                FirebaseDatabase.getInstance().reference.child("users")
                    .child(FirebaseAuth.getInstance().currentUser!!.uid)
                    .child("imageUrl")
                    .setValue(downloadUrl.toString())
            imagesRef.addOnSuccessListener {
                binding.profileProgressBbar.visibility = View.GONE
                Snacker("Upload Completed !")

            }.addOnFailureListener {
                Snacker(it.toString())
            }

        } else {
            Snacker("Fields Required")
        }


    }

    private fun openImageFiles() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    imageUri.value = result.data!!.data
//                        val selectedSongs = getSelectedImages(result.data)
                    // Get the song ids

                    val options = UCrop.Options()
                    options.setCompressionQuality(100)
                    options.setToolbarColor(ContextCompat.getColor(this, R.color.ngalo_green))
                    options.setStatusBarColor(ContextCompat.getColor(this, R.color.ngalo_blue))
                    options.setActiveWidgetColor(
                        ContextCompat.getColor(
                            this,
                            R.color.ngalo_green
                        )
                    )

                    UCrop.of(
                        result.data!!.data!!,
                        Uri.fromFile(File(cacheDir, "${UUID.randomUUID()}.jpeg"))
                    )
                        .withOptions(options)
                        .start(this)
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUri = UCrop.getOutput(data!!)
            Glide.with(binding.userProfile)
                .load(resultUri)
                .fitCenter()
                .placeholder(R.drawable.placeholder_with)
                .into(binding.userProfile)

            binding.profileProgressBbar.visibility = View.VISIBLE
            downloadUrl = imageUri.value!!
            //val downloadUrl = it.metadata!!.reference!!.downloadUrl

            val filePath = downloadUrl

            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("images/" + filePath.lastPathSegment)
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
                    uploadToFirebase(downloadUrl)
                } else {
                    // Handle failure
                    Snackbar.make(binding.root, "Uploading...", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun Snacker(snackerText: String) {
        Snackbar.make(binding.root, snackerText, Snackbar.LENGTH_SHORT).show()
    }

}
