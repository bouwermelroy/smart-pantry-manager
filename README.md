# Smart Pantry Manager

Smart Pantry Manager is a Java-based Android application that was developed to help users manage the ingredients they already have available at home. The main purpose of the application is to help reduce unnecessary food waste by keeping track of pantry ingredients and suggesting recipes that can be prepared using those ingredients.

The application only recommends recipes where the user already has all the required ingredients available in sufficient quantities. This means that the user does not need to purchase additional ingredients in order to prepare a suggested recipe.

## Features

The application allows users to add ingredients to their pantry and record information such as the ingredient name, quantity, unit and expiry date. Existing ingredients can also be edited or deleted when necessary.

A collection of approximately 15-20 recipes is included within the application. Users are able to browse through these recipes and view the full list of required ingredients, together with the preparation steps for each recipe.

One of the main features of the application is the strict ingredient-matching system. When looking at the ingredients stored in the user's pantry, the application checks whether every ingredient required by a recipe is available and whether there is a sufficient quantity available. A recipe will only be shown as available if all of its requirements can be met.

The application also includes a settings screen that allows the user to toggle a preference for being notified about ingredients that are expiring soon.

## Database

The application makes use of SQLite through `SQLiteOpenHelper`. All of the information is stored locally on the Android device.

SQLite was selected because the application does not require an external database or internet connection in order to store and retrieve information. This allows the user's pantry information to remain available offline and stored directly on the device.

The use of SQLite also gives the application control over the structure of the database and the queries that are required when comparing pantry ingredients with recipe requirements. This is particularly important for the strict ingredient-matching system, because the application needs to determine whether the correct ingredients and quantities are available before suggesting a recipe.

The database approach also aligns with the persistent storage methods covered within the Mobile App Development 700 module.

## Setup and Run Instructions

1. Clone the repository: `https://github.com/bouwermelroy/smart-pantry-manager`
2. Open the project in **Android Studio**. Android Studio Giraffe or a later version is recommended.
3. Allow Gradle to complete the project sync.
4. Run the application using either an Android emulator or a physical Android device.
5. The minimum supported SDK is API 24, which corresponds to Android 7.0.
6. When the application is launched for the first time, the database is automatically populated with a starter collection of recipes. No additional manual database setup is required.

## Tech Stack

The application was developed using the following technologies:

- Java
- Android SDK
- XML layouts and Android Views
- SQLite using `SQLiteOpenHelper`
- RecyclerView and the Adapter pattern

## Author

Melroy Bouwer  
Mobile App Development 700  
Richfield Graduate Institute of Technology