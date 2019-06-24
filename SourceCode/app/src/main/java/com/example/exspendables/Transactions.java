package com.example.exspendables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Transactions extends SQLiteOpenHelper {

    public String category;
    public String startDate;
    // harish - 25.05 - commented endDate as it is not required
    //public String endDate;
    public Float amount;
    public String code;
    public String paymentMethod;
    public String note;
    public String indicator;
    public String recurringTransaction;
    public String recurringFrequency;
    public String recurringValue;

    public Transactions(Context context) {
        super(context, "TRANSACTIONS", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createQuery = "CREATE TABLE TRANSACTIONS " +
                "(category TEXT," +
                " startDate DATE," +
                // harish - 25.05 - commented endDate as it is not required
                //" endDate   DATE," +
                " amount FLOAT," +
                " code TEXT DEFAULT 'EUR'," +
                " paymentMethod TEXT," +
                " note TEXT DEFAULT 'No value entered'," +
                " indicator TEXT,"+
                " recurringTransaction TEXT, "+
                " recurringFrequency TEXT,"+
                " recurringValue TEXT )" ;

        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TRANSACTIONS" );
        onCreate(db);
    }

    public boolean addData(String category, Date startDate, float amount,
                           String code, String paymentMethod, String note, String indicator, String recurringTransaction,
                           String recurringFrequency, String recurringValue) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("category",category);
        contentValues.put("startDate",startDate.toString());
        // harish - 25.05 - commented endDate as it is not required
        // contentValues.put("endDate",endDate.toString());
        contentValues.put("amount",amount);
        contentValues.put("code",code);
        contentValues.put("paymentMethod",paymentMethod);
        contentValues.put("note",note);
        contentValues.put("indicator",indicator);
        contentValues.put("recurringTransaction",recurringTransaction);
        contentValues.put("recurringFrequency",recurringFrequency);
        contentValues.put("recurringValue",recurringValue);

        long result = db.insert("TRANSACTIONS",null,contentValues);

        if(result > 0){
            db.close();     // harish - 25.05
            return true;
        }
        else{
            db.close();     // harish - 25.05
            return false;
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO TRANSACTIONS " +
                "VALUES(" + "Grocery" + "," +
                startDate + "," +cat
                endDate + "," +
                amount + "," +
                code + "," +
                "Cash" + "," +
                note + "," +
                indicator +")");*/
    }

    public String getCategory() {
        return category;
    }

    public String getStartDate() {
        return startDate;
    }

    // harish - 25.05 - commented endDate as it is not required
//    public String getEndDate() { return endDate; }

    public Float getAmount() {
        return amount;
    }

    public String getCode() {
        return code;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getNote() {
        return note;
    }

    public String getIndicator() {
        return indicator;
    }

    public String getRecurringTransaction() {
        return recurringTransaction;
    }

    public String getRecurringFrequency() {
        return recurringFrequency;
    }

    public String getRecurringValue() {
        return recurringValue;
    }


    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM TRANSACTIONS";

        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public boolean deleteData(String category,String date,String amount,String payment){

        SQLiteDatabase db = this.getWritableDatabase();

        date = date.replaceAll("/","-");

        int result = db.delete("TRANSACTIONS", "category = " + "'" + category + "'"+
                        " AND " + "startDate = " + "'" + date + "'"+
                        " AND " + "amount = " + "'"+ amount + "'" +
                        " AND " + "paymentMethod = "+ "'"+ payment + "'",null);

        return result > 0;
    }
}
