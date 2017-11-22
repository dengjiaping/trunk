
package com.histudent.jwsoft.histudent.commen.activity;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.login.model.CurrentUserInfoModel;
import com.histudent.jwsoft.histudent.body.hiworld.adapter.MyCommentAdpater;
import com.histudent.jwsoft.histudent.body.mine.adapter.ActionAdapter;
import com.histudent.jwsoft.histudent.body.mine.adapter.GridViewImageAdapter;
import com.histudent.jwsoft.histudent.body.mine.model.ActivityInfoBean;
import com.histudent.jwsoft.histudent.body.mine.model.CommentsModel;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.bean.PhotoCommentBean;
import com.histudent.jwsoft.histudent.commen.bean.PhotoHeadBean;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.keyword.fragment.EmotionMainFragment;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.listener.ParserCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.ItemDataExchangeUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.MyListView2;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;
import com.histudent.jwsoft.histudent.model.entity.CommentUpdateEvent;
import com.netease.nim.uikit.common.ui.dialog.CustomAlertDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;

/**
 * 评论界面 动态详情
 */
public class CommentActivity extends BaseActivity implements View.OnClickListener {
    private PullToRefreshScrollView scroll_comment;
    private ActionListBean bean;//需要显示的bean
    public List<ActionListBean> datas;
    public List<CommentsModel> comment_list;
    private List<ActionListBean.PraiseUsersBean> praiseUsersBeens;
    public MyCommentAdpater myCommentAdpater;
    private ActionAdapter infoAdapter;
    private GridViewImageAdapter adapter_image;
    private CommentsModel itemsBean;
    private TextView title;
    private LinearLayout layout, like_layout;
    private EmotionMainFragment emotionMainFragment;
    private MyCommentHandler handler;
    private TextView like_persions_num, comment_num;
    private GridView like_persions_list;
    private boolean flag_listener = false;//由于点击监听触发在长按监听之后，故设此标记
    private ListView list_info;
    private MyListView2 list_comment;
    private final int ALL = 0;
    private final int LIKE = 1;
    private final int COMMENT = 2;
    public static final int REQUESTCODE = 100;//请求码
    public static final int RESULTCODE = 200;//应答码
    public static final int DELET = 201;//应答码
    private LinearLayout layout_top, empty_layout;
    private String actId;
    private PhotoHeadBean photoHeadBean;
    private int position, type;
    private boolean isImageComment;//是否为图片详情
    private int praiseCount;
    private boolean isShield;
    private String ownerId;

    @BindView(R.id.view_bottom_line)
    View mViewBottomLine;

    @BindView(R.id.srl_refresh_layout)
    SmartRefreshLayout mRefreshLayout;


    public static void start(Activity activity, String actId,String ownerId, int type, int position, boolean isShield) {
        if (TextUtils.isEmpty(actId)) return;
        Intent intent = new Intent(activity, CommentActivity.class);
        intent.putExtra("actId", actId);
        intent.putExtra("ownerId", ownerId);
        //intent.putExtra("type", type);
        intent.putExtra("position", position);
        intent.putExtra("isShield", isShield);
        activity.startActivityForResult(intent, REQUESTCODE);
    }

    public static void start(Activity activity, String actId, int position) {
        if (TextUtils.isEmpty(actId)) return;
        Intent intent = new Intent(activity, CommentActivity.class);
        intent.putExtra("actId", actId);
        intent.putExtra("position", position);
        activity.startActivityForResult(intent, REQUESTCODE);
    }

    public static void start(Activity activity, String actId, PhotoHeadBean bean) {
        Intent intent = new Intent(activity, CommentActivity.class);
        intent.putExtra("isImageComment", true);
        intent.putExtra("actId", actId);
        intent.putExtra("bean", bean);
        activity.startActivityForResult(intent, REQUESTCODE);
    }

    @Override
    public int setViewLayout() {
        /**
         * 全屏不重绘
         */
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        return R.layout.activity_details_of_item;
    }

    @Override
    public void initView() {

//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.rootview);
//        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
//        params.topMargin = DisplayUtils.getStatusHeight(this);
//        linearLayout.setLayoutParams(params);
        Intent intent = getIntent();
        mViewBottomLine.setVisibility(View.GONE);
        isImageComment = intent.getBooleanExtra("isImageComment", false);
        actId = intent.getStringExtra("actId");
        ownerId = intent.getStringExtra("ownerId");
        photoHeadBean = (PhotoHeadBean) intent.getSerializableExtra("bean");
        position = intent.getIntExtra("position", -1);
        type = intent.getIntExtra("type", ActionAdapter.COMMENT);
        isShield = intent.getBooleanExtra("isShield", false);
        comment_list = new ArrayList<>();
        praiseUsersBeens = new ArrayList<>();
        handler = new MyCommentHandler();

        title = (TextView) findViewById(R.id.title_middle_text);
        layout = (LinearLayout) findViewById(R.id.rootview);
        scroll_comment = ((PullToRefreshScrollView) findViewById(R.id.deails_comment));
        list_info = (ListView) findViewById(R.id.list_info);
        list_comment = (MyListView2) findViewById(R.id.list_comment);

        like_persions_num = (TextView) findViewById(R.id.like_persions_num);
        like_layout = (LinearLayout) findViewById(R.id.like_layout);
        comment_num = (TextView) findViewById(R.id.comment_num);
        like_persions_list = (GridView) findViewById(R.id.like_persions_list);
        layout_top = (LinearLayout) findViewById(R.id.layout_top);
        empty_layout = (LinearLayout) findViewById(R.id.empty_layout);
        empty_layout.addView(EmptyViewUtils.EmptyViewTextAndImage(R.drawable.no_issue, CommentActivity.this, R.string.have_no_issue));
        list_comment.setEmptyView(empty_layout);
        datas = new ArrayList<>();
        list_comment.setEmptyView(empty_layout);
        infoAdapter = new ActionAdapter(CommentActivity.this, datas, type, true);
        infoAdapter.setDetail(true);
        myCommentAdpater = new MyCommentAdpater(this, comment_list);
        adapter_image = new GridViewImageAdapter(this, praiseUsersBeens);

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void doAction() {

        title.setText("动态详情");
        infoAdapter.setisShield(isShield);
        list_info.setAdapter(infoAdapter);
        list_comment.setAdapter(myCommentAdpater);
        like_persions_list.setAdapter(adapter_image);

        getCommentData(ALL, LoadingType.FLOWER);

        initEmotionMainFragment();
        scroll_comment.setMode(PullToRefreshBase.Mode.DISABLED);
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (comment_list.size() < 0) {
                    getCommentData(COMMENT, LoadingType.NONE);
                } else {
                    handler.sendEmptyMessageDelayed(0, 1000);
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                comment_list.clear();
                getCommentData(COMMENT, LoadingType.NONE);
            }
        });

        list_comment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (flag_listener) {
                    flag_listener = false;
                } else {
                    itemsBean = comment_list.get(i);
                    emotionMainFragment.setEditText("回复 " + itemsBean.getUser().getName());
                }
            }
        });

        list_comment.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {

                flag_listener = true;
                if (comment_list.size() < 1) return false;
                final CommentsModel itemsBean_delet = comment_list.get(i);

                CustomAlertDialog dialog = new CustomAlertDialog(CommentActivity.this);

                if (SystemUtil.isOneselfIn(itemsBean_delet.getUser().getUserId())) {
                    dialog.addItem("删除", new CustomAlertDialog.onSeparateItemClickListener() {
                        @Override
                        public void onClick() {
                            deletCommentData(itemsBean_delet.getCommentId());
                        }
                    });
                }
                dialog.addItem("复制", new CustomAlertDialog.onSeparateItemClickListener() {
                    @Override
                    public void onClick() {
                        copy(itemsBean_delet.getContent(), CommentActivity.this);
                    }
                });
                if (!CommentActivity.this.isFinishing())
                    dialog.show();
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //取消关心
            case R.id.relative_cancel_attention:
                Toast.makeText(this, "iv_cancel_care", Toast.LENGTH_LONG).show();
                break;

            //关心
            case R.id.relative_care:
                Toast.makeText(this, "iv_care", Toast.LENGTH_LONG).show();
                break;

            //举报
            case R.id.relative_report:
                Toast.makeText(this, "iv_report", Toast.LENGTH_LONG).show();
                break;

            //屏蔽
            case R.id.relative_shield:
                Toast.makeText(this, "iv_shield", Toast.LENGTH_LONG).show();
                break;

            //分享
            case R.id.rb_share:
                Toast.makeText(this, "rb_share", Toast.LENGTH_LONG).show();
                break;

            case R.id.title_left:
                finish();
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(CommentUpdateEvent event){
//        comment_list.clear();
//        getCommentData(COMMENT, LoadingType.NONE);
    }

    @Subscribe
    public void actions(CommentActions actions) {

        if (actions.flag == 1) {//删除动态
            Intent intent = new Intent();
            intent.putExtra("position", position);
            setResult(CommentActivity.DELET, intent);
            finish();

        } else if (actions.flag == 2) {//点赞

            praiseCount++;

            CurrentUserInfoModel infoModel = HiCache.getInstance().getLoginUserInfo();
            ActionListBean.PraiseUsersBean bean_ = new ActionListBean.PraiseUsersBean();
            bean_.setAvatar(infoModel.getAvatar());
            bean_.setName(infoModel.getRealName());
            bean_.setUserId(infoModel.getUserId());
            bean.getPraiseUsers().add(0, bean_);

            if (praiseCount > 0) {
                like_layout.setVisibility(View.VISIBLE);
                like_persions_num.setText(praiseCount + "人点赞");
            } else {
                like_layout.setVisibility(View.GONE);
            }

            getPraiseData(bean.getPraiseUsers());
            adapter_image.notifyDataSetChanged();

            setResult();

        } else if (actions.flag == 3) {//取消点赞

            CurrentUserInfoModel infoModel = HiCache.getInstance().getLoginUserInfo();
            ActionListBean.PraiseUsersBean bean_ = null;
            for (int i = 0; i < praiseCount; i++) {
                ActionListBean.PraiseUsersBean usersBean = bean.getPraiseUsers().get(i);
                if (!TextUtils.isEmpty(usersBean.getUserId()) && usersBean.getUserId().equals(infoModel.getUserId())) {
                    bean_ = usersBean;
                    break;
                }
            }
            bean.getPraiseUsers().remove(bean_);
            praiseCount--;

            if (praiseCount > 0) {
                like_layout.setVisibility(View.VISIBLE);
                like_persions_num.setText(praiseCount + "人点赞");
            } else {
                like_layout.setVisibility(View.GONE);
            }

            getPraiseData(bean.getPraiseUsers());
            adapter_image.notifyDataSetChanged();

            setResult();

        } else if (actions.flag == 4) {//点击评论图标
            itemsBean = null;
            emotionMainFragment.setEditText("");

        }
    }

    /**
     * 获取评论数据
     */
    private void getCommentData(final int type, LoadingType loadingType) {

        if (TextUtils.isEmpty(actId)) return;

        if (isImageComment) {//图片评论信息

            Map<String, Object> map = new TreeMap<>();
            map.put("objectType", 5);
            map.put("objectId", actId);
            HiStudentHttpUtils.postDataByOKHttp(CommentActivity.this, map, HistudentUrl.getPhotoInfo, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {
                    itemsBean = null;
                    mRefreshLayout.finishLoadmore();
                    mRefreshLayout.finishRefresh();
                    bean = new ActionListBean();
                    ItemDataExchangeUtils.dataExchange_PtictureInfo(bean, JSON.parseObject(result, PhotoCommentBean.class), photoHeadBean);
                    bean.setOwnerId(ownerId);
                    showCommentInfo(type);
                }

                @Override
                public void onFailure(String msg) {
                    mRefreshLayout.finishLoadmore();
                    mRefreshLayout.finishRefresh();
                }
            }, loadingType);

        } else {//非图片评论信息

            Map<String, Object> map = new TreeMap<>();
            map.put("actId", actId);

            HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.activityInfo_url, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {
                    itemsBean = null;
                    mRefreshLayout.finishLoadmore();
                    mRefreshLayout.finishRefresh();
                    bean = new ActionListBean();
                    ItemDataExchangeUtils.dataExchange_ActionInfo(bean, JSON.parseObject(result, ActivityInfoBean.class));
                    bean.setOwnerId(ownerId);
                    showCommentInfo(type);
                }

                @Override
                public void onFailure(String msg) {
                    mRefreshLayout.finishLoadmore();
                    mRefreshLayout.finishRefresh();
                }
            }, loadingType);
        }
    }

    private void showCommentInfo(int type) {
        if (bean != null && bean.getItemsBeen() != null) {

            adapter_image.setBean(bean);
            datas.clear();
            datas.add(bean);
            infoAdapter.notifyDataSetChanged();

            if (bean.getPraiseCount() == 0) {
                like_layout.setVisibility(View.GONE);
            } else {
                like_layout.setVisibility(View.VISIBLE);
                praiseCount = bean.getPraiseCount();
                like_persions_num.setText(bean.getPraiseCount() + "人点赞");
                if (type == LIKE || type == ALL) {
                    praiseUsersBeens.clear();
                    getPraiseData(bean.getPraiseUsers());
                }
                adapter_image.notifyDataSetChanged();
            }

            if (bean.getCommentCount() == 0) {
                comment_num.setVisibility(View.GONE);

            } else {
                comment_num.setVisibility(View.VISIBLE);
                comment_num.setText("全部评论" + bean.getCommentCount() + "条");
                if (type == COMMENT || type == ALL) {
                    comment_list.clear();
                    comment_list.addAll(bean.getItemsBeen());
                    myCommentAdpater.notifyDataSetChanged();
                }
            }
        }
    }

    /**
     * 获取需要显示的点赞的人数
     */
    private void getPraiseData(List<ActionListBean.PraiseUsersBean> beens) {
        praiseUsersBeens.clear();
        if (praiseCount < 7) {
            praiseUsersBeens.addAll(beens);
        } else {
            praiseUsersBeens.addAll(beens.subList(0, 7));
        }
        praiseUsersBeens.add(new ActionListBean.PraiseUsersBean());
    }

    /**
     * 发表评论
     */
    private void publicCommentData(String content) {

        Map<String, Object> map = new TreeMap<>();
        map.put("content", content);
        if (itemsBean != null) {
            map.put("parentId", TextUtils.isEmpty(itemsBean.getParentId()) ? itemsBean.getCommentId() : itemsBean.getParentId());
            map.put("toUserId", itemsBean.getUser().getUserId());
            map.put("toCommentId", itemsBean.getCommentId());
        } else {
            map.put("parentId", "");
            map.put("toUserId", "");
            map.put("toCommentId", "");
        }
        map.put("ownerId", isImageComment ? actId : bean.getOwnerId());
        map.put("objectType", isImageComment ? 5 : 1);
        map.put("objectId", isImageComment ? actId : bean.getActId());
        CommentsModel commentsModel = new CommentsModel();
        CommentsModel.UserBean userBean = new CommentsModel.UserBean();
        userBean.setAvatar(HiCache.getInstance().getLoginUserInfo().getAvatar());
        userBean.setName(HiCache.getInstance().getLoginUserInfo().getRealName());
        CommentsModel.ToUserBean toUserBean = new CommentsModel.ToUserBean();
        commentsModel.setContent(content);
        commentsModel.setCreateTime(TimeUtils.getCurrentTime());
        commentsModel.setUser(userBean);
        commentsModel.setToUser(toUserBean);
        comment_list.add(0,commentsModel);
        myCommentAdpater.notifyDataSetChanged();
        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.publicComment_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                //comment_list.clear();
                setResult();
                //getCommentData(COMMENT, LoadingType.NONE);
            }

            @Override
            public void onFailure(String msg) {

            }
        },LoadingType.NONE);


    }

    /**
     * 删除评论
     *
     * @param commentId
     */
    private void deletCommentData(String commentId) {

        Map<String, Object> map = new TreeMap<>();
        map.put("commentId", commentId);
        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.deletComment_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                comment_list.clear();
                getCommentData(COMMENT, LoadingType.FLOWER);
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }





    /**
     * 初始化表情面板
     */
    public void initEmotionMainFragment() {
        //构建传递参数
        Bundle bundle = new Bundle();
        //绑定主内容编辑框
        bundle.putBoolean(EmotionMainFragment.BIND_TO_EDITTEXT, true);
        //隐藏控件
        bundle.putBoolean(EmotionMainFragment.HIDE_BAR_EDITTEXT_AND_BTN, false);
        //替换fragment
        //创建修改实例
        emotionMainFragment = EmotionMainFragment.newInstance(EmotionMainFragment.class, bundle);
        emotionMainFragment.bindToContentView(layout_top);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in thefragment_container view with this fragment,
        // and add the transaction to the backstack
        transaction.replace(R.id.fl_emotionview_main, emotionMainFragment);
//        transaction.addToBackStack(null);
        //提交修改
        transaction.commit();

        emotionMainFragment.setCallBack(new ParserCallBack() {
            @Override
            public void parser(String result) {

                list_comment.setSelection(scroll_comment.getBottom());

                if (!TextUtils.isEmpty(result)) {
                    publicCommentData(result);
                }
            }
        });
    }

    /**
     * 设置返回数据
     */
    private void setResult() {
        Intent intent = new Intent();
        intent.putExtra("data", bean);
        intent.putExtra("position", position);
        setResult(RESULTCODE, intent);
    }

    @Override
    public void onBackPressed() {
        /**
         * 判断是否拦截返回键操作
         */
        if (!emotionMainFragment.isInterceptBackPress()) {
            super.onBackPressed();
        }
    }

    /**
     * 实现文本复制功能
     * add by wangqianzhou
     *
     * @param content
     */
    public static void copy(String content, Context context) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    /**
     * 实现粘贴功能
     * add by wangqianzhou
     *
     * @param context
     * @return
     */
    public static String paste(Context context) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return cmb.getText().toString().trim();
    }

    public static class CommentActions {
        public int flag;

        public CommentActions(int flag) {
            this.flag = flag;
        }
    }

    private class MyCommentHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0:
                    mRefreshLayout.finishLoadmore();
                    mRefreshLayout.finishRefresh();
                    Toast.makeText(CommentActivity.this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
                    break;
                case -3:
                    setResult(200);
                    finish();
                    break;
            }

        }
    }

    /**
     * **************************** 排序 ***********************************
     */
    private void sortRecentContactsModel(List<ActivityInfoBean.CommentsBean> list) {
        if (list.size() == 0) {
            return;
        }
        Collections.sort(list, comp);
    }

    private Comparator<ActivityInfoBean.CommentsBean> comp = new Comparator<ActivityInfoBean.CommentsBean>() {
        @Override
        public int compare(ActivityInfoBean.CommentsBean o1, ActivityInfoBean.CommentsBean o2) {
            long time = TimeUtils.getTimeLong(o2.getCreateTime()) - TimeUtils.getTimeLong(o1.getCreateTime());
            return time == 0 ? 0 : (time > 0 ? -1 : 1);
        }
    };

}


