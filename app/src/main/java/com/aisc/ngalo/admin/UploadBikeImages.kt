package com.aisc.ngalo.admin

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
import com.aisc.ngalo.R
import com.aisc.ngalo.databinding.ActivityUploadImagesBinding
import com.aisc.ngalo.models.Bike
import com.aisc.ngalo.models.Category
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class UploadBikeImages : AppCompatActivity() {
    private lateinit var binding: ActivityUploadImagesBinding
    private var activityResultLauncher: ActivityResultLauncher<Intent>? = null
    private val imageUri = mutableStateOf<Uri?>(null)
    private lateinit var downloadUrl: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addimageView.setOnClickListener {
            // Open to upload images
            val intent =
                Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher!!.launch(intent)
        }
        binding.addImageBtn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            downloadUrl = imageUri.value!!

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
        val bikesName = binding.edbikeimages.text.toString()
        val price = binding.enterPrice.text.toString()
        val description = binding.description.text.toString()
        val category = if (binding.radioBuy.isChecked) {
            Category.BUY
        } else {
            Category.HIRE
        }
        val bike = Bike(
            FirebaseAuth.getInstance().currentUser!!.uid,
            downloadUrl.toString(),
            bikesName,
            price,
            description,
            category
        )

        if (downloadUrl.toString()
                .isNotEmpty() && bikesName.isNotEmpty() && price.isNotEmpty() && description.isNotEmpty()
        ) {
            if (binding.radioBuy.isChecked) {
                val imagesRef =
                    FirebaseDatabase.getInstance().reference.child("bikes").child("buy").push()
                        .setValue(bike)
                imagesRef.addOnSuccessListener {
                    Snacker("Upload Completed !")
                    binding.progressBar.visibility = View.GONE
                    finish()
                }.addOnFailureListener {
                    Snacker(it.toString())
                }
            } else if (binding.radioHire.isChecked) {
                val imagesRef =
                    FirebaseDatabase.getInstance().reference.child("bikes").child("hire").push()
                        .setValue(bike)
                imagesRef.addOnSuccessListener {
                    Snacker("Upload Completed !")
                    binding.progressBar.visibility = View.GONE
                    finish()
                }.addOnFailureListener {
                    Snacker(it.toString())
                }
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
