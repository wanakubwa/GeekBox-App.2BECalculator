package com.geekbox.adapters;

import android.content.Context;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.os.Debug;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.geekbox.primitives.Group;
import com.geekbox.souvrecalculator.R;

import org.w3c.dom.Text;

import java.util.List;

public class MyAdapter extends ArrayAdapter<Group> {

    private Context _context;
    private int _resource;
    private List<Group> _groups;

    public MyAdapter(@NonNull Context context, int resource, @NonNull List<Group> objects) {
        super(context, resource, objects);

        _context = context;
        _resource = resource;
        _groups = objects;
    }

    @Nullable
    @Override
    public Group getItem(int position) {
        return _groups.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(_context);
            View view = inflater.inflate(R.layout.list_element, null);

            // Getting all display fields from screen.
            EditText editText = (EditText) view.findViewById(R.id.groupListPointsInsert);
            TextView profitLvl = (TextView) view.findViewById(R.id.grouplvlNum);
            TextView groupName = (TextView) view.findViewById(R.id.groupNameText);

            profitLvl.setText(String.valueOf(_groups.get(position).getProfitLvl()));
            groupName.setText("G" + position);

            final Group group = getItem(position);

            // Set display number if different than 0.
            if(group.getPoints() != 0){
                editText.setText(String.valueOf(group.getPoints()));
            }

            // Adding listener to watch when user input some data into field.
            editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String valueText = editable.toString();

                // Validations.
                if(valueText.equals(null) || valueText.equals("")){
                    valueText = "0";
                }

                double value;
                try{
                    value = Double.parseDouble(valueText);
                }
                catch(NumberFormatException ex){
                    Toast.makeText(_context, "Zła wartość!", Toast.LENGTH_SHORT).show();
                    value = 0;
                }
                group.setPoints(value);
            }
        });

        return view;
    }
}
