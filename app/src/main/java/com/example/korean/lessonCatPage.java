package com.example.korean;

import android.content.Intent;
import android.os.Bundle;

import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class lessonCatPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);

        ImageView ivBack = findViewById(R.id.ivBack);
        if (ivBack != null) {
            ivBack.setOnClickListener(v -> finish());
        }

        setupCategoryClicks();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });
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