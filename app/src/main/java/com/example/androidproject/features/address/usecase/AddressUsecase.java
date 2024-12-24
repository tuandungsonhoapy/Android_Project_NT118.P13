package com.example.androidproject.features.address.usecase;

import com.example.androidproject.features.address.data.model.AddressModel;

import java.util.ArrayList;
import java.util.List;

public class AddressUsecase {
    public List<AddressModel> fetchAddress(String userId) {
        // Fetch address from database
//        List<AddressModel> addressModelList = new ArrayList<>();
//        addressModelList.add(new AddressModel("1", "123 ABC", "Hanoi", "Ba Dinh", "Truc Bach", true, "1", "01","001","00004"));
//        addressModelList.add(new AddressModel("2", "148 XYZ", "Hanoi", "Ba Dinh", "Truc Bach", true, "1", "01","001","00004"));
//        addressModelList.add(new AddressModel("3", "94, Ô 7 Khu B, Thị trấn Hậu Nghĩa, Đức Hòa, Long An", "Hanoi", "Ba Dinh", "Truc Bach", true, "1", "01","001","00004"));
        return null;
    }

    public void addAddress(AddressModel address, long quantity) {
        // Add address to database
    }

    public void editAddress(String id, AddressModel addressModel) {
        // Edit address in database
    }

    public void deleteAddress(String id) {
        return;
    }
}
