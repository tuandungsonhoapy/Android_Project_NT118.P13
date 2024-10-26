package com.example.androidproject.features.admin_manager.user_manager.presentation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.auth.data.model.AuthModel;

import java.util.List;

public class ListUserItemAdminAdapter extends RecyclerView.Adapter<ListUserItemAdminAdapter.ListUserItemAdminAdapterViewHolder> {
    private List<AuthModel> userList;
    private Context context;

    public ListUserItemAdminAdapter(List<AuthModel> userList,Context context) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public ListUserItemAdminAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_user_admin, parent, false);
        return new ListUserItemAdminAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListUserItemAdminAdapterViewHolder holder, int position) {
        AuthModel user = userList.get(position);
        holder.tvUserID.setText(user.getUid());
        holder.tvUserName.setText(user.getDisplayName());
        holder.tvUserPhone.setText("1231525324");
        holder.tvUserEmail.setText(user.getEmail());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailUserAdminActivity.class);
            intent.putExtra("id", user.getUid());
            intent.putExtra("name", user.getDisplayName());
            intent.putExtra("email", user.getEmail());
            intent.putExtra("phone", "1231525324");
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ListUserItemAdminAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserID;
        TextView tvUserName;
        ImageView imgAvatar;
        TextView tvUserPhone;
        TextView tvUserEmail;
        public ListUserItemAdminAdapterViewHolder(View itemView) {
            super(itemView);

            tvUserID = itemView.findViewById(R.id.tvUserID);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvUserPhone = itemView.findViewById(R.id.tvUserPhone);
            tvUserEmail = itemView.findViewById(R.id.tvUserEmail);
        }
    }
}
