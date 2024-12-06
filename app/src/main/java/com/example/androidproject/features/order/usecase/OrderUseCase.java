package com.example.androidproject.features.order.usecase;

import com.example.androidproject.R;
import com.example.androidproject.features.brand.data.model.BrandModel;
import com.example.androidproject.features.order.data.OrderModel;
import com.example.androidproject.features.order.data.ProductDataForOrderModel;
import com.example.androidproject.features.product.data.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class OrderUseCase {
    public void createOrder() {
        // create order
    }

    public void updateOrder() {
        // update order
    }

    public void deleteOrder() {
        // delete order
    }

    public void getOrder() {
        // get order
    }

    public List<OrderModel> getAllOrders() {
        List<OrderModel> orderList = new ArrayList<>();
        orderList.add(new OrderModel("1", "1", new ProductDataForOrderModel[]{
                new ProductDataForOrderModel("1", new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel( "Lenovo", "",""), true), "1", 1),
                new ProductDataForOrderModel("2", new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel( "Lenovo", "",""), true), "1", 1)
        }, 100.0, "pending", "COD", "1", "2021-10-01"));

        orderList.add(new OrderModel("1", "1", new ProductDataForOrderModel[]{
                new ProductDataForOrderModel("1", new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel( "Lenovo", "",""), true), "1", 1),
                new ProductDataForOrderModel("2", new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel( "Lenovo", "",""), true), "1", 1)
        }, 100.0, "pending", "COD", "1", "2021-10-01"));

        orderList.add(new OrderModel("1", "1", new ProductDataForOrderModel[]{
                new ProductDataForOrderModel("1", new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel( "Lenovo", "",""), true), "1", 1),
                new ProductDataForOrderModel("2", new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel( "Lenovo", "",""), true), "1", 1)
        }, 100.0, "pending", "COD", "1", "2021-10-01"));

        orderList.add(new OrderModel("1", "1", new ProductDataForOrderModel[]{
                new ProductDataForOrderModel("1", new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel( "Lenovo", "",""), true), "1", 1),
                new ProductDataForOrderModel("2", new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel( "Lenovo", "",""), true), "1", 1)
        }, 100.0, "pending", "COD", "1", "2021-10-01"));
        return orderList;
    }
}
