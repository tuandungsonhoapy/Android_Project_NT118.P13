package com.example.androidproject.features.cart.usecase;

import com.example.androidproject.R;
import com.example.androidproject.features.brand.data.model.BrandModel;
import com.example.androidproject.features.order.data.ProductDataForOrderModel;
import com.example.androidproject.features.product.data.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class CartUseCase {
    public List<ProductDataForOrderModel>  getCart() {
        List<ProductDataForOrderModel> cartItem = new ArrayList<>();
        cartItem.add(new ProductDataForOrderModel("1", new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel(1, "Lenovo", R.drawable.image_asus_logo,1), true), "1", 1));
        cartItem.add(new ProductDataForOrderModel("1", new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel(1, "Lenovo", R.drawable.image_asus_logo,1), true), "1", 1));
        cartItem.add(new ProductDataForOrderModel("1", new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel(1, "Lenovo", R.drawable.image_asus_logo,1), true), "1", 1));

        return cartItem;
    }
}
