package com.example.androidproject.core.credential.data.entity;

public class AccountEntity {
    private boolean isSaveAccountEnable;
    private String account;
    private String password;

    public AccountEntity(boolean isSaveAccountEnable, String account, String password) {
        this.isSaveAccountEnable = isSaveAccountEnable;
        this.account = account;
        this.password = password;
    }

    public boolean isSaveAccountEnable() {
        return isSaveAccountEnable;
    }

    public void setSaveAccountEnable(boolean saveAccountEnable) {
        isSaveAccountEnable = saveAccountEnable;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
