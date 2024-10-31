package com.example.androidproject.features.product.presentation;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;

import java.util.List;

public class ProductDetailColorAdapter extends RecyclerView.Adapter<ProductDetailColorAdapter.ProductDetailColorViewHolder> {
    private List<String> colorsList;
    Context context;

    public ProductDetailColorAdapter(List<String> colorsList, Context context) {
        this.colorsList = colorsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductDetailColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_color_pd, parent, false);
        return new ProductDetailColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductDetailColorViewHolder holder, int position) {
        String color = colorsList.get(position);

        // Set color for item
        GradientDrawable background = (GradientDrawable) holder.item_color.getBackground();
        background.setColor(Color.parseColor(color));
    }

    @Override
    public int getItemCount() {

        if(colorsList == null) {
            return 0;
        }
        return colorsList.size();
    }

    public class ProductDetailColorViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout item_color;

        public ProductDetailColorViewHolder(@NonNull View itemView) {
            super(itemView);

            item_color = itemView.findViewById(R.id.item_color_pd_id);
        }
    }
}
