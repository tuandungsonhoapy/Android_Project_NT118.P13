package com.example.androidproject.features.admin_manager.presentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.androidproject.MainActivity;
import com.example.androidproject.R;
import com.example.androidproject.features.admin_manager.presentation.pages.AdminCouponManager;

public class AdminManagerFragment extends Fragment {

    private ImageView btnGoToShop;
    public AdminManagerFragment() {
        // Required empty public constructor
    }

    public static AdminManagerFragment newInstance() {
        return new AdminManagerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_manager, container, false);

        // setup click listeners
        btnGoToShop = view.findViewById(R.id.ivShop);
        btnGoToShop.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        startActivityOnClick(view, R.id.item_order_manager, AdminOrderManagerActivity.class);
        startActivityOnClick(view, R.id.item_user_manager, AdminUserManagerActivity.class);
        startActivityOnClick(view, R.id.item_admin_manager, AdminAdminManagerActivity.class);
        startActivityOnClick(view, R.id.item_product_manager, AdminProductManagementActivity.class);
        startActivityOnClick(view, R.id.item_category_manager, AdminCategoryManagerActivity.class);
        startActivityOnClick(view, R.id.item_voucher_manager, AdminCouponManager.class);

        return view;
    }

    private void startActivityOnClick(View view, int layoutId, Class<?> activityClass) {
        LinearLayout item = view.findViewById(layoutId);
        item.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), activityClass);
            startActivity(intent);
        });
    }
}