package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.CodeNum;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.ClassMemberBean;
import com.histudent.jwsoft.histudent.body.message.model.ClassModel;
import com.histudent.jwsoft.histudent.body.myclass.adapter.ClassMemberAdapter;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.ShareUtils;
import com.histudent.jwsoft.histudent.commen.bean.MyShareBean;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.CharacterParser;
import com.histudent.jwsoft.histudent.commen.view.EditText_clear;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.MyListView2;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2017/4/22.
 * 班级成员
 */

public class ClassMemberActivity extends BaseActivity {

    private TextView title, teacher_num, student_num;
    private EditText_clear reshech;
    private IconView right_image, search_icon;
    private MyListView2 teacher_list, student_list;
    private ClassMemberAdapter teacher_adpter, student_adpter;
    private List<Object> teacher_datas, teacher_datas_all, student_datas, student_datas_all;
    private final int OK = 200;
    private final int ADDMEMBER = 1;
    private MyHandler handler;
    private boolean isAdmin;
    private int type;
    private CharacterParser characterParser;//汉字转换成拼音的类

    public static void start(Activity activity, int code) {
        activity.startActivityForResult(new Intent(activity, ClassMemberActivity.class), code);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_class_member;
    }

    @Override
    public void initView() {
        handler = new MyHandler();
        teacher_datas = new ArrayList<>();
        student_datas = new ArrayList<>();
        teacher_datas_all = new ArrayList<>();
        student_datas_all = new ArrayList<>();
        ClassModel classModel = HiCache.getInstance().getClassDetailInfo();
        type = classModel.isIsClassMaker() ? 1 : classModel.isIsAdmin() ? 2 : 3;
        teacher_adpter = new ClassMemberAdapter(this, teacher_datas, type);
        student_adpter = new ClassMemberAdapter(this, student_datas, type);
        title = (TextView) findViewById(R.id.title_middle_text);
        teacher_num = (TextView) findViewById(R.id.teacher_num);
        student_num = (TextView) findViewById(R.id.student_num);
        right_image = (IconView) findViewById(R.id.title_right_image);
        search_icon = (IconView) findViewById(R.id.search_icon);
        reshech = (EditText_clear) findViewById(R.id.filter_edit);
        teacher_list = (MyListView2) findViewById(R.id.teacher_list);
        student_list = (MyListView2) findViewById(R.id.student_list);
        characterParser = CharacterParser.getInstance();

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void doAction() {
        super.doAction();

        title.setText("班级成员");

        isAdmin = HiCache.getInstance().getClassDetailInfo().isIsAdmin();
        if (isAdmin)
            right_image.setText(R.string.icon_add);

        teacher_list.setAdapter(teacher_adpter);

        student_list.setAdapter(student_adpter);
        getMemberData(new GetMembers("", 1));
        reshech.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (TextUtils.isEmpty(editable)) {
                    search_icon.setVisibility(View.VISIBLE);
                } else {
                    search_icon.setVisibility(View.GONE);
                }

                filterData_T(editable.toString());
                filterData_S(editable.toString());

            }
        });

    }

    private void filterData_T(String filterStr) {

        List<Object> admins = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {
            admins.addAll(teacher_datas_all);
        } else {
            admins.clear();
            List<ClassMemberBean.TeaClassMembersBean> beens = new ArrayList<>();
            for (Object object : teacher_datas_all) {
                ClassMemberBean.TeaClassMembersBean admin = (ClassMemberBean.TeaClassMembersBean) object;
                String name = admin.getUserRealName();
                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    beens.add(admin);
                }
            }
            sortTeaClassMembersBean(beens);
            admins.addAll(beens);
        }
        teacher_datas.clear();
        teacher_datas.addAll(admins);

        if (teacher_datas.size() == 0) {
            teacher_num.setVisibility(View.GONE);
        } else {
            teacher_num.setVisibility(View.VISIBLE);
            teacher_num.setText("教师(" + teacher_datas.size() + ")");
        }

        teacher_adpter.notifyDataSetChanged();
    }

    private void filterData_S(String filterStr) {

        List<Object> admins = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {
            admins.addAll(student_datas_all);
        } else {
            admins.clear();
            List<ClassMemberBean.StuClassMembersBean> beens = new ArrayList<>();
            for (Object object : student_datas_all) {
                ClassMemberBean.StuClassMembersBean genera = (ClassMemberBean.StuClassMembersBean) object;
                String name = genera.getUserRealName();
                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    beens.add(genera);
                }
            }
            admins.addAll(beens);
        }
        student_datas.clear();
        student_datas.addAll(admins);

        if (student_datas.size() == 0) {
            student_num.setVisibility(View.GONE);
        } else {
            student_num.setVisibility(View.VISIBLE);
            student_num.setText("学生(" + student_datas.size() + ")");
        }

        student_adpter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMemberData(GetMembers info) {
        setResult(CodeNum.CLASS_MEMBER_RESULT);
        if (info.type == 1) {
            ClassHelper.getClassMembers(this, info.keyName, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {

                    ClassMemberBean temberModel = JSON.parseObject(result, ClassMemberBean.class);
                    List<ClassMemberBean.TeaClassMembersBean> teacher_models = temberModel.getTeaClassMembers();
                    if (teacher_models != null) {
                        teacher_datas.clear();
                        teacher_datas_all.clear();
                        sortTeaClassMembersBean(teacher_models);
                        teacher_datas.addAll(teacher_models);
                        teacher_datas_all.addAll(teacher_models);
                        teacher_adpter.notifyDataSetChanged();

                        if (teacher_datas.size() == 0) {
                            teacher_num.setVisibility(View.GONE);
                        } else {
                            teacher_num.setVisibility(View.VISIBLE);
                            teacher_num.setText("教师(" + teacher_datas.size() + ")");
                        }

                    }
                    List<ClassMemberBean.StuClassMembersBean> student_models = temberModel.getStuClassMembers();
                    if (student_models != null) {
                        student_datas.clear();
                        student_datas_all.clear();
                        sortStuClassMembersBean(student_models);
                        student_datas.addAll(student_models);
                        student_datas_all.addAll(student_models);
                        student_adpter.notifyDataSetChanged();

                        if (student_datas.size() == 0) {
                            student_num.setVisibility(View.GONE);
                        } else {
                            student_num.setVisibility(View.VISIBLE);
                            student_num.setText("学生(" + student_datas.size() + ")");
                        }

                    }
                }

                @Override
                public void onFailure(String errorMsg) {

                }
            });
        } else if (info.type == 2) {

            if (teacher_datas.size() == 0) {
                teacher_num.setVisibility(View.GONE);
            } else {
                teacher_num.setVisibility(View.VISIBLE);
                teacher_num.setText("教师(" + teacher_datas.size() + ")");
            }

            if (student_datas.size() == 0) {
                student_num.setVisibility(View.GONE);
            } else {
                student_num.setVisibility(View.VISIBLE);
                student_num.setText("学生(" + student_datas.size() + ")");
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left://返回
                finish();
                break;

            case R.id.title_right://添加成员
                if (isAdmin)
                    ClassAddMemberActivity.start(this, ADDMEMBER);
                break;

            case R.id.weixin_add:
                getShareData(this, HiCache.getInstance().getClassDetailInfo().getClassId(), ShareUtils.WEIXIN);
                break;

            case R.id.QQ_add:
                getShareData(this, HiCache.getInstance().getClassDetailInfo().getClassId(), ShareUtils.QQ);
                break;
        }
    }

    public static class GetMembers {
        public String keyName;
        public int type;//(1:搜索；2:删除)

        public GetMembers(String keyName, int type) {
            this.keyName = keyName;
            this.type = type;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADDMEMBER && resultCode == OK) {
            setResult(CodeNum.CLASS_MEMBER_RESULT);
            EventBus.getDefault().post(new GetMembers("", 1));
        }

    }

    /**
     * 获取班级分享数据并予以分享
     *
     * @param activityId
     */
    private void getShareData(final BaseActivity activity, final String activityId, final int flag) {
        if (TextUtils.isEmpty(activityId)) return;
        Map<String, Object> map = new TreeMap<>();
        map.put("objectId", activityId);
        map.put("shareObjectType", 7);

        HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.share_url_, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                MyShareBean shareBean = JSON.parseObject(result, MyShareBean.class);
                ShareUtils.share(activity, shareBean, flag);
            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    EventBus.getDefault().post(new GetMembers(msg.obj.toString(), 1));
                    break;
            }
        }
    }

    /**
     * **************************** 排序 ***********************************
     */
    private void sortTeaClassMembersBean(List<ClassMemberBean.TeaClassMembersBean> list) {
        if (list.size() == 0) {
            return;
        }
        Collections.sort(list, comp_);
    }

    private Comparator<ClassMemberBean.TeaClassMembersBean> comp_ = new Comparator<ClassMemberBean.TeaClassMembersBean>() {
        @Override
        public int compare(ClassMemberBean.TeaClassMembersBean o1, ClassMemberBean.TeaClassMembersBean o2) {
            int flag = 0;
            if (o1.isIsClassMaker()) {
                flag = -1;
            } else if (o2.isIsClassMaker()) {
                flag = 1;
            } else if (o1.isIsAdmin()) {
                flag = -1;
            } else if (o2.isIsAdmin()) {
                flag = 1;
            }
            return flag;
        }
    };

    private void sortStuClassMembersBean(List<ClassMemberBean.StuClassMembersBean> list) {
        if (list.size() == 0) {
            return;
        }
        Collections.sort(list, comp);
    }

    private Comparator<ClassMemberBean.StuClassMembersBean> comp = new Comparator<ClassMemberBean.StuClassMembersBean>() {
        @Override
        public int compare(ClassMemberBean.StuClassMembersBean o1, ClassMemberBean.StuClassMembersBean o2) {
            int flag = 0;
            if (o1.isIsClassMaker()) {
                flag = -1;
            } else if (o2.isIsClassMaker()) {
                flag = 1;
            } else if (o1.isIsAdmin()) {
                flag = -1;
            } else if (o2.isIsAdmin()) {
                flag = 1;
            }
            return flag;
        }
    };

}
