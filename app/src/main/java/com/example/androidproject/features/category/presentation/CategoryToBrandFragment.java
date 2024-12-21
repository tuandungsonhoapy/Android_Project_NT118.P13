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
    private BrandUseCase brandUseCase = new BrandUseCase();

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

        List<BrandEntity> brandList = new ArrayList<>();
        brandUseCase.getBrandListByCategory(category).thenAccept(r -> {
            if (r.isRight()) {
                brandList.addAll(r.getRight());

                ItemBrandToProduct itemBrandToProduct = new ItemBrandToProduct(getContext(), brandList);
                recyclerBrandView.setAdapter(itemBrandToProduct);
                itemBrandToProduct.notifyDataSetChanged();
                recyclerBrandView.scrollToPosition(0);
                recyclerBrandView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });

        return view;
    }

}