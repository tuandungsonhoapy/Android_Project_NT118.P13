package com.example.androidproject.features.admin_manager.presentation.product;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
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
import com.example.androidproject.features.home.usecase.HomeUseCase;

public class AdminProductManagementActivity extends AdminBaseManagerLayout {
    private RecyclerView recyclerView_status, recyclerView_products;
    private Button btn_add_product;

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

        btn_add_product = findViewById(R.id.btnAddProduct);
        btn_add_product.setOnClickListener(v -> {
            // Add product
            Intent intent = new Intent(this, AddProductActivity.class);
            startActivity(intent);
        });

        recyclerView_products = findViewById(R.id.recycler_products_view);
        recyclerView_products.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_products.setAdapter(new ProductManagementAdapter(this, new HomeUseCase().getProductsList()));
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