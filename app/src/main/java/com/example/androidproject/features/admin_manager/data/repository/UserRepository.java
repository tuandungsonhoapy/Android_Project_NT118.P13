package com.example.androidproject.features.admin_manager.data.repository;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.auth.data.entity.UserEntity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UserRepository {
    private final FirebaseFirestore firebaseFirestore;

    public UserRepository() {
        this.firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public CompletableFuture<Either<Failure, List<UserEntity>>> getAllUsers() {
        CompletableFuture<Either<Failure, List<UserEntity>>> future = new CompletableFuture<>();

        firebaseFirestore.collection("users")
                .orderBy("id")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<UserEntity> users = new ArrayList<>();
                        task.getResult().forEach(document -> {
                            UserEntity user = document.toObject(UserEntity.class);
                            users.add(user);
                        });
                        future.complete(Either.right(users));
                    } else {
                        future.complete(Either.left(new Failure("Failed to get users")));
                    }
                });

        return future;
    }

    public CompletableFuture<Either<Failure, List<UserEntity>>> getAllAdmins() {
        CompletableFuture<Either<Failure, List<UserEntity>>> future = new CompletableFuture<>();

        firebaseFirestore.collection("users")
                .orderBy("id")
                .whereEqualTo("role", 0)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<UserEntity> admins = new ArrayList<>();
                        task.getResult().forEach(document -> {
                            UserEntity user = document.toObject(UserEntity.class);
                            admins.add(user);
                        });
                        future.complete(Either.right(admins));
                    } else {
                        future.complete(Either.left(new Failure("Failed to get users")));
                    }
                });

        return future;
    }

    public CompletableFuture<Either<Failure, String>> updateUserById(UserEntity user) {
        CompletableFuture<Either<Failure, String>> future = new CompletableFuture<>();

        String userId = user.getId();

        firebaseFirestore.collection("users")
                .whereEqualTo("id", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (!task.getResult().isEmpty()) {
                            DocumentSnapshot document = task.getResult().getDocuments().get(0);

                            document.getReference()
                                    .update(
                                            "role", user.getRole(),
                                            "firstName", user.getFirstName(),
                                            "lastName", user.getLastName(),
                                            "gender", user.getGender(),
                                            "email", user.getEmail(),
                                            "phone", user.getPhone()
                                    )
                                    .addOnCompleteListener(updateTask -> {
                                        if (updateTask.isSuccessful()) {
                                            future.complete(Either.right("update succeeded"));
                                        } else {
                                            future.complete(Either.left(new Failure("update failed")));
                                        }
                                    });
                        } else {
                            future.complete(Either.left(new Failure("Not found" + userId)));
                        }
                    } else {
                        future.complete(Either.left(new Failure("Failed to find user")));
                    }
                });

        return future;
    }

    public CompletableFuture<Either<Failure, List<UserEntity>>> searchUser(String searchText) {
        CompletableFuture<Either<Failure, List<UserEntity>>> future = new CompletableFuture<>();

        firebaseFirestore.collection("users")
                .orderBy("id")
                .startAt(searchText)
                .endAt(searchText + "\uf8ff")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<UserEntity> users = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            UserEntity user = document.toObject(UserEntity.class);

                            if (user.getFirstName().toLowerCase().contains(searchText.toLowerCase()) ||
                                    user.getLastName().toLowerCase().contains(searchText.toLowerCase()) ||
                                    user.getId().toLowerCase().contains(searchText.toLowerCase()) ||
                                    user.getEmail().toLowerCase().contains(searchText.toLowerCase()) ||
                                    user.getPhone().toLowerCase().contains(searchText.toLowerCase())
                            ) {
                                users.add(user);
                            }
                        }
                        future.complete(Either.right(users));
                    } else {
                        future.complete(Either.left(new Failure("Failed to find users")));
                    }
                });

        return future;
    }

    public CompletableFuture<Either<Failure, String>> getDocId(String id) {
        CompletableFuture<Either<Failure, String>> future = new CompletableFuture<>();

        firebaseFirestore.collection("users")
                .whereEqualTo("id", id)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (!task.getResult().isEmpty()) {
                            DocumentSnapshot document = task.getResult().getDocuments().get(0);
                            String docId = document.getId();
                            future.complete(Either.right(docId));
                        } else {
                            future.complete(Either.left(new Failure("Document not found with id: " + id)));
                        }
                    } else {
                        future.complete(Either.left(new Failure("Failed to find document")));
                    }
                });

        return future;
    }

    public CompletableFuture<Either<Failure, UserEntity>> getUserById(String id) {
        CompletableFuture<Either<Failure, UserEntity>> future = new CompletableFuture<>();

        firebaseFirestore.collection("users")
                .whereEqualTo("id", id)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (!task.getResult().isEmpty()) {
                            DocumentSnapshot document = task.getResult().getDocuments().get(0);
                            UserEntity user = document.toObject(UserEntity.class);
                            future.complete(Either.right(user));
                        } else {
                            future.complete(Either.left(new Failure("User not found with id: " + id)));
                        }
                    } else {
                        future.complete(Either.left(new Failure("Failed to find document")));
                    }
                });

        return future;
    }
}
