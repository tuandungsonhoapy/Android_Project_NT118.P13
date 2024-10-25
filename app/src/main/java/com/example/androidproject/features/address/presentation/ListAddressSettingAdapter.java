package com.example.androidproject.features.address.presentation;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.features.address.data.model.AddressModel;
import com.example.androidproject.features.address.usecase.AddressUsecase;

import java.util.List;

public class ListAddressSettingAdapter extends RecyclerView.Adapter<ListAddressSettingAdapter.ListAddressSettingViewHolder> {
    private List<AddressModel> addresses;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private AddressUsecase addressUsecase = new AddressUsecase();

    public ListAddressSettingAdapter(Context context, List<AddressModel> addresses) {
        this.context = context;
        this.addresses = addresses;
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

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAddress(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("ListAddressSettingAdapter", "getItemCount: "+addresses.size());
        return addresses.size();
    }

    public void updateAddressList(List<AddressModel> newAddresses) {
        this.addresses.clear();
        this.addresses.addAll(newAddresses);
        notifyDataSetChanged();
    }

    private void deleteAddress(int position) {
        addresses.remove(position);
        addressUsecase.deleteAddress(Integer.toString(position));
        notifyItemRemoved(position);
    }

    public static class ListAddressSettingViewHolder extends RecyclerView.ViewHolder {
          TextView userAddress;
          Button btnDelete;

            public ListAddressSettingViewHolder(@NonNull View itemView) {
                super(itemView);
                userAddress = itemView.findViewById(R.id.tv_address);
                btnDelete = itemView.findViewById(R.id.btn_del_address);
            }
    }
}
