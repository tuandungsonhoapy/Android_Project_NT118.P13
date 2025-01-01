package com.example.androidproject.features.product.presentation;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidproject.R;
import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.ConvertFormat;
import com.example.androidproject.core.utils.counter.CounterModel;
import com.example.androidproject.features.cart.usecase.CartUseCase;
import com.example.androidproject.features.product.data.entity.ProductOption;
import com.example.androidproject.features.product.data.model.ProductModelFB;
import com.example.androidproject.features.wishlist.data.repository.WishlistRepository;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<ProductModelFB> products;
    private Context context;
    private CartUseCase cartUseCase = new CartUseCase();
    private long cartQuantity;
    private CounterModel counterModel = new CounterModel();

    //var
    private List<String> wishlist;

    //others
    private final WishlistRepository wishlistRepository;

    public ProductAdapter(Context context, List<ProductModelFB> products) {
        this.context = context;
        this.products = products;
        wishlistRepository = new WishlistRepository();
        fetchWishlist();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductModelFB product = products.get(position);
        Glide.with(context)
                .load(product.getImages().get(0))
                .override(300, 300)
                .centerCrop()
                .into(holder.productImage);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(ConvertFormat.formatPriceToVND(product.getPrice()));
        holder.productBrand.setText(product.getBrand().getName());

        updateWishlistState(holder, product.getId());

        holder.productFavorite.setOnClickListener(v -> {
            toggleWishlist(holder, product.getId());
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("productId", product.getId());
            intent.putExtra("brandName", product.getBrand().getName());
            context.startActivity(intent);
        });

        holder.addToCartIcon.setOnClickListener(v -> {
            counterModel.getQuantity("cart").addOnSuccessListener(quantity -> {
                cartQuantity = quantity;

                ProductOption option = product.getOptions().get(0);
                if(option.getQuantity() <= 0) {
                    return;
                }
                cartUseCase.addProductToCart(
                        product.getId(),
                        1,
                        product.getOptions().get(0),
                        cartQuantity
                ).thenAccept(r -> {
                    if (r.isRight()) {
                        Toast.makeText(context, "Thêm sản phẩm vào giỏ thành công", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context, "Thêm sản phẩm vào giỏ thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    private void updateWishlistState(ProductViewHolder holder, String productId) {
        if (wishlist != null && wishlist.contains(productId)) {
            holder.productFavorite.setImageResource(R.drawable.ic_wishlist_on);
            holder.productFavorite.setColorFilter(ContextCompat.getColor(context, R.color.primary));
        } else {
            holder.productFavorite.setImageResource(R.drawable.ic_wishlist_off);
            holder.productFavorite.setColorFilter(ContextCompat.getColor(context, R.color.grey));
        }
    }

    private void toggleWishlist(ProductViewHolder holder, String productId) {
        if (wishlist != null && wishlist.contains(productId)) {
            wishlist.remove(productId);
            Toast.makeText(context, "Removed from Wishlist", Toast.LENGTH_SHORT).show();
        } else {
            if (wishlist != null) {
                wishlist.add(productId);
            }
            Toast.makeText(context, "Added to Wishlist", Toast.LENGTH_SHORT).show();
        }

        wishlistRepository.updateWishlist(wishlist)
                .thenAccept(either -> {
                    if (either.isRight()) {
                        updateWishlistState(holder, productId);
                    } else {
                        showError(either.getLeft());
                        Toast.makeText(context, "Error: " + either.getLeft().getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .exceptionally(e -> {
                    showError(new Failure("Error updating wishlist: " + e.getMessage()));
                    return null;
                });
    }

    private void fetchWishlist() {
        wishlistRepository.getWishlist()
                .thenAccept(either -> {
                    if (either.isRight()) {
                        wishlist = either.getRight();
                        notifyDataSetChanged(); // Khi wishlist đã tải xong, cập nhật lại adapter
                    } else {
                        showError(either.getLeft());
                    }
                })
                .exceptionally(e -> {
                    showError(new Failure("An error occurred"));
                    return null;
                });
    }

    private void showError(Failure failure) {
        Log.e("ProductAdapter", "Error: " + failure.getErrorMessage());
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        TextView productBrand;
        ImageView productFavorite;
        ImageView addToCartIcon;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productBrand = itemView.findViewById(R.id.productBrand);
            productFavorite = itemView.findViewById(R.id.btnWishlist);
            addToCartIcon = itemView.findViewById(R.id.addToCartIcon);
        }
    }
}
