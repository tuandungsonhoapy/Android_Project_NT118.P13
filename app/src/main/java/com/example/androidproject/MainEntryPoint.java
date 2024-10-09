package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.features.auth.presentation.LoginActivity;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainEntryPoint extends AppCompatActivity {
    private static final String TAG = MainEntryPoint.class.getSimpleName();
    public static final long SPLASH_DELAY = 2000;
    public static final long FADE_IN_DURATION = 1500;
    private CompositeDisposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main_entry_point);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        animateLogo();
        startSplashDelay();
    }

    private void animateLogo() {
        ImageView logo = findViewById(R.id.ivLogo);
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(FADE_IN_DURATION);
        logo.startAnimation(fadeIn);
    }

    private void startSplashDelay() {
        new Handler().postDelayed(() -> {
            disposable = new CompositeDisposable();
            fetchCredential();
        }, SPLASH_DELAY);
    }

    private void fetchCredential() {
        boolean isLogin = true;
        if (isLogin) {
            navigateToMainScreen();
        } else {
            navigateToLoginScreen();
        }
    }

    // Navigate
    private void navigateToMainScreen() {
        Log.i(TAG, "Intent to Main Screen");
        Intent intent = new Intent(MainEntryPoint.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToLoginScreen() {
        Log.i(TAG, "Intent to Login Screen");
        Intent intent = new Intent(MainEntryPoint.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    // onDestroy
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}