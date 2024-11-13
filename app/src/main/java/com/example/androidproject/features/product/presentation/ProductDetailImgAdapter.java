package com.example.androidproject.features.product.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidproject.R;

import java.util.List;

public class ProductDetailImgAdapter extends RecyclerView.Adapter<ProductDetailImgAdapter.ProductDetailImgViewHolder> {

    private List<String> productDetailImgList;
    private Context context;

    public ProductDetailImgAdapter(Context context, List<String> productDetailImgList) {
        this.context = context;
        this.productDetailImgList = productDetailImgList;
    }

    public void setImgs(List<String> productDetailImgList) {
        this.productDetailImgList = productDetailImgList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductDetailImgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ProductDetailImgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductDetailImgViewHolder holder, int position) {
        String img = productDetailImgList.get(position);

        Glide.with(context)
                .load(img)
                .error(R.drawable.warning_icon)
                .into(holder.imgProductDetail);
    }

    @Override
    public int getItemCount() {
        if(productDetailImgList == null) {
            return 0;
        }
        return productDetailImgList.size();
    }

    public class ProductDetailImgViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProductDetail;

        public ProductDetailImgViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProductDetail = itemView.findViewById(R.id.img_product_detail);
        }
    }
}
