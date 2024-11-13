package com.example.androidproject.features.cart.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.cart.data.model.CartModel;
import com.example.androidproject.features.order.data.ProductDataForOrderModel;

import java.util.List;

public class ListCartItemAdapter extends RecyclerView.Adapter<ListCartItemAdapter.ListCartItemAdapterViewHolder>{
    private Context context;
    private List<ProductDataForOrderModel> cartList;

    public ListCartItemAdapter(List<ProductDataForOrderModel> cartList, Context context) {
        this.cartList = cartList;
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
        ProductDataForOrderModel cartModel = cartList.get(position);

        holder.ivItemImage.setImageResource(R.drawable.ic_launcher_background);
        holder.tvItemName.setText("Apple Macbook Air 2020 13 inch \u2028(Apple M1 - 8GB/ 256GB) - MGND3SA/A");
        holder.tvItemQuantity.setText("3");
        holder.tvItemPrice.setText("15.000.000");
        holder.ivPlus.setOnClickListener(v -> {

        });

        holder.ivMinus.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
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
