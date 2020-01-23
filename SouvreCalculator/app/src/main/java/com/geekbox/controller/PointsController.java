package com.geekbox.controller;

import com.geekbox.domain.PointsEngine;
import com.geekbox.souvrecalculator.MainActivity;

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
    }
}
