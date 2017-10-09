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
    public static final String TABLE_VEHICLE = "vehicle";
    public static final String COL_VEHICLE_ID = "id";
    public static final String COL_VEHICLE_REGISTRATION = "registration";
    public static final String COL_VEHICLE_MAKE = "make";
    public static final String COL_VEHICLE_MODEL = "model";
    public static final String COL_VEHICLE_COLOUR = "colour";
    public static final String COL_VEHICLE_TYPE = "type";
    public static final String COL_VEHICLE_LONGITUDE = "longitude";
    public static final String COL_VEHICLE_LATITUDE = "latitude";
    public static final String COL_VEHICLE_CARSPACE_ID = "carspace_id";
    public static final String COL_VEHICLE_TIMESTAMP = "timestamp";

    public static final String TABLE_SPACES = "spaces";
    public static final String COL_SPACES_ID = "ID";
    public static final String COL_SPACES_CARSPACES_ID = "carspace_id";
    public static final String COL_SPACES_NOOFCARS = "noofcars";
    public static final String COL_SPACES_IMAGEURL = "imageurl";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create VEHICLES table
        Log.d("db-debug","In onCreate");
        query = "create table " + TABLE_VEHICLE +
                " (" + COL_VEHICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_VEHICLE_REGISTRATION + " TEXT," +
                COL_VEHICLE_MAKE + " TEXT," +
                COL_VEHICLE_MODEL + " TEXT," +
                COL_VEHICLE_COLOUR + " TEXT," +
                COL_VEHICLE_TYPE + " TEXT," +
                COL_VEHICLE_LONGITUDE + " TEXT," +
                COL_VEHICLE_LATITUDE + " TEXT," +
                COL_VEHICLE_CARSPACE_ID + " TEXT," +
                COL_VEHICLE_TIMESTAMP + " DATETIME DEFAULT (DATETIME(CURRENT_TIMESTAMP,'LOCALTIME')))";
        sqLiteDatabase.execSQL(query);

        Log.d("db-debug","Created Table 1");
        Log.d("db-debug",query);
        //Create CARSPACES table


        query = "create table " + TABLE_SPACES +
                " (" + COL_SPACES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_SPACES_CARSPACES_ID + " TEXT," +
                COL_SPACES_NOOFCARS + " INTEGER," +
                COL_SPACES_IMAGEURL + " TEXT," +
                " FOREIGN KEY (" + COL_SPACES_CARSPACES_ID + ") REFERENCES " + TABLE_VEHICLE + "(" + COL_VEHICLE_CARSPACE_ID + ")" + ")";

        sqLiteDatabase.execSQL(query);
        Log.d("db-debug","Created Table 2");
        Log.d("db-debug",query);

        query = "INSERT INTO " + TABLE_SPACES + " (" + COL_SPACES_CARSPACES_ID + ", " + COL_SPACES_NOOFCARS + ", " + COL_SPACES_IMAGEURL + ") VALUES (\"A\",5,\"here\");";
        Log.d("db-debug","Entry 1");
        sqLiteDatabase.execSQL(query);
        Log.d("db-debug",query);
        query = "INSERT INTO " + TABLE_SPACES + " (" + COL_SPACES_CARSPACES_ID + ", " + COL_SPACES_NOOFCARS + ", " + COL_SPACES_IMAGEURL + ") VALUES (\"B\",2,\"there\");";
        Log.d("db-debug","Entry 2");
        sqLiteDatabase.execSQL(query);
        Log.d("db-debug",query);
        query = "INSERT INTO " + TABLE_SPACES + " (" + COL_SPACES_CARSPACES_ID + ", " + COL_SPACES_NOOFCARS + ", " + COL_SPACES_IMAGEURL + ") VALUES (\"C\",7,\"where\");";
        Log.d("db-debug","Entry 3");
        sqLiteDatabase.execSQL(query);
        Log.d("db-debug",query);

        Log.d("db-debug","Inserted Data");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_VEHICLE);
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SPACES);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String registration, String make, String model, String colour, String type, String longitude, String latitude, String carSpaces){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_VEHICLE_REGISTRATION, registration);
        contentValues.put(COL_VEHICLE_MAKE, make);
        contentValues.put(COL_VEHICLE_MODEL, model);
        contentValues.put(COL_VEHICLE_COLOUR, colour);
        contentValues.put(COL_VEHICLE_TYPE, type);
        contentValues.put(COL_VEHICLE_LONGITUDE, longitude);
        contentValues.put(COL_VEHICLE_LATITUDE, latitude);
        contentValues.put(COL_VEHICLE_CARSPACE_ID, carSpaces);


        long result = sqLiteDatabase.insert(TABLE_VEHICLE,null,contentValues);

        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean insertData_2(String carspace, double noofcars, String imageurl){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_SPACES_CARSPACES_ID, carspace);
        contentValues.put(COL_SPACES_NOOFCARS, noofcars);
        contentValues.put(COL_SPACES_IMAGEURL, imageurl);

        long result = sqLiteDatabase.insert(TABLE_SPACES,null,contentValues);

        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public void updateSpacesA() {
//        int NoOfCars = -1;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        query = "UPDATE " + TABLE_SPACES + " SET " + COL_SPACES_NOOFCARS + " = ( " + COL_SPACES_NOOFCARS + " - 1 )" + " WHERE " + COL_SPACES_CARSPACES_ID + " = " + " \"A\";";
        sqLiteDatabase.execSQL(query);
        Log.d("db-debug",query);
//        if (res.moveToFirst()) {
//            do {
//                NoOfCars = res.getInt(res.getColumnIndex(COL_SPACES_NOOFCARS));
//            }
//            while (res.moveToNext());
//        }
//        return NoOfCars;
    }

    public void updateSpacesB() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        query = "UPDATE " + TABLE_SPACES + " SET " + COL_SPACES_NOOFCARS + " = ( " + COL_SPACES_NOOFCARS + " - 1 )" + " WHERE " + COL_SPACES_CARSPACES_ID + " = " + " \"B\";";
        sqLiteDatabase.execSQL(query);
        Log.d("db-debug",query);
    }

    public void updateSpacesC() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        query = "UPDATE " + TABLE_SPACES + " SET " + COL_SPACES_NOOFCARS + " = ( " + COL_SPACES_NOOFCARS + " - 1 )" + " WHERE " + COL_SPACES_CARSPACES_ID + " = " + " \"C\";";
        sqLiteDatabase.execSQL(query);
        Log.d("db-debug",query);
    }

    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_VEHICLE,null);
        return res;
    }

    public Cursor getAllData_carSpace(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_SPACES,null);
        return res;
    }

    public int getAllData_carSpaceA(){
        int NoOfCars = -1;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT " + COL_SPACES_NOOFCARS + " FROM " + TABLE_SPACES + " WHERE " + COL_SPACES_CARSPACES_ID + " = " + "\"A\";" ,null);

        if (res.moveToFirst()) {
            do {
                NoOfCars = res.getInt(res.getColumnIndex(COL_SPACES_NOOFCARS));
            }
            while (res.moveToNext());
        }
        return NoOfCars;
    }

    public int getAllData_carSpaceB(){
        int NoOfCars = -1;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT " + COL_SPACES_NOOFCARS + " FROM " + TABLE_SPACES + " WHERE " + COL_SPACES_CARSPACES_ID + " = " + "\"B\";" ,null);
        if (res.moveToFirst()) {
            do {
                NoOfCars = res.getInt(res.getColumnIndex(COL_SPACES_NOOFCARS));
            }
            while (res.moveToNext());
        }
        return NoOfCars;
    }

    public int getAllData_carSpaceC(){
        int NoOfCars = -1;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT " + COL_SPACES_NOOFCARS + " FROM " + TABLE_SPACES + " WHERE " + COL_SPACES_CARSPACES_ID + " = " + "\"C\";" ,null);
        if (res.moveToFirst()) {
            do {
                NoOfCars = res.getInt(res.getColumnIndex(COL_SPACES_NOOFCARS));
            }
            while (res.moveToNext());
        }
        return NoOfCars;
    }
}
