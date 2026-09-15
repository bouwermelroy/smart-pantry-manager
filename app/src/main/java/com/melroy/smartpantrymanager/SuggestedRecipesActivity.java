package com.melroy.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
        }
    }
}