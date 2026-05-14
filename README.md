# lostandfoundapp

## About This Project

This is a simple Lost and Found Android application developed for SIT708 Task 7.1. The main idea of this app is to help people report lost or found items so that the items can be returned to their owners more easily.

The app allows a user to create a new advert for an item, save the details, upload an image, view all saved items, filter items by category, and remove an advert when the item has been returned.

## What the App Can Do

This app includes the following features:

- Create a new lost or found item advert
- Select whether the item is Lost or Found
- Enter item name, phone number, description, date, and location
- Automatically add the current date and time to the post
- Choose a category such as Electronics, Pets, Wallets, Keys, Bags, or Other
- Upload an image for the item
- Show a preview of the uploaded image
- Save item details using SQLite database
- View all lost and found items
- Filter items based on category
- Open a selected item on a separate screen
- Remove the item once it has been returned to the owner

## Screens in the App

### Main Screen

The main screen has two buttons. One button is used to create a new advert, and the other button is used to view all lost and found items.

### Create New Advert Screen

On this screen, the user can enter the details of the lost or found item. The user can select the post type, enter contact details, add a description, choose a category, upload an image, and save the advert.

### Show Items Screen

This screen shows all saved lost and found items. The user can also use the category filter to view only specific types of items, such as Electronics or Wallets.

### Remove Item Screen

When the user selects an item from the list, the app opens a separate screen that shows the item details. If the item has been returned to the owner, the user can click the Remove button to delete the advert from the database.

## Database

The app uses SQLite to store the lost and found item details. The database stores information such as:

- Item ID
- Lost or Found type
- Item name
- Phone number
- Description
- Date and time
- Location
- Category
- Image URI

## Technologies Used

- Android Studio
- Kotlin
- XML layouts
- SQLite database
- Android Emulator
- GitHub

## Project Files

The main files used in this project are:

- `MainActivity.kt`  
  Controls the home screen and opens the other screens.

- `AddItemActivity.kt`  
  Handles creating a new lost or found advert.

- `ShowItemsActivity.kt`  
  Displays all saved items and filters them by category.

- `RemoveItemActivity.kt`  
  Shows the selected item details and removes the item from the database.

- `DBHelper.kt`  
  Handles the SQLite database operations such as saving, reading, filtering, and deleting data.

- `activity_main.xml`  
  Layout for the main screen.

- `activity_add_item.xml`  
  Layout for creating a new advert.

- `activity_show_items.xml`  
  Layout for showing saved items.

- `activity_remove_item.xml`  
  Layout for removing a selected item.

## How to Run the App

1. Download or clone this repository.
2. Open the project in Android Studio.
3. Wait for Gradle sync to finish.
4. Select an Android emulator or connect an Android phone.
5. Click the Run button.
6. Use the app to create, view, filter, and remove lost or found items.

## Future Improvements

In the future, this app can be improved by adding user login, cloud database support, map location, search by item name, and push notifications. These features would make the app more useful in real situations.

## Author

Developed for SIT708 Task 7.1.
