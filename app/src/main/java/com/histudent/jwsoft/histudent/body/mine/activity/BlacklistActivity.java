package com.histudent.jwsoft.histudent.body.mine.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.adapter.CreateGroupAdapter;
import com.histudent.jwsoft.histudent.body.message.model.CategoriesModel;
import com.histudent.jwsoft.histudent.body.message.parser.ParserInMessage;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.model.SortModel;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.view.swipemenulistview.SwipeMenu;
import com.histudent.jwsoft.histudent.commen.view.swipemenulistview.SwipeMenuCreator;
import com.histudent.jwsoft.histudent.commen.view.swipemenulistview.SwipeMenuItem;
import com.histudent.jwsoft.histudent.commen.view.swipemenulistview.SwipeMenuListView;
import com.histudent.jwsoft.histudent.comment2.utils.EmptyViewUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/7/23.
 * 黑名单界面
 */
public class BlacklistActivity extends BaseActivity implements View.OnClickListener {

    private SwipeMenuListView listView;
    private CreateGroupAdapter adapter;
    private List<SortModel> list_data;
    private ArrayList<String> list_members;//选择的群员账号
    private View foot_view;
    private Button button;
    private TextView title;
    private ImageView emptyView;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case 0:

                    title.setText("黑名单（" + list_data.size() + "）");
                    adapter.updateListView(list_data);

                    break;

                case 100:

                    List<CategoriesModel> categoriesModels = ParserInMessage.getCategoriesInfoParser(msg.obj.toString());

                    if (categoriesModels != null) {

                        Map<String, Object> map = new TreeMap<>();
                        map.put("followType", "7");
                        map.put("userCategoryId", categoriesModels.get(0).getCategoryId());

                        HiStudentHttpUtils.postDataByOKHttp(BlacklistActivity.this, map, HistudentUrl.getFriendsInList_url, new HttpRequestCallBack() {
                            @Override
                            public void onSuccess(String result) {

                                Message message = obtainMessage();
                                message.what = 300;
                                message.obj = result;
                                sendMessage(message);

                            }

                            @Override
                            public void onFailure(String msg) {

                            }
                        });
                    }
                    break;

                case 300:

//                    List<FollowUsersModel> usersModels = ParserInMessage.getFollowUsersInfoParser(msg.obj.toString());
//                    SourceDateList.clear();
//                    SourceDateList.addAll(filledData(usersModels));
//                    adapter.notifyDataSetChanged();

                    break;
            }
        }
    };


    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, BlacklistActivity.class));
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_blacklist;
    }

    @Override
    public void initView() {

        listView = (SwipeMenuListView) findViewById(R.id.blacklist);
        title = (TextView) findViewById(R.id.title_middle_text);

        emptyView = EmptyViewUtils.EmptyView(R.mipmap.default_general, this);
        listView.setEmptyView(emptyView);

        list_data = new ArrayList<>();
        list_members = new ArrayList<>();

        adapter = new CreateGroupAdapter(this, list_data);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        foot_view = inflater.inflate(R.layout.creategroup_foot_view, null);

//        button = (Button) foot_view.findViewById(R.id.creategroup_button);
    }

    @Override
    public void doAction() {
        super.doAction();

        title.setText("黑名单");

        listView.setAdapter(adapter);

        showSlidMenue();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                PersonalHomepageActivity.start(BlacklistActivity.this, list_data.get(position).getAccount());

            }
        });

        getBlackList();

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            case R.id.title_left:

                finish();

                break;

//            case R.id.creategroup_button:
//
//                HiStudentLog.e("-------BlacklistActivity-------->" + list_members.size());
//
//                SelectContactsActivity.start(this, list_members, 0, 200);
//
//                break;

        }

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 200 && SaveData.list_data != null) {
//            if (resultCode == 20) {
//                for (SortModel model : SaveData.list_data) {
//                    if (model.isFlag()) {
//                        addToBlackList(model);
//                    }
//                }
//            }
//        }
//    }

//    /**
//     * 拉入黑名单
//     *
//     * @param model
//     */
//    private void addToBlackList(final SortModel model) {
//
//        NIMClient.getService(FriendService.myclass).addToBlackList(model.getAccount())
//                .setCallback(new RequestCallback<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//
//                        list_data.add(model);
////                        list_members.add(model.getAccount());
//
//                        handler.sendEmptyMessage(0);
//
//                    }
//
//                    @Override
//                    public void onFailed(int i) {
//
//                    }
//
//                    @Override
//                    public void onException(Throwable throwable) {
//
//                    }
//                });
//
//    }
//
//    /**
//     * 移除黑名单
//     *
//     * @param model
//     */
//    private void removeFromBlackList(final SortModel model) {
//
//        NIMClient.getService(FriendService.myclass).removeFromBlackList(model.getAccount())
//                .setCallback(new RequestCallback<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//
//                        list_data.remove(model);
//                        list_members.remove(model.getAccount());
//
//                        handler.sendEmptyMessage(0);
//
//                    }
//
//                    @Override
//                    public void onFailed(int i) {
//
//                    }
//
//                    @Override
//                    public void onException(Throwable throwable) {
//
//                    }
//                });
//
//    }

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
            public boolean onMenuItemClick(int position, boolean isRightMenu, SwipeMenu menu, SwipeMenuItem item) {


//                removeFromBlackList(list_data.get(position));

                return false;
            }
        });

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    private void getBlackList() {
        HiStudentHttpUtils.postDataByOKHttp(this, null, HistudentUrl.mine_userBlackList_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                sendMessage(handler, 100, 0, result);


            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

}
