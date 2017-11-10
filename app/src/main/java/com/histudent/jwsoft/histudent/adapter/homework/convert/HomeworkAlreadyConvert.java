package com.histudent.jwsoft.histudent.adapter.homework.convert;

import android.content.Context;

import com.histudent.jwsoft.histudent.activity.homework.WorkAlreadyCompleteActivity;
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
    private final Context CONTEXT;

    public static final HomeworkAlreadyConvert create(Context context, AlreadyCompleteHomeworkEntity entity) {
        return new HomeworkAlreadyConvert(context, entity);
    }

    private HomeworkAlreadyConvert(Context context, AlreadyCompleteHomeworkEntity entity) {
        this.mAlreadyCompleteHomeworkEntity = entity;
        this.CONTEXT = context;
    }

    public final ArrayList<HomeworkAlreadyBean> convertEntity() {
        final ArrayList<HomeworkAlreadyBean> list = new ArrayList<>();
        final List<AlreadyCompleteHomeworkEntity.ItemsEntity> items = mAlreadyCompleteHomeworkEntity.getItems();
        final int size = items.size();
        if (size > 0) {
            String preHeadDesc = "";
            //获取上一次数据源中的最后一条数据 并记录其标题时间
            if (CONTEXT instanceof WorkAlreadyCompleteActivity) {
                final List<HomeworkAlreadyBean> listData = ((WorkAlreadyCompleteActivity) CONTEXT).getListData();
                final boolean loadMore = ((WorkAlreadyCompleteActivity) CONTEXT).getRefreshHandler().isLoadMore();
                if (listData != null && listData.size() > 0) {
                    if (loadMore) {
                        preHeadDesc = listData.get(listData.size() - 1).getTitleHeadContent();
                    } else {
                        preHeadDesc = "";
                    }
                }
            }
            if (preHeadDesc == null)
                preHeadDesc = "";

            for (AlreadyCompleteHomeworkEntity.ItemsEntity item : items) {
                final String timeStr = item.getTimeStr();
                final String weekStr = item.getWeekStr();
                final List<AlreadyCompleteHomeworkEntity.ItemsEntity.SubItemEntity> workList = item.getWorkList();
                final String currentHeadDesc = timeStr + " " + weekStr;
                final HomeworkAlreadyBean homeworkAlreadyBean = new HomeworkAlreadyBean(true, currentHeadDesc);
                if (!preHeadDesc.equals(currentHeadDesc))
                    list.add(homeworkAlreadyBean);
                preHeadDesc = currentHeadDesc;
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
                        final boolean isComplete = subItemEntity.isComplete();
                        homeworkAlreadySubBean.setHomeWorkName(subjectName)
                                .setHomeworkContent(contents)
                                .setGroupName(teamName)
                                .setPublishOwner(ownerName)
                                .setThumb(logo)
                                .setOwnerId(ownerId)
                                .setId(id)
                                .setComplete(isComplete)
                                .setOnlyOnline(onlyOnline);
                        final HomeworkAlreadyBean subHomeAlreadyBean = new HomeworkAlreadyBean(homeworkAlreadySubBean);
                        subHomeAlreadyBean.setTitleHeadContent(currentHeadDesc);
                        list.add(subHomeAlreadyBean);
                    }
                }


            }
        }
        return list;
    }
}
