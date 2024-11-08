package com.example.androidproject.features.admin_manager.presentation.coupon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.admin_manager.data.datasource.DummyCouponData;
import com.example.androidproject.features.admin_manager.data.model.CouponModel;
import com.example.androidproject.features.admin_manager.presentation.AdminBaseManagerLayout;
import com.example.androidproject.features.admin_manager.presentation.widgets.ButtonAdminFilterAdapter;
import com.example.androidproject.features.admin_manager.presentation.widgets.CouponAdapter;

import java.util.Arrays;
import java.util.List;

public class AdminCouponManager extends AdminBaseManagerLayout {

    private RecyclerView rvBtnAdminFilter, rvCouponList;
    private CouponAdapter couponAdapter;
    private List<CouponModel> couponList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Khuyến mãi");

        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(R.layout.admin__coupon_manager, findViewById(R.id.content_container), true);

        rvBtnAdminFilter = findViewById(R.id.rv_btn_admin_filter);
        rvBtnAdminFilter.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        List<String> buttonNames = Arrays.asList("Tất cả", "Đang diễn ra", "Sắp diễn ra", "Hết hạn");
        ButtonAdminFilterAdapter adminFilterAdapter = new ButtonAdminFilterAdapter(buttonNames, name -> {
            handleFilterButtonClick(name);
        });
        rvBtnAdminFilter.setAdapter(adminFilterAdapter);

        couponList = DummyCouponData.generateDummyCoupons();

        rvCouponList = findViewById(R.id.rv_coupon_list);
        rvCouponList.setLayoutManager(new LinearLayoutManager(this));
        couponAdapter = new CouponAdapter(couponList, this);
        rvCouponList.setAdapter(couponAdapter);
    }

    private void handleFilterButtonClick(String name) {
        switch (name) {
            case "Tất cả":
                Toast.makeText(this, "Bạn đã chọn: " + name, Toast.LENGTH_SHORT).show();
                break;
            case "Đang diễn ra":
                Toast.makeText(this, "Bạn đã chọn: " + name, Toast.LENGTH_SHORT).show();
                break;
            case "Sắp diễn ra":
                Toast.makeText(this, "Bạn đã chọn: " + name, Toast.LENGTH_SHORT).show();
                break;
            case "Hết hạn":
                Toast.makeText(this, "Bạn đã chọn: " + name, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}