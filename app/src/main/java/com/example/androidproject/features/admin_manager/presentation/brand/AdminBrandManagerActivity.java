package com.example.androidproject.features.admin_manager.presentation.brand;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.admin_manager.presentation.AdminBaseManagerLayout;
import com.example.androidproject.features.admin_manager.presentation.widgets.ListBrandItemAdminAdapter;
import com.example.androidproject.features.admin_manager.presentation.widgets.ListCategoryItemAdminAdapter;
import com.example.androidproject.features.brand.data.entity.BrandEntity;
import com.example.androidproject.features.brand.usecase.BrandUseCase;
import com.example.androidproject.features.category.data.entity.CategoryEntity;
import com.example.androidproject.features.category.usecase.CategoryUseCase;

import java.util.ArrayList;
import java.util.List;

public class AdminBrandManagerActivity extends AdminBaseManagerLayout {
    private RecyclerView rvBrandList;
    private Button btnAddBrand, btnNext, btnPrevious;
    private EditText editSearch;
    private ImageView iconSearch;
    private TextView tvPageNumber;
    private BrandUseCase brandUseCase = new BrandUseCase();
    private ListBrandItemAdminAdapter adapter;
    private String search;
    private int page = 1;
    private int limit = 2;
    private int pageNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setTitle("Thương hiệu");
        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(R.layout.activity_admin_brand_manager, findViewById(R.id.content_container), true);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        List<BrandEntity> initialBrandList = new ArrayList<>();
        rvBrandList = findViewById(R.id.recycler_brands_view);
        adapter = new ListBrandItemAdminAdapter(this, this, initialBrandList);
        rvBrandList.setAdapter(adapter);
        rvBrandList.setLayoutManager(new LinearLayoutManager(this));

        getBrandList();

        btnAddBrand = findViewById(R.id.btnAddBrand);
        btnAddBrand.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddBrandActivity.class);
            startActivityForResult(intent, 1);
        });

        tvPageNumber = findViewById(R.id.tvPageNumber);
        editSearch = findViewById(R.id.edt_search_order_admin);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(v -> {
            page++;
            getBrandList();
        });

        btnPrevious = findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(v -> {
            if (page > 1) {
                page--;
                getBrandList();
            }
        });

        iconSearch = findViewById(R.id.img_search);
        iconSearch.setOnClickListener(v -> {
            search = editSearch.getText().toString();
            page = 1;
            getBrandList();
        });
    }

    private void getBrandList() {
        brandUseCase.getBrandList(String.valueOf(page),String.valueOf(limit), search).thenAccept(r -> {
            if (r.isRight()) {
                List<BrandEntity> brandList = r.getRight();
                adapter.updateBrandList(brandList);

                pageNumber = page;
                tvPageNumber.setText(String.format("Trang %d", pageNumber));

                btnPrevious.setEnabled(page > 1);
                if (brandList.size() >= limit) {
                    btnNext.setEnabled(true);
                } else {
                    btnNext.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            boolean brandAdded = data.getBooleanExtra("added_brand", false);
            boolean brandDeleted = data.getBooleanExtra("deleted_brand", false);
            boolean brandUpdated = data.getBooleanExtra("updated_brand", false);
            if (brandAdded || brandDeleted || brandUpdated) {
                getBrandList();
            }
        }
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