package com.manue.gestionpunition.asynchronous;

import android.os.AsyncTask;

import com.manue.gestionpunition.MainActivity;
import com.manue.gestionpunition.database.Database;
import com.manue.gestionpunition.model.Child;
import com.manue.gestionpunition.model.Punition;

import java.util.List;

/**
 * Created by wede7391 on 04/02/2015.
 */
public class ChildrenDeletion extends AsyncTask<Child, Void, Void> {
    private final MainActivity activity;
    private long id;
    private Database db;

    public ChildrenDeletion(MainActivity activity) {
        this.activity = activity;
        db = new Database(activity);
    }

    @Override
    protected Void doInBackground(Child... child) {

        db.open(false);
        if (child != null) {
            db.deleteChildren(child[0].id);
            List<Punition> punitions = db.getPunitions(child[0].id);
            for (Punition p : punitions) {
                db.deletePunition(p.id);
            }
        }
        db.close();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        ChildrenLoader loader = new ChildrenLoader(activity);
        loader.execute();
    }
}
