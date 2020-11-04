package com.example.tayyabbutt.listviewadapter.receiver;

/**
 * Created by Tayyab Butt on 12/28/2017.
 */

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.tayyabbutt.listviewadapter.R;
import com.example.tayyabbutt.listviewadapter.activity.CheckBoxActivityForNotification;
import com.example.tayyabbutt.listviewadapter.activity.MainFragmentActivityForRecyclerView;
import com.example.tayyabbutt.listviewadapter.database.SqlDbHelper;
import com.example.tayyabbutt.listviewadapter.service.AlarmSoundService;
import com.example.tayyabbutt.listviewadapter.utility.Utils;


public class AlarmReceiver extends BroadcastReceiver {

    private static final int MY_NOTIFICATION_ID = 1;
    NotificationManager notificationManager;
    Notification myNotification;
    private String mSelectedMedicineId;

    @Override
    public void onReceive(Context context, Intent intent) {


        Toast.makeText(context, "Alarm received!", Toast.LENGTH_LONG).show();
        context.startService(new Intent(context, AlarmSoundService.class));


        if (intent.hasExtra(Utils.ALARM_MEDICINE_ID)) {
            mSelectedMedicineId = intent.getStringExtra(Utils.ALARM_MEDICINE_ID);
        }

        // Intent myIntent = new Intent(context, MainFragmentActivityForRecyclerView.class);
        Intent myIntent = new Intent(context, CheckBoxActivityForNotification.class);

        myIntent.setAction(System.currentTimeMillis() + "_action");
        myIntent.putExtra(Utils.ALARM_MEDICINE_ID, mSelectedMedicineId);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                myIntent,
                0);

        myNotification = new NotificationCompat.Builder(context)
                .setContentTitle("Alarm Alarm!!!")
                .setContentText("Take Medicine")
                .setTicker("Notification!")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.medical_icon1)
                .build();

        notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(MY_NOTIFICATION_ID, myNotification);
    }

}

























/*

public class AlarmReceiver extends BroadcastReceiver {

    Context context1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "ALARM!! ALARM!!", Toast.LENGTH_SHORT).show();
        context1 = context;
        //Stop sound service to play sound for alarm
        context.startService(new Intent(context, AlarmSoundService.class));


        Intent service = new Intent(context, OnRebootService.class);
        context.startService(service);

        //This will send a notification message and show notification in notification tray
        ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmNotificationService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));

        Toast.makeText(context1, "Receive.", Toast.LENGTH_LONG).show();

        int icon= R.drawable.medical_icon1;
        String message="Alarm ring";
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);

        Intent notificationIntent = new Intent(context, MainFragmentActivityForRecyclerView.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent intent1 = PendingIntent.getActivity(context, 0, notificationIntent, 0);


     //   NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

      */
/*  NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context);

        // set intent so it does not start a new activity
        Notification notification1  = builder.setContentIntent(yourPendingIntent)
                .setSmallIcon(icon).setWhen( System.currentTimeMillis())
            .setContentTitle(title)
                .setContentText(message).build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(notifId, notification);
        *//*




        notification.setLatestEventInfo(context, title, message, intent1);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);


    }

}
*/
