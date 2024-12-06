package com.example.androidproject.features.product.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.androidproject.features.brand.data.model.BrandModel;

public class ProductModel implements Parcelable {
    private String name;
    private int image;
    private double price;
    private int quantity;
    private BrandModel brand;
    private boolean favorite;

    public ProductModel(String name, int image, double price, int quantity, BrandModel brand, boolean favorite) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.brand = brand;
        this.favorite = favorite;
    }
    protected ProductModel(Parcel in) {
        name = in.readString();
        image = in.readInt();
        price = in.readDouble();
        quantity = in.readInt();
        brand = in.readParcelable(BrandModel.class.getClassLoader());
        favorite = in.readByte() != 0;
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(image);
        dest.writeDouble(price);
        dest.writeInt(quantity);
//        dest.writeParcelable(brand, flags);
        dest.writeByte((byte) (favorite ? 1 : 0));
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BrandModel getBrand() {
        return brand;
    }

    public void setBrand(BrandModel brand) {
        this.brand = brand;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
