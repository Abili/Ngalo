package com.aisc.ngalo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import com.aisc.ngalo.databinding.BikerepairBottomSheetBinding
import com.aisc.ngalo.databinding.ImagesDialogBinding
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
import kotlin.properties.Delegates

class RequestBottomSheetDialog : BottomSheetDialogFragment() {

    lateinit var binding: BikerepairBottomSheetBinding
    lateinit var bikeRepair: BikeRepair
    lateinit var descriptionOfProblems: String
    lateinit var location: String
    private var latitude = 0.0
    private var longitude: Double = 0.0
    private val imageUri = mutableStateOf<Uri?>(null)
    private lateinit var downloadUrl: Uri
    lateinit var customDialogBinding: ImagesDialogBinding
    private var activityResultLauncher: ActivityResultLauncher<Intent>? = null

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("ClickableViewAccessibility", "CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = BikerepairBottomSheetBinding.inflate(inflater, container, false)
        Places.initialize(requireContext(), getString(R.string.Api_key));


        bikeRepair = BikeRepair()

// Initialize the FusedLocationProviderClient.
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())


        val desc = binding.repairDescriptionEditText.text.toString()
        binding.userLocationTextView.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                // Set the fields to specify which types of place data to
                // return after the user has made a selection.
                val fields = listOf(Place.Field.ID, Place.Field.NAME)

                // Start the autocomplete intent.
                val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                    .build(requireContext())
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
                // Get the current location.
            }
            //setCurrentLocationAsDefault()
            false

        }

        binding.requestRepairButton.setOnClickListener {
            //sendImage to storage
            downloadUrl = imageUri.value!!
            val filePath = downloadUrl

            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("repairRequest/" + filePath.lastPathSegment)
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
                    Snackbar.make(binding.root, "Uploading Failed", Snackbar.LENGTH_SHORT).show()
                }
            }

        }
//        customDialogBinding = ImagesDialog()

        //import image
        binding.bikerepairImageView.setOnClickListener {
            //open to upload images
            // Inflate the custom_dialog layout and get the view binding object
            val intent =
                Intent(
                    Intent.ACTION_OPEN_DOCUMENT,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
            activityResultLauncher!!.launch(intent)

        }

        openImageFiles()
        //open gallery
        return binding.root
    }

    private fun uploadToFirebase(downloadUrl: Uri?) {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val repairRequestRef =
            FirebaseDatabase.getInstance().reference.child("RepaireRequests")

        val latLng = Location(location, latitude, longitude)

        val repair = Repair(
            uid,
            downloadUrl.toString(),
            descriptionOfProblems,
            null
        )
        // val uid = FirebaseAuth.getInstance().currentUser!!.uid

        repairRequestRef.child(uid).push().setValue(repair).addOnCompleteListener {
            if (it.isSuccessful) {
                requireActivity().finish()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
//                        latitude = place.latLng?.latitude!!
//                        longitude = place.latLng?.longitude!!

                        val userLocation = Editable.Factory.getInstance().newEditable(place.name)
                        binding.userLocationTextView.text = userLocation
                        location = userLocation.toString()
                        descriptionOfProblems = binding.repairDescriptionEditText.text.toString()



                        Snackbar.make(
                            binding.root,
                            "Place: ${place.name}, ${place.latLng?.latitude}",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Snackbar.make(
                            binding.root, status.statusMessage ?: "", Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val AUTOCOMPLETE_REQUEST_CODE = 1
        const val CAMERA_REQUEST_CODE = 1
        const val GALLERY_REQUEST_CODE = 2
    }

    private fun showRequestBottomSheetDialog() {
        val bottomSheetDialog = ImagesDialog()
        bottomSheetDialog.show(requireFragmentManager(), bottomSheetDialog.tag)
        bottomSheetDialog.openImageFiles()

        customDialogBinding = ImagesDialogBinding.inflate(layoutInflater)
        customDialogBinding.gallery.setOnClickListener {
            val intent =
                Intent(
                    Intent.ACTION_OPEN_DOCUMENT,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
            activityResultLauncher!!.launch(intent)
        }

        customDialogBinding.camera.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
                activityResultLauncher!!.launch(takePictureIntent)
            }
        }
    }

    private fun openImageFiles() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    imageUri.value = result.data!!.data
                    Glide.with(binding.bikerepairImageView)
                        .load(imageUri.value)
                        .centerCrop()
                        .placeholder(R.drawable.placeholder_with)
                        .into(binding.bikerepairImageView)
//                        }
//                        GALLERY_REQUEST_CODE -> {
//                            val imageBitmap = result.data!!.extras?.get("data") as Bitmap
//                            Glide.with(binding.root)
//                                .load(imageBitmap)
//                                .into(binding.bikerepairImageView)
//                        }

                }
            }
    }
}


