package com.melroy.smartpantrymanager.model;

public class RecipeIngredient {

    private long id;
    private long recipeId;
    private String ingredientName;
    private double quantityRequired;
    private String unit;

    // Create a recipe ingredient using an existing database id.
    public RecipeIngredient(long id, long recipeId, String ingredientName, double quantityRequired, String unit) {
        this.id = id;
        this.recipeId = recipeId;
        this.ingredientName = ingredientName;
        this.quantityRequired = quantityRequired;
        this.unit = unit;
    }

    // Create a new recipe ingredient before it has been saved to the database.
    public RecipeIngredient(long recipeId, String ingredientName, double quantityRequired, String unit) {
        this.recipeId = recipeId;
        this.ingredientName = ingredientName;
        this.quantityRequired = quantityRequired;
        this.unit = unit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public double getQuantityRequired() {
        return quantityRequired;
    }

    public void setQuantityRequired(double quantityRequired) {
        this.quantityRequired = quantityRequired;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}