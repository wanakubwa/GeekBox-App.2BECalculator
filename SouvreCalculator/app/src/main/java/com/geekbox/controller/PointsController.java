package com.geekbox.controller;

import com.geekbox.domain.PointsEngine;
import com.geekbox.domain.PointsEngineException;
import com.geekbox.primitives.Group;
import com.geekbox.souvrecalculator.MainActivity;

import java.text.DecimalFormat;
import java.util.NoSuchElementException;

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

    /**
     * Setting values on model instance and calculate.
     * Printing on view instace all ammounts from model.
     */
    public void calculateValues(){
        double masterPoints = _view.getMastersPoints();
        _model.setMasterPoints(masterPoints);

        // Catching an exception when user dont have any group added to list.
        try{
            _model.calculateValues();
        }
        catch(NoSuchElementException ex){
            _view.displayErrorMessage("Musisz posiadać co najmniej jedną grupę!");
        }

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

    /**
     * Adding new instance of model collection and refreshing listview from view instance.
     */
    public void addNewGroupToList(){

        Group group = new Group();
        group.setPoints(0);
        group.setProfitLvl(0);

        try{
            _model.addItemToList(group);
        }
        catch (PointsEngineException ex){
            _view.displayErrorMessage(ex.getMessage());
        }
        _view.actualizeList();
    }
}
