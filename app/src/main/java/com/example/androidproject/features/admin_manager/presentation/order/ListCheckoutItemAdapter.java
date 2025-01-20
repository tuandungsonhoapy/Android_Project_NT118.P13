package com.example.androidproject.features.admin_manager.presentation.order;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidproject.R;
import com.example.androidproject.core.utils.MoneyFomat;
import com.example.androidproject.features.cart.data.entity.ProductsOnCart;
import com.example.androidproject.features.cart.presentation.CartActivity;
import com.example.androidproject.features.cart.presentation.ListCartItemAdapter;
import com.example.androidproject.features.cart.usecase.CartUseCase;
import com.example.androidproject.features.product.presentation.ProductDetailActivity;

import java.util.List;

public class ListCheckoutItemAdapter extends RecyclerView.Adapter<ListCheckoutItemAdapter.ListCheckoutItemAdapterViewHolder>{
    private Context context;
    private List<ProductsOnCart> products;

    public ListCheckoutItemAdapter(List<ProductsOnCart> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ListCheckoutItemAdapter.ListCheckoutItemAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_product_checkout, parent, false);
        return new ListCheckoutItemAdapter.ListCheckoutItemAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCheckoutItemAdapter.ListCheckoutItemAdapterViewHolder holder, int position) {
        ProductsOnCart product = products.get(position);

        holder.tvItemName.setText(product.toString());
        holder.tvItemQuantity.setText(String.valueOf(product.getQuantity()));
        holder.tvItemPrice.setText(MoneyFomat.format(product.getProductPrice()));
        Glide.with(context).load(product.getProductImage()).into(holder.ivItemImage);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ListCheckoutItemAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItemImage;
        TextView tvItemName;
        ImageView ivPlus;
        ImageView ivMinus;
        TextView tvItemQuantity;
        TextView tvItemPrice;
        Button btnRemove;

        public ListCheckoutItemAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            ivItemImage = itemView.findViewById(R.id.ivItemImage);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            ivPlus = itemView.findViewById(R.id.ivPlus);
            ivMinus = itemView.findViewById(R.id.ivMinus);
            tvItemQuantity =itemView.findViewById(R.id.tvItemQuantity);
            tvItemPrice =itemView.findViewById(R.id.tvItemPrice);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
