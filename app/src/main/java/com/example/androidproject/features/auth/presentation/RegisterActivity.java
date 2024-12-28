package com.example.androidproject.features.auth.presentation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.MainActivity;
import com.example.androidproject.R;
import com.example.androidproject.core.utils.NavigationUtils;
import com.example.androidproject.features.auth.data.entity.UserEntity;
import com.example.androidproject.features.auth.data.repository.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.CompletableFuture;

public class RegisterActivity extends AppCompatActivity {
    // declare views
    private Button btnRegister;
    private TextView btnToLogin;
    private EditText etFirstName, etLastName, etEmail, etPhone, etPassword, etConfirmPassword;
    private RadioGroup rgGender;

    //others
    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.auth__register_page);
        init();
    }

    private void init() {
        authRepository = new AuthRepository();

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
        btnToLogin = findViewById(R.id.btnToLogin);
        btnRegister = findViewById(R.id.btnRegister);

        etFirstName = findViewById(R.id.etName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etVerifiedPassword);
        rgGender = findViewById(R.id.rgGender);
    }

    private void setupClickListeners() {
        btnToLogin.setOnClickListener(v -> NavigationUtils.navigateTo(RegisterActivity.this, LoginActivity.class));

        btnRegister.setOnClickListener(v -> handleRegister());
    }

    private void handleRegister() {
        // form
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String gender = getSelectedGender();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (validateInput(firstName, lastName, email, phone, password, confirmPassword, gender)) {
            CompletableFuture<FirebaseUser> registrationFuture = authRepository.register(email, password);

            registrationFuture
                    .thenCompose(firebaseUser -> {
                        // default
                        String uid = "";
                        Integer role = 1;
                        Integer tier = 0;
                        Long totalSpent = 0L;
                        String addressId = "";
                        UserEntity userEntity = new UserEntity(uid, role, tier, totalSpent, addressId, firstName, lastName, gender, email, phone);

                        userEntity.setUid(firebaseUser.getUid());
                        return authRepository.saveUserToFirestore(userEntity);
                    })
                    .thenRun(() -> NavigationUtils.navigateTo(RegisterActivity.this, MainActivity.class))
                    .exceptionally(ex -> {
                        Toast.makeText(RegisterActivity.this, "Registration failed: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        return null;
                    });
        }
    }

    private String getSelectedGender() {
        int selectedId = rgGender.getCheckedRadioButtonId();
        if (selectedId == R.id.rbMale) return "Male";
        if (selectedId == R.id.rbFemale) return "Female";
        if (selectedId == R.id.rbOther) return "Other";
        return null;
    }

    private boolean validateInput(String firstName, String lastName, String email, String phone, String password, String confirmPassword, String gender) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || gender == null) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidPhone(phone)) {
            Toast.makeText(this, "Invalid phone", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidPassword(password)) {
            Toast.makeText(this, "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPhone(String phone) {
        String regex = "^((\\+84)|0)([35789])[0-9]{8}$";
        return phone.matches(regex);
    }

    private boolean isValidPassword(String password) {
        String regex = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%&*+\\-_?]).{6,}";
        return password.matches(regex);
    }
}