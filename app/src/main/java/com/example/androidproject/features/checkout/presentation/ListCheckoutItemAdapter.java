package com.example.androidproject.features.checkout.presentation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidproject.R;
import com.example.androidproject.core.utils.MoneyFomat;
import com.example.androidproject.features.cart.data.entity.ProductsOnCart;
import com.example.androidproject.features.cart.presentation.CartActivity;
import com.example.androidproject.features.cart.usecase.CartUseCase;
import com.example.androidproject.features.order.data.ProductDataForOrderModel;
import com.example.androidproject.features.product.presentation.ProductDetailActivity;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;

public class ListCheckoutItemAdapter extends RecyclerView.Adapter<ListCheckoutItemAdapter.ListCheckoutItemAdapterViewHolder> {
    private List<ProductsOnCart> productList;
    private Context context;
    private CartUseCase cartUseCase = new CartUseCase();

    public ListCheckoutItemAdapter(List<ProductsOnCart> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ListCheckoutItemAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_checkout, parent, false);
        return new ListCheckoutItemAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListCheckoutItemAdapterViewHolder holder, int position) {
        ProductsOnCart product = productList.get(position);

        Glide.with(context)
                .load(product.getProductImage())
                .into(holder.ivItemImage);
        holder.tvItemName.setText(product.toString());
        holder.tvItemQuantity.setText(String.valueOf(product.getQuantity()));
        holder.tvItemPrice.setText(MoneyFomat.format(product.getProductPrice()));

        holder.ivItemImage.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("productId", product.getProductId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ListCheckoutItemAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItemImage;
        TextView tvItemName;
        TextView tvItemQuantity;
        TextView tvItemPrice;

        public ListCheckoutItemAdapterViewHolder(View itemView) {
            super(itemView);

            ivItemImage = itemView.findViewById(R.id.ivItemImage);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemQuantity =itemView.findViewById(R.id.tvItemQuantity);
            tvItemPrice =itemView.findViewById(R.id.tvItemPrice);
        }
    }
}
