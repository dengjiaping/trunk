package com.histudent.jwsoft.histudent.zxing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.activity.clock.ReadClockInActivity;
import com.histudent.jwsoft.histudent.bean.ScanBookInformation;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.constant.ParamKeys;
import com.histudent.jwsoft.histudent.constant.TransferKeys;
import com.histudent.jwsoft.histudent.entity.ReadTaskEvent;
import com.histudent.jwsoft.histudent.tool.CommonAdvanceUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lichaojie on 2017/9/19.
 * desc:
 * 书籍扫描确认页面
 */

public class CaptureConfirmActivity extends BaseActivity {


    @BindView(R.id.iv_book_cover)
    AppCompatImageView mIvBookCover;
    @BindView(R.id.tv_book_name)
    AppCompatTextView mTvBookName;
    @BindView(R.id.tv_book_author)
    AppCompatTextView mTvAuthor;
    @BindView(R.id.tv_publish_house)
    AppCompatTextView mTvPublishHouse;
    @BindView(R.id.tv_publish_time)
    AppCompatTextView mTvPublishTime;
    @BindView(R.id.title_middle_text)
    TextView mTvMiddleText;


    private static final String TAG = "CaptureConfirmActivity";
    private int mLocalScan = -1;
    private String mIsBn;
    private String mBookName;

    @OnClick(R.id.title_left)
    void finishPage() {
        this.finish();
    }

    @OnClick(R.id.tv_confirm)
    void confirm() {
        final ReadTaskEvent readTaskEvent = new ReadTaskEvent();
        readTaskEvent.setLocalStartScan(mLocalScan)
                .setIsBn(mIsBn)
                .setResult(String.valueOf(1))
                .setBookName(mBookName);
        EventBus.getDefault().post(readTaskEvent);
        final Intent intent = new Intent(this, ReadClockInActivity.class);
        CommonAdvanceUtils.startActivity(this,intent);
        this.finish();
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_scan_confirm;
    }

    @Override
    public void initView() {
        mTvMiddleText.setText(R.string.scan_book_page_title);
        final ViewGroup.LayoutParams layoutParams = mIvBookCover.getLayoutParams();
        layoutParams.width = SystemUtil.getScreenWind() / 3;
        mIvBookCover.setLayoutParams(layoutParams);
    }

    @Override
    public void doAction() {
        super.doAction();
        final Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        mIsBn = bundle.getString(ParamKeys.ISBN);
        mLocalScan = bundle.getInt(TransferKeys.LOCAL_START_SCAN);
        final String resultBookInformation = bundle.getString(TransferKeys.RESULT);
        solveRequestResult(resultBookInformation);
    }

    private void solveRequestResult(String result) {
        final ScanBookInformation scanBookInformation = JSONObject.parseObject(result, ScanBookInformation.class);
        mBookName = scanBookInformation.getName();
        final String bookUrl = scanBookInformation.getPic();
        final String bookAuthor = scanBookInformation.getAuthor();
        final String bookPublishHouse = scanBookInformation.getPublisher();
        final String bookPublishTime = scanBookInformation.getPubDate();
        CommonGlideImageLoader.getInstance().displayNetImage(this, bookUrl, mIvBookCover);
        mTvBookName.setText(mBookName);
        mTvAuthor.setText(getString(R.string.scan_book_author) + bookAuthor);
        mTvPublishHouse.setText(getString(R.string.scan_book_publish_house) + " " + bookPublishHouse);
        mTvPublishTime.setText(getString(R.string.scan_book_publish_time) + " " + bookPublishTime);
    }
}
