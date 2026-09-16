package com.melroy.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.melroy.smartpantrymanager.database.DatabaseHelper;
import com.melroy.smartpantrymanager.model.Recipe;
import com.melroy.smartpantrymanager.model.RecipeIngredient;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE_ID = "extra_recipe_id";
    public static final String EXTRA_RECIPE_NAME = "extra_recipe_name";
    public static final String EXTRA_RECIPE_INSTRUCTIONS = "extra_recipe_instructions";

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        databaseHelper = new DatabaseHelper(this);

        long recipeId = getIntent().getLongExtra(EXTRA_RECIPE_ID, -1);
        String recipeName = getIntent().getStringExtra(EXTRA_RECIPE_NAME);
        String instructions = getIntent().getStringExtra(EXTRA_RECIPE_INSTRUCTIONS);

        TextView textRecipeDetailName = findViewById(R.id.textRecipeDetailName);
        TextView textRecipeIngredients = findViewById(R.id.textRecipeIngredients);
        TextView textRecipeInstructions = findViewById(R.id.textRecipeInstructions);

        textRecipeDetailName.setText(recipeName);
        textRecipeInstructions.setText(instructions);

        List<RecipeIngredient> ingredients = databaseHelper.getIngredientsForRecipe(recipeId);
        textRecipeIngredients.setText(buildIngredientListText(ingredients));
    }

    // Combine the ingredient list into a single readable, line-per-ingredient string.
    private String buildIngredientListText(List<RecipeIngredient> ingredients) {
        StringBuilder builder = new StringBuilder();
        for (RecipeIngredient ingredient : ingredients) {
            builder.append("- ")
                    .append(ingredient.getQuantityRequired())
                    .append(" ")
                    .append(ingredient.getUnit())
                    .append(" ")
                    .append(ingredient.getIngredientName())
                    .append("\n");
        }
        return builder.toString().trim();
    }
}