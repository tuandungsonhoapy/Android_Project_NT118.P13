package com.example.androidproject.features.address.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.androidproject.R;
import com.example.androidproject.features.address.data.model.AddressModel;
import com.example.androidproject.features.setting.data.types.AddressDistrictData;
import com.example.androidproject.features.setting.data.types.AddressProvinceData;
import com.example.androidproject.features.setting.data.types.AddressWardData;
import com.example.androidproject.features.setting.usecase.AddressUtils;

import java.util.ArrayList;
import java.util.List;

public class AddEditAddressActivity extends AppCompatActivity {
    private boolean isProvinceDataFetched = false;
    private Spinner spinnerTinh, spinnerHuyen, spinnerXa;
    private EditText ETStreet;
    private Button btnSave;
    private AddressUtils addressUtils;
    private AddressModel address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_address);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initializeViews();

        addressUtils = new AddressUtils();
        handleSpinnerEvent();

        address = getIntent().getParcelableExtra("address");
        if (address != null) {
            populateAddressData(address);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAddress();
            }
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
                    isProvinceDataFetched = true;
                    addressUtils.fetchProvinces(new AddressUtils.OnProvincesFetchedListener() {
                        @Override
                        public void onProvincesFetched(List<AddressProvinceData> provinces) {
                            ArrayAdapter<AddressProvinceData> adapter = new ArrayAdapter<>(AddEditAddressActivity.this, android.R.layout.simple_spinner_item, provinces);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerTinh.setAdapter(adapter);
                            spinnerHuyen.setEnabled(true);
                        }

                        @Override
                        public void onError(String errorMessage) {
                            Toast.makeText(AddEditAddressActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                return false;
            }
        });

        spinnerHuyen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (spinnerTinh.getSelectedItem() != null && spinnerHuyen.isEnabled()) {
                    String provinceId = ((AddressProvinceData) spinnerTinh.getSelectedItem()).getId();
                    addressUtils.fetchDistricts(provinceId, new AddressUtils.OnDistrictsFetchedListener() {
                        @Override
                        public void onDistrictsFetched(List<AddressDistrictData> districts) {
                            ArrayAdapter<AddressDistrictData> adapter = new ArrayAdapter<>(AddEditAddressActivity.this, android.R.layout.simple_spinner_item, districts);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerHuyen.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            spinnerXa.setEnabled(true);
                        }

                        @Override
                        public void onError(String errorMessage) {
                            Toast.makeText(AddEditAddressActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                return false;
            }
        });

        spinnerXa.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (spinnerHuyen.getSelectedItem() != null && spinnerXa.isEnabled()) {
                    String districtId = ((AddressDistrictData) spinnerHuyen.getSelectedItem()).getId();
                    addressUtils.fetchWards(districtId, new AddressUtils.OnWardsFetchedListener() {
                        @Override
                        public void onWardsFetched(List<AddressWardData> wards) {
                            ArrayAdapter<AddressWardData> adapter = new ArrayAdapter<>(AddEditAddressActivity.this, android.R.layout.simple_spinner_item, wards);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerXa.setAdapter(adapter);
                        }

                        @Override
                        public void onError(String errorMessage) {
                            Toast.makeText(AddEditAddressActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                return false;
            }
        });
    }

    private void populateAddressData(AddressModel address) {
        ETStreet.setText(address.getStreet());

        String provinceId = address.getProvinceId();
        String districtId = address.getDistrictId();
        String wardId = address.getWardId();

        addressUtils.fetchProvinces(new AddressUtils.OnProvincesFetchedListener() {
            @Override
            public void onProvincesFetched(List<AddressProvinceData> provinces) {
                if (provinces != null) {
                    ArrayAdapter<AddressProvinceData> adapter = new ArrayAdapter<>(AddEditAddressActivity.this, android.R.layout.simple_spinner_item, provinces);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerTinh.setAdapter(adapter);

                    for (int i = 0; i < provinces.size(); i++) {
                        if (provinces.get(i).getId().equals(provinceId)) {
                            spinnerTinh.setSelection(i);
                            break;
                        }
                    }

                    // Bật spinner huyện
                    spinnerHuyen.setEnabled(true);
                    addressUtils.fetchDistricts(provinceId, new AddressUtils.OnDistrictsFetchedListener() {
                        @Override
                        public void onDistrictsFetched(List<AddressDistrictData> districts) {
                            if (districts != null) {
                                ArrayAdapter<AddressDistrictData> districtAdapter = new ArrayAdapter<>(AddEditAddressActivity.this, android.R.layout.simple_spinner_item, districts);
                                districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerHuyen.setAdapter(districtAdapter);

                                int districtIndex = -1;
                                for (int i = 0; i < districts.size(); i++) {
                                    if (districts.get(i).getId().equals(districtId)) {
                                        districtIndex = i;
                                        break;
                                    }
                                }
                                if (districtIndex != -1) {
                                    spinnerHuyen.setSelection(districtIndex);
                                }

                                // Bật spinner xã
                                spinnerXa.setEnabled(true);
                                addressUtils.fetchWards(districtId, new AddressUtils.OnWardsFetchedListener() {
                                    @Override
                                    public void onWardsFetched(List<AddressWardData> wards) {
                                        if (wards != null) {
                                            ArrayAdapter<AddressWardData> wardAdapter = new ArrayAdapter<>(AddEditAddressActivity.this, android.R.layout.simple_spinner_item, wards);
                                            wardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            spinnerXa.setAdapter(wardAdapter);

                                            int wardIndex = -1;
                                            for (int i = 0; i < wards.size(); i++) {
                                                if (wards.get(i).getId().equals(wardId)) {
                                                    wardIndex = i;
                                                    break;
                                                }
                                            }
                                            if (wardIndex != -1) {
                                                spinnerXa.setSelection(wardIndex);
                                            }
                                        } else {
                                            Toast.makeText(AddEditAddressActivity.this, "Failed to fetch wards", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onError(String errorMessage) {
                                        Toast.makeText(AddEditAddressActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(AddEditAddressActivity.this, "Failed to fetch districts", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(String errorMessage) {
                            Toast.makeText(AddEditAddressActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(AddEditAddressActivity.this, "Failed to fetch provinces", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(AddEditAddressActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveAddress() {
        String province = spinnerTinh.getSelectedItem().toString();
        String district = spinnerHuyen.getSelectedItem().toString();
        String ward = spinnerXa.getSelectedItem().toString();
        String street = ETStreet.getText().toString();

        String fullAddress = street + ", " + ward + ", " + district + ", " + province;
        Toast.makeText(AddEditAddressActivity.this, "Address saved: " + fullAddress, Toast.LENGTH_LONG).show();
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
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
