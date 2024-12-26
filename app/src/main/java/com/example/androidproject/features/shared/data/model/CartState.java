package com.example.androidproject.features.shared.data.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CartState {
    private static CartState instance;
    private final MutableLiveData<Integer> cartItemCount = new MutableLiveData<>(0);

    public CartState() {
    }

    public static CartState getInstance() {
        if (instance == null) {
            instance = new CartState();
        }
        return instance;
    }

    public LiveData<Integer> getCartItemCount() {
        return cartItemCount;
    }

    public void setCartItemCount(int count) {
        cartItemCount.setValue(count);
    }

    public void incrementItemCount(int value) {
        Integer currentCount = cartItemCount.getValue();
        if (currentCount != null) {
            cartItemCount.setValue(currentCount + value);
        }
    }
}
