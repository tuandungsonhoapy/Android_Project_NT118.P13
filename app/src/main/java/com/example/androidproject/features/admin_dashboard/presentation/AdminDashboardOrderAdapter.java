package com.example.androidproject.features.admin_dashboard.presentation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.admin_manager.presentation.order.DetailOrderAdminActivity;
import com.example.androidproject.features.order.data.OrderModel;
import com.example.androidproject.features.order.data.ProductDataForOrderModel;

import java.util.List;

public class AdminDashboardOrderAdapter extends RecyclerView.Adapter<AdminDashboardOrderAdapter.AdminDashboardOrderViewHolder> {
    private List<OrderModel> orderList;
    private Context context;

    public AdminDashboardOrderAdapter(List<OrderModel> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdminDashboardOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_dashboard_order, parent, false);
        return new AdminDashboardOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminDashboardOrderViewHolder holder, int position) {
        OrderModel order = orderList.get(position);
        holder.tvOrderID.setText(order.getId());
        holder.tvOrderDate.setText(order.getCreated_at());
        holder.tvOrderProductCount.setText(String.valueOf(order.getProductsCount()));
        holder.tvOrderTotalPrice.setText(toString().valueOf(order.getTotal_price()));
        holder.tvPaymentMethod.setText(order.getPayment_method());

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

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailOrderAdminActivity.class);
            intent.putExtra("order_id", order.getId());
            intent.putExtra("order_date", order.getCreated_at());
            intent.putExtra("order_total_price", order.getTotal_price());
            intent.putExtra("order_payment_method", order.getPayment_method());
            intent.putExtra("order_customer_name", "Triệu Lê");
            intent.putExtra("order_customer_id", order.getUser_id());
            intent.putExtra("order_status", order.getStatus());
            ProductDataForOrderModel[] productDataForOrder = order.getProducts();
            intent.putExtra("product_data_for_order", productDataForOrder);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class AdminDashboardOrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderID;
        TextView tvOrderDate;
        TextView tvOrderProductCount;
        TextView tvOrderTotalPrice;
        TextView tvPaymentMethod;
        TextView tvOrderStatus;

        public AdminDashboardOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderID = itemView.findViewById(R.id.tvOrderID);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvOrderProductCount = itemView.findViewById(R.id.tvOrderProductCount);
            tvOrderTotalPrice = itemView.findViewById(R.id.tvOrderTotalPrice);
            tvPaymentMethod = itemView.findViewById(R.id.tvPaymentMethod);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
        }
    }
}
