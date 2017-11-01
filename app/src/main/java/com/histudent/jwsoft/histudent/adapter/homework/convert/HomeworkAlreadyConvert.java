package com.histudent.jwsoft.histudent.adapter.homework.convert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.histudent.jwsoft.histudent.bean.homework.AlreadyCompleteHomeworkEntity;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkAlreadyBean;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkAlreadySubBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichaojie on 2017/10/26.
 * desc:
 * 老师或者学生的作业数据转换
 */

public class HomeworkAlreadyConvert {

    private AlreadyCompleteHomeworkEntity mAlreadyCompleteHomeworkEntity;

    public static final HomeworkAlreadyConvert create(AlreadyCompleteHomeworkEntity entity) {
        return new HomeworkAlreadyConvert(entity);
    }

    private HomeworkAlreadyConvert(AlreadyCompleteHomeworkEntity entity) {
        this.mAlreadyCompleteHomeworkEntity = entity;
    }

    public final ArrayList<HomeworkAlreadyBean> convertEntity() {
        final ArrayList<HomeworkAlreadyBean> list = new ArrayList<>();
        final List<AlreadyCompleteHomeworkEntity.ItemsEntity> items = mAlreadyCompleteHomeworkEntity.getItems();
        final int size = items.size();
        if (size > 0) {
            for (AlreadyCompleteHomeworkEntity.ItemsEntity item : items) {
                final String timeStr = item.getTimeStr();
                final String weekStr = item.getWeekStr();
                final List<AlreadyCompleteHomeworkEntity.ItemsEntity.SubItemEntity> workList = item.getWorkList();
                final HomeworkAlreadyBean homeworkAlreadyBean = new HomeworkAlreadyBean(true, timeStr + " " + weekStr);
                list.add(homeworkAlreadyBean);
                //transfer subItem
                final int subSize = workList.size();
                if (subSize > 0) {
                    for (AlreadyCompleteHomeworkEntity.ItemsEntity.SubItemEntity subItemEntity : workList) {
                        final HomeworkAlreadySubBean homeworkAlreadySubBean = new HomeworkAlreadySubBean();

                        final String subjectName = subItemEntity.getSubjectName();
                        final String teamName = subItemEntity.getTeamName();
                        final String contents = subItemEntity.getContents();
                        final String ownerName = subItemEntity.getOwnerName();
                        final String ownerId = subItemEntity.getOwnerId();
                        final String logo = subItemEntity.getLogo();
                        final String id = subItemEntity.getId();
                        final boolean onlyOnline = subItemEntity.isOnlyOnline();
                        homeworkAlreadySubBean.setHomeWorkName(subjectName)
                                .setHomeworkContent(contents)
                                .setGroupName(teamName)
                                .setPublishOwner(ownerName)
                                .setThumb(logo)
                                .setOwnerId(ownerId)
                                .setId(id)
                                .setOnlyOnline(onlyOnline);
                        //// TODO: 2017/10/31  模拟添加groupName名称
//                        if (j == 0) {
//                            homeworkAlreadySubBean.setGroupName("作业,作业(组),作业A3(组),作业,作业A5(组)");
//                        } else if (j == 1) {
//                            homeworkAlreadySubBean.setGroupName("作业B1(组),作业B2(组),作业(组),作业,作业B5(组)");
//                        } else if (j == 2) {
//                            homeworkAlreadySubBean.setGroupName("作业,作业c2(组),作业c3(组),作业c4(组),作业c5(组)");
//                        } else if (j == 3) {
//                            homeworkAlreadySubBean.setGroupName("作业d1(组),作业,作业d3(组),作业d4(组),作业d5(组)");
//                        } else if (j == 4) {
//                            homeworkAlreadySubBean.setGroupName("作业e1(组),e2,作业e3(组),作业,作业e5(组)");
//                        } else if (j == 5) {
//                            homeworkAlreadySubBean.setGroupName("作业f1(组),作业f2,作业f3(组),作业,作业f5(组)");
//                        }
                        final HomeworkAlreadyBean subHomeAlreadyBean = new HomeworkAlreadyBean(homeworkAlreadySubBean);
                        list.add(subHomeAlreadyBean);
                    }
                }


            }
        }
        return list;
    }
}
