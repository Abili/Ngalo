package com.aisc.ngalo

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.compose.runtime.mutableStateOf
import com.aisc.ngalo.databinding.BikerepairBottomSheetBinding
import com.aisc.ngalo.databinding.FragmentRequestBottomSheetDialogBinding
import com.aisc.ngalo.databinding.ImagesDialogBinding
import com.aisc.ngalo.databinding.LoadImageDialogBinding
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class ImagesDialog : BottomSheetDialogFragment() {

    lateinit var binding: ImagesDialogBinding
    lateinit var descriptionOfProblems: String
    lateinit var location: String
    private val imageUri = mutableStateOf<Uri?>(null)
    private lateinit var downloadUrl: Uri
    lateinit var customDialogBinding: BikerepairBottomSheetBinding
    private var activityResultLauncher: ActivityResultLauncher<Intent>? = null

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("ClickableViewAccessibility", "CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = ImagesDialogBinding.inflate(inflater, container, false)
        customDialogBinding = BikerepairBottomSheetBinding.inflate(layoutInflater)

        binding.camera.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
                activityResultLauncher!!.launch(takePictureIntent)
            }
        }

        //import image
        binding.gallery.setOnClickListener {
            val intent =
                Intent(
                    Intent.ACTION_OPEN_DOCUMENT,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
            activityResultLauncher!!.launch(intent)
        }
        //open gallery
        openImageFiles()
        return binding.root
    }

      fun openImageFiles() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    when (result.resultCode) {
                        RequestBottomSheetDialog.CAMERA_REQUEST_CODE -> {
                            imageUri.value = result.data!!.data
                            Glide.with(customDialogBinding.root)
                                .load(result.data!!.data)
                                .centerCrop()
                                .placeholder(R.drawable.placeholder_with)
                                .into(customDialogBinding.bikerepairImageView)

                        }
                        RequestBottomSheetDialog.GALLERY_REQUEST_CODE -> {
                            val uri = result.data!!.data
                            Glide.with(customDialogBinding.root)
                                .load(uri)
                                .into(customDialogBinding.bikerepairImageView)
                        }

                    }
                }
            }
    }


    companion object {
        private const val CAMERA_REQUEST_CODE = 1
        private const val GALLERY_REQUEST_CODE = 2
    }


}
