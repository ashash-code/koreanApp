package com.example.korean;

import java.util.HashMap;
import java.util.Map;

public class RomanizationEngine {

    // Initial consonants (Cho-seong)
    private static final String[] CHO = {
            "g", "kk", "n", "d", "tt", "r", "m", "b", "pp",
            "s", "ss", "", "j", "jj", "ch", "k", "t", "p", "h"
    };

    // Vowels (Jung-seong)
    private static final String[] JUNG = {
            "a", "ae", "ya", "yae", "eo", "e", "ye", "ye", "o", "wa",
            "wae", "oe", "yo", "u", "wo", "we", "wi", "yu", "eu", "ui", "i"
    };

    // Final consonants (Jong-seong)
    private static final String[] JONG = {
            "", "g", "kk", "gs", "n", "nj", "nh", "d", "l", "lg", "lm",
            "lb", "ls", "lt", "lp", "lh", "m", "b", "bs", "s", "ss",
            "ng", "j", "ch", "k", "t", "p", "h"
    };

    /**
     * Converts Hangeul characters to Romanization (Revised Romanization of Korean).
     */
    public static String hangeulToRoman(String text) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (c >= 0xAC00 && c <= 0xD7A3) {
                int unicode = c - 0xAC00;
                int choIdx = unicode / (21 * 28);
                int jungIdx = (unicode % (21 * 28)) / 28;
                int jongIdx = unicode % 28;

                result.append(CHO[choIdx]);
                result.append(JUNG[jungIdx]);
                result.append(JONG[jongIdx]);
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    /**
     * A basic Romanization to Hangeul converter (Experimental/Simple).
     * This handles common phonetic patterns to assist users typing "annyeong".
     */
    public static String romanToHangeul(String roman) {
        // This is a complex mapping problem. For a simple app, we can use a basic
        // phonetic replacement map for common words if the dictionary fails.
        // However, for a true engine, we'd need a full syllable parser.
        
        // For now, let's provide a hook that can be expanded or use the dictionary.
        String lower = roman.toLowerCase().trim();
        
        // Basic mapping for common phrases to demonstrate functionality
        Map<String, String> commonMap = new HashMap<>();
        commonMap.put("annyeong", "안녕");
        commonMap.put("annyeonghaseyo", "안녕하세요");
        commonMap.put("gamsahamnida", "감사합니다");
        commonMap.put("kamsahamnida", "감사합니다");
        commonMap.put("gwenchana", "괜찮아");
        commonMap.put("gwenchanha", "괜찮아");
        commonMap.put("kwenchana", "괜찮아");
        commonMap.put("gwenchanayo", "괜찮아요");
        commonMap.put("gwenchanhayo", "괜찮아요");
        commonMap.put("mianhae", "미안해");
        commonMap.put("joesonghamnida", "죄송합니다");
        commonMap.put("saranghae", "사랑해");
        commonMap.put("oppa", "오빠");
        commonMap.put("unnie", "언니");
        commonMap.put("noona", "누나");
        commonMap.put("hyung", "형");
        commonMap.put("shiro", "싫어");
        commonMap.put("siro", "싫어");
        commonMap.put("shireo", "싫어");
        commonMap.put("andwae", "안돼");
        commonMap.put("hajima", "하지마");
        commonMap.put("mulla", "몰라");
        commonMap.put("morugesseoyo", "모르겠어요");
        commonMap.put("baegopa", "배고파");
        commonMap.put("hye", "혀");
        commonMap.put("hyeo", "혀");
        commonMap.put("nun", "눈");
        commonMap.put("ko", "코");
        commonMap.put("gwit", "귀");
        commonMap.put("ip", "입");

        // Fruits
        commonMap.put("sagwa", "사과");
        commonMap.put("banana", "바나나");
        commonMap.put("podo", "포도");
        commonMap.put("ttalgi", "딸기");
        commonMap.put("subak", "수박");
        commonMap.put("orenji", "오렌지");
        commonMap.put("mang-go", "망고");

        // Vegetables
        commonMap.put("omu", "오이"); // Cucumber
        commonMap.put("dang-geun", "당근");
        commonMap.put("yangpa", "양파");
        commonMap.put("maneul", "마늘");
        commonMap.put("gamja", "감자");
        commonMap.put("goguma", "고구마");

        // Family
        commonMap.put("appa", "아빠");
        commonMap.put("omma", "엄마");
        commonMap.put("eomma", "엄마");
        commonMap.put("abeoji", "아버지");
        commonMap.put("eomeoni", "어머니");
        commonMap.put("halmeoni", "할머니");
        commonMap.put("harabeoji", "할아버지");
        commonMap.put("yeodongsaeng", "여동생");
        commonMap.put("namdongsaeng", "남동생");

        // Directions
        commonMap.put("weonjjok", "왼쪽"); // Left
        commonMap.put("orenjjok", "오른쪽"); // Right
        commonMap.put("wi", "위"); // Up
        commonMap.put("arae", "아래"); // Down
        commonMap.put("ap", "앞"); // Front
        commonMap.put("dwi", "뒤"); // Back
        commonMap.put("yeop", "옆"); // Side

        // Animals
        commonMap.put("gae", "개");
        commonMap.put("goyangi", "고양이");
        commonMap.put("sae", "새");
        commonMap.put("mulgogi", "물고기");
        commonMap.put("tokki", "토끼");

        // Colors
        commonMap.put("ppalgang", "빨강");
        commonMap.put("parang", "파랑");
        commonMap.put("norang", "노랑");
        commonMap.put("chorok", "초록");
        commonMap.put("geom-jeong", "검정");
        commonMap.put("hayan", "하얀");
        
        if (commonMap.containsKey(lower)) {
            return commonMap.get(lower);
        }
        
        return roman; // Return original if no simple match
    }
}
