package com.histudent.jwsoft.histudent.info.persioninfo.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.adapter.PersionClassesAdapter;
import com.histudent.jwsoft.histudent.body.mine.adapter.PersionDataAdapter;
import com.histudent.jwsoft.histudent.body.mine.model.CurrentUserDetailInfoModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.MyListView2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuguiyu-pc on 2016/12/23.
 */

public class PersonDataInfoActivity extends BaseActivity {

    private TextView titlt, persiondata_name, persiondata_sex, persiondata_age, persiondata_birth, persiondata_bloodtype, persiondata_constellation,
            persiondata_birthaddress, persiondata_lifeddress, persiondata_relationaddress, persiondata_phonenum, persiondata_music, persiondata_book, persiondata_movie,
            persiondata_exercise, persiondata_game, persiondata_cartoon, persiondata_cate, persiondata_celebrity, school;

    private CurrentUserDetailInfoModel userModel;

    private List<CurrentUserDetailInfoModel.ProfileBean.HonorsListBean> data_award;
    private PersionDataAdapter adapter_award;
    private MyListView2 list_add_persiondata;

    private List<CurrentUserDetailInfoModel.ClassInfoBean.ItemsBeanX> classes_list;
    private PersionClassesAdapter classesAdapter;
    private ListView list_class;

    private LinearLayout likes_layout, basic_info, award_info;

    private ImageView show_info, show_award, show_likes;

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, PersonDataInfoActivity.class));
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_persion_datainfo;
    }

    @Override
    public void initView() {
        data_award = new ArrayList<>();
        adapter_award = new PersionDataAdapter(data_award, this, null);
        list_add_persiondata = (MyListView2) findViewById(R.id.persiondata_list);
        list_add_persiondata.setAdapter(adapter_award);

        classes_list = new ArrayList<>();
        classesAdapter = new PersionClassesAdapter(this, classes_list);
        list_class = (ListView) findViewById(R.id.class_list);
        list_class.setAdapter(classesAdapter);

        titlt = (TextView) findViewById(R.id.title_middle_text);
        persiondata_name = (TextView) findViewById(R.id.persiondata_name);
        persiondata_sex = (TextView) findViewById(R.id.persiondata_sex);
        persiondata_age = (TextView) findViewById(R.id.persiondata_age);
        persiondata_birth = (TextView) findViewById(R.id.persiondata_birth);
        persiondata_bloodtype = (TextView) findViewById(R.id.persiondata_bloodtype);
        persiondata_constellation = (TextView) findViewById(R.id.persiondata_constellation);
        persiondata_birthaddress = (TextView) findViewById(R.id.persiondata_birthaddress);
        persiondata_lifeddress = (TextView) findViewById(R.id.persiondata_lifeddress);
        persiondata_relationaddress = (TextView) findViewById(R.id.persiondata_relationaddress);
        persiondata_phonenum = (TextView) findViewById(R.id.introduce);
        persiondata_music = (TextView) findViewById(R.id.persiondata_music);
        persiondata_book = (TextView) findViewById(R.id.persiondata_book);
        persiondata_movie = (TextView) findViewById(R.id.persiondata_movie);
        persiondata_exercise = (TextView) findViewById(R.id.persiondata_exercise);
        persiondata_game = (TextView) findViewById(R.id.persiondata_game);
        persiondata_cartoon = (TextView) findViewById(R.id.persiondata_cartoon);
        persiondata_cate = (TextView) findViewById(R.id.persiondata_cate);
        persiondata_celebrity = (TextView) findViewById(R.id.persiondata_celebrity);
        school = (TextView) findViewById(R.id.school);

        likes_layout = (LinearLayout) findViewById(R.id.likes_layout);
        basic_info = (LinearLayout) findViewById(R.id.basic_info);
        award_info = (LinearLayout) findViewById(R.id.award_info);

        show_info = (ImageView) findViewById(R.id.show_info);
        show_award = (ImageView) findViewById(R.id.show_award);
        show_likes = (ImageView) findViewById(R.id.show_likes);

    }

    @Override
    public void doAction() {
        super.doAction();
        userModel = HiCache.getInstance().getUserDetailInfo();
        titlt.setText("个人信息");
        persiondata_name.setText(TextUtils.isEmpty(userModel.getRealName()) ? this.getString(R.string.no_set) : userModel.getRealName());
        persiondata_sex.setText(getResources().getStringArray(R.array.sex_name_show)[userModel.getProfile().getSex()]);
        persiondata_age.setText(userModel.getProfile().getAge());
        persiondata_birth.setText(userModel.getProfile().getBirthDay().split(" ")[0]);
        persiondata_bloodtype.setText(getResources().getStringArray(R.array.boold_name_show)[userModel.getProfile().getBlood()]);
        persiondata_constellation.setText(getResources().getStringArray(R.array.constellation_name_show)[userModel.getProfile().getConstellation()]);
        persiondata_birthaddress.setText(TextUtils.isEmpty(userModel.getProfile().getHomeAreaCode()) ? this.getString(R.string.no_set) : userModel.getProfile().getHomeAreaCode());
        persiondata_lifeddress.setText(TextUtils.isEmpty(userModel.getProfile().getNowAreaCode()) ? this.getString(R.string.no_set) : userModel.getProfile().getNowAreaCode());
        persiondata_relationaddress.setText(TextUtils.isEmpty(userModel.getProfile().getAddress()) ? this.getString(R.string.no_set) : userModel.getProfile().getAddress());
        persiondata_phonenum.setText(TextUtils.isEmpty(userModel.getProfile().getPresentation()) ? this.getString(R.string.no_set) : userModel.getProfile().getPresentation());
        persiondata_music.setText(TextUtils.isEmpty(userModel.getProfile().getFavoriteMusic()) ? this.getString(R.string.no_set) : userModel.getProfile().getFavoriteMusic());
        persiondata_book.setText(TextUtils.isEmpty(userModel.getProfile().getFavoriteBooks()) ? this.getString(R.string.no_set) : userModel.getProfile().getFavoriteBooks());
        persiondata_movie.setText(TextUtils.isEmpty(userModel.getProfile().getFavoriteMovies()) ? this.getString(R.string.no_set) : userModel.getProfile().getFavoriteMovies());
        persiondata_exercise.setText(TextUtils.isEmpty(userModel.getProfile().getFavoriteSports()) ? this.getString(R.string.no_set) : userModel.getProfile().getFavoriteSports());
        persiondata_game.setText(TextUtils.isEmpty(userModel.getProfile().getFavoriteGames()) ? this.getString(R.string.no_set) : userModel.getProfile().getFavoriteGames());
        persiondata_cartoon.setText(TextUtils.isEmpty(userModel.getProfile().getFavoriteCartoons()) ? this.getString(R.string.no_set) : userModel.getProfile().getFavoriteCartoons());
        persiondata_cate.setText(TextUtils.isEmpty(userModel.getProfile().getFavoriteFood()) ? this.getString(R.string.no_set) : userModel.getProfile().getFavoriteFood());
        persiondata_celebrity.setText(TextUtils.isEmpty(userModel.getProfile().getAppreciateMen()) ? this.getString(R.string.no_set) : userModel.getProfile().getAppreciateMen());
        school.setText(TextUtils.isEmpty(userModel.getSchoolName()) ? this.getString(R.string.no_set) : userModel.getSchoolName());

        getAwardDatas();

        getClassesDatas();

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;

            case R.id.show_info:

                if (basic_info.getVisibility() == View.GONE) {
                    show_info.setImageResource(R.mipmap.go_up);
                    basic_info.setVisibility(View.VISIBLE);
                } else {
                    show_info.setImageResource(R.mipmap.hiworld_gomore);
                    basic_info.setVisibility(View.GONE);
                }

                break;

            case R.id.show_award:

                if (award_info.getVisibility() == View.GONE) {
                    show_award.setImageResource(R.mipmap.go_up);
                    award_info.setVisibility(View.VISIBLE);
                } else {
                    show_award.setImageResource(R.mipmap.hiworld_gomore);
                    award_info.setVisibility(View.GONE);
                }

                break;

            case R.id.show_likes:

                if (likes_layout.getVisibility() == View.GONE) {
                    show_likes.setImageResource(R.mipmap.go_up);
                    likes_layout.setVisibility(View.VISIBLE);
                } else {
                    show_likes.setImageResource(R.mipmap.hiworld_gomore);
                    likes_layout.setVisibility(View.GONE);
                }

                break;
        }

    }

    //*********************************************************************************************************************************

    /**
     * 获取奖项数据
     */
    private void getAwardDatas() {

        List<CurrentUserDetailInfoModel.ProfileBean.HonorsListBean> modelList = userModel.getProfile().getHonorsList();

        if (modelList != null) {
            data_award.addAll(modelList);
        }

//        SystemUtil.setListViewHeightBasedOnChildren(PersonDataInfoActivity.this, list_add_persiondata, data_award.size(), 70);

        adapter_award.notifyDataSetChanged();
    }

    /**
     * 获取班级数据
     */
    private void getClassesDatas() {

        if (userModel.getClassInfo() == null)
            return;

        List<CurrentUserDetailInfoModel.ClassInfoBean.ItemsBeanX> modelList = userModel.getClassInfo().getItems();

        if (modelList != null) {
            classes_list.addAll(modelList);
        }

        SystemUtil.setListViewHeightBasedOnChildren(PersonDataInfoActivity.this, list_class, classes_list.size(), 70);

        classesAdapter.notifyDataSetChanged();
    }

}
