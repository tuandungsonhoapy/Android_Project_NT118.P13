package com.example.androidproject.features.product.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.product.data.entity.ProductOption;

import java.util.List;

public class ProductDetailOptionAdapter extends RecyclerView.Adapter<ProductDetailOptionAdapter.ProductDetailOptionViewHolder> {
    private List<ProductOption> productOptions;
    Context context;
    private int selectedItem = 0;
    private OnOptionSelectedListener onOptionSelectedListener;

    public ProductDetailOptionAdapter(List<ProductOption> productOptions, Context context) {
        this.productOptions = productOptions;
        this.context = context;
    }

    public void setOnOptionSelectedListener(OnOptionSelectedListener listener) {
        this.onOptionSelectedListener = listener;
    }

    public interface OnOptionSelectedListener {
        void onOptionSelected(ProductOption selectedOption);
    }

    @NonNull
    @Override
    public ProductDetailOptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_options_pd, parent, false);
        return new ProductDetailOptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductDetailOptionViewHolder holder, int position) {
        ProductOption productOption = productOptions.get(position);

        holder.item_option.setText(productOption.getChip() + " - " + productOption.getRam() + "/" + productOption.getRom());

        // Thiết lập click listener
        holder.itemView.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition(); // Lấy vị trí thực tế của item
            if (onOptionSelectedListener != null) {
                onOptionSelectedListener.onOptionSelected(productOption);
            }
            if (adapterPosition != RecyclerView.NO_POSITION) {
                // Cập nhật vị trí item được chọn
                selectedItem = adapterPosition;
                // Thông báo cho adapter cập nhật giao diện
                notifyDataSetChanged();
            }
        });

        // Cập nhật màu nền dựa trên trạng thái đã chọn
        if (position == selectedItem) {
            holder.item_option.setTextColor(context.getResources().getColor(R.color.white));
            holder.itemView.setBackgroundResource(R.drawable.item_background_red_selector);
        } else {
            holder.item_option.setTextColor(context.getResources().getColor(R.color.black));
            holder.itemView.setBackgroundResource(R.drawable.item_background_white_selector);
        }
    }

    @Override
    public int getItemCount() {
        if(productOptions == null) {
            return 0;
        }
        return productOptions.size();
    }

    public ProductOption getSelectedItem() {
        return productOptions.get(selectedItem);
    }

    public class ProductDetailOptionViewHolder extends RecyclerView.ViewHolder {
        private TextView item_option;

        public ProductDetailOptionViewHolder(@NonNull View itemView) {
            super(itemView);

            item_option = itemView.findViewById(R.id.item_textView_option_pd);
        }

    }
}
