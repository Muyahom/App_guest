package com.example.myapplication.checkin_guest.viewModel;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.checkin_guest.callback.RTListener;
import com.example.myapplication.checkin_guest.callback.SearchListener;
import com.example.myapplication.checkin_guest.model.LodgingItem;
import com.example.myapplication.checkin_guest.viewModel.Executor.FireStoreExcutor;
import com.example.myapplication.checkin_guest.viewModel.Executor.RealTimeDatabaseExcutor;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class SearchViewModel extends ViewModel {
    private final String TAG = SearchViewModel.class.getSimpleName();
    private WeakReference<Activity> mActivity;

    //FireStore 처리 관련 변수
    private FireStoreExcutor fireStoreExcutor = new FireStoreExcutor();

    //RealTimeDataBase 처리 관련 변수
    private RealTimeDatabaseExcutor realTimeDatabaseExcutor = new RealTimeDatabaseExcutor();

    //city_list
    private MutableLiveData<ArrayList<String>> city_list = new MutableLiveData<>();

    //숙소 검색 결과가 담기는 list
    private MutableLiveData<ArrayList<LodgingItem>> lodgingMLList = new MutableLiveData<>();
    private ArrayList<LodgingItem> lodgingList = new ArrayList<>();

    public SearchViewModel() {

    }

    //activity setting
    public void setParentContext(Activity parentContext) {
        mActivity = new WeakReference<>(parentContext);
        realTimeDatabaseExcutor.setRtListener(getRTListener());
    }

    private void finishActivity() {
        if (mActivity.get() != null)
            mActivity.get().finish();
    }

    private RTListener getRTListener() {
        return city_result -> city_list.setValue(city_result);
    }

    /*
           RealTimeDatabase 관련 메서드 모음
                                                */
    //RealTimeDataBase 처리를 위한 RTDatabase 생성
    public LiveData<ArrayList<String>> getCityList() {
        return city_list;
    }

    public void onRequestGetCityList() {
        Log.d(TAG, "onRequestgetCityList");
        realTimeDatabaseExcutor.getCityList();
    }

    /*      검색 처리 코드   */
    public void setSearchListener(){
        fireStoreExcutor.setSearchListener(getSearchListener());
    }

    private SearchListener getSearchListener(){
        return querySnapshot -> {
            for(QueryDocumentSnapshot result : querySnapshot){
                LodgingItem lodgingItem = new LodgingItem();
                lodgingItem.setUid((String) result.get("uid"));
                lodgingItem.setAcceptance((long) result.get("acceptance"));
                lodgingItem.setAddress((String) result.get("address"));
                lodgingItem.setCity((String) result.get("city"));
                lodgingItem.setCnt_bad((long) result.get("cnt_bad"));
                lodgingItem.setCnt_badRoom((long)result.get("cnt_badRoom"));
                lodgingItem.setCnt_toilet((long)result.get("cnt_toilet"));
                lodgingItem.setConvenience((ArrayList<String>) result.get("convenience"));
                lodgingItem.setFare((long) result.get("fare"));
                lodgingItem.setGeoPoint((GeoPoint) result.get("geopoint"));
                lodgingItem.setHost_uid(String.valueOf(result.get("host_uid")));
                lodgingItem.setImg_path((ArrayList<String>) result.get("img_path"));
                lodgingItem.setIntroductory((String) result.get("introductory"));
                lodgingItem.setName((String) result.get("name"));
                lodgingItem.setNfc_distance((boolean)result.get("nfc_distance"));
                lodgingItem.setReservation_time_list((ArrayList<String>) result.get("reservation_time_list"));
                lodgingItem.setReview((ArrayList<String>) result.get("review"));
                lodgingItem.setState((String) result.get("state"));
                lodgingItem.setState((String) result.get("title_image_path"));
                lodgingItem.setType((long) result.get("type"));
                lodgingItem.setUid((String) result.get("uid"));
                Log.d(TAG, lodgingItem.toString());
                lodgingList.add(lodgingItem);

            }
            lodgingMLList.setValue(lodgingList);
        };
    }
    // city만 설정된 검색 조건
    public void search(String city){
        Log.d(TAG, "search");
        fireStoreExcutor.getSearchLodging(city);
    }

}
