package com.example.tayyabbutt.listviewadapter.database;

/**
 * Created by Tayyab Butt on 12/27/2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SqlHandler {

    public static final String DATABASE_NAME = "MY_DATABASE";
    public static final int DATABASE_VERSION = 1;

    SQLiteDatabase sqlDatabase;
    SqlDbHelper dbHelper;

    public SqlHandler(Context context) {

        dbHelper = new SqlDbHelper(context, DATABASE_NAME, null,
                DATABASE_VERSION);
        sqlDatabase = dbHelper.getWritableDatabase();
    }

    public boolean executeQuery(String query) {
        try {
            if (sqlDatabase.isOpen()) {
                sqlDatabase = dbHelper.getWritableDatabase();
                sqlDatabase.execSQL(query);
                sqlDatabase.close();
                return true;
            }


        } catch (Exception e) {

            System.out.println("DATABASE ERROR " + e);
        }
        return false;
    }

    public Cursor selectQuery(String query) {
        Cursor c1 = null;
        try {

            if (sqlDatabase.isOpen()) {
                sqlDatabase.close();

            }
            sqlDatabase = dbHelper.getWritableDatabase();
            c1 = sqlDatabase.rawQuery(query, null);

        } catch (Exception e) {

            System.out.println("DATABASE ERROR " + e);

        }
        return c1;

    }

}
