package com.example.androidproject.core.utils.counter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class CounterModel {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Task<Long> getQuantity(String documentName) {
        DocumentReference docRef = db.collection("counter").document(documentName);

        return docRef.get()
                .continueWith(t -> {
                    if (t.isSuccessful()) {
                        Long quantity = t.getResult().getLong("quantity");
                        return quantity;
                    }
                    return null;
                });
    }

    public Task<Void> updateQuantity(String documentName) {
        DocumentReference docRef = db.collection("counter").document(documentName);
        return docRef.update("quantity", FieldValue.increment(1));
    }

    public Task<String> getNewId(String documentName) {
        DocumentReference docRef = db.collection("counter").document(documentName);

        return docRef.get().continueWith(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Long currentQuantity = document.getLong("quantity");

                    // upd quantity
                    Long nextQuantity = currentQuantity + 1;
                    docRef.update("quantity", nextQuantity);

                    @SuppressLint("DefaultLocale") String formattedId = String.format("%s%06d", documentName, nextQuantity);
                    return formattedId;
                } else {
                    return null;
                }
            }
            return null;
        });
    }
}
