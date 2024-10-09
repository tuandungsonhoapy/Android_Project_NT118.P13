package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.features.auth.presentation.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthLoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    //    private Spinner sp1;
    private TextView tvUserName;
    private Button btnLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.auth__login_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Map UI components
        btnLogout = findViewById(R.id.btnLogin);

        // Get current user and display their name
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userName = user.getEmail();  // Using email as the user name, you can modify it if needed
            tvUserName.setText("Welcome, " + userName);
        }

        btnLogout.setOnClickListener(v -> handleLogout());

//        sp1 = findViewById(R.id.sp1);
//        String[] list = {"IPhone", "Xiaomi", "Oppo", "Samsung"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item, list);
//        sp1.setAdapter(adapter);
    }

    private void handleLogout() {
        mAuth.signOut();
        Intent intent = new Intent(AuthLoginActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}