package com.madao.simplebeat;


import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;


// 这一个类的代码在刀尖上运行
public class MyProvider extends ContentProvider {

    private static final String TABLE_NAME = "my_table";

    private DatabaseHelper dbHelper;
    SQLiteDatabase database;
    private Context context;

    //别删，删了有bug
    public MyProvider(){
    }

    public MyProvider(Context ctx){
        context = ctx;
    }
    @Override
    public boolean onCreate() {
        return database != null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return null;
    }
    public void setDatabase(SQLiteDatabase indatabase){
        database = indatabase;
    }
    @Override
    public String getType(Uri uri) {
        return null; // 不需要实现
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null; // 不需要实现
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0; // 不需要实现
    }

    @Nullable
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
       try {

            printAllRecords(database);
            database.update(TABLE_NAME, values, "_id=?", new String[]{"1"});
        }catch (Exception e){
            Log.e("MainActivity", "Error during onCreate: " + e.getMessage());
        }
        return 0;
    }
    public int getBPM()
    {

        String[] columns = {"_id", "bpm", "sound"};
        Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null);

        cursor.moveToFirst();
        @SuppressLint("Range") int bpm = cursor.getInt(cursor.getColumnIndex("bpm"));
        return bpm;
    }
    public String getSound()
    {

        String[] columns = {"_id", "bpm", "sound"};
        Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null);

        cursor.moveToFirst();
        @SuppressLint("Range") String sound = cursor.getString(cursor.getColumnIndex("sound"));
        return sound;
    }

    private void printAllRecords(SQLiteDatabase db) {
        String[] columns = {"_id", "bpm", "sound"};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("_id"));
                @SuppressLint("Range") int bpm = cursor.getInt(cursor.getColumnIndex("bpm"));
                @SuppressLint("Range") String sound = cursor.getString(cursor.getColumnIndex("sound"));
                Log.d("MainActivity", "Record: ID=" + id + ", BPM=" + bpm + ", Sound=" + sound);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }


}
