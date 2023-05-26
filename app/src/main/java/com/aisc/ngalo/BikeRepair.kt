package com.aisc.ngalo

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.aisc.ngalo.databinding.ActivityBikeRepairBinding
import com.bumptech.glide.Glide
import com.firebase.geofire.GeoFire
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.io.IOException
import java.util.*

class BikeRepair : AppCompatActivity(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null
    var mLastLocation: LocationObject? = null
    var mLocationRequest: LocationRequest? = null
    private val repairRequests = "RepaireRequests"
    private var mFusedLocationClient: FusedLocationProviderClient? = null

    //private var pickupLocation: LatLng? = null
    private var requestBol = false
    private var pickupMarker: Marker? = null
    private var mapFragment: SupportMapFragment? = null
    private val destination: String? = null
    private val requestService: String? = null
    private var pickupLocation: LocationObject? = null
    private var currentLocation: LocationObject? = null
    private var destinationLocation: LocationObject? = null

    //private var destinationLatLng: LatLng? = null
    private var mRepairRequestsRef: DatabaseReference? = null
    private var mPatientRideId: String? = null
    private lateinit var binding: ActivityBikeRepairBinding
    private val imageUri = mutableStateOf<Uri?>(null)
    private lateinit var downloadUrl: Uri
    private var activityResultLauncher: ActivityResultLauncher<Intent>? = null
    lateinit var descriptionOfProblems: String
    lateinit var location: String
    private var latitude = 0.0
    private var longitude: Double = 0.0
    private var selectedLocation: LatLng? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBikeRepairBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        //destinationLatLng = LatLng(0.0, 0.0)
        mRepairRequestsRef = FirebaseDatabase.getInstance().reference.child(repairRequests)
        Places.initialize(this@BikeRepair, getString(R.string.Api_key));


        //user request sending location
        binding.orderdocBtn.setOnClickListener {
            var name: String = ""
            val userId = FirebaseAuth.getInstance().currentUser!!.uid
            val geoFire = GeoFire(mRepairRequestsRef)
            mLastLocation = currentLocation
            mMap!!.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        currentLocation!!.coordinates!!.latitude,
                        currentLocation!!.coordinates!!.longitude
                    ), 17f
                )
            )
//            geoFire.setLocation(
//                userId,
//                GeoLocation(mLastLocation!!.latitude, mLastLocation!!.longitude)
//            )
            pickupLocation =
                LocationObject(
                    LatLng(
                        mLastLocation!!.coordinates!!.latitude,
                        mLastLocation!!.coordinates!!.longitude
                    ), ""
                )
            if (pickupMarker != null) {
                pickupMarker!!.remove()
            }
            pickupMarker = mMap!!.addMarker(
                MarkerOptions().position(pickupLocation!!.coordinates!!).title("Pickup Here").icon(
                    BitmapDescriptorFactory.fromBitmap(
                        generateBitmap(
                            this@BikeRepair,
                            pickupLocation!!.name,
                            null
                        )!!
                    )
                )
            )

            binding.orderdocBtn.visibility = View.GONE
            // Create TranslateAnimation object
            val animation = TranslateAnimation(
                0f, 0f,
                binding.bottomSheet.height.toFloat(), 0f
            )

            // Set the duration of the animation
            animation.duration = 500

            // Create AlphaAnimation object
            val alphaAnimation = AlphaAnimation(0f, 1f)

            // Set the duration of the alpha animation
            alphaAnimation.duration = 500

            // Create AnimationSet object
            val animationSet = AnimationSet(true)

            // Add the two animations to the animation set
            animationSet.addAnimation(animation)
            animationSet.addAnimation(alphaAnimation)

            // Set the view to "visible" and start the animation
            binding.bottomSheet.visibility = View.VISIBLE
            binding.bottomSheet.startAnimation(animationSet)

            //setUsers currentLocation
            setCurrentLocation()
        }

        binding.userLocationTextView.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                // Set the fields to specify which types of place data to
                // return after the user has made a selection.
                val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)

                // Start the autocomplete intent.
                val intent =
                    Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                        .build(this)
                startActivityForResult(
                    intent,
                    AUTOCOMPLETE_REQUEST_CODE
                )
                // Get the current location.
            }
            //setCurrentLocationAsDefault()
            // Assuming that you have a GoogleMap object named "map" and a Marker object named "marker"
            val placesClient = Places.createClient(this@BikeRepair)
            val latLng = pickupMarker!!.position

            // Create a request object for the place details of the location at the marker position
            val request = FetchPlaceRequest.newInstance(
                pickupMarker!!.tag.toString(), // Assuming that you have stored the place ID as the marker tag
                listOf(Place.Field.NAME)
            )

            // Call the Places API to get the place details
            placesClient.fetchPlace(request).addOnSuccessListener { response ->
                val place = response.place
                val name = place.name!! // Get the name of the place
                // Do something with the name
                val latlong = place.latLng
                Toast.makeText(this, name + latLng, Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                // Handle the error
            }

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
                    Snackbar.make(binding.root, "Uploading Failed", Snackbar.LENGTH_SHORT)
                        .show()
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

//            showRequestBottomSheetDialog(
//                userId,
//                geoFire,
//                mLastLocation!!.latitude,
//                mLastLocation!!.longitude,
//                name
//            )

//        binding.setCurrentLocationTV.setOnClickListener {
//            setCurrentLocation()
//        }
        openImageFiles()


    }


    var zoomUpdated = false
    var mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                if (application != null) {
                    currentLocation =
                        LocationObject(LatLng(location.latitude, location.longitude), "")
                    val latLng = LatLng(location.latitude, location.longitude)
                    if (!zoomUpdated) {
                        val zoomLevel = 17.0f //This goes up to 21
                        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                        mMap!!.animateCamera(CameraUpdateFactory.zoomTo(zoomLevel))
                        zoomUpdated = true
                    }
                }
            }
        }
    }

    private fun setCurrentLocation() {
        binding.userLocationTextView.text = (getString(R.string.set_current_location))
        //binding.setCurrentLocationTV.setImageDrawable(resources.getDrawable(R.drawable.ic_location_on_primary_24dp))
        pickupLocation = currentLocation
        if (pickupLocation == null) {
            return
        }
        fetchLocationName()

        mMap!!.clear()
        pickupMarker = mMap!!.addMarker(
            MarkerOptions().position(pickupLocation!!.coordinates!!).title("Pickup").icon(
                BitmapDescriptorFactory.fromBitmap(
                    generateBitmap(
                        this@BikeRepair,
                        pickupLocation!!.name,
                        null
                    )!!
                )
            )
        )

        binding.requestRepairButton.text = resources.getString(R.string.request_repair)
    }


    private fun generateBitmap(context: Context, location: String?, duration: String?): Bitmap? {
        var bitmap: Bitmap? = null
        val mInflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = RelativeLayout(context)
        try {
            mInflater.inflate(R.layout.item_marker, view, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val locationTextView = view.findViewById<View>(R.id.location) as TextView
        val durationTextView = view.findViewById<View>(R.id.duration) as TextView
        locationTextView.text = location
        if (duration != null) {
            durationTextView.text = duration
        } else {
            durationTextView.visibility = View.GONE
        }
        view.layoutParams = ViewGroup.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        view.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        bitmap =
            Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val c = Canvas(bitmap)
        view.draw(c)
        return bitmap
    }


    private fun uploadToFirebase(downloadUrl: Uri?) {
        val descriptionOfProblems = binding.repairDescriptionEditText.text.toString()
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val repairRequestRef =
            FirebaseDatabase.getInstance().reference.child("RepaireRequests")
        val userRequestRef =
            FirebaseDatabase.getInstance().reference.child("users").child(uid)
                .child("UsersOrders")

        val latLng = LocationObject(
            LatLng(
                mLastLocation!!.coordinates!!.latitude,
                mLastLocation!!.coordinates!!.longitude
            ), binding.userLocationTextView.text.toString()
        )

        val repair = Repair(
            uid,
            downloadUrl.toString(),
            descriptionOfProblems,
            latLng,
            System.currentTimeMillis().toString(),
            "Bike Repair"
        )
        // val uid = FirebaseAuth.getInstance().currentUser!!.uid

        repairRequestRef.push().setValue(repair).addOnCompleteListener {
            if (it.isSuccessful) {
                finish()
                binding.repairDescriptionEditText.text.clear()
                userRequestRef.push().setValue(repair)

            }

        }
    }


    private fun openImageFiles() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
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

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = 1000
        mLocationRequest!!.fastestInterval = 1000
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                mFusedLocationClient!!.requestLocationUpdates(
                    mLocationRequest!!,
                    mLocationCallback,
                    Looper.myLooper()
                )
                mMap!!.isMyLocationEnabled = true
            } else {
                checkLocationPermission()
            }
        }
    }

//    private var mLocationCallback: LocationCallback = object : LocationCallback() {
//        override fun onLocationResult(locationResult: LocationResult) {
//            for (location in locationResult.locations) {
//                if (applicationContext != null) {
//                    pickupLocation = currentLocation
//                    val latLng = LatLng(location.latitude, location.longitude)
//                    mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
//                    mMap!!.animateCamera(CameraUpdateFactory.zoomTo(15f))
//                    //if (!getDriversAroundStarted) getDriversAround()
//                }
//            }
//        }
//    }

    /*-------------------------------------------- onRequestPermissionsResult -----
    |  Function onRequestPermissionsResult
    |
    |  Purpose:  Get permissions for our app if they didn't previously exist.
    |
    |  Note:
    |	requestCode: the nubmer assigned to the request that we've made. Each
    |                request has it's own unique request code.
    |
    *-------------------------------------------------------------------*/
    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                AlertDialog.Builder(this)
                    .setTitle("give permission")
                    .setMessage("give permission message")
                    .setPositiveButton(
                        "OK"
                    ) { dialogInterface, i ->
                        ActivityCompat.requestPermissions(
                            this@BikeRepair,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            1
                        )
                    }
                    .create()
                    .show()
            } else {
                ActivityCompat.requestPermissions(
                    this@BikeRepair,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        mFusedLocationClient!!.requestLocationUpdates(
                            mLocationRequest!!,
                            mLocationCallback,
                            Looper.myLooper()
                        )
                        mMap!!.isMyLocationEnabled = true
                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Please provide the permission",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val mLocation: LocationObject

                        if (currentLocation == null) {
                            Snackbar.make(
                                binding.root,
                                "First Activate GPS",
                                Snackbar.LENGTH_LONG
                            ).show()
                            return
                        }
                        val place = Autocomplete.getPlaceFromIntent(data)

                        mLocation = LocationObject(place.latLng, place.name!!)


                        currentLocation = LocationObject(
                            LatLng(
                                currentLocation!!.coordinates!!.latitude,
                                currentLocation!!.coordinates!!.longitude
                            ), ""
                        )


                        if (requestCode == 1) {
                            mMap!!.clear()
                            if (pickupLocation != null) {
                                pickupMarker = mMap!!.addMarker(
                                    MarkerOptions().position(pickupLocation!!.coordinates!!).icon(
                                        BitmapDescriptorFactory.fromBitmap(
                                            generateBitmap(
                                                this@BikeRepair,
                                                pickupLocation!!.name,
                                                null
                                            )!!
                                        )
                                    )
                                )
                            }
                        } else if (requestCode == 2) {
                            mMap!!.clear()
                            pickupLocation = mLocation
                            pickupMarker = mMap!!.addMarker(
                                MarkerOptions().position(pickupLocation!!.coordinates!!).icon(
                                    BitmapDescriptorFactory.fromBitmap(
                                        generateBitmap(
                                            this@BikeRepair,
                                            pickupLocation!!.name,
                                            null
                                        )!!
                                    )
                                )
                            )
                        }
                        binding.userLocationTextView.text = pickupLocation!!.name
                        binding.requestRepairButton.text = getString(R.string.request_repair)
                    }
                }

                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Snackbar.make(
                            binding.root,
                            status.statusMessage ?: "Error locating User",
                            Snackbar.LENGTH_SHORT
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

    private fun fetchLocationName() {
        if (pickupLocation == null) {
            return
        }
        try {
            val geo = Geocoder(this.applicationContext, Locale.getDefault())
            val addresses = geo.getFromLocation(
                currentLocation!!.coordinates!!.latitude,
                currentLocation!!.coordinates!!.longitude,
                1
            )
            if (addresses!!.isEmpty()) {
                binding.userLocationTextView.setText(R.string.waiting_for_location)
            } else {
                addresses.size
                if (addresses[0].thoroughfare == null) {
                    pickupLocation!!.name = addresses[0].locality
                } else if (addresses[0].locality == null) {
                    pickupLocation!!.name = "Unknown Location"
                } else {
                    pickupLocation!!.name = addresses[0].locality + ", " + addresses[0].thoroughfare
                }
                binding.userLocationTextView.text = pickupLocation!!.name
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val AUTOCOMPLETE_REQUEST_CODE = 1
        const val CAMERA_REQUEST_CODE = 1
        const val GALLERY_REQUEST_CODE = 2
    }

}