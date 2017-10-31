package com.histudent.jwsoft.histudent.commen.photo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.photo.PhotoHelper;
import com.histudent.jwsoft.histudent.commen.photo.view.ZoomImageView;

public class ShowPhotoActivity extends Activity {

    private ZoomImageView zoomImg;

    public static void start(Activity activity, String path) {

        Intent intent = new Intent(activity, ShowPhotoActivity.class);
        intent.putExtra("path", path);
        activity.startActivity(intent);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showphotoactivity);
        zoomImg = (ZoomImageView) findViewById(R.id.image);
        zoomImg.setImage(PhotoHelper.decodeBitmap(getIntent().getStringExtra("path"), 1000));
    }
}