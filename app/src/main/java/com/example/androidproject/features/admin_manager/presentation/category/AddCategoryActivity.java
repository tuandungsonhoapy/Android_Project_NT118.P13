package com.example.androidproject.features.admin_manager.presentation.category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.androidproject.R;
import com.example.androidproject.core.utils.CloudinaryConfig;
import com.example.androidproject.core.utils.counter.CounterModel;
import com.example.androidproject.features.category.data.model.CategoryModel;
import com.example.androidproject.features.category.usecase.CategoryUseCase;

public class AddCategoryActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private CloudinaryConfig cloudinaryConfig = new CloudinaryConfig();
    private CategoryUseCase categoryUseCase = new CategoryUseCase();
    private CounterModel counterModel = new CounterModel();
    private long categoryQuantity;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_category);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        counterModel.getQuantity("category").addOnSuccessListener(quantity -> {
            categoryQuantity = quantity;
        });

        Toast.makeText(this, "Add category id " + categoryQuantity, Toast.LENGTH_SHORT).show();

        EditText etCategoryName;
        EditText etCategoryDescription;
        Button btnChooseImage;
        Button btnAddCategory;
        etCategoryName = findViewById(R.id.etCategoryName);
        etCategoryDescription = findViewById(R.id.etCategoryDescription);
        btnChooseImage = findViewById(R.id.btnChooseImage);
        btnAddCategory = findViewById(R.id.btnAddCategory);

        btnChooseImage.setOnClickListener(v -> {
            openImageChooser();
        });

        btnAddCategory.setOnClickListener(v -> {
            String categoryName = etCategoryName.getText().toString();
            String categoryDescription = etCategoryDescription.getText().toString();
            if (categoryName.isEmpty() || categoryDescription.isEmpty()) {
                return;
            }

            cloudinaryConfig.uploadImage(imageUrl, this).thenAccept(r -> {
                CategoryModel category = new CategoryModel(categoryName, r, categoryDescription);
                categoryUseCase.addCategory(category, categoryQuantity);
                counterModel.updateQuantity("category")
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(AddCategoryActivity.this, "Thêm category thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("added_category", true);
                            setResult(RESULT_OK, intent);
                            finish();
                        });
            });
        });
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
                ImageView ivCategoryImagePreview = findViewById(R.id.ivCategoryImagePreview);
                ivCategoryImagePreview.setVisibility(View.VISIBLE);
                Glide.with(this).load(data.getData()).into(ivCategoryImagePreview);
                imageUrl = data.getData().toString();
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