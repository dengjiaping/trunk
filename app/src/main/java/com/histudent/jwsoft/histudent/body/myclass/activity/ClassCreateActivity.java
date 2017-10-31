package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.myclass.bean.CreatClassSuccedBean;
import com.histudent.jwsoft.histudent.body.myclass.fragment.CreateClassFragmentTheFirst;
import com.histudent.jwsoft.histudent.body.myclass.fragment.CreateClassFragmentTheSecond;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.helper.PictureTailorHelper;
import com.histudent.jwsoft.histudent.commen.utils.CheckPermission;
import com.histudent.jwsoft.histudent.comment2.utils.ActivityCollector;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by liuguiyu-pc on 2016/12/9.
 * 创建班级
 */

public class ClassCreateActivity extends BaseActivity {

    private TextView title_middle;
    private BaseFragment newPersionFragmentTheFirst, newPersionFragmentTheSeconde, newPersionFragmentTheThird;
    private FragmentManager fragmentManager;
    private int positon = 1;
    private int resultCode = 0;
    private CheckPermission checkPermission;
    private PictureTailorHelper clippHelper;
    public static String logoPath;

    public static final int TAKE_PHOTO = 0;
    public static final int SECONDE = 1;
    public static final int THIRD = 2;
    //    private TextView title_right;
    private Fragment currentFragment;

    //获取照片需要的权限
    private final int PERMISSION_TAKTPHOTO_CODE = 100;
    static final String[] PERMISSION_TAKTPHOTO = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    public static void start(Activity activity, int code) {
        Intent intent = new Intent(activity, ClassCreateActivity.class);
        activity.startActivityForResult(intent, code);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_new_persion;
    }

    @Override
    public void initView() {

        clippHelper = PictureTailorHelper.getInstance();
        ActivityCollector.addActivity(this);
        checkPermission = new CheckPermission(this);

        title_middle = (TextView) findViewById(R.id.title_middle_text);

        newPersionFragmentTheFirst = new CreateClassFragmentTheFirst();
        newPersionFragmentTheSeconde = new CreateClassFragmentTheSecond();

        fragmentManager = getSupportFragmentManager();

        goNext(newPersionFragmentTheFirst);

        title_middle.setText(R.string.createClass);

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.title_left:

                switch (fragmentManager.getBackStackEntryCount()) {
                    case 1:
                        setResult(resultCode);
                        finish();
                        break;

                    case 2:
                        fragmentManager.popBackStack();
                        positon = 1;
                        break;

                    case 3:
                        setResult(200);
                        finish();
                        break;
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 300) {
            finish();
        } else {
            switch (positon) {
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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_TAKTPHOTO_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    clippHelper.showGetPhotoPictureListDialog(ClassCreateActivity.this);
                } else {
                    checkPermission.showMissingPermissionDialog();
                }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void doSomething(Integer flag) {
        switch (flag) {
            case TAKE_PHOTO:
                if (checkPermission.permissionSet(PERMISSION_TAKTPHOTO)) {
                    checkPermission.requestBasicPermission(PERMISSION_TAKTPHOTO, PERMISSION_TAKTPHOTO_CODE);
                } else {
                    clippHelper.showGetPhotoPictureListDialog(ClassCreateActivity.this);
                }
                break;

            case SECONDE:
                goNext(newPersionFragmentTheSeconde);
                positon++;
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showClassInfo(CreatClassSuccedBean bean) {

        ClassAndGroupShareActivity.start(this, bean, 100);
        finish();
    }

    /**
     * "下一步"的操作
     *
     * @param fragment
     */
    private void goNext(Fragment fragment) {
        if (!fragment.isAdded()) {
//            if (currentFragment == null) {
            fragmentManager.beginTransaction().add(R.id.fragment_layout, fragment).addToBackStack(null).commit();
//            } else {
//                fragmentManager.beginTransaction().hide(currentFragment).add(R.id.fragment_layout, fragment).addToBackStack(null).commit();
//            }
        }
        currentFragment = fragment;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (fragmentManager.getBackStackEntryCount() == 1) {
                setResult(200);
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
