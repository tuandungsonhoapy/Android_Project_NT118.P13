package com.example.androidproject.features.admin_manager.presentation.brand;

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
import com.example.androidproject.features.brand.data.model.BrandModel;
import com.example.androidproject.features.brand.usecase.BrandUseCase;
import com.example.androidproject.features.category.data.model.CategoryModel;
import com.example.androidproject.features.category.usecase.CategoryUseCase;

public class DetailBrandAdminActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private Button btnHide, btnEdit, btnDelete;
    private String id;
    private static final int PICK_IMAGE_REQUEST = 1;
    private CloudinaryConfig cloudinaryConfig = new CloudinaryConfig();
    private BrandUseCase brandUseCase = new BrandUseCase();
    private String newImageUrl;
    private String defaultImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_brand_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getBrandIntent();
        getBrandDetail();
        setupButtons();
    }

    private void getBrandIntent() {
        id = getIntent().getStringExtra("brand_id");
    }

    private void getBrandDetail() {
        brandUseCase.getBrandById(id).thenAccept(r -> {
            if (r.isRight()) {
                BrandModel brandModel = r.getRight();
                updateUI(brandModel);
            }
        });
    }

    private void updateUI(BrandModel brandModel) {
        TextView brandID = findViewById(R.id.tvBrandID);
        TextView brandName = findViewById(R.id.tvBrandName);
        TextView brandDescription = findViewById(R.id.tvBrandDescription);
        ImageView brandImage = findViewById(R.id.ivBrandImage);
        Button btnChooseImage = findViewById(R.id.btnChooseImage);

        brandID.setText(brandModel.getId());
        brandName.setText(brandModel.getName());
        brandDescription.setText(brandModel.getDescription());
        Glide.with(this).load(brandModel.getImageUrl()).into(brandImage);
        defaultImageUrl = brandModel.getImageUrl();

        btnHide = findViewById(R.id.btnHide);
        btnHide.setText(brandModel.getHidden() ? "Hiện" : "Ẩn");
        btnHide.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Xác nhận");
            builder.setMessage(brandModel.getHidden() ? "Bạn có muốn hiện thương hiệu này không?" : "Bạn có muốn ẩn thương hiệu này không?");

            builder.setPositiveButton("OK", (dialog, which) -> {
                brandUseCase.updateBrandHidden(id, !brandModel.getHidden());
                finish();
            });

            builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        brandName.setOnClickListener(v -> {
            editDialog("Tên thương hiệu", brandModel.getName(), brandName);
        });

        brandDescription.setOnClickListener(v -> {
            editDialog("Mô tả", brandModel.getDescription(), brandDescription);
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
            String newName = ((TextView) findViewById(R.id.tvBrandName)).getText().toString();
            String newDescription = ((TextView) findViewById(R.id.tvBrandDescription)).getText().toString();

            if (newImageUrl != null) {
                cloudinaryConfig.uploadImage(newImageUrl, this).thenAccept(r -> {
                    BrandModel brandModel = new BrandModel(newName, r, newDescription);
                    brandModel.setId(id);
                    brandUseCase.updateBrand(brandModel);
                    Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("updated_brand", true);
                    setResult(RESULT_OK, intent);
                    finish();
                });
            } else {
                BrandModel brandModel = new BrandModel(newName, defaultImageUrl, newDescription);
                brandModel.setId(id);
                brandUseCase.updateBrand(brandModel);
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("updated_brand", true);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Xác nhận");
            builder.setMessage("Bạn có muốn xóa thương hiệu này không?");

            builder.setPositiveButton("OK", (dialog, which) -> {
                brandUseCase.deleteBrand(id);
                Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("deleted_brand", true);
                setResult(RESULT_OK, intent);
                finish();
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
            ImageView ivBrandImage = findViewById(R.id.ivBrandImage);
            ivBrandImage.setVisibility(View.VISIBLE);
            Glide.with(this).load(data.getData()).into(ivBrandImage);
            newImageUrl = data.getData().toString();
            btnEdit.setVisibility(Button.VISIBLE);
        }
    }
}