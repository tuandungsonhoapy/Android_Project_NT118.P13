package com.example.androidproject.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidproject.R;
import com.example.androidproject.adapter.BannerAdapter;
import com.example.androidproject.adapter.Category;
import com.example.androidproject.adapter.CategoryAdapter;
import com.example.androidproject.adapter.Product;
import com.example.androidproject.adapter.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

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
    private CategoryAdapter categoryAdapter;
    private List<Category> categoryList;
    private BannerAdapter bannerAdapter;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    private Handler handler;
    private Runnable runnable;
    private int currentPage = 0;
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
        categoryList = new ArrayList<>();
        categoryList.add(new Category("Laptop", R.drawable.image_laptop));
        categoryList.add(new Category("Phone", R.drawable.image_phone));
        categoryList.add(new Category("Controller", R.drawable.image_controller));
        categoryList.add(new Category("Monitor", R.drawable.image_monitor));
        categoryList.add(new Category("Keyboard", R.drawable.image_keyboard));

        categoryAdapter = new CategoryAdapter(getContext(), categoryList);
        recyclerCategoryView.setAdapter(categoryAdapter);

        int[] images = {R.drawable.img, R.drawable.warning_icon};
        bannerAdapter = new BannerAdapter(getContext(), images);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == images.length) {
                    currentPage = 0;
                }
                viewPagerBanner.setCurrentItem(currentPage++, true); // Chuyển đến trang tiếp theo
                handler.postDelayed(this, 3000); // Thay đổi sau mỗi 3 giây
            }
        };

        productList = new ArrayList<>();
        productList.add(new Product("cc", R.drawable.ic_launcher_background, 12, 12));
        productList.add(new Product("cc", R.drawable.ic_launcher_background, 12, 12));
        productList.add(new Product("cc", R.drawable.ic_launcher_background, 12, 12));
        productList.add(new Product("cc", R.drawable.ic_launcher_background, 12, 12));
        productList.add(new Product("cc", R.drawable.ic_launcher_background, 12, 12));
        productList.add(new Product("cc", R.drawable.ic_launcher_background, 12, 12));
        productList.add(new Product("cc", R.drawable.ic_launcher_background, 12, 12));
        productList.add(new Product("cc", R.drawable.ic_launcher_background, 12, 12));

        ProductAdapter productAdapter = new ProductAdapter(getContext(), productList);
        recyclerProductView.setAdapter(productAdapter);
        int columns = 2;
        recyclerProductView.setLayoutManager(new GridLayoutManager(getContext(), columns));
        viewPagerBanner.setAdapter(bannerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerCategoryView.setLayoutManager(layoutManager);
        return view;
    }

}