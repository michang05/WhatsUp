package com.whatsup.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.whatsup.model.RequestElement;
import com.whatsup.model.WeatherElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MichaelAngelo on 10/18/2014.
 */
public class RequestDb extends DatabaseOpenHelper {


    private SQLiteDatabase db;

    public RequestDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void close() {
        db.close();
    }

    public void open() throws SQLiteException {
        try {
            db = getWritableDatabase();
        } catch (SQLiteException ex) {
            db = getReadableDatabase();
        }
    }

    public void insert(RequestElement requestElement) {
        open();


        ContentValues values = new ContentValues();
        values.put(QUERY, requestElement.getQuery());
        values.put(TYPE, requestElement.getType());


        db.insert(REQUEST_TABLE, null, values);

        close();
    }

    public List<String> getRequestedCities() {
        open();
        Cursor cursor = db.query(REQUEST_TABLE, null, null, null, null, null, null);
        ArrayList<String> listCities = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            listCities.add(cursor.getString(cursor.getColumnIndexOrThrow(QUERY)));
        }
        close();

        return listCities;
    }

    public void getQuery(RequestElement requestElement) {
        open();


        ContentValues values = new ContentValues();
        values.put(QUERY, requestElement.getQuery());
        values.put(TYPE, requestElement.getType());


        db.insert(REQUEST_TABLE, null, values);

        close();
    }


    public void delete() {
        open();
        db.delete(REQUEST_TABLE, "1", null);
        close();
    }


}
