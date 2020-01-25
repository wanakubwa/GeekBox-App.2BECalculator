package com.geekbox.controller;

import com.geekbox.domain.PointsEngine;
import com.geekbox.primitives.Group;
import com.geekbox.souvrecalculator.MainActivity;

import java.text.DecimalFormat;

public class PointsController {
    private PointsEngine _model;
    private MainActivity _view;

    public PointsController(PointsEngine model, MainActivity view){
        _model = model;
        _view = view;
    }

    /**
     * Initializing view elements.
     */
    public void initialize(){
        _view.setListViewElemnts(_model.getListElements());

        _view.setMasterLvlOnScreen(_model.getMasterLvl());
        _view.setProfitOnScreen(_model.getProfit());
        _view.setPointsSumOnScreen(_model.getPointsSum());
        _view.setBalanceOnScreen(_model.getBalancePercentage());
    }

    public void setMasterPoints(double points){
        _model.setMasterPoints(points);
    }

    public void calculateValues(){
        double masterPoints = _view.getMastersPoints();
        _model.setMasterPoints(masterPoints);

        _model.calculateValues();

        double profit = _model.getProfit();
        double pointsSum = _model.getPointsSum();
        double balance = _model.getBalancePercentage();
        int masterLvl = _model.getMasterLvl();

        _view.setProfitOnScreen(profit);
        _view.setPointsSumOnScreen(pointsSum);
        _view.setBalanceOnScreen(balance);
        _view.setMasterLvlOnScreen(masterLvl);

        _view.actualizeList();
    }

    public void addNewGroupToList(){
        Group group = new Group();
        group.setPoints(0);
        group.setProfitLvl(0);

        _model.addItemToList(group);
        _view.actualizeList();
    }
}
