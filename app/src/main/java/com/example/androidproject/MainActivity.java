package com.example.androidproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.androidproject.core.credential.UserPreferences;
import com.example.androidproject.databinding.ActivityMainBinding;

import com.example.androidproject.features.home.presentation.HomeFragment;
import com.example.androidproject.features.setting.presentation.SettingFragment;
import com.example.androidproject.features.wishlist.presentation.FragmentWishlist;
import com.example.androidproject.features.store.presentation.StoreFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private UserPreferences userPreferences;
    private Bundle userDataBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        replaceFragment(new HomeFragment()); //df fragment
    }

    private void init() {
        userPreferences = new UserPreferences(this);
        userDataBundle = createUserDataBundle();

        setupNavigation();
        initInsets();

    }

    private void initInsets() {
        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private Bundle createUserDataBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("uid", (String) userPreferences.getUserDataByKey(UserPreferences.KEY_DOC_ID));
        bundle.putString("name", (String) userPreferences.getUserDataByKey(UserPreferences.KEY_FIRST_NAME));
        bundle.putString("email", (String) userPreferences.getUserDataByKey(UserPreferences.KEY_EMAIL));
        bundle.putBoolean("isAdmin", userPreferences.isAdmin());
        return bundle;
    }

    private void setupNavigation() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = getSelectedFragment(item.getItemId());
            //selectedFragment.setArguments(userDataBundle);
            replaceFragment(selectedFragment);
            return true;
        });
    }

    private Fragment getSelectedFragment(int itemId) {
        if (itemId == R.id.home) {
            return new HomeFragment();
        } else if (itemId == R.id.store) {
            return new StoreFragment();
        } else if (itemId == R.id.wishlist) {
            return new FragmentWishlist();
        } else if (itemId == R.id.setting) {
            return new SettingFragment();
        }

        return new HomeFragment(); //df

    }


    private void replaceFragment(Fragment fragment) {
        if (fragment != null) {
            fragment.setArguments(userDataBundle);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}