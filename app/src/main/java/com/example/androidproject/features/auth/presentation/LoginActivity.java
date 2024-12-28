package com.example.androidproject.features.auth.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.MainActivity;
import com.example.androidproject.R;
import com.example.androidproject.core.utils.NavigationUtils;
import com.example.androidproject.features.auth.data.repository.AuthRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.CompletableFuture;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    // declare views
    private ProgressBar loadingIndicator;
    private Button btnLogin;
    private EditText etEmail, etPassword;
    private TextView btnToRegister;

    // others
    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.auth__login_page);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = authRepository.getCurrentUser();
        if (currentUser != null) NavigationUtils.navigateTo(LoginActivity.this, MainActivity.class);
        hideLoading();
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
        loadingIndicator = findViewById(R.id.loadingIndicator);
        btnLogin = findViewById(R.id.btnLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnToRegister = findViewById(R.id.btnToRegister);
    }

    private void setupClickListeners() {
        btnLogin.setOnClickListener(v -> handleLogin());
        btnToRegister.setOnClickListener(v -> NavigationUtils.navigateTo(LoginActivity.this, RegisterActivity.class));
    }

    // Click events
    private void handleLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (!email.isEmpty() && !password.isEmpty()) {
            showLoading();
            CompletableFuture<FirebaseUser> loginFuture = authRepository.login(email, password);

            loginFuture.thenAccept(user -> {
                if (user != null)
                    NavigationUtils.navigateTo(LoginActivity.this, MainActivity.class);
            }).exceptionally(ex -> {
                Log.w(TAG, "signInWithEmail:failure", ex);
                Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                return null;
            });
        } else {
            Toast.makeText(this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
        }
    }

    // Show and hide loading indicator
    private void hideLoading() {
        loadingIndicator.setVisibility(ProgressBar.GONE);
    }

    private void showLoading() {
        loadingIndicator.setVisibility(ProgressBar.VISIBLE);
    }

}