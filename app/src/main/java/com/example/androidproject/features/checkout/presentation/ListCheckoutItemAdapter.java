package com.example.androidproject.features.checkout.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.order.data.ProductDataForOrderModel;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;

public class ListCheckoutItemAdapter extends RecyclerView.Adapter<ListCheckoutItemAdapter.ListCheckoutItemAdapterViewHolder> {
    private List<ProductDataForOrderModel> productList;
    private Context context;

    public ListCheckoutItemAdapter(List<ProductDataForOrderModel> productList, Context context) {
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
        ProductDataForOrderModel product = productList.get(position);

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
