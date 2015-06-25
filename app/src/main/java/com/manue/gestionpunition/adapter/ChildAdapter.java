package com.manue.gestionpunition.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.manue.gestionpunition.MainActivity;
import com.manue.gestionpunition.R;
import com.manue.gestionpunition.activities.PunitionActivity;
import com.manue.gestionpunition.asynchronous.ChildrenDeletion;
import com.manue.gestionpunition.model.Child;

import java.util.List;

/**
 * Created by WEDE7391 on 05/02/2015.
 */
public class ChildAdapter extends ArrayAdapter<Child> {
    private final MainActivity activity;
    private final List<Child> children;
    private final int rowResourceId;

    public ChildAdapter(MainActivity activity, int rowResourceId,
                        int textViewResourceId, List<Child> objects) {
        super(activity, rowResourceId, textViewResourceId, objects);
        children = objects;
        this.activity = activity;
        this.rowResourceId = rowResourceId;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        view = convertView;
        if (view == null) {
            final LayoutInflater layoutInflater = activity.getLayoutInflater();
            view = layoutInflater.inflate(rowResourceId, null);
        }
        final TextView label = (TextView) view.findViewById(R.id.TextView);
        final Child child = children.get(position);
        label.setText(child.name);
        label.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(activity, PunitionActivity.class);
                i.putExtra("id", child.id);
                activity.startActivity(i);
            }
        });

        ImageButton delete = (ImageButton) view.findViewById(R.id.DeleteButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChildrenDeletion deleteChild = new ChildrenDeletion(ChildAdapter.this.activity);
                deleteChild.execute(child);
            }
        });

        return view;
    }
}
