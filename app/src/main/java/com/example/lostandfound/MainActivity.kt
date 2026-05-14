package com.example.lostandfound

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnView = findViewById<Button>(R.id.btnView)
        val btnMap = findViewById<Button>(R.id.btnMap)

        // Open Add Item screen
        btnAdd.setOnClickListener {
            startActivity(Intent(this, AddItemActivity::class.java))
        }

        // Open Show Items screen
        btnView.setOnClickListener {
            startActivity(Intent(this, ShowItemsActivity::class.java))
        }

        // Open Google Map screen
        btnMap.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
    }
}