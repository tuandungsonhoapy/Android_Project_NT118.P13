package com.example.androidproject.features.admin_manager.presentation.widgets;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.admin_manager.presentation.admin.DetailAdminAdminActivity;
import com.example.androidproject.features.auth.data.model.AuthModel;

import java.util.List;

public class ListAdminItemAdminAdapter extends RecyclerView.Adapter<ListAdminItemAdminAdapter.ListAdminItemAdminAdapterViewHolder> {
    private List<AuthModel> adminList;
    private Context context;

    public ListAdminItemAdminAdapter(List<AuthModel> adminList, Context context) {
        this.adminList = adminList;
        this.context = context;
    }

    @NonNull
    @Override
    public ListAdminItemAdminAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_admin_admin, parent, false);
        return new ListAdminItemAdminAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdminItemAdminAdapterViewHolder holder, int position) {
        AuthModel admin = adminList.get(position);
        holder.tvAdminName.setText("Triệu Lê");
        holder.tvAdminEmail.setText("hihi@gmail.com");
        holder.tvAdminPhone.setText("0976144355");
        holder.tvAdminID.setText("user00001");

        holder.itemView.setOnClickListener(v -> {
             Intent intent = new Intent(context, DetailAdminAdminActivity.class);
             context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return adminList.size();
    }
    public static class ListAdminItemAdminAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tvAdminName;
        TextView tvAdminEmail;
        TextView tvAdminPhone;
        TextView tvAdminID;
        public ListAdminItemAdminAdapterViewHolder (View itemView) {
            super(itemView);

            tvAdminName = itemView.findViewById(R.id.tvAdminName);
            tvAdminEmail = itemView.findViewById(R.id.tvAdminEmail);
            tvAdminPhone = itemView.findViewById(R.id.tvAdminPhone);
            tvAdminID = itemView.findViewById(R.id.tvAdminID);
        }
    }
}
