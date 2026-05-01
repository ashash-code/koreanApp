package com.example.korean;

public class Question {
    private String questionText;
    private String[] options;
    private int[] optionImages; // Array of drawable resource IDs
    private int correctOptionIndex;
    private String category;

    // Constructor for text-only questions
    public Question(String questionText, String[] options, int correctOptionIndex, String category) {
        this.questionText = questionText;
        this.options = options;
        this.optionImages = null;
        this.correctOptionIndex = correctOptionIndex;
        this.category = category;
    }

    // Constructor for image questions
    public Question(String questionText, String[] options, int[] optionImages, int correctOptionIndex, String category) {
        this.questionText = questionText;
        this.options = options;
        this.optionImages = optionImages;
        this.correctOptionIndex = correctOptionIndex;
        this.category = category;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public int[] getOptionImages() {
        return optionImages;
    }

    public boolean hasImages() {
        return optionImages != null && optionImages.length > 0;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    public String getCategory() {
        return category;
    }
}
