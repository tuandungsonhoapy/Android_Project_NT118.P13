package com.example.androidproject.features.setting.data.types;

import java.util.List;

public class AddressWardResponseData {
    private int error;
    private String error_text;
    private String data_name;
    private List<AddressWardData> data;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getErrorText() {
        return error_text;
    }

    public void setErrorText(String error_text) {
        this.error_text = error_text;
    }

    public String getDataName() {
        return data_name;
    }

    public void setDataName(String data_name) {
        this.data_name = data_name;
    }

    public List<AddressWardData> getData() {
        return data;
    }

    public void setData(List<AddressWardData> data) {
        this.data = data;
    }
}
