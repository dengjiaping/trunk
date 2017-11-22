package com.histudent.jwsoft.histudent.presenter.homework;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.sdk.android.vod.upload.VODUploadCallback;
import com.alibaba.sdk.android.vod.upload.VODUploadClientImpl;
import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo;
import com.alibaba.sdk.android.vod.upload.model.VodInfo;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.base.RxPresenter;
import com.histudent.jwsoft.histudent.model.bean.UploadAuthBean;
import com.histudent.jwsoft.histudent.model.manage.AudioManager;
import com.histudent.jwsoft.histudent.model.manage.RecordManager;
import com.histudent.jwsoft.histudent.model.constant.Const;
import com.histudent.jwsoft.histudent.model.entity.AudioInfo;
import com.histudent.jwsoft.histudent.model.http.ApiFactory;
import com.histudent.jwsoft.histudent.model.http.BaseHttpResponse;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;
import com.histudent.jwsoft.histudent.model.http.RequestUtils;
import com.histudent.jwsoft.histudent.presenter.homework.contract.FinishWorkContract;
import com.histudent.jwsoft.histudent.model.rx.RxException;
import com.histudent.jwsoft.histudent.model.rx.RxSchedulers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
//import top.zibin.luban.Luban;

/**
 * Created by huyg on 2017/10/30.
 */

public class FinishWorkPresenter extends RxPresenter<FinishWorkContract.View> implements FinishWorkContract.Presenter {
    private ApiFactory mApiFactory;
    private AudioManager mAudioManager;
    private RecordManager mRecordManager;
    private VODUploadClientImpl mUploader;

    @Inject
    public FinishWorkPresenter(ApiFactory mApiFactory, AudioManager mAudioManager, RecordManager mRecordManager) {
        this.mApiFactory = mApiFactory;
        this.mAudioManager = mAudioManager;
        this.mRecordManager = mRecordManager;
    }


    @Override
    public void startRecord() {
        mRecordManager.startRecord();
    }

    @Override
    public void stopRecord() {
        mRecordManager.stopRecord();
    }

    @Override
    public boolean getRecordState() {
        return mRecordManager.isRecording();
    }

    @Override
    public boolean getAudioState() {
        return mAudioManager.getState();
    }


    @Override
    public void playAudio(String source) {
        mAudioManager.start(source);
    }

    @Override
    public void playAudio(String source, int position) {
        mAudioManager.start(source, position);
    }

    @Override
    public void pauseAudio() {
        mAudioManager.pause();
    }

    @Override
    public void stopAudio() {
        mAudioManager.stop();
    }

    @Override
    public void completeHomeWork(String homeworkId,
                                 String contents,
                                 String videoIds,
                                 AudioInfo audioInfo,
                                 List<File> imgFiles,
                                 boolean hasImage,
                                 boolean hasVoice,
                                 boolean hasVideo,
                                 String delImg,
                                 String delVideo,
                                 String delVoice) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        RequestUtils.addTextPart(parts, "homeworkId", homeworkId);
        params.put("homeworkId", homeworkId);
        RequestUtils.addTextPart(parts, "content", contents);
        params.put("content", contents);
        if (!TextUtils.isEmpty(videoIds)) {
            RequestUtils.addTextPart(parts, "videoIds", videoIds);
            params.put("videoIds", videoIds);
            RequestUtils.addTextPart(parts, "hasVideo", String.valueOf(hasVideo));
            params.put("hasVideo", String.valueOf(hasVideo));
        } else {
            RequestUtils.addTextPart(parts, "hasVideo", String.valueOf(hasVideo));
            params.put("hasVideo", String.valueOf(hasVideo));
        }

        if (!TextUtils.isEmpty(delImg)) {
            RequestUtils.addTextPart(parts, "delImgs", delImg);
            params.put("delImgs", delImg);
        }
        if (!TextUtils.isEmpty(delVoice)) {
            RequestUtils.addTextPart(parts, "delVoice", delVoice);
            params.put("delVoice", delVoice);
        }
        if (!TextUtils.isEmpty(delVideo)) {
            RequestUtils.addTextPart(parts, "delVideo", delVideo);
            params.put("delVideo", delVideo);
        }

        RequestUtils.initFixedParams(parts, params);
        if (audioInfo != null && audioInfo.getFile() != null) {
            RequestUtils.addTextPart(parts, "hasVoice", String.valueOf(hasVoice));
            params.put("hasVoice", String.valueOf(hasVoice));
            RequestUtils.addTextPart(parts, "voiceLength", String.valueOf(audioInfo.getTime()));
            params.put("voiceLength", String.valueOf(audioInfo.getTime()));
            RequestBody requestBody = RequestBody.create(Const.MEDIA_TYPE_MARKDOWN, audioInfo.getFile());
            MultipartBody.Part part = MultipartBody.Part.
                    createFormData("voicefile", audioInfo.getFile().getName(), requestBody);
            parts.add(part);
        } else {
            RequestUtils.addTextPart(parts, "hasVoice", String.valueOf(hasVoice));
            params.put("hasVoice", String.valueOf(hasVoice));
            RequestUtils.addTextPart(parts, "voiceLength", String.valueOf(0));
            params.put("voiceLength", String.valueOf(0));
        }
        if (imgFiles != null && imgFiles.size() > 0) {
            RequestUtils.addTextPart(parts, "hasImage", String.valueOf(hasImage));
            params.put("hasImage", String.valueOf(hasImage));
            for (int i = 0; i < imgFiles.size(); i++) {
                RequestBody requestBody = RequestBody.create(Const.MEDIA_TYPE_MARKDOWN, imgFiles.get(i));
                MultipartBody.Part part = MultipartBody.Part.
                        createFormData("imagefile", imgFiles.get(i).getName(), requestBody);
                parts.add(part);
            }
        } else {
            RequestUtils.addTextPart(parts, "hasImage", String.valueOf(hasImage));
            params.put("hasImage", String.valueOf(hasImage));
        }


        Disposable disposable = mApiFactory.getWorkApi().completeHomework(parts)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<BaseHttpResponse>() {
                    @Override
                    public void accept(BaseHttpResponse baseHttpResponse) throws Exception {
                        if (baseHttpResponse.isSuccess()) {
                            mView.createWorkSucceed();
                        } else {
                            mView.showContent(baseHttpResponse.getMsg());
                        }
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                    mView.showContent("发布作业失败");
                }));
        addDispose(disposable);
    }


    @Override
    public void getVodUploadAuth(long fileSize) {
        Disposable disposable = mApiFactory.getCommonApi().getVodUploadAuth(fileSize)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<HttpResponse<UploadAuthBean>>() {
                    @Override
                    public void accept(HttpResponse<UploadAuthBean> response) throws Exception {
                        if (response.isSuccess()) {
                            UploadAuthBean uploadAuthBean = response.getData();
                            if (uploadAuthBean != null) {
                                mView.uploadFile(uploadAuthBean);
                            }
                        } else {
                            mView.showContent("授权失败");
                        }
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                    mView.showContent("授权失败");
                }));
        addDispose(disposable);
    }

    @Override
    public void uploadVideo(Context context, String fileName, UploadAuthBean uploadAuthBean) {
        mUploader = new VODUploadClientImpl(context);
        VODUploadCallback callback = new VODUploadCallback() {

            @Override
            public void onUploadSucceed(UploadFileInfo info) {
                mView.showVideoList(uploadAuthBean.getVideoId());
                mView.closeDialog();
            }

            @Override
            public void onUploadFailed(UploadFileInfo info, String code, String message) {
                HiStudentLog.e("onfailed ------------------ ");
            }

            @Override
            public void onUploadProgress(UploadFileInfo info, long uploadedSize, long totalSize) {
            }

            @Override
            public void onUploadTokenExpired() {
                HiStudentLog.e("onExpired ------------- ");
            }

            @Override
            public void onUploadRetry(String code, String message) {
                HiStudentLog.e("onUploadRetry ------------- ");

            }

            @Override
            public void onUploadRetryResume() {
                HiStudentLog.e("onUploadRetryResume ------------- ");
            }

            @Override
            public void onUploadStarted(UploadFileInfo uploadFileInfo) {
                HiStudentLog.e("onUploadStarted ------------- ");
                mView.showDialog();
            }
        };
        mUploader.init(callback);
        mUploader.addFile(fileName, getVodInfo());
        mUploader.setUploadAuthAndAddress(getUploadFileInfo(fileName), uploadAuthBean.getUploadAuth(), uploadAuthBean.getUploadAddress());
        mUploader.start();
    }

    @Override
    public void compressImg(Context context, List<String> imgUrls) {
        Flowable.just(imgUrls)
                .observeOn(Schedulers.io())
                .map(new Function<List<String>, List<File>>() {
                    @Override
                    public List<File> apply(@NonNull List<String> list) throws Exception {
                        List<String> realList = new ArrayList<>();
                        if (list != null && list.size() > 0) {
                            for (int i = 0; i < list.size(); i++) {
                                if (new File(list.get(i)).exists()) {
                                    realList.add(list.get(i));
                                }
                            }
                        }
                        return Luban.with(context).load(realList).get();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<File>>() {
                    @Override
                    public void accept(@NonNull List<File> list) throws Exception {
                        mView.showImgList(list);
                    }
                });
    }

    private UploadFileInfo getUploadFileInfo(String fileName) {
        UploadFileInfo vodInfo = new UploadFileInfo();
        vodInfo.setFilePath(fileName);
        return vodInfo;
    }

    /**
     * 创建视频信息实体类
     *
     * @return
     */
    private VodInfo getVodInfo() {
        VodInfo vodInfo = new VodInfo();
        vodInfo.setCateId((int) System.currentTimeMillis());
        return vodInfo;
    }
}
