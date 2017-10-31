package com.histudent.jwsoft.histudent.commen.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.adapter.AllItemAdapter;
import com.histudent.jwsoft.histudent.commen.bean.ActionListBean;
import com.histudent.jwsoft.histudent.commen.enums.ItemType;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.info.persioninfo.PersionHelper;

import static com.histudent.jwsoft.histudent.commen.enums.ItemType.HI_FRIEND_ACTION;

/**
 * Created by liuguiyu-pc on 2016/8/28.
 * 动态里面更多的PopupWindow
 */
public class ActionMorePopWindow extends PopupWindow implements View.OnClickListener {

    private RelativeLayout relative_care, relative_cancel_care, relative_report, relative_shield;
    private View mMenuView;
    private BaseActivity context;
    private ActionListBean bean_;
    private TextView particularly_see, see, action;
    private boolean isEspecially_attention, isAttention, isShield;
    private ImageView iv_care, iv_cancel_care, iv_shield;
    private AllItemAdapter.AdapterCallBack callBack;
    private ItemType type;

    public ActionMorePopWindow(BaseActivity context, ActionListBean bean_, ItemType type, View.OnClickListener itemsOnClick, AllItemAdapter.AdapterCallBack callBack) {
        super(context);
        this.context = context;
        this.bean_ = bean_;
        this.type = type;
        this.callBack = callBack;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_window, null);

        relative_cancel_care = ((RelativeLayout) mMenuView.findViewById(R.id.relative_cancel_attention));
        relative_care = ((RelativeLayout) mMenuView.findViewById(R.id.relative_care));
        relative_report = ((RelativeLayout) mMenuView.findViewById(R.id.relative_report));
        relative_shield = ((RelativeLayout) mMenuView.findViewById(R.id.relative_shield));

        iv_care = (ImageView) mMenuView.findViewById(R.id.iv_care);
        iv_cancel_care = (ImageView) mMenuView.findViewById(R.id.iv_cancel_care);
        iv_shield = (ImageView) mMenuView.findViewById(R.id.iv_shield);

        particularly_see = ((TextView) mMenuView.findViewById(R.id.particularly_see));
        see = ((TextView) mMenuView.findViewById(R.id.see));
        action = ((TextView) mMenuView.findViewById(R.id.action));

        isEspecially_attention = bean_.isIsSpecialFollow();
        isAttention = bean_.isIsCare();
        isShield = bean_.isIsShield();

        updateView();

        //设置按钮监听
        relative_cancel_care.setOnClickListener(this);
        relative_care.setOnClickListener(this);
        relative_report.setOnClickListener(itemsOnClick);
        relative_shield.setOnClickListener(this);

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.hiworld_fram_gb));
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        mMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            //取消关注/关注
            case R.id.relative_cancel_attention:

                PersionHelper.getInstance().attention(context, isAttention ? 0 : 1, bean_.getUserId(), new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        dismiss();
                        if (type == HI_FRIEND_ACTION) {
                            if (callBack != null)
                                callBack.refrensh();
                        } else {
                            isAttention = !isAttention;
                            bean_.setIsCare(isAttention);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                    }
                });
                break;
            //特别关注/取消特别关注
            case R.id.relative_care:

                if (!isAttention) {
                    Toast.makeText(context, "请先关注该用户", Toast.LENGTH_SHORT).show();
                    return;
                }

                final boolean flag = bean_.isIsSpecialFollow();
                PersionHelper.getInstance().attention(context, isEspecially_attention ? 3 : 2, bean_.getUserId(), new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        dismiss();
                        isEspecially_attention = !isEspecially_attention;
                        bean_.setIsSpecialFollow(!flag);
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
                break;
            //屏蔽动态/取消屏蔽动态
            case R.id.relative_shield:
                PersionHelper.getInstance().attention(context, isShield ? 7 : 6, bean_.getUserId(), new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        dismiss();
                        if (callBack != null)
                            callBack.refrensh();
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
                break;
        }
    }

    public void updateView() {

        particularly_see.setText(isEspecially_attention ? "取消特别关注" : "特别关注");
        iv_care.setImageResource(isEspecially_attention ? R.mipmap.action_realy_especially_attention : R.mipmap.action_no_especially_attention);

        see.setText(isAttention ? "取消关注" : "关注");
        iv_cancel_care.setImageResource(isAttention ? R.mipmap.action_cancel_attention : R.mipmap.action_realy_attention);

        action.setText(isShield ? "取消屏蔽动态" : "屏蔽动态");
        iv_shield.setImageResource(isShield ? R.mipmap.action_realy_shield : R.mipmap.action_no_shield);
    }
}
