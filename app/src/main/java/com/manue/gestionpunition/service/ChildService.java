package com.manue.gestionpunition.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.manue.gestionpunition.database.Database;
import com.manue.gestionpunition.model.Child;
import com.manue.gestionpunition.model.Constants;

/**
 * Created by WEDE7391 on 06/02/2015.
 */
public class ChildService extends Service {
    private Database db;

    public BroadcastReceiver notifyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (Constants.ACTION_NEW_CHILD.equals(action)) {
                Child child = new Child();
                child.name = intent.getExtras().getString(Constants.ACTION_NEW_CHILD);
                db.open(true);
                db.insertChild(child);
                db.close();
                Log.v(Constants.ACTION_NEW_CHILD, "insert child " + child.toString());
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        db = new Database(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(notifyReceiver);
        db = null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }
}
