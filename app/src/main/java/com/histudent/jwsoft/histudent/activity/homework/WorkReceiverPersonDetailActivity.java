package com.histudent.jwsoft.histudent.activity.homework;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.adapter.decoration.Divider;
import com.histudent.jwsoft.histudent.adapter.homework.HomeworkCreateDivideGroupAdapter;
import com.histudent.jwsoft.histudent.base.BaseActivity;
import com.histudent.jwsoft.histudent.bean.homework.CommonMemberBean;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.TopMenuPopupWindow;
import com.histudent.jwsoft.histudent.constant.TransferKeys;
import com.histudent.jwsoft.histudent.presenter.homework.WorkReceiverPersonPresenter;
import com.histudent.jwsoft.histudent.presenter.homework.contract.WorkReceiverPersonContract;
import com.histudent.jwsoft.histudent.tool.CommonAdvanceUtils;
import com.histudent.jwsoft.histudent.tool.ToastTool;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lichaojie on 2017/10/31.
 * desc:
 * 发布作业-选择接收人详情
 * tip:adapter 与创建分组相同 共用
 * sign:pre
 */

public class WorkReceiverPersonDetailActivity extends BaseActivity<WorkReceiverPersonPresenter>
        implements WorkReceiverPersonContract.View {

    @BindView(R.id.title_middle_text)
    TextView mTvTitleMiddleText;

    @BindView(R.id.title_right_image)
    IconView mIvTitleRightIcon;

    @BindView(R.id.rv_receiver_person_detail)
    RecyclerView mRvReceiverDetail;
    private HomeworkCreateDivideGroupAdapter mDivideGroupAdapter;
    private static final String TAG = WorkReceiverPersonDetailActivity.class.getName();

    private final List<CommonMemberBean> mListData = new ArrayList<>();
    private PopupWindow mMenuPopupWindow = null;
    private String mTeamId;
    private String mClassId;

    @OnClick(R.id.title_left)
    void finishPage() {
        final Intent intent = new Intent(this, WorkSelectReceiverPersonActivity.class);
        intent.putExtra(TransferKeys.GROUP_ID, mTeamId);
        CommonAdvanceUtils.startActivity(this, intent);
    }

    @OnClick(R.id.title_right_image)
    void onClickMore() {
        //右上角更多
        showPopupWindow();
    }

    private void showPopupWindow() {
        final List<String> textList = new ArrayList<>();
        final List<Integer> textColor = new ArrayList<>();
        textList.add("编辑");
        textList.add("删除");
        textColor.add(Color.rgb(51, 51, 51));
        textColor.add(Color.rgb(51, 51, 51));
        textColor.add(Color.rgb(51, 51, 51));
        mMenuPopupWindow = new TopMenuPopupWindow(this, (View view) -> {
            switch (view.getId()) {
                case R.id.btn_01:
                    showEditGroup();
                    break;
                case R.id.btn_02:
                    showLoadingDialog();
                    mPresenter.deleteGroupName(mTeamId);
                    break;
                default:
                    break;
            }
            mMenuPopupWindow.dismiss();
        }, textList, textColor, true);
        mMenuPopupWindow.showAtLocation(mIvTitleRightIcon, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void showEditGroup() {
        final Intent intent = new Intent(this, WorkCreateDivideGroupActivity.class);
        intent.putExtra(TransferKeys.DIVIDE_GROUP_NAME, mTvTitleMiddleText.getText().toString());
        intent.putExtra(TransferKeys.GROUP_ID, mTeamId);
        intent.putExtra(TransferKeys.CLASS_ID, mClassId);
        intent.putExtra(TransferKeys.TYPE, 1);//1:代表修改分组
        CommonAdvanceUtils.startActivity(this, intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            final String stringExtra = intent.getStringExtra(TransferKeys.GROUP_ID);
            if (stringExtra != null) {
                showLoadingDialog();
                mPresenter.getGroupMemberList(mTeamId);
            }
        }

    }

    @Override
    public void showContent(String message) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_homework_receiver_person_detail;
    }

    @Override
    protected void init() {
        initView();
        initData();
    }

    public void initView() {
        mIvTitleRightIcon.setText(R.string.icon_more);
    }

    public void initData() {
        mTeamId = getIntent().getStringExtra(TransferKeys.GROUP_ID);
        mClassId = getIntent().getStringExtra(TransferKeys.CLASS_ID);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        final Divider divider = new Divider(ContextCompat.getDrawable(this, R.drawable.divider_line), LinearLayoutManager.VERTICAL);
        divider.setMargin(SystemUtil.dp2px(12), 0, 0, 0);
        mRvReceiverDetail.setLayoutManager(linearLayoutManager);
        mRvReceiverDetail.addItemDecoration(divider);
        mDivideGroupAdapter = HomeworkCreateDivideGroupAdapter.create(R.layout.item_create_homework_divide_group, mListData);
        mDivideGroupAdapter.setType(0);
        mRvReceiverDetail.setAdapter(mDivideGroupAdapter);
        showLoadingDialog();
        mPresenter.getGroupMemberList(mTeamId);
    }

    @Override
    public void updateListData(List<CommonMemberBean> convert) {
        final String teamName = convert.get(0).getTeamName();
        mTvTitleMiddleText.setText(teamName + "(" + convert.size() + ")人");
        mListData.clear();
        mListData.addAll(convert);
        mDivideGroupAdapter.setNewData(mListData);
    }

    @Override
    public void deleteGroupListComplete() {
        ToastTool.showCommonToast("已删除");
        final Intent intent = new Intent(WorkReceiverPersonDetailActivity.this, WorkSelectReceiverPersonActivity.class);
        intent.putExtra(TransferKeys.GROUP_ID, mTeamId);
        CommonAdvanceUtils.startActivity(WorkReceiverPersonDetailActivity.this, intent);
    }

    @Override
    public void controlDialogStatus(String message) {
        if (!TextUtils.isEmpty(message))
            ToastTool.showCommonToast(message);
        dismissLoadingDialog();
    }

}
