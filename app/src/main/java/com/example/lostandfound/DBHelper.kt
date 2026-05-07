package com.example.lostandfound

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class LostFoundItem(
    val id: Int,
    val type: String,
    val name: String,
    val date: String,
    val location: String,
    val category: String,
    val image: String
)

class DBHelper(context: Context) : SQLiteOpenHelper(context, "LostFound.db", null, 6) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE items(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "type TEXT," +
                    "name TEXT," +
                    "phone TEXT," +
                    "description TEXT," +
                    "date TEXT," +
                    "location TEXT," +
                    "category TEXT," +
                    "image TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS items")
        onCreate(db)
    }

    fun insertItem(
        type: String,
        name: String,
        phone: String,
        description: String,
        date: String,
        location: String,
        category: String,
        image: String
    ): Boolean {
        val db = writableDatabase

        val values = ContentValues()
        values.put("type", type)
        values.put("name", name)
        values.put("phone", phone)
        values.put("description", description)
        values.put("date", date)
        values.put("location", location)
        values.put("category", category)
        values.put("image", image)

        val result = db.insert("items", null, values)
        db.close()

        return result != -1L
    }

    fun getAllItemDetails(): ArrayList<LostFoundItem> {
        val list = ArrayList<LostFoundItem>()
        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT id, type, name, date, location, category, image FROM items",
            null
        )

        while (cursor.moveToNext()) {
            val item = LostFoundItem(
                id = cursor.getInt(0),
                type = cursor.getString(1),
                name = cursor.getString(2),
                date = cursor.getString(3),
                location = cursor.getString(4),
                category = cursor.getString(5),
                image = cursor.getString(6)
            )

            list.add(item)
        }

        cursor.close()
        db.close()

        return list
    }

    fun getItemDetailsByCategory(category: String): ArrayList<LostFoundItem> {
        val list = ArrayList<LostFoundItem>()
        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT id, type, name, date, location, category, image FROM items WHERE category=?",
            arrayOf(category)
        )

        while (cursor.moveToNext()) {
            val item = LostFoundItem(
                id = cursor.getInt(0),
                type = cursor.getString(1),
                name = cursor.getString(2),
                date = cursor.getString(3),
                location = cursor.getString(4),
                category = cursor.getString(5),
                image = cursor.getString(6)
            )

            list.add(item)
        }

        cursor.close()
        db.close()

        return list
    }

    fun deleteItem(id: Int): Boolean {
        val db = writableDatabase

        val result = db.delete(
            "items",
            "id=?",
            arrayOf(id.toString())
        )

        db.close()

        return result > 0
    }
}