package com.example.korean;

public class Lesson {
    private String category;
    private String korean;
    private String romanization;
    private String meaning;
    private int imageResId;

    public Lesson(String category, String korean, String romanization, String meaning, int imageResId) {
        this.category = category;
        this.korean = korean;
        this.romanization = romanization;
        this.meaning = meaning;
        this.imageResId = imageResId;
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
}
