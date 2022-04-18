
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.checkin_guest.R;
import com.example.myapplication.checkin_guest.databinding.FragMyInfoBinding;
import com.example.myapplication.checkin_guest.view.activity.AccountActivity;
import com.example.myapplication.checkin_guest.view.activity.AlarmActivity;
import com.example.myapplication.checkin_guest.view.activity.HelpActivity;
import com.example.myapplication.checkin_guest.view.activity.SmartKey;

public class Frag_myInfo extends Fragment {
    private FragMyInfoBinding fragMyInfoBinding = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragMyInfoBinding = DataBindingUtil.inflate(inflater, R.layout.frag_my_info, container, false);

        fragMyInfoBinding.txtReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SmartKey.class);
                startActivity(intent);
            }
        });
        //계정관리정보
        fragMyInfoBinding.txtAccountManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AccountActivity.class);
                startActivity(intent);
            }
        });

        fragMyInfoBinding.txtAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AlarmActivity.class);
                startActivity(intent);
            }
        });

        fragMyInfoBinding.txtHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                startActivity(intent);
            }
        });

        return fragMyInfoBinding.getRoot();
    }
}