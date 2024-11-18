package com.example.androidproject.features.admin_manager.presentation.category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.androidproject.R;
import com.example.androidproject.core.utils.CloudinaryConfig;
import com.example.androidproject.core.utils.counter.CounterModel;
import com.example.androidproject.features.category.data.model.CategoryModel;
import com.example.androidproject.features.category.usecase.CategoryUseCase;

public class DetailCategoryAdminActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private Button btnHide, btnEdit, btnDelete;
    private String id;
    private static final int PICK_IMAGE_REQUEST = 1;
    private CloudinaryConfig cloudinaryConfig = new CloudinaryConfig();
    private CategoryUseCase categoryUseCase = new CategoryUseCase();
    private String newImageUrl;
    private String defaultImageUrl;
    private CounterModel counterModel = new CounterModel();

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
        getCategoryDetail();
        setupButtons();
    }

    private void getCategoryIntent() {
        id = getIntent().getStringExtra("category_id");
    }

    private void getCategoryDetail() {
        categoryUseCase.getCategoryByID(id).thenAccept(r -> {
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
        Button btnChooseImage = findViewById(R.id.btnChooseImage);

        btnHide = findViewById(R.id.btnHide);
        btnHide.setText(category.isHidden() ? "Hiện" : "Ẩn");
        btnHide.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Xác nhận");
            builder.setMessage(category.isHidden() ? "Bạn có muốn hiện danh mục này không?" : "Bạn có muốn ẩn danh mục này không?");

            builder.setPositiveButton("OK", (dialog, which) -> {
                categoryUseCase.updateCategoryHidden(id, !category.isHidden());
                finish();
            });

            builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        categoryID.setText(category.getId());
        categoryName.setText(category.getCategoryName());
        categoryQuantity.setText(String.valueOf(category.getProductCount()));
        categoryDescription.setText(category.getDescription());
        Glide.with(this)
                .load(category.getImageUrl())
                .override(300, 300)
                .centerCrop()
                .into(categoryImage);

        defaultImageUrl = category.getImageUrl();
        categoryName.setOnClickListener(v -> {
            editDialog("Tên danh mục", category.getCategoryName(), categoryName);
        });

        categoryDescription.setOnClickListener(v -> {
            editDialog("Mô tả", category.getDescription(), categoryDescription);
        });

        btnChooseImage.setOnClickListener(v -> {
            openImageChooser();
        });

    }

    private void setupButtons() {
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(v -> {
            String newName = ((TextView) findViewById(R.id.tvCategoryName)).getText().toString();
            String newDescription = ((TextView) findViewById(R.id.tvCategoryDescription)).getText().toString();

            if (newImageUrl != null) {
                cloudinaryConfig.uploadImage(newImageUrl,this).thenAccept(r -> {
                    CategoryModel categoryModel = new CategoryModel(newName, r, newDescription);
                    categoryModel.setId(id);
                    categoryUseCase.updateCategory(categoryModel);
                    Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    finish();
                });
            } else {
                CategoryModel categoryModel = new CategoryModel(newName, defaultImageUrl, newDescription);
                categoryModel.setId(id);
                categoryUseCase.updateCategory(categoryModel);
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Xác nhận");
            builder.setMessage("Bạn có muốn xóa danh mục này không?");

            builder.setPositiveButton("OK", (dialog, which) -> {
                categoryUseCase.deleteCategory(id);
                counterModel.updateQuantity("category", -1)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("deleted_category", true);
                            setResult(RESULT_OK, intent);
                            finish();
                        });
            });

            builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());

            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    private void editDialog(String fieldName, String value, TextView tv) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chỉnh sửa " + fieldName);

        EditText editText = new EditText(this);
        editText.setPadding(20, 20, 20, 20);
        editText.setText(value);
        builder.setView(editText);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String newValue = editText.getText().toString();
            tv.setText(newValue);

            if (!newValue.equals(value)) {
                btnEdit.setVisibility(Button.VISIBLE);
            } else {
                btnEdit.setVisibility(Button.GONE);
            }
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();
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
            ImageView ivCategoryImage = findViewById(R.id.ivCategoryImage);
            ivCategoryImage.setVisibility(View.VISIBLE);
            Glide.with(this).load(data.getData()).into(ivCategoryImage);
            newImageUrl = data.getData().toString();
        }
    }
}