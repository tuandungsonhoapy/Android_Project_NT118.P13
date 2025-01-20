package com.example.androidproject.features.admin_manager.presentation.admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.admin_manager.data.repository.UserRepository;
import com.example.androidproject.features.admin_manager.presentation.AdminBaseManagerLayout;
import com.example.androidproject.features.admin_manager.presentation.widgets.UserManagerAdapter;
import com.example.androidproject.features.auth.data.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class AdminManagerAdminActivity extends AdminBaseManagerLayout {
    private List<UserEntity> allUsers;
    private List<UserEntity> filteredUsers;
    private UserManagerAdapter userManagerAdapter;
    private RecyclerView rvUserList;
    private EditText etSearch;
    private ImageView btnSearch, btnClear, ivEmpty;
    private ImageButton btnAddAdmin;

    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setTitle("Quản lý tài khoản admin");

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadUsers();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(R.layout.activity_admin_user_manager, findViewById(R.id.content_container), true);
        userRepository = new UserRepository();
        filteredUsers = new ArrayList<>();

        initInsets();
        initViews();
        setupRecyclerView();

        loadUsers();
    }

    private void initInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initViews() {
        etSearch = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);
        btnClear = findViewById(R.id.btnClear);
        btnAddAdmin = findViewById(R.id.btnAddAdmin);
        ivEmpty = findViewById(R.id.ivEmpty);

        btnSearch.setOnClickListener(v -> searchUsers());
        btnClear.setOnClickListener(v -> clearSearch());
        btnAddAdmin.setVisibility(View.VISIBLE);
        btnAddAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddAdminAdminActivity.class);
            startActivity(intent);
        });

        etSearch.addTextChangedListener(setupSearchEdit());
    }

    private void setupRecyclerView() {
        rvUserList = findViewById(R.id.rvUser);
        rvUserList.setLayoutManager(new LinearLayoutManager(this));
        userManagerAdapter = new UserManagerAdapter(this, filteredUsers);
        rvUserList.setAdapter(userManagerAdapter);
    }

    private TextWatcher setupSearchEdit() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                searchUsers();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    btnClear.setVisibility(View.VISIBLE);
                } else {
                    btnClear.setVisibility(View.GONE);
                }
            }
        };
    }

    private void loadUsers() {
        userRepository.getAllAdmins().thenAccept(result -> {
            if (result.isRight()) {
                allUsers = result.getRight();
                filteredUsers = new ArrayList<>(allUsers);
                userManagerAdapter.updateUsers(filteredUsers);
            } else {
                Log.e("this", "Failed to load users: " + result.getLeft().getErrorMessage());
            }
        });
    }

    private void searchUsers() {
        String query = etSearch.getText().toString().toLowerCase();
        if (query.isEmpty()) {
            filteredUsers = new ArrayList<>(allUsers);
        } else {
            filteredUsers = new ArrayList<>();
            for (UserEntity user : allUsers) {
                if (user.getFirstName().toLowerCase().contains(query) ||
                        user.getLastName().toLowerCase().contains(query) ||
                        user.getId().toLowerCase().contains(query) ||
                        user.getEmail().toLowerCase().contains(query)) {
                    filteredUsers.add(user);
                }
            }
        }
        userManagerAdapter.updateUsers(filteredUsers);
        if (filteredUsers.isEmpty()) {
            ivEmpty.setVisibility(View.VISIBLE);
        } else {
            ivEmpty.setVisibility(View.GONE);
        }
    }

    private void clearSearch() {
        etSearch.setText("");
        btnClear.setVisibility(View.GONE);
        filteredUsers = new ArrayList<>(allUsers);
        userManagerAdapter.updateUsers(filteredUsers);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}