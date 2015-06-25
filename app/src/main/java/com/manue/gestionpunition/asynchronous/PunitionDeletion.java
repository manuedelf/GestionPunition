package com.manue.gestionpunition.asynchronous;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.CalendarContract;

import com.manue.gestionpunition.activities.PunitionActivity;
import com.manue.gestionpunition.database.Database;
import com.manue.gestionpunition.model.Punition;

/**
 * Created by wede7391 on 04/02/2015.
 */
public class PunitionDeletion extends AsyncTask<Punition, Void, Void> {
    private final PunitionActivity activity;
    private long id;
    private Database db;

    public PunitionDeletion(PunitionActivity activity) {
        this.activity = activity;
        db = new Database(activity);
    }

    @Override
    protected Void doInBackground(Punition... punition) {

        db.open(true);
        if (punition != null) {
            id = punition[0].id;
            //delete punition dans calendrier
            ContentResolver cr = activity.getContentResolver();
            Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, punition[0].calendarId);
            cr.delete(uri, null, null);
            //delete punition en base
            db.deletePunition(id);
        }
        db.close();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        activity.getLoaderManager().restartLoader(0, null, activity.punitionFragment);
    }
}
