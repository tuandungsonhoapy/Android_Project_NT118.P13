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

    // run when login/register
    public CompletableFuture<UserEntity> findDocumentDataByUid() {
        CompletableFuture<UserEntity> future = new CompletableFuture<>();

        String uid = getUid();  // Retrieve current user UID

        if (uid != null) {
            Log.d(TAG, "User UID: " + uid);

            firestore.collection("users")
                    .whereEqualTo("uid", uid)
                    .limit(1)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // found doc
                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);

                            // upd doc id
                            userPreferences.updateUserDataByKey(UserPreferences.KEY_DOC_ID,documentSnapshot.getId());

                            // retrieve data
                            UserEntity userEntity = retrieveData(documentSnapshot);

                            // complete
                            future.complete(userEntity);
                        } else {
                            Log.w(TAG, "No document found for UID: " + uid);
                            future.completeExceptionally(new Exception("No document found for UID: " + uid));
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.w(TAG, "Error getting document by UID: ", e);
                        future.completeExceptionally(e);
                    });

        } else {
            Log.w(TAG, "No user is logged in.");
            future.completeExceptionally(new Exception("No user is logged in."));
        }

        return future;
    }

    // get specify usser
    public CompletableFuture<UserEntity> findDocumentDataById(String documentId) {
        CompletableFuture<UserEntity> future = new CompletableFuture<>();

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
        String uid = documentSnapshot.getString("uid");
        String firstName = documentSnapshot.getString("firstName");
        String lastName = documentSnapshot.getString("lastName");
        String email = documentSnapshot.getString("email");
        String phone = documentSnapshot.getString("phone");
        String gender = documentSnapshot.getString("gender");
        int role = documentSnapshot.getLong("role").intValue();
        int tier = documentSnapshot.getLong("tier").intValue();
        long totalSpent = documentSnapshot.getLong("totalSpent");
        String addressId = documentSnapshot.getString("addressId");

        // wishlist
        List<String> wishlist = (List<String>) documentSnapshot.get("wishlist");
        if (wishlist == null) wishlist = new ArrayList<>();

        // addresses
        List<String> addresses = (List<String>) documentSnapshot.get("addresses");
        if (addresses == null) addresses = new ArrayList<>();

        // create entity
        UserEntity userEntity = new UserEntity(
                uid,
                role,
                tier,
                totalSpent,
                addressId,
                firstName,
                lastName,
                gender,
                email,
                phone,
                wishlist,
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
