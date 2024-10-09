package com.example.androidproject.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.R;

public class RegisterActivity extends AppCompatActivity {
    private long backPressTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(backPressTime + 2000 > System.currentTimeMillis()){
                    backToast.cancel();
                    finish();
                } else {
                    backToast = Toast.makeText(RegisterActivity.this, "Nhấn lại lần nữa để thoát", Toast.LENGTH_SHORT);
                    backToast.show();
                }
                backPressTime = System.currentTimeMillis();
            }
        };

        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}