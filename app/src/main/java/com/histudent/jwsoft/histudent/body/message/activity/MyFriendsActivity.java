package com.histudent.jwsoft.histudent.body.message.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.adapter.PupWindowAdapter;
import com.histudent.jwsoft.histudent.body.message.adapter.SortAdapter;
import com.histudent.jwsoft.histudent.body.message.model.CategoriesModel;
import com.histudent.jwsoft.histudent.body.message.model.FollowUsersModel;
import com.histudent.jwsoft.histudent.body.message.parser.ParserInMessage;
import com.histudent.jwsoft.histudent.call.IGuideCommonClickListener;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.enums.YdType;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.model.CommenModel;
import com.histudent.jwsoft.histudent.commen.model.SortModel;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.CharacterParser;
import com.histudent.jwsoft.histudent.commen.view.EditText_clear;
import com.histudent.jwsoft.histudent.commen.view.MyListview;
import com.histudent.jwsoft.histudent.commen.view.PinyinComparator;
import com.histudent.jwsoft.histudent.commen.view.SideBar;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 我的关注
 */
public class MyFriendsActivity extends BaseActivity {

    private TextView title_right_text, title_middle_text, dialog;
    private ImageView title_left_image, title_right_image;
    private ImageView title_middle_image;
    private MyListview sortListView;
    private ListView pupWindow_list;
    private SideBar sideBar;
    private SortAdapter adapter;
    private EditText_clear mClearEditText;
    private PopupWindow mPopupWindow;
    private float actionbar_h;
    private PupWindowAdapter windowAdapter;
    private List<CategoriesModel> models;
    private List<FollowUsersModel> usersModels;
    private CategoriesModel model, model_;
    private ImageView emptyView;
    private RelativeLayout layout;
    private boolean flag_exchange;//是否编辑分组的标志位

    Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case 100:
                    List<CategoriesModel> categoriesModels = (List<CategoriesModel>) msg.obj;

                    if (categoriesModels != null) {
                        title_middle_text.setText(model.getName());
                        models.clear();
                        models.addAll(categoriesModels);
                        windowAdapter.notifyDataSetChanged();

                        Message message = obtainMessage();
                        message.what = 400;
                        message.obj = model;
                        sendMessage(message);
                    }
                    break;

                case 300:
                    usersModels = ParserInMessage.getFollowUsersInfoParser(msg.obj.toString());
                    SourceDateList.clear();
                    SourceDateList.addAll(filledData(usersModels));
                    filterData(mClearEditText.getText().toString());
                    //if("我的粉丝".equals(model_.getName()))
                    adapter.notifyDataSetChanged();
                    title_middle_text.setText(model_.getName() + "( " + SourceDateList.size() + " )");

                    break;

                case 400://选取分组item
                    model_ = (CategoriesModel) msg.obj;
                    //修改左边标题
                    if (!model_.isIsDefault()) {
                        title_right_text.setText("编辑分组");
                        flag_exchange = true;
                    } else {
                        title_right_text.setText("创建分组");
                        flag_exchange = false;
                    }
                    //关闭挡板和窗口
                    if (mPopupWindow.isShowing()) {
                        layout.setVisibility(View.GONE);
                        mPopupWindow.dismiss();
                    }
                    getCategoryDetail(model_);
                    break;

                case 500://移动分组
                    SortModel model = (SortModel) msg.obj;
                    MoveGroupActivity.start(MyFriendsActivity.this, model.getUserId(), model.getFollowId(), model_.getName());
                    break;

                case 600://取消关注后刷新人员数量
                    int n = model_.getCateUserNum();
                    n--;
                    model_.setCateUserNum(n);
                    title_middle_text.setText(model_.getName() + "( " + SourceDateList.size() + " )");
                    getCategoriesModels(model_.getName());
                    break;

                case 700:
                    showUserShadeWindow(MyFriendsActivity.this, title_left_image, YdType.CREATE_GROUP, new IGuideCommonClickListener() {
                        @Override
                        public void onClick() {
                            sendEmptyMessage(800);
                        }
                    });
                    break;

                case 800:
                    showUserShadeWindow(MyFriendsActivity.this, title_left_image, YdType.SLIDE_YD, null);
                    break;
            }
        }
    };

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    @Override
    public int setViewLayout() {
        return R.layout.myfriends_activity;
    }

    /**
     * PopupWindow相关
     */
    private void initPopupWindow() {

        View popupWindow = LayoutInflater.from(this).inflate(R.layout.popup_window_myfriends, null);

        TypedArray actionbarSizeTypedArray = this.obtainStyledAttributes(new int[]{
                android.R.attr.actionBarSize
        });
        actionbar_h = actionbarSizeTypedArray.getDimension(0, 0);

        mPopupWindow = new PopupWindow(popupWindow, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pupWindow_list = (ListView) popupWindow.findViewById(R.id.pupWindow_list);
        models = new ArrayList<>();
        windowAdapter = new PupWindowAdapter(MyFriendsActivity.this, models, handler);
        pupWindow_list.setAdapter(windowAdapter);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setTouchable(true);

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                title_middle_image.setImageResource(R.mipmap.all_see_down);
                layout.setVisibility(View.GONE);
            }
        });
        actionbarSizeTypedArray.recycle();
    }

    @Override
    public void initView() {

        String title = getIntent().getStringExtra("name");

        //头部控件初始化
        title_right_text = (TextView) findViewById(R.id.title_right_text);
        title_middle_text = (TextView) findViewById(R.id.title_middle_text);
        title_left_image = (ImageView) findViewById(R.id.title_left_image);
        title_right_image = (ImageView) findViewById(R.id.title_right_image);
        title_middle_image = (ImageView) findViewById(R.id.title_middle_image);
        layout = (RelativeLayout) findViewById(R.id.layout);

        title_right_image.setVisibility(View.GONE);
        title_left_image.setImageResource(R.drawable.goback);
        title_middle_text.setText(title == null ? "全部关注" : title);
        title_right_text.setText("创建分组");
        title_middle_image.setVisibility(View.VISIBLE);
        title_middle_image.setImageResource(R.mipmap.all_see_down);

        initPopupWindow();

        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });

        sortListView = (MyListview) findViewById(R.id.country_lvcountry);

        SourceDateList = new ArrayList<>();

        getCategoriesModels(title);


        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(MyFriendsActivity.this, SourceDateList, handler);
        sortListView.setAdapter(adapter);

        emptyView = EmptyViewUtils.EmptyView(R.mipmap.default_general, this);
        sortListView.setEmptyView(emptyView);

        sortListView.setOnlistenerData(MyFriendsActivity.this, SourceDateList);

        mClearEditText = (EditText_clear) findViewById(R.id.filter_edit);

        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPopupWindow.isShowing()) {
                    layout.setVisibility(View.GONE);
                    mPopupWindow.dismiss();
                }
            }
        });

        handler.sendEmptyMessageDelayed(700, 100);
    }

    /**
     * 获取分组信息
     */
    private void getCategoriesModels(final String name) {
        HiStudentHttpUtils.postDataByOKHttp(this, null, HistudentUrl.getFriendsList_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                List<CategoriesModel> categoriesModels = ParserInMessage.getCategoriesInfoParser(result);

                if (TextUtils.isEmpty(name)) {
                    model = categoriesModels.get(0);
                    sendMessage(handler, 100, 0, categoriesModels);
                } else {
                    for (CategoriesModel model_ : categoriesModels) {
                        if (model_.getName().equals(name)) {
                            model = model_;
                            sendMessage(handler, 100, 0, categoriesModels);
                        }
                    }
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.title_left:
                if (mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                } else {
                    finish();
                }
                break;
            case R.id.title_right:
                if (flag_exchange) {
                    CreateGroupActivity.start(this, model_.getCategoryType(), model_.getCategoryId(), model_.getName(), 3);
                } else {
                    CreateGroupActivity.start(this, models.get(0).getCategoryId(), 1);
                }
                break;

            case R.id.title_middle:
                layout.setVisibility(View.VISIBLE);
                title_middle_image.setImageResource(R.mipmap.all_see_up);
                mPopupWindow.showAsDropDown(view);
                break;

        }

    }

    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(List<FollowUsersModel> date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();
        for (int i = 0; i < date.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date.get(i).getRealName());
            sortModel.setAvatar(date.get(i).getAvatar());
            sortModel.setFollowId(date.get(i).getFollowId());
            sortModel.setUserId(date.get(i).getUserId());
            sortModel.setSort(model_.getCategoryType());
            sortModel.setIsMutual(date.get(i).isIsMutual());
            sortModel.setUserType(date.get(i).userType);
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(date.get(i).getRealName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        // 根据a-z进行排序
        Collections.sort(mSortList, pinyinComparator);
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {

        if (TextUtils.isEmpty(filterStr)) {
            adapter.setVisibaleLiter(true);
            sideBar.setVisibility(View.VISIBLE);
        } else {
            sideBar.setVisibility(View.GONE);
            adapter.setVisibaleLiter(false);
        }


        List<SortModel> filterDateList = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == 200) {
            getCategoriesModels(data.getStringExtra("name"));
        } else if (requestCode == 100 && resultCode == 300) {
            getCategoriesModels(null);
        } else if (requestCode == 100 && resultCode == 400) {
            getCategoriesModels(data.getStringExtra("name"));
        } else if (requestCode == 100 && resultCode == 500) {
            getCategoriesModels(null);
        } else if (requestCode == CommenModel.GOTO_PERSON_HOME && resultCode == CommenModel.GOBACK_SUCCEED) {
            getCategoriesModels(null);
        }
    }

    /**
     * 获取分组详细
     */
    private void getCategoryDetail(CategoriesModel model_) {
        Map<String, Object> map = new TreeMap<>();
        map.put("followType", model_.getCategoryType());
        map.put("userCategoryId", model_.getCategoryId());

        HiStudentHttpUtils.postDataByOKHttp(MyFriendsActivity.this, map, HistudentUrl.getFriendsInList_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                Message message = handler.obtainMessage();
                message.what = 300;
                message.obj = result;
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

}
