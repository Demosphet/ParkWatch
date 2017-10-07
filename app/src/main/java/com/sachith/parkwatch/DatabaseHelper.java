package com.sachith.parkwatch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by sachs on 10/09/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    private String query;
    public static final String DATABASE_NAME = "parkwatch.db";
    public static final String TABLE_NAME_1 = "vehicle";
    public static final String COL_1_1 = "ID";
    public static final String COL_1_2 = "REGISTRATION";
    public static final String COL_1_3 = "MAKE";
    public static final String COL_1_4 = "MODEL";
    public static final String COL_1_5 = "COLOUR";
    public static final String COL_1_6 = "TYPE";
    public static final String COL_1_7 = "LONGITUDE";
    public static final String COL_1_8 = "LATITUDE";
    public static final String COL_1_9 = "CARSPACE";

    public static final String TABLE_NAME_2 = "spaces";
    public static final String COL_2_1 = "ID";
    public static final String COL_2_2 = "CARSPACE";
    public static final String COL_2_3 = "NOOFCARS";
    public static final String COL_2_4 = "IMAGEURL";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create VEHICLES table
        Log.d("db-debug","In onCreate");
        sqLiteDatabase.execSQL("create table " + TABLE_NAME_1 +
                " (" + COL_1_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_1_2 + " TEXT," +
                COL_1_3 + " TEXT," +
                COL_1_4 + " TEXT," +
                COL_1_5 + " TEXT," +
                COL_1_6 + " TEXT," +
                COL_1_7 + " TEXT," +
                COL_1_8 + " TEXT," +
                COL_1_9 + " TEXT)");
        Log.d("db-debug","Created Table 1");
        //Create CARSPACES table


        query = "create table " + TABLE_NAME_2 +
                " (" + COL_2_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_2_2 + " TEXT," +
                COL_2_3 + " INTEGER," +
                COL_2_4 + " TEXT)";

        Log.d("db-debug",query);

        sqLiteDatabase.execSQL(query);

        Log.d("db-debug","Created Table 2");

        query = "INSERT INTO " + TABLE_NAME_2 + " (" + COL_2_2 + ", " + COL_2_3 + ", " + COL_2_4 + ") VALUES (\"A\",5,\"here\");";
        sqLiteDatabase.execSQL(query);

        Log.d("db-debug","Inserted Data");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String registration, String make, String model, String colour, String type, String longitude, String latitude, String carSpaces){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1_2, registration);
        contentValues.put(COL_1_3, make);
        contentValues.put(COL_1_4, model);
        contentValues.put(COL_1_5, colour);
        contentValues.put(COL_1_6, type);
        contentValues.put(COL_1_7, longitude);
        contentValues.put(COL_1_8, latitude);
        contentValues.put(COL_1_9, carSpaces);


        long result = sqLiteDatabase.insert(TABLE_NAME_1,null,contentValues);

        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean insertData_2(String carspace, double noofcars, String imageurl){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2_2, carspace);
        contentValues.put(COL_2_2, noofcars);
        contentValues.put(COL_2_3, imageurl);

        long result = sqLiteDatabase.insert(TABLE_NAME_2,null,contentValues);

        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME_1,null);
        return res;
    }

    public Cursor getAllData_carSpace(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME_2,null);
        return res;
    }
}
