package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.adapter.SortGroupAdapter;
import com.histudent.jwsoft.histudent.body.myclass.bean.ClassJoinBean;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.helper.GetCodeHelper;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by liuguiyu-pc on 2017/4/24.
 * 加入班级
 */

public class ClassJoinActivity extends BaseActivity {

    private PopupWindow popupWindow;
    private View view;
    private EditText assignment_edit;
    private TextView assignment_button;
    private TextView title;
    private final int OK = 200;
    private SortGroupAdapter sortGroupAdapter;
    private List<Object> classJoinBeens;
    private ClassJoinBean currentClassModel;
    private View selectView;

    public static void start(Activity activity, int code) {
        activity.startActivityForResult(new Intent(activity, ClassJoinActivity.class), code);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_class_join;
    }

    @Override
    public void initView() {
        title = (TextView) findViewById(R.id.title_middle_text);
        assignment_edit = (EditText) findViewById(R.id.assignment_edit);
        assignment_button = (TextView) findViewById(R.id.assignment_button);
        classJoinBeens = new ArrayList<>();
        sortGroupAdapter = new SortGroupAdapter(classJoinBeens, this);
        assignment_button.setBackgroundResource(R.drawable.gray_button_bg);
    }

    @Override
    public void doAction() {
        super.doAction();

        title.setText("加入班级");

        assignment_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                if (ReminderHelper.getIntentce().listenerEditIsEmpty(assignment_edit)) {
                    assignment_button.setBackgroundResource(R.drawable.join_class_shape);
                    assignment_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getClassList(editable.toString());
                        }
                    });
                } else {
                    assignment_button.setBackgroundResource(R.drawable.gray_button_bg);
                    assignment_button.setOnClickListener(null);
                }
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
        }
    }

    /**
     * 加入班级
     */
    private void joinClass(String classNum) {
        if (TextUtils.isEmpty(classNum)) return;
        ClassHelper.joinClassOwner(this, classNum, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                ReminderHelper.getIntentce().ToastShow(ClassJoinActivity.this, "加入班级成功");
                setResult(OK);
                finish();
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    /**
     * 获取班级列表
     *
     * @param pahoneNUm
     */
    public void getClassList(String pahoneNUm) {

        if (!GetCodeHelper.getIntentce().isMobileNO(pahoneNUm)) {
            joinClass(pahoneNUm);

        } else {

            Map<String, Object> map = new TreeMap<>();
            map.put("classMasterMobile", pahoneNUm);
            HiStudentHttpUtils.postDataByOKHttp(ClassJoinActivity.this, map, HistudentUrl.applyClassListByMobile_url, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {

                    List<ClassJoinBean> beens = JSON.parseArray(result, ClassJoinBean.class);

                    if (beens == null || beens.size() == 0) return;

                    classJoinBeens.clear();
                    if (beens.size() == 1) {
                        currentClassModel = beens.get(0);
                        joinClass(currentClassModel.getClassNumber());
                    } else {
                        classJoinBeens.addAll(beens);
                        initPopWindow();
                    }

                }

                @Override
                public void onFailure(String errorMsg) {

                }
            });
        }
    }

    /**
     * 班级选择列表
     */
    private void initPopWindow() {
        View layout = View.inflate(this, R.layout.join_class_popwindow, null);
        ListView listView = (ListView) layout.findViewById(R.id.list_view);
        TextView confirm = (TextView) layout.findViewById(R.id.confirm);
        TextView cancle = (TextView) layout.findViewById(R.id.cancle);

        popupWindow = new PopupWindow(this);
        //设置SelectPicPopupWindow的View
        popupWindow.setContentView(layout);
        //设置SelectPicPopupWindow弹出窗体的宽
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        popupWindow.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(getResources().getColor(R.color.hiworld_fram_gb));
        //设置SelectPicPopupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);


        listView.setAdapter(sortGroupAdapter);

        popupWindow.showAtLocation(findViewById(R.id.frame), Gravity.CENTER, 0, 0);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (selectView != null)
                    selectView.setVisibility(View.INVISIBLE);

                selectView = view.findViewById(R.id.tag);
                selectView.setVisibility(View.VISIBLE);

                currentClassModel = (ClassJoinBean) classJoinBeens.get(position);

            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentClassModel = null;
                popupWindow.dismiss();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentClassModel == null) {
                    ReminderHelper.getIntentce().ToastShow(ClassJoinActivity.this, "请先选择班级");
                    return;
                }
                popupWindow.dismiss();
                joinClass(currentClassModel.getClassNumber());
            }
        });

    }

}
