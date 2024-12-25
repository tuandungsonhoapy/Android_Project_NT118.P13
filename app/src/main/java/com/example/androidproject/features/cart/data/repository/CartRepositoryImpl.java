package com.example.androidproject.features.cart.data.repository;

import android.util.Log;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.cart.data.entity.ProductsOnCart;
import com.example.androidproject.features.cart.data.model.CartModel;
import com.example.androidproject.features.product.data.entity.ProductOption;
import com.example.androidproject.features.product.data.model.ProductModelFB;
import com.example.androidproject.features.product.data.repository.ProductRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CartRepositoryImpl implements CartRepository{
    private final FirebaseFirestore db;
    private final ProductRepository productRepository;
    public CartRepositoryImpl(FirebaseFirestore db, ProductRepository productRepository) {
        this.db = db;
        this.productRepository = productRepository;
    }
    private String userId;

    @Override
    public CompletableFuture<Either<Failure,String>> addProductToCart(
                                                            String productId,
                                                            int productQuantity,
                                                            ProductOption option,
                                                            long quantity) {
        CompletableFuture<Either<Failure, String>> future = new CompletableFuture<>();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        getCartByUserId(userId). thenAccept(r -> {
           if(r.isRight()) {
                CartModel cart = r.getRight();
                List<ProductsOnCart> products = cart.getProducts();

                boolean isExist = false;
                for (ProductsOnCart product : products) {
                    if (product.getProductId().equals(productId)) {
                        product.setQuantity(product.getQuantity() + (int) productQuantity);
                        if (product.getQuantity() <= 0) {
                            products.remove(product);
                        }
                        isExist = true;
                        break;
                    }
                }

               if (!isExist) {
                   productRepository.getDetailProductById(productId)
                           .thenAccept(productResult -> {
                               if (productResult.isRight()) {
                                   ProductsOnCart newProduct = createProductOnCart(
                                           productResult.getRight(),
                                           productQuantity,
                                           option
                                   );
                                   products.add(newProduct);
                                   updateCart(cart, products);
                               } else {
                                   future.complete(Either.left(new Failure("Product not found")));
                               }
                           });
               }
           } else {
               productRepository.getDetailProductById(productId)
                       .thenAccept(productResult -> {
                           if (productResult.isRight()) {
                               createCart(
                                       userId,
                                       productResult.getRight(),
                                       productQuantity,
                                       option,
                                       quantity
                               );
                           } else {
                               future.complete(Either.left(new Failure("Product not found")));
                           }
                       });
           }
        });
        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, CartModel>> getCartByUserId(String userId) {
        CompletableFuture<Either<Failure, CartModel>> future = new CompletableFuture<>();
        db.collection("carts")
                .where(Filter.equalTo("userId", userId))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                        if(!documents.isEmpty()) {
                            CartModel cart = documents.get(0).toObject(CartModel.class);
                            future.complete(Either.right(cart));
                        } else {
                            future.complete(Either.left(new Failure("Cart not found")));
                        }
                    } else {
                        future.complete(Either.left(new Failure("Cart not found")));
                    }
                });
        return future;
    }

    private void createCart(
                        String userId,
                        ProductModelFB product,
                        int productQuantity,
                        ProductOption option,
                        long quantity
                    )  {
        CartModel newCart = new CartModel();
        ProductsOnCart newProduct = createProductOnCart(
                product,
                productQuantity,
                option
        );

        List<ProductsOnCart> products = new ArrayList<>();
        products.add(newProduct);

        newCart.setProducts(products);
        newCart.setUserId(userId);
        newCart.setTotal(newCart.calculateTotal());
        newCart.setId(newCart.prefixCartId(quantity));

        HashMap<String, Object> cartData = new HashMap<>();
        cartData.put("id", newCart.getId());
        cartData.put("userId", newCart.getUserId());
        cartData.put("products", Arrays.asList(newCart.getProducts()));
        cartData.put("total", newCart.getTotal());

        db.collection("carts").document(newCart.getId()).set(cartData);
    }

    private void updateCart(CartModel cartModel, List<ProductsOnCart> products) {
        cartModel.setProducts(products);
        cartModel.setTotal(cartModel.calculateTotal());
        db.collection("carts").document(cartModel.getId()).set(cartModel);
    }

    private ProductsOnCart createProductOnCart(ProductModelFB product, int quantity, ProductOption option) {
        ProductsOnCart newProduct = new ProductsOnCart();
        newProduct.setProductId(product.getId());
        newProduct.setProductName(product.getName());
        newProduct.setProductImage(product.getImages().get(0));
        newProduct.setProductPrice(product.getPrice());
        newProduct.setProductOptions(option);
        newProduct.setQuantity(quantity);
        return newProduct;
    }
}
