package com.example.tayyabbutt.listviewadapter.activity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tayyabbutt.listviewadapter.R;
import com.example.tayyabbutt.listviewadapter.adapter.ReportAdapter;
import com.example.tayyabbutt.listviewadapter.database.SqlDbHelper;
import com.example.tayyabbutt.listviewadapter.database.SqlHandler;
import com.example.tayyabbutt.listviewadapter.model.HistoryModel;
import com.example.tayyabbutt.listviewadapter.model.MedicineItem;

import java.util.ArrayList;
import java.util.List;

import static com.example.tayyabbutt.listviewadapter.database.SqlDbHelper.DOZE_TAKEN_HISTORY_TABLE;
import static com.example.tayyabbutt.listviewadapter.database.SqlDbHelper.MEDICINE_TABLE;

/**
 * Created by Tayyab Butt on 2/6/2018.
 */

public class ReportGenerateActivity extends Activity {

    private ArrayList<MedicineItem> medicineItems = new ArrayList<>();
    private List<HistoryModel> mHistoryList = new ArrayList<>();
    SqlHandler sqlHandler;
    private RecyclerView mRecyclerView;
    private ReportAdapter reportAdapter;
    private int mSelectedItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_recyclerview);
        sqlHandler = new SqlHandler(ReportGenerateActivity.this);

        mRecyclerView = (RecyclerView) findViewById(R.id.report_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ReportGenerateActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        reportAdapter = new ReportAdapter(ReportGenerateActivity.this, mHistoryList);
        mRecyclerView.setAdapter(reportAdapter);

        initCustomSpinner();


    }


    public void getHistory(MedicineItem model) {
        mHistoryList.clear();
     /*   String selectQuery = "SELECT " + SqlDbHelper._MEDICINEID + " , " + SqlDbHelper.COLUMN2 + " , " + SqlDbHelper.COLUMN3 +
                " , " + SqlDbHelper.COLUMN4 + " , " + SqlDbHelper.COLUMN5 + " , " + SqlDbHelper.IsRepeative + " , " + SqlDbHelper.DAYSNAME + " , "
                + SqlDbHelper.ISTAKEN + " FROM " + SqlDbHelper.MEDICINE_TABLE + " MEDICINE_TABLE, "
                + SqlDbHelper.WEEKDAYS_TABLE + " WEEKDAYS_TABLE, "
                + SqlDbHelper.DOZE_TAKEN_HISTORY_TABLE + " DOZE_TAKEN_HISTORY_TABLE"; */ // + SqlDbHelper.MEDICINE_REPEAT_DAY + " MEDICINE_REPEAT_DAY, "

        // String selectQuery = "SELECT * FROM " + SqlDbHelper.MEDICINE_TABLE;

        /*String selectQuery = "SELECT  * FROM " + SqlDbHelper.MEDICINE_TABLE + " MEDICINE_TABLE, "
                + SqlDbHelper.WEEKDAYS_TABLE + " WEEKDAYS_TABLE, " + SqlDbHelper.DOZE_TAKEN_HISTORY_TABLE + " DOZE_TAKEN_HISTORY_TABLE ";*/

       /* String selectQuery = "SELECT  * FROM " + SqlDbHelper.MEDICINE_TABLE + ", "
                + SqlDbHelper.WEEKDAYS_TABLE + ", " + SqlDbHelper.DOZE_TAKEN_HISTORY_TABLE + ";";*/

      /* String selectQuery= "SELECT * FROM " + SqlDbHelper.MEDICINE_TABLE + ", " + SqlDbHelper.WEEKDAYS_TABLE + ", "
               + SqlDbHelper.DOZE_TAKEN_HISTORY_TABLE + " WHERE " + SqlDbHelper._MEDICINEID + " = " + SqlDbHelper.__MEDICINEIDFK1 +
               " AND " + SqlDbHelper._WEEKDAYSID + " = " + SqlDbHelper._WEEKDAYSIDFK + ";";*/


        //String selectQuery = "SELECT  * FROM " + DOZE_TAKEN_HISTORY_TABLE + ", " + SqlDbHelper.MEDICINE_TABLE + " WHERE " + SqlDbHelper._MEDICINEID + " = " + model.get_medicineId() + ";";
        String selectQuery = "SELECT  * FROM " + DOZE_TAKEN_HISTORY_TABLE + " WHERE " + SqlDbHelper.__MEDICINEIDFK1 + " = '" + model.get_medicineId() + "';";
        //String selectQuery = "SELECT  * FROM " + DOZE_TAKEN_HISTORY_TABLE + ";";

        /*String selectQuery = "SELECT  * FROM " + SqlDbHelper.DOZE_TAKEN_HISTORY_TABLE + ";";*/
       /* String selectQuery = "SELECT  * FROM " + SqlDbHelper.MEDICINE_TABLE + " MEDICINE_TABLE, "
                + SqlDbHelper.WEEKDAYS_TABLE + " WEEKDAYS_TABLE ";*/



     /* String selectQuery= "SELECT " + SqlDbHelper._MEDICINEID + " , " + SqlDbHelper.COLUMN2 + " , " + SqlDbHelper.COLUMN3 + " , "
              + SqlDbHelper.COLUMN4 + " , " + SqlDbHelper.COLUMN5 + " , " + SqlDbHelper.IsRepeative + " , "
              + SqlDbHelper.DAYSNAME + " , " + SqlDbHelper.ISTAKEN + " FROM " + SqlDbHelper.MEDICINE_TABLE +
              " JOIN " + SqlDbHelper.WEEKDAYS_TABLE +
              " JOIN " + SqlDbHelper.DOZE_TAKEN_HISTORY_TABLE ;
*/
/*Get cursor
//Create new querybuilder
        SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();

//Specify books table and add join to categories table (use full_id for joining categories table)
        _QB.setTables(SqlDbHelper.MEDICINE_TABLE +
                " LEFT OUTER JOIN " + SqlDbHelper.WEEKDAYS_TABLE + " ON " +
                SqlDbHelper._MEDICINEID + " = " + SqlDbHelper._WEEKDAYSID);

//Order by records by title
      //  _OrderBy = SqlDbHelper.COLUMN2 + " ASC";

//Open database connection
       // SQLiteDatabase _DB = SqlHandler.getReadableDatabase();

//
        //Cursor _Result = _QB.query(db, null, null, null, null, null, null);
*/


        Cursor c1 = sqlHandler.selectQuery(selectQuery);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    HistoryModel historyItem = new HistoryModel();

                    historyItem.setMedicineName(model.getName());

                    historyItem.setDate(c1.getString(c1
                            .getColumnIndex(SqlDbHelper.TAKEN_DATE)));

                    historyItem.setIsTaken(c1.getString(c1
                            .getColumnIndex(SqlDbHelper.ISTAKEN)));


                    mHistoryList.add(historyItem);

                } while (c1.moveToNext());
            }
        }
        c1.close();

        reportAdapter.setList(mHistoryList);

    }


    private void initCustomSpinner() {

        Spinner spinnerCustom = (Spinner) findViewById(R.id.spinnerCustom);
        medicineItems.clear();
        String query = "SELECT  * FROM " + MEDICINE_TABLE;
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    MedicineItem medicineItem = new MedicineItem();
                    medicineItem.set_medicineId(c1.getString(c1
                            .getColumnIndex(SqlDbHelper._MEDICINEID)));
                    medicineItem.setName(c1.getString(c1
                            .getColumnIndex(SqlDbHelper.COLUMN2)));
                    medicineItems.add(medicineItem);

                } while (c1.moveToNext());
            }
        }
        c1.close();


        //   medicines.add(medicines.get(0).getName().toString());

        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(ReportGenerateActivity.this, medicineItems);
        spinnerCustom.setAdapter(customSpinnerAdapter);

        if (medicineItems.size() > 0) {
            getHistory(medicineItems.get(mSelectedItem));
        }

        spinnerCustom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedItem = position;
                getHistory(medicineItems.get(mSelectedItem));

/*
                medicines.clear();
                String query = "SELECT " + SqlDbHelper.COLUMN2 + " FROM " + MEDICINE_TABLE;
                Cursor c1 = sqlHandler.selectQuery(query);
                if (c1 != null && c1.getCount() != 0) {
                    if (c1.moveToFirst()) {
                        do {


                            MedicineItem medicineItem = new MedicineItem();
                            medicineItem.setName(c1.getString(c1
                                    .getColumnIndex("name")));
                            getMedicine(medicineItem);


                        } while (c1.moveToNext());
                    }
                }
                c1.close();*/




              /*  switch (position) {
                    case 0:
                     *//*String query=   "Select * from"  DOZE_TAKEN_HISTORY_TABLE, MEDICINE_TABLE +  " where " _medicineId = _medicineIdFK1   ;*//*
                       // getMedicine();
*//*

                        String selectQuery = "SELECT  * FROM " + MEDICINE_TABLE + ", "
                                + DOZE_TAKEN_HISTORY_TABLE + " WHERE " + SqlDbHelper._MEDICINEID + " = " + SqlDbHelper.__MEDICINEIDFK1 + ";";

*//*

                }*/
                //Toast.makeText(parent.getContext(), "Android Custom Spinner Example Output..." + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

        private final Context context;
        private ArrayList<MedicineItem> medicineItems;

        public CustomSpinnerAdapter(Context context, ArrayList<MedicineItem> medicineItems) {
            this.medicineItems = medicineItems;
            this.context = context;
        }

        public List<MedicineItem> getList() {
            return medicineItems;
        }

        public void setList(ArrayList<MedicineItem> list) {
            medicineItems = list;
            notifyDataSetChanged();
        }

        public String getHistory() {
            return SqlDbHelper._DOZETAKENHISTORYID;
        }


        public int getCount() {
            return medicineItems.size();
        }

        public Object getItem(int position) {

            return medicineItems.get(position);
        }


        public long getItemId(int position) {
            /*return (long) position;*/
            return position;
        }


        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView txt = new TextView(ReportGenerateActivity.this);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(18);
            txt.setGravity(Gravity.CENTER_VERTICAL);
            txt.setText(medicineItems.get(position).getName());
            txt.setTextColor(Color.parseColor("#000000"));
            return txt;
        }

        public View getView(int position, View view, ViewGroup viewgroup) {
            TextView txt = new TextView(ReportGenerateActivity.this);
            txt.setGravity(Gravity.CENTER);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(16);
           // txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dropdownicon, 0);
            txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.smalldropicon, 0);
          //  txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dropdown1, 0);
            txt.setText(medicineItems.get(position).getName());
            txt.setTextColor(Color.parseColor("#000000"));
            return txt;
        }

    }

}
