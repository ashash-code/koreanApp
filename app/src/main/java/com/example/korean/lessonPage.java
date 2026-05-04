package com.example.korean;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Locale;

public class lessonPage extends BaseActivity {

    private TextView tvCategoryTitle, tvLessonProgress, tvKoreanPhrase, tvRomanizationPhrase, tvMeaningPhrase;
    private ImageView ivLessonImage, ivBack;
    private Button btnPrev, btnNext, btnMarkComplete;
    private FloatingActionButton btnPronounce;
    private ProgressBar pbLessonProgress;
    private View cvLessonImage, meaningDivider;
    private DatabaseHelper dbHelper;
    private String userEmail;
    private SoundPool soundPool;
    private int soundId;
    private boolean soundLoaded = false;
    private android.media.MediaPlayer mediaPlayer;

    private List<Lesson> lessonList;
    private int currentIndex = 0;
    private String category;
    private TextToSpeech tts;

    private View achievementPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_page);

        achievementPopup = findViewById(R.id.achievementPopup);

        // Initialize views
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
        cvLessonImage = findViewById(R.id.cvLessonImage);
        meaningDivider = findViewById(R.id.meaningDivider);

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
            } else {
                // We are at the last lesson and clicked "Finish"
                if (userEmail != null) {
                    dbHelper.updateCategoryProgress(userEmail, category, lessonList.size(), lessonList.size(), "LESSON");
                }

                String achievementName = null;
                String intentExtra = null;

                if ("Colors".equals(category)) {
                    achievementName = "Artist";
                    intentExtra = "SHOW_ARTIST_POP";
                } else if ("Alphabet".equals(category)) {
                    achievementName = "Scholar";
                    intentExtra = "SHOW_SCHOLAR_POP";
                } else if ("Greetings".equals(category)) {
                    achievementName = "Polite";
                    intentExtra = "SHOW_POLITE_POP";
                } else if ("Numbers".equals(category)) {
                    achievementName = "Mathematician";
                    intentExtra = "SHOW_MATH_POP";
                } else if ("Food".equals(category)) {
                    achievementName = "Gourmet";
                    intentExtra = "SHOW_GOURMET_POP";
                } else if ("Places".equals(category)) {
                    achievementName = "Wayfarer";
                    intentExtra = "SHOW_PLACES_POP";
                } else if ("Time".equals(category)) {
                    achievementName = "Chronos";
                    intentExtra = "SHOW_TIME_POP";
                } else if ("Family".equals(category)) {
                    achievementName = "Kinship";
                    intentExtra = "SHOW_FAMILY_POP";
                } else if ("Verbs".equals(category)) {
                    achievementName = "Active";
                    intentExtra = "SHOW_VERBS_POP";
                }

                if (achievementName != null && userEmail != null) {
                    dbHelper.unlockAchievement(userEmail, achievementName);
                    showAchievement(intentExtra);
                } else {
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
            if (lessonList != null && currentIndex < lessonList.size()) {
                playLessonAudio(lessonList.get(currentIndex));
            }
        });

        btnMarkComplete.setOnClickListener(v -> {
             if (userEmail != null) {
                 dbHelper.updateCategoryProgress(userEmail, category, currentIndex + 1, lessonList.size(), "LESSON");
                 Toast.makeText(this, "Progress Saved!", Toast.LENGTH_SHORT).show();
             }
        });

        updateUI();
        checkLearnerAchievement();
    }

    private void showAchievement(String intentExtra) {
        if (achievementPopup == null) {
            Intent intent = new Intent(this, mainMenu.class);
            intent.putExtra(intentExtra, true);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return;
        }

        // Play achievement sound
        try {
            MediaPlayer achievementPlayer = MediaPlayer.create(this, R.raw.achieved);
            if (achievementPlayer != null) {
                achievementPlayer.setOnCompletionListener(MediaPlayer::release);
                achievementPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Map intent extra back to achievement name and icon
        String achievementDisplayName = "Achievement Unlocked!";
        int iconRes = R.drawable.ic_learn;
        
        if ("SHOW_ARTIST_POP".equals(intentExtra)) { achievementDisplayName = "Artist"; iconRes = R.drawable.ic_colors; }
        else if ("SHOW_SCHOLAR_POP".equals(intentExtra)) { achievementDisplayName = "Scholar"; iconRes = R.drawable.ic_alphabet; }
        else if ("SHOW_POLITE_POP".equals(intentExtra)) { achievementDisplayName = "Polite"; iconRes = R.drawable.ic_greetings; }
        else if ("SHOW_MATH_POP".equals(intentExtra)) { achievementDisplayName = "Mathematician"; iconRes = R.drawable.ic_numbers; }
        else if ("SHOW_GOURMET_POP".equals(intentExtra)) { achievementDisplayName = "Gourmet"; iconRes = R.drawable.ic_food; }
        else if ("SHOW_PLACES_POP".equals(intentExtra)) { achievementDisplayName = "Wayfarer"; iconRes = R.drawable.ic_places; }
        else if ("SHOW_TIME_POP".equals(intentExtra)) { achievementDisplayName = "Chronos"; iconRes = R.drawable.ic_time; }
        else if ("SHOW_FAMILY_POP".equals(intentExtra)) { achievementDisplayName = "Kinship"; iconRes = R.drawable.ic_family; }
        else if ("SHOW_VERBS_POP".equals(intentExtra)) { achievementDisplayName = "Active"; iconRes = R.drawable.ic_verbs; }
        else if ("SHOW_LEARNER_POP".equals(intentExtra)) { achievementDisplayName = "Learner"; iconRes = R.drawable.ic_learn; }

        TextView tvName = achievementPopup.findViewById(R.id.tvAchievementName);
        ImageView ivIcon = achievementPopup.findViewById(R.id.ivBadgeIcon);
        if (tvName != null) tvName.setText(achievementDisplayName);
        if (ivIcon != null) ivIcon.setImageResource(iconRes);

        View ivClose = achievementPopup.findViewById(R.id.ivClose);
        if (ivClose != null) {
            ivClose.setOnClickListener(v -> {
                achievementPopup.setVisibility(View.GONE);
                Intent intent = new Intent(this, profilePage.class);
                intent.putExtra(intentExtra, true);
                intent.putExtra("FROM_ACHIEVEMENT", true);
                startActivity(intent);
            });
        }
        
        achievementPopup.setOnClickListener(v -> {
            achievementPopup.setVisibility(View.GONE);
        });

        achievementPopup.setVisibility(View.VISIBLE);
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        achievementPopup.startAnimation(slideUp);
    }

    private void checkLearnerAchievement() {
        if (userEmail == null) return;
        
        // Count how many categories are completed
        int completedCount = 0;
        String[] categories = {"Greetings", "Food", "Alphabet", "Numbers", "Family", "Verbs", "Places", "Time", "Colors"};
        for (String cat : categories) {
             if (dbHelper.getCategoryProgress(userEmail, cat, "LESSON") >= 100) {
                 completedCount++;
             }
        }

        if (completedCount >= 3) {
            if (!dbHelper.hasAchievement(userEmail, "Learner")) {
                dbHelper.unlockAchievement(userEmail, "Learner");
                showAchievement("SHOW_LEARNER_POP");
            }
        }
    }

    private void updateUI() {
        if (lessonList == null || lessonList.isEmpty()) return;

        Lesson currentLesson = lessonList.get(currentIndex);
        tvKoreanPhrase.setText(currentLesson.getKorean());
        tvRomanizationPhrase.setText(currentLesson.getRomanization());
        tvMeaningPhrase.setText(currentLesson.getMeaning());
        ivLessonImage.setImageResource(currentLesson.getImageResId());

        tvLessonProgress.setText((currentIndex + 1) + " / " + lessonList.size());
        int progress = (int) (((float) (currentIndex + 1) / lessonList.size()) * 100);
        pbLessonProgress.setProgress(progress);

        // Category specific UI adjustments
        if ("Alphabet".equals(category)) {
            tvKoreanPhrase.setTextSize(100);
            cvLessonImage.setVisibility(View.GONE);
            meaningDivider.setVisibility(View.VISIBLE);
            tvMeaningPhrase.setVisibility(View.VISIBLE);
            tvRomanizationPhrase.setVisibility(View.VISIBLE);
            tvMeaningPhrase.setTextSize(30);
            tvRomanizationPhrase.setTextSize(22);
        } else if ("Numbers".equals(category)) {
            // Numbers special layout: meaning (number) is big, korean and romanization are smaller below it
            tvKoreanPhrase.setTextSize(36);
            tvRomanizationPhrase.setTextSize(22);
            tvMeaningPhrase.setTextSize(80);
            tvMeaningPhrase.setTypeface(null, android.graphics.Typeface.BOLD);
            cvLessonImage.setVisibility(View.GONE);
            meaningDivider.setVisibility(View.VISIBLE);
            tvMeaningPhrase.setVisibility(View.VISIBLE);
            tvRomanizationPhrase.setVisibility(View.VISIBLE);

            // Swap positions conceptually by setting text
            tvMeaningPhrase.setText(currentLesson.getMeaning()); // Big number
            tvKoreanPhrase.setText(currentLesson.getKorean());
            tvRomanizationPhrase.setText(currentLesson.getRomanization());
        } else {
            tvKoreanPhrase.setTextSize(48);
            tvRomanizationPhrase.setTextSize(22);
            tvMeaningPhrase.setTextSize(24);
            tvMeaningPhrase.setTypeface(null, android.graphics.Typeface.ITALIC);
            cvLessonImage.setVisibility(View.VISIBLE);
            meaningDivider.setVisibility(View.VISIBLE);
            tvMeaningPhrase.setVisibility(View.VISIBLE);
            tvRomanizationPhrase.setVisibility(View.VISIBLE);

            // Zoom out images for specific categories
            if ("Food".equals(category) || "Greetings".equals(category) || "Places".equals(category) ||
                "Family".equals(category) || "Verbs".equals(category) || "Time".equals(category)) {
                ivLessonImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            } else {
                ivLessonImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        }

        btnPrev.setEnabled(currentIndex > 0);
        if (currentIndex == lessonList.size() - 1) {
            btnNext.setText("Finish");
        } else {
            btnNext.setText("Next");
        }
    }

    private void playLessonAudio(Lesson lesson) {
        // Release previous mediaPlayer if it exists
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mediaPlayer = null;
        }

        if (lesson.getAudioResId() != 0) {
            mediaPlayer = android.media.MediaPlayer.create(this, lesson.getAudioResId());
            
            if (mediaPlayer != null) {
                // Apply Volume Setting
                SharedPreferences prefs = getSharedPreferences("KLearnPrefs", MODE_PRIVATE);
                float volume = prefs.getInt("volume", 70) / 100f;
                mediaPlayer.setVolume(volume, volume);
                
                mediaPlayer.setOnCompletionListener(mp -> {
                    mp.release();
                    if (mediaPlayer == mp) {
                        mediaPlayer = null;
                    }
                });
                mediaPlayer.start();
            } else if (tts != null) {
                // Fallback to TTS if MediaPlayer failed
                String text = lesson.getKorean();
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        } else if (tts != null) {
            String text = lesson.getKorean();
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mediaPlayer = null;
        }
    }
}
