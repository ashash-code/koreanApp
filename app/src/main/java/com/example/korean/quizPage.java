package com.example.korean;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class quizPage extends AppCompatActivity {

    private DatabaseHelper db;
    private FirebaseAuth mAuth;
    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;

    private TextView tvQuestion, tvQuestionNum, tvScore, tvFinalScore, tvSubtitle;
    
    // Text-only options
    private View layoutTextChoices;
    private Button[] optionButtons;
    
    // Image-based options
    private View layoutImageChoices;
    private MaterialCardView[] imageOptionCards;
    private ImageView[] imageOptionViews;
    private TextView[] imageOptionTexts;

    private Button btnNext, btnRetry, btnBackToCategories;
    private ProgressBar quizProgress;
    private View quizContainer, resultContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz_page);

        db = new DatabaseHelper(this);
        mAuth = FirebaseAuth.getInstance();

        // Initialize Views
        quizContainer = findViewById(R.id.quizContainer);
        resultContainer = findViewById(R.id.resultContainer);
        
        tvQuestion = findViewById(R.id.tvQuestionText);
        tvQuestionNum = findViewById(R.id.tvQuestionNum);
        tvScore = findViewById(R.id.tvScore);
        tvFinalScore = findViewById(R.id.tvFinalScore);
        tvSubtitle = findViewById(R.id.tvSubtitle);
        
        // Setup Text Choice Views
        layoutTextChoices = findViewById(R.id.layoutTextChoices);
        optionButtons = new Button[]{
                findViewById(R.id.btnChoice1),
                findViewById(R.id.btnChoice2),
                findViewById(R.id.btnChoice3),
                findViewById(R.id.btnChoice4)
        };
        
        // Setup Image Choice Views
        layoutImageChoices = findViewById(R.id.layoutImageChoices);
        imageOptionCards = new MaterialCardView[]{
                findViewById(R.id.cardChoice1),
                findViewById(R.id.cardChoice2),
                findViewById(R.id.cardChoice3),
                findViewById(R.id.cardChoice4)
        };
        imageOptionViews = new ImageView[]{
                findViewById(R.id.ivChoice1),
                findViewById(R.id.ivChoice2),
                findViewById(R.id.ivChoice3),
                findViewById(R.id.ivChoice4)
        };
        imageOptionTexts = new TextView[]{
                findViewById(R.id.tvChoice1),
                findViewById(R.id.tvChoice2),
                findViewById(R.id.tvChoice3),
                findViewById(R.id.tvChoice4)
        };
        
        btnNext = findViewById(R.id.btnNextQuestion);
        btnRetry = findViewById(R.id.btnRetry);
        btnBackToCategories = findViewById(R.id.btnBackToCategories);
        quizProgress = findViewById(R.id.quizProgress);

        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(v -> finish());

        btnRetry.setOnClickListener(v -> restartQuiz());
        btnBackToCategories.setOnClickListener(v -> finish());

        String category = getIntent().getStringExtra("category");
        if (category != null && tvSubtitle != null) {
            tvSubtitle.setText(category);
        }

        loadQuestions();
        showQuestion();

        btnNext.setOnClickListener(v -> {
            currentQuestionIndex++;
            if (currentQuestionIndex < questionList.size()) {
                showQuestion();
                btnNext.setVisibility(View.GONE);
            } else {
                finishQuiz();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void restartQuiz() {
        currentQuestionIndex = 0;
        score = 0;
        quizContainer.setVisibility(View.VISIBLE);
        resultContainer.setVisibility(View.GONE);
        Collections.shuffle(questionList);
        showQuestion();
    }

    private void loadQuestions() {
        String selectedCategory = getIntent().getStringExtra("category");
        if (selectedCategory == null) selectedCategory = "Alphabet"; // Fallback

        // Get questions from the library
        List<Question> allQuestions = QuestionLibrary.getQuestionsByCategory(selectedCategory);

        // Randomize the order
        Collections.shuffle(allQuestions);

        // Limit to 10 questions (or fewer if library doesn't have 10 yet)
        int limit = Math.min(allQuestions.size(), 10);
        questionList = new ArrayList<>(allQuestions.subList(0, limit));

        // If no questions found at all, add a fallback to prevent crash
        if (questionList.isEmpty()) {
            questionList.add(new Question("No questions available for " + selectedCategory, 
                new String[]{"Ok", "Back", "-", "-"}, 0, selectedCategory));
        }

        quizProgress.setMax(questionList.size());
    }

    private void showQuestion() {
        resetUI();
        Question currentQuestion = questionList.get(currentQuestionIndex);
        tvQuestion.setText(currentQuestion.getQuestionText());
        tvQuestionNum.setText(getString(R.string.question_format, currentQuestionIndex + 1, questionList.size()));
        tvScore.setText(getString(R.string.score_format, score));
        
        String[] options = currentQuestion.getOptions();

        if (currentQuestion.hasImages()) {
            layoutTextChoices.setVisibility(View.GONE);
            layoutImageChoices.setVisibility(View.VISIBLE);
            int[] images = currentQuestion.getOptionImages();
            for (int i = 0; i < imageOptionCards.length; i++) {
                imageOptionViews[i].setImageResource(images[i]);
                imageOptionTexts[i].setText(options[i]);
                int finalI = i;
                imageOptionCards[i].setOnClickListener(v -> checkAnswer(finalI));
            }
        } else {
            layoutTextChoices.setVisibility(View.VISIBLE);
            layoutImageChoices.setVisibility(View.GONE);
            for (int i = 0; i < optionButtons.length; i++) {
                optionButtons[i].setText(options[i]);
                int finalI = i;
                optionButtons[i].setOnClickListener(v -> checkAnswer(finalI));
            }
        }
        
        quizProgress.setProgress(currentQuestionIndex + 1);
        btnNext.setVisibility(View.GONE);
    }

    private void resetUI() {
        // Reset text buttons
        for (Button btn : optionButtons) {
            btn.setBackgroundColor(Color.TRANSPARENT);
            btn.setTextColor(Color.parseColor("#1976D2")); // Blue primary
            btn.setEnabled(true);
        }
        // Reset image cards
        for (MaterialCardView card : imageOptionCards) {
            card.setStrokeColor(Color.TRANSPARENT);
            card.setCardBackgroundColor(Color.WHITE);
            card.setEnabled(true);
        }
    }

    private void checkAnswer(int selectedIndex) {
        Question currentQuestion = questionList.get(currentQuestionIndex);
        boolean isCorrect = (selectedIndex == currentQuestion.getCorrectOptionIndex());
        
        if (isCorrect) {
            score++;
            highlightCorrect(selectedIndex, true);
        } else {
            highlightCorrect(selectedIndex, false);
            highlightCorrect(currentQuestion.getCorrectOptionIndex(), true);
        }

        disableOptions();
        
        tvScore.setText(getString(R.string.score_format, score));
        btnNext.setVisibility(View.VISIBLE);
        if (currentQuestionIndex == questionList.size() - 1) {
            btnNext.setText(R.string.finish);
        } else {
            btnNext.setText(R.string.next_question);
        }
    }

    private void highlightCorrect(int index, boolean correct) {
        int color = correct ? Color.parseColor("#4CAF50") : Color.parseColor("#F44336"); // Green : Red
        if (layoutTextChoices.getVisibility() == View.VISIBLE) {
            optionButtons[index].setBackgroundColor(color);
            optionButtons[index].setTextColor(Color.WHITE);
        } else {
            imageOptionCards[index].setStrokeColor(color);
            imageOptionCards[index].setCardBackgroundColor(correct ? Color.parseColor("#E8F5E9") : Color.parseColor("#FFEBEE"));
        }
    }

    private void disableOptions() {
        for (Button btn : optionButtons) btn.setEnabled(false);
        for (MaterialCardView card : imageOptionCards) card.setEnabled(false);
    }

    private void finishQuiz() {
        if (mAuth.getCurrentUser() != null) {
            String email = mAuth.getCurrentUser().getEmail();
            String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            db.saveQuizSession(email, today, score);
        }
        
        quizContainer.setVisibility(View.GONE);
        resultContainer.setVisibility(View.VISIBLE);
        tvFinalScore.setText(getString(R.string.final_score_format, score, questionList.size()));
        
        Toast.makeText(this, getString(R.string.quiz_finished_msg, score, questionList.size()), Toast.LENGTH_LONG).show();
    }
}
