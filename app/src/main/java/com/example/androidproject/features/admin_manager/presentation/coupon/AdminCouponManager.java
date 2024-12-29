package com.example.androidproject.features.admin_manager.presentation.coupon;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.admin_manager.data.datasource.DummyCouponData;
import com.example.androidproject.features.admin_manager.data.model.CouponModel;
import com.example.androidproject.features.admin_manager.presentation.AdminBaseManagerLayout;
import com.example.androidproject.features.admin_manager.presentation.widgets.ButtonAdminFilterAdapter;
import com.example.androidproject.features.admin_manager.presentation.widgets.CouponAdapter;
import com.example.androidproject.features.voucher.data.model.VoucherModel;
import com.example.androidproject.features.voucher.usecase.VoucherUseCase;

import java.util.Arrays;
import java.util.List;

public class AdminCouponManager extends AdminBaseManagerLayout {

    private RecyclerView rvBtnAdminFilter, rvCouponList;
    private Button btnAddVoucher;
    private CouponAdapter couponAdapter;
    private List<VoucherModel> couponList;
    private VoucherUseCase voucherUseCase = new VoucherUseCase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Khuyến mãi");

        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(R.layout.admin__coupon_manager, findViewById(R.id.content_container), true);

        btnAddVoucher = findViewById(R.id.btnAddVoucher);
        btnAddVoucher.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddVoucherActivity.class);
            startActivityForResult(intent, 1);
        });

        fetchVouchers();
    }

    private void fetchVouchers() {
        voucherUseCase.getVoucher()
                .thenAccept(r -> {
                    if(r.isRight()) {
                        couponList = r.getRight();
                        rvCouponList = findViewById(R.id.rv_coupon_list);
                        rvCouponList.setLayoutManager(new LinearLayoutManager(this));
                        couponAdapter = new CouponAdapter(couponList, this, this);
                        rvCouponList.setAdapter(couponAdapter);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK) {
            boolean voucherAdded = data.getBooleanExtra("added_voucher", false);
            boolean voucherUpdated = data.getBooleanExtra("updated_voucher", false);
            boolean voucherDeleted = data.getBooleanExtra("deleted_voucher", false);
            if(voucherAdded || voucherUpdated || voucherDeleted) {
                fetchVouchers();
            }
        }
    }
}