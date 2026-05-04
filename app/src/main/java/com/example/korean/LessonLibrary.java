package com.example.korean;

import java.util.ArrayList;
import java.util.List;

public class LessonLibrary {

    public static List<Lesson> getLessonsByCategory(String category) {
        List<Lesson> lessons = new ArrayList<>();

        switch (category) {
            case "Greetings":
                lessons.add(new Lesson("Greetings", "안녕하세요", "Annyeonghaseyo (Formal)", "Hello", R.drawable.bowing, R.raw.hello));
                lessons.add(new Lesson("Greetings", "안녕", "Annyeong (Informal)", "Hello", R.drawable.annyeong, R.raw.annyeong));
                lessons.add(new Lesson("Greetings", "좋은 아침", "Joh-eun achim", "Good morning", R.drawable.morning, R.raw.joheunachim));
                lessons.add(new Lesson("Greetings", "좋은 저녁", "Joh-eun jeonyeok", "Good evening", R.drawable.evening, R.raw.joheun));
                lessons.add(new Lesson("Greetings", "안녕히 계세요", "Annyeonghi gyeseyo", "Goodbye (Stay well)", R.drawable.staywell, R.raw.annyeonghaseyo));
                lessons.add(new Lesson("Greetings", "안녕히 가세요", "Annyeonghi gaseyo", "Goodbye (Go well)", R.drawable.gowell, R.raw.annyeonghaseyo));
                lessons.add(new Lesson("Greetings", "감사합니다", "Gamsahamnida", "Thank you (Formal)", R.drawable.bowformal, R.raw.gamsahamnida));
                lessons.add(new Lesson("Greetings", "고마워", "Gomawo", "Thank you (Informal)", R.drawable.thanksinformal, R.raw.gomawo));
                lessons.add(new Lesson("Greetings", "죄송합니다", "Joesonghamnida", "Sorry (Formal)", R.drawable.sorryformal, R.raw.joesonghamnida));
                lessons.add(new Lesson("Greetings", "미안해", "Mianhae", "Sorry (Informal)", R.drawable.sorryinformal, R.raw.mianhae));
                lessons.add(new Lesson("Greetings", "실례합니다", "Sillyehamnida", "Excuse me", R.drawable.greet, R.raw.sillyehamnida));
                lessons.add(new Lesson("Greetings", "네", "Ne", "Yes", R.drawable.yes, R.raw.ne));
                lessons.add(new Lesson("Greetings", "아니요", "Aniyo", "No", R.drawable.no, R.raw.aniyo));
                lessons.add(new Lesson("Greetings", "잘 지냈어요?", "Jal jinaesseoyo", "How are you?", R.drawable.greet2, R.raw.jaljinaesseoyo));
                lessons.add(new Lesson("Greetings", "잘 지내요", "Jal jinaeyo", "I’m fine", R.drawable.greet3, R.raw.jaljinaeyo));
                lessons.add(new Lesson("Greetings", "만나서 반갑습니다", "Mannaseo bangapseumnida", "Nice to meet you", R.drawable.niceto, R.raw.mannaseo));
                break;

            case "Food":
                lessons.add(new Lesson("Food", "밥", "Bap", "Rice/Meal", R.drawable.bap, R.raw.bap));
                lessons.add(new Lesson("Food", "물", "Mul", "Water", R.drawable.mul, R.raw.mul));
                lessons.add(new Lesson("Food", "김치", "Kimchi", "Kimchi", R.drawable.kimchi, R.raw.kimchi));
                lessons.add(new Lesson("Food", "국", "Guk", "Soup", R.drawable.guk, R.raw.guk));
                lessons.add(new Lesson("Food", "빵", "Bbang", "Bread", R.drawable.ppang, R.raw.bbang));
                lessons.add(new Lesson("Food", "고기", "Gogi", "Meat", R.drawable.gogi, R.raw.gogi));
                lessons.add(new Lesson("Food", "생선", "Saengseon", "Fish", R.drawable.fish, R.raw.saengseon));
                lessons.add(new Lesson("Food", "닭", "Dak", "Chicken", R.drawable.chicken, R.raw.dak));
                lessons.add(new Lesson("Food", "계란", "Gyeran", "Egg", R.drawable.egg, R.raw.gyeran));
                lessons.add(new Lesson("Food", "과일", "Gwail", "Fruit", R.drawable.gwail, R.raw.gwail));
                lessons.add(new Lesson("Food", "채소", "Chaeso", "Vegetable", R.drawable.chaeso, R.raw.chaeso));
                lessons.add(new Lesson("Food", "차", "Cha", "Tea", R.drawable.cha, R.raw.cha));
                lessons.add(new Lesson("Food", "커피", "Keopi", "Coffee", R.drawable.keopi, R.raw.keopi));
                lessons.add(new Lesson("Food", "우유", "Uyu", "Milk", R.drawable.uyu, R.raw.uyu));
                lessons.add(new Lesson("Food", "주스", "Jus", "Juice", R.drawable.juseu, R.raw.jus));
                lessons.add(new Lesson("Food", "맥주", "Maekju", "Beer", R.drawable.maekju, R.raw.maekju));
                lessons.add(new Lesson("Food", "와인", "Wain", "Wine", R.drawable.wain, R.raw.wain));
                lessons.add(new Lesson("Food", "소주", "Soju", "Soju", R.drawable.soju, R.raw.soju));
                lessons.add(new Lesson("Food", "라면", "Ramyeon", "Ramen", R.drawable.ramyeon, R.raw.ramyeon));


                break;

            case "Alphabet":
                lessons.add(new Lesson("Alphabet", "ㄱ", "giyeok", "g/k sound", R.drawable.gk, R.raw.giyeok));
                lessons.add(new Lesson("Alphabet", "ㄴ", "nieun", "n sound", R.drawable.n, R.raw.nieun));
                lessons.add(new Lesson("Alphabet", "ㄷ", "digeut", "d/t sound", R.drawable.td, R.raw.digeut));
                lessons.add(new Lesson("Alphabet", "ㄹ", "rieul", "r/l sound", R.drawable.rl, R.raw.rieul));
                lessons.add(new Lesson("Alphabet", "ㅁ", "mieum", "m sound", R.drawable.m, R.raw.mieum));
                lessons.add(new Lesson("Alphabet", "ㅂ", "bieup", "b/p sound", R.drawable.pb, R.raw.bieup));
                lessons.add(new Lesson("Alphabet", "ㅅ", "siot", "s sound", R.drawable.s, R.raw.siot));
                lessons.add(new Lesson("Alphabet", "ㅇ", "ieung", "ng sound", R.drawable.ieung, R.raw.ieung));
                lessons.add(new Lesson("Alphabet", "ㅈ", "jieut", "j sound", R.drawable.tj, R.raw.jieut));
                lessons.add(new Lesson("Alphabet", "ㅊ", "chieut", "ch sound", R.drawable.tjh, R.raw.chieut));
                lessons.add(new Lesson("Alphabet", "ㅋ", "kieuk", "k sound", R.drawable.kh, R.raw.kieuk));
                lessons.add(new Lesson("Alphabet", "ㅌ", "tieut", "t sound", R.drawable.th, R.raw.tieut));
                lessons.add(new Lesson("Alphabet", "ㅍ", "pieup", "p sound", R.drawable.ph, R.raw.pieup));
                lessons.add(new Lesson("Alphabet", "ㅎ", "hieut", "h sound", R.drawable.h, R.raw.hieut));
                lessons.add(new Lesson("Alphabet", "ㅏ", "a", "a sound", R.drawable.a, R.raw.a));
                lessons.add(new Lesson("Alphabet", "ㅑ", "ya", "ya sound", R.drawable.ya, R.raw.ya));
                lessons.add(new Lesson("Alphabet", "ㅓ", "eo", "eo sound", R.drawable.eo, R.raw.eo));
                lessons.add(new Lesson("Alphabet", "ㅕ", "yeo", "yeo sound", R.drawable.yeo, R.raw.yeo));
                lessons.add(new Lesson("Alphabet", "ㅗ", "o", "o sound", R.drawable.ieung, R.raw.ieung));
                lessons.add(new Lesson("Alphabet", "ㅛ", "yo", "yo sound", R.drawable.yo, R.raw.yo));
                lessons.add(new Lesson("Alphabet", "ㅜ", "u", "u sound", R.drawable.u, R.raw.u));
                lessons.add(new Lesson("Alphabet", "ㅠ", "yu", "yu sound", R.drawable.yu, R.raw.yu));
                lessons.add(new Lesson("Alphabet", "ㅡ", "eu", "eu sound", R.drawable.eu, R.raw.eu));
                lessons.add(new Lesson("Alphabet", "ㅣ", "i", "i sound", R.drawable.i, R.raw.i));

                break;

            case "Numbers":
                lessons.add(new Lesson("Numbers", "하나", "Hana", "1", R.drawable.hana, R.raw.hana));
                lessons.add(new Lesson("Numbers", "둘", "Dul", "2", R.drawable.dul, R.raw.two));
                lessons.add(new Lesson("Numbers", "셋", "Set", "3", R.drawable.set, R.raw.set));
                lessons.add(new Lesson("Numbers", "넷", "Net", "4", R.drawable.net, R.raw.net));
                lessons.add(new Lesson("Numbers", "다섯", "Daseot", "5", R.drawable.daseot, R.raw.daseot));
                lessons.add(new Lesson("Numbers", "여섯", "Yeoseot", "6", R.drawable.yeoseot, R.raw.yeoseot));
                lessons.add(new Lesson("Numbers", "일곱", "Ilgop", "7", R.drawable.ilgop, R.raw.ilgop));
                lessons.add(new Lesson("Numbers", "여덟", "Yeodeol", "8", R.drawable.yeodeol, R.raw.yeodeol));
                lessons.add(new Lesson("Numbers", "아홉", "Ahop", "9", R.drawable.ahop, R.raw.ahop));
                lessons.add(new Lesson("Numbers", "열", "Yeol", "10", R.drawable.yeol, R.raw.yeol));
                lessons.add(new Lesson("Numbers", "스물", "Seumul", "20", R.drawable.seumul, R.raw.seumul));
                lessons.add(new Lesson("Numbers", "서른", "Seoreun", "30", R.drawable.seorun, R.raw.seoreun));
                lessons.add(new Lesson("Numbers", "마흔", "Maheun", "40", R.drawable.maheun, R.raw.maheun));
                lessons.add(new Lesson("Numbers", "쉰", "Swineun", "50", R.drawable.swin, R.raw.swineun));
                lessons.add(new Lesson("Numbers", "예순", "Yesun", "60", R.drawable.yesun, R.raw.yesun));
                lessons.add(new Lesson("Numbers", "일흔", "Ilheun", "70", R.drawable.ilheun, R.raw.ilheun));
                lessons.add(new Lesson("Numbers", "여든", "Yeodeun", "80", R.drawable.yeodeun, R.raw.yeodeun));
                lessons.add(new Lesson("Numbers", "아흔", "Ahheun", "90", R.drawable.aheun, R.raw.ahheun));
                lessons.add(new Lesson("Numbers", "백", "Baek", "100", R.drawable.baek, R.raw.baek));
                lessons.add(new Lesson("Numbers", "천", "Cheon", "1,000", R.drawable.cheon, R.raw.cheon));
                lessons.add(new Lesson("Numbers", "만", "Man", "10,000", R.drawable.man, R.raw.man));
                lessons.add(new Lesson("Numbers", "십만", "Sibman", "100,000", R.drawable.man, R.raw.sibman));
                lessons.add(new Lesson("Numbers", "백만", "Baegman", "1,000,000", R.drawable.man, R.raw.baegman));
                lessons.add(new Lesson("Numbers", "십억", "Sib-eog", "1,000,000,000", R.drawable.man, R.raw.sibeog));
                break;

            case "Family":
                lessons.add(new Lesson("Family", "아버지", "Abeoji", "Father", R.drawable.father, R.raw.father));
                lessons.add(new Lesson("Family", "어머니", "Eomeoni", "Mother", R.drawable.mother, R.raw.eomeoni));
                lessons.add(new Lesson("Family", "형", "Hyeong", "Older Brother (Male)", R.drawable.olbro2, R.raw.brother));
                lessons.add(new Lesson("Family", "누나", "Nuna", "Older Sister (Male)", R.drawable.olsis2, R.raw.sister));
                lessons.add(new Lesson("Family", "오빠", "Oppa", "Older Brother (Female)", R.drawable.olbro1, R.raw.oppa));
                lessons.add(new Lesson("Family", "언니", "Eonni", "Older Sister (Female)", R.drawable.olsis1, R.raw.eonni));
                lessons.add(new Lesson("Family", "남동생", "Namdongsaeng", "Younger brother", R.drawable.ybro, R.raw.namdongsaeng));
                lessons.add(new Lesson("Family", "여동생", "Yeodongsaeng", "Younger sister", R.drawable.ysis, R.raw.yeodongsaeng));
                lessons.add(new Lesson("Family", "조부모", "Jobumo", "Grandparents", R.drawable.granpar, R.raw.jobumo));
                lessons.add(new Lesson("Family", "할머니", "Halmeoni", "Grandmother", R.drawable.granma, R.raw.halmeoni));
                lessons.add(new Lesson("Family", "할아버지", "Harabeoji", "Grandfather", R.drawable.granpa, R.raw.harabeoji));
                lessons.add(new Lesson("Family", "아내", "Anae", "Wife", R.drawable.wife, R.raw.anae));
                lessons.add(new Lesson("Family", "남편", "Nampyeon", "Husband", R.drawable.husband, R.raw.nampyeon));
                lessons.add(new Lesson("Family", "남자친구", "Namjachingu", "Boyfriend", R.drawable.boyfriend, R.raw.namjachingu));
                lessons.add(new Lesson("Family", "여자친구", "Yeojachingu", "Girlfriend", R.drawable.girlfriend, R.raw.yeojachingu));
                lessons.add(new Lesson("Family", "친척", "Chincheok", "Relatives", R.drawable.relatives, R.raw.chincheok));
                lessons.add(new Lesson("Family", "사촌", "Sachon", "Cousin", R.drawable.cousins, R.raw.sachon));
                break;

            case "Verbs":
                lessons.add(new Lesson("Verbs", "가다", "Gada", "To go", R.drawable.todo , R.raw.gada));
                lessons.add(new Lesson("Verbs", "오다", "Oda", "To come", R.drawable.tocome, R.raw.oda));
                lessons.add(new Lesson("Verbs", "먹다", "Meokda", "To eat", R.drawable.toeat, R.raw.meokda));
                lessons.add(new Lesson("Verbs", "마시다", "Masida", "To drink", R.drawable.todrink, R.raw.masida));
                lessons.add(new Lesson("Verbs", "보다", "Boda", "To see/watch", R.drawable.tosee, R.raw.boda));
                lessons.add(new Lesson("Verbs", "자다", "Jada", "To sleep", R.drawable.tosleep, R.raw.jada));
                lessons.add(new Lesson("Verbs", "주다", "Juda", "To give", R.drawable.togive, R.raw.juda));
                lessons.add(new Lesson("Verbs", "받다", "Batda", "To receive", R.drawable.toreceive, R.raw.batda));
                lessons.add(new Lesson("Verbs", "알다", "Alda", "To know", R.drawable.toknow, R.raw.alda));
                lessons.add(new Lesson("Verbs", "모르다", "Moreuda", "To not know", R.drawable.tonotknow, R.raw.moreuda));
                lessons.add(new Lesson("Verbs", "살다", "Salda", "To live", R.drawable.tolive, R.raw.salda));
                lessons.add(new Lesson("Verbs", "만들다", "Mandeulda", "To make", R.drawable.tomake, R.raw.mandeulda));
                lessons.add(new Lesson("Verbs", "읽다", "Ikda", "To read", R.drawable.toread, R.raw.ikda));
                lessons.add(new Lesson("Verbs", "쓰다", "Sseuda", "To write", R.drawable.towrite, R.raw.sseuda));
                lessons.add(new Lesson("Verbs", "사다", "Sada", "To buy", R.drawable.tobuy, R.raw.sada));
                lessons.add(new Lesson("Verbs", "듣다", "Deutda", "To hear/listen", R.drawable.tolisten, R.raw.deutda));
                lessons.add(new Lesson("Verbs", "말하다", "Malhada", "To speak/talk", R.drawable.tospeak, R.raw.malhada));
                lessons.add(new Lesson("Verbs", "만나다", "Mannada", "To meet", R.drawable.tomeet, R.raw.mannada));
                lessons.add(new Lesson("Verbs", "기다리다", "Gidarida", "To wait", R.drawable.towait, R.raw.gidarida));
                break;

            case "Places":
                lessons.add(new Lesson("Places", "학교", "Hakgyo", "School", R.drawable.school, R.raw.hakgyo));
                lessons.add(new Lesson("Places", "집", "Jip", "Home", R.drawable.house, R.raw.jip));
                lessons.add(new Lesson("Places", "병원", "Byeong-won", "Hospital", R.drawable.hospital, R.raw.byeongwon));
                lessons.add(new Lesson("Places", "식당", "Sikdang", "Restaurant", R.drawable.restaurant, R.raw.sikdang));
                lessons.add(new Lesson("Places", "공원", "Gong-won", "Park", R.drawable.park, R.raw.gongwon));
                lessons.add(new Lesson("Places", "대학", "Daehakgyo", "University", R.drawable.university, R.raw.daehak));
                lessons.add(new Lesson("Places", "사무소", "Samuso", "Office", R.drawable.postoffice, R.raw.samuso));
                lessons.add(new Lesson("Places", "카페", "Kape", "Cafe", R.drawable.cafe, R.raw.kape));
                lessons.add(new Lesson("Places", "가게", "Gage", "Store/shop", R.drawable.conviniencestore, R.raw.gage));
                lessons.add(new Lesson("Places", "시장", "Sijang", "Market", R.drawable.market, R.raw.sijang));
                lessons.add(new Lesson("Places", "도서관", "Doseogwan", "Library", R.drawable.library, R.raw.doseongwan));
                lessons.add(new Lesson("Places", "은행", "Eunhaeng", "Bank", R.drawable.bank, R.raw.eunhaeng));
                lessons.add(new Lesson("Places", "우체국", "Ucheguk", "Post office", R.drawable.postoffice, R.raw.ucheguk));
                lessons.add(new Lesson("Places", "공항", "Gonghang", "Airport", R.drawable.airport, R.raw.gonghang));
                lessons.add(new Lesson("Places", "역", "Gichayeok", "Train Station", R.drawable.trainstation, R.raw.gi));
                lessons.add(new Lesson("Places", "버스 정류장", "Beoseu jeongryujang", "Bus stop", R.drawable.busstation, R.raw.beoseujeongryujang));
                lessons.add(new Lesson("Places", "호텔", "Hotel", "Hotel", R.drawable.hotel, R.raw.hotel));
                lessons.add(new Lesson("Places", "방", "Bang", "Room", R.drawable.room, R.raw.room));

                break;

            case "Time":
                lessons.add(new Lesson("Time", "오늘", "Oneul", "Today", R.drawable.today, R.raw.oneul ));
                lessons.add(new Lesson("Time", "내일", "Naeil", "Tomorrow", R.drawable.tomorrow, R.raw.naeil));
                lessons.add(new Lesson("Time", "어제", "Eoje", "Yesterday", R.drawable.yester, R.raw.eoje));
                lessons.add(new Lesson("Time", "지금", "Jigeum", "Now", R.drawable.now, R.raw.jieum));
                lessons.add(new Lesson("Time", "월요일", "Woryoil", "Monday", R.drawable.mon, R.raw.woryoil));
                lessons.add(new Lesson("Time", "화요일", "Hwayoil", "Tuesday", R.drawable.tue, R.raw.hwayoil));
                lessons.add(new Lesson("Time", "수요일", "Suyoil", "Wednesday", R.drawable.wed, R.raw.suyoil));
                lessons.add(new Lesson("Time", "목요일", "Mogyoil", "Thursday", R.drawable.thu, R.raw.mogoil));
                lessons.add(new Lesson("Time", "금요일", "Geumyoil", "Friday", R.drawable.fri, R.raw.geumyoi));
                lessons.add(new Lesson("Time", "토요일", "Toyoil", "Saturday", R.drawable.sat, R.raw.toyoil));
                lessons.add(new Lesson("Time", "일요일", "Iryoil", "Sunday", R.drawable.sun, R.raw.iryoil));
                lessons.add(new Lesson("Time", "일월", "Irwol", "January", R.drawable.jan, R.raw.irwol));
                lessons.add(new Lesson("Time", "이월", "Iwol", "February", R.drawable.feb, R.raw.iwol));
                lessons.add(new Lesson("Time", "삼월", "Samwol", "March", R.drawable.mar, R.raw.samwol));
                lessons.add(new Lesson("Time", "사월", "Sawol", "April", R.drawable.apr, R.raw.sawol));
                lessons.add(new Lesson("Time", "오월", "Owol", "May", R.drawable.may, R.raw.owol));
                lessons.add(new Lesson("Time", "유월", "Yuwol", "June", R.drawable.jun, R.raw.yuwol));
                lessons.add(new Lesson("Time", "칠월", "Chirwol", "July", R.drawable.jul, R.raw.chirwol));
                lessons.add(new Lesson("Time", "팔월", "Parwol", "August", R.drawable.aug, R.raw.parwol));
                lessons.add(new Lesson("Time", "구월", "Guwol", "September", R.drawable.sep, R.raw.guwol));
                lessons.add(new Lesson("Time", "시월", "Siwol", "October", R.drawable.oct, R.raw.siwol));
                lessons.add(new Lesson("Time", "십일월", "Sibirwol", "November", R.drawable.nov, R.raw.sibirwol));
                lessons.add(new Lesson("Time", "십이월", "Sibiwol", "December", R.drawable.dec, R.raw.sibiwol));
                lessons.add(new Lesson("Time", "초", "Cho", "Seconds", R.drawable.sec, R.raw.cho));
                lessons.add(new Lesson("Time", "분", "Bun", "Minutes", R.drawable.min, R.raw.bun));
                lessons.add(new Lesson("Time", "시간", "Sigan", "Hours", R.drawable.hour, R.raw.sigan));
                lessons.add(new Lesson("Time", "일", "Il", "Days", R.drawable.days, R.raw.days));
                lessons.add(new Lesson("Time", "주", "Ju", "Weeks", R.drawable.weeks, R.raw.ju));
                lessons.add(new Lesson("Time", "달 / 개월", "Dal / Gaewol", "Months", R.drawable.months, R.raw.dal));
                lessons.add(new Lesson("Time", "년", "Nyeon", "Years", R.drawable.years, R.raw.nyeon));
                lessons.add(new Lesson("Time", "시 ", "Si", "O'clock", R.drawable.oclock, R.raw.si));
                break;

            case "Colors":
                lessons.add(new Lesson("Colors", "빨간색", "Ppal-gan-saek", "Red", R.drawable.red, R.raw.ppalgan));
                lessons.add(new Lesson("Colors", "주황색", "Ju-hwang-saek", "Orange", R.drawable.orange, R.raw.juhwang));
                lessons.add(new Lesson("Colors", "노란색", "No-ran-saek", "Yellow", R.drawable.yellow, R.raw.noran));
                lessons.add(new Lesson("Colors", "초록색", "Cho-rok-saek", "Green", R.drawable.green, R.raw.chorok));
                lessons.add(new Lesson("Colors", "파란색", "Pa-ran-saek", "Blue", R.drawable.blue, R.raw.paran));
                lessons.add(new Lesson("Colors", "보라색", "Bo-ra-saek", "Purple", R.drawable.purple, R.raw.bora));
                lessons.add(new Lesson("Colors", "분홍색", "Bun-hong-saek", "Pink", R.drawable.pink, R.raw.bunhong));
                lessons.add(new Lesson("Colors", "갈색", "Gal-saek", "Brown", R.drawable.brown, R.raw.galsaek));
                lessons.add(new Lesson("Colors", "검정색", "Geom-jeong-saek", "Black", R.drawable.blck, R.raw.geomjeong));
                lessons.add(new Lesson("Colors", "하얀색", "Ha-yan-saek", "White", R.drawable.white, R.raw.hayan));
                lessons.add(new Lesson("Colors", "회색", "Hoe-saek", "Gray", R.drawable.gray, R.raw.hoe));
                lessons.add(new Lesson("Colors", "금색", "Geum-saek", "Gold", R.drawable.gold, R.raw.geum));
                lessons.add(new Lesson("Colors", "은색", "Eun-saek", "Silver", R.drawable.silver, R.raw.eun));
                lessons.add(new Lesson("Colors", "구리색", "Gu-ri-saek", "Bronze", R.drawable.bronze, R.raw.guri));
                lessons.add(new Lesson("Colors", "무지개색", "Mu-ji-gae-saek", "Rainbow", R.drawable.rainbow, R.raw.mujigae));
                break;

            default:
                lessons.add(new Lesson(category, "Lesson 1", "Roman 1", "Meaning 1", android.R.drawable.ic_menu_gallery));
                break;
        }

        return lessons;
    }
}
