package com.example.myapplication.checkin_guest.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.model.LodgingItem;
import com.example.myapplication.checkin_guest.util.Util;
import com.example.myapplication.checkin_guest.view.activity.SelectActivity;

import java.util.ArrayList;

public class RecyclerViewAdapterSearchResult extends RecyclerView.Adapter<RecyclerViewAdapterSearchResult.ViewHolder> {
    private ArrayList<LodgingItem> mList;
    private Context mContext;

    public RecyclerViewAdapterSearchResult(Context context){
        this.mContext = context;
    }

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
        private LottieAnimationView lottieAnimationView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_lodging = itemView.findViewById(R.id.img_lodging);
            txt_lodging_type = itemView.findViewById(R.id.txt_lodging_type);
            img_nfc_distance = itemView.findViewById(R.id.img_nfc_distance);
            txt_lodging_name = itemView.findViewById(R.id.txt_lodging_name);
            txt_fare = itemView.findViewById(R.id.txt_fare);
            lottieAnimationView = itemView.findViewById(R.id.lottieView);

            img_nfc_distance.setVisibility(View.INVISIBLE);

        }

        public void onBind(LodgingItem lodgingItem){
            Glide.with(mContext)
                    .load(lodgingItem.getTitle_image_path())
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .addListener(Util.imageLoadingListener(lottieAnimationView))
                    .into(img_lodging);

            txt_lodging_name.setText(lodgingItem.getName());
            txt_fare.setText(String.valueOf(lodgingItem.getFare()));
            if(lodgingItem.isNfc_distance()){
                img_nfc_distance.setVisibility(View.VISIBLE);
            }
            switch ((int) lodgingItem.getType()){
                case 1:
                    txt_lodging_type.setText("집 전제");
                    break;
                case 2:
                    txt_lodging_type.setText("개인실");
                    break;
                case 3:
                    txt_lodging_type.setText("호텔객실");
                    break;
            }

            img_lodging.setOnClickListener(view -> {
                Intent intent= new Intent(view.getContext(), SelectActivity.class);
                intent.putExtra("lodging", lodgingItem);
                mContext.startActivity(intent);
            });
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
