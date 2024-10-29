package com.example.androidproject.features.admin_manager.product_management.usecase;

import java.util.ArrayList;
import java.util.List;

public class ProductManagementUsecase {

    public List<String> getStatusList() {
        List<String> statusList = new ArrayList<>();
        statusList.add("Tất cả");
        statusList.add("Còn hàng");
        statusList.add("Sắp hết");
        statusList.add("Hết hàng");
        return statusList;
    }
}
