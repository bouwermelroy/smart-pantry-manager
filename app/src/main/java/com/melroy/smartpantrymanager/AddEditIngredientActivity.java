package com.melroy.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.melroy.smartpantrymanager.database.DatabaseHelper;
import com.melroy.smartpantrymanager.model.PantryItem;

public class AddEditIngredientActivity extends AppCompatActivity {
    public static final String EXTRA_PANTRY_ITEM_ID = "extra_pantry_item_id";
    public static final String EXTRA_PANTRY_ITEM_NAME = "extra_pantry_item_name";
    public static final String EXTRA_PANTRY_ITEM_QUANTITY = "extra_pantry_item_quantity";
    public static final String EXTRA_PANTRY_ITEM_UNIT = "extra_pantry_item_unit";
    public static final String EXTRA_PANTRY_ITEM_EXPIRY = "extra_pantry_item_expiry";

    private DatabaseHelper databaseHelper;
    private EditText editIngredientName;
    private EditText editQuantity;
    private EditText editUnit;
    private EditText editExpiryDate;

    private long existingItemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        databaseHelper = new DatabaseHelper(this);

        editIngredientName = findViewById(R.id.editIngredientName);
        editQuantity = findViewById(R.id.editQuantity);
        editUnit = findViewById(R.id.editUnit);
        editExpiryDate = findViewById(R.id.editExpiryDate);

        // Check if we are editing an existing item, passed via the Intent.
        existingItemId = getIntent().getLongExtra(EXTRA_PANTRY_ITEM_ID, -1);
        if (existingItemId != -1) {
            editIngredientName.setText(getIntent().getStringExtra(EXTRA_PANTRY_ITEM_NAME));
            editQuantity.setText(String.valueOf(getIntent().getDoubleExtra(EXTRA_PANTRY_ITEM_QUANTITY, 0)));
            editUnit.setText(getIntent().getStringExtra(EXTRA_PANTRY_ITEM_UNIT));
            editExpiryDate.setText(getIntent().getStringExtra(EXTRA_PANTRY_ITEM_EXPIRY));
    }

        Button buttonSaveIngredient = findViewById(R.id.buttonSaveIngredient);
        buttonSaveIngredient.setOnClickListener(v -> saveIngredient());
}

    // Validate the form input and save the ingredient to the database.
    private void saveIngredient() {
        String name = editIngredientName.getText().toString().trim();
        String quantityText = editQuantity.getText().toString().trim();
        String unit = editUnit.getText().toString().trim();
        String expiryDate = editExpiryDate.getText().toString().trim();

        // CHeck that the required fields have been completed.
        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter an ingredient name.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (quantityText.isEmpty()) {
            Toast.makeText(this, "Please enter a quantity.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (unit.isEmpty()) {
            Toast.makeText(this, "Please enter a unit.", Toast.LENGTH_SHORT).show();
            return;
        }

        double quantity;
        try {
            quantity = Double.parseDouble(quantityText);
            if (quantity <= 0) {
                Toast.makeText(this, "Quantity must be greater than zero.", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number for quantity.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Store an empty exiry date as null rather than an empty string.
        String finalExpiryDate = expiryDate.isEmpty() ? null : expiryDate;

        if (existingItemId != -1) {
            PantryItem updatedItem = new PantryItem(existingItemId, name, quantity, unit, finalExpiryDate);
            databaseHelper.updatePantryItem(updatedItem);
            Toast.makeText(this, "Ingredient updated.", Toast.LENGTH_SHORT).show();
        } else {
            PantryItem newItem = new PantryItem(name, quantity, unit, finalExpiryDate);
            databaseHelper.insertPantryItem(newItem);
            Toast.makeText(this, "Ingredient added.", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}
