package com.sachith.parkwatch;

import android.content.ContentValues;
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
//    public static final String COL_7 = "CARSPACE";
//    public static final String COL_8 = "IMAGELOCATION";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table" + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, REGISTRATION TEXT, MAKE TEXT, MODEL TEXT, COLOUR TEXT, TYPE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String registration, String make, String model, String colour, String type){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, registration);
        contentValues.put(COL_3, make);
        contentValues.put(COL_4, model);
        contentValues.put(COL_5, colour);
        contentValues.put(COL_6, type);
//        contentValues.put(COL_7, carspaces);
//        contentValues.put(COL_8, imagelocation);
        long result = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);

        if(result == -1){
            return false;
        } else {
            return true;
        }
    }
}
