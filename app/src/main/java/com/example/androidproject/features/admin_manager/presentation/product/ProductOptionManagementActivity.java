package com.example.androidproject.features.admin_manager.presentation.product;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.admin_manager.presentation.widgets.ProductOptionListAdapter;
import com.example.androidproject.features.product.data.entity.ProductOption;
import com.example.androidproject.features.product.data.model.ProductModelFB;

import java.util.ArrayList;
import java.util.List;

public class ProductOptionManagementActivity extends AppCompatActivity {
    private RecyclerView recyclerView_option;
    private ProductOptionListAdapter adapter;
    private Button btnAddOption;
    private ActivityResultLauncher<Intent> addOptionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_option_management);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();
    }

    private void initView() {
        List<ProductOption> productOptions = getIntent().getExtras().getParcelableArrayList("product_options");
        String productId = getIntent().getStringExtra("product_id");

        int totalQuantity = 0;

        if (productOptions != null && !productOptions.isEmpty()) {
            // Duyệt qua từng ProductOption trong danh sách
            for (ProductOption option : productOptions) {
                // Cộng dồn giá trị quantity vào totalQuantity
                totalQuantity += option.getQuantity();
            }
        } else {
            productOptions = new ArrayList<>();
        }

        btnAddOption = findViewById(R.id.btnAddProductOption);

        Intent intent = new Intent(this, AddProductOptionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("product_id", productId);
        bundle.putInt("total_quantity", totalQuantity);
        intent.putExtras(bundle);

        adapter = new ProductOptionListAdapter(this, productOptions);

        recyclerView_option = findViewById(R.id.recycler_product_options);
        recyclerView_option.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_option.setAdapter(adapter);

        addOptionLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bundle resultBundle = result.getData().getExtras();
                        if (resultBundle != null) {
                            // Lấy danh sách ProductOption từ Bundle
                            List<ProductOption> updatedOptions = resultBundle.getParcelableArrayList("product_options");
                            if (updatedOptions != null) {
                                // Cập nhật adapter hoặc xử lý dữ liệu như bạn muốn
                                adapter.setProductOptionList(updatedOptions);
                                Log.d("UpdatedOptions", "Options updated: " + updatedOptions.size());
//                                Intent backIntent = new Intent();
//                                resultBundle.putParcelableArrayList("product_options", new ArrayList<>(updatedOptions));
//                                backIntent.putExtras(resultBundle);
//                                setResult(RESULT_OK, backIntent);
//                                finish();
                            }
                        }
                    }
                }
        );

        btnAddOption.setOnClickListener(v -> {
            addOptionLauncher.launch(intent);
        });
    }
}