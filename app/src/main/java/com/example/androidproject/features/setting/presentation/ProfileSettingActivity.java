package com.example.androidproject.features.setting.presentation;

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
import com.example.androidproject.core.credential.UserPreferences;
import com.example.androidproject.core.credential.data.entity.UserPrefEntity;
import com.example.androidproject.core.utils.MoneyFomat;
import com.example.androidproject.core.utils.NavigationUtils;
import com.example.androidproject.core.utils.UserTierUtils;
import com.example.androidproject.features.auth.data.entity.UserEntity;
import com.example.androidproject.features.setting.data.repository.ProfileRepository;

public class ProfileSettingActivity extends AppCompatActivity {

    //views
    private TextView tvDetail, tvTier, tvFullName, tvGenderText, tvUserId, tvFullAddress, tvEmail, tvPhone, tvTotalSpent;
    private EditText etName, etLastName, etAddress, etEmail, etPhone;
    private RadioGroup rgGender;
    private Button btnEdit, btnSave;
    private CardView card1, card2;
    private ImageView btnBack, ivUserImage, ivGenderImage, ivUserEdit;
    private LinearLayout llUserEdit;

    //others
    private UserPreferences userPreferences;
    private UserPrefEntity userData;
    private ProfileRepository profileRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_setting);

        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        userData = userPreferences.getUserEntity();
        profileRepository = new ProfileRepository();

        updateNormalUI();
        updateEditUI();
    }

    private void init() {
        userPreferences = new UserPreferences(this);
        userData = userPreferences.getUserEntity();

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
        tvTier = findViewById(R.id.tvTier);
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
        String firstName = etName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String gender = getSelectedGender();

        if (isInputValid(firstName, lastName, email, phone, gender)) {
            UserEntity updatedUser = createUpdatedUser(firstName, lastName, email, phone, gender);
            updateProfile(updatedUser);
        } else {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isInputValid(String firstName, String lastName, String email, String phone, String gender) {
        return !firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !phone.isEmpty() && gender != null;
    }

    private UserEntity createUpdatedUser(String firstName, String lastName, String email, String phone, String gender) {
        UserEntity updatedUser = new UserEntity();
        updatedUser.setFirstName(firstName);
        updatedUser.setLastName(lastName);
        updatedUser.setEmail(email);
        updatedUser.setPhone(phone);
        updatedUser.setGender(gender);
        return updatedUser;
    }

    private void updateProfile(UserEntity updatedUser) {
        profileRepository.updateUserProfile(updatedUser).thenAccept(result -> {
            if (result.isRight()) {
                updateUserPreferences(updatedUser);
                Toast.makeText(ProfileSettingActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                switchToNormal();
            } else {
                Toast.makeText(ProfileSettingActivity.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                switchToEdit();
            }
        });
    }

    private void updateUserPreferences(UserEntity updatedUser) {
        userPreferences.setUserDataByKey(UserPreferences.KEY_FIRST_NAME, updatedUser.getFirstName());
        userPreferences.setUserDataByKey(UserPreferences.KEY_LAST_NAME, updatedUser.getLastName());
        userPreferences.setUserDataByKey(UserPreferences.KEY_EMAIL, updatedUser.getEmail());
        userPreferences.setUserDataByKey(UserPreferences.KEY_PHONE, updatedUser.getPhone());
        userPreferences.setUserDataByKey(UserPreferences.KEY_GENDER, updatedUser.getGender());
        userData = userPreferences.getUserEntity();
    }

    private String getSelectedGender() {
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

    private void updateNormalUI() {
        if (userData != null) {
            ivUserImage.setImageResource(R.drawable.logo_techo_without_text);

            tvTier.setText(UserTierUtils.getTierStringFromTier(userData.getTier()));
            tvFullName.setText(userData.getLastName() + " " + userData.getFirstName());
            tvGenderText.setText(userData.getGender());
            tvUserId.setText(userData.getId());
            tvFullAddress.setText(userData.getFullAddress());
            tvEmail.setText(userData.getEmail());
            tvPhone.setText(userData.getPhone());
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
            etName.setText(userData.getFirstName());
            etLastName.setText(userData.getLastName());
            etEmail.setText(userData.getEmail());
            etPhone.setText(userData.getPhone());
            etAddress.setText(userData.getFullAddress());

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
        }
    }

    private void switchToNormal() {
        updateNormalUI();

        btnEdit.setVisibility(View.VISIBLE);
        tvDetail.setVisibility(View.VISIBLE);
        card1.setVisibility(View.VISIBLE);
        card2.setVisibility(View.VISIBLE);

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

        btnSave.setVisibility(View.VISIBLE);
        ivUserEdit.setVisibility(View.VISIBLE);
        llUserEdit.setVisibility(View.VISIBLE);
    }
}