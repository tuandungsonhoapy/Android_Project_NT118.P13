package com.example.androidproject.features.admin_manager.presentation.product;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidproject.R;
import com.example.androidproject.features.admin_manager.presentation.widgets.ProductOptionListAdapter;
import com.example.androidproject.features.product.data.entity.ProductOption;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailAdminActivity extends AppCompatActivity {
    private TextView tvProductName, tvProductPrice, tvProductId, tvProductCategory, tvProductInventory, tvProductStatus;
    private ImageView productImage;
    private ImageButton btnBack;
    private Button btnEdit, btnWatchOptions;
    private ActivityResultLauncher<Intent> launcher;
    private ArrayList<ProductOption> productOptions;

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
        btnWatchOptions = findViewById(R.id.btnWatchProductOptions);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        tvProductName.setText(bundle.getString("product_name"));
        tvProductPrice.setText(bundle.getString("product_price"));
        tvProductId.setText(bundle.getString("product_id"));
        tvProductCategory.setText(bundle.getString("brand_name"));
        tvProductInventory.setText(bundle.getString("product_inventory"));
        tvProductStatus.setText(bundle.getString("product_status"));
        productOptions = getIntent().getExtras().getParcelableArrayList("product_options");

        Log.d("ProductDetailAdminActivity", "Product options: " + productOptions.size());

        List<String> images = (List<String>) bundle.get("product_images");

        Intent productOptionIntent = new Intent(this, ProductOptionManagementActivity.class);
        Bundle productOptionbundle = new Bundle();
        productOptionbundle.putParcelableArrayList("product_options", new ArrayList<>(productOptions));
        productOptionbundle.putString("product_id", bundle.getString("product_id"));
        productOptionIntent.putExtras(productOptionbundle);

        Glide.with(this)
                .load(images.get(0))
                .into(productImage);

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bundle resultBundle = result.getData().getExtras();
                        if (resultBundle != null) {
                            // Lấy danh sách ProductOption từ Bundle
                            List<ProductOption> updatedOptions = resultBundle.getParcelableArrayList("product_options");
                            if (updatedOptions != null) {
                                productOptions = new ArrayList<>(updatedOptions);
                            }
                        }
                    }
                }
        );

        btnWatchOptions.setOnClickListener(v -> {
            launcher.launch(productOptionIntent);
        });
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