package com.example.androidproject.features.admin_manager.presentation.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.R;
import com.example.androidproject.core.utils.JsonUtils;
import com.example.androidproject.core.utils.MoneyFomat;
import com.example.androidproject.core.utils.NavigationUtils;
import com.example.androidproject.core.utils.UserTierUtils;
import com.example.androidproject.features.admin_manager.data.repository.UserRepository;
import com.example.androidproject.features.auth.data.entity.UserEntity;
import com.example.androidproject.features.setting.presentation.AddressSettingActivity;

public class DetailUserAdminActivity extends AppCompatActivity {
    //views
    private TextView tvDetail, tvTier, tvFullName, tvRoleText, tvGenderText, tvUserId, tvFullAddress, tvEmail, tvPhone, tvTotalSpent;
    private EditText etName, etLastName, etAddress, etEmail, etPhone;
    private RadioGroup rgGender, rgRole;
    private Button btnEdit, btnSave;
    private CardView card1, card2;
    private ImageView btnBack, ivUserImage, ivGenderImage, ivUserEdit;
    private LinearLayout llUserEdit;

    //others
    private UserEntity userData;
    private Boolean isOwnProfile;
    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_setting);

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (userData != null) {
            userRepository.getUserById(userData.getId()).thenAccept(r -> {
                if (r.isRight()) {
                    userData = r.getRight();

                    updateEditUI();
                } else {
                    Toast.makeText(DetailUserAdminActivity.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void init() {
        userRepository = new UserRepository();

        getExtra();
        initInsets();
        initViews();
        setupClickListeners();

        if (userData != null) {
            updateNormalUI();
        }
    }

    private void getExtra() {
        String userJson = getIntent().getStringExtra("user_data");

        userData = JsonUtils.jsonToObject(userJson, UserEntity.class);
        isOwnProfile = getIntent().getBooleanExtra("is_own_profile", false);
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
        tvTier = findViewById(R.id.tvTier);
        tvFullName = findViewById(R.id.tvFullName);
        tvRoleText = findViewById(R.id.tvRoleText);
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

        rgRole = findViewById(R.id.rgRole);
        rgGender = findViewById(R.id.rgGender);

        switchToNormal();
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());
        btnEdit.setOnClickListener(v -> handleEdit());
        btnSave.setOnClickListener(v -> handleSave());
        etAddress.setOnClickListener(v -> startAddressEditor());
    }

    private void startAddressEditor() {
        String userId = userData.getId();

        userRepository.getDocId(userId).thenAccept(result -> {
            if (result.isRight()) {
                String docId = result.getRight();
                Intent intent = new Intent(DetailUserAdminActivity.this, AddressSettingActivity.class);
                intent.putExtra("user_doc_id", docId);
                startActivity(intent);
            } else {
                Toast.makeText(DetailUserAdminActivity.this, "Failed to get user docId", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleEdit() {
        switchToEdit();
    }

    private void handleSave() {
        String updatedFirstName = etName.getText().toString().trim();
        String updatedLastName = etLastName.getText().toString().trim();
        String updatedEmail = etEmail.getText().toString().trim();
        String updatedPhone = etPhone.getText().toString().trim();
        String updatedAddress = etAddress.getText().toString().trim();

        String selectedGender = getSelectedRole();
        int selectedRole = getSelectedGender();

        if (isInputValid(updatedFirstName, updatedLastName, updatedEmail, updatedPhone, selectedGender, selectedRole)) {
            userData.setFirstName(updatedFirstName);
            userData.setLastName(updatedLastName);
            userData.setEmail(updatedEmail);
            userData.setPhone(updatedPhone);
            userData.setFullAddress(updatedAddress);
            userData.setGender(selectedGender);
            userData.setRole(selectedRole);


            userRepository.updateUserById(userData).thenAccept(result -> {
                if (result.isRight()) {
                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    if (isOwnProfile) {
                        finish();
                    } else {
                        switchToNormal();
                    }
                } else {
                    Toast.makeText(this, "Update failed: " + result.getLeft().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean isInputValid(String firstName, String lastName, String email, String phone, String gender, int role) {
        return !firstName.isEmpty() &&
                !lastName.isEmpty() &&
                !email.isEmpty() &&
                !phone.isEmpty() &&
                gender != null &&
                role >= 0;
    }

    private String getSelectedRole() {
        int selectedId = rgGender.getCheckedRadioButtonId();
        if (selectedId == R.id.rbMale) {
            return "Male";
        } else if (selectedId == R.id.rbFemale) {
            return "Female";
        } else if (selectedId == R.id.rbOther) {
            return "Other";
        } else {
            return null;
        }
    }

    private int getSelectedGender() {
        int selectedId = rgRole.getCheckedRadioButtonId();
        if (selectedId == R.id.rbAdmin) {
            return 0;
        } else {
            return 1;
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateNormalUI() {
        if (userData != null) {
            ivUserImage.setImageResource(R.drawable.logo_techo_without_text);

            tvTier.setText(UserTierUtils.getTierStringFromTier(userData.getTier()));
            tvFullName.setText(getNonNullValue(userData.getLastName()) + " " + getNonNullValue(userData.getFirstName()));
            tvGenderText.setText(getNonNullValue(userData.getGender()));
            tvUserId.setText(getNonNullValue(userData.getId()));
            tvFullAddress.setText(getNonNullValue(userData.getFullAddress()));
            tvEmail.setText(getNonNullValue(userData.getEmail()));
            tvPhone.setText(getNonNullValue(userData.getPhone()));
            tvTotalSpent.setText(MoneyFomat.formatToCurrency(userData.getTotalSpent()));

            String gender = userData.getGender();
            tvGenderText.setText(gender);

            if (gender.equalsIgnoreCase("Male")) {
                ivGenderImage.setImageResource(R.drawable.ic_gender_male);
            } else if (userData.getGender().equalsIgnoreCase("Female")) {
                ivGenderImage.setImageResource(R.drawable.ic_gender_female);
            } else if (userData.getGender().equalsIgnoreCase("Other")) {
                ivGenderImage.setImageResource(R.drawable.ic_gender_other);
            } else {
                ivGenderImage.setImageResource(R.drawable.ic_gender_other);
            }
        }
    }

    private void updateEditUI() {
        if (userData != null) {
            etName.setText(getNonNullValue(userData.getFirstName()));
            etLastName.setText(getNonNullValue(userData.getLastName()));
            etEmail.setText(getNonNullValue(userData.getEmail()));
            etPhone.setText(getNonNullValue(userData.getPhone()));
            etAddress.setText(getNonNullValue(userData.getFullAddress()));

            String gender = userData.getGender();
            if (gender.equalsIgnoreCase("Male")) {
                rgGender.check(R.id.rbMale);
            } else if (userData.getGender().equalsIgnoreCase("Female")) {
                rgGender.check(R.id.rbFemale);
            } else if (userData.getGender().equalsIgnoreCase("Other")) {
                rgGender.check(R.id.rbOther);
            } else {
                rgGender.clearCheck();
            }

            int role = userData.getRole();
            if (role == 0) {
                rgRole.check(R.id.rbAdmin);
            } else if (role == 1) {
                rgRole.check(R.id.rbGuest);
            } else {
                rgRole.clearCheck();
            }
        }
    }

    private String getNonNullValue(String value) {
        return (value == null || value.trim().isEmpty()) ? "Trống" : value;
    }

    private void switchToNormal() {
        updateNormalUI();

        btnEdit.setVisibility(View.VISIBLE);
        tvDetail.setVisibility(View.VISIBLE);
        card1.setVisibility(View.VISIBLE);
        card2.setVisibility(View.VISIBLE);

        tvRoleText.setVisibility(View.GONE);
        rgRole.setVisibility(View.GONE);
        btnSave.setVisibility(View.GONE);
        ivUserEdit.setVisibility(View.GONE);
        llUserEdit.setVisibility(View.GONE);
    }

    private void switchToEdit() {
        updateEditUI();

        btnEdit.setVisibility(View.GONE);
        tvDetail.setVisibility(View.GONE);
        card1.setVisibility(View.GONE);
        card2.setVisibility(View.GONE);

        tvRoleText.setVisibility(View.VISIBLE);
        rgRole.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.VISIBLE);
        ivUserEdit.setVisibility(View.VISIBLE);
        llUserEdit.setVisibility(View.VISIBLE);
    }
}