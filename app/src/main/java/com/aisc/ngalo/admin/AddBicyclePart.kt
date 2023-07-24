package com.aisc.ngalo.admin

import BikePart
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import com.aisc.ngalo.R
import com.aisc.ngalo.databinding.ActivityAddBicyclePartBinding
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddBicyclePart : AppCompatActivity() {
    private lateinit var binding: ActivityAddBicyclePartBinding
    private var activityResultLauncher: ActivityResultLauncher<Intent>? = null
    private val imageUri = mutableStateOf<Uri?>(null)
    private lateinit var downloadUrl: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBicyclePartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addimageView.setOnClickListener {
            // Open to upload images
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher!!.launch(intent)
        }
        binding.addpart.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            downloadUrl = imageUri.value!!

            val filePath = downloadUrl

            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("bikepart/" + filePath.lastPathSegment)
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
                    // Save downloadUrl to the database
                    uploadToFirebase(downloadUrl)
                } else {
                    // Handle failure
                    Snackbar.make(binding.root, "Uploading...", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        openImageFiles()
    }

    private fun uploadToFirebase(downloadUrl: Uri) {
        val partName = binding.partname.text.toString()
        val price = binding.partprice.text.toString()
        val description = binding.description.text.toString()

        val bikePart = BikePart(
            FirebaseAuth.getInstance().currentUser!!.uid,
            downloadUrl.toString(),
            partName,
            price,
            description
        )

        if (downloadUrl.toString()
                .isNotEmpty() && partName.isNotEmpty() && price.isNotEmpty() && description.isNotEmpty()
        ) {
            val imagesRef = FirebaseDatabase.getInstance().reference.child("bikepart").push()
                .setValue(bikePart)
            imagesRef.addOnSuccessListener {
                Snacker("Upload Completed!")
                binding.progressBar.visibility = View.GONE
                finish()
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
                    val selectedImage = imageUri.value

                    // Load and display the selected image using Glide
                    if (selectedImage != null) {
                        Glide.with(binding.addimageView)
                            .load(selectedImage)
                            .centerCrop()
                            .placeholder(R.drawable.placeholder_with)
                            .into(binding.addimageView)
                    }
                }
            }
    }

    private fun Snacker(snackerText: String) {
        Snackbar.make(binding.root, snackerText, Snackbar.LENGTH_SHORT).show()
    }
}
