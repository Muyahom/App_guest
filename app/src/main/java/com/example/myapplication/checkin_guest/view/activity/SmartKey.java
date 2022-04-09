package com.example.myapplication.checkin_guest.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.ActivitySmartKeyBinding;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class SmartKey extends AppCompatActivity {
    private final String TAG = SmartKey.class.getSimpleName();
    // 실제로 태그에 성공적으로 연결했는지 여부에 따라 표시될 메시지를 추가
    public static final String Error_Detected = "No NFC Tag Detected";
    public static final String Write_Success = "Text Written Successfully";
    public static final String Write_Error = "Error during Writing, Try Again!";
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter writeTagFilters[];
    boolean writeMode;
    Tag myTag;
    Context context;
    TextView txt_contents;
    private ActivitySmartKeyBinding activitySmartKeyBinding;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySmartKeyBinding = DataBindingUtil.setContentView(this, R.layout.activity_smart_key);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Logger.d(TAG + "NFC is not available");
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
        }

        context = this;

        activitySmartKeyBinding.switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //체크된 상태로 만들 시 코드
                    Log.d(TAG, "ON");
                    writeModeOn();
                    try {
                        if (myTag == null) { // 태깅된 것이 없다면,
                            Toast.makeText(context, Error_Detected, Toast.LENGTH_SHORT).show();
                        } else {
                            // 태깅된것이 있다면
                            write("PlainText|" + "smartkey", myTag);
                            Toast.makeText(context, Write_Success, Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        Toast.makeText(context, Write_Error, Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (FormatException e) {
                        Toast.makeText(context, Write_Error, Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                } else {
                    //체크된 상태 취소 시 코드
                    Log.d(TAG, "OFF");
                    writeModeOff();
                }
            }
        });

        readFromIntent(getIntent());
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_MUTABLE);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writeTagFilters = new IntentFilter[]{tagDetected};

    }

    private void readFromIntent(Intent intent) {
        Log.d(TAG, "readFromIntent");
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            //
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs = null;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }
            buildTagViews(msgs);
        }
    }

    private void buildTagViews(NdefMessage[] msgs) {
        if (msgs == null || msgs.length == 0) return;
        Log.d(TAG, "buildTagViews");
        String text = "";
//        String tagId = new String(msgs[0].getRecords()[0].getType());
        byte[] payload = msgs[0].getRecords()[0].getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16"; // Get the Text Encoding
        int langaugeCodeLength = payload[0] & 0063; // Get the Langauge Code, e.g "en"
        // String langaugeCode = new String(payload, 1, languageCodeLength, "US-ASCII");

        try {
            //Get the Text
            text = new String(payload, langaugeCodeLength + 1, payload.length - langaugeCodeLength - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncoding", e.toString());
        }

        txt_contents.setText("NFC Content: " + text);
    }

    private void write(String text, Tag tag) throws IOException, FormatException {
        Log.d(TAG, "write");
        NdefRecord[] records = {createRecord(text)};
        NdefMessage message = new NdefMessage(records);
        Log.d(TAG, "write : "+tag.toString());
        // Get an instance of Ndef for the tag.
        NfcA nfcA = NfcA.get(tag);

//        NFC
//        nfcA.connect();
//        nfcA.transceive(message);
//        Ndef ndef = Ndef.get(tag);
//        // Enable I/O
//        ndef.connect();
//        // Write the message
//        ndef.writeNdefMessage(message);
        // Close the connection
//        ndef.close();
    }

    private NdefRecord createRecord(String text) throws UnsupportedEncodingException {
        Log.d(TAG, "createRecord");
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
//        System.arraycopy(langBytes, 0, payload, 1 + langLength, textLength);=
        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);
        return recordNFC;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent");
        this.intent = intent;
        setIntent(intent);
        readFromIntent(intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Log.d(TAG, "myTag 초기화");
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        }
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
        writeModeOn();
        try {
            if (myTag != null) {
                Log.d(TAG, myTag.toString());
                write("PlainText|" + "smartkey", myTag);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
    }

    private void writeModeOn() {
        writeMode = true;
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, writeTagFilters, null);
    }

    private void writeModeOff() {
        writeMode = false;
        nfcAdapter.disableForegroundDispatch(this);
    }
}