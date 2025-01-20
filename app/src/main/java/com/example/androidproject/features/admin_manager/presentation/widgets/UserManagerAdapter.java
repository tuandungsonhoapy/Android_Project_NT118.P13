package com.example.androidproject.features.admin_manager.presentation.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.core.credential.UserPreferences;
import com.example.androidproject.core.utils.JsonUtils;
import com.example.androidproject.features.admin_manager.presentation.user.DetailUserAdminActivity;
import com.example.androidproject.features.auth.data.entity.UserEntity;

import java.util.List;

public class UserManagerAdapter extends RecyclerView.Adapter<UserManagerAdapter.ListUserItemAdminAdapterViewHolder> {
    private List<UserEntity> userList;
    private final Context context;
    private final UserPreferences userPreferences;

    public UserManagerAdapter(Context context, List<UserEntity> userList) {
        this.context = context;
        this.userList = userList;
        this.userPreferences = new UserPreferences(context);
    }

    @NonNull
    @Override
    public ListUserItemAdminAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_user_admin, parent, false);
        return new ListUserItemAdminAdapterViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ListUserItemAdminAdapterViewHolder holder, int position) {
        UserEntity user = userList.get(position);
        holder.tvUserID.setText(user.getId());
        holder.tvUserName.setText(user.getLastName() + " " + user.getFirstName());
        holder.tvUserRole.setText(user.getRole() == 0 ? "Admin" : "Guest");
        holder.tvUserEmail.setText(user.getEmail());
        holder.tvUserPhone.setText(user.getPhone());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailUserAdminActivity.class);
            String userId = user.getId();
            String currentUserId = String.valueOf(userPreferences.getUserDataByKey(UserPreferences.KEY_ID));
            intent.putExtra("is_own_profile", userId.equals(currentUserId));
            intent.putExtra("user_data", JsonUtils.objectToJson(user));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateUsers(List<UserEntity> newUsers) {
        userList = newUsers;
        notifyDataSetChanged();
    }

    public static class ListUserItemAdminAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvUserID, tvUserName, tvUserRole, tvUserEmail, tvUserPhone;

        public ListUserItemAdminAdapterViewHolder(View itemView) {
            super(itemView);

            imgAvatar = itemView.findViewById(R.id.ivUserImage);
            tvUserID = itemView.findViewById(R.id.tvUserId);
            tvUserRole = itemView.findViewById(R.id.tvUserRole);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserEmail = itemView.findViewById(R.id.tvUserEmail);
            tvUserPhone = itemView.findViewById(R.id.tvUserPhone);
        }
    }
}
