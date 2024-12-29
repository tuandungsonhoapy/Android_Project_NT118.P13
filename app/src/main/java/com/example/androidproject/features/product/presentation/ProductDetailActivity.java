package com.example.androidproject.features.product.presentation;

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
import com.example.androidproject.core.utils.counter.CounterModel;
import com.example.androidproject.features.cart.usecase.CartUseCase;
import com.example.androidproject.features.home.usecase.HomeUseCase;
import com.example.androidproject.features.product.data.entity.ProductEntity;
import com.example.androidproject.features.product.data.entity.ProductOption;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity implements ProductDetailImgAdapter.OnImageClickListener, ProductDetailOptionAdapter.OnOptionSelectedListener {

    private RecyclerView recyclerProductDetailImgView, recyclerView_options;
    private ImageView imgProductDetail;
    private ProductDetailImgAdapter productDetailImgAdapter;
    private HomeUseCase homeUseCase = new HomeUseCase();
//    private TextView oldPrice;
    private TextView tvRating, tvStockQuantity, tvBrandName, tvProductName, tvProductPrice, tvProductDescription, tvPrice, tvquantity, tvStock;
    private LinearLayout btnIncrease, btnDecrease;
    private ArrayList<ProductOption> productOptions;
    private ProductOption selectedOption;
    private ProductDetailOptionAdapter productDetailOptionAdapter;
    private Button btnAddToCart;
    private CounterModel counterModel = new CounterModel();
    private CartUseCase cartUseCase = new CartUseCase();
    private long cartQuantity;

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

        Bundle bundle = getIntent().getExtras();

        tvquantity.setText("1");

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

        assert bundle != null;
        String productId = bundle.getString("productId");
        String productName = bundle.getString("productName");
        String productPrice = bundle.getString("productPrice");
        List<String> productImgs = bundle.getStringArrayList("productImages");
        double productRating = bundle.getDouble("productRating");
        int productStockQuantity = bundle.getInt("productStockQuantity");
        String brandId = bundle.getString("brandId");
        String productDescription = bundle.getString("productDescription");
        String brandName = bundle.getString("brandName");
        productOptions = bundle.getParcelableArrayList("productOptions");

        if (productOptions != null && !productOptions.isEmpty()) {
            selectedOption = productOptions.get(0);
            tvStock.setText(String.valueOf(selectedOption.getQuantity()));
        }

        Log.d("ProductDetailActivity", "initialView: " + productId);
        tvBrandName.setText(brandName);
        tvProductName.setText(productName);
        tvProductPrice.setText(productPrice);
        tvRating.setText(String.valueOf(productRating));
        tvStockQuantity.setText(String.valueOf(productStockQuantity));
        tvProductDescription.setText(productDescription);
        tvPrice.setText(productPrice);

//        oldPrice = findViewById(R.id.textView_old_price_pd);
//        oldPrice.setPaintFlags(oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        imgProductDetail = findViewById(R.id.imgProductDetail);
        Glide.with(this).load(productImgs.get(0)).into(imgProductDetail);

        recyclerProductDetailImgView = findViewById(R.id.recycler_product_images);
        recyclerProductDetailImgView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        productDetailImgAdapter = new ProductDetailImgAdapter(this, productImgs);
        productDetailImgAdapter.setOnImageClickListener(this);
        recyclerProductDetailImgView.setAdapter(productDetailImgAdapter);

        productDetailOptionAdapter = new ProductDetailOptionAdapter(productOptions, this);
        productDetailOptionAdapter.setOnOptionSelectedListener(this);

        recyclerView_options = findViewById(R.id.recycler_options_pd);
        recyclerView_options.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView_options.setAdapter(productDetailOptionAdapter);

        btnAddToCart.setOnClickListener(v -> {
            counterModel.getQuantity("cart").addOnSuccessListener(quantity -> {
                cartQuantity = quantity;

                if(selectedOption.getQuantity() <= 0) {
                    return;
                }
                Log.d("ProductAdapter", "add cart: " + productId);
                Log.d("ProductAfterAdding1", "add cart: " + productId);
                cartUseCase.addProductToCart(
                        productId,
                        Integer.parseInt(tvquantity.getText().toString()),
                        selectedOption,
                        cartQuantity
                ).thenAccept(r -> {
                    if (r.isRight()) {
                        Toast.makeText(this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Thêm vào giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
                Toast.makeText(this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
            });
        });
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