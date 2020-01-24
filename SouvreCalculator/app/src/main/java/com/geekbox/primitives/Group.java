package com.geekbox.primitives;

import android.widget.EditText;

public class Group {
    private double _profitLvl;
    private double _points;
    EditText _pointsText;

    public double getProfitLvl(){
        return _profitLvl;
    }

    public double getPoints(){
        return _points;
    }

    public void setProfitLvl(double lvl){
        _profitLvl = lvl;
    }

    public void setPoints(double points){
        _points = points;
    }

    public void setPointsText(EditText pointsText){
        _pointsText = pointsText;
    }
    public EditText getPointsText(){
        return _pointsText;
    }
}
