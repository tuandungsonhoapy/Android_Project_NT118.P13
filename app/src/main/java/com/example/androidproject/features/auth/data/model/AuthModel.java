package com.example.androidproject.features.auth.data.model;

import com.example.androidproject.features.auth.domain.entity.AuthEntity;
import com.google.firebase.auth.FirebaseUser;

public class AuthModel extends AuthEntity {

    public AuthModel(String uid, String email, String displayName, String photoUrl) {
        super(uid, email, displayName, photoUrl);
    }

    public static AuthModel toAuthModel(FirebaseUser firebaseUser) {
        return new AuthModel(
                firebaseUser.getUid(),
                firebaseUser.getEmail(),
                firebaseUser.getDisplayName(),
                String.valueOf(firebaseUser.getPhotoUrl())
        );
    }

    public static AuthEntity toAuthEntity(AuthModel userModel) {
        return new AuthEntity(
                userModel.getUid(),
                userModel.getEmail(),
                userModel.getDisplayName(),
                userModel.getPhotoUrl());
    }
}
