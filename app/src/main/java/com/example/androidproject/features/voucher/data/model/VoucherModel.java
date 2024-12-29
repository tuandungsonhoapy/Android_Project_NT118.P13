package com.example.androidproject.features.voucher.data.model;

import com.example.androidproject.features.voucher.data.entity.VoucherEntity;

import java.util.List;
import java.util.stream.Collectors;

public class VoucherModel extends VoucherEntity {
    public VoucherModel() {
        super();
    }

    public VoucherModel(String name, String type, double value, double minimalTotal) {
        super(name, type, value, minimalTotal);
    }

    public String getId() {
        return super.getId();
    }

    public void setId(String id) {
        super.setId(id);
    }

    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName(name);
    }

    public String getType() {
        return super.getType();
    }

    public void setType(String type) {
        super.setType(type);
    }

    public double getValue() {
        return super.getValue();
    }

    public void setValue(double value) {
        super.setValue(value);
    }

    public double getMinimalTotal() {
        return super.getMinimalTotal();
    }

    public void setMinimalTotal(double minimalTotal) {
        super.setMinimalTotal(minimalTotal);
    }

    public boolean isHidden() {
        return super.isHidden();
    }

    public void setHidden(boolean hidden) {
        super.setHidden(hidden);
    }

    public String prefixVoucherId(long quantity) {
        return "voucher" + String.format("%05d", quantity);
    }

    public List<VoucherEntity> toVoucherEntityList(List<VoucherEntity> items) {
        return items.stream()
                .map(item -> {
                    VoucherEntity voucherEntity = new VoucherEntity();
                    voucherEntity.setId(item.getId());
                    voucherEntity.setName(item.getName());
                    voucherEntity.setType(item.getType());
                    voucherEntity.setValue(item.getValue());
                    voucherEntity.setMinimalTotal(item.getMinimalTotal());
                    return voucherEntity;
                })
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return getName();
    }
}
