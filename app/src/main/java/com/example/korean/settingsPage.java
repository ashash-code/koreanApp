package com.example.korean;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import java.util.concurrent.TimeUnit;

public class settingsPage extends BaseActivity {

    private SharedPreferences prefs;
    private static final String PREFS_NAME = "KLearnPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings_page);

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(v -> finish());

        // Dark Mode
        SwitchMaterial switchDarkMode = findViewById(R.id.switchDarkModeSettings);
        boolean isDarkMode = prefs.getBoolean("dark_mode", false);
        switchDarkMode.setChecked(isDarkMode);
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("dark_mode", isChecked).apply();
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        // Font Size
        Spinner spinnerFontSize = findViewById(R.id.spinnerFontSize);
        int savedFontSizePos = prefs.getInt("font_size_pos", 1); // Default to Medium (index 1)
        spinnerFontSize.setSelection(savedFontSizePos, false); // false to avoid triggering listener immediately
        spinnerFontSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int currentPos = prefs.getInt("font_size_pos", 1);
                if (position != currentPos) {
                    prefs.edit().putInt("font_size_pos", position).apply();
                    String fontSize = parent.getItemAtPosition(position).toString();
                    prefs.edit().putString("font_size", fontSize).apply();
                    recreate();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // High Contrast
        SwitchMaterial switchHighContrast = findViewById(R.id.switchHighContrast);
        switchHighContrast.setChecked(prefs.getBoolean("high_contrast", false));
        switchHighContrast.setOnCheckedChangeListener((v, isChecked) -> {
            prefs.edit().putBoolean("high_contrast", isChecked).apply();
            recreate();
        });

        // Volume
        SeekBar seekVolume = findViewById(R.id.seekPronunciationVolume);
        seekVolume.setProgress(prefs.getInt("volume", 70));
        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                prefs.edit().putInt("volume", progress).apply();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Auto Play
        SwitchMaterial switchAutoPlay = findViewById(R.id.switchAutoPlay);
        switchAutoPlay.setChecked(prefs.getBoolean("auto_play", false));
        switchAutoPlay.setOnCheckedChangeListener((v, isChecked) -> 
            prefs.edit().putBoolean("auto_play", isChecked).apply());

        // Reminders
        SwitchMaterial switchReminders = findViewById(R.id.switchReminders);
        switchReminders.setChecked(prefs.getBoolean("reminders", true));
        switchReminders.setOnCheckedChangeListener((v, isChecked) -> {
            prefs.edit().putBoolean("reminders", isChecked).apply();
            if (isChecked) {
                scheduleDailyReminder();
            } else {
                cancelDailyReminder();
            }
        });

        // Romanization
        SwitchMaterial switchRomanization = findViewById(R.id.switchRomanization);
        switchRomanization.setChecked(prefs.getBoolean("show_romanization", true));
        switchRomanization.setOnCheckedChangeListener((v, isChecked) -> 
            prefs.edit().putBoolean("show_romanization", isChecked).apply());

        // Shuffle
        SwitchMaterial switchShuffle = findViewById(R.id.switchShuffle);
        switchShuffle.setChecked(prefs.getBoolean("shuffle_quizzes", false));
        switchShuffle.setOnCheckedChangeListener((v, isChecked) -> 
            prefs.edit().putBoolean("shuffle_quizzes", isChecked).apply());

        Button btnLogout = findViewById(R.id.btnLogout);

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

    private void scheduleDailyReminder() {
        PeriodicWorkRequest reminderRequest = new PeriodicWorkRequest.Builder(DailyReminderWorker.class, 24, TimeUnit.HOURS)
                .addTag("daily_reminder")
                .build();
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("daily_reminder", androidx.work.ExistingPeriodicWorkPolicy.KEEP, reminderRequest);
    }

    private void cancelDailyReminder() {
        WorkManager.getInstance(this).cancelAllWorkByTag("daily_reminder");
    }
}
