package com.example.androidproject.features.admin_manager.presentation.product;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.admin_manager.presentation.AdminBaseManagerLayout;
import com.example.androidproject.features.admin_manager.presentation.widgets.ProductManagementAdapter;
import com.example.androidproject.features.admin_manager.presentation.widgets.ProductManagementStatusAdapter;
import com.example.androidproject.features.category.data.entity.CategoryEntity;
import com.example.androidproject.features.home.usecase.HomeUseCase;
import com.example.androidproject.features.product.data.entity.ProductEntity;
import com.example.androidproject.features.product.usecase.ProductUseCase;

import java.util.ArrayList;
import java.util.List;

public class AdminProductManagementActivity extends AdminBaseManagerLayout {
    private RecyclerView recyclerView_status, recyclerView_products;
    private Button btn_add_product, btnNext, btnPrevious;
    private TextView tvPageNumber;
    private ProductUseCase productUseCase = new ProductUseCase();
    private ProductManagementAdapter adapter;
    private String search = "";
    private int page = 1;
    private int limit = 5;
    private int pageNumber = 1;
    private static final int ADD_PRODUCT_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setTitle("Quản lý sản phẩm");
        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(R.layout.activity_admin_product_management, findViewById(R.id.content_container), true);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initialView();
    }

    public void initialView(){
        recyclerView_status = findViewById(R.id.recycler_products_view_status);
        recyclerView_status.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView_status.setAdapter(new ProductManagementStatusAdapter(this));

        tvPageNumber = findViewById(R.id.tvPageNumber);
        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);
        btn_add_product = findViewById(R.id.btnAddProduct);
        btn_add_product.setOnClickListener(v -> {
            // Add product
            Intent intent = new Intent(this, AddProductActivity.class);
            startActivityForResult(intent, ADD_PRODUCT_REQUEST_CODE);
        });

        List<ProductEntity> initialProductList = new ArrayList<>();
        adapter = new ProductManagementAdapter(this, initialProductList);

        recyclerView_products = findViewById(R.id.recycler_products_view);
        recyclerView_products.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_products.setAdapter(adapter);

        getProductList();
    }

    public void getProductList() {
        productUseCase.getProducts(String.valueOf(page),String.valueOf(limit), search).thenAccept(r -> {
            if (r.isRight()) {
                List<ProductEntity> productList = r.getRight();
                adapter.setProductList(productList);

                pageNumber = page;
                tvPageNumber.setText(String.format("Trang %d", pageNumber));

                btnPrevious.setEnabled(page > 1);
                if (productList.size() >= limit) {
                    btnNext.setEnabled(true);
                } else {
                    btnNext.setEnabled(false);
                }
            }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_PRODUCT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Gọi hàm cập nhật danh sách sản phẩm
            getProductList();
        }
    }

}