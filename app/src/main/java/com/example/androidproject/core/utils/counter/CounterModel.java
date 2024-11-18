package com.example.androidproject.core.utils.counter;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
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

    public Task<Void> updateQuantity(String documentName, int delta) {
        DocumentReference docRef = db.collection("counter").document(documentName);
        return docRef.update("quantity", FieldValue.increment(delta));
    }
}
