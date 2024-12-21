package com.example.androidproject.features.admin_manager.presentation.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.product.data.entity.ProductEntity;
import com.example.androidproject.features.product.data.entity.ProductOption;

import java.util.List;

public class ProductOptionListAdapter extends RecyclerView.Adapter<ProductOptionListAdapter.ProductOptionListViewHolder> {
    private List<ProductOption> productOptionList;
    Context context;

    public ProductOptionListAdapter(Context context, List<ProductOption> productOptionList) {
        this.context = context;
        this.productOptionList = productOptionList;
    }

    @NonNull
    @Override
    public ProductOptionListAdapter.ProductOptionListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_product_option, parent, false);

        return new ProductOptionListAdapter.ProductOptionListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductOptionListAdapter.ProductOptionListViewHolder holder, int position) {
        ProductOption productOption = productOptionList.get(position);

        holder.productChip.setText(productOption.getChip());
        holder.productRamAndRom.setText(productOption.getRam() + "/" + productOption.getRom());
        holder.productInventory.setText(productOption.getQuantity() + " máy");
    }

    @Override
    public int getItemCount() {
        if (productOptionList != null) {
            return productOptionList.size();
        }
        return 0;
    }

    public void setProductOptionList(List<ProductOption> productOptionList) {
        this.productOptionList = productOptionList;
        notifyDataSetChanged();
    }

    public class ProductOptionListViewHolder extends RecyclerView.ViewHolder {
        private TextView productChip, productRamAndRom, productInventory;

        public ProductOptionListViewHolder(@NonNull View view) {
            super(view);

            productChip = view.findViewById(R.id.tvChip);
            productRamAndRom = view.findViewById(R.id.tvRamAndRom);
            productInventory = view.findViewById(R.id.tvProductIventory);
        }
    }
}
