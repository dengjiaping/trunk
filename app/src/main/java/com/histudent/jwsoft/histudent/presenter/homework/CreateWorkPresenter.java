package com.histudent.jwsoft.histudent.presenter.homework;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.sdk.android.vod.upload.VODUploadCallback;
import com.alibaba.sdk.android.vod.upload.VODUploadClientImpl;
import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo;
import com.alibaba.sdk.android.vod.upload.model.VodInfo;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.base.RxPresenter;
import com.histudent.jwsoft.histudent.bean.UploadAuthBean;
import com.histudent.jwsoft.histudent.body.hiworld.activity.EssayActivity;
import com.histudent.jwsoft.histudent.commen.bean.VideoAuthBean;
import com.histudent.jwsoft.histudent.commen.utils.RequestManager;
import com.histudent.jwsoft.histudent.component.AudioManager;
import com.histudent.jwsoft.histudent.component.RecordManager;
import com.histudent.jwsoft.histudent.constant.Const;
import com.histudent.jwsoft.histudent.entity.AudioInfo;
import com.histudent.jwsoft.histudent.model.http.ApiFactory;
import com.histudent.jwsoft.histudent.model.http.BaseHttpResponse;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;
import com.histudent.jwsoft.histudent.model.http.RequestUtils;
import com.histudent.jwsoft.histudent.presenter.homework.contract.CreateWorkContract;
import com.histudent.jwsoft.histudent.rx.RxException;
import com.histudent.jwsoft.histudent.rx.RxSchedulers;

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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import top.zibin.luban.Luban;
//import top.zibin.luban.Luban;

/**
 * Created by huyg on 2017/10/23.
 */

public class CreateWorkPresenter extends RxPresenter<CreateWorkContract.View> implements CreateWorkContract.Presenter {

    private ApiFactory mApiFactory;
    private AudioManager mAudioManager;
    private RecordManager mRecordManager;
    private VODUploadClientImpl mUploader;

    @Inject
    public CreateWorkPresenter(ApiFactory mApiFactory, AudioManager mAudioManager, RecordManager mRecordManager) {
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
    public void pauseAudio() {
        mAudioManager.pause();
    }

    @Override
    public void stopAudio() {
        mAudioManager.stop();
    }

    @Override
    public void createHomeWork(String subjectId, String userTeamIds, String contents, boolean onlyOnline, String videoIds, AudioInfo mAudioInfo, List<File> imgFiles) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        RequestUtils.addTextPart(parts, "subjectId", subjectId);
        params.put("subjectId", subjectId);
        RequestUtils.addTextPart(parts, "userTeamIds", userTeamIds);
        params.put("userTeamIds", userTeamIds);
        RequestUtils.addTextPart(parts, "contents", contents);
        params.put("contents", contents);
        RequestUtils.addTextPart(parts, "onlyOnline", String.valueOf(onlyOnline));
        params.put("onlyOnline", String.valueOf(onlyOnline));
        if (!TextUtils.isEmpty(videoIds)) {
            RequestUtils.addTextPart(parts, "videoIds", videoIds);
            params.put("videoIds", videoIds);
        }

        RequestUtils.initFixedParams(parts, params);
        if (mAudioInfo.getFile() != null) {
            RequestBody requestBody = RequestBody.create(Const.MEDIA_TYPE_MARKDOWN, mAudioInfo.getFile());
            MultipartBody.Part part = MultipartBody.Part.
                    createFormData("voicefile", mAudioInfo.getFile().getName(), requestBody);
            RequestUtils.addTextPart(parts, "voiceLength", String.valueOf(mAudioInfo.getTime()));
            params.put("voiceLength", String.valueOf(mAudioInfo.getTime()));
            parts.add(part);
        } else {
            RequestUtils.addTextPart(parts, "voiceLength", "0");
            params.put("voiceLength", "0");
        }
        for (int i = 0; i < imgFiles.size(); i++) {
            RequestBody requestBody = RequestBody.create(Const.MEDIA_TYPE_MARKDOWN, imgFiles.get(i));
            MultipartBody.Part part = MultipartBody.Part.
                    createFormData("imagefile", imgFiles.get(i).getName(), requestBody);
            parts.add(part);
        }

        Disposable disposable = mApiFactory.getWorkApi().createHomeWork(parts)
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
                mView.closeDialog();
                mView.showVideoList(uploadAuthBean.getVideoId());
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
                        return Luban.with(context).load(list).get();
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
