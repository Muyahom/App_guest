package com.example.myapplication.checkin_guest.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.ActivityHelpBinding;
import com.example.myapplication.checkin_guest.databinding.ActivityHelpBindingImpl;

public class HelpActivity extends AppCompatActivity {
    ActivityHelpBinding activityHelpBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHelpBinding = DataBindingUtil.setContentView(this, R.layout.activity_help);

        //질문목록
        String [] help={"NFC 비밀번호 사용 숙소가 무엇인가요?","숙소 검색/예약 방법이 궁금해요","알림설정 방법이 궁금해요","계정설정변경 방법이 궁금해요","예약 내역을 확인하고 싶어요","호스트와 연락은 어떻게 하나요?"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,help);
        activityHelpBinding.helpList.setAdapter(adapter);

        //취소버튼 누르면 종료
        activityHelpBinding.imX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
