package com.example.androidproject.features.admin_manager.presentation.widgets;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.admin_manager.data.model.CouponModel;
import com.example.androidproject.features.admin_manager.presentation.pages.AdminCouponDetail;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.CouponViewHolder> {
    private List<CouponModel> allCoupons;
    private List<CouponModel> couponList;
    private Context context;

    public CouponAdapter(List<CouponModel> coupons, Context context) {
        Log.d("CouponAdapter", "Initializing with " + coupons.size() + " items.");
        for (CouponModel coupon : coupons) {
            Log.d("DummyCouponData", "Coupon details: " + coupon.toString());
        }
        this.allCoupons = new ArrayList<>(coupons);
        this.couponList = new ArrayList<>(coupons);
        this.context = context;
    }

    @NonNull
    @Override
    public CouponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item__admin_coupon_card, parent, false);
        return new CouponViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponViewHolder holder, int position) {
        CouponModel coupon = couponList.get(position);
        holder.bind(coupon);

        // Set an onClickListener here
//        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(context, AdminCouponDetail.class);
//            intent.putExtra("coupon", coupon);
//            context.startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        return couponList.size();
    }

    public void filterAll() {
        couponList.clear();
        couponList.addAll(allCoupons);
        notifyDataSetChanged();
    }

    // Filter methods can be defined further
    public void filterActive() {
    }

    public void filterUpcoming() {
    }

    public void filterExpired() {
    }

    public static class CouponViewHolder extends RecyclerView.ViewHolder {
        private TextView couponName;
        private TextView couponId;
        private TextView couponDate;
        private TextView couponQuantity;
        private TextView couponType;

        public CouponViewHolder(@NonNull View itemView) {
            super(itemView);
            couponName = itemView.findViewById(R.id.admin_coupon_card_name);
            couponId = itemView.findViewById(R.id.admin_coupon_card_id);
            couponDate = itemView.findViewById(R.id.admin_coupon_card_date);
            couponQuantity = itemView.findViewById(R.id.admin_coupon_card_quantity);
            couponType = itemView.findViewById(R.id.admin_coupon_card_type);
        }

        public void bind(CouponModel coupon) {
            couponName.setText(coupon.getName());
            couponId.setText("#" + coupon.getCouponId());
            couponDate.setText(coupon.getDateStart() + " -> " + coupon.getDateEnd());
            couponQuantity.setText(String.valueOf(coupon.getQuantity()));

            NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

            String typeText;
            if ("percentage".equalsIgnoreCase(coupon.getType())) {
                typeText = String.format("Giảm %s%%", coupon.getValue());
            } else if ("fixed".equalsIgnoreCase(coupon.getType())) {
                String valueFormatted = numberFormat.format(coupon.getValue() * 1000);
                String minimalTotalFormatted = numberFormat.format(coupon.getMinimalTotal() * 1000);
                typeText = String.format("Giảm %s đ cho đơn trên %s đ", valueFormatted, minimalTotalFormatted);
            } else {
                typeText = "Không xác định";
            }
            couponType.setText(typeText);
        }
    }
}
