package com.example.androidproject.features.setting.usecase;

import com.example.androidproject.features.auth.data.model.AuthModel;

public class SettingUseCase {
    public AuthModel getUser() {
        return new AuthModel("1", "cc@gmail.com", "trieu", "https://png.pngtree.com/png-clipart/20191120/original/pngtree-outline-user-icon-png-image_5045523.jpg");
    }
}
