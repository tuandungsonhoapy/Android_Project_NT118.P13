package com.example.androidproject.features.setting.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.androidproject.R;
import com.example.androidproject.features.address.data.model.AddressModel;
import com.example.androidproject.features.address.presentation.AddEditAddressActivity;
import com.example.androidproject.features.address.presentation.ListAddressSettingAdapter;
import com.example.androidproject.features.address.usecase.AddressUsecase;

import java.util.List;

public class AddressSettingActivity extends AppCompatActivity {
    private static final int ADD_EDIT_ADDRESS_REQUEST_CODE = 1;
    AddressUsecase addressUsecase = new AddressUsecase();
    private ListAddressSettingAdapter listAddressSettingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_address_setting);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RecyclerView addressRecyclerView = findViewById(R.id.addressRecyclerView);
        addressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button btnAddAddress = findViewById(R.id.addAddressButton);

        addressUsecase.getAddresses().thenAccept(r -> {
            if(r.isRight()) {
                runOnUiThread(() -> {
                    listAddressSettingAdapter = new ListAddressSettingAdapter(this, r.getRight());
                    addressRecyclerView.setAdapter(listAddressSettingAdapter);
                    listAddressSettingAdapter.setOnItemClickListener(new ListAddressSettingAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(AddressModel address) {
                            Intent intent = new Intent(AddressSettingActivity.this, AddEditAddressActivity.class);
                            intent.putExtra("editMode", true);
                            intent.putExtra("address_id", address.getId());
                            startActivityForResult(intent, ADD_EDIT_ADDRESS_REQUEST_CODE);
                        }
                    });
                });
            }
        });

        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddressSettingActivity.this, AddEditAddressActivity.class);
                intent.putExtra("editMode", false);
                startActivityForResult(intent, ADD_EDIT_ADDRESS_REQUEST_CODE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_EDIT_ADDRESS_REQUEST_CODE && resultCode == RESULT_OK) {
            addressUsecase.getAddresses().thenAccept(r -> {
                if(r.isRight()) {
                    runOnUiThread(() -> {
                        listAddressSettingAdapter.updateAddressList(r.getRight());
                    });
                }
            });
        }
    }
}