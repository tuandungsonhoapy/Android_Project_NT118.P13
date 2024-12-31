package com.example.androidproject.features.voucher.presentation;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.auth.usecase.UserUseCase;
import com.example.androidproject.features.voucher.data.model.VoucherModel;

import java.util.List;

public class ListVoucherAdapter extends RecyclerView.Adapter<ListVoucherAdapter.ListVoucherViewHolder> {
    private List<VoucherModel> allVouchers;
    private final Context ctx;
    private UserUseCase userUseCase;
    public ListVoucherAdapter(List<VoucherModel> vouchers, Context ctx) {
        this.allVouchers = vouchers;
        this.ctx = ctx;
        userUseCase = new UserUseCase(ctx);
    }

    @NonNull
    @Override
    public ListVoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_voucher, parent, false);
        return new ListVoucherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListVoucherViewHolder holder, int position) {
        VoucherModel voucher = allVouchers.get(position);
        holder.couponName.setText(voucher.getName());
        holder.couponId.setText("#" + voucher.getId());
        holder.couponDate.setText(voucher.getCreatedAt().toDate() + " -> " + " ___");
        holder.couponType.setText(voucher.getType() == "percent" ? "Giảm phần trăm" : "Giảm tiền");
        if(voucher.getType().equals("percent")) {
            holder.couponValue.setText("Giảm " + voucher.getValue() + "%");
        } else {
            holder.couponValue.setText("Giảm " + voucher.getValue() + "đ");
        }

        holder.delVoucher.setOnClickListener(v -> {
            userUseCase.deleteUserVoucher(voucher.getId())
                    .thenAccept(r -> {
                        if(r.isRight()) {
                            allVouchers.remove(position);
                            notifyDataSetChanged();
                            Log.d("Voucher", "Xóa voucher thành công");
                        } else {
                            Log.d("Voucher", "Xóa voucher thất bại");
                        }
                    });
        });
    }

    @Override
    public int getItemCount() {
        return allVouchers.size();
    }

    public static class ListVoucherViewHolder extends RecyclerView.ViewHolder {
        private TextView couponName;
        private TextView couponId;
        private TextView couponDate;
        private TextView couponType;
        private TextView couponValue;
        private Button delVoucher;
        public ListVoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            couponName = itemView.findViewById(R.id.admin_coupon_card_name);
            couponId = itemView.findViewById(R.id.admin_coupon_card_id);
            couponDate = itemView.findViewById(R.id.admin_coupon_card_date);
            couponType = itemView.findViewById(R.id.admin_coupon_card_type);
            couponValue = itemView.findViewById(R.id.admin_coupon_card_value);
            delVoucher = itemView.findViewById(R.id.delVoucher);
        }
    }
}
