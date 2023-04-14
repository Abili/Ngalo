package com.aisc.ngalo.admin

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.aisc.ngalo.LocationObject
import com.aisc.ngalo.R
import com.aisc.ngalo.databinding.ActivityAdminHomePageBinding
import com.akexorcist.googledirection.DirectionCallback
import com.akexorcist.googledirection.GoogleDirection
import com.akexorcist.googledirection.constant.TransportMode
import com.akexorcist.googledirection.model.Direction
import com.akexorcist.googledirection.model.Route
import com.akexorcist.googledirection.util.DirectionConverter
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class AdminHomePage : AppCompatActivity(), OnMapReadyCallback, DirectionCallback {

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
            customerMarker = mMap.addMarker(
                MarkerOptions().position(
                    LatLng(
                        myLocation!!.coordinates!!.latitude,
                        myLocation!!.coordinates!!.longitude
                    )
                ).title("your driver")
                    .icon(
                        BitmapDescriptorFactory.fromBitmap(
                            generateBitmap(
                                this@AdminHomePage,
                                myLocation!!.name,
                                null
                            )!!
                        )
                    )
            )
            val uid = FirebaseAuth.getInstance().uid
            val requestsRef =
                FirebaseDatabase.getInstance().reference.child("RepaireRequests").child(uid!!)

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
                                ).title("your driver")
                                //   .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car))
                            )

                            mapFragment.getMapAsync {
                                val currentLocation = LocationObject(
                                    LatLng(
                                        myLocation!!.coordinates!!.latitude,
                                        myLocation!!.coordinates!!.longitude
                                    ), placeName.toString()
                                )
                                mMap = it
                                mMap.addMarker(MarkerOptions().position(currentLocation.coordinates!!))
                                val destinationLocation = LocationObject(
                                    LatLng(
                                        latitude,
                                        longitude
                                    ), placeName.toString()
                                )
                                mMap.addMarker(MarkerOptions().position(destinationLocation.coordinates!!))
                                getRouteToMarker(destinationLocation.coordinates)
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

    private fun generateBitmap(context: Context, location: String?, duration: String?): Bitmap? {
        var bitmap: Bitmap?
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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


//    private fun getDirectionURL(origin: LatLng, dest: LatLng, secret: String): String {
//        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}" +
//                "&destination=${dest.latitude},${dest.longitude}" +
//                "&sensor=false" +
//                "&mode=driving" +
//                "&key=$secret"
//    }
//
//    @SuppressLint("StaticFieldLeak")
//    private inner class GetDirection(val url: String) :
//        AsyncTask<Void, Void, List<List<LatLng>>>() {
//        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
//            val client = OkHttpClient()
//            val request = Request.Builder().url(url).build()
//            val response = client.newCall(request).execute()
//            val data = response.body()!!.string()
//
//            val result = ArrayList<List<LatLng>>()
//            try {
//                val respObj = Gson().fromJson(data, MapData::class.java)
//                val path = ArrayList<LatLng>()
//                for (i in 0 until respObj.routes[0].legs[0].steps.size) {
//                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
//                }
//                result.add(path)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            return result
//        }
//
//        override fun onPostExecute(result: List<List<LatLng>>) {
//            val lineoption = PolylineOptions()
//            for (i in result.indices) {
//                lineoption.addAll(result[i])
//                lineoption.width(10f)
//                lineoption.color(Color.GREEN)
//                lineoption.geodesic(true)
//            }
//            mMap.addPolyline(lineoption)
//        }
//    }
//
//    fun decodePolyline(encoded: String): List<LatLng> {
//        val poly = ArrayList<LatLng>()
//        var index = 0
//        val len = encoded.length
//        var lat = 0
//        var lng = 0
//        while (index < len) {
//            var b: Int
//            var shift = 0
//            var result = 0
//            do {
//                b = encoded[index++].code - 63
//                result = result or (b and 0x1f shl shift)
//                shift += 5
//            } while (b >= 0x20)
//            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
//            lat += dlat
//            shift = 0
//            result = 0
//            do {
//                b = encoded[index++].code - 63
//                result = result or (b and 0x1f shl shift)
//                shift += 5
//            } while (b >= 0x20)
//            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
//            lng += dlng
//            val latLng = LatLng((lat.toDouble() / 1E5), (lng.toDouble() / 1E5))
//            poly.add(latLng)
//        }
//        return poly
//    }

    /**
     * Get Route from pickup to destination, showing the route to the user
     * @param destination - LatLng of the location to go to
     */
    private fun getRouteToMarker(destination: LatLng?) {
        val serverKey = resources.getString(R.string.Api_key)
        if (destination != null && myLocation != null) {
            GoogleDirection.withServerKey(serverKey)
                .from(
                    LatLng(
                        myLocation!!.coordinates!!.latitude,
                        myLocation!!.coordinates!!.longitude
                    )
                )
                .to(destination)
                .transportMode(TransportMode.DRIVING)
                .execute(this)
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
        for (line in polylines!!) {
            line.remove()
        }
        polylines.clear()
    }

    override fun onDirectionSuccess(direction: Direction, rawBody: String?) {
        if (direction.isOK) {
            val route = direction.routeList[0]
            val directionPositionList = route.legList[0].directionPoint
            val polyline = mMap.addPolyline(
                DirectionConverter.createPolyline(
                    this,
                    directionPositionList,
                    5,
                    Color.BLACK
                )
            )
            polylines!!.add(polyline)
            setCameraWithCoordinationBounds(route)
        }
    }

    private fun setCameraWithCoordinationBounds(route: Route) {
        val southwest = route.bound.southwestCoordination.coordination
        val northeast = route.bound.northeastCoordination.coordination
        val bounds = LatLngBounds(southwest, northeast)
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
    }

    override fun onDirectionFailure(t: Throwable?) {}

}
