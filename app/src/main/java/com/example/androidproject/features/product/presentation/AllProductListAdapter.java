package com.example.androidproject.features.product.presentation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.product.data.model.ProductModel;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;

public class AllProductListAdapter extends RecyclerView.Adapter<AllProductListAdapter.AllProductListAdapterViewHolder> {
    private List<ProductModel> productList;
    private Context context;

    public AllProductListAdapter(List<ProductModel> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public AllProductListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_all_product, parent, false);
        return new AllProductListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllProductListAdapterViewHolder holder, int position) {
        ProductModel product = productList.get(position);
        holder.productImage.setImageResource(product.getImage());
        holder.productName.setText(product.getName());
        holder.productBrand.setText("Laptop");
        holder.productPrice.setText(String.valueOf(product.getPrice()));
        holder.addToCartIcon.setOnClickListener(v -> {

        });
        holder.heartIcon.setOnClickListener(v -> {

        });

        if (product.isFavorite()) {
            holder.heartIcon.setImageResource(R.drawable.favorite_heart);
            holder.heartIcon.setColorFilter(ContextCompat.getColor(context, R.color.pink));
        } else {
            holder.heartIcon.setImageResource(R.drawable.unfavorite_heart);
            holder.heartIcon.setColorFilter(ContextCompat.getColor(context, R.color.grey));
        }

        holder.heartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.isFavorite()) {
                    holder.heartIcon.setImageResource(R.drawable.favorite_heart);
                    holder.heartIcon.setColorFilter(ContextCompat.getColor(context, R.color.pink));
                } else {
                    holder.heartIcon.setImageResource(R.drawable.unfavorite_heart);
                    holder.heartIcon.setColorFilter(ContextCompat.getColor(context, R.color.grey));
                }
                product.setFavorite(!product.isFavorite());
            }
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class AllProductListAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productBrand;
        TextView productPrice;
        ImageView addToCartIcon;
        ImageView heartIcon;

        public AllProductListAdapterViewHolder(View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productBrand = itemView.findViewById(R.id.productBrand);
            productPrice = itemView.findViewById(R.id.productPrice);
            addToCartIcon = itemView.findViewById(R.id.addToCartIcon);
            heartIcon = itemView.findViewById(R.id.heartIcon);
        }
    }
}
