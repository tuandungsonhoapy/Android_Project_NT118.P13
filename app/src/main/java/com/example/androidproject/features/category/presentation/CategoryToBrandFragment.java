package com.example.androidproject.features.category.presentation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidproject.R;
import com.example.androidproject.features.brand.data.model.BrandModel;
import com.example.androidproject.features.brand.presentation.BrandAdapter;

import java.util.ArrayList;
import java.util.List;


public class CategoryToBrandFragment extends Fragment {

    private static final String ARG_CATEGORY = "category";
    private String category;
    private RecyclerView recyclerBrandView;
    private BrandAdapter brandAdapter;
    private List<BrandModel> brandList;


    public CategoryToBrandFragment() {
        // Required empty public constructor
    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_to_brand, container, false);

        recyclerBrandView = view.findViewById(R.id.recyclerview);
        recyclerBrandView.setLayoutManager(new LinearLayoutManager(getContext()));

        brandList = new ArrayList<>();
        brandAdapter = new BrandAdapter(getContext(), brandList);
        recyclerBrandView.setAdapter(brandAdapter);
        return view;
    }

    private List<BrandModel> getBrandListByCategory(String category) {
        List<BrandModel> brands = new ArrayList<>();
        if(category.equals("Máy tính")){
            brands.add(new BrandModel(1,"Acer", R.drawable.image_acer_logo));
            brands.add(new BrandModel(2,"Asus", R.drawable.image_asus_logo));
            brands.add(new BrandModel(3,"Dell", R.drawable.image_dell_logo));
            brands.add(new BrandModel(4,"HP", R.drawable.image_hp_logo));
        } else if (category.equals("Điện thoại")){
            brands.add(new BrandModel(1,"Acer", R.drawable.image_acer_logo));
            brands.add(new BrandModel(2,"Asus", R.drawable.image_asus_logo));
            brands.add(new BrandModel(3,"Dell", R.drawable.image_dell_logo));
            brands.add(new BrandModel(4,"HP", R.drawable.image_hp_logo));
        } else if (category.equals("Máy tính bảng")){
            brands.add(new BrandModel(1,"Acer", R.drawable.image_acer_logo));
            brands.add(new BrandModel(2,"Asus", R.drawable.image_asus_logo));
            brands.add(new BrandModel(3,"Dell", R.drawable.image_dell_logo));
            brands.add(new BrandModel(4,"HP", R.drawable.image_hp_logo));
        }

        return brands;
    }
}