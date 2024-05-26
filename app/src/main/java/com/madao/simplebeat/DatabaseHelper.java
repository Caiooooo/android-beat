package com.madao.simplebeat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_database.db";
    private static final int DATABASE_VERSION = 1;
    private String TABLE_NAME = "my_table";

    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }
    // 创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String createTable = "CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY, "
                    + "bpm INTEGER, sound TEXT)";
            db.execSQL(createTable);
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE _id = 1", null);

            if (cursor.getCount() == 0) {
                // 如果不存在，则插入默认记录
                ContentValues contentValues = new ContentValues();
                contentValues.put("_id", 1);
                contentValues.put("bpm", 120);
                contentValues.put("sound", "Default");
                db.insert(TABLE_NAME, null, contentValues);
            }
            cursor.close();

        }catch (Exception e){
            Log.e("MainActivity", "Error during onCreate: " + e.getMessage());
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}