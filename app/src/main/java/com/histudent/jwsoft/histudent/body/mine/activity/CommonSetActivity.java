package com.histudent.jwsoft.histudent.body.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.utils.FileUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.friend.FriendService;

import java.io.File;

/**
 * Created by liuguiyu-pc on 2016/9/12.
 */
public class CommonSetActivity extends BaseActivity {
    private TextView title, cacheSize;
    private CheckBox checkBox;

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, CommonSetActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        cacheSize.setText(FileUtil.getAutoFileOrFilesSize());
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_commonset;
    }

    @Override
    public void initView() {
        title = (TextView) findViewById(R.id.title_middle_text);
        checkBox = (CheckBox) findViewById(R.id.commonset_04_checkbox);
        cacheSize = (TextView) findViewById(R.id.commonset_01_text);
    }

    @Override
    public void doAction() {
        super.doAction();
        title.setText("通用设置");
        final String imId = HiCache.getInstance().getLoginUserInfo().getUserId().replace("-", "");

        boolean notice = NIMClient.getService(FriendService.class).isNeedMessageNotify(imId);
        checkBox.setChecked(notice);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                NIMClient.toggleNotification(true);
            }
        });

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;

            case R.id.commonset_01_layout:
                ReminderHelper.getIntentce().showDialog(CommonSetActivity.this, "清除图片缓存", "是否清除图片缓存?", "确定", new DialogButtonListener() {
                    @Override
                    public void setOnDialogButtonListener() {
                        deleteAllFiles(new File(FileUtil.getPathFactory(FileUtil.CacheType.XUTILS_IMG, true)));
                        cacheSize.setText(FileUtil.getAutoFileOrFilesSize());
                    }
                }, "取消", new DialogButtonListener() {
                    @Override
                    public void setOnDialogButtonListener() {

                    }
                });
                break;

        }
    }

    private void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                try {
                    f.delete();
                } catch (Exception e) {
                }
            }
    }

}
