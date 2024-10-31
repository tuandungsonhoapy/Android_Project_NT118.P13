package com.example.androidproject.features.admin_manager.data.datasource;

import com.example.androidproject.features.admin_manager.data.model.CouponModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class CouponRemoteImpl implements CouponRemote{
    @Override
    public Single<List<CouponModel>> fetch() {
        return Single.create(emitter -> {
            List<CouponModel> coupons = new ArrayList<>();
            if (!emitter.isDisposed()) {
                emitter.onSuccess(coupons);
            }
        });
    }

    @Override
    public Single<Boolean> post(CouponModel coupon) {
        return Single.create(emitter -> {
            boolean success = true;
            if (!emitter.isDisposed()) {
                emitter.onSuccess(success);
            }
        });
    }
}
