package com.example.tayyabbutt.listviewadapter.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
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
import com.example.tayyabbutt.listviewadapter.interfaces.TextEntered;
import com.example.tayyabbutt.listviewadapter.model.MedicineItem;
import com.example.tayyabbutt.listviewadapter.receiver.AlarmReceiver;
import com.example.tayyabbutt.listviewadapter.service.AlarmNotificationService;
import com.example.tayyabbutt.listviewadapter.service.AlarmSoundService;
import com.example.tayyabbutt.listviewadapter.utility.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;
import static com.example.tayyabbutt.listviewadapter.database.SqlDbHelper.MEDICINE_TABLE;

/**
 * Created by Tayyab Butt on 12/27/2017.
 */

public class EnterDataFragment extends Fragment implements OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener {

    TextEntered mCallback;
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


    public EnterDataFragment() {
    }

    @SuppressLint("ValidFragment")
    public EnterDataFragment(Context context) {
        this.context = context;
    }

    private PendingIntent pendingIntent;

    private RadioButton secondsRadioButton, minutesRadioButton, hoursRadioButton;
    private static final int ALARM_REQUEST_CODE = 133;
    RadioButton chk_once1, chk_repeat1;
    RadioGroup radio_group_chk_selection1;

    final static int RQS_1 = 1;
    private Intent myIntent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        myIntent = new Intent(getContext(), AlarmReceiver.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        View view = inflater.inflate(R.layout.fragment1, container, false);
        //Find id of all radio buttons
        secondsRadioButton = (RadioButton) view.findViewById(R.id.seconds_radio_button);
        minutesRadioButton = (RadioButton) view.findViewById(R.id.minutes_radio_button);
        hoursRadioButton = (RadioButton) view.findViewById(R.id.hours_radio_button);

        chk_monday = (CheckBox) view.findViewById(R.id.chk_monday);
        chk_tuesday = (CheckBox) view.findViewById(R.id.chk_tuesday);
        chk_wednesday = (CheckBox) view.findViewById(R.id.chk_wednesday);
        chk_thursday = (CheckBox) view.findViewById(R.id.chk_thursday);
        chk_friday = (CheckBox) view.findViewById(R.id.chk_friday);
        chk_sat = (CheckBox) view.findViewById(R.id.chk_sat);
        chk_sunday = (CheckBox) view.findViewById(R.id.chk_sunday);
        chk_monday.setOnCheckedChangeListener(this);
        chk_tuesday.setOnCheckedChangeListener(this);
        chk_wednesday.setOnCheckedChangeListener(this);
        chk_thursday.setOnCheckedChangeListener(this);
        chk_friday.setOnCheckedChangeListener(this);
        chk_sat.setOnCheckedChangeListener(this);
        chk_sunday.setOnCheckedChangeListener(this);
        linearLayout_repeat = (LinearLayout) view.findViewById(R.id.weekdayl);
        linearLayout_once = (LinearLayout) view.findViewById(R.id.chk_selection);
        radio_group_chk_selection1 = (RadioGroup) view.findViewById(R.id.radio_group_chk_selection);


        chk_once1 = (RadioButton) view.findViewById(R.id.chk_once);
        chk_repeat1 = (RadioButton) view.findViewById(R.id.chk_repeat);
        radio_group_chk_selection1.setOnCheckedChangeListener(this);


        etName = (EditText) view.findViewById(R.id.et_name);
        etName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        etPhone = (EditText) view.findViewById(R.id.et_phone);
        btnsubmit = (Button) view.findViewById(R.id.btn_submit);
        sqlHandler = new SqlHandler(getContext());

        btnDatePicker = (Button) view.findViewById(R.id.btn_date);
        btnTimePicker = (Button) view.findViewById(R.id.btn_time);
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        txtDate = (EditText) view.findViewById(R.id.in_date);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtDate.setText(sdf.format(c.getTime()));

        txtTime = (EditText) view.findViewById(R.id.in_time);
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
        String currentTime = sdf1.format(new Date());
        txtTime.setText(currentTime);

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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
                timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                nhourOfDay = hourOfDay;
                                nminute = minute;

                                txtTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }


        });

        Intent alarmIntent = new Intent(getContext(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getContext(), ALARM_REQUEST_CODE, alarmIntent, 0);

        final EditText editText = (EditText) view.findViewById(R.id.input_interval_time);

        btnsubmit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String name = etName.getText().toString();
                String quantiy = etPhone.getText().toString();
                String datePicked = ndayOfMonth + "-" + (nmonthOfYear + 1) + "-" + nyear;
                String timePicked = nhourOfDay + ":" + nminute;
                String radio_group_value = ((RadioButton) getActivity().findViewById(radio_group_chk_selection1
                        .getCheckedRadioButtonId())).getText().toString();

                String query = "INSERT INTO " + SqlDbHelper.MEDICINE_TABLE + "(" + SqlDbHelper.COLUMN2 + "," + SqlDbHelper.COLUMN3 + ", " + SqlDbHelper.COLUMN4 + ", "
                        + SqlDbHelper.COLUMN5 + ", "
                        + SqlDbHelper.IsRepeative + ") values ('"
                        + name + "','" + quantiy + "','" + timePicked + "','" + datePicked + "','" + radio_group_value + "')";


                sqlHandler.executeQuery(query);

                findMedicineIdOfLastRow();


                //replacing with backStack....
                FragmentManager manager = getActivity().getSupportFragmentManager();
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
                    Toast.makeText(context, "Invalid Date/Time...please check Date/Time enter correctly", Toast.LENGTH_LONG).show();
                } else {
                    setAlarm(cal);
                }
                showlist();

                etName.setText("");
                etPhone.setText("");
                String getInterval = editText.getText().toString().trim();//get interval from edittext
                //check interval should not be empty and 0
                if (!getInterval.equals("") && !getInterval.equals("0"))

                    //finally trigger alarm manager
                    triggerAlarmManager(getTimeInterval(getInterval));
                else {
                /*    Toast.makeText(context, "Please Enter Alarm time to save alarm and delete previous saved...", Toast.LENGTH_LONG).show();
*/
                }
                mCallback.setValue("");


                StringBuffer result = new StringBuffer();
                result.append("Monday ").append(chk_monday.isChecked());
                result.append(" Tuesday ").append(chk_tuesday.isChecked());
                result.append(" Wednesday ").append(chk_wednesday.isChecked());
                result.append(" Thurdsday ").append(chk_thursday.isChecked());
                result.append(" Friday ").append(chk_friday.isChecked());
                result.append(" Saturday ").append(chk_sat.isChecked());
                result.append(" Sunday ").append(chk_sunday.isChecked());


                String text;
                /*List<CheckBox> items = new ArrayList<CheckBox>();*/
              /*  for (CheckBox checkBox : items){
                    if(checkBox.isChecked()) {
                        text = checkBox.getText().toString();
                    }
                    *//*else if(chk_tuesday.isChecked()){
                        text= chk_tuesday.getText().toString();
                    }
                    else if(chk_wednesday.isChecked()){
                        text= chk_wednesday.getText().toString();
                    }
                    else if(chk_thursday.isChecked()){
                        text= chk_thursday.getText().toString();
                    }
                    else if(chk_friday.isChecked()){
                        text= chk_friday.getText().toString();
                    }
                    else if(chk_sat.isChecked()){
                        text= chk_sat.getText().toString();
                    }
                    else if(chk_sunday.isChecked()){
                        text= chk_sunday.getText().toString();
                    }*//*
                }*/

                //Query for insert WEEKDAYS_TABLE
                /*String query2 = "INSERT INTO WEEKDAYS_TABLE(daysname,_medicineIdFK) values ('"
                        + getSelectedString() + "','" + " SELECT " + SqlDbHelper._MEDICINEID +
                        " FROM " + SqlDbHelper.MEDICINE_TABLE + "')";*/
                String query2 = "INSERT INTO " + SqlDbHelper.WEEKDAYS_TABLE + "(" + SqlDbHelper.DAYSNAME + ") values ('"
                        + getSelectedString() + "')";


                sqlHandler.executeQuery(query2);

                //Query for insert MEDICINE_REPEAT_DAY

                /*String query1 = "INSERT INTO MEDICINE_REPEAT_DAY(days,_medicineIdFK2,_weekdaysidFK) values ('"
                        + result + "','" + " SELECT " + SqlDbHelper._MEDICINEID +
                        " FROM " + SqlDbHelper.MEDICINE_TABLE + " , " + " SELECT " + SqlDbHelper._WEEKDAYSID +
                        " FROM " + SqlDbHelper.WEEKDAYS_TABLE + "')";*/


                String query1 = "INSERT INTO " + SqlDbHelper.MEDICINE_REPEAT_DAY + "(" + SqlDbHelper.DAYS + ", " + SqlDbHelper.__MEDICINEIDFK2 + ", "
                        + SqlDbHelper._WEEKDAYSIDFK1 + ") values ('" + result + "','" + " SELECT " + SqlDbHelper._MEDICINEID +
                        " FROM " + MEDICINE_TABLE + " , " + " SELECT " + SqlDbHelper._WEEKDAYSID +
                        " FROM " + SqlDbHelper.WEEKDAYS_TABLE + "')";
                sqlHandler.executeQuery(query1);
              /*  String query1 = "INSERT INTO "+ SqlDbHelper.MEDICINE_REPEAT_DAY +"("+ SqlDbHelper.DAYS + " , " + SqlDbHelper._MEDICINEID + " , " + SqlDbHelper._WEEKDAYSID + ") " + " VALUES(result, (SELECT "+ SqlDbHelper._MEDICINEID + " FROM "+ SqlDbHelper.MEDICINE_TABLE + " WHERE "+ SqlDbHelper.COLUMN2 + " = ? ) ," + "(SELECT " + SqlDbHelper._WEEKDAYSID + " FROM " + SqlDbHelper.WEEKDAYS_TABLE + " WHERE " + SqlDbHelper.DAYSNAME + " = ? ))";  //,new String[]{ name, }
                sqlHandler.executeQuery(query1);*/


            }
        });

        view.findViewById(R.id.stop_alarm_button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAlarmManager();
            }
        });

        return view;

    }

    private void findMedicineIdOfLastRow() {
        //  Get Added Record's Id
        String selectQuery = "SELECT " + SqlDbHelper._MEDICINEID + " FROM " + SqlDbHelper.MEDICINE_TABLE + " ORDER BY " + SqlDbHelper._MEDICINEID + " DESC LIMIT 1 ;";
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
        Toast.makeText(context, "*** Alarm is set@" + targetCal.getTime() + "***", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction(System.currentTimeMillis() + "_action");
        intent.putExtra(Utils.ALARM_MEDICINE_ID, mInsertedRowId);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final int _idForDiffrentAlarms = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, _idForDiffrentAlarms, intent, PendingIntent.FLAG_UPDATE_CURRENT);   //RQS_1
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);

    }

    public void triggerAlarmManager(int alarmTriggerTime) {
        // get a Calendar object with current time
        Calendar cal = Calendar.getInstance();
        // add alarmTriggerTime seconds to the calendar object
        cal.add(Calendar.SECOND, alarmTriggerTime);

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);//get instance of alarm manager
        manager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);//set alarm manager with entered timer by converting into milliseconds
/*
        Toast.makeText(context, "Alarm Set for " + alarmTriggerTime + " seconds.", Toast.LENGTH_SHORT).show();*/

    }

    private int getTimeInterval(String getInterval) {
        int interval = Integer.parseInt(getInterval);//convert string interval into integer

        //Return interval on basis of radio button selection
        if (secondsRadioButton.isChecked())
            return interval;
        if (minutesRadioButton.isChecked())
            return interval * 60;//convert minute into seconds
        if (hoursRadioButton.isChecked())
            return interval * 60 * 60;//convert hours into seconds

        return 0;
    }

    public void stopAlarmManager() {

        alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);//cancel the alarm manager of the pending intent

        //Stop the Media Player Service to stop sound

        getActivity().stopService(new Intent(getContext(), AlarmSoundService.class));

        //remove the notification from notification tray
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(AlarmNotificationService.NOTIFICATION_ID);

        Toast.makeText(getContext(), "Alarm Canceled", Toast.LENGTH_SHORT).show();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.popBackStackImmediate();
    }


  /*  @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
    *//*   btnsubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String s;
                s = etName.getText().toString();
                // s=etPhone.getText().toString();
                mCallback.setValue(s);
            }

        });*//*
    }*/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (TextEntered) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement textEntered");
        }
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

        i = new Intent(getContext(), AlarmReceiver.class);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        pendingIntent = PendingIntent.getBroadcast(context, RQS_1, i, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // TODO Auto-generated method stub

        if (buttonView == chk_monday) {

            if (isChecked) {

                Bundle bundle = new Bundle();

                bundle.putInt("Selected_day", 2);

                i = new Intent(getContext(), AlarmReceiver.class);

                i.putExtras(bundle);

                operation = PendingIntent.getBroadcast(getActivity(), 0, i, 0);
                selectedStrings.add(chk_monday.getText().toString());
                forday(2, operation);

            } else {
                selectedStrings.remove(chk_monday.getText().toString());
                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);

                alarmManager.cancel(operation);

            }

        } else if (buttonView == chk_tuesday) {

            if (isChecked) {

                Bundle bundle = new Bundle();

                bundle.putInt("Selected_day", 3);
                i = new Intent(getContext(), AlarmReceiver.class);

                i.putExtras(bundle);
                selectedStrings.add(chk_tuesday.getText().toString());
                operation1 = PendingIntent.getBroadcast(getActivity(), 1, i, 0);

                forday(3, operation1);

            } else {
                selectedStrings.remove(chk_tuesday.getText().toString());
                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);
                alarmManager.cancel(operation1);

            }

        } else if (buttonView == chk_wednesday) {

            if (isChecked) {

                Bundle bundle = new Bundle();

                bundle.putInt("Selected_day", 4);

                i = new Intent(getContext(), AlarmReceiver.class);
                i.putExtras(bundle);
                selectedStrings.add(chk_wednesday.getText().toString());
                operation2 = PendingIntent.getBroadcast(getActivity(), 2, i, 0);

                forday(4, operation2);

            } else {
                selectedStrings.remove(chk_wednesday.getText().toString());
                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);

                alarmManager.cancel(operation2);

            }

        } else if (buttonView == chk_thursday) {

            if (isChecked) {

                Bundle bundle = new Bundle();

                bundle.putInt("Selected_day", 5);

                i = new Intent(getContext(), AlarmReceiver.class);
                i.putExtras(bundle);
                selectedStrings.add(chk_thursday.getText().toString());
                operation3 = PendingIntent.getBroadcast(getActivity(), 3, i, 0);

                forday(5, operation3);

            } else {
                selectedStrings.remove(chk_thursday.getText().toString());
                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);

                alarmManager.cancel(operation3);

            }

        } else if (buttonView == chk_friday) {

            if (isChecked) {

                Bundle bundle = new Bundle();

                bundle.putInt("Selected_day", 6);

                //   i.putExtras(bundle);
                i = new Intent(getContext(), AlarmReceiver.class);

                i.putExtras(bundle);
                selectedStrings.add(chk_friday.getText().toString());
                operation4 = PendingIntent.getBroadcast(getActivity(), 4, i, 0);

                forday(6, operation4);

            } else {
                selectedStrings.remove(chk_friday.getText().toString());
                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);

                alarmManager.cancel(operation4);

            }

        } else if (buttonView == chk_sat) {

            if (isChecked) {

                Bundle bundle = new Bundle();

                bundle.putInt("Selected_day", 7);
                selectedStrings.add(chk_sat.getText().toString());
                i = new Intent(getContext(), AlarmReceiver.class);
                i.putExtras(bundle);


                operation5 = PendingIntent.getBroadcast(getActivity(), 5, i, 0);

                forday(7, operation5);

            } else {


                selectedStrings.remove(chk_sat.getText().toString());
                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);

                alarmManager.cancel(operation5);

            }

        } else if (buttonView == chk_sunday) {

            if (isChecked) {

                Bundle bundle = new Bundle();

                bundle.putInt("Selected_day", 1);

                i = new Intent(getContext(), AlarmReceiver.class);
                i.putExtras(bundle);

                selectedStrings.add(chk_sunday.getText().toString());
                operation6 = PendingIntent.getBroadcast(getActivity(), 6, i, 0);

                forday(1, operation6);

            } else {
                selectedStrings.remove(chk_sunday.getText().toString());
                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);

                alarmManager.cancel(operation6);

            }

        } else {

            buttonView.setChecked(false);

            Toast.makeText(getContext(), "Please first select time/ check day to repeat/ alarm set for 1 day", Toast.LENGTH_LONG).show();

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


