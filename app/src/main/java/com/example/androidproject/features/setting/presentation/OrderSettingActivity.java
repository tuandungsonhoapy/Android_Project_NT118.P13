package com.example.androidproject.features.setting.presentation;

import android.os.Bundle;
import android.view.MenuItem;
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
import com.example.androidproject.features.order.presentation.OrderHistoryListAdapter;
import com.example.androidproject.features.order.usecase.OrderUseCase;

import java.util.List;

public class OrderSettingActivity extends AppCompatActivity {
    private Spinner spinner_order_status;
    private RecyclerView recycler_order_list;
    private OrderUseCase orderUseCase = new OrderUseCase();

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

        recycler_order_list.setLayoutManager(new LinearLayoutManager(this));
        recycler_order_list.setAdapter(new OrderHistoryListAdapter(this, orderUseCase.getAllOrders()));
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
}