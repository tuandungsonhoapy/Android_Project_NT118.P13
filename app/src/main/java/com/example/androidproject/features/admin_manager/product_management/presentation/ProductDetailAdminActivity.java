package com.example.androidproject.features.admin_manager.product_management.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.androidproject.R;

public class ProductDetailAdminActivity extends AppCompatActivity {
    private TextView tvProductName, tvProductPrice, tvProductId, tvProductCategory, tvProductInventory, tvProductStatus;
    private ImageView productImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initialView();
    }

    public void initialView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvProductName = findViewById(R.id.tvProductDetailName);
        tvProductPrice = findViewById(R.id.tvProductDetailPrice);
        tvProductId = findViewById(R.id.tvProductDetailID);
        tvProductCategory = findViewById(R.id.tvCategory);
        tvProductInventory = findViewById(R.id.tvProductDetailInventory);
        tvProductStatus = findViewById(R.id.tvProductStatus);
        productImage = findViewById(R.id.ivProductImage);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        tvProductName.setText(bundle.getString("product_name"));
        tvProductPrice.setText(bundle.getString("product_price"));
        tvProductId.setText(bundle.getString("product_id"));
        tvProductCategory.setText(bundle.getString("brand_name"));
        tvProductInventory.setText(bundle.getString("product_inventory"));
        tvProductStatus.setText(bundle.getString("product_status"));

        Glide.with(this)
                .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7U_69OLMV-mASOOC1CdFjJ50-yUmU5hv5UQ&s")
                .into(productImage);
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