package com.histudent.jwsoft.histudent.presenter.homework;

import com.histudent.jwsoft.histudent.base.RxPresenter;
import com.histudent.jwsoft.histudent.model.bean.homework.CompleteDetailBean;
import com.histudent.jwsoft.histudent.model.bean.homework.HomeworkDetailBean;
import com.histudent.jwsoft.histudent.commen.utils.FileUtil;
import com.histudent.jwsoft.histudent.model.manage.AudioManager;
import com.histudent.jwsoft.histudent.model.http.ApiFactory;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;
import com.histudent.jwsoft.histudent.presenter.homework.contract.WorkDetailStudentContract;
import com.histudent.jwsoft.histudent.model.rx.RxException;
import com.histudent.jwsoft.histudent.model.rx.RxSchedulers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * Created by huyg on 2017/10/25.
 */

public class WorkDetailStudentPresenter extends RxPresenter<WorkDetailStudentContract.View> implements WorkDetailStudentContract.Presenter {

    private ApiFactory mApiFactory;
    private AudioManager mAudioManager;

    @Inject
    public WorkDetailStudentPresenter(ApiFactory mApiFactory, AudioManager audioManager) {
        this.mApiFactory = mApiFactory;
        this.mAudioManager = audioManager;
    }

    @Override
    public void getHomeworkDetail(String homeworkId) {
        Disposable disposable = mApiFactory.getWorkApi().getHomeworkDetail(homeworkId)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<HttpResponse<HomeworkDetailBean>>() {
                    @Override
                    public void accept(HttpResponse<HomeworkDetailBean> response) throws Exception {
                        if (response.isSuccess()) {
                            HomeworkDetailBean homeworkDetail = response.getData();
                            if (homeworkDetail != null) {
                                mView.showHomeworkDetail(homeworkDetail);
                            }
                        } else {
                            mView.getHomeworkDetailFail();
                        }
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                    mView.getHomeworkDetailFail();
                }));
        addDispose(disposable);
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
    public boolean getAudioState() {
        return mAudioManager.getState();
    }

    @Override
    public void playAudio(String source) {
        mAudioManager.start(source);
    }
    @Override
    public void playAudio(String source,int position) {
        mAudioManager.start(source,position);
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
    public void downloadVoice(String voiceId, int type) {
        Disposable disposable = mApiFactory.getCommonApi().downloadFile(voiceId)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String id = System.currentTimeMillis() + "";
                        File file = new File(FileUtil.getPathFactory(FileUtil.CacheType.VOICE, false) + voiceId);
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
                                mView.downloadVoiceSuccess(file, type);
                            } catch (IOException e) {
                            }
                        }
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                }));
        addDispose(disposable);
    }


}
