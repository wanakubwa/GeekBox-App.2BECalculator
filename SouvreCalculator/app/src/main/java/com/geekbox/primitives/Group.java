package com.geekbox.primitives;

public class Group {
    private double _profitLvl;
    private double _points;

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
}
