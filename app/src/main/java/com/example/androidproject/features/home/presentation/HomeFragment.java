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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidproject.R;
import com.example.androidproject.features.banner.data.model.BannerModel;
import com.example.androidproject.features.banner.presentation.BannerAdapter;
import com.example.androidproject.features.brand.data.model.BrandModel;
import com.example.androidproject.features.cart.presentation.CartActivity;
import com.example.androidproject.features.category.data.entity.CategoryEntity;
import com.example.androidproject.features.category.data.model.CategoryModel;
import com.example.androidproject.features.category.presentation.CategoryAdapter;
import com.example.androidproject.features.category.usecase.CategoryUseCase;
import com.example.androidproject.features.home.usecase.HomeUseCase;
import com.example.androidproject.features.product.data.model.ProductModel;
import com.example.androidproject.features.product.data.model.ProductModelFB;
import com.example.androidproject.features.product.presentation.AllProductActivity;
import com.example.androidproject.features.product.presentation.ProductAdapter;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
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
    private ImageView cartIcon;
    private TextView viewAllProduct;
    private HomeUseCase homeUseCase = new HomeUseCase();
    private CategoryUseCase categoryUseCase = new CategoryUseCase();

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

        //view categories
        List<CategoryEntity> categoryList = new ArrayList<>();
        categoryUseCase.getCategoryList().thenAccept(r -> {
            if (r.isRight()){
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

    private void fetchTop10ProductsFromFirestore() {
        Set<String> brandIds = new HashSet<>();

        db.collection("products")
                .orderBy("stockQuantity", Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .addOnSuccessListener(productSnapshots -> {
                    for (QueryDocumentSnapshot document : productSnapshots) {
                        ProductModelFB product = document.toObject(ProductModelFB.class);
                        productList.add(product);
                        if (product.getBrandId() != null) {
                            brandIds.add(product.getBrandId());
                        }
                    }

                    // Query tất cả các brand theo danh sách brandIds
                    db.collection("brands")
                            .whereIn(FieldPath.documentId(), new ArrayList<>(brandIds))
                            .get()
                            .addOnSuccessListener(brandSnapshots -> {
                                Map<String, BrandModel> brandMap = new HashMap<>();
                                for (QueryDocumentSnapshot brandDoc : brandSnapshots) {
                                    BrandModel brand = brandDoc.toObject(BrandModel.class);
                                    brandMap.put(brandDoc.getId(), brand);
                                }

                                // Gán brand vào từng sản phẩm
                                for (ProductModelFB product : productList) {
                                    product.setBrand(brandMap.get(product.getBrandId()));
                                }

                                // Set adapter
                                ProductAdapter productAdapter = new ProductAdapter(getContext(), productList);
                                recyclerProductView.setAdapter(productAdapter);
                                recyclerProductView.setLayoutManager(new GridLayoutManager(getContext(), homeUseCase.getColumns(2)));
                            })
                            .addOnFailureListener(e -> Log.e("FirestoreError", "Error fetching brands", e));
                })
                .addOnFailureListener(e -> Log.e("FirestoreError", "Error fetching products", e));
    }

}