package com.example.androidproject.features.order;

public class Card {
    private int line; // ID cho Line
    private int icon; // ID cho Icon
    private String id; // ID cho Order ID
    private String location; // ID cho Location
    private int resourcesIconLocation; // ID cho ảnh icon
    private String ngayNhan; // ID cho Ngày Nhận
    private String receiveTime; // ID cho Receive Time
    private int dateIcon2; // ID cho Date Icon
    private String ShipTime; // ID cho Ngày Xuất Kho
    private int dateIcon; // ID cho Date Icon
    private String ngayXuatKho; // ID cho Ngày Xuất Kho
    private int chip; // ID cho Chip


    public Card(int line, int icon, String id, String location, int resourcesIconLocation, String ngayNhan, String receiveTime, int dateIcon2, String shipTime, int dateIcon, String ngayXuatKho, int chip) {
        this.line = line;
        this.icon = icon;
        this.id = id;
        this.location = location;
        this.resourcesIconLocation = resourcesIconLocation;
        this.ngayNhan = ngayNhan;
        this.receiveTime = receiveTime;
        this.dateIcon2 = dateIcon2;
        ShipTime = shipTime;
        this.dateIcon = dateIcon;
        this.ngayXuatKho = ngayXuatKho;
        this.chip = chip;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getResourcesIconLocation() {
        return resourcesIconLocation;
    }

    public void setResourcesIconLocation(int resourcesIconLocation) {
        this.resourcesIconLocation = resourcesIconLocation;
    }

    public String getNgayNhan() {
        return ngayNhan;
    }

    public void setNgayNhan(String ngayNhan) {
        this.ngayNhan = ngayNhan;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public int getDateIcon2() {
        return dateIcon2;
    }

    public void setDateIcon2(int dateIcon2) {
        this.dateIcon2 = dateIcon2;
    }

    public String getShipTime() {
        return ShipTime;
    }

    public void setShipTime(String shipTime) {
        ShipTime = shipTime;
    }

    public int getDateIcon() {
        return dateIcon;
    }

    public void setDateIcon(int dateIcon) {
        this.dateIcon = dateIcon;
    }

    public String getNgayXuatKho() {
        return ngayXuatKho;
    }

    public void setNgayXuatKho(String ngayXuatKho) {
        this.ngayXuatKho = ngayXuatKho;
    }

    public int getChip() {
        return chip;
    }

    public void setChip(int chip) {
        this.chip = chip;
    }
}
