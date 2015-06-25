package com.manue.gestionpunition.fragment;


import android.app.ListFragment;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.manue.gestionpunition.R;
import com.manue.gestionpunition.activities.AddPunitionActivity;
import com.manue.gestionpunition.activities.PunitionActivity;
import com.manue.gestionpunition.adapter.PunitionAdapter;
import com.manue.gestionpunition.asynchronous.PunitionLoader;
import com.manue.gestionpunition.model.Punition;

import java.util.List;

/**
 * Created by wede7391 on 04/03/2015.
 */
public class PunitionFragment extends ListFragment implements android.app.LoaderManager.LoaderCallbacks<List<Punition>> {
    PunitionAdapter adapter;
    private long childId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_punition, container, false);
        childId = getActivity().getIntent().getLongExtra("id", -1);
        Button button = (Button) rootView.findViewById(R.id.buttonAddPunition);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddPunitionActivity.class);
                i.putExtra("id", childId);
                startActivity(i);

            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new PunitionAdapter((PunitionActivity) getActivity());
        setListAdapter(adapter);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<List<Punition>> onCreateLoader(int id, Bundle args) {
        return new PunitionLoader(getActivity(), childId);
    }


    @Override
    public void onLoadFinished(android.content.Loader<List<Punition>> loader, List<Punition> punitions) {
        adapter.setdata(punitions);
    }


    @Override
    public void onLoaderReset(android.content.Loader<List<Punition>> loader) {
        adapter.setdata(null);
    }


}
