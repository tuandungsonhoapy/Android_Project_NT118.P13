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
import com.example.androidproject.features.checkout.data.model.CheckoutModel;
import com.example.androidproject.features.checkout.usecase.CheckoutUseCase;
import com.example.androidproject.features.order.data.ProductDataForOrderModel;

import java.util.ArrayList;
import java.util.List;

public class DetailOrderAdminActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private Button btnEdit;
    private CheckoutModel checkoutModelGlobal = new CheckoutModel();
    private CheckoutUseCase checkoutUseCase = new CheckoutUseCase();
    private String orderIdGlobal, statusGlobal;
    TextView orderStatus;
    private Button btnUpdateStatus;

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
        TextView orderCustomerPhone = findViewById(R.id.tvCustomerPhone);
        TextView orderCustomerEmail = findViewById(R.id.tvCustomerEmail);
        RecyclerView orderDetailRecyclerView = findViewById(R.id.recycler_products_order_view);
        orderStatus = findViewById(R.id.tvOrderStatus);
        btnUpdateStatus = findViewById(R.id.btnUpdateOrderStatus);

        statusGlobal = getIntent().getStringExtra("order_status");
        setStatusOrder(statusGlobal);

        // Retrieve data from Intent
        orderId.setText(getIntent().getStringExtra("order_id"));
        orderIdGlobal = getIntent().getStringExtra("order_id");

        getCheckoutById();

        if(checkoutModelGlobal != null) {
            Toast.makeText(this, "Checkout model is not null" + checkoutModelGlobal.getStatus(), Toast.LENGTH_SHORT).show();
        } else {
            Log.d("DetailOrderAdminActivity", "Checkout model is null!!!");
        }

        orderDate.setText(getIntent().getStringExtra("order_date"));
        orderTotalPrice.setText(getIntent().getStringExtra("order_total_price"));
        orderCustomerName.setText(getIntent().getStringExtra("order_customer_name"));
        orderCustomerID.setText(getIntent().getStringExtra("order_customer_id"));
        orderCustomerPhone.setText(getIntent().getStringExtra("order_customer_phone"));
        orderCustomerEmail.setText(getIntent().getStringExtra("order_customer_email"));
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

        btnUpdateStatus.setOnClickListener(v -> {
            Toast.makeText(this, "Update status button clicked", Toast.LENGTH_SHORT).show();
            updateOrder();
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

    public void getCheckoutById() {

        checkoutUseCase.getCheckoutById(orderIdGlobal)
                .thenAccept(r -> {
                    if(r.isRight()) {
                        Log.d("DetailOrderAdminActivity", "Getting checkout by order id: " + orderIdGlobal);
                        checkoutModelGlobal = new CheckoutModel();
                        checkoutModelGlobal = r.getRight();

//                        if(r.getRight() != null) {
//                            Toast.makeText(this, "Checkout model is not null" + checkoutModelGlobal.getStatus(), Toast.LENGTH_SHORT).show();
//                        } else {
//                            Log.d("DetailOrderAdminActivity", "Checkout model is null!!!");
//                        }
                    }
                    else {
                        Log.d("DetailOrderAdminActivity", "Error getting checkout by order id: " + orderIdGlobal);
                    }
                });
    }

    public void updateOrder() {
        if(statusGlobal == null || statusGlobal.isEmpty() || statusGlobal == "SUCCESS" || statusGlobal == "FAILED") {
            Toast.makeText(this, "Cannot update order", Toast.LENGTH_SHORT).show();
            return;
        }

        if(statusGlobal == "PENDING") {
            updateStatusOrder(orderIdGlobal, "INTRANSIT");
            setStatusOrder("INTRANSIT");
        } else if (statusGlobal == "INTRANSIT") {
            updateStatusOrder(orderIdGlobal, "SUCCESS");
            setStatusOrder("SUCCESS");
        }
    }

    public void updateStatusOrder(String orderId, String status) {
        checkoutUseCase.updateStatus(orderId, status)
                .thenAccept(r -> {
                    if(r.isRight()) {
                        Toast.makeText(this, "Update status successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("DetailOrderAdminActivity", "Update status failed");
                    }
                });
    }

    public void setStatusOrder(String status){
        if (statusGlobal.equals("PENDING")) {
            orderStatus.setText("Đang xử lý");
            orderStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.pending_order_status));
        } else if (statusGlobal.equals("INTRASIT")) {
            orderStatus.setText("Đang giao");
            orderStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.delivery_order_status));
        } else if (statusGlobal.equals("SUCCESS")) {
            orderStatus.setText("Thành công");
            orderStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.succes_order_status));
        } else if (statusGlobal.equals("FAILED")) {
            orderStatus.setText("Thất bại");
            orderStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.reject_order_status));
        }
    }
}