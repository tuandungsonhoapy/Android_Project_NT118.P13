package com.example.androidproject.core.utils;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;

import java.io.IOException;

public class FileHandler {
    private Context context;

    public FileHandler(Context context) {
        this.context = context;
    }

    public void openFileChooser(ActivityResultLauncher<Intent> launcher) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(intent);
    }

    public void displayImageFromUri(Uri imageUri, ImageView ivImagePreview) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
            ivImagePreview.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveImage(Uri imageUri) {
        // Save image to storage

    }
}
