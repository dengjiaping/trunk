package com.histudent.jwsoft.histudent.adapter.homework.convert;

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
                        final HomeworkAlreadyBean subHomeAlreadyBean = new HomeworkAlreadyBean(homeworkAlreadySubBean);
                        list.add(subHomeAlreadyBean);
                    }
                }


            }
        }
        return list;
    }
}
