package com.example.androidproject.features.wishlist.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidproject.R;
import com.example.androidproject.core.utils.MoneyFomat;
import com.example.androidproject.features.product.data.entity.ProductEntity;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder> {
    private final Context context;
    private final List<ProductEntity> wishlistItems;
    private final OnAddToCartListener addToCartListener;
    private final OnAddToWishlistListener addToWishlistListener;

    public WishlistAdapter(Context context, List<ProductEntity> wishlistItems, OnAddToCartListener addToCartListener, OnAddToWishlistListener addToWishlistListener) {
        this.context = context;
        this.wishlistItems = wishlistItems;
        this.addToCartListener = addToCartListener;
        this.addToWishlistListener = addToWishlistListener;
    }

    @NonNull
    @Override
    public WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_card, parent, false);
        return new WishlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistViewHolder holder, int position) {
        ProductEntity item = wishlistItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return wishlistItems.size();
    }

    class WishlistViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvBrand, tvPrice;
        private ImageView ivProduct, btnAddToCart, btnWishlist;

        public WishlistViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.ivProductImage);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvBrand = itemView.findViewById(R.id.tvProductBrand);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
            btnWishlist = itemView.findViewById(R.id.btnWishlist);
        }

        public void bind(ProductEntity item) {
            if (item.getImages() != null && !item.getImages().isEmpty()) {
                loadImageFromUrl(item.getImages().get(0));
            } else {
                ivProduct.setImageResource(R.drawable.logo_techo_without_text);
            }
            tvName.setText(item.getName());
            tvBrand.setText(item.getBrandId());
            tvPrice.setText(MoneyFomat.formatToCurrency(item.getPrice()));

            updateWishlistButtonColor();

            btnAddToCart.setOnClickListener(v -> addToCartListener.onAddToCart(item));
            btnWishlist.setOnClickListener(v -> addToWishlistListener.onAddToWishlist(item.getId()));
        }

        private void updateWishlistButtonColor() {
            btnWishlist.setImageResource(R.drawable.ic_wishlist_on);
            btnWishlist.setImageTintList(context.getResources().getColorStateList(R.color.primary));
        }

        private void loadImageFromUrl(String url) {
            Glide.with(context)
                    .load(url)
                    .placeholder(R.drawable.logo_techo_without_text)
                    .into(ivProduct);
        }
    }

    public interface OnAddToCartListener {
        void onAddToCart(ProductEntity item);
    }
    public interface OnAddToWishlistListener {
        void onAddToWishlist(String productId);
    }
}