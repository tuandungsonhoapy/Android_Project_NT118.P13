package com.example.androidproject.features.cart.presentation;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.cart.usecase.CartUseCase;

public class CartActivity extends AppCompatActivity {

    private LinearLayout emptyCartLayout;
    private RecyclerView cartItemsLayout;
    private Button btnCheckout, btnContinueShopping;
    private CartUseCase cartUseCase = new CartUseCase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        emptyCartLayout = findViewById(R.id.empty_cart_layout);
        cartItemsLayout = findViewById(R.id.rvCartItem);
        btnCheckout = findViewById(R.id.btn_checkout);
        btnContinueShopping = findViewById(R.id.btn_continue_shopping);

        if (isCartEmpty()) {
            emptyCartLayout.setVisibility(View.VISIBLE);
            cartItemsLayout.setVisibility(View.GONE);
        } else {
            emptyCartLayout.setVisibility(View.GONE);
            cartItemsLayout.setVisibility(View.VISIBLE);
            btnCheckout.setVisibility(View.VISIBLE);
            cartItemsLayout.setAdapter(new ListCartItemAdapter(cartUseCase.getCart(), this));
            cartItemsLayout.setLayoutManager(new LinearLayoutManager(this));
        }

        btnContinueShopping.setOnClickListener(v -> finish());

        btnCheckout.setOnClickListener(v -> openOrderReviewScreen());
    }

    private boolean isCartEmpty() {
        return false;
    }

    private void openOrderReviewScreen() {
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
