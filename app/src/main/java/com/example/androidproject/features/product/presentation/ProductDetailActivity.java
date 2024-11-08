package com.example.androidproject.features.product.presentation;

import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidproject.R;
import com.example.androidproject.features.home.usecase.HomeUseCase;

public class ProductDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerProductDetailImgView, recyclerView_colors, recyclerView_options;
    private ImageView imgProductDetail;
    private ProductDetailImgAdapter productDetailImgAdapter;
    private HomeUseCase homeUseCase = new HomeUseCase();
    private TextView oldPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initialView();
    }

    public void initialView() {
        oldPrice = findViewById(R.id.textView_old_price_pd);
        oldPrice.setPaintFlags(oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        imgProductDetail = findViewById(R.id.imgProductDetail);
        Glide.with(this).load(homeUseCase.getImgProductDetail()).into(imgProductDetail);

        recyclerProductDetailImgView = findViewById(R.id.recycler_product_images);
        recyclerProductDetailImgView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        productDetailImgAdapter = new ProductDetailImgAdapter(this, homeUseCase.getProductDetailImgList());
        recyclerProductDetailImgView.setAdapter(productDetailImgAdapter);

        recyclerView_colors = findViewById(R.id.recycler_colors_pd);
        recyclerView_colors.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView_colors.setAdapter(new ProductDetailColorAdapter(homeUseCase.getColorList(), this));

        recyclerView_options = findViewById(R.id.recycler_options_pd);
        recyclerView_options.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView_options.setAdapter(new ProductDetailOptionAdapter(homeUseCase.getOptionsList(), this));
    }
}