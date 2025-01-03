package com.example.androidproject.features.checkout.data.repository;

import android.util.Log;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.auth.data.entity.UserEntity;
import com.example.androidproject.features.cart.data.entity.ProductsOnCart;
import com.example.androidproject.features.checkout.data.model.CheckoutModel;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Calendar;
import java.util.ArrayList;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;

public class CheckoutRepositoryImpl implements CheckoutRepository {
    private final FirebaseFirestore db;
    public CheckoutRepositoryImpl(FirebaseFirestore db) {
        this.db = db;
    }

    @Override
    public CompletableFuture<Either<Failure, String>> addCheckoutRepository(CheckoutModel checkoutModel, long quantity) {
        CompletableFuture<Either<Failure, String>> future = new CompletableFuture<>();
        HashMap<String, Object> checkout = new HashMap<>();

        checkout.put("id", checkoutModel.prefixCheckoutId(quantity));
        checkout.put("userId", checkoutModel.getUserId());
        checkout.put("addressId", checkoutModel.getAddressId());
        checkout.put("note", checkoutModel.getNote());
        checkout.put("status", checkoutModel.getStatus());
        checkout.put("createdAt", checkoutModel.getCreatedAt());
        checkout.put("updatedAt", checkoutModel.getUpdatedAt());
        checkout.put("products", checkoutModel.getProducts());
        checkout.put("fullAddress", checkoutModel.getFullAddress());
        checkout.put("totalPrice", checkoutModel.getTotalPrice());
        checkout.put("oldTotalPrice", checkoutModel.getOldTotalPrice());
        checkout.put("voucherId", checkoutModel.getVoucherId());

        db.collection("checkouts").document(checkoutModel.prefixCheckoutId(quantity)).set(checkout);
        future.complete(Either.right("Success"));
        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, List<CheckoutModel>>> getCheckoutList(String userId) {
        CompletableFuture<Either<Failure, List<CheckoutModel>>> future = new CompletableFuture<>();

        db.collection("checkouts")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    try {
                        List<CheckoutModel> checkouts = new ArrayList<>(); // Khởi tạo danh sách rỗng
                        for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                            CheckoutModel checkout = doc.toObject(CheckoutModel.class); // Chuyển đổi document thành CheckoutModel
                            if (checkout != null) {
                                checkouts.add(checkout); // Thêm vào danh sách
                            }
                        }
                        future.complete(Either.right(checkouts)); // Hoàn thành với kết quả thành công
                    } catch (Exception e) {
                        future.complete(Either.left(new Failure.DatabaseFailure("Error parsing data: " + e.getMessage())));
                    }
                })
                .addOnFailureListener(e -> {
                    future.complete(Either.left(new Failure.DatabaseFailure("Error fetching data: " + e.getMessage())));
                });

        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, List<CheckoutModel>>> getLatestCheckouts(int limit) {
        CompletableFuture<Either<Failure, List<CheckoutModel>>> future = new CompletableFuture<>();

        db.collection("checkouts")
                .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .limit(limit)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    try {
                        List<CheckoutModel> checkouts = new ArrayList<>(); // Khởi tạo danh sách rỗng
                        for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                            CheckoutModel checkout = doc.toObject(CheckoutModel.class); // Chuyển đổi document thành CheckoutModel
                            if (checkout != null) {
                                checkouts.add(checkout); // Thêm vào danh sách
                            }
                        }
                        future.complete(Either.right(checkouts)); // Hoàn thành với kết quả thành công
                    } catch (Exception e) {
                        future.complete(Either.left(new Failure.DatabaseFailure("Error parsing data: " + e.getMessage())));
                    }
                })
                .addOnFailureListener(e -> {
                    future.complete(Either.left(new Failure.DatabaseFailure("Error fetching data: " + e.getMessage())));
                });

        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, Integer>> getNumberCheckoutToday() {
        CompletableFuture<Either<Failure, Integer>> future = new CompletableFuture<>();

        // Lấy thời gian đầu ngày và cuối ngày dưới dạng Firestore Timestamp
        Timestamp startOfDay = getStartOfDayTimestamp();
        Timestamp endOfDay = getEndOfDayTimestamp();

        db.collection("checkouts")
                .whereGreaterThanOrEqualTo("createdAt", startOfDay)
                .whereLessThan("createdAt", endOfDay)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    try {
                        int count = querySnapshot.size(); // Đếm số lượng bản ghi
                        future.complete(Either.right(count));
                    } catch (Exception e) {
                        future.complete(Either.left(new Failure.DatabaseFailure("Error fetching data: " + e.getMessage())));
                    }
                })
                .addOnFailureListener(e -> {
                    future.complete(Either.left(new Failure.DatabaseFailure("Error fetching data: " + e.getMessage())));
                });

        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, List<CheckoutModel>>> getCheckoutListByStatus(String status) {
        CompletableFuture<Either<Failure, List<CheckoutModel>>> future = new CompletableFuture<>();

        db.collection("checkouts")
                .whereEqualTo("status", status)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    try {
                        List<CheckoutModel> checkouts = new ArrayList<>(); // Khởi tạo danh sách rỗng
                        for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                            CheckoutModel checkout = doc.toObject(CheckoutModel.class); // Chuyển đổi document thành CheckoutModel
                            if (checkout != null) {
                                checkouts.add(checkout); // Thêm vào danh sách
                            }
                        }
                        future.complete(Either.right(checkouts)); // Hoàn thành với kết quả thành công
                    } catch (Exception e) {
                        future.complete(Either.left(new Failure.DatabaseFailure("Error parsing data: " + e.getMessage())));
                    }
                })
                .addOnFailureListener(e -> {
                    future.complete(Either.left(new Failure.DatabaseFailure("Error fetching data: " + e.getMessage())));
                });

        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, List<CheckoutModel>>> getCheckoutListByStatusAndUserId(String status, String userId) {
        CompletableFuture<Either<Failure, List<CheckoutModel>>> future = new CompletableFuture<>();

        db.collection("checkouts")
                .whereEqualTo("status", status)
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    try {
                        List<CheckoutModel> checkouts = new ArrayList<>(); // Khởi tạo danh sách rỗng
                        for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                            CheckoutModel checkout = doc.toObject(CheckoutModel.class); // Chuyển đổi document thành CheckoutModel
                            if (checkout != null) {
                                checkouts.add(checkout); // Thêm vào danh sách
                            }
                        }
                        future.complete(Either.right(checkouts)); // Hoàn thành với kết quả thành công
                    } catch (Exception e) {
                        future.complete(Either.left(new Failure.DatabaseFailure("Error parsing data: " + e.getMessage())));
                    }
                })
                .addOnFailureListener(e -> {
                    future.complete(Either.left(new Failure.DatabaseFailure("Error fetching data: " + e.getMessage())));
                });

        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, List<CheckoutModel>>> getCheckoutListByUserId(String userId) {
        CompletableFuture<Either<Failure, List<CheckoutModel>>> future = new CompletableFuture<>();

        db.collection("checkouts")
                .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    try {
                        List<CheckoutModel> checkouts = new ArrayList<>(); // Khởi tạo danh sách rỗng
                        for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                            CheckoutModel checkout = doc.toObject(CheckoutModel.class); // Chuyển đổi document thành CheckoutModel
                            if (checkout != null) {
                                checkouts.add(checkout); // Thêm vào danh sách
                            }
                        }
                        future.complete(Either.right(checkouts)); // Hoàn thành với kết quả thành công
                    } catch (Exception e) {
                        future.complete(Either.left(new Failure.DatabaseFailure("Error parsing data: " + e.getMessage())));
                    }
                })
                .addOnFailureListener(e -> {
                    future.complete(Either.left(new Failure.DatabaseFailure("Error fetching data: " + e.getMessage())));
                });

        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, List<CheckoutModel>>> getCheckoutListByFilter(String field, String filter, String userId) {
        CompletableFuture<Either<Failure, List<CheckoutModel>>> future = new CompletableFuture<>();

        db.collection("checkouts")
                .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .whereEqualTo("userId", userId)
                .whereEqualTo(field, filter)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    try {
                        List<CheckoutModel> checkouts = new ArrayList<>(); // Khởi tạo danh sách rỗng
                        for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                            CheckoutModel checkout = doc.toObject(CheckoutModel.class); // Chuyển đổi document thành CheckoutModel
                            if (checkout != null) {
                                checkouts.add(checkout); // Thêm vào danh sách
                            }
                        }
                        future.complete(Either.right(checkouts)); // Hoàn thành với kết quả thành công
                    } catch (Exception e) {
                        future.complete(Either.left(new Failure.DatabaseFailure("Error parsing data: " + e.getMessage())));
                    }
                })
                .addOnFailureListener(e -> {
                    future.complete(Either.left(new Failure.DatabaseFailure("Error fetching data: " + e.getMessage())));
                });

        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, List<CheckoutModel>>> getCheckoutsThisMonth() {
        CompletableFuture<Either<Failure, List<CheckoutModel>>> future = new CompletableFuture<>();

        // Lấy thời gian đầu tháng và cuối tháng dưới dạng Firestore Timestamp
        Timestamp startOfMonth = getStartOfMonthTimestamp();
        Timestamp endOfMonth = getEndOfMonthTimestamp();

        db.collection("checkouts")
                .whereGreaterThanOrEqualTo("createdAt", startOfMonth)
                .whereLessThan("createdAt", endOfMonth)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    try {
                        List<CheckoutModel> checkouts = new ArrayList<>(); // Khởi tạo danh sách rỗng
                        for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                            CheckoutModel checkout = doc.toObject(CheckoutModel.class); // Chuyển đổi document thành CheckoutModel
                            if (checkout != null) {
                                checkouts.add(checkout); // Thêm vào danh sách
                            }
                        }
                        future.complete(Either.right(checkouts)); // Hoàn thành với kết quả thành công
                    } catch (Exception e) {
                        future.complete(Either.left(new Failure.DatabaseFailure("Error parsing data: " + e.getMessage())));
                    }
                })
                .addOnFailureListener(e -> {
                    future.complete(Either.left(new Failure.DatabaseFailure("Error fetching data: " + e.getMessage())));
                });

        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, List<CheckoutModel>>> getCheckoutsToday() {
        CompletableFuture<Either<Failure, List<CheckoutModel>>> future = new CompletableFuture<>();

        // Lấy thời gian đầu ngày và cuối ngày dưới dạng Firestore Timestamp
        Timestamp startOfDay = getStartOfDayTimestamp();
        Timestamp endOfDay = getEndOfDayTimestamp();

        db.collection("checkouts")
                .whereGreaterThanOrEqualTo("createdAt", startOfDay)
                .whereLessThan("createdAt", endOfDay)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    try {
                        List<CheckoutModel> checkouts = new ArrayList<>(); // Khởi tạo danh sách rỗng
                        for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                            CheckoutModel checkout = doc.toObject(CheckoutModel.class); // Chuyển đổi document thành CheckoutModel
                            if (checkout != null) {
                                checkouts.add(checkout); // Thêm vào danh sách
                            }
                        }
                        future.complete(Either.right(checkouts)); // Hoàn thành với kết quả thành công
                    } catch (Exception e) {
                        future.complete(Either.left(new Failure.DatabaseFailure("Error parsing data: " + e.getMessage())));
                    }
                })
                .addOnFailureListener(e -> {
                    future.complete(Either.left(new Failure.DatabaseFailure("Error fetching data: " + e.getMessage())));
                });

        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, Boolean>> updateStatus(String checkoutId, String status) {
        CompletableFuture<Either<Failure, Boolean>> future = new CompletableFuture<>();

        db.collection("checkouts").document(checkoutId)
                .update("status", status)
                .addOnSuccessListener(aVoid -> {
                    future.complete(Either.right(true));
                })
                .addOnFailureListener(e -> {
                    future.complete(Either.left(new Failure.DatabaseFailure("Error updating data: " + e.getMessage())));
                });

        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, List<CheckoutModel>>> getAllCheckouts() {
        CompletableFuture<Either<Failure, List<CheckoutModel>>> future = new CompletableFuture<>();
        Set<String> userIds = new HashSet<>();
        List<CheckoutModel> checkoutList = new ArrayList<>();

        // Truy vấn collection "checkouts"
        db.collection("checkouts")
                .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(checkoutSnapshots -> {
                    // Thu thập thông tin checkouts và userId
                    for (QueryDocumentSnapshot document : checkoutSnapshots) {
                        CheckoutModel checkout = document.toObject(CheckoutModel.class);
                        checkoutList.add(checkout);
                        if (checkout.getUserId() != null) {
                            userIds.add(checkout.getUserId());
                        }
                    }

                    // Truy vấn collection "users" với các userId đã thu thập
                    db.collection("users")
                            .whereIn(FieldPath.documentId(), new ArrayList<>(userIds))
                            .get()
                            .addOnSuccessListener(userSnapshots -> {
                                Map<String, UserEntity> userMap = new HashMap<>();
                                // Tạo map userId -> UserModel
                                for (QueryDocumentSnapshot userDoc : userSnapshots) {
                                    UserEntity user = userDoc.toObject(UserEntity.class);
                                    userMap.put(userDoc.getId(), user);
                                }

                                for (CheckoutModel checkout : checkoutList) {
                                    UserEntity user = userMap.get(checkout.getUserId());
                                    checkout.setUser(user);
                                }

                                future.complete(Either.right(checkoutList));
                            })
                            .addOnFailureListener(e -> future.complete(Either.left(new Failure.DatabaseFailure("Error fetching users: " + e.getMessage()))));
                })
                .addOnFailureListener(e -> future.complete(Either.left(new Failure.DatabaseFailure("Error fetching checkouts: " + e.getMessage()))));

        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, List<CheckoutModel>>> getALlCheckoutsByStatus(String status) {
        CompletableFuture<Either<Failure, List<CheckoutModel>>> future = new CompletableFuture<>();
        Set<String> userIds = new HashSet<>();
        List<CheckoutModel> checkoutList = new ArrayList<>();

        // Truy vấn collection "checkouts"
        db.collection("checkouts")
                .whereEqualTo("status", status)
                .get()
                .addOnSuccessListener(checkoutSnapshots -> {
                    // Thu thập thông tin checkouts và userId
                    for (QueryDocumentSnapshot document : checkoutSnapshots) {
                        CheckoutModel checkout = document.toObject(CheckoutModel.class);
                        checkoutList.add(checkout);
                        if (checkout.getUserId() != null) {
                            userIds.add(checkout.getUserId());
                        }
                    }

                    // Truy vấn collection "users" với các userId đã thu thập
                    if (userIds.isEmpty()) {
                        future.complete(Either.right(checkoutList));
                        return;
                    }
                    db.collection("users")
                            .whereIn(FieldPath.documentId(), new ArrayList<>(userIds))
                            .get()
                            .addOnSuccessListener(userSnapshots -> {
                                Map<String, UserEntity> userMap = new HashMap<>();
                                // Tạo map userId -> UserModel
                                for (QueryDocumentSnapshot userDoc : userSnapshots) {
                                    UserEntity user = userDoc.toObject(UserEntity.class);
                                    userMap.put(userDoc.getId(), user);
                                }

                                for (CheckoutModel checkout : checkoutList) {
                                    UserEntity user = userMap.get(checkout.getUserId());
                                    checkout.setUser(user);
                                }

                                future.complete(Either.right(checkoutList));
                            })
                            .addOnFailureListener(e -> future.complete(Either.left(new Failure.DatabaseFailure("Error fetching users: " + e.getMessage()))));
                })
                .addOnFailureListener(e -> future.complete(Either.left(new Failure.DatabaseFailure("Error fetching checkouts: " + e.getMessage()))));

        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, CheckoutModel>> getCheckoutById(String checkoutId) {
        CompletableFuture<Either<Failure, CheckoutModel>> future = new CompletableFuture<>();

        db.collection("checkouts").document(checkoutId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        CheckoutModel checkout = documentSnapshot.toObject(CheckoutModel.class);
                        if (checkout != null) {
                            future.complete(Either.right(checkout));
                        } else {
                            future.complete(Either.left(new Failure.DatabaseFailure("Failed to parse CheckoutModel")));
                        }
                    } else {
                        future.complete(Either.left(new Failure.DatabaseFailure("Checkout not found")));
                    }
                })
                .addOnFailureListener(e ->
                        future.complete(Either.left(new Failure.DatabaseFailure("Error fetching data: " + e.getMessage())))
                );

        return future;
    }

    // Hàm lấy Firestore Timestamp đầu tháng (UTC+7)
    private Timestamp getStartOfMonthTimestamp() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC+7")); // Sử dụng múi giờ UTC+7
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTime());
    }

    // Hàm lấy Firestore Timestamp cuối tháng (UTC+7)
    private Timestamp getEndOfMonthTimestamp() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC+7")); // Sử dụng múi giờ UTC+7
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTime());
    }

    // Hàm lấy Firestore Timestamp đầu ngày (UTC+7)
    private Timestamp getStartOfDayTimestamp() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC+7")); // Sử dụng múi giờ UTC+7
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTime());
    }

    // Hàm lấy Firestore Timestamp cuối ngày (UTC+7)
    private Timestamp getEndOfDayTimestamp() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC+7")); // Sử dụng múi giờ UTC+7
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTime());
    }

}
