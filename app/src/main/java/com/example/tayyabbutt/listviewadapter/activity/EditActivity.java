package com.example.tayyabbutt.listviewadapter.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.tayyabbutt.listviewadapter.R;
import com.example.tayyabbutt.listviewadapter.database.SqlDbHelper;
import com.example.tayyabbutt.listviewadapter.database.SqlHandler;
import com.example.tayyabbutt.listviewadapter.receiver.AlarmReceiver;

import java.util.Calendar;

/**
 * Created by Tayyab Butt on 11/24/2017.
 */

public class EditActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private EditText titleText, alarmText;
    private Button updateBtn, cancelBtn;
    private EditText descText;
    SqlHandler sqlHandler;
    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;

    private int mYear, mMonth, mDay, mHour, mMinute;
    private PendingIntent pendingIntent;
    final static int RQS_1 = 1;
    private static final int ALARM_REQUEST_CODE = 133;
    String id;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    int nyear, nmonthOfYear, ndayOfMonth, nhourOfDay, nminute;
    private PendingIntent operation, operation1, operation2, operation3, operation4, operation5, operation6;
    private CheckBox chk_monday, chk_tuesday, chk_wednesday, chk_thursday, chk_friday, chk_sat, chk_sunday;
    private Intent i;
    AlarmManager alarmManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Modify Record");
        setContentView(R.layout.activity_editable_enterdata);
        sqlHandler = new SqlHandler(this);

        chk_monday = (CheckBox) findViewById(R.id.edit_chk_monday);
        chk_tuesday = (CheckBox) findViewById(R.id.edit_chk_tuesday);
        chk_wednesday = (CheckBox) findViewById(R.id.edit_chk_wednesday);
        chk_thursday = (CheckBox) findViewById(R.id.edit_chk_thursday);
        chk_friday = (CheckBox) findViewById(R.id.edit_chk_friday);
        chk_sat = (CheckBox) findViewById(R.id.edit_chk_sat);
        chk_sunday = (CheckBox) findViewById(R.id.edit_chk_sunday);

        chk_monday.setOnCheckedChangeListener(this);
        chk_tuesday.setOnCheckedChangeListener(this);
        chk_wednesday.setOnCheckedChangeListener(this);
        chk_thursday.setOnCheckedChangeListener(this);
        chk_friday.setOnCheckedChangeListener(this);
        chk_sat.setOnCheckedChangeListener(this);
        chk_sunday.setOnCheckedChangeListener(this);


        titleText = (EditText) findViewById(R.id.edit_medicine);
        descText = (EditText) findViewById(R.id.edit_quantity);

        btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnTimePicker = (Button) findViewById(R.id.btn_time);
        txtDate = (EditText) findViewById(R.id.in_date);
        txtTime = (EditText) findViewById(R.id.in_time);

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                datePickerDialog = new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        nyear = year;
                        nmonthOfYear = monthOfYear;
                        ndayOfMonth = dayOfMonth;

                        txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                timePickerDialog = new TimePickerDialog(EditActivity.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        nhourOfDay = hourOfDay;
                        nminute = minute;

                        //txtTime.setText(hourOfDay + ":" + minute);
                        txtTime.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                }, mHour, mMinute, false);
                timePickerDialog.show();
            }


        });


        Intent alarmIntent = new Intent(EditActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(EditActivity.this, ALARM_REQUEST_CODE, alarmIntent, 0);

        alarmText = (EditText) findViewById(R.id.edit_alarm);

        updateBtn = (Button) findViewById(R.id.btn_update);
        cancelBtn = (Button) findViewById(R.id.btn_cancel);


        Intent intent = getIntent();
        id = getIntent().getStringExtra(SqlDbHelper._MEDICINEID);
        String name = intent.getStringExtra(SqlDbHelper.COLUMN2);
        String desc = intent.getStringExtra(SqlDbHelper.COLUMN3);
       // String time = intent.getStringExtra(SqlDbHelper.COLUMN4);
        String time = intent.getStringExtra(SqlDbHelper.COLUMN4);
        String date = intent.getStringExtra(SqlDbHelper.COLUMN5);

        String time_1 = addColon(time);


        titleText.setText(name);
        descText.setText(desc);
        txtDate.setText(date);
        txtTime.setText(time_1);
        updateBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                updateRecord();
                break;

            case R.id.btn_cancel:
                returnHome();
                break;
        }
    }

    private void updateRecord() {
        String title = titleText.getText().toString();
        String desc = descText.getText().toString();
        String datePicked = ndayOfMonth + "-" + (nmonthOfYear + 1) + "-" + nyear;
        //String timePicked = nhourOfDay + ":" + nminute;
        String timePicked = String.format("%02d:%02d", nhourOfDay, nminute);
        int timeInInteger = Integer.parseInt(timePicked.replaceAll(":", ""));


        String query = "UPDATE " + SqlDbHelper.MEDICINE_TABLE + " SET " + SqlDbHelper.COLUMN2 + " = '" + title + "' , " + SqlDbHelper.COLUMN3 + " = '" + desc +
                "' , " + SqlDbHelper.COLUMN4 + " = '" + timeInInteger + "' , " + SqlDbHelper.COLUMN5 + " = '" + datePicked + "'   WHERE " + SqlDbHelper._MEDICINEID + " = " + id + ";";

        Calendar current = Calendar.getInstance();

        Calendar cal = Calendar.getInstance();
        cal.set(nyear,
                nmonthOfYear,
                ndayOfMonth,
                nhourOfDay,
                nminute,
                00);

        if (cal.compareTo(current) <= 0) {
            //The set Date/Time already passed
            Toast.makeText(this, "Invalid Date/Time...please check Date/Time enter correctly", Toast.LENGTH_LONG).show();
        } else {
            setAlarm(cal);
        }

        if (sqlHandler.executeQuery(query) == true) {
            Toast.makeText(EditActivity.this, "Success", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(EditActivity.this, "Failure", Toast.LENGTH_SHORT).show();
        }
    }

    private String addColon(String str) {
        String temp = "";
        if (str.length() == 3) {
            temp = str.substring(0, 1) + ":" + str.substring(1);

        }
        if (str.length() == 4) {
            temp = str.substring(0, 2) + ":" + str.substring(2);
        }
        return temp;
    }

    public void returnHome() {
        finish();
    }

    private void setAlarm(Calendar targetCal) {
        Toast.makeText(this, "*** Alarm is set@" + targetCal.getTime() + "***", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, AlarmReceiver.class);
        final int _idForDiffrentAlarms = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, _idForDiffrentAlarms, intent, 0);
        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);

    }

    public void forday(int week, PendingIntent pendingIntent) {

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.DAY_OF_WEEK, week);
        cal.set(Calendar.HOUR_OF_DAY, nhourOfDay);
        cal.set(Calendar.MINUTE, nminute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        i = new Intent(this, AlarmReceiver.class);
        final int _idForDiffrentAlarms = (int) System.currentTimeMillis();
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        pendingIntent = PendingIntent.getBroadcast(this, _idForDiffrentAlarms, i, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == chk_monday) {

            if (isChecked) {

                Bundle bundle = new Bundle();

                bundle.putInt("Selected_day", 2);

                i = new Intent(this, AlarmReceiver.class);

                i.putExtras(bundle);


                operation = PendingIntent.getBroadcast(this, 0, i, 0);

                forday(2, operation);

            } else {

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                alarmManager.cancel(operation);

            }

        } else if (buttonView == chk_tuesday) {

            if (isChecked) {

                Bundle bundle = new Bundle();

                bundle.putInt("Selected_day", 3);
                i = new Intent(this, AlarmReceiver.class);

                i.putExtras(bundle);

                operation1 = PendingIntent.getBroadcast(this, 1, i, 0);


                forday(3, operation1);

            } else {

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                alarmManager.cancel(operation1);

            }

        } else if (buttonView == chk_wednesday) {

            if (isChecked) {

                Bundle bundle = new Bundle();

                bundle.putInt("Selected_day", 4);

                i = new Intent(this, AlarmReceiver.class);
                i.putExtras(bundle);

                operation2 = PendingIntent.getBroadcast(this, 2, i, 0);

                forday(4, operation2);

            } else {

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                alarmManager.cancel(operation2);

            }

        } else if (buttonView == chk_thursday) {

            if (isChecked) {

                Bundle bundle = new Bundle();

                bundle.putInt("Selected_day", 5);

                i = new Intent(this, AlarmReceiver.class);
                i.putExtras(bundle);

                operation3 = PendingIntent.getBroadcast(this, 3, i, 0);

                forday(5, operation3);

            } else {

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                alarmManager.cancel(operation3);

            }

        } else if (buttonView == chk_friday) {

            if (isChecked) {

                Bundle bundle = new Bundle();

                bundle.putInt("Selected_day", 6);

                //   i.putExtras(bundle);
                i = new Intent(this, AlarmReceiver.class);

                i.putExtras(bundle);

                operation4 = PendingIntent.getBroadcast(this, 4, i, 0);

                forday(6, operation4);

            } else {

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                alarmManager.cancel(operation4);

            }

        } else if (buttonView == chk_sat) {

            if (isChecked) {

                Bundle bundle = new Bundle();

                bundle.putInt("Selected_day", 7);

                i = new Intent(this, AlarmReceiver.class);
                i.putExtras(bundle);


                operation5 = PendingIntent.getBroadcast(this, 5, i, 0);

                forday(7, operation5);

            } else {

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                alarmManager.cancel(operation5);

            }

        } else if (buttonView == chk_sunday) {

            if (isChecked) {

                Bundle bundle = new Bundle();

                bundle.putInt("Selected_day", 1);

                i = new Intent(this, AlarmReceiver.class);
                i.putExtras(bundle);


                operation6 = PendingIntent.getBroadcast(this, 6, i, 0);

                forday(1, operation6);

            } else {

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                alarmManager.cancel(operation6);

            }

        } else {

            buttonView.setChecked(false);

            Toast.makeText(this, "Please first select time/ check day to repeat/ alarm set for 1 day", Toast.LENGTH_LONG).show();

        }

    }
}

