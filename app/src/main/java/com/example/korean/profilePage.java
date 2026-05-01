package com.example.korean;

import android.os.Bundle;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profilePage extends AppCompatActivity {

    private TextView tvUserName, tvUserEmail, tvProgressPercent;
    private ProgressBar pbOverallProgress;
    private FirebaseAuth mAuth;
    private DatabaseHelper dbHelper;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_page);

        mAuth = FirebaseAuth.getInstance();

        tvUserName = findViewById(R.id.tvUserName);
        tvUserEmail = findViewById(R.id.tvUserEmail);
        tvProgressPercent = findViewById(R.id.tvProgressPercent);
        pbOverallProgress = findViewById(R.id.pbOverallProgress);
        dbHelper = new DatabaseHelper(this);
        
        ImageView ivBack = findViewById(R.id.ivBack);

        ivBack.setOnClickListener(v -> finish());

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String name = currentUser.getDisplayName();
            userEmail = currentUser.getEmail();

            // If name is null from Firebase, try to get it from local DB
            if (name == null || name.isEmpty()) {
                name = dbHelper.getFullName(userEmail); 
            }

            if (name != null && !name.isEmpty()) {
                tvUserName.setText(name);
            }
            if (userEmail != null && !userEmail.isEmpty()) {
                tvUserEmail.setText(userEmail);
            }

            // Fetch progress from DB
            int[] progressData = dbHelper.getProgress(userEmail);
            updateStats(progressData[0], progressData[1]);
            
            checkAchievements();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });
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
                    Animation popIn = AnimationUtils.loadAnimation(this, R.anim.pop_in);
                    layout.startAnimation(popIn);
                    getIntent().removeExtra(extraKey);
                }
            }
        }
    }

    private void updateStats(int lessons, int categories) {
        // Example progress calculation (out of 50 lessons)
        int progress = (int) ((lessons / 50.0) * 100);
        tvProgressPercent.setText(getString(R.string.progress_percentage_format, progress));
        pbOverallProgress.setProgress(progress);
    }
}