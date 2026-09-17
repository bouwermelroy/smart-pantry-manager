package com.melroy.smartpantrymanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "smart_pantry_prefs";
    private static final String KEY_EXPIRY_ALERTS_ENABLED = "expiry_alerts_enabled";

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        Switch switchExpiryAlerts = findViewById(R.id.switchExpiryAlerts);

        // Load the previously saved preference, defaulting to enabled.
        boolean alertsEnabled = sharedPreferences.getBoolean(KEY_EXPIRY_ALERTS_ENABLED, true);
        switchExpiryAlerts.setChecked(alertsEnabled);

        // Save the preference immediately whenever the switch is toggled.
        switchExpiryAlerts.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPreferences.edit()
                    .putBoolean(KEY_EXPIRY_ALERTS_ENABLED, isChecked)
                    .apply();
        });

        setupBottomNavigation();
    }

    // Configure the bottom navigation bar to switch between the three main screens.
    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.nav_settings);

        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_pantry) {
                startActivity(new Intent(SettingsActivity.this, PantryListActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_recipes) {
                startActivity(new Intent(SettingsActivity.this, SuggestedRecipesActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_settings) {
                return true;
            }
            return false;
        });
    }
}