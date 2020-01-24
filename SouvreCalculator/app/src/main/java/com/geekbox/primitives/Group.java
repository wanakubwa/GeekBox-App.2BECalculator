package com.geekbox.primitives;

import android.widget.EditText;

public class Group {
    private int _profitLvl;
    private double _points;

    public int getProfitLvl(){
        return _profitLvl;
    }

    public double getPoints(){
        return _points;
    }

    public void setProfitLvl(int lvl){
        _profitLvl = lvl;
    }

    public void setPoints(double points){
        _points = points;
    }
}
