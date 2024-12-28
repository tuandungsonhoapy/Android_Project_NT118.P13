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

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidproject.R;
import com.example.androidproject.core.utils.MoneyFomat;
import com.example.androidproject.core.utils.counter.CounterModel;
import com.example.androidproject.features.cart.usecase.CartUseCase;
import com.example.androidproject.features.product.data.entity.ProductOption;
import com.example.androidproject.features.product.data.model.ProductModel;
import com.example.androidproject.features.product.data.model.ProductModelFB;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;

public class AllProductListAdapter extends RecyclerView.Adapter<AllProductListAdapter.AllProductListAdapterViewHolder> {
    private List<ProductModelFB> productList;
    private Context context;
    private CartUseCase cartUseCase = new CartUseCase();
    private long cartQuantity;
    private CounterModel counterModel = new CounterModel();
    
    public AllProductListAdapter(List<ProductModelFB> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public AllProductListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_all_product, parent, false);
        return new AllProductListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllProductListAdapterViewHolder holder, int position) {
        ProductModelFB product = productList.get(position);

        Glide.with(context)
                .load(product.getImages().get(0))
                .override(300, 300)
                .centerCrop()
                .into(holder.productImage);

        holder.productName.setText(product.getName());
        holder.productBrand.setText(product.getBrandId());
        holder.productPrice.setText(MoneyFomat.format(product.getPrice()));
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
                        Toast.makeText(context, "Thêm sản phẩm vào giỏ thành công", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context, "Thêm sản phẩm vào giỏ thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        });
        holder.heartIcon.setOnClickListener(v -> {

        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class AllProductListAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productBrand;
        TextView productPrice;
        ImageView addToCartIcon;
        ImageView heartIcon;

        public AllProductListAdapterViewHolder(View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productBrand = itemView.findViewById(R.id.productBrand);
            productPrice = itemView.findViewById(R.id.productPrice);
            addToCartIcon = itemView.findViewById(R.id.addToCartIcon);
            heartIcon = itemView.findViewById(R.id.heartIcon);
        }
    }
}
