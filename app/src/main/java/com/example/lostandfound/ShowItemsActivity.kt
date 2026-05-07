package com.example.lostandfound

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ShowItemsActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper
    private lateinit var listItems: ListView
    private lateinit var spFilter: Spinner

    private var currentItems = ArrayList<LostFoundItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_items)

        dbHelper = DBHelper(this)

        listItems = findViewById(R.id.listItems)
        spFilter = findViewById(R.id.spFilter)

        val categories = arrayOf("All", "Electronics", "Pets", "Wallets", "Keys", "Bags", "Other")

        spFilter.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            categories
        )

        loadItems("All")

        spFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
                loadItems(categories[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        listItems.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = currentItems[position]

            val intent = Intent(this, RemoveItemActivity::class.java)
            intent.putExtra("id", selectedItem.id)
            intent.putExtra("type", selectedItem.type)
            intent.putExtra("name", selectedItem.name)
            intent.putExtra("date", selectedItem.date)
            intent.putExtra("location", selectedItem.location)
            intent.putExtra("category", selectedItem.category)
            intent.putExtra("image", selectedItem.image)

            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        if (::spFilter.isInitialized) {
            loadItems(spFilter.selectedItem.toString())
        }
    }

    private fun loadItems(category: String) {
        currentItems.clear()

        currentItems = if (category == "All") {
            dbHelper.getAllItemDetails()
        } else {
            dbHelper.getItemDetailsByCategory(category)
        }

        val displayList = ArrayList<String>()

        for (item in currentItems) {
            displayList.add("${item.type} - ${item.name}")
        }

        if (displayList.isEmpty()) {
            Toast.makeText(this, "No items found", Toast.LENGTH_SHORT).show()
        }

        listItems.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            displayList
        )
    }
}