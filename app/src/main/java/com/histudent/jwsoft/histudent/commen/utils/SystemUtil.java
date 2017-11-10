package com.histudent.jwsoft.histudent.commen.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.histudent.jwsoft.histudent.HTApplication;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.lang.Thread.sleep;

/**
 * Created by liuguiyu-pc on 2016/5/30.
 */
public class SystemUtil {

    public static Map<String, Object> map_item = new TreeMap<>();

    public static List<Activity> activity = new ArrayList<>();

    public static List<Activity> getActivityInList() {
        return activity;
    }

    public static void addActivityToList(Activity activity) {
        SystemUtil.activity.add(activity);
    }

    public static void finishActivityByIndex(int... index) {

        for (int i = index.length - 1; i > 0; i--) {
            if (activity.get(i) != null) {
                activity.get(i).finish();
                activity.remove(i);
            }

        }

    }

    public static boolean inMainProcess(Context context) {
        String packageName = context.getPackageName();
        String processName = SystemUtil.getProcessName(context);
        return packageName.equals(processName);
    }

    public static String DEVICETYPE = "1";

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getScreenWind() {
        return HTApplication.getInstance().getResources().getDisplayMetrics().widthPixels;
    }


    public static String UserIdToIMAccont(String userId) {
        if (TextUtils.isEmpty(userId)) return null;
        return userId.replace("-", "");
    }

    public static String IMAccontToUserId(String iMAccount) {
        if (TextUtils.isEmpty(iMAccount)) return null;
        StringBuilder sb = new StringBuilder(iMAccount);
        sb.insert(20, "-");
        sb.insert(16, "-");
        sb.insert(12, "-");
        sb.insert(8, "-");
        return sb.toString();
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static String getScreenHeight() {
        return HTApplication.getInstance().getResources().getDisplayMetrics().heightPixels + "";
    }

    public static int getScreenHeight_() {
        return HTApplication.getInstance().getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion() {
        try {
            PackageManager manager = HTApplication.getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(HTApplication.getInstance().getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取设备唯一编号
     *
     * @return
     */
    public static String getUniquetag() {
        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 +
                Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 +
                Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 +
                Build.HOST.length() % 10 +
                Build.ID.length() % 10 +
                Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 +
                Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 +
                Build.TYPE.length() % 10 +
                Build.USER.length() % 10;

        return m_szDevIDShort;
    }

    /**
     * 将时间格式化
     */
    public static String formatTime(long time) {

        if (time < 0) {
            return null;
        } else {
            long currentTime = System.currentTimeMillis(); //获取开始时间
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date d1 = new Date(time);
            Date d2 = new Date(currentTime);
            String time_msg = format.format(d1);
            String time_current = format.format(d2);
            String[] time1 = time_msg.split(" ");
            String[] time2 = time_current.split(" ");

            if (time1[0].equals(time2[0])) {
                return time1[1];
            } else if ((currentTime - time) < 24 * 60 * 60 * 1000) {
                return "昨天";
            } else {
                return time1[0].split("-")[1] + "-" + time1[0].split("-")[2];
            }
        }
    }

    /**
     * 获取当前进程名
     *
     * @param context
     * @return 进程名
     */
    public static final String getProcessName(Context context) {
        String processName = null;

        // ActivityManager
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));

        while (true) {
            for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName;
                    break;
                }
            }

            // go home
            if (!TextUtils.isEmpty(processName)) {
                return processName;
            }

            // take a rest and again
            try {
                sleep(100L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 重设listview的高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(Context context, View listView, int size, int height) {

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = size * dip2px(context, height);
        listView.setLayoutParams(params);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static List<String> doActionInData(List<String> list_image) {
        if (list_image.size() < 8) {
            return list_image;
        } else {
            return list_image.subList(0, 8);
        }
    }

    /**
     * 重新计算listView的高度
     */
    public static void setListViewHeightBasedOnChildren(PullToRefreshListView listView_) {
        ListView listView = listView_.getRefreshableView();
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            totalHeight += listItem.getHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    static Thread thread = null;
    static int i;

    /**
     * 关闭计时器
     */
    public static void closeDown() {
        i = 0;
    }

    /**
     * 验证码倒计时
     *
     * @param handler
     * @param time
     */
    public static void countDown(final Handler handler, final int time) {

        if (handler == null || time < 1)
            return;

        i = time;

        if (thread == null) {
            thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    for (; i > -1; i--) {

                        try {
                            Message message = handler.obtainMessage();
                            message.what = -1;
                            message.arg1 = i;
                            handler.sendMessage(message);
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    thread = null;
                }
            });
            thread.start();
        }
    }

    public static void countDown(final int time) {
        if (thread == null) {
            i = time;
            thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    for (; i > -1; i--) {
                        try {
                            EventBus.getDefault().post(i + "");
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    thread = null;
                }
            });
            thread.start();
        } else {
            i = time + 1;
        }

    }

    /**
     * 隐藏软键盘
     *
     * @param activity
     */
    public static void hideSoftKeyboard(Activity activity) {

        //隐藏软键盘
        View view_ = activity.getWindow().peekDecorView();
        if (view_ != null) {
            InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view_.getWindowToken(), 0);
        }
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurrentData() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);

        return str;
    }

    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                HTApplication.getInstance().getResources().getDisplayMetrics());
    }

    public static boolean isOneselfIn(String userId) {
        if (TextUtils.isEmpty(userId))
            return false;
        return userId.equals(HiCache.getInstance().getLoginUserInfo().getUserId());
    }

    /**
     * 给带head的list添加emptyImage
     *
     * @param context
     * @param headView
     * @param imageId
     * @return
     */
    public static View setEmptyView(Context context, View headView, int imageId, int tipInfo) {
        View empty_view = View.inflate(context, R.layout.list_empty_defbg, null);
        View layout = empty_view.findViewById(R.id.emptyBg_layout);
        ImageView image = (ImageView) empty_view.findViewById(R.id.emptyBg_image);
        TextView text = (TextView) empty_view.findViewById(R.id.emptyBg_text);
        text.setText(tipInfo);
        image.setImageResource(imageId);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) layout.getLayoutParams(); //取控件textView当前的布局参数
        linearParams.width = SystemUtil.getScreenWind();
        linearParams.height = SystemUtil.getScreenHeight_() - headView.getHeight();
        layout.setLayoutParams(linearParams);
        return empty_view;
    }

    /**
     * 文件转base64
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static String encodeBase64File(File file) throws Exception {
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }

    /**
     * textView加载Html
     *
     * @param textView
     * @param context
     * @param html
     */
    public static void showHtml(final TextView textView, final Context context, final String html) {

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

}
