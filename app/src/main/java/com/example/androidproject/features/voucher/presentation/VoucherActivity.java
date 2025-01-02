package com.example.androidproject.features.voucher.presentation;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.auth.usecase.UserUseCase;
import com.example.androidproject.features.voucher.usecase.VoucherUseCase;

import java.util.List;

public class VoucherActivity extends AppCompatActivity {
    private RecyclerView recyclerViewAllVouchers;
    private TextView tvNoVoucher;
    private ListVoucherAdapter listVoucherAdapter;
    private VoucherUseCase voucherUseCase = new VoucherUseCase();
    private ImageView img_search;
    private EditText edt_search;
    private String voucherId = null;
    private UserUseCase userUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_voucher);
        userUseCase = new UserUseCase(this);

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

        recyclerViewAllVouchers = findViewById(R.id.recyclerViewAllVouchers);
        tvNoVoucher = findViewById(R.id.tvNoVoucher);
        img_search = findViewById(R.id.btnSearch);
        edt_search = findViewById(R.id.edt_search);

        img_search.setOnClickListener(v -> {
            String search = edt_search.getText().toString();
            if (search.isEmpty()) {
                Toast.makeText(this, "Hãy nhập mã voucher để thêm", Toast.LENGTH_SHORT).show();
            } else {
                voucherId = search;
                addVoucher();
                edt_search.setText("");
            }
        });
    }

    private void addVoucher() {
        userUseCase.addVoucher(voucherId)
                .thenAccept(r -> {
                    if (r.isRight()) {
                        runOnUiThread(() -> {
                            getVouchers();
                            Toast.makeText(this, r.getRight(), Toast.LENGTH_SHORT).show();
                        });
                    } else {
                        Toast.makeText(this, r.getLeft().getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getVouchers() {
        userUseCase.getAllUserVouchers()
                .thenCompose(r -> {
                   if(r.isRight()) {
                       List<String> voucherIds = r.getRight().getVouchers();
                       voucherUseCase.getAllActiveVouchers(voucherIds)
                               .thenAccept(r1 -> {
                                      if (r1.isRight()) {
                                        runOnUiThread(() -> {
                                             if(r1.getRight().size() > 0) {
                                                 tvNoVoucher.setText("Danh sách voucher");
                                                 listVoucherAdapter = new ListVoucherAdapter(r1.getRight(), this);
                                                 recyclerViewAllVouchers.setLayoutManager(new LinearLayoutManager(this));
                                                 recyclerViewAllVouchers.setAdapter(listVoucherAdapter);
                                                 listVoucherAdapter.notifyDataSetChanged();
                                             } else {
                                                    tvNoVoucher.setText("Không có voucher nào");
                                                    recyclerViewAllVouchers.setVisibility(View.GONE);
                                             }
                                        });
                                      } else {
                                        runOnUiThread(() -> {
                                            tvNoVoucher.setText("Không có voucher nào");
                                             recyclerViewAllVouchers.setVisibility(View.GONE);
                                            Toast.makeText(this, "Không có voucher nào", Toast.LENGTH_SHORT).show();
                                        });
                                      }
                               });
                   } else {
                          runOnUiThread(() -> {
                            tvNoVoucher.setVisibility(View.VISIBLE);
                            recyclerViewAllVouchers.setVisibility(View.GONE);
                            Toast.makeText(this, "Không có voucher nào", Toast.LENGTH_SHORT).show();
                          });
                   }
                     return null;
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