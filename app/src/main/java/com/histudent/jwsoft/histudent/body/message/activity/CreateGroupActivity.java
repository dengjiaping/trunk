package com.histudent.jwsoft.histudent.body.message.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.adapter.CreateGroupAdapter;
import com.histudent.jwsoft.histudent.body.message.model.FollowUsersModel;
import com.histudent.jwsoft.histudent.body.message.model.HieldBean;
import com.histudent.jwsoft.histudent.body.message.parser.ParserInMessage;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.model.SortModel;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.CharacterParser;
import com.histudent.jwsoft.histudent.commen.view.swipemenulistview.SwipeMenu;
import com.histudent.jwsoft.histudent.commen.view.swipemenulistview.SwipeMenuCreator;
import com.histudent.jwsoft.histudent.commen.view.swipemenulistview.SwipeMenuItem;
import com.histudent.jwsoft.histudent.commen.view.swipemenulistview.SwipeMenuListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/6/7.
 * 创建分组/编辑分组/屏蔽用户界面
 */
public class CreateGroupActivity extends BaseActivity implements View.OnClickListener {

    private TextView title_right_text, title_middle_text, creategroup_member_num;
    private ImageView title_left_image;
    private EditText editText;
    public static List<SortModel> list_data;
    public List<String> list_id_save, list_id_exchange;
    private SwipeMenuListView listView;
    private CreateGroupAdapter adapter;
    private View head_view;
    private List<FollowUsersModel> usersModels;
    private Button creategroup_button;
    private String userCategoryId, userCategoryName, userCategoryType;
    private ImageView default_picture;
    private final int CREATEGROUP = 1;//创建分组
    private final int HIELD = 2;//屏蔽用户
    private final int EXCHANGEGROUP = 3;//编辑分组
    private int TYPE = 0;//类型标识
    private CharacterParser characterParser;
    private Button button_delet;

    public static void start(Activity context, String userCategoryId, int type) {
        Intent intent = new Intent(context, CreateGroupActivity.class);
        intent.putExtra("userCategoryId", userCategoryId);
        intent.putExtra("type", type);
        context.startActivityForResult(intent, 100);
    }

    public static void start(Activity context, String userCategoryType, String userCategoryId, String userCategoryName, int type) {
        Intent intent = new Intent(context, CreateGroupActivity.class);
        intent.putExtra("userCategoryId", userCategoryId);
        intent.putExtra("userCategoryType", userCategoryType);
        intent.putExtra("userCategoryName", userCategoryName);
        intent.putExtra("type", type);
        context.startActivityForResult(intent, 100);
    }

    @Override
    public int setViewLayout() {
        return R.layout.creatgroup_activity;
    }

    @Override
    public void initView() {
        userCategoryId = getIntent().getStringExtra("userCategoryId");
        userCategoryName = getIntent().getStringExtra("userCategoryName");
        userCategoryType = getIntent().getStringExtra("userCategoryType");
        TYPE = getIntent().getIntExtra("type", 0);

        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        //头部控件初始化
        title_right_text = (TextView) findViewById(R.id.title_right_text);
        title_middle_text = (TextView) findViewById(R.id.title_middle_text);
        title_left_image = (ImageView) findViewById(R.id.title_left_image);
        listView = (SwipeMenuListView) findViewById(R.id.creategroup_list);
        default_picture = (ImageView) findViewById(R.id.default_picture);
        button_delet = (Button) findViewById(R.id.createGroup_delet);

        list_data = new ArrayList<>();
        list_id_save = new ArrayList<>();
        list_id_exchange = new ArrayList<>();

        adapter = new CreateGroupAdapter(this, list_data);

        head_view = findViewById(R.id.creategroup_head);

        creategroup_member_num = (TextView) findViewById(R.id.creategroup_member_num);
        editText = (EditText) findViewById(R.id.creategroup_edit);
        creategroup_button = (Button) findViewById(R.id.addTember_button);

        creategroup_button.setOnClickListener(this);

        switch (TYPE) {
            case HIELD:
                title_middle_text.setText("屏蔽用户");
                title_right_text.setText("添加");
                head_view.setVisibility(View.GONE);
                listView.setAdapter(adapter);
                getRealyHieldList();
                break;
            case CREATEGROUP:
                title_middle_text.setText("创建分组");
                title_right_text.setText("保存");
                listView.setAdapter(adapter);
                break;
            case EXCHANGEGROUP:
                title_middle_text.setText("编辑分组");
                title_right_text.setText("保存");
                button_delet.setVisibility(View.VISIBLE);
                editText.setText(userCategoryName);
                listView.setAdapter(adapter);
                getUserCategoryMember();
                break;
        }

        showSlidMenue();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && list_data != null) {
            if (resultCode == 20) {
                if (TYPE == HIELD) {
                    default_picture.setVisibility(View.GONE);
                    adapter.updateListView(list_data);
                } else {
                    creategroup_member_num.setText("分组成员（" + list_data.size() + "）");
                    adapter.updateListView(list_data);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;

            case R.id.title_right:

                switch (TYPE) {
                    case HIELD:

                        SelectContactsActivity.start(this, 100, true);

                        break;
                    case CREATEGROUP:

                        String name = editText.getText().toString();
                        if (name != null && !"".equals(name)) {
                            if (list_data.size() > 0) {
                                createGroup(name);
                            } else {
                                Toast.makeText(this, "请先选择分组成员", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "分组名称不能为空", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case EXCHANGEGROUP:

                        editCategory();

                        break;
                }

                break;

            case R.id.addTember_button:

                SelectContactsActivity.start(this, 100, userCategoryId);

                break;

            case R.id.createGroup_delet:

                deletGroup(userCategoryId);

                break;

        }

    }

    /**
     * 侧滑item滑出按钮相关
     */
    private void showSlidMenue() {
        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(255,
                        88, 88)));
                deleteItem.setWidth(dp2px(80));
                deleteItem.setTitle("删除");
                deleteItem.setTitleSize(15);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addRightMenuItem(deleteItem);
            }
        };


        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, boolean isRightMenu, SwipeMenu menu, SwipeMenuItem item) {

                if (TYPE == HIELD) {
                    Map<String, Object> map = new TreeMap<>();
                    map.put("userId", list_data.get(position).getUserId());
                    HiStudentHttpUtils.postDataByOKHttp(CreateGroupActivity.this, map, HistudentUrl.mine_delete_disable_user_url, new HttpRequestCallBack() {
                        @Override
                        public void onSuccess(String result) {
                            list_data.remove(position);
                            creategroup_member_num.setText("分组成员（" + list_data.size() + "）");
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(String msg) {

                        }
                    });


                } else {
                    list_data.remove(position);
                    creategroup_member_num.setText("分组成员（" + list_data.size() + "）");
                    adapter.notifyDataSetChanged();
                }
                return false;
            }
        });

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    /**
     * 创建分组
     *
     * @param teamName
     */
    private void createGroup(final String teamName) {

        Map<String, Object> map = new TreeMap<>();
        map.put("groupName", teamName);
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        for (int i = 0; i < CreateGroupActivity.list_data.size(); i++) {
            if (i == CreateGroupActivity.list_data.size() - 1) {
                buffer.append("'" + list_data.get(i).getFollowId() + "']");
            } else {
                buffer.append("'" + list_data.get(i).getFollowId() + "',");
            }
        }
        map.put("followIds", buffer.toString());
        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.creatGroup_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject object = new JSONObject(result);
                    Intent intent = new Intent();
                    intent.putExtra("categoriesId", object.optString("id"));
                    setResult(300, intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }

    /**
     * 删除分组
     *
     * @param categoryId
     */
    private void deletGroup(String categoryId) {

        Map<String, Object> map = new TreeMap<>();
        map.put("categoryId", categoryId);

        HiStudentHttpUtils.postDataByOKHttp(this, map, HistudentUrl.categoryDelete_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                setResult(500);
                finish();

            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

    }

    /**
     * 获取已屏蔽的好友的列表
     */
    private void getRealyHieldList() {

        HiStudentHttpUtils.getDataByOKHttp(this, null, HistudentUrl.mine_disableuser_of_myhome_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                List<HieldBean> beens = JSON.parseArray(result, HieldBean.class);

                if (beens == null || beens.size() == 0) {
                    default_picture.setVisibility(View.VISIBLE);
                    default_picture.setImageResource(R.mipmap.default_picture_common);
                } else {
                    list_data.clear();
                    list_data.addAll(filledData_(beens));
                    adapter.notifyDataSetChanged();
                    creategroup_member_num.setText("分组成员（" + list_data.size() + "）");
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    /**
     * 获取需要编辑分组的成员
     */
    private void getUserCategoryMember() {

        Map<String, Object> map = new TreeMap<>();
        map.put("followType", userCategoryType);
        map.put("userCategoryId", userCategoryId);

        HiStudentHttpUtils.postDataByOKHttp(CreateGroupActivity.this, map, HistudentUrl.getFriendsInList_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                usersModels = ParserInMessage.getFollowUsersInfoParser(result);
                list_data.addAll(filledData(usersModels));
                for (SortModel model : list_data) {
                    list_id_save.add(model.getFollowId());
                }
                adapter.notifyDataSetChanged();
                creategroup_member_num.setText("分组成员（" + list_data.size() + "）");
            }

            @Override
            public void onFailure(String msg) {

            }
        });

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
//            //汉字转换成拼音
//            String pinyin = characterParser.getSelling(date.get(i).getName());
//            String sortString = pinyin.substring(0, 1).toUpperCase();
//
//            // 正则表达式，判断首字母是否是英文字母
//            if (sortString.matches("[A-Z]")) {
//                sortModel.setSortLetters(sortString.toUpperCase());
//            } else {
//                sortModel.setSortLetters("#");
//            }

            mSortList.add(sortModel);
        }
        return mSortList;

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
     * 编辑分组
     */
    private void editCategory() {

        final String name = editText.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入分组名称！", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuffer buffer_add = new StringBuffer();
        StringBuffer buffer_reduce = new StringBuffer();

        for (SortModel model : list_data) {
            list_id_exchange.add(model.getFollowId());
        }

        for (int i = 0; i < list_id_save.size(); i++) {
            if (!list_id_exchange.contains(list_id_save.get(i))) {
                buffer_reduce.append("'" + list_id_save.get(i) + "',");
            }
        }
        for (int i = 0; i < list_id_exchange.size(); i++) {
            if (!list_id_save.contains(list_id_exchange.get(i))) {
                buffer_add.append("'" + list_id_exchange.get(i) + "',");
            }
        }
        String info_add = buffer_add.toString();
        String info_reduce = buffer_reduce.toString();

        String info_add_;
        String info_reduce_;

        if (TextUtils.isEmpty(info_add)) {
            info_add_ = "[]";
        } else {
            info_add_ = "[" + info_add.substring(0, info_add.length() - 1) + "]";
        }

        if (TextUtils.isEmpty(info_reduce)) {
            info_reduce_ = "[]";
        } else {
            info_reduce_ = "[" + info_reduce.substring(0, info_reduce.length() - 1) + "]";
        }
        Map<String, Object> map = new TreeMap<>();
        map.put("groupName", name);
        map.put("addFollowIds", info_add_);
        map.put("deleteFollowIds", info_reduce_);
        map.put("editType", 2);
        map.put("categoryId", userCategoryId);

        HiStudentHttpUtils.postDataByOKHttp(CreateGroupActivity.this, map, HistudentUrl.editCategory, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Toast.makeText(CreateGroupActivity.this, "编辑分组成功！", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.putExtra("name", name);
                setResult(400, intent);
                finish();

            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

}
