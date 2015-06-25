package com.manue.gestionpunition.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.manue.gestionpunition.R;
import com.manue.gestionpunition.activities.PunitionActivity;
import com.manue.gestionpunition.asynchronous.PunitionDeletion;
import com.manue.gestionpunition.model.Punition;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by WEDE7391 on 05/02/2015.
 */
public class PunitionAdapter extends ArrayAdapter<Punition> {
    private final PunitionActivity activity;
    private List<Punition> punitions = new ArrayList<Punition>();

    public PunitionAdapter(PunitionActivity activity) {
        super(activity, android.R.layout.simple_list_item_1);
        this.activity = activity;
    }

    public void setdata(List<Punition> punitions) {
        this.punitions = punitions;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        view = convertView;
        if (view == null) {
            final LayoutInflater layoutInflater = activity.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.row_punition, parent, false);
        }
        final TextView label = (TextView) view.findViewById(R.id.TextViewPunition);
        final Punition punition = punitions.get(position);
        label.setText(punition.name);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        ((TextView) view.findViewById(R.id.TextViewDebut)).setText(dateFormatter.format(punition.begin));
        ((TextView) view.findViewById(R.id.TextViewFin)).setText(dateFormatter.format(punition.end));
        ImageButton delete = (ImageButton) view.findViewById(R.id.DeleteButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PunitionDeletion deletePunition = new PunitionDeletion(PunitionAdapter.this.activity);
                deletePunition.execute(punition);
            }
        });

        return view;
    }

    @Override
    public int getCount() {
        return punitions.size();
    }
}
