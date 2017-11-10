package com.histudent.jwsoft.histudent.component;

import com.histudent.jwsoft.histudent.HTApplication;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.entity.AudioPlayEvent;
import com.histudent.jwsoft.histudent.entity.AudioPlayStatusEvent;
import com.netease.nimlib.sdk.media.player.AudioPlayer;
import com.netease.nimlib.sdk.media.player.OnPlayListener;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by huyg on 2017/10/23.
 */

public class AudioManager {
    private AudioPlayer mPlayer;
    private int mode;
    private long position;
    private String lastSource = "";
    private int count;


    @Inject
    public AudioManager() {
        mPlayer = new AudioPlayer(HTApplication.getInstance());
        mPlayer.setOnPlayListener(mListener);
        mode = android.media.AudioManager.STREAM_MUSIC;
    }


    public int getMode() {
        return mode;
    }

    /**
     * 设置播放模式
     * AudioManager.STREAM_VOICE_CALL 表示使用听筒模式
     * AudioManager.STREAM_MUSIC 表示使用扬声器模式
     *
     * @param mode
     */
    public void setMode(int mode) {
        this.mode = mode;
    }

    /**
     * 设置资源
     *
     * @param source
     */
    public void setSource(String source) {
        if (mPlayer != null) {
            mPlayer.setDataSource(source);
        }
    }

    /**
     * 播放
     */
    public void start(String source) {
        if (!lastSource.equals(source)) {
            mPlayer.setDataSource(source);
            lastSource = source;
            mPlayer.start(mode);
        } else {
            resume();
        }
    }

    public void start(String source, int position) {
        mPlayer.stop();
        mPlayer.setDataSource(source);
        lastSource = source;
        mPlayer.start(mode);
        mPlayer.seekTo(position);
    }

    /**
     * 暂停
     */
    public void pause() {
        if (mPlayer != null) {
            position = mPlayer.getCurrentPosition();
            mPlayer.stop();
        }
    }

    /**
     * 停止
     */
    public void stop() {
        if (mPlayer != null) {
            mPlayer.stop();
            position = 0;
            lastSource = "";
            count = 0;
        }
    }

    /**
     * 继续播放
     */
    public void resume() {
        if (mPlayer != null) {
            mPlayer.start(mode);
            mPlayer.seekTo((int) position);
        }
    }

    public boolean getState() {
        if (mPlayer != null) {
            return mPlayer.isPlaying();
        }
        return false;
    }


    /**
     * 播放音频监听
     */
    OnPlayListener mListener = new OnPlayListener() {

        // 音频转码解码完成，会马上开始播放了
        public void onPrepared() {
            EventBus.getDefault().post(new AudioPlayStatusEvent(1));
        }

        // 播放结束
        public void onCompletion() {
            EventBus.getDefault().post(new AudioPlayStatusEvent(0));
            position = 0;
            count = 0;
        }

        // 播放被中断了
        public void onInterrupt() {
            //EventBus.getDefault().post(new AudioPlayStatusEvent(-1));
        }

        // 播放过程中出错。参数为出错原因描述
        public void onError(String error) {
            EventBus.getDefault().post(new AudioPlayStatusEvent(-1));
        }

        // 播放进度报告，每隔 500ms 会回调一次，告诉当前进度。 参数为当前进度，单位为毫秒，可用于更新 UI
        public void onPlaying(long position) {
            if (count % 2 == 1) {
                AudioManager.this.position = position;
                HiStudentLog.i("position" + position);
                double p = position / 1000.0;
                EventBus.getDefault().post(new AudioPlayEvent((int) Math.ceil(p) * 1000));
            }
            count++;

        }
    };


}
