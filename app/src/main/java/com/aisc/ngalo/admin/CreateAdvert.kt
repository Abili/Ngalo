package com.aisc.ngalo.admin

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.aisc.ngalo.R
import com.aisc.ngalo.databinding.ActivityUploadsBinding
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.yalantis.ucrop.UCrop
import java.io.File
import java.util.*

class CreateAdvert : AppCompatActivity() {
    private lateinit var binding: ActivityUploadsBinding
    private var activityResultLauncher: ActivityResultLauncher<Intent>? = null
    private val imageUri = mutableStateOf<Uri?>(null)
    private lateinit var downloadUrl: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.advertIMageView.setOnClickListener {
            //open to upload images
            val intent =
                Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher!!.launch(intent)
        }
        binding.addImageBtn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            downloadUrl = imageUri.value!!
            //val downloadUrl = it.metadata!!.reference!!.downloadUrl

            val filePath = downloadUrl

            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("adverts/" + filePath.lastPathSegment)
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
        val adName = binding.adName.text.toString()
        val bike = Advert(
            FirebaseAuth.getInstance().currentUser!!.uid,
            downloadUrl.toString(),
            adName
        )

        if (downloadUrl.toString()
                .isNotEmpty() && adName.isNotEmpty()
        ) {
            val imagesRef =
                FirebaseDatabase.getInstance().reference.child("adverts")
                    .setValue(bike)
            imagesRef.addOnSuccessListener {
                Snacker("Upload Completed !")
                binding.progressBar.visibility = View.GONE
                notification(adName)
                finish()


            }.addOnFailureListener {
                Snacker(it.toString())
            }
        } else {
            Snacker("Fields Required")
        }


    }

    private fun notification(adName: String) {


        // Step 1: Create a notification channel (if necessary)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "ngaloAd",
                "channel_name",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

// Step 2: Build the notification
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ngalobg)
            .setContentTitle("Ngalo Advert")
            .setContentText(adName)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(adName))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

// Step 3: Post the notification

    }
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)


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
            Glide.with(binding.advertIMageView)
                .load(resultUri)
                .fitCenter()
                .placeholder(R.drawable.placeholder_with)
                .into(binding.advertIMageView)
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

    companion object {
        private const val CHANNEL_ID = "ngaloAd"
    }

}