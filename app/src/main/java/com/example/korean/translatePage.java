package com.example.korean;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class translatePage extends BaseActivity {

    private EditText etInputText;
    private TextView tvTranslatedText, tvRomanization;
    private Button btnTranslateAction;
    private ImageView ivBack;
    private ImageButton btnSwapLang, btnCopy, btnClear, btnMic;
    private TextView tvSourceLang, tvTargetLang, tvModelStatus;
    private ProgressBar pbTranslate, pbModelDownload;
    
    // Achievement UI elements
    private View achievementPopup;
    private View achievementGlow;
    private View vBlindingLight;
    private DatabaseHelper dbHelper;
    private String userEmail;
    private android.media.SoundPool soundPool;
    private int soundId;
    private boolean soundLoaded = false;

    private static final int SPEECH_REQUEST_CODE = 100;
    private boolean isEnglishToKorean = true;
    private Translator englishKoreanTranslator;
    private Translator koreanEnglishTranslator;
    private boolean isModelDownloaded = false;

    // Enhanced local dictionary for offline fallback
    private final Map<String, String> dictionary = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_page);

        // Initialize UI components
        etInputText = findViewById(R.id.etInputText);
        tvTranslatedText = findViewById(R.id.tvTranslatedText);
        tvRomanization = findViewById(R.id.tvRomanization);
        btnTranslateAction = findViewById(R.id.btnTranslateAction);
        ivBack = findViewById(R.id.ivBack);
        btnSwapLang = findViewById(R.id.btnSwapLang);
        btnCopy = findViewById(R.id.btnCopy);
        btnClear = findViewById(R.id.btnClear);
        btnMic = findViewById(R.id.btnMic);
        tvSourceLang = findViewById(R.id.tvSourceLang);
        tvTargetLang = findViewById(R.id.tvTargetLang);
        tvModelStatus = findViewById(R.id.tvModelStatus);
        pbTranslate = findViewById(R.id.pbTranslate);
        pbModelDownload = findViewById(R.id.pbModelDownload);

        // Initialize Achievement Views
        achievementPopup = findViewById(R.id.achievementPopup);
        achievementGlow = achievementPopup != null ? achievementPopup.findViewById(R.id.achievementGlow) : null;
        vBlindingLight = findViewById(R.id.vBlindingLight);
        dbHelper = new DatabaseHelper(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail();
        }

        // Initialize SoundPool
        android.media.AudioAttributes audioAttributes = new android.media.AudioAttributes.Builder()
                .setUsage(android.media.AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new android.media.SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();
        soundId = soundPool.load(this, R.raw.achieved, 1);
        soundPool.setOnLoadCompleteListener((pool, sampleId, status) -> {
            if (status == 0) soundLoaded = true;
        });

        initDictionary();
        setupTranslators();

        // Edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        ivBack.setOnClickListener(v -> finish());
        btnSwapLang.setOnClickListener(v -> swapLanguages());
        btnTranslateAction.setOnClickListener(v -> performTranslation());
        btnCopy.setOnClickListener(v -> copyToClipboard());
        btnMic.setOnClickListener(v -> startVoiceInput());
        btnClear.setOnClickListener(v -> {
            etInputText.setText("");
            tvTranslatedText.setText("");
            tvRomanization.setText("");
            tvRomanization.setVisibility(View.GONE);
            btnCopy.setVisibility(View.GONE);
        });

        etInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnClear.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void initDictionary() {
        // Essential Vocabulary
        dictionary.put("hello", "안녕하세요 (annyeonghaseyo)");
        dictionary.put("hi", "안녕 (annyeong)");
        dictionary.put("goodbye", "안녕히 가세요 (annyeonghi gaseyo)");
        dictionary.put("thank you", "감사합니다 (gamsahamnida)");
        dictionary.put("thanks", "고마워 (gomawo)");
        dictionary.put("sorry", "죄송합니다 (joesonghamnida)");
        dictionary.put("excuse me", "실례합니다 (sillyehamnida)");
        dictionary.put("please", "부탁합니다 (butakhamnida)");
        dictionary.put("yes", "네 (ne)");
        dictionary.put("no", "아니요 (aniyo)");
        dictionary.put("i don't want to", "싫어 (silheo)");
        dictionary.put("i dislike", "싫어 (silheo)");
        dictionary.put("don't like", "싫어 (silheo)");
        dictionary.put("water", "물 (mul)");
        dictionary.put("food", "음식 (eumsik)");
        dictionary.put("rice", "밥 (bap)");
        dictionary.put("bread", "빵 (ppang)");
        dictionary.put("meat", "고기 (gogi)");
        dictionary.put("coffee", "커피 (keopi)");
        dictionary.put("school", "학교 (hakgyo)");
        dictionary.put("teacher", "선생님 (seonsaengnim)");
        dictionary.put("student", "학생 (haksaeng)");
        dictionary.put("house", "집 (jip)");
        dictionary.put("friend", "친구 (chingu)");
        dictionary.put("family", "가족 (gajok)");
        dictionary.put("love", "사랑 (sarang)");
        dictionary.put("happy", "행복해 (haengbokhae)");
        dictionary.put("sad", "슬퍼 (seulpeo)");
        dictionary.put("good", "좋아 (joa)");
        dictionary.put("bad", "나빠 (nappa)");
        dictionary.put("today", "오늘 (oneul)");
        dictionary.put("tomorrow", "내일 (naeil)");
        dictionary.put("yesterday", "어제 (eoje)");
        dictionary.put("money", "돈 (don)");
        dictionary.put("time", "시간 (sigan)");
        dictionary.put("where", "어디 (eodi)");
        dictionary.put("who", "누구 (nugu)");
        dictionary.put("what", "무엇 (mueot)");
        dictionary.put("eat", "먹다 (meokda)");
        dictionary.put("drink", "마시다 (masida)");
        dictionary.put("go", "가다 (gada)");
        dictionary.put("come", "오다 (oda)");
        dictionary.put("sleep", "자다 (jada)");
    }

    private void setupTranslators() {
        TranslatorOptions enKoOptions = new TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.KOREAN)
                .build();
        englishKoreanTranslator = Translation.getClient(enKoOptions);

        TranslatorOptions koEnOptions = new TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.KOREAN)
                .setTargetLanguage(TranslateLanguage.ENGLISH)
                .build();
        koreanEnglishTranslator = Translation.getClient(koEnOptions);

        checkModelStatus();
    }

    private void checkModelStatus() {
        DownloadConditions conditions = new DownloadConditions.Builder()
                .build();

        tvModelStatus.setText(R.string.model_status_checking);
        pbModelDownload.setVisibility(View.VISIBLE);

        // Track both models
        englishKoreanTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(v -> {
                    Log.d("Translator", "EN-KO model ready");
                    updateModelStatusUI();
                })
                .addOnFailureListener(e -> {
                    pbModelDownload.setVisibility(View.GONE);
                    tvModelStatus.setText(R.string.model_status_error);
                    Log.e("Translator", "EN-KO model download failed", e);
                });

        koreanEnglishTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(v -> {
                    Log.d("Translator", "KO-EN model ready");
                    updateModelStatusUI();
                })
                .addOnFailureListener(e -> {
                    pbModelDownload.setVisibility(View.GONE);
                    tvModelStatus.setText(R.string.model_status_error);
                    Log.e("Translator", "KO-EN model download failed", e);
                });

        getLifecycle().addObserver(new androidx.lifecycle.DefaultLifecycleObserver() {
            @Override
            public void onDestroy(@NonNull androidx.lifecycle.LifecycleOwner owner) {
                englishKoreanTranslator.close();
                koreanEnglishTranslator.close();
            }
        });
    }

    private void updateModelStatusUI() {
        // If we reach here, at least one model is being handled. 
        // ML Kit handles multiple calls to downloadModelIfNeeded gracefully.
        isModelDownloaded = true;
        pbModelDownload.setVisibility(View.GONE);
        tvModelStatus.setText(R.string.model_status_ready);
        tvModelStatus.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));
    }

    private void swapLanguages() {
        isEnglishToKorean = !isEnglishToKorean;
        tvSourceLang.setText(isEnglishToKorean ? R.string.english : R.string.korean);
        tvTargetLang.setText(isEnglishToKorean ? R.string.korean : R.string.english);
        etInputText.setHint(isEnglishToKorean ? getString(R.string.translate_hint) : "한국어를 입력하세요...");
        
        etInputText.setText("");
        tvTranslatedText.setText("");
        tvRomanization.setText("");
        tvRomanization.setVisibility(View.GONE);
        btnCopy.setVisibility(View.GONE);
    }

    private void performTranslation() {
        String originalInput = etInputText.getText().toString().trim();
        if (originalInput.isEmpty()) {
            Toast.makeText(this, R.string.error_empty_input, Toast.LENGTH_SHORT).show();
            return;
        }

        // If in Korean -> English mode, handle romanized input (e.g., "annyeong" -> "안녕")
        String input = originalInput;
        if (!isEnglishToKorean) {
            input = RomanizationEngine.romanToHangeul(originalInput);
        }

        btnTranslateAction.setEnabled(false);
        pbTranslate.setVisibility(View.VISIBLE);
        tvTranslatedText.setText("");
        tvRomanization.setText("");
        tvRomanization.setVisibility(View.GONE);
        btnCopy.setVisibility(View.GONE);

        final Translator activeTranslator = isEnglishToKorean ? englishKoreanTranslator : koreanEnglishTranslator;

        if (!isModelDownloaded) {
            btnTranslateAction.setText(R.string.downloading_data);
            Toast.makeText(this, "Downloading language data (one-time setup). Please wait...", Toast.LENGTH_LONG).show();
        } else {
            btnTranslateAction.setText(R.string.translating_msg);
        }

        DownloadConditions conditions = new DownloadConditions.Builder()
                .build();

        String finalInput = input;
        activeTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(v -> {
                    isModelDownloaded = true;
                    activeTranslator.translate(finalInput)
                            .addOnSuccessListener(translatedText -> {
                                pbTranslate.setVisibility(View.GONE);
                                tvTranslatedText.setText(translatedText);
                                
                                // Show Romanization if the result is Korean
                                SharedPreferences prefs = getSharedPreferences("KLearnPrefs", MODE_PRIVATE);
                                boolean showRomanization = prefs.getBoolean("show_romanization", true);

                                if (isEnglishToKorean) {
                                    String roman = RomanizationEngine.hangeulToRoman(translatedText);
                                    tvRomanization.setText(roman);
                                    tvRomanization.setVisibility(showRomanization ? View.VISIBLE : View.GONE);
                                } else {
                                    // If result is English, we can show the Hangeul we translated FROM in the romanization field
                                    if (!finalInput.equals(originalInput)) {
                                        tvRomanization.setText(finalInput); // This is the Hangeul version of "shiro"
                                        tvRomanization.setVisibility(showRomanization ? View.VISIBLE : View.GONE);
                                    }
                                }
                                
                                btnCopy.setVisibility(View.VISIBLE);
                                btnTranslateAction.setEnabled(true);
                                btnTranslateAction.setText(R.string.translate_button);
                                
                                // Track achievement progress
                                checkPolyglotAchievement();
                            })
                            .addOnFailureListener(e -> {
                                Log.e("Translator", "Translation error", e);
                                pbTranslate.setVisibility(View.GONE);
                                handleTranslationFailure(finalInput);
                            });
                })
                .addOnFailureListener(e -> {
                    Log.e("Translator", "Model download error", e);
                    pbTranslate.setVisibility(View.GONE);
                    handleTranslationFailure(finalInput);
                });
    }

    private void handleTranslationFailure(String input) {
        String lowerInput = input.toLowerCase();
        String result = null;

        if (isEnglishToKorean) {
            result = dictionary.get(lowerInput);
            if (result == null) {
                for (Map.Entry<String, String> entry : dictionary.entrySet()) {
                    if (lowerInput.contains(entry.getKey())) {
                        result = entry.getValue();
                        break;
                    }
                }
            }
        } else {
            for (Map.Entry<String, String> entry : dictionary.entrySet()) {
                if (entry.getValue().contains(input)) {
                    result = entry.getKey();
                    break;
                }
            }
        }

        if (result != null) {
            if (isEnglishToKorean && result.contains("(") && result.contains(")")) {
                int start = result.indexOf("(");
                int end = result.indexOf(")");
                String korean = result.substring(0, start).trim();
                String roman = result.substring(start + 1, end).trim();
                
                tvTranslatedText.setText(korean);
                tvRomanization.setText(roman);
                tvRomanization.setVisibility(View.VISIBLE);
            } else {
                tvTranslatedText.setText(result);
                tvRomanization.setVisibility(View.GONE);
            }
            Toast.makeText(this, "Using offline vocabulary...", Toast.LENGTH_SHORT).show();
            
            // Track achievement progress
            checkPolyglotAchievement();
        } else {
            tvTranslatedText.setText(R.string.error_not_found);
            Toast.makeText(this, "Wait for model download or check connection.", Toast.LENGTH_LONG).show();
        }

        btnTranslateAction.setEnabled(true);
        btnTranslateAction.setText(R.string.translate_button);
    }

    private void copyToClipboard() {
        String text = tvTranslatedText.getText().toString();
        String roman = tvRomanization.getText().toString();
        if (!roman.isEmpty()) {
            text += " (" + roman + ")";
        }

        if (!text.isEmpty()) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Translation", text);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, R.string.msg_copied, Toast.LENGTH_SHORT).show();
        }
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, isEnglishToKorean ? "en-US" : "ko-KR");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...");
        try {
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(this, "Speech recognition not available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                etInputText.setText(result.get(0));
                performTranslation();
            }
        }
    }

    private void checkPolyglotAchievement() {
        if (userEmail == null) return;
        
        dbHelper.incrementTranslationCount(userEmail);
        int count = dbHelper.getTranslationCount(userEmail);
        
        if (count >= 5 && !dbHelper.hasAchievement(userEmail, "Polyglot")) {
            showAchievement("Polyglot");
        }
    }

    private void showAchievement(String name) {
        if (userEmail != null && !dbHelper.hasAchievement(userEmail, name)) {
            dbHelper.unlockAchievement(userEmail, name);
        }

        if (achievementPopup == null) return;

        // Update popup UI
        TextView tvName = achievementPopup.findViewById(R.id.tvAchievementName);
        ImageView ivIcon = achievementPopup.findViewById(R.id.ivBadgeIcon);
        if (tvName != null) tvName.setText(name);
        if (ivIcon != null) {
            ivIcon.setImageResource(R.drawable.ic_translate);
            ivIcon.setColorFilter(androidx.core.content.ContextCompat.getColor(this, R.color.blue_primary), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        achievementPopup.setOnClickListener(v -> {
            achievementPopup.setVisibility(View.GONE);
        });

        View ivCloseBtn = achievementPopup.findViewById(R.id.ivClose);
        if (ivCloseBtn != null) {
            ivCloseBtn.setOnClickListener(v -> {
                achievementPopup.setVisibility(View.GONE);
            });
        }

        triggerAchievementUI(() -> {
            achievementPopup.setVisibility(View.VISIBLE);
            android.view.animation.Animation slideUp = android.view.animation.AnimationUtils.loadAnimation(translatePage.this, R.anim.slide_up);
            achievementPopup.startAnimation(slideUp);

            if (achievementGlow != null) {
                android.view.animation.Animation pulse = android.view.animation.AnimationUtils.loadAnimation(translatePage.this, R.anim.pulse);
                achievementGlow.startAnimation(pulse);
            }
        });
    }

    private void triggerAchievementUI(Runnable onFinished) {
        // Play achievement sound reliably
        try {
            android.media.MediaPlayer mp = android.media.MediaPlayer.create(this, R.raw.achieved);
            if (mp != null) {
                mp.setOnCompletionListener(android.media.MediaPlayer::release);
                mp.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (vBlindingLight != null) {
            vBlindingLight.setVisibility(View.VISIBLE);
            android.view.animation.Animation blind = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.blinding_light);
            
            blind.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
                @Override
                public void onAnimationStart(android.view.animation.Animation animation) {}

                @Override
                public void onAnimationEnd(android.view.animation.Animation animation) {
                    vBlindingLight.setVisibility(View.GONE);
                    if (onFinished != null) onFinished.run();
                }

                @Override
                public void onAnimationRepeat(android.view.animation.Animation animation) {}
            });
            
            vBlindingLight.startAnimation(blind);
        }
    }

    private void triggerAchievementUI() {
        triggerAchievementUI(null);
    }

    @Override
    protected void onDestroy() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
        super.onDestroy();
    }
}
