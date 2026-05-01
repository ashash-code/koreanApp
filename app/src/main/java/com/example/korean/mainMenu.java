package com.example.korean;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class mainMenu extends AppCompatActivity {

    private LinearLayout btnLearn, btnTranslate, btnQuiz;
    private TextView drawerProfile, drawerFeedback, drawerSettings, tvStreakCount;
    private ImageView ivHamburger;
    private DrawerLayout drawerLayout;
    private SwitchMaterial switchDarkMode;
    private CalendarView calendarView;
    private DatabaseHelper db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView((int) R.layout.activity_main_menu);

        db = new DatabaseHelper(this);
        mAuth = FirebaseAuth.getInstance();

        // Initialize Views
        drawerLayout = findViewById(R.id.drawer_layout);
        ivHamburger = findViewById(R.id.ivHamburger);
        switchDarkMode = findViewById(R.id.switchDarkMode);
        calendarView = findViewById(R.id.calendarView);
        tvStreakCount = findViewById(R.id.tvStreakCount);

        androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            androidx.core.graphics.Insets systemBars = insets.getInsets(androidx.core.view.WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        // Set switch state based on current theme
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        switchDarkMode.setChecked(nightMode == AppCompatDelegate.MODE_NIGHT_YES);

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed()) { // Only trigger if the user actually clicked it
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        // Grid buttons
        btnLearn = findViewById(R.id.btnLearn);
        btnTranslate = findViewById(R.id.btnTranslate);
        btnQuiz = findViewById(R.id.btnQuiz);

        // Drawer items
        drawerProfile = findViewById(R.id.drawerProfile);
        drawerFeedback = findViewById(R.id.drawerFeedback);
        drawerSettings = findViewById(R.id.drawerSettings);

        // Hamburger logic
        ivHamburger.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        // Navigation Logic for Grid
        btnLearn.setOnClickListener(v -> navigateTo(categoriesPage.class));
        btnTranslate.setOnClickListener(v -> navigateTo(translatePage.class));
        btnQuiz.setOnClickListener(v -> navigateTo(lessonCatPage.class));

        // Navigation Logic for Drawer
        drawerProfile.setOnClickListener(v -> navigateTo(profilePage.class));
        drawerFeedback.setOnClickListener(v -> Toast.makeText(this, "Feedback feature coming soon!", Toast.LENGTH_SHORT).show());
        drawerSettings.setOnClickListener(v -> navigateTo(settingsPage.class));

        loadActivityStats();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadActivityStats();
    }

    private void loadActivityStats() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            List<String> quizDates = db.getQuizDates(email);
            
            // Calculate streak
            int streak = calculateStreak(quizDates);
            tvStreakCount.setText(getString(R.string.streak_format, streak));

            // For CalendarView highlighting, we can't easily change individual cell backgrounds 
            // without a custom adapter or library like MaterialCalendarView.
            // We can toast the result of a selected date if it was a quiz day.
            calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
                String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                if (quizDates.contains(selectedDate)) {
                    Toast.makeText(mainMenu.this, R.string.quiz_completed_on_this_day, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private int calculateStreak(List<String> quizDates) {
        if (quizDates == null || quizDates.isEmpty()) return 0;
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        java.util.Collections.sort(quizDates, java.util.Collections.reverseOrder());

        int streak = 0;
        Calendar cal = Calendar.getInstance();
        String today = sdf.format(cal.getTime());
        
        cal.add(Calendar.DATE, -1);
        String yesterday = sdf.format(cal.getTime());

        if (!quizDates.contains(today) && !quizDates.contains(yesterday)) {
            return 0;
        }

        cal = Calendar.getInstance();
        if (!quizDates.contains(today)) {
            cal.add(Calendar.DATE, -1);
        }

        while (quizDates.contains(sdf.format(cal.getTime()))) {
            streak++;
            cal.add(Calendar.DATE, -1);
        }

        return streak;
    }

    private void navigateTo(Class<?> targetActivity) {
        Intent intent = new Intent(mainMenu.this, targetActivity);
        startActivity(intent);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}
