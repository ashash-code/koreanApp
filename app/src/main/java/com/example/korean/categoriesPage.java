package com.example.korean;

import android.content.Intent;
import android.os.Bundle;

import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class categoriesPage extends BaseActivity {

    private DatabaseHelper dbHelper;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_categories_page);

        dbHelper = new DatabaseHelper(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail();
        }

        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(v -> finish());

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
        int[] progressData = dbHelper.getCategoryProgressDetailed(userEmail, category, "LESSON");
        int progress = progressData[0];
        int total = progressData[1];

        ProgressBar pb = findViewById(pbId);
        TextView tv = findViewById(tvId);

        if (total > 0) {
            int percentage = (int) (((float) progress / total) * 100);
            pb.setProgress(percentage);
            tv.setText(getString(R.string.progress_percentage_format, percentage));
        } else {
            pb.setProgress(0);
            tv.setText("0%");
        }
    }

    private void setupCategoryClicks() {
        findViewById(R.id.cardAlphabet).setOnClickListener(v -> startLesson("Alphabet"));
        findViewById(R.id.cardNumbers).setOnClickListener(v -> startLesson("Numbers"));
        findViewById(R.id.cardGreetings).setOnClickListener(v -> startLesson("Greetings"));
        findViewById(R.id.cardFood).setOnClickListener(v -> startLesson("Food"));
        findViewById(R.id.cardPlaces).setOnClickListener(v -> startLesson("Places"));
        findViewById(R.id.cardColors).setOnClickListener(v -> startLesson("Colors"));
        findViewById(R.id.cardFamily).setOnClickListener(v -> startLesson("Family"));
        findViewById(R.id.cardVerbs).setOnClickListener(v -> startLesson("Verbs"));
        findViewById(R.id.cardTime).setOnClickListener(v -> startLesson("Time"));
    }

    private void startLesson(String category) {
        Intent intent = new Intent(this, lessonPage.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }
}