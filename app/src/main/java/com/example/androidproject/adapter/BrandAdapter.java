package com.example.androidproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {
    private Context context;
    private List<Brand> brandList;

    public BrandAdapter(Context context, List<Brand> brandList) {
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
        Brand brand = brandList.get(position);
        holder.brandName.setText(brand.getName());
        holder.brandImage.setImageResource(brand.getImage());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class BrandViewHolder extends RecyclerView.ViewHolder {
        ImageView brandImage;
        TextView brandName;

        public BrandViewHolder(@NonNull View itemView) {
            super(itemView);

            brandName = itemView.findViewById(R.id.textViewBrand);
            brandImage = itemView.findViewById(R.id.imageViewBrand);
        }
    }
}
