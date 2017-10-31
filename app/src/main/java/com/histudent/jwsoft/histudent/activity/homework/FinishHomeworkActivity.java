package com.histudent.jwsoft.histudent.activity.homework;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.base.BaseActivity;
import com.histudent.jwsoft.histudent.presenter.homework.FinishWorkPresenter;
import com.histudent.jwsoft.histudent.presenter.homework.contract.FinishWorkContract;

/**
 * Created by huyg on 2017/10/30.
 */

public class FinishHomeworkActivity extends BaseActivity<FinishWorkPresenter> implements FinishWorkContract.View{


    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_work_finish;
    }

    @Override
    protected void init() {

    }

    @Override
    public void showContent(String message) {

    }
}
