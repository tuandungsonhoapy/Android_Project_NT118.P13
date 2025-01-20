package com.example.androidproject.features.address.data.entity;

import com.google.firebase.Timestamp;

public class AddressEntity {
    private String id;
    private String street;
    private String province;
    private String district;
    private String ward;
    private boolean isDefault;
    private String userId;
    private String provinceId;
    private String districtId;
    private String wardId;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public AddressEntity() {
    }

    public AddressEntity(String street, String province, String district, String ward ,String userId, String provinceId, String districtId, String wardId) {
        this.street = street;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.userId = userId;
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.wardId = wardId;
        this.isDefault = false;
        this.createdAt = Timestamp.now();
        this.updatedAt = null;
    }

    public String getStreet() {
        return street;
    }

    public String getProvince() {
        return province;
    }

    public String getDistrict() {
        return district;
    }

    public String getWard() {
        return ward;
    }

    public String getUserId() {
        return userId;
    }

    public String getId() {
        return id;
    }

    public boolean getIsDefault() {
        return isDefault;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public String getWardId() {
        return wardId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

}
