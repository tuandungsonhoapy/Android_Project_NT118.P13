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
import com.example.androidproject.core.credential.FirebaseHelper;
import com.example.androidproject.features.banner.data.model.BannerModel;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<ProductModelFB> productList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
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
    private FirebaseHelper firebaseHelper;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // firebase helper
        firebaseHelper = new FirebaseHelper(getContext());
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

        // Get the user data and update the username TextView
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
        firebaseHelper.findDocumentDataByUid()
                .thenAccept(userEntity -> {
                    if (userEntity != null) {
                        String userName = userEntity.getFirstName();
                        if (tvUserName != null) {
                            tvUserName.setText("Hello, " + userName);
                        }
                    }
                })
                .exceptionally(e -> {
                    Log.e("HomeFragment", "Error fetching user data", e);
                    return null;
                });
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