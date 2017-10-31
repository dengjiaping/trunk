package com.histudent.jwsoft.histudent.body.find.adapter;

import android.app.Activity;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.GroupMembersBean;
import com.histudent.jwsoft.histudent.body.message.model.ClassTemberModel;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.comment2.utils.GroupOrClassActionUtils;
import com.histudent.jwsoft.histudent.info.InfoHelper;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhangYT on 2016/6/14.
 */
public class GroupDetailsMemberAdapter extends BaseAdapter {
    private List<Object> list;
    private Activity context;
    private Map<String, String> adminMap = new HashMap<>();
    private int masterCount;
    private getDeleteUserId getDeleteUserId;
    private boolean isMaster;

    public GroupDetailsMemberAdapter(List<Object> list, Activity context, getDeleteUserId getDeleteUserId) {
        this.list = list;
        this.context = context;
        this.getDeleteUserId = getDeleteUserId;
        getGroupAdminAndMaker();
    }

    private void getGroupAdminAndMaker() {

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof GroupMembersBean) {
                GroupMembersBean bean = ((GroupMembersBean) list.get(i));

                //获取管理员的ids ,第一个位置为群主的
                if (bean.isIsGroupMaster()) {
                    adminMap.put("Master", bean.getUserId());

                } else if (bean.isIsAdmin()) {
                    adminMap.put("Admin" + masterCount, bean.getUserId());
                    masterCount++;

                }
            } else if (list.get(i) instanceof ClassTemberModel) {
                ClassTemberModel bean = ((ClassTemberModel) list.get(i));
                if (SystemUtil.isOneselfIn(bean.getUserId())) {
                    isMaster = bean.isIsClassMaker();
                }
            }
        }

    }

    public void setIsMemberOfGroup(int tag) {

    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void notifyDataSetChanged() {
        adminMap.clear();
        getGroupAdminAndMaker();
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        Object o = list.get(position);
        if (o instanceof String) {
            convertView = View.inflate(context, R.layout.instr_item, null);
            String s = o.toString();
            TextView tv = ((TextView) convertView.findViewById(R.id.tv));

            if (!StringUtil.isEmpty(s)) {
                tv.setText(Html.fromHtml(s));
            }
        } else {
            convertView = View.inflate(context, R.layout.class_center_item_classmement_set, null);
            viewHolder = new ViewHolder(convertView);
        }


        if (o instanceof GroupMembersBean) {

            final GroupMembersBean bean = ((GroupMembersBean) o);

            viewHolder.class_set_item_name.setVisibility(View.VISIBLE);
            viewHolder.class_set_item_psw.setVisibility(View.GONE);
            viewHolder.class_set_item_image.loadBuddyAvatar(bean.getUserAvatar());
            viewHolder.class_set_item_userName.setText(bean.getUserRealName());
            viewHolder.class_set_item_name.setText(bean.getClassName());

            showImage(bean, viewHolder);

//            if ((HiCache.getIntence().getLoginUserInfo().getUserId().equals(adminMap.get("Master")) && !bean.isIsGroupMaster())
//                    || (!bean.isIsAdmin() && isAdmin(HiCache.getIntence().getLoginUserInfo().getUserId()))) {
            setButtonListener_group(bean, viewHolder);
//            }
            goToPersonPage(viewHolder.class_set_item_image, bean.getUserId());

        } else if (o instanceof ClassTemberModel) {

            final ClassTemberModel model = ((ClassTemberModel) o);

            viewHolder.class_set_item_name.setVisibility(View.GONE);

            if (isMaster && position != 0) {
                viewHolder.class_set_item_psw.setVisibility(View.VISIBLE);
            } else {
                viewHolder.class_set_item_psw.setVisibility(View.GONE);
            }

            viewHolder.class_set_item_image.loadBuddyAvatar(model.getUserAvatar());
            viewHolder.class_set_item_userName.setText(model.getUserRealName());

            showImage(model, viewHolder);
            setButtonListener_class(model, viewHolder, position);
            goToPersonPage(viewHolder.class_set_item_image, model.getUserId());

        }
        return convertView;
    }

    class ViewHolder {

        HiStudentHeadImageView class_set_item_image;
        TextView class_set_item_name, class_set_item_userName;
        ImageView class_set_item_admin, class_set_item_delet, class_set_item_psw;

        public ViewHolder(View convertView) {
            class_set_item_image = (HiStudentHeadImageView) convertView.findViewById(R.id.class_set_item_image);
            class_set_item_admin = (ImageView) convertView.findViewById(R.id.class_set_item_admin);
            class_set_item_delet = (ImageView) convertView.findViewById(R.id.class_set_item_delet);
            class_set_item_psw = (ImageView) convertView.findViewById(R.id.class_set_item_psw);
            class_set_item_name = (TextView) convertView.findViewById(R.id.class_set_item_name);
            class_set_item_userName = (TextView) convertView.findViewById(R.id.class_set_item_userName);
        }
    }

    /**
     * 显示不同图标
     *
     * @param holderMember
     */
    private void showImage(Object object, ViewHolder holderMember) {

        if (object instanceof GroupMembersBean) {
            GroupMembersBean teamMembe = (GroupMembersBean) object;
            if (teamMembe.isIsGroupMaster()) {
                holderMember.class_set_item_admin.setImageResource(R.mipmap.super_manager);
                holderMember.class_set_item_delet.setVisibility(View.GONE);
            } else {
                if (teamMembe.isIsAdmin()) {
                    if (!HiCache.getInstance().getLoginUserInfo().getUserId().equals(adminMap.get("Master")))
                        holderMember.class_set_item_delet.setVisibility(View.GONE);
                    holderMember.class_set_item_admin.setImageResource(R.mipmap.comon_manager);

                } else {
                    holderMember.class_set_item_admin.setImageResource(R.mipmap.member);
                }
            }
        } else {
            ClassTemberModel teamMembe = (ClassTemberModel) object;
            if (teamMembe.isIsClassMaker()) {
                holderMember.class_set_item_admin.setImageResource(R.mipmap.super_manager);
                holderMember.class_set_item_delet.setVisibility(View.GONE);
            } else {
                if (teamMembe.isIsAdmin()) {
                    holderMember.class_set_item_admin.setImageResource(R.mipmap.comon_manager);
                    if (!isMaster)
                        holderMember.class_set_item_delet.setVisibility(View.GONE);
                } else {
                    holderMember.class_set_item_admin.setImageResource(R.mipmap.member);
                }
            }
        }
    }

    /**
     * 跳转到个人主页
     *
     * @param view
     * @param userId
     */
    private void goToPersonPage(View view, final String userId) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoHelper.gotoPersonHome((BaseActivity) context, userId, false);
            }
        });
    }

    /**
     * 设置社群控件点击监听
     *
     * @param teamMembe
     * @param holderMember
     */
    private void setButtonListener_group(final GroupMembersBean teamMembe, final ViewHolder holderMember) {


//        if (isCanDelete(teamMembe.getUserId()))
            holderMember.class_set_item_delet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GroupOrClassActionUtils.deleteMember(context, teamMembe.getUserId(), teamMembe.getGroupId(), new HttpRequestCallBack() {
                        @Override
                        public void onSuccess(String result) {
                            list.remove(teamMembe);
                            if (getDeleteUserId != null) {
                                getDeleteUserId.getId(teamMembe.getUserId());
                            }
                            GroupDetailsMemberAdapter.this.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(String errorMsg) {

                        }
                    });
                }
            });


        holderMember.class_set_item_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isCanSetAdmin(teamMembe.getUserId())) {
                    GroupOrClassActionUtils.setAdmin(context, teamMembe.getUserId(), teamMembe.getGroupId(), !teamMembe.isIsAdmin(), new HttpRequestCallBack() {
                        @Override
                        public void onSuccess(String result) {
                            teamMembe.setIsAdmin(!teamMembe.isIsAdmin());
                            GroupDetailsMemberAdapter.this.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(String errorMsg) {

                        }
                    });
                } else {

                    Toast.makeText(context, "你不是群主，无权进行管理员操作！", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    /**
     * 设置班级控件点击监听
     *
     * @param teamMembe
     * @param holderMember
     */
    private void setButtonListener_class(final ClassTemberModel teamMembe, final ViewHolder holderMember, final int posion) {

        holderMember.class_set_item_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isMaster) {
                    Toast.makeText(context, "你不是该班级班主任，无权设置管理员。", Toast.LENGTH_SHORT).show();
                } else if (isMaster && posion == 0) {
                    Toast.makeText(context, "你已经是班主任，无需设置。", Toast.LENGTH_SHORT).show();
                } else {
                    ClassHelper.setClassAdmin((BaseActivity) context, teamMembe, new HttpRequestCallBack() {
                        @Override
                        public void onSuccess(String result) {

                            teamMembe.setIsAdmin(!teamMembe.isIsAdmin());
                            GroupDetailsMemberAdapter.this.notifyDataSetChanged();
                            Toast.makeText(context, teamMembe.isIsAdmin() ? "设置管理员成功！" : "取消管理员成功！", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(String msg) {

                        }
                    });
                }
            }
        });

        holderMember.class_set_item_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isMaster && teamMembe.isIsAdmin()) {
                    Toast.makeText(context, "该同学为管理员，你无权将他移出班级", Toast.LENGTH_SHORT).show();
                } else {

                    ClassHelper.removeClass((BaseActivity) context, teamMembe.getClassId(), teamMembe.getUserId(), new HttpRequestCallBack() {
                        @Override
                        public void onSuccess(String result) {
                            list.remove(teamMembe);
                            GroupDetailsMemberAdapter.this.notifyDataSetChanged();
                            Toast.makeText(context, "移出班级成功！", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(String errorMsg) {

                        }
                    });
                }
            }
        });

        holderMember.class_set_item_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassHelper.resetPsw((BaseActivity) context, teamMembe.getUserId(), new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(context, "重置密码成功！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String errorMsg) {

                    }
                });
            }
        });

    }

    private boolean isAdmin(String id) {

        for (int i = 1; i < adminMap.size(); i++) {
            if (id.equals("Admin" + i)) {
                return true;
            }
        }
        return false;
    }

    //是否能对该成员进行删除操作
    private boolean isCanDelete(String id) {

        if (HiCache.getInstance().getLoginUserInfo().getUserId().equals(adminMap.get("Master"))
                && !id.equals(adminMap.get("Master"))
                || (isAdmin(HiCache.getInstance().getLoginUserInfo().getUserId()) && !isAdmin(id)))
            return true;

        return false;
    }

    //是否能对改成员进行管理员操作
    private boolean isCanSetAdmin(String id) {
        if (HiCache.getInstance().getLoginUserInfo().getUserId().equals(adminMap.get("Master")) && !id.equals(adminMap.get("Master")))
            return true;
        return false;
    }

    public interface getDeleteUserId {

        void getId(String id);

    }

}
