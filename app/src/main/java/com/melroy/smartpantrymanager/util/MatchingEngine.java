package com.melroy.smartpantrymanager.util;

import com.melroy.smartpantrymanager.model.PantryItem;
import com.melroy.smartpantrymanager.model.RecipeIngredient;
import java.util.List;

public class MatchingEngine {

    /* Check whether the pantry contains every ingredient a recipe requires,
       in at least the required quantity. */
    public static boolean canMakeRecipe(List<RecipeIngredient> requiredIngredients,
                                         List<PantryItem> pantryItems) {
        for (RecipeIngredient required : requiredIngredients) {
            if (!pantryHasEnoughOf(required, pantryItems)) {
                return false;
            }
        }
        return true;
    }

    // Check whether the pantry has enough of one specific required ingredient.
    private static boolean pantryHasEnoughOf(RecipeIngredient required, List<PantryItem> pantryItems) {
        String normalizedRequiredName = normalize(required.getIngredientName());

        for (PantryItem pantryItem : pantryItems) {
            String normalizedPantryName = normalize(pantryItem.getName());

            if (normalizedPantryName.equals(normalizedRequiredName)
                    && pantryItem.getQuantity() >= required.getQuantityRequired()) {
                return true;
            }
        }
        return false;
    }

    // Normalize an ingredient name so minor differences like plurals or
    // capitalisation do not break matching (e.g. "Tomatoes" matches "tomato").
    private static String normalize(String ingredientName) {
        String name = ingredientName.trim().toLowerCase();

        if (name.endsWith("ies")) {
            name = name.substring(0, name.length() - 3) + "y";
        } else if (name.endsWith("es")) {
            name = name.substring(0, name.length() - 2);
        } else if (name.endsWith("s") && !name.endsWith("ss")) {
            name = name.substring(0, name.length() - 1);
        }

        return name;
    }
}