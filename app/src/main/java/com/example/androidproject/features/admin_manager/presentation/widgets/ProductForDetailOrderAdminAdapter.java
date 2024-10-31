package com.example.androidproject.features.admin_manager.presentation.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.order.data.ProductDataForOrderModel;

import java.util.List;

    public class ProductForDetailOrderAdminAdapter extends RecyclerView.Adapter<ProductForDetailOrderAdminAdapter.ProductForDetailOrderAdminViewHolder> {
        List<ProductDataForOrderModel> productDataList;
        private Context context;

        public ProductForDetailOrderAdminAdapter(List<ProductDataForOrderModel> productDataList, Context context) {
            this.productDataList = productDataList;
            this.context = context;
        }

        @NonNull
        @Override
        public ProductForDetailOrderAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_for_detail_order_admin, parent, false);
            return new ProductForDetailOrderAdminViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductForDetailOrderAdminViewHolder holder, int position) {
            ProductDataForOrderModel product = productDataList.get(position);
            holder.tvProductName.setText(product.getProduct().getName());
            holder.tvProductPrice.setText(String.valueOf(product.getProduct().getPrice()));
            holder.tvProductQuantity.setText(String.valueOf(product.getQuantity()));
        }

        @Override
        public int getItemCount() {
            return productDataList.size();
        }

        public static class ProductForDetailOrderAdminViewHolder extends RecyclerView.ViewHolder {
            private TextView tvProductName;
            private TextView tvProductPrice;
            private TextView tvProductQuantity;

            public ProductForDetailOrderAdminViewHolder(@NonNull View itemView) {
                super(itemView);
                tvProductName = itemView.findViewById(R.id.tv_product_name);
                tvProductPrice = itemView.findViewById(R.id.tv_product_price);
                tvProductQuantity = itemView.findViewById(R.id.tv_product_quantity);
            }
        }
    }
