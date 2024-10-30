package com.example.androidproject.features.admin_manager.user_manager.presentation;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.R;

public class DetailUserAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_user_admin);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}