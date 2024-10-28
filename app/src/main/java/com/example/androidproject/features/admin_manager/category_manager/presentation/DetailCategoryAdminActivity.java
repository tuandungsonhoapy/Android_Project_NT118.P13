package com.example.androidproject.features.admin_manager.category_manager.presentation;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.androidproject.R;

public class DetailCategoryAdminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_category_admin);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView categoryID = findViewById(R.id.tvCategoryID);
        TextView categoryName = findViewById(R.id.tvCategoryName);
        TextView categoryQuantity = findViewById(R.id.tvCategoryQuantity);
        TextView categoryDescription = findViewById(R.id.tvCategoryDescription);
        ImageView categoryImage = findViewById(R.id.ivCategoryImage);

        categoryID.setText(getIntent().getStringExtra("category_id"));
        categoryName.setText(getIntent().getStringExtra("category_name"));
        categoryQuantity.setText(getIntent().getStringExtra("category_quantity"));
        categoryDescription.setText("helo world");

        Glide.with(this)
                .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7U_69OLMV-mASOOC1CdFjJ50-yUmU5hv5UQ&s")
                .into(categoryImage);
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