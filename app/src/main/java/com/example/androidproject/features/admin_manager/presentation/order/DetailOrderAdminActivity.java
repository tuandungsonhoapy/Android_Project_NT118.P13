package com.example.androidproject.features.admin_manager.presentation.order;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.admin_manager.presentation.widgets.ProductForDetailOrderAdminAdapter;
import com.example.androidproject.features.order.data.ProductDataForOrderModel;

import java.util.ArrayList;
import java.util.List;

public class DetailOrderAdminActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_order_admin);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupButtons();

        TextView orderId = findViewById(R.id.tvOrderID);
        TextView orderDate = findViewById(R.id.tvOrderDate);
        TextView orderTotalPrice = findViewById(R.id.tvOrderTotalPrice);
        TextView orderCustomerName = findViewById(R.id.tvCustomerName);
        TextView orderCustomerID = findViewById(R.id.tvCustomerID);
        TextView orderStatus = findViewById(R.id.tvOrderStatus);
        TextView orderCustomerPhone = findViewById(R.id.tvCustomerPhone);
        TextView orderCustomerEmail = findViewById(R.id.tvCustomerEmail);
        RecyclerView orderDetailRecyclerView = findViewById(R.id.recycler_products_order_view);


        String status = getIntent().getStringExtra("order_status");
        if (status.equals("pending")) {
            orderStatus.setText("Đang xử lý");
            orderStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.pending_order_status));
        } else if (status.equals("on_delivery")) {
            orderStatus.setText("Đang giao");
            orderStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.delivery_order_status));
        } else if (status.equals("completed")) {
            orderStatus.setText("Thành công");
            orderStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.succes_order_status));
        } else if (status.equals("rejected")) {
            orderStatus.setText("Thất bại");
            orderStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.reject_order_status));
        }

        // Retrieve data from Intent
        orderId.setText(getIntent().getStringExtra("order_id"));
        orderDate.setText(getIntent().getStringExtra("order_date"));
        orderTotalPrice.setText(String.valueOf(getIntent().getDoubleExtra("order_total_price", 0.0)));
        orderCustomerName.setText(getIntent().getStringExtra("order_customer_name"));
        orderCustomerID.setText(getIntent().getStringExtra("order_customer_id"));
        orderCustomerPhone.setText("123142352345");
        orderCustomerEmail.setText("cc@gmail.com");
        Parcelable[] parcelableArray = getIntent().getParcelableArrayExtra("product_data_for_order");

        List<ProductDataForOrderModel> orderProductDataList = new ArrayList<>();
        if (parcelableArray != null) {
            for (Parcelable parcelable : parcelableArray) {
                if (parcelable instanceof ProductDataForOrderModel) {
                    orderProductDataList.add((ProductDataForOrderModel) parcelable);
                }
            }
        } else {
            Log.d("DetailOrderAdminActivity", "Product data is null");
        }

        ProductForDetailOrderAdminAdapter adapter = new ProductForDetailOrderAdminAdapter(orderProductDataList, this);
        orderDetailRecyclerView.setAdapter(adapter);
        orderDetailRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderStatus.setOnClickListener(v -> {
            String[] statusOptions = {"Đang xử lý", "Đang giao", "Thành công", "Thất bại"};
            int currentIndex = -1;

            String currentStatus = orderStatus.getText().toString();
            for(int i = 0; i < statusOptions.length; i++) {
                if (statusOptions[i].equals(currentStatus)) {
                    currentIndex = i;
                    break;
                }
            }

            final int[] selectedIndex = {currentIndex};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Chọn trạng thái đơn hàng")
                    .setSingleChoiceItems(statusOptions, currentIndex, (dialog, which) -> {
                        selectedIndex[0] = which;
                    })
                    .setPositiveButton("OK", (dialog, which) -> {
                        String newStatus = statusOptions[selectedIndex[0]];
                        orderStatus.setText(newStatus);
                        if (newStatus.equals("Đang xử lý")) {
                            orderStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.pending_order_status));
                        } else if (newStatus.equals("Đang giao")) {
                            orderStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.delivery_order_status));
                        } else if (newStatus.equals("Thành công")) {
                            orderStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.succes_order_status));
                        } else if (newStatus.equals("Thất bại")) {
                            orderStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.reject_order_status));
                        }
                    })
                    .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

            builder.create().show();
        });
    }

    private void setupButtons() {
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        btnEdit = findViewById(R.id.btn_admin_order_edit);
        btnEdit.setOnClickListener(v -> {
            Toast.makeText(this, "Edit button clicked", Toast.LENGTH_SHORT).show();
        });
    }
}