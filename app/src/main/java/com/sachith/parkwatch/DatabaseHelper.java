package com.sachith.parkwatch;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sachs on 10/09/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "parkwatch.db";
    public static final String TABLE_NAME = "vehicle";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "REGISTRATION";
    public static final String COL_3 = "MAKE";
    public static final String COL_4 = "MODEL";
    public static final String COL_5 = "COLOUR";
    public static final String COL_6 = "TYPE";
    public static final String COL_7 = "CARSPACE";
    public static final String COL_8 = "IMAGELOCATION";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table" + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, REGISTRATION TEXT, MAKE TEXT, MODEL TEXT, COLOUR TEXT, TYPE TEXT, CARSPACE TEXT, IMAGELOCATION TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
