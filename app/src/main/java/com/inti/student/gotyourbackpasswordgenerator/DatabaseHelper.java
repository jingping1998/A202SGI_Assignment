package com.inti.student.gotyourbackpasswordgenerator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "password_table5";
    private static final String COL1 = "ID";
    private static final String COL2 = "Application_Name";
    private static final String COL3 = "Username";
    private static final String COL4 = "Password";

    public DatabaseHelper(Context context){
        super(context, TABLE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL2 + " TEXT," + COL3 + " TEXT," + COL4 + " TEXT" + ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String ApplicationName, String Username, String Password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,ApplicationName);
        contentValues.put(COL3,Username);
        contentValues.put(COL4,Password);

        Log.d(TAG, "addData: Adding " + ApplicationName + " to " + TABLE_NAME);
        Log.d(TAG, "addData: Adding " + Username + " to " + TABLE_NAME);
        Log.d(TAG, "addData: Adding " + Password + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME,null,contentValues);
        db.close();

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemID(String applicationName, String userName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME + " WHERE " + COL2 + " = '" + applicationName + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void deleteID(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL1 + " = '" + id + "'";
        db.execSQL(query);
    }
}
