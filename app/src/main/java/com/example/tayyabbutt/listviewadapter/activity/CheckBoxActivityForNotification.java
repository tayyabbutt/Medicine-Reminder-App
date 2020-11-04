package com.example.tayyabbutt.listviewadapter.activity;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.tayyabbutt.listviewadapter.R;
import com.example.tayyabbutt.listviewadapter.database.SqlDbHelper;
import com.example.tayyabbutt.listviewadapter.database.SqlHandler;
import com.example.tayyabbutt.listviewadapter.receiver.AlarmReceiver;
import com.example.tayyabbutt.listviewadapter.service.AlarmNotificationService;
import com.example.tayyabbutt.listviewadapter.service.AlarmSoundService;
import com.example.tayyabbutt.listviewadapter.utility.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.tayyabbutt.listviewadapter.database.SqlDbHelper.DOZE_TAKEN_HISTORY_TABLE;
import static com.example.tayyabbutt.listviewadapter.database.SqlDbHelper.ISTAKEN;
import static com.example.tayyabbutt.listviewadapter.database.SqlDbHelper.TAKEN_DATE;
import static com.example.tayyabbutt.listviewadapter.database.SqlDbHelper._WEEKDAYSIDFK;
import static com.example.tayyabbutt.listviewadapter.database.SqlDbHelper.__MEDICINEIDFK1;

public class CheckBoxActivityForNotification extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    SqlHandler sqlHandler;
    private CheckBox chk_yes1, chk_no1;
    AlarmManager alarmManager;
    private Intent i;
    private static final int ALARM_REQUEST_CODE = 133;
    String selectedStrings ;
    private String mSelectedMedicineId;


    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_checkvalue);

        if (getIntent().hasExtra(Utils.ALARM_MEDICINE_ID)) {
            mSelectedMedicineId = getIntent().getStringExtra(Utils.ALARM_MEDICINE_ID);
        }


        Intent alarmIntent = new Intent(CheckBoxActivityForNotification.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(CheckBoxActivityForNotification.this, ALARM_REQUEST_CODE, alarmIntent, 0);
        sqlHandler = new SqlHandler(this);
        chk_yes1 = (CheckBox) findViewById(R.id.chk_yes);
        chk_no1 = (CheckBox) findViewById(R.id.chk_no);
        chk_yes1.setOnCheckedChangeListener(this);
        chk_no1.setOnCheckedChangeListener(this);

    }

    public void stopAlarmManager() {

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);//cancel the alarm manager of the pending intent

        //Stop the Media Player Service to stop sound

        this.stopService(new Intent(CheckBoxActivityForNotification.this, AlarmSoundService.class));

        //remove the notification from notification tray
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(AlarmNotificationService.NOTIFICATION_ID);

        Toast.makeText(CheckBoxActivityForNotification.this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
        FragmentManager manager = getSupportFragmentManager();
        manager.popBackStackImmediate();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (buttonView == chk_yes1) {

            if (isChecked) {

                selectedStrings=chk_yes1.getText().toString();
               /* String query3 = "INSERT INTO " + DOZE_TAKEN_HISTORY_TABLE + "(" + ISTAKEN + "," + __MEDICINEIDFK1 + "," + _WEEKDAYSIDFK + ") values ('"
                        + getSelectedString() + "','" + " SELECT " + SqlDbHelper._MEDICINEID +
                        " FROM " + SqlDbHelper.MEDICINE_TABLE + "' , '" + " SELECT " + SqlDbHelper._WEEKDAYSID +
                        " FROM " + SqlDbHelper.WEEKDAYS_TABLE + "')";*/
                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c);
              /*  String query3 = "INSERT INTO " + DOZE_TAKEN_HISTORY_TABLE + "(" + ISTAKEN + "," + TAKEN_DATE + ", " + __MEDICINEIDFK1 + ") values ('"
                        + getSelectedString() + "','" + formattedDate + "','" + " SELECT " + SqlDbHelper._MEDICINEID +
                        " FROM " + SqlDbHelper.MEDICINE_TABLE + "')";*/

                String query3 = "INSERT INTO " + DOZE_TAKEN_HISTORY_TABLE + "(" + ISTAKEN + "," + TAKEN_DATE + ", " + __MEDICINEIDFK1 + ") values ('"
                        + getSelectedString() + "','" + formattedDate + "','" + mSelectedMedicineId + "')";

                if (sqlHandler.executeQuery(query3)) {
                    Toast.makeText(CheckBoxActivityForNotification.this, "Query Executed Success", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(CheckBoxActivityForNotification.this, "Query Not Executed Success", Toast.LENGTH_LONG).show();
                }
                stopAlarmManager();
                finish();



            }
        }/*else {

                //  selectedStrings.remove(chk_monday.getText().toString());
             *//*   AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);

                alarmManager.cancel(operation);*//*

            }*/ else if (buttonView == chk_no1) {

            if (isChecked) {

                selectedStrings=chk_no1.getText().toString();

                /*String query3 = "INSERT INTO " + DOZE_TAKEN_HISTORY_TABLE + "(" + ISTAKEN + "," + __MEDICINEIDFK1 + "," + _WEEKDAYSIDFK + ") values ('"
                        + getSelectedString() + "','" + " SELECT " + SqlDbHelper._MEDICINEID +
                        " FROM " + SqlDbHelper.MEDICINE_TABLE + " , " + " SELECT " + SqlDbHelper._WEEKDAYSID +
                        " FROM " + SqlDbHelper.WEEKDAYS_TABLE + "')";*/
                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c);
                /*String query3 = "INSERT INTO " + DOZE_TAKEN_HISTORY_TABLE + "(" + ISTAKEN + "," + TAKEN_DATE + ", " + __MEDICINEIDFK1 + ") values ('"
                        + getSelectedString() + "','" + formattedDate + "','" + " SELECT " + SqlDbHelper._MEDICINEID +
                        " FROM " + SqlDbHelper.MEDICINE_TABLE + "')";*/


                String query3 = "INSERT INTO " + DOZE_TAKEN_HISTORY_TABLE + "(" + ISTAKEN + "," + TAKEN_DATE + ", " + __MEDICINEIDFK1 + ") values ('"
                        + getSelectedString() + "','" + formattedDate + "','" + mSelectedMedicineId + "')";


                if (sqlHandler.executeQuery(query3)) {
                    Toast.makeText(CheckBoxActivityForNotification.this, "Query Executed Success", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(CheckBoxActivityForNotification.this, "Query Not Executed Success", Toast.LENGTH_LONG).show();
                }
                Calendar calendar = Calendar.getInstance();
                Long preTime = calendar.getTimeInMillis();

                calendar.add(Calendar.MINUTE, 1);
                Long postTime = calendar.getTimeInMillis();
                Long delay = postTime - preTime;

                AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                manager.set(AlarmManager.RTC_WAKEUP, postTime, null);
                stopAlarmManager();

                finish();

            } /*else {
                selectedStrings.remove(chk_no1.getText().toString());
                stopAlarmManager();
            }*/


        }
    }

   private String getSelectedString() {
        return selectedStrings;
    }

}

