package com.example.androidproject.features.voucher.data.entity;

import com.google.firebase.Timestamp;

public class VoucherEntity {
    private String id;
    private String name;
    private String type;
    private double value;
    private double minimalTotal;
    private boolean hidden;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public VoucherEntity() {}

    public VoucherEntity(String name, String type, double value, double minimalTotal) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.hidden = false;
        this.minimalTotal = minimalTotal;
        this.createdAt = Timestamp.now();
        this.updatedAt = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
