package com.example.androidproject.features.order;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.androidproject.R;
import java.util.ArrayList;
import java.util.List;

public class FragmentOrder extends Fragment {
    private RecyclerView rcOrder;
    private OrderAdapter orderAdapter;
    private List<Card> orderList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_history, container, false);
        rcOrder = view.findViewById(R.id.rc_Order);

        // Khởi tạo danh sách đơn hàng
        orderList = new ArrayList<>();
        initializeOrderList(); // Phương thức khởi tạo mẫu đơn hàng

        // Thiết lập RecyclerView
        orderAdapter = new OrderAdapter(getContext(), orderList);
        rcOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        rcOrder.setAdapter(orderAdapter);

        return view;
    }

    private void initializeOrderList() {
        // Thêm dữ liệu mẫu vào danh sách đơn hàng
        orderList.add(new Card(R.drawable.line_3, R.drawable.icon, "#HD0921", "Tp.Hồ Chí Minh, Việt Nam", R.drawable.date, "20/03/2024", "12:00", R.drawable.date, "21/03/2024", R.drawable.location, "21/03/2024", 0));
        orderList.add(new Card(R.drawable.line_3, R.drawable.icon, "#HD0922", "Hà Nội, Việt Nam", R.drawable.date, "21/03/2024", "14:00", R.drawable.date, "22/03/2024", R.drawable.location, "22/03/2024", 1));
        // Thêm nhiều đơn hàng hơn nếu cần
    }

    // Phương thức để thêm đơn hàng mới
    public void addNewOrder(Card newOrder) {
        orderList.add(newOrder); // Thêm đơn hàng mới vào danh sách
        orderAdapter.notifyDataSetChanged(); // Cập nhật giao diện
    }
}