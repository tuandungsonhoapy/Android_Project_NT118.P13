package com.example.androidproject.features.admin_manager.presentation.widgets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidproject.R;
import com.example.androidproject.features.admin_manager.presentation.brand.DetailBrandAdminActivity;
import com.example.androidproject.features.brand.data.entity.BrandEntity;

import java.util.List;

public class ListBrandItemAdminAdapter extends RecyclerView.Adapter<ListBrandItemAdminAdapter.ListBrandItemAdminViewHolder> {
    private Context ctx;
    private Activity activity;
    private List<BrandEntity> brandList;

    public ListBrandItemAdminAdapter(Activity activity, Context ctx, List<BrandEntity> brandList) {
        this.ctx = ctx;
        this.brandList = brandList;
        this.activity = activity;
    }

    @Override
    public ListBrandItemAdminViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_brand_admin, parent, false);
        return new ListBrandItemAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListBrandItemAdminViewHolder holder, int position) {
        BrandEntity brand = brandList.get(position);
        holder.tvBrandID.setText(brand.getId());
        holder.tvBrandName.setText(brand.getName());
        Glide.with(ctx)
                .load(brand.getImageUrl())
                .override(300, 300)
                .centerCrop()
                .into(holder.ivBrandImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(ctx, DetailBrandAdminActivity.class);
            intent.putExtra("brand_id", brand.getId());
            activity.startActivityForResult(intent, 1);
        });
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    public void updateBrandList(List<BrandEntity> newBrandList) {
        brandList.clear();
        brandList.addAll(newBrandList);
        notifyDataSetChanged();
    }

    public static class ListBrandItemAdminViewHolder extends RecyclerView.ViewHolder {
        TextView tvBrandName;
        TextView tvBrandID;
        ImageView ivBrandImage;

        public ListBrandItemAdminViewHolder(View itemView) {
            super(itemView);

            tvBrandName = itemView.findViewById(R.id.tvBrandName);
            tvBrandID = itemView.findViewById(R.id.tvBrandID);
            ivBrandImage = itemView.findViewById(R.id.ivBrandImage);
        }
    }
}
