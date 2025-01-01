package com.example.androidproject.features.setting.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.checkout.data.model.CheckoutModel;
import com.example.androidproject.features.checkout.usecase.CheckoutUseCase;
import com.example.androidproject.features.order.presentation.OrderHistoryListAdapter;
import com.example.androidproject.features.order.usecase.OrderUseCase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class OrderSettingActivity extends AppCompatActivity {
    private Spinner spinner_order_status;
    private RecyclerView recycler_order_list;
    private OrderUseCase orderUseCase = new OrderUseCase();
    private OrderHistoryListAdapter orderHistoryListAdapter;
    private CheckoutUseCase checkoutUseCase = new CheckoutUseCase();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_setting);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initView();
    }

    public void initView() {
        spinner_order_status = findViewById(R.id.spStatus);
        recycler_order_list = findViewById(R.id.recycler_order_history);

        List<String> statusList = List.of("Tất cả", "Đang xử lý", "Đang giao", "Thành công", "Thất bại");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_order_status.setAdapter(adapter);

        List<CheckoutModel> checkoutModelList = new ArrayList<>();
        orderHistoryListAdapter = new OrderHistoryListAdapter(this, checkoutModelList);

        recycler_order_list.setLayoutManager(new LinearLayoutManager(this));
        recycler_order_list.setAdapter(orderHistoryListAdapter);

        // Set listener for Spinner
        spinner_order_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    getCheckoutList(); // Hiển thị tất cả các đơn hàng
                } else if (position == 1) {
                    getCheckoutListByStatus("PENDING"); // Hiển thị các đơn hàng đang xử lý
                } else if (position == 2) {
                    getCheckoutListByStatus("INTRANSIT"); // Hiển thị các đơn hàng đang giao
                } else if (position == 3) {
                    getCheckoutListByStatus("SUCCESS"); // Hiển thị các đơn hàng đã thành công
                } else if (position == 4) {
                    getCheckoutListByStatus("FAILED"); // Hiển thị các đơn hàng đã thất bại
                }
                else {
                    Log.d("OrderSettingActivity", "Invalid status");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không làm gì cả
            }
        });

        getCheckoutList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getCheckoutList() {
        if(currentUser != null) {
            checkoutUseCase.getCheckoutList(currentUser.getUid())
                    .thenAccept(r -> {
                        if(r.isRight()) {
                            List<CheckoutModel> checkoutModelList = r.getRight();
                            orderHistoryListAdapter.setOrderList(checkoutModelList);
                        }
                    });
        } else {
            Log.d("OrderSettingActivity", "User is not logged in");
        }
    }

    public void getCheckoutListByStatus(String status) {
        if(currentUser != null) {

            checkoutUseCase.getCheckoutListByStatusAndUserId(status, currentUser.getUid())
                    .thenAccept(r -> {
                        if(r.isRight()) {
                            Log.d("OrderSettingActivity", "User is logged in abc and status is " + status);
                            List<CheckoutModel> checkoutModelList = r.getRight();
                            orderHistoryListAdapter.setOrderList(checkoutModelList);
                        }
                    });
        } else {
            Log.d("OrderSettingActivity", "User is not logged in");
        }
    }
}