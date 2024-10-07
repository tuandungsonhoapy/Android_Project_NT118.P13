package com.example.androidproject.features.auth.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.MainActivity;
import com.example.androidproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    // Firebase
    private FirebaseAuth mAuth;

    private ProgressBar loadingIndicator;
    private Button btnLogin;
    private EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.auth__login_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            int horizontalPadding = getResources().getDimensionPixelSize(R.dimen.screen_horizontal);
            int verticalPadding = getResources().getDimensionPixelSize(R.dimen.screen_vertical);

            v.setPadding(systemBars.left + horizontalPadding, systemBars.top + verticalPadding, systemBars.right + horizontalPadding, systemBars.bottom + verticalPadding);
            return insets;
        });

        // Firebase
        mAuth = FirebaseAuth.getInstance();

        // Map UI components
        loadingIndicator = findViewById(R.id.loadingIndicator);
        btnLogin = findViewById(R.id.btnLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        // Setup click events
        btnLogin.setOnClickListener(v -> handleLogin());

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            reload(currentUser);
        }
        hideLoading();
    }

    // Click events
    private void handleLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (!email.isEmpty() && !password.isEmpty()) {
            showLoading();
            signIn(email, password);
        } else {
            Toast.makeText(this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
        }
    }

    // Firebase auth
    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    // Show and hide loading indicator
    private void hideLoading() {
        loadingIndicator.setVisibility(ProgressBar.GONE);
    }

    private void showLoading() {
        loadingIndicator.setVisibility(ProgressBar.VISIBLE);
    }

    // Update
    private void reload(FirebaseUser user) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            reload(user);
        }
    }
}