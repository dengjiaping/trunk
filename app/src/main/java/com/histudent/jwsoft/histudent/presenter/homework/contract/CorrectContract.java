package com.histudent.jwsoft.histudent.presenter.homework.contract;

import com.histudent.jwsoft.histudent.base.BasePresenter;
import com.histudent.jwsoft.histudent.base.BaseView;
import com.histudent.jwsoft.histudent.bean.homework.CommentBean;
import com.histudent.jwsoft.histudent.bean.homework.CompleteDetailBean;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkDetailBean;

import java.io.File;
import java.util.List;

/**
 * Created by huyg on 2017/10/30.
 */

public interface CorrectContract {

    interface View extends BaseView{
        void showCompleteDetail(CompleteDetailBean completeDetailBean);
        void getCompleteDetailFail();
        void downloadVoiceSuccess(File file);
        void commentProposalSuccess(List<CommentBean> commentBeen);
    }

    interface Presenter extends BasePresenter<View>{
        void startRecord();
        void stopRecord();
        boolean getRecordState();
        void getCompleteDetail(String homeworkId,String userId);
        boolean getAudioState();
        void playAudio(String source);
        void pauseAudio();
        void stopAudio();
        void downloadVoice(String voiceId);
        void getCommentList(String completeId);
        void commentHomework(String completeId,String commentContent,String proposalIds);
    }
}
