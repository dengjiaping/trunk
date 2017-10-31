package com.histudent.jwsoft.histudent.activity.homework;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.google.gson.Gson;
import com.histudent.jwsoft.histudent.HiStudentApplication;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.adapter.decoration.Divider;
import com.histudent.jwsoft.histudent.adapter.homework.HomeworkCreateDivideGroupAdapter;
import com.histudent.jwsoft.histudent.adapter.homework.convert.HomeworkGroupDetailDataConvert;
import com.histudent.jwsoft.histudent.bean.homework.CommonMemberBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.constant.ParamKeys;
import com.histudent.jwsoft.histudent.constant.TransferKeys;
import com.histudent.jwsoft.histudent.manage.ParamsManager;
import com.histudent.jwsoft.histudent.tool.CommonAdvanceUtils;
import com.histudent.jwsoft.histudent.tool.ToastTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lichaojie on 2017/10/24.
 * desc:
 * 创建作业分组
 * 跳转来源
 * 1.接收人页面
 * 2.小组成员详细列表页面(右上角编辑)
 */

public class HomeworkCreateDivideGroupActivity extends BaseActivity {


    @BindView(R.id.title_middle_text)
    TextView mTvTitleMiddleText;
    @BindView(R.id.title_right_text)
    TextView mTvTitleRightText;

    @BindView(R.id.et_input_divide_name)
    EditText mEtInputDivideName;

    @BindView(R.id.tv_divide_group_member_count)
    TextView mTvDivideGroupMember;

    @BindView(R.id.ll_add_layout)
    LinearLayout mLLAddLayoutHead;

    @BindView(R.id.rv_divide_group)
    RecyclerView mDivideGroupRecyclerView;

    private final ArrayList<CommonMemberBean> mDivideGroupBeanList = new ArrayList<>();
    private HomeworkCreateDivideGroupAdapter mDivideGroupAdapter;
    private String mClassId;
    private List<String> userIds = new ArrayList<>();

    /**
     * 0:添加分组
     * 1:修改分组
     */
    private int mType = -1;
    private String mGroupId;

    @OnClick({R.id.title_left, R.id.ll_add_layout, R.id.title_right_text})
    void onClickView(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                this.finish();
                break;
            case R.id.ll_add_layout:
                jumpAddMemberPage();
                break;
            case R.id.title_right_text:
                saveBuildGroup();
                break;
        }
    }

    private void jumpAddMemberPage() {
        final Intent intent = new Intent(HomeworkCreateDivideGroupActivity.this, HomeworkAddMemberActivity.class);
        intent.putParcelableArrayListExtra(TransferKeys.ADD_MEMBER, mDivideGroupBeanList);
        intent.putExtra(TransferKeys.CLASS_ID, mClassId);
        CommonAdvanceUtils.startActivityForResult(HomeworkCreateDivideGroupActivity.this, intent, TransferKeys.ConstantNum.NUM_2000);
    }


    @Override
    public int setViewLayout() {
        return R.layout.activity_create_homework_divide_group;
    }

    @Override
    public void initView() {
        mTvTitleMiddleText.setText("创建作业分组");
        mTvTitleRightText.setText("保存");
        ((TextView) findViewById(R.id.tv_add)).setText("添加成员");
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        final Divider divider = new Divider(ContextCompat.getDrawable(this, R.drawable.divider_line), LinearLayoutManager.VERTICAL);
        divider.setMargin(SystemUtil.dp2px(12), 0, 0, 0);
        mDivideGroupRecyclerView.setLayoutManager(linearLayoutManager);
        mDivideGroupRecyclerView.addItemDecoration(divider);
    }

    @Override
    public void doAction() {
        super.doAction();
        final Intent intent = getIntent();
        mType = intent.getIntExtra(TransferKeys.TYPE, -1);
        mGroupId = intent.getStringExtra(TransferKeys.GROUP_ID);
        mClassId = intent.getStringExtra(TransferKeys.CLASS_ID);
        if (mType == 1) {
            //修改分组
            requestData();
            mLLAddLayoutHead.setVisibility(View.GONE);
        } else {
            mLLAddLayoutHead.setVisibility(View.VISIBLE);
        }
        mDivideGroupAdapter = HomeworkCreateDivideGroupAdapter.create(R.layout.item_create_homework_divide_group, mDivideGroupBeanList);
        mDivideGroupAdapter.setType(1);
        mDivideGroupRecyclerView.setAdapter(mDivideGroupAdapter);
        mDivideGroupRecyclerView.addOnItemTouchListener(new OnItemCreateDivideGroupListener());
        mEtInputDivideName.addTextChangedListener(new InputTextWatcher());
    }

    private void requestData() {
        final Map<String, Object> paramsMap = ParamsManager.getInstance().setParams(ParamKeys.TEAM_ID, mGroupId).getParamsMap();
        HiStudentHttpUtils.postDataByOKHttp(this, paramsMap, HistudentUrl.TEACHER_HOMEWORK_LIST_TEAM_MEMBER, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                final List<CommonMemberBean> convert = HomeworkGroupDetailDataConvert.create(result).convert();
                final String teamName = convert.get(0).getTeamName();
                mEtInputDivideName.setText(teamName);
                mDivideGroupBeanList.clear();
                mDivideGroupBeanList.addAll(HomeworkGroupDetailDataConvert.create(result).convert());
                mDivideGroupAdapter.setNewData(mDivideGroupBeanList);
                mTvDivideGroupMember.setText("分组成员(" + mDivideGroupBeanList.size() + ")");
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    private final class OnItemCreateDivideGroupListener extends OnItemChildClickListener {

        @Override
        public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            final int id = view.getId();
            if (id == R.id.ll_delete_add_group_member) {
                mDivideGroupBeanList.remove(position);
                mDivideGroupAdapter.notifyDataSetChanged();
                updateDivideStatus();
            }
            if (id == R.id.ll_add_layout) {
                jumpAddMemberPage();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TransferKeys.ConstantNum.NUM_2000 && resultCode == TransferKeys.ConstantNum.NUM_2001) {
            if (data != null) {
                final ArrayList<CommonMemberBean> commonMemberBean = data.getParcelableArrayListExtra(TransferKeys.ADD_MEMBER);
                mDivideGroupBeanList.clear();
                mDivideGroupBeanList.addAll(commonMemberBean);
                mDivideGroupAdapter.notifyDataSetChanged();
                updateDivideStatus();
            }
        }
    }

    private void updateDivideStatus() {
        mTvDivideGroupMember.setText("分组成员(" + mDivideGroupBeanList.size() + ")");
        if (mDivideGroupBeanList.size() > 0) {
            mLLAddLayoutHead.setVisibility(View.GONE);
        }
    }

    private final class InputTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!TextUtils.isEmpty(s)) {
                mTvTitleRightText.setTextColor(ContextCompat.getColor(HiStudentApplication.getInstance(), R.color._28ca7e));
                mTvTitleRightText.setEnabled(true);
            } else {
                mTvTitleRightText.setTextColor(ContextCompat.getColor(HiStudentApplication.getInstance(), R.color._333333));
                mTvTitleRightText.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private void saveBuildGroup() {
        //处理studentId
        final String groupName = mEtInputDivideName.getText().toString().trim();
        if (TextUtils.isEmpty(groupName)) {
            ToastTool.showCommonToast("组名不可以为空");
            return;
        }
        if (mDivideGroupBeanList.size() == 0) {
            ToastTool.showCommonToast("请先添加成员");
            return;
        }
        final int size = mDivideGroupBeanList.size();
        for (int i = 0; i < size; i++) {
            userIds.add(mDivideGroupBeanList.get(i).getUserId());
        }

        String studentId = new Gson().toJson(userIds);
        if (mType == 0) {
            createGroup(studentId, groupName);
        } else if (mType == 1) {
            modifyGroup(studentId, groupName);
        }
    }

    private void modifyGroup(String studentId, String groupName) {
        final Map<String, Object> paramsMap = ParamsManager.getInstance()
                .setParams(ParamKeys.NAME, groupName)
                .setParams(ParamKeys.TEAM_ID, mGroupId)
                .setParams(ParamKeys.STUDENT_ID, studentId)
                .getParamsMap();
        HiStudentHttpUtils.postDataByOKHttp(this, paramsMap, HistudentUrl.TEACHER_HOMEWORK_EDIT_TEAM, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                final Intent intent = new Intent(HomeworkCreateDivideGroupActivity.this, HomeworkReceiverPersonDetailActivity.class);
                intent.putExtra(TransferKeys.CLASS_ID, mClassId);
                intent.putExtra(TransferKeys.GROUP_ID, mGroupId);
                CommonAdvanceUtils.startActivity(HomeworkCreateDivideGroupActivity.this, intent);
                finish();
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    private void createGroup(String studentId, String groupName) {
        final Map<String, Object> paramsMap = ParamsManager.getInstance()
                .setParams(ParamKeys.NAME, groupName)
                .setParams(ParamKeys.CLASS_ID, mClassId)
                .setParams(ParamKeys.STUDENT_ID, studentId)
                .getParamsMap();
        HiStudentHttpUtils.postDataByOKHttp(this, paramsMap, HistudentUrl.TEACHER_HOMEWORK_ADD_TEAM, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                final Intent intent = new Intent(HomeworkCreateDivideGroupActivity.this, HomeworkSelectReceiverPersonActivity.class);
                intent.putExtra(TransferKeys.CLASS_ID, mClassId);
                CommonAdvanceUtils.startActivity(HomeworkCreateDivideGroupActivity.this, intent);
                finish();
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    public List<CommonMemberBean> getListData() {
        return mDivideGroupBeanList;
    }
}
