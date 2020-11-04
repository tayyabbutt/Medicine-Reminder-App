package com.example.tayyabbutt.listviewadapter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tayyabbutt.listviewadapter.R;
import com.example.tayyabbutt.listviewadapter.interfaces.OnMedicineItemClickListener;
import com.example.tayyabbutt.listviewadapter.model.MedicineItem;

import java.util.ArrayList;

/**
 * Created by Tayyab Butt on 1/17/2018.
 */

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ViewHolder> {


    Context context;
    private OnMedicineItemClickListener mListener;
    private ArrayList<MedicineItem> contactList;
    private int lastPosition = -1;

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView circle_btn;
        TextView time1;
        TextView date1;
        TextView tvName;
        TextView tvPhone;

        public ViewHolder(View v) {
            super(v);

            circle_btn = (ImageView) v.findViewById(R.id.circle_bttn);
            time1 = (TextView) v.findViewById(R.id.time_1);
            tvName = (TextView) v.findViewById(R.id.tv_name);
            tvPhone = (TextView) v.findViewById(R.id.tv_phone);
            date1 = (TextView) v.findViewById(R.id.date_1);


            circle_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mListener != null) {
                        mListener.onEditClicked(getAdapterPosition());
                    }
                }
            });


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mListener != null) {
                        mListener.OnItemLongClickListener(getAdapterPosition());
                    }
                    return false;
                }
            });

        }

    }


    //Provide a suitable constructor
    public RecyclerListAdapter(Context context, ArrayList<MedicineItem> list, OnMedicineItemClickListener listener) {
        this.context = context;
        contactList = list;
        this.mListener = listener;
    }


    public void setList(ArrayList<MedicineItem> list) {
        contactList = list;
        notifyDataSetChanged();
    }

    public ArrayList<MedicineItem> getList() {
        return contactList;
    }

    @Override
    public RecyclerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.contact_list_row, parent, false);
        //View v = LayoutInflater.from(context).inflate(R.layout.report_generate_item, parent, false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final RecyclerListAdapter.ViewHolder holder, int position) {

        final MedicineItem medicineItem = contactList.get(position);

        //  String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        holder.time1.setText(addColon(medicineItem.getTime()));
        holder.date1.setText(medicineItem.getDate());
        holder.tvName.setText(medicineItem.getName());
        holder.tvPhone.setText(medicineItem.getPhone());
        holder.tvPhone.setText(medicineItem.getPhone());

        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.up_from_bottom_1
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;
        // setAnimation(holder.itemView, position);
    }

    private String addColon(String str) {
        String temp = "";
        if (str.length() == 3) {
            temp = str.substring(0, 1) + ":" + str.substring(1);

        }
        if (str.length() == 4) {
            temp = str.substring(0, 2) + ":" + str.substring(2);
        }
        return temp;
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
      /*  if (position > lastPosition)
        {*/
        // Animation animation = AnimationUtils.loadAnimation(context, android.R.up_from_bottom.slide_in_left);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.up_from_bottom);
        viewToAnimate.startAnimation(animation);
        //lastPosition = position;


    }
}
