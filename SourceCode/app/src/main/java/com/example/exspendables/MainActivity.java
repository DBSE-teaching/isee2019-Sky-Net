package com.example.exspendables;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.icu.lang.UCharacter;
import android.icu.util.LocaleData;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.util.SparseBooleanArray;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mynameismidori.currencypicker.CurrencyPicker;
import com.mynameismidori.currencypicker.CurrencyPickerListener;

import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    DatabaseHandler dbPinTable;     // database table for storing PIN
    Categories dbCategories;
    private Button btnSavePin;      // access Save PIN button during First time user login
    private boolean isEntryDateClicked = false;
    private boolean isEndDateClicked = false;
    public static String oldValue;


    // main() method of UI
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbPinTable = new DatabaseHandler(this);
        Cursor cursor = dbPinTable.getPinData();

        // check if a value of PIN exists in dbTable - PIN
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                //launch authenticate PIN page
                setContentView(R.layout.login_page);

            } else {
                // if PIN does not exist, launch first time login page
                setContentView(R.layout.first_time_login);
                btnSavePin = (Button) findViewById(R.id.set_btn);
                btnSavePin.setOnClickListener(this);
            }
        }
    }


    // Event handler for button "Enter Income"
    public void openIncomePage(View view) {
        setContentView(R.layout.income);

        Button button = (Button) findViewById(R.id.selectDate);
        button.setOnClickListener(new View.OnClickListener() {
            public void checkButtonStat(){
                isEntryDateClicked = true;
                isEndDateClicked = false;
            }
            @Override
            public void onClick(View v) {
                checkButtonStat();
                DialogFragment datePicker = new com.example.exspendables.DatePicker();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    // Graph Summary - Logesh

    public void openGraphSummaryPage(View view) {
        setContentView(R.layout.view_summary);

        Button buttonStartDate = (Button) findViewById(R.id.filterStartDate);
        buttonStartDate.setOnClickListener(new View.OnClickListener() {
            public void checkButtonStat() {
                isEntryDateClicked = true;
                isEndDateClicked = false;
            }

            @Override
            public void onClick(View v) {
                checkButtonStat();
                DialogFragment datePicker = new com.example.exspendables.DatePicker();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


        Button buttonEndDate = (Button) findViewById(R.id.filterEndDate);
        buttonEndDate.setOnClickListener(new View.OnClickListener() {
            public void checkButtonStat() {
                isEntryDateClicked = false;
                isEndDateClicked = true;
            }

            @Override
            public void onClick(View v) {
                checkButtonStat();
                DialogFragment datePicker = new com.example.exspendables.DatePicker();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        Spinner spinner = findViewById(R.id.filter);
        Button button = findViewById(R.id.okSubmitButton);
        final DatabaseIncomeExpense databaseIncomeExpense = new DatabaseIncomeExpense(this);


        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {

                //changeToDateFormat("Mon Jun 03 00:00:00 GMT+02:00 2019");

                TextView startDate = findViewById(R.id.entryDate);
                String startDateValue = startDate.getText().toString();
                SimpleDateFormat Formatter = new SimpleDateFormat("yyyy/MM/dd");


                TextView endDate = findViewById(R.id.endDate);
                String endDateValue = endDate.getText().toString();


                BarChart chart;
                ArrayList<BarEntry> BARENTRY;
                ArrayList<String> BarEntryLabels;
                BarDataSet Bardataset;
                BarData BARDATA;
                chart = (BarChart) findViewById(R.id.barGraph);


                BARENTRY = new ArrayList<>();
                BarEntryLabels = new ArrayList<String>();


                SQLiteDatabase db = databaseIncomeExpense.getReadableDatabase();

                Cursor cur;

                String startDateVal = startDate.getText().toString();
                String endDateVal = endDate.getText().toString();

                startDateVal = startDateVal.replaceAll("-", "/");
                endDateVal = endDateVal.replaceAll("-", "/");

                java.util.Date startDate1 = null;
                java.util.Date endDate1 = null;

                //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    startDate1 = (java.util.Date) Formatter.parse(startDateVal);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    endDate1 = (java.util.Date) Formatter.parse(endDateVal);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //int count = 0;
                //int k = 0;
                //String newDate = null;
                //Calendar calendar = Calendar.getInstance();


                //  for (java.util.Date date = startDate1 ; date.before(endDate1); date = date) {
                //for (long j = startDate1.getTime(); j<endDate1.getTime(); j++) {
                //      count = count+1;

                //for (LocalDate date = startDate1; date.isBefore(endDate1); date = date.plusDays(1)) {

                //      String dateVal = date.toString();
                //      String dateChanged = changeToDateFormat(dateVal);

                //cur = db.rawQuery( "SELECT amount FROM TRANSACTIONS WHERE startDate = ? ", new String[]{dateChanged});
                cur = db.rawQuery("SELECT amount,startDate FROM TRANSACTIONS", null);

                //Harish

                java.util.Date dateToCheck = null;
                java.util.Date startdateToCheck = null;
                java.util.Date enddateToCheck = null;
                int i = 0;
                int k = 0;


                cur.moveToFirst();
                while (!cur.isAfterLast()) {
                    String amount = cur.getString(0);
                    String date = cur.getString(1);

                    date = date.replaceAll("-", "/");

                    try {
                        dateToCheck = Formatter.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        startdateToCheck = Formatter.parse(startDateVal);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        enddateToCheck = Formatter.parse(endDateVal);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (dateToCheck.after(startdateToCheck) && dateToCheck.before(enddateToCheck)) {

                        //date = date.replaceAll("/", "");
                        i = Integer.valueOf(date);
                        k = Integer.valueOf(amount);
                        BARENTRY.add(new BarEntry(i, k));

                    }
                    cur.moveToNext();
                }



                //Harish

                   /* try {
                        calendar.setTime(Formatter.parse(startDateVal));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    calendar.add(Calendar.DAY_OF_MONTH, count);
                    newDate = Formatter.format(calendar.getTime());

                    try {
                        date = Formatter.parse(newDate);
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }*/

                //newDate = newDate.replaceAll("/","");
                //int i = Integer.valueOf(newDate);
                //int i =Integer.parseInt(dateVal);
                //int j = Integer.parseInt(databaseIncomeExpense.getAmount());
                //cur.moveToFirst();


                //databaseIncomeExpense.amount = cur.getString(2);
                    /*if(cur != null){
                if(cur.moveToFirst()){
                    String amount = cur.getString(0);
                    String currentDate = cur.getString(1);
                    k = Integer.valueOf(amount);
                }
            }*/


                //BARENTRY.add(new BarEntry(i,k));


                Bardataset = new BarDataSet(BARENTRY, "Expenses");

                BARDATA = new BarData(Bardataset);

                Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

                chart.setData(BARDATA);

                chart.animateY(3000);

                //}

           /* public void AddValuesToBARENTRY(){


                BARENTRY.add(new BarEntry(2f, 0));
                BARENTRY.add(new BarEntry(4f, 1));
                BARENTRY.add(new BarEntry(6f, 2));
                BARENTRY.add(new BarEntry(8f, 3));
                BARENTRY.add(new BarEntry(7f, 4));
                BARENTRY.add(new BarEntry(3f, 5));

            }; */

            /*public void AddValuesToBarEntryLabels(){


                BarEntryLabels.add("January");
                BarEntryLabels.add("February");
                BarEntryLabels.add("March");
                BarEntryLabels.add("April");
                BarEntryLabels.add("May");
                BarEntryLabels.add("June");

            };*/

            }



        });




    }
//Logesh


    // Event handler for button "Enter Expense"
    public void openExpensePage(View view) {
        setContentView(R.layout.expense);

        Categories dbCategories;
        dbCategories = new Categories(this);
        // Populate Category DDLB
        Spinner categoryddlb = (Spinner) findViewById(R.id.categoryddlb);
        List<String> categorylist = dbCategories.getData();
        categorylist.add(0,"");
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,categorylist);
        categoryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        categoryddlb.setAdapter(categoryAdapter);

        /*for(int i = 0;i < categorylist.size();i++){
            categoryAdapter.add(categorylist.get(i).toString());
            categoryAdapter.notifyDataSetChanged();
        }*/

        Button button = (Button) findViewById(R.id.selectDate);
        button.setOnClickListener(new View.OnClickListener() {
            public void checkButtonStat(){
                isEntryDateClicked = true;
                isEndDateClicked = false;
            }
            @Override
            public void onClick(View v) {
                checkButtonStat();
                DialogFragment datePicker = new com.example.exspendables.DatePicker();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        DatabaseCurrency databaseCurrency = new DatabaseCurrency(this);
        Cursor currCode = databaseCurrency.getData();

        if(currCode != null){
            if(currCode.moveToFirst()){
                String currencySavedInDB = currCode.getString(0).toString();
                TextView code = (TextView) findViewById(R.id.currencyCode);
                code.setText(currencySavedInDB);
            }
        }

        //Logesh - 20.05

        final Switch recurringSwitch = (Switch) findViewById(R.id.recurringSwitch);
        final CharSequence[] items = {"Daily", "Weekly", "Monthly", "Other"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText editText = new EditText(this);
        final TextView textView = (TextView) findViewById(R.id.recurringValue);
        final TextView textView1 = (TextView) findViewById(R.id.recurringValue1);

        builder.setView(editText);
        editText.setVisibility(View.INVISIBLE);
        editText.setHint("Ex. 10");
        builder.setTitle("Select any option");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item) {
                Toast.makeText(getApplicationContext(), items[item].toString(), Toast.LENGTH_SHORT).show();
                if(item == 3){
                    final String value = items[item].toString();
                    textView.setText(value);
                    editText.setVisibility(View.VISIBLE);
                    textView1.setVisibility(View.VISIBLE);
                    editText.setText("");
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    String value1 = editText.getText().toString();
                    if (value1.isEmpty()){
                        editText.setError("Enter Value to Proceed");
                        //editText.setText("0");
                        textView1.setText(value1);
                    } else{
                        textView1.setText(value1);
                    }

                }else if(item == 0)
                {
                    String value = items[item].toString();
                    textView.setText(value);
                    textView1.setVisibility(View.INVISIBLE);
                    editText.setText("");
                    editText.setVisibility(View.INVISIBLE);
                }else if(item == 1)
                {
                    String value = items[item].toString();
                    textView.setText(value);
                    textView1.setVisibility(View.INVISIBLE);
                    editText.setText("");
                    editText.setVisibility(View.INVISIBLE);
                }else if(item == 2)
                {
                    String value = items[item].toString();
                    textView.setText(value);
                    textView1.setVisibility(View.INVISIBLE);
                    editText.setText("");
                    editText.setVisibility(View.INVISIBLE);
                } else
                {

                    editText.setVisibility(View.INVISIBLE);
                    editText.setText("");
                    textView1.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                }


            }
        });

        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String value1 = editText.getText().toString();
                        Integer value2 = editText.getVisibility();

                        if (value1.equals("") & value2.equals(View.VISIBLE)){
                            textView1.setText("0");
                            editText.setText("0");
                        } else {
                            textView1.setText(value1);
                        }

                        Toast.makeText(getApplicationContext(), "Recurring option selected", Toast.LENGTH_SHORT).show();

                        //textView1.setText(items);
                    }
                });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        textView1.setText("");
                        recurringSwitch.setChecked(false);
                        Toast.makeText(getApplicationContext(), "Data Not Saved", Toast.LENGTH_SHORT).show();
                    }
                });

        final AlertDialog alert = builder.create();
        recurringSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked == true ) {
                    alert.show();
                    textView.setVisibility(View.VISIBLE);
                    textView1.setVisibility(View.VISIBLE);
                } else
                {
                    alert.cancel();
                    textView.setVisibility(View.INVISIBLE);
                    textView1.setVisibility(View.INVISIBLE);
                    textView.setText("");
                    textView1.setText("");
                }

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.show();
                    }
                });
            }
        });

        //Logesh - 21.05

    }

    public void onSetCurrency(View view){
        final DatabaseCurrency databaseCurrency = new DatabaseCurrency(this);
        final CurrencyPicker picker = CurrencyPicker.newInstance("Select Currency");  // dialog title
        picker.setListener(new CurrencyPickerListener() {
            @Override
            public void onSelectCurrency(String name, String code, String symbol, int flagDrawableResID) {
                // Implement your code here
                closeOptionsMenu();
                String currency_selected = code.toString();

                // save this value to DB so that it can be displayed next to Amount
                Cursor currencyData = databaseCurrency.getData();
                if(currencyData != null){
                    if(currencyData.moveToFirst()){
                        String currencySavedInDB = currencyData.getString(0).toString();
                        databaseCurrency.modifyData(currency_selected,currencySavedInDB);
                        // harish - 25.05
                        Toast.makeText(getApplicationContext(), "Selected currency unit is set", Toast.LENGTH_SHORT).show();
                        // harish - 25.05
                    }
                    else{
                        databaseCurrency.addData(currency_selected);
                        // harish - 25.05
                        Toast.makeText(getApplicationContext(), "Selected currency unit is set", Toast.LENGTH_SHORT).show();
                        // harish - 25.05
                    }
                }
            }
        });
        picker.show(getSupportFragmentManager(), "CURRENCY_PICKER");
    }


    public void openSummaryPage(View view) {
        setContentView(R.layout.summary);

        Button button = (Button) findViewById(R.id.selectDate);
        button.setOnClickListener(new View.OnClickListener() {
            public void checkButtonStat(){
                isEntryDateClicked = true;
                isEndDateClicked = false;
            }
            @Override
            public void onClick(View v) {
                checkButtonStat();
                DialogFragment datePicker = new com.example.exspendables.DatePicker();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


        Button buttonEndDate = (Button) findViewById(R.id.selectEndDate);
        buttonEndDate.setOnClickListener(new View.OnClickListener() {
            public void checkButtonStat(){
                isEntryDateClicked = false;
                isEndDateClicked = true;
            }

            @Override
            public void onClick(View v) {
                checkButtonStat();
                DialogFragment datePicker = new com.example.exspendables.DatePicker();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    public void showSummary(View view){

        TextView startDate = (TextView) findViewById(R.id.entryDate);
        String startDateValue = startDate.getText().toString();

        TextView endDate = (TextView) findViewById(R.id.endDate);
        String endDateValue = endDate.getText().toString();

        setContentView(R.layout.tablesummary);

        DatabaseIncomeExpense databaseIncomeExpense = new DatabaseIncomeExpense(this);
        Cursor cursor = databaseIncomeExpense.getData(startDateValue,endDateValue);

        List<DatabaseIncomeExpense> expenseDetails = new ArrayList<DatabaseIncomeExpense>();

        cursor.moveToFirst();
        int i = 0;
        while (!cursor.isAfterLast()){
            databaseIncomeExpense.category      = cursor.getString(0);
            databaseIncomeExpense.startDate     = cursor.getString(1);
            // harish - 25.05 - commented endDate as it is not required and altered column index further
            //databaseIncomeExpense.endDate       = cursor.getString(2);
            databaseIncomeExpense.amount        = cursor.getString(2);
            databaseIncomeExpense.code          = cursor.getString(3);
            databaseIncomeExpense.paymentMethod = cursor.getString(4);
            databaseIncomeExpense.note          = cursor.getString(5);
            databaseIncomeExpense.indicator     = cursor.getString(6);
            // harish - 25.05

            expenseDetails.add(databaseIncomeExpense);
            i++;
            cursor.moveToNext();
        }

        StringBuilder builder = new StringBuilder();

        // harish - 25.05
        builder.append("Category").append(";")
                .append("Date").append(";")
                .append("Amount").append(";")
                .append("Currency").append(";")
                .append("Payment").append(";")
                .append("Note").append("_");
        // harish - 25.05

        for(DatabaseIncomeExpense expense: expenseDetails){
            builder.append(expense.getCategory()).append(";")
                    .append(expense.getStartDate()).append(";")
                    // harish - 25.05 - commented endDate as it is not required
                    //.append(expense.getEndDate()).append(";")
                    .append(expense.getAmount()).append(";")
                    .append(expense.getCode()).append(";")
                    .append(expense.getPaymentMethod()).append(";")
                    .append(expense.getNote()).append("_");
        }

        builder.toString();
        String st = new String(builder);
        String[] rows  = st.split("_");

        TableLayout tableLayout = (TableLayout)findViewById(R.id.tab);
        tableLayout.removeAllViews();
        String row;
        for(int rowCount =0;rowCount<rows.length;rowCount++){
            //   Log.d("Rows",rows[i]);
            row  = rows[rowCount];
            TableRow tableRow = new TableRow(getApplicationContext());
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            final String[] cols = row.split(";");

            Handler handler = null;
           /* TableRow tableRowHead = (TableRow) findViewById(R.id.tableHeading);
            TableRow tableDataRow = (TableRow) findViewById(R.id.tableData);*/

            for (int j = 0; j < cols.length; j++) {
                final String col = cols[j];
                TextView columsView = new TextView(getApplicationContext());
                columsView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                //columsView.setTextColor(android.R.color.black);
                columsView.setText(String.format("%7s", col));
                //Log.d("Cols", String.format("%7s", col));
                if(rowCount == 0) {
                    tableRow.setClickable(false);
                    tableRow.setBackgroundColor(Color.CYAN);
                }else{
                    tableRow.setBackgroundColor(Color.LTGRAY);
                    tableRow.setClickable(true);
                }
                tableRow.addView(columsView);
            }
            tableLayout.addView(tableRow);
        }
    }

    public void openSettingsPage(View view){
        setContentView(R.layout.settings);

        Button categoryDisplay = (Button) findViewById(R.id.edit_categories);
        categoryDisplay.setOnClickListener(this);

        Button changePinBtn = (Button) findViewById(R.id.changePin);
        changePinBtn.setOnClickListener(this);

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        //String month1 = String.valueOf(month);
        //String dayOfMonth1 = String.valueOf(dayOfMonth);
        String month1;
        String dayOfMonth1;

        month = month + 1;

        if(month < 10){
            month1 = "0" + month;
        }else {
            month1 = String.valueOf(month);
        }

        if(dayOfMonth < 10){
            dayOfMonth1  = "0" + dayOfMonth ;
        }else {
            dayOfMonth1 = String.valueOf(dayOfMonth);
        }


        String currentDateString = year + "-" + month1 + "-" + dayOfMonth1; //DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());


        if(isEntryDateClicked == true){
            TextView entryDate = (TextView) findViewById(R.id.entryDate);
            entryDate.setText(currentDateString);
        }
        else if(isEndDateClicked == true){
            TextView endDate = (TextView) findViewById(R.id.endDate);
            endDate.setText(currentDateString);
        }
    }

    //Logesh - 22.05

    // harish - 25.05 - Fields as mandatory


    public String changeToDateFormat(String date){
        // Mon Jun 03 00:00:00 GMT+02:00 2019

        String month = date.substring(4,7);
        String dateVal = date.substring(8,10);
        String year = date.substring(29,34);

        switch (month){
            case "Jan":
                month = "01";
                break;

            case "Feb":
                month = "02";
                break;

            case "Mar":
                month = "03";
                break;

            case "Apr":
                month = "04";
                break;

            case "May":
                month = "05";
                break;

            case "Jun":
                month = "06";
                break;

            case "Jul":
                month="07";
                break;

            case "Aug":
                month="08";
                break;

            case "Sep":
                month = "09";
                break;

            case "Oct":
                month="10";
                break;

            case "Nov":
                month="11";
                break;

            case  "Dec":
                month="12";
                break;

        }

        return year+"/"+month+"/"+dateVal;

    }


    public void saveExpense(View view) {
        String categoryValue = "";
        String indicatorValue = "";
        Date transactionDateValue = null;
        int amountValue = 0;
        String codeValue = "";
        String paymMethodValue = "";
        boolean mandatoryFieldMissing = false;

        Spinner category = (Spinner) findViewById(R.id.categoryddlb);
        TextView categoryTV = (TextView) findViewById(R.id.category);
        int selectedCategoryItem = category.getSelectedItemPosition();
        if(selectedCategoryItem > 0) {
            categoryValue = category.getSelectedItem().toString();
            categoryTV.setTextColor(Color.BLACK);
        }else{
            mandatoryFieldMissing = true;
            categoryTV.setTextColor(Color.RED);
        }

        TextView transactionDate = (TextView) findViewById(R.id.entryDate);
        TextView trandateTV = (TextView) findViewById(R.id.date);
        if(TextUtils.isEmpty(transactionDate.getText())){
            mandatoryFieldMissing = true;
            trandateTV.setTextColor(Color.RED);
        }else{
            String dummy = transactionDate.getText().toString();
            transactionDateValue = Date.valueOf(transactionDate.getText().toString());
            trandateTV.setTextColor(Color.BLACK);
        }


        // harish - 25.05 - commented endDate as it is not required
        /*TextView endDate = (TextView) findViewById(R.id.endDate);
        Date endDateValue = Date.valueOf(endDate.getText().toString());*/

        EditText amount = (EditText) findViewById(R.id.amount);
        TextView amountTV = (TextView) findViewById(R.id.amountenter);
        if(TextUtils.isEmpty(amount.getText())){
            mandatoryFieldMissing = true;
            amountTV.setTextColor(Color.RED);
        }else{
            amountValue = Integer.valueOf(amount.getText().toString());
            amountTV.setTextColor(Color.BLACK);
        }

        TextView code = (TextView) findViewById(R.id.currencyCode);
        TextView codeTV = (TextView) findViewById(R.id.currencytype);
        if(TextUtils.isEmpty(code.getText())){
            mandatoryFieldMissing = true;
            codeTV.setTextColor(Color.RED);
        }else{
            codeValue = code.getText().toString();
            codeTV.setTextColor(Color.BLACK);
        }

        Spinner paymentMethod = (Spinner) findViewById(R.id.paymList);
        TextView paymentTV = (TextView) findViewById(R.id.payment);
        int selectedPaymentItem = paymentMethod.getSelectedItemPosition();
        if(selectedPaymentItem > 0){
            paymMethodValue = paymentMethod.getSelectedItem().toString();
            paymentTV.setTextColor(Color.BLACK);
        }else{
            mandatoryFieldMissing = true;
            paymentTV.setTextColor(Color.RED);
        }
        // harish - 25.05

        EditText note = (EditText) findViewById(R.id.optionalNote);
        String noteValue = note.getText().toString();

        TextView recurringTransaction = (TextView) findViewById(R.id.recurringSwitch);
        String recurringTransactionValue = String.valueOf(recurringTransaction.getText().toString());

        TextView recurringFrequency = (TextView) findViewById(R.id.recurringValue);
        String recurringFrequencyValue = String.valueOf(recurringFrequency.getText().toString());

        TextView recurringValue = (TextView) findViewById(R.id.recurringValue);
        String recurringValueValue = String.valueOf(recurringValue.getText().toString());

        // harish - 25.05
        RadioGroup transactiontype = (RadioGroup) findViewById(R.id.radioTransactionType);
        if(transactiontype.getCheckedRadioButtonId() == R.id.radioIncome){
            indicatorValue = "Income";
        }else{
            indicatorValue = "Expense";
        }


        if(mandatoryFieldMissing == false){
            // harish - 25.05

            DatabaseIncomeExpense databaseIncomeExpense = new DatabaseIncomeExpense(this);

            //harish-25.05
            boolean isTransactionSaved = databaseIncomeExpense.addData(categoryValue,transactionDateValue,amountValue,
                    codeValue,paymMethodValue,noteValue,indicatorValue, recurringTransactionValue, recurringFrequencyValue, recurringValueValue);

            // display transaction is saved and clear the fields
            if(isTransactionSaved) {
                Toast.makeText(getApplicationContext(), "Transaction is saved", Toast.LENGTH_SHORT).show();

                dbCategories = new Categories(this);
                List<String> categorylist = dbCategories.getData();
                categorylist.add(0, "");

                ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, categorylist);
                categoryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                category.setAdapter(categoryAdapter);

                List<String> paymentList = new ArrayList<String>();
                paymentList.add(" ");
                paymentList.add("Cash");
                paymentList.add("Debit Card");
                paymentList.add("Credit Card");

                ArrayAdapter<String> paymentAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, paymentList);
                paymentAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                paymentMethod.setAdapter(paymentAdapter);

                transactionDate.setText("");
                amount.setText("");
                note.setText("");
                recurringTransaction.setText("");
                recurringFrequency.setText("");
                recurringValue.setText("");
            }
        }
        else{
            // harish - 25.05
            Toast.makeText(getApplicationContext(), "Please fill the mandatory fields", Toast.LENGTH_SHORT).show();
            // harish - 25.05
        }
    }
    // harish - 25.05

    //Logesh 22.05

    public void onLogin(View view){
        // get the value of PIN from Database
        // verify against the currently entered user PIN
        // if same redirect to Income/Expense/Summary page
        // else display an error message

        EditText pinToAuth = (EditText) findViewById(R.id.pinTextBox);
        String pinToCheck = pinToAuth.getText().toString();

        dbPinTable = new DatabaseHandler(this);
        Cursor cursor = dbPinTable.getPinData();

        // check if a value of PIN exists in dbTable - PIN
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String pinSavedInDB = cursor.getString(0).toString();
                if (pinSavedInDB.equals(pinToCheck)) {
                    setContentView(R.layout.income_or_expense);
                }
                else
                {
                    TextView incorrectPin = (TextView) findViewById(R.id.incorrectPin);
                    incorrectPin.setText("PIN entered is wrong, please check");
                }
            }
        }
    }

    @Override
    public void onClick(View v) {

        DialogFragment datePicker = new com.example.exspendables.DatePicker();

        switch (v.getId()){
            case R.id.set_btn:
                // harish - 25.05
                Toast.makeText(this,"Button clicked",Toast.LENGTH_SHORT).show();
                // harish - 25.05
                EditText pinValue = (EditText) findViewById(R.id.set_Pin_textbox);
                EditText reEnterPinValue = (EditText) findViewById(R.id.confirm_Pin_textbox);

                String pin = pinValue.getText().toString();
                String pinToConfirm = reEnterPinValue.getText().toString();

                if(pin.equals(pinToConfirm)){
                    dbPinTable = new DatabaseHandler(this);
                    dbPinTable.addData(pin);
                    // harish - 25.05
                    Toast.makeText(getApplicationContext(), "PIN details saved", Toast.LENGTH_SHORT).show();
                    // harish - 25.05
                    // Redirect to activity where user is prompted to
                    // 1. Enter income 2. Expense 3. View summary
                    setContentView(R.layout.income_or_expense);

                }
                else
                {
                    // PIN does not match, display an error message next to Text box
                    TextView pin_no_match = (TextView)findViewById(R.id.pin_no_match);
                    pin_no_match.setText("PIN do not match, please re-enter");

                }
                break;

            case R.id.edit_categories:

                setContentView(R.layout.category_popup);

                ListView categoryList = (ListView) findViewById(R.id.categorylist);
                Button deleteCategory = (Button) findViewById(R.id.deleteCategoryBtn);
                Button modifyCategory = (Button) findViewById(R.id.modifyCategoryBtn);
                Button addCategory = (Button) findViewById(R.id.addCategoryBtn);

                dbCategories = new Categories(this);
                // Populate Category List View
                List<String> categorylist = dbCategories.getData();
                String[] values = new String[categorylist.size()];
                for(int i = 0; i < categorylist.size();i++){
                    values[i] = categorylist.get(i).toString();
                }
                ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>
                        (this,android.R.layout.simple_list_item_multiple_choice,values);
                categoryList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                categoryList.setAdapter(categoryAdapter);

                deleteCategory.setOnClickListener(this);
                modifyCategory.setOnClickListener(this);
                addCategory.setOnClickListener(this);
                break;

            case  R.id.deleteCategoryBtn:
                dbCategories = new Categories(this);
                categoryList = (ListView) findViewById(R.id.categorylist);
                SparseBooleanArray checked = categoryList.getCheckedItemPositions();
                ArrayList<String> selectedItems = new ArrayList<String>();

                categorylist = dbCategories.getData();
                values = new String[categorylist.size()];
                for(int i = 0; i < categorylist.size();i++){
                    values[i] = categorylist.get(i).toString();
                }
                categoryAdapter = new ArrayAdapter<String>
                        (this,android.R.layout.simple_list_item_multiple_choice,values);

                for (int i = 0; i < checked.size(); i++) {
                    // Item position in adapter
                    int position = checked.keyAt(i);
                    // Add sport if it is checked i.e.) == TRUE!
                    if (checked.valueAt(i)) {
                        //          String dummy1 = selectedItems.get(i).toString();
                        //         String dummy2 = selectedItems.get(position).toString();
                        dbCategories.deleteData(categoryAdapter.getItem(position).toString());

                    }
                }

                // harish - 25.05
                if(checked.size() == 1){
                    Toast.makeText(getApplicationContext(), "Category is deleted", Toast.LENGTH_SHORT).show();
                }
                else if(checked.size() > 1){
                    Toast.makeText(getApplicationContext(), "Categories are deleted", Toast.LENGTH_SHORT).show();
                }
                // harish - 25.05

                //refresh the list with new value
                categoryList = (ListView) findViewById(R.id.categorylist);

                dbCategories = new Categories(this);
                // Populate Category List View
                categorylist = dbCategories.getData();
                values = new String[categorylist.size()];
                for(int i = 0; i < categorylist.size();i++){
                    values[i] = categorylist.get(i).toString();
                }

                categoryAdapter = new ArrayAdapter<String>
                        (this,android.R.layout.simple_list_item_multiple_choice,values);
                categoryList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                categoryList.setAdapter(categoryAdapter);
                break;


            case R.id.addCategoryBtn:
                // create a layout with a EditText and OK button on click on "add"
                setContentView(R.layout.add_category);
                Button okAddCategory = (Button) findViewById(R.id.btnOKAddCategory);
                okAddCategory.setOnClickListener(this);
                break;

            case R.id.btnOKAddCategory:
                dbCategories = new Categories(this);
                EditText newCategoryValue = (EditText) findViewById(R.id.add_cat_textbox);
                dbCategories.addData(newCategoryValue.getText().toString());
                // harish - 25.05
                Toast.makeText(getApplicationContext(), "New category added", Toast.LENGTH_SHORT).show();
                // harish - 25.05
                setContentView(R.layout.category_popup);

                //refresh the list with new value
                categoryList = (ListView) findViewById(R.id.categorylist);

                deleteCategory = (Button) findViewById(R.id.deleteCategoryBtn);
                modifyCategory = (Button) findViewById(R.id.modifyCategoryBtn);
                addCategory = (Button) findViewById(R.id.addCategoryBtn);

                deleteCategory.setOnClickListener(this);
                modifyCategory.setOnClickListener(this);
                addCategory.setOnClickListener(this);

                dbCategories = new Categories(this);
                // Populate Category List View
                categorylist = dbCategories.getData();
                values = new String[categorylist.size()];
                for(int i = 0; i < categorylist.size();i++){
                    values[i] = categorylist.get(i).toString();
                }

                categoryAdapter = new ArrayAdapter<String>
                        (this,android.R.layout.simple_list_item_multiple_choice,values);
                categoryList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                categoryList.setAdapter(categoryAdapter);
                break;

            case R.id.modifyCategoryBtn:
                // create a layout with a EditText and OK button on click on "Modify"

                dbCategories = new Categories(this);
                categoryList = (ListView) findViewById(R.id.categorylist);
                checked = categoryList.getCheckedItemPositions();
                //selectedItems = new ArrayList<String>();

                checked = categoryList.getCheckedItemPositions();
                //selectedItems = new ArrayList<String>();

                categorylist = dbCategories.getData();
                values = new String[categorylist.size()];
                for(int i = 0; i < categorylist.size();i++){
                    values[i] = categorylist.get(i).toString();
                }
                categoryAdapter = new ArrayAdapter<String>
                        (this,android.R.layout.simple_list_item_multiple_choice,values);

                for (int i = 0; i < checked.size(); i++) {
                    // Item position in adapter
                    int position = checked.keyAt(i);
                    // Add sport if it is checked i.e.) == TRUE!
                    if (checked.valueAt(i)) {
                        //          String dummy1 = selectedItems.get(i).toString();
                        //         String dummy2 = selectedItems.get(position).toString();
                        oldValue = categoryAdapter.getItem(position).toString();
                        break;
                    }
                }

                setContentView(R.layout.modify_category);
                EditText valueToBeModified = (EditText) findViewById(R.id.modify_cat_textbox);
                valueToBeModified.setText(oldValue);

                Button okModifyButton = (Button) findViewById(R.id.btnOKModifyCategory);
                okModifyButton.setOnClickListener(this);
                break;

            case R.id.btnOKModifyCategory:

                EditText newValue = (EditText) findViewById(R.id.modify_cat_textbox);
                dbCategories = new Categories(this);
                dbCategories.modifyData(newValue.getText().toString(),oldValue);
                // harish - 25.05
                Toast.makeText(getApplicationContext(), "Changes saved", Toast.LENGTH_SHORT).show();
                // harish - 25.05
                setContentView(R.layout.category_popup);
                //refresh the list with new value
                categoryList = (ListView) findViewById(R.id.categorylist);

                deleteCategory = (Button) findViewById(R.id.deleteCategoryBtn);
                modifyCategory = (Button) findViewById(R.id.modifyCategoryBtn);
                addCategory = (Button) findViewById(R.id.addCategoryBtn);

                deleteCategory.setOnClickListener(this);
                modifyCategory.setOnClickListener(this);
                addCategory.setOnClickListener(this);

                dbCategories = new Categories(this);
                // Populate Category List View
                categorylist = dbCategories.getData();
                values = new String[categorylist.size()];
                for(int i = 0; i < categorylist.size();i++){
                    values[i] = categorylist.get(i).toString();
                }
                categoryAdapter = new ArrayAdapter<String>
                        (this,android.R.layout.simple_list_item_multiple_choice,values);
                categoryList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                categoryList.setAdapter(categoryAdapter);
                break;

            case R.id.changePin:
                setContentView(R.layout.change_pin);

                Button changePinOkBtn = (Button) findViewById(R.id.change_pin_ok);
                changePinOkBtn.setOnClickListener(this);
                break;

            case R.id.change_pin_ok:

                pinValue = (EditText) findViewById(R.id.set_Pin_textbox);
                reEnterPinValue = (EditText) findViewById(R.id.confirm_Pin_textbox);

                pin = pinValue.getText().toString();
                pinToConfirm = reEnterPinValue.getText().toString();


                if (pin.equals(pinToConfirm)) {
                    dbPinTable = new DatabaseHandler(this);
                    Cursor cursor = dbPinTable.getPinData();

                    // check if a value of PIN exists in dbTable - PIN
                    if (cursor != null) {
                        if (cursor.moveToFirst()) {
                            String pinSavedInDB = cursor.getString(0).toString();
                            dbPinTable.modifyData(pin, pinSavedInDB);
                            // harish - 25.05
                            Toast.makeText(getApplicationContext(), "Changes saved", Toast.LENGTH_SHORT).show();
                            // harish - 25.05
                            setContentView(R.layout.settings);
                            Button categoryDisplay = (Button) findViewById(R.id.edit_categories);
                            categoryDisplay.setOnClickListener(this);

                            Button changePinBtn = (Button) findViewById(R.id.changePin);
                            changePinBtn.setOnClickListener(this);

                            break;
                        }
                    }
                }
                else{
                    // PIN does not match, display an error message next to Text box
                    TextView pin_no_match = (TextView)findViewById(R.id.pin_no_match);
                    pin_no_match.setText("PIN do not match, please re-enter");
                    break;
                }
        }
    }

    public void backToHome(View view) {
        setContentView(R.layout.income_or_expense);
    }

    public void backToSettings(View view){
        setContentView(R.layout.settings);

        Button categoryDisplay = (Button) findViewById(R.id.edit_categories);
        categoryDisplay.setOnClickListener(this);

        Button changePinBtn = (Button) findViewById(R.id.changePin);
        changePinBtn.setOnClickListener(this);
    }

    public void backToCategory(View view){
        setContentView(R.layout.category_popup);

        Button deleteCategory = (Button) findViewById(R.id.deleteCategoryBtn);
        Button modifyCategory = (Button) findViewById(R.id.modifyCategoryBtn);
        Button addCategory = (Button) findViewById(R.id.addCategoryBtn);

        deleteCategory = (Button) findViewById(R.id.deleteCategoryBtn);
        modifyCategory = (Button) findViewById(R.id.modifyCategoryBtn);
        addCategory = (Button) findViewById(R.id.addCategoryBtn);

        deleteCategory.setOnClickListener(this);
        modifyCategory.setOnClickListener(this);
        addCategory.setOnClickListener(this);
    }
}

