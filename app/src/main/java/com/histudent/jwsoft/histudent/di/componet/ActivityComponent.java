package com.histudent.jwsoft.histudent.di.componet;

import android.app.Activity;

import com.histudent.jwsoft.histudent.activity.blog.BlogDetailActivity;
import com.histudent.jwsoft.histudent.activity.clock.AddClockActivity;
import com.histudent.jwsoft.histudent.activity.homework.CorrectWorkActivity;
import com.histudent.jwsoft.histudent.activity.homework.CreateWorkActivity;
import com.histudent.jwsoft.histudent.activity.homework.WorkAlreadyCompleteActivity;
import com.histudent.jwsoft.histudent.activity.homework.WorkCreateDivideGroupActivity;
import com.histudent.jwsoft.histudent.activity.homework.WorkDetailStudentActivity;
import com.histudent.jwsoft.histudent.activity.homework.FinishHomeworkActivity;
import com.histudent.jwsoft.histudent.activity.homework.WorkDetailTeacherActivity;
import com.histudent.jwsoft.histudent.activity.homework.WorkAddMemberActivity;
import com.histudent.jwsoft.histudent.activity.homework.WorkReceiverPersonDetailActivity;

import com.histudent.jwsoft.histudent.activity.homework.WorkSelectReceiverPersonActivity;
import com.histudent.jwsoft.histudent.activity.homework.WorkSubjectManageActivity;
import com.histudent.jwsoft.histudent.activity.homework.WorkUndoneActivity;
import com.histudent.jwsoft.histudent.activity.image.ShowImageActivity;
import com.histudent.jwsoft.histudent.di.ActivityScope;
import com.histudent.jwsoft.histudent.di.module.ActivityModule;

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
