package com.histudent.jwsoft.histudent.activity.homework;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.base.BaseActivity;
import com.histudent.jwsoft.histudent.presenter.homework.CorrectPresenter;
import com.histudent.jwsoft.histudent.presenter.homework.contract.CorrectContract;

/**
 * Created by huyg on 2017/10/30.
 * 批改作业
 */

public class CorrectWorkActivity extends BaseActivity<CorrectPresenter> implements CorrectContract.View{


    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_correct_work;
    }

    @Override
    protected void init() {

    }

    @Override
    public void showContent(String message) {

    }
}
