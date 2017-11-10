package com.histudent.jwsoft.histudent.activity.clock;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.histudent.jwsoft.histudent.HTApplication;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.bean.UserReadBookInformation;
import com.histudent.jwsoft.histudent.body.hiworld.adapter.UploadImageRecyclerViewAdapter;
import com.histudent.jwsoft.histudent.body.hiworld.bean.RelationPersonModel;
import com.histudent.jwsoft.histudent.call.IPermissionCallBackListener;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.ImageBrowserActivity;
import com.histudent.jwsoft.histudent.commen.activity.MyWebActivity;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.enums.LoactionType;
import com.histudent.jwsoft.histudent.commen.enums.LoadingType;
import com.histudent.jwsoft.histudent.commen.enums.ShowImageType;
import com.histudent.jwsoft.histudent.commen.helper.PictureTailorHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.photo.PhotoHelper;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.RequestManager;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.activity.MapActivity;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.constant.ParamKeys;
import com.histudent.jwsoft.histudent.constant.TransferKeys;
import com.histudent.jwsoft.histudent.entity.ReadTaskEvent;
import com.histudent.jwsoft.histudent.entity.StoreAddPhotoVideoEntity;
import com.histudent.jwsoft.histudent.entity.StoreUploadVideoEntity;
import com.histudent.jwsoft.histudent.manage.ParamsManager;
import com.histudent.jwsoft.histudent.manage.PhotoManager;
import com.histudent.jwsoft.histudent.tool.CommonAdvanceUtils;
import com.histudent.jwsoft.histudent.tool.ToastTool;
import com.histudent.jwsoft.histudent.zxing.CaptureActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lichaojie on 2017/8/21.
 * desc:
 * 阅读打卡
 */

public class ReadClockInActivity extends BaseActivity {


    /**
     * 存入图片列表Url
     */
    private ArrayList<String> mPhotoListUrl = new ArrayList<>();
    private ArrayList<String> mPhotoCompressUrlTemp = new ArrayList<>();
    private ArrayList<ActionListBean.ImagesBean> mListImages = new ArrayList<>();
    private UploadImageRecyclerViewAdapter mUploadImageRecyclerViewAdapter = null;
    /**
     * 存入图片关联人
     */
    private ArrayList<RelationPersonModel> mRelationPersonModels = new ArrayList<>();
    private static final String ADD = "add";
    private String mFileMovieUrl = null;
    private int mFileMovieDuration = 0;
    private String mFileMovieTransfer = null;
    private boolean mIsVideoMovie = false;
    private static final int PHOTO_COMPRESS_WHAT = 1;
    /**
     * 位置信息
     */
    private AddressInforBean mAddressInformationBean = null;
    private boolean mIsAutoLocation = false;

    @BindView(R.id.title_left_text)
    TextView mTopLeftText;
    @BindView(R.id.title_left_image)
    IconView mTopLeftIcon;
    @BindView(R.id.title_middle_text)
    TextView mTopMiddleTitle;
    @BindView(R.id.title_left)
    LinearLayout mTopLeft;
    @BindView(R.id.title_right_text)
    TextView mTopRightText;
    @BindView(R.id.tv_read_book_name)
    AppCompatTextView mReadBookName;
    @BindView(R.id.et_read_start_page_index)
    AppCompatEditText mReadStartPageIndex;
    @BindView(R.id.et_read_end_page_index)
    AppCompatEditText mReadEndPageIndex;
    @BindView(R.id.et_read_book_total_time)
    AppCompatEditText mReadBookTotalTime;
    @BindView(R.id.et_record_read_book_feel)
    AppCompatEditText mRecordReadBookFeel;
    @BindView(R.id.tv_current_location)
    AppCompatTextView mCurrentLocation;
    @BindView(R.id.rcl_add_photo)
    RecyclerView mAddPhotoRecyclerView;
    @BindView(R.id.rl_publish_movie_layout)
    RelativeLayout mRLPublishMovieLayout;
    @BindView(R.id.iv_movie_video)
    AppCompatImageView mIvMovieVideo;
    @BindView(R.id.iv_publish_movie_play)
    IconView mIvPublishMoviePlay;
    @BindView(R.id.iv_location_icon)
    IconView mIvLocationIcon;
    @BindView(R.id.tv_movie_time)
    AppCompatTextView mTvMovieTime;
    @BindView(R.id.iv_icon_scan)
    IconView mIvIconScan;

    private UserReadBookInformation mUserBookInformation = null;
    private boolean mIsPerformScan = false;
    /**
     * 根据bookTaskId去获取书架上信息
     * 根据返回来的以下书架url 去判断 显示扫描状态还是去书架库去选书
     */
    private String mBookShelfUrl = null;

    /**
     * 当前是否处于扫描状态
     * a.true 显示扫描图标 点击跳转扫描书籍页面
     * b.false 显示向右箭头 点击跳转书籍详情页面
     */
    private boolean isInScanStatus = true;


    private UploadImageRecyclerViewAdapter.OnRecyclerViewOnClickListener mViewOnClickListener = (View v, int position) -> {
        if (position == mPhotoListUrl.size() - 1 && mPhotoListUrl.get(position).equals(ADD)) {
            checkTakePhotosPermission(() -> {
                final PictureTailorHelper pictureTailorHelper = PictureTailorHelper.getInstance();
                mPhotoListUrl.remove(mPhotoListUrl.size() - 1);
                pictureTailorHelper.selectPictures(ReadClockInActivity.this, mPhotoListUrl, 9, mRelationPersonModels);
            });
        } else {

            //跳转到图片查看器
            if (mPhotoListUrl != null && mPhotoListUrl.size() > 1) {
                ActionListBean.ImagesBean imagesBean;
                mListImages.clear();
                for (String picId : mPhotoListUrl) {
                    if (!ADD.equals(picId)) {
                        imagesBean = new ActionListBean.ImagesBean();
                        final File f = new File(picId);
                        imagesBean.setBigSizeUrl(picId);
                        imagesBean.setName(f.getName());
                        imagesBean.setThumbnailUrl(picId);
                        mListImages.add(imagesBean);
                    }
                }
                ImageBrowserActivity.start(ReadClockInActivity.this, position, 100, mListImages, ShowImageType.DISMISS_RELATION, mRelationPersonModels);
            }
        }
    };

    @OnClick(R.id.ll_scan_layout)
    void onScanClick() {
        if (isInScanStatus) {
            checkTakePhotosPermission(() -> {
                mIsPerformScan = true;
                final Intent intent = new Intent(ReadClockInActivity.this, CaptureActivity.class);
                intent.putExtra(TransferKeys.LOCAL_START_SCAN, 0);
                CommonAdvanceUtils.startActivityForResult(ReadClockInActivity.this, intent, TransferKeys.ConstantNum.NUM_2000);
            });
        } else {
            //跳转H5书架
            MyWebActivity.start(this, HistudentUrl.USER_SELECT_BOOK_INFORMATION);
        }
    }


    @Override
    public int setViewLayout() {
        return R.layout.activity_read_clock_in;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        mTopMiddleTitle.setText(getResources().getString(R.string.read_clock_in));
        mTopMiddleTitle.setTextSize(18);
        TextPaint paint = mTopMiddleTitle.getPaint();
        paint.setFakeBoldText(true);
        mTopRightText.setText(getResources().getString(R.string.read_clock_issue));
        mTopLeftText.setText(getResources().getString(R.string.cancel));
        mTopLeftIcon.setVisibility(View.GONE);
        mTopRightText.setEnabled(false);
    }

    @Override
    public void doAction() {
        super.doAction();
        final Intent intent = getIntent();
        final String taskBookId = intent.getStringExtra(TransferKeys.BOOK_TASK_ID);
        mUserBookInformation = UserReadBookInformation.create().setBookTaskId(taskBookId);
        requestReadBookInformation();
        mPhotoListUrl.add(ADD);
        mUploadImageRecyclerViewAdapter = new UploadImageRecyclerViewAdapter(mPhotoListUrl, mViewOnClickListener);
        mAddPhotoRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        mAddPhotoRecyclerView.setAdapter(mUploadImageRecyclerViewAdapter);
        mAddressInformationBean = HiWorldCache.getUserLocationInfor();
        final String city = mAddressInformationBean.getCity();
        final String name = mAddressInformationBean.getName();
        final String locationInformation = city + "·" + name;
        if (mAddressInformationBean != null) {
            if (TextUtils.isEmpty(city) && TextUtils.isEmpty(name)) {
                mCurrentLocation.setText("");
            } else {
                mCurrentLocation.setText(locationInformation);
            }
        }
        mUserBookInformation.setLongitude(mAddressInformationBean.getLatitude())
                .setLatitude(mAddressInformationBean.getLongitude())
                .setLocation(locationInformation);
        loadListener();
    }


    private void loadListener() {
//        mRecordReadBookFeel.addTextChangedListener(mEtTextWatcher);
        mReadStartPageIndex.addTextChangedListener(mEtTextWatcher);
        mReadEndPageIndex.addTextChangedListener(mEtTextWatcher);
        mReadBookTotalTime.addTextChangedListener(mEtTextWatcher);
        mTopLeft.setOnClickListener((View view) -> ReadClockInActivity.this.finish());
        //地理位置
        mCurrentLocation.setOnClickListener((View view) ->
                checkTakePhotosPermission(new IPermissionCallBackListener() {
                    @Override
                    public void doAction() {
                        MapActivity.startForResult(ReadClockInActivity.this, mAddressInformationBean, LoactionType.DYNAMIC, mIsAutoLocation, true);
                    }
                })
        );
        //发布
        mTopRightText.setOnClickListener((View view) -> {
            if (!checkPublishCondition())
                return;

            if (mPhotoListUrl.contains(ADD)) {
                mPhotoListUrl.remove(ADD);
            }
            //发布时  若有图片需要进行压缩处理
            if (mPhotoListUrl.size() > 0) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        PhotoHelper.saveImageInFile(ReadClockInActivity.this, getString(R.string.normal_text), mPhotoListUrl, mReadClockHandler);
                    }
                }.start();
            } else {
                //直接发布
                final StoreUploadVideoEntity uploadVideoEntity = new StoreUploadVideoEntity();
                checkReadClockFeel(uploadVideoEntity);
            }
        });
    }


    /**
     * 发布前检查是否符合条件
     *
     * @return true:满足  false:不满足
     */
    private boolean checkPublishCondition() {
        final String readBookName = mReadBookName.getText().toString().trim();
        if (TextUtils.isEmpty(readBookName)) {
            if (!mIsPerformScan) {
                ToastTool.showCommonToast(HTApplication.getInstance(), getString(R.string.show_scan_book));
                return false;
            }
        }
        final String readStartPageIndex = mReadStartPageIndex.getText().toString().trim();
        final String readEndPageIndex = mReadEndPageIndex.getText().toString().trim();
        final String readBookTotalTime = mReadBookTotalTime.getText().toString().trim();
        final String recordReadBookFeel = mRecordReadBookFeel.getText().toString();
        if (TextUtils.isEmpty(readStartPageIndex)) {
            ToastTool.showCommonToast(HTApplication.getInstance(), getString(R.string.show_start_page_empty));
            return false;
        }
        if (TextUtils.isEmpty(readEndPageIndex)) {
            ToastTool.showCommonToast(HTApplication.getInstance(), getString(R.string.show_end_page_empty));
            return false;
        }
        if (TextUtils.isEmpty(readBookTotalTime)) {
            ToastTool.showCommonToast(HTApplication.getInstance(), getString(R.string.show_read_time_empty));
            return false;
        }
//        if (TextUtils.isEmpty(recordReadBookFeel)) {
//            ToastTool.showCommonToast(HTApplication.getInstance(), getString(R.string.show_read_feel_empty));
//            return false;
//        }

        if (Integer.valueOf(readEndPageIndex) < Integer.valueOf(readStartPageIndex)) {
            ToastTool.showCommonToast(HTApplication.getInstance(), getString(R.string.show_judge_page_index));
            return false;
        }

        mUserBookInformation.setStartPageIndex(Integer.valueOf(readStartPageIndex))
                .setEndPageIndex(Integer.valueOf(readEndPageIndex))
                .setReadBookTotalTime(Integer.valueOf(readBookTotalTime))
                .setUserReadFeel(recordReadBookFeel);
        return true;
    }

    private static final String TAG = "ReadClockInActivity";

    /**
     * 阅读打卡获取上一次阅读书籍
     */
    private void requestReadBookInformation() {
        final String bookTaskId = mUserBookInformation.getBookTaskId();
        final Map<String, Object> hashMap = ParamsManager.getInstance()
                .setParams(TransferKeys.BOOK_TASK_ID, bookTaskId)
                .getParamsMap();
        HiStudentHttpUtils.postDataByOKHttp(this, hashMap, HistudentUrl.GET_LAST_BOOK_INFORMATION, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                final JSONObject jsonObject = JSON.parseObject(result);
                final String bookSn = jsonObject.getString(ParamKeys.ISBN);
                final String bookName = jsonObject.getString(ParamKeys.NAME);
                final Integer startPage = jsonObject.getInteger(ParamKeys.START_PAGE);
                //获取书架上信息Url
                mBookShelfUrl = jsonObject.getString(ParamKeys.BOOK_SHELF_URL);
                mUserBookInformation.setBookBn(bookSn)
                        .setBookName(bookName)
                        .setStartPageIndex(startPage);
                refreshScanIconStatus(bookName);
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        final int result = intent.getIntExtra(TransferKeys.RESULT, -1);
        if (result == TransferKeys.ConstantNum.NUM_1000) {
            //说明是从手动输入书名页面而来
            final String bookName = intent.getStringExtra(TransferKeys.BOOK_NAME);
            final String scanBookISBN = intent.getStringExtra(TransferKeys.BOOK_SCAN_ISBN);
            mUserBookInformation
                    .setBookName(bookName)
                    .setBookBn(scanBookISBN);
            refreshScanIconStatus(bookName);
        } else if (result == TransferKeys.ConstantNum.NUM_1001) {
            //用户从H5选择书籍页面返回来
            final String bookName = intent.getStringExtra(TransferKeys.BOOK_NAME);
            final String isbn = intent.getStringExtra(TransferKeys.ISBN);
            mUserBookInformation
                    .setBookName(bookName)
                    .setBookBn(isbn);
            refreshScanIconStatus(bookName);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: requestCode:" + requestCode + "=====resultCode:" + resultCode);
        if (requestCode == 1001) {
            //从相册返回过来的数据
            mIsVideoMovie = false;
            PhotoManager.getInstance().solveRequestCode1001(requestCode, resultCode, data, mPhotoListUrl,
                    mRelationPersonModels, mUploadImageRecyclerViewAdapter);
        } else if (requestCode == 201) {
            //处理拍照或者摄像返回数据
            final StoreAddPhotoVideoEntity entity = PhotoManager.getInstance().solveRequestCode201(this, requestCode,
                    resultCode, data, mIsVideoMovie, mFileMovieUrl,
                    mFileMovieDuration, mPhotoListUrl, mFileMovieTransfer,
                    mIvMovieVideo, mTvMovieTime, mIvPublishMoviePlay);
            mIsVideoMovie = entity.isVideoMovie();
            mFileMovieTransfer = entity.getFileMovieTransfer();
            mFileMovieUrl = entity.getFileMovieUrl();
            mFileMovieDuration = entity.getFileMovieDuration();
        } else if (requestCode == 100) {
            //图片预览返回数据
            mIsVideoMovie = false;
            PhotoManager.getInstance().solveRequestCode100(requestCode, resultCode, data, mPhotoListUrl,
                    mRelationPersonModels, mUploadImageRecyclerViewAdapter);
        } else if (requestCode == 10 && resultCode == -10) {
            //视频预览返回 此时视频已删除
            mIsVideoMovie = false;
        } else if (requestCode == 200 && resultCode == 300) {
            //地理位置返回数据
            mIsVideoMovie = false;
            final StoreAddPhotoVideoEntity entity = PhotoManager.getInstance().solveRequestCode200_300(this, requestCode,
                    resultCode, data, mAddressInformationBean,
                    mCurrentLocation, mIvLocationIcon, mIsAutoLocation);
            mIsAutoLocation = entity.isAutoLocation();
            mAddressInformationBean = entity.getAddressInformationBean();

            //重新更新数据
            final String city = mAddressInformationBean.getCity();
            final String name = mAddressInformationBean.getName();
            final String locationInformation = city + "·" + name;
            mUserBookInformation.setLatitude(mAddressInformationBean.getLatitude())
                    .setLongitude(mAddressInformationBean.getLongitude())
                    .setLocation((TextUtils.isEmpty(city) && TextUtils.isEmpty(name)) ? "" : locationInformation);
        }
        refreshAdapter();
        refreshTitle();
    }


    @Subscribe
    public void onEvent(ReadTaskEvent readTaskEvent) {
        //二维码扫描结果
        if (readTaskEvent != null) {
            final String bookSn = readTaskEvent.getIsBn();
            Log.i(TAG, "onActivityResult: ---二维码result:-->" + bookSn);
            final String bookName = readTaskEvent.getBookName();
            mUserBookInformation.setBookBn(bookSn);
            mReadBookName.setText("《" + bookName + "》");
        }
    }


    private void refreshAdapter() {
        if (mPhotoListUrl != null && mPhotoListUrl.size() < 9) {
            if (!mPhotoListUrl.contains(ADD))
                mPhotoListUrl.add(ADD);
        }
        mUploadImageRecyclerViewAdapter.notifyDataSetChanged();
        if (mIsVideoMovie) {
            mAddPhotoRecyclerView.setVisibility(View.GONE);
            mRLPublishMovieLayout.setVisibility(View.VISIBLE);
        } else {
            mAddPhotoRecyclerView.setVisibility(View.VISIBLE);
            mRLPublishMovieLayout.setVisibility(View.GONE);
            mFileMovieUrl = null;
        }
    }

    private TextWatcher mEtTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            refreshTitle();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    private final void refreshTitle() {
        final String readStartPageIndex = mReadStartPageIndex.getText().toString().trim();
        final String readEndPageIndex = mReadEndPageIndex.getText().toString().trim();
        final String readBookTotalTime = mReadBookTotalTime.getText().toString().trim();
        if (mPhotoListUrl.size() > 0 && !TextUtils.isEmpty(readStartPageIndex)
                && !TextUtils.isEmpty(readEndPageIndex)
                && !TextUtils.isEmpty(readBookTotalTime)) {
            mTopRightText.setTextColor(ContextCompat.getColor(this, R.color.green_color));
            mTopRightText.setEnabled(true);
        } else {
            mTopRightText.setTextColor(ContextCompat.getColor(this, R.color.text_black_h1));
            mTopRightText.setEnabled(false);
        }
    }


    private final void checkReadClockFeel(StoreUploadVideoEntity video) {
        //如果存在视频   则先进行视频上传处理
        if (!TextUtils.isEmpty(mFileMovieUrl)) {
            PhotoManager.getInstance().uploadVideoFile(this, mFileMovieUrl, mFileMovieTransfer, mFileMovieDuration);
        } else {
            publishReadClockFeel(video);
        }
    }


    /**
     * 发布读书感受
     */
    public void publishReadClockFeel(StoreUploadVideoEntity video) {
        final Map<String, Object> map = ParamsManager.getInstance()
                .setParams(ParamKeys.BOOK_TASK_ID, mUserBookInformation.getBookTaskId())
                .setParams(ParamKeys.ISBN, mUserBookInformation.getBookBn())
                .setParams(ParamKeys.BOOK_NAME, mUserBookInformation.getBookName())
                .setParams(ParamKeys.START_PAGE, mUserBookInformation.getStartPageIndex())
                .setParams(ParamKeys.END_PAGE, mUserBookInformation.getEndPageIndex())
                .setParams(ParamKeys.READ_TIME, mUserBookInformation.getReadBookTotalTime())
                .setParams(ParamKeys.MICROBLOGBODY, mUserBookInformation.getUserReadFeel())
                .setParams(ParamKeys.LONGITUDE, mUserBookInformation.getLongitude())
                .setParams(ParamKeys.LATITUDE, mUserBookInformation.getLatitude())
                .getParamsMap();
        final String location = mUserBookInformation.getLocation();
        if (!TextUtils.isEmpty(location)) {
            map.put(ParamKeys.LOCATION, mUserBookInformation.getLocation());
        }
        //如果存在需要上传的照片 需要上传压缩后所获取的ImageUrl并设置type
        final int type = RequestManager.TYPE_POST_FORM_IMG;
        HiStudentHttpUtils.postDataByOKHttp(this, map, type, HistudentUrl.PUBLISH_BOOK_RECORD, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {
                final ReadTaskEvent readTaskEvent = new ReadTaskEvent();
                readTaskEvent.setLocalStartScan(0)
                        .setResult(String.valueOf(1))
                        .setPerformPublish(true)
                        .setIsBn(mUserBookInformation.getBookBn());
                EventBus.getDefault().post(readTaskEvent);
                ReadClockInActivity.this.finish();
            }

            @Override
            public void onFailure(String errorMsg) {
                final ReadTaskEvent readTaskEvent = new ReadTaskEvent();
                readTaskEvent.setLocalStartScan(0)
                        .setResult(String.valueOf(-1))
                        .setPerformPublish(true)
                        .setIsBn(mUserBookInformation.getBookBn());
                EventBus.getDefault().post(readTaskEvent);
                ReadClockInActivity.this.finish();
            }
        }, mPhotoListUrl, LoadingType.FLOWER);
    }


    private Handler mReadClockHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case PHOTO_COMPRESS_WHAT:
                    boolean isAlready = PhotoManager.getInstance().solvePhotoCompressCase(msg, mPhotoListUrl, mPhotoCompressUrlTemp);
                    if (isAlready)
                        publishReadClockFeel(new StoreUploadVideoEntity());
                    break;
                default:
                    break;
            }

        }
    };


    /**
     * 根据图库中是否存在书籍去控制图标显示状态(扫描、右侧箭头)
     *
     * @param bookName
     */
    private void refreshScanIconStatus(String bookName) {
        if (!TextUtils.isEmpty(bookName)) {
            mUserBookInformation.setBookName(bookName);
            mReadBookName.setText("《" + bookName + "》");
        }
        if (!TextUtils.isEmpty(mBookShelfUrl)) {
            mIvIconScan.setText(R.string.icon_arrowright);
            isInScanStatus = false;
        } else {
            mIvIconScan.setText(R.string.icon_scan);
            isInScanStatus = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mReadClockHandler.removeMessages(PHOTO_COMPRESS_WHAT);
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }
}
