package com.histudent.jwsoft.histudent.body.mine.activity;

import android.content.Intent;

import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.CommentActivity;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;

/**
 * Created by liuguiyu-pc on 2017/5/22.
 * 含动态详情的BaseActivity
 */

public abstract class ActionBaseActivity extends BaseActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommentActivity.REQUESTCODE && resultCode == CommentActivity.RESULTCODE) {
            ActionListBean bean = (ActionListBean) data.getSerializableExtra("data");
            int position = data.getIntExtra("position", -1);
            reFreshActionList(position, bean);
        } else if (requestCode == CommentActivity.REQUESTCODE && resultCode == CommentActivity.DELET) {
            int position = data.getIntExtra("position", -1);
            reMoveAction(position);
        }
    }

    /**
     * 从动态详情中返回时，进行刷新操作。
     *
     * @param position
     * @param bean
     */
    protected abstract void reFreshActionList(int position, ActionListBean bean);

    /**
     * 从动态详情中返回时,删除动态。
     *
     * @param position
     */
    protected abstract void reMoveAction(int position);

}
