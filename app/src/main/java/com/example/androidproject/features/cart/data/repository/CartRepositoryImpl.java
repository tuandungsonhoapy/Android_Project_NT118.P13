package com.example.androidproject.features.cart.data.repository;

import android.util.Log;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.core.utils.counter.CounterModel;
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
    private CounterModel counterModel = new CounterModel();
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
                boolean productExist = false;

                for (ProductsOnCart product : new ArrayList<>(products)) {
                    if(
                            product.getProductId().equals(productId)
                            && (option == null || product.getProductOptions() == null || product.getProductOptions().equals(option))
                    ) {
                        product.setQuantity(product.getQuantity() + productQuantity);
                        productExist = true;
                        if (product.getQuantity() <= 0) {
                            products.remove(product);
                        }
                        updateCart(cart, products);
                        future.complete(Either.right("Success"));
                        return;
                    }
                }

               if (!productExist) {
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
                                      future.complete(Either.right("Success"));
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
                               counterModel.updateQuantity("cart");
                               future.complete(Either.right("Success"));
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

    @Override
    public CompletableFuture<Either<Failure,String>> getCurrentCartItemCount() {
        CompletableFuture<Either<Failure, String>> future = new CompletableFuture<>();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();

        getCartByUserId(userId).thenAccept(r -> {
            if(r.isRight()) {
                CartModel cart = r.getRight();
                int total = 0;
                for (ProductsOnCart product : cart.getProducts()) {
                    total += product.getQuantity();
                }
                future.complete(Either.right(String.valueOf(total)));
            } else {
                future.complete(Either.left(new Failure("Cart not found")));
            }
        });
        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, CartModel>> getCurrentUserCart() {
        CompletableFuture<Either<Failure, CartModel>> future = new CompletableFuture<>();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        getCartByUserId(userId).thenAccept(r -> {
            if(r.isRight()) {
                future.complete(Either.right(r.getRight()));
            } else {
                future.complete(Either.left(new Failure("Cart not found")));
            }
        });
        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, String>> updateCartQuantity(String productId, int productQuantity) {
        CompletableFuture<Either<Failure, String>> future = new CompletableFuture<>();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        getCartByUserId(userId).thenAccept(r -> {
            if(r.isRight()) {
                CartModel cart = r.getRight();
                List<ProductsOnCart> products = cart.getProducts();
                for (ProductsOnCart product : products) {
                    if (product.getProductId().equals(productId)) {
                        product.setQuantity(product.getQuantity() + productQuantity);
                        if (product.getQuantity() <= 0) {
                            products.remove(product);
                        }
                        break;
                    }
                }
                updateCart(cart, products);
                future.complete(Either.right("Success"));
            } else {
                future.complete(Either.left(new Failure("Cart not found")));
            }
        });
        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, String>> deleteCart(String userId) {
        CompletableFuture<Either<Failure, String>> future = new CompletableFuture<>();
        db.collection("carts")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        db.collection("carts")
                                .document(document.getId())
                                .update("products", new ArrayList<>())
                                .addOnSuccessListener(unused -> future.complete(Either.right("Success")));
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
        cartData.put("id", newCart.prefixCartId(quantity));
        cartData.put("userId", newCart.getUserId());
        cartData.put("products", newCart.getProducts());
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
