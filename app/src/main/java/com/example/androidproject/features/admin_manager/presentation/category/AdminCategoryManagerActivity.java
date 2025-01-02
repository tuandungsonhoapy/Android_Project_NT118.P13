package com.example.androidproject.features.admin_manager.presentation.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
    private Button btnAddCategory, btnNext, btnPrevious;
    private EditText editSearch;
    private ImageView iconSearch;
    private TextView tvPageNumber;
    private CategoryUseCase categoryUseCase = new CategoryUseCase();
    private ListCategoryItemAdminAdapter adapter;
    private String search;
    private int page = 1;
    private int limit = 2;
    private int pageNumber = 1;

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
        adapter = new ListCategoryItemAdminAdapter(initialCategoryList, this, this);
        rvCategoryList.setAdapter(adapter);
        rvCategoryList.setLayoutManager(new LinearLayoutManager(this));

        getCategoryList();

        btnAddCategory = findViewById(R.id.btnAddCategory);
        btnAddCategory.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddCategoryActivity.class);
            startActivityForResult(intent, 1);
        });

        tvPageNumber = findViewById(R.id.tvPageNumber);
        editSearch = findViewById(R.id.edt_search_order_admin);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(v -> {
            page++;
            getCategoryList();
        });

        btnPrevious = findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(v -> {
            if (page > 1) {
                page--;
                getCategoryList();
            }
        });

        iconSearch = findViewById(R.id.btnSearch);
        iconSearch.setOnClickListener(v -> {
            search = editSearch.getText().toString();
            page = 1;
            getCategoryList();
        });
    }

    private void getCategoryList() {
        categoryUseCase.getCategory(String.valueOf(page),String.valueOf(limit), search).thenAccept(r -> {
            if (r.isRight()) {
                List<CategoryEntity> categoryList = r.getRight();
                adapter.updateCategoryList(categoryList);

                pageNumber = page;
                tvPageNumber.setText(String.format("Trang %d", pageNumber));

                btnPrevious.setEnabled(page > 1);
                if (categoryList.size() >= limit) {
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
            boolean categoryAdded = data.getBooleanExtra("added_category", false);
            boolean categoryDeleted = data.getBooleanExtra("deleted_category", false);
            boolean categoryUpdated = data.getBooleanExtra("updated_category", false);
            if (categoryAdded || categoryDeleted || categoryUpdated) {
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