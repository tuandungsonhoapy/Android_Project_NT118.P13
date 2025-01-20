package com.example.androidproject.features.auth.data.entity;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.List;

public class UserEntity {
    private String id;
    private Integer role;
    private Integer tier;
    private Long totalSpent;
    private String addressId;
    private String fullAddress;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String phone;

    private List<String> vouchers;
    private List<String> wishlist;

    @ServerTimestamp
    private Date createdAt;
    @ServerTimestamp
    private Date updatedAt;

    public UserEntity() {
    }

    public UserEntity(String id, Integer role, Integer tier, Long totalSpent, String addressId, String fullAddress,
                      String firstName, String lastName, String gender, String email, String phone,
                      List<String> vouchers, List<String> wishlist) {
        this.id = id;
        this.role = role;
        this.tier = tier;
        this.totalSpent = totalSpent;
        this.addressId = addressId;
        this.fullAddress = fullAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.vouchers = vouchers;
        this.wishlist = wishlist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
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

    public List<String> getVouchers() {
        return vouchers;
    }

    public void setVouchers(List<String> vouchers) {
        this.vouchers = vouchers;
    }

    public List<String> getWishlist() {
        return wishlist;
    }

    public void setWishlist(List<String> wishlist) {
        this.wishlist = wishlist;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
