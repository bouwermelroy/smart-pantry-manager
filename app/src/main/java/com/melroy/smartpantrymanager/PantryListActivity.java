package com.melroy.smartpantrymanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.melroy.smartpantrymanager.adapter.PantryAdapter;
import com.melroy.smartpantrymanager.database.DatabaseHelper;
import com.melroy.smartpantrymanager.model.PantryItem;

import java.util.List;

public class PantryListActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerPantryList;
    private PantryAdapter pantryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_list);

        databaseHelper = new DatabaseHelper(this);
        recyclerPantryList = findViewById(R.id.recyclerPantryList);
        recyclerPantryList.setLayoutManager(new LinearLayoutManager(this));

        Button buttonAddPantryItem = findViewById(R.id.buttonAddPantryItem);
        buttonAddPantryItem.setOnClickListener(v -> {
            Intent intent = new Intent(PantryListActivity.this, AddEditIngredientActivity.class);
            startActivity(intent);
        });

        setupBottomNavigation();
    }

    // Configure the bottom navigation bar to switch between the three main screens.
    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.nav_pantry);

        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_pantry) {
                return true;
            } else if (itemId == R.id.nav_recipes) {
                startActivity(new Intent(PantryListActivity.this, SuggestedRecipesActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_settings) {
                startActivity(new Intent(PantryListActivity.this, SettingsActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPantryItems();
    }

    // Fetch all pantry items from the database and display them in the list.
    private void loadPantryItems() {
        List<PantryItem> pantryItems = databaseHelper.getAllPantryItems();
        pantryAdapter = new PantryAdapter(pantryItems);
        recyclerPantryList.setAdapter(pantryAdapter);

        pantryAdapter.setOnItemClickListener(item -> {
            Intent intent = new Intent(PantryListActivity.this, AddEditIngredientActivity.class);
            intent.putExtra(AddEditIngredientActivity.EXTRA_PANTRY_ITEM_ID, item.getId());
            intent.putExtra(AddEditIngredientActivity.EXTRA_PANTRY_ITEM_NAME, item.getName());
            intent.putExtra(AddEditIngredientActivity.EXTRA_PANTRY_ITEM_QUANTITY, item.getQuantity());
            intent.putExtra(AddEditIngredientActivity.EXTRA_PANTRY_ITEM_UNIT, item.getUnit());
            intent.putExtra(AddEditIngredientActivity.EXTRA_PANTRY_ITEM_EXPIRY, item.getExpiryDate());
            startActivity(intent);
        });

        pantryAdapter.setOnItemLongClickListener(item -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Ingredient")
                    .setMessage("Are you sure you want to delete " + item.getName() + "?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        databaseHelper.deletePantryItem(item.getId());
                        loadPantryItems();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }
}