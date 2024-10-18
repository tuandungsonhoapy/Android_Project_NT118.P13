package com.example.androidproject.features.store.presentation;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.brand.data.model.BrandModel;
import com.example.androidproject.features.category.data.model.CategoryModel;

import java.util.List;

public class ItemBrandToProduct extends RecyclerView.Adapter<ItemBrandToProduct.ViewHolder> {
    private List<CategoryModel> categoryList;
    private List<BrandModel> brandList;
    private Context context;

    public ItemBrandToProduct(Context context, List<BrandModel> brandList, List<CategoryModel> categoryList) {
        this.context = context;
        this.brandList = brandList;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_to_brand, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BrandModel brand = brandList.get(position);
        holder.brandName.setText(brand.getName());
        holder.brandQuantity.setText(brand.getQuantity() + " products");
        holder.brandImage.setImageResource(brand.getImageResource());
        holder.gridLayout.removeAllViews();

        for (CategoryModel category : categoryList) {
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(category.getCategoryImage());
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 100;
            params.height = 100;
            params.setMargins(10, 10, 10, 10);
            imageView.setLayoutParams(params);

            holder.gridLayout.addView(imageView);
        }
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView brandImage;
        TextView brandName;
        TextView brandQuantity;
        GridLayout gridLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            brandImage = itemView.findViewById(R.id.store_brand_img);
            brandName = itemView.findViewById(R.id.store_brand_name);
            brandQuantity = itemView.findViewById(R.id.store_brand_quantity);
            gridLayout = itemView.findViewById(R.id.grid_images);
        }
    }
}
