package com.example.exspendables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Categories extends SQLiteOpenHelper {

    /*private static final String table_name = "Categories";
    private static final String col1_name = "catList";*/

    String catList;
    String maxBudget;

    public Categories(Context context) {
        super(context, "CATEGORIES", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE CATEGORIES (catlist TEXT PRIMARY KEY, maxbudget TEXT,iconname TEXT)" ;
        db.execSQL(createQuery);

        String insertQuery = "INSERT INTO CATEGORIES VALUES('Shopping','0',''), ('Food','0',''), ('Rent','0',''), ('Entertainment','0','')";
        db.execSQL(insertQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS CATEGORIES" );
        onCreate(db);
    }

    public boolean addData(String category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("catlist",category);

        long result = db.insert("CATEGORIES",null,contentValues);

        if(result == -1){
            Log.d(TAG, "Category added to DB successfully");
            db.close();     // harish - 25.05
            return false;
        }else{
            Log.d(TAG, "Category not added to DB");
            db.close();     // harish - 25.05
            return true;
        }

    }

    public boolean deleteData(String category){
        SQLiteDatabase db = this.getWritableDatabase();
        //int result = db.delete("CATEGORIES","catlist=" + category,null);
        int result = db.delete("CATEGORIES","catlist=?",new String[]{category});
        if(result > 0){
            db.close();     // harish - 25.05
            return true;
        }
        else{
            db.close();     // harish - 25.05
            return false;
        }
    }

    public boolean modifyData(String new_category, String old_category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("catlist",new_category);
        //int result = db.update("CATEGORIES",contentValues,"catlist=" + old_category,null);
        int result = db.update("CATEGORIES",contentValues,"catlist=?",new String[]{old_category});

        if(result > 0){
            db.close();     // harish - 25.05
            return true;
        }
        else
        {
            db.close();     // harish - 25.05
            return false;
        }
    }

    public boolean modifyIcon(String category,String iconName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("iconname",iconName);

        int result = db.update("CATEGORIES",contentValues,"catlist=?",new String[]{category});

        if(result > 0){
            db.close();     // harish - 25.05
            return true;
        }
        else
        {
            db.close();     // harish - 25.05
            return false;
        }

    }

    public List<String> getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CATEGORIES",null);

        List<String> categories = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String categoryname = cursor.getString(cursor.getColumnIndex("catlist"));
                //String maxbudget = cursor.getString(cursor.getColumnIndex("maxbudget"));
                categories.add(categoryname);

                cursor.moveToNext();
            }
        }
        db.close();     // harish - 25.05
        return categories;
    }

    public List<String> getCategoryAndBudget(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CATEGORIES",null);

        List<String> categories = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String categoryname = cursor.getString(cursor.getColumnIndex("catlist"));
                String maxbudget = cursor.getString(cursor.getColumnIndex("maxbudget"));

                categoryname = categoryname+";"+maxbudget;
                categories.add(categoryname);

                cursor.moveToNext();
            }
        }
        db.close();     // harish - 25.05
        return categories;
    }

    public boolean addmaxbudget(String category, String budget){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("catlist",category);
        contentValues.put("maxbudget",budget);
        //int result = db.update("CATEGORIES",contentValues,"catlist=" + old_category,null);
        int result = db.update("CATEGORIES",contentValues,"catlist=?",new String[]{category});

        if(result > 0){
            db.close();
            return true;
        }
        else
        {
            db.close();
            return false;
        }

    }

}
