package com.example.androidproject.features.category.presentation;

import android.content.Context;
import android.content.Intent;
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
import com.example.androidproject.features.category.data.entity.CategoryEntity;
import com.example.androidproject.features.category.data.model.CategoryModel;
import com.example.androidproject.features.product.presentation.AllProductActivity;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewholder> {
    private List<CategoryEntity> categories;
    private Context context;

    public CategoryAdapter(Context context, List<CategoryEntity> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_categories, parent, false);
        return new CategoryViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewholder holder, int position) {
        CategoryEntity category = categories.get(position);
        holder.categoryName.setText(category.getCategoryName());
        Glide.with(context).load(category.getImageUrl()).into(holder.categoryImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AllProductActivity.class);
                intent.putExtra("categoryId", category.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoryViewholder extends  RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView categoryName;

        public CategoryViewholder(@NonNull View itemView) {
            super(itemView);

            categoryImage = itemView.findViewById(R.id.imageViewCategory);
            categoryName = itemView.findViewById(R.id.textViewCategory);
        }
    }
}
