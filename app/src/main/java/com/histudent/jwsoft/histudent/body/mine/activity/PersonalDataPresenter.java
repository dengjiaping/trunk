package com.histudent.jwsoft.histudent.body.mine.activity;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.view.BasePickerView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.adapter.PersionDataAdapter;
import com.histudent.jwsoft.histudent.body.mine.model.CurrentUserDetailInfoModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.BasePresenter;
import com.histudent.jwsoft.histudent.commen.activity.ImageBrowserActivity;
import com.histudent.jwsoft.histudent.commen.activity.InputActivity;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.enums.ShowImageType;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.AddressCallback;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.listener.MyDialogListener;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.addressSelect.Hz_address_selecte_dialog;
import com.histudent.jwsoft.histudent.info.persioninfo.PersionHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/10/27.
 */
public class PersonalDataPresenter implements BasePresenter {

    private BaseActivity activity;
    private PersonalDataActivity.ViewHolder viewHolder;
    private CurrentUserDetailInfoModel detail_model;
    private PersionDataAdapter adapter;
    private List<CurrentUserDetailInfoModel.ProfileBean.HonorsListBean> listView_data;
    private boolean flag;
    private TimePickerView mTimePick;
    public PersonalDataPresenter(BaseActivity activity, PersonalDataActivity.ViewHolder viewHolder) {
        this.activity = activity;
        this.viewHolder = viewHolder;
        listView_data = new ArrayList<>();
        adapter = new PersionDataAdapter(listView_data, activity, this);
        viewHolder.persiondata_list.setAdapter(adapter);
        initUI();
    }


    @Override
    public void doGoBack() {
        activity.finish();
    }

    /**
     * 设置头像
     */
    void setHeadImage(Activity activity_) {

        ArrayList<ActionListBean.ImagesBean> urls2 = new ArrayList<>();
        ActionListBean.ImagesBean imagesBean = new ActionListBean.ImagesBean();
        imagesBean.setBigSizeUrl(detail_model.getAvatar());
        imagesBean.setThumbnailUrl(detail_model.getAvatar());
        urls2.add(imagesBean);
        ImageBrowserActivity.start(activity_, 0, 100, urls2, ShowImageType.EXCHANGE, 0, "");
    }

    /**
     * 设置性别
     */
    void setSex() {

        List<String> list = new ArrayList<>();
        list.add("男");
        list.add("女");
        List<Integer> colorList = new ArrayList<>();
        colorList.add(Color.rgb(51, 51, 51));
        colorList.add(Color.rgb(51, 51, 51));

        ReminderHelper.getIntentce().showTopMenuDialog(activity, "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int index = 0;
                switch (v.getId()) {
                    case R.id.btn_01:
                        index = 1;
                        break;

                    case R.id.btn_02:
                        index = 2;
                        break;
                }

                Map<String, Object> map = new TreeMap<>();
                map.put("sex", index);
                HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.editUserInfo, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        UpDataUI();
                    }

                    @Override
                    public void onFailure(String msg) {
                    }
                });

            }
        }, list, colorList, false);

    }

    /**
     * 设置生日
     */
    void setBirthDay() {

        String date = viewHolder.persiondata_birth.getText().toString();
        //选择时间对话框的显示
        initTimePicker(date);
        mTimePick.show();
    }


    private void initTimePicker(String date) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        Calendar selectedDate = Calendar.getInstance();
        try {
            selectedDate.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.set(1990,1,1);
        endDate.set(2099, 11, 28);
        //时间选择器
        mTimePick = new TimePickerView.Builder(activity, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                Map<String, Object> map = new TreeMap<>();
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                String str=sdf.format(date);
                map.put("birthDay",  str+ "T02:55:57.997Z");

                if (judgeTime(str)) {
                    Toast.makeText(activity, "请选择正确的日期", Toast.LENGTH_SHORT).show();
                    return;
                }
                HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.editUserInfo, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        UpDataUI();
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
    }

    /**
     * 设置血型
     */
    void setBlood() {
        activity.showListDialog("血型", activity.getResources().getStringArray(R.array.boold_name_set), new MyDialogListener() {
            @Override
            public void callBack(final int index) {

                Map<String, Object> map = new TreeMap<>();
                map.put("blood", index);
                HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.editUserInfo, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        UpDataUI();
                    }

                    @Override
                    public void onFailure(String msg) {
                    }
                });
            }
        });

    }

    /**
     * 设置星座
     */
    void setConstellation() {
        activity.showListDialog("星座", activity.getResources().getStringArray(R.array.constellation_name_set), new MyDialogListener() {
            @Override
            public void callBack(final int index) {

                Map<String, Object> map = new TreeMap<>();
                map.put("constellation", index);

                HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.editUserInfo, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        UpDataUI();
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
            }
        });
    }

    /**
     * 设置出生地
     */
    void setBirthPlace() {
        AddressSelector("homeAreaCode");
    }

    /**
     * 设置居住地
     */
    void setLivePlace() {
        AddressSelector("nowAreaCode");
    }

    /**
     * 设置居住地
     */
    void setContactPlace() {
        InputActivity.startOnResult(activity, "通讯地址", "address", viewHolder.persiondata_relationaddress.getText().toString(), 10);
    }

    /**
     * 设置自我介绍
     */
    void setIntroduceMyself() {
        InputActivity.startOnResult(activity, "自我介绍", "presentation", viewHolder.introduce_myself_text.getText().toString(), 10);
    }

    /**
     * 添加奖项
     */
    void addAwards() {
        AddWardActivity.start(activity, 20);
    }

    /**
     * 设置音乐
     */
    void setMusic() {
        InputActivity.startOnResult(activity, "音乐", "favoriteMusic", viewHolder.persiondata_music.getText().toString(), 10);
    }

    /**
     * 设置书籍
     */
    void setBooks() {
        InputActivity.startOnResult(activity, "书籍", "favoriteBooks", viewHolder.persiondata_book.getText().toString(), 10);
    }

    /**
     * 设置电影
     */
    void setMovie() {
        InputActivity.startOnResult(activity, "电影", "favoriteMovies", viewHolder.persiondata_movie.getText().toString(), 10);
    }

    /**
     * 设置运动
     */
    void setSports() {
        InputActivity.startOnResult(activity, "运动", "favoriteSports", viewHolder.persiondata_exercise.getText().toString(), 10);
    }

    /**
     * 设置游戏
     */
    void setGames() {
        InputActivity.startOnResult(activity, "游戏", "favoriteGames", viewHolder.persiondata_game.getText().toString(), 10);
    }

    /**
     * 设置动漫
     */
    void setCartoons() {
        InputActivity.startOnResult(activity, "动漫", "favoriteCartoons", viewHolder.persiondata_cartoon.getText().toString(), 10);
    }

    /**
     * 设置美食
     */
    void setFood() {
        InputActivity.startOnResult(activity, "美食", "favoriteFood", viewHolder.persiondata_cate.getText().toString(), 10);
    }

    /**
     * 设置名人
     */
    void setReciateMen() {
        InputActivity.startOnResult(activity, "名人", "appreciateMen", viewHolder.persiondata_celebrity.getText().toString(), 10);
    }


//TODO=====================================================================================================================

    /**
     * 地址选择器
     */
    private void AddressSelector(final String type) {
        // 解析完毕，显示对话框
        Hz_address_selecte_dialog hz_address_selecte_dialog = new Hz_address_selecte_dialog(activity, new AddressCallback() {
            @Override
            public void getAddress(final List<String> address) {

                String[] cade = address.get(1).split("-");
                Map<String, Object> map = new TreeMap<>();
                map.put(type, cade[cade.length - 1]);

                HiStudentHttpUtils.postDataByOKHttp(activity, map, HistudentUrl.editUserInfo, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        UpDataUI();
                    }

                    @Override
                    public void onFailure(String msg) {
                    }
                });
            }
        });

        hz_address_selecte_dialog
                .requestWindowFeature(Window.FEATURE_NO_TITLE);

        hz_address_selecte_dialog.setCanceledOnTouchOutside(true);

        hz_address_selecte_dialog.show();
    }

    public void UpDataUI() {
        PersionHelper.getInstance().getUserInfo(activity, HiCache.getInstance().getLoginUserInfo().getUserId(), new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                initUI();
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    void initUI() {

        detail_model = HiCache.getInstance().getUserDetailInfo();
        if (detail_model != null && SystemUtil.isOneselfIn(detail_model.getUserId())) {

            if (detail_model != null) {
                viewHolder.userHead.loadBuddyAvatar(detail_model.getAvatar());
                viewHolder.persiondata_name.setText(TextUtils.isEmpty(detail_model.getRealName()) ? activity.getString(R.string.no_set) : detail_model.getRealName());
                viewHolder.persiondata_phonenum.setText(detail_model.getMobile());
                viewHolder.persiondata_email.setText(TextUtils.isEmpty(detail_model.getEmail()) ? activity.getString(R.string.no_set) : detail_model.getEmail());
                viewHolder.persiondata_sex.setText(activity.getResources().getStringArray(R.array.sex_name_show)[detail_model.getProfile().getSex()]);
                viewHolder.persiondata_birth.setText(detail_model.getProfile().getBirthDay().split(" ")[0]);
                viewHolder.persiondata_bloodtype.setText(activity.getResources().getStringArray(R.array.boold_name_show)[detail_model.getProfile().getBlood()]);
                viewHolder.persiondata_constellation.setText(activity.getResources().getStringArray(R.array.constellation_name_show)[detail_model.getProfile().getConstellation()]);
                viewHolder.persiondata_birthaddress.setText(TextUtils.isEmpty(detail_model.getProfile().getHomeAreaCode()) ? activity.getString(R.string.no_set) : detail_model.getProfile().getHomeAreaCode());
                viewHolder.persiondata_lifeddress.setText(TextUtils.isEmpty(detail_model.getProfile().getNowAreaCode()) ? activity.getString(R.string.no_set) : detail_model.getProfile().getNowAreaCode());
                viewHolder.persiondata_relationaddress.setText(TextUtils.isEmpty(detail_model.getProfile().getAddress()) ? activity.getString(R.string.no_set) : detail_model.getProfile().getAddress());
                viewHolder.persiondata_music.setText(TextUtils.isEmpty(detail_model.getProfile().getFavoriteMusic()) ? activity.getString(R.string.no_set) : detail_model.getProfile().getFavoriteMusic());
                viewHolder.persiondata_book.setText(TextUtils.isEmpty(detail_model.getProfile().getFavoriteBooks()) ? activity.getString(R.string.no_set) : detail_model.getProfile().getFavoriteBooks());
                viewHolder.persiondata_movie.setText(TextUtils.isEmpty(detail_model.getProfile().getFavoriteMovies()) ? activity.getString(R.string.no_set) : detail_model.getProfile().getFavoriteMovies());
                viewHolder.persiondata_exercise.setText(TextUtils.isEmpty(detail_model.getProfile().getFavoriteSports()) ? activity.getString(R.string.no_set) : detail_model.getProfile().getFavoriteSports());
                viewHolder.persiondata_game.setText(TextUtils.isEmpty(detail_model.getProfile().getFavoriteGames()) ? activity.getString(R.string.no_set) : detail_model.getProfile().getFavoriteGames());
                viewHolder.persiondata_cartoon.setText(TextUtils.isEmpty(detail_model.getProfile().getFavoriteCartoons()) ? activity.getString(R.string.no_set) : detail_model.getProfile().getFavoriteCartoons());
                viewHolder.persiondata_cate.setText(TextUtils.isEmpty(detail_model.getProfile().getFavoriteFood()) ? activity.getString(R.string.no_set) : detail_model.getProfile().getFavoriteFood());
                viewHolder.persiondata_celebrity.setText(TextUtils.isEmpty(detail_model.getProfile().getAppreciateMen()) ? activity.getString(R.string.no_set) : detail_model.getProfile().getAppreciateMen());
                viewHolder.introduce_myself_text.setText(TextUtils.isEmpty(detail_model.getProfile().getPresentation()) ? activity.getString(R.string.no_set) : detail_model.getProfile().getPresentation());

                List<CurrentUserDetailInfoModel.ProfileBean.HonorsListBean> list_model = detail_model.getProfile().getHonorsList();
                if (list_model != null) {
                    listView_data.clear();
                    listView_data.addAll(list_model);
                }

                SystemUtil.setListViewHeightBasedOnChildren(activity, viewHolder.persiondata_list, listView_data.size(), 80);
                adapter.notifyDataSetChanged();
            }

        } else {

            PersionHelper.getInstance().getUserInfo(activity, HiCache.getInstance().getLoginUserInfo().getUserId(), new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {
                    initUI();
                }

                @Override
                public void onFailure(String errorMsg) {

                }
            });

        }
    }

    /**
     * 将进行剪裁后的图片显示到UI界面上
     */
    void setPicToView(String path) {

        List<String> urls = new ArrayList<>();
        urls.add(path);
        HiStudentHttpUtils.postImageByOKHttp(activity, urls, null, HistudentUrl.uploadAvatar, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                Toast.makeText(activity, "上传成功!", Toast.LENGTH_SHORT).show();

                try {
                    UpDataUI();
                    HiCache.getInstance().updataAccountDataInDB(new JSONObject(result).optString("imgUrl"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    private boolean judgeTime(String time) {
        String[] times1 = SystemUtil.getCurrentData().split("-");
        String[] times2 = time.split("-");
        String date_ = times1[0] + times1[1] + times1[2];
        String time_ = times2[0] + times2[1] + times2[2];
        return (Integer.parseInt(time_) - Integer.parseInt(date_)) > 0;
    }

}
