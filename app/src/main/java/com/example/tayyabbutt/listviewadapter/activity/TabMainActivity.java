package com.example.tayyabbutt.listviewadapter.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tayyabbutt.listviewadapter.R;
import com.example.tayyabbutt.listviewadapter.adapter.MyPagerAdapter;
import com.example.tayyabbutt.listviewadapter.database.SqlHandler;
import com.example.tayyabbutt.listviewadapter.fragment.RecyclerFragment;
import com.example.tayyabbutt.listviewadapter.fragment.UpcomingAlarmListing;
import com.example.tayyabbutt.listviewadapter.interfaces.TextEntered;
import com.example.tayyabbutt.listviewadapter.service.OnRebootService;

public class TabMainActivity extends AppCompatActivity implements TextEntered {
    private final String LOG_TAG = TabMainActivity.class.getSimpleName();

    String value;
    RecyclerFragment mRecyclerFragment;
    UpcomingAlarmListing mUpcomingAlarmListing;
    // EnterDataFragment mDataFragment;
    FloatingActionButton fab;
    SqlHandler sqlHandler;
    // Titles of the individual pages (displayed in tabs)
    RelativeLayout fabContainer;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(3);


        sqlHandler = new SqlHandler(TabMainActivity.this);

        startService(new Intent(getBaseContext(), OnRebootService.class));

       /* if (savedInstanceState == null) {
            listFrag();
            upcomingList();
        }*/
       /* if (savedInstanceState==null){
            upcomingList();
        }*/







      /*  fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   toggleFabMenu();
                startActivity(new Intent(TabMainActivity.this, EnteredDataActivity.class));
            }

        });*/


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
              /*  if (position == 0) {
                    fab.setVisibility(View.VISIBLE);
                } else {
                    fab.setVisibility(View.GONE);

                }*/
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
        transaction.add(R.id.content1, mRecyclerFragment);
        transaction.commit();

    }


    private void upcomingList() {

        SharedPreferences pref = getPreferences(MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("secondLaunch", true);
        editor.commit();
        mUpcomingAlarmListing = new UpcomingAlarmListing();
        FragmentManager manager = getSupportFragmentManager();//create an instance of fragment manager
        FragmentTransaction transaction = manager.beginTransaction();//create an instance of Fragment-transaction
        transaction.add(R.id.content1, mUpcomingAlarmListing);
        transaction.commit();

    }

    @Override
    public void setValue(String editextvalue) {


        // TODO Auto-generated method stub
        value = editextvalue;
        Log.i("..............", "" + value);
        mRecyclerFragment.onDataModified();
        //  mUpcomingAlarmListing.onDataModified();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        Toast.makeText(TabMainActivity.this, "Report Selected", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ReportGenerateActivity.class);
        this.startActivity(intent);

        return true;
    }


/*    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void toggleFabMenu() {
        //  if (!fabMenuOpen) {
        //     fab.setImageResource(R.drawable.ic_close);
        int centerX = fabContainer.getWidth() / 2;
        int centerY = fabContainer.getHeight() / 2;
        int startRadius = 0;
        int endRadius = (int) Math.hypot(fabContainer.getWidth(), fabContainer.getHeight()) / 2;

        fabContainer.setVisibility(View.VISIBLE);
        ViewAnimationUtils
                .createCircularReveal(
                        fabContainer,
                        centerX,
                        centerY,
                        startRadius,
                        endRadius
                )
                .setDuration(1000)
                .start();

        //     fab.setImageResource(R.drawable.ic_add);
            *//*int centerX = fabContainer.getWidth() / 2;
            int centerY = fabContainer.getHeight() / 2;
            int startRadius = (int) Math.hypot(fabContainer.getWidth(), fabContainer.getHeight()) / 2;
            int endRadius = 0;*//*

        Animator animator = ViewAnimationUtils
                .createCircularReveal(
                        fabContainer,
                        centerX,
                        centerY,
                        startRadius,
                        endRadius
                );
        animator.setDuration(1000);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                fabContainer.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.start();
    }*/
    //  fabMenuOpen = !fabMenuOpen;
}






