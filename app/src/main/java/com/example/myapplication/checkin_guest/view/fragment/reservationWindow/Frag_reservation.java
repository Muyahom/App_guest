package com.example.myapplication.checkin_guest.view.fragment.reservationWindow;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.FragReservationBinding;
import com.example.myapplication.checkin_guest.view.activity.SearchActivity;
import com.example.myapplication.checkin_guest.view.fragment.searchWindow.Frag_searchWindow2;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

import java.util.Calendar;
import java.util.List;

public class Frag_reservation extends Fragment {

    private final String TAG = "Frag_searchWindow2";
    private FragReservationBinding FR_Binding= null;
    int count1,count2,count3=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FR_Binding = DataBindingUtil.inflate(inflater, R.layout.frag_reservation, container, false);

        //calendar 설정
        FR_Binding.materialCalendar.state()
                .edit()
                .setFirstDayOfWeek(DayOfWeek.of(Calendar.MONDAY))
                .commit();

        // 월, 요일을 한글로 보이게 설정 (MonthArrayTitleFormatter의 작동을 확인하려면 밑의 setTitleFormatter()를 지운다)
        FR_Binding.materialCalendar.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months)));
        FR_Binding.materialCalendar.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)));
        // 좌우 화살표 사이 연, 월의 폰트 스타일 설정
        FR_Binding.materialCalendar.setHeaderTextAppearance(R.style.CalendarWidgetHeader);
        // 요일 선택 시 내가 정의한 드로어블이 적용되도록 함
        FR_Binding.materialCalendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                String clickYear = String.valueOf(date.getYear());
                String clickMonth = String.valueOf(date.getMonth());
                String clickDate = String.valueOf(date.getDay());
                setDateText(clickYear, clickMonth, clickDate);
            }
        });
        FR_Binding.materialCalendar.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {
                // 아래 로그를 통해 시작일, 종료일이 어떻게 찍히는지 확인하고 본인이 필요한 방식에 따라 바꿔 사용한다
                // UTC 시간을 구하려는 경우 이 라이브러리에서 제공하지 않으니 별도의 로직을 짜서 만들어내 써야 한다
                String startYear = String.valueOf(dates.get(0).getDate().getYear());
                String startMonth = String.valueOf(dates.get(0).getDate().getMonthValue());
                String startDate = String.valueOf(dates.get(0).getDate().getDayOfMonth());
                String endYear = String.valueOf(dates.get(dates.size() - 1).getDate().getYear());
                String endMonth = String.valueOf(dates.get(dates.size() - 1).getDate().getMonthValue());
                String endDate = String.valueOf(dates.get(dates.size() - 1).getDate().getDayOfMonth());
                setPeriod(startYear, startMonth, startDate, endYear, endMonth, endDate);
            }
        });

        // 일자 선택 시 내가 정의한 드로어블이 적용되도록 한다
        FR_Binding.materialCalendar.addDecorators(new Frag_reservation.DayDecorator(getContext()));

        // 좌우 화살표 가운데의 연/월이 보이는 방식 커스텀
        FR_Binding.materialCalendar.setTitleFormatter(new TitleFormatter() {
            @Override
            public CharSequence format(CalendarDay day) {
                // CalendarDay라는 클래스는 LocalDate 클래스를 기반으로 만들어진 클래스다
                // 때문에 MaterialCalendarView에서 연/월 보여주기를 커스텀하려면 CalendarDay 객체의 getDate()로 연/월을 구한 다음 LocalDate 객체에 넣어서
                // LocalDate로 변환하는 처리가 필요하다
                LocalDate inputText = day.getDate();
                String[] calendarHeaderElements = inputText.toString().split("-");
                StringBuilder calendarHeaderBuilder = new StringBuilder();
                calendarHeaderBuilder.append(calendarHeaderElements[0])
                        .append(" ")
                        .append(calendarHeaderElements[1]);
                return calendarHeaderBuilder.toString();
            }
        });

        //버튼 눌렀을때 텍스트뷰 증가/감소
        FR_Binding.adultP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count1++;
                FR_Binding.adult.setText(count1+"");
            }
        });
        FR_Binding.adultM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count1--;
                FR_Binding.adult.setText(count1+"");
                if(count1<0){
                    FR_Binding.adult.setText(0+"");
                    count1=0;
                }
            }
        });
        FR_Binding.childP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count2++;
                FR_Binding.child.setText(count2+"");
            }
        });
        FR_Binding.childM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count2--;
                FR_Binding.child.setText(count2+"");
                if(count2<0){
                    FR_Binding.child.setText(0+"");
                    count2=0;
                }
            }
        });

        FR_Binding.patP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count3++;
                FR_Binding.pat.setText(count3+"");

            }
        });
        FR_Binding.patM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count3--;
                FR_Binding.pat.setText(count3+"");
                if(count3<0){
                    FR_Binding.pat.setText(0+"");
                    count3=0;
                }
            }
        });



        return FR_Binding.getRoot();
    }

    private void setDateText(String clickYear, String clickMonth, String clickDate){
        FR_Binding.txtStartYear.setText(clickYear);
        FR_Binding.txtStartMonth.setText(clickMonth);
        FR_Binding.txtStartDate.setText(clickDate);
        FR_Binding.txtEndYear.setText("");
        FR_Binding.txtEndMonth.setText("");
        FR_Binding.txtEndDate.setText("");
    }

    private void setPeriod(String startYear, String startMonth, String startDate, String endYear, String endMonth, String endDate){
        FR_Binding.txtStartYear.setText(startYear);
        FR_Binding.txtStartMonth.setText(startMonth);
        FR_Binding.txtStartDate.setText(startDate);
        FR_Binding.txtEndYear.setText(endYear);
        FR_Binding.txtEndMonth.setText(endMonth);
        FR_Binding.txtEndDate.setText(endDate);
    }

    /* 선택된 요일의 background를 설정하는 Decorator 클래스 */
    private static class DayDecorator implements DayViewDecorator {

        private final Drawable drawable;

        public DayDecorator(Context context) {
            drawable = ContextCompat.getDrawable(context, R.drawable.calendar_selector);
        }

        // true를 리턴 시 모든 요일에 내가 설정한 드로어블이 적용된다
        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return true;
        }

        // 일자 선택 시 내가 정의한 드로어블이 적용되도록 한다
        @Override
        public void decorate(DayViewFacade view) {
            view.setSelectionDrawable(drawable);
//            view.addSpan(new StyleSpan(Typeface.BOLD));   // 달력 안의 모든 숫자들이 볼드 처리됨
        }


    }





}
