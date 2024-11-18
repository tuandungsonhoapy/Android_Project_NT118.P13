package com.example.androidproject.core.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.utils.ObjectUtils;
import com.example.androidproject.R;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class CloudinaryConfig {
    public static void khoi_tao_cloudinary(Context ctx) {
        try {
            String apiKey = ctx.getString(R.string.api_key);
            String apiSecret = ctx.getString(R.string.api_secret);
            String cloudName = ctx.getString(R.string.cloud_name);

            if (cloudName.isEmpty() || apiKey.isEmpty() || apiSecret.isEmpty()) {
                Log.e("CLOUDINARY", "thiêu key");
                return;
            }

            Map<String, Object> config = new HashMap<>();
            config.put("cloud_name", cloudName);
            config.put("api_key", apiKey);
            config.put("api_secret", apiSecret);

            MediaManager.init(ctx, config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CompletableFuture<String> uploadImage(String imagePath, Context ctx) {
        CompletableFuture<String> future = new CompletableFuture<>();
        try {
            File file;
            if (imagePath.contains("content://")) {
                file = createImageFileFromUri(Uri.parse(imagePath), ctx);
            } else {
                file = new File(imagePath);
            }

            MediaManager.get()
                    .upload(file.getAbsolutePath())
                    .callback(new UploadCallback() {
                        @Override
                        public void onStart(String requestId) {

                        }

                        @Override
                        public void onProgress(String requestId, long bytes, long totalBytes) {

                        }

                        @Override
                        public void onSuccess(String requestId, Map resultData) {
                            String uploadUrl = (String) resultData.get("secure_url");
                            future.complete(uploadUrl);
                        }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {

                        }

                        @Override
                        public void onReschedule(String requestId, ErrorInfo error) {

                        }
                    }).dispatch();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("CLOUDINARY", "uploadImage-err: " + e.getMessage());
            future.completeExceptionally(e);
        }

        return future;
    }

    public static File createImageFileFromUri(Uri uri, Context ctx) {
        File tmp_file = null;
        try {
            InputStream inputStream = ctx.getContentResolver().openInputStream(uri);

            tmp_file = File.createTempFile("tmp_file", ".jpg", ctx.getCacheDir());
            OutputStream outputStream = new FileOutputStream(tmp_file);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.close();
            inputStream.close();

            return tmp_file;
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (tmp_file == null || !tmp_file.exists()) {
            Log.e("CLOUDINARY", "Không thể tạo file tạm");
        }

        return tmp_file;
    }

}
