package com.histudent.jwsoft.histudent.comment2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.EditText_clear;
import com.histudent.jwsoft.histudent.comment2.adapter.CommentSelectAdapter;
import com.histudent.jwsoft.histudent.comment2.bean.ClassSelectModel;
import com.histudent.jwsoft.histudent.comment2.bean.GradeBean;
import com.histudent.jwsoft.histudent.comment2.utils.ActivityCollector;
import com.histudent.jwsoft.histudent.comment2.utils.EditGrouoOrClassViewUitils;
import com.histudent.jwsoft.histudent.comment2.utils.EditGroupOrClassTypeEnum;
import com.histudent.jwsoft.histudent.comment2.utils.GroupOrClassActionUtils;
import com.histudent.jwsoft.histudent.comment2.utils.PullToRefreshUtils;
import com.netease.nim.uikit.common.util.string.StringUtil;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

//班级信息修改选择界面

public class CommenSelectActivity extends BaseActivity implements View.OnClickListener {

    private EditText_clear clearEditText;
    private TextView tv_address, tiitle_middle;
    private PullToRefreshListView mListView;
    private LinearLayout linear_address;
    private EditGroupOrClassTypeEnum actionEnum;
    private Intent intent;
    private GroupOrClassActionUtils actionUtils;
    private GroupOrClassActionUtils.isSuccessCallBack isSuccessCallBack;
    private List<Object> mList;
    private LinearLayout parent;
    private CommentSelectAdapter mAdapter;
    private boolean isChanged, isStudent;
    private int currentPageIndex;
    private Object item;
    private ClassSelectModel selectModel;
    private String keyName, city, area;
    private TextView mSearch;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case 2:

                    if (msg.arg1 == HiWorldCache.SUCCESS) {

                        if (!StringUtil.isEmpty(msg.obj.toString())) {
                            JSONObject o = JSON.parseObject(msg.obj.toString());
                            if (o.getIntValue("status") == 1) {
                                if (!StringUtil.isEmpty(o.getString("cId"))) {
                                    joinClassAction(o.getString("cId"));
                                }
                            } else {
                                EditGrouoOrClassViewUitils.setResult(CommenSelectActivity.this, intent, actionEnum,
                                        EditGrouoOrClassViewUitils.onItemSelected(actionEnum, item), isChanged);
                            }
                        }
                    }

                    Log.e("ClassIsExsit", msg.obj.toString());
                    break;

                case 1:

                    if (msg.arg1 == HiWorldCache.SUCCESS) {

                        Toast.makeText(CommenSelectActivity.this, "加入班级成功！", Toast.LENGTH_LONG).show();
                        setResult(-2000);
                        CommenSelectActivity.this.finish();
                        ActivityCollector.finishAll();

                    } else {

                        Bundle bundle = msg.getData();
                        if (bundle != null) {

                            //失败时，获取失败消息，如果是已经是成语昂，则跳转到班级页面，否则只是推出该页面
                            String mess = bundle.getString("errorMsg");


                            if (!StringUtil.isEmpty(mess)) {
                                if (mess.equals("你已是该班级成员，请勿重复加入！")) {
                                    Log.e("mess", mess);
                                    ActivityCollector.finishAll();
                                }
                            }
                        }
                        setResult(0);
                        CommenSelectActivity.this.finish();

                    }

                    Log.e("JoinClass", msg.obj.toString());
                    break;
            }
        }
    };


    @Override
    public int setViewLayout() {
        return R.layout.activity_commen_select;
    }

    public static void start(Activity activity, EditGroupOrClassTypeEnum actionEnum, String schoolId,
                             String gradeName, String classId, String eduSystemId, String shoolName, String cityName, String areaName) {
        Intent intent = new Intent(activity, CommenSelectActivity.class);
        ClassSelectModel selectModel = ClassSelectModel.getIntent();
        selectModel.setSchoolId(schoolId);
        selectModel.setSchoolName(shoolName);
        selectModel.setGradeName(gradeName);
        selectModel.setClassId(classId);
        selectModel.setEduSystemId(eduSystemId);
        selectModel.setCityName(cityName);
        selectModel.setAreaName(areaName);
        intent.putExtra("actionEnum", actionEnum);
        intent.putExtra("selectModel", selectModel);
        activity.startActivityForResult(intent, 400);
    }

    public static void start(Activity activity, EditGroupOrClassTypeEnum actionEnum, String schoolId,
                             String gradeName, String classId, String eduSystemId, String shoolName, String cityName, String areaName, int requestCode) {
        Intent intent = new Intent(activity, CommenSelectActivity.class);
        ClassSelectModel selectModel = ClassSelectModel.getIntent();
        selectModel.setSchoolId(schoolId);
        selectModel.setSchoolName(shoolName);
        selectModel.setGradeName(gradeName);
        selectModel.setClassId(classId);
        selectModel.setEduSystemId(eduSystemId);
        selectModel.setCityName(cityName);
        selectModel.setAreaName(areaName);
        intent.putExtra("actionEnum", actionEnum);
        intent.putExtra("selectModel", selectModel);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void start(Activity activity, EditGroupOrClassTypeEnum actionEnum, ClassSelectModel selectModel, int requestCode) {
        Intent intent = new Intent(activity, CommenSelectActivity.class);
        intent.putExtra("actionEnum", actionEnum);
        intent.putExtra("selectModel", selectModel);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void start(Activity activity, EditGroupOrClassTypeEnum actionEnum, ClassSelectModel selectModel, int requestCode, boolean isStudent) {
        Intent intent = new Intent(activity, CommenSelectActivity.class);
        intent.putExtra("actionEnum", actionEnum);
        intent.putExtra("selectModel", selectModel);
        intent.putExtra("isStudent", isStudent);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void initView() {
        intent = getIntent();
        actionEnum = ((EditGroupOrClassTypeEnum) intent.getSerializableExtra("actionEnum"));
        selectModel = (ClassSelectModel) intent.getSerializableExtra("selectModel");
        isStudent = intent.getBooleanExtra("isStudent", false);
        clearEditText = ((EditText_clear) findViewById(R.id.filter_edit));
        tiitle_middle = ((TextView) findViewById(R.id.title_middle_text));
        tv_address = ((TextView) findViewById(R.id.tv_));
        mListView = ((PullToRefreshListView) findViewById(R.id.list_view));
        linear_address = (LinearLayout) findViewById(R.id.linear);
        parent = ((LinearLayout) findViewById(R.id.activity_commen_select));
        mSearch = (TextView) findViewById(R.id.tv_search);
        mList = new ArrayList<>();
        mAdapter = new CommentSelectAdapter(mList, this);
        mListView.setAdapter(mAdapter);
        mListView.getRefreshableView().setDividerHeight(0);
        linear_address.setOnClickListener(this);
        initUtils();
        initPullToRefreshListView();
        EditGrouoOrClassViewUitils.initEditClassActionView(actionEnum, findViewById(R.id.activity_commen_select), actionUtils, selectModel, mList);
        loadListener();
    }

    private void loadListener() {
        mListView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            isChanged = true;
            item = mList.get(position - 1);
            takeActionOnItemClick(item);
        });

        clearEditText.addTextChangedListener(mClearEditTextWatcher);

        mSearch.setOnClickListener((View v) -> doSearchContent());

    }

    private void doSearchContent() {
        String keyName = clearEditText.getText().toString().trim();
        String address = tv_address.getText().toString().trim();

        String city = "", area = "";
        if (!StringUtil.isEmpty(address)) {
            if (address.contains("—")) {
                city = address.substring(0, address.indexOf("—"));
                area = address.substring(address.indexOf("—") + 1, address.length());
            } else {
                city = address;
            }
        }
        actionUtils.getSchool(keyName, area, city, 0, mList, false);
    }

    private void initPullToRefreshListView() {
        if (actionEnum == EditGroupOrClassTypeEnum.EditSchoolName || actionEnum == EditGroupOrClassTypeEnum.AddSchool) {

            PullToRefreshUtils.initPullToRefreshListView2(mListView);

            mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                    getSearchInfor();
                    currentPageIndex = 0;
                    actionUtils.getSchool(keyName, area, city, currentPageIndex, mList, false);

                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    getSearchInfor();
                    currentPageIndex++;
                    actionUtils.getSchool(keyName, area, city, currentPageIndex, mList, true);

                }
            });
        }

    }

    private void initUtils() {
        isSuccessCallBack = (int type, boolean isSuccess) -> {
            if (mListView.isRefreshing()) {
                mListView.onRefreshComplete();
            }
            mAdapter.notifyDataSetChanged();
        };
        actionUtils = new GroupOrClassActionUtils(this, isSuccessCallBack);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left:
                if (isChanged) {
                    EditGrouoOrClassViewUitils.showNoticeDialog(this);
                } else {
                    EditGrouoOrClassViewUitils.setResult(this, intent, actionEnum, null, false);
                }

                break;

            case R.id.linear_address:
                getSearchInfor();
                SelectAddressActivity.start(this, city, area, 3);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null)
            return;
        switch (resultCode) {
            case 200:
                if (requestCode == 300) {
                    String cityName = data.getStringExtra("city");
                    String areaName = data.getStringExtra("area");

                    selectModel.setCityName(cityName);
                    selectModel.setAreaName(areaName);
                    tv_address.setText(cityName + ((StringUtil.isEmpty(areaName)) == true ? "" : "—" + areaName));

                    getSearchInfor();
                    actionUtils.getSchool(keyName, areaName, cityName, 0, mList, false);
                }
                break;
        }
    }

    //点击每个条目时的操作
    private void takeActionOnItemClick(final Object item) {

        if (actionEnum == EditGroupOrClassTypeEnum.EditClasses || actionEnum == EditGroupOrClassTypeEnum.AddClass) {
            GradeBean classBean = ((GradeBean) item);
            String className = classBean.getName();
            if (isStudent) {
                judgeClassExist(selectModel, className);
            } else {
                GroupOrClassActionUtils.ClassIsExsit(this, selectModel.getSchoolId(), selectModel.getEduSystemId(), selectModel.getGradeName(), className, mHandler);
            }
        } else {

            EditGrouoOrClassViewUitils.setResult(CommenSelectActivity.this, intent, actionEnum,
                    EditGrouoOrClassViewUitils.onItemSelected(actionEnum, item), isChanged);

        }
    }

    //初始化上下拉 刷新搜索字段
    private void getSearchInfor() {

        String addres = ((TextView) findViewById(R.id.tv_)).getText().toString();
        keyName = clearEditText.getText().toString();

        if (!StringUtil.isEmpty(addres)) {

            if (addres.contains("—")) {
                city = addres.substring(0, addres.indexOf("—"));
                area = addres.substring(addres.indexOf("—") + 1, addres.length());
            } else {
                city = addres;
                area = "";
            }
        }
    }

    //班级存在时加入班级操作
    private void joinClassAction(String classId) {

        GroupOrClassActionUtils.TrasferOrJoinClassOnCreatingClass(this, classId, mHandler);

    }

    /**
     * 判断班级是否存在
     */
    private void judgeClassExist(ClassSelectModel selectModel, final String className) {
        if (selectModel == null) return;
        Map<String, Object> map = new TreeMap<>();
        map.put("orgId", selectModel.getSchoolId());
        map.put("eduId", selectModel.getEduSystemId());
        map.put("gradeName", selectModel.getGradeName());
        map.put("className", className);
        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.classIsExsit, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                try {
                    org.json.JSONObject object = new org.json.JSONObject(result);
                    String cId = object.optString("cId");
                    Intent intent = new Intent();
                    intent.putExtra("classId", cId);
                    intent.putExtra("myclass", className);
                    setResult(200, intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }


    private TextWatcher mClearEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(s)) {
                mSearch.setText("取消");
            } else {
                mSearch.setText("搜索");
            }
        }
    };


}
