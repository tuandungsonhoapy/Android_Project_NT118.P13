package com.example.androidproject.features.setting.data.repository;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.auth.data.entity.UserEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ProfileRepository {

    private final FirebaseAuth auth;
    private final FirebaseFirestore firebaseFirestore;

    public ProfileRepository() {
        this.auth = FirebaseAuth.getInstance();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public CompletableFuture<Either<Failure, String>> updateUserProfile(UserEntity user) {
        CompletableFuture<Either<Failure, String>> future = new CompletableFuture<>();
        String docId = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        firebaseFirestore.collection("users").document(docId)
                .update(
                        "firstName", user.getFirstName(),
                        "lastName", user.getLastName(),
                        "gender", user.getGender(),
                        "email", user.getEmail(),
                        "phone", user.getPhone(),
                        "updatedAt", new Date()
                ).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        future.complete(Either.right("Cập nhật thành công"));
                    } else {
                        future.complete(Either.left(new Failure("Cập nhật thất bại")));
                    }
                });

        return future;
    }
}
