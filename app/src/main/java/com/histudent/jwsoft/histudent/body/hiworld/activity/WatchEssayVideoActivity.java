package com.histudent.jwsoft.histudent.body.hiworld.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.view.IconView;

/**
 * Created by liuguiyu-pc on 2017/4/14.
 * 播放动态随记中的预览视频
 */

public class WatchEssayVideoActivity extends BaseActivity {

    private VideoView videoView;
    private IconView delet;
    private String path;

    public static void start(Activity activity, String path, int code) {
        Intent intent = new Intent(activity, WatchEssayVideoActivity.class);
        intent.putExtra("path", path);
        activity.startActivityForResult(intent, code);
    }


    @Override
    public int setViewLayout() {
        return R.layout.activity_watch_essay_movie;
    }

    @Override
    public void initView() {

        path = getIntent().getStringExtra("path");

        videoView = (VideoView) findViewById(R.id.videoView);

        delet = (IconView) findViewById(R.id.title_right_image);

        delet.setText(R.string.icon_del);

        delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(-10);
                finish();
            }
        });

        /**
         * VideoView控制视频播放的功能相对较少，具体而言，它只有start和pause方法。为了提供更多的控制，
         * 可以实例化一个MediaController，并通过setMediaController方法把它设置为VideoView的控制器。
         */
        videoView.setMediaController(new MediaController(this));
        Uri videoUri = Uri.parse(path);
        videoView.setVideoURI(videoUri);
        videoView.start();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
        }
    }
}
