package com.histudent.jwsoft.histudent.account.regist.activity;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.regist.fragment.NewPersonFragmentTheFirst;
import com.histudent.jwsoft.histudent.account.regist.fragment.NewPersonFragmentTheSecond;
import com.histudent.jwsoft.histudent.account.regist.fragment.NewPersonFragmentTheThird;
import com.histudent.jwsoft.histudent.body.BodyActivity;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.helper.PictureTailorHelper;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.receiver.TheReceiverAction;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.CheckPermission;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.comment2.bean.ClassSelectModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/12/5.
 * 新手指导界面
 */

public class NewPersonActivity extends BaseActivity {

    private TextView title_left, title_right, title_middle;
    private ImageView title_image;
    private BaseFragment newPersionFragmentTheFirst, newPersionFragmentTheSeconde, newPersionFragmentTheThird;
    private FragmentManager fragmentManager;
    private BroadcastReceiver receiver;
    public static boolean isUpdataImage;
    public static ClassSelectModel selectModel;
    public static List<String> list_ids;
    private CheckPermission checkPermission;
    private PictureTailorHelper clippHelper;

    //获取照片需要的权限
    private final int PERMISSION_TAKTPHOTO_CODE = 100;
    static final String[] PERMISSION_TAKTPHOTO = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, NewPersonActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_new_persion;
    }

    @Override
    public void initView() {

        clippHelper = PictureTailorHelper.getInstance();
        checkPermission = new CheckPermission(this);

        list_ids = new ArrayList<>();
        title_image = (ImageView) findViewById(R.id.title_left_image);
        title_left = (TextView) findViewById(R.id.title_left_text);
        title_middle = (TextView) findViewById(R.id.title_middle_text);
        title_right = (TextView) findViewById(R.id.title_right_text);

        newPersionFragmentTheFirst = new NewPersonFragmentTheFirst();
        newPersionFragmentTheSeconde = new NewPersonFragmentTheSecond();
        newPersionFragmentTheThird = new NewPersonFragmentTheThird();

        fragmentManager = getSupportFragmentManager();

        goNext(newPersionFragmentTheFirst);

        title_image.setVisibility(View.GONE);
        title_left.setText(R.string.skip);
        title_right.setText(R.string.next);
        title_middle.setText(R.string.newPerson_01);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.title_left:

                switch (fragmentManager.getBackStackEntryCount()) {
                    case 1:
                        finish();
                        BodyActivity.start(this);
                        break;

                    case 2:
                        title_left.setText("跳过");
                        title_right.setText("下一步");
                        title_middle.setText(R.string.newPerson_01);
                        fragmentManager.popBackStack();
                        break;

                    case 3:
                        title_left.setText("上一步");
                        title_right.setText("下一步");
                        title_middle.setText(R.string.newPerson_02);
                        fragmentManager.popBackStack();
                        break;
                }

                break;
            case R.id.title_right:

                switch (fragmentManager.getBackStackEntryCount()) {

                    case 1:
                        if (isUpdataImage) {
                            selectModel = ClassSelectModel.getIntent();
                            title_left.setText("上一步");
                            title_right.setText("下一步");
                            title_middle.setText(R.string.newPerson_02);
                            goNext(newPersionFragmentTheSeconde);
                        } else {
                            theFirstTip();
                        }
                        break;

                    case 2:
                        if (selectModel != null && !TextUtils.isEmpty(selectModel.getClassName())) {
                            if (selectModel.getUserType() == 3) {
                                createClass(selectModel);
                            } else {
                                applyAddInClass(selectModel.getClassId());
                            }
                        } else {
                            theSecondeTip();
                        }
                        break;

                    case 3:

                        follwUserBatch();

                        break;
                }
                break;
        }
    }

    @Override
    public void doWorkByResevier() {
        super.doWorkByResevier();

        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(TheReceiverAction.GETHEADIMAGE);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (intent.getAction()) {
                    case TheReceiverAction.GETHEADIMAGE:

                        if (checkPermission.permissionSet(PERMISSION_TAKTPHOTO)) {
                            checkPermission.requestBasicPermission(PERMISSION_TAKTPHOTO, PERMISSION_TAKTPHOTO_CODE);
                        } else {
                            clippHelper.showGetPhotoPictureListDialog(NewPersonActivity.this);
                        }

                        break;
                }

            }
        };
        registerReceiver(receiver, myIntentFilter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 40 && resultCode == -2000) {
            title_left.setText("上一步");
            title_right.setText("完成");
            title_middle.setText(R.string.newPerson_03);
            goNext(newPersionFragmentTheThird);
        }

        switch (fragmentManager.getBackStackEntryCount()) {
            case 1:
                newPersionFragmentTheFirst.onActivityResult(requestCode, resultCode, data);
                break;
            case 2:
                newPersionFragmentTheSeconde.onActivityResult(requestCode, resultCode, data);
                break;
            case 3:
                newPersionFragmentTheThird.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_TAKTPHOTO_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    clippHelper.showGetPhotoPictureListDialog(NewPersonActivity.this);
                } else {
                    checkPermission.showMissingPermissionDialog();
                }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (fragmentManager.getBackStackEntryCount() == 1) {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 申请加入班级
     *
     * @param classId
     */
    private void applyAddInClass(String classId) {

        Map<String, Object> map = new TreeMap<>();
        map.put("classId", classId);

        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.applyJoinByClassId_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Toast.makeText(NewPersonActivity.this, "加入班级申请成功！", Toast.LENGTH_SHORT).show();

                title_left.setText("上一步");
                title_right.setText("完成");
                title_middle.setText(R.string.newPerson_03);
                goNext(newPersionFragmentTheThird);

            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

    }

    /**
     * 创建班级
     */
    private void createClass(ClassSelectModel selectModel) {
        Map<String, Object> map = new TreeMap<>();
        List<String> faths = new ArrayList<>();
        boolean isLogo = !TextUtils.isEmpty(selectModel.getLogo());
        map.put("orgId", selectModel.getSchoolId());
        map.put("gradeName", selectModel.getGradeName());
        map.put("className", selectModel.getClassName());
        map.put("eduSystem", selectModel.getEduSystemId());
        map.put("noteName", TextUtils.isEmpty(selectModel.getNoteName()) ? "" : selectModel.getNoteName());
        map.put("classDescription", TextUtils.isEmpty(selectModel.getClassDescription()) ? "" : selectModel.getClassDescription());
        map.put("isLogo", isLogo);
        if (isLogo) faths.add(selectModel.getLogo());

        HiStudentHttpUtils.postImageByOKHttp(this, faths, map, HistudentUrl.classCreate_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(NewPersonActivity.this, "创建班级成功！", Toast.LENGTH_SHORT).show();

                title_left.setText("上一步");
                title_right.setText("完成");
                title_middle.setText(R.string.newPerson_03);
                goNext(newPersionFragmentTheThird);
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

    }

    /**
     * 上传头像提醒
     */
    private void theFirstTip() {
        ReminderHelper.getIntentce().showDialog(this, "提醒", "是否跳过上传头像步骤？", "跳过", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {
                selectModel = ClassSelectModel.getIntent();
                title_left.setText("上一步");
                title_right.setText("下一步");
                title_middle.setText(R.string.newPerson_02);
                goNext(newPersionFragmentTheSeconde);
            }
        }, "继续", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {
            }
        });
    }

    /**
     * 加入班级提醒
     */
    private void theSecondeTip() {
        ReminderHelper.getIntentce().showDialog(this, "提醒", selectModel.getUserType() == 3 ? "是否跳过创建班级步骤？" : "是否跳过加入班级步骤？", "跳过", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {
                title_left.setText("上一步");
                title_right.setText("完成");
                title_middle.setText(R.string.newPerson_03);
                goNext(newPersionFragmentTheThird);
            }
        }, "继续", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {
            }
        });
    }

    /**
     * 批量关注
     */
    private void follwUserBatch() {

        if (list_ids.size() == 0) {
            finish();
            BodyActivity.start(NewPersonActivity.this);
            return;
        }


        Map<String, Object> map = new TreeMap<>();
        map.put("followUserIds", list_ids.toString());
        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.follwUserBatch_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                finish();
                BodyActivity.start(NewPersonActivity.this);
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

    }

    /**
     * "下一步"的操作
     *
     * @param fragment
     */
    private void goNext(BaseFragment fragment) {
        fragmentManager.beginTransaction().add(R.id.fragment_layout, fragment).addToBackStack(null).commit();
    }

}
