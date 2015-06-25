package com.manue.gestionpunition.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.manue.gestionpunition.model.Child;
import com.manue.gestionpunition.model.Punition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Database {
    private static final String SQLITE_DB_NAME = "gestionPunition.db";
    private static final String SQLITE_CHILDREN_TABLE_NAME = "children";
    private static final String SQLITE_CHILDREN_ID_COLUMN_NAME = "child_id";
    private static final String SQLITE_CHILDREN_NAME_COLUMN_NAME = "child_name";
    private static final String SQLITE_CREATE_CHILDREN_TABLE_REQUEST = "CREATE TABLE "
            + SQLITE_CHILDREN_TABLE_NAME
            + " ("
            + SQLITE_CHILDREN_ID_COLUMN_NAME
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SQLITE_CHILDREN_NAME_COLUMN_NAME
            + " VARCHAR(250) NOT NULL)";
    private static final int SQLITE_DB_VERSION = 4;
    private static final String SQLITE_PUNITION_TABLE_NAME = "punition";
    private static final String SQLITE_PUNITION_ID_COLUMN_NAME = "punition_id";
    private static final String SQLITE_PUNITION_ID_COLUMN_CHILD = "child_id";
    private static final String SQLITE_PUNITION_COLUMN_PUNITION_NAME = "punition_name";
    private static final String SQLITE_PUNITION_BEGIN_COLUMN_NAME = "begin_date";
    private static final String SQLITE_PUNITION_END_COLUMN_NAME = "end_date";
    private static final String SQLITE_PUNITION_CALENDAR_ID_COLUMN_NAME = "calendar_id";
    private static final String SQLITE_CREATE_PUNITION_TABLE_REQUEST = "CREATE TABLE "
            + SQLITE_PUNITION_TABLE_NAME
            + " ("
            + SQLITE_PUNITION_ID_COLUMN_NAME
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SQLITE_PUNITION_ID_COLUMN_CHILD
            + " INTEGER, "
            + SQLITE_PUNITION_COLUMN_PUNITION_NAME
            + " VARCHAR(250) NOT NULL, "
            + SQLITE_PUNITION_BEGIN_COLUMN_NAME
            + " DATETIME, "
            + SQLITE_PUNITION_END_COLUMN_NAME
            + " DATETIME, "
            + SQLITE_PUNITION_CALENDAR_ID_COLUMN_NAME
            + " INTEGER)";
    private final DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public Database(final Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void close() {
        dbHelper.close();
    }

    public void deleteChildren(final long childId) {
        Log.v("gestionPunition", getClass().getSimpleName() + " delete child : id="
                + childId);
        db.delete(Database.SQLITE_CHILDREN_TABLE_NAME,
                Database.SQLITE_CHILDREN_ID_COLUMN_NAME + "=" + childId, null);
    }

    public List<Child> getChildren() {
        Cursor cursor = null;
        try {
            cursor = db.query(Database.SQLITE_CHILDREN_TABLE_NAME, null, null,
                    null, null, null, null);
        } catch (final Exception e) {
            Log.e("gestionPunition", "Error", e);
        }
        if (cursor == null) {
            return null;
        }
        try {
            final List<Child> children = new ArrayList<Child>();
            Child child;
            while (true) {
                cursor.moveToNext();
                if (cursor.isAfterLast()) {
                    break;
                }
                child = new Child();
                child.id = cursor.getLong(cursor.getColumnIndexOrThrow(Database.SQLITE_CHILDREN_ID_COLUMN_NAME));
                child.name = cursor.getString(cursor.getColumnIndexOrThrow(Database.SQLITE_CHILDREN_NAME_COLUMN_NAME));
                children.add(child);
            }
            return children;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public Child getChild(long id) {
        Cursor cursor = db.query(Database.SQLITE_CHILDREN_TABLE_NAME, null, Database.SQLITE_CHILDREN_ID_COLUMN_NAME + "=" + id,
                null, null, null, null);

        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        cursor.moveToFirst();
        Child child = new Child();
        child.id = cursor.getLong(cursor.getColumnIndexOrThrow(Database.SQLITE_CHILDREN_ID_COLUMN_NAME));
        child.name = cursor.getString(cursor.getColumnIndexOrThrow(Database.SQLITE_CHILDREN_NAME_COLUMN_NAME));
        cursor.close();
        return child;

    }

    public long insertChild(final Child child) {
        final ContentValues initialValues = new ContentValues();
        initialValues.put(Database.SQLITE_CHILDREN_NAME_COLUMN_NAME,
                child.name);
        final long id = db.insert(Database.SQLITE_CHILDREN_TABLE_NAME, null,
                initialValues);
        Log.v("gestionPunition", getClass().getSimpleName() + " insertChild : id="
                + id);
        return id;
    }

    public long insertPunition(final Punition punition) {
        final ContentValues initialValues = new ContentValues();
        initialValues.put(Database.SQLITE_PUNITION_COLUMN_PUNITION_NAME,
                punition.name);
        initialValues.put(Database.SQLITE_PUNITION_ID_COLUMN_CHILD,
                punition.childId);
        initialValues.put(Database.SQLITE_PUNITION_BEGIN_COLUMN_NAME,
                punition.begin.getTime());
        initialValues.put(Database.SQLITE_PUNITION_END_COLUMN_NAME,
                punition.end.getTime());
        initialValues.put(Database.SQLITE_PUNITION_CALENDAR_ID_COLUMN_NAME,
                punition.calendarId);
        final long id = db.insert(Database.SQLITE_PUNITION_TABLE_NAME, null,
                initialValues);
        Log.v("gestionPunition", getClass().getSimpleName() + " insertPunition : id="
                + id);
        return id;
    }

    public void deletePunition(final long punitionId) {
        Log.v("gestionPunition", getClass().getSimpleName() + " delete punition : id="
                + punitionId);
        db.delete(Database.SQLITE_PUNITION_TABLE_NAME,
                Database.SQLITE_PUNITION_ID_COLUMN_NAME + "=" + punitionId, null);
    }

    public List<Punition> getPunitions(long childId) {
        Cursor cursor = null;
        try {
            cursor = db.query(Database.SQLITE_PUNITION_TABLE_NAME, null, Database.SQLITE_PUNITION_ID_COLUMN_CHILD + "=" + childId,
                    null, null, null, null);
        } catch (final Exception e) {
            Log.e("gestionPunition", "Error", e);
        }
        if (cursor == null) {
            return null;
        }
        try {
            final List<Punition> punitions = new ArrayList<Punition>();
            Punition punition;
            while (true) {
                cursor.moveToNext();
                if (cursor.isAfterLast()) {
                    break;
                }
                punition = new Punition();
                punition.id = cursor.getLong(cursor.getColumnIndexOrThrow(Database.SQLITE_PUNITION_ID_COLUMN_NAME));
                punition.name = cursor.getString(cursor.getColumnIndexOrThrow(Database.SQLITE_PUNITION_COLUMN_PUNITION_NAME));
                punition.childId = cursor.getLong(cursor.getColumnIndex(Database.SQLITE_PUNITION_ID_COLUMN_CHILD));
                punition.begin = new Date(cursor.getLong(cursor.getColumnIndex(Database.SQLITE_PUNITION_BEGIN_COLUMN_NAME)));
                punition.end = new Date(cursor.getLong(cursor.getColumnIndex(Database.SQLITE_PUNITION_END_COLUMN_NAME)));
                punition.calendarId = cursor.getLong(cursor.getColumnIndexOrThrow(Database.SQLITE_PUNITION_CALENDAR_ID_COLUMN_NAME));
                punitions.add(punition);
            }
            return punitions;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public Database open(boolean writable) throws SQLException {
        db = writable ? dbHelper.getWritableDatabase() : dbHelper
                .getReadableDatabase();
        return this;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(final Context context) {
            super(context, Database.SQLITE_DB_NAME, null,
                    Database.SQLITE_DB_VERSION);
        }

        @Override
        public void onCreate(final SQLiteDatabase db) {
            db.execSQL(Database.SQLITE_CREATE_CHILDREN_TABLE_REQUEST);
            db.execSQL(Database.SQLITE_CREATE_PUNITION_TABLE_REQUEST);
        }

        @Override
        public void onUpgrade(final SQLiteDatabase db, final int oldVersion,
                              final int newVersion) {
            Log.w("BigBrother", "Upgrading database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS "
                    + Database.SQLITE_CHILDREN_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS "
                    + Database.SQLITE_PUNITION_TABLE_NAME);
            onCreate(db);
        }
    }
}
