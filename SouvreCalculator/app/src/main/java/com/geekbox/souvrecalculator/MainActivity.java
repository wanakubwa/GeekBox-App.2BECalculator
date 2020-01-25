package com.geekbox.souvrecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.geekbox.adapters.MyAdapter;
import com.geekbox.controller.PointsController;
import com.geekbox.domain.PointsEngine;
import com.geekbox.primitives.Group;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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

    private PointsController _controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _masterPoints = (EditText) findViewById(R.id.masterPointsInsert);
        _profit = (TextView) findViewById(R.id.profitNum);
        _pointsSum = (TextView) findViewById(R.id.groupPointsNum);
        _balance = (TextView) findViewById(R.id.balanceNum);
        _calculateBtn = (Button) findViewById(R.id.calculateBtn);
        _addItemBtn = (Button) findViewById(R.id.addItemBtn);
        _listView = (ListView) findViewById(R.id.gropListView);
        _masterLvl = (TextView) findViewById(R.id.masterLvl);

        // Setting default statements of texts on screen.
        _masterPoints.setText("0");
        _profit.setText("0");
        _pointsSum.setText("0");
        _balance.setText("0");

        // Buttons initializing listeners actions.
        buttonsActionsInitialize();

        // Controller initializing and creating.
        PointsEngine model = getPointsEngine();
        _controller = new PointsController(model, this);
        _controller.initialize();
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

    /**
     * Initializing all buttons actions when pressed on this view.
     */
    private void buttonsActionsInitialize(){
        _calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _controller.calculateValues();
            }
        });

        _addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _controller.addNewGroupToList();
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
        _profit.setText(text +"zł");
    }
    public void setPointsSumOnScreen(double value){
        DecimalFormat format = new DecimalFormat("#.##");

        _pointsSum.setText(String.valueOf(format.format(value)) + "pkt.");
    }
    public void setBalanceOnScreen(double value){
        DecimalFormat format = new DecimalFormat("#.##");

        _balance.setText(String.valueOf(format.format(value)) + "%");
    }
    public void setMasterLvlOnScreen(int value){
        _masterLvl.setText(String.valueOf(value) + "%");
    }
}
