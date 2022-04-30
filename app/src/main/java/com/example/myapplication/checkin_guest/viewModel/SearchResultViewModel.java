package com.example.myapplication.checkin_guest.viewModel;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.checkin_guest.callback.GetLodgingImg;
import com.example.myapplication.checkin_guest.callback.GetLodgingTitleImg;
import com.example.myapplication.checkin_guest.callback.SearchListener;
import com.example.myapplication.checkin_guest.model.LodgingItem;
import com.example.myapplication.checkin_guest.view.activity.SearchResultActivity;
import com.example.myapplication.checkin_guest.viewModel.Executor.FireStorageExcutor;
import com.example.myapplication.checkin_guest.viewModel.Executor.FireStoreExcutor;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class SearchResultViewModel extends ViewModel {
    private final String TAG = SearchResultActivity.class.getSimpleName();
    private WeakReference<Activity> mActivity;

    //FireStore 처리 관련 변수
    private FireStoreExcutor fireStoreExcutor = new FireStoreExcutor();
    private FireStorageExcutor fireStorageExcutor = new FireStorageExcutor();

    //숙소 검색 결과가 담기는 list
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<ArrayList<LodgingItem>> lodgingMLList = new MutableLiveData<>();
    private ArrayList<LodgingItem> lodgingList = new ArrayList<>();

    public SearchResultViewModel() {

    }

    //activity setting
    public void setParentContext(Activity parentContext) {
        mActivity = new WeakReference<>(parentContext);
    }

    private void finishActivity() {
        if (mActivity.get() != null)
            mActivity.get().finish();
    }

    /*      검색 처리 코드   */

    public MutableLiveData<ArrayList<LodgingItem>> getLodgingMLList() {
        return this.lodgingMLList;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return this.isLoading;
    }

    public void setSearchListener() {
        fireStoreExcutor.setSearchListener(getSearchListener());
    }

    private SearchListener getSearchListener() {
        return querySnapshot -> {
            if (querySnapshot.size() != 0) {
                for (QueryDocumentSnapshot result : querySnapshot) {
                    try{
                        LodgingItem lodgingItem = new LodgingItem();
                        lodgingItem.setUid((String) result.get("uid"));
                        lodgingItem.setAcceptance((long) result.get("acceptance"));
                        lodgingItem.setAddress((String) result.get("address"));
                        lodgingItem.setCity((String) result.get("city"));
                        lodgingItem.setCnt_bad((long) result.get("cnt_bed"));
                        lodgingItem.setCnt_badRoom((long) result.get("cnt_bedRoom"));
                        lodgingItem.setCnt_toilet((long) result.get("cnt_toilet"));
                        lodgingItem.setConvenience((ArrayList<String>) result.get("convenience"));
                        lodgingItem.setFare((long) result.get("fare"));
                        lodgingItem.setGeoPoint((GeoPoint) result.get("geopoint"));
                        lodgingItem.setHost_uid(String.valueOf(result.get("host_uid")));
                        lodgingItem.setImg_path((ArrayList<String>) result.get("img_path"));
                        lodgingItem.setIntroductory((String) result.get("introductory"));
                        lodgingItem.setName((String) result.get("name"));
                        lodgingItem.setNfc_distance((boolean) result.get("nfc_distance"));
                        lodgingItem.setReservation_time_list((ArrayList<String>) result.get("reservation_time_list"));
                        lodgingItem.setReview((ArrayList<String>) result.get("review"));
                        lodgingItem.setState((String) result.get("state"));
                        lodgingItem.setTitle_image_path((String) result.get("title_image_path"));
                        lodgingItem.setType((long) result.get("type"));
                        lodgingItem.setUid((String) result.get("uid"));
                        Log.d(TAG, lodgingItem.toString());
                        lodgingList.add(lodgingItem);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                setLodgingTitleImgCallBack();
                getLodgingTitleImg();
            }else{
                isLoading.setValue(false);
            }
        };
    }

    // city만 설정된 검색 조건
    public void search(String city) {
        Log.d(TAG, "search");
        isLoading.setValue(true);
        this.lodgingList.clear();
        fireStoreExcutor.getSearchLodging(city);
    }

    private void setLodgingTitleImgCallBack() {
        fireStorageExcutor.setGetLodgingTitleImg(getLodgingTitleImgCallBack());
    }

    private void getLodgingImg() {
        fireStorageExcutor.getLodgingImage(this.lodgingList);
    }
    private GetLodgingImg getLodgingImgCallBack() {
        return () -> {
            Log.d(TAG, "successGetLodgingTitleImg");
            lodgingMLList.setValue(lodgingList);
            isLoading.setValue(false);
        };
    }
    private void setLodgingImgCallBack() {
        fireStorageExcutor.setGetLodgingTitleImg(getLodgingTitleImgCallBack());
    }

    private void getLodgingTitleImg() {
        fireStorageExcutor.getLodgingImage(this.lodgingList);
    }



    private GetLodgingTitleImg getLodgingTitleImgCallBack() {
        return () -> {
            Log.d(TAG, "successGetLodgingTitleImg");
            lodgingMLList.setValue(lodgingList);
            isLoading.setValue(false);
        };
    }


}
