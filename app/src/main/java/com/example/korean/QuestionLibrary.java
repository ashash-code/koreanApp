package com.example.korean;

import java.util.ArrayList;
import java.util.List;

public class QuestionLibrary {

    public static List<Question> getQuestionsByCategory(String category) {
        List<Question> allQuestions = new ArrayList<>();

        // --- ALPHABET CATEGORY ---
        if (category.equalsIgnoreCase("Alphabet")) {
            allQuestions.add(new Question("Which character makes the 'K' sound?", new String[]{"ㄱ", "ㄴ", "ㄷ", "ㄹ"}, 0, "Alphabet"));
            allQuestions.add(new Question("Which vowel makes the 'A' sound?", new String[]{"ㅣ", "ㅡ", "ㅏ", "ㅓ"}, 2, "Alphabet"));
            allQuestions.add(new Question("What is this character: 'ㄴ'?", new String[]{"K", "N", "M", "S"}, 1, "Alphabet"));
            allQuestions.add(new Question("What is this character: 'ㄹ'?", new String[]{"R/L", "P", "T", "H"}, 0, "Alphabet"));
            // Add 10-20 more here...
        }

        // --- FOOD CATEGORY (With Images) ---
        else if (category.equalsIgnoreCase("Food")) {
            // Note: Replace android.R.drawable with your actual drawable resources like R.drawable.bibimbap
            allQuestions.add(new Question("Choose 'Bibimbap'", 
                new String[]{"Kimchi", "Bibimbap", "Bulgogi", "Kimbap"},
                new int[]{android.R.drawable.ic_menu_gallery, android.R.drawable.ic_menu_gallery, android.R.drawable.ic_menu_gallery, android.R.drawable.ic_menu_gallery}, 
                1, "Food"));
            
            allQuestions.add(new Question("What is 'Mul'?", 
                new String[]{"Rice", "Soup", "Water", "Tea"}, 2, "Food"));
            
            allQuestions.add(new Question("Choose 'Kimchi'", 
                new String[]{"Kimchi", "Ramyeon", "Tteokbokki", "Mandu"},
                new int[]{android.R.drawable.ic_menu_gallery, android.R.drawable.ic_menu_gallery, android.R.drawable.ic_menu_gallery, android.R.drawable.ic_menu_gallery}, 
                0, "Food"));
            // Add 10-20 more here...
        }

        // --- NUMBERS CATEGORY ---
        else if (category.equalsIgnoreCase("Numbers")) {
            allQuestions.add(new Question("What is 'Hana'?", new String[]{"One", "Two", "Three", "Four"}, 0, "Numbers"));
            allQuestions.add(new Question("What is 'Set'?", new String[]{"One", "Two", "Three", "Four"}, 2, "Numbers"));
            allQuestions.add(new Question("How do you say '2' in Sino-Korean?", new String[]{"Il", "I", "Sam", "Sa"}, 1, "Numbers"));
            // Add 10-20 more here...
        }

        // --- COLORS CATEGORY ---
        else if (category.equalsIgnoreCase("Colors")) {
            // Image-based questions
            allQuestions.add(new Question("Choose 'Red'", 
                new String[]{"Black", "Red", "Blue", "Green"},
                new int[]{R.drawable.blck, R.drawable.red, R.drawable.blue, R.drawable.green},
                1, "Colors"));
            
            allQuestions.add(new Question("Choose 'Yellow'", 
                new String[]{"Yellow", "Purple", "Pink", "Orange"},
                new int[]{R.drawable.yellow, R.drawable.purple, R.drawable.pink, R.drawable.orange}, 
                0, "Colors"));

            allQuestions.add(new Question("Choose 'Blue'", 
                new String[]{"Silver", "Gold", "Blue", "Brown"},
                new int[]{R.drawable.silver, R.drawable.gold, R.drawable.blue, R.drawable.brown}, 
                2, "Colors"));

            allQuestions.add(new Question("Choose 'Black'", 
                new String[]{"White", "Gray", "Bronze", "Black"},
                new int[]{R.drawable.white, R.drawable.gray, R.drawable.bronze, R.drawable.blck},
                3, "Colors"));

            allQuestions.add(new Question("Choose 'Rainbow'", 
                new String[]{"Green", "Rainbow", "Purple", "Red"},
                new int[]{R.drawable.green, R.drawable.rainbow, R.drawable.purple, R.drawable.red}, 
                1, "Colors"));

            // Text-based questions
            allQuestions.add(new Question("What does '초록색' mean?", new String[]{"Green", "Blue", "Red", "Yellow"}, 0, "Colors"));
            allQuestions.add(new Question("What does '보라색' mean?", new String[]{"Pink", "Purple", "Gray", "Brown"}, 1, "Colors"));
            allQuestions.add(new Question("What does '분홍색' mean?", new String[]{"Red", "Orange", "Pink", "White"}, 2, "Colors"));
            allQuestions.add(new Question("What does '갈색' mean?", new String[]{"Gold", "Silver", "Black", "Brown"}, 3, "Colors"));
            allQuestions.add(new Question("What does '하얀색' mean?", new String[]{"White", "Black", "Gray", "Silver"}, 0, "Colors"));
            allQuestions.add(new Question("What does '회색' mean?", new String[]{"Gold", "Gray", "Bronze", "Black"}, 1, "Colors"));
            allQuestions.add(new Question("What does '금색' mean?", new String[]{"Silver", "Bronze", "Gold", "Rainbow"}, 2, "Colors"));
            allQuestions.add(new Question("What does '은색' mean?", new String[]{"Gold", "Bronze", "Black", "Silver"}, 3, "Colors"));
            allQuestions.add(new Question("What does '구리색' mean?", new String[]{"Bronze", "Gold", "Silver", "Gray"}, 0, "Colors"));
            allQuestions.add(new Question("What does '주황색' mean?", new String[]{"Red", "Orange", "Yellow", "Green"}, 1, "Colors"));
        }
        
        // Add other categories (Greetings, Places, etc.) here...

        return allQuestions;
    }
}
