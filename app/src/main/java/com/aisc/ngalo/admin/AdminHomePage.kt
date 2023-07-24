package com.aisc.ngalo.admin

import android.Manifest
import android.app.AlertDialog
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
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
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Dot
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.RoundCap
import com.google.android.libraries.places.api.Places
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.maps.model.DirectionsRoute

class AdminHomePage : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var myLocation: LocationObject? = null
    var currentLocation: LocationObject? = null
    private var mLocationRequest: LocationRequest? = null
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
    private var adminMarker: Marker? = null
    private var polyline: Polyline? = null
    private var reachedUserLocation = false
    private var fixedMarker: Marker? = null
    var destinationLocation: LocationObject? = null
    private var isDrivingMode = false // Flag to track if driving mode is active
    private var previousBearing: Float = 0f
    private var polylineStartLocation: LocationObject? = null
    var value: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminHomePageBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val id = intent.getStringExtra("id")

        // Fetching API_KEY which we wrapped
        val ai: ApplicationInfo = applicationContext.packageManager.getApplicationInfo(
            applicationContext.packageName, PackageManager.GET_META_DATA
        )
        value = resources.getString(R.string.Api_key)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Initializing the Places API with the help of our API_KEY
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, value)
        }

        // Map Fragment
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapAdmin) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.locateUser.setOnClickListener {
            myLocation = currentLocation
            val uid = FirebaseAuth.getInstance().uid
            val requestsRef = FirebaseDatabase.getInstance().reference.child("RepaireRequests")

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
                            destinationLocation = LocationObject(
                                LatLng(
                                    latitude, longitude
                                ), placeName.toString()
                            )

                            customerMarker = mMap.addMarker(
                                MarkerOptions().position(
                                    LatLng(
                                        latitude, longitude
                                    )
                                ).title(placeName.toString())
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.client_marker))
                            )

                            mapFragment.getMapAsync { it ->
//                                val currentLocation = LocationObject(
//                                    LatLng(
//                                        0.3765387, 32.6068885
//                                    ), "Ngalo Kulambiro-Ring road"
//                                )
                                mMap = it
                                if (adminMarker == null) {
                                    adminMarker = mMap.addMarker(
                                        MarkerOptions().position(
                                            LatLng(
                                                currentLocation!!.coordinates!!.latitude,
                                                currentLocation!!.coordinates!!.longitude
                                            )
                                        ).title("Ngalo")
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.admin_marker))
                                    )
                                } else {
                                    adminMarker!!.position = LatLng(
                                        currentLocation!!.coordinates!!.latitude,
                                        currentLocation!!.coordinates!!.longitude
                                    )
                                }

                                //mMap.addMarker(MarkerOptions().position(destinationLocation.coordinates!!))
                                updateRoute(currentLocation!!, destinationLocation!!, value)
                                updateAdminRoute(currentLocation!!, destinationLocation!!, value)
                                // Check if the admin has reached the user's location
                                // Check if the admin has reached the user's location
                                val distanceToUser = calculateDistance(
                                    currentLocation!!.coordinates!!, LatLng(latitude, longitude)
                                )

                                val distanceThreshold =
                                    0.1 // Adjust this threshold based on your requirements

                                if (distanceToUser < distanceThreshold && !reachedUserLocation) {
                                    reachedUserLocation = true
                                    binding.locateUser.visibility = View.GONE
                                    binding.endRequest.visibility = View.VISIBLE
                                    Snackbar.make(
                                        binding.root, "Customer Found", Snackbar.LENGTH_LONG
                                    ).show()
                                } else {
                                    if (reachedUserLocation) {
                                        // Handle the case where the Snackbar was already shown

                                    } else {
                                        // Handle other cases or show the Snackbar if needed
                                    }
                                }

                                mMap.animateCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                        currentLocation!!.coordinates!!, 15F
                                    )
                                )
                            }

                        } else {
                            Toast.makeText(
                                this@AdminHomePage, "User Not Found", Toast.LENGTH_SHORT
                            ).show()
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

        binding.endRequest.setOnClickListener {
            if (reachedUserLocation) {
                // Handle actions when the admin presses "End PickUp"
                // For example, remove polylines, hide bottom sheet, and reset button text
                erasePolylines()
                binding.bottomSheet.visibility = View.GONE
                binding.locateUser.visibility = View.VISIBLE
                reachedUserLocation = false
            } else {
                // Handle actions when the admin presses "Locate User"
                // For example, initiate navigation
            }
        }

        binding.startDrive.setOnClickListener {
            toggleDrivingMode()
        }

    }


    // Update the camera position when the user's location changes
    private fun updateCameraPosition(location: Location) {
        val target = LatLng(location.latitude, location.longitude)

        // Calculate the bearing between the previous and current location
        val bearing = calculateBearing(
            LatLng(
                currentLocation!!.coordinates!!.latitude,
                currentLocation!!.coordinates!!.longitude
            ),
            LatLng(location.latitude, location.longitude)
        )

        // Set up the camera position with the calculated bearing
        val cameraPosition = CameraPosition.Builder()
            .target(target)
            .zoom(15f)
            .bearing(bearing)
            .tilt(45f)
            .build()

        // Animate the camera to the new position
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        // Update the previous bearing for the next iteration
        previousBearing = bearing
    }

    // Call this method whenever the user's location changes
    private fun onLocationChanged(location: Location) {
        updateCameraPosition(location)
    }

    private fun calculateDistance(start: LatLng, end: LatLng): Float {
        val results = FloatArray(1)
        Location.distanceBetween(
            start.latitude, start.longitude, end.latitude, end.longitude, results
        )
        return results[0]
    }

    // Inside your AdminHomePage class

// ... (other existing code)

    private fun updateRoute(
        start: LocationObject,
        end: LocationObject,
        value: String
    ) {
        // Remove previous admin polyline and fixed marker
        eraseAdminPolyline()

        // Fetch the route from the start to the end for the admin
        fetchRoute(
            LatLng(start.coordinates!!.latitude, start.coordinates!!.longitude),
            LatLng(end.coordinates!!.latitude, end.coordinates!!.longitude),
            value
        ) { directionsResult ->
            val routes = directionsResult.routes

            // Loop through each route and draw directionsResult on the map
            for (i in routes.indices) {
                val route = routes[i]

                // Polyline options for the admin route
                val adminPolylineOptions = PolylineOptions()
                    .color(Color.BLUE) // Use a color for the admin polyline (adjust as needed)
                    .width(15f)
                    .startCap(RoundCap())  // Rounded cap at the start of the admin polyline
                    .endCap(RoundCap())    // Rounded cap at the end of the admin polyline

                // Add each step of the admin route to the polyline options
                for (j in route.legs.indices) {
                    val leg = route.legs[j]
                    for (k in leg.steps.indices) {
                        val step = leg.steps[k]
                        val points = step.polyline.decodePath()
                        for (point in points) {
                            adminPolylineOptions.add(LatLng(point.lat, point.lng))
                        }
                    }
                }

                // Draw the admin polyline on the map
                val adminPolyline = mMap.addPolyline(adminPolylineOptions)
                binding.bottomSheet.visibility = View.VISIBLE

                // Draw a fixed gray marker at the start of the admin polyline (admin's location on the road)
                fixedMarker = mMap.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            route.legs.last().endLocation.lat,
                            route.legs.last().endLocation.lng
                        )
                    )
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                )

                // Draw a dotted line from the fixed marker to the actual admin location
                val dottedAdminPolylineOptions = PolylineOptions()
                    .color(Color.GRAY)
                    .width(15f)
                    .pattern(listOf(Dot(), Gap(20f)))

                dottedAdminPolylineOptions.add(
                    fixedMarker!!.position,
                    LatLng(end.coordinates!!.latitude, end.coordinates!!.longitude)
                )

                mMap.addPolyline(dottedAdminPolylineOptions)

                // Update the UI with the calculated distance and duration
                val distanceInMeters = route.legs.sumOf { it.distance.inMeters.toInt() }
                val distanceInKilometers = distanceInMeters / 1000
                val durationInSeconds = route.legs.sumOf { it.duration.inSeconds.toInt() }

                // Assuming standard speed in km/h
                val standardSpeed = 50

                // Calculate time in hours
                val timeInHours = distanceInKilometers / standardSpeed

                // Convert hours to minutes
                val timeInMinutes = (timeInHours * 60).toInt()

                // Display time
                val duration: String = if (timeInMinutes > 0) {
                    "$timeInMinutes min"
                } else {
                    "Less than 1 min"
                }

                val distance: String = if (distanceInKilometers >= 1) {
                    // Display in kilometers
                    "$distanceInKilometers km"
                } else {
                    // Display in meters
                    "$distanceInMeters m"
                }

                binding.time.text = duration
                binding.distance.text = distance

                // Update the polylineStartLocation to the current location for the next update
                polylineStartLocation = start

                mMap.animateCamera(
                    CameraUpdateFactory.newCameraPosition(
                        CameraPosition.Builder()
                            .target(
                                LatLng(
                                    start.coordinates!!.latitude,
                                    start.coordinates!!.longitude
                                )
                            )
                            .zoom(15f)
                            .bearing(previousBearing)
                            .tilt(45f)
                            .build()
                    )
                )
            }
        }
    }

// ... (other existing code)


    private fun updateAdminRoute(
        start: LocationObject,
        end: LocationObject,
        value: String
    ) {
        // Remove previous admin polyline and fixed marker
        eraseAdminPolyline()

        // Fetch the route from the start to the end for the admin
        fetchRoute(
            LatLng(start.coordinates!!.latitude, start.coordinates!!.longitude),
            LatLng(end.coordinates!!.latitude, end.coordinates!!.longitude),
            value
        ) { directionsResult ->
            val routes = directionsResult.routes

            // Loop through each route and draw directionsResult on the map
            for (i in routes.indices) {
                val route = routes[i]

                // Polyline options for the admin route
                val adminPolylineOptions = PolylineOptions()
                    .color(Color.BLUE) // Use a color for the admin polyline (adjust as needed)
                    .width(15f)
                    .startCap(RoundCap())  // Rounded cap at the start of the admin polyline
                    .endCap(RoundCap())    // Rounded cap at the end of the admin polyline

                // Add each step of the admin route to the polyline options
                for (j in route.legs.indices) {
                    val leg = route.legs[j]
                    for (k in leg.steps.indices) {
                        val step = leg.steps[k]
                        val points = step.polyline.decodePath()
                        for (point in points) {
                            adminPolylineOptions.add(LatLng(point.lat, point.lng))
                        }
                    }
                }

                // Draw the admin polyline on the map
                val adminPolyline = mMap.addPolyline(adminPolylineOptions)

                // Draw a fixed gray marker at the start of the admin polyline (admin's location on the road)
                fixedMarker = mMap.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            route.legs.first().startLocation.lat,
                            route.legs.first().startLocation.lng
                        )
                    )
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                )

                // Draw a dotted line from the fixed marker to the actual admin location
                val dottedAdminPolylineOptions = PolylineOptions()
                    .color(Color.GRAY)
                    .width(15f)
                    .pattern(listOf(Dot(), Gap(20f)))

                dottedAdminPolylineOptions.add(
                    fixedMarker!!.position,
                    LatLng(start.coordinates!!.latitude, start.coordinates!!.longitude)
                )

                mMap.addPolyline(dottedAdminPolylineOptions)
            }
        }
    }

    private fun setMapOrientation(bearing: Float) {
        mMap.animateCamera(
            CameraUpdateFactory.newCameraPosition(
                CameraPosition.Builder()
                    .target(mMap.cameraPosition.target)
                    .zoom(mMap.maxZoomLevel)
                    .bearing(bearing)
                    .tilt(mMap.cameraPosition.tilt)
                    .build()
            )
        )
    }


    private fun eraseAdminPolyline() {
        // Remove previous admin polyline
        if (polyline != null) {
            polyline!!.remove()
        }

        // Remove previous fixed marker
        if (fixedMarker != null) {
            fixedMarker!!.remove()
        }
    }


    private fun convertToAndroidLatLng(googleLatLng: com.google.maps.model.LatLng): LatLng {
        return LatLng(googleLatLng.lat, googleLatLng.lng)
    }

    private fun isUserOnRoad(route: DirectionsRoute): Boolean {
        val toleranceMeters = 2 // Adjust this threshold as needed

        val userLocation = currentLocation!!.coordinates!!

        // Iterate through each step of the route to find the nearest point

        for (leg in route.legs) {
            for (step in leg.steps) {
                val points = step.polyline.decodePath().map { convertToAndroidLatLng(it) }

                // Find the nearest point on the polyline to the user's location
                val nearestPoint = findNearestPointOnPolyline(userLocation, points)

                // Calculate the distance between the user and the nearest point
                val distance = calculateDistance(userLocation, nearestPoint)

                // Check if the distance is below the tolerance threshold
                if (distance <= toleranceMeters) {
                    return true
                }
            }
        }

        return false
    }

    private fun findNearestPointOnPolyline(point: LatLng, polylinePoints: List<LatLng>): LatLng {
        var nearestPoint = polylinePoints[0]
        var minDistance = calculateDistance(point, nearestPoint)

        for (polylinePoint in polylinePoints) {
            val distance = calculateDistance(point, polylinePoint)
            if (distance < minDistance) {
                minDistance = distance
                nearestPoint = polylinePoint
            }
        }

        return nearestPoint
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        googleMap.setMapStyle(
            MapStyleOptions(
                resources.getString(R.string.style_json)
            )
        )


        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = 1000
        mLocationRequest!!.fastestInterval = 1000
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mFusedLocationClient!!.requestLocationUpdates(
                mLocationRequest!!, mLocationCallback, Looper.myLooper()
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

                    updateRoute(
                        LocationObject(LatLng(location.latitude, location.longitude), ""),
                        LocationObject(
                            LatLng(
                                destinationLocation!!.coordinates!!.latitude,
                                destinationLocation!!.coordinates!!.longitude
                            ), ""
                        ), value
                    )


                    // Update the admin marker position
                    if (adminMarker != null) {
                        adminMarker!!.position = LatLng(
                            currentLocation!!.coordinates!!.latitude,
                            currentLocation!!.coordinates!!.longitude
                        )
                    }

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
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                AlertDialog.Builder(this).setTitle("give permission")
                    .setMessage("give permission message").setPositiveButton(
                        "OK"
                    ) { dialogInterface, i ->
                        ActivityCompat.requestPermissions(
                            this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
                        )
                    }.create().show()
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this, Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        mFusedLocationClient!!.requestLocationUpdates(
                            mLocationRequest!!, mLocationCallback, Looper.myLooper()
                        )
                        mMap.isMyLocationEnabled = true
                    }
                } else {
                    Toast.makeText(
                        applicationContext, "Please provide the permission", Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }


    // Function to start the journey
    private fun startJourney() {
        val startLocation = LatLng(
            currentLocation!!.coordinates!!.latitude,
            currentLocation!!.coordinates!!.longitude
        )
        val endLocation = LatLng(
            destinationLocation!!.coordinates!!.latitude,
            destinationLocation!!.coordinates!!.longitude
        )
        val bearing = calculateBearing(startLocation, endLocation)

        // Define a LatLngBounds containing both the start and end locations
        val bounds = LatLngBounds.Builder()
            .include(startLocation)
            .include(endLocation)
            .build()

        // Change camera perspective to simulate driving mode
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))

        // Orient the map in the direction of the admin's movement
        mMap.animateCamera(
            CameraUpdateFactory.newCameraPosition(
                CameraPosition.Builder()
                    .target(startLocation)
                    .zoom(21f)
                    .bearing(bearing) // Set the bearing (direction) of the camera
                    .tilt(45f) // Set the tilt angle to give a 3D effect
                    .build()
            )
        )
        setMapOrientation(bearing)
    }


    private fun calculateBearing(start: LatLng, end: LatLng): Float {
        val startLat = Math.toRadians(start.latitude)
        val startLng = Math.toRadians(start.longitude)
        val endLat = Math.toRadians(end.latitude)
        val endLng = Math.toRadians(end.longitude)

        val deltaLng = endLng - startLng

        val x = Math.sin(deltaLng) * Math.cos(endLat)
        val y =
            Math.cos(startLat) * Math.sin(endLat) - (Math.sin(startLat) * Math.cos(endLat) * Math.cos(
                deltaLng
            ))

        var bearing = Math.atan2(x, y)
        bearing = Math.toDegrees(bearing)

        // Normalize to a compass bearing
        bearing = (bearing + 360) % 360

        return bearing.toFloat()
    }

    // Function to stop the journey
    private fun stopJourney() {
        // Set driving mode flag to false
        isDrivingMode = false

        // Reset camera perspective to default
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation!!.coordinates!!, 21f))
    }

    // Example of how to toggle the driving mode
    private fun toggleDrivingMode() {
        if (isDrivingMode) {
            // If driving mode is active, stop the journey
            stopJourney()
        } else {
            // If driving mode is not active, start the journey
            startJourney()
        }
    }

// Call toggleDrivingMode() when your button is clicked to start/stop the journey

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
