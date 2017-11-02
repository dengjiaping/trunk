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
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.adapter.decoration.Divider;
import com.histudent.jwsoft.histudent.adapter.homework.HomeworkSubjectManageAdapter;
import com.histudent.jwsoft.histudent.base.BaseActivity;
import com.histudent.jwsoft.histudent.bean.homework.CommonSubjectBean;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.constant.TransferKeys;
import com.histudent.jwsoft.histudent.dialog.CommonInputDialog;
import com.histudent.jwsoft.histudent.presenter.homework.WorkSubjectManagePresenter;
import com.histudent.jwsoft.histudent.presenter.homework.contract.WorkSubjectManageContract;
import com.histudent.jwsoft.histudent.tool.ToastTool;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lichaojie on 2017/10/25.
 * desc:
 * 科目管理
 */

public class WorkSubjectManageActivity extends BaseActivity<WorkSubjectManagePresenter>
        implements WorkSubjectManageContract.View {

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
    private int mDeletePosition = -1;
    private String mTransferSubjectId = null;
    private String mAddSubjectId = null;

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

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_homework_subject_manage;
    }

    @Override
    protected void init() {
        initView();
        initData();
    }

    public void initView() {
        mTvTitleMiddleText.setText("科目管理");
        mIvTitleRightText.setText(R.string.confirm);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        final Divider divider = new Divider(ContextCompat.getDrawable(this, R.drawable.divider_line), LinearLayoutManager.VERTICAL);
        divider.setMargin(SystemUtil.dp2px(12), 0, 0, 0);
        mRvReceiverDetail.setLayoutManager(linearLayoutManager);
        mRvReceiverDetail.addItemDecoration(divider);
    }

    public void initData() {
        mTransferSubjectId = getIntent().getStringExtra(TransferKeys.SUBJECT_ID);
        mSubjectManageAdapter = HomeworkSubjectManageAdapter.create(R.layout.item_homework_subject_manage, mCommonSubjectBeanList);
        mRvReceiverDetail.setAdapter(mSubjectManageAdapter);
        mRvReceiverDetail.addOnItemTouchListener(new OnItemClickListener());
        showLoadingDialog();
        mPresenter.getSubjectList();
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
            showLoadingDialog();
            mPresenter.addSpecifiedSubject(inputContent);
        } else {
            ToastTool.showCommonToast("请输入科目");
        }
    }


    private static final String TAG = WorkSubjectManageActivity.class.getName();


    @Override
    public void controlDialogStatus(String message) {
        if (!TextUtils.isEmpty(message))
            ToastTool.showCommonToast(message);
        dismissLoadingDialog();
    }

    @Override
    public void updateListData(List<CommonSubjectBean> commonSubjectBean) {
        mCommonSubjectBeanList.clear();
        mCommonSubjectBeanList.addAll(commonSubjectBean);
        //根据传来的id 默认选择该科目
        if (!TextUtils.isEmpty(mTransferSubjectId) || !TextUtils.isEmpty(mAddSubjectId)) {
            final int size = mCommonSubjectBeanList.size();
            for (int i = 0; i < size; i++) {
                final CommonSubjectBean subjectBean = mCommonSubjectBeanList.get(i);
                final String subjectId = subjectBean.getSubjectId();
                if (subjectId.equals(mTransferSubjectId) || subjectId.equals(mAddSubjectId)) {
                    if (!TextUtils.isEmpty(mTransferSubjectId) || !TextUtils.isEmpty(mAddSubjectId)) {
                        mPrePosition = i;
                        mCurrentSubjectItem = subjectBean;
                        subjectBean.setCheck(true);
                        break;
                    }
                }
            }
            mTransferSubjectId = null;
            mAddSubjectId = null;
        }

        mSubjectManageAdapter.setNewData(mCommonSubjectBeanList);
        if (mInputDialog != null) {
            if (mInputDialog.isShowing())
                mInputDialog.dismiss();
        }

    }

    @Override
    public void deleteSpecifiedSubjectSuccess() {
        mCommonSubjectBeanList.remove(mDeletePosition);
        mSubjectManageAdapter.setNewData(mCommonSubjectBeanList);
    }

    @Override
    public void addSpecifiedSubjectSuccess(String subjectId) {
        this.mAddSubjectId = subjectId;
        showLoadingDialog();
        mPresenter.getSubjectList();
    }


    @Override
    public void showContent(String message) {

    }

    private final class OnItemClickListener extends OnItemChildClickListener {

        @Override
        public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            final int id = view.getId();
            switch (id) {
                case R.id.ll_subject_manage_delete:
                    mDeletePosition = position;
                    showLoadingDialog();
                    final String subjectId = mCommonSubjectBeanList.get(position).getSubjectId();
                    mPresenter.deleteSpecifiedSubject(subjectId);
                    break;
                case R.id.ll_subject_manage_select:
                    solveSelectSubject(position);
                    break;
                case R.id.ll_add_layout:
                    mInputDialog = new CommonInputDialog(WorkSubjectManageActivity.this);
                    mInputDialog.setOnPositiveClickListener(() -> solveAddSubject());
                    mInputDialog.setOnNegativeClickListener(() -> mInputDialog.dismiss());
                    mInputDialog.show();
                    break;
                default:
                    break;
            }
        }
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
