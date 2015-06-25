package com.manue.gestionpunition.asynchronous;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;

import com.manue.gestionpunition.database.Database;
import com.manue.gestionpunition.model.Punition;

import java.util.TimeZone;

/**
 * Created by wede7391 on 04/02/2015.
 */
public class PunitionCreation extends AsyncTask<Punition, Void, Void> {
    private final Activity activity;
    private long id;
    private Database db;

    public PunitionCreation(Activity activity) {
        this.activity = activity;
        db = new Database(activity);
    }

    @Override
    protected Void doInBackground(Punition... punition) {

        db.open(true);
        if (punition != null) {
            //insertion punition dans calendrier
            ContentResolver cr = activity.getContentResolver();
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Events.DTSTART, punition[0].begin.getTime());
            values.put(CalendarContract.Events.DTEND, punition[0].end.getTime());
            values.put(CalendarContract.Events.TITLE, "Punition " + punition[0].name);
            values.put(CalendarContract.Events.DESCRIPTION, "Punition " + db.getChild(punition[0].childId).name);
            values.put(CalendarContract.Events.CALENDAR_ID, 1);
            values.put(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
            values.put(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_FREE);
            values.put(CalendarContract.Events.HAS_ALARM, true);
            values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
            //insertion rappel
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity.getBaseContext());
            final long rappel_frequency = Long.parseLong(sharedPref.getString("rappel_frequency", "240"));
            if (rappel_frequency != -1) {
                long eventId = Long.parseLong(uri.getLastPathSegment());
                punition[0].calendarId = eventId;
                ContentValues reminderValues = new ContentValues();

                reminderValues.put(CalendarContract.Reminders.MINUTES, rappel_frequency);
                reminderValues.put(CalendarContract.Reminders.EVENT_ID, eventId);
                reminderValues.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
                cr.insert(CalendarContract.Reminders.CONTENT_URI, reminderValues);
            }
            //insertion punition en base
            id = db.insertPunition(punition[0]);
        }
        db.close();

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        activity.onBackPressed();
    }
}
