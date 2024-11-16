package com.example.androidproject.core.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.androidproject.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ImageUriHandler {
    public static void loadImageUri(Context ctx, Uri uri, ImageView imageView) {
        if (uri != null) {
            try {
                InputStream inputStream = ctx.getContentResolver().openInputStream(uri);
                if (inputStream != null) {
                    Glide.with(ctx).
                            load(inputStream).
                            into(imageView);
                } else {
                    imageView.setImageResource(R.drawable.ic_launcher_background);
                }
            } catch (FileNotFoundException e) {
                imageView.setImageResource(R.drawable.ic_launcher_background);
            } catch (IOException e) {
                imageView.setImageResource(R.drawable.ic_launcher_background);
            }
        } else {
            imageView.setImageResource(R.drawable.ic_launcher_background);
        }
    }
}
