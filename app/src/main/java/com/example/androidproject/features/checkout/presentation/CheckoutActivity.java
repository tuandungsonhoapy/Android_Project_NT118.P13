package com.example.androidproject.features.checkout.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.MainActivity;
import com.example.androidproject.R;
import com.example.androidproject.core.credential.UserPreferences;
import com.example.androidproject.core.utils.MoneyFomat;
import com.example.androidproject.core.utils.counter.CounterModel;
import com.example.androidproject.features.address.data.model.AddressModel;
import com.example.androidproject.features.address.usecase.AddressUsecase;
import com.example.androidproject.features.cart.data.entity.ProductsOnCart;
import com.example.androidproject.features.cart.usecase.CartUseCase;
import com.example.androidproject.features.checkout.data.model.CheckoutModel;
import com.example.androidproject.features.checkout.usecase.CheckoutUseCase;
import com.example.androidproject.features.product.usecase.ProductUseCase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.stream.Collectors;

public class CheckoutActivity extends AppCompatActivity {
    private LinearLayout paymentSuccessLayout, llTotalPrice;
    private RecyclerView rvCheckoutItem;
    private Button btnPayment, btnContinueShopping;
    private TextView tvTotalPrice;
    private TextView tvUserAddress, tvUserInformation;
    private EditText etNote;
    private LinearLayout llNote,llUserAddress;
    private CheckoutUseCase checkoutUseCase = new CheckoutUseCase();
    private CartUseCase cartUseCase = new CartUseCase();
    private AddressUsecase addressUsecase = new AddressUsecase();
    private CounterModel counterModel = new CounterModel();
    private ProductUseCase productUseCase = new ProductUseCase();
    private long checkoutQuantity;
    private String addressId;
    private String fullAddress;
    private List<ProductsOnCart> productsOnCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        initView();

        cartUseCase.getCurrentUserCart()
                        .thenAccept(r -> {
                            if(r.isRight()) {
                                productsOnCart = r.getRight().getProducts();
                                rvCheckoutItem.setAdapter(new ListCheckoutItemAdapter(r.getRight().getProducts(), this));
                                rvCheckoutItem.setLayoutManager(new LinearLayoutManager(this));
                                tvTotalPrice.setText(MoneyFomat.format(r.getRight().getTotal()));
                            }
                        });

        setupUserAddress();

        tvUserAddress.setOnClickListener(v -> {
            addressDialog();
        });

        btnPayment.setOnClickListener(v -> {
            if (!tvUserAddress.getText().toString().isEmpty()) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String userId = auth.getCurrentUser().getUid();
                counterModel.getQuantity("checkout").addOnSuccessListener(quantity -> {
                    checkoutQuantity = quantity;

                    CheckoutModel checkoutModel = new CheckoutModel(
                            userId,
                            addressId,
                            etNote.getText().toString(),
                            productsOnCart,
                            fullAddress,
                            MoneyFomat.parseMoney(tvTotalPrice.getText().toString())
                    );

                    checkoutUseCase.addCheckout(checkoutModel, checkoutQuantity)
                            .thenAccept(r -> {
                                if(r.isRight()) {
                                    cartUseCase.deleteCart(userId).thenAccept(r1 -> {
                                        Log.d("CheckoutActivity", "delete cart: " + r1);
                                        if (r1.isRight()) {
                                            productUseCase.updateProductQuantity(productsOnCart)
                                                            .thenAccept(r2 -> {
                                                                if(r2.isRight()) {
                                                                    Log.d("CheckoutActivity", "update product quantity: " + r2);
                                                                    counterModel.updateQuantity("checkout");
                                                                    paymentSuccessLayout.setVisibility(View.VISIBLE);
                                                                    btnPayment.setVisibility(View.GONE);
                                                                    rvCheckoutItem.setVisibility(View.GONE);
                                                                    llTotalPrice.setVisibility(View.GONE);
                                                                    btnContinueShopping.setVisibility(View.VISIBLE);
                                                                    llUserAddress.setVisibility(View.GONE);
                                                                    tvUserInformation.setVisibility(View.GONE);
                                                                    llNote.setVisibility(View.GONE);
                                                                    getIntent().addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                                                    Toast.makeText(this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                        }
                                    });
                                } else {
                                    Toast.makeText(this, "Đặt hàng thất bại", Toast.LENGTH_SHORT).show();
                                }
                            });
                });
            } else {
                Toast.makeText(this, "Vui lòng thêm địa chỉ giao hàng", Toast.LENGTH_SHORT).show();
            }
        });

        btnContinueShopping.setOnClickListener(v -> {
            setResult(RESULT_OK);
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    public void updateUI() {
        cartUseCase.getCurrentUserCart()
                        .thenAccept(r -> {
                            if(r.isRight()) {
                                productsOnCart = r.getRight().getProducts();
                                rvCheckoutItem.setAdapter(new ListCheckoutItemAdapter(r.getRight().getProducts(), this));
                                rvCheckoutItem.setLayoutManager(new LinearLayoutManager(this));
                                tvTotalPrice.setText(MoneyFomat.format(r.getRight().getTotal()));
                            }
                        });
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        paymentSuccessLayout = findViewById(R.id.ll_payment_success);
        rvCheckoutItem = findViewById(R.id.rvCheckoutItem);
        btnPayment = findViewById(R.id.btnPayment);
        btnContinueShopping = findViewById(R.id.btn_continue_shopping);
        llTotalPrice = findViewById(R.id.llTotalPrice);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvUserAddress = findViewById(R.id.tvUserAddress);
        tvUserInformation = findViewById(R.id.tvUserInformation);
        etNote = findViewById(R.id.etNote);
        llNote = findViewById(R.id.llNote);
        llUserAddress = findViewById(R.id.llUserAddress);
    }

    private void setupUserAddress() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userName = auth.getCurrentUser().getDisplayName();
        String userEmail = auth.getCurrentUser().getEmail();

        tvUserInformation.setText(userName + " - " + userEmail);
        addressUsecase.getDefaultAddress()
                .thenAccept(r -> {
                    if(r.isRight()) {
                        String address = r.getRight().getFullAddress();
                        addressId = r.getRight().getId();
                        fullAddress = address;
                        tvUserAddress.setText(address);
                    }
                });
    }

    private void addressDialog() {
        addressUsecase.getAddresses()
                .thenAccept(r -> {
                   if(r.isRight()) {
                       List<AddressModel> addressModels = r.getRight();
                       String[] addressList = addressModels.stream()
                               .map(AddressModel::getFullAddress)
                               .toArray(String[]::new);

                       AlertDialog.Builder builder = new AlertDialog.Builder(this)
                               .setTitle("Chọn địa chỉ")
                               .setItems(addressList, (dialog, which) -> {
                                      AddressModel addressModel = addressModels.get(which);
                                      tvUserAddress.setText(addressModel.getFullAddress());
                                      addressId = addressModel.getId();
                                      fullAddress = addressModel.getFullAddress();
                               })
                               .setNegativeButton("Hủy", (dialog, which) -> {
                                   dialog.dismiss();
                               });

                          builder.show();
                   }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}