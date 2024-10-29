package com.example.androidproject.features.admin_manager.product_management.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.admin_manager.product_management.usecase.ProductManagementUsecase;

import java.util.List;

public class ProductManagementStatusAdapter extends RecyclerView.Adapter<ProductManagementStatusAdapter.ProductManagementStatusViewHolder> {
    private List<String> statusList;
    Context context;
    private int selectedItem = 0;
    private ProductManagementUsecase productManagementUsecase = new ProductManagementUsecase();

    public ProductManagementStatusAdapter(Context context) {
        this.statusList = productManagementUsecase.getStatusList();
        this.context = context;
    }

    public ProductManagementStatusAdapter(Context context, List<String> list) {
        this.statusList = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductManagementStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_status_product_management, parent, false);

        return new ProductManagementStatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductManagementStatusViewHolder holder, int position) {
        holder.item_status.setText(statusList.get(position));

        // Cập nhật màu nền dựa trên trạng thái đã chọn
        if (position == selectedItem) {
            holder.item_status.setTextColor(context.getResources().getColor(R.color.white));
            holder.itemView.setBackgroundResource(R.drawable.item_background_black_selector);
        } else {
            holder.item_status.setTextColor(context.getResources().getColor(R.color.black));
            holder.itemView.setBackgroundResource(R.drawable.item_background_light_grey_selector);
        }

        // Thiết lập click listener
        holder.itemView.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition(); // Lấy vị trí thực tế của item
            if (adapterPosition != RecyclerView.NO_POSITION) {
                // Cập nhật vị trí item được chọn
                selectedItem = adapterPosition;
                // Thông báo cho adapter cập nhật giao diện
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(statusList == null) {
            return 0;
        }
        return statusList.size();
    }

    public class ProductManagementStatusViewHolder extends RecyclerView.ViewHolder {
        private TextView item_status;

        public ProductManagementStatusViewHolder(@NonNull View view) {
            super(view);
            item_status = view.findViewById(R.id.item_textView_status_product_management);
        }
    }
}