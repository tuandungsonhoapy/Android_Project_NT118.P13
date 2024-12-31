package com.example.androidproject.features.setting.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.androidproject.R;
import com.example.androidproject.core.utils.NavigationUtils;

public class ProfileSettingActivity extends AppCompatActivity {

    private TextView tvDetail, tvFullName, tvGenderText, tvUserId, tvFullAddress, tvEmail, tvPhone, tvTotalSpent;
    private EditText etName, etLastName, etAddress, etEmail, etPhone;
    private RadioGroup rgGender;
    private Button btnEdit, btnSave;
    private CardView card1, card2;
    private ImageView btnBack, ivUserImage, ivGenderImage, ivUserEdit;
    private LinearLayout llUserEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_setting);

        init();

    }

    private void init() {
        initInsets();
        initViews();
        setupClickListeners();
    }

    private void initInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnEdit = findViewById(R.id.btnEdit);
        btnSave = findViewById(R.id.btnSave);
        tvDetail = findViewById(R.id.tvDetail);
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        ivUserEdit = findViewById(R.id.ivUserEdit);
        llUserEdit = findViewById(R.id.llUserEdit);

        // normal
        ivUserImage = findViewById(R.id.ivUserImage);
        ivGenderImage = findViewById(R.id.ivGenderImage);
        tvDetail = findViewById(R.id.tvDetail);
        tvFullName = findViewById(R.id.tvFullName);
        tvGenderText = findViewById(R.id.tvGenderText);
        tvUserId = findViewById(R.id.tvUserId);
        tvFullAddress = findViewById(R.id.tvFullAddress);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvTotalSpent = findViewById(R.id.tvTotalSpent);

        // edit
        etName = findViewById(R.id.etName);
        etLastName = findViewById(R.id.etLastName);
        etAddress = findViewById(R.id.etAddress);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);

        rgGender = findViewById(R.id.rgGender);

        switchToNormal();
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());
        btnEdit.setOnClickListener(v -> handleEdit());
        btnSave.setOnClickListener(v -> handleSave());
        etAddress.setOnClickListener(v -> NavigationUtils.navigateTo(this, AddressSettingActivity.class, false));
    }

    private void handleEdit() {
        switchToEdit();
    }

    private void handleSave() {
        switchToNormal();
    }

    private void switchToNormal() {
        btnEdit.setVisibility(View.VISIBLE);
        tvDetail.setVisibility(View.VISIBLE);
        card1.setVisibility(View.VISIBLE);
        card2.setVisibility(View.VISIBLE);

        btnSave.setVisibility(View.GONE);
        ivUserEdit.setVisibility(View.GONE);
        llUserEdit.setVisibility(View.GONE);
    }

    private void switchToEdit() {
        btnEdit.setVisibility(View.GONE);
        tvDetail.setVisibility(View.GONE);
        card1.setVisibility(View.GONE);
        card2.setVisibility(View.GONE);

        btnSave.setVisibility(View.VISIBLE);
        ivUserEdit.setVisibility(View.VISIBLE);
        llUserEdit.setVisibility(View.VISIBLE);
    }
}