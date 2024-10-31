package com.example.androidproject.features.admin_manager.presentation.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ImageButton btnBack;
    private Button btnEdit;

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
        setupButtons();
        initialView();
    }

    public void initialView() {

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

    private void setupButtons() {
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        btnEdit = findViewById(R.id.btn_admin_product_edit);
        btnEdit.setOnClickListener(v -> {
            Toast.makeText(this, "Edit button clicked", Toast.LENGTH_SHORT).show();
        });
    }
}