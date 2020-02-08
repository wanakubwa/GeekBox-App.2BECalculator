package com.geekbox.souvrecalculator;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class Settings {

    private static Map<Integer, Double> percentageLvls = new HashMap<Integer, Double>(){{
       put(1, 200.0);
       put(3, 1000.0);
       put(6, 3500.0);
       put(9, 7000.0);
       put(12, 12000.0);
       put(16, 20000.0);
       put(21, 30000.0);
    }};

    public static Map<Integer, Double> getPercentageLvls(){
        return percentageLvls;
    }
}
