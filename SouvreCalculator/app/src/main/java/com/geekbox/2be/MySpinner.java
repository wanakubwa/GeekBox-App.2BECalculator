package com.geekbox.souvrecalculator;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatSpinner;

public class MySpinner extends AppCompatSpinner {
    private int lastPosition = 0;

    public MySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setSelection(int position, boolean animate) {
        super.setSelection(position, animate);
        if (position == lastPosition){
            getOnItemSelectedListener().onItemSelected(this,null,position,0);
        }
        lastPosition = position;
    }

    @Override
    public void setSelection(int position) {
        super.setSelection(position);
        if (position == lastPosition){
            getOnItemSelectedListener().onItemSelected(this,null,position,0);
        }
        lastPosition = position;
    }
}
