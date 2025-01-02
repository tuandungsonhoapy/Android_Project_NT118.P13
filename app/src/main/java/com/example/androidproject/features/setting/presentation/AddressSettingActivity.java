package com.example.androidproject.features.setting.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.androidproject.R;
import com.example.androidproject.core.credential.UserPreferences;
import com.example.androidproject.features.address.data.model.AddressModel;
import com.example.androidproject.features.address.presentation.AddEditAddressActivity;
import com.example.androidproject.features.address.presentation.ListAddressSettingAdapter;
import com.example.androidproject.features.address.usecase.AddressUsecase;

import java.util.List;

public class AddressSettingActivity extends AppCompatActivity {
    private static final int ADD_EDIT_ADDRESS_REQUEST_CODE = 1;
    AddressUsecase addressUsecase = new AddressUsecase();
    private ListAddressSettingAdapter listAddressSettingAdapter;
    private String userDocId;
    private List<AddressModel> addresses;

    //views
    private ImageView ivEmpty;
    Toolbar toolbar;
    RecyclerView addressRecyclerView;
    Button btnAddAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_address_setting);

        // get extra
        userDocId = getIntent().getStringExtra("user_doc_id");
        initViews();
        setupToolbar();

        addressRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadAddresses();

        btnAddAddress.setOnClickListener(view -> handleAddAddress());
    }

    private void initViews() {
        ivEmpty = findViewById(R.id.ivEmpty);
        toolbar = findViewById(R.id.toolbar);
        addressRecyclerView = findViewById(R.id.addressRecyclerView);
        btnAddAddress = findViewById(R.id.addAddressButton);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void loadAddresses() {
        if (userDocId == null) {
            addressUsecase.getAddresses().thenAccept(r -> {
                if (r.isRight()) {
                    updateAddressList(r.getRight());
                }
            });
        } else {
            addressUsecase.getAddresses(userDocId).thenAccept(r -> {
                if (r.isRight()) {
                    updateAddressList(r.getRight());
                }
            });
        }
    }

    private void updateAddressList(List<AddressModel> addressList) {
        addresses = addressList;
        checkEmpty();
        listAddressSettingAdapter = new ListAddressSettingAdapter(this, userDocId, addresses);
        addressRecyclerView.setAdapter(listAddressSettingAdapter);

        listAddressSettingAdapter.setOnItemClickListener(address -> {
            Intent intent = new Intent(AddressSettingActivity.this, AddEditAddressActivity.class);
            intent.putExtra("editMode", true);
            intent.putExtra("address_id", address.getId());
            startActivityForResult(intent, ADD_EDIT_ADDRESS_REQUEST_CODE);
        });
    }

    private void handleAddAddress() {
        Intent intent = new Intent(AddressSettingActivity.this, AddEditAddressActivity.class);
        intent.putExtra("editMode", false);
        intent.putExtra("user_doc_id", userDocId);
        startActivityForResult(intent, ADD_EDIT_ADDRESS_REQUEST_CODE);
    }

    private void checkEmpty() {
        if (addresses.isEmpty()) ivEmpty.setVisibility(View.VISIBLE);
        else ivEmpty.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
            if (userDocId == null)
                addressUsecase.getAddresses().thenAccept(r -> {
                    if (r.isRight()) {
                        runOnUiThread(() -> {
                            listAddressSettingAdapter.updateAddressList(r.getRight());
                        });
                    }
                });
            else
                addressUsecase.getAddresses(userDocId).thenAccept(r -> {
                    if (r.isRight()) {
                        runOnUiThread(() -> {
                            listAddressSettingAdapter.updateAddressList(r.getRight());
                        });
                    }
                });
        }
    }
}