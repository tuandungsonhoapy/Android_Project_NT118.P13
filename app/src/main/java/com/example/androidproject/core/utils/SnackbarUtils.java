package com.example.androidproject.core.utils;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackbarUtils {

    public static void showSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    public static void showSnackbarLong(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static void showSnackbarWithAction(View view, String message, String actionText, View.OnClickListener action) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setAction(actionText, action)
                .show();
    }

//    public static void showSuccessSnackbar(View view, String message) {
//        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
//                .setBackgroundTint(ContextCompat.getColor(view.getContext(), R.color.snackbar_success_background))
//                .setTextColor(ContextCompat.getColor(view.getContext(), R.color.snackbar_text_color))
//                .setActionTextColor(ContextCompat.getColor(view.getContext(), R.color.snackbar_action_color))
//                .setAction("OK", v -> {
//                    // Thực hiện hành động khi nhấn OK
//                });
//
//        snackbar.setIcon(ContextCompat.getDrawable(view.getContext(), R.drawable.ic_check)); // Thêm icon thành công
//        snackbar.show();
//    }
//
//    // Snackbar thất bại (màu đỏ, icon lỗi)
//    public static void showErrorSnackbar(View view, String message) {
//        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
//                .setBackgroundTint(ContextCompat.getColor(view.getContext(), R.color.snackbar_error_background))
//                .setTextColor(ContextCompat.getColor(view.getContext(), R.color.snackbar_text_color))
//                .setActionTextColor(ContextCompat.getColor(view.getContext(), R.color.snackbar_action_color))
//                .setAction("Try Again", v -> {
//                    // Thực hiện hành động khi nhấn "Try Again"
//                });
//
//        snackbar.setIcon(ContextCompat.getDrawable(view.getContext(), R.drawable.ic_error)); // Thêm icon lỗi
//        snackbar.show();
//    }
//
//    // Snackbar thông báo (màu vàng, icon thông báo)
//    public static void showInfoSnackbar(View view, String message) {
//        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
//                .setBackgroundTint(ContextCompat.getColor(view.getContext(), R.color.snackbar_info_background))
//                .setTextColor(ContextCompat.getColor(view.getContext(), R.color.snackbar_text_color))
//                .setActionTextColor(ContextCompat.getColor(view.getContext(), R.color.snackbar_action_color))
//                .setAction("Got It", v -> {
//                    // Thực hiện hành động khi nhấn "Got It"
//                });
//
//        snackbar.setIcon(ContextCompat.getDrawable(view.getContext(), R.drawable.ic_info)); // Thêm icon thông báo
//        snackbar.show();
//    }
//
//    // Snackbar bình thường (màu xám, không có icon)
//    public static void showDefaultSnackbar(View view, String message) {
//        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
//                .setBackgroundTint(ContextCompat.getColor(view.getContext(), R.color.snackbar_default_background))
//                .setTextColor(ContextCompat.getColor(view.getContext(), R.color.snackbar_text_color))
//                .setActionTextColor(ContextCompat.getColor(view.getContext(), R.color.snackbar_action_color));
//
//        snackbar.show();
//    }

//    View rootView = findViewById(android.R.id.content);
//
//    // Hiển thị Snackbar với thông báo ngắn
//        SnackbarUtils.showSnackbar(rootView, "This is a short Snackbar!");
//
//    // Hiển thị Snackbar với thông báo dài
//        SnackbarUtils.showSnackbarLong(rootView, "This is a longer Snackbar that will disappear after a few seconds.");
//
//    // Hiển thị Snackbar với hành động (ví dụ: "Undo")
//        SnackbarUtils.showSnackbarWithAction(rootView, "Action Snackbar", "UNDO", v -> {
//        // Xử lý hành động "UNDO"
//        Snackbar.make(rootView, "Action undone!", Snackbar.LENGTH_SHORT).show();
//    });
}

