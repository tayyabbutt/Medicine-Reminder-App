package com.example.tayyabbutt.listviewadapter.activity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.tayyabbutt.listviewadapter.R;
import com.example.tayyabbutt.listviewadapter.database.SqlDbHelper;
import com.example.tayyabbutt.listviewadapter.database.SqlHandler;
import com.example.tayyabbutt.listviewadapter.model.MedicineItem;
import com.example.tayyabbutt.listviewadapter.receiver.AlarmReceiver;
import com.example.tayyabbutt.listviewadapter.service.AlarmNotificationService;
import com.example.tayyabbutt.listviewadapter.service.AlarmSoundService;
import com.example.tayyabbutt.listviewadapter.utility.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.tayyabbutt.listviewadapter.database.SqlDbHelper.MEDICINE_TABLE;

public class EnteredDataActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener {
    SqlHandler sqlHandler;
    EditText etName, etPhone;
    Button btnsubmit;
    private Context context;
    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    int nyear, nmonthOfYear, ndayOfMonth, nhourOfDay, nminute;
    private PendingIntent operation, operation1, operation2, operation3, operation4, operation5, operation6;
    private CheckBox chk_monday, chk_tuesday, chk_wednesday, chk_thursday, chk_friday, chk_sat, chk_sunday;
    private Intent i;
    AlarmManager alarmManager;
    LinearLayout linearLayout_repeat, linearLayout_once;
    ArrayList<String> selectedStrings = new ArrayList<String>();
    private String mInsertedRowId;


    public EnteredDataActivity() {
    }


    @SuppressLint("ValidActivity")

    public EnteredDataActivity(Context context) {
        this.context = context;
    }

    private PendingIntent pendingIntent;

    private static final int ALARM_REQUEST_CODE = 133;
    RadioButton chk_once1, chk_repeat1;
    RadioGroup radio_group_chk_selection1;

    final static int RQS_1 = 1;
    private Intent myIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entered_data);
        myIntent = new Intent(EnteredDataActivity.this, AlarmReceiver.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        chk_monday = (CheckBox) findViewById(R.id.chk_monday);
        chk_tuesday = (CheckBox) findViewById(R.id.chk_tuesday);
        chk_wednesday = (CheckBox) findViewById(R.id.chk_wednesday);
        chk_thursday = (CheckBox) findViewById(R.id.chk_thursday);
        chk_friday = (CheckBox) findViewById(R.id.chk_friday);
        chk_sat = (CheckBox) findViewById(R.id.chk_sat);
        chk_sunday = (CheckBox) findViewById(R.id.chk_sunday);
        chk_monday.setOnCheckedChangeListener(this);
        chk_tuesday.setOnCheckedChangeListener(this);
        chk_wednesday.setOnCheckedChangeListener(this);
        chk_thursday.setOnCheckedChangeListener(this);
        chk_friday.setOnCheckedChangeListener(this);
        chk_sat.setOnCheckedChangeListener(this);
        chk_sunday.setOnCheckedChangeListener(this);
        linearLayout_repeat = (LinearLayout) findViewById(R.id.weekdayl);
        linearLayout_once = (LinearLayout) findViewById(R.id.chk_selection);
        radio_group_chk_selection1 = (RadioGroup) findViewById(R.id.radio_group_chk_selection);


        chk_once1 = (RadioButton) findViewById(R.id.chk_once);
        chk_repeat1 = (RadioButton) findViewById(R.id.chk_repeat);
        radio_group_chk_selection1.setOnCheckedChangeListener(this);


        etName = (EditText) findViewById(R.id.et_name);
        etName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        etPhone = (EditText) findViewById(R.id.et_phone);
        btnsubmit = (Button) findViewById(R.id.btn_submit);
        sqlHandler = new SqlHandler(EnteredDataActivity.this);

        btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnTimePicker = (Button) findViewById(R.id.btn_time);
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        txtDate = (EditText) findViewById(R.id.in_date);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtDate.setText(sdf.format(c.getTime()));

        txtTime = (EditText) findViewById(R.id.in_time);
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:MM");
        //String currentTime = sdf1.format(new Date());
        Date currentTime = Calendar.getInstance().getTime();

        //  txtTime.setText(currentTime.getHours() + ":" + currentTime.getMinutes());

        txtTime.setText(String.format("%02d:%02d", currentTime.getHours(), currentTime.getMinutes()));
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(EnteredDataActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                timePickerDialog = new TimePickerDialog(EnteredDataActivity.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        nhourOfDay = hourOfDay;
                        nminute = minute;
                        txtTime.setText(String.format("%02d:%02d", hourOfDay, minute));
                        //txtTime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.show();
            }


        });

        Intent alarmIntent = new Intent(EnteredDataActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(EnteredDataActivity.this, ALARM_REQUEST_CODE, alarmIntent, 0);
/*

        final EditText editText = (EditText) findViewById(R.id.input_interval_time);
*/


        btnsubmit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String name = etName.getText().toString();
                String quantiy = etPhone.getText().toString();
                String datePicked = ndayOfMonth + "-" + (nmonthOfYear + 1) + "-" + nyear;


                // String timePicked = nhourOfDay + ":" + nminute;
                String timePicked = String.format("%02d:%02d", nhourOfDay, nminute);
                int timeInInteger = Integer.parseInt(timePicked.replaceAll(":", ""));


                String radio_group_value = ((RadioButton) findViewById(radio_group_chk_selection1
                        .getCheckedRadioButtonId())).getText().toString();
               /* String query = "INSERT INTO MEDICINE_TABLE(name,phone,time,date,radio_group) values ('"
                        + name + "','" + quantiy + "','" + timePicked + "','" + datePicked + "','" + radio_group_value + "')";
              */

                String query = "INSERT INTO " + SqlDbHelper.MEDICINE_TABLE + "(" + SqlDbHelper.COLUMN2 + "," + SqlDbHelper.COLUMN3 + ", "
                        + SqlDbHelper.COLUMN4 + ", " + SqlDbHelper.COLUMN5 + ", " + SqlDbHelper.IsRepeative + ") values ('"
                        + name + "','" + quantiy + "','" + timeInInteger + "','" + datePicked + "','" + radio_group_value + "')";

                sqlHandler.executeQuery(query);

                findMedicineIdOfLastRow();


                //replacing with backStack....
                FragmentManager manager = getSupportFragmentManager();
                manager.popBackStackImmediate();

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
                    Toast.makeText(EnteredDataActivity.this, "Invalid Date/Time...please check Date/Time enter correctly", Toast.LENGTH_LONG).show();
                } else {
                    setAlarm(cal);
                }
                showlist();


                StringBuffer result = new StringBuffer();
                result.append("Monday ").append(chk_monday.isChecked());
                result.append(" Tuesday ").append(chk_tuesday.isChecked());
                result.append(" Wednesday ").append(chk_wednesday.isChecked());
                result.append(" Thurdsday ").append(chk_thursday.isChecked());
                result.append(" Friday ").append(chk_friday.isChecked());
                result.append(" Saturday ").append(chk_sat.isChecked());
                result.append(" Sunday ").append(chk_sunday.isChecked());


                /*String query2 = "INSERT INTO WEEKDAYS_TABLE(daysname) values ('"
                        + getSelectedString() + "')";*/
                String query2 = "INSERT INTO " + SqlDbHelper.WEEKDAYS_TABLE + "(" + SqlDbHelper.DAYSNAME + ") values ('"
                        + getSelectedString() + "')";

                sqlHandler.executeQuery(query2);

                //Query for insert MEDICINE_REPEAT_DAY

                String query1 = "INSERT INTO " + SqlDbHelper.MEDICINE_REPEAT_DAY + "(" + SqlDbHelper.DAYS + ", " + SqlDbHelper.__MEDICINEIDFK2 + ", "
                        + SqlDbHelper._WEEKDAYSIDFK1 + ") values ('" + result + "','" + " SELECT " + SqlDbHelper._MEDICINEID +
                        " FROM " + MEDICINE_TABLE + " , " + " SELECT " + SqlDbHelper._WEEKDAYSID +
                        " FROM " + SqlDbHelper.WEEKDAYS_TABLE + "')";

                /*String query1 = "INSERT INTO MEDICINE_REPEAT_DAY(days,_medicineIdFK2,_weekdaysidFK) values ('"
                        + result + "','" + " SELECT " + SqlDbHelper._MEDICINEID +
                        " FROM " + SqlDbHelper.MEDICINE_TABLE + " , " + " SELECT " + SqlDbHelper._WEEKDAYSID +
                        " FROM " + SqlDbHelper.WEEKDAYS_TABLE + "')";*/
                sqlHandler.executeQuery(query1);

              /*  String query1 = "INSERT INTO "+ SqlDbHelper.MEDICINE_REPEAT_DAY +"("+ SqlDbHelper.DAYS + " , " + SqlDbHelper._MEDICINEID + " , " + SqlDbHelper._WEEKDAYSID + ") " + " VALUES(result, (SELECT "+ SqlDbHelper._MEDICINEID + " FROM "+ SqlDbHelper.MEDICINE_TABLE + " WHERE "+ SqlDbHelper.COLUMN2 + " = ? ) ," + "(SELECT " + SqlDbHelper._WEEKDAYSID + " FROM " + SqlDbHelper.WEEKDAYS_TABLE + " WHERE " + SqlDbHelper.DAYSNAME + " = ? ))";  //,new String[]{ name, }
                sqlHandler.executeQuery(query1);*/
                finish();

            }
        });

        findViewById(R.id.stop_alarm_button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAlarmManager();
            }
        });


    }


    private void findMedicineIdOfLastRow() {
        //  Get Added Record's Id
        String selectQuery = "SELECT " + SqlDbHelper._MEDICINEID + " FROM " + MEDICINE_TABLE + " ORDER BY " + SqlDbHelper._MEDICINEID + " DESC LIMIT 1 ;";
        sqlHandler.executeQuery(selectQuery);

        Cursor c1 = sqlHandler.selectQuery(selectQuery);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    mInsertedRowId = c1.getString(c1
                            .getColumnIndex(SqlDbHelper._MEDICINEID));
                    break;

                } while (c1.moveToNext());
            }
        }
        c1.close();
    }

    private void setAlarm(Calendar targetCal) {
        Toast.makeText(EnteredDataActivity.this, "*** Alarm is set@" + targetCal.getTime() + "***", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(EnteredDataActivity.this, AlarmReceiver.class);
        intent.setAction(System.currentTimeMillis() + "_action");
        intent.putExtra(Utils.ALARM_MEDICINE_ID, mInsertedRowId);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final int _idForDiffrentAlarms = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, _idForDiffrentAlarms, intent, PendingIntent.FLAG_UPDATE_CURRENT);   //RQS_1
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);

    }


    public void stopAlarmManager() {

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);//cancel the alarm manager of the pending intent

        //Stop the Media Player Service to stop sound

        stopService(new Intent(EnteredDataActivity.this, AlarmSoundService.class));

        //remove the notification from notification tray
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(AlarmNotificationService.NOTIFICATION_ID);

        Toast.makeText(EnteredDataActivity.this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
        FragmentManager manager = getSupportFragmentManager();
        manager.popBackStackImmediate();
    }

    private void showlist() {

        ArrayList<MedicineItem> contactList = new ArrayList<MedicineItem>();
        contactList.clear();
        String query = "SELECT * FROM " + SqlDbHelper.MEDICINE_TABLE;
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    MedicineItem medicineItem = new MedicineItem();

                    medicineItem.setSlno(c1.getString(c1
                            .getColumnIndex("_medicineId")));
                    medicineItem.setName(c1.getString(c1
                            .getColumnIndex("name")));
                    medicineItem.setPhone(c1.getString(c1
                            .getColumnIndex("phone")));
                    medicineItem.setTime(c1.getString(c1
                            .getColumnIndex("time")));
                    medicineItem.setDate(c1.getString(c1
                            .getColumnIndex("date")));
                    contactList.add(medicineItem);


                } while (c1.moveToNext());
            }
        }
        c1.close();

    }

    public Editable getText() {
        etName.getText();
        etPhone.getText();

        return etName.getText();
    }

    public void forday(int week, PendingIntent pendingIntent) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, week);
        cal.set(Calendar.HOUR_OF_DAY, nhourOfDay);
        cal.set(Calendar.MINUTE, nminute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        i = new Intent(EnteredDataActivity.this, AlarmReceiver.class);
        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        pendingIntent = PendingIntent.getBroadcast(this, RQS_1, i, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


        if (buttonView == chk_monday) {

            if (isChecked) {

                Bundle bundle = new Bundle();

                bundle.putInt("Selected_day", 2);

                i = new Intent(EnteredDataActivity.this, AlarmReceiver.class);

                i.putExtras(bundle);

                operation = PendingIntent.getBroadcast(EnteredDataActivity.this, 0, i, 0);
                selectedStrings.add(chk_monday.getText().toString());
                forday(2, operation);

            } else {
                selectedStrings.remove(chk_monday.getText().toString());
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                alarmManager.cancel(operation);

            }

        } else if (buttonView == chk_tuesday) {

            if (isChecked) {

                Bundle bundle = new Bundle();

                bundle.putInt("Selected_day", 3);
                i = new Intent(EnteredDataActivity.this, AlarmReceiver.class);

                i.putExtras(bundle);
                selectedStrings.add(chk_tuesday.getText().toString());
                operation1 = PendingIntent.getBroadcast(EnteredDataActivity.this, 1, i, 0);

                forday(3, operation1);

            } else {
                selectedStrings.remove(chk_tuesday.getText().toString());
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.cancel(operation1);

            }

        } else if (buttonView == chk_wednesday) {

            if (isChecked) {

                Bundle bundle = new Bundle();

                bundle.putInt("Selected_day", 4);

                i = new Intent(EnteredDataActivity.this, AlarmReceiver.class);
                i.putExtras(bundle);
                selectedStrings.add(chk_wednesday.getText().toString());
                operation2 = PendingIntent.getBroadcast(EnteredDataActivity.this, 2, i, 0);

                forday(4, operation2);

            } else {
                selectedStrings.remove(chk_wednesday.getText().toString());
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                alarmManager.cancel(operation2);

            }

        } else if (buttonView == chk_thursday) {

            if (isChecked) {

                Bundle bundle = new Bundle();

                bundle.putInt("Selected_day", 5);

                i = new Intent(EnteredDataActivity.this, AlarmReceiver.class);
                i.putExtras(bundle);
                selectedStrings.add(chk_thursday.getText().toString());
                operation3 = PendingIntent.getBroadcast(EnteredDataActivity.this, 3, i, 0);

                forday(5, operation3);

            } else {
                selectedStrings.remove(chk_thursday.getText().toString());
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                alarmManager.cancel(operation3);

            }

        } else if (buttonView == chk_friday) {

            if (isChecked) {

                Bundle bundle = new Bundle();

                bundle.putInt("Selected_day", 6);

                //   i.putExtras(bundle);
                i = new Intent(EnteredDataActivity.this, AlarmReceiver.class);

                i.putExtras(bundle);
                selectedStrings.add(chk_friday.getText().toString());
                operation4 = PendingIntent.getBroadcast(EnteredDataActivity.this, 4, i, 0);

                forday(6, operation4);

            } else {
                selectedStrings.remove(chk_friday.getText().toString());
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                alarmManager.cancel(operation4);

            }

        } else if (buttonView == chk_sat) {

            if (isChecked) {

                Bundle bundle = new Bundle();

                bundle.putInt("Selected_day", 7);
                selectedStrings.add(chk_sat.getText().toString());
                i = new Intent(EnteredDataActivity.this, AlarmReceiver.class);
                i.putExtras(bundle);


                operation5 = PendingIntent.getBroadcast(EnteredDataActivity.this, 5, i, 0);

                forday(7, operation5);

            } else {


                selectedStrings.remove(chk_sat.getText().toString());
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                alarmManager.cancel(operation5);

            }

        } else if (buttonView == chk_sunday) {

            if (isChecked) {

                Bundle bundle = new Bundle();

                bundle.putInt("Selected_day", 1);

                i = new Intent(EnteredDataActivity.this, AlarmReceiver.class);
                i.putExtras(bundle);

                selectedStrings.add(chk_sunday.getText().toString());
                operation6 = PendingIntent.getBroadcast(EnteredDataActivity.this, 6, i, 0);

                forday(1, operation6);

            } else {
                selectedStrings.remove(chk_sunday.getText().toString());
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                alarmManager.cancel(operation6);

            }

        } else {

            buttonView.setChecked(false);

            Toast.makeText(EnteredDataActivity.this, "Please first select time/ check day to repeat/ alarm set for 1 day", Toast.LENGTH_LONG).show();

        }


    }


    ArrayList<String> getSelectedString() {
        return selectedStrings;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (i == R.id.chk_once) {
            linearLayout_repeat.setVisibility(View.GONE);
        } else {
            linearLayout_repeat.setVisibility(View.VISIBLE);
        }

    }
}
