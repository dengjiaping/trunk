package com.histudent.jwsoft.histudent.body.mine.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.model.InvitationResponseModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.HTWebActivity;
import com.histudent.jwsoft.histudent.commen.activity.ShareUtils;
import com.histudent.jwsoft.histudent.commen.bean.MyShareBean;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by liuguiyu-pc on 2016/12/8.
 * 邀请有礼
 */

public class InvitationActivity extends BaseActivity {

    private TextView title, text_, gz, yq;
    private ImageView top_image;
    private MyShareBean model;
    private InvitationResponseModel responseModel;
    private final int PICK_CONTACT = 0;
    private String phoneNumber;

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, InvitationActivity.class));
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_invitation;
    }

    @Override
    public void initView() {
        title = (TextView) findViewById(R.id.title_middle_text);
        text_ = (TextView) findViewById(R.id.text_);
        gz = (TextView) findViewById(R.id.gz);
        yq = (TextView) findViewById(R.id.yq);
        top_image = (ImageView) findViewById(R.id.imageView2);

        title.setText("邀请有礼");

        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) top_image.getLayoutParams();
        linearParams.height = SystemUtil.getScreenWind() * 393 / 540;
        top_image.setLayoutParams(linearParams);

        getShareInfo();
    }

    private void upDataUI() {
        showHtml(text_, this, responseModel.getInvitedTitle());
        showHtml(yq, this, responseModel.getInvitedContent() + ">>");

        gz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HTWebActivity.start(InvitationActivity.this, responseModel.getRuleUrl());
            }
        });

        yq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HTWebActivity.start(InvitationActivity.this, responseModel.getInvitedListUrl());
            }
        });

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.wx_i:
                if (model != null)
                    ShareUtils.share(InvitationActivity.this, model, ShareUtils.WEIXIN);
                break;
            case R.id.msg_i:

                sendSmsWithBody(responseModel.getSmsContent());

                break;
            case R.id.qq_i:
                if (model != null)
                    ShareUtils.share(InvitationActivity.this, model, ShareUtils.QQ);

                break;
        }
    }

    private void getShareInfo() {

        HiStudentHttpUtils.postDataByOKHttp(this, null, HistudentUrl.invitationShareUrl_url, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String result) {

                responseModel = JSON.parseObject(result, InvitationResponseModel.class);

                if (responseModel != null) {
                    model = new MyShareBean();
                    model.setShareTitle(responseModel.getShareTitle());
                    model.setShareText(responseModel.getShareText());
                    model.setSharePic(responseModel.getSharePic());
                    model.setShareUrl(responseModel.getShareUrl());

                    upDataUI();
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    /**
     * textView加载Html
     *
     * @param textView
     * @param context
     * @param html
     */
    private void showHtml(final TextView textView, final Context context, final String html) {

        // 因为从网上下载图片是耗时操作 所以要开启新线程
        Thread t = new Thread(new Runnable() {
            Message msg = Message.obtain();

            @Override
            public void run() {
                // TODO Auto-generated method stub
                // bar.setVisibility(View.VISIBLE);
                /**
                 * 要实现图片的显示需要使用Html.fromHtml的一个重构方法：public static Spanned
                 * fromHtml (String source, Html.ImageGetterimageGetter,
                 * Html.TagHandler
                 * tagHandler)其中Html.ImageGetter是一个接口，我们要实现此接口，在它的getDrawable
                 * (String source)方法中返回图片的Drawable对象才可以。
                 */
                Html.ImageGetter imageGetter = new Html.ImageGetter() {
                    @Override
                    public Drawable getDrawable(String source) {
                        // TODO Auto-generated method stub
                        URL url;
                        Drawable drawable = null;
                        try {
                            url = new URL(source);
                            drawable = Drawable.createFromStream(
                                    url.openStream(), null);
                            drawable.setBounds(0, 0,
                                    drawable.getIntrinsicWidth(),
                                    drawable.getIntrinsicHeight());
                        } catch (MalformedURLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        return drawable;
                    }
                };
                final CharSequence test = Html.fromHtml(html, imageGetter, null);

                ((Activity) context).runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // 在这里执行你要想的操作 比如直接在这里更新ui或者调用回调在 在回调中更新ui
                        textView.setText(test);
                    }
                });
            }
        });
        t.start();

    }

    /**
     * 调用系统界面，给指定的号码发送短信，并附带短信内容
     *
     * @param body
     */
    public void sendSmsWithBody(String body) {
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(Uri.parse("smsto:"));
        sendIntent.putExtra("sms_body", body);
        startActivity(sendIntent);
    }

}
