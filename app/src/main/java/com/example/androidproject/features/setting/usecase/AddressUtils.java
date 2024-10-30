package com.example.androidproject.features.setting.usecase;

import android.util.Log;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressUtils {
    public static String getFullAddress(String tinh, String huyen, String xa) {
        return tinh + ", " + huyen + ", " + xa;
    }

    public void fetchProvinces(OnProvincesFetchedListener listener) {
        AddressApiServices apiServices = AddressApiClient.getRetrofitInstance().create(AddressApiServices.class);
        Call<AddressProvinceResponseData> call = apiServices.getProvince("1", "0");

        call.enqueue(new Callback<AddressProvinceResponseData>() {
            @Override
            public void onResponse(Call<AddressProvinceResponseData> call, Response<AddressProvinceResponseData> response) {
                if (response.isSuccessful()) {
                    AddressProvinceResponseData data = response.body();
                    if (data != null && listener != null) {
                        listener.onProvincesFetched(data.getData());
                    } else {
                        listener.onError("Failed to fetch provinces");
                    }
                } else {
                        listener.onError("Failed to fetch provinces");
                }
            }

            @Override
            public void onFailure(Call<AddressProvinceResponseData> call, Throwable t) {
                if (listener != null) {
                    listener.onError(t.getMessage());
                }
            }
        });
    }

    public void fetchDistricts(String provinceId, OnDistrictsFetchedListener listener) {
        AddressApiServices apiServices = AddressApiClient.getRetrofitInstance().create(AddressApiServices.class);
        Call<AddressDistrictResponseData> call = apiServices.getDistrict("2", provinceId);

        call.enqueue(new Callback<AddressDistrictResponseData>() {
            @Override
            public void onResponse(Call<AddressDistrictResponseData> call, Response<AddressDistrictResponseData> response) {
                if (response.isSuccessful()) {
                    AddressDistrictResponseData data = response.body();
                    if (data != null && listener != null) {
                        listener.onDistrictsFetched(data.getData());
                    } else {
                        listener.onError("Failed to fetch districts");
                    }
                } else {
                        listener.onError("Failed to fetch districts");
                }
            }

            @Override
            public void onFailure(Call<AddressDistrictResponseData> call, Throwable t) {
                if (listener != null) {
                    listener.onError(t.getMessage());
                }
            }
        });
    }

    public void fetchWards(String districtId ,OnWardsFetchedListener listener) {
        AddressApiServices apiServices = AddressApiClient.getRetrofitInstance().create(AddressApiServices.class);
        Call<AddressWardResponseData> call = apiServices.getWard("3", districtId );

        call.enqueue(new Callback<AddressWardResponseData>() {
            @Override
            public void onResponse(Call<AddressWardResponseData> call, Response<AddressWardResponseData> response) {
                if (response.isSuccessful()) {
                    AddressWardResponseData data = response.body();
                    if (data != null && listener != null) {
                        listener.onWardsFetched(data.getData());
                    } else {
                        listener.onError("Failed to fetch wards");
                    }
                } else {
                        listener.onError("Failed to fetch wards");
                }
            }

            @Override
            public void onFailure(Call<AddressWardResponseData> call, Throwable t) {
                if (listener != null) {
                    listener.onError(t.getMessage());
                }
            }
        });
    }

    public interface OnProvincesFetchedListener {
        void onProvincesFetched(List<AddressProvinceData> provinces);
        void onError(String errorMessage);
    }

    public interface OnDistrictsFetchedListener {
        void onDistrictsFetched(List<AddressDistrictData> districts);
        void onError(String errorMessage);
    }

    public interface OnWardsFetchedListener {
        void onWardsFetched(List<AddressWardData> wards);
        void onError(String errorMessage);
    }
}
