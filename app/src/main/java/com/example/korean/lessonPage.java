package com.example.korean;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Locale;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.media.AudioAttributes;
import android.media.SoundPool;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class lessonPage extends AppCompatActivity {

    private TextView tvCategoryTitle, tvLessonProgress, tvKoreanPhrase, tvRomanizationPhrase, tvMeaningPhrase, tvLogo;
    private ImageView ivLessonImage, ivBack;
    private Button btnPrev, btnNext, btnMarkComplete;
    private com.google.android.material.floatingactionbutton.FloatingActionButton btnPronounce;
    private ProgressBar pbLessonProgress;
    private View achievementPopup;
    private View achievementGlow;
    private View vBlindingLight;
    private DatabaseHelper dbHelper;
    private String userEmail;
    private SoundPool soundPool;
    private int soundId;
    private boolean soundLoaded = false;

    private List<Lesson> lessonList;
    private int currentIndex = 0;
    private String category;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_page);

        // Initialize Views
        tvLogo = findViewById(R.id.tvLogo);
        tvCategoryTitle = findViewById(R.id.tvCategoryTitle);
        tvLessonProgress = findViewById(R.id.tvLessonProgress);
        tvKoreanPhrase = findViewById(R.id.tvKoreanPhrase);
        tvRomanizationPhrase = findViewById(R.id.tvRomanizationPhrase);
        tvMeaningPhrase = findViewById(R.id.tvMeaningPhrase);
        ivLessonImage = findViewById(R.id.ivLessonImage);
        ivBack = findViewById(R.id.ivBack);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        btnMarkComplete = findViewById(R.id.btnMarkComplete);
        btnPronounce = findViewById(R.id.btnPronounce);
        pbLessonProgress = findViewById(R.id.pbLessonProgress);
        achievementPopup = findViewById(R.id.achievementPopup);
        achievementGlow = achievementPopup.findViewById(R.id.achievementGlow);
        vBlindingLight = findViewById(R.id.vBlindingLight);
        dbHelper = new DatabaseHelper(this);

        // Initialize SoundPool
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();
        soundId = soundPool.load(this, R.raw.achieved, 1);
        soundPool.setOnLoadCompleteListener((pool, sampleId, status) -> {
            if (status == 0) soundLoaded = true;
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail();
        }

        // Get category from intent
        category = getIntent().getStringExtra("category");
        if (category == null) category = "Greetings";
        tvCategoryTitle.setText(category);

        // Load lessons
        lessonList = LessonLibrary.getLessonsByCategory(category);

        // Initialize TTS
        tts = new TextToSpeech(this, status -> {
            if (status != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.KOREAN);
            }
        });

        // Set listeners
        ivBack.setOnClickListener(v -> finish());
        
        btnNext.setOnClickListener(v -> {
            if (currentIndex < lessonList.size() - 1) {
                currentIndex++;
                updateUI();
                
                // Save progress to database
                if (userEmail != null) {
                    dbHelper.updateCategoryProgress(userEmail, category, currentIndex + 1, lessonList.size());
                }

                // If we just reached the last lesson, change button text to "Finish"
                if (currentIndex == lessonList.size() - 1) {
                    btnNext.setText("Finish");
                }
            } else {
                // We are at the last lesson and clicked "Finish"
                String achievementName = null;
                String intentExtra = null;
                int badgeIconRes = R.drawable.ic_colors;

                if ("Colors".equals(category)) {
                    achievementName = "Artist";
                    intentExtra = "SHOW_ARTIST_POP";
                } else if ("Alphabet".equals(category)) {
                    achievementName = "Scholar";
                    intentExtra = "SHOW_SCHOLAR_POP";
                    badgeIconRes = R.drawable.ic_alphabet;
                } else if ("Greetings".equals(category)) {
                    achievementName = "Polite";
                    intentExtra = "SHOW_POLITE_POP";
                    badgeIconRes = R.drawable.ic_greetings;
                } else if ("Numbers".equals(category)) {
                    achievementName = "Mathematician";
                    intentExtra = "SHOW_MATH_POP";
                    badgeIconRes = R.drawable.ic_numbers;
                } else if ("Food".equals(category)) {
                    achievementName = "Gourmet";
                    intentExtra = "SHOW_GOURMET_POP";
                    badgeIconRes = R.drawable.ic_food;
                } else if ("Places".equals(category)) {
                    achievementName = "Wayfarer";
                    intentExtra = "SHOW_PLACES_POP";
                    badgeIconRes = R.drawable.ic_places;
                } else if ("Family".equals(category)) {
                    achievementName = "Relative";
                    intentExtra = "SHOW_FAMILY_POP";
                    badgeIconRes = R.drawable.ic_family;
                } else if ("Verbs".equals(category)) {
                    achievementName = "Active";
                    intentExtra = "SHOW_VERBS_POP";
                    badgeIconRes = R.drawable.ic_verbs;
                } else if ("Time".equals(category)) {
                    achievementName = "Chronos";
                    intentExtra = "SHOW_TIME_POP";
                    badgeIconRes = R.drawable.ic_time;
                }

                if (achievementName != null) {
                    final String finalExtra = intentExtra;
                    
                    // Update popup UI for specific achievement
                    TextView tvName = achievementPopup.findViewById(R.id.tvAchievementName);
                    ImageView ivIcon = achievementPopup.findViewById(R.id.ivBadgeIcon);
                    if (tvName != null) tvName.setText(achievementName);
                    if (ivIcon != null) ivIcon.setImageResource(badgeIconRes);

                    achievementPopup.setOnClickListener(v2 -> {
                        achievementPopup.setVisibility(View.GONE);
                        android.content.Intent intent = new android.content.Intent(lessonPage.this, profilePage.class);
                        intent.putExtra(finalExtra, true);
                        startActivity(intent);
                        finish();
                    });

                    View ivCloseBtn = achievementPopup.findViewById(R.id.ivClose);
                    if (ivCloseBtn != null) {
                        ivCloseBtn.setOnClickListener(v2 -> {
                            achievementPopup.setVisibility(View.GONE);
                            android.content.Intent intent = new android.content.Intent(lessonPage.this, profilePage.class);
                            intent.putExtra(finalExtra, true);
                            startActivity(intent);
                            finish();
                        });
                    }

                    showAchievement(achievementName);
                } else {
                    Toast.makeText(this, "Category Completed!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        btnPrev.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                updateUI();
            }
        });

        btnPronounce.setOnClickListener(v -> {
            String text = lessonList.get(currentIndex).getKorean();
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        });

        btnMarkComplete.setOnClickListener(v -> {
            Toast.makeText(this, "Marked as learned!", Toast.LENGTH_SHORT).show();
        });

        updateUI();
    }

    private void showAchievement(String name) {
        // Unlock in database if not already there
        if (userEmail != null && !dbHelper.hasAchievement(userEmail, name)) {
            dbHelper.unlockAchievement(userEmail, name);
        }
        
        // Always trigger the celebration UI when the category is finished
        triggerAchievementUI();
    }

    private void triggerAchievementUI() {
        // Play Sound
        if (soundLoaded) {
            soundPool.play(soundId, 1f, 1f, 1, 0, 1f);
        }

        // Blinding Light Effect
        vBlindingLight.setVisibility(View.VISIBLE);
        Animation blind = AnimationUtils.loadAnimation(this, R.anim.blinding_light);
        vBlindingLight.startAnimation(blind);

        blind.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                vBlindingLight.setVisibility(View.GONE);
                
                // Show Achievement Popup
                achievementPopup.setVisibility(View.VISIBLE);
                Animation slideUp = AnimationUtils.loadAnimation(lessonPage.this, R.anim.slide_up);
                achievementPopup.startAnimation(slideUp);

                // Light/Glow animation
                if (achievementGlow != null) {
                    Animation pulse = AnimationUtils.loadAnimation(lessonPage.this, R.anim.pulse);
                    achievementGlow.startAnimation(pulse);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    private void updateUI() {
        if (lessonList.isEmpty()) return;

        Lesson currentLesson = lessonList.get(currentIndex);
        tvKoreanPhrase.setText(currentLesson.getKorean());
        tvRomanizationPhrase.setText(currentLesson.getRomanization());
        tvMeaningPhrase.setText(currentLesson.getMeaning());
        ivLessonImage.setImageResource(currentLesson.getImageResId());
        
        btnPrev.setEnabled(currentIndex > 0);
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
        super.onDestroy();
    }
}
