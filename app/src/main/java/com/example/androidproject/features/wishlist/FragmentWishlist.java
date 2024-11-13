package com.example.androidproject.features.wishlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidproject.R;
import java.util.ArrayList;
import java.util.List;

public class FragmentWishlist extends Fragment {

    private RecyclerView recyclerView;
    private WishlistAdapter wishlistAdapter;
    private List<Wishlist> wishlistItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout cho Fragment
        View view = inflater.inflate(R.layout.wishlist, container, false);

        // Khởi tạo RecyclerView
        recyclerView = view.findViewById(R.id.rc_WishList);

        // Sử dụng GridLayoutManager với 2 cột
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setAdapter(wishlistAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);

        // Tạo danh sách sản phẩm yêu thích
        wishlistItems = new ArrayList<>();
        // Thêm sản phẩm vào danh sách
        wishlistItems.add(new Wishlist(R.drawable.product_img, "Lenovo Legion 5", "Lenovo", "$1299", R.drawable.twitter_verified_badge_1, "1%", R.drawable.group_34012, R.drawable.heart_selector));
        wishlistItems.add(new Wishlist(R.drawable.product_img, "Lenovo Legion 5", "Lenovo", "$1299", R.drawable.twitter_verified_badge_1, "2%", R.drawable.group_34012, R.drawable.heart_selector));
        wishlistItems.add(new Wishlist(R.drawable.product_img, "Lenovo Legion 5", "Lenovo", "$1299", R.drawable.twitter_verified_badge_1, "3%", R.drawable.group_34012, R.drawable.heart_selector));
        wishlistItems.add(new Wishlist(R.drawable.product_img, "Lenovo Legion 5", "Lenovo", "$1299", R.drawable.twitter_verified_badge_1, "4%", R.drawable.group_34012, R.drawable.heart_selector));
        wishlistItems.add(new Wishlist(R.drawable.product_img, "Lenovo Legion 5", "Lenovo", "$1299", R.drawable.twitter_verified_badge_1, "5%", R.drawable.group_34012, R.drawable.heart_selector));
        wishlistItems.add(new Wishlist(R.drawable.product_img, "Lenovo Legion 5", "Lenovo", "$1299", R.drawable.twitter_verified_badge_1, "6%", R.drawable.group_34012, R.drawable.heart_selector));
        wishlistItems.add(new Wishlist(R.drawable.product_img, "Lenovo Legion 5", "Lenovo", "$1299", R.drawable.twitter_verified_badge_1, "7%", R.drawable.group_34012, R.drawable.heart_selector));
        wishlistItems.add(new Wishlist(R.drawable.product_img, "Lenovo Legion 5", "Lenovo", "$1299", R.drawable.twitter_verified_badge_1, "8%", R.drawable.group_34012, R.drawable.heart_selector));
        wishlistItems.add(new Wishlist(R.drawable.product_img, "Lenovo Legion 5", "Lenovo", "$1299", R.drawable.twitter_verified_badge_1, "9%", R.drawable.group_34012, R.drawable.heart_selector));
        // Thêm nhiều sản phẩm nếu cần

        // Khởi tạo và gán adapter cho RecyclerView
        wishlistAdapter = new WishlistAdapter(wishlistItems, this::onAddToCart);
        recyclerView.setAdapter(wishlistAdapter);

        return view; // Trả về view đã inflate
    }

    // Xử lý sự kiện thêm vào giỏ hàng
    private void onAddToCart(Wishlist item) {
        // Logic để thêm sản phẩm vào giỏ hàng
        // Ví dụ: Toast hoặc cập nhật dữ liệu
    }
}