package com.example.androidproject.features.admin_manager.product_management.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidproject.R;
import com.example.androidproject.features.admin_manager.category_manager.presentation.DetailCategoryAdminActivity;
import com.example.androidproject.features.product.data.model.ProductModel;

import java.util.List;

public class ProductManagementAdapter extends RecyclerView.Adapter<ProductManagementAdapter.ProductManagementViewHolder> {
    private List<ProductModel> productList;
    Context context;

    public ProductManagementAdapter(Context context, List<ProductModel> productList) {
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
        ProductModel product = productList.get(position);

        holder.productName.setText(product.getName());
        holder.productID.setText("PRODUCT004");
        holder.productPrice.setText(product.getPrice() + " đ");
        holder.productInventory.setText(product.getQuantity() + " máy");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailAdminActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("product_id", "PRODUCT004");
            bundle.putString("product_name", product.getName());
            bundle.putString("product_price", product.getPrice() + " đ");
            bundle.putString("product_inventory", product.getQuantity() + " máy");
            bundle.putString("brand_name", product.getBrand().getName());
            bundle.putString("product_status", "Hiển thị");

            intent.putExtras(bundle);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if(productList == null) return 0;
        return productList.size();
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
