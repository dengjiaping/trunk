package com.histudent.jwsoft.histudent.body.message.adapter;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.GroupMembersBean;
import com.histudent.jwsoft.histudent.body.message.model.ClassTemberModel;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.listener.ParserCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.comment2.utils.GroupOrClassActionUtils;
import com.histudent.jwsoft.histudent.info.InfoHelper;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liuguiyu-pc on 2016/6/28.
 */
public class ClassCenterAdapter extends BaseAdapter {
    private List<Object> objects;
    private Activity activity;
    private float x_down, x_up, y_down, y_up;
    private ParserCallBack callBack;
    private Handler handler;
    private int control_flag = 3;


    public ClassCenterAdapter(Activity activity, List<Object> objects, ParserCallBack callBack, Handler handler) {
        this.objects = objects;
        this.activity = activity;
        this.callBack = callBack;
        this.handler = handler;
    }
    public void setControl(int flag) {
        control_flag = flag;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //班级成员
        HolderMember holderMember;
        String UserAvatar = null,RealName=null,BlogNum=null,ImgCount=null,tell=null,userId=null;
        boolean isAdmin = false,isMarker=false;

        if (convertView != null && convertView.getTag() instanceof HolderMember) {

            holderMember = (HolderMember) convertView.getTag();

        } else {

            holderMember = new HolderMember();
            convertView = View.inflate(activity, R.layout.class_center_item_classmement, null);
            holderMember.class_center_item_image = (HiStudentHeadImageView) convertView.findViewById(R.id.class_center_item_image);
            holderMember.class_center_item_admin = (ImageView) convertView.findViewById(R.id.class_center_item_admin);
            holderMember.class_center_item_phone = (ImageView) convertView.findViewById(R.id.class_center_item_phone);
            holderMember.class_center_item_name = (TextView) convertView.findViewById(R.id.class_center_item_name);
            holderMember.class_center_item_logs = (TextView) convertView.findViewById(R.id.class_center_item_logs);
            holderMember.class_center_item_pictures = (TextView) convertView.findViewById(R.id.class_center_item_pictures);
            holderMember.layout_show = (RelativeLayout) convertView.findViewById(R.id.layout_show);
            holderMember.button_left = (Button) convertView.findViewById(R.id.button_left);
            holderMember.button_right = (Button) convertView.findViewById(R.id.button_right);
            holderMember.scrollView = (HorizontalScrollView) convertView.findViewById(R.id.scrollView);
            convertView.setTag(holderMember);
        }



        //权限控制
        switch (control_flag) {
            case 1://超级管理员

                break;
            case 2://管理员

                holderMember.button_left.setVisibility(View.GONE);

                break;
            case 3://学生

                holderMember.button_left.setVisibility(View.GONE);
                holderMember.button_right.setVisibility(View.GONE);

                break;
        }

        final Object obj = objects.get(position);


        if (obj instanceof ClassTemberModel){
            final ClassTemberModel teamMembe = ((ClassTemberModel) obj);
            if (teamMembe.isIsClassMaker()) {

                holderMember.button_left.setVisibility(View.GONE);
                holderMember.button_right.setVisibility(View.GONE);
            } else if (SystemUtil.isOneselfIn(teamMembe.getUserId()) && teamMembe.isIsAdmin()) {
                holderMember.button_right.setVisibility(View.GONE);
            }

            UserAvatar=teamMembe.getUserAvatar();
            RealName=teamMembe.getUserRealName();
            BlogNum=teamMembe.getBlogNum()+"";
            ImgCount=teamMembe.getImgCount()+"";
            isAdmin=teamMembe.isIsAdmin();
            isMarker=teamMembe.isIsClassMaker();
            tell=teamMembe.getUserMobile();
            userId=teamMembe.getUserId();
            setButtonListener(teamMembe, holderMember);
        }else if(obj instanceof GroupMembersBean){

            GroupMembersBean groupTeam= ((GroupMembersBean) obj);

            if (groupTeam.isIsGroupMaster()
                    ||control_flag==2 && groupTeam.isIsAdmin()) {

                Log.e("control_flag",control_flag+"");

                holderMember.button_left.setVisibility(View.GONE);
                holderMember.button_right.setVisibility(View.GONE);
            }


            UserAvatar=groupTeam.getUserAvatar();
            RealName=groupTeam.getUserRealName();
            BlogNum=groupTeam.getBlogNum();
            ImgCount=groupTeam.getImgCount();
            isAdmin=groupTeam.isIsAdmin();
            isMarker=groupTeam.isIsGroupMaster();
            tell=groupTeam.getUserMobile();
            userId=groupTeam.getUserId();
            setButtonListener1(groupTeam, holderMember);
        }

        holderMember.class_center_item_image.loadBuddyAvatar(UserAvatar);
        holderMember.class_center_item_name.setText(RealName);
        holderMember.class_center_item_logs.setText(BlogNum);
        holderMember.class_center_item_pictures.setText(ImgCount);

        holderMember.button_left.setText(isAdmin ? "取消管理员" : "设为管理员");
        holderMember.button_right.setText("删除");

        setWidth(holderMember);
        showImage(isMarker,isAdmin, holderMember);
        callPhone(tell, holderMember);
        setScrollListener(holderMember);

        final String finalUserId = userId;
        holderMember.class_center_item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InfoHelper.gotoPersonHome((BaseActivity) activity, finalUserId, false);
            }
        });

        return convertView;
    }

    /**
     * 显示不同图标

     * @param holderMember
     */
    private void showImage(boolean isMarker,boolean isAdmin, HolderMember holderMember) {

        if (isMarker) {
            holderMember.class_center_item_admin.setImageResource(R.mipmap.super_manager);
        } else {
            if (isAdmin) {
                holderMember.class_center_item_admin.setImageResource(R.mipmap.comon_manager);
            } else {
                holderMember.class_center_item_admin.setImageResource(R.mipmap.member);
            }
        }

    }

    /**
     * 打电话
     *
     * @param holderMember
     */
    private void callPhone(final String tell, HolderMember holderMember) {
        holderMember.class_center_item_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassHelper.callPhone((BaseActivity) activity, tell);
            }
        });
    }

    /**
     * 重新设置可见部分的宽度
     */
    private void setWidth(HolderMember holderMember) {

        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) holderMember.layout_show.getLayoutParams();
        linearParams.width = SystemUtil.getScreenWind();
        holderMember.layout_show.setLayoutParams(linearParams);

    }

    /**
     * 设置控件点击监听
     */
    private void setButtonListener(final ClassTemberModel teamMembe, final HolderMember holderMember) {

        holderMember.button_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> map = new TreeMap<>();
                map.put("addOrRemove", teamMembe.isIsAdmin() ? false + "" : true + "");
                map.put("classId", teamMembe.getClassId());
                map.put("adminUserId", teamMembe.getUserId());

                HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.setClassAdmin, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        scorllView(holderMember, true);
                        callBack.parser("");
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });

            }
        });

        holderMember.button_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassHelper.removeClass((BaseActivity) activity, teamMembe.getClassId(), teamMembe.getUserId(), new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        scorllView(holderMember, true);
                        callBack.parser("");
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
            }
        });

    }

    /**
     * 设置控件点击监听
     */
    private void setButtonListener1(final GroupMembersBean teamMembe, final HolderMember holderMember) {

        holderMember.button_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GroupOrClassActionUtils.setAdmin(activity, teamMembe.getUserId(), teamMembe.getGroupId(), !teamMembe.isIsAdmin(), new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        scorllView(holderMember, true);
                        callBack.parser("");
                    }

                    @Override
                    public void onFailure(String errorMsg) {

                    }
                });

            }
        });

        holderMember.button_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GroupOrClassActionUtils.deleteMember(activity, teamMembe.getUserId(), teamMembe.getGroupId(), new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        scorllView(holderMember, true);
                        callBack.parser("");
                    }

                    @Override
                    public void onFailure(String errorMsg) {

                    }
                });
            }
        });

    }


    boolean flag = false;

    /**
     * 设置控件滚动监听
     */
    private void setScrollListener(final HolderMember holderMember) {

        holderMember.scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                        x_down = motionEvent.getX();
                        y_down = motionEvent.getY();

                        break;
                    case MotionEvent.ACTION_UP:

                        x_up = motionEvent.getX();
                        y_up = motionEvent.getY();

                        float w = holderMember.button_left.getWidth() + holderMember.button_right.getWidth();

                        if (x_down > x_up) {//左移

                            if (x_down - x_up > w / 2 && Math.abs(y_down - y_up) < 70) {
                                scorllView(holderMember, false);
                            } else {
                                scorllView(holderMember, true);
                            }
                        } else if (x_down < x_up) {//右移
                            if (x_up - x_down > w / 2 && Math.abs(y_down - y_up) < 70) {
                                scorllView(holderMember, true);
                            } else {
                                scorllView(holderMember, false);
                            }
                        }

                        break;
                }
                return false;
            }
        });

    }

    private void scorllView(final HolderMember holderMember, final boolean isLeft) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                holderMember.scrollView.smoothScrollTo(isLeft ? 0 : SystemUtil.getScreenWind(), 0);
                if (holderMember.button_right.getVisibility() != View.GONE) {
                    holderMember.class_center_item_phone.setVisibility(isLeft ? View.VISIBLE : View.GONE);
                }
            }
        });

    }

    private class HolderMember {
        HiStudentHeadImageView class_center_item_image;
        TextView class_center_item_name, class_center_item_logs, class_center_item_pictures;
        ImageView class_center_item_admin, class_center_item_phone;
        RelativeLayout layout_show;
        Button button_left, button_right;
        HorizontalScrollView scrollView;
    }

}
