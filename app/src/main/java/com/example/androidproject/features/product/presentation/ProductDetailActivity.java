package com.example.androidproject.features.product.presentation;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidproject.R;
import com.example.androidproject.core.utils.MoneyFomat;
import com.example.androidproject.core.utils.counter.CounterModel;
import com.example.androidproject.features.brand.usecase.BrandUseCase;
import com.example.androidproject.features.cart.usecase.CartUseCase;
import com.example.androidproject.features.home.usecase.HomeUseCase;
import com.example.androidproject.features.product.data.entity.ProductEntity;
import com.example.androidproject.features.product.data.entity.ProductOption;
import com.example.androidproject.features.product.data.model.ProductModelFB;
import com.example.androidproject.features.product.usecase.ProductUseCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity implements ProductDetailImgAdapter.OnImageClickListener, ProductDetailOptionAdapter.OnOptionSelectedListener {

    private RecyclerView recyclerProductDetailImgView, recyclerView_options;
    private ImageView imgProductDetail;
    private ProductDetailImgAdapter productDetailImgAdapter;
    private TextView tvRating, tvStockQuantity, tvBrandName, tvProductName, tvProductPrice, tvProductDescription, tvPrice, tvquantity, tvStock;
    private LinearLayout btnIncrease, btnDecrease;
    private ArrayList<ProductOption> productOptions;
    private ProductOption selectedOption;
    private ProductDetailOptionAdapter productDetailOptionAdapter;
    private Button btnAddToCart;
    private CounterModel counterModel = new CounterModel();
    private CartUseCase cartUseCase = new CartUseCase();
    private ProductUseCase productUseCase = new ProductUseCase();
    private BrandUseCase brandUseCase = new BrandUseCase();
    private long cartQuantity;
    private String productId;
    private ArrayList<String> productImgs;

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

        getProductIntent();
        initialView();
        if (productId != null) {
            getDetailProductById(productId);
        }
    }

    public void initialView() {
        tvProductName = findViewById(R.id.textView_pd_name);
        tvPrice = findViewById(R.id.textView_price);
        tvProductPrice = findViewById(R.id.textView_new_price_pd);
        tvRating = findViewById(R.id.textView_rating_number);
        tvStockQuantity = findViewById(R.id.textView_stock_quantity);
        tvBrandName = findViewById(R.id.textView24);
        tvProductDescription = findViewById(R.id.textView25);
        tvquantity = findViewById(R.id.textView_number);
        btnIncrease = findViewById(R.id.linearLayout_increase);
        btnDecrease = findViewById(R.id.linearLayout_decrease);
        tvStock = findViewById(R.id.tv_stock_value);
        btnAddToCart = findViewById(R.id.button_add_to_cart);
        imgProductDetail = findViewById(R.id.imgProductDetail);
        recyclerProductDetailImgView = findViewById(R.id.recycler_product_images);
        recyclerView_options = findViewById(R.id.recycler_options_pd);
        tvquantity.setText("0");

        btnIncrease.setOnClickListener(v -> {
            int q = Integer.parseInt(tvquantity.getText().toString());
            q++;
            tvquantity.setText(String.valueOf(q));
        });

        btnDecrease.setOnClickListener(v -> {
            int q = Integer.parseInt(tvquantity.getText().toString());
            if (q > 1) {
                q--;
                tvquantity.setText(String.valueOf(q));
            }
        });

        btnAddToCart.setOnClickListener(v -> {
            counterModel.getQuantity("cart").addOnSuccessListener(quantity -> {
                cartQuantity = quantity;
                if(
                        (       selectedOption != null
                                && selectedOption.getQuantity() < Integer.parseInt(tvquantity.getText().toString())
                        )
                        || Integer.parseInt(tvquantity.getText().toString()) == 0
                ) {
                    Toast.makeText(this, "Mẫu hàng hiện tại đã hết hàng", Toast.LENGTH_SHORT).show();
                    return;
                }
                cartUseCase.addProductToCart(
                        productId,
                        Integer.parseInt(tvquantity.getText().toString()),
                        selectedOption,
                        cartQuantity
                ).thenAccept(r -> {
                    if (r.isRight()) {
                        tvquantity.setText("0");
                        Toast.makeText(this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Thêm vào giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
                Toast.makeText(this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
            });
        });
    }

    public void getProductIntent() {
        Intent intent = getIntent();
        if(intent.hasExtra("productId")) {
            productId = intent.getStringExtra("productId");
        }
    }

    public void getDetailProductById(String productId) {
        productUseCase.getDetailProductById(productId)
                .thenAccept(r -> {
                    if(r.isRight()){
                        ProductModelFB product = r.getRight();
                        brandUseCase.getBrandById(product.getBrandId())
                                .thenAccept(brand -> {
                                    if(brand.isRight()) {
                                        product.setBrand(brand.getRight());
                                        runOnUiThread(() -> {
                                            tvBrandName.setText(product.getBrand().getName());
                                            tvProductName.setText(product.getName());
                                            tvProductPrice.setText(MoneyFomat.format(product.getPrice()) + "đ");
                                            tvRating.setText(String.valueOf(product.getRating()));
                                            tvStockQuantity.setText(String.valueOf(product.getStockQuantity()));
                                            tvProductDescription.setText(product.getDescription());
                                            tvPrice.setText(MoneyFomat.format(product.getPrice()) + "đ");

                                            if (product.getImages() != null && !product.getImages().isEmpty()) {
                                                Glide.with(this).load(product.getImages().get(0)).into(imgProductDetail);
                                            }

                                            if (product.getImages() != null) {
                                                productImgs = (ArrayList<String>) product.getImages();
                                                setupImageRecyclerView();
                                            }

                                            if (product.getOptions() != null) {
                                                productOptions = (ArrayList<ProductOption>) product.getOptions();
                                                setupOptionsRecyclerView();
                                                if (productOptions != null && !productOptions.isEmpty()) {
                                                    selectedOption = productOptions.get(0);
                                                    tvStock.setText(String.valueOf(selectedOption.getQuantity()));
                                                }
                                            } else {
                                                    tvStock.setText(String.valueOf(product.getStockQuantity()));
                                                    selectedOption = null;
                                            }
                                        });
                                    }
                                });
                    }
                });
    }

    private void setupImageRecyclerView() {
        recyclerProductDetailImgView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        productDetailImgAdapter = new ProductDetailImgAdapter(this, productImgs);
        productDetailImgAdapter.setOnImageClickListener(this);
        recyclerProductDetailImgView.setAdapter(productDetailImgAdapter);
    }

    private void setupOptionsRecyclerView() {
        productDetailOptionAdapter = new ProductDetailOptionAdapter(productOptions, this);
        productDetailOptionAdapter.setOnOptionSelectedListener(this);
        recyclerView_options.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );
        recyclerView_options.setAdapter(productDetailOptionAdapter);
    }
    @Override
    public void onImageClick(String imageUrl) {
        Glide.with(this).load(imageUrl).into(imgProductDetail);
    }

    @Override
    public void onOptionSelected(ProductOption option) {
        selectedOption = option;
        tvStock.setText(String.valueOf(option.getQuantity()));
    }
}