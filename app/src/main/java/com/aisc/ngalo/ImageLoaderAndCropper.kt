package com.aisc.ngalo

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import com.yalantis.ucrop.UCrop
import java.io.File
import java.util.*

class ImageLoaderAndCropper(private val activity: Activity) {
    private var imageUri: Uri? = null

    fun openImagePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            if (imageUri != null) {
                // Customize cropping options if needed
                val options = UCrop.Options()
                options.setCompressionQuality(100)
                options.setToolbarColor(ContextCompat.getColor(activity, R.color.ngalo_green))
                options.setStatusBarColor(ContextCompat.getColor(activity, R.color.ngalo_blue))
                options.setActiveWidgetColor(ContextCompat.getColor(activity, R.color.ngalo_green))

                val destinationUri = Uri.fromFile(File(activity.cacheDir, "${UUID.randomUUID()}.jpeg"))
                UCrop.of(imageUri!!, destinationUri)
                    .withOptions(options)
                    .start(activity)
            }
        }
    }

    companion object {
        const val REQUEST_IMAGE_PICK = 1001
    }
}
