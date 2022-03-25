package com.example.myapplication.checkin_guest.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.view.WindowManager;

public class Util {

    public static void transparency_statusBar(Activity acticity){
        acticity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    // 휴대폰의 하단 네비게이션바가 있는지 검사
    public static boolean isUseBottomNavigation(Context context) {
        int id = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        boolean useSoftNavigation = context.getResources().getBoolean(id);
        return useSoftNavigation;
    }

    // 휴대폰의 하단 네비게이션 바가 존재한다면 높이를 반환
    public static int getBottomNavigationHeight(Context context) {
        int bottomNavigation = 0;
        int screenSizeType = (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK);
        if (screenSizeType != Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            //태블릿 예외처리
            int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                bottomNavigation = context.getResources().getDimensionPixelSize(resourceId);
            }
        }
        if (!isUseBottomNavigation(context)) bottomNavigation = 0;
        return bottomNavigation;
    }
}
