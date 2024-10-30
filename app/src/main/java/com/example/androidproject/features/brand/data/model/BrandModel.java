package com.example.androidproject.features.brand.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BrandModel implements Parcelable {
    private int id;
    private String name;
    private int imageResource;
    private int quantity;

    public BrandModel(int id, String name, int imageResource, int quantity) {
        this.id = id;
        this.name = name;
        this.imageResource = imageResource;
        this.quantity = quantity;
    }

    protected BrandModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        imageResource = in.readInt();
        quantity = in.readInt();
    }

    public static final Creator<BrandModel> CREATOR = new Creator<BrandModel>() {
        @Override
        public BrandModel createFromParcel(Parcel in) {
            return new BrandModel(in);
        }

        @Override
        public BrandModel[] newArray(int size) {
            return new BrandModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(imageResource);
        dest.writeInt(quantity);
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getImageResource() { return imageResource; }
    public void setImageResource(int imageResource) { this.imageResource = imageResource; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
