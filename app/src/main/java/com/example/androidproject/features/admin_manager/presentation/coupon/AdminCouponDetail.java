package com.example.androidproject.features.admin_manager.presentation.coupon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.R;
import com.example.androidproject.core.utils.JsonUtil;
import com.example.androidproject.features.admin_manager.data.model.CouponModel;
import com.example.androidproject.features.voucher.data.model.VoucherModel;
import com.example.androidproject.features.voucher.usecase.VoucherUseCase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdminCouponDetail extends AppCompatActivity {
    private TextView tvCouponId, tvCouponName, tvCouponValue, tvCouponMinValue, tvCouponUnit1, tvCouponUnit2;
    private ImageButton btnBack;
    private Button btnHide,btnEdit,btnDelete;
    private String voucherId;
    private Spinner spinnerVoucherType;
    private String voucherType;
    private boolean hidden;
    private VoucherUseCase voucherUseCase = new VoucherUseCase();
    private NumberFormat numberFormat = NumberFormat.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin__coupon_detail);
        setupWindowInsets();

        initializeViews();
        setupButtons();
        getVoucherId();
        fetchDetailVoucher();
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.admin__coupon_detail), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initializeViews() {
        tvCouponId = findViewById(R.id.tv_coupon_id);
        tvCouponName = findViewById(R.id.tv_coupon_name);
        tvCouponValue = findViewById(R.id.tv_coupon_value);
        tvCouponMinValue = findViewById(R.id.tv_coupon_min_value);
        spinnerVoucherType = findViewById(R.id.spinnerVoucherType);
        tvCouponUnit1 = findViewById(R.id.tv_coupon_unit1);
        tvCouponUnit2 = findViewById(R.id.tv_coupon_unit2);
    }

    private void setupButtons() {
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        btnHide = findViewById(R.id.btn_admin_coupon_hide);
        btnHide.setOnClickListener(v -> {
            voucherUseCase.updateVoucherHidden(voucherId,!hidden)
                            .thenAccept(r -> {
                                if(r.isRight()) {
                                    Toast.makeText(this, "Cập nhật trạng thái voucher thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
        });

        btnEdit = findViewById(R.id.btn_admin_coupon_update);
        btnEdit.setOnClickListener(v -> {
            if(voucherType.equals("Chọn loại voucher")) {
                Toast.makeText(this, "Vui lòng chọn loại voucher", Toast.LENGTH_SHORT).show();
            } else {
                VoucherModel voucherModel = new VoucherModel(
                        tvCouponName.getText().toString(),
                        voucherType.equals("Giảm phần trăm") ? "percent" : "minus",
                        Integer.parseInt(tvCouponValue.getText().toString().replace(",", "")),
                        Integer.parseInt(tvCouponMinValue.getText().toString().replace(",", ""))
                );
                voucherModel.setId(voucherId);
                voucherUseCase.updateVoucher(voucherModel)
                                .thenAccept(r -> {
                                    if(r.isRight()) {
                                        Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent();
                                        intent.putExtra("updated_voucher", true);
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    }
                                });
            }
        });

        btnDelete = findViewById(R.id.btn_admin_coupon_delete);
        btnDelete.setOnClickListener(v -> {
            voucherUseCase.deleteVoucher(voucherId)
                            .thenAccept(r -> {
                                if(r.isRight()) {
                                    Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent();
                                    intent.putExtra("deleted_voucher", true);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }
                            });
        });

        tvCouponName.setOnClickListener(v -> editDialog("Tên", tvCouponName.getText().toString(), tvCouponName));
        tvCouponValue.setOnClickListener(v -> editDialog("Giá trị", tvCouponValue.getText().toString(), tvCouponValue));
        tvCouponMinValue.setOnClickListener(v -> editDialog("Giá trị tối thiểu", tvCouponMinValue.getText().toString(), tvCouponMinValue));
    }

    private void setSpinnerValue(String type) {
        List<String> voucherTypes = new ArrayList<>();
        voucherTypes.add("Chọn loại voucher");
        voucherTypes.add("Giảm phần trăm");
        voucherTypes.add("Giảm tiền");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, voucherTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerVoucherType.setAdapter(adapter);
        int index = 0;
        if(type.equals("percent")) {
            index = voucherTypes.indexOf("Giảm phần trăm");
        } else if(type.equals("minus")) {
            index = voucherTypes.indexOf("Giảm tiền");
        }

        spinnerVoucherType.setSelection(index);

        spinnerVoucherType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                voucherType = adapterView.getItemAtPosition(i).toString();
                if(voucherType.equals("Giảm phần trăm")) {
                    tvCouponUnit1.setText(" %");
                    tvCouponUnit2.setText(" đ");
                } else if(voucherType.equals("Giảm tiền")) {
                    tvCouponUnit1.setText(" đ");
                    tvCouponUnit2.setText(" đ");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void fetchDetailVoucher() {
        voucherUseCase.getVoucherById(voucherId)
                .thenAccept(r -> {
                    if(r.isRight()) {
                        tvCouponId.setText(r.getRight().getId());
                        tvCouponName.setText(r.getRight().getName());
                        if(r.getRight().getType().equals("percent")) {
                            tvCouponValue.setText(numberFormat.format(r.getRight().getValue()));
                            tvCouponUnit1.setText(" %");
                            tvCouponUnit2.setText(" đ");
                        } else {
                            tvCouponValue.setText(numberFormat.format(r.getRight().getValue()));
                            tvCouponUnit1.setText(" đ");
                            tvCouponUnit2.setText(" đ");
                        }
                        setSpinnerValue(r.getRight().getType());
                        tvCouponMinValue.setText(numberFormat.format(r.getRight().getMinimalTotal()));
                        hidden = r.getRight().isHidden();
                        btnHide.setText(hidden ? "Hiện" : "Ẩn");
                    }
                });

    }

    private void getVoucherId() {
        voucherId = getIntent().getStringExtra("voucherId");
    }

    private void editDialog(String fieldName, String value, TextView tv) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chỉnh sửa " + fieldName);

        EditText editText = new EditText(this);
        editText.setPadding(20, 20, 20, 20);
        editText.setText(value.replace(",", ""));
        builder.setView(editText);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String newValue = editText.getText().toString().replace(",", "");
            int intValue = Integer.parseInt(newValue);
            tv.setText(numberFormat.format(intValue));

            if (!newValue.equals(value.replace(",", ""))) {
                btnEdit.setVisibility(Button.VISIBLE);
            } else {
                btnEdit.setVisibility(Button.GONE);
            }
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
