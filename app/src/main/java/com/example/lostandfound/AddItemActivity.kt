package com.example.lostandfound

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddItemActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    private var imageSelected = false
    private var imageText = ""

    private var latitude = 0.0
    private var longitude = 0.0
    private var locationSelected = false

    private lateinit var imgPreview: ImageView
    private lateinit var etLocation: EditText

    private val imagePicker =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->

            if (uri != null) {
                imageSelected = true
                imageText = uri.toString()

                contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                imgPreview.setImageURI(uri)
                imgPreview.visibility = View.VISIBLE

                Toast.makeText(this, "Image selected successfully", Toast.LENGTH_SHORT).show()
            } else {
                imageSelected = false
                imageText = ""
                imgPreview.visibility = View.GONE

                Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        dbHelper = DBHelper(this)

        val radioGroupType = findViewById<RadioGroup>(R.id.radioGroupType)
        val etName = findViewById<EditText>(R.id.etName)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etDescription = findViewById<EditText>(R.id.etDescription)
        val etDate = findViewById<EditText>(R.id.etDate)
        etLocation = findViewById(R.id.etLocation)
        val spCategory = findViewById<Spinner>(R.id.spCategory)
        val btnUploadImage = findViewById<Button>(R.id.btnUploadImage)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnCurrentLocation = findViewById<Button>(R.id.btnCurrentLocation)

        imgPreview = findViewById(R.id.imgPreview)

        val currentDate = SimpleDateFormat(
            "dd/MM/yyyy HH:mm",
            Locale.getDefault()
        ).format(Date())

        etDate.setText(currentDate)

        val categories = arrayOf(
            "Electronics",
            "Pets",
            "Wallets",
            "Keys",
            "Bags",
            "Other"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            categories
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spCategory.adapter = adapter

        btnUploadImage.setOnClickListener {
            imagePicker.launch(arrayOf("image/*"))
        }

        btnCurrentLocation.setOnClickListener {
            getCurrentLocation()
        }

        btnSave.setOnClickListener {

            val selectedRadioId = radioGroupType.checkedRadioButtonId

            if (selectedRadioId == -1) {
                Toast.makeText(this, "Please select Lost or Found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedRadio = findViewById<RadioButton>(selectedRadioId)

            val type = selectedRadio.text.toString()
            val name = etName.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val description = etDescription.text.toString().trim()
            val date = etDate.text.toString().trim()
            val location = etLocation.text.toString().trim()
            val category = spCategory.selectedItem.toString()

            if (
                name.isEmpty() ||
                phone.isEmpty() ||
                description.isEmpty() ||
                date.isEmpty() ||
                location.isEmpty()
            ) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!locationSelected) {
                Toast.makeText(this, "Please click Get Current Location", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!imageSelected) {
                Toast.makeText(this, "Please upload image", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val success = dbHelper.insertItem(
                type,
                name,
                phone,
                description,
                date,
                location,
                category,
                imageText,
                latitude,
                longitude
            )

            if (success) {
                Toast.makeText(this, "Advert saved successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Advert not saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getCurrentLocation() {
        if (
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                200
            )
            return
        }

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->

            if (location != null) {
                latitude = location.latitude
                longitude = location.longitude
                locationSelected = true

                etLocation.setText("Lat: $latitude, Lng: $longitude")

                Toast.makeText(this, "Current location selected", Toast.LENGTH_SHORT).show()
            } else {
                // Default Melbourne location for emulator testing
                latitude = -37.8136
                longitude = 144.9631
                locationSelected = true

                etLocation.setText("Lat: $latitude, Lng: $longitude")

                Toast.makeText(this, "Default Melbourne location selected", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to get location", Toast.LENGTH_SHORT).show()
        }
    }
}