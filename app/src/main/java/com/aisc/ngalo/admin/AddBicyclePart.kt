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
import androidx.core.content.ContextCompat
import com.aisc.ngalo.R
import com.aisc.ngalo.databinding.ActivityAddBicyclePartBinding
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.yalantis.ucrop.UCrop
import java.io.File
import java.util.*

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
            //open to upload images
            val intent =
                Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher!!.launch(intent)
        }
        binding.addpart.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            downloadUrl = imageUri.value!!
            //val downloadUrl = it.metadata!!.reference!!.downloadUrl

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
                    // save downloadUrl to database
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
        val bikesName = binding.partname.text.toString()
        val price = binding.partprice.text.toString()
        val description = binding.description.text.toString()

        val bike = BikePart(
            FirebaseAuth.getInstance().currentUser!!.uid,
            downloadUrl.toString(),
            bikesName,
            price,
            description,
        )

        if (downloadUrl.toString()
                .isNotEmpty() && bikesName.isNotEmpty() && price.isNotEmpty() && description.isNotEmpty()
        ) {
            val imagesRef =
                FirebaseDatabase.getInstance().reference.child("bikepart").push()
                    .setValue(bike)
            imagesRef.addOnSuccessListener {
                Snacker("Upload Completed !")
                binding.progressBar.visibility = View.GONE
                finish()


            }.addOnFailureListener {
                Snacker(it.toString())

            }.addOnFailureListener {
                Snacker(it.toString())
            }
        } else {
            Snacker("Fields Required")
        }


    }

//    private fun openImageFiles() {
//        activityResultLauncher =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//                if (result.resultCode == RESULT_OK) {
//                    imageUri.value = result.data!!.data
//                    val selectedSongs = getSelectedImages(result.data)
//                    // Get the song ids
//                    Glide.with(binding.addimageView)
//                        .load(result.data!!.data)
//                        .centerCrop()
//                        .placeholder(R.drawable.placeholder_with)
//                        .into(binding.addimageView)
//
//                }
//            }
//    }


    private fun openImageFiles() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    imageUri.value = result.data!!.data
                    val selectedSongs = getSelectedImages(result.data)
                    // Get the song ids

                    val options = UCrop.Options()
                    options.setCompressionQuality(100)
                    options.setToolbarColor(ContextCompat.getColor(this, R.color.ngalo_green))
                    options.setStatusBarColor(ContextCompat.getColor(this, R.color.ngalo_blue))
                    options.setActiveWidgetColor(ContextCompat.getColor(this, R.color.ngalo_green))

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
            Glide.with(binding.addimageView)
                .load(resultUri)
                .fitCenter()
                .placeholder(R.drawable.placeholder_with)
                .into(binding.addimageView)
        }
    }


    private fun getSelectedImages(data: Intent?): List<String> {
        val selectedSongs = mutableListOf<String>()
        // Check if the Intent contains a clip data
        if (data?.clipData != null) {
            // Iterate over the clip data items
            for (i in 0 until data.clipData!!.itemCount) {
                val item = data.clipData!!.getItemAt(i)
                // Get the URI of the audio file
                val uri = item.uri
                selectedSongs.add(uri.toString())
            }
        } else if (data?.data != null) {
            // Get the URI of the audio file
            val uri = data.data!!.lastPathSegment
            selectedSongs.add(uri.toString())
        }
        return selectedSongs
    }

    private fun Snacker(snackerText: String) {
        Snackbar.make(binding.root, snackerText, Snackbar.LENGTH_SHORT).show()
    }

}