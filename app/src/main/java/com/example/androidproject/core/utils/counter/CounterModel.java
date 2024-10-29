package com.example.androidproject.core.utils.counter;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CounterModel {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public interface QuantityCallback {
        void onQuantityReceived(Long quantity);
        void onError(Exception e);
    }

    public void getQuantity(String documentName, QuantityCallback callback) {
        DocumentReference docRef = db.collection("counter").document(documentName);

        docRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Long quantity = documentSnapshot.getLong("quantity");
                        if (quantity != null) {
                            callback.onQuantityReceived(quantity);
                        } else {
                            Log.d("Firestore", "Field 'quantity' không tồn tại trong document: " + documentName);
                            callback.onQuantityReceived(null);
                        }
                    } else {
                        Log.d("Firestore", "Document không tồn tại: " + documentName);
                        callback.onQuantityReceived(null);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Lỗi khi lấy document: " + documentName, e);
                    callback.onError(e);
                });
    }

    public void updateQuantity(String documentName, int delta) {
        DocumentReference docRef = db.collection("counter").document(documentName);

        db.runTransaction(transaction -> {
                    Long currentQuantity = transaction.get(docRef).getLong("quantity");
                    if (currentQuantity != null) {
                        long newQuantity = currentQuantity + delta;
                        transaction.update(docRef, "quantity", newQuantity);
                    } else {
                        Log.d("Firestore", "Field 'quantity' không tồn tại trong document: " + documentName);
                    }
                    return null;
                }).addOnSuccessListener(aVoid -> Log.d("Firestore", "Cập nhật quantity thành công cho document: " + documentName))
                .addOnFailureListener(e -> Log.e("Firestore", "Lỗi khi cập nhật quantity cho document: " + documentName, e));
    }
}
