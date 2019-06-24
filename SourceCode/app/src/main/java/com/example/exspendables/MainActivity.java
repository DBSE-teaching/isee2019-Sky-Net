package com.example.exspendables;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.util.SparseBooleanArray;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mynameismidori.currencypicker.CurrencyPicker;
import com.mynameismidori.currencypicker.CurrencyPickerListener;


import java.sql.Array;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    DatabaseHandler dbPinTable;     // database table for storing PIN
    Categories dbCategories;
    private Button btnSavePin;      // access Save PIN button during First time user login
    private boolean isEntryDateClicked = false;
    private boolean isEndDateClicked = false;
    public static String oldValue;

    public static String startDateValue = null;
    public static String endDateValue = null;

    private ArrayList<CategoryIcon> mCategoryList;
    private IconAdapter mAdapter;
    protected DrawerLayout drawer;


    // main() method of UI
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbPinTable = new DatabaseHandler(this);
        Cursor cursor = dbPinTable.getPinData();




        // check if a value of PIN exists in dbTable - PIN
        // abhivanth , changed "login_pin" to
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                //launch authenticate PIN page
                //setContentView(R.layout.login_page);
                setContentView(R.layout.activity_main);
                Toolbar toolbar = findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                drawer = findViewById(R.id.drawer_layout);
                NavigationView navigationView = findViewById(R.id.nav_view);
                navigationView.setNavigationItemSelectedListener(this);

                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawer.addDrawerListener(toggle);
                toggle.syncState();
                drawer.setDrawerLockMode(1);
                View view1= null;
                FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
                contentFrameLayout.removeAllViewsInLayout();
                getLayoutInflater().inflate(R.layout.login_page, contentFrameLayout);


            } else {
                // changed income/expense to activity main - abhivanth
                // if PIN does not exist, launch first time login page
                setContentView(R.layout.activity_main);
                Toolbar toolbar = findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                drawer = findViewById(R.id.drawer_layout);
                NavigationView navigationView = findViewById(R.id.nav_view);
                navigationView.setNavigationItemSelectedListener(this);


                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                        R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawer.addDrawerListener(toggle);
                toggle.syncState();
                if (savedInstanceState == null) {
                    View view= null;
                    FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
                    contentFrameLayout.removeAllViewsInLayout();
                    this.openExpensePage(view);
                    //getLayoutInflater().inflate(R.layout.expense, contentFrameLayout);
                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new
                    //      transactionFragment()).commit();
                    navigationView.setCheckedItem(R.id.nav_transaction);
                }                // btnSavePin = (Button) findViewById(R.id.set_btn);
                //btnSavePin.setOnClickListener(this);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_transaction:
                View view= null;

                FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
                contentFrameLayout.removeAllViewsInLayout();
                //getLayoutInflater().inflate(R.layout.expense, contentFrameLayout);
                this.openExpensePage(view);


                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new
                //transactionFragment()).commit();
                break;
            case R.id.nav_summary:
                View view1= null;
                FrameLayout contentFrameLayout1 = (FrameLayout) findViewById(R.id.fragment_container);
                contentFrameLayout1.removeAllViewsInLayout();
                this.openSummaryPage(view1);
                //getLayoutInflater().inflate(R.layout.prompt_filter_ddlb, contentFrameLayout1);
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new
                //      summaryFragment()).commit();
                break;
            case R.id.nav_settings:
                View view2= null;
                FrameLayout contentFrameLayout2 = (FrameLayout) findViewById(R.id.fragment_container);
                contentFrameLayout2.removeAllViewsInLayout();
                this.openSettingsPage(view2);
                //getLayoutInflater().inflate(R.layout.settings, contentFrameLayout2);
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new
                //      settingsFragment()).commit();
                break;
            case R.id.nav_graph:
                View view3= null;
                FrameLayout contentFrameLayout3 = (FrameLayout) findViewById(R.id.fragment_container);
                contentFrameLayout3.removeAllViewsInLayout();
                this.openGraphSummaryPage(view3);
                //getLayoutInflater().inflate(R.layout.view_summary, contentFrameLayout3);
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new
                //      graphFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    // Graph Summary - Logesh

    public void openGraphSummaryPage(View view) {
        //setContentView(R.layout.view_summary);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        contentFrameLayout.removeAllViewsInLayout();
        getLayoutInflater().inflate(R.layout.graph_summary, contentFrameLayout);

        Categories dbCategories;
        dbCategories = new Categories(this);
        final Spinner categoryList = findViewById(R.id.categoryList);
        final List<String> categorylist = dbCategories.getData();
        categorylist.add(0, "");
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, categorylist);
        categoryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        categoryList.setAdapter(categoryAdapter);

        Button buttonStartDate = findViewById(R.id.filterStartDate);
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


        Button buttonEndDate = findViewById(R.id.filterEndDate);
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
        final Button button = findViewById(R.id.okSubmitButton);
        final Transactions transactions = new Transactions(this);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //changeToDateFormat("Mon Jun 03 00:00:00 GMT+02:00 2019");

                Spinner spinner = findViewById(R.id.filter);
                TextView startDate = findViewById(R.id.entryDate);
                TextView byDate = findViewById(R.id.byDate);
                SimpleDateFormat Formatter = new SimpleDateFormat("yyyy/MM/dd");


                TextView endDate = findViewById(R.id.endDate);
                EditText amountFilter = findViewById(R.id.enterAmount);
                String amountValue = amountFilter.getText().toString();


                Spinner selectPaymentMethod = findViewById(R.id.selectPaymentMethod);
                Spinner selectOperator = findViewById(R.id.selectOperator);
                String catList = String.valueOf(categoryList.getSelectedItem());
                String payList = String.valueOf(selectPaymentMethod.getSelectedItem());
                String operatorList = String.valueOf(selectOperator.getSelectedItem());
                //Float amountValue1 = Float.parseFloat(amountValue);


                loop:{
                    if (startDate.getText() != "" && endDate.getText() != "" & catList.equals("") && payList.equals("") && operatorList.equals("") && amountValue.equals("") ) {

                        //setContentView(R.layout.barchart);
                        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
                        contentFrameLayout.removeAllViewsInLayout();
                        getLayoutInflater().inflate(R.layout.barchart, contentFrameLayout);

                        String startDateValue = startDate.getText().toString();
                        String endDateValue = endDate.getText().toString();


                        BarChart chart;
                        final ArrayList<BarEntry> BARENTRY;
                        final ArrayList<Integer> Entries = new ArrayList<>();
                        String label = null;
                        ArrayList<String> BarEntryLabels;
                        BarDataSet Bardataset;
                        BarData BARDATA;
                        chart = findViewById(R.id.barGraph);


                        BARENTRY = new ArrayList<>();
                        BarEntryLabels = new ArrayList<String>();

                        SQLiteDatabase sdb = transactions.getReadableDatabase();
                        String sql = "Select startDate, amount, category from TRANSACTIONS " +
                                "where startDate between '" +startDateValue+"' and '"+ endDateValue + "'" + "ORDER BY startDate" ;

                        Cursor c = sdb.rawQuery(sql, null);
                        final int count = c.getCount();
                        c.moveToFirst();
                        String date;
                        //int amount;
                        float amount;
                        String cat;
                        final XAxis xAxis = chart.getXAxis();


                        for (int m = 0; m < count; m++) {
                            date = c.getString(0);
                            //Date date1 = Date.valueOf(date);
                            //amount = c.getInt(1);
                            amount = c.getFloat(1);
                            cat = c.getString(2);
                            date = date.replaceAll("-", "");
                            int j = Integer.valueOf(date);
                            String p = date.substring(date.length() - 6) ;
                            int i = Integer.valueOf(p);
                            BARENTRY.add(new BarEntry(i, amount));
                        /*xAxis.mEntries = new float[
                                Entries.add(i, m[i]) ]; */
                            c.moveToNext();
                        }

                        Bardataset = new BarDataSet(BARENTRY, "Expenses");
                        BARDATA = new BarData(Bardataset);
                        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                        chart.setData(BARDATA);

                        chart.animateXY(3000, 3000);
                        chart.getAxisLeft().setAxisMinimum(0);
                        chart.getAxisRight().setAxisMinimum(0);
                        chart.setPinchZoom(false);
                        float barWidth = 0.25f;
                        chart.setFitBars(true);
                        BARDATA.setBarWidth(barWidth);
                        //final XAxis xAxis = chart.getXAxis();
                        xAxis.setGranularity(1f);
                        xAxis.mEntryCount = count;
                        xAxis.mAxisRange = 1f;
                        chart.invalidate();
                        //xAxis.setLabelCount(count);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setLabelRotationAngle(-90);

                        chart.invalidate();
                        xAxis.setValueFormatter(new IAxisValueFormatter() {

                            SimpleDateFormat mFormat = new SimpleDateFormat("yyMMdd");
                            SimpleDateFormat mFormat1 = new SimpleDateFormat("MMM dd");


                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {

                                int g = Math.round(value);
                                String f = String.valueOf(g);
                                java.util.Date dateValue = null;

                                try {
                                    dateValue = mFormat.parse(f);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                return mFormat1.format(dateValue);

                            }

                        });

                        chart.invalidate();
                    }
                    else if (startDate.getText().equals("") && endDate.getText().equals("") && catList.equals("")&& payList.equals("") && operatorList.equals("") && amountValue.equals("")){

                        Toast.makeText(getApplicationContext(), "Select any value to display chart", Toast.LENGTH_LONG).show();
                    }

                    else if ((startDate.getText() != "" && endDate.getText().equals("")) || (startDate.getText().equals("") && endDate.getText() !="")){

                        Toast.makeText(getApplicationContext(), "Select both start date & end date", Toast.LENGTH_LONG).show();
                    }

                    else if ((!operatorList.equals("") && amountValue.equals("")) || (operatorList.equals("") && !amountValue.equals(""))){

                        Toast.makeText(getApplicationContext(), "Select both operator and amount value", Toast.LENGTH_LONG).show();
                    }


                    else if (!catList.equals("") || startDate.getText() != "" || endDate.getText() != "" || !payList.equals("") || operatorList.equals("Greater than") || operatorList.equals("Lesser than") || !amountValue.equals("")) {

                        //setContentView(R.layout.piechart_paymethod);

                        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
                        contentFrameLayout.removeAllViewsInLayout();
                        //getLayoutInflater().inflate(R.layout.piechart, contentFrameLayout);

                        String startDatePie = startDate.getText().toString();
                        String endDatePie = endDate.getText().toString();

                        PieChart mChart = null;
                        SQLiteDatabase db = transactions.getReadableDatabase();

                        String sql = null;

                        if (startDate.getText().equals("") && endDate.getText().equals("") && !catList.equals("") && payList.equals("") && operatorList.equals("") && amountValue.equals("")) {
                            sql = "Select category, amount from TRANSACTIONS where category ='" + catList + "'";
                            getLayoutInflater().inflate(R.layout.piechart, contentFrameLayout);
                            mChart = findViewById(R.id.PieChart);

                        }else if (startDate.getText().equals("") && endDate.getText().equals("") && catList.equals("") && payList.equals("") && operatorList.equals("Greater than") && 		!amountValue.equals("")) {
                            sql = "Select category, amount from TRANSACTIONS where amount > '" + amountValue + "'";
                            getLayoutInflater().inflate(R.layout.piechart, contentFrameLayout);
                            mChart = findViewById(R.id.PieChart);

                        }else if (startDate.getText().equals("") && endDate.getText().equals("") && catList.equals("") && payList.equals("") && operatorList.equals("Lesser than") && 		!amountValue.equals("")) {
                            sql = "Select category, amount from TRANSACTIONS where amount < '" + amountValue + "'";
                            getLayoutInflater().inflate(R.layout.piechart, contentFrameLayout);
                            mChart = findViewById(R.id.PieChart);

                        }else if (startDate.getText() != "" && endDate.getText() != "" && !catList.equals("") && payList.equals("") && operatorList.equals("") && amountValue.equals("")) {
                            sql = "Select category, amount from TRANSACTIONS where startDate between '" + startDatePie + "' and '" + endDatePie + "' and category = '" + catList + "'";
                            getLayoutInflater().inflate(R.layout.piechart, contentFrameLayout);
                            mChart = findViewById(R.id.PieChart);

                        }else if (startDate.getText() != "" && endDate.getText() != "" && catList.equals("") && operatorList.equals("Greater than") && !amountValue.equals("") ) {
                            sql = "Select category, amount from TRANSACTIONS where startDate between '" + startDatePie + "' and '" + endDatePie + "' and amount > '" + amountValue + "'";
                            getLayoutInflater().inflate(R.layout.piechart, contentFrameLayout);
                            mChart = findViewById(R.id.PieChart);

                        }else if (startDate.getText() != "" && endDate.getText() != "" && catList.equals("") && operatorList.equals("Lesser than") && !amountValue.equals("") ) {
                            sql = "Select category, amount from TRANSACTIONS where startDate between '" + startDatePie + "' and '" + endDatePie + "' and amount < '" + amountValue + "'";
                            getLayoutInflater().inflate(R.layout.piechart, contentFrameLayout);
                            mChart = findViewById(R.id.PieChart);

                        }else if (startDate.getText() != "" && endDate.getText() != "" && catList.equals("") && !payList.equals("") && operatorList.equals("") && amountValue.equals("")) {
                            sql = "Select paymentMethod, amount from TRANSACTIONS where startDate between '" + startDatePie + "' and '" + endDatePie + "' and paymentMethod ='" + payList + "'";
                            getLayoutInflater().inflate(R.layout.piechart_paymethod, contentFrameLayout);
                            mChart = findViewById(R.id.PieChart1);

                        }else if (startDate.getText().equals("") && endDate.getText().equals("") && !catList.equals("") && payList.equals("") && operatorList.equals("Greater than") && !amountValue.equals("")) {
                            sql = "Select category, amount from TRANSACTIONS where category = '" + catList + "' and amount > '" + amountValue + "'";
                            getLayoutInflater().inflate(R.layout.piechart, contentFrameLayout);
                            mChart = findViewById(R.id.PieChart);

                        }else if (startDate.getText().equals("") && endDate.getText().equals("") && !catList.equals("") && payList.equals("") && operatorList.equals("Lesser than") && !amountValue.equals("")) {
                            sql = "Select category, amount from TRANSACTIONS where category = '" + catList + "' and amount < '" + amountValue + "'";
                            getLayoutInflater().inflate(R.layout.piechart, contentFrameLayout);
                            mChart = findViewById(R.id.PieChart);

                        }else if (startDate.getText().equals("") && endDate.getText().equals("") && !catList.equals("") && !payList.equals("") && operatorList.equals(" ") && amountValue.equals("")) {
                            sql = "Select category, amount from TRANSACTIONS where category = '" + catList + "' and paymentMethod = '" + payList + "'" ;
                            getLayoutInflater().inflate(R.layout.piechart, contentFrameLayout);
                            mChart = findViewById(R.id.PieChart);

                        }else if (startDate.getText().equals("") && endDate.getText().equals("") && catList.equals("") && operatorList.equals("Greater than") && !amountValue.equals("") && !payList.equals("")) {
                            sql = "Select paymentMethod, amount from TRANSACTIONS where paymentMethod = '" + payList + "'and amount > '" + amountValue + "'";
                            getLayoutInflater().inflate(R.layout.piechart_paymethod, contentFrameLayout);
                            mChart = findViewById(R.id.PieChart1);

                        }else if (startDate.getText().equals("") && endDate.getText().equals("") && catList.equals("") && operatorList.equals("Lesser than") && !amountValue.equals("") && 	!payList.equals("")) {
                            sql = "Select category, amount from TRANSACTIONS where paymentMethod = '" + payList + "' and amount < '" + amountValue + "'";
                            getLayoutInflater().inflate(R.layout.piechart_paymethod, contentFrameLayout);
                            mChart = findViewById(R.id.PieChart1);

                        }else if (startDate.getText() != "" && endDate.getText() != "" && !catList.equals("") && operatorList.equals("Greater than") && !amountValue.equals("") && payList.equals("")) {
                            sql = "Select category, amount from TRANSACTIONS where startDate between '" + startDatePie + "' and '" + endDatePie + "' and category = '" + catList + "' and amount > '" + amountValue + "'";
                            getLayoutInflater().inflate(R.layout.piechart, contentFrameLayout);
                            mChart = findViewById(R.id.PieChart);

                        }else if (startDate.getText() != "" && endDate.getText() != "" && !catList.equals("") && operatorList.equals("Lesser than") && !amountValue.equals("") && payList.equals("")) {
                            sql = "Select category, amount from TRANSACTIONS where startDate between '" + startDatePie + "' and '" + endDatePie + "' and category = '" + catList + "' and amount < '" + amountValue + "'";
                            getLayoutInflater().inflate(R.layout.piechart, contentFrameLayout);
                            mChart = findViewById(R.id.PieChart);

                        }else if (startDate.getText() != "" && endDate.getText() != "" && !catList.equals("") && operatorList.equals("") && amountValue.equals("") && !payList.equals("")){
                            sql = "Select category, amount from TRANSACTIONS where startDate between '" + startDatePie + "' and '" + endDatePie + "' and category = '" + catList + "' and paymentMethod = '" + payList + "'";
                            getLayoutInflater().inflate(R.layout.piechart, contentFrameLayout);
                            mChart = findViewById(R.id.PieChart);

                        }else if (startDate.getText() != "" && endDate.getText() != "" && catList.equals("") && operatorList.equals("Greater than") && 		!amountValue.equals("") && !payList.equals("")) {
                            sql = "Select category, amount from TRANSACTIONS where startDate between '" + startDatePie + "' and '" + endDatePie + "' and amount > '" + amountValue + "' and paymentMethod = '" + payList + "'";
                            getLayoutInflater().inflate(R.layout.piechart, contentFrameLayout);
                            mChart = findViewById(R.id.PieChart);

                        }else if (startDate.getText() != "" && endDate.getText() != "" && catList.equals("") && operatorList.equals("Lesser than") && 		!amountValue.equals("") && !payList.equals("")) {
                            sql = "Select category, amount from TRANSACTIONS where startDate between '" + startDatePie + "' and '" + endDatePie + "' and amount < '" + amountValue + "' and paymentMethod = '" + payList + "'";
                            getLayoutInflater().inflate(R.layout.piechart, contentFrameLayout);
                            mChart = findViewById(R.id.PieChart);

                        }else if (startDate.getText() != "" && endDate.getText() != "" && !catList.equals("") && operatorList.equals("Greater than") && 	!amountValue.equals("") && !payList.equals("")) {
                            sql = "Select category, amount from TRANSACTIONS where startDate between '" + startDatePie + "' and '" + endDatePie + "' and category = '" + catList + "' and paymentMethod = '" + payList + "' and amount > '" + amountValue + "'";
                            getLayoutInflater().inflate(R.layout.piechart, contentFrameLayout);
                            mChart = findViewById(R.id.PieChart);

                        }else if (startDate.getText() != "" && endDate.getText() != "" && !catList.equals("") && operatorList.equals("Greater than") && 	!amountValue.equals("") ) {
                            sql = "Select category, amount from TRANSACTIONS where startDate between '" + startDatePie + "' and '" + endDatePie + "' and category = '" + catList + "' and amount > '" + amountValue + "'";
                            getLayoutInflater().inflate(R.layout.piechart, contentFrameLayout);
                            mChart = findViewById(R.id.PieChart);

                        }

                        final Cursor c = db.rawQuery(sql, null);
                        int count = c.getCount();
                        c.moveToFirst();

                        double[] values = new double[count];
                        String[] categoryNames = new String[count];
                        int[] colors = new int[count];

                        for (int m = 0; m < count; m++) {
                            categoryNames[m] = c.getString(0);
                            values[m] = c.getDouble(1);
                            colors[m] = c.getInt(0);
                            c.moveToNext();
                        }

                        ArrayList<PieEntry> yVals1 = new ArrayList<PieEntry>();

                        for (int i = 0; i < categoryNames.length; i++) {
                            yVals1.add(new PieEntry((float) (values[i]), i));
                        }

                        ArrayList<String> xVals = new ArrayList<String>();//array legend

                        for (int i = 0; i < categoryNames.length; i++) {
                            xVals.add(categoryNames[i]);
                            String xVals1 = xVals.toString();
                            PieDataSet set1 = new PieDataSet(yVals1, xVals1);
                            set1.setSliceSpace(3f);
                            set1.setValueTextSize(15f);
                            set1.setColors(ColorTemplate.createColors(colors));

                            PieData data = new PieData(set1);
                            set1.setColors(ColorTemplate.COLORFUL_COLORS);
                            mChart.setData(data);
                            // undo all highlights
                            mChart.highlightValues(null);
                            mChart.setCenterTextSize(20f);
                            mChart.setEntryLabelTextSize(20f);
                            mChart.invalidate();
                        }
                        c.close();
                        db.close();

                    }
                }

            }
        });
    }
//Logesh

    public String changeToDateFormat(String date) {
        // Mon Jun 03 00:00:00 GMT+02:00 2019

        String month = date.substring(4, 7);
        String dateVal = date.substring(8, 10);
        String year = date.substring(29, 34);

        switch (month) {
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

    // Event handler for button "Enter Income"
    public void openIncomePage(View view) {

        //setContentView(R.layout.income);

        //FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);//Remember this is the FrameLayout area within your activity_main.xml
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new
        // incomeFragment()).commit();
        //getLayoutInflater().inflate(R.layout.income, contentFrameLayout);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        contentFrameLayout.removeAllViewsInLayout();
        getLayoutInflater().inflate(R.layout.income, contentFrameLayout);


        Button button = findViewById(R.id.selectDate);
        button.setOnClickListener(new View.OnClickListener() {
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

        Currency currency = new Currency(this);
        Cursor currCode = currency.getData();

        if (currCode != null) {
            if (currCode.moveToFirst()) {
                String currencySavedInDB = currCode.getString(0);
                TextView code = findViewById(R.id.currencyCode);
                code.setText(currencySavedInDB);
            }
        }
    }

    // Event handler for button "Enter Expense"
    public void openExpensePage(View view)  {

        //setContentView(R.layout.expense);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        contentFrameLayout.removeAllViewsInLayout();
        getLayoutInflater().inflate(R.layout.expense, contentFrameLayout);
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new transactionFragment()).commit();

        Categories dbCategories;
        dbCategories = new Categories(this);
        // Populate Category DDLB
        Spinner categoryddlb = findViewById(R.id.categoryddlb);
        List<String> categorylist = dbCategories.getData();
        categorylist.add(0, "");
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, categorylist);
        categoryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        categoryddlb.setAdapter(categoryAdapter);

        /*for(int i = 0;i < categorylist.size();i++){
            categoryAdapter.add(categorylist.get(i).toString());
            categoryAdapter.notifyDataSetChanged();
        }*/

        Button button = findViewById(R.id.selectDate);
        button.setOnClickListener(new View.OnClickListener() {
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

        Currency currency = new Currency(this);
        Cursor currCode = currency.getData();

        if (currCode != null) {
            if (currCode.moveToFirst()) {
                String currencySavedInDB = currCode.getString(0);
                TextView code = findViewById(R.id.currencyCode);
                code.setText(currencySavedInDB);
            }
        }

        //Logesh - 20.05

        final Switch recurringSwitch = findViewById(R.id.recurringSwitch);
        final CharSequence[] items = {"Daily", "Weekly", "Monthly", "Other"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText editText = new EditText(this);
        final TextView textView = findViewById(R.id.recurringValue);
        final TextView textView1 = findViewById(R.id.recurringValue1);

        builder.setView(editText);
        editText.setVisibility(View.INVISIBLE);
        editText.setHint("Ex. 10");
        builder.setTitle("Select any option");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Toast.makeText(getApplicationContext(), items[item].toString(), Toast.LENGTH_SHORT).show();
                if (item == 3) {
                    final String value = items[item].toString();
                    textView.setText(value);
                    editText.setVisibility(View.VISIBLE);
                    textView1.setVisibility(View.VISIBLE);
                    editText.setText("");
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    String value1 = editText.getText().toString();
                    if (value1.isEmpty()) {
                        editText.setError("Enter Value to Proceed");
                        //editText.setText("0");
                        textView1.setText(value1);
                    } else {
                        textView1.setText(value1);
                    }

                } else if (item == 0) {
                    String value = items[item].toString();
                    textView.setText(value);
                    textView1.setVisibility(View.INVISIBLE);
                    editText.setText("");
                    editText.setVisibility(View.INVISIBLE);
                } else if (item == 1) {
                    String value = items[item].toString();
                    textView.setText(value);
                    textView1.setVisibility(View.INVISIBLE);
                    editText.setText("");
                    editText.setVisibility(View.INVISIBLE);
                } else if (item == 2) {
                    String value = items[item].toString();
                    textView.setText(value);
                    textView1.setVisibility(View.INVISIBLE);
                    editText.setText("");
                    editText.setVisibility(View.INVISIBLE);
                } else {

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

                        if (value1.equals("") & value2.equals(View.VISIBLE)) {
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

                if (isChecked == true) {
                    alert.show();
                    textView.setVisibility(View.VISIBLE);
                    textView1.setVisibility(View.VISIBLE);
                } else {
                    alert.cancel();
                    textView.setVisibility(View.INVISIBLE);
                    textView1.setVisibility(View.INVISIBLE);
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

    public void onSetCurrency(View view) {
        final Currency currency = new Currency(this);
        final CurrencyPicker picker = CurrencyPicker.newInstance("Select Currency");  // dialog title
        picker.setListener(new CurrencyPickerListener() {
            @Override
            public void onSelectCurrency(String name, String code, String symbol, int flagDrawableResID) {
                // Implement your code here
                closeOptionsMenu();

                String currency_selected = code;

                // save this value to DB so that it can be displayed next to Amount
                Cursor currencyData = currency.getData();
                if (currencyData != null) {
                    if (currencyData.moveToFirst()) {
                        String currencySavedInDB = currencyData.getString(0);
                        currency.modifyData(currency_selected, currencySavedInDB);
                        // harish - 25.05
                        Toast.makeText(getApplicationContext(), "Selected currency unit is set", Toast.LENGTH_SHORT).show();
                        // harish - 25.05
                    } else {
                        currency.addData(currency_selected);
                        // harish - 25.05
                        Toast.makeText(getApplicationContext(), "Selected currency unit is set", Toast.LENGTH_SHORT).show();
                        // harish - 25.05
                    }
                }
                picker.dismiss();
            }
        });
        picker.show(getSupportFragmentManager(), "CURRENCY_PICKER");
    }


    public void openSummaryPage(View view) {

        //setContentView(R.layout.prompt_filter_ddlb);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        contentFrameLayout.removeAllViewsInLayout();
        getLayoutInflater().inflate(R.layout.graph_summary_list, contentFrameLayout);

        Categories dbCategories;
        dbCategories = new Categories(this);
        final Spinner categoryList = findViewById(R.id.categoryList);
        final List<String> categorylist = dbCategories.getData();
        categorylist.add(0, "");
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, categorylist);
        categoryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        categoryList.setAdapter(categoryAdapter);

        Button buttonStartDate = findViewById(R.id.filterStartDate);
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


        Button buttonEndDate = findViewById(R.id.filterEndDate);
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



        //filterData();
        //loadFilters();
    }

    /*public void filterData(View view) {

        boolean fieldsMissing = false;
        boolean dateSelected = false;
        boolean categorySelected = false;
        boolean amountSelected = false;
        boolean paymentSelected = false;
        String categoryValueSelected = null;
        String paymentValueSelected = null;
        float amountValueSelected = 0;

        TextView entryDateTV = (TextView) findViewById(R.id.entryDate);
        TextView endDateTV = (TextView) findViewById(R.id.endDate);

        // for filter by date
        if(TextUtils.isEmpty(entryDateTV.getText().toString())
                && TextUtils.isEmpty(endDateTV.getText().toString())){
            // keine probleme
        }
        else if(entryDateTV.getText().toString() != ""
                && endDateTV.getText().toString() != ""){
            // keine probleme
            dateSelected = true;
        }
        else{
            fieldsMissing = true;

            if(TextUtils.isEmpty(entryDateTV.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Please set End Date", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Please set Start Date", Toast.LENGTH_SHORT).show();
            }
        }

        // for filter by amount

        Spinner operator = findViewById(R.id.selectOperator);
        int indexSelected = operator.getSelectedItemPosition();

        TextView amountTV = (TextView) findViewById(R.id.enterAmount);

        if(indexSelected > 0 && amountTV.getText().toString() != ""){
            //keine probleme
            amountSelected = true;
            amountValueSelected = Float.valueOf(amountTV.getText().toString());
        }
        else if(indexSelected == 0 && TextUtils.isEmpty(amountTV.getText().toString())){
            //keine probleme
        }
        else{
            fieldsMissing = true;
            if(indexSelected > 0){
                Toast.makeText(getApplicationContext(), "Please enter amount", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Please select an operator", Toast.LENGTH_SHORT).show();
            }
        }

        Spinner category = (Spinner) findViewById(R.id.categoryList);
        int categoryIndexSelected = category.getSelectedItemPosition();

        if(categoryIndexSelected > 0){
            categorySelected = true;
            categoryValueSelected = category.getSelectedItem().toString();
        }

        Spinner payment = (Spinner) findViewById(R.id.selectPaymentMethod);
        int paymentIndexSelected = payment.getSelectedItemPosition();

        if(paymentIndexSelected > 0){
            paymentSelected = true;
            paymentValueSelected = payment.getSelectedItem().toString();
        }

        if(fieldsMissing == false) {
            // load all values from table
            Transactions dbTransactions = new Transactions(this);
            Cursor cursor = dbTransactions.getData();

            StringBuilder builder = new StringBuilder();

            cursor.moveToFirst();
            int i = 0;
            while (!cursor.isAfterLast()) {

                String date = cursor.getString(1);
                String indicator = cursor.getString(6);
                date = date.replaceAll("-", "/");

                if (indicator.equals("Expense")) {
                    builder.append(cursor.getString(0)).append(";")
                            .append(date).append(";")
                            .append(cursor.getString(2)).append(";")
                            .append(cursor.getString(4)).append("_");

                    i++;
                }
                cursor.moveToNext();
            }

            builder.toString();
            String st = new String(builder);
            String[] values = st.split("_");

            ArrayList<String> recordsOK = new ArrayList<String>();

            for (int index = 0; index < values.length; index++) {
                recordsOK.add(values[index]);
            }

            if (dateSelected == true) {

                startDateValue = entryDateTV.getText().toString();
                startDateValue = startDateValue.replaceAll("-", "/");

                endDateValue = endDateTV.getText().toString();
                endDateValue = endDateValue.replaceAll("-", "/");

                SimpleDateFormat Formatter = new SimpleDateFormat("yyyy/MM/dd");

                java.util.Date dateToCheck = null;
                java.util.Date startdateToCheck = null;
                java.util.Date enddateToCheck = null;

                try {
                    startdateToCheck = Formatter.parse(startDateValue);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                startdateToCheck.setDate(startdateToCheck.getDate() - 1);

                try {
                    enddateToCheck = Formatter.parse(endDateValue);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                startdateToCheck.setDate(startdateToCheck.getDate() - 1);
                enddateToCheck.setDate(enddateToCheck.getDate() - 1);

                for (int index = 0; index < recordsOK.size(); index++) {
                    // String[] dummy = values[index].split(";");

                    String[] dummy = recordsOK.get(index).split(";");

                    try {
                        dateToCheck = Formatter.parse(dummy[1]);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (dateToCheck.before(enddateToCheck) && dateToCheck.after(startdateToCheck)) {

                    } else {
                        recordsOK.remove(index);
                    }
                }
            }

            if (categorySelected == true) {

                for (int index = 0; index < recordsOK.size(); index++) {

                    String[] dummy = recordsOK.get(index).split(";");

                    if (dummy[0].equals(categoryValueSelected)) {

                    } else {
                        recordsOK.remove(index);
                    }
                }
            }

            if (paymentSelected == true) {

                for (int index = 0; index < recordsOK.size(); index++) {

                    String[] dummy = recordsOK.get(index).split(";");

                    if (dummy[3].equals(paymentValueSelected)) {

                    } else {
                        recordsOK.remove(index);
                    }
                }
            }

            if (amountSelected == true) {

                String operatorValue = operator.getSelectedItem().toString();

                for (int index = 0; index < recordsOK.size(); index++) {

                    String[] dummy = recordsOK.get(index).split(";");
                    float amountFromDb = Float.valueOf(dummy[2]);

                    if (operatorValue == "Greater than") {
                        if (amountValueSelected > amountFromDb) {

                        } else {
                            recordsOK.remove(index);
                        }
                    } else {
                        if (amountValueSelected < amountFromDb) {

                        } else {
                            recordsOK.remove(index);
                        }
                    }
                }
            }
        }
    }*/

    private void loadFilters() {
        Spinner spinner = findViewById(R.id.selectfilter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = findViewById(R.id.selectfilter);
                int indexSelected = spinner.getSelectedItemPosition();
                switch (indexSelected){
                    case 1: //date range
                        //setContentView(R.layout.prompt_filter_date);
                        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
                        contentFrameLayout.removeAllViewsInLayout();
                        getLayoutInflater().inflate(R.layout.prompt_filter_date, contentFrameLayout);

                        Button button = findViewById(R.id.selectDate);
                        button.setOnClickListener(new View.OnClickListener() {
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


                        Button buttonEndDate = findViewById(R.id.selectEndDate);
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

                        break;

                    case 2: // categories
                        //setContentView(R.layout.prompt_filter_category);
                        FrameLayout contentFrameLayout1 = (FrameLayout) findViewById(R.id.fragment_container);
                        contentFrameLayout1.removeAllViewsInLayout();
                        getLayoutInflater().inflate(R.layout.prompt_filter_category, contentFrameLayout1);

                        Categories dbCategories;
                        dbCategories = new Categories(MainActivity.this);
                        // Populate Category DDLB
                        Spinner categoryddlb = findViewById(R.id.selectCategory);
                        List<String> categorylist = dbCategories.getData();
                        categorylist.add(0, "");
                        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, categorylist);
                        categoryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                        categoryddlb.setAdapter(categoryAdapter);

                        break;

                    case 3: //payment Method
                        //setContentView(R.layout.prompt_filter_paym);

                        FrameLayout contentFrameLayout2 = (FrameLayout) findViewById(R.id.fragment_container);
                        contentFrameLayout2.removeAllViewsInLayout();
                        getLayoutInflater().inflate(R.layout.prompt_filter_paym, contentFrameLayout2);

                        break;

                    case 4: //Amount

                        //setContentView(R.layout.prompt_filter_amount);

                        FrameLayout contentFrameLayout3 = (FrameLayout) findViewById(R.id.fragment_container);
                        contentFrameLayout3.removeAllViewsInLayout();
                        getLayoutInflater().inflate(R.layout.prompt_filter_amount, contentFrameLayout3);

                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void showSummaryByDate(View view) {

        boolean missingMandatoryFields = false;

        TextView startDate = findViewById(R.id.entryDate);

        if (TextUtils.isEmpty(startDate.getText())) {
            missingMandatoryFields = true;
            Toast.makeText(getApplicationContext(), "Please set Start Date", Toast.LENGTH_SHORT).show();
        }

        TextView endDate = findViewById(R.id.endDate);

        if (TextUtils.isEmpty(endDate.getText())) {
            missingMandatoryFields = true;
            Toast.makeText(getApplicationContext(), "Please set End Date", Toast.LENGTH_SHORT).show();
        }

        if(missingMandatoryFields == false) {

            startDateValue = startDate.getText().toString();
            startDateValue = startDateValue.replaceAll("-", "/");

            endDateValue = endDate.getText().toString();
            endDateValue = endDateValue.replaceAll("-", "/");

            SimpleDateFormat Formatter = new SimpleDateFormat("yyyy/MM/dd");

            java.util.Date dateToCheck = null;
            java.util.Date startdateToCheck = null;
            java.util.Date enddateToCheck = null;

            try {
                startdateToCheck = Formatter.parse(startDateValue);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            startdateToCheck.setDate(startdateToCheck.getDate()-1);

            try {
                enddateToCheck = Formatter.parse(endDateValue);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            startdateToCheck.setDate(startdateToCheck.getDate()-1);
            enddateToCheck.setDate(enddateToCheck.getDate()-1);

            //setContentView(R.layout.tablesummary);
            //setContentView(R.layout.listsummary);
            FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
            contentFrameLayout.removeAllViewsInLayout();
            getLayoutInflater().inflate(R.layout.listsummary, contentFrameLayout);

            ListView transactions = findViewById(R.id.transactionlist);
            Button deleteTransaction = findViewById(R.id.deleteTxnBtn);
            deleteTransaction.setOnClickListener(this);


            Transactions databaseIncomeExpense = new Transactions(this);
            Cursor cursor = databaseIncomeExpense.getData();

            // List<Transactions> expenseDetails = new ArrayList<Transactions>();

            List<String> transactionList = null;
            StringBuilder builder = new StringBuilder();

            cursor.moveToFirst();
            int i = 0;
            while (!cursor.isAfterLast()) {

                String date = cursor.getString(1);
                String indicator = cursor.getString(6);
                date = date.replaceAll("-", "/");

                try {
                    dateToCheck = Formatter.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(indicator.equals("Expense")) {
                    if (dateToCheck.before(enddateToCheck) && dateToCheck.after(startdateToCheck)) {

                        builder.append(cursor.getString(0)).append(";")
                                .append(date).append(";")
                                .append(cursor.getString(2)).append(";")
                                .append(cursor.getString(4)).append("_");

                        i++;

                    }
                }
                cursor.moveToNext();
            }

            builder.toString();
            String st = new String(builder);
            String[] values = st.split("_");

            for (int row = 0; row < values.length; row++) {
                values[row] = values[row].replaceAll(";", "\t");
            }

            ArrayAdapter<String> transactionAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_list_item_multiple_choice, values);
            transactions.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            transactions.setAdapter(transactionAdapter);

        }
    }

    public void openSettingsPage(View view) {
        //setContentView(R.layout.settings);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        contentFrameLayout.removeAllViewsInLayout();
        getLayoutInflater().inflate(R.layout.settings, contentFrameLayout);

        Button categoryDisplay = findViewById(R.id.edit_categories);
        categoryDisplay.setOnClickListener(this);

        Button changePinBtn = findViewById(R.id.changePin);
        changePinBtn.setOnClickListener(this);

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

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


        if (isEntryDateClicked == true) {
            TextView entryDate = findViewById(R.id.entryDate);
            entryDate.setText(currentDateString);
        } else if (isEndDateClicked == true) {
            TextView endDate = findViewById(R.id.endDate);
            endDate.setText(currentDateString);
        }
    }

    //Logesh - 22.05

    public void saveIncome(View view) {

        Date transactionDateValue = null;
        //int amountValue = 0;
        float amountValue = 0;
        String codeValue = "";
        boolean mandatoryFieldMissing = false;

        TextView transactionDate = findViewById(R.id.entryDate);
        TextView trandateTV = findViewById(R.id.enterdate);
        if (TextUtils.isEmpty(transactionDate.getText())) {
            mandatoryFieldMissing = true;
            trandateTV.setTextColor(Color.RED);
        } else {
            String dummy = transactionDate.getText().toString();
            transactionDateValue = Date.valueOf(transactionDate.getText().toString());
            trandateTV.setTextColor(Color.BLACK);
        }

        EditText amount = findViewById(R.id.amount);
        TextView amountTV = findViewById(R.id.Amount);
        if (TextUtils.isEmpty(amount.getText())) {
            mandatoryFieldMissing = true;
            amountTV.setTextColor(Color.RED);
        } else {
            //amountValue = Integer.valueOf(amount.getText().toString());
            amountValue = Float.valueOf(amount.getText().toString());
            amountTV.setTextColor(Color.BLACK);
        }

        TextView code = findViewById(R.id.currencyCode);
        TextView codeTV = findViewById(R.id.Currency);
        if (TextUtils.isEmpty(code.getText())) {
            mandatoryFieldMissing = true;
            codeTV.setTextColor(Color.RED);
        } else {
            codeValue = code.getText().toString();
            codeTV.setTextColor(Color.BLACK);
        }

        EditText note = findViewById(R.id.optionalNote);
        String noteValue = note.getText().toString();

        String indicatorValue = "Income";

        if (mandatoryFieldMissing == false) {

            Transactions transactions = new Transactions(this);

            //harish-25.05
            boolean isTransactionSaved = transactions.addData("", transactionDateValue, amountValue,
                    codeValue, "", noteValue, indicatorValue, "", "", "");

            // display transaction is saved and clear the fields
            if (isTransactionSaved) {
                Toast.makeText(getApplicationContext(), "Transaction is saved", Toast.LENGTH_SHORT).show();

                transactionDate.setText("");
                amount.setText("");
                note.setText("");
            }
        }
    }

    // harish - 25.05 - Fields as mandatory
    public void saveExpense(View view) {
        String categoryValue = "";
        String indicatorValue = "";
        Date transactionDateValue = null;
        //int amountValue = 0;
        float amountValue = 0;
        String codeValue = "";
        String paymMethodValue = "";
        boolean mandatoryFieldMissing = false;

        Spinner category = findViewById(R.id.categoryddlb);
        TextView categoryTV = findViewById(R.id.category);
        int selectedCategoryItem = category.getSelectedItemPosition();
        if (selectedCategoryItem > 0) {
            categoryValue = category.getSelectedItem().toString();
            categoryTV.setTextColor(Color.BLACK);
        } else {
            mandatoryFieldMissing = true;
            categoryTV.setTextColor(Color.RED);
        }

        TextView transactionDate = findViewById(R.id.entryDate);
        TextView trandateTV = findViewById(R.id.date);
        if (TextUtils.isEmpty(transactionDate.getText())) {
            mandatoryFieldMissing = true;
            trandateTV.setTextColor(Color.RED);
        } else {
            String dummy = transactionDate.getText().toString();
            transactionDateValue = Date.valueOf(transactionDate.getText().toString());
            trandateTV.setTextColor(Color.BLACK);
        }


        // harish - 25.05 - commented endDate as it is not required
        /*TextView endDate = (TextView) findViewById(R.id.endDate);
        Date endDateValue = Date.valueOf(endDate.getText().toString());*/

        EditText amount = findViewById(R.id.amount);
        TextView amountTV = findViewById(R.id.amountenter);
        if (TextUtils.isEmpty(amount.getText())) {
            mandatoryFieldMissing = true;
            amountTV.setTextColor(Color.RED);
        } else {
            //amountValue = Integer.valueOf(amount.getText().toString());
            amountValue = Float.valueOf(amount.getText().toString());
            amountTV.setTextColor(Color.BLACK);
        }

        TextView code = findViewById(R.id.currencyCode);
        TextView codeTV = findViewById(R.id.currencytype);
        if (TextUtils.isEmpty(code.getText())) {
            mandatoryFieldMissing = true;
            codeTV.setTextColor(Color.RED);
        } else {
            codeValue = code.getText().toString();
            codeTV.setTextColor(Color.BLACK);
        }

        Spinner paymentMethod = findViewById(R.id.paymList);
        TextView paymentTV = findViewById(R.id.payment);
        int selectedPaymentItem = paymentMethod.getSelectedItemPosition();
        if (selectedPaymentItem > 0) {
            paymMethodValue = paymentMethod.getSelectedItem().toString();
            paymentTV.setTextColor(Color.BLACK);
        } else {
            mandatoryFieldMissing = true;
            paymentTV.setTextColor(Color.RED);
        }
        // harish - 25.05

        EditText note = findViewById(R.id.optionalNote);
        String noteValue = note.getText().toString();

        Switch recurringTransaction = findViewById(R.id.recurringSwitch);
        String recurringTransactionValue = recurringTransaction.getText().toString();

        TextView recurringFrequency = findViewById(R.id.recurringValue);
        String recurringFrequencyValue = recurringFrequency.getText().toString();

        TextView recurringValue = findViewById(R.id.recurringValue);
        String recurringValueValue = recurringValue.getText().toString();

        // harish - 25.05
        RadioGroup transactiontype = findViewById(R.id.radioTransactionType);
        if (transactiontype.getCheckedRadioButtonId() == R.id.radioIncome) {
            indicatorValue = "Income";
        } else {
            indicatorValue = "Expense";
        }


        if (mandatoryFieldMissing == false) {
            // harish - 25.05

            Transactions transactions = new Transactions(this);

            //harish-25.05
            boolean isTransactionSaved = transactions.addData(categoryValue, transactionDateValue, amountValue,
                    codeValue, paymMethodValue, noteValue, indicatorValue, recurringTransactionValue, recurringFrequencyValue, recurringValueValue);

            // display transaction is saved and clear the fields
            if (isTransactionSaved) {
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
                recurringTransaction.setChecked(false);
                //recurringTransaction.setText("");

                recurringFrequency.setText("");
                recurringValue.setText("");

                Transactions txns = new Transactions(this);
                Cursor txnCur = txns.getData();
                txnCur.moveToFirst();
                int total = 0;
                while (!txnCur.isAfterLast()) {
                    // fetch Categories and amount and compare with categoryValue
                    String cat = txnCur.getString(0);
                    if(cat.equals(categoryValue)){
                        String amountStr = txnCur.getString(2);
                        //total += Integer.valueOf(amountStr);
                        total += Float.valueOf(amountStr);
                    }
                    txnCur.moveToNext();
                }

                Categories categories = new Categories(this);
                List<String> categoryList = categories.getCategoryAndBudget();
                int budgetSetByUser = 0;

                for(int listIdx = 0; listIdx < categoryList.size(); listIdx++){

                    String dummy = categoryList.get(listIdx);
                    String[] values = dummy.split(";");

                    if(values[0].equals(categoryValue)){
                        budgetSetByUser = Integer.valueOf(values[1]);
                    }
                }


                float percentSpent = (float)total/(float)budgetSetByUser;
                percentSpent = percentSpent * 100;

                int percent = (int)percentSpent;

                NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notify=new Notification.Builder
                        (getApplicationContext()).setContentTitle("Budget notification").setContentText(
                        "You have spent "+percent+ " % in the category " + categoryValue).
                        setContentTitle("abc").setSmallIcon(R.drawable.ic_android_black_24dp).build();

                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                notif.notify(0, notify);
            }
        } else {
            // harish - 25.05
            Toast.makeText(getApplicationContext(), "Please fill the mandatory fields", Toast.LENGTH_SHORT).show();
            // harish - 25.05
        }
    }
    // harish - 25.05

    //Logesh 22.05

    public void onLogin(View view) {
        // get the value of PIN from Database
        // verify against the currently entered user PIN
        // if same redirect to Income/Expense/Summary page
        // else display an error message

        EditText pinToAuth = findViewById(R.id.pinTextBox);
        String pinToCheck = pinToAuth.getText().toString();

        dbPinTable = new DatabaseHandler(this);
        Cursor cursor = dbPinTable.getPinData();

        // check if a value of PIN exists in dbTable - PIN
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String pinSavedInDB = cursor.getString(0);
                if (pinSavedInDB.equals(pinToCheck)) {
                    //setContentView(R.layout.income_or_expense);

                    Toolbar toolbar = findViewById(R.id.toolbar);
                    setSupportActionBar(toolbar);

                    drawer = findViewById(R.id.drawer_layout);
                    NavigationView navigationView = findViewById(R.id.nav_view);
                    navigationView.setNavigationItemSelectedListener(this);

                    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                    drawer.addDrawerListener(toggle);
                    toggle.syncState();
                    drawer.setDrawerLockMode(0);

                    View view1= null;
                    FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
                    contentFrameLayout.removeAllViewsInLayout();
                    this.openExpensePage(view1);
                    //getLayoutInflater().inflate(R.layout.expense, contentFrameLayout);
                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new
                    //      transactionFragment()).commit();
                    navigationView.setCheckedItem(R.id.nav_transaction);
                } else {
                    TextView incorrectPin = findViewById(R.id.incorrectPin);
                    incorrectPin.setText("PIN entered is wrong, please check");
                }
            }
        }
    }

    @Override
    public void onClick(View v) {

        DialogFragment datePicker = new com.example.exspendables.DatePicker();

        switch (v.getId()) {
            case R.id.set_btn:
                // harish - 25.05
                Toast.makeText(this, "Button clicked", Toast.LENGTH_SHORT).show();
                // harish - 25.05
                EditText pinValue = findViewById(R.id.set_Pin_textbox);
                EditText reEnterPinValue = findViewById(R.id.confirm_Pin_textbox);

                String pin = pinValue.getText().toString();
                String pinToConfirm = reEnterPinValue.getText().toString();

                if (pin.equals(pinToConfirm)) {
                    dbPinTable = new DatabaseHandler(this);
                    dbPinTable.addData(pin);
                    // harish - 25.05
                    Toast.makeText(getApplicationContext(), "PIN details saved", Toast.LENGTH_SHORT).show();
                    // harish - 25.05
                    // Redirect to activity where user is prompted to
                    // 1. Enter income 2. Expense 3. View summary
                    //setContentView(R.layout.income_or_expense);
                    FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
                    contentFrameLayout.removeAllViewsInLayout();
                    getLayoutInflater().inflate(R.layout.expense, contentFrameLayout);

                } else {
                    // PIN does not match, display an error message next to Text box
                    TextView pin_no_match = findViewById(R.id.pin_no_match);
                    pin_no_match.setText("PIN do not match, please re-enter");

                }
                break;

            case R.id.edit_categories:

                //setContentView(R.layout.category_popup);

                FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
                contentFrameLayout.removeAllViewsInLayout();
                getLayoutInflater().inflate(R.layout.category_popup, contentFrameLayout);

                ListView categoryList = findViewById(R.id.categorylist);
                Button deleteCategory = findViewById(R.id.deleteCategoryBtn);
                Button modifyCategory = findViewById(R.id.modifyCategoryBtn);
                Button addCategory = findViewById(R.id.addCategoryBtn);

                dbCategories = new Categories(this);
                // Populate Category List View
                List<String> categorylist = dbCategories.getData();
                String[] values = new String[categorylist.size()];
                for (int i = 0; i < categorylist.size(); i++) {
                    values[i] = categorylist.get(i);
                }
                ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>
                        (this, android.R.layout.simple_list_item_multiple_choice, values);
                categoryList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                categoryList.setAdapter(categoryAdapter);

                deleteCategory.setOnClickListener(this);
                modifyCategory.setOnClickListener(this);
                addCategory.setOnClickListener(this);
                break;

            case R.id.deleteTxnBtn:
                Transactions transactions = new Transactions(this);
                ListView transactionList = findViewById(R.id.transactionlist);
                SparseBooleanArray isChecked = transactionList.getCheckedItemPositions();
                ArrayList<String> selectedTxn = new ArrayList<>();

                ListAdapter transactionAdapter = transactionList.getAdapter();

                for(int i=0;i<isChecked.size();i++){
                    if(isChecked.valueAt(i)){
                        int index = isChecked.keyAt(i);
                        String valueToDelete = transactionAdapter.getItem(index).toString();
                        Log.d("VALUE",valueToDelete);

                        String[] tableValues = valueToDelete.split("\\t");
                        boolean result = transactions.deleteData(tableValues[0],tableValues[1],tableValues[2],tableValues[3]);
                        Toast.makeText(getApplicationContext(), "Transaction deleted", Toast.LENGTH_SHORT).show();
                    }
                }

                //refresh the list with new value

                SimpleDateFormat Formatter = new SimpleDateFormat("yyyy/MM/dd");

                java.util.Date dateToCheck = null;
                java.util.Date startdateToCheck = null;
                java.util.Date enddateToCheck = null;

                try {
                    startdateToCheck = Formatter.parse(startDateValue);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    enddateToCheck = Formatter.parse(endDateValue);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                ListView transactionLV = findViewById(R.id.transactionlist);
                Button deleteTransaction = findViewById(R.id.deleteTxnBtn);
                deleteTransaction.setOnClickListener(this);

                /*Button backToSetting = (Button) findViewById(R.id.backToSettings);
                backToSetting.setOnClickListener(this);*/

                Transactions databaseIncomeExpense = new Transactions(this);
                Cursor cursor = databaseIncomeExpense.getData();

                // List<Transactions> expenseDetails = new ArrayList<Transactions>();

                List<String> tranList = null;
                StringBuilder builder = new StringBuilder();

                cursor.moveToFirst();

                while (!cursor.isAfterLast()) {

                    String date = cursor.getString(1);
                    date = date.replaceAll("-","/");

                    try {
                        dateToCheck = Formatter.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if(dateToCheck.before(enddateToCheck) && dateToCheck.after(startdateToCheck)) {

                        builder.append(cursor.getString(0)).append(";")
                                .append(date).append(";")
                                .append(cursor.getString(2)).append(";")
                                .append(cursor.getString(4)).append("_");



                    }

                    cursor.moveToNext();
                }

                builder.toString();
                String st = new String(builder);
                String[] values1 = st.split("_");

                for(int row = 0;row < values1.length;row++){
                    values1[row] = values1[row].replaceAll(";","\t");
                }

                ArrayAdapter<String> tranAdapter = new ArrayAdapter<String>
                        (this, android.R.layout.simple_list_item_multiple_choice, values1);
                transactionLV.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                transactionLV.setAdapter(tranAdapter);

                break;

            case R.id.deleteCategoryBtn:
                dbCategories = new Categories(this);
                categoryList = findViewById(R.id.categorylist);
                SparseBooleanArray checked = categoryList.getCheckedItemPositions();
                ArrayList<String> selectedItems = new ArrayList<String>();

                categorylist = dbCategories.getData();
                values = new String[categorylist.size()];
                for (int i = 0; i < categorylist.size(); i++) {
                    values[i] = categorylist.get(i);
                }
                categoryAdapter = new ArrayAdapter<String>
                        (this, android.R.layout.simple_list_item_multiple_choice, values);

                for (int i = 0; i < checked.size(); i++) {
                    // Item position in adapter
                    int position = checked.keyAt(i);
                    // Add sport if it is checked i.e.) == TRUE!
                    if (checked.valueAt(i)) {
                        //          String dummy1 = selectedItems.get(i).toString();
                        //         String dummy2 = selectedItems.get(position).toString();
                        dbCategories.deleteData(categoryAdapter.getItem(position));

                    }
                }

                // harish - 25.05
                if (checked.size() == 1) {
                    Toast.makeText(getApplicationContext(), "Category is deleted", Toast.LENGTH_SHORT).show();
                } else if (checked.size() > 1) {
                    Toast.makeText(getApplicationContext(), "Categories are deleted", Toast.LENGTH_SHORT).show();
                }
                // harish - 25.05

                //refresh the list with new value
                categoryList = findViewById(R.id.categorylist);

                dbCategories = new Categories(this);
                // Populate Category List View
                categorylist = dbCategories.getData();
                values = new String[categorylist.size()];
                for (int i = 0; i < categorylist.size(); i++) {
                    values[i] = categorylist.get(i);
                }

                categoryAdapter = new ArrayAdapter<String>
                        (this, android.R.layout.simple_list_item_multiple_choice, values);
                categoryList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                categoryList.setAdapter(categoryAdapter);
                break;


            case R.id.addCategoryBtn:
                // create a layout with a EditText and OK button on click on "add"
                //setContentView(R.layout.add_category);
                FrameLayout contentFrameLayout1 = (FrameLayout) findViewById(R.id.fragment_container);
                contentFrameLayout1.removeAllViewsInLayout();
                getLayoutInflater().inflate(R.layout.add_category, contentFrameLayout1);

                Button okAddCategory = findViewById(R.id.btnOKAddCategory);
                okAddCategory.setOnClickListener(this);
                break;

            case R.id.btnOKAddCategory:
                dbCategories = new Categories(this);
                EditText newCategoryValue = findViewById(R.id.add_cat_textbox);
                dbCategories.addData(newCategoryValue.getText().toString());
                // harish - 25.05
                Toast.makeText(getApplicationContext(), "New category added", Toast.LENGTH_SHORT).show();
                // harish - 25.05
                //setContentView(R.layout.category_popup);

                FrameLayout contentFrameLayout2 = (FrameLayout) findViewById(R.id.fragment_container);
                contentFrameLayout2.removeAllViewsInLayout();
                getLayoutInflater().inflate(R.layout.category_popup, contentFrameLayout2);

                //refresh the list with new value
                categoryList = findViewById(R.id.categorylist);

                deleteCategory = findViewById(R.id.deleteCategoryBtn);
                modifyCategory = findViewById(R.id.modifyCategoryBtn);
                addCategory = findViewById(R.id.addCategoryBtn);

                deleteCategory.setOnClickListener(this);
                modifyCategory.setOnClickListener(this);
                addCategory.setOnClickListener(this);

                dbCategories = new Categories(this);
                // Populate Category List View
                categorylist = dbCategories.getData();
                values = new String[categorylist.size()];
                for (int i = 0; i < categorylist.size(); i++) {
                    values[i] = categorylist.get(i);
                }

                categoryAdapter = new ArrayAdapter<String>
                        (this, android.R.layout.simple_list_item_multiple_choice, values);
                categoryList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                categoryList.setAdapter(categoryAdapter);
                break;

            case R.id.modifyCategoryBtn:
                // create a layout with a EditText and OK button on click on "Modify"

                dbCategories = new Categories(this);
                categoryList = findViewById(R.id.categorylist);
                checked = categoryList.getCheckedItemPositions();
                //selectedItems = new ArrayList<String>();

                checked = categoryList.getCheckedItemPositions();
                //selectedItems = new ArrayList<String>();

                categorylist = dbCategories.getData();
                values = new String[categorylist.size()];
                for (int i = 0; i < categorylist.size(); i++) {
                    values[i] = categorylist.get(i);
                }
                categoryAdapter = new ArrayAdapter<String>
                        (this, android.R.layout.simple_list_item_multiple_choice, values);

                for (int i = 0; i < checked.size(); i++) {
                    // Item position in adapter
                    int position = checked.keyAt(i);
                    // Add sport if it is checked i.e.) == TRUE!
                    if (checked.valueAt(i)) {
                        //          String dummy1 = selectedItems.get(i).toString();
                        //         String dummy2 = selectedItems.get(position).toString();
                        oldValue = categoryAdapter.getItem(position);
                        break;
                    }
                }

                //setContentView(R.layout.modify_category);
                FrameLayout contentFrameLayout3 = (FrameLayout) findViewById(R.id.fragment_container);
                contentFrameLayout3.removeAllViewsInLayout();
                getLayoutInflater().inflate(R.layout.modify_category, contentFrameLayout3);

                EditText valueToBeModified = findViewById(R.id.modify_cat_textbox);
                valueToBeModified.setText(oldValue);

                Button okModifyButton = findViewById(R.id.btnOKModifyCategory);
                okModifyButton.setOnClickListener(this);
                break;

            case R.id.btnOKModifyCategory:

                EditText newValue = findViewById(R.id.modify_cat_textbox);
                dbCategories = new Categories(this);
                dbCategories.modifyData(newValue.getText().toString(), oldValue);
                // harish - 25.05
                Toast.makeText(getApplicationContext(), "Changes saved", Toast.LENGTH_SHORT).show();
                // harish - 25.05
                //setContentView(R.layout.category_popup);
                FrameLayout contentFrameLayout4 = (FrameLayout) findViewById(R.id.fragment_container);
                contentFrameLayout4.removeAllViewsInLayout();
                getLayoutInflater().inflate(R.layout.category_popup, contentFrameLayout4);

                //refresh the list with new value
                categoryList = findViewById(R.id.categorylist);

                deleteCategory = findViewById(R.id.deleteCategoryBtn);
                modifyCategory = findViewById(R.id.modifyCategoryBtn);
                addCategory = findViewById(R.id.addCategoryBtn);

                deleteCategory.setOnClickListener(this);
                modifyCategory.setOnClickListener(this);
                addCategory.setOnClickListener(this);

                dbCategories = new Categories(this);
                // Populate Category List View
                categorylist = dbCategories.getData();
                values = new String[categorylist.size()];
                for (int i = 0; i < categorylist.size(); i++) {
                    values[i] = categorylist.get(i);
                }
                categoryAdapter = new ArrayAdapter<String>
                        (this, android.R.layout.simple_list_item_multiple_choice, values);
                categoryList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                categoryList.setAdapter(categoryAdapter);
                break;
// abhivanth,changed "change_pin" to switch_pin
            case R.id.changePin:
                //setContentView(R.layout.switch_pin);

                FrameLayout contentFrameLayout5 = (FrameLayout) findViewById(R.id.fragment_container);
                contentFrameLayout5.removeAllViewsInLayout();
                getLayoutInflater().inflate(R.layout.switch_pin, contentFrameLayout5);


                Switch enablePin = findViewById(R.id.enablePin);
                enablePin.setOnClickListener(this);

                enableSwitch();

                // Button changePinOkBtn = (Button) findViewById(R.id.change_pin_ok);
                //changePinOkBtn.setOnClickListener(this);
                break;

            case R.id.enablePin:

                enablePin = findViewById(R.id.enablePin);
                if (enablePin.isChecked()) {
                    //setContentView(R.layout.change_pin);
                    FrameLayout contentFrameLayout6 = (FrameLayout) findViewById(R.id.fragment_container);
                    contentFrameLayout6.removeAllViewsInLayout();
                    getLayoutInflater().inflate(R.layout.change_pin, contentFrameLayout6);

                    Button changePinOkBtn = findViewById(R.id.change_pin_ok);
                    changePinOkBtn.setOnClickListener(this);
                } else {
                    dbPinTable.deleteData();
                }
                break;

            case R.id.change_pin_ok:


                pinValue = findViewById(R.id.set_Pin_textbox);
                reEnterPinValue = findViewById(R.id.confirm_Pin_textbox);

                pin = pinValue.getText().toString();
                pinToConfirm = reEnterPinValue.getText().toString();


                if (pin.equals(pinToConfirm)) {
                    dbPinTable = new DatabaseHandler(this);
                    Cursor pinCursor = dbPinTable.getPinData();


                    // check if a value of PIN exists in dbTable - PIN
                    if (pinCursor != null) {
                        if (pinCursor.moveToFirst()) {
                            String pinSavedInDB = pinCursor.getString(0);
                            dbPinTable.modifyData(pin, pinSavedInDB);
                            // harish - 25.05
                            Toast.makeText(getApplicationContext(), "Changes saved", Toast.LENGTH_SHORT).show();
                            // harish - 25.05
                            //setContentView(R.layout.settings);
                            FrameLayout contentFrameLayout6 = (FrameLayout) findViewById(R.id.fragment_container);
                            contentFrameLayout6.removeAllViewsInLayout();
                            getLayoutInflater().inflate(R.layout.settings, contentFrameLayout6);

                            Button categoryDisplay = findViewById(R.id.edit_categories);
                            categoryDisplay.setOnClickListener(this);

                            Button changePinBtn = findViewById(R.id.changePin);
                            changePinBtn.setOnClickListener(this);


                            break;
                        } else {

                            dbPinTable.addData(pin);
                            Toast.makeText(getApplicationContext(), "Changes saved", Toast.LENGTH_SHORT).show();
                            //setContentView(R.layout.settings);
                            FrameLayout contentFrameLayout6 = (FrameLayout) findViewById(R.id.fragment_container);
                            contentFrameLayout6.removeAllViewsInLayout();
                            getLayoutInflater().inflate(R.layout.settings, contentFrameLayout6);

                            Button categoryDisplay = findViewById(R.id.edit_categories);
                            categoryDisplay.setOnClickListener(this);

                            Button changePinBtn = findViewById(R.id.changePin);
                            changePinBtn.setOnClickListener(this);
                        }
                    }
                } else {
                    // PIN does not match, display an error message next to Text box
                    TextView pin_no_match = findViewById(R.id.pin_no_match);
                    pin_no_match.setText("PIN do not match, please re-enter");
                    break;
                }
                break;


            case R.id.submit2:
                Spinner categoryBudget = findViewById(R.id.categorybudget);
                String categorySelected = categoryBudget.getSelectedItem().toString();

                EditText budgetamount = findViewById(R.id.budgetamount);
                String budgetValue = budgetamount.getText().toString();

                dbCategories = new Categories(this);
                dbCategories.addmaxbudget(categorySelected,budgetValue);

                Toast.makeText(getApplicationContext(), "Budget saved", Toast.LENGTH_SHORT).show();
                budgetamount.setText("");
                categorylist = dbCategories.getData();
                categorylist.add(0, "");
                categoryAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, categorylist);
                categoryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                categoryBudget.setAdapter(categoryAdapter);
                break;


        }


    }

    public void maxbudget(View view) {
        Button button = findViewById(R.id.maxbudget);
        //setContentView(R.layout.maxbudget);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        contentFrameLayout.removeAllViewsInLayout();
        getLayoutInflater().inflate(R.layout.maxbudget, contentFrameLayout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setContentView(R.layout.maxbudget);
                FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
                contentFrameLayout.removeAllViewsInLayout();
                getLayoutInflater().inflate(R.layout.maxbudget, contentFrameLayout);
            }
        });

        Categories dbCategories = new Categories(MainActivity.this);
        // Populate Category DDLB
        Spinner categorybudget = findViewById(R.id.categorybudget);
        List<String> categorylist = dbCategories.getData();
        categorylist.add(0, "");
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, categorylist);
        categoryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        categorybudget.setAdapter(categoryAdapter);

        Button submit2 = findViewById(R.id.submit2);
        submit2.setOnClickListener(this);
    }

    public void backToHome(View view) {

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        contentFrameLayout.removeAllViewsInLayout();
        this.openExpensePage(view);

    }

    public void sendemail(View view){

        String[] to = {"shudarsan20@gmail.com"};

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL,to);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Transaction details");

        ListView transactionLV = findViewById(R.id.transactionlist);
        Button deleteTransaction = findViewById(R.id.deleteTxnBtn);
        deleteTransaction.setOnClickListener(this);

        int count = transactionLV.getCount();
        for(int index = 0;index < count;index++){

            String temp = transactionLV.getItemAtPosition(index).toString();
            temp = temp.replaceAll(";","\t");
            emailIntent.putExtra(Intent.EXTRA_TEXT, temp);

        }

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void backToSettings(View view) {
        //setContentView(R.layout.settings);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        contentFrameLayout.removeAllViewsInLayout();
        getLayoutInflater().inflate(R.layout.settings, contentFrameLayout);

        Button categoryDisplay = findViewById(R.id.edit_categories);
        categoryDisplay.setOnClickListener(this);

        Button changePinBtn = findViewById(R.id.changePin);
        changePinBtn.setOnClickListener(this);
    }

    public void backToCategory(View view) {
        //setContentView(R.layout.category_popup);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        contentFrameLayout.removeAllViewsInLayout();
        getLayoutInflater().inflate(R.layout.category_popup, contentFrameLayout);

        Button deleteCategory = findViewById(R.id.deleteCategoryBtn);
        Button modifyCategory = findViewById(R.id.modifyCategoryBtn);
        Button addCategory = findViewById(R.id.addCategoryBtn);

        deleteCategory = findViewById(R.id.deleteCategoryBtn);
        modifyCategory = findViewById(R.id.modifyCategoryBtn);
        addCategory = findViewById(R.id.addCategoryBtn);

        deleteCategory.setOnClickListener(this);
        modifyCategory.setOnClickListener(this);
        addCategory.setOnClickListener(this);

        ListView categoryList = findViewById(R.id.categorylist);

        List<String> categorylist = dbCategories.getData();
        String[] values = new String[categorylist.size()];
        for (int i = 0; i < categorylist.size(); i++) {
            values[i] = categorylist.get(i);
        }
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_multiple_choice, values);
        categoryList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        categoryList.setAdapter(categoryAdapter);
    }

    // Abhivanth , enable switch.
    public void enableSwitch() {
        Switch enablepinBtn = findViewById(R.id.enablePin);

        dbPinTable = new DatabaseHandler(this);
        Cursor cursor = dbPinTable.getPinData();

        // check if a value of PIN exists in dbTable - PIN
        // abhivanth , changed "login_pin" to
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                enablepinBtn.setChecked(true);
            } else {
                enablepinBtn.setChecked(false);

            }
        }
    }


    private void initIconList() {
        mCategoryList = new ArrayList<>();
        mCategoryList.add(new CategoryIcon("Clothing", R.drawable.clothing));
        mCategoryList.add(new CategoryIcon("Food", R.drawable.food));
        mCategoryList.add(new CategoryIcon("Groceries", R.drawable.groceries));
        mCategoryList.add(new CategoryIcon("Shopping", R.drawable.shopping));
    }


    public void setIcons(View view) {

        //setContentView(R.layout.set_icons);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        contentFrameLayout.removeAllViewsInLayout();
        getLayoutInflater().inflate(R.layout.set_icons, contentFrameLayout);

        Categories dbCategories = new Categories(this);
        // Populate Category DDLB
        Spinner categoryddlb = findViewById(R.id.categoryddlb);
        List<String> categorylist = dbCategories.getData();
        categorylist.add(0, "");
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, categorylist);
        categoryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        categoryddlb.setAdapter(categoryAdapter);

        initIconList();

        Spinner categoryIcons = findViewById(R.id.iconddlb);
        mAdapter = new IconAdapter(this, mCategoryList);
        categoryIcons.setAdapter(mAdapter);

        categoryIcons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CategoryIcon selectedItem = (CategoryIcon) parent.getItemAtPosition(position);
                String clickedIconName = selectedItem.getmIconName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void saveicons(View view) {

        Spinner category = findViewById(R.id.categoryddlb);
        String cat = category.getSelectedItem().toString();

        Spinner categoryIcons = findViewById(R.id.iconddlb);
        CategoryIcon selectedItem = (CategoryIcon) categoryIcons.getSelectedItem();
        String iconName = selectedItem.getmIconName();

        Categories categories = new Categories(this);
        categories.modifyIcon(cat,iconName);

        Toast.makeText(getApplicationContext(), "Icon saved", Toast.LENGTH_SHORT).show();
    }

    public void backToFilter(View view) {
        //setContentView(R.layout.prompt_filter_ddlb);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        contentFrameLayout.removeAllViewsInLayout();
        getLayoutInflater().inflate(R.layout.graph_summary_list, contentFrameLayout);

        //loadFilters();
    }

    public void showSummaryByAmount(View view) {

        boolean missingMandatoryFields = false;

        String operatorValue = "";
        //int amountValue = 0;
        float amountValue = 0;

        Spinner operator = findViewById(R.id.selectOperator);
        TextView operatorTV = findViewById(R.id.promptOperator);

        EditText amount = findViewById(R.id.enterAmount);
        TextView amountTV = findViewById(R.id.promptAmount);

        int selectedOpItem = operator.getSelectedItemPosition();
        if (selectedOpItem > 0) {
            operatorValue = operator.getSelectedItem().toString();
            operatorTV.setTextColor(Color.BLACK);
        } else {
            missingMandatoryFields = true;
            operatorTV.setTextColor(Color.RED);
        }

        if (TextUtils.isEmpty(amount.getText())) {
            missingMandatoryFields = true;
            amountTV.setTextColor(Color.RED);
        } else {
            //amountValue = Integer.valueOf(amount.getText().toString());
            amountValue = Float.valueOf(amount.getText().toString());
            amountTV.setTextColor(Color.BLACK);
        }

        if(missingMandatoryFields == false) {

            //setContentView(R.layout.tablesummary);
            //setContentView(R.layout.listsummary);
            FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
            contentFrameLayout.removeAllViewsInLayout();
            getLayoutInflater().inflate(R.layout.listsummary, contentFrameLayout);

            ListView transactions = findViewById(R.id.transactionlist);
            Button deleteTransaction = findViewById(R.id.deleteTxnBtn);
            deleteTransaction.setOnClickListener(this);

            Transactions databaseIncomeExpense = new Transactions(this);
            Cursor cursor = databaseIncomeExpense.getData();

            List<String> transactionList = null;
            StringBuilder builder = new StringBuilder();

            cursor.moveToFirst();
            int i = 0;
            while (!cursor.isAfterLast()) {

                //int amountFromDB = Integer.valueOf(cursor.getString(2));
                float amountFromDB = Float.valueOf(cursor.getString(2));
                String indicator = cursor.getString(6);

                if(indicator.equals("Expense")) {

                    if(operatorValue.equals("Greater than")){
                        if(amountFromDB > amountValue){
                            builder.append(cursor.getString(0)).append(";")
                                    .append(cursor.getString(1)).append(";")
                                    .append(cursor.getString(2)).append(";")
                                    .append(cursor.getString(4)).append("_");
                            i++;
                        }
                    }else{
                        if(amountFromDB < amountValue){
                            builder.append(cursor.getString(0)).append(";")
                                    .append(cursor.getString(1)).append(";")
                                    .append(cursor.getString(2)).append(";")
                                    .append(cursor.getString(4)).append("_");
                            i++;
                        }
                    }
                }
                cursor.moveToNext();
            }

            builder.toString();
            String st = new String(builder);
            String[] values = st.split("_");

            for (int row = 0; row < values.length; row++) {
                values[row] = values[row].replaceAll(";", "\t");
            }

            ArrayAdapter<String> transactionAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_list_item_multiple_choice, values);
            transactions.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            transactions.setAdapter(transactionAdapter);

        }

    }

    public void showSummaryByCategory(View view) {

        boolean missingMandatoryFields = false;

        String categoryValue = "";

        Spinner category = findViewById(R.id.selectCategory);
        TextView categoryTV = findViewById(R.id.promptCategory);

        int selectedcategory = category.getSelectedItemPosition();
        if (selectedcategory > 0) {
            categoryValue = category.getSelectedItem().toString();
            categoryTV.setTextColor(Color.BLACK);
        } else {
            missingMandatoryFields = true;
            categoryTV.setTextColor(Color.RED);
        }

        if(missingMandatoryFields == false) {

            //setContentView(R.layout.tablesummary);
            // setContentView(R.layout.listsummary);
            FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
            contentFrameLayout.removeAllViewsInLayout();
            getLayoutInflater().inflate(R.layout.listsummary, contentFrameLayout);

            ListView transactions = findViewById(R.id.transactionlist);
            Button deleteTransaction = findViewById(R.id.deleteTxnBtn);
            deleteTransaction.setOnClickListener(this);

            Transactions databaseIncomeExpense = new Transactions(this);
            Cursor cursor = databaseIncomeExpense.getData();

            List<String> transactionList = null;
            StringBuilder builder = new StringBuilder();

            cursor.moveToFirst();
            int i = 0;
            while (!cursor.isAfterLast()) {

                String categoryFromDB = cursor.getString(0);
                String indicator = cursor.getString(6);


                if(indicator.equals("Expense")) {
                    if(categoryFromDB.equals(categoryValue)){
                        builder.append(cursor.getString(0)).append(";")
                                .append(cursor.getString(1)).append(";")
                                .append(cursor.getString(2)).append(";")
                                .append(cursor.getString(4)).append("_");
                        i++;
                    }
                }
                cursor.moveToNext();
            }

            builder.toString();
            String st = new String(builder);
            String[] values = st.split("_");

            for (int row = 0; row < values.length; row++) {
                values[row] = values[row].replaceAll(";", "\t");
            }

            ArrayAdapter<String> transactionAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_list_item_multiple_choice, values);
            transactions.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            transactions.setAdapter(transactionAdapter);

        }
    }

    public void showSummaryByPaym(View view) {

        boolean missingMandatoryFields = false;

        String paymentValue = "";

        Spinner payment = findViewById(R.id.selectPayMethod);
        TextView paymentTV = findViewById(R.id.promptPayM);

        int selectedPayment = payment.getSelectedItemPosition();
        if (selectedPayment > 0) {
            paymentValue = payment.getSelectedItem().toString();
            paymentTV.setTextColor(Color.BLACK);
        } else {
            missingMandatoryFields = true;
            paymentTV.setTextColor(Color.RED);
        }

        if(missingMandatoryFields == false) {

            //setContentView(R.layout.tablesummary);
            //setContentView(R.layout.listsummary);
            FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
            contentFrameLayout.removeAllViewsInLayout();
            getLayoutInflater().inflate(R.layout.listsummary, contentFrameLayout);

            ListView transactions = findViewById(R.id.transactionlist);
            Button deleteTransaction = findViewById(R.id.deleteTxnBtn);
            deleteTransaction.setOnClickListener(this);

            Transactions databaseIncomeExpense = new Transactions(this);
            Cursor cursor = databaseIncomeExpense.getData();

            List<String> transactionList = null;
            StringBuilder builder = new StringBuilder();

            cursor.moveToFirst();
            int i = 0;
            while (!cursor.isAfterLast()) {

                String paymentFromDB = cursor.getString(4);
                String indicator = cursor.getString(6);


                if(indicator.equals("Expense")) {
                    if(paymentFromDB.equals(paymentValue)){
                        builder.append(cursor.getString(0)).append(";")
                                .append(cursor.getString(1)).append(";")
                                .append(cursor.getString(2)).append(";")
                                .append(cursor.getString(4)).append("_");
                        i++;
                    }
                }
                cursor.moveToNext();
            }

            builder.toString();
            String st = new String(builder);
            String[] values = st.split("_");

            for (int row = 0; row < values.length; row++) {
                values[row] = values[row].replaceAll(";", "\t");
            }

            ArrayAdapter<String> transactionAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_list_item_multiple_choice, values);
            transactions.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            transactions.setAdapter(transactionAdapter);

        }
    }

    public void filterData(View view) {


        boolean fieldsMissing = false;
        boolean dateSelected = false;
        boolean categorySelected = false;
        boolean amountSelected = false;
        boolean paymentSelected = false;
        String categoryValueSelected = null;
        String paymentValueSelected = null;
        float amountValueSelected = 0;

        TextView entryDateTV = (TextView) findViewById(R.id.entryDate);
        TextView endDateTV = (TextView) findViewById(R.id.endDate);

        // for filter by date
        if(TextUtils.isEmpty(entryDateTV.getText().toString())
                && TextUtils.isEmpty(endDateTV.getText().toString())){
            // keine probleme
        }
        else if(entryDateTV.getText().toString() != ""
                && endDateTV.getText().toString() != ""){
            // keine probleme
            dateSelected = true;
        }
        else{
            fieldsMissing = true;

            if(TextUtils.isEmpty(entryDateTV.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Please set Start Date", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Please set End Date", Toast.LENGTH_SHORT).show();
            }
        }

        // for filter by amount

        Spinner operator = findViewById(R.id.selectOperator);
        int indexSelected = operator.getSelectedItemPosition();

        TextView amountTV = (TextView) findViewById(R.id.enterAmount);
        String amount = amountTV.getText().toString();

        if(indexSelected > 0 && !amount.equals("")){
            //keine probleme
            amountSelected = true;
            amountValueSelected = Float.valueOf(amount);
        }
        else if(indexSelected == 0 && TextUtils.isEmpty(amountTV.getText().toString())){
            //keine probleme
        }
        else{
            fieldsMissing = true;
            if(indexSelected > 0){
                Toast.makeText(getApplicationContext(), "Please enter amount", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Please select an operator", Toast.LENGTH_SHORT).show();
            }
        }

        Spinner category = (Spinner) findViewById(R.id.categoryList);
        int categoryIndexSelected = category.getSelectedItemPosition();

        if(categoryIndexSelected > 0){
            categorySelected = true;
            categoryValueSelected = category.getSelectedItem().toString();
        }

        Spinner payment = (Spinner) findViewById(R.id.selectPaymentMethod);
        int paymentIndexSelected = payment.getSelectedItemPosition();

        if(paymentIndexSelected > 0){
            paymentSelected = true;
            paymentValueSelected = payment.getSelectedItem().toString();
        }

        if(fieldsMissing == false) {

            if (dateSelected == true || amountSelected == true || paymentSelected == true || categorySelected == true) {
                // load all values from table
                Transactions dbTransactions = new Transactions(this);
                Cursor cursor = dbTransactions.getData();

                StringBuilder builder = new StringBuilder();

                cursor.moveToFirst();
                int i = 0;
                while (!cursor.isAfterLast()) {

                    String date = cursor.getString(1);
                    String indicator = cursor.getString(6);
                    date = date.replaceAll("-", "/");

                    if (indicator.equals("Expense")) {
                        builder.append(cursor.getString(0)).append(";")
                                .append(date).append(";")
                                .append(cursor.getString(2)).append(";")
                                .append(cursor.getString(4)).append("_");

                        i++;
                    }
                    cursor.moveToNext();
                }

                builder.toString();
                String st = new String(builder);
                String[] values = st.split("_");

                ArrayList<String> recordsOK = new ArrayList<String>();

                for (int index = 0; index < values.length; index++) {
                    recordsOK.add(values[index]);
                }

                if (dateSelected == true) {

                    startDateValue = entryDateTV.getText().toString();
                    startDateValue = startDateValue.replaceAll("-", "/");

                    endDateValue = endDateTV.getText().toString();
                    endDateValue = endDateValue.replaceAll("-", "/");

                    SimpleDateFormat Formatter = new SimpleDateFormat("yyyy/MM/dd");

                    java.util.Date dateToCheck = null;
                    java.util.Date startdateToCheck = null;
                    java.util.Date enddateToCheck = null;

                    try {
                        startdateToCheck = Formatter.parse(startDateValue);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    startdateToCheck.setDate(startdateToCheck.getDate() - 1);

                    try {
                        enddateToCheck = Formatter.parse(endDateValue);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    startdateToCheck.setDate(startdateToCheck.getDate() - 1);
                    enddateToCheck.setDate(enddateToCheck.getDate() - 1);

                    for (int index = 0; index < recordsOK.size(); index++) {
                        // String[] dummy = values[index].split(";");

                        String[] dummy = recordsOK.get(index).split(";");

                        try {
                            dateToCheck = Formatter.parse(dummy[1]);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (dateToCheck.before(enddateToCheck) && dateToCheck.after(startdateToCheck)) {

                        } else {
                            recordsOK.remove(index);
                        }
                    }
                }

                if (categorySelected == true) {

                    for (int index = 0; index < recordsOK.size(); index++) {

                        String[] dummy = recordsOK.get(index).split(";");

                        if (dummy[0].equals(categoryValueSelected)) {

                        } else {
                            recordsOK.remove(index);
                        }
                    }
                }

                if (paymentSelected == true) {

                    for (int index = 0; index < recordsOK.size(); index++) {

                        String[] dummy = recordsOK.get(index).split(";");

                        if (dummy[3].equals(paymentValueSelected)) {

                        } else {
                            recordsOK.remove(index);
                        }
                    }
                }

                if (amountSelected == true) {

                    String operatorValue = operator.getSelectedItem().toString();

                    for (int index = 0; index < recordsOK.size(); index++) {

                        String[] dummy = recordsOK.get(index).split(";");
                        float amountFromDb = Float.valueOf(dummy[2]);

                        if (operatorValue == "Greater than") {
                            if (amountValueSelected > amountFromDb) {

                            } else {
                                recordsOK.remove(index);
                            }
                        } else {
                            if (amountValueSelected < amountFromDb) {

                            } else {
                                recordsOK.remove(index);
                            }
                        }
                    }
                }

                FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
                contentFrameLayout.removeAllViewsInLayout();
                getLayoutInflater().inflate(R.layout.listsummary, contentFrameLayout);

                ListView transactions = findViewById(R.id.transactionlist);
                Button deleteTransaction = findViewById(R.id.deleteTxnBtn);
                deleteTransaction.setOnClickListener(this);

                values = new String[recordsOK.size()];
                for (int index = 0; index < recordsOK.size(); index++) {
                    values[index] = recordsOK.get(index);
                }

                for (int row = 0; row < values.length; row++) {
                    values[row] = values[row].replaceAll(";", "\t");
                }

                ArrayAdapter<String> transactionAdapter = new ArrayAdapter<String>
                        (this, android.R.layout.simple_list_item_multiple_choice, values);
                transactions.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                transactions.setAdapter(transactionAdapter);

            }

            else{
                Toast.makeText(getApplicationContext(), "Select atleast one filter", Toast.LENGTH_SHORT).show();

            }
        }
    }
}

