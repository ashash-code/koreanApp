package com.example.korean;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;

public class settingsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings_page);

        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(v -> finish());

        SwitchMaterial switchDarkMode = findViewById(R.id.switchDarkModeSettings);
        switchDarkMode.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        // Other Settings Logic
        Spinner spinnerFontSize = findViewById(R.id.spinnerFontSize);
        SwitchMaterial switchHighContrast = findViewById(R.id.switchHighContrast);
        SeekBar seekVolume = findViewById(R.id.seekPronunciationVolume);
        SwitchMaterial switchAutoPlay = findViewById(R.id.switchAutoPlay);
        SwitchMaterial switchReminders = findViewById(R.id.switchReminders);
        SwitchMaterial switchRomanization = findViewById(R.id.switchRomanization);
        SwitchMaterial switchShuffle = findViewById(R.id.switchShuffle);

        // Simple Toast feedback for demonstration of functionality
        switchHighContrast.setOnCheckedChangeListener((v, isChecked) -> 
            Toast.makeText(this, "High Contrast: " + isChecked, Toast.LENGTH_SHORT).show());
        
        switchAutoPlay.setOnCheckedChangeListener((v, isChecked) -> 
            Toast.makeText(this, "Auto-play: " + isChecked, Toast.LENGTH_SHORT).show());

        switchRomanization.setOnCheckedChangeListener((v, isChecked) -> 
            Toast.makeText(this, "Romanization: " + isChecked, Toast.LENGTH_SHORT).show());

        Button btnLogout = findViewById(R.id.btnLogout);
        Button btnFeedback = findViewById(R.id.btnFeedback);

        btnFeedback.setOnClickListener(v -> {
            Toast.makeText(this, "Feedback feature coming soon", Toast.LENGTH_SHORT).show();
        });

        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(settingsPage.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
