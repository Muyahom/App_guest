package com.example.myapplication.checkin_guest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.model.ViewPageDataSelect;

import java.util.ArrayList;

public class ViewPagerAdapterSelect extends RecyclerView.Adapter<ViewPagerAdapterSelect.ViewHolderPage>{
    private ArrayList<ViewPageDataSelect> listData;

    public void setListData(ArrayList<ViewPageDataSelect> listData){
        this.listData = listData;
    }

    class ViewHolderPage extends RecyclerView.ViewHolder{
        private ImageView roomimage;

        public ViewHolderPage(@NonNull View itemView) {
            super(itemView);
            roomimage = itemView.findViewById(R.id.img_main);

        }

        public void onBind(ViewPageDataSelect viewPageDataBanner){
            roomimage.setImageBitmap(viewPageDataBanner.getBitmap());
        }
    }

    @NonNull
    @Override
    public ViewHolderPage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewpager_select, parent, false);
        return new ViewHolderPage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPage holder, int position) {
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }

}
