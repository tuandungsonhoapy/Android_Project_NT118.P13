package com.example.androidproject.features.admin_manager.presentation.widgets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.core.utils.JsonUtil;
import com.example.androidproject.features.admin_manager.data.model.CouponModel;
import com.example.androidproject.features.admin_manager.presentation.coupon.AdminCouponDetail;
import com.example.androidproject.features.voucher.data.model.VoucherModel;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.CouponViewHolder> {
    private List<VoucherModel> allCoupons;
    private List<VoucherModel> couponList;
    private Context context;
    private Activity activity;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public CouponAdapter(List<VoucherModel> coupons, Context context, Activity activity) {
        this.allCoupons = new ArrayList<>(coupons);
        this.couponList = new ArrayList<>(coupons);
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CouponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item__admin_coupon_card, parent, false);
        return new CouponViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponViewHolder holder, int position) {
        VoucherModel coupon = couponList.get(position);

        holder.couponName.setText(coupon.getName());
        holder.couponId.setText("#" + coupon.getId());
        holder.couponDate.setText(dateFormat.format(coupon.getCreatedAt().toDate()) + " -> " + " ___");
        holder.couponType.setText(coupon.getType() == "percent" ? "Giảm phần trăm" : "Giảm tiền");
        if(coupon.getType().equals("percent")) {
            holder.couponValue.setText("Giảm " + coupon.getValue() + "%");
        } else {
            holder.couponValue.setText("Giảm " + coupon.getValue() + "đ");
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AdminCouponDetail.class);
            intent.putExtra("voucherId", coupon.getId());
            activity.startActivityForResult(intent, 1);
        });
    }

    @Override
    public int getItemCount() {
        return couponList.size();
    }

    public void updateList() {
        couponList.clear();
        couponList.addAll(allCoupons);
        notifyDataSetChanged();
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
        private TextView couponType;
        private TextView couponValue;
        public CouponViewHolder(@NonNull View itemView) {
            super(itemView);
            couponName = itemView.findViewById(R.id.admin_coupon_card_name);
            couponId = itemView.findViewById(R.id.admin_coupon_card_id);
            couponDate = itemView.findViewById(R.id.admin_coupon_card_date);
            couponType = itemView.findViewById(R.id.admin_coupon_card_type);
            couponValue = itemView.findViewById(R.id.admin_coupon_card_value);
        }

//        public void bind(VoucherModel coupon) {
//            couponName.setText(coupon.getName());
//            couponId.setText("#" + coupon.getCouponId());
//            couponDate.setText(coupon.getDateStart() + " -> " + coupon.getDateEnd());
//            couponQuantity.setText(String.valueOf(coupon.getQuantity()));
//
//            NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
//
//            String typeText;
//            if ("percentage".equalsIgnoreCase(coupon.getType())) {
//                typeText = String.format("-%s%%", coupon.getValue());
//            } else if ("fixed".equalsIgnoreCase(coupon.getType())) {
//                String valueFormatted = numberFormat.format(coupon.getValue() * 1000);
//                String minimalTotalFormatted = numberFormat.format(coupon.getMinimalTotal() * 1000);
//                typeText = String.format("-%sđ đơn hơn %sđ", valueFormatted, minimalTotalFormatted);
//            } else {
//                typeText = "Không xác định";
//            }
//            couponType.setText(typeText);
//        }
    }
}
