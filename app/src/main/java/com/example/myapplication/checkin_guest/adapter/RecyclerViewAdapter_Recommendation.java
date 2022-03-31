package com.example.myapplication.checkin_guest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.model.RecommendationItem;
import com.example.myapplication.checkin_guest.model.ViewPageDataBanner;

import java.util.ArrayList;

public class RecyclerViewAdapter_Recommendation extends RecyclerView.Adapter<RecyclerViewAdapter_Recommendation.ViewHolder> {

    ArrayList<RecommendationItem> listData;

    public void setListData(ArrayList<RecommendationItem> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_rc);
        }

        public void onBind(RecommendationItem recommendationItem){
            imageView.setImageBitmap(recommendationItem.getBitmap());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_rc, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }
}
