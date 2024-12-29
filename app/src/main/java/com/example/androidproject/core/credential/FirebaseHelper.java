package com.example.androidproject.core.credential;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class FirebaseHelper {
    private final String TAG = FirebaseHelper.class.getSimpleName();

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private SharedPreferences sharedPreferences;
    private Context context;

    public FirebaseHelper(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
    }

    public void findDocumentIdByUid() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            String uid = user.getUid();
            Log.d(TAG, "User UID: " + uid);

            db.collection("users")
                    .whereEqualTo("uid", uid)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // Nếu có tài liệu khớp, lấy document ID
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                String documentId = document.getId(); // Document ID
                                Log.d(TAG, "Document ID: " + documentId);

                                // Lấy thêm thông tin người dùng từ tài liệu này nếu cần
                                String userName = document.getString("userName");
                                String userEmail = document.getString("userEmail");
                                Log.d(TAG, "User Name: " + userName);
                                Log.d(TAG, "User Email: " + userEmail);
                            }
                        } else {
                            Log.d(TAG, "No document found for uid: " + uid);
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.w(TAG, "Error getting documents: ", e);
                    });
        } else {
            Log.w(TAG, "No user is logged in.");
        }
    }

    public void saveUserData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userId", user.getUid());
            editor.putString("userName", user.getDisplayName());
            editor.putString("userEmail", user.getEmail());
            editor.apply();
        }
    }

    public void checkUserLogin() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            saveUserData();
        }
    }

    public boolean isUserLoggedIn() {
        FirebaseUser user = mAuth.getCurrentUser();
        return user != null;
    }
}
