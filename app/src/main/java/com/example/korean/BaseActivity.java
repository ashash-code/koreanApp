package com.example.korean;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        applySettings();
        super.onCreate(savedInstanceState);
    }

    protected void applySettings() {
        SharedPreferences prefs = getSharedPreferences("KLearnPrefs", MODE_PRIVATE);
        
        // Dark Mode
        boolean isDarkMode = prefs.getBoolean("dark_mode", false);
        if (isDarkMode) {
            androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Font Size
        String fontSize = prefs.getString("font_size", "Medium");
        float scale = 1.0f;
        switch (fontSize) {
            case "Small":
                scale = 0.85f;
                break;
            case "Large":
                scale = 1.15f;
                break;
            case "Medium":
            default:
                scale = 1.0f;
                break;
        }
        
        Configuration configuration = getResources().getConfiguration();
        configuration.fontScale = scale;
        
        // High Contrast Support
        boolean isHighContrast = prefs.getBoolean("high_contrast", false);
        if (isHighContrast) {
            setTheme(R.style.Theme_HighContrast);
        }

        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        SharedPreferences prefs = newBase.getSharedPreferences("KLearnPrefs", MODE_PRIVATE);
        String fontSize = prefs.getString("font_size", "Medium");
        float scale = 1.0f;
        switch (fontSize) {
            case "Small": scale = 0.85f; break;
            case "Large": scale = 1.15f; break;
            default: scale = 1.0f; break;
        }

        Configuration configuration = newBase.getResources().getConfiguration();
        configuration.fontScale = scale;
        Context context = newBase.createConfigurationContext(configuration);
        super.attachBaseContext(context);
    }
}
