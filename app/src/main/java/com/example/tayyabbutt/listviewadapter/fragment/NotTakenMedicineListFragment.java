package com.example.tayyabbutt.listviewadapter.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tayyabbutt.listviewadapter.R;
import com.example.tayyabbutt.listviewadapter.adapter.NotTakenListAdapter;
import com.example.tayyabbutt.listviewadapter.adapter.UpcomingListAdapter;
import com.example.tayyabbutt.listviewadapter.database.SqlDbHelper;
import com.example.tayyabbutt.listviewadapter.database.SqlHandler;
import com.example.tayyabbutt.listviewadapter.interfaces.OnMedicineItemClickListener;
import com.example.tayyabbutt.listviewadapter.model.MedicineItem;
import com.example.tayyabbutt.listviewadapter.utility.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.tayyabbutt.listviewadapter.database.SqlDbHelper.MEDICINE_TABLE;

/**
 * Created by Tayyab Butt on 2/12/2018.
 */

public class NotTakenMedicineListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    SqlHandler sqlHandler;
    ArrayList<MedicineItem> medicineItems = new ArrayList<MedicineItem>();
    //private int mSelectedItem;
    ArrayList<MedicineItem> medicineItemsListforLoop = new ArrayList<MedicineItem>();
    private NotTakenListAdapter notTakenListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.not_taken_medicine_fragment_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.notTaken_list);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        sqlHandler = new SqlHandler(getContext());


        //  String query = "SELECT  "+ SqlDbHelper.ISTAKEN + " FROM " + SqlDbHelper.DOZE_TAKEN_HISTORY_TABLE + " WHERE " + SqlDbHelper.ISTAKEN + " = '" + " No " + "';";


        showlist();
        //showlist(medicineItems.get(mSelectedItem));

/*        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/M/yyyy h:mm");
        String currentDate = formatter1.format(calendar1.getTime());

       *//* final String dateString = cursor.getString(4);

        final String timeString = cursor.getString(5);*//*
        String datadb = SqlDbHelper.COLUMN4;
        String dbTime = datadb.toString();

//  Toast.makeText(context,"databse date:-"+datadb+"Current Date :-"+currentDate,Toast.LENGTH_LONG).show();

        if (currentDate == medicineItem.getTime()) {
            showlist();
        }*/


        /*if (currentDate.compareTo(dbTime) >= 0) {
            showlist();
        }
        if (currentDate.compareTo(dbTime) >= 0) {
            showlist();
        }
*/
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        showlist();
        // medicineItems.get(mSelectedItem);
    }


    private void showlist() {

        medicineItems.clear();
        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");
        String currentDate = formatter1.format(calendar1.getTime());
        String time = currentDate.toString();
        String dbTime = SqlDbHelper.COLUMN4.toString();

        String query = "SELECT  * FROM " + MEDICINE_TABLE + ", " + SqlDbHelper.DOZE_TAKEN_HISTORY_TABLE + " WHERE " + SqlDbHelper.DOZE_TAKEN_HISTORY_TABLE + "." + SqlDbHelper.ISTAKEN + " = " + "'No'" +
                " AND " + SqlDbHelper.DOZE_TAKEN_HISTORY_TABLE + "." + SqlDbHelper.__MEDICINEIDFK1 + " = " + MEDICINE_TABLE + "." + SqlDbHelper._MEDICINEID + ";";

        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    MedicineItem medicineItem = new MedicineItem();
                    /*medicineItem.set_medicineId(c1.getString(c1
                            .getColumnIndex(SqlDbHelper._MEDICINEID)));*/
                    medicineItem.setSlno(c1.getString(c1
                            .getColumnIndex(SqlDbHelper._MEDICINEID)));
                    medicineItem.setName(c1.getString(c1
                            .getColumnIndex(SqlDbHelper.COLUMN2)));
                    medicineItem.setPhone(c1.getString(c1
                            .getColumnIndex(SqlDbHelper.COLUMN3)));
                    medicineItem.setTime(c1.getString(c1
                            .getColumnIndex(SqlDbHelper.COLUMN4)));
                    medicineItem.setDate(c1.getString(c1
                            .getColumnIndex(SqlDbHelper.COLUMN5)));
                    medicineItems.add(medicineItem);

                } while (c1.moveToNext());
            }
        }
        // c1.close();
        if (notTakenListAdapter == null) {
            notTakenListAdapter = new NotTakenListAdapter(getContext(), medicineItems, new OnMedicineItemClickListener() {
                @Override
                public void OnItemLongClickListener(int position) {
                }

                @Override
                public void onEditClicked(int position) {


                    Toast.makeText(getContext(), "Editing is disabled in this tab", Toast.LENGTH_SHORT).show();

                }
            });
            mRecyclerView.setAdapter(notTakenListAdapter);
        } else {
            notTakenListAdapter.setList(medicineItems);
        }
    }



   /* public void onDataModified() {
        showlist();
    }
*/

}
