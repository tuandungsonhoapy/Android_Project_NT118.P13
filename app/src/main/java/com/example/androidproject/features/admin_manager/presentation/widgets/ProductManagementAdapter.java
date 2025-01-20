package com.example.androidproject.features.admin_manager.presentation.widgets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.androidproject.core.utils.ConvertFormat;
import com.example.androidproject.features.admin_manager.presentation.product.ProductDetailAdminActivity;
import com.example.androidproject.features.product.data.entity.ProductEntity;
import com.example.androidproject.features.product.data.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class ProductManagementAdapter extends RecyclerView.Adapter<ProductManagementAdapter.ProductManagementViewHolder> {
    private List<ProductEntity> productList;
    Context context;

    public ProductManagementAdapter(Context context, List<ProductEntity> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductManagementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_product_admin, parent, false);

        return new ProductManagementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductManagementViewHolder holder, int position) {
        ProductEntity product = productList.get(position);

        holder.productName.setText(product.getName());
        holder.productID.setText(product.getId());
        holder.productPrice.setText(ConvertFormat.formatPriceToVND(product.getPrice()));
        holder.productInventory.setText(product.getStockQuantity() + " máy");
        Glide.with(context)
                .load(product.getImages().get(0))
                .override(300, 300)
                .centerCrop()
                .into(holder.productImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailAdminActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("product_id", product.getId());
            bundle.putString("product_name", product.getName());
            bundle.putString("product_price", ConvertFormat.formatPriceToVND(product.getPrice()));
            bundle.putString("product_inventory", product.getStockQuantity() + " máy");
            bundle.putString("brand_name", product.getBrandId());
            bundle.putString("product_status", "Hiển thị");
            bundle.putStringArrayList("product_images", new ArrayList<>(product.getImages()));
            bundle.putParcelableArrayList("product_options", new ArrayList<>(product.getOptions()));

            intent.putExtras(bundle);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if(productList == null) return 0;
        return productList.size();
    }

    public void setProductList(List<ProductEntity> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    public class ProductManagementViewHolder extends RecyclerView.ViewHolder {
        private TextView productName, productID, productPrice, productInventory;
        private ImageView productImage;

        public ProductManagementViewHolder(@NonNull View view) {
            super(view);

            productName = view.findViewById(R.id.tvProductNameManagement);
            productID = view.findViewById(R.id.tvProductID);
            productPrice = view.findViewById(R.id.tvProductPrice);
            productInventory = view.findViewById(R.id.tvProductIventory);
            productImage = view.findViewById(R.id.imgView_product_management);
        }
    }
}
