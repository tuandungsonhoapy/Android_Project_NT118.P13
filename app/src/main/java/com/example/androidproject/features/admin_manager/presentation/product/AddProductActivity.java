package com.example.androidproject.features.admin_manager.presentation.product;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.R;
import com.example.androidproject.features.category.data.model.CategoryModel;
import com.example.androidproject.features.home.usecase.HomeUseCase;

import java.util.List;
import java.util.stream.Collectors;

public class AddProductActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> openImageLauncher;
//    private FileHandler fileHandler = new FileHandler(this);
    private EditText etProductName, etProductPrice, etProductDescription, etProductStock;
    private Spinner spCategory;
    ImageView ivProductImagePreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();
    }

    public void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etProductName = findViewById(R.id.etProductName);
        etProductPrice = findViewById(R.id.etProductPrice);
        etProductDescription = findViewById(R.id.etProductDescription);
        etProductStock = findViewById(R.id.etProductInventory);
        spCategory = findViewById(R.id.spCategory);
        Button btnChooseImage;
        btnChooseImage = findViewById(R.id.btnChooseImage);

        List<CategoryModel> categoryModelList = new HomeUseCase().getCategoriesList();
        List<String> categoryNameList = categoryModelList.stream().map(CategoryModel::getCategoryName).collect(Collectors.toList());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNameList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);

//        openImageLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
//                        Uri imageUri = result.getData().getData();
//                        ivProductImagePreview.setVisibility(View.VISIBLE);
//                        if (imageUri != null) {
//                            fileHandler.displayImageFromUri(imageUri, ivProductImagePreview);
//                        }
//                    }
//                }
//        );

//        btnChooseImage.setOnClickListener(v -> {
//            fileHandler.openFileChooser(openImageLauncher);
//        });
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