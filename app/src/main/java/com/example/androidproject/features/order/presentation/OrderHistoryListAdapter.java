package com.example.androidproject.features.order.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.order.data.OrderModel;

import java.util.List;

public class OrderHistoryListAdapter extends RecyclerView.Adapter<OrderHistoryListAdapter.OrderHistoryListViewHolder> {
    private Context context;
    private List<OrderModel> orderList;

    public OrderHistoryListAdapter(Context context, List<OrderModel> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderHistoryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_history, parent, false);
        return new OrderHistoryListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryListViewHolder holder, int position) {
        OrderModel order = orderList.get(position);
        holder.tvOrderId.setText(order.getId());

        String orderStatus = order.getStatus();
        if (orderStatus.equals("pending")) {
            holder.tvOrderStatus.setText("Đang xử lỹ");
            holder.tvOrderStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.pending_order_status));
        } else if (orderStatus.equals("on_delivery")) {
            holder.tvOrderStatus.setText("Đang giao");
            holder.tvOrderStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.delivery_order_status));
        } else if (orderStatus.equals("completed")) {
            holder.tvOrderStatus.setText("Thành công");
            holder.tvOrderStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.succes_order_status));
        } else if (orderStatus.equals("rejected")) {
            holder.tvOrderStatus.setText("Thất bại");
            holder.tvOrderStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.reject_order_status));
        }

    }

    @Override
    public int getItemCount() {
        if (orderList != null) {
            return orderList.size();
        }
        return 0;
    }

    public static class OrderHistoryListViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOrderId, tvOrderStatus, tvOrderDateExport;

        public OrderHistoryListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvOrderId = itemView.findViewById(R.id.textView_order_id);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvOrderDateExport = itemView.findViewById(R.id.textView_date_export);
        }
    }
}
