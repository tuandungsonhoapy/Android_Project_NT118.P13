package com.example.androidproject.features.admin_manager.presentation;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.admin_manager.admin_manager.presentation.AddAdminAdminActivity;
import com.example.androidproject.features.admin_manager.admin_manager.presentation.ListAdminItemAdminAdapter;
import com.example.androidproject.features.admin_manager.admin_manager.usecase.AdminManagerUseCase;

public class AdminAdminManagerActivity extends AppCompatActivity {
    AdminManagerUseCase adminManagerUseCase = new AdminManagerUseCase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_admin_manager);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_back_arrow, null);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationIcon(upArrow);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView rvAdminManager = findViewById(R.id.rvAdminManager);
        Button btnAddAdmin = findViewById(R.id.addAdmin);

        rvAdminManager.setAdapter(new ListAdminItemAdminAdapter(adminManagerUseCase.getUserList(), this));
        rvAdminManager.setLayoutManager(new LinearLayoutManager(this));

        btnAddAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddAdminAdminActivity.class);
            startActivity(intent);
        });
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