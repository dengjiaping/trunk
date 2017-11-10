package com.histudent.jwsoft.histudent.presenter.homework;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.histudent.jwsoft.histudent.adapter.homework.convert.HomeworkGroupMemberDataConvert;
import com.histudent.jwsoft.histudent.base.RxPresenter;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkSelectGroupL0Bean;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkSelectGroupL1Bean;
import com.histudent.jwsoft.histudent.bean.homework.SelectReceiverPersonEntity;
import com.histudent.jwsoft.histudent.constant.ParamKeys;
import com.histudent.jwsoft.histudent.entity.WorkReceiverEvent;
import com.histudent.jwsoft.histudent.manage.ParamsManager;
import com.histudent.jwsoft.histudent.model.http.ApiFactory;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;
import com.histudent.jwsoft.histudent.presenter.homework.contract.WorkSelectReceiverPersonContract;
import com.histudent.jwsoft.histudent.rx.RxException;
import com.histudent.jwsoft.histudent.rx.RxSchedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by lichaojie on 2017/11/1.
 * desc:
 */

public class WorkSelectReceiverPersonPresenter extends RxPresenter<WorkSelectReceiverPersonContract.View>
        implements WorkSelectReceiverPersonContract.Presenter {

    private final ApiFactory APIFACTORY;

    @Inject
    public WorkSelectReceiverPersonPresenter(ApiFactory apiFactory) {
        this.APIFACTORY = apiFactory;
    }

    @Override
    public void getSelectReceiverPersonList(String classId) {
        final Map<String, Object> paramsMap = ParamsManager.getInstance()
//                .setParams(ParamKeys.CLASS_ID, "8d3c244c-065a-4bfa-b67e-82949c11760d")
                .setParams(ParamKeys.CLASS_ID, classId)
                .getParamsMap();
        Disposable disposable = APIFACTORY.getWorkApi().getSelectReceiverList(paramsMap)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<HttpResponse<SelectReceiverPersonEntity>>() {
                    @Override
                    public void accept(HttpResponse<SelectReceiverPersonEntity> response) throws Exception {
                        if (response.isSuccess()) {
                            final SelectReceiverPersonEntity data = response.getData();
                            final List<HomeworkSelectGroupL0Bean> homeworkSelectGroupL0Been =
                                    HomeworkGroupMemberDataConvert.create(data).convertEntity();
                            mView.updateListData(homeworkSelectGroupL0Been);
                        }
                        mView.controlDialogStatus(response.getMsg());
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                    mView.controlDialogStatus(e.getMessage());
                }));
        addDispose(disposable);
    }


    /**
     * 添加分组后默认选择该分组
     *
     * @param teamId
     * @param list
     */
    public List<HomeworkSelectGroupL0Bean> solveAddGroupMemberInformation(String teamId, List<HomeworkSelectGroupL0Bean> list) {
        if (list != null && list.size() > 0) {
            //主标题
            for (HomeworkSelectGroupL0Bean selectGroupL0Bean : list) {
                final boolean isCheck = selectGroupL0Bean.isCheck();
                final List<HomeworkSelectGroupL1Bean> subItems = selectGroupL0Bean.getSubItems();
                //子标题
                if (subItems != null && subItems.size() > 0) {
                    for (HomeworkSelectGroupL1Bean subItem : subItems) {
                        final String divideTeamId = subItem.getGroupDivideId();
                        if (teamId.equals(divideTeamId)) {
                            //更新刚刚创建的分组
                            subItem.setCheck(true);
                            //如果之前班级标题选中(主标题) 更新主标题状态
                            if (isCheck) {
                                selectGroupL0Bean.setCheck(false);
                            }
                            break;
                        }
                    }
                }
            }
        }
        return list;
    }

    public void adjustDefaultClassStatus(List<String> mTransferTeamIdListData, List<HomeworkSelectGroupL0Bean> list) {
        if (mTransferTeamIdListData.size() == 0)
            return;
        if (mTransferTeamIdListData != null && mTransferTeamIdListData.size() > 0) {
            for (String teamId : mTransferTeamIdListData) {
                if (list != null && list.size() > 0) {
                    for (HomeworkSelectGroupL0Bean homeworkSelectGroupL0Bean : list) {
                        //头标题
                        if (homeworkSelectGroupL0Bean.getTeamId().equals(teamId)) {
                            homeworkSelectGroupL0Bean.setCheck(true);
                        } else {
                            //子条目
                            final List<HomeworkSelectGroupL1Bean> subItems = homeworkSelectGroupL0Bean.getSubItems();
                            if (subItems != null && subItems.size() > 0) {
                                for (HomeworkSelectGroupL1Bean subItem : subItems) {
                                    if (subItem.getGroupDivideId().equals(teamId)) {
                                        subItem.setCheck(true);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            mTransferTeamIdListData.clear();
        }
    }

    public void updateOldDataSelectStatus(List<MultiItemEntity> listData, List<HomeworkSelectGroupL0Bean> list) {
        final List<String> oldSelectTeam = new ArrayList<>();
        for (MultiItemEntity multiItemEntity : listData) {
            if (multiItemEntity instanceof HomeworkSelectGroupL0Bean) {
                //主标题
                final HomeworkSelectGroupL0Bean itemEntity = (HomeworkSelectGroupL0Bean) multiItemEntity;
                if (itemEntity.isCheck()) {
                    oldSelectTeam.add(itemEntity.getTeamId());
                } else {
                    //子条目
                    final List<HomeworkSelectGroupL1Bean> subItems = itemEntity.getSubItems();
                    if (subItems != null && subItems.size() > 0) {
                        for (HomeworkSelectGroupL1Bean subItem : subItems) {
                            if (subItem.isCheck()) {
                                oldSelectTeam.add(subItem.getGroupDivideId());
                            }
                        }
                    }
                }
            }
        }

        //刷新新数据容器中的状态
        if (oldSelectTeam.size() > 0) {
            for (String id : oldSelectTeam) {
                for (HomeworkSelectGroupL0Bean homeworkSelectGroupL0Bean : list) {
                    if (homeworkSelectGroupL0Bean.getTeamId().equals(id)) {
                        homeworkSelectGroupL0Bean.setCheck(true);
                    } else {
                        final List<HomeworkSelectGroupL1Bean> subItems = homeworkSelectGroupL0Bean.getSubItems();
                        if (subItems != null && subItems.size() > 0) {
                            for (HomeworkSelectGroupL1Bean subItem : subItems) {
                                if (subItem.getGroupDivideId().equals(id)) {
                                    subItem.setCheck(true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void solveGroupInformationFromPublish(List<String> transferTeamIdListData, WorkReceiverEvent workReceiverEvent) {
        transferTeamIdListData.clear();
        final List<HomeworkSelectGroupL0Bean> homeworkSelectGroupL0Been = workReceiverEvent.mWorkGroupL0;
        if (homeworkSelectGroupL0Been.size() > 0) {
            for (HomeworkSelectGroupL0Bean groupL0Bean : homeworkSelectGroupL0Been) {
                //添加首标题
                if (groupL0Bean.isCheck()) {
                    final String teamId = groupL0Bean.getTeamId();
                    transferTeamIdListData.add(teamId);
                }
                //添加子标题Id
                final List<HomeworkSelectGroupL1Bean> subItems = groupL0Bean.getSubItems();
                if (subItems.size() > 0) {
                    for (HomeworkSelectGroupL1Bean subItem : subItems) {
                        if (subItem.isCheck()) {
                            final String groupDivideId = subItem.getGroupDivideId();
                            transferTeamIdListData.add(groupDivideId);
                        }
                    }
                }
            }
        }
    }
}
