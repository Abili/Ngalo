package com.aisc.ngalo.rides

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.aisc.ngalo.LocationObject
import com.aisc.ngalo.R
import com.aisc.ngalo.databinding.ActivityRidesBinding
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
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RidesActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityRidesBinding
    private lateinit var locationManager: LocationManager
    private lateinit var googleMap: GoogleMap
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var startLocation: Location? = null
    private var distance: Float = 0f
    var mLastLocation: LocationObject? = null
    private var currentLocation: LocationObject? = null
    private var startTime: Long = 0L
    private var mapFragment: SupportMapFragment? = null
    private var pickupMarker: Marker? = null
    var mLocationRequest: LocationRequest? = null
    private var countDownTimer: CountDownTimer? = null
    private var shouldDrawPolyline: Boolean = false
    private var polylineOptions: PolylineOptions? = null
    private var remainingTime: Long = 0L
    private var positionMarker: Marker? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRidesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mapFragment = supportFragmentManager.findFragmentById(R.id.map_view) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        // Check for location permission
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request the permission if not granted
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
        }

        // Get the LocationManager instance
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager


        binding.startrideBtn.setOnClickListener {
            // Start the ride
            startRide()
            binding.bottomSheet.visibility = View.VISIBLE
            binding.startrideBtn.visibility = View.GONE
        }

        binding.stoprideBtn.setOnClickListener {
            // Stop the ride
            stopRide()

        }


    }

    var zoomUpdated = false
    private var mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                if (application != null) {
                    currentLocation =
                        LocationObject(LatLng(location.latitude, location.longitude), "")
                    val latLng = LatLng(location.latitude, location.longitude)
                    if (!zoomUpdated) {
                        val zoomLevel = 17.0f //This goes up to 21
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(zoomLevel))
                        zoomUpdated = true
                    }
                    updatePositionMarker(latLng)
                }
            }
        }

    }

    private fun updatePositionMarker(latLng: LatLng) {
        if (positionMarker == null) {
            val markerOptions = MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            positionMarker = googleMap.addMarker(markerOptions)
        } else {
            positionMarker?.position = latLng
        }
    }


    override fun onResume() {
        super.onResume()
        mapFragment!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapFragment!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapFragment!!.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapFragment!!.onLowMemory()
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        polylineOptions = PolylineOptions()
            .width(10f)
            .color(Color.BLUE)

        mLastLocation = currentLocation
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    startLocation?.latitude ?: 0.0,
                    startLocation?.longitude ?: 0.0
                ), 17f
            )
        )

        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = 1000
        mLocationRequest!!.fastestInterval = 1000
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
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
            map.isMyLocationEnabled = true
        } else {
            checkLocationPermission()
        }

    }

    private fun startRide() {
        startTime = System.currentTimeMillis()
        shouldDrawPolyline = true
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0L,
                0f,
                locationListener
            )

            // Start the countdown timer
            startTime = System.currentTimeMillis()
            binding.tracktrideTime.text = startTime.toString()
            countDownTimer =
                object : CountDownTimer(Long.MAX_VALUE, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val elapsedTime = System.currentTimeMillis() - startTime
                        remainingTime = (elapsedTime) / 1000
                        updateTimerText(remainingTime)
                    }

                    override fun onFinish() {
                        // Countdown timer has finished
                        stopRide()
                    }
                }

            countDownTimer!!.start()
        }
    }

    private fun stopRide() {
        // Cancel the countdown timer
        countDownTimer?.cancel()

        // Set the flag to false to disable polyline drawing
        shouldDrawPolyline = false
        positionMarker?.remove()

        // Remove the polyline from the map
        googleMap.clear()

        // Show the summary dialog
        showRideSummaryDialog()
    }


    private fun showRideSummaryDialog() {
        AlertDialog.Builder(this)
            .setTitle("Ride Summary")
            .setMessage("Distance: $distance m\nTime: ${getElapsedTime(startTime)} seconds")
            .setPositiveButton("Save") { dialog, _ ->
                // Save the ride details
                nameRideSummaryDialog()
                //captureAndSaveScreenshot(remainingTime.toString())
                dialog.dismiss()
            }
            .setNegativeButton("Reset Ride") { dialog, _ ->
                // Resume the ride
                resetRide()
                binding.bottomSheet.visibility = View.GONE
                binding.startrideBtn.visibility = View.VISIBLE
                // Start the countdown timer with the remaining time
                //updateTimerText(remainingTime)
                dialog.dismiss()
            }
            .show()
    }


    private fun nameRideSummaryDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_ride_summary, null)
        val editTextRideName = dialogView.findViewById<EditText>(R.id.editTextRideName)

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("Save") { dialog, _ ->
                val rideName = editTextRideName.text.toString().trim()
                if (rideName.isNotEmpty()) {
                    // Save the ride details
                    saveRide(rideName, distance, getElapsedTime(startTime))
                    resetRide()
                    googleMap.clear()
                    finish()
                } else {
                    Toast.makeText(this, "Please enter a ride name", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                // Resume the ride
                resetRide()
                binding.bottomSheet.visibility = View.GONE
                binding.startrideBtn.visibility = View.VISIBLE
                dialog.dismiss()
            }
            .show()
    }


    private fun resetRide() {
        // Reset all ride-related variables and flags
        distance = 0f
        startTime = 0L
        remainingTime = 0L
        shouldDrawPolyline = false
        polylineOptions = null
        countDownTimer?.cancel()
        countDownTimer = null
        // Reset the UI elements
        clearUI()
        resetMap()
        positionMarker?.remove()
    }
    private fun resetMap() {
        googleMap.clear()
        polylineOptions = PolylineOptions().width(10f).color(Color.BLUE)
        zoomUpdated = false
    }
    private fun clearUI() {
        binding.trackrideDistance.text = "0"
        binding.tracktrideTime.text = "0"
    }

    private fun startCountdownTimer(timeInMillis: Long) {
        countDownTimer = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Update the countdown timer
                val remainingTimeSeconds = millisUntilFinished / 1000
                binding.tracktrideTime.text = remainingTimeSeconds.toString()
                remainingTime = millisUntilFinished
            }

            override fun onFinish() {
                // Handle countdown timer finish
            }
        }
        countDownTimer!!.start()
    }

    private fun updateTimerText(timeInSeconds: Long) {
        val hours = timeInSeconds / 3600
        val minutes = timeInSeconds / 60
        val seconds = timeInSeconds % 60
        binding.tracktrideTime.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    private fun saveRide(rideName: String, distance: Float, timeInSeconds: String) {
        // Save ride details to the database
        // ...
        saveRideToFirebase(rideName, distance, timeInSeconds)
        Toast.makeText(
            this,
            "Ride saved: Distance=$distance, Time=$timeInSeconds seconds",
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        private const val MAX_TIME: Long = 300 // 5 minutes in seconds
    }


    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            // Calculate distance between consecutive locations
            // Draw the route on the map
            if (shouldDrawPolyline) {
                if (startLocation != null) {
                    distance += location.distanceTo(startLocation!!)
                    // Update the distance in the TextView
                    runOnUiThread {
                        // Update the distance in the TextView
                        binding.trackrideDistance.text = distance.toString()

                        // Calculate elapsed time
                        // Update the time in the TextView
                        //binding.tracktrideTime.text = getElapsedTime(startTime)
                    }
                    // Draw the route on the map

                    // Add the polyline to the map
                    polylineOptions?.add(
                        LatLng(startLocation!!.latitude, startLocation!!.longitude),
                        LatLng(location.latitude, location.longitude)
                    )
                    // Draw the polyline on the map
                    googleMap.addPolyline(polylineOptions!!)
                }
                startLocation = location
            }
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

        override fun onProviderEnabled(provider: String) {}

        override fun onProviderDisabled(provider: String) {}
    }


    private fun getElapsedTime(startTime: Long): String {
        val elapsedTime = System.currentTimeMillis() - startTime
        val seconds = (elapsedTime / 1000) % 60
        val minutes = (elapsedTime / (1000 * 60)) % 60
        val hours = (elapsedTime / (1000 * 60 * 60)) % 24
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    private fun saveRideToFirebase(rideName: String, distance: Float, elapsedTime: String) {
        // Save ride details (distance and elapsed time) to Firebase Database
        // TODO: Initialize your Firebase Realtime Database reference
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val databaseReference =
            FirebaseDatabase.getInstance().reference.child("rides")
        val userdbReference =
            FirebaseDatabase.getInstance().reference.child("users").child(uid).child("rides")
        val rideId = databaseReference.push().key
        val ride = Ride(uid, rideName, distance, elapsedTime, null)

        if (rideId != null) {
            databaseReference.push().setValue(ride)
                .addOnSuccessListener {
                    Toast.makeText(this, "Ride saved to Firebase", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        this,
                        "Failed to save ride: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }

        userdbReference.push().setValue(ride)
            .addOnSuccessListener {
                Toast.makeText(this, "Ride saved to Firebase", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    this,
                    "Failed to save ride: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
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
                android.app.AlertDialog.Builder(this)
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

    private fun captureAndSaveScreenshot(elapsedTime: String) {
        // Create a Bitmap object of the map view
        val mapView: View = findViewById(R.id.map_view)
        val screenshot = Bitmap.createBitmap(mapView.width, mapView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(screenshot)
        mapView.draw(canvas)

        // Save the screenshot to a file
        val screenshotsDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "ride_screenshot_$timestamp.png"
        val screenshotFile = File(screenshotsDir, fileName)
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(screenshotFile)
            screenshot.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
            // Add the screenshot file path to the ride object before saving to Firebase
            //saveRideToFirebase(distance, elapsedTime)
        } catch (e: IOException) {
            e.printStackTrace()
            // Handle the error
        } finally {
            fos?.close()
        }
    }
}

data class Ride(
    val uid: String?="",
    val rideName: String?="",
    val distance: Float = 0f,
    val elapsedTime: String? = "",
    val username: String?=""
)

