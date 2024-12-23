package com.example.androidproject.features.category.presentation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.androidproject.R;
import com.example.androidproject.features.brand.data.entity.BrandEntity;
import com.example.androidproject.features.brand.data.model.BrandModel;
import com.example.androidproject.features.brand.presentation.BrandAdapter;
import com.example.androidproject.features.brand.usecase.BrandUseCase;
import com.example.androidproject.features.category.data.entity.CategoryEntity;
import com.example.androidproject.features.category.usecase.CategoryUseCase;
import com.example.androidproject.features.store.presentation.ItemBrandToProduct;

import java.util.ArrayList;
import java.util.List;


public class CategoryToBrandFragment extends Fragment {
    private static final String ARG_CATEGORY = "category";
    private String category;
    private RecyclerView recyclerBrandView;
    private TextView noBrandTextView;
    private BrandUseCase brandUseCase = new BrandUseCase();
    private ItemBrandToProduct itemBrandToProduct;
    private List<BrandEntity> brandList = new ArrayList<>();

    public static CategoryToBrandFragment newInstance(String category) {
        CategoryToBrandFragment fragment = new CategoryToBrandFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(ARG_CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_to_brand, container, false);
        recyclerBrandView = view.findViewById(R.id.recycler_view_list_brands);
        noBrandTextView = view.findViewById(R.id.text_no_brands);
        itemBrandToProduct = new ItemBrandToProduct(getContext(), brandList);
        recyclerBrandView.setAdapter(itemBrandToProduct);
        recyclerBrandView.setLayoutManager(new LinearLayoutManager(getContext()));
        brandUseCase.getBrandListByCategory(category).thenAccept(r -> {
            if (r.isRight()) {
                brandList.clear();
                brandList.addAll(r.getRight());;

                if (brandList.isEmpty()) {
                    recyclerBrandView.setVisibility(View.GONE);
                    noBrandTextView.setVisibility(View.VISIBLE);
                } else {
                    recyclerBrandView.setVisibility(View.VISIBLE);
                    noBrandTextView.setVisibility(View.GONE);
                }
                itemBrandToProduct.notifyDataSetChanged();
            } else {
                recyclerBrandView.setVisibility(View.GONE);
                noBrandTextView.setVisibility(View.VISIBLE);
                noBrandTextView.setText("Hiện không có brand nào.");
            }
        });

        return view;
    }

}