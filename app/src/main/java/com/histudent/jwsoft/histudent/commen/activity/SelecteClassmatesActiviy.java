package com.histudent.jwsoft.histudent.commen.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.adapter.SortGroupAdapter;
import com.histudent.jwsoft.histudent.body.find.bean.ClassMateModel;
import com.histudent.jwsoft.histudent.body.find.helper.GroupHelper;
import com.histudent.jwsoft.histudent.body.hiworld.adapter.SelectedClamatesAdapter;
import com.histudent.jwsoft.histudent.body.hiworld.bean.SimpleUserModel;
import com.histudent.jwsoft.histudent.body.message.model.ClassModel;
import com.histudent.jwsoft.histudent.body.message.model.ClassTemberModel;
import com.histudent.jwsoft.histudent.body.mine.model.UserClassListModel;
import com.histudent.jwsoft.histudent.body.mine.parser.DataParser;
import com.histudent.jwsoft.histudent.body.myclass.activity.ContactActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.ComViewUitls;
import com.histudent.jwsoft.histudent.commen.utils.DataUtils;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.getSeletedUser;
import com.histudent.jwsoft.histudent.commen.view.CharacterParser;
import com.histudent.jwsoft.histudent.commen.view.EditText_clear;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.SideBar;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.histudent.jwsoft.histudent.comment2.utils.SeletClassMateEnum;
import com.histudent.jwsoft.histudent.comment2.utils.UserPinYinComparator;
import com.histudent.jwsoft.histudent.comment2.utils.ViewUtils;
import com.histudent.jwsoft.histudent.comment2.utils.GetDeleteUserCallBack;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;
import com.netease.nim.uikit.common.util.string.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.histudent.jwsoft.histudent.comment2.utils.SeletClassMateEnum.AT;
import static com.histudent.jwsoft.histudent.comment2.utils.SeletClassMateEnum.CLASSMATE;
import static com.histudent.jwsoft.histudent.comment2.utils.SeletClassMateEnum.GROUP;
import static com.histudent.jwsoft.histudent.comment2.utils.SeletClassMateEnum.REALTION;


/**
 * 选择关联人，at好友，班级通信录界面
 */
public class SelecteClassmatesActiviy extends BaseActivity {

    private SelectedClamatesAdapter allStuAdapter, searchAdapter;
    private SortGroupAdapter mAdapter;
    private List<Object> allList;
    private ArrayList<Object> selecteList;
    private ListView mListView;
    private EditText_clear editText_clear;
    private SideBar sideBar;
    private CharacterParser characterParser;
    private UserPinYinComparator pinyinComparator;
    private TextView titleMiddle;
    private IconView imgMiddle;
    private List<Object> mClassList;
    private String title, classId;
    private PopupWindow popupWindow;
    private getSeletedUser getSeletedUserId;
    private boolean isChanged;
    private SeletClassMateEnum type;
    private LinearLayout linearLayout;
    private ListView mSearchListView;
    private GetDeleteUserCallBack deleteUserCallBack;
    private List<Object> searchList;
    private TextView tv_sure;
    private String id;
    private TextView tittle_right;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_selecte_classmates_activiy;
    }

    public static void start(Activity activity, ArrayList<SimpleUserModel> userList, int requstCode, SeletClassMateEnum type) {

        Intent intent = new Intent(activity, SelecteClassmatesActiviy.class);
        intent.putExtra("userModel", userList);
        intent.putExtra("type", type);
        activity.startActivityForResult(intent, requstCode);
    }

    public static void start(Activity activity, ArrayList<SimpleUserModel> userList, int requstCode, SeletClassMateEnum type, String id) {

        Intent intent = new Intent(activity, SelecteClassmatesActiviy.class);
        intent.putExtra("userModel", userList);
        intent.putExtra("type", type);
        intent.putExtra("id", id);
        activity.startActivityForResult(intent, requstCode);
    }


    @Override
    public void initView() {
        type = ((SeletClassMateEnum) getIntent().getSerializableExtra("type"));
        id = getIntent().getStringExtra("id");
        allList = new ArrayList<>();
        searchList = new ArrayList<>();
        selecteList = new ArrayList<>();
        mListView = ((ListView) findViewById(R.id.list));
        mSearchListView = ((ListView) findViewById(R.id.list_02));
        editText_clear = ((EditText_clear) findViewById(R.id.filter_edit));
        sideBar = ((SideBar) findViewById(R.id.sidrbar));
        titleMiddle = ((TextView) findViewById(R.id.title_middle_text));
        imgMiddle = ((IconView) findViewById(R.id.title_middle_image));
        linearLayout = ((LinearLayout) findViewById(R.id.linear_person));
        tv_sure = ((TextView) findViewById(R.id.tv_sure));
        tittle_right = ((TextView) findViewById(R.id.title_right_text));
//        tv_sure.setEnabled(false);

        characterParser = CharacterParser.getInstance();
        mClassList = new ArrayList<>();

        getSeletedUserId = new getSeletedUser() {
            @Override
            public void getSelectedUser(SimpleUserModel user) {


                switch (type) {

                    case REALTION:

                        //社群邀请班级同学
                    case GROUP:
                        mListView.setVisibility(View.VISIBLE);
                        searchAdapter.notifyDataSetChanged();
                        mSearchListView.setVisibility(View.GONE);
                        searchList.clear();
                        addUserModel(user);
                        break;

                    case CLASSMATE:

                        if (user.getUserId().equals(HiCache.getInstance().getLoginUserInfo().getUserId())) {

                            PersonCenterActivity.start(SelecteClassmatesActiviy.this, user.getUserId());
                        } else {
                            ContactActivity.start(SelecteClassmatesActiviy.this, 444, user);
                        }
                        break;
                    case AT:
                        addUserModel(user);
                        setResult();
                        break;


                }


            }
        };

        deleteUserCallBack = new GetDeleteUserCallBack() {
            @Override
            public void getDeleteUserModel(SimpleUserModel user) {

                selecteList.remove(user);
                allStuAdapter.RefreshDateByUser(selecteList);
                isChanged = true;
                setSureButtonEnable();

            }
        };
        //班级学生
        allStuAdapter = new SelectedClamatesAdapter(allList, this, getSeletedUserId, type);
        searchAdapter = new SelectedClamatesAdapter(searchList, this, getSeletedUserId, type);
        mListView.setAdapter(allStuAdapter);
        mSearchListView.setAdapter(searchAdapter);

        imgMiddle.setText(R.string.icon_arrowbottom);

        switch (type) {

            //全部班级下的同学
            case AT:
                titleMiddle.setText("切换班级");
                getMyClasses();
                tittle_right.setVisibility(View.GONE);
                findViewById(R.id.title_right).setEnabled(false);
                break;

            //图片关联
            case REALTION:
            case GROUP:
                titleMiddle.setText("切换班级");
                getMyClasses();
                findViewById(R.id.rb_comment_relation).setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                params.setMargins(0, SystemUtil.dp2px(105), 0, SystemUtil.dp2px(55));
                findViewById(R.id.frame).setLayoutParams(params);
                break;

            //某一个班级下的同学
            case CLASSMATE:
                imgMiddle.setVisibility(View.GONE);
//                titleMiddle.setText("通讯录");
                ClassModel classModel = HiCache.getInstance().getClassDetailInfo();
                classId = classModel.getClassId();
                titleMiddle.setText(classModel.getGradeName() + classModel.getCName());
                getMember();

                break;

        }


        if (type == SeletClassMateEnum.REALTION) {
            selecteList.addAll((ArrayList) getIntent().getSerializableExtra("userModel"));

            if (selecteList.size() > 0) {
                addUsers();
            }
        }

        setListener();
        setSureButtonEnable();
    }

    private void setSureButtonEnable() {

        ViewUtils.changeClickable(this, tv_sure, selecteList.size() > 0);
    }

    private void setListener() {
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {

                if (!StringUtil.isEmpty(s)) {
                    mListView.setSelection(allStuAdapter.getSelection(s));
                }
            }
        });

        editText_clear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {

                if (!StringUtil.isEmpty(s + "")) {

                    searchUser(s + "");
                } else {
                    mListView.setVisibility(View.VISIBLE);
                    mSearchListView.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void addUsers() {
        linearLayout.removeAllViews();
        for (Object m : selecteList) {
            SimpleUserModel model = ((SimpleUserModel) m);
            ComViewUitls.addView(this, linearLayout, model, deleteUserCallBack);
        }
    }

    private void addUserModel(SimpleUserModel user) {
        isChanged = true;
        boolean isExsit = false;
        if (selecteList != null && user != null) {
            if (selecteList.size() != 0) {
                for (int i = 0; i < selecteList.size(); i++) {
                    if (user.equals(selecteList.get(i))) {
                        isExsit = true;
                        break;
                    }
                }
            }
            //不存在添加
            if (!isExsit) {
                selecteList.add(user);
            } else {
                selecteList.remove(user);
            }

            if (type == SeletClassMateEnum.REALTION || type == SeletClassMateEnum.GROUP) {
                findViewById(R.id.rb_comment_relation).setVisibility(View.VISIBLE);
                addUsers();
                setSureButtonEnable();
            }
            if (type == AT) {

                ViewUtils.changeTitleRightClickable(SelecteClassmatesActiviy.this, searchList.size() > 0);

            }
        }
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            //点击中间,关联图片时可以切换班级
            case R.id.title_middle:
                if (type != SeletClassMateEnum.CLASSMATE) {
                    if (mClassList.size() > 1) {
                        imgMiddle.setText(R.string.icon_arrowup);
                        initPopWindow();
                    }
                }

                break;

            //点击确定
            case R.id.tv_sure:
                setResult();
                break;

            case R.id.title_left:

                if (type == REALTION && selecteList.size() > 0) {
                    ReminderHelper.getIntentce().showDialog(SelecteClassmatesActiviy.this, "提示", "是否确定取消？", "确定", new DialogButtonListener() {
                        @Override
                        public void setOnDialogButtonListener() {
                            isChanged = false;
                            setResult();
                        }
                    }, "取消", new DialogButtonListener() {
                        @Override
                        public void setOnDialogButtonListener() {

                        }
                    });
                } else {
                    setResult(-1);
                    finish();
                }

                break;
            case R.id.title_right:
                if (type == AT)
                    setResult();
                break;
        }
    }


    //图片关联时切换班级

    private void initPopWindow() {
        View layout = View.inflate(this, R.layout.same_city_popwindow, null);
        findViewById(R.id.frame1).setVisibility(View.VISIBLE);
        layout.findViewById(R.id.line).setVisibility(View.VISIBLE);
        final ListView listView = ((ListView) layout.findViewById(R.id.list_view));
        mAdapter = new SortGroupAdapter(mClassList, this);
        listView.setAdapter(mAdapter);
        mAdapter.initSeletedItem(classId);
        popupWindow = new PopupWindow(layout, SystemUtil.getScreenWind(), LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(R.style.PopupAnimation);

//        int xoff = SystemUtil.getScreenWind() / 2 - 120;
        popupWindow.showAsDropDown(findViewById(R.id.title_middle), 0, 0);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                imgMiddle.setText(R.string.icon_arrowbottom);
                findViewById(R.id.frame1).setVisibility(View.GONE);

            }
        });


        //切换班级监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击条目的事件
                TextView tv = ((TextView) view.findViewById(R.id.tv_name));
                tv.setTextColor(Color.rgb(32, 126, 189));

                UserClassListModel bean = ((UserClassListModel) mClassList.get(position));

                if (!StringUtil.isEmpty(bean.getClassId()) && !bean.getClassId().equals(classId)) {
                    classId = bean.getClassId();
                    mAdapter.initSeletedItem(classId);
                    title = bean.getCName();
                    titleMiddle.setText(title);
                    if (type != SeletClassMateEnum.GROUP) {
                        getMembers();
                    } else {
                        getGroupnoInvitationMembers(SelecteClassmatesActiviy.this.id, classId, "");
                    }

                }

                popupWindow.dismiss();
            }
        });
    }

    //获取同步班级列表 图片关联时用
    private void getMyClasses() {

        DataUtils.GetUserSyncClassList(this, false, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.e("onSuccess", result);
                mClassList.clear();

                mClassList.addAll(DataParser.getUserClassList(result));
                if (mClassList != null && mClassList.size() > 0) {
                    if (mClassList.size() > 1) {
                        imgMiddle.setVisibility(View.VISIBLE);
                    } else {
                        imgMiddle.setVisibility(View.GONE);
                    }

                    title = ((UserClassListModel) mClassList.get(0)).getCName();
                    Log.e("getGradeName", title);
                    classId = ((UserClassListModel) mClassList.get(0)).getClassId();
                    titleMiddle.setText(title);

                    if (type == GROUP) {
                        getGroupnoInvitationMembers(id, classId, "");
                    } else if (type != CLASSMATE) {
                        getMembers();
                    } else {
                        getMember();
                    }

                } else {
                    Toast.makeText(SelecteClassmatesActiviy.this, "未加入任何班级！", Toast.LENGTH_LONG).show();
                    setResult();
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        }, LoadingType.FLOWER);
    }


    //获取某个班级下班级成员.图片关联人用
    private void getMembers() {

        Map<String, Object> map = new TreeMap<>();
        map.put("classId", classId);
        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.getAssociateClassMembers, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {


                Log.e("---", result);
                exchangeListData(JSON.parseArray(result, ClassTemberModel.class));
                allStuAdapter.notifyDataSetChanged();
                allStuAdapter.RefreshDateByUser(selecteList);//用户刷新已经选择的学生的数据显示
            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }


    //获取某个班级下班级成员,获取班级同学用
    private void getMember() {

        Map<String, Object> map = new TreeMap<>();
        map.put("classId", classId);
        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.getClassTeamber_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {


                JSONObject object = null;
                try {
                    object = new JSONObject(result);
                    List<ClassTemberModel> models = JSON.parseArray(object.optString("teaClassMembers"), ClassTemberModel.class);
                    List<ClassTemberModel> student_models = JSON.parseArray(object.optString("stuClassMembers"), ClassTemberModel.class);
                    models.addAll(student_models);

                    exchangeListData(models);
                    if (models.size() == 0) {
                        mListView.setEmptyView(EmptyViewUtils.EmptyViewTextAndImage(R.drawable.no_join_class,
                                SelecteClassmatesActiviy.this, R.string.empty_no_class_info));
                    }
                    allStuAdapter.notifyDataSetChanged();
                    allStuAdapter.RefreshDateByUser(selecteList);//用户刷新已经选择的学生的数据显示
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }

    private void exchangeListData(List<ClassTemberModel> list) {
        allList.clear();

        SimpleUserModel masker = null;
        for (ClassTemberModel item : list) {

            SimpleUserModel model = new SimpleUserModel(item.getUserAvatar(), item.getUserRealName(), item.getUserId(), item.getUserMobile());
            model.setAdmin(item.isIsAdmin());
            model.setMasker(item.isIsClassMaker());
            model.setType(item.getUserType());

            if (model.isMasker()) {
                masker = model;
            }
            allList.add(model);
        }
        sortData();
        //班级通讯录班主任另加排第一
        if (type == CLASSMATE) {
//            allList.remove(masker);
            allList.add(0, masker);
        }
    }

    private void ExchangeList(List<ClassMateModel> list) {
        allList.clear();

        SimpleUserModel masker = null;
        for (ClassMateModel item : list) {

            SimpleUserModel model = new SimpleUserModel(item.getUserAvatar(), item.getRealName(), item.getUserId(), item.getUserMobile());
            allList.add(model);
        }
        sortData();
    }

    //返回被选中用户的列表
    private void setResult() {

        if (isChanged) {
            Intent intent = new Intent();
            intent.putExtra("userModel", selecteList);
            setResult(200, intent);
            Log.e("selecteList", ((ArrayList) intent.getSerializableExtra("userModel")).toString());

        } else {
            setResult(-2);
        }
        finish();
    }


    //将获取的用户进行排序
    private void sortData() {

        if (allList == null || allList.size() < 1) return;
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new UserPinYinComparator();
        Collections.sort(allList, pinyinComparator);

        List<Object> list = new ArrayList<>();
        list.addAll(allList);
        allList.clear();
        SimpleUserModel model = ((SimpleUserModel) list.get(0));
        String first = characterParser.getSelling(model.getName()).toUpperCase().substring(0, 1);

        //添加第一个
        allList.add(first);
        allList.add(model);
        for (int i = 1; i < list.size(); i++) {
            SimpleUserModel model2 = ((SimpleUserModel) list.get(i));
            if (!StringUtil.isEmpty(model2.getName().trim())) {
                String second = characterParser.getSelling(model2.getName()).toUpperCase().substring(0, 1);
                if (!first.equals(second)) {
                    first = second;
                    allList.add(first);
                }
            }
            allList.add(model2);
        }

    }


    //关键字搜索
    private void searchUser(String user) {

        boolean tag = false;
        searchList.clear();//每次数据清空

        for (Object item : allList) {

            if (item instanceof SimpleUserModel) {
                SimpleUserModel model = ((SimpleUserModel) item);
                if (characterParser.getSelling(model.getName()).toUpperCase().contains(user.toUpperCase())) {
                    searchList.add(model);
                    tag = true;
                } else if (model.getName().contains(user)) {
                    searchList.add(model);
                    tag = true;
                }

                //检查手机号
                if (!StringUtil.isEmpty(user)) {

                    //匹配手机号
                    if (!StringUtil.isEmpty(model.getUserMobile()) && model.getUserMobile().contains(user))
                        searchList.add(model);
                    tag = true;
                }
            }
        }
        if (!tag) {
            searchList.add(new String("     没有找到任何对象！"));
        }
        mListView.setVisibility(View.GONE);
        mSearchListView.setVisibility(View.VISIBLE);
        searchAdapter.notifyDataSetChanged();
    }


    //获取社群中某个班级下未被邀请的同学列表
    private void getGroupnoInvitationMembers(String id, String classId, String key) {

        GroupHelper.getGroupnoInvitationMembers(this, id, classId, key, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                List<ClassMateModel> list = JSON.parseArray(result, ClassMateModel.class);
                if (list != null) {
                    ExchangeList(list);
                    allStuAdapter.notifyDataSetChanged();
                    if (selecteList != null)
                        allStuAdapter.RefreshDateByUser(selecteList);//用户刷新已经选
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

    }

}
