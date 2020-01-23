package com.geekbox.domain;

import com.geekbox.primitives.Group;

import java.util.ArrayList;
import java.util.List;

public class PointsEngine {
    private double _mastersPoints;
    private ArrayList<Group> _listElements = new ArrayList<>();

    public void setListElements(ArrayList<Group> listElements){
        _listElements = listElements;
    }

    public ArrayList<Group> getListElements(){
        return _listElements;
    }

    public double getPointsSum(){

        return 100.0;
    }

    public double getBalance(){

        return 50.0;
    }

    public double getProfit(){

        return 1600.0;
    }
}
