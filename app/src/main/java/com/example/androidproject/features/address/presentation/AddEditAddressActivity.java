package com.example.androidproject.features.address.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.androidproject.R;
import com.example.androidproject.core.utils.counter.CounterModel;
import com.example.androidproject.features.address.data.model.AddressModel;
import com.example.androidproject.features.address.usecase.AddressUsecase;
import com.example.androidproject.features.setting.data.types.AddressDistrictData;
import com.example.androidproject.features.setting.data.types.AddressProvinceData;
import com.example.androidproject.features.setting.data.types.AddressWardData;
import com.example.androidproject.features.setting.usecase.AddressUtils;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class AddEditAddressActivity extends AppCompatActivity {
    private boolean isProvinceDataFetched = false;
    private Spinner spinnerTinh, spinnerHuyen, spinnerXa;
    private EditText ETStreet;
    private Button btnSave;
    private AddressUtils addressUtils= new AddressUtils();
    private AddressModel address;
    private String provinceId, provinceName;
    private String districtId, districtName;
    private String wardId, wardName;
    private AddressUsecase addressUsecase = new AddressUsecase();
    private CounterModel counterModel = new CounterModel();
    private long addressQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_address);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initializeViews();
        handleSpinnerEvent();

//        address = getIntent().getParcelableExtra("address");
//        if (address != null) {
//            populateAddressData(address);
//        }

        btnSave.setOnClickListener(v -> {
            saveAddress();
        });
    }

    private void initializeViews() {
        spinnerTinh = findViewById(R.id.spinner_tinh);
        spinnerHuyen = findViewById(R.id.spinner_quan);
        spinnerXa = findViewById(R.id.spinner_phuong);
        ETStreet = findViewById(R.id.addressEditText);
        btnSave = findViewById(R.id.saveAddressButton);

        spinnerHuyen.setEnabled(false);
        spinnerXa.setEnabled(false);
    }

    private void handleSpinnerEvent() {
        spinnerTinh.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!isProvinceDataFetched) {
                    addressUtils.fetchProvinces()
                            .thenAccept(r -> {
                                if (r.isRight()) {
                                    List<AddressProvinceData> provinces = r.getRight();
                                    ArrayAdapter<AddressProvinceData> adapter = new ArrayAdapter<>(AddEditAddressActivity.this, android.R.layout.simple_spinner_item, provinces);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinnerTinh.setAdapter(adapter);
                                    spinnerHuyen.setEnabled(true);

                                    spinnerHuyen.setOnTouchListener(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View view, MotionEvent motionEvent) {
                                            provinceId = ((AddressProvinceData) spinnerTinh.getSelectedItem()).getId();
                                            provinceName = ((AddressProvinceData) spinnerTinh.getSelectedItem()).getName();
                                            addressUtils.fetchDistricts(provinceId)
                                                    .thenAccept(r1 -> {
                                                        if (r.isRight()) {
                                                            List<AddressDistrictData> districts = r1.getRight();
                                                            ArrayAdapter<AddressDistrictData> districtAdapter = new ArrayAdapter<>(AddEditAddressActivity.this, android.R.layout.simple_spinner_item, districts);
                                                            districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                            spinnerHuyen.setAdapter(districtAdapter);
                                                            spinnerXa.setEnabled(true);

                                                            spinnerXa.setOnTouchListener(new View.OnTouchListener() {
                                                                @Override
                                                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                                                    districtId = ((AddressDistrictData) spinnerHuyen.getSelectedItem()).getId();
                                                                    districtName = ((AddressDistrictData) spinnerHuyen.getSelectedItem()).getName();
                                                                    addressUtils.fetchWards(districtId)
                                                                            .thenAccept(r2 -> {
                                                                                if (r2.isRight()) {
                                                                                    List<AddressWardData> wards = r2.getRight();
                                                                                    ArrayAdapter<AddressWardData> wardAdapter = new ArrayAdapter<>(AddEditAddressActivity.this, android.R.layout.simple_spinner_item, wards);
                                                                                    wardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                                    spinnerXa.setAdapter(wardAdapter);
                                                                                }
                                                                            });
                                                                    return false;
                                                                }
                                                            });
                                                        }
                                                    });
                                            return false;
                                        }
                                    });
                                }
                            });
                    isProvinceDataFetched = true;
                }
                return false;
            }
        });
    }

    private void saveAddress() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        String street = ETStreet.getText().toString();
        wardName = ((AddressWardData) spinnerXa.getSelectedItem()).getName();
        wardId = ((AddressWardData) spinnerXa.getSelectedItem()).getId();
        AddressModel address = new AddressModel(
                street,
                provinceName,
                districtName,
                wardName,
                userId,
                provinceId,
                districtId,
                wardId
        );

        counterModel.getQuantity("address")
                        .addOnSuccessListener(quantity -> {
                            addressQuantity = quantity;
                            addressUsecase.addAddress(address, quantity)
                                    .thenAccept(r -> {
                                        if (r.isRight()) {
                                            addressQuantity++;
                                            counterModel.updateQuantity("address");
                                            Toast.makeText(AddEditAddressActivity.this, "Đã lưu địa chỉ: " + address.getFullAddress(), Toast.LENGTH_LONG).show();
                                            Intent resultIntent = new Intent();
                                            setResult(RESULT_OK, resultIntent);
                                            finish();
                                        }
                                    });
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
