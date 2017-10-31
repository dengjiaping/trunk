package com.histudent.jwsoft.histudent.commen.activity;

import android.app.Activity;
import android.text.TextUtils;

import com.gzsll.jsbridge.WVJBWebView;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.bean.MyShareBean;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/9/19.
 * 分享类
 */
public class ShareUtils {

    public static int WEIXIN = 0;
    public static int WEIXIN_CIRCLE = 1;
    public static int QZONE = 2;
    public static int QQ = 3;
    private static UMImage umImage;
    private static String url, content, title_share;
    static WVJBWebView.WVJBResponseCallback callback;
    static MyShareBean shareBean;
    private static Activity activity;

    /**
     * 友盟分享(链接)
     *
     * @param flag
     */
    public static void share(BaseActivity activity_, MyShareBean bean, int flag) {

        if (bean == null) return;
        shareBean = bean;
        ShareUtils.activity = activity_;
        url = TextUtils.isEmpty(shareBean.getShareUrl()) ? " " : shareBean.getShareUrl();
        content = TextUtils.isEmpty(shareBean.getShareText()) ? " " : shareBean.getShareText();
        title_share = TextUtils.isEmpty(shareBean.getShareTitle()) ? " " : shareBean.getShareTitle();
        umImage = new UMImage(activity, shareBean.getSharePic());

        switch (flag) {
            case 0:
                shareBean.setSocial(3);
                new ShareAction(activity).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener)
                        .withTitle(title_share)
                        .withText(content)
                        .withMedia(umImage)
                        .withTargetUrl(url)
                        .share();
                break;
            case 1:
                shareBean.setSocial(4);
                new ShareAction(activity).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(umShareListener)
                        .withTitle(TextUtils.isEmpty(title_share.trim()) ? content : title_share)
                        .withText(content)
                        .withMedia(umImage)
                        .withTargetUrl(url)
                        .share();
                break;
            case 2:
                shareBean.setSocial(2);
                new ShareAction(activity).setPlatform(SHARE_MEDIA.QZONE).setCallback(umShareListener)
                        .withTitle(title_share)
                        .withText(content)
                        .withMedia(umImage)
                        .withTargetUrl(url)
                        .share();
                break;
            case 3:
                shareBean.setSocial(1);
                new ShareAction(activity).setPlatform(SHARE_MEDIA.QQ).setCallback(umShareListener)
                        .withTitle(title_share)
                        .withText(content)
                        .withMedia(umImage)
                        .withTargetUrl(url)
                        .share();
                break;
        }
    }

    /**
     * h5分享
     *
     * @param activity
     * @param bean
     * @param shareType
     * @param callback
     */
    public static void getShareData_w(final BaseActivity activity, MyShareBean bean, final int shareType, WVJBWebView.WVJBResponseCallback callback) {

        ShareUtils.callback = callback;
        ShareUtils.shareBean = bean;
        share(activity, bean, shareType);

    }

    private static UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            if (callback != null && shareBean != null)
                callback.callback(shareBean.getShareObjectId());

            ReminderHelper.getIntentce().ToastShow_with_image(activity, "分享成功！", R.string.icon_check);

            Map<String, Object> map = new TreeMap<>();
            map.put("objectId", shareBean.getShareObjectId());
            map.put("shareObjectType", shareBean.getShareObjectType());
            map.put("social", shareBean.getSocial());

            HiStudentHttpUtils.postDataByOKHttp(null, map, HistudentUrl.shareCallBack, new HttpRequestCallBack() {
                @Override
                public void onSuccess(String result) {
                }

                @Override
                public void onFailure(String msg) {
                }
            });

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            //ReminderHelper.getIntentce().ToastShow_with_image(activity, "分享失败。", R.string.icon_tip);

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
        }

    };

}
