package com.example.androidproject.features.admin_manager.presentation.pages;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.R;
import com.example.androidproject.core.utils.JsonUtil;
import com.example.androidproject.features.admin_manager.data.model.CouponModel;

import java.text.NumberFormat;
import java.util.Locale;

public class AdminCouponDetail extends AppCompatActivity {
    private TextView tvCouponId, tvCouponName, tvCouponQuantity, tvCouponStartDate, tvCouponEndDate, tvCouponType, tvCouponValue;
    private ImageButton btnBack;
    private Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin__coupon_detail);
        setupWindowInsets();

        initializeViews();
        setupButtons();

        // get data and render
        String couponJson = getIntent().getStringExtra("coupon");
        if (couponJson != null) {
            CouponModel coupon = JsonUtil.jsonToObject(couponJson, CouponModel.class);
            renderData(coupon);
        }
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.admin__coupon_detail), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initializeViews() {
        // map ui components
        tvCouponId = findViewById(R.id.tv_coupon_id);
        tvCouponName = findViewById(R.id.tv_coupon_name);
        tvCouponQuantity = findViewById(R.id.tv_coupon_quantity);
        tvCouponStartDate = findViewById(R.id.tv_coupon_start_date);
        tvCouponEndDate = findViewById(R.id.tv_coupon_end_date);
        tvCouponType = findViewById(R.id.tv_coupon_type);
        tvCouponValue = findViewById(R.id.tv_coupon_value);
    }

    private void setupButtons() {
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        btnEdit = findViewById(R.id.btn_admin_coupon_edit);
        btnEdit.setOnClickListener(v -> {
            Toast.makeText(this, "Edit button clicked", Toast.LENGTH_SHORT).show();
        });
    }

    private void renderData(CouponModel coupon) {
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

        String typeText, valueText;
        if ("percentage".equalsIgnoreCase(coupon.getType())) {
            typeText = "Phần trăm";
            valueText = String.format("Giảm %s%%", coupon.getValue());
        } else if ("fixed".equalsIgnoreCase(coupon.getType())) {
            String valueFormatted = numberFormat.format(coupon.getValue() * 1000);
            String minimalTotalFormatted = numberFormat.format(coupon.getMinimalTotal() * 1000);
            typeText = "Cố định";
            valueText = String.format("Giảm %s đ cho đơn trên %s đ", valueFormatted, minimalTotalFormatted);
        } else {
            typeText = valueText = "Không xác định";
        }

        tvCouponId.setText(coupon.getCouponId());
        tvCouponName.setText(coupon.getName());
        tvCouponQuantity.setText(String.valueOf(coupon.getQuantity()));
        tvCouponStartDate.setText(coupon.getDateStart());
        tvCouponEndDate.setText(coupon.getDateEnd());
        tvCouponType.setText(typeText);
        tvCouponValue.setText(valueText);
    }
}
