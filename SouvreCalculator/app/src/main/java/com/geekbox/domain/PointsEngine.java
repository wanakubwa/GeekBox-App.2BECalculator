package com.geekbox.domain;

import android.util.Log;

import com.geekbox.primitives.Group;
import com.geekbox.souvrecalculator.Settings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PointsEngine {
    private double _mastersPoints = 0;
    private int _masterLvl = 0;
    private double _profit = 0;
    private double _balancePercentage = 0;
    private double _pointsSum = 0;

    private ArrayList<Group> _listElements = new ArrayList<>();

    public void setListElements(ArrayList<Group> listElements){
        _listElements = listElements;
    }

    public ArrayList<Group> getListElements(){
        return _listElements;
    }

    public void setMasterPoints(double points){
        _mastersPoints = points;
    }

    /**
     *
     */
    public void calculateValues(){
        calculatePointsSum();
        setPercentageLvls();
        calculateProfit();
        calculateBalance();
    }

    /**
     *
     */
    private void calculateBalance() {
        List<Double> shareFactors = getSharesForGroups();

        // Find max value from collections using generic method.
        _balancePercentage = Collections.max(shareFactors);
    }

    /**
     *
     * @return
     */
    private List<Double> getSharesForGroups() {
        List<Double> shares = new ArrayList<>();

        for (Group group : _listElements){
            double singleShare = (group.getPoints()/_pointsSum)*100.0;
            shares.add(singleShare);
        }
        return shares;
    }

    /**
     *
     */
    private void calculateProfit() {
        double profitSum = 0.0;

        for(Group group : _listElements){
            int lvlDifference = _masterLvl - group.getProfitLvl();
            profitSum += getProfitForValues(lvlDifference, group.getPoints());
        }

        profitSum += getProfitForValues(_masterLvl, _mastersPoints);
        _profit = profitSum;
    }
    private double getProfitForValues(double lvlDiff, double points){
        double profit = (lvlDiff/100)*points;
        return profit;
    }

    /**
     *
     */
    private void setPercentageLvls() {
        for(Group group : _listElements){
            group.setProfitLvl(getPercentageLvlForValue(group.getPoints()));
        }
        _masterLvl = getPercentageLvlForValue(_pointsSum);
    }

    /**
     * Calculating points sum for each grup and master points.
     */
    private void calculatePointsSum() {
        double sum = 0;

        for(Group group : _listElements){
            sum += group.getPoints();
        }
        sum += _mastersPoints;
        _pointsSum = sum;
    }

    /**
     *
     * @param value
     * @return
     */
    private int getPercentageLvlForValue(double value){
        Map<Integer, Double> lvls = Settings.getPercentageLvls();
        int groupLvl = 0;

        for (Map.Entry<Integer,Double> lvl : lvls.entrySet()){
            if(value >= lvl.getValue()){
                if(groupLvl < lvl.getKey()){
                    groupLvl = lvl.getKey();
                }
            }
        }

        return groupLvl;
    }

    public double getPointsSum(){

        return _pointsSum;
    }

    public double getBalancePercentage(){

        return _balancePercentage;
    }

    public double getProfit(){

        return _profit;
    }

    public int getMasterLvl(){
        return _masterLvl;
    }
}
