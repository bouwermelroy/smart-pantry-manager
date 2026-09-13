package com.melroy.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        loadPantryItems();

        Button buttonAddPantryItem = findViewById(R.id.buttonAddPantryItem);
        buttonAddPantryItem.setOnClickListener(v ->
                Toast.makeText(this, "Add screen coming soon", Toast.LENGTH_SHORT).show());
    }

    // Fetch all pantry items from the database and display them in the list.
    private void loadPantryItems() {
        List<PantryItem> pantryItems = databaseHelper.getAllPantryItems();
        pantryAdapter = new PantryAdapter(pantryItems);
        recyclerPantryList.setAdapter(pantryAdapter);
    }
}
