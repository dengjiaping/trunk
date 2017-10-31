package com.histudent.jwsoft.histudent.activity.homework;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.adapter.decoration.Divider;
import com.histudent.jwsoft.histudent.adapter.homework.HomeworkSubjectManageAdapter;
import com.histudent.jwsoft.histudent.adapter.homework.convert.HomeworkSubjectDataConvert;
import com.histudent.jwsoft.histudent.bean.homework.CommonSubjectBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.constant.ParamKeys;
import com.histudent.jwsoft.histudent.constant.TransferKeys;
import com.histudent.jwsoft.histudent.dialog.CommonInputDialog;
import com.histudent.jwsoft.histudent.manage.ParamsManager;
import com.histudent.jwsoft.histudent.tool.ToastTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lichaojie on 2017/10/25.
 * desc:
 * 科目管理
 */

public class HomeworkSubjectManageActivity extends BaseActivity {

    @BindView(R.id.title_middle_text)
    TextView mTvTitleMiddleText;
    @BindView(R.id.title_right_text)
    TextView mIvTitleRightText;
    @BindView(R.id.rv_subject_manage)
    RecyclerView mRvReceiverDetail;

    private final List<CommonSubjectBean> mCommonSubjectBeanList = new ArrayList<>();
    private CommonSubjectBean mCurrentSubjectItem = null;
    private HomeworkSubjectManageAdapter mSubjectManageAdapter;
    private int mPrePosition = 0;
    private CommonInputDialog mInputDialog = null;


    @OnClick(R.id.title_left)
    void finishPage() {
        finish();
    }

    @OnClick(R.id.title_right_text)
    void confirm() {
        final Intent intent = new Intent();
        intent.putExtra(TransferKeys.GROUP_ID, mCurrentSubjectItem);
        setResult(TransferKeys.ConstantNum.NUM_2000, intent);
        finish();
    }

    private void solveAddSubject() {
        final String inputContent = mInputDialog.getInputContent();
        if (!TextUtils.isEmpty(inputContent)) {
            //判断用户添加的科目是否存在 存在的话提示重新输入
            for (CommonSubjectBean commonSubjectBean : mCommonSubjectBeanList) {
                final String subjectName = commonSubjectBean.getSubjectName();
                if (inputContent.equals(subjectName)) {
                    ToastTool.showCommonToast("科目已存在");
                    return;
                }
            }

            final Map<String, Object> paramsMap = ParamsManager.getInstance()
                    .setParams(ParamKeys.SUBJECT_NAME, inputContent)
                    .getParamsMap();
            HiStudentHttpUtils.postDataByOKHttp(this, paramsMap, HistudentUrl.HOMEWORK_SUBJECT_ADD, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {
                    requestData();
                }

                @Override
                public void onFailure(String errorMsg) {

                }
            });

        } else {
            ToastTool.showCommonToast("请输入科目");
        }
    }


    @Override
    public int setViewLayout() {
        return R.layout.activity_homework_subject_manage;
    }

    @Override
    public void initView() {
        mTvTitleMiddleText.setText("科目管理");
        mIvTitleRightText.setText(R.string.confirm);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        final Divider divider = new Divider(ContextCompat.getDrawable(this, R.drawable.divider_line), LinearLayoutManager.VERTICAL);
        divider.setMargin(SystemUtil.dp2px(12), 0, 0, 0);
        mRvReceiverDetail.setLayoutManager(linearLayoutManager);
        mRvReceiverDetail.addItemDecoration(divider);
    }

    private static final String TAG = HomeworkSubjectManageActivity.class.getName();

    private void requestData() {
        HiStudentHttpUtils.postDataByOKHttp(this, null, HistudentUrl.HOMEWORK_SUBJECT_LIST, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                HiStudentLog.i(TAG, "onSuccess: result" + result);
                final List<CommonSubjectBean> convert = HomeworkSubjectDataConvert.create(result).convert();
                mCommonSubjectBeanList.clear();
                mCommonSubjectBeanList.addAll(convert);
                mSubjectManageAdapter.setNewData(mCommonSubjectBeanList);
                if (mInputDialog != null) {
                    if (mInputDialog.isShowing())
                        mInputDialog.dismiss();
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    @Override
    public void doAction() {
        super.doAction();
        mSubjectManageAdapter = HomeworkSubjectManageAdapter.create(R.layout.item_homework_subject_manage, mCommonSubjectBeanList);
        mRvReceiverDetail.setAdapter(mSubjectManageAdapter);
        mRvReceiverDetail.addOnItemTouchListener(new OnItemClickListener());
        requestData();
    }

    private final class OnItemClickListener extends OnItemChildClickListener {

        @Override
        public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            final int id = view.getId();
            switch (id) {
                case R.id.ll_subject_manage_delete:
                    solveDeleteSubject(position);
                    break;
                case R.id.ll_subject_manage_select:
                    solveSelectSubject(position);
                    break;
                case R.id.ll_add_layout:
                    mInputDialog = new CommonInputDialog(HomeworkSubjectManageActivity.this);
                    mInputDialog.setOnPositiveClickListener(() -> solveAddSubject());
                    mInputDialog.setOnNegativeClickListener(() -> mInputDialog.dismiss());
                    mInputDialog.show();
                    break;
                default:
                    break;
            }
        }
    }

    private void solveDeleteSubject(int position) {
        final String subjectId = mCommonSubjectBeanList.get(position).getSubjectId();
        final Map<String, Object> paramsMap = ParamsManager.getInstance().setParams(ParamKeys.SUBJECT_ID, subjectId).getParamsMap();
        HiStudentHttpUtils.postDataByOKHttp(this, paramsMap, HistudentUrl.HOMEWORK_SUBJECT_DEL, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mCommonSubjectBeanList.remove(position);
                mSubjectManageAdapter.setNewData(mCommonSubjectBeanList);
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    private void solveSelectSubject(int position) {
        final CommonSubjectBean commonSubjectBean = mCommonSubjectBeanList.get(position);
        if (mPrePosition == position) {
            //用户点击的是当前的position
            if (commonSubjectBean.isCheck()) {
                commonSubjectBean.setCheck(false);
            } else {
                commonSubjectBean.setCheck(true);
            }
        } else {
            if (mPrePosition < mCommonSubjectBeanList.size()) {
                //如果上一个科目存在的话才进行还原
                mCommonSubjectBeanList.get(mPrePosition).setCheck(false);
                commonSubjectBean.setCheck(true);
            }
        }
        mPrePosition = position;
        mSubjectManageAdapter.notifyDataSetChanged();
        mCurrentSubjectItem = commonSubjectBean;
    }
}
