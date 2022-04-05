package com.example.myapplication.checkin_guest.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.model.LodgingItem;
import com.example.myapplication.checkin_guest.model.RecommendationItem;
import com.example.myapplication.checkin_guest.view.activity.SelectActivity;

import java.util.ArrayList;

public class RecyclerViewAdapter_searchResult extends RecyclerView.Adapter<RecyclerViewAdapter_searchResult.ViewHolder> {
    private ArrayList<LodgingItem> mList;

    public void set_mList(ArrayList<LodgingItem> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img_lodging;
        private TextView txt_lodging_type;
        private ImageView img_nfc_distance;
        private TextView txt_lodging_name;
        private TextView txt_fare;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_lodging = itemView.findViewById(R.id.img_lodging);
            txt_lodging_type = itemView.findViewById(R.id.txt_lodging_type);
            img_nfc_distance = itemView.findViewById(R.id.img_nfc_distance);
            txt_lodging_name = itemView.findViewById(R.id.txt_lodging_name);
            txt_fare = itemView.findViewById(R.id.txt_fare);

            img_lodging.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent(view.getContext(), SelectActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
        }

        public void onBind(LodgingItem lodgingItem){
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_sr, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(mList.get(position));

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

}
