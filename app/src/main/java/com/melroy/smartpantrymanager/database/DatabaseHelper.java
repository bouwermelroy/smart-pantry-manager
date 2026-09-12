package com.melroy.smartpantrymanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry_manager.db";
    private static final int DATABASE_VERSION = 1;

    // Table and column names for the pantry_items table.
    public static final String TABLE_PANTRY_ITEMS = "pantry_items";
    public static final String COLUMN_PANTRY_ID = "id";
    public static final String COLUMN_PANTRY_NAME = "name";
    public static final String COLUMN_PANTRY_QUANTITY = "quantity";
    public static final String COLUMN_PANTRY_UNIT = "unit";
    public static final String COLUMN_PANTRY_EXPIRY_DATE = "expiry_date";

    // Table and column names for the recipes table.
    public static final String TABLE_RECIPES = "recipes";
    public static final String COLUMN_RECIPE_ID = "id";
    public static final String COLUMN_RECIPE_NAME = "name";
    public static final String COLUMN_RECIPE_INSTRUCTIONS = "instructions";

    // Table and column names for the recipe_ingredients table.
    public static final String TABLE_RECIPE_INGREDIENTS = "recipe_ingredients";
    public static final String COLUMN_RI_ID = "id";
    public static final String COLUMN_RI_RECIPE_ID = "recipe_id";
    public static final String COLUMN_RI_INGREDIENT_NAME = "ingredient_name";
    public static final String COLUMN_RI_QUANTITY_REQUIRED = "quantity_required";
    public static final String COLUMN_RI_UNIT = "unit";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the pantry_items table.
        String createPantryItemsTable = "CREATE TABLE " + TABLE_PANTRY_ITEMS + " ("
                + COLUMN_PANTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PANTRY_NAME + " TEXT NOT NULL, "
                + COLUMN_PANTRY_QUANTITY + " REAL NOT NULL, "
                + COLUMN_PANTRY_UNIT + " TEXT, "
                + COLUMN_PANTRY_EXPIRY_DATE + " TEXT)";
        db.execSQL(createPantryItemsTable);

        // Create the recipes table.
        String createRecipesTable = "CREATE TABLE " + TABLE_RECIPES + " ("
                + COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_RECIPE_NAME + " TEXT NOT NULL, "
                + COLUMN_RECIPE_INSTRUCTIONS + " TEXT)";
        db.execSQL(createRecipesTable);

        // Create the recipe_ingredients table, linked to recipes via recipe_id.
        String createRecipeIngredientsTable = "CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + " ("
                + COLUMN_RI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_RI_RECIPE_ID + " INTEGER NOT NULL, "
                + COLUMN_RI_INGREDIENT_NAME + " TEXT NOT NULL, "
                + COLUMN_RI_QUANTITY_REQUIRED + " REAL NOT NULL, "
                + COLUMN_RI_UNIT + " TEXT, "
                + "FOREIGN KEY(" + COLUMN_RI_RECIPE_ID + ") REFERENCES "
                + TABLE_RECIPES + "(" + COLUMN_RECIPE_ID + "))";
        db.execSQL(createRecipeIngredientsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop and recreate all the tables if the database version changes.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY_ITEMS);
        onCreate(db);
    }
}
