package com.example.androidproject.features.wishlist.data.entity;

public class WishlistEntity {
    private int productImage;         // Tài nguyên hình ảnh của sản phẩm (drawable resource ID)
    private String productName;        // Tên sản phẩm
    private String productBrand;       // Thương hiệu sản phẩm
    private String productPrice;       // Giá sản phẩm (dưới dạng chuỗi)
    private int icon;                  // Tài nguyên hình ảnh biểu tượng (nếu có, drawable resource ID)
    private String saleTag;            // Nhãn giảm giá hoặc khuyến mãi (dưới dạng chuỗi)
    private int addToCartButton;       // Tài nguyên cho nút "Thêm vào giỏ hàng" (drawable resource ID)
    private int wishlistAddButton;     // Tài nguyên cho nút "Thêm vào danh sách yêu thích" (drawable resource ID)

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getSaleTag() {
        return saleTag;
    }

    public void setSaleTag(String saleTag) {
        this.saleTag = saleTag;
    }

    public int getAddToCartButton() {
        return addToCartButton;
    }

    public void setAddToCartButton(int addToCartButton) {
        this.addToCartButton = addToCartButton;
    }

    public int getWishlistAddButton() {
        return wishlistAddButton;
    }

    public void setWishlistAddButton(int wishlistAddButton) {
        this.wishlistAddButton = wishlistAddButton;
    }

    public WishlistEntity(int productImage, String productName, String productBrand, String productPrice, int icon, String saleTag, int addToCartButton, int wishlistAddButton) {
        this.productImage = productImage;
        this.productName = productName;
        this.productBrand = productBrand;
        this.productPrice = productPrice;
        this.icon = icon;
        this.saleTag = saleTag;
        this.addToCartButton = addToCartButton;
        this.wishlistAddButton = wishlistAddButton;
    }
}
