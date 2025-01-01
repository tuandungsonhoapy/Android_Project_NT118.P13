package com.example.androidproject.features.address.presentation;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.core.credential.UserPreferences;
import com.example.androidproject.features.address.data.model.AddressModel;
import com.example.androidproject.features.address.usecase.AddressUsecase;

import java.util.List;

public class ListAddressSettingAdapter extends RecyclerView.Adapter<ListAddressSettingAdapter.ListAddressSettingViewHolder> {
    private List<AddressModel> addresses;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private AddressUsecase addressUsecase = new AddressUsecase();
    UserPreferences userPreferences;

    public ListAddressSettingAdapter(Context context, List<AddressModel> addresses) {
        this.context = context;
        this.addresses = addresses;
        userPreferences = new UserPreferences(context);
    }

    public interface OnItemClickListener {
        void onItemClick(AddressModel address);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public ListAddressSettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_address_setting, parent, false);
        return new ListAddressSettingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAddressSettingViewHolder holder, int position) {
        AddressModel address = addresses.get(position);
        holder.userAddress.setText(address.getFullAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(address);
                }
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            addresses.remove(position);
            addressUsecase.deleteAddress(address.getId())
                    .thenAccept(r -> {
                        if (r.isRight()) {
                            Toast.makeText(context, "Xóa địa chỉ thành công", Toast.LENGTH_SHORT).show();
                            notifyItemRemoved(position);
                        }
                    });
        });

        holder.rbDefault.setChecked(address.getIsDefault());
        holder.rbDefault.setOnClickListener(v -> {
            String addressId = address.getId();
            String fullAddress = address.getFullAddress();
            addressUsecase.updateAddressDefault(addressId, fullAddress)
                    .thenAccept(r -> {
                        if (r.isRight()) {
                            for (AddressModel a : addresses) {
                                a.setIsDefault(a.getId().equals(address.getId()));
                            }

                            ((Activity) context).runOnUiThread(() -> notifyDataSetChanged());

                            userPreferences.setUserDataByKey(UserPreferences.KEY_ADDRESS_ID, addressId);
                            userPreferences.setUserDataByKey(UserPreferences.KEY_FULL_ADDRESS, fullAddress);
                            Toast.makeText(context, "Đặt làm địa chỉ mặc định thành công", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    public void updateAddressList(List<AddressModel> newAddresses) {
        this.addresses.clear();
        this.addresses.addAll(newAddresses);
        notifyDataSetChanged();
    }

    public static class ListAddressSettingViewHolder extends RecyclerView.ViewHolder {
          TextView userAddress;
          Button btnDelete;
          RadioButton rbDefault;
          
            public ListAddressSettingViewHolder(@NonNull View itemView) {
                super(itemView);
                userAddress = itemView.findViewById(R.id.tv_address);
                btnDelete = itemView.findViewById(R.id.btn_del_address);
                rbDefault = itemView.findViewById(R.id.rb_default_address);
            }
    }
}
