package com.example.gallery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private String SQL_CREATE_TABLE_IMAGE = "CREATE TABLE image (path TEXT, date TEXT)";

    public DatabaseHelper(Context context) {
        super(context, "gallery", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_IMAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS image");
        onCreate(db);
    }

    public void addImage(Image image) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("path", image.getPath());
        values.put("date", image.getDate());
        db.insert("image", null, values);
    }

    public void deleteImage(Image image) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("image", "path = ?", new String[]{image.getPath()});
    }

    public void updateImage(Image image) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("path", image.getPath());
        values.put("date", image.getDate());
        db.update("image", values, "path = ?", new String[]{image.getPath()});
    }

    public ArrayList<Image> getAllImage() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Image> images = new ArrayList<>();
        Cursor cursor = db.query("image",
                null, null, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String path = cursor.getString(cursor.getColumnIndex("path"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            Image image = new Image(date, path);
            images.add(image);
            cursor.moveToNext();
        }
        return images;
    }
}
