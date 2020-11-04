package com.example.tayyabbutt.listviewadapter.database;

/**
 * Created by Tayyab Butt on 12/27/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class SqlDbHelper extends SQLiteOpenHelper {

    //Database tables
    public static final String MEDICINE_TABLE = "MEDICINE_TABLE";
    public static final String WEEKDAYS_TABLE = "WEEKDAYS_TABLE";
    public static final String DOZE_TAKEN_HISTORY_TABLE = "DOZE_TAKEN_HISTORY_TABLE";
    public static final String MEDICINE_REPEAT_DAY = "MEDICINE_REPEAT_DAY";

    //For MEDICINE_TABLE
    public static final String _MEDICINEID = "_medicineId";
    public static final String COLUMN2 = "name";
    public static final String COLUMN3 = "phone";
    public static final String COLUMN4 = "time";
    public static final String COLUMN5 = "date";
    public static final String IsRepeative = "radio_group";


    //For WEEKDAYS_TABLE
    public static final String _WEEKDAYSID = "_weekdaysid";
    public static final String DAYSNAME = "daysname";
    // public static final String __MEDICINEIDFK = "_medicineIdFK";


    //for DOZE_TAKEN_HISTORY_TABLE
    public static final String _DOZETAKENHISTORYID = "_dozetakenhistoryid";
    public static final String ISTAKEN = "istaken";
    public static final String TAKEN_DATE = "taken_date";
    public static final String __MEDICINEIDFK1 = "_medicineIdFK1";
    public static final String _WEEKDAYSIDFK = "_weekdaysidFK";


    //for Medicine_Repeat_Days
    public static final String _MEDICINE_REPEAT_DAYS_ID = "_medicine_repeat_days_id";
    public static final String DAYS = "days";
    public static final String __MEDICINEIDFK2 = "_medicineIdFK2";
    public static final String _WEEKDAYSIDFK1 = "_weekdaysidFK1";

    //Table creation
    private static final String CREATE_MEDICINE_TABLE = "create table "
            + MEDICINE_TABLE + " (" + _MEDICINEID
            + " integer primary key autoincrement, " + COLUMN2
            + " text not null, " + COLUMN3 + " text not null, " + COLUMN4 + " text not null, " + COLUMN5 + " text not null , "
            + IsRepeative + " text not null );";


  /*  private static final String CREATE_MEDICINE_TABLE = "create table "
            + MEDICINE_TABLE + " (" + _MEDICINEID
            + " integer primary key autoincrement, " + COLUMN2
            + " text not null, " + COLUMN3 + " text not null, " + COLUMN4 + " text not null, " + IsRepeative + " text not null );";
*/
    //vcgsdhvsdvbdnsxcvhhjdskczmxnvv
    private static final String CREATE_WEEKDAYS_TABLE = "create table "
            + WEEKDAYS_TABLE + " (" + _WEEKDAYSID +
            " integer primary key autoincrement, " + DAYSNAME + " text not null );";


  /*  private static final String CREATE_WEEKDAYS_TABLE = "create table "
            + WEEKDAYS_TABLE + " (" + _WEEKDAYSID +
            " integer primary key autoincrement, " + DAYSNAME + " text not null, " + __MEDICINEIDFK + " integer, " +
            " FOREIGN KEY (" + __MEDICINEIDFK + ") REFERENCES " + MEDICINE_TABLE + " (" + _MEDICINEID + "));";

*/

    private static final String CREATE_DOZE_TAKEN_HISTORY_TABLE = "create table "
            + DOZE_TAKEN_HISTORY_TABLE + " ("
            + _DOZETAKENHISTORYID + " integer primary key autoincrement, "
            + ISTAKEN + " text not null, "
            + TAKEN_DATE + " text not null, "
            + __MEDICINEIDFK1 + " integer, "
            + " FOREIGN KEY (" + __MEDICINEIDFK1 + ") REFERENCES " + MEDICINE_TABLE + " (" + _MEDICINEID + "));";


    /*private static final String CREATE_DOZE_TAKEN_HISTORY_TABLE = "create table "
            + DOZE_TAKEN_HISTORY_TABLE + " ("
            + _DOZETAKENHISTORYID + " integer primary key autoincrement, " + ISTAKEN + " text not null, " + __MEDICINEIDFK1 + " integer, "
            + _WEEKDAYSIDFK + " integer, "
            + " FOREIGN KEY (" + __MEDICINEIDFK1 + ") REFERENCES " + MEDICINE_TABLE + " (" + _MEDICINEID + "), "
            + " FOREIGN KEY (" + _WEEKDAYSIDFK + ") REFERENCES " + WEEKDAYS_TABLE + " (" + _WEEKDAYSID + "));";*/


    private static final String CREATE_MEDICINE_REPEAT_DAY = "create table "
            + MEDICINE_REPEAT_DAY + " ("
            + _MEDICINE_REPEAT_DAYS_ID + " integer primary key autoincrement, " + DAYS + " text not null, " + __MEDICINEIDFK2 + " integer, " +
            _WEEKDAYSIDFK1 + " integer, " +
            " FOREIGN KEY (" + __MEDICINEIDFK2 + ") REFERENCES " + MEDICINE_TABLE + " (" + _MEDICINEID + "), " +
            " FOREIGN KEY (" + _WEEKDAYSIDFK1 + ") REFERENCES " + WEEKDAYS_TABLE + " (" + _WEEKDAYSID + "));";


    public SqlDbHelper(Context context, String name, CursorFactory factory,
                       int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CREATE_MEDICINE_TABLE);
        db.execSQL(CREATE_WEEKDAYS_TABLE);
        db.execSQL(CREATE_DOZE_TAKEN_HISTORY_TABLE);
        db.execSQL(CREATE_MEDICINE_REPEAT_DAY);

    }

   /* @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }*/

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + MEDICINE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + WEEKDAYS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DOZE_TAKEN_HISTORY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MEDICINE_REPEAT_DAY);
        onCreate(db);
    }

}
