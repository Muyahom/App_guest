package com.example.myapplication.checkin_guest.view.fragment.searchWindow;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.FragSearchWindow2Binding;
import com.example.myapplication.checkin_guest.view.activity.SearchActivity;
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

public class Frag_searchWindow2 extends Fragment {
    private final String TAG = "Frag_searchWindow2";
    private FragSearchWindow2Binding fragSearchWindow2Binding = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragSearchWindow2Binding = DataBindingUtil.inflate(inflater, R.layout.frag_search_window2, container, false);

        //calendar 설정
        fragSearchWindow2Binding.materialCalendar.state()
                .edit()
                .setFirstDayOfWeek(DayOfWeek.of(Calendar.MONDAY))
                .commit();

        // 월, 요일을 한글로 보이게 설정 (MonthArrayTitleFormatter의 작동을 확인하려면 밑의 setTitleFormatter()를 지운다)
        fragSearchWindow2Binding.materialCalendar.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months)));
        fragSearchWindow2Binding.materialCalendar.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)));
        // 좌우 화살표 사이 연, 월의 폰트 스타일 설정
        fragSearchWindow2Binding.materialCalendar.setHeaderTextAppearance(R.style.CalendarWidgetHeader);
        // 요일 선택 시 내가 정의한 드로어블이 적용되도록 함
        fragSearchWindow2Binding.materialCalendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                String clickYear = String.valueOf(date.getYear());
                String clickMonth = String.valueOf(date.getMonth());
                String clickDate = String.valueOf(date.getDay());
                setDateText(clickYear, clickMonth, clickDate);
            }
        });
        fragSearchWindow2Binding.materialCalendar.setOnRangeSelectedListener(new OnRangeSelectedListener() {
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
        fragSearchWindow2Binding.materialCalendar.addDecorators(new DayDecorator(getContext()));

        // 좌우 화살표 가운데의 연/월이 보이는 방식 커스텀
        fragSearchWindow2Binding.materialCalendar.setTitleFormatter(new TitleFormatter() {
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

        fragSearchWindow2Binding.btnNext.setOnClickListener(view -> {
            ((SearchActivity) getActivity()).setIsPeriodCheck(true);
            ((SearchActivity) getActivity()).move_frag(2);
        });

        fragSearchWindow2Binding.btnSkip.setOnClickListener(view -> {
            ((SearchActivity) getActivity()).setIsPeriodCheck(false);
            ((SearchActivity) getActivity()).move_frag(2);
        });

        return fragSearchWindow2Binding.getRoot();
    }

    private void setDateText(String clickYear, String clickMonth, String clickDate) {
        fragSearchWindow2Binding.txtStartYear.setText(clickYear);
        fragSearchWindow2Binding.txtStartMonth.setText(clickMonth);
        fragSearchWindow2Binding.txtStartDate.setText(clickDate);
        fragSearchWindow2Binding.txtEndYear.setText("");
        fragSearchWindow2Binding.txtEndMonth.setText("");
        fragSearchWindow2Binding.txtEndDate.setText("");
    }

    private void setPeriod(String startYear, String startMonth, String startDate, String endYear, String endMonth, String endDate) {
        fragSearchWindow2Binding.txtStartYear.setText(startYear);
        fragSearchWindow2Binding.txtStartMonth.setText(startMonth);
        fragSearchWindow2Binding.txtStartDate.setText(startDate);
        fragSearchWindow2Binding.txtEndYear.setText(endYear);
        fragSearchWindow2Binding.txtEndMonth.setText(endMonth);
        fragSearchWindow2Binding.txtEndDate.setText(endDate);
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