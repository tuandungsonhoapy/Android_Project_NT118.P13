package com.example.androidproject.features.admin_manager.presentation.widgets;

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

public class ListOrderItemAdminAdapter extends RecyclerView.Adapter<ListOrderItemAdminAdapter.ListOrderItemAdminViewHolder> {
    private List<OrderModel> orderList;
    private Context context;

    public ListOrderItemAdminAdapter(List<OrderModel> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public ListOrderItemAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_order_admin, parent, false);
        return new ListOrderItemAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListOrderItemAdminViewHolder holder, int position) {
        OrderModel order = orderList.get(position);
        holder.tvOrderId.setText(order.getId());
        holder.tvOrderDate.setText(order.getCreated_at());
        holder.tvOrderTotalPrice.setText(toString().valueOf(order.getTotal_price()));
        holder.tvOrderPaymentMethod.setText(order.getPayment_method());
        holder.tvOrderCustomerName.setText("Triệu Lê");
        holder.tvOrderCustomerID.setText(order.getUser_id());

        String orderStatus = order.getStatus();
        if (orderStatus.equals("pending")) {
            holder.tvOrderStatus.setText("Đang xử lý");
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

    public static class ListOrderItemAdminViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId;
        TextView tvOrderDate;
        TextView tvOrderStatus;
        TextView tvOrderTotalPrice;
        TextView tvOrderPaymentMethod;
        TextView tvOrderCustomerName;
        TextView tvOrderCustomerID;

        public ListOrderItemAdminViewHolder(@NonNull View itemView) {
            super(itemView);

            tvOrderId = itemView.findViewById(R.id.tvOrderID);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvOrderTotalPrice = itemView.findViewById(R.id.tvOrderTotalPrice);
            tvOrderPaymentMethod = itemView.findViewById(R.id.tvOrderPaymentMethod);
            tvOrderCustomerName = itemView.findViewById(R.id.tvOrderCustomerName);
            tvOrderCustomerID = itemView.findViewById(R.id.tvOrderCustomerID);
        }
    }
}
