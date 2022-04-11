package com.example.myapplication.checkin_guest.viewModel;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.orhanobut.logger.Logger;

import java.lang.ref.WeakReference;

public class SmartKeyViewModel extends ViewModel {
    private final String TAG = LoginViewModel.class.getSimpleName();
    private WeakReference<Activity> mActivityRef;

    private MutableLiveData<NfcAdapter> nfcAdpater = new MutableLiveData<>();
    private MutableLiveData<Tag> tag = new MutableLiveData<>();
    private MutableLiveData<Boolean> writeMode = new MutableLiveData<>();

    // activity setting
    public void setParentContext(Activity parentContext){
        mActivityRef = new WeakReference<>(parentContext);
    }

    // nfcAdapter setting
    public void initAdapter(){
        nfcAdpater.setValue(NfcAdapter.getDefaultAdapter(mActivityRef.get()));
        if (nfcAdpater.getValue() == null) {
            Logger.d(TAG + "NFC is not available");
            Toast.makeText(mActivityRef.get(), "NFC를 활성화 해주세요.", Toast.LENGTH_LONG).show();
        }
    }

    public MutableLiveData<NfcAdapter> getNfcAdpater() {
        return nfcAdpater;
    }

    public MutableLiveData<Tag> getTag() {
        return tag;
    }

    public MutableLiveData<Boolean> getIsSwitchOn() {
        return writeMode;
    }
}
