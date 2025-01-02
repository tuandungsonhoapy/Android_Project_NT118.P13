package com.example.androidproject.features.admin_manager.presentation.widgets;

import static android.app.Activity.RESULT_OK;
import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

public class ListOrderItemAdminAdapter extends RecyclerView.Adapter<ListOrderItemAdminAdapter.ListOrderItemAdminViewHolder> {
    private List<CheckoutModel> orderList;
    private Context context;
    private CheckoutModel checkoutModel;
    private ActivityResultLauncher<Intent> detailOrderLauncher;

    public ListOrderItemAdminAdapter(List<CheckoutModel> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    public void setOrderList(List<CheckoutModel> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListOrderItemAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_order_admin, parent, false);
        return new ListOrderItemAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListOrderItemAdminViewHolder holder, int position) {
        CheckoutModel order = orderList.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String formattedDate = dateFormat.format(order.getCreatedAt().toDate());

        holder.tvOrderTotalProduct.setText(String.valueOf(calculateTotalQuantity(order.getProducts())));

        holder.tvOrderId.setText(order.getId());
        holder.tvOrderDate.setText(formattedDate);
        holder.tvOrderTotalPrice.setText(ConvertFormat.formatPriceToVND(order.getTotalPrice()));
        holder.tvOrderPaymentMethod.setText("COD");
        holder.tvOrderCustomerName.setText(order.getUser().getFirstName());
        holder.tvOrderCustomerID.setText(order.getId());

        String orderStatus = order.getStatus();
        if (orderStatus.equals("PENDING")) {
            holder.tvOrderStatus.setText("Đang xử lý");
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

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailOrderAdminActivity.class);
            intent.putExtra("order_id", order.getId());
            intent.putExtra("order_date", formattedDate);
            intent.putExtra("order_total_price", ConvertFormat.formatPriceToVND(order.getTotalPrice()));
            intent.putExtra("order_payment_method", "COD");
            intent.putExtra("order_customer_name", order.getUser().getFirstName());
            intent.putExtra("order_customer_id", order.getId());
            intent.putExtra("order_status", order.getStatus());
            intent.putExtra("order_customer_phone", order.getUser().getPhone());
            intent.putExtra("order_customer_email", order.getUser().getEmail());

//            ProductDataForOrderModel[] productDataForOrder = order.getProducts();
//            intent.putExtra("product_data_for_order", productDataForOrder);
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
        TextView tvOrderCustomerID, tvOrderTotalProduct;

        public ListOrderItemAdminViewHolder(@NonNull View itemView) {
            super(itemView);

            tvOrderId = itemView.findViewById(R.id.tvOrderID);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvOrderTotalPrice = itemView.findViewById(R.id.tvOrderTotalPrice);
            tvOrderPaymentMethod = itemView.findViewById(R.id.tvOrderPaymentMethod);
            tvOrderCustomerName = itemView.findViewById(R.id.tvOrderCustomerName);
            tvOrderCustomerID = itemView.findViewById(R.id.tvOrderCustomerID);
            tvOrderTotalProduct = itemView.findViewById(R.id.tvOrderTotalProduct);
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
