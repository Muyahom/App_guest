package com.example.myapplication.checkin_guest.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.checkin_guest.view.fragment.alarmWindow.Frag_alarm1;
import com.example.myapplication.checkin_guest.view.fragment.alarmWindow.Frag_alarm2;

import java.util.ArrayList;

public class ViewPagerAdapterAlarm extends FragmentPagerAdapter {
    private ArrayList<Fragment> arrayList = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<String>();

    public ViewPagerAdapterAlarm(@NonNull FragmentManager fm) {

        super(fm);
        arrayList.add(new Frag_alarm1());
        arrayList.add(new Frag_alarm2());

        name.add("업데이트 및 혜택");
        name.add("계정관리");


    }


    @NonNull
    @Override

    public CharSequence getPageTitle(int position)
    {
        return name.get(position);
    }
    public Fragment getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }
}
