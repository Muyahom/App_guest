package com.example.myapplication.checkin_guest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.model.ViewPageDataBanner;

import java.util.ArrayList;

public class ViewPagerAdapterBanner extends RecyclerView.Adapter<ViewPagerAdapterBanner.ViewHolderPage>{
    private ArrayList<ViewPageDataBanner> listData;

    public void setListData(ArrayList<ViewPageDataBanner> listData){
        this.listData = listData;
    }

    class ViewHolderPage extends RecyclerView.ViewHolder{
        private ImageView imageView;

        public ViewHolderPage(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_banner);
        }

        public void onBind(ViewPageDataBanner viewPageDataBanner){
            imageView.setImageBitmap(viewPageDataBanner.getBitmap());
        }
    }

    @NonNull
    @Override
    public ViewHolderPage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewpager_banner, parent, false);
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
