package com.histudent.jwsoft.histudent.presenter.homework;

import android.text.TextUtils;

import com.histudent.jwsoft.histudent.base.RxPresenter;
import com.histudent.jwsoft.histudent.model.bean.homework.CommentBean;
import com.histudent.jwsoft.histudent.model.bean.homework.CompleteDetailBean;
import com.histudent.jwsoft.histudent.commen.utils.FileUtil;
import com.histudent.jwsoft.histudent.model.manage.AudioManager;
import com.histudent.jwsoft.histudent.model.manage.RecordManager;
import com.histudent.jwsoft.histudent.model.constant.Const;
import com.histudent.jwsoft.histudent.model.entity.AudioInfo;
import com.histudent.jwsoft.histudent.model.http.ApiFactory;
import com.histudent.jwsoft.histudent.model.http.BaseHttpResponse;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;
import com.histudent.jwsoft.histudent.model.http.RequestUtils;
import com.histudent.jwsoft.histudent.presenter.homework.contract.CorrectContract;
import com.histudent.jwsoft.histudent.model.rx.RxException;
import com.histudent.jwsoft.histudent.model.rx.RxSchedulers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by huyg on 2017/10/30.
 */

public class CorrectPresenter extends RxPresenter<CorrectContract.View> implements CorrectContract.Presenter {
    private ApiFactory mApiFactory;
    private AudioManager mAudioManager;
    private RecordManager mRecordManager;

    @Inject
    public CorrectPresenter(ApiFactory mApiFactory, AudioManager audioManager, RecordManager mRecordManager) {
        this.mApiFactory = mApiFactory;
        this.mAudioManager = audioManager;
        this.mRecordManager = mRecordManager;
    }

    @Override
    public void getCompleteDetail(String homeworkId, String userId) {
        Disposable disposable = mApiFactory.getWorkApi().completeDetailed(homeworkId, userId)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<HttpResponse<CompleteDetailBean>>() {
                    @Override
                    public void accept(HttpResponse<CompleteDetailBean> response) throws Exception {
                        if (response.isSuccess()) {
                            CompleteDetailBean completeDetailBean = response.getData();
                            if (completeDetailBean != null) {
                                mView.showCompleteDetail(completeDetailBean);
                            }
                        } else {
                            mView.showContent("获取作业失败");
                        }
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                    mView.showContent("获取作业失败");
                }));
        addDispose(disposable);
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
    public void playAudio(String source) {
        mAudioManager.start(source);
    }

    @Override
    public boolean getAudioState() {
        return mAudioManager.getState();
    }

    @Override
    public void downloadVoice(String voiceId,int type) {
        Disposable disposable = mApiFactory.getCommonApi().downloadFile(voiceId)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String id = System.currentTimeMillis() + "";
                        File file = new File(FileUtil.getPathFactory(FileUtil.CacheType.VOICE, false));
                        InputStream is = null;
                        byte[] buf = new byte[2048];
                        int len = 0;
                        FileOutputStream fos = null;
                        try {
                            long total = responseBody.contentLength();
                            long current = 0;
                            is = responseBody.byteStream();
                            fos = new FileOutputStream(file);
                            while ((len = is.read(buf)) != -1) {
                                current += len;
                                fos.write(buf, 0, len);
                            }
                            fos.flush();
                        } catch (Exception e) {

                        } finally {
                            try {
                                if (is != null) {
                                    is.close();
                                }
                                if (fos != null) {
                                    fos.close();
                                }
                                mView.downloadVoiceSuccess(file,type);
                            } catch (IOException e) {
                            }
                        }
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                }));
        addDispose(disposable);
    }

    @Override
    public void getCommentList(String completeId) {
        Disposable disposable = mApiFactory.getWorkApi().commentProposalList(completeId)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<HttpResponse<List<CommentBean>>>() {
                    @Override
                    public void accept(HttpResponse<List<CommentBean>> response) throws Exception {
                        if (response.isSuccess()) {
                            List<CommentBean> commentBeans = response.getData();
                            if (commentBeans != null && commentBeans.size() > 0) {
                                mView.commentProposalSuccess(commentBeans);
                            }
                        } else {
                        }
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                }));
        addDispose(disposable);
    }

    @Override
    public void commentHomework(String completeId, String commentContent, String proposalIds, AudioInfo audioInfo,String delVoice) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        RequestUtils.addTextPart(parts, "completeId", completeId);
        params.put("completeId", completeId);
        RequestUtils.addTextPart(parts, "commentContent", commentContent);
        params.put("commentContent", commentContent);
        if (!TextUtils.isEmpty(proposalIds)){
            RequestUtils.addTextPart(parts, "proposalIds", proposalIds);
            params.put("proposalIds", proposalIds);
        }
//        if (!TextUtils.isEmpty(delVoice)){
//            RequestUtils.addTextPart(parts, "delVoice", delVoice);
//            params.put("delVoice", delVoice);
//        }
        RequestUtils.initFixedParams(parts, params);
        if (audioInfo.getFile() != null) {
            RequestBody requestBody = RequestBody.create(Const.MEDIA_TYPE_MARKDOWN, audioInfo.getFile());
            MultipartBody.Part part = MultipartBody.Part.
                    createFormData("voicefile", audioInfo.getFile().getName(), requestBody);
            RequestUtils.addTextPart(parts, "voiceLength", String.valueOf(audioInfo.getTime()));
            params.put("voiceLength", String.valueOf(audioInfo.getTime()));
            RequestUtils.addTextPart(parts, "hasVoice", String.valueOf(true));
            params.put("hasVoice", String.valueOf(true));
            parts.add(part);
        } else {
            RequestUtils.addTextPart(parts, "voiceLength", "0");
            params.put("voiceLength", "0");
            RequestUtils.addTextPart(parts, "hasVoice", String.valueOf(false));
            params.put("hasVoice", String.valueOf(false));
        }
        RequestUtils.addTextPart(parts, "hasImage", String.valueOf(false));
        params.put("hasImage", String.valueOf(false));
        Disposable disposable = mApiFactory.getWorkApi().commentHomework(parts)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<BaseHttpResponse>() {
                    @Override
                    public void accept(BaseHttpResponse response) throws Exception {
                        if (response.isSuccess()) {
                            mView.commentHomeworkSuccess();
                        } else {
                            mView.showContent("评价失败");
                        }
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                    mView.showContent("评价失败");
                }));
        addDispose(disposable);
    }
}
