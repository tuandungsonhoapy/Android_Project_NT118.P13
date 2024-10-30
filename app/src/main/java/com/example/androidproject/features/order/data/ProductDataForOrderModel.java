package com.example.androidproject.features.order.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.androidproject.features.product.data.model.ProductModel;

public class ProductDataForOrderModel implements Parcelable {
    private String id;
    private ProductModel product;
    private String order_id;
    private int quantity;

    public ProductDataForOrderModel(String id, ProductModel product, String order_id, int quantity) {
        this.id = id;
        this.product = product;
        this.order_id = order_id;
        this.quantity = quantity;
    }

    protected ProductDataForOrderModel(Parcel in) {
        id = in.readString();
        product = in.readParcelable(ProductModel.class.getClassLoader());
        order_id = in.readString();
        quantity = in.readInt();
    }

    public static final Creator<ProductDataForOrderModel> CREATOR = new Creator<ProductDataForOrderModel>() {
        @Override
        public ProductDataForOrderModel createFromParcel(Parcel in) {
            return new ProductDataForOrderModel(in);
        }

        @Override
        public ProductDataForOrderModel[] newArray(int size) {
            return new ProductDataForOrderModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(product, flags);
        dest.writeString(order_id);
        dest.writeInt(quantity);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product_id) {
        this.product = product;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
