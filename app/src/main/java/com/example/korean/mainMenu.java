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

public class mainMenu extends BaseActivity {

    private LinearLayout btnLearn, btnTranslate, btnQuiz, weeklyStreakContainer;
    private TextView drawerProfile, drawerSettings, tvStreakCount;
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
        weeklyStreakContainer = findViewById(R.id.weeklyStreakContainer);

        androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            androidx.core.graphics.Insets systemBars = insets.getInsets(androidx.core.view.WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        // Set switch state based on saved preference
        android.content.SharedPreferences prefs = getSharedPreferences("KLearnPrefs", MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean("dark_mode", false);
        switchDarkMode.setChecked(isDarkMode);

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed()) {
                prefs.edit().putBoolean("dark_mode", isChecked).apply();
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
        drawerSettings = findViewById(R.id.drawerSettings);

        // Hamburger logic
        ivHamburger.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        // Navigation Logic for Grid
        btnLearn.setOnClickListener(v -> navigateTo(categoriesPage.class));
        btnTranslate.setOnClickListener(v -> navigateTo(translatePage.class));
        btnQuiz.setOnClickListener(v -> navigateTo(lessonCatPage.class));

        // Navigation Logic for Drawer
        drawerProfile.setOnClickListener(v -> navigateTo(profilePage.class));
        drawerSettings.setOnClickListener(v -> navigateTo(settingsPage.class));

        loadActivityStats();
    }

    @Override
    protected void onResume() {
        super.onResume();
        
        // Sync Dark Mode switch state
        android.content.SharedPreferences prefs = getSharedPreferences("KLearnPrefs", MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean("dark_mode", false);
        if (switchDarkMode != null) {
            switchDarkMode.setChecked(isDarkMode);
        }

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

            updateWeeklyStreakUI(quizDates);

            // For CalendarView highlighting, we can't easily change individual cell backgrounds 
            // without a custom adapter or library like MaterialCalendarView.
            calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
                String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                if (quizDates.contains(selectedDate)) {
                    Toast.makeText(mainMenu.this, R.string.quiz_completed_on_this_day, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateWeeklyStreakUI(List<String> quizDates) {
        if (weeklyStreakContainer == null) return;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        String[] days = {"M", "T", "W", "T", "F", "S", "S"};

        for (int i = 0; i < 7; i++) {
            View dayView = weeklyStreakContainer.getChildAt(i);
            if (dayView == null) continue;

            TextView tvDayLabel = dayView.findViewById(R.id.tvDayLabel);
            ImageView ivStreakStatus = dayView.findViewById(R.id.ivStreakStatus);

            tvDayLabel.setText(days[i]);
            String dateStr = sdf.format(cal.getTime());

            if (quizDates.contains(dateStr)) {
                ivStreakStatus.setImageResource(R.drawable.ic_circle_filled);
                ivStreakStatus.setColorFilter(null); // Use original orange color
            } else {
                ivStreakStatus.setImageResource(R.drawable.ic_circle_outline);
                // Set tint based on theme
                ivStreakStatus.setColorFilter(getResources().getColor(android.R.color.darker_gray, getTheme()));
            }

            cal.add(Calendar.DATE, 1);
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
