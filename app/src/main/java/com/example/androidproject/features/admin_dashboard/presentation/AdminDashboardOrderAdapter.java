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
import com.example.androidproject.core.utils.ConvertFormat;
import com.example.androidproject.features.admin_manager.presentation.order.DetailOrderAdminActivity;
import com.example.androidproject.features.cart.data.entity.ProductsOnCart;
import com.example.androidproject.features.checkout.data.model.CheckoutModel;
import com.example.androidproject.features.order.data.OrderModel;
import com.example.androidproject.features.order.data.ProductDataForOrderModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AdminDashboardOrderAdapter extends RecyclerView.Adapter<AdminDashboardOrderAdapter.AdminDashboardOrderViewHolder> {
    private List<CheckoutModel> orderList;
    private Context context;

    public AdminDashboardOrderAdapter(List<CheckoutModel> orderList, Context context) {
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
        CheckoutModel order = orderList.get(position);
        holder.tvOrderID.setText(order.getId());

        // Định dạng ngày-tháng-năm giờ:phút:giây
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String formattedDate = dateFormat.format(order.getCreatedAt().toDate());
        holder.tvOrderDate.setText(formattedDate);
        holder.tvOrderProductCount.setText(String.valueOf(calculateTotalQuantity(order.getProducts())));
        holder.tvOrderTotalPrice.setText(ConvertFormat.formatPriceToVND(order.getTotalPrice()));
//        holder.tvPaymentMethod.setText(order.getPayment_method());

        String orderStatus = order.getStatus();
        if (orderStatus.equals("PENDING")) {
            holder.tvOrderStatus.setText("Đang xử lỹ");
            holder.tvOrderStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.pending_order_status));
        } else if (orderStatus.equals("INTRANSIT")) {
            holder.tvOrderStatus.setText("Đang giao");
            holder.tvOrderStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.delivery_order_status));
        } else if (orderStatus.equals("SUCCESS")) {
            holder.tvOrderStatus.setText("Thành công");
            holder.tvOrderStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.succes_order_status));
        } else if (orderStatus.equals("FAILED")) {
            holder.tvOrderStatus.setText("Thất bại");
            holder.tvOrderStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.reject_order_status));
        }

//        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(context, DetailOrderAdminActivity.class);
//            intent.putExtra("order_id", order.getId());
//            intent.putExtra("order_date", order.getCreated_at());
//            intent.putExtra("order_total_price", order.getTotal_price());
//            intent.putExtra("order_payment_method", order.getPayment_method());
//            intent.putExtra("order_customer_name", "Triệu Lê");
//            intent.putExtra("order_customer_id", order.getUser_id());
//            intent.putExtra("order_status", order.getStatus());
//            ProductDataForOrderModel[] productDataForOrder = order.getProducts();
//            intent.putExtra("product_data_for_order", productDataForOrder);
//            context.startActivity(intent);
//        });
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

    public int calculateTotalQuantity(List<ProductsOnCart> products) {
        int totalQuantity = 0;
        for (ProductsOnCart product : products) {
            totalQuantity += product.getQuantity();
        }
        return totalQuantity;
    }
}
