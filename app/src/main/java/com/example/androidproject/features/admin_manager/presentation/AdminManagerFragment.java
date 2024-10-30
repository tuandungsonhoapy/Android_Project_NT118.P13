package com.example.androidproject.features.admin_manager.presentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.androidproject.R;
import com.example.androidproject.features.admin_manager.presentation.pages.AdminCouponManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminManagerFragment extends Fragment {

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
        LinearLayout itemOrder = view.findViewById(R.id.item_order_manager);
        itemOrder.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AdminOrderManagerActivity.class);
            startActivity(intent);
        });

        LinearLayout itemProduct = view.findViewById(R.id.item_product_manager);

        LinearLayout itemUser = view.findViewById(R.id.item_user_manager);
        itemUser.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AdminUserManagerActivity.class);
            startActivity(intent);
        });

        LinearLayout itemCategory = view.findViewById(R.id.item_category_manager);

        startActivityOnClick(view, R.id.item_voucher_manager, AdminCouponManager.class);

        LinearLayout itemAdmin = view.findViewById(R.id.item_admin_manager);


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