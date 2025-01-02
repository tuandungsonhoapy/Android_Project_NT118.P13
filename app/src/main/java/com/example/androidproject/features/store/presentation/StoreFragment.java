package com.example.androidproject.features.store.presentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.androidproject.R;
import com.example.androidproject.features.brand.data.entity.BrandEntity;
import com.example.androidproject.features.brand.presentation.BrandAdapter;
import com.example.androidproject.features.brand.usecase.BrandUseCase;
import com.example.androidproject.features.category.data.entity.CategoryEntity;
import com.example.androidproject.features.category.presentation.CategoryToBrandAdapter;
import com.example.androidproject.features.category.usecase.CategoryUseCase;
import com.example.androidproject.features.product.presentation.AllProductActivity;
import com.example.androidproject.features.store.usecase.StoreUseCase;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoreFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerBrandView;
    private TabLayout tabLayout;
    private ViewPager2 viewPagerCategoryToBrand;
    private ImageView img_search;
    private EditText edt_search;
    private StoreUseCase storeUseCase = new StoreUseCase();
    private BrandUseCase brandUseCase = new BrandUseCase();
    private CategoryUseCase categoryUseCase = new CategoryUseCase();
    public StoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StoreFragment newInstance(String param1, String param2) {
        StoreFragment fragment = new StoreFragment();
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
        View view = inflater.inflate(R.layout.fragment_store, container, false);

        recyclerBrandView = view.findViewById(R.id.recycler_brand_view);
        recyclerBrandView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        tabLayout = view.findViewById(R.id.tab_categories);
        viewPagerCategoryToBrand = view.findViewById(R.id.view_pager_categories_to_brand);


        // Get brand list
        List<BrandEntity> brandList = new ArrayList<>();
        BrandAdapter brandAdapter = new BrandAdapter(getContext(), brandList);
        brandUseCase.getBrandListForStoreScreen().thenAccept(r -> {
            if (r.isRight()) {
                brandList.addAll(r.getRight());

                getActivity().runOnUiThread(() -> brandAdapter.notifyDataSetChanged());
            }
        });
        recyclerBrandView.setAdapter(brandAdapter);

        // Get category list
        List<CategoryEntity> categoryList = new ArrayList<>();
        CategoryToBrandAdapter categoryToBrandFragment = new CategoryToBrandAdapter(requireActivity(), categoryList);
        viewPagerCategoryToBrand.setAdapter(categoryToBrandFragment);
        categoryUseCase.getCategoryListForStoreScreen().thenAccept(r -> {
            if (r.isRight()) {
                categoryList.addAll(r.getRight());
                getActivity().runOnUiThread(() -> {
                    categoryToBrandFragment.notifyDataSetChanged();
                    new TabLayoutMediator(tabLayout, viewPagerCategoryToBrand, (tab, position) -> {
                        tab.setText(categoryList.get(position).getCategoryName());
                    }).attach();
                });
            }
        });

        img_search = view.findViewById(R.id.btnSearch);
        edt_search = view.findViewById(R.id.edt_search);
        img_search.setOnClickListener(v -> {
            String search = edt_search.getText().toString();
            Intent intent = new Intent(getActivity(), AllProductActivity.class);
            intent.putExtra("search", search);
            startActivity(intent);
        });

        return view;
    }
}