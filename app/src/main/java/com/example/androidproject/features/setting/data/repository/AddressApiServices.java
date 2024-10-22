package com.example.androidproject.features.setting.data.repository;

import com.example.androidproject.features.setting.data.types.AddressDistrictResponseData;
import com.example.androidproject.features.setting.data.types.AddressProvinceResponseData;
import com.example.androidproject.features.setting.data.types.AddressWardResponseData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AddressApiServices {
    @GET("api-tinhthanh/{a}/{b}.htm")
    public Call<AddressProvinceResponseData> getProvince(@Path("a") int a, @Path("b") int b);

    @GET("api-tinhthanh/{a}/{b}.htm")
    public Call<AddressDistrictResponseData> getDistrict(@Path("a") int a, @Path("b") int b);

    @GET("api-tinhthanh/{a}/{b}.htm")
    public Call<AddressWardResponseData> getWard(@Path("a") int a, @Path("b") int b);
}
