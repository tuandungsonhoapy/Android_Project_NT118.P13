package com.example.androidproject.features.checkout.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.MainActivity;
import com.example.androidproject.R;
import com.example.androidproject.features.checkout.usecase.CheckoutUseCase;

public class CheckoutActivity extends AppCompatActivity {
    private LinearLayout paymentSuccessLayout, llTotalPrice;
    private RecyclerView rvCheckoutItem;
    private Button btnPayment, btnContinueShopping;
    private CheckoutUseCase checkoutUseCase = new CheckoutUseCase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        paymentSuccessLayout = findViewById(R.id.ll_payment_success);
        rvCheckoutItem = findViewById(R.id.rvCheckoutItem);
        btnPayment = findViewById(R.id.btnPayment);
        btnContinueShopping = findViewById(R.id.btn_continue_shopping);
        llTotalPrice = findViewById(R.id.llTotalPrice);

        rvCheckoutItem.setAdapter(new ListCheckoutItemAdapter(checkoutUseCase.getCheckout(), this));
        rvCheckoutItem.setLayoutManager(new LinearLayoutManager(this));

        btnPayment.setOnClickListener(v -> {
            paymentSuccessLayout.setVisibility(paymentSuccessLayout.getVisibility() == android.view.View.VISIBLE ? android.view.View.GONE : android.view.View.VISIBLE);
            btnPayment.setVisibility(View.GONE);
            rvCheckoutItem.setVisibility(View.GONE);
            llTotalPrice.setVisibility(View.GONE);
            btnContinueShopping.setVisibility(View.VISIBLE);
        });

        btnContinueShopping.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
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