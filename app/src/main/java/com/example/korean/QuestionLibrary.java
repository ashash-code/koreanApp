package com.example.korean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuestionLibrary {

    public static List<Question> getQuestionsByCategory(String category) {
        List<Question> allQuestions = new ArrayList<>();
        List<Lesson> lessons;

        if (category.equalsIgnoreCase("Random Quiz") || category.equalsIgnoreCase("All Lessons")) {
            lessons = new ArrayList<>();
            String[] categories = {"Greetings", "Food", "Alphabet", "Numbers", "Family", "Verbs", "Places", "Time", "Colors"};
            for (String cat : categories) {
                List<Lesson> catLessons = LessonLibrary.getLessonsByCategory(cat);
                if (catLessons != null) {
                    lessons.addAll(catLessons);
                }
            }
        } else {
            lessons = LessonLibrary.getLessonsByCategory(category);
        }

        if (lessons == null || lessons.isEmpty()) {
            return allQuestions;
        }

        // Shuffle all available lessons to pick 10 random ones
        List<Lesson> shuffledLessons = new ArrayList<>(lessons);
        Collections.shuffle(shuffledLessons);
        int numQuestions = Math.min(shuffledLessons.size(), 10);

        for (int i = 0; i < numQuestions; i++) {
            Lesson correctLesson = shuffledLessons.get(i);
            String currentCategory = correctLesson.getCategory();

            // Create a pool for distractors from the same category if possible, otherwise from all lessons
            List<Lesson> sameCatPool = LessonLibrary.getLessonsByCategory(currentCategory);
            List<Lesson> distractorPool = new ArrayList<>(sameCatPool.size() > 4 ? sameCatPool : lessons);
            
            // Remove the correct lesson from distractors pool
            for (int d = 0; d < distractorPool.size(); d++) {
                if (distractorPool.get(d).getKorean().equals(correctLesson.getKorean())) {
                    distractorPool.remove(d);
                    break;
                }
            }
            
            Collections.shuffle(distractorPool);

            List<Lesson> choices = new ArrayList<>();
            choices.add(correctLesson);
            
            // Add 3 distractors
            for (int j = 0; j < 3 && j < distractorPool.size(); j++) {
                choices.add(distractorPool.get(j));
            }

            // Shuffle choices
            Collections.shuffle(choices);

            int correctIndex = choices.indexOf(correctLesson);
            String[] optionsText = new String[choices.size()];
            int[] optionsImages = new int[choices.size()];
            for (int k = 0; k < choices.size(); k++) {
                optionsText[k] = choices.get(k).getMeaning();
                optionsImages[k] = choices.get(k).getImageResId();
            }

            boolean isTextOnly = currentCategory.equalsIgnoreCase("Alphabet") || currentCategory.equalsIgnoreCase("Numbers");

            if (isTextOnly) {
                // Question: Korean character, Choices: English/Sound
                allQuestions.add(new Question(correctLesson.getKorean(), optionsText, correctIndex, currentCategory));
            } else {
                // Question: Korean (Romanization), Choices: English + Image
                String questionText = correctLesson.getKorean() + "\n(" + correctLesson.getRomanization() + ")";
                allQuestions.add(new Question(questionText, optionsText, optionsImages, correctIndex, currentCategory));
            }
        }

        return allQuestions;
    }
}
