package com.example.androidproject;

import com.example.androidproject.core.utils.CloudinaryConfig;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CloudinaryConfig.khoi_tao_cloudinary(this);
    }
}
