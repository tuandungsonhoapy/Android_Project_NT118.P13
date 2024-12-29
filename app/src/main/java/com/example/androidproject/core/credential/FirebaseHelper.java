package com.example.androidproject.core.credential;

import android.content.Context;
import android.util.Log;

import com.example.androidproject.features.auth.data.entity.UserEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FirebaseHelper {
    private final String TAG = FirebaseHelper.class.getSimpleName();

    private final Context context;
    private final FirebaseAuth auth;
    private final FirebaseFirestore firestore;
    private final UserPreferences userPreferences;

    public FirebaseHelper(Context context) {
        this.context = context;
        this.auth = FirebaseAuth.getInstance();
        this.firestore = FirebaseFirestore.getInstance();
        this.userPreferences = new UserPreferences(context);
    }

    public String getUid() {
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            String uid = user.getUid();
            Log.d(TAG, "User UID: " + uid);
            return uid;
        } else {
            Log.w(TAG, "No user is logged in.");
            return null;
        }
    }

    // get specify usser
    public CompletableFuture<UserEntity> findDocumentDataByUid() {
        CompletableFuture<UserEntity> future = new CompletableFuture<>();

        String documentId = getUid();

        if (documentId != null && !documentId.isEmpty()) {
            firestore.collection("users")
                    .document(documentId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            // retrieve data
                            UserEntity userEntity = retrieveData(documentSnapshot);

                            // complete
                            future.complete(userEntity);

                            Log.d(TAG, "User data retrieved by documentId and saved: " + userEntity);
                        } else {
                            Log.d(TAG, "No document found for documentId: " + documentId);
                            future.completeExceptionally(new Exception("No document found for documentId: " + documentId));
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.w(TAG, "Error getting document by ID: ", e);
                        future.completeExceptionally(e);
                    });
        } else {
            Log.w(TAG, "Invalid documentId provided.");
            future.completeExceptionally(new Exception("Invalid documentId provided."));
        }

        return future;
    }

    private UserEntity retrieveData(DocumentSnapshot documentSnapshot) {
        String id = documentSnapshot.getString("id");
        String firstName = documentSnapshot.getString("firstName");
        String lastName = documentSnapshot.getString("lastName");
        String email = documentSnapshot.getString("email");
        String phone = documentSnapshot.getString("phone");
        String gender = documentSnapshot.getString("gender");
        int role = documentSnapshot.getLong("role").intValue();
        int tier = documentSnapshot.getLong("tier").intValue();
        long totalSpent = documentSnapshot.getLong("totalSpent");
        String addressId = documentSnapshot.getString("addressId");
        String fullAddress = documentSnapshot.getString("fullAddress");

        // vouchers
        List<String> vouchers = (List<String>) documentSnapshot.get("vouchers");
        if (vouchers == null) vouchers = new ArrayList<>();

        // addresses
        List<String> addresses = (List<String>) documentSnapshot.get("addresses");
        if (addresses == null) addresses = new ArrayList<>();

        // create entity
        UserEntity userEntity = new UserEntity(
                id,
                role,
                tier,
                totalSpent,
                addressId,
                fullAddress,
                firstName,
                lastName,
                gender,
                email,
                phone,
                vouchers,
                addresses
        );

        // save to pref
        saveUserData(userEntity);

        return userEntity;
    }

    public void saveUserData(UserEntity userEntity) {
        userPreferences.saveUser(userEntity);
    }

    public boolean isUserLoggedIn() {
        FirebaseUser user = auth.getCurrentUser();
        return user != null;
    }
}
