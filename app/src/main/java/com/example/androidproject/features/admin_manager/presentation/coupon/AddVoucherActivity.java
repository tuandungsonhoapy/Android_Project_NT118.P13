package com.example.androidproject.features.admin_manager.presentation.coupon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.R;
import com.example.androidproject.core.utils.counter.CounterModel;
import com.example.androidproject.features.voucher.data.model.VoucherModel;
import com.example.androidproject.features.voucher.usecase.VoucherUseCase;

import java.util.ArrayList;
import java.util.List;

public class AddVoucherActivity extends AppCompatActivity {
    private EditText etVoucherName, etVoucherValue, etVoucherMinValue;
    private Button btnAddVoucher;
    private Spinner spinnerVoucherType;
    private String voucherType;
    private CounterModel counterModel = new CounterModel();
    private long voucherQuantity;
    private VoucherUseCase voucherUseCase = new VoucherUseCase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_voucher);

        initView();
        setSpinnerValue();

        btnAddVoucher.setOnClickListener(v -> {
            addVoucher();
        });
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

        etVoucherName = findViewById(R.id.etVoucherName);
        etVoucherValue = findViewById(R.id.etVoucherValue);
        etVoucherMinValue = findViewById(R.id.etVoucherMinValue);
        spinnerVoucherType = findViewById(R.id.spinnerVoucherType);
        btnAddVoucher = findViewById(R.id.btnAddVoucher);
    }

    private void setSpinnerValue() {
        List<String> voucherTypes = new ArrayList<>();
        voucherTypes.add("Chọn loại voucher");
        voucherTypes.add("Giảm phần trăm");
        voucherTypes.add("Giảm tiền");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, voucherTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerVoucherType.setAdapter(adapter);

        spinnerVoucherType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                voucherType = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void addVoucher() {
        if(etVoucherName.getText().toString().isEmpty()
                || etVoucherValue.getText().toString().isEmpty()
                || etVoucherMinValue.getText().toString().isEmpty()
                || voucherType.equals("Chọn loại voucher")) {
            Toast.makeText(this, "Hãy điền đây đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            counterModel.getQuantity("voucher")
                    .addOnSuccessListener(quantity -> {
                        voucherQuantity = quantity;

                        VoucherModel voucher = new VoucherModel(
                                etVoucherName.getText().toString(),
                                voucherType == "Giảm phần trăm" ? "percent" : "minus",
                                Integer.parseInt(etVoucherValue.getText().toString()),
                                Integer.parseInt(etVoucherMinValue.getText().toString())
                        );

                        voucherUseCase.addVoucher(voucher, voucherQuantity)
                                .thenAccept(r -> {
                                    if(r.isRight()) {
                                        Toast.makeText(this, "Thêm voucher thành công", Toast.LENGTH_SHORT).show();
                                        counterModel.updateQuantity("voucher");
                                        Intent intent = new Intent();
                                        intent.putExtra("added_voucher", true);
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    } else {
                                        Toast.makeText(this, "Thêm voucher thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    });
        }
    }
}