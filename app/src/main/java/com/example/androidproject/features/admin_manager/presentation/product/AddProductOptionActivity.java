package com.example.androidproject.features.admin_manager.presentation.product;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidproject.R;
import com.example.androidproject.features.product.data.model.ProductModelFB;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AddProductOptionActivity extends AppCompatActivity {
    private EditText etChip, etRam, etRom, etQuantity;
    private Button btnAddOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product_option);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();
    }

    private void initView() {
        etChip = findViewById(R.id.etChip);
        etRam = findViewById(R.id.etRam);
        etRom = findViewById(R.id.etRom);
        etQuantity = findViewById(R.id.etQuantity);
        btnAddOption = findViewById(R.id.btnAddOption);
        String productId = getIntent().getStringExtra("product_id");
        AtomicInteger totalQuantity = new AtomicInteger(getIntent().getIntExtra("total_quantity", 0));

        btnAddOption.setOnClickListener(v -> {
            String chip = etChip.getText().toString();
            String ram = etRam.getText().toString();
            String rom = etRom.getText().toString();
            String quantity = etQuantity.getText().toString();
            // Add product option to firestore
            if (productId != null && !productId.isEmpty()) {
                // Tạo đối tượng option mới
                Map<String, Object> newOption = new HashMap<>();
                newOption.put("chip", chip);
                newOption.put("ram", ram);
                newOption.put("rom", rom);
                newOption.put("quantity", Integer.parseInt(quantity));

                totalQuantity.set(totalQuantity.get() + Integer.parseInt(quantity));

                // Tham chiếu tới tài liệu product
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference productRef = db.collection("products").document(productId);

                // Tạo map cập nhật
                Map<String, Object> updates = new HashMap<>();
                updates.put("options", FieldValue.arrayUnion(newOption));
                updates.put("stockQuantity", totalQuantity.get());

                // Cập nhật mảng options
                productRef.update(updates)
                        .addOnSuccessListener(aVoid -> {
                            // Thành công
                            etChip.setText("");
                            etRam.setText("");
                            etRom.setText("");
                            etQuantity.setText("");
                            Toast.makeText(this, "Option added successfully!", Toast.LENGTH_SHORT).show();

                            // Lấy bản ghi mới sau khi update
                            productRef.get()
                                    .addOnSuccessListener(documentSnapshot -> {
                                        if (documentSnapshot.exists()) {
                                            // Map dữ liệu sang class ProductModelFB
                                            ProductModelFB updatedProduct = documentSnapshot.toObject(ProductModelFB.class);
                                            if (updatedProduct != null) {
                                                Intent resultIntent = new Intent();
                                                Bundle resultBundle = new Bundle();
                                                resultBundle.putParcelableArrayList("product_options", new ArrayList<>(updatedProduct.getOptions()));
                                                resultIntent.putExtras(resultBundle);
                                                setResult(RESULT_OK, resultIntent);
                                                finish();
                                            }
                                        }
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(this, "Failed to fetch updated product: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        })
                        .addOnFailureListener(e -> {
                            // Xử lý lỗi
                            Toast.makeText(this, "Failed to add option: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(this, "Product ID is invalid!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}