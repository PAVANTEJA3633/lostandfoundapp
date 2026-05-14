package com.example.lostandfound

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.math.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var dbHelper: DBHelper
    private lateinit var etRadius: EditText

    // User/current location for emulator testing
    // In real phone this would be the user's GPS location
    private val userLocation = LatLng(-37.8136, 144.9631) // Melbourne City

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        dbHelper = DBHelper(this)

        val btnBackMap = findViewById<Button>(R.id.btnBackMap)
        val btnApplyRadius = findViewById<Button>(R.id.btnApplyRadius)
        etRadius = findViewById(R.id.etRadius)

        btnBackMap.setOnClickListener {
            finish()
        }

        btnApplyRadius.setOnClickListener {
            val radiusText = etRadius.text.toString().trim()

            if (radiusText.isEmpty()) {
                Toast.makeText(this, "Please enter radius", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val radiusKm = radiusText.toDoubleOrNull()

            if (radiusKm == null || radiusKm <= 0) {
                Toast.makeText(this, "Enter valid radius", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            showMarkersWithinRadius(radiusKm)
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isZoomGesturesEnabled = true
        googleMap.uiSettings.isScrollGesturesEnabled = true

        // Default radius when map opens
        showMarkersWithinRadius(25.0)
    }

    private fun showMarkersWithinRadius(radiusKm: Double) {
        googleMap.clear()

        // User/current location marker
        googleMap.addMarker(
            MarkerOptions()
                .position(userLocation)
                .title("User Current Location")
                .snippet("Radius search starts from here")
        )

        // Demo lost/found markers
        val demoMarkers = arrayListOf(
            DemoMarker(
                "Melbourne City Item",
                "Lost item example",
                LatLng(-37.8136, 144.9631)
            ),
            DemoMarker(
                "Deakin University Burwood Campus",
                "Lost item marker example",
                LatLng(-37.8479, 145.1149)
            ),
            DemoMarker(
                "Melbourne Airport",
                "Found item marker example",
                LatLng(-37.6690, 144.8410)
            )
        )

        var markerCount = 0

        for (marker in demoMarkers) {
            val distance = calculateDistanceKm(
                userLocation.latitude,
                userLocation.longitude,
                marker.position.latitude,
                marker.position.longitude
            )

            if (distance <= radiusKm) {
                googleMap.addMarker(
                    MarkerOptions()
                        .position(marker.position)
                        .title(marker.title)
                        .snippet("${marker.snippet} - ${"%.2f".format(distance)} km away")
                )

                markerCount++
            }
        }

        // Saved database item markers
        val savedItems = dbHelper.getAllItemsForMap()

        for (item in savedItems) {
            val distance = calculateDistanceKm(
                userLocation.latitude,
                userLocation.longitude,
                item.latitude,
                item.longitude
            )

            if (distance <= radiusKm) {
                val position = LatLng(item.latitude, item.longitude)

                googleMap.addMarker(
                    MarkerOptions()
                        .position(position)
                        .title(item.name)
                        .snippet("${item.type} - ${item.location} - ${"%.2f".format(distance)} km away")
                )

                markerCount++
            }
        }

        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                userLocation,
                9f
            )
        )

        Toast.makeText(
            this,
            "$markerCount item(s) found within $radiusKm km",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun calculateDistanceKm(
        startLat: Double,
        startLng: Double,
        endLat: Double,
        endLng: Double
    ): Double {
        val earthRadius = 6371.0

        val dLat = Math.toRadians(endLat - startLat)
        val dLng = Math.toRadians(endLng - startLng)

        val a = sin(dLat / 2).pow(2.0) +
                cos(Math.toRadians(startLat)) *
                cos(Math.toRadians(endLat)) *
                sin(dLng / 2).pow(2.0)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return earthRadius * c
    }

    data class DemoMarker(
        val title: String,
        val snippet: String,
        val position: LatLng
    )
}