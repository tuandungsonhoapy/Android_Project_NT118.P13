package com.example.androidproject.features.admin_manager.presentation.user;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.R;

public class DetailUserAdminActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_user_admin);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupButtons();

        TextView userID = findViewById(R.id.tvUserID);
        TextView userName = findViewById(R.id.tvUserName);
        TextView userPhone = findViewById(R.id.tvUserPhone);
        TextView userEmail = findViewById(R.id.tvUserEmail);
        TextView userPaid = findViewById(R.id.tvUserPaid);

        userID.setText(getIntent().getStringExtra("id"));
        userName.setText(getIntent().getStringExtra("name"));
        userPhone.setText(getIntent().getStringExtra("phone"));
        userEmail.setText(getIntent().getStringExtra("email"));
        userPaid.setText("10.000.000");
    }

    private void setupButtons() {
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        btnEdit = findViewById(R.id.btn_admin_user_edit);
        btnEdit.setOnClickListener(v -> {
            Toast.makeText(this, "Edit button clicked", Toast.LENGTH_SHORT).show();
        });
    }
}