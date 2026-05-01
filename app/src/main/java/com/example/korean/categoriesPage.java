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

public class categoriesPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_categories_page);

        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(v -> finish());

        setupCategoryClicks();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });
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