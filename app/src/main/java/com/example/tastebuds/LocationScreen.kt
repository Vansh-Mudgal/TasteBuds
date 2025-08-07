package com.example.tastebuds

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.tastebuds.databinding.ActivityLocationScreenBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import java.util.Locale

class LocationScreen : AppCompatActivity(), OnMapReadyCallback {

    private val binding : ActivityLocationScreenBinding by lazy {
        ActivityLocationScreenBinding.inflate(layoutInflater)
    }
    private lateinit var  myMap : GoogleMap
    private val finePermissionCode = 1
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val auth = Firebase.auth
    private val db = Firebase.database.reference
    private lateinit var locationString : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation()

    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), finePermissionCode)
            return
        }
        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener(OnSuccessListener<Location> { location ->
            if (location != null) {
                currentLocation = location
                val geocoder = Geocoder(this, Locale.getDefault())
                if(Geocoder.isPresent()){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        // API 33+ async geocoding
                        geocoder.getFromLocation(
                            location.latitude,
                            location.longitude,
                            1,
                            object : Geocoder.GeocodeListener {
                                override fun onGeocode(addresses: MutableList<Address>) {
                                    handleAddressResult(addresses.firstOrNull())
                                }
                            }
                        )
                    } else {
                        // API <33 blocking geocoding
                        try {
                            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                            handleAddressResult(addresses?.firstOrNull())
                        } catch (e: Exception) {
                            locationString = "Location not available"
                        }
                    }

                }
            }
            val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
            mapFragment.getMapAsync(this@LocationScreen)
        })

    }

    private fun handleAddressResult(address: Address?) {
        locationString = address?.let {
            "${it.locality ?: "Unknown"}, ${it.adminArea ?: "Unknown"}, ${it.countryName ?: "Unknown"}"
        } ?: "Location not available"

        val updates = mapOf<String, Any>(
            "address" to locationString
        )
        auth.uid?.let { db.child("user").child(it).updateChildren(updates) }

    }

    override fun onMapReady(p0: GoogleMap) {
        myMap = p0
        val initialLocation = LatLng(currentLocation.latitude, currentLocation.longitude)
        myMap.addMarker(MarkerOptions().position(initialLocation).title("Current Location"))
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 10f))
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

    @SuppressLint("NewApi")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == finePermissionCode){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getLastLocation()
            }
            else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}