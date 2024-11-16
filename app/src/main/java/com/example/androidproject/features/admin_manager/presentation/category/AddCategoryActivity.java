package com.example.androidproject.features.admin_manager.presentation.category;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.R;
import com.example.androidproject.core.utils.CloudinaryConfig;
import com.example.androidproject.core.utils.FileHandler;
import com.example.androidproject.core.utils.counter.CounterModel;
import com.example.androidproject.features.category.data.model.CategoryModel;
import com.example.androidproject.features.category.usecase.CategoryUseCase;

public class AddCategoryActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> openImageLauncher;
    private CloudinaryConfig cloudinaryConfig = new CloudinaryConfig();
    private FileHandler fileHandler = new FileHandler(this);
    private CategoryUseCase categoryUseCase = new CategoryUseCase();
    private CounterModel counterModel;
    private long categoryQuantity;
    private String imageUrl;
    private String uploadUrl;

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

        counterModel = new CounterModel();
        counterModel.getQuantity("category", new CounterModel.QuantityCallback() {
            @Override
            public void onQuantityReceived(Long quantity) {
                if (quantity != null) {
                    categoryQuantity = quantity;
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e("Firestore", "Lỗi khi lấy số lượng category", e);
            }
        });

        EditText etCategoryName;
        EditText etCategoryDescription;
        Button btnChooseImage;
        ImageView ivCategoryImagePreview;
        Button btnAddCategory;
        etCategoryName = findViewById(R.id.etCategoryName);
        etCategoryDescription = findViewById(R.id.etCategoryDescription);
        btnChooseImage = findViewById(R.id.btnChooseImage);
        ivCategoryImagePreview = findViewById(R.id.ivCategoryImagePreview);
        btnAddCategory = findViewById(R.id.btnAddCategory);

        openImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        ivCategoryImagePreview.setVisibility(View.VISIBLE);
                        if (imageUri != null) {
                            fileHandler.displayImageFromUri(imageUri, ivCategoryImagePreview);
                            imageUrl = Uri.parse(imageUri.toString()).toString();
                        }
                    }
                }
        );

        btnChooseImage.setOnClickListener(v -> {
             fileHandler.openFileChooser(openImageLauncher);
        });

        btnAddCategory.setOnClickListener(v -> {
            String categoryName = etCategoryName.getText().toString();
            String categoryDescription = etCategoryDescription.getText().toString();
            if (categoryName.isEmpty() || categoryDescription.isEmpty()) {
                Log.d("Firestore", "Vui lòng nhập đầy đủ thông tin");
                return;
            }

            cloudinaryConfig.uploadImage(imageUrl, this, new CloudinaryConfig.UploadImageInterface() {
                @Override
                public void onStart() {

                }

                @Override
                public void onProgress(long bytes, long totalBytes) {

                }

                @Override
                public void onSuccess(String url) {
                    uploadUrl = url;
                    Log.d("Firestore", uploadUrl);
                    CategoryModel category = new CategoryModel(categoryName, uploadUrl, categoryDescription);
                    categoryUseCase.addCategory(category, categoryQuantity);
                    counterModel.updateQuantity("category", 1);
                    Log.d("Firestore", "Số lượng category sau khi thêm: " + categoryQuantity);
                    Toast.makeText(AddCategoryActivity.this, "Thêm category thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onError(String errorMessage) {

                }
            });

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
}