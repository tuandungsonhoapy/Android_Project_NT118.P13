package com.example.androidproject.features.setting.presentation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidproject.R;
import com.example.androidproject.features.admin.presentation.AdminHomeActivity;
import com.example.androidproject.features.auth.presentation.LoginActivity;
import com.example.androidproject.features.cart.presentation.CartActivity;
import com.example.androidproject.features.setting.usecase.SettingUseCase;
import com.example.androidproject.features.voucher.presentation.VoucherActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SettingUseCase settingUseCase = new SettingUseCase();
    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        ImageView photoURL = view.findViewById(R.id.iv_setting_profile);
        TextView name = view.findViewById(R.id.tv_setting_profile_name);
        TextView email = view.findViewById(R.id.tv_setting_profile_tier);

        name.setText(settingUseCase.getUser().getDisplayName());
        email.setText(settingUseCase.getUser().getEmail());

        new Thread(new Runnable() {
            public void run() {
                try {
                    URL url = new URL(settingUseCase.getUser().getPhotoUrl());
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    photoURL.post(new Runnable() {
                        public void run() {
                            photoURL.setImageBitmap(bmp);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), AdminHomeActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        // Address Layout
        LinearLayout addressLayout = view.findViewById(R.id.ll_setting_profile_address);
        addressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(getActivity(), AddressSettingActivity.class);
                 startActivity(intent);
            }
        });

        // Cart Layout
        LinearLayout cartLayout = view.findViewById(R.id.ll_setting_profile_cart);
        cartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(getActivity(), CartActivity.class);
                 startActivity(intent);
            }
        });

        // Order Layout
        LinearLayout orderLayout = view.findViewById(R.id.ll_setting_profile_order);
        orderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(getActivity(), OrderSettingActivity.class);
                 startActivity(intent);
            }
        });

        // Voucher Layout
        LinearLayout voucherLayout = view.findViewById(R.id.ll_setting_profile_voucher);
        voucherLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(getActivity(), VoucherActivity.class);
                 startActivity(intent);
            }
        });

        // Notification Layout
        LinearLayout informationLayout = view.findViewById(R.id.ll_setting_profile_notification);
        informationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(getActivity(), NotificationSettingActivity.class);
                 startActivity(intent);
            }
        });

        //Logout
        Button buttonLogout = view.findViewById(R.id.btn_setting_profile_logout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }
}