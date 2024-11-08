package com.example.androidproject.features.address.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AddressModel implements Parcelable {
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

    public AddressModel(String id,String street, String province, String district, String ward, boolean isDefault ,String userId, String provinceId, String districtId, String wardId) {
        this.street = street;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.userId = userId;
        this.id = id;
        this.isDefault = isDefault;
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.wardId = wardId;
    }

    protected AddressModel(Parcel in) {
        id = in.readString();
        street = in.readString();
        province = in.readString();
        district = in.readString();
        ward = in.readString();
        isDefault = in.readByte() != 0; // Duy trì boolean
        userId = in.readString();
        provinceId = in.readString();
        districtId = in.readString();
        wardId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(street);
        dest.writeString(province);
        dest.writeString(district);
        dest.writeString(ward);
        dest.writeByte((byte) (isDefault ? 1 : 0)); // Ghi boolean
        dest.writeString(userId);
        dest.writeString(provinceId);
        dest.writeString(districtId);
        dest.writeString(wardId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<AddressModel> CREATOR = new Parcelable.Creator<AddressModel>() {
        @Override
        public AddressModel createFromParcel(Parcel in) {
            return new AddressModel(in);
        }

        @Override
        public AddressModel[] newArray(int size) {
            return new AddressModel[size];
        }
    };

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
    public String getFullAddress() {
        return street + ", " + ward + ", " + district + ", " + province;
    }
}
