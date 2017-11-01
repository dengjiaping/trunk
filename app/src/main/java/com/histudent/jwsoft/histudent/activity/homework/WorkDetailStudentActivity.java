package com.histudent.jwsoft.histudent.activity.homework;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.base.BaseActivity;
import com.histudent.jwsoft.histudent.presenter.homework.WorkDetailStudentPresenter;
import com.histudent.jwsoft.histudent.presenter.homework.contract.WorkDetailStudentContract;

/**
 * Created by huyg on 2017/10/25.
 */

public class WorkDetailStudentActivity extends BaseActivity<WorkDetailStudentPresenter> implements WorkDetailStudentContract.View{


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_work_detail_student;
    }

    @Override
    protected void init() {

    }

    @Override
    public void showContent(String message) {

    }


}
