package com.example.myapplication.checkin_guest.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;

import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.ActivitySmartKeyBinding;
import com.example.myapplication.checkin_guest.util.Util;
import com.example.myapplication.checkin_guest.viewModel.SmartKeyViewModel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/*                    설계
   SmartKey 액티비티는 사용자 예약 숙소 목록을 참조한다,
   SmartKey 액티비티는 예약 기간 동안만 활성화 할 수 있다.
   SmartKey 액티비티는 ViewModel을 가진다.
                                                */

public class SmartKey extends AppCompatActivity {
    private final String TAG = SmartKey.class.getSimpleName();
    private SmartKeyViewModel smartKeyViewModel;

    //뒤로가기 종료
    private long backKeyPressedTime = 0;

    // 실제로 태그에 성공적으로 연결했는지 여부에 따라 표시될 메시지를 추가
    public static final String Write_Success = "성공적으로 태깅되었습니다.";
    public static final String Write_Failed = "태깅에 실패했습니다.";


    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter writeTagFilters[];
    private boolean writeMode;
    private Tag myTag;
    private Context context;
    private ActivitySmartKeyBinding activitySmartKeyBinding;
    private Intent intent;

    private Thread vibeThread;

    @Override
    public void onBackPressed() {
        // 기존 뒤로가기 버튼의 기능을 막기위해 주석처리 또는 삭제
        // super.onBackPressed();
        if (writeMode) {
            activitySmartKeyBinding.switchBtn.setChecked(false);
        } else if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 스마트키가 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        } else if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            vibeOff();
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "on Create");
        activitySmartKeyBinding = DataBindingUtil.setContentView(this, R.layout.activity_smart_key);
        context = this;

        // 처리할 UI 관련 소스코드, 상태바 투명 및 바텀내비게이션 높이에 따른 레이아웃 페딩 설정
        Util.transparency_statusBar(this);
        activitySmartKeyBinding.motionLayout.setPadding(0, 0, 0, Util.getBottomNavigationHeight(getApplicationContext()));

        //ViewModel 연결
        smartKeyViewModel = new ViewModelProvider(this, new ViewModelProvider
                .AndroidViewModelFactory(getApplication())).get(SmartKeyViewModel.class);
        smartKeyViewModel.setParentContext(this);

        // nfc adapter 초기화
        smartKeyViewModel.initAdapter();

        activitySmartKeyBinding.switchBtn.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                this.writeMode = true;
                switchIsOn();
            } else {
                this.writeMode = false;
                switchIsOff();
            }
        });


//        readFromIntent(getIntent());
        //Nfc 감지관련 세팅 메서드
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_MUTABLE);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writeTagFilters = new IntentFilter[]{tagDetected};
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent");
        this.intent = intent;
        setIntent(intent);
//        readFromIntent(intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Log.d(TAG, "myTag 초기화");
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        }
        myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        writeModeOff();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");

        smartKeyViewModel.getNfcAdpater().observe(this, data -> {
            Log.d(TAG, "NfcAdapter observe");
            if (smartKeyViewModel.getNfcAdpater() == null) {
                Log.d(TAG, "NfcAdapter == null");
                return;
            }
            Log.d(TAG, "NfcAdapter not null");
            this.nfcAdapter = smartKeyViewModel.getNfcAdpater().getValue();
        });
        //switch가 켜져있는 상태였으면 인식된 태그에 write 실시
        if (writeMode) {
            activitySmartKeyBinding.switchBtn.setChecked(true);
            try {
                if (myTag != null) {
                    Log.d(TAG, myTag.toString());
//                    write("941399", myTag);
                    writeNdef("9413", myTag);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FormatException e) {
                e.printStackTrace();
            }
            //switch가 꺼져있는 상태였으면 switch off
        } else {
            activitySmartKeyBinding.switchBtn.setChecked(false);
        }
    }

    private void switchIsOn() {
        //체크된 상태로 만들 시 코드
        Log.d(TAG, "ON");
        writeMode = true;
        writeModeOn();
        activitySmartKeyBinding.linearLodgingInfo.setVisibility(View.INVISIBLE);
        activitySmartKeyBinding.txtTitle.setVisibility(View.INVISIBLE);

        //자연스러운 배경전환
        @SuppressLint("ResourceAsColor") ColorDrawable[] colorDrawables = {new ColorDrawable(R.color.remote_color), new ColorDrawable(Color.BLACK)};
        activitySmartKeyBinding.motionLayout.setBackground(null);
        TransitionDrawable transitionDrawable = new TransitionDrawable(colorDrawables);
        activitySmartKeyBinding.motionLayout.setBackground(transitionDrawable);
        transitionDrawable.startTransition(1000);

        //진동 수행
        vibeThread = new Thread(new Runnable() {
            public void run() {
                try {
                    for (; ; ) {
                        vibeOn();
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        vibeThread.start();

    }

    private void switchIsOff() {
        //체크된 상태 취소 시 코드
        Log.d(TAG, "OFF");
        writeMode = false;
        writeModeOff();
        activitySmartKeyBinding.linearLodgingInfo.setVisibility(View.VISIBLE);
        activitySmartKeyBinding.txtTitle.setVisibility(View.VISIBLE);
        activitySmartKeyBinding.motionLayout.setBackground(getDrawable(R.color.white));
        activitySmartKeyBinding.motionLayout.setBackground(getDrawable(R.drawable.gradient_main_logo));
        vibeOff();
    }

    private void writeModeOn() {
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, writeTagFilters, null);
    }

    private void writeModeOff() {
        nfcAdapter.disableForegroundDispatch(this);
    }

    private void write(String text, Tag tag) throws IOException, FormatException {
        Log.d(TAG, "write");
        writeTag(tag, text);
    }

    private void writeNdef(String text, Tag tag) throws IOException, FormatException {
//        NfcF nfcF = NfcF.get(tag);
//        nfcF.connect();

        Log.d(TAG, "write");
        // Get an instance of Ndef for the tag.
        Ndef ndef = Ndef.get(tag);
        try {
            // Enable I/O
            ndef.connect();
            // Write the message
            Log.d(TAG, "ndef is Connected? : " + ndef.isConnected());
            Log.d(TAG, "ndef is writable? "+ndef.isWritable());
            Log.d(TAG, "ndef is null? : " + (ndef==null));
            ndef.writeNdefMessage(getTextAsNdef());
//            activitySmartKeyBinding.switchBtn.setChecked(false);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "잘못된 태깅입니다.", Toast.LENGTH_SHORT).show();
            activitySmartKeyBinding.switchBtn.setChecked(false);
        } finally {
            // Close the connection
//            activitySmartKeyBinding.switchBtn.setChecked(false);
            ndef.close();
        }
    }

    private NdefMessage getTextAsNdef() {
        String mWriteText = "9413";
        byte[] textBytes = mWriteText.getBytes();

        NdefRecord textRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
                "text/plain".getBytes(),
                new byte[] {},
                textBytes);
        return new NdefMessage(new NdefRecord[] {textRecord});

    }

    private NdefRecord createRecord(String text) throws UnsupportedEncodingException {
        String lang = "en";
        byte[] textBytes = text.getBytes();
        byte[] langBytes = lang.getBytes("US-ASCII");
        int langLength = langBytes.length;
        int textLength = textBytes.length;
        byte[] payload = new byte[1 + langLength + textLength];

        // set status byte (see NDEF spec for actual bits)
        payload[0] = (byte) langLength;

        // copy langbytes and textbytes into payload
        System.arraycopy(langBytes, 0, payload, 1, langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);

        return recordNFC;
    }


    public void writeTag(Tag tag, String tagText) {
        MifareUltralight ultralight = MifareUltralight.get(tag);
        try {
            ultralight.connect();
            ultralight.writePage(4, tagText.getBytes(Charset.forName("US-ASCII")));
//            ultralight.writePage(5, tagText.getBytes(Charset.forName("US-ASCII")));
//            ultralight.writePage(6, tagText.getBytes(Charset.forName("US-ASCII")));
//            ultralight.writePage(7, tagText.getBytes(Charset.forName("US-ASCII")));
            Toast.makeText(this, Write_Success, Toast.LENGTH_SHORT).show();
            activitySmartKeyBinding.switchBtn.setChecked(false);
        } catch (IOException e) {
            Log.e(TAG, "IOException while writing MifareUltralight...", e);
        } catch (NullPointerException e) {
            // 잘못된 nfc에 태깅한 경우
            e.printStackTrace();
            Toast.makeText(this, Write_Failed, Toast.LENGTH_SHORT).show();
            activitySmartKeyBinding.switchBtn.setChecked(false);
            switchIsOff();
        } finally {
            try {
                ultralight.close();
                activitySmartKeyBinding.switchBtn.setChecked(false);
            } catch (IOException e) {
                Log.e(TAG, "IOException while closing MifareUltralight...", e);
            } catch (Exception e) {

            }
        }

    }

    private void vibeOn() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE)); // 0.5초간 진동
    }

    private void vibeOff() {
        //진동 끄기
        if (vibeThread != null) {
            vibeThread.interrupt();
        }
    }
}