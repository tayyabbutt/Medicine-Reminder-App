package com.example.tayyabbutt.listviewadapter.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tayyabbutt.listviewadapter.R;
import com.example.tayyabbutt.listviewadapter.activity.EditActivity;
import com.example.tayyabbutt.listviewadapter.activity.EnteredDataActivity;
import com.example.tayyabbutt.listviewadapter.adapter.RecyclerListAdapter;
import com.example.tayyabbutt.listviewadapter.database.SqlDbHelper;
import com.example.tayyabbutt.listviewadapter.database.SqlHandler;
import com.example.tayyabbutt.listviewadapter.interfaces.OnMedicineItemClickListener;
import com.example.tayyabbutt.listviewadapter.model.MedicineItem;

import java.util.ArrayList;

/**
 * Created by Tayyab Butt on 12/27/2017.
 */

public class RecyclerFragment extends Fragment {


    private RecyclerView mRecyclerView;
    SqlHandler sqlHandler;
    ArrayList<MedicineItem> contactList = new ArrayList<MedicineItem>();
    private RecyclerListAdapter recyclerListAdapter;
    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_fragment, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        sqlHandler = new SqlHandler(getContext());


        fab = (FloatingActionButton) view.findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), EnteredDataActivity.class));
            }

        });


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    fab.show();
                }
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        showlist();
    }

    private void deleteList(int arg2) {
        MedicineItem medicineItem = contactList.get(arg2);
        String slno = medicineItem.getSlno();
        String delQuery = "DELETE FROM MEDICINE_TABLE WHERE _medicineId='" + slno + "' ";
        sqlHandler.executeQuery(delQuery);
        showlist();
    }

    private void showlist() {
        contactList.clear();
        String query = "SELECT * FROM " + SqlDbHelper.MEDICINE_TABLE;
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    MedicineItem medicineItem = new MedicineItem();

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
                    contactList.add(medicineItem);

                } while (c1.moveToNext());
            }
        }
        c1.close();
        if (recyclerListAdapter == null) {
            recyclerListAdapter = new RecyclerListAdapter(getContext(), contactList, new OnMedicineItemClickListener() {
                @Override
                public void OnItemLongClickListener(int position) {
                    Toast.makeText(getContext(), "On LongClick", Toast.LENGTH_SHORT).show();
                    showDialog(position);

                }

                @Override
                public void onEditClicked(int position) {
                    Intent intent = new Intent(getContext(), EditActivity.class);
                    intent.putExtra(SqlDbHelper._MEDICINEID, recyclerListAdapter.getList().get(position).getSlno());
                    intent.putExtra(SqlDbHelper.COLUMN2, recyclerListAdapter.getList().get(position).getName());
                    intent.putExtra(SqlDbHelper.COLUMN3, recyclerListAdapter.getList().get(position).getPhone());
                    intent.putExtra(SqlDbHelper.COLUMN4, recyclerListAdapter.getList().get(position).getTime());
                    intent.putExtra(SqlDbHelper.COLUMN5, recyclerListAdapter.getList().get(position).getDate());
                    startActivity(intent);
                }
            });
            mRecyclerView.setAdapter(recyclerListAdapter);
        } else {
            recyclerListAdapter.setList(contactList);
        }
    }


    public void onDataModified() {
        showlist();
    }

    private void showDialog(final int position) {

        final Dialog dialog = new Dialog(getContext());
        // Include dialog.xml file
        dialog.setContentView(R.layout.custom_dialog);
        // Set dialog title
        dialog.setTitle("Delete selected item");

        // set values for custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.txt_dia);
        text.setText("Do you really want to Delete ? ");
        Button yesBtn = (Button) dialog.findViewById(R.id.btn_yes);
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteList(position);
                dialog.dismiss();


            }
        });

        dialog.show();

        Button noBtn = (Button) dialog.findViewById(R.id.btn_no);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
            }
        });

    }
}
