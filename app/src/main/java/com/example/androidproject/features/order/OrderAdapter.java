package com.example.androidproject.features.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidproject.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private final Context context;
    private final List<Card> orderList;

    public OrderAdapter(Context context, List<Card> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orderhistory_sub, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Card order = orderList.get(position);

        // Thiết lập các thông tin cho holder từ đối tượng Card
        holder.tvID.setText(order.getId());
        holder.tvLocation.setText(order.getLocation());
        holder.tvNgayNhan.setText("Ngày nhận (dự kiến): " + order.getNgayNhan());
        holder.tvReciveTime.setText(order.getReceiveTime());

        // Lấy ChipGroup từ layout
        ChipGroup chipGroup = holder.chipGroupStatus;
        //test


        // Ẩn tất cả Chip trước
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            chip.setVisibility(View.GONE);
        }

        // Gán trạng thái cho Chip tương ứng
        // Hiển thị Chip tương ứng với trạng thái của đơn hàng
        switch (order.getChip()) {
            case 0:
                holder.chipThanhCong.setVisibility(View.VISIBLE);
                break;
            case 1:
                holder.chipDaHuy.setVisibility(View.VISIBLE);
                break;
            case 2:
                holder.chipDangGiao.setVisibility(View.VISIBLE);
                break;
            case 3:
                holder.chipDangXuLy.setVisibility(View.VISIBLE);
                break;
        }

        // Thiết lập icon
        holder.ivLine.setImageResource(order.getLine());
        holder.ivIcon.setImageResource(order.getResourcesIconLocation());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        ImageView ivLine, ivIcon;
        TextView tvID, tvLocation, tvNgayNhan, tvReciveTime, tvNgayXuatKho;
        ChipGroup chipGroupStatus;
        com.google.android.material.chip.Chip chipThanhCong, chipDaHuy, chipDangGiao, chipDangXuLy;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            ivLine = itemView.findViewById(R.id.iv_Line);
            ivIcon = itemView.findViewById(R.id.iv_Icon);
            tvID = itemView.findViewById(R.id.tv_ID);
            tvLocation = itemView.findViewById(R.id.tv_Location);
            tvNgayNhan = itemView.findViewById(R.id.tv_NgayNhan);
            tvReciveTime = itemView.findViewById(R.id.tv_ReciveTime);
            chipGroupStatus = itemView.findViewById(R.id.chipGroupStatus);
            chipDangGiao = itemView.findViewById(R.id.chipDangGiao);
            chipDangXuLy = itemView.findViewById(R.id.chipDangXuLy);
            chipDaHuy = itemView.findViewById(R.id.chipDaHuy);
            chipThanhCong = itemView.findViewById(R.id.chipThanhCong);
        }
    }
}
