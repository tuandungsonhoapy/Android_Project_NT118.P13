package com.example.androidproject.features.admin_manager.presentation.widgets;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.admin_manager.presentation.category.DetailCategoryAdminActivity;
import com.example.androidproject.features.category.data.model.CategoryModel;

import java.util.List;

public class ListCategoryItemAdminAdapter extends RecyclerView.Adapter<ListCategoryItemAdminAdapter.ListCategoryItemAdminViewHolder> {
    private List<CategoryModel> categoryList;
    private Context context;

    public ListCategoryItemAdminAdapter(List<CategoryModel> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public ListCategoryItemAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_category_admin, parent, false);
        return new ListCategoryItemAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCategoryItemAdminViewHolder holder, int position) {
        CategoryModel category = categoryList.get(position);
        holder.tvCategoryID.setText("#TECH113");
        holder.tvCategoryName.setText(category.getCategoryName());
        holder.tvCategoryQuantity.setText("10");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailCategoryAdminActivity.class);
            intent.putExtra("category_id", "#TECH113");
            intent.putExtra("category_name", category.getCategoryName());
            intent.putExtra("category_quantity", "10");
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ListCategoryItemAdminViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName;
        TextView tvCategoryID;
        TextView tvCategoryQuantity;

        public ListCategoryItemAdminViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            tvCategoryID = itemView.findViewById(R.id.tvCategoryID);
            tvCategoryQuantity = itemView.findViewById(R.id.tvCategoryQuantity);
        }
    }
}
