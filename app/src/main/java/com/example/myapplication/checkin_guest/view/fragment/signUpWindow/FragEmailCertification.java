package com.example.myapplication.checkin_guest.view.fragment.signUpWindow;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.FragEmailCertificationBinding;
import com.example.myapplication.checkin_guest.model.GMailSender;
import com.example.myapplication.checkin_guest.view.activity.SignUpActivity;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public class FragEmailCertification extends Fragment {

    private final String TAG = "EmailCertification";
    private FragEmailCertificationBinding fragEmailcertificationBinding;

    private MainHandler mainHandler;
    private String GmailCode;

    private static int value;
    private int mailSend = 0;

    private final String GMAIL = "wsm9175@gmail.com";
    private final String PWD = "fdxwjxrjajntcuok";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragEmailcertificationBinding = DataBindingUtil.inflate(inflater, R.layout.frag_email_certification, container, false);
        init();

        //이메일 인증 부분
        //이메일 시간초가 흐르는데 이때 인증을 마치지 못하면 인증코드를 지운다.
        fragEmailcertificationBinding.btnSend.setOnClickListener(v -> {
            MailThread mailThread = new MailThread();
            mailThread.start();

            if (mailSend == 0) {
                value = 180;
                BackgrounThread backgrounThread = new BackgrounThread();
                backgrounThread.start();
                mailSend += 1;
            } else {
                value = 180;
            }

            fragEmailcertificationBinding.edtCertificationCode.setVisibility(View.VISIBLE);
            fragEmailcertificationBinding.txtTimer.setVisibility(View.VISIBLE);

            mainHandler = new MainHandler();
        });

        ((SignUpActivity)getActivity()).getBtn_next().setOnClickListener(v -> {
            Log.d(TAG, GmailCode + " " + fragEmailcertificationBinding.edtCertificationCode.getText().toString().trim());
            if (fragEmailcertificationBinding.edtCertificationCode.getText().toString().equals(GmailCode)) {
                Toast.makeText(getActivity(), "이메일 인증 성공", Toast.LENGTH_SHORT).show();
                ((SignUpActivity)getActivity()).setMainCheck3();
            } else {
                Log.d(TAG, fragEmailcertificationBinding.edtCertificationCode.getText().toString() + " " + GmailCode);
                Toast.makeText(getActivity(), "인증번호를 확인해주세요", Toast.LENGTH_SHORT).show();
            }
        });

        return fragEmailcertificationBinding.getRoot();
    }

    public void init() {
        fragEmailcertificationBinding.edtCertificationCode.setVisibility(View.GONE);
        fragEmailcertificationBinding.txtTimer.setVisibility(View.GONE);
        fragEmailcertificationBinding.txtEmail.setText(((SignUpActivity)getActivity()).getEmail());
    }

    //메일 보내는 쓰레드
    class MailThread extends Thread {
        public void run() {
            GMailSender gMailSender = new GMailSender(GMAIL, PWD);
            //GMailSender.sendMail(제목, 본문내용, 받는사람);
            //인증코드
            GmailCode = gMailSender.getEmailCode();
            try {
                gMailSender.sendMail("회원가입 이메일 인증", GmailCode, fragEmailcertificationBinding.txtEmail.getText().toString());
            } catch (SendFailedException e) {

            } catch (MessagingException e) {
                System.out.println("인터넷 문제" + e);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //시간초가 카운트 되는 쓰레드
    class BackgrounThread extends Thread {
        //180초는 3분
        //메인 쓰레드에 value를 전달하여 시간초가 카운트다운 되게 한다.
        public void run() {
            //180초 보다 밸류값이 작거나 같으면 계속 실행시켜라
            while (true) {
                value -= 1;
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
                Message message = mainHandler.obtainMessage();
                //메세지는 번들의 객체 담아서 메인 핸들러에 전달한다.
                Bundle bundle = new Bundle();
                bundle.putInt("value", value);
                message.setData(bundle);
                //핸들러에 메세지 객체 보내기기
                mainHandler.sendMessage(message);
                if (value <= 0) {
                    GmailCode = "";
                    break;
                }
            }
        }
    }

    //쓰레드로부터 메시지를 받아 처리하는 핸들러
    //메인에서 생성된 핸들러만이 Ui를 컨트롤 할 수 있다.
    class MainHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int min, sec;

            Bundle bundle = message.getData();
            int value = bundle.getInt("value");

            min = value / 60;
            sec = value % 60;
            //초가 10보다 작으면 앞에 0이 더 붙어서 나오도록한다.
            if (sec < 10) {
                //텍스트뷰에 시간초가 카운팅
                fragEmailcertificationBinding.txtTimer.setText("0" + min + " : 0" + sec);
            } else {
                fragEmailcertificationBinding.txtTimer.setText("0" + min + " : " + sec);
            }
        }
    }
}