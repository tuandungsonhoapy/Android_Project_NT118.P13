package com.example.androidproject.features.admin.presentation;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.androidproject.MainActivity;
import com.example.androidproject.R;
import com.example.androidproject.core.credential.UserPreferences;
import com.example.androidproject.core.utils.NavigationUtils;
import com.example.androidproject.databinding.ActivityAdminHomeBinding;
import com.example.androidproject.databinding.ActivityMainBinding;
import com.example.androidproject.features.admin_dashboard.presentation.AdminDashboardFragment;
import com.example.androidproject.features.admin_manager.presentation.AdminManagerFragment;
import com.example.androidproject.features.home.presentation.HomeFragment;
import com.example.androidproject.features.setting.presentation.SettingFragment;
import com.example.androidproject.features.store.presentation.StoreFragment;

public class AdminHomeActivity extends AppCompatActivity {
    private UserPreferences userPreferences;

    ActivityAdminHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new AdminDashboardFragment());
        binding = ActivityAdminHomeBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        userPreferences = new UserPreferences(this);
        checkForPermission();

        binding.bottomAdminNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.dashboard) {
                replaceFragment(new AdminDashboardFragment());
            } else if(item.getItemId() == R.id.manager) {
                replaceFragment(new AdminManagerFragment());
            }

            return true;
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void checkForPermission() {
        if (!userPreferences.isAdmin()){
            Toast.makeText(this, "No Permission", Toast.LENGTH_SHORT).show();
            NavigationUtils.navigateTo(this, MainActivity.class);
        } else {
            Toast.makeText(this, "Hello, Admin", Toast.LENGTH_SHORT).show();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_admin, fragment);
        fragmentTransaction.commit();
    }
}