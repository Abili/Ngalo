package com.aisc.ngalo.admin

import android.Manifest
import android.app.AlertDialog
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.aisc.ngalo.LocationObject
import com.aisc.ngalo.R
import com.aisc.ngalo.databinding.ActivityAdminHomePageBinding
import com.aisc.ngalo.helpers.fetchRoute
import com.akexorcist.googledirection.model.Route
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.libraries.places.api.Places
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminHomePage : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var myLocation: LocationObject? = null
    var currentLocation: LocationObject? = null
    var mLocationRequest: LocationRequest? = null
    private val repairRequests = "RepaireRequests"
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var pickupLocation: LatLng? = null
    private var originLatitude: Double? = null
    private var originLongitude: Double? = null
    private var latitude: Double? = null
    private var longitude: Double? = null
    private lateinit var binding: ActivityAdminHomePageBinding
    var started = false
    var zoomUpdated = false
    private var customerMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminHomePageBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val id = intent.getStringExtra("id")

        // Fetching API_KEY which we wrapped
        val ai: ApplicationInfo = applicationContext.packageManager
            .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
        val value = resources.getString(R.string.Api_key)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Initializing the Places API with the help of our API_KEY
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, value)
        }

        // Map Fragment
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapAdmin) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.acceptRequest.setOnClickListener {
            myLocation = currentLocation
            val uid = FirebaseAuth.getInstance().uid
            val requestsRef =
                FirebaseDatabase.getInstance().reference.child("RepaireRequests")

            requestsRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (childSnapshot in snapshot.children) {
                        val userID = childSnapshot.child("id").getValue(String::class.java)
                        if (id == userID) {
                            val coordinates = childSnapshot.child("latLng").child("coordinates")
                            val latitude = coordinates.child("latitude").value as Double
                            val longitude = coordinates.child("longitude").value as Double

                            val placeName = childSnapshot.child("latLng").child("name").value

                            // Use the latitude, longitude, and placeName values as needed
//                        destinationLatitude!!.coordinates = -0.3449805 as LatLng
//                        destinationLongitude!!.coordinates = longitude

                            customerMarker = mMap.addMarker(
                                MarkerOptions().position(
                                    LatLng(
                                        latitude,
                                        longitude
                                    )
                                ).title(placeName.toString())
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.client_marker))
                            )

                            mapFragment.getMapAsync {
                                val currentLocation = LocationObject(
                                    LatLng(
                                        0.3765387, 32.6068885
                                    ), "Ngalo Kulambiro-Ring road"
                                )
                                mMap = it
                                customerMarker = mMap.addMarker(
                                    MarkerOptions().position(
                                        LatLng(
                                            currentLocation.coordinates!!.latitude,
                                            currentLocation.coordinates!!.longitude
                                        )
                                    ).title("Ngalo Kulambiro-Ring road")
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.admin_marker))
                                )
                                val destinationLocation = LocationObject(
                                    LatLng(
                                        latitude,
                                        longitude
                                    ), placeName.toString()
                                )
                                //mMap.addMarker(MarkerOptions().position(destinationLocation.coordinates!!))
                                fetchRoute(
                                    LatLng(
                                        currentLocation.coordinates!!.latitude,
                                        currentLocation.coordinates!!.longitude
                                    ),
                                    LatLng(
                                        destinationLocation.coordinates!!.latitude,
                                        destinationLocation.coordinates!!.longitude
                                    ),
                                    value,
                                ) { directionsResult ->
                                    val routes = directionsResult.routes

                                    // Loop through each route and draw directionsResult on the map
                                    for (i in routes.indices) {
                                        val route = routes[i]

                                        // Polyline options for the route
                                        val polylineOptions = PolylineOptions()
                                            .color(Color.BLUE)
                                            .width(15f)

                                        // Add each step of the route to the polyline options
                                        for (j in route.legs.indices) {
                                            val leg = route.legs[j]
                                            for (k in leg.steps.indices) {
                                                val step = leg.steps[k]
                                                val points = step.polyline.decodePath()
                                                for (point in points) {
                                                    polylineOptions.add(
                                                        LatLng(
                                                            point.lat,
                                                            point.lng
                                                        )
                                                    )
                                                }
                                            }
                                        }

                                        // Draw the polyline on the map
                                        mMap.addPolyline(polylineOptions)
                                        binding.bottomSheet.visibility = View.VISIBLE

                                        val distanceInMeters =
                                            route?.legs?.sumBy { it.distance.inMeters.toInt() } ?: 0
                                        val duration =
                                            route?.legs?.sumBy { it.duration.inSeconds.toInt() }
                                                ?: 0

                                        val distanceInKilometers = distanceInMeters / 1000
                                        // Convert duration to hours and minutes
                                        val hours = duration / 3600
                                        val minutes = (duration % 3600) / 60

                                        // Update the UI with the calculated distance and duration
                                        // Format the time string
                                        val time = String.format("%02d:%02d", hours, minutes)

                                        binding.time.text = time + "Hrs"
                                        binding.distance.text = "$distanceInKilometers KM"
                                    }
                                }
                                mMap.animateCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                        currentLocation.coordinates!!,
                                        15F
                                    )
                                )
                            }

                        } else {
                            Toast.makeText(
                                this@AdminHomePage,
                                "User Not Found",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(
                        "AdminHomePage",
                        "Failed to retrieve repair requests from Firebase",
                        error.toException()
                    )
                }
            })
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        googleMap.setMapStyle(
            MapStyleOptions(
                resources
                    .getString(R.string.style_json)
            )
        )
        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = 1000
        mLocationRequest!!.fastestInterval = 1000
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mFusedLocationClient!!.requestLocationUpdates(
                mLocationRequest!!,
                mLocationCallback,
                Looper.myLooper()
            )
            mMap.isMyLocationEnabled = true
        } else {
            checkLocationPermission()
        }
    }

    private var mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                if (application != null) {
                    currentLocation =
                        LocationObject(LatLng(location.latitude, location.longitude), "")
                    val latLng = LatLng(location.latitude, location.longitude)
                    if (!zoomUpdated) {
                        val zoomLevel = 17.0f //This goes up to 21
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(zoomLevel))
                        zoomUpdated = true
                    }
                }
            }
        }
    }


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
                            this,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            1
                        )
                    }
                    .create()
                    .show()
            } else {
                ActivityCompat.requestPermissions(
                    this,
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
                        mMap.isMyLocationEnabled = true
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


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }


    //private val polylines: MutableList<Polyline>? = null
    private val polylines: MutableList<Polyline> = ArrayList()

    /**
     * Remove route polylines from the map
     */
    private fun erasePolylines() {
        for (line in polylines) {
            line.remove()
        }
        polylines.clear()
    }


    private fun setCameraWithCoordinationBounds(route: Route) {
        val southwest = route.bound.southwestCoordination.coordination
        val northeast = route.bound.northeastCoordination.coordination
        val bounds = LatLngBounds(southwest, northeast)
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
    }

    override fun onStop() {
        super.onStop()
        erasePolylines()
    }
}
