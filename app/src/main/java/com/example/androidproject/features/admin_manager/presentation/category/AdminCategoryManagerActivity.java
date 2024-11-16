package com.example.androidproject.features.admin_manager.presentation.category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.admin_manager.presentation.widgets.ListCategoryItemAdminAdapter;
import com.example.androidproject.features.admin_manager.presentation.AdminBaseManagerLayout;
import com.example.androidproject.features.category.data.entity.CategoryEntity;
import com.example.androidproject.features.category.usecase.CategoryUseCase;

import java.util.ArrayList;
import java.util.List;

public class AdminCategoryManagerActivity extends AdminBaseManagerLayout {
    private RecyclerView rvCategoryList;
    private Button btnAddCategory;
    private CategoryUseCase categoryUseCase = new CategoryUseCase();
    ListCategoryItemAdminAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setTitle("Danh mục sản phẩm");

        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(R.layout.activity_admin_category_manager, findViewById(R.id.content_container), true);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        List<CategoryEntity> initialCategoryList = new ArrayList<>();
        rvCategoryList = findViewById(R.id.recycler_categories_view);
        adapter = new ListCategoryItemAdminAdapter(initialCategoryList, this);
        rvCategoryList.setAdapter(adapter);
        rvCategoryList.setLayoutManager(new LinearLayoutManager(this));

        getCategoryList();

        btnAddCategory = findViewById(R.id.btnAddCategory);
        btnAddCategory.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddCategoryActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    private void getCategoryList() {
        categoryUseCase.getCategory("1","10").thenAccept(r -> {
            if (r.isRight()) {
                List<CategoryEntity> categoryList = r.getRight();
                adapter.updateCategoryList(categoryList);
            } else {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            boolean categoryAdded = data.getBooleanExtra("added_category", false);
            if (categoryAdded) {
                getCategoryList();
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