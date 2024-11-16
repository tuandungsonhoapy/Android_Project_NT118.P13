package com.example.androidproject.features.admin_manager.presentation.category;

import android.os.Bundle;
import android.util.Log;
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
import com.example.androidproject.features.category.data.model.CategoryModel;
import com.example.androidproject.features.category.usecase.CategoryUseCase;

public class DetailCategoryAdminActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private Button btnEdit;
    private String categoryID;
    private CategoryUseCase categoryUseCase = new CategoryUseCase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_category_admin);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getCategoryIntent();
        setupButtons();
        getCategoryDetail();
    }

    private void getCategoryIntent() {
        categoryID = getIntent().getStringExtra("category_id");
    }

    private void getCategoryDetail() {
        categoryUseCase.getCategoryByID(categoryID).thenAccept(r -> {
           if (r.isRight()) {
                CategoryModel category = r.getRight();
                updateUI(category);
           } else {
               Log.d("categopry", "k lay duoc catergory");
           }
        });
    }

    private void updateUI(CategoryModel category) {
        TextView categoryID = findViewById(R.id.tvCategoryID);
        TextView categoryName = findViewById(R.id.tvCategoryName);
        TextView categoryQuantity = findViewById(R.id.tvCategoryQuantity);
        TextView categoryDescription = findViewById(R.id.tvCategoryDescription);
        ImageView categoryImage = findViewById(R.id.ivCategoryImage);

        categoryID.setText(category.getId());
        categoryName.setText(category.getCategoryName());
        categoryQuantity.setText(String.valueOf(category.getProductCount()));
        categoryDescription.setText(category.getDescription());
        Glide.with(this)
                .load(category.getImageUrl())
                .override(300, 300)
                .centerCrop()
                .into(categoryImage);
    }

    private void setupButtons() {
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        btnEdit = findViewById(R.id.btn_admin_category_edit);
        btnEdit.setOnClickListener(v -> {
            Toast.makeText(this, "Edit button clicked", Toast.LENGTH_SHORT).show();
        });
    }
}