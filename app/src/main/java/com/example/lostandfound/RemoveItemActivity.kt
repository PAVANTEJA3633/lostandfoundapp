package com.example.lostandfound

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RemoveItemActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    private var itemId: Int = -1
    private var itemImage: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remove_item)

        dbHelper = DBHelper(this)

        val tvTitle = findViewById<TextView>(R.id.tvRemoveTitle)
        val tvDate = findViewById<TextView>(R.id.tvRemoveDate)
        val tvLocation = findViewById<TextView>(R.id.tvRemoveLocation)
        val tvCategory = findViewById<TextView>(R.id.tvRemoveCategory)
        val imgRemovePreview = findViewById<ImageView>(R.id.imgRemovePreview)
        val btnRemove = findViewById<Button>(R.id.btnRemoveItem)

        itemId = intent.getIntExtra("id", -1)

        val itemType = intent.getStringExtra("type") ?: ""
        val itemName = intent.getStringExtra("name") ?: ""
        val itemDate = intent.getStringExtra("date") ?: ""
        val itemLocation = intent.getStringExtra("location") ?: ""
        val itemCategory = intent.getStringExtra("category") ?: ""
        itemImage = intent.getStringExtra("image") ?: ""

        tvTitle.text = "$itemType $itemName"
        tvDate.text = itemDate
        tvLocation.text = "At $itemLocation"
        tvCategory.text = "Category: $itemCategory"

        if (itemImage.isNotEmpty()) {
            imgRemovePreview.setImageURI(Uri.parse(itemImage))
            imgRemovePreview.visibility = View.VISIBLE
        } else {
            imgRemovePreview.visibility = View.GONE
        }

        btnRemove.setOnClickListener {
            if (itemId == -1) {
                Toast.makeText(this, "Item not found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val deleted = dbHelper.deleteItem(itemId)

            if (deleted) {
                Toast.makeText(this, "Item removed successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Item not removed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}