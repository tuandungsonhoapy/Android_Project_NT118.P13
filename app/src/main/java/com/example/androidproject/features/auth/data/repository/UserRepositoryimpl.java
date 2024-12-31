package com.example.androidproject.features.auth.data.repository;

import android.content.Context;
import android.util.Log;

import com.example.androidproject.core.credential.UserPreferences;
import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.auth.data.entity.UserEntity;
import com.example.androidproject.features.voucher.data.model.VoucherModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UserRepositoryimpl implements UserRepository {
    private final FirebaseFirestore db;
    private final Context ctx;
    private final UserPreferences userPreferences;
    public UserRepositoryimpl(FirebaseFirestore db, Context ctx, UserPreferences userPreferences) {
        this.db = db;
        this.ctx = ctx;
        this.userPreferences = userPreferences;
    }

    @Override
    public CompletableFuture<Either<Failure, String>> addVoucherRepository(String voucherId) {
        CompletableFuture<Either<Failure, String>> future = new CompletableFuture<>();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid();
        if(uid != null) {
            getAllUserVouchers()
                    .thenAccept(r -> {
                        List<String> vouchers = r.getRight().getVouchers();
                        if(vouchers.contains(voucherId)) {
                            Log.d("Voucher", "Voucher đã có");
                            future.complete(Either.left(new Failure("Voucher đã có")));
                        } else {
                            vouchers.add(voucherId);
                            db.collection("users")
                                    .document(uid)
                                    .update("vouchers", vouchers)
                                    .addOnSuccessListener(aVoid -> future.complete(Either.right("Thêm voucher thành công")))
                                    .addOnFailureListener(e -> future.complete(Either.left(new Failure(e.getMessage()))));
                            Log.d("Voucher", "Thêm voucher thành công");
                        }
                    });
        }
        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, UserEntity>> getAllUserVouchers() {
        CompletableFuture<Either<Failure, UserEntity>> future = new CompletableFuture<>();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid();
        if (uid != null) {
            db.collection("users")
                    .document(uid)
                    .get()
                    .addOnSuccessListener(r -> {
                        UserEntity userEntity = r.toObject(UserEntity.class);
                        future.complete(Either.right(userEntity));
                    });
        } else {
            future.complete(Either.left(new Failure("User not found")));
        }
        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, String>> deleteUserVoucher(String voucherId) {
        CompletableFuture<Either<Failure, String>> future = new CompletableFuture<>();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid();
        if(uid != null) {
            getAllUserVouchers()
                    .thenAccept(r -> {
                        List<String> vouchers = r.getRight().getVouchers();
                        if(vouchers.contains(voucherId)) {
                            vouchers.remove(voucherId);
                            db.collection("users")
                                    .document(uid)
                                    .update("vouchers", vouchers)
                                    .addOnSuccessListener(aVoid -> future.complete(Either.right("Xóa voucher thành công")))
                                    .addOnFailureListener(e -> future.complete(Either.left(new Failure(e.getMessage()))));
                        } else {
                            future.complete(Either.left(new Failure("Voucher không tồn tại")));
                        }
                    });
        }
        return future;
    }
}
