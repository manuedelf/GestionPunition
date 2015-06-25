package com.manue.gestionpunition.asynchronous;

import android.os.AsyncTask;

import com.manue.gestionpunition.MainActivity;
import com.manue.gestionpunition.database.Database;
import com.manue.gestionpunition.model.Child;

/**
 * Created by wede7391 on 04/02/2015.
 */
public class ChildrenCreation extends AsyncTask<Child, Void, Void> {
    private final MainActivity activity;
    private long id;
    private Database db;

    public ChildrenCreation(MainActivity activity) {
        this.activity = activity;
        db = new Database(activity);
    }

    @Override
    protected Void doInBackground(Child... child) {

        db.open(false);
        if (child != null) {
            id = db.insertChild(child[0]);
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
