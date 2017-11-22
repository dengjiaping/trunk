package com.histudent.jwsoft.histudent.presenter.homework;

import com.histudent.jwsoft.histudent.base.RxPresenter;
import com.histudent.jwsoft.histudent.model.bean.homework.HomeworkDetailBean;
import com.histudent.jwsoft.histudent.model.bean.homework.WorkCompleteBean;
import com.histudent.jwsoft.histudent.commen.utils.FileUtil;
import com.histudent.jwsoft.histudent.model.manage.AudioManager;
import com.histudent.jwsoft.histudent.model.manage.ParamsManager;
import com.histudent.jwsoft.histudent.model.http.ApiFactory;
import com.histudent.jwsoft.histudent.model.http.BaseHttpResponse;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;
import com.histudent.jwsoft.histudent.presenter.homework.contract.WorkDetailTeacherContract;
import com.histudent.jwsoft.histudent.model.rx.RxException;
import com.histudent.jwsoft.histudent.model.rx.RxSchedulers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * Created by huyg on 2017/10/25.
 */

public class WorkDetailTeacherPresenter extends RxPresenter<WorkDetailTeacherContract.View> implements WorkDetailTeacherContract.Presenter {

    private ApiFactory mApiFactory;
    private AudioManager mAudioManager;

    @Inject
    public WorkDetailTeacherPresenter(ApiFactory mApiFactory, AudioManager mAudioManager) {
        this.mApiFactory = mApiFactory;
        this.mAudioManager = mAudioManager;
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
    public boolean getAudioState() {
        return mAudioManager.getState();
    }

    @Override
    public void getCompleteList(String homeworkId, boolean isComplete, int index, int size) {
        Disposable disposable = mApiFactory.getWorkApi().getCompleteList(homeworkId, isComplete, index, size)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<HttpResponse<WorkCompleteBean>>() {
                    @Override
                    public void accept(HttpResponse<WorkCompleteBean> response) throws Exception {
                        if (response.isSuccess()) {
                            WorkCompleteBean workComplete = response.getData();
                            if (workComplete != null) {
                                List<WorkCompleteBean.ItemsBean> itemsBeens = workComplete.getItems();

                                mView.showCompleteList(isComplete,itemsBeens);
                            }
                        } else {
                            mView.getCompleteListFail();
                        }

                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                    mView.getCompleteListFail();
                }));
        addDispose(disposable);
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
    public void downloadVoice(String voiceId) {
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
                                mView.downloadVoiceSuccess(file);
                            } catch (IOException e) {
                            }
                        }
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                }));
        addDispose(disposable);
    }


    /**
     * 删除作业操作
     *
     * @param homeworkId
     */
    public void deleteHomeworkOperation(String homeworkId) {
        // TODO: 2017/11/7
        final Map<String, Object> paramsMap = ParamsManager.getInstance()
                .setParams("hwId", homeworkId)
                .getParamsMap();
        Disposable disposable = mApiFactory.getWorkApi().deleteCompleteSpecifiedHomework(paramsMap)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<BaseHttpResponse>() {
                    @Override
                    public void accept(BaseHttpResponse baseHttpResponse) throws Exception {
                        if (baseHttpResponse.isSuccess()) {
                            mView.deleteHomeworkSuccess();
                        }
                        mView.controlDialogStatus(null);
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                    mView.controlDialogStatus(e.getMessage());
                }));
        addDispose(disposable);
    }

    @Override
    public void singleNotice(String homeworkId, String userId, int type) {
        Disposable disposable = mApiFactory.getWorkApi().singleNotice(homeworkId, userId, type)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<BaseHttpResponse>() {
                    @Override
                    public void accept(BaseHttpResponse response) throws Exception {
                        if (response.isSuccess()) {
                            mView.showContent("通知成功");
                        } else {
                            mView.showContent(response.getMsg());
                        }

                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                    mView.showContent("通知失败");
                }));
        addDispose(disposable);
    }
}
