package com.example.androidproject.features.admin_manager.presentation.user;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;

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
import com.example.androidproject.features.admin_manager.presentation.AdminBaseManagerLayout;
import com.example.androidproject.features.admin_manager.presentation.widgets.ListUserItemAdminAdapter;
import com.example.androidproject.features.admin_manager.usecase.UserManagerUseCase;

public class AdminUserManagerActivity extends AdminBaseManagerLayout {
    private UserManagerUseCase userManagerUseCase = new UserManagerUseCase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setTitle("Quản lý người dùng");

        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(R.layout.activity_admin_user_manager, findViewById(R.id.content_container), true);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView rvUserList = findViewById(R.id.recycler_users_view);
        rvUserList.setAdapter(new ListUserItemAdminAdapter(userManagerUseCase.getUserList(), this));
        rvUserList.setLayoutManager(new LinearLayoutManager(this));

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