package com.example.androidproject.features.admin_manager.usecase;

import com.example.androidproject.features.auth.data.model.AuthModel;

import java.util.ArrayList;
import java.util.List;

public class UserManagerUseCase {
    public List<AuthModel> getUserList() {
        List<AuthModel> userList = new ArrayList<>();
        userList.add(new AuthModel("1", "trieule", "Triệu lê", "fc"));;
        userList.add(new AuthModel("2", "trieule", "Triệu lê", "df"));;
        userList.add(new AuthModel("3", "trieule", "Triệu lê", "ff"));;

        return userList;
    }
}
