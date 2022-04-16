package com.example.myapplication.checkin_guest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.model.City;

import java.util.ArrayList;

public class RecyclerViewAdapterSm extends RecyclerView.Adapter<RecyclerViewAdapterSm.ViewHolder> {
    private ArrayList<City> mList;

    public void setmList(ArrayList<City> list){
        this.mList = list;
        notifyDataSetChanged();
    }

    public void clearMList(){
        this.mList.clear();
        notifyDataSetChanged();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_basic);
            textView = itemView.findViewById(R.id.txt_city);
        }

        public void bind(City city){
            textView.setText(city.getName());
        }
    }


    @NonNull
    @Override
    public RecyclerViewAdapterSm.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_sm, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterSm.ViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }
}
