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
import com.example.androidproject.features.auth.usecase.UserUseCase;
import com.example.androidproject.features.cart.data.entity.ProductsOnCart;
import com.example.androidproject.features.cart.usecase.CartUseCase;
import com.example.androidproject.features.checkout.data.model.CheckoutModel;
import com.example.androidproject.features.checkout.usecase.CheckoutUseCase;
import com.example.androidproject.features.product.usecase.ProductUseCase;
import com.example.androidproject.features.voucher.data.model.VoucherModel;
import com.example.androidproject.features.voucher.usecase.VoucherUseCase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.stream.Collectors;

public class CheckoutActivity extends AppCompatActivity {
    private LinearLayout paymentSuccessLayout, llTotalPrice, llApplyDiscount, llDecreasePrice, llNewTotalPrice ;
    private RecyclerView rvCheckoutItem;
    private Button btnPayment, btnContinueShopping, btnApplyDiscount;
    private TextView tvTotalPrice, tvDiscount, tvNewTotalPrice, tvDecreasePrice;
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
    private List<String> voucherIds;
    private String selectedVoucherId = null;
    private UserUseCase userUseCase;
    private VoucherUseCase voucherUseCase = new VoucherUseCase();
    private double totalPrice;
    private double totalPriceWithoutVoucher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        userUseCase = new UserUseCase(this);

        initView();
        updateUI();
        setupUserAddress();

        tvUserAddress.setOnClickListener(v -> {
            addressDialog();
        });

        getUserVoucher();
        btnApplyDiscount.setOnClickListener(v -> {
            voucherDialog();
        });

        btnPayment.setOnClickListener(v -> {
            if (!tvUserAddress.getText().toString().isEmpty()) {
                makeOrder();
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
                                tvNewTotalPrice.setText(MoneyFomat.format(r.getRight().getTotal()) + "đ");
                                totalPriceWithoutVoucher = r.getRight().getTotal();
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
        llApplyDiscount = findViewById(R.id.llApplyDiscount);
        llDecreasePrice = findViewById(R.id.llDecreasePrice);
        llNewTotalPrice = findViewById(R.id.llNewTotalPrice);
        tvDiscount = findViewById(R.id.tvDiscount);
        tvNewTotalPrice = findViewById(R.id.tvNewTotalPrice);
        tvDecreasePrice = findViewById(R.id.tvDecreasePrice);
        btnApplyDiscount = findViewById(R.id.btnApplyDiscount);

    }

    private void makeOrder() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();
        counterModel.getQuantity("checkout").addOnSuccessListener(quantity -> {
            checkoutQuantity = quantity;
            totalPrice = MoneyFomat.parseMoney(tvNewTotalPrice.getText().toString().replace("đ", ""));

            CheckoutModel checkoutModel = new CheckoutModel(
                    userId,
                    addressId,
                    etNote.getText().toString(),
                    productsOnCart,
                    fullAddress,
                    totalPrice,
                    selectedVoucherId,
                    totalPriceWithoutVoucher
            );

            checkoutUseCase.addCheckout(checkoutModel, checkoutQuantity)
                    .thenAccept(r -> {
                        if(r.isRight()) {
                            cartUseCase.deleteCart(userId).thenAccept(r1 -> {
                                if (r1.isRight()) {
                                    productUseCase.updateProductQuantity(productsOnCart)
                                            .thenAccept(r2 -> {
                                                if(r2.isRight()) {
                                                    counterModel.updateQuantity("checkout");
                                                    paymentSuccessLayout.setVisibility(View.VISIBLE);
                                                    btnPayment.setVisibility(View.GONE);
                                                    rvCheckoutItem.setVisibility(View.GONE);
                                                    llNewTotalPrice.setVisibility(View.GONE);
                                                    btnContinueShopping.setVisibility(View.VISIBLE);
                                                    llUserAddress.setVisibility(View.GONE);
                                                    tvUserInformation.setVisibility(View.GONE);
                                                    llNote.setVisibility(View.GONE);
                                                    llTotalPrice.setVisibility(View.GONE);
                                                    llApplyDiscount.setVisibility(View.GONE);
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

    private void voucherDialog() {
        String[] voucherList = voucherIds.stream()
                .toArray(String[]::new);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Chọn voucher")
                .setItems(voucherList, (dialog, which) -> {
                    selectedVoucherId = voucherIds.get(which);
                    fetchDetailVoucher();
                })
                .setNegativeButton("Hủy", (dialog, which) -> {
                    dialog.dismiss();
                });

        builder.show();
    }

    private void fetchDetailVoucher() {
        voucherUseCase.getVoucherById(selectedVoucherId)
                .thenAccept(r -> {
                    if (r.isRight()) {
                        VoucherModel voucherModel = r.getRight();
                        if(totalPriceWithoutVoucher < voucherModel.getMinimalTotal()) {
                            selectedVoucherId = null;
                            Toast.makeText(this, "Yêu cầu đơn hàng tối thiểu " + voucherModel.getMinimalTotal() + "đ", Toast.LENGTH_SHORT).show();
                        } else {
                            llTotalPrice.setVisibility(View.VISIBLE);
                            llDecreasePrice.setVisibility(View.VISIBLE);
                            llNewTotalPrice.setVisibility(View.VISIBLE);
                            if(voucherModel.getType().equals("percent")) {
                                tvDiscount.setText(voucherModel.getId());
                                double discount = MoneyFomat.parseMoney(tvTotalPrice.getText().toString()) * voucherModel.getValue() / 100;
                                tvDecreasePrice.setText(voucherModel.getValue() + "%");
                                double newTotalPrice = MoneyFomat.parseMoney(tvTotalPrice.getText().toString()) - discount;
                                tvNewTotalPrice.setText(MoneyFomat.format(newTotalPrice) + "đ");
                            } else if(voucherModel.getType().equals("minus")) {
                                tvDiscount.setText(voucherModel.getId());
                                tvDecreasePrice.setText(MoneyFomat.format(voucherModel.getValue()) + "đ");
                                double newTotalPrice = MoneyFomat.parseMoney(tvTotalPrice.getText().toString()) - voucherModel.getValue();
                                tvNewTotalPrice.setText(MoneyFomat.format(newTotalPrice) + "đ");
                            }
                        }
                    }
                });
    }

    private void getUserVoucher() {
        userUseCase.getAllUserVouchers()
                .thenAccept(r -> {
                    if (r.isRight()) {
                        voucherIds = r.getRight().getVouchers();
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