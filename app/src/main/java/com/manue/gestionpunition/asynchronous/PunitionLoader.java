package com.manue.gestionpunition.asynchronous;

import android.app.Activity;
import android.content.AsyncTaskLoader;

import com.manue.gestionpunition.database.Database;
import com.manue.gestionpunition.model.Punition;

import java.util.List;

/**
 * Created by wede7391 on 04/03/2015.
 */
public class PunitionLoader extends AsyncTaskLoader<List<Punition>> {
    private Activity activity;
    private Long childId;
    private Database db;
    private List<Punition> punitions;

    public PunitionLoader(Activity activity, Long childId) {
        super(activity);
        this.activity = activity;
        this.childId = childId;
        db = new Database(activity);

    }

    @Override
    public List<Punition> loadInBackground() {
        db.open(false);
        punitions = db.getPunitions(childId);
        db.close();
        return punitions;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
