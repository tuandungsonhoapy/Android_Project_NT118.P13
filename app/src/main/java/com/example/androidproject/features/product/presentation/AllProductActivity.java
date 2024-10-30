package com.example.androidproject.features.product.presentation;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.core.utils.ItemData;
import com.example.androidproject.features.product.usecase.ProductUseCase;

import java.util.ArrayList;
import java.util.List;

public class AllProductActivity extends AppCompatActivity {
    private Spinner categorySpinner;
    private Spinner brandSpinner;
    private RecyclerView rvAllProduct;
    private ProductUseCase productUseCase = new ProductUseCase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_product);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setSpinnerValues();

        rvAllProduct = findViewById(R.id.rvAllProduct);
        rvAllProduct.setAdapter(new AllProductListAdapter(productUseCase.getProductsList(), this));
        rvAllProduct.setLayoutManager(new GridLayoutManager(this, 2));
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

    private void setSpinnerValues() {
        List<ItemData> categoryList = new ArrayList<>();
        List<ItemData> brandList = new ArrayList<>();
        ArrayAdapter<ItemData> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<ItemData> brandAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, brandList);
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner = findViewById(R.id.spinnerCategory);
        brandSpinner = findViewById(R.id.spinnerBrand);

        categoryList.add(new ItemData("0", "--Chọn danh mục--"));
        categoryList.add(new ItemData("1", "Category 1"));
        categoryList.add(new ItemData("2", "Category 2"));
        categoryList.add(new ItemData("3", "Category 3"));
        categoryList.add(new ItemData("4", "Category 4"));

        brandList.add(new ItemData("0", "--Chọn thương hiệu--"));
        brandList.add(new ItemData("1", "Brand 1"));
        brandList.add(new ItemData("2", "Brand 2"));
        brandList.add(new ItemData("3", "Brand 3"));
        brandList.add(new ItemData("4", "Brand 4"));

        categorySpinner.setAdapter(categoryAdapter);
        brandSpinner.setAdapter(brandAdapter);
    }
}