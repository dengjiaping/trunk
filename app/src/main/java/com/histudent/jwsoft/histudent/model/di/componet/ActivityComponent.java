package com.histudent.jwsoft.histudent.model.di.componet;

import android.app.Activity;

import com.histudent.jwsoft.histudent.view.activity.blog.BlogDetailActivity;
import com.histudent.jwsoft.histudent.view.activity.clock.AddClockActivity;
import com.histudent.jwsoft.histudent.view.activity.homework.CorrectWorkActivity;
import com.histudent.jwsoft.histudent.view.activity.homework.CreateWorkActivity;
import com.histudent.jwsoft.histudent.view.activity.homework.WorkAlreadyCompleteActivity;
import com.histudent.jwsoft.histudent.view.activity.homework.WorkCreateDivideGroupActivity;
import com.histudent.jwsoft.histudent.view.activity.homework.WorkDetailStudentActivity;
import com.histudent.jwsoft.histudent.view.activity.homework.FinishHomeworkActivity;
import com.histudent.jwsoft.histudent.view.activity.homework.WorkDetailTeacherActivity;
import com.histudent.jwsoft.histudent.view.activity.homework.WorkAddMemberActivity;
import com.histudent.jwsoft.histudent.view.activity.homework.WorkReceiverPersonDetailActivity;

import com.histudent.jwsoft.histudent.view.activity.homework.WorkSelectReceiverPersonActivity;
import com.histudent.jwsoft.histudent.view.activity.homework.WorkSubjectManageActivity;
import com.histudent.jwsoft.histudent.view.activity.homework.WorkUndoneActivity;
import com.histudent.jwsoft.histudent.view.activity.image.ShowImageActivity;
import com.histudent.jwsoft.histudent.model.di.ActivityScope;
import com.histudent.jwsoft.histudent.model.di.module.ActivityModule;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(AddClockActivity addClockActivity);

    void inject(BlogDetailActivity blogDetailActivity);

    void inject(ShowImageActivity showImageActivity);

    void inject(CreateWorkActivity createWorkActivity);

    void inject(WorkDetailTeacherActivity workDetailTeacherActivity);

    void inject(WorkReceiverPersonDetailActivity workReceiverPersonDetailActivity);

    void inject(WorkAddMemberActivity workAddMemberActivity);

    void inject(WorkUndoneActivity workUndoneActivity);

    void inject(FinishHomeworkActivity finishHomeworkActivity);

    void inject(WorkSubjectManageActivity workSubjectManageActivity);

    void inject(WorkSelectReceiverPersonActivity workSelectReceiverPersonActivity);

    void inject(WorkCreateDivideGroupActivity workCreateDivideGroupActivity);

    void inject(WorkDetailStudentActivity workDetailStudentActivity);

    void inject(CorrectWorkActivity correctWorkActivity);

    void inject(WorkAlreadyCompleteActivity workAlreadyCompleteActivity);

}
