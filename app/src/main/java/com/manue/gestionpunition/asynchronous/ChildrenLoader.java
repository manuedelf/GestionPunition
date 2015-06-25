package com.manue.gestionpunition.asynchronous;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.manue.gestionpunition.MainActivity;
import com.manue.gestionpunition.R;
import com.manue.gestionpunition.adapter.ChildAdapter;
import com.manue.gestionpunition.database.Database;
import com.manue.gestionpunition.model.Child;

import java.util.List;

/**
 * Created by wede7391 on 04/02/2015.
 */
public class ChildrenLoader extends AsyncTask<Void, Void, Void> {
    private final MainActivity activity;
    private List<Child> children;
    private ProgressDialog progressDialog;
    private Database db;

    public ChildrenLoader(MainActivity activity) {
        this.activity = activity;
        db = new Database(activity);
    }

    @Override
    protected Void doInBackground(Void[] params) {

        db.open(false);
        children = db.getChildren();
        db.close();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        progressDialog.cancel();
        if (children != null) {
            activity.listView.setAdapter(new ChildAdapter(activity, R.layout.row, R.id.TextView, children));

        } else {
            Toast.makeText(activity, activity.getString(R.string.no_child),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPreExecute() {
        children = null;
        progressDialog = ProgressDialog.show(activity, "",
                activity.getString(R.string.loading), true, true);
    }
}
