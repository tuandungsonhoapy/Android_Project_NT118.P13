package com.example.androidproject.features.setting.presentation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidproject.R;
import com.example.androidproject.core.utils.NavigationUtils;
import com.example.androidproject.features.admin.presentation.AdminHomeActivity;
import com.example.androidproject.features.auth.presentation.LoginActivity;
import com.example.androidproject.features.cart.presentation.CartActivity;
import com.example.androidproject.features.setting.usecase.SettingUseCase;
import com.example.androidproject.features.voucher.presentation.VoucherActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.net.URL;

public class SettingFragment extends Fragment {

    // views
    private ImageButton ibProfileSetting;
    private TextView name, email;
    private ImageView photoURL;
    LinearLayout llAdminMenu, addressLayout, cartLayout, orderLayout, voucherLayout, informationLayout;
    Button btnLogout;

    //others
    private SettingUseCase settingUseCase = new SettingUseCase();
    private Bundle userDataBundle;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userDataBundle = getArguments();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        init(view);

        return view;
    }

    private void init(View view) {
        initViews(view);

        loadNameAndEmail();
        loadProfilePhoto();

        setupClickListeners();
    }

    private void initViews(View view) {
        ibProfileSetting = view.findViewById(R.id.ib_setting_profile_edit);
        name = view.findViewById(R.id.tv_setting_profile_name);
        email = view.findViewById(R.id.tv_setting_profile_tier);
        photoURL = view.findViewById(R.id.iv_setting_profile);
        btnLogout = view.findViewById(R.id.btn_setting_profile_logout);

        llAdminMenu = view.findViewById(R.id.ll_admin_manager);
        addressLayout = view.findViewById(R.id.ll_setting_profile_address);
        cartLayout = view.findViewById(R.id.ll_setting_profile_cart);
        orderLayout = view.findViewById(R.id.ll_setting_profile_order);
        voucherLayout = view.findViewById(R.id.ll_setting_profile_voucher);
        informationLayout = view.findViewById(R.id.ll_setting_profile_notification);
    }

    private void setupClickListeners() {
        ibProfileSetting.setOnClickListener(v -> NavigationUtils.navigateTo(getActivity(), ProfileSettingActivity.class, false));

        // admin menu
        if (userDataBundle != null && userDataBundle.getBoolean("isAdmin", false)) {
            llAdminMenu.setVisibility(View.VISIBLE);
            llAdminMenu.setOnClickListener(v -> NavigationUtils.navigateTo(getActivity(), AdminHomeActivity.class));
        }

        // Address Layout
        addressLayout.setOnClickListener(v -> NavigationUtils.navigateTo(getActivity(), AddressSettingActivity.class, false));

        // Cart Layout
        cartLayout.setOnClickListener(v -> NavigationUtils.navigateTo(getActivity(), CartActivity.class, false));

        // Order Layout
        orderLayout.setOnClickListener(v -> NavigationUtils.navigateTo(getActivity(), OrderSettingActivity.class, false));

        // Voucher Layout
        voucherLayout.setOnClickListener(v -> NavigationUtils.navigateTo(getActivity(), VoucherActivity.class, false));

        // Notification Layout
        informationLayout.setOnClickListener(v -> NavigationUtils.navigateTo(getActivity(), NotificationSettingActivity.class, false));

        //Logout
        btnLogout.setOnClickListener(v -> handleLogout());
    }

    private void loadNameAndEmail() {
        if (userDataBundle != null) {
            name.setText(userDataBundle.getString("name"));
            email.setText(userDataBundle.getString("email"));
        }
    }

    private void loadProfilePhoto() {
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
    }

    private void handleLogout() {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}