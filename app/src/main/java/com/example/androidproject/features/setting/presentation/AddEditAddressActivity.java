package com.example.androidproject.features.setting.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.androidproject.R;
import com.example.androidproject.features.setting.data.types.AddressDistrictData;
import com.example.androidproject.features.setting.data.types.AddressProvinceData;
import com.example.androidproject.features.setting.data.types.AddressWardData;
import com.example.androidproject.features.setting.usecase.AddressUtils;

import java.util.List;

public class AddEditAddressActivity extends AppCompatActivity {
    private boolean isProvinceDataFetched = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_address);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Spinner spinnerTinh = findViewById(R.id.spinner_tinh);
        Spinner spinnerHuyen = findViewById(R.id.spinner_quan);
        Spinner spinnerXa = findViewById(R.id.spinner_phuong);
        Button btnSave = findViewById(R.id.saveAddressButton);

        spinnerHuyen.setEnabled(false);
        spinnerXa.setEnabled(false);

        AddressUtils addressUtils = new AddressUtils();

        spinnerTinh.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(!isProvinceDataFetched) {
                    isProvinceDataFetched = true;
                    addressUtils.fetchProvinces(new AddressUtils.OnProvincesFetchedListener() {
                        @Override
                        public void onProvincesFetched(List<AddressProvinceData> provinces) {
                            ArrayAdapter<AddressProvinceData> adapter = new ArrayAdapter<>(AddEditAddressActivity.this, android.R.layout.simple_spinner_item, provinces);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerTinh.setAdapter(adapter);

                            spinnerTinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    spinnerHuyen.setEnabled(true);
                                    String provinceId = provinces.get(position).getId();

                                    addressUtils.fetchDistricts(provinceId, new AddressUtils.OnDistrictsFetchedListener() {
                                        @Override
                                        public void onDistrictsFetched(List<AddressDistrictData> districts) {
                                            ArrayAdapter<AddressDistrictData> adapter = new ArrayAdapter<>(AddEditAddressActivity.this, android.R.layout.simple_spinner_item, districts);
                                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            spinnerHuyen.setAdapter(adapter);

                                            spinnerXa.setEnabled(false);

                                            spinnerHuyen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                    spinnerXa.setEnabled(true);
                                                    String districtId = districts.get(position).getId();

                                                    addressUtils.fetchWards(districtId,new AddressUtils.OnWardsFetchedListener() {
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

                                                @Override
                                                public void onNothingSelected(AdapterView<?> parent) {
                                                }
                                            });
                                        }

                                        @Override
                                        public void onError(String errorMessage) {
                                            Toast.makeText(AddEditAddressActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddEditAddressActivity.this, "Address saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
