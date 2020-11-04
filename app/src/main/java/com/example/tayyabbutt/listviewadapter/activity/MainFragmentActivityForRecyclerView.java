package com.example.tayyabbutt.listviewadapter.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.tayyabbutt.listviewadapter.R;
import com.example.tayyabbutt.listviewadapter.database.SqlHandler;
import com.example.tayyabbutt.listviewadapter.fragment.EnterDataFragment;
import com.example.tayyabbutt.listviewadapter.fragment.RecyclerFragment;
import com.example.tayyabbutt.listviewadapter.interfaces.TextEntered;
import com.example.tayyabbutt.listviewadapter.service.OnRebootService;

public class MainFragmentActivityForRecyclerView extends AppCompatActivity implements TextEntered {

    String value;
    RecyclerFragment mRecyclerFragment;
    EnterDataFragment mDataFragment;
    FloatingActionButton fab;
    SqlHandler sqlHandler;
    //  Button reportButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // setContentView(R.layout.navigation_activity_main);


        sqlHandler = new SqlHandler(MainFragmentActivityForRecyclerView.this);

        startService(new Intent(getBaseContext(), OnRebootService.class));

      /*  reportButton= (Button) findViewById(R.id.report_btn);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainFragmentActivityForRecyclerView.this,ReportGenerateActivity.class);
                startActivity(i);

            }
        });
*/

        fab = (FloatingActionButton) findViewById(R.id.fab);
        if (savedInstanceState == null) {
            listFrag();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataFragment = new EnterDataFragment(MainFragmentActivityForRecyclerView.this);
                FragmentManager manager = getSupportFragmentManager();//create an instance of fragment manager
                FragmentTransaction transaction = manager.beginTransaction();//create an instance of Fragment-transaction
                transaction.add(R.id.content, mDataFragment);
                transaction.addToBackStack("Frag_form_tag");
                transaction.commit();
            }

        });

    }

    private void listFrag() {

        SharedPreferences pref = getPreferences(MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("firstLaunch", true);
        editor.commit();
        mRecyclerFragment = new RecyclerFragment();
        FragmentManager manager = getSupportFragmentManager();//create an instance of fragment manager
        FragmentTransaction transaction = manager.beginTransaction();//create an instance of Fragment-transaction
        transaction.add(R.id.content, mRecyclerFragment);
        transaction.commit();

    }

    @Override
    public void setValue(String editextvalue) {
        // TODO Auto-generated method stub
        value = editextvalue;
        Log.i("..............", "" + value);
        mRecyclerFragment.onDataModified();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        Toast.makeText(MainFragmentActivityForRecyclerView.this, "Report Selected", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ReportGenerateActivity.class);
        this.startActivity(intent);




    /*    switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_refresh:
                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            // action with ID action_settings was selected
            case R.id.action_settings:
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                break;
        }*/

        return true;
    }


    /*    medicineItems.clear();
    String selectQuery = "SELECT  * FROM " + SqlDbHelper.MEDICINE_TABLE + " MEDICINE_TABLE, "
            + SqlDbHelper.WEEKDAYS_TABLE + " WEEKDAYS_TABLE, " + SqlDbHelper.MEDICINE_REPEAT_DAY + " MEDICINE_REPEAT_DAY, "
            + SqlDbHelper.DOZE_TAKEN_HISTORY_TABLE + " DOZE_TAKEN_HISTORY_TABLE";

    Cursor c1 = sqlHandler.selectQuery(selectQuery);
        if (c1 != null && c1.getCount() != 0) {
        if (c1.moveToFirst()) {
            do {
                MedicineItem contactListItems = new MedicineItem();

                contactListItems.setSlno(c1.getString(c1
                        .getColumnIndex("_medicineId")));
                contactListItems.setName(c1.getString(c1
                        .getColumnIndex("name")));
                contactListItems.setPhone(c1.getString(c1
                        .getColumnIndex("phone")));
                contactListItems.setTime(c1.getString(c1
                        .getColumnIndex("time")));
                contactListItems.setDate(c1.getString(c1
                        .getColumnIndex("date")));
                contactListItems.setRadio_group(c1.getString(c1.getColumnIndex("radio_group")));
                contactListItems.setDaysname(c1.getString(c1.getColumnIndex("daysname")));

                contactListItems.setIstaken(c1.getString(c1.getColumnIndex("istaken")));
                medicineItems.add(contactListItems);

            } while (c1.moveToNext());
        }
    }
        c1.close();*/
}
