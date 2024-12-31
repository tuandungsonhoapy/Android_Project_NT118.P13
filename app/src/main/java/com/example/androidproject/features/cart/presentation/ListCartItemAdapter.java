package com.example.androidproject.features.cart.presentation;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidproject.R;
import com.example.androidproject.core.utils.MoneyFomat;
import com.example.androidproject.features.cart.data.entity.ProductsOnCart;
import com.example.androidproject.features.cart.data.model.CartModel;
import com.example.androidproject.features.cart.usecase.CartUseCase;
import com.example.androidproject.features.order.data.ProductDataForOrderModel;
import com.example.androidproject.features.product.presentation.ProductDetailActivity;

import java.util.List;

public class ListCartItemAdapter extends RecyclerView.Adapter<ListCartItemAdapter.ListCartItemAdapterViewHolder>{
    private Context context;
    private List<ProductsOnCart> products;
    private CartUseCase cartUseCase = new CartUseCase();

    public ListCartItemAdapter(List<ProductsOnCart> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ListCartItemAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_cart, parent, false);
        return new ListCartItemAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCartItemAdapterViewHolder holder, int position) {
        ProductsOnCart product = products.get(position);

        holder.tvItemName.setText(product.toString());
        holder.tvItemQuantity.setText(String.valueOf(product.getQuantity()));
        holder.tvItemPrice.setText(MoneyFomat.format(product.getProductPrice()));
        Glide.with(context).load(product.getProductImage()).into(holder.ivItemImage);

        holder.ivItemImage.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("productId", product.getProductId());
            context.startActivity(intent);
        });

        holder.ivPlus.setOnClickListener(v -> {
            cartUseCase.updateCartQuantity(product.getProductId(), 1)
                    .thenAccept(r -> {
                        if(r.isRight()) {
                            int newQuantity = product.getQuantity() + 1;
                            product.setQuantity(newQuantity);
                            notifyItemChanged(holder.getAdapterPosition());
                            Toast.makeText(context, "Cập nhật số lượng thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Cập nhật số lượng thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        holder.ivMinus.setOnClickListener(v -> {
            cartUseCase.updateCartQuantity(product.getProductId(), -1)
                    .thenAccept(r -> {
                        if(r.isRight()) {
                            int newQuantity = product.getQuantity() - 1;
                            if(newQuantity <= 0) {
                                int index = holder.getAdapterPosition();
                                products.remove(product);
                                notifyItemRemoved(index);
                                ((CartActivity) context).updateUI();
                                Toast.makeText(context, "Sản phẩm đã bị xóa khỏi giỏ hàng", Toast.LENGTH_SHORT).show();
                            } else {
                                product.setQuantity(newQuantity);
                                holder.tvItemQuantity.setText(String.valueOf(newQuantity));
                                Toast.makeText(context, "Cập nhật số lượng thành công", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Cập nhật số lượng thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ListCartItemAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItemImage;
        TextView tvItemName;
        ImageView ivPlus;
        ImageView ivMinus;
        TextView tvItemQuantity;
        TextView tvItemPrice;

        public ListCartItemAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            ivItemImage = itemView.findViewById(R.id.ivItemImage);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            ivPlus = itemView.findViewById(R.id.ivPlus);
            ivMinus = itemView.findViewById(R.id.ivMinus);
            tvItemQuantity =itemView.findViewById(R.id.tvItemQuantity);
            tvItemPrice =itemView.findViewById(R.id.tvItemPrice);
        }
    }
}
