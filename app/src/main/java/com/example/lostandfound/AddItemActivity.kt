package com.example.lostandfound

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddItemActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    private var imageSelected = false
    private var imageText = ""

    private lateinit var imgPreview: ImageView

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
        val etLocation = findViewById<EditText>(R.id.etLocation)
        val spCategory = findViewById<Spinner>(R.id.spCategory)
        val btnUploadImage = findViewById<Button>(R.id.btnUploadImage)
        val btnSave = findViewById<Button>(R.id.btnSave)

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
                imageText
            )

            if (success) {
                Toast.makeText(this, "Advert saved successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Advert not saved", Toast.LENGTH_SHORT).show()
            }
        }
    }
}