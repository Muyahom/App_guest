package com.example.myapplication.checkin_guest.view.fragment.searchWindow;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.adapter.RecyclerViewAdapterSm;
import com.example.myapplication.checkin_guest.callback.RecommendSearchWordClickListener;
import com.example.myapplication.checkin_guest.databinding.FragSearchWindow1Binding;
import com.example.myapplication.checkin_guest.model.City;
import com.example.myapplication.checkin_guest.view.activity.SearchActivity;
import com.example.myapplication.checkin_guest.viewModel.SearchViewModel;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Frag_searchWindow1 extends Fragment {
    private final String TAG = Frag_searchWindow1.class.getSimpleName();
    private FragSearchWindow1Binding fragSearchWindow1Binding = null;
    private SearchViewModel searchViewModel;
    private Map<String, City> cityMap;

    //지역명 배열
    private ArrayList<String> cityList;
    private RecyclerViewAdapterSm recyclerViewAdapter_sm;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragSearchWindow1Binding = DataBindingUtil.inflate(inflater, R.layout.frag_search_window1, container, false);
        //앱 최초 실행시 추천 검색어 창을 비활성화
        fragSearchWindow1Binding.nestedScrollView.setVisibility(View.INVISIBLE);

        init();

        // view model 사용을 위한 초기화 작업
        searchViewModel = new ViewModelProvider(this, new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication())).get(SearchViewModel.class);
        searchViewModel.setParentContext(getActivity());

        searchViewModel.getCityList().observe(getViewLifecycleOwner(), data -> {
            if (searchViewModel.getCityList().getValue() == null) {
                Logger.d(TAG, "city_list = null");
                return;
            }
            Logger.d(TAG, "지역 받아오기 완료");
            //cityList 세팅
            this.cityList = searchViewModel.getCityList().getValue();
            Log.d(TAG, cityList.toString());
            for (int i = 1; i < this.cityList.size(); i++) {
                String name = cityList.get(i);
                City city = new City();
                city.setName(name);
                cityMap.put(name, city);
            }
            Log.d(TAG, "cityMap 세팅완료");
        });


        //검색창 실행시 지역리스트를 우선 받아옴
        searchViewModel.onRequestGetCityList();


        fragSearchWindow1Binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 검색 버튼 누를 때 호출
                ((SearchActivity) getActivity()).setSearchWord(query);
                ((SearchActivity) getActivity()).move_frag(1);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //검색창에서 글자가 변경이 일어날 때 마다 호출, 공백인 경우는 리스트 비우기
                Log.d(TAG, "입력 글자 : " + newText);
                if (!newText.equals("")) {
                    search(newText);
                    fragSearchWindow1Binding.nestedScrollView.setVisibility(View.VISIBLE);
                    fragSearchWindow1Binding.recentSearch.setVisibility(View.INVISIBLE);
                }else{
                    fragSearchWindow1Binding.nestedScrollView.setVisibility(View.INVISIBLE);
                    fragSearchWindow1Binding.recentSearch.setVisibility(View.VISIBLE);
                    recyclerViewAdapter_sm.clearMList();
                }
                return true;
            }
        });

        return fragSearchWindow1Binding.getRoot();
    }

    private void init() {
        //디자인 문제로 인한 fragment 로딩시 바로 searchView 활성화
        fragSearchWindow1Binding.searchView.setIconified(false);

        fragSearchWindow1Binding.recyclerviewSm.setLayoutManager((new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false)));
        recyclerViewAdapter_sm = new RecyclerViewAdapterSm();
        recyclerViewAdapter_sm.setmClickListener(getRecommentSearchWordClickListener());
        fragSearchWindow1Binding.recyclerviewSm.setAdapter(recyclerViewAdapter_sm);

        //검색 기능 관련 처리
        cityList = new ArrayList<String>();
        cityMap = new HashMap<>();


    }

    /* 사용자가 텍스트 입력 -> 글자 변경이 일어날때마다 검색 함수 실행 -> 그 단어들은 이미 저장되어있는 Map의 key값임(Map -> (key, City.class))
    -> 그럼 그 key값을 통해 SearchArrayList 세팅, recyclerview에 삽입 -> 근데이걸 main thread만 담당하면 부담이 간다. -> async thread를 만들어서
    검색에 관한 처리를 별도로 수행하게 한다.*/

    private void search(String text) {
        ArrayList<City> city_list = new ArrayList<City>();
        for (int i = 1; i < this.cityList.size(); i++) {
            String item = this.cityList.get(i);
            if (item.contains(text.trim())) {
                city_list.add(cityMap.get(item));
            }
        }
        Log.d(TAG, "연관 검색어 : " + city_list.toString());
        recyclerViewAdapter_sm.setmList(city_list);
    }

    private RecommendSearchWordClickListener getRecommentSearchWordClickListener(){
        return query -> settingSearchWord(query);
    }

    private boolean settingSearchWord(String searchWord){
        boolean check = false;
        if(searchWord.equals("")) {
            Toast.makeText(getContext(), "검색어를 입력해주세요", Toast.LENGTH_SHORT).show();

        }else{
            //SearchActivity로 데이터 보냄
            ((SearchActivity)getActivity()).setSearchWord(searchWord);
            check = true;
        }

        return check;
    }
}
