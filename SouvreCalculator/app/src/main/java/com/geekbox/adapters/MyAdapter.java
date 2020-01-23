package com.geekbox.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(_context);

        View view = inflater.inflate(R.layout.list_element, null);

        EditText pointsText = (EditText) view.findViewById(R.id.groupListPointsInsert);
        TextView profitLvl = (TextView) view.findViewById(R.id.grouplvlNum);
        TextView groupName = (TextView) view.findViewById(R.id.groupNameText);

        profitLvl.setText(String.valueOf(_groups.get(position).getProfitLvl()));
        groupName.setText("G" + position);

        return view;
    }
}
