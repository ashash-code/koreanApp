package com.example.korean;

import java.util.ArrayList;
import java.util.List;

public class LessonLibrary {

    public static List<Lesson> getLessonsByCategory(String category) {
        List<Lesson> lessons = new ArrayList<>();

        switch (category) {
            case "Greetings":
                lessons.add(new Lesson("Greetings", "안녕하세요", "Annyeong-haseyo", "Hello", R.drawable.wave));
                lessons.add(new Lesson("Greetings", "감사합니다", "Gamsa-hamnida", "Thank you", R.drawable.wave));
                lessons.add(new Lesson("Greetings", "죄송합니다", "Joesong-hamnida", "I am sorry", R.drawable.wave));
                lessons.add(new Lesson("Greetings", "안녕히 계세요", "Annyeonghi gyeseyo", "Goodbye (Stay well)", R.drawable.wave));
                lessons.add(new Lesson("Greetings", "안녕히 가세요", "Annyeonghi gaseyo", "Goodbye (Go well)", R.drawable.wave));
                break;

            case "Food":
                lessons.add(new Lesson("Food", "밥", "Bap", "Rice/Meal", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Food", "물", "Mul", "Water", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Food", "김치", "Kimchi", "Kimchi", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Food", "비빔밥", "Bibimbap", "Mixed Rice", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Food", "고기", "Gogi", "Meat", android.R.drawable.ic_menu_gallery));
                break;

            case "Alphabet":
                lessons.add(new Lesson("Alphabet", "ㄱ", "Gi-yeok", "k/g sound", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Alphabet", "ㄴ", "Ni-eun", "n sound", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Alphabet", "ㄷ", "Di-geut", "d/t sound", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Alphabet", "ㄹ", "Ri-eul", "r/l sound", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Alphabet", "ㅁ", "Mi-eum", "m sound", android.R.drawable.ic_menu_gallery));
                break;

            case "Numbers":
                lessons.add(new Lesson("Numbers", "하나", "Hana", "One (Native)", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Numbers", "둘", "Dul", "Two (Native)", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Numbers", "셋", "Set", "Three (Native)", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Numbers", "넷", "Net", "Four (Native)", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Numbers", "다섯", "Daseot", "Five (Native)", android.R.drawable.ic_menu_gallery));
                break;

            case "Family":
                lessons.add(new Lesson("Family", "아버지", "Abeoji", "Father", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Family", "어머니", "Eomeoni", "Mother", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Family", "형", "Hyeong", "Older Brother (Male)", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Family", "누나", "Nuna", "Older Sister (Male)", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Family", "오빠", "Oppa", "Older Brother (Female)", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Family", "언니", "Eonni", "Older Sister (Female)", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Family", "언니", "Eonni", "Younger brother", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Family", "언니", "Eonni", "Younger sister", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Family", "언니", "Eonni", "Grandparents", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Family", "언니", "Eonni", "Grandmother", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Family", "언니", "Eonni", "Grandfather", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Family", "언니", "Eonni", "Wife", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Family", "언니", "Eonni", "Husband", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Family", "언니", "Eonni", "Boyfriend", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Family", "언니", "Eonni", "Girlfriend", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Family", "언니", "Eonni", "Relatives", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Family", "언니", "Eonni", "Cousin", android.R.drawable.ic_menu_gallery));
                break;

            case "Verbs":
                lessons.add(new Lesson("Verbs", "가다", "Gada", "To go", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Verbs", "오다", "Oda", "To come", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Verbs", "먹다", "Meokda", "To eat", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Verbs", "마시다", "Masida", "To drink", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Verbs", "마시다", "Masida", "To see/watch", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Verbs", "자다", "Jada", "To sleep", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Verbs", "자다", "Jada", "To give", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Verbs", "자다", "Jada", "To receive", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Verbs", "자다", "Jada", "To know", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Verbs", "자다", "Jada", "To not know", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Verbs", "자다", "Jada", "To live", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Verbs", "자다", "Jada", "To make", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Verbs", "자다", "Jada", "To read", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Verbs", "자다", "Jada", "To write", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Verbs", "자다", "Jada", "To buy", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Verbs", "자다", "Jada", "To hear/listen", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Verbs", "자다", "Jada", "To speak/talk", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Verbs", "자다", "Jada", "To meet", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Verbs", "자다", "Jada", "To wait", android.R.drawable.ic_menu_gallery));
                break;

            case "Places":
                lessons.add(new Lesson("Places", "학교", "Hakgyo", "School", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Places", "집", "Jip", "Home", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Places", "병원", "Byeong-won", "Hospital", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Places", "식당", "Sikdang", "Restaurant", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Places", "공원", "Gong-won", "Park", android.R.drawable.ic_menu_gallery));
                break;

            case "Time":
                lessons.add(new Lesson("Time", "오늘", "Oneul", "Today", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "내일", "Naeil", "Tomorrow", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "어제", "Eoje", "Yesterday", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "지금", "Jigeum", "Now", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "Morning", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "Monday", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "Tuesday", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "Wednesday", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "Thursday", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "Friday", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "Saturday", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "Sunday", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "January", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "February", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "March", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "April", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "March", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "June", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "July", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "August", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "September", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "October", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "November", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "December", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "Seconds", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "Minutes", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "Hours", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "Days", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "Weeks", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "Months", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "Years", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Time", "아침", "Achim", "O'clock", android.R.drawable.ic_menu_gallery));
                break;

            case "Colors":
                lessons.add(new Lesson("Colors", "빨간색", "Ppal-gan-saek", "Red", R.drawable.red));
                lessons.add(new Lesson("Colors", "주황색", "Ju-hwang-saek", "Orange", R.drawable.orange));
                lessons.add(new Lesson("Colors", "노란색", "No-ran-saek", "Yellow", R.drawable.yellow));
                lessons.add(new Lesson("Colors", "초록색", "Cho-rok-saek", "Green", R.drawable.green));
                lessons.add(new Lesson("Colors", "파란색", "Pa-ran-saek", "Blue", R.drawable.blue));
                lessons.add(new Lesson("Colors", "보라색", "Bo-ra-saek", "Purple", R.drawable.purple));
                lessons.add(new Lesson("Colors", "분홍색", "Bun-hong-saek", "Pink", R.drawable.pink));
                lessons.add(new Lesson("Colors", "갈색", "Gal-saek", "Brown", R.drawable.brown));
                lessons.add(new Lesson("Colors", "검정색", "Geom-jeong-saek", "Black", R.drawable.blck));
                lessons.add(new Lesson("Colors", "하얀색", "Ha-yan-saek", "White", R.drawable.white));
                lessons.add(new Lesson("Colors", "회색", "Hoe-saek", "Gray", R.drawable.gray));
                lessons.add(new Lesson("Colors", "금색", "Geum-saek", "Gold", R.drawable.gold));
                lessons.add(new Lesson("Colors", "은색", "Eun-saek", "Silver", R.drawable.silver));
                lessons.add(new Lesson("Colors", "구리색", "Gu-ri-saek", "Bronze", R.drawable.bronze));
                lessons.add(new Lesson("Colors", "무지개색", "Mu-ji-gae-saek", "Rainbow", R.drawable.rainbow));
                break;

            default:
                lessons.add(new Lesson(category, "Lesson 1", "Roman 1", "Meaning 1", android.R.drawable.ic_menu_gallery));
                break;
        }

        return lessons;
    }
}
