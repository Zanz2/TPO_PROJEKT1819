package com.tpo.sparovcek.sparovcek;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class bazahelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "pb.db";

    public bazahelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + baza.pb.TABLE_NAME + " (" +
                        baza.pb._ID + " INTEGER PRIMARY KEY," +
                        baza.pb.COLUMN_NAME_TITLE + " TEXT)";
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + baza.pb.TABLE_NAME;
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void deleteAll(SQLiteDatabase db) {
        db.execSQL("DELETE FROM "+ baza.pb.TABLE_NAME);
    }
}
