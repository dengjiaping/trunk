package com.histudent.jwsoft.histudent.body.mine.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.BodyActivity;
import com.histudent.jwsoft.histudent.body.message.uikit.session.fragment.MyRecentContactsFragment;
import com.histudent.jwsoft.histudent.body.mine.activity.AboutHiActivity;
import com.histudent.jwsoft.histudent.body.mine.activity.InvitationActivity;
import com.histudent.jwsoft.histudent.body.mine.activity.LevelActivity;
import com.histudent.jwsoft.histudent.body.mine.activity.MessageManageActivity;
import com.histudent.jwsoft.histudent.body.mine.activity.SetingActivity;
import com.histudent.jwsoft.histudent.body.mine.model.CurrentUserDetailInfoModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.ImageBrowserActivity;
import com.histudent.jwsoft.histudent.commen.activity.MyWebActivity;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.ShowImageType;
import com.histudent.jwsoft.histudent.commen.fragment.BaseFragment;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.entity.MessageCountEvent;
import com.histudent.jwsoft.histudent.info.persioninfo.PersionHelper;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.histudent.jwsoft.histudent.R.id.themy_fragment_portrait;

/**
 * Created by liuguiyu-pc on 2016/5/26.
 * “我的”界面的fragment
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private TextView themy_fragment_lev_text, my_name, message_redpoint;
    private CurrentUserDetailInfoModel model;
    private HiStudentHeadImageView headImageView;
    private RelativeLayout persion_home, grow_info, meg_info, user_lev, shopping, user_record, user_accountSet, user_about, user_problem;
    private MyRecentContactsFragment fragment_message;
    private TextView title, title_right;
    private IconView title_left_image;
    private int unReadNum;
    private static final int REQ_MESSAGE = 997;

    @BindView(R.id.srl_refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.view_status_bar)
    View mViewStatus;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mRefreshLayout.finishRefresh();
            switch (msg.what) {
                case 1:
                    updateUI();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_main_mine, container, false);
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this, view);
        title_left_image = view.findViewById(R.id.title_left_image);
        title = view.findViewById(R.id.title_middle_text);
        title_right = view.findViewById(R.id.title_right_text);
        my_name = view.findViewById(R.id.my_name);//姓名
        themy_fragment_lev_text = view.findViewById(R.id.themy_fragment_lev_text);//用户等级数
        headImageView = view.findViewById(R.id.themy_fragment_portrait);//头像
        persion_home = view.findViewById(R.id.persion_home);
        grow_info = view.findViewById(R.id.grow_info);
        meg_info = view.findViewById(R.id.meg_info);
        user_lev = view.findViewById(R.id.user_lev);
        shopping = view.findViewById(R.id.shopping);
        user_record = view.findViewById(R.id.user_record);
        user_accountSet = view.findViewById(R.id.user_accountSet);
        user_about = view.findViewById(R.id.user_about);
        user_problem = view.findViewById(R.id.user_problem);
        message_redpoint = view.findViewById(R.id.message_redpoint);
        fragment_message = new MyRecentContactsFragment();

        mRefreshLayout.setEnableLoadmore(false);

        final int statusBarHeight = ImmersionBar.getStatusBarHeight(getActivity());
        final ViewGroup.LayoutParams layoutParams = mViewStatus.getLayoutParams();
        layoutParams.height = statusBarHeight;
        mViewStatus.setLayoutParams(layoutParams);

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

    }

    @Override
    public void initData() {
        super.initData();

        title_left_image.setVisibility(View.GONE);
        title.setText("我的");
        title_right.setText("个人资料");

        persion_home.setOnClickListener(this);
        grow_info.setOnClickListener(this);
        grow_info.setOnClickListener(this);
        meg_info.setOnClickListener(this);
        user_lev.setOnClickListener(this);
        shopping.setOnClickListener(this);
        user_record.setOnClickListener(this);
        headImageView.setOnClickListener(this);
        user_accountSet.setOnClickListener(this);
        user_about.setOnClickListener(this);
        user_problem.setOnClickListener(this);
        loadListener();
    }

    private void loadListener() {
        mRefreshLayout.setOnRefreshListener((RefreshLayout layout) -> {
            PersionHelper.getInstance().getUserInfo((BaseActivity) getActivity(), HiCache.getInstance().getLoginUserInfo().getUserId(), handler, 1);
            ((BodyActivity) getActivity()).getUnreadMessageCount();
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case themy_fragment_portrait://头像
                ArrayList<ActionListBean.ImagesBean> urls2 = new ArrayList<>();
                ActionListBean.ImagesBean imagesBean = new ActionListBean.ImagesBean();
                imagesBean.setBigSizeUrl(model.getAvatar());
                imagesBean.setThumbnailUrl(model.getAvatar());
                urls2.add(imagesBean);
                ImageBrowserActivity.start(getActivity(), 0, 100, urls2, ShowImageType.EXCHANGE, 0, "");
                break;

            case R.id.persion_home://个人主页
                PersonCenterActivity.start(getActivity(), HiCache.getInstance().getLoginUserInfo().getUserId());
                break;

            case R.id.grow_info://成长记录
                MyWebActivity.start(getActivity(), HistudentUrl.growthrecord, "成长记录");
                break;

            case R.id.meg_info://我的消息

//                MyMessageActivity.start(getActivity());
                Intent intent = new Intent(getActivity(), MessageManageActivity.class);
                startActivityForResult(intent, REQ_MESSAGE);
                break;

            case R.id.user_lev://用户等级

                LevelActivity.start(getActivity());
                break;

            case R.id.shopping://积分商城
//                ShoppingActivity.start(getActivity());
                MyWebActivity.start(getActivity(), HistudentUrl.jifenShop, "积分商城");
                break;

            case R.id.user_record://邀请有礼

                InvitationActivity.start(getActivity());
                break;

            case R.id.user_accountSet://设置

                SetingActivity.start(getActivity(), 101);
                break;

            case R.id.user_problem://常见问题

                MyWebActivity.start(getActivity(), HistudentUrl.commonproblem);
                break;

            case R.id.user_about://关于

                AboutHiActivity.start(getActivity());
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 20 && resultCode == 10) {
            PersionHelper.getInstance().getUserInfo((BaseActivity) getActivity(), HiCache.getInstance().getLoginUserInfo().getUserId(), handler, 1);
        } else if (requestCode == 100 && resultCode == 200) {
            PersionHelper.getInstance().getUserInfo((BaseActivity) getActivity(), HiCache.getInstance().getLoginUserInfo().getUserId(), handler, 1);
        } else if (requestCode == 300) {
            PersionHelper.getInstance().getUserInfo((BaseActivity) getActivity(), HiCache.getInstance().getLoginUserInfo().getUserId(), handler, 1);
        }
    }

    public void updateUI() {

        model = HiCache.getInstance().getUserDetailInfo();
        if (model != null && SystemUtil.isOneselfIn(model.getUserId())) {
            CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(getContext(),model.getAvatar(),headImageView);
            my_name.setText(model.getRealName());
            themy_fragment_lev_text.setText("LV." + model.getLevel());
            user_record.setVisibility(model.isIsOpenInvitationRewardSwitch() ? View.VISIBLE : View.GONE);
            my_name.setText(model.getRealName());
            themy_fragment_lev_text.setText("LV." + model.getLevel());

            fragment_message.setOnlisener(new MyRecentContactsFragment.HaveNesMsg() {
                @Override
                public void showRedPoint(int num) {
                    if (num == 0) {
                        message_redpoint.setVisibility(View.GONE);
                    } else {
                        message_redpoint.setVisibility(View.VISIBLE);
                        message_redpoint.setText("" + num);
                    }
                }
            });

        } else {
            PersionHelper.getInstance().getUserInfo((BaseActivity) getActivity(), HiCache.getInstance().getLoginUserInfo().getUserId(), handler, 1);
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        model = HiCache.getInstance().getUserDetailInfo();
        updateUI();
    }

    @Subscribe(sticky = true)
    public void onEvent(MessageCountEvent event) {
        message_redpoint.setVisibility(event.count > 0 ? View.VISIBLE : View.GONE);
        message_redpoint.setText(String.valueOf(event.count));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        CommonGlideImageLoader.getInstance().clearMemory(getContext());
    }
}
