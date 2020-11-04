package com.example.tayyabbutt.listviewadapter.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tayyabbutt.listviewadapter.R;
import com.example.tayyabbutt.listviewadapter.database.SqlDbHelper;
import com.example.tayyabbutt.listviewadapter.interfaces.OnMedicineItemClickListener;
import com.example.tayyabbutt.listviewadapter.model.MedicineItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.tayyabbutt.listviewadapter.database.SqlDbHelper.COLUMN4;

/**
 * Created by Tayyab Butt on 1/17/2018.
 */

public class NotTakenListAdapter extends RecyclerView.Adapter<NotTakenListAdapter.ViewHolder> {


    Context context;
    private OnMedicineItemClickListener mListener;
    private ArrayList<MedicineItem> medicineItems;
    private int lastPosition = -1;


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView circle_btn;
        TextView time1;
        TextView date1;
        TextView tvName;
        TextView tvPhone;
        android.support.v7.widget.CardView card_view;

        public ViewHolder(View v) {
            super(v);

            circle_btn = (ImageView) v.findViewById(R.id.circle_bttn_1_1);
            time1 = (TextView) v.findViewById(R.id.time_1_1_1);
            tvName = (TextView) v.findViewById(R.id.tv_name_1_1);
            tvPhone = (TextView) v.findViewById(R.id.tv_phone_1_1);
            date1 = (TextView) v.findViewById(R.id.date_1_1_1);
            card_view = (CardView) v.findViewById(R.id.LinearLayout1_1_1);


        }

    }


    //Provide a suitable constructor
    public NotTakenListAdapter(Context context, ArrayList<MedicineItem> list, OnMedicineItemClickListener listener) {
        this.context = context;
        medicineItems = list;
        this.mListener = listener;
    }


    public void setList(ArrayList<MedicineItem> list) {
        medicineItems = list;
        notifyDataSetChanged();
    }

    public ArrayList<MedicineItem> getList() {
        return medicineItems;
    }

    @Override
    public NotTakenListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.not_taken_medicine_list_activity, parent, false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final NotTakenListAdapter.ViewHolder holder, int position) {

        final MedicineItem medicineItem = medicineItems.get(position);

        //  String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        holder.time1.setText(addColon(medicineItem.getTime()));
        holder.date1.setText(medicineItem.getDate());
        holder.tvName.setText(medicineItem.getName());
        holder.tvPhone.setText(medicineItem.getPhone());
        holder.tvPhone.setText(medicineItem.getPhone());
        ((CardView) holder.card_view).setCardBackgroundColor(Color.RED);
        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom_1 : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;


        // ((CardView) holder.card_view).setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.lightRed));


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
    public void onViewDetachedFromWindow(NotTakenListAdapter.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return medicineItems.size();
    }

}
