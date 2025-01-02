package com.example.androidproject.features.admin_manager.presentation.order;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.admin_manager.presentation.AdminBaseManagerLayout;
import com.example.androidproject.features.admin_manager.presentation.widgets.ListOrderItemAdminAdapter;
import com.example.androidproject.features.cart.data.entity.ProductsOnCart;
import com.example.androidproject.features.checkout.data.model.CheckoutModel;
import com.example.androidproject.features.checkout.usecase.CheckoutUseCase;
import com.example.androidproject.features.order.usecase.OrderUseCase;

import java.util.ArrayList;
import java.util.List;

public class AdminOrderManagerActivity extends AdminBaseManagerLayout {
    private OrderUseCase orderUseCase = new OrderUseCase();
    private RecyclerView rvOrderList;
    private Spinner spinner_order_status;
    private CheckoutUseCase checkoutUseCase = new CheckoutUseCase();
    private ListOrderItemAdminAdapter orderHistoryListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setTitle("Quản lý đơn hàng");

        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(R.layout.activity_admin_order_manager, findViewById(R.id.content_container), true);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rvOrderList = findViewById(R.id.recycler_orders_view);
        List<CheckoutModel> checkoutModelList = new ArrayList<>();
        orderHistoryListAdapter = new ListOrderItemAdminAdapter(checkoutModelList, this);
        rvOrderList.setAdapter(orderHistoryListAdapter);
        rvOrderList.setLayoutManager(new LinearLayoutManager(this));

        spinner_order_status = findViewById(R.id.spStatus);

        List<String> statusList = List.of("Tất cả", "Đang xử lý", "Đang giao", "Thành công", "Thất bại");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_order_status.setAdapter(adapter);

        spinner_order_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    getAllCheckouts(); // Hiển thị tất cả các đơn hàng
                } else if (position == 1) {
                    getAllCheckoutsByStatus("PENDING"); // Hiển thị các đơn hàng đang xử lý
                } else if (position == 2) {
                    getAllCheckoutsByStatus("INTRANSIT"); // Hiển thị các đơn hàng đang giao
                } else if (position == 3) {
                    getAllCheckoutsByStatus("SUCCESS"); // Hiển thị các đơn hàng đã thành công
                } else if (position == 4) {
                    getAllCheckoutsByStatus("FAILED"); // Hiển thị các đơn hàng thất bại
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không làm gì cả
            }
        });
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

    public void getAllCheckouts(){
        checkoutUseCase.getAllCheckouts()
                .thenAccept(r -> {
                    if(r.isRight()) {
                        List<CheckoutModel> checkoutModelList = r.getRight();
                        orderHistoryListAdapter.setOrderList(checkoutModelList);
                    }
                });
    }

    public void getAllCheckoutsByStatus(String status){
        checkoutUseCase.getAllCheckoutsByStatus(status)
                .thenAccept(r -> {
                    if(r.isRight()) {
                        List<CheckoutModel> checkoutModelList = r.getRight();
                        orderHistoryListAdapter.setOrderList(checkoutModelList);
                    } else {
                        List<CheckoutModel> checkoutModelList = new ArrayList<>();
                        orderHistoryListAdapter.setOrderList(checkoutModelList);
                    }
                });
    }
}