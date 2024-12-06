package com.example.androidproject.features.product.usecase;

import com.example.androidproject.R;
import com.example.androidproject.features.brand.data.model.BrandModel;
import com.example.androidproject.features.product.data.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class ProductUseCase {
    public List<ProductModel> getProductsList() {
        List<ProductModel> productList = new ArrayList<>();
        productList.add(new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel( "Lenovo", "",""), true));
        productList.add(new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel("Lenovo","",""),false));
        productList.add(new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel("Lenovo", "",""),false));
        productList.add(new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel("Lenovo", "",""),false));
        productList.add(new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel("Lenovo", "",""),false));
        productList.add(new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel( "Lenovo", "",""),false));
        productList.add(new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel( "Lenovo", "",""),false));
        productList.add(new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel( "Lenovo", "",""),false));

        return productList;

    }
}
