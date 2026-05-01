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
        super(context, DATABASE_NAME, null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FULLNAME TEXT, EMAIL TEXT, PASSWORD TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS progress (EMAIL TEXT PRIMARY KEY, COMPLETED_LESSONS INTEGER, COMPLETED_CATEGORIES INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS quiz_sessions (ID INTEGER PRIMARY KEY AUTOINCREMENT, EMAIL TEXT, DATE TEXT, SCORE INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS achievements (EMAIL TEXT, ACHIEVEMENT_NAME TEXT, PRIMARY KEY(EMAIL, ACHIEVEMENT_NAME))");
        db.execSQL("CREATE TABLE IF NOT EXISTS category_progress (EMAIL TEXT, CATEGORY_NAME TEXT, PROGRESS INTEGER, TOTAL INTEGER, PRIMARY KEY(EMAIL, CATEGORY_NAME))");
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
    }

    public synchronized void updateCategoryProgress(String email, String category, int progress, int total) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("EMAIL", email);
        values.put("CATEGORY_NAME", category);
        values.put("PROGRESS", progress);
        values.put("TOTAL", total);
        db.insertWithOnConflict("category_progress", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public synchronized int getCategoryProgress(String email, String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT PROGRESS FROM category_progress WHERE EMAIL = ? AND CATEGORY_NAME = ?", new String[]{email, category});
        int progress = 0;
        if (cursor.moveToFirst()) {
            progress = cursor.getInt(0);
        }
        cursor.close();
        return progress;
    }

    public synchronized void saveQuizSession(String email, String date, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("EMAIL", email);
        values.put("DATE", date);
        values.put("SCORE", score);
        db.insert("quiz_sessions", null, values);
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

    public synchronized void updateProgress(String email, int lessons, int categories) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("EMAIL", email);
        values.put("COMPLETED_LESSONS", lessons);
        values.put("COMPLETED_CATEGORIES", categories);
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
