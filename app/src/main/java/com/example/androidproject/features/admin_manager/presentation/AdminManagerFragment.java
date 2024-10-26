package com.example.androidproject.features.admin_manager.presentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.androidproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminManagerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminManagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminManagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminManagerFragment newInstance(String param1, String param2) {
        AdminManagerFragment fragment = new AdminManagerFragment();
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
        View view = inflater.inflate(R.layout.fragment_admin_manager, container, false);

        LinearLayout itemOrder = view.findViewById(R.id.item_order_manager);
        itemOrder.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AdminOrderManagerActivity.class);
            startActivity(intent);
        });

        LinearLayout itemProduct = view.findViewById(R.id.item_product_manager);

        LinearLayout itemUser = view.findViewById(R.id.item_user_manager);
        itemUser.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AdminUserManagerActivity.class);
            startActivity(intent);
        });

        LinearLayout itemCategory = view.findViewById(R.id.item_category_manager);
        LinearLayout itemVoucher = view.findViewById(R.id.item_voucher_manager);
        LinearLayout itemAdmin = view.findViewById(R.id.item_admin_manager);


        return view;
    }
}