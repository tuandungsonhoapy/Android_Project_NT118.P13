package com.example.androidproject.features.admin_manager.presentation.category;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.androidproject.R;
import com.example.androidproject.core.utils.FileHandler;
import com.example.androidproject.features.category.data.model.CategoryModel;
import com.example.androidproject.features.category.usecase.CategoryUseCase;

public class DetailCategoryAdminActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private Button btnHide, btnEdit, btnDelete;
    private String categoryID;
    private ActivityResultLauncher<Intent> openImageLauncher;
    private FileHandler fileHandler = new FileHandler(this);
    private CategoryUseCase categoryUseCase = new CategoryUseCase();
    private String imageUrl;

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

        openImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        categoryImage.setVisibility(View.VISIBLE);
                        if (imageUri != null) {
                            fileHandler.displayImageFromUri(imageUri, categoryImage);
                            imageUrl = Uri.parse(imageUri.toString()).toString();
                        }
                    }
                }
        );

        categoryName.setOnClickListener(v -> {
            editDialog("Tên danh mục", category.getCategoryName());
        });

        categoryDescription.setOnClickListener(v -> {
            editDialog("Mô tả", category.getDescription());
        });

        categoryImage.setOnClickListener(v -> {
            fileHandler.openFileChooser(openImageLauncher);
        });
    }

    private void setupButtons() {
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        btnHide = findViewById(R.id.btnHide);
        btnHide.setOnClickListener(v -> {

        });

        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
    }

    private void editDialog(String fieldName, String value) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chỉnh sửa " + fieldName);

        EditText editText = new EditText(this);
        editText.setPadding(20, 20, 20, 20);
        editText.setText(value);
        builder.setView(editText);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String newValue = editText.getText().toString();
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


}