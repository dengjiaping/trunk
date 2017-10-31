package com.histudent.jwsoft.histudent.body.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.activity.clock.ManualInputBarCodeActivity;
import com.histudent.jwsoft.histudent.activity.clock.ManualInputBookNameActivity;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.helper.PictureTailorHelper;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.constant.TransferKeys;
import com.histudent.jwsoft.histudent.tool.CommonAdvanceUtils;

/**
 * Created by liuguiyu-pc on 2016/5/26.
 * 个人资料界面
 */
public class PersonalDataActivity extends BaseActivity {
    private TextView title_middle_text;
    private ViewHolder viewHolder;
    public PersonalDataPresenter presenter;
    private PictureTailorHelper clippHelper;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, PersonalDataActivity.class);
        activity.startActivityForResult(intent, 300);
    }

    @Override
    public int setViewLayout() {
        return R.layout.personaldata_activity;
    }

    @Override
    public void initView() {

        clippHelper = PictureTailorHelper.getInstance();

        //头部控件初始化
        title_middle_text = (TextView) findViewById(R.id.title_middle_text);
        title_middle_text.setText("个人资料");
        initTextView();
    }

    private void initTextView() {
        viewHolder = new ViewHolder();
        viewHolder.userHead = (HiStudentHeadImageView) findViewById(R.id.persiondata_portrait);//头像
        viewHolder.persiondata_name = (TextView) findViewById(R.id.persiondata_name);//真实姓名
        viewHolder.persiondata_sex = (TextView) findViewById(R.id.persiondata_sex);//性别
        viewHolder.persiondata_birth = (TextView) findViewById(R.id.persiondata_birth);//生日
        viewHolder.persiondata_bloodtype = (TextView) findViewById(R.id.persiondata_bloodtype);//血型
        viewHolder.persiondata_constellation = (TextView) findViewById(R.id.persiondata_constellation);//星座
        viewHolder.persiondata_birthaddress = (TextView) findViewById(R.id.persiondata_birthaddress);//出生地
        viewHolder.persiondata_lifeddress = (TextView) findViewById(R.id.persiondata_lifeddress);//居住地
        viewHolder.persiondata_relationaddress = (TextView) findViewById(R.id.persiondata_relationaddress);//通讯地址
        viewHolder.introduce_myself_text = (TextView) findViewById(R.id.introduce_myself_text);//自我介绍
        viewHolder.persiondata_phonenum = (TextView) findViewById(R.id.persiondata_phonenum);//手机号
        viewHolder.persiondata_email = (TextView) findViewById(R.id.persiondata_email);//邮箱
        viewHolder.persiondata_music = (TextView) findViewById(R.id.persiondata_music);//音乐
        viewHolder.persiondata_book = (TextView) findViewById(R.id.persiondata_book);//书籍
        viewHolder.persiondata_movie = (TextView) findViewById(R.id.persiondata_movie);//电影
        viewHolder.persiondata_exercise = (TextView) findViewById(R.id.persiondata_exercise);//运动
        viewHolder.persiondata_game = (TextView) findViewById(R.id.persiondata_game);//游戏
        viewHolder.persiondata_cartoon = (TextView) findViewById(R.id.persiondata_cartoon);//动漫
        viewHolder.persiondata_cate = (TextView) findViewById(R.id.persiondata_cate);//美食
        viewHolder.persiondata_celebrity = (TextView) findViewById(R.id.persiondata_celebrity);//名人
        viewHolder.persiondata_list = (ListView) findViewById(R.id.persiondata_list);

        presenter = new PersonalDataPresenter(this, viewHolder);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            case R.id.title_left://返回

                presenter.doGoBack();

                break;
            case R.id.headImage://头像

                presenter.setHeadImage(this);

                break;
            case R.id.realName://真实姓名
                showHintDialog();

                break;
            case R.id.sex://性别

                presenter.setSex();

                break;
            case R.id.age://年龄

                break;
            case R.id.birthDay://生日

                presenter.setBirthDay();

                break;
            case R.id.blood://血型

                presenter.setBlood();

                break;
            case R.id.constellation://星座

                presenter.setConstellation();

                break;
            case R.id.birthAddress://出生地

                presenter.setBirthPlace();

                break;
            case R.id.lifeAddress://居住地

                presenter.setLivePlace();

                break;
            case R.id.contractAddress://通讯地址

                presenter.setContactPlace();

                break;
            case R.id.introduce_myself://自我介绍

                presenter.setIntroduceMyself();

                break;
            case R.id.persiondata_getpalm_goright://添加奖项
                presenter.addAwards();

                break;
            case R.id.music://音乐

                presenter.setMusic();

                break;
            case R.id.book://书籍

                presenter.setBooks();

                break;
            case R.id.movie://电影

                presenter.setMovie();

                break;
            case R.id.sport://运动

                presenter.setSports();

                break;
            case R.id.game://游戏

                presenter.setGames();

                break;
            case R.id.cartoon://动漫

                presenter.setCartoons();

                break;
            case R.id.food://美食

                presenter.setFood();

                break;
            case R.id.appreciateMen://名人

                presenter.setReciateMen();

                break;
        }

    }

    private void showHintDialog() {
        ReminderHelper.getIntentce().showDialog(this, "",getString(R.string.not_modify_name), getString(R.string.cancel), () -> {
        }, getString(R.string.confirm), () -> {
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10:
            case 20:
                if (resultCode == 20) {
                    presenter.UpDataUI();
                }
                break;

            case PictureTailorHelper.PHOTO_REQUEST_TAKEPHOTO:
                clippHelper.startPhotoZoom(this, Uri.fromFile(clippHelper.photo_path), 150);
                break;

            case PictureTailorHelper.PHOTO_REQUEST_GALLERY:
                if (data != null)
                    clippHelper.startPhotoZoom(this, data.getData(), 150);
                break;

            case PictureTailorHelper.PHOTO_REQUEST_CUT:
                if (data != null)
                    presenter.setPicToView(clippHelper.setPicToView(data));
                break;
        }

        if (requestCode == 100 && resultCode == 200) {
            presenter.UpDataUI();
        }

    }

    class ViewHolder {
        HiStudentHeadImageView userHead;
        TextView persiondata_name, persiondata_sex, persiondata_birth, persiondata_bloodtype, persiondata_constellation,
                persiondata_birthaddress, persiondata_lifeddress, introduce_myself_text, persiondata_relationaddress, persiondata_phonenum, persiondata_email, persiondata_music,
                persiondata_book, persiondata_movie, persiondata_exercise, persiondata_game, persiondata_cartoon, persiondata_cate, persiondata_celebrity;
        ListView persiondata_list;
    }
}
