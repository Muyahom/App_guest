package com.example.myapplication.checkin_guest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.model.ChattingItem;
import com.example.myapplication.checkin_guest.model.PopularItem;

import java.util.ArrayList;

public class RecyclerViewAdapter_chatting extends RecyclerView.Adapter<RecyclerViewAdapter_chatting.ViewHolder> {
    ArrayList<ChattingItem> listData;

    public void setListData(ArrayList<ChattingItem> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView txt_sender, txt_msg, txt_senderDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_sender);
            txt_sender = itemView.findViewById(R.id.txt_sender);
            txt_msg = itemView.findViewById(R.id.txt_msg);
            txt_senderDate = itemView.findViewById(R.id.txt_sendDate);
        }
        public void onBind(ChattingItem chattingItem){
            imageView.setImageBitmap(chattingItem.getBitmap());
            txt_sender.setText(chattingItem.getSender());
            txt_msg.setText(chattingItem.getMessage());
            txt_senderDate.setText(chattingItem.getSendTime());
        }
    }
    @NonNull
    @Override
    public RecyclerViewAdapter_chatting.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_ch, parent, false);
        return new RecyclerViewAdapter_chatting.ViewHolder(view);
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
