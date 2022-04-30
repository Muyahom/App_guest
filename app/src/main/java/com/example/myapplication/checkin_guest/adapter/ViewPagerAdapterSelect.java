package com.example.myapplication.checkin_guest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.model.Banner;
import com.example.myapplication.checkin_guest.model.LodgingItem;
import com.example.myapplication.checkin_guest.model.ViewPageDataSelect;
import com.example.myapplication.checkin_guest.util.Util;

import java.util.ArrayList;

public class ViewPagerAdapterSelect extends RecyclerView.Adapter<ViewPagerAdapterSelect.ViewHolderPage>{
    private ArrayList<LodgingItem> listData;
    private ImageView roomimage;
    private LottieAnimationView lottieAnimationView;
    Context context;
    private ArrayList<LodgingItem>mList;

//    public ViewPagerAdapterSelect(Context context){
//
//        this.context = context;
//    }

    public void setListData(ArrayList<LodgingItem> listData){
        this.listData = listData;
        notifyDataSetChanged();
    }

    class ViewHolderPage extends RecyclerView.ViewHolder{


        public ViewHolderPage(@NonNull View itemView) {
            super(itemView);
            roomimage = itemView.findViewById(R.id.img_main);
        }
        public void onBind(LodgingItem lodgingItem){

            Glide.with(context)
                    .load(lodgingItem.getTitle_image_path())
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(roomimage);
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
        holder.onBind(mList.get(position));
    }


    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }

}
