package com.histudent.jwsoft.histudent.presenter.homework;

import com.histudent.jwsoft.histudent.adapter.homework.convert.AddMemberDataConvert;
import com.histudent.jwsoft.histudent.base.RxPresenter;
import com.histudent.jwsoft.histudent.bean.homework.ClassMemberEntity;
import com.histudent.jwsoft.histudent.bean.homework.CommonMemberBean;
import com.histudent.jwsoft.histudent.constant.ParamKeys;
import com.histudent.jwsoft.histudent.manage.ParamsManager;
import com.histudent.jwsoft.histudent.model.http.ApiFactory;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;
import com.histudent.jwsoft.histudent.presenter.homework.contract.WorkAddMemberContract;
import com.histudent.jwsoft.histudent.rx.RxException;
import com.histudent.jwsoft.histudent.rx.RxSchedulers;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by lichaojie on 2017/10/31.
 * desc:
 * 作业-添加成员列表
 */

public class WorkAddMemberPresenter extends RxPresenter<WorkAddMemberContract.View>
        implements WorkAddMemberContract.Presenter {

    private final ApiFactory APIFACTORY;


    @Inject
    public WorkAddMemberPresenter(ApiFactory apiFactory) {
        this.APIFACTORY = apiFactory;
    }


    @Override
    public void getGroupMemberList(String classId) {
        final Map<String, Object> map = ParamsManager.getInstance()
                .setParams(ParamKeys.CLASS_ID, classId)
                .getParamsMap();

        Disposable disposable = APIFACTORY.getWorkApi().getClassMembersList(map)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<HttpResponse<ClassMemberEntity>>() {
                    @Override
                    public void accept(HttpResponse<ClassMemberEntity> classMemberEntityHttpResponse) throws Exception {
                        final ClassMemberEntity data = classMemberEntityHttpResponse.getData();
                        if (data != null) {
                            final AddMemberDataConvert addMemberDataConvert = new AddMemberDataConvert();
                            final List<CommonMemberBean> commonMemberBean = addMemberDataConvert.convertEntity(data);
                            mView.updateListData(commonMemberBean);
                        }
                        mView.controlDialogStatus(null);
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                    final String message = e.getMessage();
                    mView.controlDialogStatus(message);
                }));

        addDispose(disposable);
    }
}
