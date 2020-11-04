package com.example.tayyabbutt.listviewadapter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tayyabbutt.listviewadapter.R;
import com.example.tayyabbutt.listviewadapter.model.HistoryModel;

import java.util.List;

/**
 * Created by Tayyab Butt on 1/17/2018.
 */

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {


    Context context;
    private List<HistoryModel> mDataSet;

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView name_text, date_text, isTaken_text;

        public ViewHolder(View v) {
            super(v);

            name_text = (TextView) v.findViewById(R.id.text_name);
            date_text = (TextView) v.findViewById(R.id.text_date);
            isTaken_text = (TextView) v.findViewById(R.id.text_istaken);

        }
    }

    //Provide a suitable constructor
    public ReportAdapter(Context context,List<HistoryModel> list) {
        this.context = context;
        mDataSet = list;
        // this.mListener = listener;
    }


    public void setList(List<HistoryModel> list) {
        mDataSet = list;
        notifyDataSetChanged();
    }

    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.report_generate_item, parent, false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ReportAdapter.ViewHolder holder, int position) {

        final HistoryModel medicineItem = mDataSet.get(position);

        holder.name_text.setText(medicineItem.getMedicineName());
        holder.date_text.setText(medicineItem.getDate());
        holder.isTaken_text.setText(medicineItem.getIsTaken());

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

}
