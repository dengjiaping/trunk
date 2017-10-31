package com.histudent.jwsoft.histudent.activity.homework;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.adapter.decoration.Divider;
import com.histudent.jwsoft.histudent.adapter.homework.convert.HomeworkGroupMemberDataConvert;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkSelectGroupL0Bean;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkSelectGroupL1Bean;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkSelectPersonExpandableAdapter;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.constant.ParamKeys;
import com.histudent.jwsoft.histudent.constant.TransferKeys;
import com.histudent.jwsoft.histudent.entity.WorkReceiverEvent;
import com.histudent.jwsoft.histudent.listener.homework.OnItemSelectReceiverPersonListener;
import com.histudent.jwsoft.histudent.manage.ParamsManager;
import com.histudent.jwsoft.histudent.manage.UserManager;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lichaojie on 2017/10/26.
 * desc:
 * 发布作业-选择接收人
 */

public class HomeworkSelectReceiverPersonActivity extends BaseActivity {

    @BindView(R.id.title_middle_text)
    TextView mTvTitleMiddleText;
    @BindView(R.id.title_right_text)
    TextView mIvTitleRightText;
    @BindView(R.id.rv_select_group)
    RecyclerView mRvSelectGroup;

    private List<MultiItemEntity> mListData = new ArrayList<>();
    private HomeworkSelectPersonExpandableAdapter mPersonExpandableAdapter;
    private final List<String> mUserSelectTeamIdList = new ArrayList<>();
    private List<HomeworkSelectGroupL0Bean> entitys = new ArrayList<>();

    @OnClick(R.id.title_left)
    void finishPage() {
        finish();
    }

    @OnClick(R.id.title_right_text)
    void confirm() {
        mUserSelectTeamIdList.clear();
        for (MultiItemEntity multiItemEntity : mListData) {

            if (multiItemEntity instanceof HomeworkSelectGroupL0Bean) {
                final HomeworkSelectGroupL0Bean entity = (HomeworkSelectGroupL0Bean) multiItemEntity;
                entitys.add(entity);

            }
        }
        EventBus.getDefault().postSticky(new WorkReceiverEvent(entitys));
//        HiStudentLog.i(TAG, "confirm: userSelectTeamIdList----->" + mUserSelectTeamIdList.toString());
//        final Intent intent = new Intent();
//        intent.putExtra(TransferKeys.TEAM_ID, (Serializable) entitys);
//        setResult(TransferKeys.ConstantNum.NUM_2002, intent);
        finish();
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_homework_select_receiver_person;
    }

    @Override
    public void initView() {
        mTvTitleMiddleText.setText("接收人");
        mIvTitleRightText.setText(R.string.confirm);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        final Divider divider = new Divider(ContextCompat.getDrawable(this, R.drawable.divider_line), LinearLayoutManager.VERTICAL);
        divider.setMargin(SystemUtil.dp2px(12), 0, 0, 0);
        mRvSelectGroup.setLayoutManager(linearLayoutManager);
        mRvSelectGroup.addItemDecoration(divider);
    }

    @Override
    public void doAction() {
        super.doAction();
        requestData();
        mPersonExpandableAdapter = HomeworkSelectPersonExpandableAdapter.create(mListData);
        mRvSelectGroup.setAdapter(mPersonExpandableAdapter);
        mRvSelectGroup.addOnItemTouchListener(OnItemSelectReceiverPersonListener.create(this, mListData));
    }

    private static final String TAG = HomeworkSelectReceiverPersonActivity.class.getName();

    private void requestData() {
        final String currentClassId = UserManager.getInstance().getCurrentClassId();
        final Map<String, Object> paramsMap = ParamsManager.getInstance()
                .setParams(ParamKeys.CLASS_ID, currentClassId)
                .getParamsMap();
        HiStudentHttpUtils.postDataByOKHttp(this, paramsMap, HistudentUrl.TEACHER_HOMEWORK_LIST_TEAM, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, "onSuccess: result---->" + result);
                final List<HomeworkSelectGroupL0Bean> list = HomeworkGroupMemberDataConvert.create(result).convert();
                mListData.clear();
                mListData.addAll(list);
                mPersonExpandableAdapter.setNewData(mListData);
                mPersonExpandableAdapter.expandAll();
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //重新刷新数据 分组成员已经发
        if (intent != null) {
            final String classId = intent.getStringExtra(TransferKeys.CLASS_ID);
            final String groupId = intent.getStringExtra(TransferKeys.GROUP_ID);
            if (classId != null || groupId != null) {
                requestData();
            }
        }
    }
}
