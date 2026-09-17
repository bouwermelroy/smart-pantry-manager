package com.melroy.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.melroy.smartpantrymanager.adapter.RecipeAdapter;
import com.melroy.smartpantrymanager.database.DatabaseHelper;
import com.melroy.smartpantrymanager.model.PantryItem;
import com.melroy.smartpantrymanager.model.Recipe;
import com.melroy.smartpantrymanager.model.RecipeIngredient;
import com.melroy.smartpantrymanager.util.MatchingEngine;

import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerSuggestedRecipes;
    private TextView textEmptyState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        databaseHelper = new DatabaseHelper(this);
        recyclerSuggestedRecipes = findViewById(R.id.recyclerSuggestedRecipes);
        recyclerSuggestedRecipes.setLayoutManager(new LinearLayoutManager(this));
        textEmptyState = findViewById(R.id.textEmptyState);

        setupBottomNavigation();
    }

    // Configure the bottom navigation bar to switch between the three main screens.
    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.nav_recipes);

        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_pantry) {
                startActivity(new Intent(SuggestedRecipesActivity.this, PantryListActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_recipes) {
                return true;
            } else if (itemId == R.id.nav_settings) {
                startActivity(new Intent(SuggestedRecipesActivity.this, SettingsActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Re-run the matching logic every time this screen becomes visible,
        // so changes to the pantry are reflected immediately.
        loadSuggestedRecipes();
    }

    // Check every recipe against the current pantry and display only the ones
    // that pass the strict-matching rule.
    private void loadSuggestedRecipes() {
        List<PantryItem> pantryItems = databaseHelper.getAllPantryItems();
        List<Recipe> allRecipes = databaseHelper.getAllRecipes();
        List<Recipe> suggestedRecipes = new ArrayList<>();

        for (Recipe recipe : allRecipes) {
            List<RecipeIngredient> requiredIngredients =
                    databaseHelper.getIngredientsForRecipe(recipe.getId());

            if (MatchingEngine.canMakeRecipe(requiredIngredients, pantryItems)) {
                suggestedRecipes.add(recipe);
            }
        }

        if (suggestedRecipes.isEmpty()) {
            textEmptyState.setVisibility(TextView.VISIBLE);
            recyclerSuggestedRecipes.setVisibility(RecyclerView.GONE);
        } else {
            textEmptyState.setVisibility(TextView.GONE);
            recyclerSuggestedRecipes.setVisibility(RecyclerView.VISIBLE);

            RecipeAdapter recipeAdapter = new RecipeAdapter(suggestedRecipes);
            recyclerSuggestedRecipes.setAdapter(recipeAdapter);

            recipeAdapter.setOnRecipeClickListener(recipe -> {
                Intent intent = new Intent(SuggestedRecipesActivity.this, RecipeDetailActivity.class);
                intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_ID, recipe.getId());
                intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_NAME, recipe.getName());
                intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_INSTRUCTIONS, recipe.getInstructions());
                startActivity(intent);
            });
        }
    }
}