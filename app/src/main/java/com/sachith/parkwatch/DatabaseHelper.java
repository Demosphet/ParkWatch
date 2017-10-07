package com.sachith.parkwatch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    public static final String COL_7 = "LONGITUDE";
    public static final String COL_8 = "LATITUDE";
//    public static final String COL_8 = "CARSPACE";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME +
                " (" + COL_1 + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_2 + "TEXT," +
                COL_3 + "TEXT," +
                COL_4 + "TEXT," +
                COL_5 + "TEXT," +
                COL_6 + "TEXT," +
                COL_7 + "TEXT," +
                COL_8 + "TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String registration, String make, String model, String colour, String type, String longitude, String latitude){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, registration);
        contentValues.put(COL_3, make);
        contentValues.put(COL_4, model);
        contentValues.put(COL_5, colour);
        contentValues.put(COL_6, type);
        contentValues.put(COL_7, longitude);
        contentValues.put(COL_8, latitude);
//        contentValues.put(COL_9, carspaces);


        long result = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);

        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME,null);
        return res;
    }
}
