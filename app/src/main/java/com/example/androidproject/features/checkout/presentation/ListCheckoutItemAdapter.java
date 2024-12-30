package com.example.androidproject.features.checkout.presentation;

import android.content.Context;
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

        holder.ivPlus.setOnClickListener(v -> {
            cartUseCase.updateCartQuantity(product.getProductId(), 1)
                    .thenAccept(r -> {
                        if(r.isRight()) {
                            int newQuantity = product.getQuantity() + 1;
                            product.setQuantity(newQuantity);
                            notifyItemChanged(holder.getAdapterPosition());
                            ((CheckoutActivity) context).updateUI();
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
                                productList.remove(product);
                                notifyItemRemoved(index);
                                ((CheckoutActivity) context).updateUI();
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
        return productList.size();
    }

    public static class ListCheckoutItemAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItemImage;
        TextView tvItemName;
        ImageView ivPlus;
        ImageView ivMinus;
        TextView tvItemQuantity;
        TextView tvItemPrice;

        public ListCheckoutItemAdapterViewHolder(View itemView) {
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
