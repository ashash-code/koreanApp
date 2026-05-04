package com.example.korean;

public class Lesson {
    private String category;
    private String korean;
    private String romanization;
    private String meaning;
    private int imageResId;
    private int audioResId; // Resource ID for pronunciation audio

    public Lesson(String category, String korean, String romanization, String meaning, int imageResId) {
        this(category, korean, romanization, meaning, imageResId, 0);
    }

    public Lesson(String category, String korean, String romanization, String meaning, int imageResId, int audioResId) {
        this.category = category;
        this.korean = korean;
        this.romanization = romanization;
        this.meaning = meaning;
        this.imageResId = imageResId;
        this.audioResId = audioResId;
    }

    public String getCategory() {
        return category;
    }

    public String getKorean() {
        return korean;
    }

    public String getRomanization() {
        return romanization;
    }

    public String getMeaning() {
        return meaning;
    }

    public int getImageResId() {
        return imageResId;
    }

    public int getAudioResId() {
        return audioResId;
    }
}
