package com.example.androidproject.features.admin_manager.presentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidproject.MainActivity;
import com.example.androidproject.R;
import com.example.androidproject.core.credential.UserPreferences;
import com.example.androidproject.features.admin_manager.presentation.admin.AdminManagerAdminActivity;
import com.example.androidproject.features.admin_manager.presentation.brand.AdminBrandManagerActivity;
import com.example.androidproject.features.admin_manager.presentation.category.AdminCategoryManagerActivity;
import com.example.androidproject.features.admin_manager.presentation.coupon.AdminCouponManager;
import com.example.androidproject.features.admin_manager.presentation.product.AdminProductManagementActivity;
import com.example.androidproject.features.admin_manager.presentation.user.UserManagerAdminActivity;
import com.example.androidproject.features.admin_manager.presentation.order.AdminOrderManagerActivity;

public class AdminManagerFragment extends Fragment {
    private UserPreferences userPreferences;
    private TextView tvUserName;


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

        tvUserName = view.findViewById(R.id.tvUserName);

        userPreferences = new UserPreferences(getContext());
        tvUserName.setText(userPreferences.getUserDataByKey(UserPreferences.KEY_FIRST_NAME).toString());

        startActivityOnClick(view, R.id.item_order_manager, AdminOrderManagerActivity.class);
        startActivityOnClick(view, R.id.item_user_manager, UserManagerAdminActivity.class);
        startActivityOnClick(view, R.id.item_admin_manager, AdminManagerAdminActivity.class);
        startActivityOnClick(view, R.id.item_product_manager, AdminProductManagementActivity.class);
        startActivityOnClick(view, R.id.item_category_manager, AdminCategoryManagerActivity.class);
        startActivityOnClick(view, R.id.item_voucher_manager, AdminCouponManager.class);
        startActivityOnClick(view, R.id.item_brand_manager, AdminBrandManagerActivity.class);

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