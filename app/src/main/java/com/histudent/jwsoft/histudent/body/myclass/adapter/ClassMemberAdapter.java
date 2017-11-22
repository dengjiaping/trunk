package com.histudent.jwsoft.histudent.body.myclass.adapter;

import android.app.Activity;
import android.content.Context;
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
import com.histudent.jwsoft.histudent.body.find.activity.GroupMemberActivity;
import com.histudent.jwsoft.histudent.body.find.bean.ClassMemberBean;
import com.histudent.jwsoft.histudent.body.find.bean.GroupMemberBean;
import com.histudent.jwsoft.histudent.body.find.helper.GroupHelper;
import com.histudent.jwsoft.histudent.body.myclass.activity.ClassMemberActivity;
import com.histudent.jwsoft.histudent.body.myclass.helper.ClassHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.MyImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.model.entity.SelectMemberEvent;
import com.histudent.jwsoft.histudent.info.persioninfo.activity.PersonCenterActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class ClassMemberAdapter extends BaseAdapter {

    private Context context;
    private List<Object> list;
    private int flag;//1：班主任/群主，2：管理员，3学生/成员
    private boolean isSeleted;

    public ClassMemberAdapter(Context context, List<Object> list, int flag) {
        this.context = context;
        this.list = list;
        this.isSeleted = false;
        this.flag = flag;
    }

    public ClassMemberAdapter(Activity context, List<Object> list, int flag, boolean isSeleted) {
        this.context = context;
        this.list = list;
        this.isSeleted = isSeleted;
        this.flag = flag;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_classmember_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = ((ViewHolder) convertView.getTag());
        }

        Object object = list.get(position);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewHolder.layout_recentcontact.getLayoutParams();
        params.width = SystemUtil.getScreenWind() - SystemUtil.dip2px(context, 20);
        viewHolder.layout_recentcontact.setLayoutParams(params);

        viewHolder.scrollView.smoothScrollTo(0, 0);

        if (object instanceof ClassMemberBean.TeaClassMembersBean) {//教师列表
            ClassMemberBean.TeaClassMembersBean teaClassMembersBean = (ClassMemberBean.TeaClassMembersBean) object;

            MyImageLoader.getIntent().displayNetImage(context, teaClassMembersBean.getUserAvatar(), viewHolder.headImageView);
            viewHolder.name.setTextColor(context.getResources().getColor(R.color.text_black_h3));
            viewHolder.name.setText(teaClassMembersBean.getUserRealName());
            viewHolder.mTeacherIndentity.setVisibility(View.VISIBLE);

            showTeaItem(viewHolder, teaClassMembersBean);
            setTeaListener(viewHolder, teaClassMembersBean);

        } else if (object instanceof ClassMemberBean.StuClassMembersBean) {//学生列表
            ClassMemberBean.StuClassMembersBean stuClassMembersBean = (ClassMemberBean.StuClassMembersBean) object;

            MyImageLoader.getIntent().displayNetImage(context, stuClassMembersBean.getUserAvatar(), viewHolder.headImageView);
            viewHolder.name.setTextColor(context.getResources().getColor(R.color.text_black_h3));
            viewHolder.name.setText(stuClassMembersBean.getUserRealName());

            showStuItem(viewHolder, stuClassMembersBean);

            setStuListener(viewHolder, stuClassMembersBean);
            viewHolder.mTeacherIndentity.setVisibility(View.INVISIBLE);

        } else if (object instanceof GroupMemberBean.AdminMembersListBean) {//社群管理员
            GroupMemberBean.AdminMembersListBean adminMembersListBean = (GroupMemberBean.AdminMembersListBean) object;

            MyImageLoader.getIntent().displayNetImage(context, adminMembersListBean.getUserAvatar(), viewHolder.headImageView);
            viewHolder.name.setTextColor(context.getResources().getColor(R.color.text_black_h3));
            viewHolder.name.setText(adminMembersListBean.getUserRealName());
            viewHolder.mTeacherIndentity.setVisibility(View.INVISIBLE);
            showGroupAdminItem(viewHolder, adminMembersListBean);
            setGroupAdminListener(viewHolder, adminMembersListBean);

        } else if (object instanceof GroupMemberBean.GeneralMembersListBean) {//社群普通成员
            GroupMemberBean.GeneralMembersListBean generalMembersListBean = (GroupMemberBean.GeneralMembersListBean) object;

            MyImageLoader.getIntent().displayNetImage(context, generalMembersListBean.getUserAvatar(), viewHolder.headImageView);
            viewHolder.name.setTextColor(context.getResources().getColor(R.color.text_black_h3));
            viewHolder.name.setText(generalMembersListBean.getUserRealName());
            showGroupMemberItem(viewHolder, generalMembersListBean);
            setGroupMemberListener(viewHolder, generalMembersListBean);
            viewHolder.mTeacherIndentity.setVisibility(View.INVISIBLE);
        }

        if (isSeleted) {
            viewHolder.reset_pwd.setVisibility(View.GONE);
            viewHolder.cancle_manager.setVisibility(View.GONE);
            viewHolder.button_delet.setVisibility(View.GONE);
        }


        return convertView;
    }

    class ViewHolder {
        private HiStudentHeadImageView headImageView;
        private HorizontalScrollView scrollView;
        private TextView name, position, reset_pwd, cancle_manager;
        private Button button_delet;
        private RelativeLayout layout_recentcontact;
        private ImageView mTeacherIndentity;

        public ViewHolder(View convertView) {
            headImageView = convertView.findViewById(R.id.head_image);
            name = convertView.findViewById(R.id.name);
            position = convertView.findViewById(R.id.position);
            reset_pwd = convertView.findViewById(R.id.reset_pwd);
            cancle_manager = convertView.findViewById(R.id.cancle_manager);
            button_delet = convertView.findViewById(R.id.button_delet);
            layout_recentcontact = convertView.findViewById(R.id.layout_recentcontact);
            scrollView = convertView.findViewById(R.id.scrollView);
            mTeacherIndentity = convertView.findViewById(R.id.iv_teacher_identity);
        }

    }

    private void setTeaListener(ViewHolder viewHolder, final ClassMemberBean.TeaClassMembersBean teamMembe) {

        viewHolder.headImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonCenterActivity.start(context, teamMembe.getUserId());
            }
        });

        viewHolder.reset_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassHelper.resetPsw((BaseActivity) context, teamMembe.getUserId(), new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        ReminderHelper.getIntentce().showDialog(context, teamMembe.getUserRealName() + "的密码已重置成功，重置后的密码将以手机短信的方式发送！", "确定", new DialogButtonListener() {
                            @Override
                            public void setOnDialogButtonListener() {

                            }
                        });
                    }

                    @Override
                    public void onFailure(String errorMsg) {

                    }
                });
            }
        });

        viewHolder.cancle_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClassHelper.setClassAdmin((BaseActivity) context, teamMembe, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        ReminderHelper.getIntentce().ToastShow(context, "设置成功！");
                        EventBus.getDefault().post(new ClassMemberActivity.GetMembers("", 1));
                    }

                    @Override
                    public void onFailure(String errorMsg) {

                    }
                });
            }
        });

        viewHolder.button_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReminderHelper.getIntentce().showDialog((BaseActivity) context, null, "确认要移除该班级成员吗？", "取消", new DialogButtonListener() {
                    @Override
                    public void setOnDialogButtonListener() {

                    }
                }, "确定", new DialogButtonListener() {
                    @Override
                    public void setOnDialogButtonListener() {
                        Map<String, Object> map = new TreeMap<>();
                        map.put("classId", teamMembe.getClassId());
                        map.put("userId", teamMembe.getUserId());
                        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) context, map, HistudentUrl.removeUser, new HttpRequestCallBack() {
                            @Override
                            public void onSuccess(String result) {
                                list.remove(teamMembe);
                                notifyDataSetChanged();
                                EventBus.getDefault().post(new ClassMemberActivity.GetMembers("", 2));
                            }

                            @Override
                            public void onFailure(String errorMsg) {

                            }
                        });
                    }
                });
            }
        });

    }

    private void setStuListener(ViewHolder viewHolder, final ClassMemberBean.StuClassMembersBean teamMembe) {

        viewHolder.headImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonCenterActivity.start(context, teamMembe.getUserId());
            }
        });

        viewHolder.reset_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassHelper.resetPsw((BaseActivity) context, teamMembe.getUserId(), new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        ReminderHelper.getIntentce().showDialog(context, teamMembe.getUserRealName() + "的密码已重置成功，重置后的密码将以手机短信的方式发送！", "确定", new DialogButtonListener() {
                            @Override
                            public void setOnDialogButtonListener() {

                            }
                        });
                    }

                    @Override
                    public void onFailure(String errorMsg) {

                    }
                });
            }
        });

        viewHolder.cancle_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassHelper.setClassAdmin((BaseActivity) context, teamMembe, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        ReminderHelper.getIntentce().ToastShow(context, "设置成功！");
                        EventBus.getDefault().post(new ClassMemberActivity.GetMembers("", 1));
                    }

                    @Override
                    public void onFailure(String errorMsg) {

                    }
                });
            }
        });

        viewHolder.button_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReminderHelper.getIntentce().showDialog((BaseActivity) context, null, "确认要移除该班级成员吗？", "取消", new DialogButtonListener() {
                    @Override
                    public void setOnDialogButtonListener() {

                    }
                }, "确定", new DialogButtonListener() {
                    @Override
                    public void setOnDialogButtonListener() {
                        Map<String, Object> map = new TreeMap<>();
                        map.put("classId", teamMembe.getClassId());
                        map.put("userId", teamMembe.getUserId());
                        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) context, map, HistudentUrl.removeUser, new HttpRequestCallBack() {
                            @Override
                            public void onSuccess(String result) {
                                list.remove(teamMembe);
                                notifyDataSetChanged();
                                EventBus.getDefault().post(new ClassMemberActivity.GetMembers("", 2));
                            }

                            @Override
                            public void onFailure(String errorMsg) {

                            }
                        });
                    }
                });
            }
        });

    }

    private void setGroupAdminListener(ViewHolder viewHolder, final GroupMemberBean.AdminMembersListBean teamMembe) {

        viewHolder.headImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonCenterActivity.start(context, teamMembe.getUserId());
            }
        });

        if (isSeleted) {
            viewHolder.layout_recentcontact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new SelectMemberEvent(teamMembe.getUserMobile(), teamMembe.getUserRealName()));
                }
            });
        } else {
            viewHolder.cancle_manager.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    GroupHelper.setGroupAdmin(((BaseActivity) context), teamMembe, new HttpRequestCallBack() {
                        @Override
                        public void onSuccess(String result) {
                            ReminderHelper.getIntentce().ToastShow(context, "取消群管成功！");
                            EventBus.getDefault().post(new GroupMemberActivity.GetMembers("", 1));
                        }

                        @Override
                        public void onFailure(String errorMsg) {

                        }
                    });
                }
            });

            viewHolder.button_delet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReminderHelper.getIntentce().showDialog(context, null, "确认要移除该社群成员吗？", "取消", new DialogButtonListener() {
                        @Override
                        public void setOnDialogButtonListener() {

                        }
                    }, "确定", new DialogButtonListener() {
                        @Override
                        public void setOnDialogButtonListener() {
                            Map<String, Object> map = new TreeMap<>();
                            map.put("addOrRemove", false);
                            map.put("groupId", teamMembe.getGroupId());
                            map.put("memberUserId", teamMembe.getUserId());
                            HiStudentHttpUtils.postDataByOKHttp((BaseActivity) context, map, HistudentUrl.setMembers_group, new HttpRequestCallBack() {
                                @Override
                                public void onSuccess(String result) {
                                    list.remove(teamMembe);
                                    notifyDataSetChanged();
                                    EventBus.getDefault().post(new GroupMemberActivity.GetMembers("", 2));
                                }

                                @Override
                                public void onFailure(String errorMsg) {

                                }
                            });
                        }
                    });
                }
            });
        }
    }

    private void setGroupMemberListener(ViewHolder viewHolder, final GroupMemberBean.GeneralMembersListBean teamMembe) {

        viewHolder.headImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonCenterActivity.start(context, teamMembe.getUserId());
            }
        });

        if (isSeleted) {
            viewHolder.layout_recentcontact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new SelectMemberEvent(teamMembe.getUserMobile(), teamMembe.getUserRealName()));
                }
            });
        } else {
            viewHolder.cancle_manager.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    GroupMemberBean.AdminMembersListBean teamMembe1 = new GroupMemberBean.AdminMembersListBean();
                    teamMembe1.setGroupId(teamMembe.getGroupId());
                    teamMembe1.setUserId(teamMembe.getUserId());
                    teamMembe1.setIsAdmin(teamMembe.isIsAdmin());
                    GroupHelper.setGroupAdmin(((BaseActivity) context), teamMembe1, new HttpRequestCallBack() {
                        @Override
                        public void onSuccess(String result) {
                            ReminderHelper.getIntentce().ToastShow(context, "设置群管成功！");
                            EventBus.getDefault().post(new GroupMemberActivity.GetMembers("", 1));
                        }

                        @Override
                        public void onFailure(String errorMsg) {

                        }
                    });
                }
            });

            viewHolder.button_delet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReminderHelper.getIntentce().showDialog((BaseActivity) context, null, "确认要移除该社群成员吗？", "取消", new DialogButtonListener() {
                        @Override
                        public void setOnDialogButtonListener() {

                        }
                    }, "确定", new DialogButtonListener() {
                        @Override
                        public void setOnDialogButtonListener() {
                            Map<String, Object> map = new TreeMap<>();
                            map.put("addOrRemove", false);
                            map.put("groupId", teamMembe.getGroupId());
                            map.put("memberUserId", teamMembe.getUserId());
                            HiStudentHttpUtils.postDataByOKHttp((BaseActivity) context, map, HistudentUrl.setMembers_group, new HttpRequestCallBack() {
                                @Override
                                public void onSuccess(String result) {
                                    list.remove(teamMembe);
                                    notifyDataSetChanged();
                                    EventBus.getDefault().post(new GroupMemberActivity.GetMembers("", 2));
                                }

                                @Override
                                public void onFailure(String errorMsg) {

                                }
                            });
                        }
                    });
                }
            });
        }
    }

    private void showTeaItem(ViewHolder viewHolder, ClassMemberBean.TeaClassMembersBean model) {

        viewHolder.reset_pwd.setVisibility(View.GONE);
        if (flag == 1) {//用户是班级班主任

            if (model.isIsClassMaker()) {
                viewHolder.position.setVisibility(View.VISIBLE);
                viewHolder.position.setBackgroundResource(R.drawable.green_button_bg_circle);
                viewHolder.position.setText("班主任");
                viewHolder.cancle_manager.setVisibility(View.GONE);
                viewHolder.reset_pwd.setVisibility(View.GONE);
                viewHolder.button_delet.setVisibility(View.GONE);
            } else if (model.isIsAdmin()) {
                viewHolder.position.setVisibility(View.VISIBLE);
                viewHolder.position.setBackgroundResource(R.drawable.yellow_button_bg_circle);
                viewHolder.position.setText("管理员");
                viewHolder.cancle_manager.setVisibility(View.VISIBLE);
                viewHolder.cancle_manager.setText("取消班管");
                viewHolder.cancle_manager.setBackgroundResource(R.drawable.red_button_bg_stroke);
                viewHolder.cancle_manager.setTextColor(context.getResources().getColor(R.color.red));
                viewHolder.button_delet.setVisibility(View.VISIBLE);
            } else {
                viewHolder.position.setVisibility(View.GONE);
                viewHolder.cancle_manager.setVisibility(View.VISIBLE);
                viewHolder.cancle_manager.setText("设为班管");
                viewHolder.cancle_manager.setBackgroundResource(R.drawable.yellow_button_bg_stroke);
                viewHolder.cancle_manager.setTextColor(context.getResources().getColor(R.color.yellow_color));
                viewHolder.button_delet.setVisibility(View.VISIBLE);
            }

        } else if (flag == 2) {//用户是班级管理员

            if (model.isIsClassMaker()) {
                viewHolder.position.setVisibility(View.VISIBLE);
                viewHolder.position.setBackgroundResource(R.drawable.green_button_bg_circle);
                viewHolder.position.setText("班主任");
                viewHolder.cancle_manager.setVisibility(View.GONE);
                viewHolder.button_delet.setVisibility(View.GONE);
            } else if (model.isIsAdmin()) {
                viewHolder.position.setVisibility(View.VISIBLE);
                viewHolder.position.setBackgroundResource(R.drawable.yellow_button_bg_circle);
                viewHolder.position.setText("管理员");
                viewHolder.cancle_manager.setVisibility(View.GONE);
                viewHolder.button_delet.setVisibility(View.GONE);
            } else {
                viewHolder.position.setVisibility(View.GONE);
                viewHolder.cancle_manager.setVisibility(View.GONE);
                viewHolder.button_delet.setVisibility(View.VISIBLE);
            }

        } else if (flag == 3) {//登录人是班级成员

            if (model.isIsClassMaker()) {
                viewHolder.position.setVisibility(View.VISIBLE);
                viewHolder.position.setBackgroundResource(R.drawable.green_button_bg_circle);
                viewHolder.position.setText("班主任");
                viewHolder.cancle_manager.setVisibility(View.GONE);
                viewHolder.button_delet.setVisibility(View.GONE);
            } else if (model.isIsAdmin()) {
                viewHolder.position.setVisibility(View.VISIBLE);
                viewHolder.position.setBackgroundResource(R.drawable.yellow_button_bg_circle);
                viewHolder.position.setText("管理员");
                viewHolder.cancle_manager.setVisibility(View.GONE);
                viewHolder.button_delet.setVisibility(View.GONE);
            } else {
                viewHolder.position.setVisibility(View.GONE);
                viewHolder.cancle_manager.setVisibility(View.GONE);
                viewHolder.button_delet.setVisibility(View.GONE);
            }

        }
    }

    private void showStuItem(ViewHolder viewHolder, ClassMemberBean.StuClassMembersBean model) {

        if (flag == 1) {//用户是班级班主任

            if (model.isIsAdmin()) {
                viewHolder.position.setVisibility(View.VISIBLE);
                viewHolder.position.setBackgroundResource(R.drawable.yellow_button_bg_circle);
                viewHolder.position.setText("管理员");
                viewHolder.cancle_manager.setVisibility(View.VISIBLE);
                viewHolder.cancle_manager.setText("取消班管");
                viewHolder.cancle_manager.setBackgroundResource(R.drawable.red_button_bg_stroke);
                viewHolder.cancle_manager.setTextColor(context.getResources().getColor(R.color.red));
                viewHolder.button_delet.setVisibility(View.VISIBLE);
                viewHolder.reset_pwd.setVisibility(View.VISIBLE);
            } else {
                viewHolder.position.setVisibility(View.GONE);
                viewHolder.cancle_manager.setVisibility(View.VISIBLE);
                viewHolder.cancle_manager.setText("设为班管");
                viewHolder.cancle_manager.setBackgroundResource(R.drawable.yellow_button_bg_stroke);
                viewHolder.cancle_manager.setTextColor(context.getResources().getColor(R.color.yellow_color));
                viewHolder.button_delet.setVisibility(View.VISIBLE);
                viewHolder.reset_pwd.setVisibility(View.VISIBLE);
            }

        } else if (flag == 2) {//用户是班级管理员

            if (model.isIsAdmin()) {
                viewHolder.position.setVisibility(View.VISIBLE);
                viewHolder.position.setBackgroundResource(R.drawable.yellow_button_bg_circle);
                viewHolder.position.setText("管理员");
                viewHolder.cancle_manager.setVisibility(View.GONE);
                viewHolder.reset_pwd.setVisibility(View.GONE);
                viewHolder.button_delet.setVisibility(View.GONE);
            } else {
                viewHolder.position.setVisibility(View.GONE);
                viewHolder.cancle_manager.setVisibility(View.GONE);
                viewHolder.reset_pwd.setVisibility(View.VISIBLE);
                viewHolder.button_delet.setVisibility(View.VISIBLE);
            }

        } else if (flag == 3) {//登录人是班级成员

            if (model.isIsAdmin()) {
                viewHolder.position.setVisibility(View.VISIBLE);
                viewHolder.position.setBackgroundResource(R.drawable.yellow_button_bg_circle);
                viewHolder.position.setText("管理员");
                viewHolder.cancle_manager.setVisibility(View.GONE);
                viewHolder.button_delet.setVisibility(View.GONE);
                viewHolder.reset_pwd.setVisibility(View.GONE);
            } else {
                viewHolder.position.setVisibility(View.GONE);
                viewHolder.cancle_manager.setVisibility(View.GONE);
                viewHolder.button_delet.setVisibility(View.GONE);
                viewHolder.reset_pwd.setVisibility(View.GONE);
            }

        }
    }

    private void showGroupAdminItem(ViewHolder viewHolder, GroupMemberBean.AdminMembersListBean model) {

        viewHolder.reset_pwd.setVisibility(View.GONE);
        if (flag == 1) {//用户是班级班主任

            if (model.isIsGroupMaster()) {
                viewHolder.position.setVisibility(View.VISIBLE);
                viewHolder.position.setBackgroundResource(R.drawable.green_button_bg_circle);
                viewHolder.position.setText("群主");
                viewHolder.cancle_manager.setVisibility(View.GONE);
                viewHolder.button_delet.setVisibility(View.GONE);
            } else if (model.isIsAdmin()) {
                viewHolder.position.setVisibility(View.VISIBLE);
                viewHolder.position.setBackgroundResource(R.drawable.yellow_button_bg_circle);
                viewHolder.position.setText("管理员");
                viewHolder.cancle_manager.setVisibility(View.VISIBLE);
                viewHolder.cancle_manager.setText("取消群管");
                viewHolder.cancle_manager.setBackgroundResource(R.drawable.red_button_bg_stroke);
                viewHolder.cancle_manager.setTextColor(context.getResources().getColor(R.color.red));
                viewHolder.button_delet.setVisibility(View.VISIBLE);
            }

        } else if (flag == 2) {//用户是班级管理员

            if (model.isIsGroupMaster()) {
                viewHolder.position.setVisibility(View.VISIBLE);
                viewHolder.position.setBackgroundResource(R.drawable.green_button_bg_circle);
                viewHolder.position.setText("群主");
                viewHolder.cancle_manager.setVisibility(View.GONE);
                viewHolder.button_delet.setVisibility(View.GONE);
            } else if (model.isIsAdmin()) {
                viewHolder.position.setVisibility(View.VISIBLE);
                viewHolder.position.setBackgroundResource(R.drawable.yellow_button_bg_circle);
                viewHolder.position.setText("管理员");
                viewHolder.cancle_manager.setVisibility(View.GONE);
                viewHolder.button_delet.setVisibility(View.GONE);
            }

        } else {
            if (model.isIsGroupMaster()) {
                viewHolder.position.setVisibility(View.VISIBLE);
                viewHolder.position.setBackgroundResource(R.drawable.green_button_bg_circle);
                viewHolder.position.setText("群主");
                viewHolder.cancle_manager.setVisibility(View.GONE);
                viewHolder.button_delet.setVisibility(View.GONE);
            } else if (model.isIsAdmin()) {
                viewHolder.position.setVisibility(View.VISIBLE);
                viewHolder.position.setBackgroundResource(R.drawable.yellow_button_bg_circle);
                viewHolder.position.setText("管理员");
                viewHolder.cancle_manager.setVisibility(View.GONE);
                viewHolder.button_delet.setVisibility(View.GONE);
            }
        }
    }

    private void showGroupMemberItem(ViewHolder viewHolder, GroupMemberBean.GeneralMembersListBean model) {

        viewHolder.reset_pwd.setVisibility(View.GONE);
        if (flag == 1) {
            viewHolder.position.setVisibility(View.GONE);
            viewHolder.cancle_manager.setVisibility(View.VISIBLE);
            viewHolder.cancle_manager.setText("设为群管");
            viewHolder.cancle_manager.setBackgroundResource(R.drawable.green_button_bg_3);
            viewHolder.cancle_manager.setTextColor(context.getResources().getColor(R.color.text_white));
            viewHolder.button_delet.setVisibility(View.VISIBLE);
        } else if (flag == 2) {
            viewHolder.position.setVisibility(View.GONE);
            viewHolder.cancle_manager.setVisibility(View.GONE);
            viewHolder.button_delet.setVisibility(View.VISIBLE);
        } else {
            viewHolder.position.setVisibility(View.GONE);
            viewHolder.cancle_manager.setVisibility(View.GONE);
            viewHolder.button_delet.setVisibility(View.GONE);
        }
    }

}
