package com.example.androidproject.features.order.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.checkout.data.model.CheckoutModel;
import com.example.androidproject.features.checkout.usecase.CheckoutUseCase;
import com.example.androidproject.features.order.data.OrderModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OrderHistoryListAdapter extends RecyclerView.Adapter<OrderHistoryListAdapter.OrderHistoryListViewHolder> {
    private Context context;
    private List<CheckoutModel> orderList;
    private CheckoutUseCase checkoutUseCase = new CheckoutUseCase();

    public OrderHistoryListAdapter(Context context, List<CheckoutModel> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    public void setOrderList(List<CheckoutModel> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderHistoryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_history, parent, false);
        return new OrderHistoryListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryListViewHolder holder, int position) {
        CheckoutModel order = orderList.get(position);
        holder.tvOrderId.setText(order.getId());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(order.getCreatedAt().toDate());

        holder.tvOrderDateExport.setText(formattedDate);
        holder.textView_date_receive.setText(formattedDate);
        holder.tvAddress.setText(order.getFullAddress());
        holder.button6_confirm.setVisibility(View.GONE);
        holder.button7_cancel.setVisibility(View.GONE);
        holder.button8_buy_again.setVisibility(View.GONE);

        String orderStatus = order.getStatus();
        if (orderStatus.equals("PENDING")) {
            holder.tvOrderStatus.setText("Đang xử lỹ");
            holder.tvOrderStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.pending_order_status));
            holder.button7_cancel.setVisibility(View.VISIBLE);
        } else if (orderStatus.equals("INTRANSIT")) {
            holder.tvOrderStatus.setText("Đang giao");
            holder.tvOrderStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.delivery_order_status));
            holder.button6_confirm.setVisibility(View.VISIBLE);
            holder.button7_cancel.setVisibility(View.VISIBLE);
        } else if (orderStatus.equals("SUCCESS")) {
            holder.tvOrderStatus.setText("Thành công");
            holder.tvOrderStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.succes_order_status));
        } else if (orderStatus.equals("FAILED")) {
            holder.tvOrderStatus.setText("Thất bại");
            holder.tvOrderStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.reject_order_status));
            holder.button8_buy_again.setVisibility(View.VISIBLE);
        }

        holder.button6_confirm.setOnClickListener(v -> {
            checkoutUseCase.updateStatus(order.getId(), "SUCCESS")
                    .thenAccept(r -> {
                        if (r.isRight()) {
                            order.setStatus("SUCCESS");
                            holder.tvOrderStatus.setText("Thành công");
                            holder.tvOrderStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.succes_order_status));
                            holder.button6_confirm.setVisibility(View.GONE);
                            holder.button7_cancel.setVisibility(View.GONE);
                            holder.button8_buy_again.setVisibility(View.GONE);
                        }
                    })
                    .exceptionally(e -> {
                        return null;
                    });
        });

        holder.button7_cancel.setOnClickListener(v -> {
            checkoutUseCase.updateStatus(order.getId(), "FAILED")
                    .thenAccept(r -> {
                        if (r.isRight()) {
                            order.setStatus("FAILED");
                            holder.tvOrderStatus.setText("Thất bại");
                            holder.tvOrderStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.reject_order_status));
                            holder.button6_confirm.setVisibility(View.GONE);
                            holder.button7_cancel.setVisibility(View.GONE);
                            holder.button8_buy_again.setVisibility(View.VISIBLE);
                        }
                    })
                    .exceptionally(e -> {
                        return null;
                    });
        });

        holder.button8_buy_again.setOnClickListener(v -> {
            checkoutUseCase.updateStatus(order.getId(), "PENDING")
                    .thenAccept(r -> {
                        if (r.isRight()) {
                            order.setStatus("PENDING");
                            holder.tvOrderStatus.setText("Đang xử lý");
                            holder.tvOrderStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.pending_order_status));
                            holder.button7_cancel.setVisibility(View.VISIBLE);
                            holder.button8_buy_again.setVisibility(View.GONE);
                        }
                    })
                    .exceptionally(e -> {
                        return null;
                    });
        });
    }

    @Override
    public int getItemCount() {
        if (orderList != null) {
            return orderList.size();
        }
        return 0;
    }

    public static class OrderHistoryListViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOrderId, tvOrderStatus, tvOrderDateExport, textView_date_receive, tvAddress;
        private Button button6_confirm, button7_cancel, button8_buy_again;

        public OrderHistoryListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvOrderId = itemView.findViewById(R.id.textView_order_id);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvOrderDateExport = itemView.findViewById(R.id.textView_date_export);
            textView_date_receive = itemView.findViewById(R.id.textView_date_receive);
            tvAddress = itemView.findViewById(R.id.textView20_address);
            button6_confirm = itemView.findViewById(R.id.button6_confirm);
            button7_cancel = itemView.findViewById(R.id.button7_cancel);
            button8_buy_again = itemView.findViewById(R.id.button8_buy_again);
        }
    }
}
