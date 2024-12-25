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
import com.example.androidproject.core.utils.ConvertFormat;
import com.example.androidproject.core.utils.counter.CounterModel;
import com.example.androidproject.features.cart.usecase.CartUseCase;
import com.example.androidproject.features.product.data.entity.ProductOption;
import com.example.androidproject.features.product.data.model.ProductModel;
import com.example.androidproject.features.product.data.model.ProductModelFB;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<ProductModelFB> products;
    private Context context;
    private CartUseCase cartUseCase = new CartUseCase();
    private long cartQuantity;
    private CounterModel counterModel = new CounterModel();

    public ProductAdapter(Context context, List<ProductModelFB> products) {
        this.context = context;
        this.products = products;
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
        if (true) {
            holder.productFavorite.setImageResource(R.drawable.favorite_heart);
            holder.productFavorite.setColorFilter(ContextCompat.getColor(context, R.color.pink));
        } else {
            holder.productFavorite.setImageResource(R.drawable.unfavorite_heart);
            holder.productFavorite.setColorFilter(ContextCompat.getColor(context, R.color.grey));
        }

        holder.productFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (product.isFavorite()) {
//                    holder.productFavorite.setImageResource(R.drawable.favorite_heart);
//                    holder.productFavorite.setColorFilter(ContextCompat.getColor(context, R.color.pink));
//                } else {
//                    holder.productFavorite.setImageResource(R.drawable.unfavorite_heart);
//                    holder.productFavorite.setColorFilter(ContextCompat.getColor(context, R.color.grey));
//                }
//                product.setFavorite(!product.isFavorite());
            }
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
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
                        cartQuantity++;
                        counterModel.updateQuantity("cart");
                        Toast.makeText(context, "Add product to cart successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context, "Add product to cart failed", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
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
            productFavorite = itemView.findViewById(R.id.heartIcon);
            addToCartIcon = itemView.findViewById(R.id.addToCartIcon);
        }
    }
}
