package com.example.androidproject.features.setting.usecase;

import android.util.Log;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.setting.data.repository.AddressApiClient;
import com.example.androidproject.features.setting.data.repository.AddressApiServices;
import com.example.androidproject.features.setting.data.types.AddressDistrictData;
import com.example.androidproject.features.setting.data.types.AddressDistrictResponseData;
import com.example.androidproject.features.setting.data.types.AddressProvinceData;
import com.example.androidproject.features.setting.data.types.AddressProvinceResponseData;
import com.example.androidproject.features.setting.data.types.AddressWardData;
import com.example.androidproject.features.setting.data.types.AddressWardResponseData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressUtils {
    public CompletableFuture<Either<Failure, List<AddressProvinceData>>> fetchProvinces() {
        CompletableFuture<Either<Failure, List<AddressProvinceData>>> future = new CompletableFuture<>();
        AddressApiServices apiServices = AddressApiClient.getRetrofitInstance().create(AddressApiServices.class);
        Call<AddressProvinceResponseData> call = apiServices.getProvince("1", "0");

        call.enqueue(new Callback<AddressProvinceResponseData>() {
            @Override
            public void onResponse(Call<AddressProvinceResponseData> call, Response<AddressProvinceResponseData> response) {
                if (response.isSuccessful()) {
                    AddressProvinceResponseData data = response.body();
                    if (data != null) {
                        future.complete(Either.right(data.getData()));
                    } else {
                        future.complete(Either.left(new Failure("Failed to fetch provinces")));
                    }
                } else {
                    future.complete(Either.left(new Failure("Failed to fetch provinces")));
                }
            }

            @Override
            public void onFailure(Call<AddressProvinceResponseData> call, Throwable t) {
                future.complete(Either.left(new Failure(t.getMessage())));
            }
        });

        return future;
    }

    public CompletableFuture<Either<Failure, List<AddressDistrictData>>> fetchDistricts(String provinceId) {
        CompletableFuture<Either<Failure, List<AddressDistrictData>>> future = new CompletableFuture<>();
        AddressApiServices apiServices = AddressApiClient.getRetrofitInstance().create(AddressApiServices.class);
        Call<AddressDistrictResponseData> call = apiServices.getDistrict("2", provinceId);

        call.enqueue(new Callback<AddressDistrictResponseData>() {
            @Override
            public void onResponse(Call<AddressDistrictResponseData> call, Response<AddressDistrictResponseData> response) {
                if (response.isSuccessful()) {
                    AddressDistrictResponseData data = response.body();
                    if (data != null) {
                        future.complete(Either.right(data.getData()));
                    } else {
                        future.complete(Either.left(new Failure("Failed to fetch districts")));
                    }
                } else {
                    future.complete(Either.left(new Failure("Failed to fetch districts")));
                }
            }

            @Override
            public void onFailure(Call<AddressDistrictResponseData> call, Throwable t) {
                future.complete(Either.left(new Failure(t.getMessage())));
            }
        });

        return future;
    }

    public CompletableFuture<Either<Failure, List<AddressWardData>>> fetchWards(String districtId) {
        CompletableFuture<Either<Failure, List<AddressWardData>>> future = new CompletableFuture<>();
        AddressApiServices apiServices = AddressApiClient.getRetrofitInstance().create(AddressApiServices.class);
        Call<AddressWardResponseData> call = apiServices.getWard("3", districtId);

        call.enqueue(new Callback<AddressWardResponseData>() {
            @Override
            public void onResponse(Call<AddressWardResponseData> call, Response<AddressWardResponseData> response) {
                if (response.isSuccessful()) {
                    AddressWardResponseData data = response.body();
                    if (data != null) {
                        future.complete(Either.right(data.getData()));
                    } else {
                        future.complete(Either.left(new Failure("Failed to fetch wards")));
                    }
                } else {
                    future.complete(Either.left(new Failure("Failed to fetch wards")));
                }
            }

            @Override
            public void onFailure(Call<AddressWardResponseData> call, Throwable t) {
                future.complete(Either.left(new Failure(t.getMessage())));
            }
        });

        return future;
    }
}
