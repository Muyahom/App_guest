package com.example.myapplication.checkin_guest.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.model.Banner;
import com.example.myapplication.checkin_guest.model.ViewPageDataBanner;
import com.example.myapplication.checkin_guest.util.Util;

import java.util.ArrayList;

public class ViewPagerAdapterBanner extends RecyclerView.Adapter<ViewPagerAdapterBanner.ViewHolderPage>{
    private final String TAG = ViewPagerAdapterBanner.class.getSimpleName();
    private ArrayList<Banner> listData;
    private Context context;

    public ViewPagerAdapterBanner(Context context){
        this.context = context;
    }

    public void setListData(ArrayList<Banner> listData){
        this.listData = listData;
        notifyDataSetChanged();
    }

    class ViewHolderPage extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private LottieAnimationView lottieAnimationView;

        public ViewHolderPage(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_banner);
            lottieAnimationView = itemView.findViewById(R.id.lottieView);
        }

        public void onBind(Banner banner){
            Log.d(TAG, "onBind");
            Glide.with(context)
                    .load(banner.getImg_url())
                    .addListener(Util.imageLoadingListener(lottieAnimationView))
                    .into(imageView);

//                .error(R.drawable.user_basic).fallback(R.drawable.user_basic)
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
