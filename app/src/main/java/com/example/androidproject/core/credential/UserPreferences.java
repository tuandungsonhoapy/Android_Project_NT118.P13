package com.example.androidproject.core.credential;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.androidproject.core.credential.data.entity.UserPrefEntity;
import com.example.androidproject.core.utils.EncryptionUtils;
import com.example.androidproject.features.auth.data.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserPreferences {

    // pref
    public static final String PREF_NAME = "user_pref";

    // user data
    public static final String KEY_DOC_ID = "doc_id";
    public static final String KEY_ID = "id";
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_LAST_NAME = "last_name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_ROLE = "role";
    public static final String KEY_TIER = "tier";
    public static final String KEY_TOTAL_SPENT = "total_spent";
    public static final String KEY_ADDRESS_ID = "address_id";

    // acc info
    public static final String ACCOUNT = "account";
    public static final String PASSWORD = "password";
    public static final String IS_SAVE_PASSWORD_ENABLE = "is_save_password_enable";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private final String secretKey;

    public UserPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        secretKey = "TECHNOUIT69";
        // secretKey = context.getString(R.id.secret_key)
    }

    // user data
    public void saveUser(UserEntity userEntity) {
        editor.putString(KEY_ID, EncryptionUtils.encrypt(userEntity.getId(), secretKey));
        editor.putString(KEY_FIRST_NAME, EncryptionUtils.encrypt(userEntity.getFirstName(), secretKey));
        editor.putString(KEY_LAST_NAME, EncryptionUtils.encrypt(userEntity.getLastName(), secretKey));
        editor.putString(KEY_EMAIL, EncryptionUtils.encrypt(userEntity.getEmail(), secretKey));
        editor.putString(KEY_PHONE, EncryptionUtils.encrypt(userEntity.getPhone(), secretKey));
        editor.putString(KEY_GENDER, EncryptionUtils.encrypt(userEntity.getGender(), secretKey));
        editor.putInt(KEY_ROLE, userEntity.getRole());
        editor.putInt(KEY_TIER, userEntity.getTier());
        editor.putLong(KEY_TOTAL_SPENT, userEntity.getTotalSpent());
        editor.putString(KEY_ADDRESS_ID, EncryptionUtils.encrypt(userEntity.getAddressId(), secretKey));
        editor.apply();
    }

    // get n set
    public Object getUserDataByKey(String key) {
        switch (key) {
            case KEY_DOC_ID:
            case KEY_ID:
            case KEY_FIRST_NAME:
            case KEY_LAST_NAME:
            case KEY_EMAIL:
            case KEY_PHONE:
            case KEY_GENDER:
            case KEY_ADDRESS_ID:
                return EncryptionUtils.decrypt(sharedPreferences.getString(key, null), secretKey);

            case KEY_ROLE:
            case KEY_TIER:
                return sharedPreferences.getInt(key, 1);

            case KEY_TOTAL_SPENT:
                return sharedPreferences.getLong(key, 0L);

            default:
                return null;
        }
    }

    public UserPrefEntity getUserEntity() {
        String id = sharedPreferences.getString(KEY_ID, null);
        if (id == null) {
            return null;
        }

        String firstName = EncryptionUtils.decrypt(sharedPreferences.getString(KEY_FIRST_NAME, null), secretKey);
        String lastName = EncryptionUtils.decrypt(sharedPreferences.getString(KEY_LAST_NAME, null), secretKey);
        String email = EncryptionUtils.decrypt(sharedPreferences.getString(KEY_EMAIL, null), secretKey);
        String phone = EncryptionUtils.decrypt(sharedPreferences.getString(KEY_PHONE, null), secretKey);
        String gender = EncryptionUtils.decrypt(sharedPreferences.getString(KEY_GENDER, null), secretKey);
        int role = sharedPreferences.getInt(KEY_ROLE, 1); // df=1
        int tier = sharedPreferences.getInt(KEY_TIER, 0); // df=0
        long totalSpent = sharedPreferences.getLong(KEY_TOTAL_SPENT, 0L); // df=0
        String addressId = EncryptionUtils.decrypt(sharedPreferences.getString(KEY_ADDRESS_ID, null), secretKey);

        return new UserPrefEntity(id, role, tier, totalSpent, addressId, firstName, lastName, gender, email, phone);
    }

    public void updateUserDataByKey(String key, Object value) {
        if (value == null) return;

        switch (key) {
            case KEY_DOC_ID:
            case KEY_ID:
            case KEY_FIRST_NAME:
            case KEY_LAST_NAME:
            case KEY_EMAIL:
            case KEY_PHONE:
            case KEY_GENDER:
            case KEY_ADDRESS_ID:
                editor.putString(key, EncryptionUtils.encrypt((String) value, secretKey));
                break;

            case KEY_ROLE:
            case KEY_TIER:
                editor.putInt(key, (Integer) value);
                break;

            case KEY_TOTAL_SPENT:
                editor.putLong(key, (Long) value);
                break;

            default:
                break;
        }
        editor.apply();
    }

    // user acc
    public void saveAccount(String account, String password, boolean isSavePasswordEnable) {
        editor.putString(ACCOUNT, account);
        editor.putString(PASSWORD, password);
        editor.putBoolean(IS_SAVE_PASSWORD_ENABLE, isSavePasswordEnable);
        editor.apply();
    }

    public String getAccount() {
        return sharedPreferences.getString(ACCOUNT, null);
    }

    public String getPassword() {
        return sharedPreferences.getString(PASSWORD, null);
    }

    public boolean isSaveAccountEnabled() {
        return sharedPreferences.getBoolean(IS_SAVE_PASSWORD_ENABLE, false);
    }

    // clear
    private List<String> keysToRemove() {
        List<String> keysToRemove = new ArrayList<>();

        keysToRemove.add(KEY_DOC_ID);
        keysToRemove.add(KEY_ID);
        keysToRemove.add(KEY_FIRST_NAME);
        keysToRemove.add(KEY_LAST_NAME);
        keysToRemove.add(KEY_EMAIL);
        keysToRemove.add(KEY_PHONE);
        keysToRemove.add(KEY_GENDER);
        keysToRemove.add(KEY_ROLE);
        keysToRemove.add(KEY_TIER);
        keysToRemove.add(KEY_TOTAL_SPENT);
        keysToRemove.add(KEY_ADDRESS_ID);

        return keysToRemove;
    }

    public void clearUser() {
        if (!isSaveAccountEnabled()) {
            editor.remove(ACCOUNT);
            editor.remove(PASSWORD);
            editor.remove(IS_SAVE_PASSWORD_ENABLE);
        }

        // others
        for (String key : keysToRemove()) {
            editor.remove(key);
        }

        // apply
        editor.apply();
    }

    // boom ez life
    public boolean isUserLoggedIn() {
        return sharedPreferences.contains(KEY_ID);
    }

    public boolean isAdmin() {
        return sharedPreferences.getInt(KEY_ROLE, 1) == 0;
    }
}
