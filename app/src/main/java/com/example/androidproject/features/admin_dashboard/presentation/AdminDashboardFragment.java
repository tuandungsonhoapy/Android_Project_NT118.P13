package com.example.androidproject.features.admin_dashboard.presentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidproject.MainActivity;
import com.example.androidproject.R;
import com.example.androidproject.core.credential.UserPreferences;
import com.example.androidproject.core.utils.ConvertFormat;
import com.example.androidproject.features.checkout.data.model.CheckoutModel;
import com.example.androidproject.features.checkout.usecase.CheckoutUseCase;
import com.example.androidproject.features.order.usecase.OrderUseCase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminDashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminDashboardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView tvOrderToday, tvOrderOnHold, tvRevenueOnDay, tvRevenueOnMonth, tvUserName;
    private ImageView btnGoToShop;
    private RecyclerView rvAdminDashboardOrders;
    private OrderUseCase orderUseCase = new OrderUseCase();
    private CheckoutUseCase checkoutUseCase = new CheckoutUseCase();
    private UserPreferences userPreferences;

    public AdminDashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminDashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminDashboardFragment newInstance(String param1, String param2) {
        AdminDashboardFragment fragment = new AdminDashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_dashboard, container, false);
        btnGoToShop = view.findViewById(R.id.ivShop);

        tvOrderToday = view.findViewById(R.id.tvOrderToday);
        tvOrderOnHold = view.findViewById(R.id.tvOrderOnHold);
        tvRevenueOnDay = view.findViewById(R.id.tvRevenueOnDay);
        tvRevenueOnMonth = view.findViewById(R.id.tvRevenueOnMonth);
        tvUserName = view.findViewById(R.id.tvUserName);

        userPreferences = new UserPreferences(getContext());
        tvUserName.setText(userPreferences.getUserDataByKey(UserPreferences.KEY_FIRST_NAME).toString());

        btnGoToShop.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });

        rvAdminDashboardOrders = view.findViewById(R.id.rvAdminOrder);
        rvAdminDashboardOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        getCheckoutList();
        getNumberOrderToday();
        getNumberCheckoutByStatus("PENDING");
        getRevenueOnDay();
        getRevenueOnMonth();

        return view;
    }

    private void getCheckoutList() {
        checkoutUseCase.getLatestCheckouts(6)
                .thenAccept(r -> {
                    if (r.isRight()) {
                        rvAdminDashboardOrders.setAdapter(new AdminDashboardOrderAdapter(r.getRight(), getContext()));
                    }
                })
                .exceptionally(e -> {
                    Log.d("Checkout", "fail to get checkout list");
                    return null;
                });
    }

    private void getNumberOrderToday() {
        checkoutUseCase.getNumberCheckoutToday()
                .thenAccept(r -> {
                    if (r.isRight()) {
                        tvOrderToday.setText(String.valueOf(r.getRight()));
                    }
                })
                .exceptionally(e -> {
                    return null;
                });
    }

    private void getNumberCheckoutByStatus(String status) {
        checkoutUseCase.getCheckoutListByStatus(status)
                .thenAccept(r -> {
                    if (r.isRight()) {
                        tvOrderOnHold.setText(String.valueOf(r.getRight().size()));
                    }
                })
                .exceptionally(e -> {
                    return null;
                });
    }

    private void getRevenueOnDay() {
        checkoutUseCase.getCheckoutsToday()
                .thenAccept(r -> {
                    if (r.isRight()) {
                        double total = 0;
                        for (CheckoutModel checkoutModel : r.getRight()) {
                            if (checkoutModel.getStatus().equals("SUCCESS"))
                                total += checkoutModel.getTotalPrice();
                        }
                        tvRevenueOnDay.setText(ConvertFormat.formatPriceToVND(total));
                    }
                })
                .exceptionally(e -> {
                    return null;
                });
    }

    private void getRevenueOnMonth() {
        checkoutUseCase.getCheckoutsThisMonth()
                .thenAccept(r -> {
                    if (r.isRight()) {
                        double total = 0;
                        for (CheckoutModel checkoutModel : r.getRight()) {
                            if (checkoutModel.getStatus().equals("SUCCESS"))
                                total += checkoutModel.getTotalPrice();
                        }
                        tvRevenueOnMonth.setText(ConvertFormat.formatPriceToVND(total));
                    }
                })
                .exceptionally(e -> {
                    return null;
                });
    }
}