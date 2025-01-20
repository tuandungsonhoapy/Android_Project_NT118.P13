package com.example.androidproject.features.address.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.androidproject.features.address.data.entity.AddressEntity;

import java.util.List;
import java.util.stream.Collectors;

public class AddressModel extends AddressEntity {
    public AddressModel() {
    }

    public AddressModel(String street, String province, String district, String ward, String userId, String provinceId, String districtId, String wardId) {
        super(street, province, district, ward, userId, provinceId, districtId, wardId);
    }

    public String getStreet() {
        return super.getStreet();
    }

    public String getProvince() {
        return super.getProvince();
    }

    public String getDistrict() {
        return super.getDistrict();
    }

    public String getWard() {
        return super.getWard();
    }

    public String getUserId() {
        return super.getUserId();
    }

    public String getId() {
        return super.getId();
    }

    public boolean getIsDefault() {
        return super.getIsDefault();
    }

    public String getProvinceId() {
        return super.getProvinceId();
    }

    public String getDistrictId() {
        return super.getDistrictId();
    }

    public String getWardId() {
        return super.getWardId();
    }

    public void setStreet(String street) {
        super.setStreet(street);
    }

    public void setProvince(String province) {
        super.setProvince(province);
    }

    public void setDistrict(String district) {
        super.setDistrict(district);
    }

    public void setWard(String ward) {
        super.setWard(ward);
    }

    public void setUserId(String userId) {
        super.setUserId(userId);
    }

    public void setId(String id) {
        super.setId(id);
    }

    public void setIsDefault(boolean isDefault) {
        super.setIsDefault(isDefault);
    }

    public void setProvinceId(String provinceId) {
        super.setProvinceId(provinceId);
    }

    public void setDistrictId(String districtId) {
        super.setDistrictId(districtId);
    }

    public void setWardId(String wardId) {
        super.setWardId(wardId);
    }

    List<AddressEntity> toAddressEntityList(List<AddressModel> items) {
        return items.stream()
                .map(item -> {
                    AddressEntity addressEntity = new AddressEntity();
                    addressEntity.setId(item.getId());
                    addressEntity.setStreet(item.getStreet());
                    addressEntity.setProvince(item.getProvince());
                    addressEntity.setDistrict(item.getDistrict());
                    addressEntity.setWard(item.getWard());
                    addressEntity.setUserId(item.getUserId());
                    addressEntity.setProvinceId(item.getProvinceId());
                    addressEntity.setDistrictId(item.getDistrictId());
                    addressEntity.setWardId(item.getWardId());
                    return addressEntity;
                })
                .collect(Collectors.toList());
    }

    public String prefixAddressID(long quantity) {
        return "address" + String.format("%05d", quantity);
    }

    public String toString() {
        return "AddressModel{street=" + this.getStreet() + ", province=" + this.getProvince() + ", district=" + this.getDistrict() + ", ward=" + this.getWard() + ", userId=" + this.getUserId() + ", id=" + this.getId() + ", isDefault=" + this.getIsDefault() + ", provinceId=" + this.getProvinceId() + ", districtId=" + this.getDistrictId() + ", wardId=" + this.getWardId() + "}";
    }

    public String getFullAddress() {
        return this.getStreet() + ", " + this.getWard() + ", " + this.getDistrict() + ", " + this.getProvince();
    }
}
