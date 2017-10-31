package com.histudent.jwsoft.histudent.body.message.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.adapter.SortAdapter;
import com.histudent.jwsoft.histudent.body.message.model.CategoriesModel;
import com.histudent.jwsoft.histudent.body.message.model.FindUserBean;
import com.histudent.jwsoft.histudent.body.message.model.FollowUsersModel;
import com.histudent.jwsoft.histudent.body.message.model.HieldBean;
import com.histudent.jwsoft.histudent.body.message.parser.ParserInMessage;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.model.SaveData;
import com.histudent.jwsoft.histudent.commen.model.SortModel;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.CharacterParser;
import com.histudent.jwsoft.histudent.commen.view.EditText_clear;
import com.histudent.jwsoft.histudent.commen.view.PinyinComparator;
import com.histudent.jwsoft.histudent.commen.view.SideBar;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 选择好友界面
 */
public class SelectContactsActivity extends BaseActivity {
    private TextView title_right_text, title_middle_text, title_left_text, dialog;
    private ImageView title_left_image;
    private ListView sortListView;
    private SortAdapter adapter;
    private EditText_clear mClearEditText;
    private SideBar sideBar;
    private List<FollowUsersModel> usersModels;
    private boolean flag = false;//跳转标志
    private List<CategoriesModel> models;
    private CategoriesModel model, model_;
    private ImageView emptyView;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;
    public static List<String> selected_url;//选中的账号集合

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case 100:

                    usersModels = ParserInMessage.getFollowUsersInfoParser(msg.obj.toString());
                    SourceDateList.clear();
                    SourceDateList.addAll(filledData(usersModels));

                    for (int i = 0; i < CreateGroupActivity.list_data.size(); i++) {

                        for (int n = 0; n < SourceDateList.size(); n++) {

                            if (CreateGroupActivity.list_data.get(i).getUserId().equals(SourceDateList.get(n).getUserId())) {
                                SourceDateList.remove(n);
                            }
                        }
                    }

                    Collections.sort(SourceDateList, pinyinComparator);// 根据a-z进行排序源数据
                    adapter.notifyDataSetChanged();

                    break;

                case 200:
                    List<HieldBean> beens = JSON.parseArray(msg.obj.toString(), HieldBean.class);
                    SourceDateList.clear();
                    SourceDateList.addAll(filledData_(beens));
                    Collections.sort(SourceDateList, pinyinComparator);// 根据a-z进行排序源数据
                    adapter.notifyDataSetChanged();

                    break;

                case 300:

                    List<CategoriesModel> categoriesModels = (List<CategoriesModel>) msg.obj;

                    if (categoriesModels != null) {

                        Message message = obtainMessage();
                        message.what = 400;
                        message.obj = model;
                        sendMessage(message);
                    }
                    break;

                case 400:

                    model_ = (CategoriesModel) msg.obj;
                    Map<String, Object> map = new TreeMap<>();
                    map.put("followType", model_.getCategoryType());
                    map.put("userCategoryId", model_.getCategoryId());

                    HiStudentHttpUtils.postDataByOKHttp(SelectContactsActivity.this, map, HistudentUrl.getFriendsInList_url, new HttpRequestCallBack() {
                        @Override
                        public void onSuccess(String result) {

                            Message message = obtainMessage();
                            message.what = 500;
                            message.obj = result;
                            sendMessage(message);

                        }

                        @Override
                        public void onFailure(String msg) {

                        }
                    });

                    break;

                case 500:

                    usersModels = ParserInMessage.getFollowUsersInfoParser(msg.obj.toString());
                    SourceDateList.clear();
                    SourceDateList.addAll(filledData(usersModels));
                    adapter.notifyDataSetChanged();

                    break;

            }
        }
    };

    public static void start(Activity activity, int requestCode, String userCategoryId) {

        Intent intent = new Intent(activity, SelectContactsActivity.class);
        intent.putExtra("userCategoryId", userCategoryId);
        intent.putExtra("requestCode", requestCode);
        activity.startActivityForResult(intent, requestCode);

    }

    public static void start(Activity activity, int requestCode, boolean flag) {
        Intent intent = new Intent(activity, SelectContactsActivity.class);
        intent.putExtra("flag", flag);
        intent.putExtra("requestCode", requestCode);
        activity.startActivityForResult(intent, requestCode);

    }

    @Override
    public int setViewLayout() {
        return R.layout.selectcontacts_activity;
    }

    /**
     * 获取分组信息
     */
    private void getCategoriesModels(final String name) {
        HiStudentHttpUtils.postDataByOKHttp(this, null, HistudentUrl.getFriendsList_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                List<CategoriesModel> categoriesModels = ParserInMessage.getCategoriesInfoParser(result);

                model = categoriesModels.get(0);
                getFriendsList(model.getCategoryId());
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    @Override
    public void initView() {
        flag = getIntent().getBooleanExtra("flag", false);

        //头部控件初始化
        title_right_text = (TextView) findViewById(R.id.title_right_text);
        title_left_text = (TextView) findViewById(R.id.title_left_text);
        title_middle_text = (TextView) findViewById(R.id.title_middle_text);
        title_left_image = (ImageView) findViewById(R.id.title_left_image);

        if (!flag) {
            title_left_text.setText("取消");
            title_middle_text.setText("选择好友");
            title_right_text.setText("确定");
            title_left_image.setVisibility(View.GONE);
        } else {
            title_left_text.setText("取消");
            title_middle_text.setText("选择屏蔽用户");
            title_right_text.setText("确定");
            title_left_image.setVisibility(View.GONE);
        }


        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sortListView = (ListView) findViewById(R.id.country_lvcountry);

        emptyView = EmptyViewUtils.EmptyView(R.mipmap.default_general, this);
        sortListView.setEmptyView(emptyView);

        SourceDateList = new ArrayList<>();
        selected_url = new ArrayList<>();

        adapter = new SortAdapter(this, SourceDateList, handler, true);
        sortListView.setAdapter(adapter);

        mClearEditText = (EditText_clear) findViewById(R.id.filter_edit);

        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

    }

    @Override
    public void doAction() {
        super.doAction();

        if (flag) {
            getCategoriesModels(null);
        } else {
            getFriendsList(getIntent().getStringExtra("userCategoryId"));
        }

//        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
//                SortModel model = SourceDateList.get(position);
//                boolean flag = model.isFlag();
//                model.setFlag(!flag);
//
//                if (!flag && !CreateGroupActivity.list_data.contains(model)) {
//                    CreateGroupActivity.list_data.add(model);
//                    selected_url.add(model.getUserId());
//                } else if (flag && CreateGroupActivity.list_data.contains(model)) {
//                    CreateGroupActivity.list_data.remove(model);
//                    selected_url.remove(model.getUserId());
//                }
//
//                adapter.notifyDataSetChanged();
//            }
//        });

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

        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());

                if (!flag) {
                    filterData(s.toString());
                } else {
                    filterData_(s.toString());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.title_left:

                setResult(10);
                finish();

                break;
            case R.id.title_right:
                if (flag) {
                    setHieldList(selected_url);
                } else {
                    SaveData.list_data = SourceDateList;
                    setResult(20);
                    finish();
                }
                break;

        }

    }


    /**
     * 屏蔽用户集合函数
     */
    private void setHieldList(List<String> account_list) {

        Map<String, Object> map = new TreeMap<>();
        StringBuffer userIds = new StringBuffer();
        for (int i = 0; i < account_list.size(); i++) {
            if (i < account_list.size() - 1) {
                userIds.append(account_list.get(i) + ",");
            } else {
                userIds.append(account_list.get(i));
            }

        }
        map.put("userIds", userIds.toString());
        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.hieldUser_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                SaveData.list_data = SourceDateList;
                setResult(20);
                finish();
            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }

    /**
     * 获取好友列表
     */
    private void getFriendsList(String categoryId) {
        Map<String, Object> map = new TreeMap<>();
        map.put("followType", "1");
        map.put("userCategoryId", categoryId);

        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.getFriendsInList_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Message message = handler.obtainMessage();
                message.what = 100;
                message.obj = result;
                handler.sendMessage(message);

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    /**
     * 为分组数据适配器
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
            sortModel.setUserId(date.get(i).getUserId());
            sortModel.setFollowId(date.get(i).getFollowId());
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
        return mSortList;
    }

    /**
     * 为屏蔽用户数据适配器
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData_(List<HieldBean> date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date.get(i).getName());
            sortModel.setAvatar(date.get(i).getAvatar());
            sortModel.setUserId(date.get(i).getUserId());
//            sortModel.setFollowId(date.get(i).getFollowId());
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(date.get(i).getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 为查找好友适配器
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData_fiandFriends(List<FindUserBean> date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date.get(i).getRealName());
            sortModel.setAvatar(date.get(i).getAvatar());
            sortModel.setUserId(date.get(i).getUserId());
//            sortModel.setFollowId(date.get(i).getFollowId());
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
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

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


    /**
     * 根据输入框中的值作为关键字来查找用户
     *
     * @param filterStr
     */
    private void filterData_(String filterStr) {

        Map<String, Object> map = new TreeMap<>();
        map.put("keyword", filterStr);

        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.findUsers_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                List<FindUserBean> findUserBeens = ParserInMessage.getFindUserBeanParser(result);

                SourceDateList.clear();
                SourceDateList.addAll(filledData_fiandFriends(findUserBeens));
                Collections.sort(SourceDateList, pinyinComparator);// 根据a-z进行排序源数据
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }

}
