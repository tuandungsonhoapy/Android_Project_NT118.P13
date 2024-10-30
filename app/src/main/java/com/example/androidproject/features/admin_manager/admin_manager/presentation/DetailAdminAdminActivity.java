package com.example.androidproject.features.admin_manager.admin_manager.presentation;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.R;

public class DetailAdminAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_admin_admin);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tvAdminID = findViewById(R.id.tvAdminID);
        TextView tvAdminName = findViewById(R.id.tvAdminName);
        TextView tvAdminEmail = findViewById(R.id.tvAdminEmail);
        TextView tvAdminPhone = findViewById(R.id.tvAdminPhone);
        TextView tvAdminRole = findViewById(R.id.tvAdminRole);
        TextView tvAdminAddress = findViewById(R.id.tvAdminAddress);

        tvAdminID.setText("user000001");
        tvAdminName.setText("Nguyen Van A");
        tvAdminEmail.setText("hihi@gmail.com");
        tvAdminPhone.setText("0123456789");
        tvAdminRole.setText("Admin");
        tvAdminAddress.setText("Ha Noi");

        tvAdminRole.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.pending_order_status, null));
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