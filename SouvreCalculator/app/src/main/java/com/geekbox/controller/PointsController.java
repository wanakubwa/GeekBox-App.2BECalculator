package com.geekbox.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import com.geekbox.domain.PointsEngine;
import com.geekbox.domain.PointsEngineException;
import com.geekbox.primitives.Group;
import com.geekbox.souvrecalculator.MainActivity;

import java.util.Locale;
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
        // Language initialize.
        String language = getLanguage();
        changeLanguage(language);

        _view.setListViewElemnts(_model.getListElements());
        _view.setMasterLvlOnScreen(_model.getMasterLvl());
        _view.setProfitOnScreen(_model.getProfit());
        _view.setPointsSumOnScreen(_model.getPointsSum());
        _view.setBalanceOnScreen(_model.getBalancePercentage());
    }

    public void changeLanguage(String languageID){
        Locale locale = new Locale(languageID);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);

        _view.getBaseContext().getResources().updateConfiguration(configuration, _view.getBaseContext().getResources().getDisplayMetrics());

        // Save data to prferences.
        SharedPreferences.Editor editor = _view.getSharedPreferences("Settings", Context.MODE_PRIVATE).edit();
        editor.putString("Language", languageID);
        editor.apply();
    }

    private String getLanguage(){
        SharedPreferences preferences = _view.getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language;
        language = preferences.getString("Language", "");

        return language;
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

    public void resetView(Intent intent) {
        _view.finish();
        _view.startActivity(intent);
    }
}
