package com.manue.gestionpunition.activities;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.manue.gestionpunition.R;
import com.manue.gestionpunition.fragment.PunitionFragment;

public class PunitionActivity extends FragmentActivity {


    public PunitionFragment punitionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punition);

        if (savedInstanceState == null) {
            punitionFragment = new PunitionFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.container, punitionFragment)
                    .commit();
        }


    }


}
