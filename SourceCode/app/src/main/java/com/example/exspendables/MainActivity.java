package com.example.exspendables;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.StackedValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mynameismidori.currencypicker.CurrencyPicker;
import com.mynameismidori.currencypicker.CurrencyPickerListener;


import java.sql.Date;
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
    public static int oldIcon;
    public static String startDateValue = null;
    public static String endDateValue = null;
    private ArrayList<CategoryIcon> mCategoryList;
    private IconAdapter mAdapter;
    protected DrawerLayout drawer;
    private String layoutID;


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
                    navigationView.setCheckedItem(R.id.nav_transaction);
                }
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
                this.openExpensePage(view);
                break;
            case R.id.nav_summary:
                View view1= null;
                FrameLayout contentFrameLayout1 = (FrameLayout) findViewById(R.id.fragment_container);
                contentFrameLayout1.removeAllViewsInLayout();
                this.openSummaryPage(view1);
                break;
            case R.id.nav_settings:
                View view2= null;
                FrameLayout contentFrameLayout2 = (FrameLayout) findViewById(R.id.fragment_container);
                contentFrameLayout2.removeAllViewsInLayout();
                this.openSettingsPage(view2);
                break;
            case R.id.nav_graph:
                View view3= null;
                FrameLayout contentFrameLayout3 = (FrameLayout) findViewById(R.id.fragment_container);
                contentFrameLayout3.removeAllViewsInLayout();
                this.openGraphSummaryPage(view3);
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

        final Button button = findViewById(R.id.okSubmitButton);
        final Transactions transactions = new Transactions(this);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //changeToDateFormat("Mon Jun 03 00:00:00 GMT+02:00 2019");
                TextView startDate = findViewById(R.id.entryDate);
                TextView byDate = findViewById(R.id.byDate);
                SimpleDateFormat Formatter = new SimpleDateFormat("yyyy/MM/dd");


                TextView endDate = findViewById(R.id.endDate);
                EditText amountFilter = findViewById(R.id.enterAmount);
                RadioButton expenseRadio = findViewById(R.id.radioExpenseGraph);
                RadioButton incomeRadio = findViewById(R.id.radioIncomeGraph);
                RadioGroup radioGroup = findViewById(R.id.radioTransactionTypeGraph);
                String amountValue = amountFilter.getText().toString();
                String indicatorValue;



                Spinner selectPaymentMethod = findViewById(R.id.selectPaymentMethod);
                Spinner selectOperator = findViewById(R.id.selectOperator);
                String catList = String.valueOf(categoryList.getSelectedItem());
                String payList = String.valueOf(selectPaymentMethod.getSelectedItem());
                String operatorList = String.valueOf(selectOperator.getSelectedItem());

                if (startDate.getText() != "" && endDate.getText() != "" & catList.equals("") && payList.equals("") && operatorList.equals("") && amountValue.equals("") ) {

                    if (radioGroup.getCheckedRadioButtonId() == R.id.radioIncomeGraph) {
                        indicatorValue = "Income";
                    } else {
                        indicatorValue = "Expense";
                    }

                    String startDateValue = startDate.getText().toString();
                    String endDateValue = endDate.getText().toString();

                    SQLiteDatabase sdb = transactions.getReadableDatabase();
                    String sql = "Select startDate, amount, category from TRANSACTIONS " +
                            "where startDate between '" +startDateValue+"' and '"+ endDateValue + "'and indicator ='" + indicatorValue + "'" + "ORDER BY startDate";

                    Cursor c = sdb.rawQuery(sql, null);
                    int count = c.getCount();
                    c.moveToFirst();

                    if (count != 0) {

                        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
                        contentFrameLayout.removeAllViewsInLayout();
                        getLayoutInflater().inflate(R.layout.barchart, contentFrameLayout);
                        BarChart chart;
                        final ArrayList<BarEntry> BARENTRY;
                        BarDataSet Bardataset;
                        BarData BARDATA;
                        chart = findViewById(R.id.barGraph);
                        String date;
                        float amount;
                        String cat;
                        final XAxis xAxis = chart.getXAxis();
                        SimpleDateFormat mFormat = new SimpleDateFormat("yyMMdd");
                        SimpleDateFormat mFormat1 = new SimpleDateFormat("dd-MMM-yy");
                        ArrayList xAxisValue = new ArrayList<>();
                        String categoryList[] = new String[50];
                        ArrayList<LegendEntry> legendEntry;


                        BARENTRY = new ArrayList<>();
                        legendEntry = new ArrayList<>();

                        for (int m = 0; m < count; m++) {
                            date = c.getString(0);
                            amount = c.getFloat(1);
                            cat = c.getString(2);
                            date = date.replaceAll("-", "");
                            int j = Integer.valueOf(date);
                            String p = date.substring(date.length() - 6);
                            int i = Integer.valueOf(p);
                            BARENTRY.add(new BarEntry(m, amount));

                            String f = String.valueOf(i);
                            java.util.Date dateValue = null;

                            try {
                                dateValue = mFormat.parse(f);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String dateValue1= mFormat1.format(dateValue);

                            xAxisValue.add(dateValue1);
                           // LegendEntry legend = new LegendEntry();
                            //legend.label = cat;
                            //legend.formColor = Color.RED;
                            categoryList[m] = cat;
                            //legendEntry.add(legendEntryA);
                           // legendEntry.add(m, legend);
                            c.moveToNext();
                        }

                        Bardataset = new BarDataSet(BARENTRY, "");
                        BARDATA = new BarData(Bardataset);
                        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                        Bardataset.setForm(Legend.LegendForm.SQUARE);
                        chart.setData(BARDATA);

                        Bardataset.setValues(BARENTRY);


                        chart.animateXY(3000, 3000);
                        chart.getAxisLeft().setAxisMinimum(0);
                        chart.getAxisRight().setAxisMinimum(0);
                        chart.setPinchZoom(false);
                        float barWidth = 0.25f;
                        chart.setFitBars(true);

                        BARDATA.setBarWidth(barWidth);
                        xAxis.setGranularity(1f);
                        xAxis.setGranularityEnabled(true);
                        xAxis.mEntryCount = count;
                        xAxis.mAxisRange = 1f;
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setLabelRotationAngle(-90);
                        chart.setTouchEnabled(true);
                        chart.getDescription().setEnabled(false);


                        chart.setDragEnabled(true);

                        chart.invalidate();
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValue));
                        Legend l = chart.getLegend();
                        l.setEnabled(true);
                       // l.setTextColor(Color.BLACK);

                       // l.setOrientation(Legend.LegendOrientation.VERTICAL);
                       // l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);

                        String catList1;
                        c.moveToFirst();

                        for (int m = 0; m < l.getEntries().length; m++) {
                            catList1 = c.getString(2);
                            LegendEntry legend = new LegendEntry();
                            legend.label = catList1;
                            legend.formColor = Bardataset.getColor(m);
                            legendEntry.add(m, legend);
                            c.moveToNext();
                        }

                        //l.setExtra(ColorTemplate.COLORFUL_COLORS, categoryList);
                        l.setCustom(legendEntry);
                        chart.invalidate();
                    } else
                    {
                        Toast.makeText(getApplicationContext(),"No values available to display chart", Toast.LENGTH_LONG).show();
                    }
                }
                else if (startDate.getText().equals("") && endDate.getText().equals("") && catList.equals("")&& payList.equals("") && operatorList.equals("") && amountValue.equals("")){

                    Toast.makeText(getApplicationContext(), "Select atleast one filter", Toast.LENGTH_LONG).show();
                }
                else if ((startDate.getText() != "" && endDate.getText().equals("")) || (startDate.getText().equals("") && endDate.getText() !="")){

                    Toast.makeText(getApplicationContext(), "Select start date & end date", Toast.LENGTH_LONG).show();
                }
                else if ((!operatorList.equals("") && amountValue.equals("")) || (operatorList.equals("") && !amountValue.equals(""))){

                    Toast.makeText(getApplicationContext(), "Select operator & amount value", Toast.LENGTH_LONG).show();
                }

                else if (!catList.equals("") || startDate.getText() != "" || endDate.getText() != "" || !payList.equals("") || operatorList.equals("Greater than") || operatorList.equals("Lesser than") || !amountValue.equals("")) {

                    if (radioGroup.getCheckedRadioButtonId() == R.id.radioIncomeGraph) {
                        indicatorValue = "Income";
                    } else {
                        indicatorValue = "Expense";
                    }

                    String startDatePie = startDate.getText().toString();
                    String endDatePie = endDate.getText().toString();
                    PieChart mChart = null;
                    SQLiteDatabase db = transactions.getReadableDatabase();
                    String sql = null;

                    if (startDate.getText().equals("") && endDate.getText().equals("") && !catList.equals("") && payList.equals("") && operatorList.equals("") && amountValue.equals("")) {
                        sql = "Select paymentMethod, sum(amount) from TRANSACTIONS where category ='" + catList + "' and indicator ='" + indicatorValue + "'" + "GROUP BY paymentMethod";

                    }else if (startDate.getText().equals("") && endDate.getText().equals("") && catList.equals("") && payList.equals("") && operatorList.equals("Greater than") && !amountValue.equals("")) {
                        sql = "Select category, sum(amount) from TRANSACTIONS where amount > '" + amountValue + "' and indicator ='" + indicatorValue + "'"+ "GROUP BY category";

                    }else if (startDate.getText().equals("") && endDate.getText().equals("") && catList.equals("") && payList.equals("") && operatorList.equals("Lesser than") && !amountValue.equals("")) {
                        sql = "Select category, sum(amount) from TRANSACTIONS where amount < '" + amountValue + "' and indicator ='" + indicatorValue + "'"+ "GROUP BY category";

                    }else if (startDate.getText().equals("") && endDate.getText().equals("") && catList.equals("") && operatorList.equals("") && amountValue.equals("") && !payList.equals("")) {
                        sql = "Select category, sum(amount) from TRANSACTIONS where paymentMethod ='" + payList + "' and indicator ='" + indicatorValue + "'"+ "GROUP BY category";

                    }else if (startDate.getText() != "" && endDate.getText() != "" && !catList.equals("") && payList.equals("") && operatorList.equals("") && amountValue.equals("")) {
                        sql = "Select category, amount from TRANSACTIONS where startDate between '" + startDatePie + "' and '" + endDatePie + "' and category = '" + catList + "' and indicator ='" + indicatorValue + "'";

                    }else if (startDate.getText() != "" && endDate.getText() != "" && catList.equals("") && operatorList.equals("Greater than") && !amountValue.equals("") ) {
                        sql = "Select category, amount from TRANSACTIONS where startDate between '" + startDatePie + "' and '" + endDatePie + "' and amount > '" + amountValue + "' and indicator ='" + indicatorValue + "'";

                    }else if (startDate.getText() != "" && endDate.getText() != "" && catList.equals("") && operatorList.equals("Lesser than") && !amountValue.equals("") ) {
                        sql = "Select category, amount from TRANSACTIONS where startDate between '" + startDatePie + "' and '" + endDatePie + "' and amount < '" + amountValue + "'and indicator ='" + indicatorValue + "'";

                    }else if (startDate.getText() != "" && endDate.getText() != "" && catList.equals("") && !payList.equals("") && operatorList.equals("") && amountValue.equals("")) {
                        sql = "Select paymentMethod, amount from TRANSACTIONS where startDate between '" + startDatePie + "' and '" + endDatePie + "' and paymentMethod ='" + payList + "' and indicator ='" + indicatorValue + "'";

                    }else if (startDate.getText().equals("") && endDate.getText().equals("") && !catList.equals("") && payList.equals("") && operatorList.equals("Greater than") && !amountValue.equals("")) {
                        sql = "Select category, amount from TRANSACTIONS where category = '" + catList + "' and amount > '" + amountValue + "' and indicator ='" + indicatorValue + "'";

                    }else if (startDate.getText().equals("") && endDate.getText().equals("") && !catList.equals("") && payList.equals("") && operatorList.equals("Lesser than") && !amountValue.equals("")) {
                        sql = "Select category, amount from TRANSACTIONS where category = '" + catList + "' and amount < '" + amountValue + "' and indicator ='" + indicatorValue + "'";

                    }else if (startDate.getText().equals("") && endDate.getText().equals("") && !catList.equals("") && !payList.equals("") && operatorList.equals(" ") && amountValue.equals("")) {
                        sql = "Select category, amount from TRANSACTIONS where category = '" + catList + "' and paymentMethod = '" + payList + "' and indicator ='" + indicatorValue + "'";

                    }else if (startDate.getText().equals("") && endDate.getText().equals("") && catList.equals("") && operatorList.equals("Greater than") && !amountValue.equals("") && !payList.equals("")) {
                        sql = "Select paymentMethod, amount from TRANSACTIONS where paymentMethod = '" + payList + "'and amount > '" + amountValue + "' and indicator ='" + indicatorValue + "'";

                    }else if (startDate.getText().equals("") && endDate.getText().equals("") && catList.equals("") && operatorList.equals("Lesser than") && !amountValue.equals("") && 	!payList.equals("")) {
                        sql = "Select category, amount from TRANSACTIONS where paymentMethod = '" + payList + "' and amount < '" + amountValue + "' and indicator ='" + indicatorValue + "'";

                    }else if (startDate.getText() != "" && endDate.getText() != "" && !catList.equals("") && operatorList.equals("Greater than") && !amountValue.equals("") && payList.equals("")) {
                        sql = "Select category, amount from TRANSACTIONS where startDate between '" + startDatePie + "' and '" + endDatePie + "' and category = '" + catList + "' and amount > '" + amountValue + "' and indicator ='" + indicatorValue + "'";

                    }else if (startDate.getText() != "" && endDate.getText() != "" && !catList.equals("") && operatorList.equals("Lesser than") && !amountValue.equals("") && payList.equals("")) {
                        sql = "Select category, amount from TRANSACTIONS where startDate between '" + startDatePie + "' and '" + endDatePie + "' and category = '" + catList + "' and amount < '" + amountValue + "' and indicator ='" + indicatorValue + "'";

                    }else if (startDate.getText() != "" && endDate.getText() != "" && !catList.equals("") && operatorList.equals("") && amountValue.equals("") && !payList.equals("")){
                        sql = "Select category, amount from TRANSACTIONS where startDate between '" + startDatePie + "' and '" + endDatePie + "' and category = '" + catList + "' and paymentMethod = '" + payList + "' and indicator ='" + indicatorValue + "'";

                    }else if (startDate.getText() != "" && endDate.getText() != "" && catList.equals("") && operatorList.equals("Greater than") && 		!amountValue.equals("") && !payList.equals("")) {
                        sql = "Select category, amount from TRANSACTIONS where startDate between '" + startDatePie + "' and '" + endDatePie + "' and amount > '" + amountValue + "' and paymentMethod = '" + payList + "' and indicator ='" + indicatorValue + "'";

                    }else if (startDate.getText() != "" && endDate.getText() != "" && catList.equals("") && operatorList.equals("Lesser than") && 		!amountValue.equals("") && !payList.equals("")) {
                        sql = "Select category, amount from TRANSACTIONS where startDate between '" + startDatePie + "' and '" + endDatePie + "' and amount < '" + amountValue + "' and paymentMethod = '" + payList + "' and indicator ='" + indicatorValue + "'";

                    }else if (startDate.getText() != "" && endDate.getText() != "" && !catList.equals("") && operatorList.equals("Greater than") && 	!amountValue.equals("") && !payList.equals("")) {
                        sql = "Select category, amount from TRANSACTIONS where startDate between '" + startDatePie + "' and '" + endDatePie + "' and category = '" + catList + "' and paymentMethod = '" + payList + "' and amount > '" + amountValue + "' and indicator ='" + indicatorValue + "'";

                    }else if (startDate.getText() != "" && endDate.getText() != "" && !catList.equals("") && operatorList.equals("Greater than") && 	!amountValue.equals("") ) {
                        sql = "Select category, amount from TRANSACTIONS where startDate between '" + startDatePie + "' and '" + endDatePie + "' and category = '" + catList + "' and amount > '" + amountValue + "' and indicator ='" + indicatorValue + "'";
                    }

                    final Cursor c = db.rawQuery(sql, null);
                    int count = c.getCount();
                    c.moveToFirst();

                    if(count != 0) {

                        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
                        contentFrameLayout.removeAllViewsInLayout();
                        getLayoutInflater().inflate(R.layout.piechart, contentFrameLayout);
                        mChart = findViewById(R.id.PieChart);
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
                            yVals1.add(new PieEntry((float) (values[i]), categoryNames[i].toString()));
                        }

                        ArrayList<String> xVals = new ArrayList<String>();//array legend

                        PieDataSet set1 = new PieDataSet(yVals1, "");
                        set1.setColors(ColorTemplate.createColors(colors));

                        PieData data = new PieData(set1);
                        set1.setColors(ColorTemplate.COLORFUL_COLORS);
                        mChart.setData(data);
                        // undo all highlights
                        mChart.highlightValues(null);
                        mChart.invalidate();
                        data.setValueTextSize(15f);
                        mChart.setDrawEntryLabels(false);
                        mChart.getDescription().setEnabled(false);
                        Legend legend = mChart.getLegend();
                        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);

                        c.close();
                        db.close();
                    } else
                    {

                        Toast.makeText(getApplicationContext(),"No values available to display chart", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    // Event handler for button "Enter Expense"
    public void openExpensePage(View view)  {


        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        contentFrameLayout.removeAllViewsInLayout();
        getLayoutInflater().inflate(R.layout.expense, contentFrameLayout);
        layoutID = "expense";
        Categories dbCategories;
        dbCategories = new Categories(this);
        // Populate Category DDLB
        Spinner categoryddlb = findViewById(R.id.categoryddlb);
        List<String> categorylist = dbCategories.getCategoryAndIcon();

        int budgetSetByUser = 0;
        mCategoryList = new ArrayList<>();

        mCategoryList.add(0, new CategoryIcon("",0));

        for(int listIdx = 0; listIdx < categorylist.size(); listIdx++){

            String dummy = categorylist.get(listIdx);
            String[] values = dummy.split(";");

            switch (values[1]){
                case "2131230918":
                    mCategoryList.add(new CategoryIcon(values[0], R.drawable.ic_airplane));
                    break;

                case "2131230923":
                    mCategoryList.add(new CategoryIcon(values[0], R.drawable.ic_bagel));
                    break;

                case "2131230924":
                    mCategoryList.add(new CategoryIcon(values[0], R.drawable.ic_barbershop));
                    break;

                case "2131230925":
                    mCategoryList.add(new CategoryIcon(values[0], R.drawable.ic_bonds));
                    break;

                case "2131230926":
                    mCategoryList.add(new CategoryIcon(values[0], R.drawable.ic_business));
                    break;

                case "2131230927":
                    mCategoryList.add(new CategoryIcon(values[0], R.drawable.ic_buy));
                    break;

                case "2131230928":
                    mCategoryList.add(new CategoryIcon(values[0], R.drawable.ic_circus));
                    break;

                case "2131230929":
                    mCategoryList.add(new CategoryIcon(values[0], R.drawable.ic_debit_card));
                    break;

                case "2131230930":
                    mCategoryList.add(new CategoryIcon(values[0], R.drawable.ic_globe));
                    break;

                case "2131230932":
                    mCategoryList.add(new CategoryIcon(values[0], R.drawable.ic_hotel));
                    break;

                case "2131230938":
                    mCategoryList.add(new CategoryIcon(values[0], R.drawable.ic_railway));
                    break;

                default:
                    mCategoryList.add(new CategoryIcon(values[0],R.drawable.ic_white_box));
                    break;
            }

        }

        mAdapter = new IconAdapter(this, mCategoryList);
        categoryddlb.setAdapter(mAdapter);

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
            else{
                TextView code = findViewById(R.id.currencyCode);
                Toast.makeText(getApplicationContext(), "Please select currency unit " +
                        "in Settings > Set Currency", Toast.LENGTH_SHORT).show();
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
                    alert.setCanceledOnTouchOutside(false);
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
                        if(layoutID.equals("expense")){
                            TextView currcode = (TextView) findViewById(R.id.currencyCode);
                            currcode.setText(currency_selected);
                        }
                    } else {
                        currency.addData(currency_selected);
                        // harish - 25.05
                        Toast.makeText(getApplicationContext(), "Selected currency unit is set", Toast.LENGTH_SHORT).show();
                        // harish - 25.05
                        if(layoutID.equals("expense")){
                           TextView currcode = (TextView) findViewById(R.id.currencyCode);
                           currcode.setText(currency_selected);
                        }
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
    }

    public void openSettingsPage(View view) {
        //setContentView(R.layout.settings);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        contentFrameLayout.removeAllViewsInLayout();
        getLayoutInflater().inflate(R.layout.settings, contentFrameLayout);
        layoutID = "settings";

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
            CategoryIcon selectedItem = (CategoryIcon) category.getSelectedItem();
            categoryValue = selectedItem.getmIconName();
            //categoryValue = category.getSelectedItem().toString();
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

                View view1 = null;
                FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
                contentFrameLayout.removeAllViewsInLayout();
                this.openExpensePage(view);



                Categories dbCategories;
                dbCategories = new Categories(this);
                // Populate Category DDLB
                Spinner categoryddlb = findViewById(R.id.categoryddlb);
                List<String> categorylist = dbCategories.getCategoryAndIcon();


                mCategoryList = new ArrayList<>();

                mCategoryList.add(0, new CategoryIcon("",0));

                for(int listIdx = 0; listIdx < categorylist.size(); listIdx++){

                    String dummy = categorylist.get(listIdx);
                    String[] values = dummy.split(";");

                    switch (values[1]){
                        case "2131230918":
                            mCategoryList.add(new CategoryIcon(values[0], R.drawable.ic_airplane));
                            break;

                        case "2131230923":
                            mCategoryList.add(new CategoryIcon(values[0], R.drawable.ic_bagel));
                            break;

                        case "2131230924":
                            mCategoryList.add(new CategoryIcon(values[0], R.drawable.ic_barbershop));
                            break;

                        case "2131230925":
                            mCategoryList.add(new CategoryIcon(values[0], R.drawable.ic_bonds));
                            break;

                        case "2131230926":
                            mCategoryList.add(new CategoryIcon(values[0], R.drawable.ic_business));
                            break;

                        case "2131230927":
                            mCategoryList.add(new CategoryIcon(values[0], R.drawable.ic_buy));
                            break;

                        case "2131230928":
                            mCategoryList.add(new CategoryIcon(values[0], R.drawable.ic_circus));
                            break;

                        case "2131230929":
                            mCategoryList.add(new CategoryIcon(values[0], R.drawable.ic_debit_card));
                            break;

                        case "2131230930":
                            mCategoryList.add(new CategoryIcon(values[0], R.drawable.ic_globe));
                            break;

                        case "2131230932":
                            mCategoryList.add(new CategoryIcon(values[0], R.drawable.ic_hotel));
                            break;

                        case "2131230938":
                            mCategoryList.add(new CategoryIcon(values[0], R.drawable.ic_railway));
                            break;

                        default:
                            mCategoryList.add(new CategoryIcon(values[0],R.drawable.ic_white_box));
                            break;
                    }

                }

                mAdapter = new IconAdapter(this, mCategoryList);
                categoryddlb.setAdapter(mAdapter);

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

                if(budgetSetByUser > 0) {

                    float percentSpent = (float) total / (float) budgetSetByUser;
                    percentSpent = percentSpent * 100;

                    int percent = (int) percentSpent;

                    if(percent > 60 && percent <= 100){
                        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        final EditText textView = new EditText(this);
                        builder.setView(textView);
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("You have spent " + percent + " % in the category " + categoryValue);
                        textView.setTextColor(Color.BLACK);
                        builder.setTitle("Budget warning");

                        builder.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });

                        final AlertDialog alert = builder.create();
                        alert.show();
                    } else if (percent > 100){
                        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        final EditText textView = new EditText(this);
                        builder.setView(textView);
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("You have exceeded 100% limit in the category " + categoryValue);
                        textView.setTextColor(Color.RED);
                        builder.setTitle("Budget warning");

                        builder.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });
                        final AlertDialog alert = builder.create();
                        alert.show();

                    }


                    /*NotificationManager notif = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    Notification notify = new Notification.Builder
                            (getApplicationContext()).setContentTitle("Budget notification").setContentText(
                            "You have spent " + percent + " % in the category " + categoryValue).
                            setContentTitle("abc").setSmallIcon(R.drawable.ic_android_black_24dp).build();

                    notify.flags |= Notification.FLAG_AUTO_CANCEL;
                    notif.notify(0, notify);*/
                }
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

        EditText pinToAuth = findViewById(R.id.pinTextBox);
        String pinToCheck = pinToAuth.getText().toString();

        dbPinTable = new DatabaseHandler(this);
        Cursor cursor = dbPinTable.getPinData();

        // check if a value of PIN exists in dbTable - PIN
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String pinSavedInDB = cursor.getString(0);
                if (pinSavedInDB.equals(pinToCheck)) {
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
                    navigationView.setCheckedItem(R.id.nav_transaction);
                } else {
                    TextView incorrectPin = findViewById(R.id.incorrectPin);
                    incorrectPin.setText("PIN entered is wrong, please check");
                }
            }
        }
    }


    public void forgotPin(View view) {

        dbPinTable = new DatabaseHandler(this);
        Cursor cursor = dbPinTable.getPinData();

        cursor.moveToFirst();
        String savedPin = cursor.getString(0);
        String toMail = cursor.getString(1);
        String to[] = new String[] {toMail};


        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Pin details to access Exspendables application");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "The saved pin is: "+ savedPin);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("EMAIL_SENT", "Finished sending email...");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {

        DialogFragment datePicker = new com.example.exspendables.DatePicker();

        ArrayAdapter<String> categoryAdapter;
        String[] values;
        SparseBooleanArray checked;
        ListView categoryList;
        List<String> categorylist;

        switch (v.getId()) {

            case R.id.set_btn:
                // harish - 25.05
                Toast.makeText(this, "Button clicked", Toast.LENGTH_SHORT).show();
                // harish - 25.05
                EditText pinValue = findViewById(R.id.set_Pin_textbox);
                EditText reEnterPinValue = findViewById(R.id.confirm_Pin_textbox);
                EditText emailId = findViewById(R.id.setEmail);

                String pin = pinValue.getText().toString();
                String pinToConfirm = reEnterPinValue.getText().toString();
                String emailIdValue = emailId.getText().toString();

                if (pin.equals(pinToConfirm)) {
                    dbPinTable = new DatabaseHandler(this);
                    dbPinTable.addData(pin, emailIdValue);
                    // harish - 25.05
                    Toast.makeText(getApplicationContext(), "PIN details saved", Toast.LENGTH_SHORT).show();
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

                FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
                contentFrameLayout.removeAllViewsInLayout();
                getLayoutInflater().inflate(R.layout.category_popup, contentFrameLayout);

                populateListCategories();

                break;

            case R.id.deleteTxnBtn:
                Transactions transactions = new Transactions(this);

                TableLayout tableLayout = (TableLayout) findViewById(R.id.tab);
                int rowSize = tableLayout.getChildCount();
                int colSize = 0;
                TableRow tableRow = new TableRow(this);
                TextView cData = new TextView(this);

                String[] tableValues = new String[4];
                List recordToDelete = new ArrayList();

                for(int row = 1; row < rowSize;row++){

                    tableRow = (TableRow) tableLayout.getChildAt(row);
                    colSize = tableRow.getChildCount();

                    CheckBox checkBox = (CheckBox) tableRow.getChildAt(0);
                    if(checkBox.isChecked()){
                        cData = (TextView) tableRow.getChildAt(1);
                        tableValues[0] = cData.getText().toString();
                        cData = (TextView) tableRow.getChildAt(2);
                        tableValues[1] = cData.getText().toString();
                        cData = (TextView) tableRow.getChildAt(3);
                        tableValues[2] = cData.getText().toString();
                        cData = (TextView) tableRow.getChildAt(4);
                        tableValues[3] = cData.getText().toString();

                        boolean result = transactions.deleteData(tableValues[0],tableValues[1],tableValues[2],tableValues[3]);
                        Toast.makeText(getApplicationContext(), "Transaction deleted", Toast.LENGTH_SHORT).show();
                        recordToDelete.add(row);
                    }
                }
                for(int i = 0; i < recordToDelete.size(); i++) {
                    tableLayout.removeViewAt((int)recordToDelete.get(i));
                }
                break;

            case R.id.deleteCategoryBtn:
                dbCategories = new Categories(this);
                categoryList = findViewById(R.id.categorylist);

                ArrayList<CheckboxIconCategory> checkboxIconCategories = new ArrayList<>();

                ListAdapter listAdapter =  categoryList.getAdapter();

                for(int index=0; index < listAdapter.getCount();index++){
                    checkboxIconCategories.add((CheckboxIconCategory) listAdapter.getItem(index));
                }

                CheckboxIconCategory checkedCategory = null;
                recordToDelete = new ArrayList();

                int noOfItemsChecked = 0;
                for(int i=0;i<categoryList.getCount();i++){

                    checkedCategory = (CheckboxIconCategory) listAdapter.getItem(i);
                    if(checkedCategory.ismIsChecked()){
                        noOfItemsChecked++;
                        dbCategories.deleteData(checkedCategory.getmIconName());
                        recordToDelete.add(i);
                    }
                }

                for(int i = 0;i < recordToDelete.size();i++){
                    checkboxIconCategories.remove(recordToDelete.get(i));
                }

                if (noOfItemsChecked == 1) {
                    Toast.makeText(getApplicationContext(), "Category is deleted", Toast.LENGTH_SHORT).show();
                } else if (noOfItemsChecked > 1) {
                    Toast.makeText(getApplicationContext(), "Categories are deleted", Toast.LENGTH_SHORT).show();
                }

               //refresh the list with new value
                populateListCategories();

                break;


            case R.id.addCategoryBtn:
                FrameLayout contentFrameLayout1 = (FrameLayout) findViewById(R.id.fragment_container);
                contentFrameLayout1.removeAllViewsInLayout();
                getLayoutInflater().inflate(R.layout.add_category, contentFrameLayout1);

                initIconList();

                Spinner categoryIcons = findViewById(R.id.iconddlb);
                mAdapter = new IconAdapter(this, mCategoryList);
                categoryIcons.setAdapter(mAdapter);

                Button okAddCategory = findViewById(R.id.btnOKAddCategory);
                okAddCategory.setOnClickListener(this);
                break;

            case R.id.btnOKAddCategory:

                dbCategories = new Categories(this);
                EditText newCategoryValue = findViewById(R.id.add_cat_textbox);

                if(newCategoryValue.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Please enter a category name," +
                                    " Icon is optional",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Spinner iconddlb = (Spinner) findViewById(R.id.iconddlb);

                    if(iconddlb.getSelectedItemPosition() > 0) {
                        CategoryIcon selectedItem = (CategoryIcon) iconddlb.getSelectedItem();
                        int iconImg = selectedItem.getmIconImage();
                        dbCategories.addData(newCategoryValue.getText().toString(),iconImg);
                    }
                    else {
                        dbCategories.addData(newCategoryValue.getText().toString());
                    }
                    // harish - 25.05
                    Toast.makeText(getApplicationContext(), "New category added", Toast.LENGTH_SHORT).show();

                    // harish - 25.05
                    FrameLayout contentFrameLayout2 = (FrameLayout) findViewById(R.id.fragment_container);
                    contentFrameLayout2.removeAllViewsInLayout();
                    getLayoutInflater().inflate(R.layout.category_popup, contentFrameLayout2);

                    //refresh the list with new value
                    populateListCategories();
                }
                break;

            case R.id.modifyCategoryBtn:
                // create a layout with a EditText and OK button on click on "Modify"

                int position = -1;

                categoryList = findViewById(R.id.categorylist);

                checkboxIconCategories = new ArrayList<>();

                listAdapter =  categoryList.getAdapter();

                for(int index=0; index < listAdapter.getCount();index++){
                    checkboxIconCategories.add((CheckboxIconCategory) listAdapter.getItem(index));
                }

                noOfItemsChecked = 0;
                for(int i=0;i<categoryList.getCount();i++){
                    checkedCategory = (CheckboxIconCategory) listAdapter.getItem(i);
                    if(checkedCategory.ismIsChecked()){
                        noOfItemsChecked++;
                        oldValue = checkedCategory.mIconName;
                        oldIcon = checkedCategory.mIconImage;
                    }
                }

                if(noOfItemsChecked > 1){
                    Toast.makeText(getApplicationContext(), "Please select only one category " +
                            "for modification", Toast.LENGTH_SHORT).show();
                }
                else if(noOfItemsChecked == 0){
                    Toast.makeText(getApplicationContext(), "Please select one category " +
                            "for modification", Toast.LENGTH_SHORT).show();
                }
                else{
                    FrameLayout contentFrameLayout3 = (FrameLayout) findViewById(R.id.fragment_container);
                    contentFrameLayout3.removeAllViewsInLayout();
                    getLayoutInflater().inflate(R.layout.modify_category, contentFrameLayout3);

                    EditText valueToBeModified = findViewById(R.id.modify_cat_textbox);
                    valueToBeModified.setText(oldValue);

                    initIconList();

                    categoryIcons = findViewById(R.id.iconddlb2);
                    mAdapter = new IconAdapter(this, mCategoryList);
                    categoryIcons.setAdapter(mAdapter);

                    for(int i = 0; i < mCategoryList.size(); i++){
                        if(mCategoryList.get(i).getmIconImage() == oldIcon){
                            position = i;
                            break;
                        }
                    }

                    categoryIcons.setSelection(position);

                    Button okModifyButton = findViewById(R.id.btnOKModifyCategory);
                    okModifyButton.setOnClickListener(this);
                }

                break;

            case R.id.btnOKModifyCategory:

                EditText newValue = findViewById(R.id.modify_cat_textbox);

                Spinner iconddlb = (Spinner) findViewById(R.id.iconddlb2);

                CategoryIcon selectedItem = (CategoryIcon) iconddlb.getSelectedItem();
                int iconImg = selectedItem.getmIconImage();

                if(oldValue.equals(newValue) && oldIcon == iconImg){
                    Toast.makeText(getApplicationContext(), "No modifications done, data not saved",
                            Toast.LENGTH_SHORT).show();
                }
                else {

                    dbCategories = new Categories(this);
                    dbCategories.modifyData(newValue.getText().toString(), oldValue, iconImg);
                    // harish - 25.05
                    Toast.makeText(getApplicationContext(), "Changes saved", Toast.LENGTH_SHORT).show();
                    // harish - 25.05
                    FrameLayout contentFrameLayout4 = (FrameLayout) findViewById(R.id.fragment_container);
                    contentFrameLayout4.removeAllViewsInLayout();
                    getLayoutInflater().inflate(R.layout.category_popup, contentFrameLayout4);

                    populateListCategories();
                }
                break;
            // abhivanth,changed "change_pin" to switch_pin
            case R.id.changePin:

                FrameLayout contentFrameLayout5 = (FrameLayout) findViewById(R.id.fragment_container);
                contentFrameLayout5.removeAllViewsInLayout();
                getLayoutInflater().inflate(R.layout.switch_pin, contentFrameLayout5);

                Switch enablePin = findViewById(R.id.enablePin);
                enablePin.setOnClickListener(this);

                enableSwitch();
                break;

            case R.id.enablePin:

                enablePin = findViewById(R.id.enablePin);
                if (enablePin.isChecked()) {
                    FrameLayout contentFrameLayout6 = (FrameLayout) findViewById(R.id.fragment_container);
                    contentFrameLayout6.removeAllViewsInLayout();
                    getLayoutInflater().inflate(R.layout.change_pin, contentFrameLayout6);

                    emailId = findViewById(R.id.setEmail);
                    dbPinTable = new DatabaseHandler(this);
                    Cursor emailCursor = dbPinTable.getPinData();
                    emailCursor.moveToFirst();

                    try{
                        String email = emailCursor.getString(1);
                        if (!email.equals("")){
                            emailId.setText(email);
                        }

                    } catch(Exception e) {
                        Log.i("No Value found", "No Value");
                    }


                    Button changePinOkBtn = findViewById(R.id.change_pin_ok);
                    changePinOkBtn.setOnClickListener(this);
                } else {
                    //dbPinTable.deleteData();
                }
                break;

            case R.id.change_pin_ok:

                pinValue = findViewById(R.id.set_Pin_textbox);
                reEnterPinValue = findViewById(R.id.confirm_Pin_textbox);
                emailId = findViewById(R.id.setEmail);
                pin = pinValue.getText().toString();
                pinToConfirm = reEnterPinValue.getText().toString();
                emailIdValue = emailId.getText().toString();

                if (emailIdValue.equals("") || pin.equals("") || pinToConfirm.equals("") ) {
                    Toast.makeText(getApplicationContext(), "Enter All Values", Toast.LENGTH_LONG).show();
                    break;

                } else if (!emailIdValue.equals("") && !pin.equals("") && !pinToConfirm.equals("") ) {

                    if (pin.equals(pinToConfirm)) {
                        dbPinTable = new DatabaseHandler(this);
                        Cursor pinCursor = dbPinTable.getPinData();

                        // check if a value of PIN exists in dbTable - PIN
                        if (pinCursor != null) {
                            if (pinCursor.moveToFirst()) {
                                String pinSavedInDB = pinCursor.getString(0);
                                dbPinTable.modifyData(pin, pinSavedInDB, emailIdValue);
                                // harish - 25.05
                                Toast.makeText(getApplicationContext(), "Changes saved", Toast.LENGTH_SHORT).show();
                                // harish - 25.05
                                FrameLayout contentFrameLayout6 = (FrameLayout) findViewById(R.id.fragment_container);
                                contentFrameLayout6.removeAllViewsInLayout();
                                getLayoutInflater().inflate(R.layout.settings, contentFrameLayout6);

                                Button categoryDisplay = findViewById(R.id.edit_categories);
                                categoryDisplay.setOnClickListener(this);
                                Button changePinBtn = findViewById(R.id.changePin);
                                changePinBtn.setOnClickListener(this);
                                break;
                            } else {

                                dbPinTable.addData(pin, emailIdValue);
                                Toast.makeText(getApplicationContext(), "Changes saved", Toast.LENGTH_SHORT).show();
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

    private void populateListCategories() {
        String[] values;

        ArrayList<CheckboxIconCategory> mCategoryListView;
        mCategoryListView = new ArrayList<>();

        dbCategories = new Categories(this);
        List<String> categorylist = dbCategories.getCategoryAndIcon();

        ListView categoryList = findViewById(R.id.categorylist);

        Button deleteCategory = findViewById(R.id.deleteCategoryBtn);
        Button modifyCategory = findViewById(R.id.modifyCategoryBtn);
        final Button addCategory = findViewById(R.id.addCategoryBtn);

        for(int listIdx = 0; listIdx < categorylist.size(); listIdx++){

            String dummy = categorylist.get(listIdx);
            values = dummy.split(";");

            switch (values[1]){
                case "2131230918":
                    mCategoryListView.add(new CheckboxIconCategory(values[0], R.drawable.ic_airplane,false));
                    break;

                case "2131230923":
                    mCategoryListView.add(new CheckboxIconCategory(values[0], R.drawable.ic_bagel,false));
                    break;

                case "2131230924":
                    mCategoryListView.add(new CheckboxIconCategory(values[0], R.drawable.ic_barbershop,false));
                    break;

                case "2131230925":
                    mCategoryListView.add(new CheckboxIconCategory(values[0], R.drawable.ic_bonds,false));
                    break;

                case "2131230926":
                    mCategoryListView.add(new CheckboxIconCategory(values[0], R.drawable.ic_business,false));
                    break;

                case "2131230927":
                    mCategoryListView.add(new CheckboxIconCategory(values[0], R.drawable.ic_buy,false));
                    break;

                case "2131230928":
                    mCategoryListView.add(new CheckboxIconCategory(values[0], R.drawable.ic_circus,false));
                    break;

                case "2131230929":
                    mCategoryListView.add(new CheckboxIconCategory(values[0], R.drawable.ic_debit_card,false));
                    break;

                case "2131230930":
                    mCategoryListView.add(new CheckboxIconCategory(values[0], R.drawable.ic_globe,false));
                    break;

                case "2131230932":
                    mCategoryListView.add(new CheckboxIconCategory(values[0], R.drawable.ic_hotel,false));
                    break;

                case "2131230938":
                    mCategoryListView.add(new CheckboxIconCategory(values[0], R.drawable.ic_railway,false));
                    break;

                default:
                    mCategoryListView.add(new CheckboxIconCategory(values[0],R.drawable.ic_white_box,false));
                    break;
            }


        }

        ListIconAdapter mListIconAdapter = new ListIconAdapter(this,mCategoryListView);
        categoryList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        categoryList.setAdapter(mListIconAdapter);

        deleteCategory.setOnClickListener(this);
        modifyCategory.setOnClickListener(this);
        addCategory.setOnClickListener(this);
    }

    public void maxbudget(View view) {
        Button button = findViewById(R.id.maxbudget);
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

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_transaction);

    }

    public void sendemail(View view){

        String[] to = {"shudarsan20@gmail.com"};

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL,to);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Transaction details");

        //ListView transactionLV = findViewById(R.id.transactionlist);
        TableLayout tableLayout = findViewById(R.id.tab);
        Button deleteTransaction = findViewById(R.id.deleteTxnBtn);
        deleteTransaction.setOnClickListener(this);

        int count = tableLayout.getChildCount();
        TextView textView = new TextView(this);
        //int count = transactionLV.getCount();
        String content = "";
        for(int index = 0;index < count;index++){
            //read rows
            TableRow tableRow = (TableRow) tableLayout.getChildAt(index);

            int columnsize = tableRow.getChildCount();

            for(int c = 0; c < columnsize; c++){
                textView = (TextView) tableRow.getChildAt(c);
                content = content + "\t" + textView.getText().toString();
            }
            content = content + "\n";
            //String temp = transactionLV.getItemAtPosition(index).toString();
            //temp = temp.replaceAll(";","\t");
            //content = content + "\n" + temp;
        }

        emailIntent.putExtra(Intent.EXTRA_TEXT, content);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("EMAIL_SENT", "Finished sending email...");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void backToSettings(View view) {
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        contentFrameLayout.removeAllViewsInLayout();
        getLayoutInflater().inflate(R.layout.settings, contentFrameLayout);

        Button categoryDisplay = findViewById(R.id.edit_categories);
        categoryDisplay.setOnClickListener(this);

        Button changePinBtn = findViewById(R.id.changePin);
        changePinBtn.setOnClickListener(this);

    }

    public void backFromPin(View view) {
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        contentFrameLayout.removeAllViewsInLayout();
        getLayoutInflater().inflate(R.layout.switch_pin, contentFrameLayout);

        dbPinTable = new DatabaseHandler(this);
        Cursor cursor = dbPinTable.getPinData();
        dbPinTable.deleteData();

        Toast.makeText(getApplicationContext(), "The data is not saved", Toast.LENGTH_LONG).show();

        Switch enablePin = findViewById(R.id.enablePin);
        enablePin.setOnClickListener(this);

    }

    public void backToCategory(View view) {
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        contentFrameLayout.removeAllViewsInLayout();
        getLayoutInflater().inflate(R.layout.category_popup, contentFrameLayout);

        populateListCategories();

    }

    // Abhivanth , enable switch.
    public void enableSwitch() {
        Switch enablepinBtn = findViewById(R.id.enablePin);

        dbPinTable = new DatabaseHandler(this);
        Cursor cursor = dbPinTable.getPinData();
        cursor.moveToFirst();

        // check if a value of PIN exists in dbTable - PIN
        // abhivanth , changed "login_pin" to
        if (cursor != null ) {
          if (cursor.moveToFirst()) {
                enablepinBtn.setChecked(true);
            } else {
                enablepinBtn.setChecked(false);

            }
        }
    }

    private void initIconList() {
        mCategoryList = new ArrayList<>();
        mCategoryList.add(new CategoryIcon(R.drawable.ic_white_box));
        /*mCategoryList.add(new CategoryIcon(R.drawable.clothing));
        mCategoryList.add(new CategoryIcon(R.drawable.food));
        mCategoryList.add(new CategoryIcon(R.drawable.groceries));
        mCategoryList.add(new CategoryIcon(R.drawable.shopping));*/
        mCategoryList.add(new CategoryIcon(R.drawable.ic_airplane));
        mCategoryList.add(new CategoryIcon(R.drawable.ic_globe));
        mCategoryList.add(new CategoryIcon(R.drawable.ic_bagel));
        mCategoryList.add(new CategoryIcon(R.drawable.ic_barbershop));
        mCategoryList.add(new CategoryIcon(R.drawable.ic_bonds));
        mCategoryList.add(new CategoryIcon(R.drawable.ic_business));
        mCategoryList.add(new CategoryIcon(R.drawable.ic_buy));
        mCategoryList.add(new CategoryIcon(R.drawable.ic_circus));
        mCategoryList.add(new CategoryIcon(R.drawable.ic_railway));
        mCategoryList.add(new CategoryIcon(R.drawable.ic_debit_card));
        mCategoryList.add(new CategoryIcon(R.drawable.ic_hotel));
    }


    public void setIcons(View view) {

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
        int iconImg = selectedItem.getmIconImage();

        Categories categories = new Categories(this);
        categories.modifyIcon(cat,iconImg);
        Toast.makeText(getApplicationContext(), "Icon saved", Toast.LENGTH_SHORT).show();
    }

    public void backToFilter(View view) {
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        contentFrameLayout.removeAllViewsInLayout();
        getLayoutInflater().inflate(R.layout.graph_summary_list, contentFrameLayout);
        this.openSummaryPage(view);
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
        }
        else if(entryDateTV.getText().toString() != ""
                && endDateTV.getText().toString() != ""){

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

        String indicatorValue = "";

        RadioGroup transactiontype = findViewById(R.id.radioTransactionType);
        if (transactiontype.getCheckedRadioButtonId() == R.id.radioIncome) {
            indicatorValue = "Income";
        } else {
            indicatorValue = "Expense";
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

                    if (indicator.equals(indicatorValue)) {
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
                    if(!values[index].equals("")) {
                        recordsOK.add(values[index]);
                    }
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

                    //startdateToCheck.setDate(startdateToCheck.getDate() - 1);

                    try {
                        enddateToCheck = Formatter.parse(endDateValue);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    startdateToCheck.setDate(startdateToCheck.getDate() - 1);
                    enddateToCheck.setDate(enddateToCheck.getDate() + 1);

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
                            index--;
                        }
                    }
                }

                if (categorySelected == true) {

                    for (int index = 0; index < recordsOK.size(); index++) {

                        String[] dummy = recordsOK.get(index).split(";");

                        if (dummy[0].equals(categoryValueSelected)) {

                        } else {
                            recordsOK.remove(index);
                            index--;
                        }
                    }
                }

                if (paymentSelected == true) {

                    for (int index = 0; index < recordsOK.size(); index++) {

                        String[] dummy = recordsOK.get(index).split(";");

                        if (dummy[3].equals(paymentValueSelected)) {

                        } else {
                            recordsOK.remove(index);
                            index--;
                        }
                    }
                }

                if (amountSelected == true) {

                    String operatorValue = operator.getSelectedItem().toString();

                    for (int index = 0; index < recordsOK.size(); index++) {

                        String[] dummy = recordsOK.get(index).split(";");
                        float amountFromDb = Float.valueOf(dummy[2]);

                        if (operatorValue.equals("Greater than")) {
                            if ( amountFromDb > amountValueSelected) {

                            } else {
                                recordsOK.remove(index);
                                index--;
                            }
                        } else {
                            if ( amountFromDb  < amountValueSelected) {

                            } else {
                                recordsOK.remove(index);
                                index--;
                            }
                        }
                    }
                }

                FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.fragment_container);
                contentFrameLayout.removeAllViewsInLayout();
                getLayoutInflater().inflate(R.layout.tablesummary,contentFrameLayout);
            /*    getLayoutInflater().inflate(R.layout.listsummary, contentFrameLayout);

                ListView transactions = findViewById(R.id.transactionlist); */
                Button deleteTransaction = findViewById(R.id.deleteTxnBtn);
                deleteTransaction.setOnClickListener(this);

                values = new String[recordsOK.size()];
                for (int index = 0; index < recordsOK.size(); index++) {
                    values[index] = recordsOK.get(index);
                }


                TableLayout tableLayout = (TableLayout) findViewById(R.id.tab);

                TableRow tableRow = new TableRow(this);

                TextView catRow    = new TextView(this);
                TextView dateRow   = new TextView(this);
                TextView amountRow = new TextView(this);
                TextView paymRow   = new TextView(this);

                Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                catRow.setText("Category");
                catRow.setTypeface(boldTypeface);

                dateRow.setText("Date");
                dateRow.setTypeface(boldTypeface);

                amountRow.setText("Amount");
                amountRow.setTypeface(boldTypeface);

                paymRow.setText("Payment Type");
                paymRow.setTypeface(boldTypeface);

                TextView dummyTV = new TextView(this);

                tableRow.addView(dummyTV);
                tableRow.addView(catRow);
                tableRow.addView(dateRow);
                tableRow.addView(amountRow);
                tableRow.addView(paymRow);


                tableLayout.addView(tableRow,0);

                for (int row = 0; row < values.length; row++) {
                    //values[row] = values[row].replaceAll(";", "\t");

                    String[] dummy = values[row].split(";");
                    //TableRow tableRow = (TableRow) findViewById(R.id.tableData);
                    tableRow = new TableRow(this);

                    CheckBox checkBox = new CheckBox(this);

                    /*TextView catRow = (TextView) findViewById(R.id.catRow);
                    TextView dateRow = (TextView) findViewById(R.id.dateRow);
                    TextView amountRow = (TextView) findViewById(R.id.amountRow);
                    TextView paymRow = (TextView) findViewById(R.id.paymRow);*/

                    catRow    = new TextView(this);
                    dateRow   = new TextView(this);
                    amountRow = new TextView(this);
                    paymRow   = new TextView(this);

                    catRow.setText(dummy[0]);
                    dateRow.setText(dummy[1]);
                    amountRow.setText(dummy[3]);
                    paymRow.setText(dummy[2]);

                    tableRow.addView(checkBox);
                    tableRow.addView(catRow);
                    tableRow.addView(dateRow);
                    tableRow.addView(paymRow);
                    tableRow.addView(amountRow);

                    tableLayout.addView(tableRow,row+1);
                }

              /*  ArrayAdapter<String> transactionAdapter = new ArrayAdapter<String>
                        (this, android.R.layout.simple_list_item_multiple_choice, values);
                transactions.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                transactions.setAdapter(transactionAdapter);*/

                if(values.length == 0){
                    Toast.makeText(getApplicationContext(), "None of the transaction matches this criteria", Toast.LENGTH_SHORT).show();
                }
            }

            else{
                Toast.makeText(getApplicationContext(), "Select atleast one filter", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

