package com.histudent.jwsoft.histudent.body.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.CreateHuoDongBean;
import com.histudent.jwsoft.histudent.body.find.bean.GroupHuoDongDetailModel;
import com.histudent.jwsoft.histudent.body.find.helper.GroupHelper;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.model.listener.IPermissionCallBackListener;
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
import com.histudent.jwsoft.histudent.comment2.utils.FileUtils;
import com.histudent.jwsoft.histudent.comment2.utils.TimeUtils;
import com.histudent.jwsoft.histudent.comment2.utils.ViewUtils;
import com.histudent.jwsoft.histudent.model.manage.PhotoManager;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 创建社群活动的第一步
 */

public class CreateGroupHuoDongFristStep extends BaseActivity implements View.OnClickListener {
    private TextView tv_next, tv_start_time, tv_end_time, tv_address;
    private Intent intent;
    private TimePickerView pvTime;
    private String startTime, endTime;
    private CreateHuoDongBean activityBean;
    private AddressInforBean addressInforBean;
    private static GroupHuoDongDetailModel ActivtyIfor;
    private List<TypeBean> list;
    private boolean isNeedMyLocation = false;
    private View.OnClickListener noticeTimeItems;
    private TopMenuPopupWindow menuWindow;
    private TextView tv_notice_time;
    private ImageView huoDong_log;
    private String filename;
    private EditText et_name;
    private String groupId;
    private List<NetImageModel> fileLIst;
    private List<String> instrImageList;
    private TextView tv_instr;
    private EditText et_limit_count;
    private EditText et_price;
    private PictureTailorHelper clippHelper;
    private ArrayList<String> url;


    @Override
    public int setViewLayout() {
        return R.layout.create_group;
    }

    /**
     * 创建社群活动第一步
     *
     * @param context
     * @param groupId     社群id
     * @param requestCode
     */
    public static void start(Activity context, String groupId, int requestCode) {

        Intent intent = new Intent(context, CreateGroupHuoDongFristStep.class);
        intent.putExtra("groupId", groupId);
        context.startActivityForResult(intent, requestCode);
    }


    /**
     * 编辑社群活动入口
     *
     * @param context
     * @param bean        社群活动实体类
     * @param requestCode
     */
    public static void startEditeActivity(Activity context, GroupHuoDongDetailModel bean, int requestCode) {

        ActivtyIfor = bean;
        Intent intent = new Intent(context, CreateGroupHuoDongFristStep.class);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    public void initView() {

//        setInput();

        //控件的初始化
        intent = getIntent();
        groupId = intent.getStringExtra("groupId");
        addressInforBean = HiWorldCache.getUserLocationInfor();
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
        et_limit_count = ((EditText) findViewById(R.id.limit_count));
        et_price = ((EditText) findViewById(R.id.cost));

        ListenerUtils.setTextChangeListener(et_name, this, "最多20个字！");
        ((TextView) findViewById(R.id.title_middle_text)).setText("发起活动");
        huoDong_log = ((ImageView) findViewById(R.id.huodong_log));
        tv_next.setText("发起");
        ViewUtils.changeTitleRightClickable(this, true);
        findViewById(R.id.title_left_image).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.title_left_text)).setText("取消");

        activityBean = new CreateHuoDongBean();
        if (ActivtyIfor != null) {
            updateUI();
        }


        noticeTimeItems = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
            }
        };
        initTimePicker();
    }

    private void updateUI() {

        fileLIst = new ArrayList<>();


        if (ActivtyIfor.getIntroImgList() != null && ActivtyIfor.getIntroImgList().size() > 0) {

            for (GroupHuoDongDetailModel.IntroImgListBean be : ActivtyIfor.getIntroImgList()) {
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
        groupId = ActivtyIfor.getOwnerId();
        ((TextView) findViewById(R.id.title_middle_text)).setText("编辑活动");
        tv_notice_time.setText(ClassHelper.getNoticeText(ActivtyIfor.getAlarmType()));
//        MyImageLoader.getIntent().displayNetImageWithAnimation(this, ActivtyIfor.getCoverUrl(), huoDong_log, R.mipmap.default_image);

        CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(this, ActivtyIfor.getCoverUrl(),
                huoDong_log, PhotoManager.getInstance().getDefaultPlaceholderResource());

        et_limit_count.setText(ActivtyIfor.getMaxUserNum() + "");
        et_price.setText(ActivtyIfor.getUserCost() + "");
        tv_address.setText(ActivtyIfor.getPlace());

        activityBean.setHuoDongId(ActivtyIfor.getId());
        activityBean.setAlarmType(ActivtyIfor.getAlarmType());
        activityBean.setInstruction(ActivtyIfor.getIntroduction());
        activityBean.setLat(ActivtyIfor.getLatitude() * 1.0);
        activityBean.setLon(ActivtyIfor.getLongitude() * 1.0);
        activityBean.setPalce(ActivtyIfor.getPlace());
        activityBean.setLimitCount(ActivtyIfor.getMaxUserNum());
        activityBean.setPrice(ActivtyIfor.getUserCost());

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
                CreateHuoDongSeond.start(this, activityBean, 2, 666);
                break;

            //选择地址
            case R.id.relative_address:

                MapActivity.startForResult(this, addressInforBean, LoactionType.HUODONG, ActivtyIfor == null ? isNeedMyLocation : false, false);

                break;
            //选择地址


            case R.id.title_right:

                if (isEmptyContent()) {
                    if (!TimeUtils.ActivityIsValid(endTime)) {
                        Toast.makeText(CreateGroupHuoDongFristStep.this, R.string.HuodongErrorEndTimeInvalid, Toast.LENGTH_LONG).show();
                    } else {
                        int time = TimeUtils.getTimeDifferent2(startTime, endTime);

                        if (time > 0) {
                            if (activityBean.getImages() != null && activityBean.getImages().size() > 0) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        activityBean.setImages(ImageUtils.comPressBitmaps(CreateGroupHuoDongFristStep.this, activityBean.getImages(), 80));

                                    }
                                }).start();
                                postHuoDong();
                            } else {
                                postHuoDong();
                            }

                        } else {

                            Toast.makeText(this, R.string.HuodongErrorTimeEnd_TimeSmallThanStartTime, Toast.LENGTH_LONG).show();

                        }
                    }
                }

                break;

            //开始时间
            case R.id.relative_start_time:
                ViewUtils.hideSoftKeyBoard(this);
                pvTime.show(v);
                break;

            //结束时间
            case R.id.relative_end_time:
                et_name.clearFocus();
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
                menuWindow = new TopMenuPopupWindow(CreateGroupHuoDongFristStep.this, noticeTimeItems, list_name1, list_color1, false);
                menuWindow.showAtLocation(findViewById(R.id.title_middle), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

                break;


            //添加活动log
            case R.id.huodong_log:
                checkTakePhotoPermission(new IPermissionCallBackListener() {
                    @Override
                    public void doAction() {
                        clippHelper.selectPictures(CreateGroupHuoDongFristStep.this, new ArrayList<String>(), 1, null);
                    }
                });


                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            // 如果是直接从相册获取
            case PictureTailorHelper.PHOTO_REQUEST_GALLERYS:

                if (resultCode == 200) {
                    if (data != null) {
                        url.clear();
                        List<String> urls = data.getStringArrayListExtra("return");
                        url.addAll(urls);
//                        MyImageLoader.getIntent().displayLocalImage(activity,new File(url.get(0)), huoDong_log);
                        final File file = new File(url.get(0));
                        clippHelper.startPhotoZoom(this, Uri.fromFile(file),
                                SystemUtil.getScreenWind(), SystemUtil.dp2px(200),file);
                    }
                }

                break;
            // 如果是调用相机拍照时
            case PictureTailorHelper.PHOTO_REQUEST_CUT:

//                filename = clippHelper.setPicToView(huoDong_log, this);
                filename = clippHelper.setPicToView(huoDong_log, data);
                if (!StringUtil.isEmpty(filename)) ;
                activityBean.setImageUrl(filename);

                //地址返回
            case 200:
                if (resultCode == 300)
                    if (data != null && data.getSerializableExtra("address") != null) {

                        addressInforBean = (AddressInforBean) data.getSerializableExtra("address");
                        isNeedMyLocation = false;
                        activityBean.setLon(addressInforBean.getLongitude());
                        activityBean.setLat(addressInforBean.getLatitude());
                        setTextStyle(addressInforBean.getName(), tv_address);
                        activityBean.setPalce(addressInforBean.getName());
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



    private void initTimePicker() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        endDate.set(2099, 11, 28);
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
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

    //返回选择的时间，用于时间的回显
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    //判断是否有信息没有填写完整
    private boolean isEmptyContent() {

        if (StringUtil.isEmpty(activityBean.getImageUrl()) && ActivtyIfor == null) {
            Toast.makeText(CreateGroupHuoDongFristStep.this, "请选择图片", Toast.LENGTH_SHORT).show();
            return false;
        } else if (StringUtil.isEmpty(et_name.getText().toString())) {
            Toast.makeText(CreateGroupHuoDongFristStep.this, "请填写活动名称", Toast.LENGTH_SHORT).show();
            return false;
        } else if (StringUtil.isEmpty(tv_start_time.getText().toString())) {
            Toast.makeText(CreateGroupHuoDongFristStep.this, "请选择开始时间", Toast.LENGTH_SHORT).show();
            return false;
        } else if (StringUtil.isEmpty(tv_end_time.getText().toString())) {
            Toast.makeText(CreateGroupHuoDongFristStep.this, "请选择结束时间", Toast.LENGTH_SHORT).show();
            return false;
        } else if (StringUtil.isEmpty(tv_address.getText().toString())) {
            Toast.makeText(CreateGroupHuoDongFristStep.this, "请选择活动地点", Toast.LENGTH_SHORT).show();
            return false;
        } else if (activityBean.getAlarmType() == -1) {
            Toast.makeText(CreateGroupHuoDongFristStep.this, "请设置提醒时间", Toast.LENGTH_SHORT).show();
            return false;
        } else if (StringUtil.isEmpty(et_limit_count.getText().toString())) {
            Toast.makeText(CreateGroupHuoDongFristStep.this, "请填写人数限制", Toast.LENGTH_SHORT).show();
            return false;

        } else if (StringUtil.isEmpty(et_price.getText().toString())) {
            Toast.makeText(CreateGroupHuoDongFristStep.this, "请填写人均费用", Toast.LENGTH_SHORT).show();
            return false;
        } else if (StringUtil.isEmpty(activityBean.getInstruction())) {
            Toast.makeText(CreateGroupHuoDongFristStep.this, "请填写活动介绍", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            startTime = TimeUtils.getFormateData(tv_start_time.getText().toString());
            endTime = TimeUtils.getFormateData(tv_end_time.getText().toString());
            activityBean.setHuoDongName(et_name.getText().toString().trim());
            activityBean.setEndTime(endTime);
            activityBean.setStartTime(startTime);
            activityBean.setLimitCount(Integer.parseInt(et_limit_count.getText().toString().trim()));
            activityBean.setPrice(Integer.parseInt(et_price.getText().toString().trim()));
            return true;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivtyIfor = null;
        FileUtils.deleteFiles(instrImageList);
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

        GroupHelper.postGroupHuoDong(CreateGroupHuoDongFristStep.this, groupId, activityBean, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {


                ReminderHelper.getIntentce().ToastShow_with_image(CreateGroupHuoDongFristStep.this,
                        StringUtil.isEmpty(activityBean.getHuoDongId()) ? "创建活动成功！" : "修改活动成功！", R.string.icon_check);
                setResult(200);
                CreateGroupHuoDongFristStep.this.finish();
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
                !StringUtil.isEmpty(activityBean.getInstruction()) || activityBean.getLimitCount() >= 0 || activityBean.getPrice() >= 0) {


            ReminderHelper.getIntentce().showDialog(this, "提示", "发起活动未提交，是否取消？", "确定", new DialogButtonListener() {
                @Override
                public void setOnDialogButtonListener() {
                    setResult(-1);
                    CreateGroupHuoDongFristStep.this.finish();
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
