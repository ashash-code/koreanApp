package com.example.korean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserRegistration.db";
    public static final String TABLE_NAME = "users";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "FULLNAME";
    public static final String COL_3 = "EMAIL";
    public static final String COL_4 = "PASSWORD";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 11);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FULLNAME TEXT, EMAIL TEXT, PASSWORD TEXT, PROFILE_IMAGE TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS progress (EMAIL TEXT PRIMARY KEY, COMPLETED_LESSONS INTEGER, COMPLETED_CATEGORIES INTEGER, TRANSLATIONS_COUNT INTEGER DEFAULT 0)");
        db.execSQL("CREATE TABLE IF NOT EXISTS quiz_sessions (ID INTEGER PRIMARY KEY AUTOINCREMENT, EMAIL TEXT, CATEGORY_NAME TEXT, DATE TEXT, SCORE INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS achievements (EMAIL TEXT, ACHIEVEMENT_NAME TEXT, PRIMARY KEY(EMAIL, ACHIEVEMENT_NAME))");
        db.execSQL("CREATE TABLE IF NOT EXISTS category_progress (EMAIL TEXT, CATEGORY_NAME TEXT, PROGRESS INTEGER, TOTAL INTEGER, TYPE TEXT, PRIMARY KEY(EMAIL, CATEGORY_NAME, TYPE))");
        Log.d("DB_HELP", "Tables Created Successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("CREATE TABLE IF NOT EXISTS progress (EMAIL TEXT PRIMARY KEY, COMPLETED_LESSONS INTEGER, COMPLETED_CATEGORIES INTEGER)");
        }
        if (oldVersion < 3) {
            db.execSQL("CREATE TABLE IF NOT EXISTS quiz_sessions (ID INTEGER PRIMARY KEY AUTOINCREMENT, EMAIL TEXT, DATE TEXT, SCORE INTEGER)");
        }
        if (oldVersion < 4) {
            db.execSQL("CREATE TABLE IF NOT EXISTS achievements (EMAIL TEXT, ACHIEVEMENT_NAME TEXT, PRIMARY KEY(EMAIL, ACHIEVEMENT_NAME))");
        }
        if (oldVersion < 5) {
            db.execSQL("CREATE TABLE IF NOT EXISTS category_progress (EMAIL TEXT, CATEGORY_NAME TEXT, PROGRESS INTEGER, TOTAL INTEGER, PRIMARY KEY(EMAIL, CATEGORY_NAME))");
        }
        if (oldVersion < 6) {
            try {
                db.execSQL("ALTER TABLE quiz_sessions ADD COLUMN CATEGORY_NAME TEXT");
            } catch (Exception e) {
                Log.e("DB_HELP", "Error upgrading to version 6: " + e.getMessage());
            }
        }
        if (oldVersion < 7) {
            try {
                db.execSQL("ALTER TABLE progress ADD COLUMN TRANSLATIONS_COUNT INTEGER DEFAULT 0");
            } catch (Exception e) {
                Log.e("DB_HELP", "Error upgrading to version 7: " + e.getMessage());
            }
        }
        if (oldVersion < 8) {
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS category_progress_new (EMAIL TEXT, CATEGORY_NAME TEXT, PROGRESS INTEGER, TOTAL INTEGER, TYPE TEXT, PRIMARY KEY(EMAIL, CATEGORY_NAME, TYPE))");
                db.execSQL("INSERT INTO category_progress_new (EMAIL, CATEGORY_NAME, PROGRESS, TOTAL, TYPE) SELECT EMAIL, CATEGORY_NAME, PROGRESS, TOTAL, 'LESSON' FROM category_progress");
                db.execSQL("DROP TABLE category_progress");
                db.execSQL("ALTER TABLE category_progress_new RENAME TO category_progress");
            } catch (Exception e) {
                Log.e("DB_HELP", "Error upgrading to version 8: " + e.getMessage());
            }
        }
        if (oldVersion < 11) {
            try {
                db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN PROFILE_IMAGE TEXT");
            } catch (Exception e) {
                Log.d("DB_HELP", "PROFILE_IMAGE column might already exist: " + e.getMessage());
            }
        }
    }


    public synchronized void updateCategoryProgress(String email, String category, int progress, int total, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("EMAIL", email);
        values.put("CATEGORY_NAME", category);
        values.put("PROGRESS", progress);
        values.put("TOTAL", total);
        values.put("TYPE", type);
        db.insertWithOnConflict("category_progress", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public synchronized int[] getCategoryProgressDetailed(String email, String category, String type) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT PROGRESS, TOTAL FROM category_progress WHERE EMAIL = ? AND CATEGORY_NAME = ? AND TYPE = ?", new String[]{email, category, type});
        int[] result = new int[]{0, 0};
        if (cursor.moveToFirst()) {
            result[0] = cursor.getInt(0);
            result[1] = cursor.getInt(1);
        }
        cursor.close();
        return result;
    }

    public synchronized int getCategoryProgress(String email, String category, String type) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT PROGRESS FROM category_progress WHERE EMAIL = ? AND CATEGORY_NAME = ? AND TYPE = ?", new String[]{email, category, type});
        int progress = 0;
        if (cursor.moveToFirst()) {
            progress = cursor.getInt(0);
        }
        cursor.close();
        return progress;
    }

    public synchronized void saveQuizSession(String email, String category, String date, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("EMAIL", email);
        values.put("CATEGORY_NAME", category);
        values.put("DATE", date);
        values.put("SCORE", score);
        db.insert("quiz_sessions", null, values);
    }

    public synchronized int getCompletedLessonCategoriesCount(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM category_progress WHERE EMAIL = ? AND TYPE = 'LESSON' AND PROGRESS >= TOTAL AND TOTAL > 0", new String[]{email});
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }


    public synchronized int getCompletedQuizCategoriesCount(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(DISTINCT CATEGORY_NAME) FROM quiz_sessions WHERE EMAIL = ? AND CATEGORY_NAME IS NOT NULL", new String[]{email});
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public synchronized java.util.List<String> getQuizDates(String email) {
        java.util.List<String> dates = new java.util.ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT DATE FROM quiz_sessions WHERE EMAIL = ?", new String[]{email});
        if (cursor.moveToFirst()) {
            do {
                dates.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dates;
    }

    public synchronized int getTranslationCount(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT TRANSLATIONS_COUNT FROM progress WHERE EMAIL = ?", new String[]{email});
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public synchronized void incrementTranslationCount(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        int currentCount = getTranslationCount(email);
        ContentValues values = new ContentValues();
        values.put("EMAIL", email);
        values.put("TRANSLATIONS_COUNT", currentCount + 1);
        db.insertWithOnConflict("progress", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public synchronized int[] getProgress(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COMPLETED_LESSONS, COMPLETED_CATEGORIES FROM progress WHERE EMAIL = ?", new String[]{email});
        int[] progress = new int[]{0, 0};
        if (cursor.moveToFirst()) {
            progress[0] = cursor.getInt(0);
            progress[1] = cursor.getInt(1);
        }
        cursor.close();
        return progress;
    }

    public synchronized void updateProfileImage(String email, String imageUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PROFILE_IMAGE", imageUri);
        db.update(TABLE_NAME, values, COL_3 + " = ?", new String[]{email});
    }

    public synchronized String getProfileImage(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT PROFILE_IMAGE FROM " + TABLE_NAME + " WHERE EMAIL = ?", new String[]{email});
        String imageUri = null;
        if (cursor.moveToFirst()) {
            imageUri = cursor.getString(0);
        }
        cursor.close();
        return imageUri;
    }

    public synchronized void updateFullName(String email, String fullName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, fullName);
        db.update(TABLE_NAME, values, COL_3 + " = ?", new String[]{email});
        Log.d("DB_HELP", "Updated name for " + email + " to " + fullName);
    }

    public synchronized boolean addUser(String fullName, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, fullName);
        contentValues.put(COL_3, email);
        contentValues.put(COL_4, password);
        long result = db.insert(TABLE_NAME, null, contentValues);
        
        if (result != -1) {
            Log.d("DB_HELP", "User added to SQLite: " + email);
            return true;
        } else {
            Log.e("DB_HELP", "Failed to add user to SQLite");
            return false;
        }
    }

    public synchronized boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMAIL = ? AND PASSWORD = ?", new String[]{email, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        Log.d("DB_HELP", "Checking user login: " + email + " (Found: " + exists + ")");
        return exists;
    }

    public synchronized boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMAIL = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public synchronized String getFullName(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT FULLNAME FROM " + TABLE_NAME + " WHERE EMAIL = ?", new String[]{email});
        String name = "";
        if (cursor.moveToFirst()) {
            name = cursor.getString(0);
        }
        cursor.close();
        return name;
    }

    public synchronized void unlockAchievement(String email, String achievementName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("EMAIL", email);
        values.put("ACHIEVEMENT_NAME", achievementName);
        db.insertWithOnConflict("achievements", null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }

    public synchronized boolean hasAchievement(String email, String achievementName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM achievements WHERE EMAIL = ? AND ACHIEVEMENT_NAME = ?", new String[]{email, achievementName});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
