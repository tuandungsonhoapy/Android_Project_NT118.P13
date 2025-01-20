package com.example.androidproject.features.admin_manager.presentation.brand;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.androidproject.features.admin_manager.presentation.category.AddCategoryActivity;
import com.example.androidproject.features.brand.data.model.BrandModel;
import com.example.androidproject.features.brand.usecase.BrandUseCase;
import com.example.androidproject.features.category.data.model.CategoryModel;
import com.example.androidproject.features.category.usecase.CategoryUseCase;

public class AddBrandActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private CloudinaryConfig cloudinaryConfig = new CloudinaryConfig();
    private BrandUseCase brandUseCase = new BrandUseCase();
    private CounterModel counterModel = new CounterModel();
    private long brandQuantity;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_brand);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        counterModel.getQuantity("brand").addOnSuccessListener(quantity -> {
            brandQuantity = quantity;
        });

        EditText etBrandName;
        EditText etBrandDescription;
        Button btnChooseImage;
        Button btnAddBrand;

        etBrandName = findViewById(R.id.etBrandName);
        etBrandDescription = findViewById(R.id.etBrandDescription);
        btnChooseImage = findViewById(R.id.btnChooseImage);
        btnAddBrand = findViewById(R.id.btnAddBrand);

        btnChooseImage.setOnClickListener(v -> {
            openImageChooser();
        });

        btnAddBrand.setOnClickListener(v -> {
            String brandName = etBrandName.getText().toString();
            String brandDescription = etBrandDescription.getText().toString();
            if (brandName.isEmpty() || brandDescription.isEmpty()) {
                return;
            }

            cloudinaryConfig.uploadImage(imageUrl, this).thenAccept(r -> {
                BrandModel brand = new BrandModel(brandName, r, brandDescription);
                brandUseCase.addBrand(brand, brandQuantity);
                counterModel.updateQuantity("brand")
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(AddBrandActivity.this, "Thêm brand thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("added_brand", true);
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
            ImageView ivBrandImagePreview = findViewById(R.id.ivBrandImagePreview);
            ivBrandImagePreview.setVisibility(View.VISIBLE);
            Glide.with(this).load(data.getData()).into(ivBrandImagePreview);
            imageUrl = data.getData().toString();
        }
    }
}