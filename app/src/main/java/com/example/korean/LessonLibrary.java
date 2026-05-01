package com.example.korean;

import java.util.ArrayList;
import java.util.List;

public class LessonLibrary {

    public static List<Lesson> getLessonsByCategory(String category) {
        List<Lesson> lessons = new ArrayList<>();

        switch (category) {
            case "Greetings":
                lessons.add(new Lesson("Greetings", "안녕하세요", "Annyeonghaseyo (Formal)", "Hello", R.drawable.wave));
                lessons.add(new Lesson("Greetings", "안녕하세요", "Annyeong (Informal)", "Hello", R.drawable.wave));
                lessons.add(new Lesson("Greetings", "안녕하세요", "Joh-eun achim", "Good morning", R.drawable.wave));
                lessons.add(new Lesson("Greetings", "안녕하세요", "Joh-eun jeonyeok", "Good evening", R.drawable.wave));
                lessons.add(new Lesson("Greetings", "안녕히 계세요", "Annyeonghi  gyeseyo", "Goodbye (Stay well)", R.drawable.wave));
                lessons.add(new Lesson("Greetings", "안녕히 가세요", "Annyeonghi gaseyo", "Goodbye (Go well)", R.drawable.wave));
                lessons.add(new Lesson("Greetings", "감사합니다", "Gamsahamnida", "Thank you (Formal)", R.drawable.wave));
                lessons.add(new Lesson("Greetings", "감사합니다", "Gomawo", "Thank you (Informal)", R.drawable.wave));
                lessons.add(new Lesson("Greetings", "죄송합니다", "Joesonghamnida", "Sorry (Formal)", R.drawable.wave));
                lessons.add(new Lesson("Greetings", "죄송합니다", "Mianhae", "Sorry (Informal)", R.drawable.wave));
                lessons.add(new Lesson("Greetings", "죄송합니다", "Sillyehamnida", "Excuse me", R.drawable.wave));
                lessons.add(new Lesson("Greetings", "죄송합니다", "Ne", "Yes", R.drawable.wave));
                lessons.add(new Lesson("Greetings", "죄송합니다", "Aniyo", "No", R.drawable.wave));
                lessons.add(new Lesson("Greetings", "죄송합니다", "Jal jinaesseoyo", "How are you?", R.drawable.wave));
                lessons.add(new Lesson("Greetings", "죄송합니다", "Jal jinaeyo", "I’m fine", R.drawable.wave));
                lessons.add(new Lesson("Greetings", "죄송합니다", "Mannaseo bangapseumnida", "Nice to meet you", R.drawable.wave));
                break;

            case "Food":
                lessons.add(new Lesson("Food", "밥", "Bap", "Rice/Meal", R.drawable.bap));
                lessons.add(new Lesson("Food", "물", "Mul", "Water", R.drawable.mul));
                lessons.add(new Lesson("Food", "김치", "Kimchi", "Kimchi", R.drawable.kimchi));
                lessons.add(new Lesson("Food", "국", "Guk", "Soup", R.drawable.guk));
                lessons.add(new Lesson("Food", "빵", "Bbang", "Bread", R.drawable.ppang));
                lessons.add(new Lesson("Food", "고기", "Gogi", "Meat", R.drawable.gogi));
                lessons.add(new Lesson("Food", "생선", "Saengseon", "Fish", R.drawable.fish));
                lessons.add(new Lesson("Food", "닭", "Dak", "Chicken", R.drawable.chicken));
                lessons.add(new Lesson("Food", "계란", "Gyeran", "Egg", R.drawable.egg));
                lessons.add(new Lesson("Food", "과일", "Gwail", "Fruit", R.drawable.gwail));
                lessons.add(new Lesson("Food", "채소", "Chaeso", "Vegetable", R.drawable.chaeso));
                lessons.add(new Lesson("Food", "차", "Cha", "Tea", R.drawable.cha));
                lessons.add(new Lesson("Food", "커피", "Keopi", "Coffee", R.drawable.keopi));
                lessons.add(new Lesson("Food", "우유", "Uyu", "Milk", R.drawable.uyu));
                lessons.add(new Lesson("Food", "주스", "Jus", "Juice", R.drawable.juseu));
                lessons.add(new Lesson("Food", "맥주", "Maekju", "Beer", R.drawable.maekju));
                lessons.add(new Lesson("Food", "와인", "Wain", "Wine", R.drawable.wain));
                lessons.add(new Lesson("Food", "소주", "Soju", "Soju", R.drawable.soju));
                lessons.add(new Lesson("Food", "라면", "Ramyeon", "Ramen", R.drawable.ramyeon));
                lessons.add(new Lesson("Food", "김치", "Kimchi", "Kimchi", R.drawable.kimchi));

                break;

            case "Alphabet":
                lessons.add(new Lesson("Alphabet", "ㄱ", "giyeok", "g/k sound", R.drawable.gk));
                lessons.add(new Lesson("Alphabet", "ㄴ", "nieun", "n sound", R.drawable.n));
                lessons.add(new Lesson("Alphabet", "ㄷ", "digeut", "d/t sound", R.drawable.td));
                lessons.add(new Lesson("Alphabet", "ㄹ", "rieul", "r/l sound", R.drawable.rl));
                lessons.add(new Lesson("Alphabet", "ㅁ", "mieum", "m sound", R.drawable.m));
                lessons.add(new Lesson("Alphabet", "ㅂ", "bieup", "b/p sound", R.drawable.pb));
                lessons.add(new Lesson("Alphabet", "ㅅ", "siot", "s sound", R.drawable.s));
                lessons.add(new Lesson("Alphabet", "ㅇ", "ieung", "ng sound", R.drawable.ieung));
                lessons.add(new Lesson("Alphabet", "ㅈ", "jieut", "j sound", R.drawable.tj));
                lessons.add(new Lesson("Alphabet", "ㅊ", "chieut", "ch sound", R.drawable.tjh));
                lessons.add(new Lesson("Alphabet", "ㅋ", "kieuk", "k sound", R.drawable.kh));
                lessons.add(new Lesson("Alphabet", "ㅌ", "tieut", "t sound", R.drawable.th));
                lessons.add(new Lesson("Alphabet", "ㅍ", "pieup", "p sound", R.drawable.ph));
                lessons.add(new Lesson("Alphabet", "ㅎ", "hieut", "h sound", R.drawable.h));
                lessons.add(new Lesson("Alphabet", "ㅏ", "a", "a sound", R.drawable.a));
                lessons.add(new Lesson("Alphabet", "ㅑ", "ya", "ya sound", R.drawable.ya));
                lessons.add(new Lesson("Alphabet", "ㅓ", "eo", "eo sound", R.drawable.eo));
                lessons.add(new Lesson("Alphabet", "ㅕ", "yeo", "yeo sound", R.drawable.yeo));
                lessons.add(new Lesson("Alphabet", "ㅗ", "o", "o sound", R.drawable.ieung));
                lessons.add(new Lesson("Alphabet", "ㅛ", "yo", "yo sound", R.drawable.yo));
                lessons.add(new Lesson("Alphabet", "ㅜ", "u", "u sound", R.drawable.u));
                lessons.add(new Lesson("Alphabet", "ㅠ", "yu", "yu sound", R.drawable.yu));
                lessons.add(new Lesson("Alphabet", "ㅡ", "eu", "eu sound", R.drawable.eu));
                lessons.add(new Lesson("Alphabet", "ㅣ", "i", "i sound", R.drawable.i));

                break;

            case "Numbers":
                lessons.add(new Lesson("Numbers", "하나", "Hana", "One", R.drawable.hana));
                lessons.add(new Lesson("Numbers", "둘", "Dul", "Two", R.drawable.dul));
                lessons.add(new Lesson("Numbers", "셋", "Set", "Three", R.drawable.set));
                lessons.add(new Lesson("Numbers", "넷", "Net", "Four", R.drawable.net));
                lessons.add(new Lesson("Numbers", "다섯", "Daseot", "Five", R.drawable.daseot));
                lessons.add(new Lesson("Numbers", "여섯", "Yeoseot", "Six", R.drawable.yeoseot));
                lessons.add(new Lesson("Numbers", "일곱", "Ilgop", "Seven", R.drawable.ilgop));
                lessons.add(new Lesson("Numbers", "여덟", "Yeodeol", "Eight", R.drawable.yeodeol));
                lessons.add(new Lesson("Numbers", "아홉", "Ahop", "Nine", R.drawable.ahop));
                lessons.add(new Lesson("Numbers", "열", "Yeol", "Ten", R.drawable.yeol));
                lessons.add(new Lesson("Numbers", "스물", "Seumul", "Twenty", R.drawable.seumul));
                lessons.add(new Lesson("Numbers", "서른", "Seoreun", "Thirty", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Numbers", "마흔", "Maheun", "Forty", R.drawable.maheun));
                lessons.add(new Lesson("Numbers", "쉰", "Swineun", "Fifty", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Numbers", "예순", "Yesun", "Sixty", R.drawable.yesun));
                lessons.add(new Lesson("Numbers", "일흔", "Ilheun", "Seventy", R.drawable.ilheun));
                lessons.add(new Lesson("Numbers", "여든", "Yeodeun", "Eighty", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Numbers", "아흔", "Ahheun", "Ninety", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Numbers", "백", "Baek", "Hundred", R.drawable.baek));
                lessons.add(new Lesson("Numbers", "천", "Cheon", "Thousand", R.drawable.cheon));
                lessons.add(new Lesson("Numbers", "만", "Man", "Ten Thousand", R.drawable.man));
                lessons.add(new Lesson("Numbers", "십만", "Sibman", "Hundred Thousand", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Numbers", "백만", "Baegman", "Million", android.R.drawable.ic_menu_gallery));
                lessons.add(new Lesson("Numbers", "십억", "Sib-eog", "Billion", android.R.drawable.ic_menu_gallery));
                break;

            case "Family":
                lessons.add(new Lesson("Family", "아버지", "Abeoji", "Father", R.drawable.father));
                lessons.add(new Lesson("Family", "어머니", "Eomeoni", "Mother", R.drawable.mother));
                lessons.add(new Lesson("Family", "형", "Hyeong", "Older Brother (Male)", R.drawable.olbro2));
                lessons.add(new Lesson("Family", "누나", "Nuna", "Older Sister (Male)", R.drawable.olsis2));
                lessons.add(new Lesson("Family", "오빠", "Oppa", "Older Brother (Female)", R.drawable.olbro1));
                lessons.add(new Lesson("Family", "언니", "Eonni", "Older Sister (Female)", R.drawable.olsis1));
                lessons.add(new Lesson("Family", "남동생", "Namdongsaeng", "Younger brother", R.drawable.ybro));
                lessons.add(new Lesson("Family", "여동생", "Yeodongsaeng", "Younger sister", R.drawable.ysis));
                lessons.add(new Lesson("Family", "조부모", "Jobumo", "Grandparents", R.drawable.granpar));
                lessons.add(new Lesson("Family", "할머니", "Halmeoni", "Grandmother", R.drawable.granma));
                lessons.add(new Lesson("Family", "할아버지", "Harabeoji", "Grandfather", R.drawable.granpa));
                lessons.add(new Lesson("Family", "아내", "Anae", "Wife", R.drawable.wife));
                lessons.add(new Lesson("Family", "남편", "Nampyeon", "Husband", R.drawable.husband));
                lessons.add(new Lesson("Family", "남자친구", "Namjachingu", "Boyfriend", R.drawable.boyfriend));
                lessons.add(new Lesson("Family", "여자친구", "Yeojachingu", "Girlfriend", R.drawable.girlfriend));
                lessons.add(new Lesson("Family", "친척", "Chincheok", "Relatives", R.drawable.relatives));
                lessons.add(new Lesson("Family", "사촌", "Sachon", "Cousin", R.drawable.cousins));
                break;

            case "Verbs":
                lessons.add(new Lesson("Verbs", "가다", "Gada", "To go", R.drawable.todo));
                lessons.add(new Lesson("Verbs", "오다", "Oda", "To come", R.drawable.tocome));
                lessons.add(new Lesson("Verbs", "먹다", "Meokda", "To eat", R.drawable.toeat));
                lessons.add(new Lesson("Verbs", "마시다", "Masida", "To drink", R.drawable.todrink));
                lessons.add(new Lesson("Verbs", "보다", "Boda", "To see/watch", R.drawable.tosee));
                lessons.add(new Lesson("Verbs", "자다", "Jada", "To sleep", R.drawable.tosleep));
                lessons.add(new Lesson("Verbs", "주다", "Juda", "To give", R.drawable.togive));
                lessons.add(new Lesson("Verbs", "받다", "Batda", "To receive", R.drawable.toreceive));
                lessons.add(new Lesson("Verbs", "알다", "Alda", "To know", R.drawable.toknow));
                lessons.add(new Lesson("Verbs", "모르다", "Moreuda", "To not know", R.drawable.tonotknow));
                lessons.add(new Lesson("Verbs", "살다", "Salda", "To live", R.drawable.tolive));
                lessons.add(new Lesson("Verbs", "만들다", "Mandeulda", "To make", R.drawable.tomake));
                lessons.add(new Lesson("Verbs", "읽다", "Ikda", "To read", R.drawable.toread));
                lessons.add(new Lesson("Verbs", "쓰다", "Sseuda", "To write", R.drawable.towrite));
                lessons.add(new Lesson("Verbs", "사다", "Sada", "To buy", R.drawable.tobuy));
                lessons.add(new Lesson("Verbs", "듣다", "Deutda", "To hear/listen", R.drawable.tolisten));
                lessons.add(new Lesson("Verbs", "말하다", "Malhada", "To speak/talk", R.drawable.tospeak));
                lessons.add(new Lesson("Verbs", "만나다", "Mannada", "To meet", R.drawable.tomeet));
                lessons.add(new Lesson("Verbs", "기다리다", "Gidarida", "To wait", R.drawable.towait));
                break;

            case "Places":
                lessons.add(new Lesson("Places", "학교", "Hakgyo", "School", R.drawable.school));
                lessons.add(new Lesson("Places", "집", "Jip", "Home", R.drawable.house));
                lessons.add(new Lesson("Places", "병원", "Byeong-won", "Hospital", R.drawable.hospital));
                lessons.add(new Lesson("Places", "식당", "Sikdang", "Restaurant", R.drawable.restaurant));
                lessons.add(new Lesson("Places", "공원", "Gong-won", "Park", R.drawable.park));
                lessons.add(new Lesson("Places", "대학", "Daehakgyo", "University", R.drawable.university));
                lessons.add(new Lesson("Places", "사무소", "Samuso", "Office", R.drawable.postoffice));
                lessons.add(new Lesson("Places", "카페", "Kape", "Cafe", R.drawable.cafe));
                lessons.add(new Lesson("Places", "가게", "Gage", "Store/shop", R.drawable.store));
                lessons.add(new Lesson("Places", "시장", "Sijang", "Market", R.drawable.market));
                lessons.add(new Lesson("Places", "도서관", "Doseogwan", "Library", R.drawable.library));
                lessons.add(new Lesson("Places", "은행", "Eunhaeng", "Bank", R.drawable.bank));
                lessons.add(new Lesson("Places", "우체국", "Ucheguk", "Post office", R.drawable.postoffice));
                lessons.add(new Lesson("Places", "공항", "Gonghang", "Airport", R.drawable.airport));
                lessons.add(new Lesson("Places", "역", "Gichayeok", "Train Station", R.drawable.trainstation));
                lessons.add(new Lesson("Places", "버스 정류장", "Beoseu jeongryujang", "Bus stop", R.drawable.busstation));
                lessons.add(new Lesson("Places", "호텔", "Hotel", "Hotel", R.drawable.hotel));
                lessons.add(new Lesson("Places", "방", "Bang", "Room", R.drawable.room));

                break;

            case "Time":
                lessons.add(new Lesson("Time", "오늘", "Oneul", "Today", R.drawable.today));
                lessons.add(new Lesson("Time", "내일", "Naeil", "Tomorrow", R.drawable.tomorrow));
                lessons.add(new Lesson("Time", "어제", "Eoje", "Yesterday", R.drawable.yester));
                lessons.add(new Lesson("Time", "지금", "Jigeum", "Now", R.drawable.now));
                lessons.add(new Lesson("Time", "월요일", "Woryoil", "Monday", R.drawable.mon));
                lessons.add(new Lesson("Time", "화요일", "Hwayoil", "Tuesday", R.drawable.tue));
                lessons.add(new Lesson("Time", "수요일", "Suyoil", "Wednesday", R.drawable.wed));
                lessons.add(new Lesson("Time", "목요일", "Mogyoil", "Thursday", R.drawable.thu));
                lessons.add(new Lesson("Time", "금요일", "Geumyoil", "Friday", R.drawable.fri));
                lessons.add(new Lesson("Time", "토요일", "Toyoil", "Saturday", R.drawable.sat));
                lessons.add(new Lesson("Time", "일요일", "Iryoil", "Sunday", R.drawable.sun));
                lessons.add(new Lesson("Time", "일월", "Irwol", "January", R.drawable.jan));
                lessons.add(new Lesson("Time", "이월", "Iwol", "February", R.drawable.feb));
                lessons.add(new Lesson("Time", "삼월", "Samwol", "March", R.drawable.mar));
                lessons.add(new Lesson("Time", "사월", "Sawol", "April", R.drawable.apr));
                lessons.add(new Lesson("Time", "오월", "Owol", "May", R.drawable.may));
                lessons.add(new Lesson("Time", "유월", "Yuwol", "June", R.drawable.jun));
                lessons.add(new Lesson("Time", "칠월", "Chirwol", "July", R.drawable.jul));
                lessons.add(new Lesson("Time", "팔월", "Parwol", "August", R.drawable.aug));
                lessons.add(new Lesson("Time", "구월", "Guwol", "September", R.drawable.sep));
                lessons.add(new Lesson("Time", "시월", "Siwol", "October", R.drawable.oct));
                lessons.add(new Lesson("Time", "십일월", "Sibirwol", "November", R.drawable.nov));
                lessons.add(new Lesson("Time", "십이월", "Sibiwol", "December", R.drawable.dec));
                lessons.add(new Lesson("Time", "초", "Cho", "Seconds", R.drawable.sec));
                lessons.add(new Lesson("Time", "분", "Bun", "Minutes", R.drawable.min));
                lessons.add(new Lesson("Time", "시간", "Sigan", "Hours", R.drawable.hour));
                lessons.add(new Lesson("Time", "일", "Il", "Days", R.drawable.days));
                lessons.add(new Lesson("Time", "주", "Ju", "Weeks", R.drawable.weeks));
                lessons.add(new Lesson("Time", "달 / 개월", "Dal / Gaewol", "Months", R.drawable.months));
                lessons.add(new Lesson("Time", "년", "Nyeon", "Years", R.drawable.years));
                lessons.add(new Lesson("Time", "시 ", "Si", "O'clock", R.drawable.oclock));
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
