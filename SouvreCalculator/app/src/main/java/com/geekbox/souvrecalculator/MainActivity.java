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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText _masterPoints;
    private TextView _profit;
    private TextView _pointsSum;
    private TextView _balance;
    private Button _calculateBtn;
    private ListView _listView;

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
        _listView = (ListView) findViewById(R.id.gropListView);

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

    // Creating groups instances.
    private ArrayList<Group> getGroupsFromDb(){
        ArrayList<Group> groups = new ArrayList<>();

        for(int i = 0; i < 25; i++ ){
            Group group = new Group();
            group.setPoints(0);
            group.setProfitLvl(0);
            groups.add(group);
        }

        return groups;
    }

    private void buttonsActionsInitialize(){
        _calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCalculateButtonPress();
            }
        });
    }

    // Action to be done when calculate button pressed.
    private void onCalculateButtonPress(){
        Toast.makeText(this.getBaseContext(), "Kalkulacje robie juz!", Toast.LENGTH_SHORT).show();
    }

    public void setListViewElemnts(ArrayList<Group> groups){
        MyAdapter adapter = new MyAdapter(this, R.layout.list_element, groups);
        _listView.setAdapter(adapter);
    }
}
