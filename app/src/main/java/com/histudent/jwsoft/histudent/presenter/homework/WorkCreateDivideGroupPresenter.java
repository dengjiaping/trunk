package com.histudent.jwsoft.histudent.presenter.homework;

import com.histudent.jwsoft.histudent.adapter.homework.convert.HomeworkGroupDetailDataConvert;
import com.histudent.jwsoft.histudent.base.RxPresenter;
import com.histudent.jwsoft.histudent.bean.homework.CommonMemberBean;
import com.histudent.jwsoft.histudent.bean.homework.GroupMemberDetailEntity;
import com.histudent.jwsoft.histudent.constant.ParamKeys;
import com.histudent.jwsoft.histudent.manage.ParamsManager;
import com.histudent.jwsoft.histudent.model.http.ApiFactory;
import com.histudent.jwsoft.histudent.model.http.BaseHttpResponse;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;
import com.histudent.jwsoft.histudent.presenter.homework.contract.WorkCreateDivideGroupContract;
import com.histudent.jwsoft.histudent.rx.RxException;
import com.histudent.jwsoft.histudent.rx.RxSchedulers;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by lichaojie on 2017/11/1.
 * desc:
 */

public class WorkCreateDivideGroupPresenter extends RxPresenter<WorkCreateDivideGroupContract.View>
        implements WorkCreateDivideGroupContract.Presenter {

    private ApiFactory APIFACTORY;

    @Inject
    public WorkCreateDivideGroupPresenter(ApiFactory mApiFactory) {
        this.APIFACTORY = mApiFactory;
    }


    /**
     * 获取分组列表成员
     *
     * @param teamId
     */
    @Override
    public void getGroupMemberList(String teamId) {
        final Map<String, Object> paramsMap = ParamsManager.getInstance()
                .setParams(ParamKeys.TEAM_ID, teamId)
                .getParamsMap();
        Disposable disposable = APIFACTORY.getWorkApi().getMemberDetailList(paramsMap)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<HttpResponse<GroupMemberDetailEntity>>() {
                    @Override
                    public void accept(HttpResponse<GroupMemberDetailEntity> groupMemberDetailEntityHttpResponse) throws Exception {
                        if (groupMemberDetailEntityHttpResponse.isSuccess()) {
                            final GroupMemberDetailEntity data = groupMemberDetailEntityHttpResponse.getData();
                            final List<CommonMemberBean> convert = HomeworkGroupDetailDataConvert.create(data).convertEntity();
                            mView.updateListData(convert);
                        }
                        mView.controlDialogStatus(groupMemberDetailEntityHttpResponse.getMsg());
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                    mView.controlDialogStatus(e.getMessage());
                }));
        addDispose(disposable);
    }


    /**
     * 修改分组信息
     *
     * @param groupName
     * @param teamId
     * @param studentId
     */
    @Override
    public void modifyGroupInformation(String groupName, String teamId, String studentId) {
        final Map<String, Object> paramsMap = ParamsManager.getInstance()
                .setParams(ParamKeys.NAME, groupName)
                .setParams(ParamKeys.TEAM_ID, teamId)
                .setParams(ParamKeys.STUDENT_ID, studentId)
                .getParamsMap();
        Disposable disposable = APIFACTORY.getWorkApi().modifyGroupInformation(paramsMap)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<BaseHttpResponse>() {
                    @Override
                    public void accept(BaseHttpResponse baseHttpResponse) throws Exception {
                        if (baseHttpResponse.isSuccess()) {
                            mView.modifyGroupInformationSuccess();
                        }
                        mView.controlDialogStatus(baseHttpResponse.getMsg());
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                    mView.controlDialogStatus(e.getMessage());
                }));
        addDispose(disposable);
    }


    /**
     * 添加分组
     *
     * @param groupName
     * @param classId
     * @param studentId
     */
    @Override
    public void addGroupInformation(String groupName, String classId, String studentId) {
        final Map<String, Object> paramsMap = ParamsManager.getInstance()
                .setParams(ParamKeys.NAME, groupName)
                .setParams(ParamKeys.CLASS_ID, classId)
                .setParams(ParamKeys.STUDENT_ID, studentId)
                .getParamsMap();
        Disposable disposable = APIFACTORY.getWorkApi().addGroupInformation(paramsMap)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<BaseHttpResponse>() {
                    @Override
                    public void accept(BaseHttpResponse baseHttpResponse) throws Exception {
                        if (baseHttpResponse.isSuccess()) {
                            mView.addGroupInformationSuccess();
                        }
                        mView.controlDialogStatus(null);
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                    mView.controlDialogStatus(e.getMessage());
                }));
        addDispose(disposable);
    }
}
