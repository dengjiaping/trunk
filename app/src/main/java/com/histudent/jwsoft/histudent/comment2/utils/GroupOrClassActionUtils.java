package com.histudent.jwsoft.histudent.comment2.utils;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.body.find.bean.GroupDetailsBean;
import com.histudent.jwsoft.histudent.body.find.bean.GroupMembersBean;
import com.histudent.jwsoft.histudent.body.find.bean.HuodongBean;
import com.histudent.jwsoft.histudent.body.message.model.ClassModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.comment2.bean.ClassSelectModel;
import com.histudent.jwsoft.histudent.comment2.bean.GradeBean;
import com.histudent.jwsoft.histudent.comment2.bean.SchoolBean;
import com.histudent.jwsoft.histudent.comment2.bean.SystemProid;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by ZhangYT on 2016/12/2.
 */

public class GroupOrClassActionUtils {

    private isSuccessCallBack isSuccessCallBack;
    private GroupOrClassActionUtils utils;
    private Activity activity;


    public GroupOrClassActionUtils(Activity activity, isSuccessCallBack isSuccessCallBack) {
        this.activity = activity;
        this.isSuccessCallBack = isSuccessCallBack;
    }


    public static void deleteMember(final Activity activity, final String useId, final String groupId, final HttpRequestCallBack callBack) {

        ReminderHelper.getIntentce().showDialog(activity, "提示", "是否确定移除社群？", "确定", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

                Map<String, Object> map = new HashMap<>();
                map.put("groupId", groupId);
                map.put("addOrRemove", false + "");
                final List<String> listIds = new ArrayList<>();
                listIds.add("\"" + useId + "\"");
                map.put("memberUserIds", listIds.toString());

                HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.group_remove_group_member_url, callBack);
            }
        }, "取消", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

            }
        });


    }

    public static void setAdmin(final Activity activity, final String useId, final String groupId, final boolean isSetAdmin, final HttpRequestCallBack callBack) {


        ReminderHelper.getIntentce().showDialog(activity, "提示", isSetAdmin == true ? "设置管理员？" : "取消管理员？", "确定", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("adminUserId", useId);
                map.put("groupId", groupId);
                map.put("addOrRemove", isSetAdmin + "");

                HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map, HistudentUrl.group_set_admin_of_remove_url, callBack);
            }
        }, "取消", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

            }
        });
    }

    public void transferGroupOrClass(final boolean isClass, final String classOrGroupId) {

        final Map<String, Object> map = new TreeMap<>();

        popWindowUtils.showTranferGroupDialog(activity, new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {
                if (popWindowUtils.isCanTranfer) {
                    map.put(isClass == true ? "classId" : "groupId", classOrGroupId);
                    map.put(isClass == true ? "acceptUserMobile" : "groupUserPhone", popWindowUtils.tel);
                    ReminderHelper.getIntentce().showDialog(activity, "提示", isClass == true ? "是否确定转让该班级？" : "是否确定转让该社群？",
                            "确定", new DialogButtonListener() {
                                @Override
                                public void setOnDialogButtonListener() {

                                    HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map,
                                            isClass == true ? HistudentUrl.exchangeClassOwner :
                                                    HistudentUrl.group_transfer_url,
                                            new HttpRequestCallBack() {
                                                @Override
                                                public void onSuccess(String result) {
                                                    popWindowUtils.isCanTranfer = false;
                                                    popWindowUtils.tel = "";

                                                    GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(1, true);
                                                }

                                                @Override
                                                public void onFailure(String msg) {
                                                    GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(1, false);
                                                    popWindowUtils.isCanTranfer = false;
                                                    popWindowUtils.tel = "";

                                                }
                                            });

                                }
                            }, "取消", new DialogButtonListener() {
                                @Override
                                public void setOnDialogButtonListener() {
                                    popWindowUtils.isCanTranfer = false;
                                    popWindowUtils.tel = "";

                                }
                            });

                    popWindowUtils.tel = null;
                    popWindowUtils.isCanTranfer = false;
                }
            }
        }, isClass);


    }

    public void quitGroupOrClass(final boolean isClass, String classOrGroupId) {
        final Map<String, Object> map = new TreeMap<>();
        map.put(isClass == true ? "classId" : "groupId", classOrGroupId);
        map.put("addOrRemove", false + "");
        List<String> list_tep = new ArrayList<>();
        list_tep.add("\"" + HiCache.getInstance().getLoginUserInfo().getUserId() + "\"");
        map.put("memberUserIds", list_tep.toString());

        ReminderHelper.getIntentce().showDialog(activity, "提示", isClass == true ? "是否确定退出班级？" :
                "是否确定退出社群？", "确定", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

                HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map,
                        isClass == true ? HistudentUrl.setMembers :
                                HistudentUrl.group_remove_group_member_url,
                        new HttpRequestCallBack() {
                            @Override
                            public void onSuccess(String result) {

                                Toast.makeText(activity, isClass == true ? "退出班级成功！" :
                                        "退出社群成功！", Toast.LENGTH_LONG).show();
                                GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(2, true);
                            }

                            @Override
                            public void onFailure(String msg) {
                                GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(2, false);
                            }
                        });

            }
        }, "取消", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

            }
        });

    }

    public void dismissGroupAction(final String id) {

        ReminderHelper.getIntentce().showDialog(activity, "提示", "是否确定解散社群？", "确定", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

                Map<String, Object> map_class = new TreeMap<>();
                map_class.put("gId", id);
                HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map_class, HistudentUrl.dimissGroup, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(3, true);
                    }

                    @Override
                    public void onFailure(String msg) {
                        GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(3, false);
                    }
                });

            }
        }, "取消", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

            }
        });

    }


    //编辑班级或者社群
    public void EditGroupOrClass(final boolean isClass, final Object o, final String filePath) {


        final Map<String, Object> map_edit = new HashMap<>();
        final List<String> listPath = new ArrayList<>();

        if (!StringUtil.isEmpty(filePath))
            listPath.add(filePath);

        if (isClass) {

            ClassModel classModel = ((ClassModel) o);

            if (StringUtil.isEmpty(classModel.getOrgId())) {

                Toast.makeText(activity, "请选择学校！", Toast.LENGTH_LONG).show();
                return;

            } else if (classModel.getEductionSystemId() == -1) {

                Toast.makeText(activity, "请选择学段！", Toast.LENGTH_LONG).show();
                return;
            } else if (StringUtil.isEmpty(classModel.getGradeName())) {

                Toast.makeText(activity, "请选择年级！", Toast.LENGTH_LONG).show();
                return;

            } else if (StringUtil.isEmpty(classModel.getCName())) {
                Toast.makeText(activity, "请选择班次！", Toast.LENGTH_LONG).show();
                return;
            }
            map_edit.put("cId", classModel.getClassId());
            map_edit.put("noteName", classModel.getAlias());
//            map_edit.put("classDescription", classModel.getClassDescribe());
            map_edit.put("orgId", classModel.getOrgId());
            map_edit.put("isLogo", StringUtil.isEmpty(filePath) == true ? false + "" : true + "");
            map_edit.put("gradeName", classModel.getGradeName());
            map_edit.put("className", classModel.getCName());
            map_edit.put("eduSystem", classModel.getEductionSystemId());
            map_edit.put("isAllowJoin", classModel.isAllowJoin() + "");
        } else {
            GroupDetailsBean groupModel = ((GroupDetailsBean) o);

            if (groupModel.getAearStr().contains("市")) {

                String str = groupModel.getAearStr();
                map_edit.put("areaName", str.substring(str.indexOf("市") + 1, str.length()));
            } else {
                map_edit.put("areaName", groupModel.getAearStr());
            }


            if (StringUtil.isEmpty(groupModel.getGroupName())) {

                Toast.makeText(activity, "社群名称不能为空！", Toast.LENGTH_LONG).show();
                return;
            }
            map_edit.put("gId", groupModel.getGroupId());
            map_edit.put("groupName", groupModel.getGroupName());
            map_edit.put("tagId", groupModel.getTagId());
            map_edit.put("groupInfo", groupModel.getGroupDescription());
            map_edit.put("isPublic", groupModel.isIsPublic() + "");
            map_edit.put("isLogo", StringUtil.isEmpty(filePath) == true ? false + "" : true + "");
        }

        ReminderHelper.getIntentce().showDialog(activity, "提示", isClass == true ? "是否确认修改班级信息？" : "是否确定修改社群信息？", "确定", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

                if (listPath.size() != 0) {

                    HiStudentHttpUtils.postImageByOKHttp((BaseActivity) activity, listPath, map_edit,
                            isClass == true ? HistudentUrl.class_update_class : HistudentUrl.group_update_group, new HttpRequestCallBack() {
                                @Override
                                public void onSuccess(String result) {

                                    Toast.makeText(activity, isClass == true ? "修改班级信息成功！" : "修改社群信息成功！", Toast.LENGTH_LONG).show();
                                    GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(4, true);
                                }

                                @Override
                                public void onFailure(String msg) {
                                    GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(4, false);
                                }
                            });

                } else {

                    HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity, map_edit,
                            isClass == true ? HistudentUrl.class_update_class : HistudentUrl.group_update_group, new HttpRequestCallBack() {
                                @Override
                                public void onSuccess(String result) {

                                    Toast.makeText(activity, isClass == true ? "修改班级信息成功！" : "修改社群信息成功！", Toast.LENGTH_LONG).show();
                                    GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(4, true);
                                }

                                @Override
                                public void onFailure(String msg) {
                                    GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(4, false);
                                }
                            });


                }


            }
        }, "取消", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

            }
        });


    }

    public void InvationFriendsToGroup(final String groupId, final List<String> userIds) {


        ReminderHelper.getIntentce().showDialog(activity, "提示", "是否确定邀请以下选择的好友？", "确定", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

                Map<String, Object> map_class = new TreeMap<>();
                map_class.put("groupId", groupId);
                map_class.put("beInvitationUserId", userIds.toString());

                HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity,
                        map_class, HistudentUrl.group_invitation_friends,
                        new HttpRequestCallBack() {
                            @Override
                            public void onSuccess(String result) {

                                Log.e("getSystenList", result);
                                Toast.makeText(activity, "邀请成功，等待对方审核！", Toast.LENGTH_LONG).show();

                                GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(10, true);
                            }

                            @Override
                            public void onFailure(String msg) {
                                GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(10, false);
                            }
                        });
            }
        }, "取消", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

            }
        });


    }

    public void quitGroup(String groupId) {
        final Map<String, Object> map_class = new TreeMap<>();
        map_class.put("groupId", groupId);
        map_class.put("addOrRemove", false + "");

        List<String> list = new ArrayList<>();
        list.add("\"" + HiCache.getInstance().getLoginUserInfo().getUserId() + "\"");
        map_class.put("memberUserIds", list.toString());

        ReminderHelper.getIntentce().showDialog(activity, "提示", "是否确认退出社群？", "确认", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {
                HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity,
                        map_class, HistudentUrl.group_remove_group_member_url,
                        new HttpRequestCallBack() {
                            @Override
                            public void onSuccess(String result) {

                                Toast.makeText(activity, "退出成功！", Toast.LENGTH_LONG).show();
                                GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(10, true);
                            }

                            @Override
                            public void onFailure(String msg) {
                                GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(10, false);
                            }
                        });
            }
        }, "取消", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

            }
        });

    }

    //获取年级下的班级
    public void getClasses(String gradeName, String classId, String schoolId, String eduSystemId, final List<Object> list) {

        Map<String, Object> map_class = new TreeMap<>();
        map_class.put("gradeName", gradeName);
        map_class.put("classId", classId);
        map_class.put("schoolId", schoolId);
        map_class.put("eduSystemId", eduSystemId);

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity,
                map_class, HistudentUrl.getClass,
                new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {

                        list.clear();
                        List<GradeBean> gradeBean = JSON.parseArray(result, GradeBean.class);
                        list.addAll(gradeBean);
                        Log.e("getClasses", result);
                        GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(6, true);
                    }

                    @Override
                    public void onFailure(String msg) {
                        GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(6, false);
                    }
                });


    }

    //获取年级下的班级
    public void getClasses_addClass(final List<Object> list, ClassSelectModel selectModel) {

        Map<String, Object> map_class = new TreeMap<>();
        map_class.put("gradeId", selectModel.getGradeId());
        map_class.put("schoolId", selectModel.getSchoolId());
        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity,
                map_class, HistudentUrl.classGetList,
                new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {

                        list.clear();
                        List<GradeBean> gradeBean = JSON.parseArray(result, GradeBean.class);
                        list.addAll(gradeBean);
                        Log.e("getClasses", result);
                        GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(6, true);
                    }

                    @Override
                    public void onFailure(String msg) {
                        GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(6, false);
                    }
                });

    }

    //获取年级
    public void getGrades(final List<Object> list) {

        Map<String, Object> map_class = new TreeMap<>();
        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity,
                map_class, HistudentUrl.gradeList_url,
                new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        list.clear();
                        List<GradeBean> gradeBean = JSON.parseArray(result, GradeBean.class);
                        Log.e("getGrades", result);

                        list.addAll(gradeBean);
                        GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(7, true);
                    }

                    @Override
                    public void onFailure(String msg) {
                        GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(7, false);
                    }
                });

    }

    //获取年级
    public void getGrades_addClass(final List<Object> list, ClassSelectModel selectModel) {
        Map<String, Object> map_class = new TreeMap<>();
        map_class.put("orgId", selectModel.getSchoolId());
        map_class.put("eduSystemId", selectModel.getEduSystemId());
        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity,
                map_class, HistudentUrl.gradeGetList,
                new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        list.clear();
                        List<GradeBean> gradeBean = JSON.parseArray(result, GradeBean.class);
                        Log.e("getGrades", result);

                        list.addAll(gradeBean);
                        GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(7, true);
                    }

                    @Override
                    public void onFailure(String msg) {
                        GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(7, false);
                    }
                });

    }

    //获取学段
    public void getSystenList(String orgId, final List<Object> list) {


        Map<String, Object> map_class = new TreeMap<>();
        map_class.put("orgId", orgId);

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity,
                map_class, HistudentUrl.getSchoolSystemList,
                new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        list.clear();
                        Log.e("getSystenList", result);
                        List<SystemProid> proid = JSON.parseArray(result, SystemProid.class);
                        Log.e("getSystenList", result + proid.toString());
                        list.addAll(proid);

                        GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(8, true);
                    }

                    @Override
                    public void onFailure(String msg) {
                        GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(8, false);
                    }
                });


    }

    //获取学校
    public void getSchool(final String keyName, final String areaName, String city, int pageIndex, final List<Object> list, final boolean isMore) {

        Map<String, Object> map_class = new TreeMap<>();
        map_class.put("keyName", keyName);
        map_class.put("quName", areaName);
        map_class.put("shiName", city);
        map_class.put("pageIndex", pageIndex + "");
        map_class.put("pageSize", 8 + "");


        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity,
                map_class, HistudentUrl.searchSchoolListByName_url,
                new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        if (!isMore)
                            list.clear();
                        list.addAll(JSON.parseObject(result, SchoolBean.class).getItems());
                        Log.e("getSchool", result);
                        GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(isMore == true ? 6 : -1, true);
                    }

                    @Override
                    public void onFailure(String msg) {
                        GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(6, false);
                    }
                });

    }


    public static int AdminControl(List<GroupMembersBean> teamMembers) {

        if (teamMembers == null) return 3;

        int num = 3;
        for (GroupMembersBean model : teamMembers) {
            if (model.getUserId().equals(HiCache.getInstance().getLoginUserInfo().getUserId())) {

                if (model.isIsAdmin()) {
                    num = 2;
                }
                if (model.isIsGroupMaster()) {
                    num = 1;
                }
            }
        }

        return num;
    }

    public static void rankData(List<GroupMembersBean> teamMmbers) {
        Collections.sort(teamMmbers, new Comparator<GroupMembersBean>() {
            @Override
            public int compare(GroupMembersBean t0, GroupMembersBean t1) {

                if (t0.isIsGroupMaster()) {
                    return -1;
                } else if (t1.isIsGroupMaster()) {
                    return 1;
                } else {
                    if (t0.isIsAdmin() && t1.isIsAdmin()) {
                        return 0;
                    } else {
                        if (t0.isIsAdmin()) {
                            return -1;
                        } else if (t1.isIsAdmin()) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                }
            }
        });
    }

    //获取社群成员
    public static void getMembers(Activity activity, String groupId, Handler handler, int what) {

        Map<String, Object> map = new HashMap<>();
        map.put("groupId", groupId);
        HiWorldCache.postHttpData(((BaseActivity) activity),
                handler, what, HistudentUrl.single_group_members_url, map, HiWorldCache.Quarry, false,true);
    }

    public void isGroupAdminOrMarkerOrMember(final String useId, String groupId, final List<Object> list) {


        final int[] type = {97};//100是群主99是管理员，98是成员，97，不是成员
        Map<String, Object> map_class = new TreeMap<>();
        map_class.put("groupId", groupId);

        HiStudentHttpUtils.postDataByOKHttp((BaseActivity) activity,
                map_class, HistudentUrl.single_group_members_url,
                new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {


                        Log.e("GroupMemberResult", result);
                        List<GroupMembersBean> bean = JSON.parseArray(result, GroupMembersBean.class);
                        if (list != null) {
                            list.clear();
                            list.addAll(bean);
                        }
                        if (bean != null && bean.size() > 0) {
                            for (GroupMembersBean bean1 : bean) {
                                if (bean1.getUserId().equals(useId)) {
                                    if (bean1.isIsAdmin()) {
                                        type[0] = 100;
                                    } else if (bean1.isIsGroupMaster()) {
                                        type[0] = 99;
                                    } else {
                                        type[0] = 98;
                                    }
                                    break;
                                }
                            }
                        }
                        GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(type[0], true);
                    }

                    @Override
                    public void onFailure(String msg) {
                        GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(type[0], false);
                    }
                });
    }

    public void changeGroupBackgroud(String groupId, String filePath) {


        Map<String, Object> map_class = new TreeMap<>();
        map_class.put("groupId", groupId);

        List<String> fileList = new ArrayList<>();
        fileList.add(filePath);

        HiStudentHttpUtils.postImageByOKHttp((BaseActivity) activity, fileList,
                map_class, HistudentUrl.group_edit_group_cover_image_url,
                new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(activity, "更换社群背景图片成功", Toast.LENGTH_SHORT).show();
                        Log.e("getSchool", result);
                        GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(9, true);
                    }

                    @Override
                    public void onFailure(String msg) {
                        GroupOrClassActionUtils.this.isSuccessCallBack.isSuccess(9, false);
                    }
                });

    }


    //删除添加的空白条目
    public static void removeEmptyItem(List<Object> list) {

        HuodongBean bean;
        List<Object> emptyList = new ArrayList<>();
        if (list != null && list.size() > 0)
            for (int i = 0; i < list.size(); i++) {
                bean = ((HuodongBean) list.get(i));
                if (bean.isEmpty()) {
                    //记录补充的view
                    emptyList.add(bean);
                }
            }

        list.removeAll(emptyList);

    }

    //增加添加更多应用的条目
    public static void addMoreItem(List<Object> list) {

        HuodongBean bean, moreBean = null;
        if (list != null && list.size() > 0)
            for (int i = 0; i < list.size(); i++) {
                bean = ((HuodongBean) list.get(i));
                if (!StringUtil.isEmpty(bean.getAppName()) && bean.getAppName().equals("更多应用")) {
//                    bean.setImageId(R.mipmap.more_app);
                    moreBean = bean;
                }
            }

        if (moreBean != null) {
            list.remove(moreBean);
            list.add(moreBean);
        } else {
            HuodongBean b = new HuodongBean();
            b.setAppName("更多应用");
//            b.setImageId(R.mipmap.more_app);
            list.add(b);
        }

    }


    //删除添加更多应用的条目
    public static void deleteMoreItem(List<Object> list) {

        HuodongBean bean;
        List<Object> emptyList = new ArrayList<>();
        if (list != null && list.size() > 0)
            for (int i = 0; i < list.size(); i++) {
                bean = ((HuodongBean) list.get(i));
                if (!StringUtil.isEmpty(bean.getAppName()) && bean.getAppName().equals("更多应用")) {
                    emptyList.add(bean);
                }
            }

        if (emptyList.size() != 0) {
            list.removeAll(emptyList);
        }

    }


    //当应用条目不足整行时添加空白条目
    public static void addEmptyItem(List<Object> list) {

        removeEmptyItem(list);
        int size = list.size() % 4;
        if (size != 0)
            for (int i = 0; i < 4 - size; i++) {
                HuodongBean bean = new HuodongBean();
                bean.setEmpty(true);//座标记，表示该条目为补充空白的
                list.add(bean);
            }
    }


    //判断该应用是否时重复添加的
    public static boolean isAlreadAdd(List<Object> list, Object item) {

        HuodongBean model = null;
        if (item != null) {
            if (item instanceof HuodongBean) {
                model = ((HuodongBean) item);
                if (list != null && list.size() > 0 && model != null) {
                    for (int i = 0; i < list.size(); i++) {

                        if (list.get(i) instanceof HuodongBean) {
                            HuodongBean bean = ((HuodongBean) list.get(i));

                            //appkey相同时表示为同一个应用
                            if (!StringUtil.isEmpty(bean.getAppKey())
                                    && bean.getAppKey().equals(model.getAppKey())) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }


    //判断班级是否存在
    public static void ClassIsExsit(Activity activity, String orgId, String eduId, String gradeName,
                                    String className, final Handler handler) {
        final Map<String, Object> map = new HashMap<>();
        map.put("orgId", orgId);
        map.put("eduId", eduId);
        map.put("gradeName", gradeName);
        map.put("className", className);


        HiWorldCache.postHttpData(((BaseActivity) activity), handler, 2, HistudentUrl.classIsExsit, map, HiWorldCache.Quarry, false,true);
    }


    //在班级创建班级过程中，加入或者转让班级
    public static void TrasferOrJoinClassOnCreatingClass(final Activity activity, final String classId, final Handler handler) {

        ReminderHelper.getIntentce().showDialog(activity, "提示", "是否加入该班级？", "确定", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {
                Map<String, Object> map = new HashMap<>();
                map.put("classId", classId);

                HiWorldCache.postHttpData(((BaseActivity) activity),
                        handler, 1, HistudentUrl.joinOrTransferClassOnCreatingClass, map, HiWorldCache.Quarry, false,true);

            }
        }, "取消", new DialogButtonListener() {
            @Override
            public void setOnDialogButtonListener() {

            }
        });

    }

    public interface isSuccessCallBack {
        public void isSuccess(int type, boolean isSuccess);
    }

}
