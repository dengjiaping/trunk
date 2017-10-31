package com.histudent.jwsoft.histudent.body.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.view.BasePickerView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.activity.CreateHuoDongSeond;
import com.histudent.jwsoft.histudent.body.find.bean.CreateHuoDongBean;
import com.histudent.jwsoft.histudent.body.find.bean.HuoDongDetailsModel;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.call.IPermissionCallBackListener;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.enums.LoactionType;
import com.histudent.jwsoft.histudent.commen.helper.PictureTailorHelper;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.photo.utils.ImageUtils;
import com.histudent.jwsoft.histudent.commen.utils.ListenerUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.popupwindow.TopMenuPopupWindow;
import com.histudent.jwsoft.histudent.comment2.activity.MapActivity;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.comment2.bean.NetImageModel;
import com.histudent.jwsoft.histudent.comment2.bean.TypeBean;
import com.histudent.jwsoft.histudent.comment2.utils.ActivityCollector;
import com.histudent.jwsoft.histudent.comment2.utils.FileUtils;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;
import com.histudent.jwsoft.histudent.comment2.utils.ViewUtils;
import com.histudent.jwsoft.histudent.manage.PhotoManager;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CreateHuoDongFirstStep extends BaseActivity implements View.OnClickListener {


    private TextView tv_next, tv_start_time, tv_end_time, tv_address;
    private Intent intent;
    private TimePickerView pvTime;
    private String startTime, endTime;
    private CreateHuoDongBean activityBean;
    private AddressInforBean addressInforBean;
    private static HuoDongDetailsModel ActivtyIfor;
    private List<TypeBean> list;
    private View.OnClickListener noticeTimeItems;
    private TopMenuPopupWindow menuWindow;
    private TextView tv_notice_time;
    private ImageView huoDong_log;
    private String filename;
    private EditText et_name;
    private String classId;
    private List<NetImageModel> fileLIst;
    private List<NetImageModel> instrImageList;
    private boolean isAutoLocation = false;
    private TextView tv_instr;
    private PictureTailorHelper clippHelper;
    private List<String> url;


    private static final String TAG = "CreateHuoDongFirstStep";


    @Override
    public int setViewLayout() {
        return R.layout.activity_create_activity_;
    }

    //创建活动
    public static void start(Activity context, String classId) {

        Intent intent = new Intent(context, CreateHuoDongFirstStep.class);
        intent.putExtra("classId", classId);
        context.startActivityForResult(intent, 200);
    }

    //创建活动
    public static void startNormal(Activity context, String classId) {

        Intent intent = new Intent(context, CreateHuoDongFirstStep.class);
        intent.putExtra("classId", classId);
        context.startActivity(intent);
    }

    //修改活动
    public static void startEditeActivity(Activity context, HuoDongDetailsModel bean) {

        ActivtyIfor = bean;
        Intent intent = new Intent(context, CreateHuoDongFirstStep.class);
        context.startActivityForResult(intent, 200);
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        setInput();

        //控件的初始化
        intent = getIntent();
        classId = intent.getStringExtra("classId");
        clippHelper = PictureTailorHelper.getInstance();

        list = new ArrayList<>();
        url = new ArrayList<>();

        tv_address = ((TextView) findViewById(R.id.create_activity_tv_address));
        tv_next = ((TextView) findViewById(R.id.title_right_text));
        tv_notice_time = ((TextView) findViewById(R.id.notice_time));
        tv_start_time = ((TextView) findViewById(R.id.create_activity_tv_start_time));
        tv_end_time = ((TextView) findViewById(R.id.create_activity_tv_end_time));
        tv_instr = ((TextView) findViewById(R.id.tv_instr));
        et_name = ((EditText) findViewById(R.id.ed_name));
        ((TextView) findViewById(R.id.title_left_text)).setText("取消");
        findViewById(R.id.title_left_text).setVisibility(View.VISIBLE);
        findViewById(R.id.title_left_image).setVisibility(View.GONE);


        ListenerUtils.setTextChangeListener(et_name, this, "最多20个字！");
        ((TextView) findViewById(R.id.title_middle_text)).setText("发起活动");
        huoDong_log = ((ImageView) findViewById(R.id.huodong_log));
        tv_next.setText("发起");
        ViewUtils.changeTitleRightClickable(this, true);
        addressInforBean = HiWorldCache.getUserLocationInfor();

        activityBean = new CreateHuoDongBean();
        if (ActivtyIfor != null) {
            updateUI();
        }

        noticeTimeItems = (View v) -> {
            menuWindow.dismiss();
            switch (v.getId()) {

                case R.id.btn_01:
                    activityBean.setAlarmType(0);
                    tv_notice_time.setText("不提醒");
                    break;
                case R.id.btn_02:
                    activityBean.setAlarmType(1);
                    tv_notice_time.setText("提前30分钟");
                    break;
                case R.id.btn_03:
                    activityBean.setAlarmType(2);
                    tv_notice_time.setText("提前12小时");
                    break;
                case R.id.btn_04:
                    activityBean.setAlarmType(3);
                    tv_notice_time.setText("提前24小时");
                    break;
            }
        };
    }

    private void updateUI() {

        fileLIst = new ArrayList<>();

        if (ActivtyIfor.getIntroImgList() != null && ActivtyIfor.getIntroImgList().size() > 0) {

            for (HuoDongDetailsModel.IntroImgListBean be : ActivtyIfor.getIntroImgList()) {
                NetImageModel imageModel = new NetImageModel();
                imageModel.setUrl(be.getSavePath());
                imageModel.setWidth(be.getWidth());
                imageModel.setHeight(be.getHeight());
                imageModel.setId(be.getImgId());
                fileLIst.add(imageModel);
            }
            activityBean.setInstrFileList(fileLIst);

        }

        instrImageList = new ArrayList<>();

        tv_end_time.setText(TimeUtils.getFormateTimeWithoutSecond(ActivtyIfor.getEndTime()));
        tv_start_time.setText(TimeUtils.getFormateTimeWithoutSecond(ActivtyIfor.getStartTime()));
        setTextStyle(ActivtyIfor.getPlace(), tv_address);
        et_name.setText(ActivtyIfor.getName());
        tv_next.setText("修改");
        classId = ActivtyIfor.getOwnerId();
        ((TextView) findViewById(R.id.title_middle_text)).setText("编辑活动");
        tv_notice_time.setText(ClassHelper.getNoticeText(ActivtyIfor.getAlarmType()));

        CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(this, ActivtyIfor.getCoverUrl(),
                huoDong_log, PhotoManager.getInstance().getDefaultPlaceholderResource());

        activityBean.setHuoDongId(ActivtyIfor.getId());
        activityBean.setAlarmType(ActivtyIfor.getAlarmType());
        activityBean.setInstruction(ActivtyIfor.getIntroduction());
        activityBean.setLat(ActivtyIfor.getLatitude());
        activityBean.setLon(ActivtyIfor.getLongitude());
        activityBean.setPalce(ActivtyIfor.getPlace());
        tv_address.setText(ActivtyIfor.getPlace());
        setInstr(ActivtyIfor.getIntroduction());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left:

                showBackNotice();

                break;
            //点击下一步跳转到上传活动照片和活动介绍页面
            case R.id.huodong_instr:

                CreateHuoDongSeond.start(this, activityBean, 1, 666);

                break;

            //选择地址
            case R.id.relative_address:

                MapActivity.startForResult(this, addressInforBean, LoactionType.HUODONG,
                        ActivtyIfor == null ? isAutoLocation : false, false);

                break;
            //选择地址
            case R.id.title_right:

                if (isEmptyContent()) {

                    if (!TimeUtils.ActivityIsValid(endTime)) {
                        Toast.makeText(CreateHuoDongFirstStep.this, R.string.HuodongErrorEndTimeInvalid, Toast.LENGTH_LONG).show();
                    } else {
                        int time = TimeUtils.getTimeDifferent2(startTime, endTime);
                        Log.e("time1", time + "");
                        if (time > 0) {
                            Log.e("time2", time + "");
                            if (activityBean.getImages() != null && activityBean.getImages().size() > 0) {
                                new Thread(() ->
                                        activityBean.setImages(ImageUtils.comPressBitmaps(CreateHuoDongFirstStep.this, activityBean.getImages(), 80))
                                ).start();
                            }
                            postHuoDong();
                        } else {
                            Toast.makeText(this, R.string.HuodongErrorTimeEnd_TimeSmallThanStartTime, Toast.LENGTH_LONG).show();
                        }
                    }
                }

                break;

            //开始时间
            case R.id.relative_start_time:
                ViewUtils.hideSoftKeyBoard(this);
                initTimePicker();
                pvTime.show(v);
                break;

            //结束时间
            case R.id.relative_end_time:
//                initTime(tv_end_time);
                initTimePicker();
                pvTime.show(v);
                break;

            //提醒时间
            case R.id.relative_notice_time:
                et_name.clearFocus();
                List<String> list_name1 = new ArrayList<>();
                list_name1.add("不提醒");
                list_name1.add("提前30分钟");
                list_name1.add("提前12小时");
                list_name1.add("提前24小时");

                List<Integer> list_color1 = new ArrayList<>();
                list_color1.add(Color.rgb(255, 0, 0));
                list_color1.add(Color.rgb(51, 51, 51));
                list_color1.add(Color.rgb(51, 51, 51));
                list_color1.add(Color.rgb(51, 51, 51));
                menuWindow = new TopMenuPopupWindow(CreateHuoDongFirstStep.this, noticeTimeItems, list_name1, list_color1, false);
                menuWindow.showAtLocation(findViewById(R.id.title_middle), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

                break;


            //添加活动log
            case R.id.huodong_log:
                checkTakePhotoPermission(new IPermissionCallBackListener() {
                    @Override
                    public void doAction() {
                        clippHelper.selectPictures(CreateHuoDongFirstStep.this, new ArrayList<String>(), 1, null);
                    }
                });
                break;
        }
    }


    private void initTimePicker() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        endDate.set(2099, 11, 28);
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                switch (v.getId()){
                    case R.id.relative_start_time:
                        tv_start_time.setText(getTime(date));
                        break;
                    case R.id.relative_end_time:
                        tv_end_time.setText(getTime(date));
                        break;
                }
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "requestCode:" + requestCode + "resultCode" + resultCode);
        switch (requestCode) {
            // 如果是直接从相册获取
            case PictureTailorHelper.PHOTO_REQUEST_GALLERYS:
                if (resultCode == 200) {
                    if (data != null) {

                        url.clear();
                        List<String> urls = data.getStringArrayListExtra("return");
                        url.addAll(urls);
                        Log.d(TAG, "requestCode:" + requestCode + data + url.get(0));
                        final File file = new File(url.get(0));
                        Uri uri = Uri.fromFile(file);
                        clippHelper.startPhotoZoom(CreateHuoDongFirstStep.this, uri,
                                SystemUtil.getScreenWind(), SystemUtil.dp2px(200),file);

                    }
                }

                break;
            // 如果是调用相机拍照时
            case PictureTailorHelper.PHOTO_REQUEST_CUT:
//                filename = clippHelper.setPicToView(huoDong_log, this);
                filename = clippHelper.setPicToView(huoDong_log, data);
                if (!StringUtil.isEmpty(filename)) {
                    activityBean.setImageUrl(filename);
                    url.clear();
                    url.add(filename);
                }


                //地址返回
            case 200:
                if (resultCode == 300)
                    if (data != null && data.getSerializableExtra("address") != null) {

                        addressInforBean = (AddressInforBean) data.getSerializableExtra("address");
                        activityBean.setLon(addressInforBean.getLongitude());
                        activityBean.setLat(addressInforBean.getLatitude());
                        setTextStyle(addressInforBean.getName(), tv_address);
                        activityBean.setPalce(addressInforBean.getName());
                        isAutoLocation = false;
                    }

                break;

            //返回设置的活动介绍的文本内容和图片内容

            case 666:
                if (resultCode == 200)
                    if (data != null) {
                        activityBean = ((CreateHuoDongBean) data.getSerializableExtra("infor"));
                        setInstr(activityBean.getInstruction());
                    }

                break;
        }

    }

    //返回选择的时间，用于时间的回显
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    //判断是否有信息没有填写完整
    private boolean isEmptyContent() {

        if (StringUtil.isEmpty(activityBean.getImageUrl()) && ActivtyIfor == null) {
            Toast.makeText(CreateHuoDongFirstStep.this, "请选择图片", Toast.LENGTH_SHORT).show();
            return false;

        } else if (StringUtil.isEmpty(et_name.getText().toString())) {
            Toast.makeText(CreateHuoDongFirstStep.this, "请填写活动名称", Toast.LENGTH_SHORT).show();
            return false;

        } else if ("选择开始时间".equals(tv_start_time.getText().toString())) {
            Toast.makeText(CreateHuoDongFirstStep.this, "请选择开始时间", Toast.LENGTH_SHORT).show();
            return false;

        } else if ("选择结束时间".equals(tv_end_time.getText().toString())) {
            Toast.makeText(CreateHuoDongFirstStep.this, "请选择结束时间", Toast.LENGTH_SHORT).show();
            return false;

        } else if (StringUtil.isEmpty(tv_address.getText().toString())) {
            Toast.makeText(CreateHuoDongFirstStep.this, "请选择活动地点", Toast.LENGTH_SHORT).show();

            return false;
        } else if (activityBean.getAlarmType() == -1) {
            Toast.makeText(CreateHuoDongFirstStep.this, "请设置提醒时间", Toast.LENGTH_SHORT).show();
            return false;
        } else if (StringUtil.isEmpty(activityBean.getInstruction())) {
            Toast.makeText(CreateHuoDongFirstStep.this, "请填写活动介绍", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            startTime = TimeUtils.getFormateData(tv_start_time.getText().toString());
            endTime = TimeUtils.getFormateData(tv_end_time.getText().toString());
            activityBean.setHuoDongName(et_name.getText().toString().trim());
            activityBean.setEndTime(endTime);
            activityBean.setStartTime(startTime);
            return true;
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivtyIfor = null;
        FileUtils.deleteFile(activityBean.getImageUrl());
        FileUtils.deleteFiles(activityBean.getImages());
        ImageUtils.deleteCompressFile();//删除压缩文件
        Log.e("onDestroy", "onDestroyCreata1");
    }

    //当活动地址长度太大的时候，将文字字体变小
    private void setTextStyle(String content, TextView tv) {

        if (!StringUtil.isEmpty(content)) {
            if (content.length() > 10) {
                tv.setTextSize(15);
                tv.setSingleLine(false);
                tv.setText(content);
            } else {
                tv.setText(content);
            }
        }
    }

    public void postHuoDong() {

        ClassHelper.postHuoDong(CreateHuoDongFirstStep.this, classId, activityBean, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                ReminderHelper.getIntentce().ToastShow_with_image(CreateHuoDongFirstStep.this,
                        StringUtil.isEmpty(activityBean.getHuoDongId()) ? "创建活动成功！" : "修改活动成功！", R.string.icon_check);

                setResult(200);
                CreateHuoDongFirstStep.this.finish();
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }


    private void setInstr(String content) {


        if (StringUtil.isEmpty(content)) {
            tv_instr.setText("填写介绍");
            tv_instr.setTextColor(getResources().getColor(R.color.text_gray_h1));
        } else {

            tv_instr.setText(content);
            tv_instr.setTextColor(getResources().getColor(R.color.text_black_h1));
        }
    }

    private void showBackNotice() {

        if (!StringUtil.isEmpty(activityBean.getImageUrl()) || !StringUtil.isEmpty(et_name.getText().toString()) ||
                !StringUtil.isEmpty(activityBean.getStartTime()) || !StringUtil.isEmpty(activityBean.getAreaStr())
                || !StringUtil.isEmpty(activityBean.getEndTime()) || activityBean.getAlarmType() >= 0 ||
                !StringUtil.isEmpty(activityBean.getInstruction())) {
            ReminderHelper.getIntentce().showDialog(this, "提示", "发起活动未提交，是否取消？", "确定", new DialogButtonListener() {
                @Override
                public void setOnDialogButtonListener() {
                    setResult(-1);
                    CreateHuoDongFirstStep.this.finish();
                }
            }, "取消", new DialogButtonListener() {
                @Override
                public void setOnDialogButtonListener() {

                }
            });
        } else {
            setResult(-1);
            this.finish();

        }

    }
}
