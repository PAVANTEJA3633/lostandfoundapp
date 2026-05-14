# Lost and Found Map Mobile App

## Overview

This project is an Android mobile application developed for Task 9.1P. It is an extension of the Lost and Found app from Task 7.1P. The main purpose of this app is to help users report lost or found items and view their locations on Google Maps.

The app allows users to create a lost or found advert, upload an item image, save item details into a local SQLite database, get the current location, and display item locations using map markers. A radius-based search feature is also included so users can view only the items within a selected distance from the current location.

## Main Features

- Create a lost or found advert
- Enter item details such as name, phone number, description, date, location, and category
- Upload an image for the lost or found item
- Store item details using SQLite database
- Save latitude and longitude values for item locations
- Show item locations on Google Maps
- Display multiple map markers
- Use current location from the emulator or device
- Apply radius-based search to show nearby items only
- Navigate back from the map screen easily

## Technologies Used

- Android Studio
- Kotlin
- XML layouts
- SQLite database
- Google Maps SDK for Android
- Google Location Services
- Git and GitHub

## App Screens

### 1. Home Screen

The home screen provides three main options:

- Create New Advert
- Show All Lost and Found Items
- Show On Map

The Show On Map button opens the Google Maps screen where item locations are displayed as markers.

### 2. Create New Advert Screen

This screen allows users to create a lost or found item advert. The user can select whether the item is lost or found, enter item details, choose a category, upload an image, and get the current location.

The Get Current Location button gets the latitude and longitude from the emulator or device. These coordinates are stored with the advert details.

### 3. Show Items Screen

This screen displays the saved lost and found adverts in list format. Users can view the details of items that have been saved in the local SQLite database.

### 4. Map Screen

The map screen displays Google Maps with item markers. It includes fixed demo markers and saved item markers from the database. The screen also includes a Back button and zoom controls.

### 5. Radius Search

The radius-based search allows users to enter a distance in kilometres. The app then shows only the item markers that are within the selected radius from the user’s current location.

## How the Location Feature Works

When the user clicks the Get Current Location button, the app gets the current latitude and longitude from the Android emulator or mobile device.

In the emulator, the location depends on the location set in the emulator settings. On a real mobile phone, this would use the phone’s GPS location.

The latitude and longitude values are saved into the SQLite database together with the lost or found item details. These coordinates are later used to show the item location on Google Maps.

## Radius-Based Search

The radius-based search feature filters map markers based on distance.

The user can enter a distance such as:

- 5 km
- 15 km
- 25 km

The app calculates the distance between the user's current location and each item location. Only the items within the selected radius are shown on the map.

This feature is useful because users can focus on lost or found items near their current area instead of seeing items that are too far away.

## Project Structure

```text
app/
 └── src/
     └── main/
         ├── java/com/example/lostandfound/
         │   ├── MainActivity.kt
         │   ├── AddItemActivity.kt
         │   ├── ShowItemsActivity.kt
         │   ├── RemoveItemActivity.kt
         │   ├── MapsActivity.kt
         │   └── DBHelper.kt
         │
         ├── res/layout/
         │   ├── activity_main.xml
         │   ├── activity_add_item.xml
         │   ├── activity_show_items.xml
         │   ├── activity_remove_item.xml
         │   └── activity_maps.xml
         │
         └── AndroidManifest.xml
