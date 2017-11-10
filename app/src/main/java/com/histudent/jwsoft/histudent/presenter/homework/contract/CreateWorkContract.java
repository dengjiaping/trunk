package com.histudent.jwsoft.histudent.presenter.homework.contract;

import android.content.Context;

import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo;
import com.histudent.jwsoft.histudent.base.BasePresenter;
import com.histudent.jwsoft.histudent.base.BaseView;
import com.histudent.jwsoft.histudent.bean.UploadAuthBean;
import com.histudent.jwsoft.histudent.bean.homework.CreateWorkBean;
import com.histudent.jwsoft.histudent.commen.bean.VideoAuthBean;
import com.histudent.jwsoft.histudent.entity.AudioInfo;

import java.io.File;
import java.util.List;

/**
 * Created by huyg on 2017/10/23.
 */

public interface CreateWorkContract {

    interface View extends BaseView{
        void showVideoList(String videoId);
        void uploadFile(UploadAuthBean uploadAuthBean);
        void showDialog();
        void closeDialog();
        void createWorkSucceed(CreateWorkBean createWorkBean);
        void showImgList(List<File> files);
    }

    interface Presenter extends BasePresenter<View>{

        void startRecord();
        void stopRecord();
        boolean getRecordState();

        boolean getAudioState();
        void playAudio(String source);
        void playAudio(String source,int position);
        void pauseAudio();
        void stopAudio();

        /**
         * 创建作业任务
         * @param subjectId 科目id
         * @param userTeamIds 分组
         * @param contents 内容
         * @param onlyOnline 是否在线提交
         * @param videoIds 视频id
         * @param audioFiles 音频
         */
        void createHomeWork(String subjectId,
                            String userTeamIds,
                            String contents,
                            boolean onlyOnline,
                            String videoIds,
                            AudioInfo mAudioInfo,
                            List<File> imgFiles);

        /**
         * 获取上传视频凭证
         * @param fileSize
         */
        void getVodUploadAuth(long fileSize);

        /**
         * 上传视频
         * @param uploadAuthBean
         */
        void uploadVideo(Context context,String fileName,UploadAuthBean uploadAuthBean);

        /**
         * 压缩文件图片
         *
         */

        void compressImg(Context context,List<String> imgUrls);

    }


}
