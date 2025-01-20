package com.example.androidproject.features.admin_manager.presentation.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import android.app.Activity;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
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
import com.example.androidproject.features.home.usecase.HomeUseCase;
import com.example.androidproject.features.product.data.entity.ProductOption;
import com.example.androidproject.features.product.data.entity.ProductOptions;
import com.example.androidproject.features.product.data.model.ProductModelFB;
import com.example.androidproject.features.product.usecase.ProductUseCase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class AddProductActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> openImageLauncher;
//    private FileHandler fileHandler = new FileHandler(this);
    private EditText etProductName, etProductPrice, etProductDescription, etProductStock;
    private Spinner spCategory, spBrand;
    private Button btnAddProduct;
    ImageView ivProductImagePreview;
    private static final int PICK_IMAGE_REQUEST = 1;
    private CloudinaryConfig cloudinaryConfig = new CloudinaryConfig();
    private long productQuantity;
    private String imageUrl;
    private ProductUseCase productUseCase = new ProductUseCase();
    private CounterModel counterModel = new CounterModel();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String selectedBrandIdFS, selectedCategoryIdFS;

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
        spCategory = findViewById(R.id.spCategory);
        spBrand = findViewById(R.id.spBrand);
        Button btnChooseImage;
        btnChooseImage = findViewById(R.id.btnChooseImage);
        btnAddProduct = findViewById(R.id.btnAddProduct);

        List<CategoryModel> categoryModelList = new HomeUseCase().getCategoriesList();
        List<String> categoryNameList = categoryModelList.stream().map(CategoryModel::getCategoryName).collect(Collectors.toList());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNameList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);

        long productId = 0;

        counterModel.getQuantity("product").addOnSuccessListener(quantity -> {
            setProductQuantity(quantity);
        });


        btnChooseImage.setOnClickListener(v -> {
            openImageChooser();
        });

        btnAddProduct.setOnClickListener(v -> {
            String productName = etProductName.getText().toString();
            String productPrice = etProductPrice.getText().toString();
            String productDescription = etProductDescription.getText().toString();

            if (productName.isEmpty() || productPrice.isEmpty() || productDescription.isEmpty()) {
                Toast.makeText(AddProductActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            List<String> images = new ArrayList<>();

            List<ProductOption> productOptions = new ArrayList<>();
            productOptions.add(new ProductOption("Intel i7", "16GB", "512GB", 23));
            productOptions.add(new ProductOption("Intel i5", "8GB", "256GB", 12));

            cloudinaryConfig.uploadImage(imageUrl, this).thenAccept(r -> {
                images.add(r);
                ProductModelFB product = new ProductModelFB(productName, images, Double.parseDouble(productPrice), 0, selectedBrandIdFS, selectedCategoryIdFS, productOptions, productDescription);
                productUseCase.addProduct(product, productQuantity);
                counterModel.updateQuantity("product")
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(AddProductActivity.this, "Thêm product thành công", Toast.LENGTH_SHORT).show();
                            Intent resultIntent = new Intent();
                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();
                        });
            });
        });

        fetchBrands();
        fetchCategories();

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

    private void fetchBrands() {
        db.collection("brands")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<String> brandNameList = new ArrayList<>();
                        List<String> brandIdList = new ArrayList<>();

                        // Lặp qua các documents
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Lấy giá trị trường "name" và ID của mỗi document
                            String brandName = document.getString("name");
                            String brandId = document.getId();
                            if (brandName != null) {
                                brandNameList.add(brandName);
                                brandIdList.add(brandId);
                            }
                        }

                        // Set adapter cho Spinner
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddProductActivity.this, android.R.layout.simple_spinner_item, brandNameList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spBrand.setAdapter(adapter);

                        // Lấy giá trị ID khi chọn một thương hiệu
                        spBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String selectedBrandId = brandIdList.get(position);
                                selectedBrandIdFS = selectedBrandId;
                                Toast.makeText(AddProductActivity.this, "Brand ID: " + selectedBrandId, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                    } else {
                        Toast.makeText(this, "Không thể lấy danh sách thương hiệu", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fetchCategories() {
        db.collection("categories")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<String> categoryNameList = new ArrayList<>();
                        List<String> categoryIdList = new ArrayList<>();

                        // Lặp qua các documents
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Lấy giá trị trường "name" và ID của mỗi document
                            String categoryName = document.getString("name");
                            String categoryId = document.getId();
                            if (categoryName != null) {
                                categoryNameList.add(categoryName);
                                categoryIdList.add(categoryId);
                            }
                        }

                        // Set adapter cho Spinner
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddProductActivity.this, android.R.layout.simple_spinner_item, categoryNameList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spCategory.setAdapter(adapter);

                        // Lấy giá trị ID khi chọn một danh mục
                        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String selectedCategoryId = categoryIdList.get(position);
                                selectedCategoryIdFS = selectedCategoryId;
                                Toast.makeText(AddProductActivity.this, "Category ID: " + selectedCategoryIdFS, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                    } else {
                        Toast.makeText(this, "Không thể lấy danh sách danh mục", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void setProductQuantity(long quantity){
        this.productQuantity = quantity;
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
            ImageView ivProductImagePreview = findViewById(R.id.ivProductImagePreview);
            ivProductImagePreview.setVisibility(View.VISIBLE);
            Glide.with(this).load(data.getData()).into(ivProductImagePreview);
            imageUrl = data.getData().toString();
            Toast.makeText(AddProductActivity.this, "Image URL: " + imageUrl, Toast.LENGTH_SHORT).show();
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