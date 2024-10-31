package com.example.androidproject.features.admin_manager.data.entity;

public class CouponEntity {
    private String couponId;
    private String name;
    private int quantity;
    private String type;
    private double value;
    private double minimalTotal;
    private String dateStart;
    private String dateEnd;

    public CouponEntity() {}


    public CouponEntity(String couponId, String name, int quantity, String type, double value, double minimalTotal, String dateStart, String dateEnd) {
        this.couponId = couponId;
        this.name = name;
        this.quantity = quantity;
        this.type = type;
        this.value = value;
        this.minimalTotal = minimalTotal;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getMinimalTotal() {
        return minimalTotal;
    }

    public void setMinimalTotal(double minimalTotal) {
        this.minimalTotal = minimalTotal;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }
}