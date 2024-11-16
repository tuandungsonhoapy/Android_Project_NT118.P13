package com.example.androidproject.features.admin_manager.presentation.widgets;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidproject.R;
import com.example.androidproject.core.utils.ImageUriHandler;
import com.example.androidproject.features.admin_manager.presentation.category.DetailCategoryAdminActivity;
import com.example.androidproject.features.category.data.entity.CategoryEntity;
import com.example.androidproject.features.category.data.model.CategoryModel;
import com.example.androidproject.features.category.usecase.CategoryUseCase;

import java.io.InputStream;
import java.util.List;

public class ListCategoryItemAdminAdapter extends RecyclerView.Adapter<ListCategoryItemAdminAdapter.ListCategoryItemAdminViewHolder> {
    private List<CategoryEntity> categoryList;
    private Context context;
    private ImageUriHandler imageUriHandler = new ImageUriHandler();
    private CategoryUseCase categoryUseCase = new CategoryUseCase();
    public ListCategoryItemAdminAdapter(List<CategoryEntity> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public ListCategoryItemAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_category_admin, parent, false);
        return new ListCategoryItemAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCategoryItemAdminViewHolder holder, int position) {
        CategoryEntity category = categoryList.get(position);
        holder.tvCategoryID.setText(category.getId());
        holder.tvCategoryName.setText(category.getCategoryName());
        holder.tvCategoryQuantity.setText("10");
        Glide.with(context)
                .load(category.getImageUrl())
                .override(300, 300)
                .centerCrop()
                .into(holder.ivCategoryImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailCategoryAdminActivity.class);
            intent.putExtra("category_id", "#TECH113");
            intent.putExtra("category_name", category.getCategoryName());
            intent.putExtra("category_quantity", "10");
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void updateCategoryList(List<CategoryEntity> newCategoryList) {
        categoryList.clear();
        categoryList.addAll(newCategoryList);
        notifyDataSetChanged();
    }

    public static class ListCategoryItemAdminViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName;
        TextView tvCategoryID;
        TextView tvCategoryQuantity;
        ImageView ivCategoryImage;

        public ListCategoryItemAdminViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            tvCategoryID = itemView.findViewById(R.id.tvCategoryID);
            tvCategoryQuantity = itemView.findViewById(R.id.tvCategoryQuantity);
            ivCategoryImage = itemView.findViewById(R.id.ivCategoryImage);
        }
    }
}
