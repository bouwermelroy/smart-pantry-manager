package com.melroy.smartpantrymanager.model;

public class Recipe {

    private long id;
    private String name;
    private String instructions;

    // Create a recipe using an existing database id.
    public Recipe(long id, String name, String instructions) {
        this.id = id;
        this.name = name;
        this.instructions = instructions;
    }

    // Create a new recipe before it has been saved to the database.
    public Recipe(String name, String instructions) {
        this.name = name;
        this.instructions = instructions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
