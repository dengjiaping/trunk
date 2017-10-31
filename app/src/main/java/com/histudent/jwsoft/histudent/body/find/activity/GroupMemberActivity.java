package com.histudent.jwsoft.histudent.body.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.CodeNum;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.GroupBean;
import com.histudent.jwsoft.histudent.body.find.bean.GroupMemberBean;
import com.histudent.jwsoft.histudent.body.myclass.adapter.ClassMemberAdapter;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.ShareUtils;
import com.histudent.jwsoft.histudent.commen.bean.MyShareBean;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.CharacterParser;
import com.histudent.jwsoft.histudent.commen.view.EditText_clear;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.MyListView2;
import com.histudent.jwsoft.histudent.entity.SelectMemberEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2017/4/22.
 * 社群成员
 */

public class GroupMemberActivity extends BaseActivity {

    private TextView title, teacher_num, student_num;
    private EditText_clear reshech;
    private IconView right_image, search_icon;
    private MyListView2 teacher_list, student_list;
    private ClassMemberAdapter teacher_adpter, student_adpter;
    private List<Object> admin_datas, admin_datas_all, genera_datas, genera_datas_all;
    private final int OK = 200;
    private final int ADDMEMBER = 1;
    private boolean isAdmin;
    private GroupBean groupBean;
    private int type;
    private boolean isSeleted;
    private LinearLayout request_layout;
    private CharacterParser characterParser;//汉字转换成拼音的类

    /**
     * 设置成员
     *
     * @param activity
     * @param groupBean
     * @param requestCode
     */
    public static void start(Activity activity, GroupBean groupBean, int requestCode) {
        Intent intent = new Intent(activity, GroupMemberActivity.class);
        intent.putExtra("groupBean", groupBean);
        intent.putExtra("isSeleted", false);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 选择成员
     *
     * @param activity
     * @param groupBean
     * @param requestCode
     * @param isSeleted
     */
    public static void start(Activity activity, GroupBean groupBean, int requestCode, boolean isSeleted) {
        Intent intent = new Intent(activity, GroupMemberActivity.class);
        intent.putExtra("groupBean", groupBean);
        intent.putExtra("isSeleted", isSeleted);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_class_member;
    }

    @Override
    public void initView() {
        isSeleted = getIntent().getBooleanExtra("isSeleted", false);
        groupBean = (GroupBean) getIntent().getSerializableExtra("groupBean");
        admin_datas = new ArrayList<>();
        genera_datas = new ArrayList<>();
        admin_datas_all = new ArrayList<>();
        genera_datas_all = new ArrayList<>();
        type = groupBean.isIsGroupMaker() ? 1 : groupBean.isIsManager() ? 2 : 3;
        teacher_adpter = new ClassMemberAdapter(this, admin_datas, type, isSeleted);
        student_adpter = new ClassMemberAdapter(this, genera_datas, type, isSeleted);
        title = (TextView) findViewById(R.id.title_middle_text);
        teacher_num = (TextView) findViewById(R.id.teacher_num);
        student_num = (TextView) findViewById(R.id.student_num);
        right_image = (IconView) findViewById(R.id.title_right_image);
        request_layout = (LinearLayout) findViewById(R.id.request_layout);
        reshech = (EditText_clear) findViewById(R.id.filter_edit);
        search_icon = (IconView) findViewById(R.id.search_icon);
        teacher_list = (MyListView2) findViewById(R.id.teacher_list);
        student_list = (MyListView2) findViewById(R.id.student_list);
        characterParser = CharacterParser.getInstance();

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void doAction() {
        super.doAction();

        title.setText("社群成员");

        isAdmin = groupBean.isIsManager();
        if (isAdmin)
            right_image.setText(R.string.icon_add);

        teacher_list.setAdapter(teacher_adpter);

        student_list.setAdapter(student_adpter);
        getMembers("1");
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
                filterData_A(editable.toString());
                filterData_G(editable.toString());
            }
        });

        if (isSeleted) {
            right_image.setVisibility(View.GONE);
            request_layout.setVisibility(View.GONE);
        }

    }


    @Subscribe
    public void onEvent(SelectMemberEvent event) {
        Intent intent = new Intent();
        intent.putExtra("mobile", event.mobile);
        intent.putExtra("name", event.name);
        setResult(200, intent);
        finish();
    }

    @Subscribe
    public void onEvent(GetMembers info) {
        setResult(CodeNum.GROUP_MEMBER_RESULT);
        if (info.type == 1) {
            getMembers(info.keyName);
        } else if (info.type == 2) {

            if (admin_datas.size() == 0) {
                teacher_num.setVisibility(View.GONE);
            } else {
                teacher_num.setVisibility(View.VISIBLE);
                teacher_num.setText("管理员(" + admin_datas.size() + ")");
            }

            if (genera_datas.size() == 0) {
                student_num.setVisibility(View.GONE);
            } else {
                student_num.setVisibility(View.VISIBLE);
                student_num.setText("普通成员(" + genera_datas.size() + ")");
            }

        }

    }

    private void getMembers(String key) {
        Map<String, Object> map = new TreeMap<>();
        map.put("groupId", groupBean.getGroupId());
        map.put("key", key);
        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.groupMembers_group, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                GroupMemberBean memberBean = JSON.parseObject(result, GroupMemberBean.class);
                List<GroupMemberBean.AdminMembersListBean> teacher_models = memberBean.getAdminMembersList();
                if (teacher_models != null) {
                    admin_datas.clear();
                    sortRecentContactsModel(teacher_models);

                    for (int i = 0; i < teacher_models.size(); i++) {
                        GroupMemberBean.AdminMembersListBean adminMembersListBean = teacher_models.get(i);
                        adminMembersListBean.setPinyin(characterParser.getSelling(adminMembersListBean.getUserRealName()));
                        admin_datas.add(adminMembersListBean);
                        admin_datas_all.add(adminMembersListBean);
                    }

                    teacher_adpter.notifyDataSetChanged();

                    if (admin_datas.size() == 0) {
                        teacher_num.setVisibility(View.GONE);
                    } else {
                        teacher_num.setVisibility(View.VISIBLE);
                        teacher_num.setText("管理员(" + admin_datas.size() + ")");
                    }
                }
                List<GroupMemberBean.GeneralMembersListBean> student_models = memberBean.getGeneralMembersList();
                if (student_models != null) {
                    genera_datas.clear();
                    for (int i = 0; i < student_models.size(); i++) {
                        GroupMemberBean.GeneralMembersListBean generalMembersListBean = student_models.get(i);
                        generalMembersListBean.setPinyin(characterParser.getSelling(generalMembersListBean.getUserRealName()));
                        genera_datas.add(generalMembersListBean);
                        genera_datas_all.add(generalMembersListBean);
                    }
                    student_adpter.notifyDataSetChanged();

                    if (genera_datas.size() == 0) {
                        student_num.setVisibility(View.GONE);
                    } else {
                        student_num.setVisibility(View.VISIBLE);
                        student_num.setText("普通成员(" + genera_datas.size() + ")");
                    }
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
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
                    AddClassmatesIntoGroupActivity.start(this, groupBean.getGroupId(), ADDMEMBER);
                break;

            case R.id.weixin_add:
                getShareData(this, groupBean.getGroupId(), ShareUtils.WEIXIN);
                break;

            case R.id.QQ_add:
                getShareData(this, groupBean.getGroupId(), ShareUtils.QQ);
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

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData_A(String filterStr) {

        List<Object> admins = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {
            admins.addAll(admin_datas_all);
        } else {
            admins.clear();
            List<GroupMemberBean.AdminMembersListBean> beens = new ArrayList<>();
            for (Object object : admin_datas_all) {
                GroupMemberBean.AdminMembersListBean admin = (GroupMemberBean.AdminMembersListBean) object;
                String name = admin.getUserRealName();
                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    beens.add(admin);
                }
            }
            sortRecentContactsModel(beens);
            admins.addAll(beens);
        }
        admin_datas.clear();
        admin_datas.addAll(admins);
        teacher_num.setText("管理员(" + admin_datas.size() + ")");
        teacher_adpter.notifyDataSetChanged();
    }

    private void filterData_G(String filterStr) {

        List<Object> admins = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {
            admins.addAll(genera_datas_all);
        } else {
            admins.clear();
            List<GroupMemberBean.GeneralMembersListBean> beens = new ArrayList<>();
            for (Object object : genera_datas_all) {
                GroupMemberBean.GeneralMembersListBean genera = (GroupMemberBean.GeneralMembersListBean) object;
                String name = genera.getUserRealName();
                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    beens.add(genera);
                }
            }
            admins.addAll(beens);
        }
        genera_datas.clear();
        genera_datas.addAll(admins);
        student_num.setText("普通成员(" + genera_datas.size() + ")");
        student_adpter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADDMEMBER && resultCode == OK) {
            EventBus.getDefault().post(new GetMembers("", 1));
        }

    }

    /**
     * 获取社群分享数据并予以分享
     *
     * @param groupId
     */
    private void getShareData(final BaseActivity activity, final String groupId, final int flag) {
        if (TextUtils.isEmpty(groupId)) return;
        Map<String, Object> map = new TreeMap<>();
        map.put("objectId", groupId);
        map.put("shareObjectType", 5);

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

    /**
     * **************************** 排序 ***********************************
     */

    private void sortRecentContactsModel(List<GroupMemberBean.AdminMembersListBean> list) {
        if (list.size() == 0) {
            return;
        }
        Collections.sort(list, comp);
    }

    private Comparator<GroupMemberBean.AdminMembersListBean> comp = new Comparator<GroupMemberBean.AdminMembersListBean>() {
        @Override
        public int compare(GroupMemberBean.AdminMembersListBean o1, GroupMemberBean.AdminMembersListBean o2) {
            int flag = 0;
            if (o1.isIsGroupMaster()) {
                flag = -1;
            } else {
                flag = 1;
            }
            return flag;
        }
    };

}
