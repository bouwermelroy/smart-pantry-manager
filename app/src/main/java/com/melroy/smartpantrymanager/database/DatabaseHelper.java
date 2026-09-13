package com.melroy.smartpantrymanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import com.melroy.smartpantrymanager.model.PantryItem;
import java.util.ArrayList;
import java.util.List;

import com.melroy.smartpantrymanager.model.PantryItem;

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
        seedRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop and recreate all the tables if the database version changes.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY_ITEMS);
        onCreate(db);
    }

    // Insert a starter set of South African recipes and their ingredients into the database.
    private void seedRecipes(SQLiteDatabase db) {
        insertRecipe(db, "Pap and Chakalaka",
                "Boil the mielie meal into a stiff pap and serve with a spiced chakalaka relish.",
                new String[]{"mielie mean", "tomato", "onion", "carrot"},
                new double[]{250, 3, 1, 2},
                new String[]{"g", "count", "count", "count"});

        insertRecipe(db, "Boerewors Rolls",
                "Grill the boerewors and serve in a bread roll with fried onions.",
                new String[]{"boerewors", "bread roll", "onion", "tomato sauce"},
                new double[]{300, 2, 1, 2},
                new String[]{"g", "count", "count", "tbsp"});

        insertRecipe(db, "Bobotie",
                "Fry the mince with onion and spices, top with an egg custard, and bake.",
                new String[]{"beef mince", "onion", "eggs", "milk", "bread"},
                new double[]{500, 1, 2, 200, 1},
                new String[]{"g", "count", "count", "ml", "slice"});

        insertRecipe(db, "Vetkoek with Mince",
                "Fry the dough balls until golden and fill with a savoury mince mixture.",
                new String[]{"flour", "beef minced", "onion", "eggs"},
                new double[]{300, 250, 1, 1},
                new String[]{"g", "g", "count", "count"});

        insertRecipe(db, "Chicken Potjie",
                "Slow-cook the chicken with potato, carrot, and onion in one pot.",
                new String[]{"chicken", "potato", "carrot", "onion"},
                new double[]{500, 4, 2, 1},
                new String[]{"g", "count", "count", "count"});

        insertRecipe(db, "Braai Broodjies",
                "Butter the bread, fill with cheese and tomato, and grill over the coals.",
                new String[]{"bread", "cheese", "tomato", "butter"},
                new double[]{4, 100, 2, 2},
                new String[]{"slice", "g", "count", "tbsp"});

        insertRecipe(db, "Boerewors and Pap",
                "Grill the boerewors and serve alongside a stiff mielie pap and tomato relish.",
                new String[]{"boerewors", "mielie meal", "tomato", "onion"},
                new double[]{300, 250, 2, 1},
                new String[]{"g", "g", "count", "count"});

        insertRecipe(db, "Beef Stew",
                "Brown the beef, then simmer with potato, carrot, and onion until tender.",
                new String[]{"beef", "potato", "carrot", "onion"},
                new double[]{500, 3, 2, 1},
                new String[]{"g", "count", "count", "count"});

        insertRecipe(db, "Chakalaka Beans on Toast",
                "Heat the beans with chakalaka spices and serve over toasted bread.",
                new String[]{"baked beans", "tomato", "onion", "bread"},
                new double[]{400, 2, 1, 2},
                new String[]{"g", "count", "count", "slice"});

        insertRecipe(db, "Milk Tart",
                "Bake a pastry base, fill with a milk and egg custard, and dust with cinnamon.",
                new String[]{"flour", "milk", "eggs", "sugar"},
                new double[]{200, 500, 3, 100},
                new String[]{"g", "ml", "count", "g"});

        insertRecipe(db, "Chicken and Rice",
                "Fry the chicken with onion, then simmer with rice until cooked through.",
                new String[]{"chicken", "rice", "onion", "carrot"},
                new double[]{400, 250, 1, 1},
                new String[]{"g", "g", "count", "count"});

        insertRecipe(db, "Egg and Tomato Sandwich",
                "Boil the eggs, slice with tomato, and layer between buttered bread.",
                new String[]{"eggs", "tomato", "bread", "butter"},
                new double[]{2, 1, 2, 1},
                new String[]{"count", "count", "slice", "tbsp"});

        insertRecipe(db, "Samp and beans",
                "Boil the samp and beans until soft, and season to taste.",
                new String[]{"samp", "sugar beans", "onion"},
                new double[]{300, 2, 1},
                new String[]{"g", "g", "count"});

        insertRecipe(db, "Tomato Bredie",
                "Simmer lamb with tomato, onion, and potato until the meat is tender.",
                new String[]{"lamb", "tomato", "onion", "potato"},
                new double[]{500, 4, 1, 3},
                new String[]{"g", "count", "count", "count"});

        insertRecipe(db, "Cheese and Tomato Vetkoek",
                "Fry the dough balls until golden and fill with cheese and sliced tomato.",
                new String[]{"flour", "cheese", "tomato"},
                new double[]{300, 100, 2},
                new String[]{"g", "g", "count"});

        insertRecipe(db, "Butternut and Onion Soup",
                "Simmer the butternut and onion in stock, then blend until smooth.",
                new String[]{"butternut", "onion", "vegetable stock"},
                new double[]{1, 1, 500},
                new String[]{"count", "count", "ml"});
    }

    // Helper method that inserts one recipe and all of its ingredients.
    private void insertRecipe(SQLiteDatabase db, String name, String instructions, String[] ingredientNames,
                              double[] quantities, String[] units) {
        ContentValues recipeValues = new ContentValues();
        recipeValues.put(COLUMN_RECIPE_NAME, name);
        recipeValues.put(COLUMN_RECIPE_INSTRUCTIONS, instructions);
        long recipeId = db.insert(TABLE_RECIPES, null, recipeValues);

        for (int i = 0; i < ingredientNames.length; i++) {
            ContentValues ingredientValues = new ContentValues();
            ingredientValues.put(COLUMN_RI_RECIPE_ID, recipeId);
            ingredientValues.put(COLUMN_RI_INGREDIENT_NAME, ingredientNames[i]);
            ingredientValues.put(COLUMN_RI_QUANTITY_REQUIRED, quantities[i]);
            ingredientValues.put(COLUMN_RI_UNIT, units[i]);
            db.insert(TABLE_RECIPE_INGREDIENTS, null, ingredientValues);
        }
    }

    // Insert a new pantry item and return its generated id.
    public long insertPantryItem(PantryItem item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PANTRY_NAME, item.getName());
        values.put(COLUMN_PANTRY_QUANTITY, item.getQuantity());
        values.put(COLUMN_PANTRY_UNIT, item.getUnit());
        values.put(COLUMN_PANTRY_EXPIRY_DATE, item.getExpiryDate());
        return db.insert(TABLE_PANTRY_ITEMS, null, values);
    }

    // Return every pantry item currently stored, ordered by name.
    public List<PantryItem> getAllPantryItems() {
            List<PantryItem> items = new ArrayList<>();
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.query(TABLE_PANTRY_ITEMS, null, null, null,
                    null, null, COLUMN_PANTRY_NAME + " ASC");

            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_PANTRY_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PANTRY_NAME));
                double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PANTRY_QUANTITY));
                String unit = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PANTRY_UNIT));
                String expiryDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PANTRY_EXPIRY_DATE));
                items.add(new PantryItem(id, name, quantity, unit, expiryDate));
            }
            cursor.close();
            return items;
    }

    // Update an existing pantry item, matched by its id.
    public int updatePantryItem(PantryItem item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PANTRY_NAME, item.getName());
        values.put(COLUMN_PANTRY_QUANTITY, item.getQuantity());
        values.put(COLUMN_PANTRY_UNIT, item.getUnit());
        values.put(COLUMN_PANTRY_EXPIRY_DATE, item.getExpiryDate());
        return db.update(TABLE_PANTRY_ITEMS, values, COLUMN_PANTRY_ID + " = ?",
                new String[]{String.valueOf(item.getId())});
    }

    // Delete a pantry item by its id.
    public void deletePantryItem(long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_PANTRY_ITEMS, COLUMN_PANTRY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
}