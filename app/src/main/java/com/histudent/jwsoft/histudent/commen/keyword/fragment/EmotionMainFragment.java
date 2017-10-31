package com.histudent.jwsoft.histudent.commen.keyword.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.keyword.adapter.HorizontalRecyclerviewAdapter;
import com.histudent.jwsoft.histudent.commen.keyword.adapter.NoHorizontalScrollerVPAdapter;
import com.histudent.jwsoft.histudent.commen.keyword.emotionkeyboardview.EmotionKeyboard;
import com.histudent.jwsoft.histudent.commen.keyword.emotionkeyboardview.NoHorizontalScrollerViewPager;
import com.histudent.jwsoft.histudent.commen.keyword.model.ImageModel;
import com.histudent.jwsoft.histudent.commen.keyword.utils.EmotionUtils;
import com.histudent.jwsoft.histudent.commen.keyword.utils.GlobalOnItemClickManagerUtils;
import com.histudent.jwsoft.histudent.commen.keyword.utils.SharedPreferencedUtils;
import com.histudent.jwsoft.histudent.commen.listener.ParserCallBack;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zejian
 * Time  16/1/6 下午5:26
 * Email shinezejian@163.com
 * Description:表情主界面
 */
public class EmotionMainFragment extends BaseFragment {

    //是否绑定当前Bar的编辑框的flag
    public static final String BIND_TO_EDITTEXT = "bind_to_edittext";
    //是否隐藏bar上的编辑框和发生按钮
    public static final String HIDE_BAR_EDITTEXT_AND_BTN = "hide bar's editText and btn";
    //切换键盘的按钮
    public static final String HIDE_BAR_EMTION = "bind_to_emtion_btn";
    //当前被选中底部tab
    private static final String CURRENT_POSITION_FLAG = "CURRENT_POSITION_FLAG";
    private int CurrentPosition = 0;
    //底部水平tab
    private RecyclerView recyclerview_horizontal;
    private HorizontalRecyclerviewAdapter horizontalRecyclerviewAdapter;
    //表情面板
    private EmotionKeyboard mEmotionKeyboard;

    private View exchangeView;//切换表情阿牛

    private EditText bar_edit_text;
    private ImageView bar_image_add_btn;
    private TextView bar_btn_send;
    private CheckBox bar_emotion_btn;
    private LinearLayout rl_editbar_bg, bar_emotion_layout;
//    private LinearLayout linearLayout;

    //需要绑定的内容view
    private View contentView_;
    private EditText contentView;

    private View view_edit;//自带的编辑栏
    private GlobalOnItemClickManagerUtils globalOnItemClickManager;

    //不可横向滚动的ViewPager
    private NoHorizontalScrollerViewPager viewPager;

    //是否绑定当前Bar的编辑框,默认true,即绑定。
    //false,则表示绑定contentView,此时外部提供的contentView必定也是EditText
    private boolean isBindToBarEditText = true;
    private boolean isBindToEmotion = false;

    //是否隐藏bar上的编辑框和发生按钮,默认不隐藏
    private boolean isHidenBarEditTextAndBtn = false;
    private int emtion_btn_id;

    private List<Fragment> fragments = new ArrayList<>();

    private ParserCallBack callBack;

    /**
     * 创建与Fragment对象关联的View视图时调用
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_emotion, container, false);
        isHidenBarEditTextAndBtn = args.getBoolean(EmotionMainFragment.HIDE_BAR_EDITTEXT_AND_BTN);
        isBindToBarEditText = args.getBoolean(EmotionMainFragment.BIND_TO_EDITTEXT);
        initView(rootView);
        mEmotionKeyboard = EmotionKeyboard.with(getActivity())
                .setEmotionView(rootView.findViewById(R.id.ll_emotion_layout))//绑定表情面板
                .bindToContent(contentView_)//绑定内容view
                .bindToEditText(!isBindToBarEditText ? contentView : bar_edit_text)//判断绑定那种EditView
                .bindToEmotionButton(exchangeView == null ? rootView.findViewById(R.id.bar_emotion_btn) : exchangeView)//绑定表情按钮
                .build();

        initListener();
        initDatas();
        //创建全局监听
        globalOnItemClickManager = GlobalOnItemClickManagerUtils.getInstance(getActivity());

        if (isBindToBarEditText) {
            //绑定当前Bar的编辑框
            globalOnItemClickManager.attachToEditText(bar_edit_text);
            bar_edit_text.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (StringUtil.isEmpty(editable.toString())) {
                        bar_btn_send.setBackgroundResource(R.drawable.gray_button_bg);
                        bar_btn_send.setClickable(false);
                    } else {
                        bar_btn_send.setBackgroundResource(R.drawable.green_button_bg);
                        bar_btn_send.setClickable(true);
                    }

                }
            });

        } else {
            // false,则表示绑定contentView,此时外部提供的contentView必定也是EditText
            globalOnItemClickManager.attachToEditText(contentView);
            mEmotionKeyboard.bindToEditText(contentView);
        }
        return rootView;

    }

    public void setEmotionIsVisiable(boolean isShow) {
        if (isShow) {
            getActivity().getSupportFragmentManager().beginTransaction().show(this).commit();
        } else {
            getActivity().getSupportFragmentManager().beginTransaction().hide(this).commit();
        }
    }

    public void hitEmotion() {
        mEmotionKeyboard.hideEmotionLayout(true);
    }

    /**
     * 绑定布局，用于防闪跳
     *
     * @param contentView_
     * @return
     */
    public void bindToContentView(View contentView_) {
        this.contentView_ = contentView_;
    }

    /**
     * 绑定edit输入框
     *
     * @param contentView
     * @return
     */
    public void bindToEditText(EditText contentView) {
        this.contentView = contentView;
        if (mEmotionKeyboard != null) {
            globalOnItemClickManager.attachToEditText(contentView);
            mEmotionKeyboard.bindToEditText(contentView);
        }
    }

    /**
     * 传入头部View
     *
     * @param headItemView
     * @return
     */
    public void setExchangeButton(View headItemView) {
        this.exchangeView = headItemView;
    }

    /**
     * 初始化view控件
     */
    protected void initView(View rootView) {

        viewPager = (NoHorizontalScrollerViewPager) rootView.findViewById(R.id.vp_emotionview_layout);
        recyclerview_horizontal = (RecyclerView) rootView.findViewById(R.id.recyclerview_horizontal);
        bar_edit_text = (EditText) rootView.findViewById(R.id.bar_edit_text);
        bar_image_add_btn = (ImageView) rootView.findViewById(R.id.bar_image_add_btn);
        bar_btn_send = (TextView) rootView.findViewById(R.id.bar_btn_send);
        rl_editbar_bg = (LinearLayout) rootView.findViewById(R.id.rl_editbar_bg);
        bar_emotion_layout = (LinearLayout) rootView.findViewById(R.id.bar_emotion_layout);
        bar_emotion_btn = (CheckBox) rootView.findViewById(R.id.bar_emotion_btn);
        view_edit = rootView.findViewById(R.id.include_emotion_view);

        if (isHidenBarEditTextAndBtn) {//隐藏
            bar_emotion_layout.setVisibility(View.GONE);
            bar_edit_text.setVisibility(View.GONE);
            bar_image_add_btn.setVisibility(View.GONE);
            bar_btn_send.setVisibility(View.GONE);
            bar_emotion_btn.setVisibility(View.GONE);
            rl_editbar_bg.setBackgroundResource(R.color.bg_edittext_color);
            view_edit.setVisibility(View.GONE);
        } else {
            bar_emotion_layout.setVisibility(View.VISIBLE);
            bar_edit_text.setVisibility(View.VISIBLE);
            bar_image_add_btn.setVisibility(View.GONE);
            bar_btn_send.setVisibility(View.VISIBLE);
            bar_emotion_btn.setVisibility(View.VISIBLE);
            rl_editbar_bg.setBackgroundResource(R.drawable.shape_bg_reply_edittext);
            view_edit.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化监听器
     */
    protected void initListener() {

        bar_btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mEmotionKeyboard.hideAll();

                if (callBack != null) {
                    callBack.parser(bar_edit_text.getText().toString());
                    bar_edit_text.setText("");
                    bar_edit_text.setHint("");
                }
            }
        });

    }

    public void setEditText(String content) {
        bar_edit_text.setHint(content);
        bar_edit_text.setFocusable(true);
        bar_edit_text.setFocusableInTouchMode(true);
        bar_edit_text.requestFocus();
    }

    public void setEditTextFouces() {
        bar_edit_text.setFocusable(true);
        bar_edit_text.setFocusableInTouchMode(true);
        bar_edit_text.requestFocus();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    public void setCallBack(ParserCallBack callBack) {
        this.callBack = callBack;
    }

    /**
     * 数据操作,这里是测试数据，请自行更换数据
     */
    protected void initDatas() {
        replaceFragment();
        List<ImageModel> list = new ArrayList<>();
        for (int i = 0; i < fragments.size(); i++) {
            if (i == 0) {
                ImageModel model1 = new ImageModel();
                model1.icon = getResources().getDrawable(R.drawable.face);
                model1.flag = "经典笑脸";
                model1.isSelected = true;
                list.add(model1);
            } else {
                ImageModel model = new ImageModel();
                model.icon = getResources().getDrawable(R.mipmap.ic_plus);
                model.flag = "其他笑脸" + i;
                model.isSelected = false;
//                list.add(model);
            }
        }

        //记录底部默认选中第一个
        CurrentPosition = 0;
        SharedPreferencedUtils.setInteger(getActivity(), CURRENT_POSITION_FLAG, CurrentPosition);

        //底部tab
        horizontalRecyclerviewAdapter = new HorizontalRecyclerviewAdapter(getActivity(), list);
        recyclerview_horizontal.setHasFixedSize(true);//使RecyclerView保持固定的大小,这样会提高RecyclerView的性能
        recyclerview_horizontal.setAdapter(horizontalRecyclerviewAdapter);
        recyclerview_horizontal.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false));
        //初始化recyclerview_horizontal监听器
        horizontalRecyclerviewAdapter.setOnClickItemListener(new HorizontalRecyclerviewAdapter.OnClickItemListener() {
            @Override
            public void onItemClick(View view, int position, List<ImageModel> datas) {
                //获取先前被点击tab
                int oldPosition = SharedPreferencedUtils.getInteger(getActivity(), CURRENT_POSITION_FLAG, 0);
                //修改背景颜色的标记
                datas.get(oldPosition).isSelected = false;
                //记录当前被选中tab下标
                CurrentPosition = position;
                datas.get(CurrentPosition).isSelected = true;
                SharedPreferencedUtils.setInteger(getActivity(), CURRENT_POSITION_FLAG, CurrentPosition);
                //通知更新，这里我们选择性更新就行了
                horizontalRecyclerviewAdapter.notifyItemChanged(oldPosition);
                horizontalRecyclerviewAdapter.notifyItemChanged(CurrentPosition);
                //viewpager界面切换
                viewPager.setCurrentItem(position, false);
            }

            @Override
            public void onItemLongClick(View view, int position, List<ImageModel> datas) {
            }
        });


    }

    private void replaceFragment() {
        //创建fragment的工厂类
        FragmentFactory factory = FragmentFactory.getSingleFactoryInstance();
        //创建修改实例
        EmotiomComplateFragment f1 = (EmotiomComplateFragment) factory.getFragment(EmotionUtils.EMOTION_CLASSIC_TYPE);
        fragments.add(f1);
        Bundle b = null;
        for (int i = 0; i < 7; i++) {
            b = new Bundle();
            b.putString("Interge", "Fragment-" + i);
            Fragment1 fg = Fragment1.newInstance(Fragment1.class, b);
            fragments.add(fg);
        }

        NoHorizontalScrollerVPAdapter adapter = new NoHorizontalScrollerVPAdapter(getActivity().getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }

    /**
     * 是否拦截返回键操作，如果此时表情布局未隐藏，先隐藏表情布局
     *
     * @return true则隐藏表情布局，拦截返回键操作
     * false 则不拦截返回键操作
     */
    public boolean isInterceptBackPress() {
        return mEmotionKeyboard.interceptBackPress();
    }

}


