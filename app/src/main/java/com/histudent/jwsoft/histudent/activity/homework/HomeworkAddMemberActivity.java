package com.histudent.jwsoft.histudent.activity.homework;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.adapter.decoration.Divider;
import com.histudent.jwsoft.histudent.adapter.homework.HomeworkAddMemberAdapter;
import com.histudent.jwsoft.histudent.adapter.homework.convert.AddMemberDataConvert;
import com.histudent.jwsoft.histudent.bean.homework.CommonMemberBean;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.constant.ParamKeys;
import com.histudent.jwsoft.histudent.constant.TransferKeys;
import com.histudent.jwsoft.histudent.manage.ParamsManager;
import com.histudent.jwsoft.histudent.manage.UserManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lichaojie on 2017/10/25.
 * desc:
 */

public class HomeworkAddMemberActivity extends BaseActivity {

    @BindView(R.id.title_middle_text)
    TextView mTvTitleMiddleText;
    @BindView(R.id.title_right_text)
    TextView mTvTitleRightText;
    @BindView(R.id.title_left_text)
    TextView mTvTitleLeftText;

    @BindView(R.id.rv_add_group_member)
    RecyclerView mRvAddGroupMember;

    @BindView(R.id.et_input_search_member)
    EditText mEtInputSearchMember;


    /**
     * 上个页面传递过来的数据
     */
    private ArrayList<CommonMemberBean> mTransferSelectList = new ArrayList<>();


    /**
     * 所有班级成员
     */
    private final List<CommonMemberBean> mMemberBeanList = new ArrayList<>();
    /**
     * 选中的班级成员
     */
    private final ArrayList<CommonMemberBean> mCurrentSelectMemberList = new ArrayList<>();
    /**
     * 用户点击搜索列表
     */
    private final ArrayList<CommonMemberBean> mSearchMemberList = new ArrayList<>();

    /**
     * 所有成员名称
     */
    private final List<String> mAllNameList = new ArrayList<>();

    private HomeworkAddMemberAdapter mAddMemberAdapter = null;
    private String mCurrentClassId;

    /**
     * true:如果是搜索状态-->点击条目处理搜索数据
     */
    private boolean isSearchStatus;
    private static final String TAG = HomeworkAddMemberActivity.class.getName();

    @OnClick(R.id.title_left_text)
    void cancel() {
        finish();
    }

    @OnClick(R.id.title_right_text)
    void confirm() {
        final Intent intent = new Intent();
        intent.putParcelableArrayListExtra(TransferKeys.ADD_MEMBER, mCurrentSelectMemberList);
        setResult(TransferKeys.ConstantNum.NUM_2001, intent);
        finish();
    }

    @OnClick(R.id.search_icon)
    void onSearch() {
        isSearchStatus = true;
        mSearchMemberList.clear();
        final String searchContent = mEtInputSearchMember.getText().toString().trim();
        final char[] chars = searchContent.toCharArray();
        final int size = mMemberBeanList.size();
        for (int i = 0; i < size; i++) {
            final String name = mMemberBeanList.get(i).getName();
            for (int j = 0; j < chars.length; j++) {
                final char word = chars[j];
                if (name.contains(String.valueOf(word))) {
                    final CommonMemberBean commonMemberBean = mMemberBeanList.get(i);
                    mSearchMemberList.add(commonMemberBean);
                    break;
                }
            }
        }
        mAddMemberAdapter.setNewData(mSearchMemberList);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_homework_add_member;
    }

    @Override
    public void initView() {
        findViewById(R.id.title_left_image).setVisibility(View.GONE);
        mTvTitleLeftText.setText("取消");
        mTvTitleLeftText.setTextColor(ContextCompat.getColor(this, R.color._999999));
        mTvTitleRightText.setText("确定");
        mTvTitleMiddleText.setText("添加成员");
        mTvTitleMiddleText.setTextColor(ContextCompat.getColor(this, R.color._333333));
        mTvTitleRightText.setTextColor(ContextCompat.getColor(this, R.color._28ca7e));
        mEtInputSearchMember.addTextChangedListener(new InputTextWatcher());
    }

    @Override
    public void doAction() {
        super.doAction();
        //获取上个页面已选中成员列表
        mTransferSelectList = getIntent().getParcelableArrayListExtra(TransferKeys.ADD_MEMBER);
        mCurrentSelectMemberList.clear();
        mCurrentSelectMemberList.addAll(mTransferSelectList);
        mCurrentClassId = getIntent().getStringExtra(TransferKeys.CLASS_ID);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        final Divider divider = new Divider(ContextCompat.getDrawable(this, R.drawable.divider_line), LinearLayoutManager.VERTICAL);
        divider.setMargin(SystemUtil.dp2px(12), 0, 0, 0);
        mRvAddGroupMember.setLayoutManager(linearLayoutManager);
        mRvAddGroupMember.addItemDecoration(divider);
        mAddMemberAdapter = HomeworkAddMemberAdapter
                .create(R.layout.item_homework_select_member, mMemberBeanList);
        mRvAddGroupMember.addOnItemTouchListener(new OnItemClickListener());
        loadData();
    }

    private void loadData() {
        final Map<String, Object> map = ParamsManager.getInstance()
                .setParams(ParamKeys.CLASS_ID, mCurrentClassId)
                .getParamsMap();
        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.getClassTeamber_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mMemberBeanList.clear();
                Log.e(TAG, "onSuccess: result----->" + result);
                final AddMemberDataConvert addMemberDataConvert = new AddMemberDataConvert();
                final List<CommonMemberBean> listData = addMemberDataConvert.setJsonData(result)
                        .convert();
                for (CommonMemberBean subData : listData) {
                    mAllNameList.add(subData.getName());
                }
                mMemberBeanList.addAll(listData);
                //默认选中传递过来的成员数据
                if (mTransferSelectList != null && mTransferSelectList.size() > 0) {
                    for (CommonMemberBean memberBean : mTransferSelectList) {
                        final String transferUserId = memberBean.getUserId();
                        for (CommonMemberBean commonMemberBean : mMemberBeanList) {
                            final String userId = commonMemberBean.getUserId();
                            if (transferUserId != null) {
                                if (transferUserId.equals(userId)) {
                                    commonMemberBean.setCheck(true);
                                    break;
                                }
                            }
                        }
                    }

                }
                mRvAddGroupMember.setAdapter(mAddMemberAdapter);
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    private final class OnItemClickListener extends SimpleClickListener {

        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        }

        @Override
        public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

        }

        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            if (view.getId() == R.id.ll_add_member_search_select) {
                CommonMemberBean commonMemberBean = null;
                if (isSearchStatus) {
                    commonMemberBean = mSearchMemberList.get(position);
                } else {
                    commonMemberBean = mMemberBeanList.get(position);
                }

                final boolean isChecked = commonMemberBean.isCheck();
                final String userId = commonMemberBean.getUserId();
                //改变当前成员状态
                if (isChecked) {
                    commonMemberBean.setCheck(false);
                } else {
                    commonMemberBean.setCheck(true);
                }

                //如果是搜索状态  此时用户点击选择添加联系  选择状态需要更新至全部成员中
                if (isSearchStatus) {
                    for (CommonMemberBean memberBean : mMemberBeanList) {
                        final boolean status = commonMemberBean.isCheck();
                        final String searchUserId = commonMemberBean.getUserId();
                        if (memberBean.getUserId().equals(searchUserId)) {
                            memberBean.setCheck(status);
                            break;
                        }
                    }
                }
                mAddMemberAdapter.notifyDataSetChanged();

                //更新当前选中数据
                //1.如果原来选中的数据源中包括当前UserId 即当前成员存在
                final int size = mCurrentSelectMemberList.size();
                int index = -1;
                if (size > 0) {
                    for (int i = 0; i < size; i++) {
                        final CommonMemberBean subMember = mCurrentSelectMemberList.get(i);
                        final String subUserId = subMember.getUserId();
                        if (subUserId != null) {
                            if (subUserId.equals(userId)) {
                                index = i;
                                break;
                            }
                        }
                    }
                }

                if (index != -1) {
                    //当前用户所点击的成员  在原来中已经存在
                    if (!commonMemberBean.isCheck()) {
                        //如果用户取消点击 即直接移动该成员
                        mCurrentSelectMemberList.remove(index);
                    }
                    return;
                }
                //2.如果原来选中的数据源中不包括当前UserId 即原来不存在 直接加入当前数据
                mCurrentSelectMemberList.add(commonMemberBean);
            }
        }

        @Override
        public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

        }
    }

    private final class InputTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(s)) {
                isSearchStatus = false;
                mSearchMemberList.clear();
                mAddMemberAdapter.setNewData(mMemberBeanList);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
