package com.example.androidproject.features.admin_manager.presentation;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.MainActivity;
import com.example.androidproject.R;
import com.example.androidproject.core.credential.UserPreferences;
import com.example.androidproject.core.utils.NavigationUtils;

public class AdminBaseManagerLayout extends AppCompatActivity {
    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin__base_manager_layout);

        setupWindowInsets(findViewById(R.id.admin__base_manager_main));

        userPreferences = new UserPreferences(this);
        checkForPermission();

        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());
    }

    private void setupWindowInsets(View view) {
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
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
//            Toast.makeText(this, "Hello, Admin", Toast.LENGTH_SHORT).show();
        }
    }

    protected void setTitle(String title) {
        TextView titleTextView = findViewById(R.id.title_admin_base);
        if (titleTextView != null) {
            titleTextView.setText(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
