package com.example.androidproject.features.wishlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder> {
    private List<Wishlist> wishlistItems;  // Danh sách các mục trong danh sách yêu thích
    private OnAddToCartListener addToCartListener;  // Listener cho sự kiện thêm vào giỏ hàng

    // Constructor
    public WishlistAdapter(List<Wishlist> wishlistItems, OnAddToCartListener addToCartListener) {
        this.wishlistItems = wishlistItems;
        this.addToCartListener = addToCartListener;
    }

    @NonNull
    @Override
    public WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho mỗi mục trong RecyclerView
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
        return new WishlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistViewHolder holder, int position) {
        // Gán dữ liệu cho ViewHolder
        Wishlist item = wishlistItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        // Trả về số lượng mục trong danh sách yêu thích
        return wishlistItems.size();
    }

    // ViewHolder cho mỗi mục trong RecyclerView
    class WishlistViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivProduct; // Hình ảnh sản phẩm
        private TextView tvName;      // Tên sản phẩm
        private TextView tvBrand;     // Thương hiệu sản phẩm
        private TextView tvPrice;     // Giá sản phẩm
        private TextView tvSaleTag;   // Nhãn giảm giá
        private ImageView ibAddToCart; // Nút "Thêm vào giỏ hàng"
        private ImageView ibWishlistAdd; // Nút "Thêm vào danh sách yêu thích"

        public WishlistViewHolder(@NonNull View itemView) {
            super(itemView);
            // Khởi tạo các view
            ivProduct = itemView.findViewById(R.id.iv_Product);
            tvName = itemView.findViewById(R.id.tv_Name);
            tvBrand = itemView.findViewById(R.id.tv_Brand);
            tvPrice = itemView.findViewById(R.id.tv_Price);
            tvSaleTag = itemView.findViewById(R.id.tv_SaleTag);
            ibAddToCart = itemView.findViewById(R.id.iv_AddToCart);
            ibWishlistAdd = itemView.findViewById(R.id.iv_WishlistAdd);
        }

        public void bind(Wishlist item) {
            // Gán dữ liệu từ Wishlist vào các view
            ivProduct.setImageResource(item.getProductImage());
            tvName.setText(item.getProductName());
            tvBrand.setText(item.getProductBrand());
            tvPrice.setText(item.getProductPrice());
            tvSaleTag.setText(item.getSaleTag());

            // Sự kiện khi nhấn nút "Thêm vào giỏ hàng"
            ibAddToCart.setOnClickListener(v -> addToCartListener.onAddToCart(item));

            // Sự kiện khi nhấn nút "Thêm vào danh sách yêu thích"
            ibWishlistAdd.setOnClickListener(v -> {
                // Logic để thêm sản phẩm vào danh sách yêu thích
                // Bạn có thể thêm logic ở đây nếu cần
            });
        }
    }

    // Interface để xử lý sự kiện thêm vào giỏ hàng
    public interface OnAddToCartListener {
        void onAddToCart(Wishlist item);
    }
}