package com.example.androidproject.features.admin_manager.data.datasource;

import com.example.androidproject.features.admin_manager.data.model.CouponModel;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface CouponRemote {
    Single<List<CouponModel>> fetch();

    Single<Boolean> post(CouponModel coupon);
}
