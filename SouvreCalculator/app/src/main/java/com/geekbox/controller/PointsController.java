package com.geekbox.controller;

import android.content.Intent;
import android.content.res.Configuration;

import com.geekbox.domain.DatabaseEngine;
import com.geekbox.domain.PointsEngine;
import com.geekbox.domain.PointsEngineException;
import com.geekbox.primitives.Group;
import com.geekbox.souvrecalculator.MainActivity;

import java.util.Locale;
import java.util.NoSuchElementException;

public class PointsController {
    private PointsEngine model;
    private MainActivity view;
    private DatabaseEngine database;

    public PointsController(PointsEngine model, MainActivity view, DatabaseEngine databaseEngine){
        this.model = model;
        this.view = view;
        this.database = databaseEngine;
    }

    public void initializeLanguageVersion(){
        // Language initialize.
        String language = database.getLanguage();
        changeLanguage(language);
    }

    /**
     * Initializing view elements.
     */
    public void initialize(){
        view.setListViewElemnts(model.getListElements());
    }

    public void changeLanguage(String languageID){
        Locale locale = new Locale(languageID);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);

        view.getBaseContext().getResources().updateConfiguration(configuration, view.getBaseContext().getResources().getDisplayMetrics());
        database.saveLanguage(languageID);
    }

    /**
     * Setting values on model instance and calculate.
     * Printing on view instace all ammounts from model.
     */
    public void calculateValues(){
        double masterPoints = view.getMastersPoints();
        model.setMasterPoints(masterPoints);

        double masterBonus = view.getMasterBonus();
        model.setMasterBonus(masterBonus);

        // Catching an exception when user dont have any group added to list.
        try{
            model.calculateValues();
        }
        catch(NoSuchElementException ex){
            view.displayErrorMessage("Musisz posiadać co najmniej jedną grupę!");
        }

        double profit = model.getProfit();
        double pointsSum = model.getPointsSum();
        double balance = model.getBalancePercentage();
        int masterLvl = model.getMasterLvl();

        view.setProfitOnScreen(profit);
        view.setPointsSumOnScreen(pointsSum);
        view.setBalanceOnScreen(balance);
        view.setMasterLvlOnScreen(masterLvl);

        view.actualizeList();
    }

    /**
     * Adding new instance of model collection and refreshing listview from view instance.
     */
    public void addNewGroupToList(){

        Group group = new Group();
        group.setPoints(0);
        group.setProfitLvl(0);

        try{
            model.addItemToList(group);
        }
        catch (PointsEngineException ex){
            view.displayErrorMessage(ex.getMessage());
        }
        view.actualizeList();
    }

    public void resetView(Intent intent) {
        view.finish();
        view.startActivity(intent);
    }
}
