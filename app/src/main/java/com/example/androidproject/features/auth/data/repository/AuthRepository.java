package com.example.androidproject.features.auth.data.repository;

import com.example.androidproject.core.utils.counter.CounterModel;
import com.example.androidproject.features.auth.data.entity.UserEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

public class AuthRepository {
    private final CounterModel counterModel = new CounterModel();

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    public AuthRepository() {
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public CompletableFuture<FirebaseUser> login(String email, String password) {
        CompletableFuture<FirebaseUser> future = new CompletableFuture<>();

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        future.complete(user);
                    } else {
                        future.completeExceptionally(task.getException());
                    }
                });

        return future;
    }

    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
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


    public CompletableFuture<FirebaseUser> addNewAccount(String adminA, String adminP, String email, String password) {
        CompletableFuture<FirebaseUser> future = new CompletableFuture<>();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser newUser = auth.getCurrentUser();

                        auth.signOut();

                        auth.signInWithEmailAndPassword(adminA, adminP)
                                .addOnCompleteListener(loginTask -> {
                                    if (loginTask.isSuccessful()) {
                                        future.complete(newUser);
                                    } else {
                                        future.completeExceptionally(loginTask.getException());
                                    }
                                });
                    } else {
                        future.completeExceptionally(task.getException());
                    }
                });

        return future;
    }

    public CompletableFuture<Void> saveUserToFirestore(String uid, UserEntity userEntity) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        counterModel.getNewId("user").addOnSuccessListener(documentId -> {

            userEntity.setId(documentId);
            userEntity.setCreatedAt(new Date());
            userEntity.setUpdatedAt(new Date());

            DocumentReference userRef = firestore.collection("users").document(uid);
            userRef.set(userEntity)
                    .addOnSuccessListener(aVoid -> future.complete(null))
                    .addOnFailureListener(e -> future.completeExceptionally(e));
        });

        return future;
    }
}
