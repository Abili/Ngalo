package com.aisc.ngalo.admin

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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
import com.aisc.ngalo.R
import com.aisc.ngalo.databinding.ActivityUploadsBinding
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

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
        val adName = binding.adName.text.toString()
        val bike = Advert(
            FirebaseAuth.getInstance().currentUser!!.uid,
            downloadUrl.toString(),
            adName
        )

        if (downloadUrl.toString().isNotEmpty() && adName.isNotEmpty()) {
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
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ngalobg)
            .setContentTitle("Ngalo Advert")
            .setContentText(adName)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(adName))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            notificationManager.notify(1, builder.build())
            return
        }

    }

    private fun openImageFiles() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val selectedImageUri = result.data?.data
                    if (selectedImageUri != null) {
                        imageUri.value = selectedImageUri
                        Glide.with(binding.advertIMageView)
                            .load(selectedImageUri)
                            .centerCrop()
                            .placeholder(R.drawable.placeholder_with)
                            .into(binding.advertIMageView)
                    }
                }
            }
    }

    private fun Snacker(snackerText: String) {
        Snackbar.make(binding.root, snackerText, Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        private const val CHANNEL_ID = "ngaloAd"
    }
}
