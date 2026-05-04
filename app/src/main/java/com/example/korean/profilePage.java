package com.example.korean;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.View;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profilePage extends BaseActivity {

    private TextView tvUserName, tvUserEmail, tvLessonsPercent, tvQuizzesPercent;
    private ProgressBar pbLessonsProgress, pbQuizzesProgress;
    private ImageView ivProfilePicture;
    private FirebaseAuth mAuth;
    private DatabaseHelper dbHelper;
    private String userEmail;

    private ActivityResultLauncher<String> mGetContent;

    private DrawerLayout drawerLayout;
    private ImageView ivHamburger, ivBackArrow;
    private TextView drawerHome, drawerSettings;
    private SwitchMaterial switchDarkMode;
    private androidx.core.widget.NestedScrollView profileScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_page);

        mAuth = FirebaseAuth.getInstance();
        dbHelper = new DatabaseHelper(this);

        // Initialize Drawer and Views
        drawerLayout = findViewById(R.id.drawer_layout);
        ivHamburger = findViewById(R.id.ivHamburger);
        ivBackArrow = findViewById(R.id.ivBackArrow);
        profileScrollView = findViewById(R.id.profileScrollView);
        switchDarkMode = findViewById(R.id.switchDarkMode);
        drawerHome = findViewById(R.id.drawerHome);
        drawerSettings = findViewById(R.id.drawerSettings);

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

        ivHamburger.setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

        ivBackArrow.setOnClickListener(v -> {
            boolean fromAchievement = getIntent().getBooleanExtra("FROM_ACHIEVEMENT", false);
            if (fromAchievement) {
                Intent intent = new Intent(this, categoriesPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            } else {
                finish();
            }
        });

        // Check if we came from an achievement trigger
        boolean fromAchievement = getIntent().getBooleanExtra("FROM_ACHIEVEMENT", false);
        if (fromAchievement) {
            ivHamburger.setVisibility(View.GONE);
            ivBackArrow.setVisibility(View.VISIBLE);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

        drawerHome.setOnClickListener(v -> navigateTo(mainMenu.class));
        drawerSettings.setOnClickListener(v -> navigateTo(settingsPage.class));

        tvUserName = findViewById(R.id.tvUserName);
        tvUserEmail = findViewById(R.id.tvUserEmail);
        tvLessonsPercent = findViewById(R.id.tvLessonsPercent);
        tvQuizzesPercent = findViewById(R.id.tvQuizzesPercent);
        pbLessonsProgress = findViewById(R.id.pbLessonsProgress);
        pbQuizzesProgress = findViewById(R.id.pbQuizzesProgress);
        ivProfilePicture = findViewById(R.id.ivProfilePicture);

        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        // Persist permission for the URI
                        getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        ivProfilePicture.setImageURI(uri);
                        dbHelper.updateProfileImage(userEmail, uri.toString());
                        Toast.makeText(this, "Profile picture updated", Toast.LENGTH_SHORT).show();
                    }
                });

        ivProfilePicture.setOnClickListener(v -> {
            mGetContent.launch("image/*");
        });

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String name = currentUser.getDisplayName();
            userEmail = currentUser.getEmail();

            // If name is null from Firebase, try to get it from local DB
            if (name == null || name.isEmpty()) {
                name = dbHelper.getFullName(userEmail); 
            }

            // Fallback: If name is still not found, use the part of email before @
            if (name == null || name.trim().isEmpty()) {
                if (userEmail != null && userEmail.contains("@")) {
                    name = userEmail.split("@")[0];
                } else {
                    name = "User";
                }
            }

            tvUserName.setText(name);
            if (userEmail != null) {
                tvUserEmail.setText(userEmail);
                
                // Load profile image
                String savedImageUri = dbHelper.getProfileImage(userEmail);
                if (savedImageUri != null && !savedImageUri.isEmpty()) {
                    try {
                        ivProfilePicture.setImageURI(Uri.parse(savedImageUri));
                    } catch (Exception e) {
                        Log.e("ProfilePage", "Error loading profile image", e);
                    }
                }

                // Sync check: update local SQLite if it has no name but Firebase does
                String localName = dbHelper.getFullName(userEmail);
                if ((localName == null || localName.isEmpty()) && currentUser.getDisplayName() != null) {
                    dbHelper.updateFullName(userEmail, currentUser.getDisplayName());
                }
            }

            // Update stats
            updateStats();
            
            checkAchievements();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });
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

        updateStats();
    }

    private void checkAchievements() {
        if (userEmail == null) return;
        
        handleAchievement("Artist", R.id.badgeColorsLayout, "SHOW_ARTIST_POP");
        handleAchievement("Scholar", R.id.badgeAlphabetLayout, "SHOW_SCHOLAR_POP");
        handleAchievement("Polite", R.id.badgeGreetingsLayout, "SHOW_POLITE_POP");
        handleAchievement("Mathematician", R.id.badgeNumbersLayout, "SHOW_MATH_POP");
        handleAchievement("Gourmet", R.id.badgeFoodLayout, "SHOW_GOURMET_POP");
        
        // Handling other badges
        handleAchievement("Learner", R.id.badgeLearnerLayout, "SHOW_LEARNER_POP");
        handleAchievement("Quizzer", R.id.badgeQuizzerLayout, "SHOW_QUIZZER_POP");
        handleAchievement("Polyglot", R.id.badgePolyglotLayout, "SHOW_POLYGLOT_POP");
        handleAchievement("Wayfarer", R.id.badgePlacesLayout, "SHOW_PLACES_POP");
        handleAchievement("Relative", R.id.badgeFamilyLayout, "SHOW_FAMILY_POP");
        handleAchievement("Active", R.id.badgeVerbsLayout, "SHOW_VERBS_POP");
        handleAchievement("Chronos", R.id.badgeTimeLayout, "SHOW_TIME_POP");
    }

    private void handleAchievement(String name, int layoutId, String extraKey) {
        if (dbHelper.hasAchievement(userEmail, name)) {
            View layout = findViewById(layoutId);
            if (layout != null) {
                layout.setAlpha(1.0f);
                if (getIntent().getBooleanExtra(extraKey, false)) {
                    // Jump and appear animation
                    Animation jumpPop = AnimationUtils.loadAnimation(this, R.anim.jump_pop);
                    layout.startAnimation(jumpPop);
                    
                    // Scroll to the achievement
                    layout.post(() -> {
                        int top = layout.getTop();
                        int parentTop = ((View)layout.getParent()).getTop();
                        profileScrollView.smoothScrollTo(0, top + parentTop - 100);
                    });

                    getIntent().removeExtra(extraKey);
                }
            }
        }
    }

    private void navigateTo(Class<?> targetActivity) {
        Intent intent = new Intent(this, targetActivity);
        startActivity(intent);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void updateStats() {
        if (userEmail == null) return;

        int totalCategories = 9; // Total number of categories in the app

        // Lessons Progress: Based on categories with 100% lesson progress
        int completedLessonCats = dbHelper.getCompletedLessonCategoriesCount(userEmail);
        int lessonProgressPercent = (int) ((completedLessonCats / (float) totalCategories) * 100);
        
        tvLessonsPercent.setText(getString(R.string.progress_percentage_format, lessonProgressPercent));
        pbLessonsProgress.setProgress(lessonProgressPercent);

        // Quizzes Progress: Based on how many categories have at least one quiz attempt
        int completedQuizCats = dbHelper.getCompletedQuizCategoriesCount(userEmail);
        int quizProgressPercent = (int) ((completedQuizCats / (float) totalCategories) * 100);

        tvQuizzesPercent.setText(getString(R.string.progress_percentage_format, quizProgressPercent));
        pbQuizzesProgress.setProgress(quizProgressPercent);
    }
}