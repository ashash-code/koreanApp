package com.example.korean;

import android.content.Intent;
import android.os.Bundle;

import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class lessonCatPage extends BaseActivity {

    private DatabaseHelper dbHelper;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);

        dbHelper = new DatabaseHelper(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail();
        }

        ImageView ivBack = findViewById(R.id.ivBack);
        if (ivBack != null) {
            ivBack.setOnClickListener(v -> finish());
        }

        setupCategoryClicks();
        updateAllProgress();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAllProgress();
    }

    private void updateAllProgress() {
        if (userEmail == null) return;

        updateCategoryUI("Alphabet", R.id.pbAlphabet, R.id.tvAlphabetPercent);
        updateCategoryUI("Numbers", R.id.pbNumbers, R.id.tvNumbersPercent);
        updateCategoryUI("Greetings", R.id.pbGreetings, R.id.tvGreetingsPercent);
        updateCategoryUI("Food", R.id.pbFood, R.id.tvFoodPercent);
        updateCategoryUI("Places", R.id.pbPlaces, R.id.tvPlacesPercent);
        updateCategoryUI("Colors", R.id.pbColors, R.id.tvColorsPercent);
        updateCategoryUI("Family", R.id.pbFamily, R.id.tvFamilyPercent);
        updateCategoryUI("Verbs", R.id.pbVerbs, R.id.tvVerbsPercent);
        updateCategoryUI("Time", R.id.pbTime, R.id.tvTimePercent);
    }

    private void updateCategoryUI(String category, int pbId, int tvId) {
        // Fetch QUIZ type progress instead of LESSON
        int[] progressData = dbHelper.getCategoryProgressDetailed(userEmail, category, "QUIZ");
        int progress = progressData[0];
        int total = progressData[1];

        ProgressBar pb = findViewById(pbId);
        TextView tv = findViewById(tvId);

        if (pb != null && tv != null) {
            if (total > 0) {
                int percentage = (int) (((float) progress / total) * 100);
                pb.setProgress(percentage);
                tv.setText(percentage + "%");
            } else {
                pb.setProgress(0);
                tv.setText("0%");
            }
        }
    }

    private void setupCategoryClicks() {
        findViewById(R.id.cardAlphabet).setOnClickListener(v -> startQuiz("Alphabet"));
        findViewById(R.id.cardNumbers).setOnClickListener(v -> startQuiz("Numbers"));
        findViewById(R.id.cardGreetings).setOnClickListener(v -> startQuiz("Greetings"));
        findViewById(R.id.cardFood).setOnClickListener(v -> startQuiz("Food"));
        findViewById(R.id.cardPlaces).setOnClickListener(v -> startQuiz("Places"));
        findViewById(R.id.cardColors).setOnClickListener(v -> startQuiz("Colors"));
        findViewById(R.id.cardFamily).setOnClickListener(v -> startQuiz("Family"));
        findViewById(R.id.cardVerbs).setOnClickListener(v -> startQuiz("Verbs"));
        findViewById(R.id.cardTime).setOnClickListener(v -> startQuiz("Time"));
    }

    private void startQuiz(String category) {
        Intent intent = new Intent(this, quizPage.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }
}