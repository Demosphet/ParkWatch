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
    public static final String COL_VEHICLE_URL = "imageURL";

    public static final String TABLE_SPACES = "spaces";
    public static final String COL_SPACES_ID = "ID";
    public static final String COL_SPACES_CARSPACES_ID = "carspace_id";
    public static final String COL_SPACES_NOOFCARS = "noofcars";
//    public static final String COL_SPACES_IMAGEURL = "imageurl";
    public static final String COL_SPACES_INFLOW = "inflow";
    public static final String COL_SPACES_OUTFLOW = "outflow";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

//    References for Setting up a Local Database
//    1st Tutorial: Android SQLite Database Tutorial 1 # Introduction + Creating Database and Tables (Part 1)
//    Link: https://www.youtube.com/watch?v=cp2rL3sAFmI
//
//            2nd Tutorial: Android SQLite Database Tutorial 2 # Introduction + Creating Database and Tables (Part 2)
//    Link: https://www.youtube.com/watch?v=p8TaTgr4uKM
//
//            3rd Tutorial: Android SQLite Database Tutorial 3 # Insert values to SQLite Database table using Android
//    Link: https://www.youtube.com/watch?v=T0ClYrJukPA
//
//            4th Tutorial: Android SQLite Database Tutorial 4 # Show SQLite Database table Values using Android
//    Link: https://www.youtube.com/watch?v=KUq5wf3Mh0c
//
//            5th Tutorial: Android SQLite Database Tutorial 5 # Update values in SQLite Database table using Android
//    Link: https://www.youtube.com/watch?v=PA4A9IesyCg

//    Reference for SQLite Timestamp
//    https://stackoverflow.com/questions/381371/sqlite-current-timestamp-is-in-gmt-not-the-timezone-of-the-machine

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
                COL_VEHICLE_LONGITUDE + " REAL," +
                COL_VEHICLE_LATITUDE + " REAL," +
                COL_VEHICLE_CARSPACE_ID + " TEXT," +
                COL_VEHICLE_TIMESTAMP + " DATETIME DEFAULT (DATETIME(CURRENT_TIMESTAMP,'LOCALTIME'))," +
                COL_VEHICLE_URL + " TEXT)";
        sqLiteDatabase.execSQL(query);

        Log.d("db-debug","Created Table 1");
        Log.d("db-debug",query);
        //Create CARSPACES table


        query = "create table " + TABLE_SPACES +
                " (" + COL_SPACES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_SPACES_CARSPACES_ID + " TEXT," +
                COL_SPACES_NOOFCARS + " INTEGER," +
                COL_SPACES_INFLOW + " INTEGER," +
                COL_SPACES_OUTFLOW + " INTEGER," +
                " FOREIGN KEY (" + COL_SPACES_CARSPACES_ID + ") REFERENCES " + TABLE_VEHICLE + "(" + COL_VEHICLE_CARSPACE_ID + ")" + ")";

        sqLiteDatabase.execSQL(query);
        Log.d("db-debug","Created Table 2");
        Log.d("db-debug",query);

        query = "INSERT INTO " + TABLE_SPACES + " (" + COL_SPACES_CARSPACES_ID + ", " + COL_SPACES_NOOFCARS + ", " + COL_SPACES_INFLOW + ", " + COL_SPACES_OUTFLOW + ") VALUES (\"A\",5,10,5);";
        Log.d("db-debug","Entry 1");
        sqLiteDatabase.execSQL(query);
        Log.d("db-debug",query);
        query = "INSERT INTO " + TABLE_SPACES + " (" + COL_SPACES_CARSPACES_ID + ", " + COL_SPACES_NOOFCARS + ", " + COL_SPACES_INFLOW + ", " + COL_SPACES_OUTFLOW + ") VALUES (\"B\",2,7,5);";
        Log.d("db-debug","Entry 2");
        sqLiteDatabase.execSQL(query);
        Log.d("db-debug",query);
        query = "INSERT INTO " + TABLE_SPACES + " (" + COL_SPACES_CARSPACES_ID + ", " + COL_SPACES_NOOFCARS + ", " + COL_SPACES_INFLOW + ", " + COL_SPACES_OUTFLOW + ") VALUES (\"C\",7,21,14);";
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

    public boolean insertData(String registration, String make, String model, String colour, String type, String longitude, String latitude, String carSpaces, String url){
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
        contentValues.put(COL_VEHICLE_URL, url);


        long result = sqLiteDatabase.insert(TABLE_VEHICLE,null,contentValues);

        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean insertData_2(String carspace, double noofcars, String inflow, String outflow){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_SPACES_CARSPACES_ID, carspace);
        contentValues.put(COL_SPACES_NOOFCARS, noofcars);
        contentValues.put(COL_SPACES_INFLOW, inflow);
        contentValues.put(COL_SPACES_OUTFLOW, outflow);

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
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_VEHICLE + " ORDER BY " + COL_VEHICLE_TIMESTAMP + " DESC;",null);
        return res;
    }

    public Vehicle getAllData_ListView(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "SELECT " +
                COL_VEHICLE_ID + ", " +
                COL_VEHICLE_REGISTRATION + ", " +
                COL_VEHICLE_MAKE + ", " +
                COL_VEHICLE_MODEL + ", " +
                COL_VEHICLE_COLOUR + ", " +
                COL_VEHICLE_TYPE + ", " +
                COL_VEHICLE_LONGITUDE + ", " +
                COL_VEHICLE_LATITUDE + ", " +
                COL_VEHICLE_CARSPACE_ID + ", " +
                COL_VEHICLE_TIMESTAMP + ", " +
                COL_VEHICLE_URL +
                " FROM " + TABLE_VEHICLE +
                " WHERE " + COL_VEHICLE_ID +
                " = " + id + ";";
        Cursor res = sqLiteDatabase.rawQuery(query,null);
        res.moveToFirst();
        Vehicle vehicleHistory = new Vehicle(
                res.getString(res.getColumnIndex("id")),
                res.getString(res.getColumnIndex("registration")),
                res.getString(res.getColumnIndex("make")),
                res.getString(res.getColumnIndex("model")),
                res.getString(res.getColumnIndex("colour")),
                res.getString(res.getColumnIndex("type")),
                res.getString(res.getColumnIndex("longitude")),
                res.getString(res.getColumnIndex("latitude")),
                res.getString(res.getColumnIndex("carspace_id")),
                res.getString(res.getColumnIndex("timestamp")),
                res.getString(res.getColumnIndex("imageURL"))
        );
        return vehicleHistory;
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

    public int getIn_carSpaceA(){
        int NoOfCars = -1;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT " + COL_SPACES_INFLOW + " FROM " + TABLE_SPACES + " WHERE " + COL_SPACES_CARSPACES_ID + " = " + "\"A\";" ,null);
        if (res.moveToFirst()) {
            do {
                NoOfCars = res.getInt(res.getColumnIndex(COL_SPACES_INFLOW));
            }
            while (res.moveToNext());
        }
        return NoOfCars;
    }

    public int getIn_carSpaceB(){
        int NoOfCars = -1;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT " + COL_SPACES_INFLOW + " FROM " + TABLE_SPACES + " WHERE " + COL_SPACES_CARSPACES_ID + " = " + "\"B\";" ,null);
        if (res.moveToFirst()) {
            do {
                NoOfCars = res.getInt(res.getColumnIndex(COL_SPACES_INFLOW));
            }
            while (res.moveToNext());
        }
        return NoOfCars;
    }

    public int getIn_carSpaceC(){
        int NoOfCars = -1;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT " + COL_SPACES_INFLOW + " FROM " + TABLE_SPACES + " WHERE " + COL_SPACES_CARSPACES_ID + " = " + "\"C\";" ,null);
        if (res.moveToFirst()) {
            do {
                NoOfCars = res.getInt(res.getColumnIndex(COL_SPACES_INFLOW));
            }
            while (res.moveToNext());
        }
        return NoOfCars;
    }

    public int getOut_carSpaceA(){
        int NoOfCars = -1;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT " + COL_SPACES_OUTFLOW + " FROM " + TABLE_SPACES + " WHERE " + COL_SPACES_CARSPACES_ID + " = " + "\"A\";" ,null);
        if (res.moveToFirst()) {
            do {
                NoOfCars = res.getInt(res.getColumnIndex(COL_SPACES_OUTFLOW));
            }
            while (res.moveToNext());
        }
        return NoOfCars;
    }

    public int getOut_carSpaceB(){
        int NoOfCars = -1;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT " + COL_SPACES_OUTFLOW + " FROM " + TABLE_SPACES + " WHERE " + COL_SPACES_CARSPACES_ID + " = " + "\"B\";" ,null);
        if (res.moveToFirst()) {
            do {
                NoOfCars = res.getInt(res.getColumnIndex(COL_SPACES_OUTFLOW));
            }
            while (res.moveToNext());
        }
        return NoOfCars;
    }

    public int getOut_carSpaceC(){
        int NoOfCars = -1;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT " + COL_SPACES_OUTFLOW + " FROM " + TABLE_SPACES + " WHERE " + COL_SPACES_CARSPACES_ID + " = " + "\"C\";" ,null);
        if (res.moveToFirst()) {
            do {
                NoOfCars = res.getInt(res.getColumnIndex(COL_SPACES_OUTFLOW));
            }
            while (res.moveToNext());
        }
        return NoOfCars;
    }

    public void deleteCar(String id, String carSpace){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_SPACES + " SET " + COL_SPACES_NOOFCARS + " = ( " + COL_SPACES_NOOFCARS + " + 1 )" + " WHERE " + COL_SPACES_CARSPACES_ID + " = " + "\"" + carSpace + "\"" + " ;";
        sqLiteDatabase.execSQL(query);
        Log.d("db-debug",query);
        query = "DELETE FROM " + TABLE_VEHICLE + " WHERE " + COL_VEHICLE_ID + " = " + id + " ;";
        sqLiteDatabase.execSQL(query);
        Log.d("db-debug",query);
    }
}
