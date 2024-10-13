package com.example.androidproject.features.category.presentation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.androidproject.features.category.data.model.Category;

import java.util.List;

public class CategoryToBrandAdapter extends FragmentStateAdapter {
    private List<Category> categoryList;

    public CategoryToBrandAdapter(@NonNull Fragment fragment, List<Category> categoryList) {
        super(fragment);
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return CategoryToBrandFragment.newInstance(categoryList.get(position).getCategoryName());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
