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

public class DatabaseIncomeExpense extends SQLiteOpenHelper {

    public String category;
    public Date startDate;
    public Date endDate;
    public int amount;
    public String code;
    public String paymentMethod;
    public String note;
    public String indicator;

    public DatabaseIncomeExpense(Context context) {
        super(context, "TRANSACTIONS", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createQuery = "CREATE TABLE TRANSACTIONS " +
                "(category TEXT," +
                " startDate DATE," +
                " endDate   DATE," +
                " amount INTEGER," +
                " code TEXT DEFAULT 'EUR'," +
                " paymentMethod TEXT," +
                " note TEXT DEFAULT 'No value entered'," +
                " indicator TEXT )" ;

        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TRANSACTIONS" );
        onCreate(db);
    }

    public boolean addData(String category, Date startDate,Date endDate,int amount,
                        String code, String paymMethod, String note, String indicator) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("category",category);
        contentValues.put("startDate",startDate.toString());
        contentValues.put("endDate",endDate.toString());
        contentValues.put("amount",amount);
        contentValues.put("code",code);
        contentValues.put("paymentMethod",paymMethod);
        contentValues.put("note",note);
        contentValues.put("indicator",indicator);

        long result = db.insert("TRANSACTIONS",null,contentValues);

        if(result > 0){
            return true;
        }
        else{
            return false;
        }

        /*SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO TRANSACTIONS " +
                "VALUES(" + "Grocery" + "," +
                startDate + "," +
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

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public Cursor getData(Date startDate, Date endDate){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * " +
                "FROM TRANSACTIONS " +
                "WHERE startDate >= " +
                startDate + " AND endDate <= " +
                endDate + " AND indicator = Expense";

        Cursor data = db.rawQuery(query,null);

        return data;
    }
}
