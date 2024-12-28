package com.example.androidproject.features.auth.data.repository;

import com.example.androidproject.core.utils.counter.CounterModel;
import com.example.androidproject.features.auth.data.entity.UserEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.CompletableFuture;

public class AuthRepository {
    private final CounterModel counterModel = new CounterModel();

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    public AuthRepository() {
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public CompletableFuture<FirebaseUser> register(String email, String password) {
        CompletableFuture<FirebaseUser> future = new CompletableFuture<>();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        future.complete(firebaseUser);
                    } else {
                        future.completeExceptionally(task.getException());
                    }
                });

        return future;
    }

    public CompletableFuture<Void> saveUserToFirestore(UserEntity userEntity) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        counterModel.getNewId("user").addOnSuccessListener(documentId -> {
            DocumentReference userRef = firestore.collection("users").document(documentId);
            userRef.set(userEntity)
                    .addOnSuccessListener(aVoid -> future.complete(null))
                    .addOnFailureListener(e -> future.completeExceptionally(e));
        });

        return future;
    }
}
