package com.example.androidproject.features.cart.presentation;
import android.content.Intent;
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
import com.example.androidproject.features.checkout.presentation.CheckoutActivity;

import java.util.concurrent.CompletableFuture;

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

        isCartEmpty().thenAccept(r -> {
            if(r) {
                emptyCartLayout.setVisibility(View.VISIBLE);
                cartItemsLayout.setVisibility(View.GONE);
                btnCheckout.setVisibility(View.GONE);
            } else {
                cartUseCase.getCurrentUserCart()
                        .thenAccept(r1 -> {
                            if(r1.isRight()) {
                                emptyCartLayout.setVisibility(View.GONE);
                                cartItemsLayout.setVisibility(View.VISIBLE);
                                btnCheckout.setVisibility(View.VISIBLE);
                                ListCartItemAdapter adapter = new ListCartItemAdapter(r1.getRight().getProducts(), this);
                                cartItemsLayout.setLayoutManager(new LinearLayoutManager(this));
                                cartItemsLayout.setAdapter(adapter);
                            }
                        });
            }
        });

        btnContinueShopping.setOnClickListener(v -> finish());

        btnCheckout.setOnClickListener(v -> openOrderReviewScreen());
    }

    private CompletableFuture<Boolean> isCartEmpty() {
        return cartUseCase.getCurrentUserCart()
                .thenApply(r -> {
                    if(r.isRight()) {
                        return r.getRight().getProducts().isEmpty();
                    } else {
                        return true;
                    }
                });
    }

    public void updateUI() {
        cartUseCase.getCurrentUserCart().thenAccept(r -> {
            if (r.isRight() && (r.getRight().getProducts() == null || r.getRight().getProducts().isEmpty())) {
                emptyCartLayout.setVisibility(View.VISIBLE);
                cartItemsLayout.setVisibility(View.GONE);
                btnCheckout.setVisibility(View.GONE);
            } else {
                emptyCartLayout.setVisibility(View.GONE);
                cartItemsLayout.setVisibility(View.VISIBLE);
                btnCheckout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void openOrderReviewScreen() {
        Intent intent = new Intent(this, CheckoutActivity.class);
        startActivity(intent);
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
