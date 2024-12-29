package com.example.androidproject.features.voucher.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.voucher.data.model.VoucherModel;
import com.example.androidproject.features.voucher.usecase.VoucherUseCase;

import java.util.List;

public class VoucherActivity extends AppCompatActivity {
    private RecyclerView recyclerViewAllVouchers;
    private Button btnMyVoucher;
    private ListVoucherAdapter listVoucherAdapter;
    private VoucherUseCase voucherUseCase = new VoucherUseCase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_voucher);

        initView();
        getVouchers();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnMyVoucher = findViewById(R.id.btnMyVoucher);
        recyclerViewAllVouchers = findViewById(R.id.recyclerViewAllVouchers);
    }

    private void getVouchers() {
        voucherUseCase.getAllActiveVouchers()
                .thenAccept(r -> {
                    if (r.isRight()) {
                        List<VoucherModel> vouchers = r.getRight();
                        runOnUiThread(() -> {
                            recyclerViewAllVouchers.setLayoutManager(new LinearLayoutManager(this));
                            listVoucherAdapter = new ListVoucherAdapter(vouchers, this);
                            recyclerViewAllVouchers.setAdapter(listVoucherAdapter);
                        });
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
}