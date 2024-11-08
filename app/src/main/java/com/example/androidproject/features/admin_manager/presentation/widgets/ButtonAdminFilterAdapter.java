package com.example.androidproject.features.admin_manager.presentation.widgets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;

import java.util.List;

public class ButtonAdminFilterAdapter extends RecyclerView.Adapter<ButtonAdminFilterAdapter.ButtonViewHolder> {

    private List<String> buttonNames;
    private OnButtonClickListener listener;

    public ButtonAdminFilterAdapter(List<String> buttonNames, OnButtonClickListener listener) {
        this.buttonNames = buttonNames;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item__btn_admin_filter, parent, false);
        return new ButtonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonViewHolder holder, int position) {
        String name = buttonNames.get(position);

        holder.button.setText(name);
        holder.button.setOnClickListener(v -> listener.onButtonClick(name));
    }

    @Override
    public int getItemCount() {
        return buttonNames.size();
    }

    public static class ButtonViewHolder extends RecyclerView.ViewHolder {
        Button button;

        public ButtonViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.button_item);
        }
    }

    public interface OnButtonClickListener {
        void onButtonClick(String name);
    }
}

