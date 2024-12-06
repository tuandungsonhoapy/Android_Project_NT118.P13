package com.example.androidproject.features.brand.presentation;

import android.content.Context;
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
import com.example.androidproject.features.brand.data.model.BrandModel;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {
    private Context context;
    private List<BrandModel> brandList;

    public BrandAdapter(Context context, List<BrandModel> brandList) {
        this.context = context;
        this.brandList = brandList;
    }

    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_store_brand, parent, false);
        return new BrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {
        BrandModel brand = brandList.get(position);
        holder.brandName.setText(brand.getName());
        Glide.with(context).load(brand.getImageUrl()).into(holder.brandImage);
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    public static class BrandViewHolder extends RecyclerView.ViewHolder {
        ImageView brandImage;
        TextView brandName;

        public BrandViewHolder(@NonNull View itemView) {
            super(itemView);

            brandName = itemView.findViewById(R.id.tv_store_brand_name);
            brandImage = itemView.findViewById(R.id.img_store_brand);
        }
    }
}
