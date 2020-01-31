package com.geekbox.souvrecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

// New androidx toolbar.
import androidx.appcompat.widget.Toolbar;

import com.geekbox.adapters.MyAdapter;
import com.geekbox.controller.PointsController;
import com.geekbox.domain.DatabaseEngine;
import com.geekbox.domain.PointsEngine;
import com.geekbox.primitives.Group;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText _masterPoints;
    private TextView _profit;
    private TextView _pointsSum;
    private TextView _balance;
    private TextView _masterLvl;
    private Button _calculateBtn;
    private Button _addItemBtn;
    private ListView _listView;
    private MyAdapter _adapter;
    private MySpinner toolSpinner;

    private PointsController _controller;
    private Toolbar mToolbar;

    private String currency;
    private String pointsShort;
    private boolean isInitialize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Controller initializing and creating.
        PointsEngine model = getPointsEngine();
        DatabaseEngine databaseEngine = getDatabaseEngine();
        _controller = new PointsController(model, this, databaseEngine);
        _controller.initializeLanguageVersion();

        setContentView(R.layout.activity_main);
        isInitialize = true;

        _masterPoints = (EditText) findViewById(R.id.masterPointsInsert);
        _profit = (TextView) findViewById(R.id.profitNum);
        _pointsSum = (TextView) findViewById(R.id.groupPointsNum);
        _balance = (TextView) findViewById(R.id.balanceNum);
        _calculateBtn = (Button) findViewById(R.id.calculateBtn);
        _addItemBtn = (Button) findViewById(R.id.addItemBtn);
        _listView = (ListView) findViewById(R.id.gropListView);
        _masterLvl = (TextView) findViewById(R.id.masterLvl);
        toolSpinner = (MySpinner) findViewById(R.id.toolSpinner);

        // Getting names from resources package.
        pointsShort = getString(R.string.pointsShort);
        currency = getString(R.string.currency);

        // Spinner initializing array strings content.
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getBaseContext(), R.array.languageSpinner,
                android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toolSpinner.setAdapter(spinnerAdapter);

        // Setting default statements of texts on screen.
        _masterPoints.setText("0");
        _profit.setText("0" + currency);
        _pointsSum.setText("0" + pointsShort);
        _balance.setText("0%");
        _listView.setFocusable(true);

        // Buttons initializing listeners actions.
        listenersInitialize();

        _controller.initialize();

        closeKeyboard();
    }

    private DatabaseEngine getDatabaseEngine() {
        return new DatabaseEngine(this);
    }

    protected Toolbar getActionBarToolbar() {
        if (mToolbar == null) {
            //mToolbar = (Toolbar) findViewById(R.id.toolBar2);
            if (mToolbar != null) {
                setSupportActionBar(mToolbar);
            }
        }
        return mToolbar;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getActionBarToolbar();
    }

    // Creating model instance.
    private PointsEngine getPointsEngine(){
        PointsEngine pointsEngine = new PointsEngine();

        // Getting groups initialize.
        pointsEngine.setListElements(getGroupsFromDb());
        return pointsEngine;
    }

    /**
     * Returning groups from database if exists.
     * @return Groups from DB.
     */
    private ArrayList<Group> getGroupsFromDb(){
        ArrayList<Group> groups = new ArrayList<>();

        for(int i = 0; i < 3; i++ ){
            Group group = new Group();
            group.setPoints(0);
            group.setProfitLvl(0);
            groups.add(group);
        }

        return groups;
    }

    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Initializing all buttons actions when pressed on this view.
     */
    private void listenersInitialize(){

        // Calculate btn listener.
        _calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                _controller.calculateValues();
            }
        });

        // Add item btn listener.
        _addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                _controller.addNewGroupToList();
            }
        });

        // Initializing tool bar spinner listener.
        toolSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(!isInitialize){
                    String selectedLanguage = toolSpinner.getSelectedItem().toString();
                    _controller.changeLanguage(selectedLanguage.toLowerCase());
                    _controller.resetView(getIntent());
                }
                isInitialize = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Setting list view and initialize elements on it.
     * @param groups Elements to display on screen.
     */
    public void setListViewElemnts(ArrayList<Group> groups){
        _adapter = new MyAdapter(this, R.layout.list_element, groups);
        _listView.setAdapter(_adapter);
    }

    /**
     * Displaying short error message on screen as a Toast popout.
     * @param text Text to display on screen.
     */
    public void displayErrorMessage(String text){
        Toast.makeText(this.getBaseContext(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Getting masters points as a double and validate for NumberFormatException.
     * If typed number is equals null or empty then write 0 in text view.
     * @return Masters points typed on text fill.
     */
    public double getMastersPoints(){
        String points = _masterPoints.getText().toString();
        double pointsValue = 0;

        if(points.equals(null) || points.equals("")){
            points = "0";
            _masterPoints.setText(points);
        }

        try{
            pointsValue = Double.valueOf(points);
        }
        catch(NumberFormatException ex){
            displayErrorMessage("Niepoprawna liczba Twoich punktów!");
        }

        return pointsValue;
    }

    public void actualizeList(){
        _adapter.notifyDataSetChanged();
    }
    public void setProfitOnScreen(double value){
        DecimalFormat format = new DecimalFormat("#.##");

        String text = String.valueOf(format.format(value));
        _profit.setText(text + currency);
    }
    public void setPointsSumOnScreen(double value){
        DecimalFormat format = new DecimalFormat("#.##");

        _pointsSum.setText(String.valueOf(format.format(value)) + pointsShort);
    }
    public void setBalanceOnScreen(double value){
        DecimalFormat format = new DecimalFormat("#.##");

        _balance.setText(String.valueOf(format.format(value)) + "%");
    }
    public void setMasterLvlOnScreen(int value){
        _masterLvl.setText(String.valueOf(value) + "%");
    }
}
