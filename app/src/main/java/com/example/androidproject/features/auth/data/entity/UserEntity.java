package com.example.androidproject.features.auth.data.entity;

public class UserEntity {
    private String uid;
    private Integer role;
    private Integer tier;
    private Long totalSpent;
    private String addressId;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String phone;

    public UserEntity() {
    }


    public UserEntity(String uid, Integer role, Integer tier, Long totalSpent, String addressId, String firstName, String lastName, String gender, String email, String phone) {
        this.uid = uid;
        this.role = role;
        this.tier = tier;
        this.totalSpent = totalSpent;
        this.addressId = addressId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getTier() {
        return tier;
    }

    public void setTier(Integer tier) {
        this.tier = tier;
    }

    public Long getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(Long totalSpent) {
        this.totalSpent = totalSpent;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
