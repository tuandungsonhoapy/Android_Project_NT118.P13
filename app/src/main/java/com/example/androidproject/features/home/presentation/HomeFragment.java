package com.example.androidproject.features.home.presentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidproject.R;
import com.example.androidproject.features.banner.presentation.BannerAdapter;
import com.example.androidproject.features.brand.data.model.BrandModel;
import com.example.androidproject.features.cart.presentation.CartActivity;
import com.example.androidproject.features.category.data.entity.CategoryEntity;
import com.example.androidproject.features.category.presentation.CategoryAdapter;
import com.example.androidproject.features.category.usecase.CategoryUseCase;
import com.example.androidproject.features.home.usecase.HomeUseCase;
import com.example.androidproject.features.product.data.model.ProductModelFB;
import com.example.androidproject.features.product.presentation.AllProductActivity;
import com.example.androidproject.features.product.presentation.ProductAdapter;
import com.example.androidproject.features.product.usecase.ProductUseCase;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HomeFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<ProductModelFB> productList = new ArrayList<>();

    private RecyclerView recyclerCategoryView;
    private RecyclerView recyclerProductView;
    private ViewPager2 viewPagerBanner;
    private ImageView cartIcon, img_search;
    private EditText edt_search;
    private TextView viewAllProduct, tvUserName;
    private HomeUseCase homeUseCase = new HomeUseCase();
    private CategoryUseCase categoryUseCase = new CategoryUseCase();
    private ProductUseCase productUseCase = new ProductUseCase();

    // others
    private Bundle userDataBundle;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get arg
        if (getArguments() != null) {
            userDataBundle = getArguments();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerCategoryView = view.findViewById(R.id.recycler_categories_view);
        recyclerProductView = view.findViewById(R.id.recycler_products_view);
        viewPagerBanner = view.findViewById(R.id.view_pager);
        cartIcon = view.findViewById(R.id.cartIcon);
        viewAllProduct = view.findViewById(R.id.viewAllProduct);
        tvUserName = view.findViewById(R.id.tvUserName);

        //username
        updateUserName();

        //view categories
        List<CategoryEntity> categoryList = new ArrayList<>();
        categoryUseCase.getCategoryList().thenAccept(r -> {
            if (r.isRight()) {
                categoryList.addAll(r.getRight());
                CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categoryList);
                recyclerCategoryView.setAdapter(categoryAdapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerCategoryView.setLayoutManager(layoutManager);
            }
        });

        //view banners
        BannerAdapter bannerAdapter = new BannerAdapter(getContext(), homeUseCase.getBannersList());
        viewPagerBanner.setAdapter(bannerAdapter);

        img_search = view.findViewById(R.id.img_search);
        edt_search = view.findViewById(R.id.edt_search);
        img_search.setOnClickListener(v -> {
            String search = edt_search.getText().toString();
            Intent intent = new Intent(getActivity(), AllProductActivity.class);
            intent.putExtra("search", search);
            startActivity(intent);
        });

        //view products
        fetchTop10ProductsFromFirestore();

        cartIcon.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CartActivity.class);
            startActivity(intent);
        });

        viewAllProduct.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AllProductActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private void updateUserName() {
        if (userDataBundle != null) {
            String name = userDataBundle.getString("name");
            tvUserName.setText("Hello, " + name);
        } else {
            tvUserName.setText("Hello, User");
        }
    }

    private void fetchTop10ProductsFromFirestore() {
        productUseCase.getProductsAndMapBrands().thenAccept(r -> {
            if (r.isRight()) {
                productList.addAll(r.getRight());
                ProductAdapter productAdapter = new ProductAdapter(getContext(), productList);
                recyclerProductView.setAdapter(productAdapter);
                recyclerProductView.setLayoutManager(new GridLayoutManager(getContext(), homeUseCase.getColumns(2)));
            }
        });
    }
}