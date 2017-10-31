package com.histudent.jwsoft.histudent.body.hiworld.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.adapter.SortGroupAdapter;
import com.histudent.jwsoft.histudent.body.hiworld.fragment.OhterAlbumList;
import com.histudent.jwsoft.histudent.body.hiworld.fragment.PersonAlbumList;
import com.histudent.jwsoft.histudent.body.mine.model.UserClassListModel;
import com.histudent.jwsoft.histudent.body.mine.parser.DataParser;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.utils.DataUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.utils.ActionTypeEnum;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.width;

//选择图片需要上传到的相册
public class SelectUploadAlbumActivity extends BaseActivity implements View.OnClickListener {

    private List<Fragment> list;
    private SortGroupAdapter mAdapter;
    private TextView title_right, title_middle;
    private IconView iv_down;
    private Intent intent;
    private String id;
    private boolean isCanCreatePhoto;
    private PopupWindow popupWindow;
    private ActionTypeEnum typeEnum;
    private FragmentManager Mmanager;
    private List<Object> mClassList;
    private String classId, title;

    @Override
    public int setViewLayout() {
        return R.layout.activity_select_file;
    }

    public static void start(Activity activity, ActionTypeEnum typeEnum, String id, boolean isCanCreatePhoto) {

        Intent intent = new Intent(activity, SelectUploadAlbumActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("typeEnum", typeEnum);
        intent.putExtra("isCanCreatePhoto", isCanCreatePhoto);
        activity.startActivityForResult(intent, 600);
    }

    @Override
    public void initView() {

        id = getIntent().getStringExtra("id");
        typeEnum = ((ActionTypeEnum) getIntent().getSerializableExtra("typeEnum"));
        isCanCreatePhoto = getIntent().getBooleanExtra("isCanCreatePhoto", false);

        title_middle = ((TextView) findViewById(R.id.title_middle_text));
        iv_down = ((IconView) findViewById(R.id.title_middle_image));
        title_right = ((TextView) findViewById(R.id.title_right_text));
        findViewById(R.id.title_left_image).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.title_left_text)).setText("取消");
        mClassList = new ArrayList<>();
        mAdapter = new SortGroupAdapter(mClassList, this);
        Mmanager = getSupportFragmentManager();


        if (typeEnum == ActionTypeEnum.CLASSANDOWNER || typeEnum == ActionTypeEnum.OWNER) {
            title_middle.setText("上传到");
            title_right.setText("班级相册");

            Mmanager.beginTransaction().replace(R.id.fragment, new PersonAlbumList()).commit();
        } else {

            HideCreateBtn();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment, OhterAlbumList.OhterAlbumList(typeEnum, id)).commit();
//            title_right.setText("");
            if (typeEnum == ActionTypeEnum.GROUP) {
                title_middle.setText("社群相册");

            } else {
                title_middle.setText("班级相册");
            }
        }

        if (typeEnum == ActionTypeEnum.OWNER||typeEnum == ActionTypeEnum.CLASS) {
            title_right.setVisibility(View.VISIBLE);
        } else {
            title_right.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            //新建相册
            case R.id.btn_join:

                if (typeEnum == ActionTypeEnum.CLASSANDOWNER) {
                    if (!StringUtil.isEmpty(title_middle.getText().toString()) && title_middle.getText().toString().equals("上传到")) {
                        CreateAlbumActivity.start(SelectUploadAlbumActivity.this, ActionTypeEnum.OWNER, id);
                    } else {
                        CreateAlbumActivity.start(SelectUploadAlbumActivity.this, ActionTypeEnum.CLASS, classId);
                    }

                } else {
                    CreateAlbumActivity.start(SelectUploadAlbumActivity.this, typeEnum, id);
                }

                break;

            //返回
            case R.id.title_left:
                this.finish();
                break;

            case R.id.title_right:

                if (typeEnum == ActionTypeEnum.OWNER||typeEnum == ActionTypeEnum.CLASS) {
                    if (!StringUtil.isEmpty(title_middle.getText().toString()) && title_middle.getText().toString().equals("上传到")) {
                        getMyClasses();
                    } else {
                        iv_down.setVisibility(View.GONE);
                        title_middle.setText("上传到");
                        if (typeEnum == ActionTypeEnum.GROUP) {
                            title_right.setText("社群相册");
                        } else if (typeEnum == ActionTypeEnum.CLASS || typeEnum == ActionTypeEnum.CLASSANDOWNER) {
                            title_right.setText("班级相册");
                        }
                        Mmanager.beginTransaction().replace(R.id.fragment, new PersonAlbumList()).commit();
                    }
                }


                break;

            case R.id.title_middle:
                if (typeEnum == ActionTypeEnum.CLASSANDOWNER && !title_middle.getText().equals("上传到")) {
                    if (mClassList.size() > 1) {
                        iv_down.setText(R.string.icon_arrowbottom);
                        initPopWindow();
                    }
                }
        }
    }

    private void initPopWindow() {

        View layout = View.inflate(this, R.layout.same_city_popwindow, null);
        layout.findViewById(R.id.line).setVisibility(View.VISIBLE);
        findViewById(R.id.frame).setVisibility(View.VISIBLE);
        final ListView listView = ((ListView) layout.findViewById(R.id.list_view));

        //设置默认显示的班级
        if (!StringUtil.isEmpty(classId)) {
            mAdapter.initSeletedItem(classId);
        }
        listView.setAdapter(mAdapter);
        popupWindow = new PopupWindow(layout, SystemUtil.getScreenWind(), LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        int xoff = width / 2 - 120;
        popupWindow.showAsDropDown(findViewById(R.id.title_middle_text), xoff, 0);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                iv_down.setText(R.string.icon_arrowbottom);
                findViewById(R.id.frame).setVisibility(View.GONE);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击条目的事件
                TextView tv = ((TextView) view.findViewById(R.id.tv_name));
                tv.setTextColor(Color.rgb(32, 126, 189));

                UserClassListModel bean = ((UserClassListModel) mClassList.get(position));

                if (!StringUtil.isEmpty(bean.getClassId()) && !bean.getClassId().equals(classId)) {
                    classId = bean.getClassId();
                    isCanCreatePhoto = bean.isAdmin();
                    title = bean.getCName();
                    title_middle.setText(title);
                    HideCreateBtn();
                    Mmanager.beginTransaction().replace(R.id.fragment, OhterAlbumList.OhterAlbumList(ActionTypeEnum.CLASS, classId)).commit();
                }

                popupWindow.dismiss();
            }
        });
    }

    //获取班级列表
    private void getMyClasses() {

        DataUtils.GetUserSyncClassList(this, false, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.e("onSuccess", result);
                mClassList.clear();
                mClassList.addAll(DataParser.getUserClassList(result));
                if (mClassList != null && mClassList.size() > 0) {

                    if (mClassList.size() > 0) {

                        if (mClassList.size() > 1) {
                            iv_down.setVisibility(View.VISIBLE);
                            iv_down.setText(R.string.icon_arrowbottom);
                        } else {
                            iv_down.setVisibility(View.GONE);
                        }

                        title = ((UserClassListModel) mClassList.get(0)).getCName();
                        Log.e("getGradeName", title);
                        classId = ((UserClassListModel) mClassList.get(0)).getClassId();
                        isCanCreatePhoto = ((UserClassListModel) mClassList.get(0)).isAdmin();

                        HideCreateBtn();
                        title_middle.setText(title);
                        title_right.setText("个人相册");
                        Mmanager.beginTransaction().replace(R.id.fragment, OhterAlbumList.OhterAlbumList(
                                ActionTypeEnum.CLASS, classId)).commit();
                    } else {
                        iv_down.setVisibility(View.GONE);
                    }

                } else {
                    Mmanager.beginTransaction().replace(R.id.fragment, OhterAlbumList.OhterAlbumList(
                            ActionTypeEnum.CLASS, "")).commit();
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        }, LoadingType.FLOWER);
    }

    // 隐藏或者显示创建相册的入口
    private void HideCreateBtn() {
        if (isCanCreatePhoto) {
            findViewById(R.id.line0).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_join).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.btn_join).setVisibility(View.GONE);
            findViewById(R.id.line0).setVisibility(View.GONE);
        }
    }

}
