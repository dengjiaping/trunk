package com.histudent.jwsoft.histudent.body.hiworld.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gzsll.jsbridge.Logger;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.activity.RichEditorSelectColorActivity;
import com.histudent.jwsoft.histudent.body.hiworld.bean.SimpleUserModel;
import com.histudent.jwsoft.histudent.body.mine.model.UserClassListModel;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.activity.SelecteClassmatesActiviy;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.helper.PictureTailorHelper;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.commen.listener.DialogButtonListener;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.photo.utils.ImageUtils;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.commen.utils.DataUtils;
import com.histudent.jwsoft.histudent.commen.utils.HiStudentHttpUtils;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.comment2.utils.LogParameterModel;
import com.histudent.jwsoft.histudent.comment2.utils.SeletClassMateEnum;
import com.histudent.jwsoft.histudent.comment2.utils.ViewUtils;
import com.histudent.jwsoft.histudent.constant.TransferKeys;
import com.netease.nim.uikit.common.util.string.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.richeditor.RichEditor;


/**
 * Created by liuguiyu-pc on 2016/11/14.
 * 发布日志界面
 */
public class LogActivity extends BaseActivity implements View.OnClickListener {

    private ArrayList<String> all_path;//压缩前的图片保存集合
    public ArrayList<UserClassListModel> classListModels;
    private PictureTailorHelper clippHelper;
    private ArrayList<SimpleUserModel> atList;//at的用户集合
    private int cursorPosition;
    private LogParameterModel parameterModel;//用于保存发送日志所需参数
    private AddressInforBean addressModel;
    private List<SimpleUserModel> allAtList;
    @BindView(R.id.title_left_image)
    IconView title_image_left;
    @BindView(R.id.title_right_text)
    TextView title_text_right;
    @BindView(R.id.et_log_title)
    EditText mLogTitle;
    @BindView(R.id.editor)
    RichEditor mEditor;
    @BindView(R.id.top_margin_line)
    View mMarginLine;
    @BindView(R.id.tv_input)
    TextView mInput;
    @BindView(R.id.ll_root_layout)
    LinearLayout mRootLayout;
    @BindView(R.id.tl_rich_editor_layout)
    TabLayout mRichEditorTabLayout;

    @BindView(R.id.action_control_keyboard)
    ImageView mIvKeyBoard;

    private boolean mIsShowSysKeyboard;
    /**
     * 每次进入编辑页面随机获取一个UUID
     * 主要用于上传图片
     * paramKey:batchNumber
     */
    private UUID mUUID;
    //软件盘弹起后所占高度阀值
    private int mKeyHeight = 0;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, LogActivity.class);
        activity.startActivityForResult(intent, 500);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_issue_log;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initRichEditor();
        ((TextView) findViewById(R.id.title_middle_text)).setText(R.string.write_log);
        title_image_left.setVisibility(View.VISIBLE);
        title_image_left.setText(R.string.icon_close);
        title_image_left.setTextSize(15);
        findViewById(R.id.title_right).setEnabled(false);
        title_text_right.setText(R.string.do_next);
        title_text_right.setTextColor(getResources().getColor(R.color.text_gray_h2));
        loadListener();
    }

    private void loadListener() {
        mLogTitle.addTextChangedListener(mInputTextWatcher);
        mLogTitle.setOnFocusChangeListener(mOnFocusChangeListener);
    }


    @Override
    public void doAction() {
        super.doAction();
        mKeyHeight = SystemUtil.getScreenHeight_() / 3;
        mUUID = UUID.randomUUID();
        clippHelper = PictureTailorHelper.getInstance();
        all_path = new ArrayList<>();
        atList = new ArrayList<>();
        allAtList = new ArrayList<>();
        classListModels = new ArrayList<>();
        parameterModel = new LogParameterModel();
        getAddressInfor();
    }

    private List<Integer> mRichEditorList;
    private List<View> mViews;

    private void initRichEditor() {
        buildRichEditorIcon();
        mEditor.setPadding(10, 10, 10, 10);
        mEditor.setPlaceholder(getString(R.string.content));
        mEditor.setOnTextChangeListener((String text) ->
                ViewUtils.changeTitleRightClickable(LogActivity.this,
                        !StringUtil.isEmpty(mEditor.getHtml()) && !StringUtil.isEmpty(mLogTitle.getText().toString()) ? true : false)
        );

        mRichEditorTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mRichEditorTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (int i = 0; i < mRichEditorList.size(); i++) {
            Drawable drawable = ContextCompat.getDrawable(LogActivity.this, mRichEditorList.get(i));
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
            SpannableString spannableString = new SpannableString(" ");
            spannableString.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mRichEditorTabLayout.addTab(mRichEditorTabLayout.newTab().setText(spannableString));
        }
        mRichEditorTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loadOnEditorItemClickListener(mRichEditorTabLayout.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                loadOnEditorItemClickListener(mRichEditorTabLayout.getSelectedTabPosition());
            }
        });

        mRichEditorTabLayout.getTabAt(10).select();
        mIvKeyBoard.setOnClickListener(new View.OnClickListener() {
            private boolean isChange;

            @Override
            public void onClick(View view) {
                showSysKeyboard();
                isChange = !isChange;
                if (isChange) {
                    mIvKeyBoard.setImageResource(R.mipmap.icon_keyboard_down);
                } else {
                    mIvKeyBoard.setImageResource(R.mipmap.icon_keyboard_up);
                }
            }
        });
    }

    private void loadOnEditorItemClickListener(int position) {
        switch (position) {
            case 0:
                checkTakePhotoPermission(() -> clippHelper.selectPictures(LogActivity.this, all_path, 9, null));
                break;
            case 1:
                SelecteClassmatesActiviy.start(this, new ArrayList<>(), 222, SeletClassMateEnum.AT);
                break;
            case 2:
                mEditor.setBold();
                break;
            case 3:
                mEditor.setItalic();
                break;
            case 4:
                mEditor.setStrikeThrough();
                break;
            case 5:
                mEditor.setUnderline();
                break;
            case 6:
                mEditor.setHeading(1);
                break;
            case 7:
                mEditor.setHeading(3);
                break;
            case 8:
                mEditor.setHeading(5);
                break;
            case 9:
                Intent intent = new Intent(this, RichEditorSelectColorActivity.class);
                this.startActivityForResult(intent, TransferKeys.ConstantNum.NUM_1001);
                break;
            case 10:
                mEditor.setAlignLeft();
                break;
            case 11:
                mEditor.setAlignCenter();
                break;
            case 12:
                mEditor.setAlignRight();
                break;
            default:
                break;
        }
    }

    private void buildRichEditorIcon() {
        mRichEditorList = new ArrayList<>();
        mViews = new ArrayList<>();
        View view;
        for (int i = 0; i < 13; i++) {
            view = new View(this);
            mViews.add(view);
            switch (i) {
                case 0:
                    mRichEditorList.add(R.mipmap.action_insert_image);
                    break;
                case 1:
                    mRichEditorList.add(R.mipmap.action_at);
                    break;
                case 2:
                    mRichEditorList.add(R.mipmap.action_bold);
                    break;
                case 3:
                    mRichEditorList.add(R.mipmap.action_italic);
                    break;
                case 4:
                    mRichEditorList.add(R.mipmap.action_strikethrough);
                    break;
                case 5:
                    mRichEditorList.add(R.mipmap.action_underline);
                    break;
                case 6:
                    mRichEditorList.add(R.mipmap.text_big);
                    break;
                case 7:
                    mRichEditorList.add(R.mipmap.text_middle);
                    break;
                case 8:
                    mRichEditorList.add(R.mipmap.text_small);
                    break;
                case 9:
                    mRichEditorList.add(R.mipmap.action_txt_color);
                    break;
                case 10:
                    mRichEditorList.add(R.mipmap.action_align_left);
                    break;
                case 11:
                    mRichEditorList.add(R.mipmap.action_align_center);
                    break;
                case 12:
                    mRichEditorList.add(R.mipmap.action_align_right);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //用于监听系统键盘
        mRootLayout.addOnLayoutChangeListener(mOnLayoutChangeListener);
    }

    //获取地址信息
    private void getAddressInfor() {
        addressModel = HiWorldCache.getUserLocationInfor();
        if (addressModel == null) {
            addressModel = new AddressInforBean();
        }
        parameterModel.setLatitude(addressModel.getLatitude());
        parameterModel.setLongitude(addressModel.getLongitude());
        parameterModel.setLocation(addressModel.getCity() + addressModel.getAreaStr());
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_left:
                showBackNotice();
                break;
            case R.id.title_right:
                doNextAction();
                break;
            default:
                break;
        }
    }

    private void doNextAction() {
//                parameterModel.setContent(getContent());
        HiStudentLog.e(mEditor.getHtml());
        parameterModel.setContent(mEditor.getHtml());
        parameterModel.setTitle(mLogTitle.getText().toString());
        parameterModel.setFileList(all_path);
        parameterModel.setAtUserList(DataUtils.deleteUser(mEditor.getHtml(), allAtList));
        LogNextActivity.start(this, parameterModel, 666);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PictureTailorHelper.PHOTO_REQUEST_TAKEPHOTO && resultCode == -1) {
            //判断上一个childView,如果它是空edit则删除它
//            int childViewNums = log_content_body.getChildCount();
//            View view = log_content_body.getChildAt(childViewNums - 1);
//            if (view instanceof EditText) {
//                if (TextUtils.isEmpty(((EditText) view).getText().toString())) {
//                    log_content_body.removeViewAt(childViewNums - 1);
//                }
//            }

            //添加图片
//            addPicture(clippHelper.photo_path.getAbsolutePath());

        } else if (requestCode == PictureTailorHelper.PHOTO_REQUEST_GALLERYS && resultCode == 200) {
            //照片选择后   返回过来的图片位置
            List<String> urls = data.getStringArrayListExtra("return");
            doUploadSelectImage(urls);
        } else if (resultCode == 200 && requestCode == 100) {
            if (data != null && data.getSerializableExtra("classList") != null) {
                classListModels.clear();
                classListModels.addAll(((ArrayList<UserClassListModel>) data.getSerializableExtra("classList")));
                Log.e("Size", classListModels.size() + "");
            }

        } else if (resultCode == 200 && requestCode == 222) {
            //at返回
            if (data != null) {
                atList.clear();
                atList.addAll((ArrayList<SimpleUserModel>) data.getSerializableExtra("userModel"));
                allAtList.addAll(atList);
//                DataUtils.setAtText(et_content, cursorPosition, DataUtils.getAtUserNameStr(atList));
            }
            String atUserNameStr = DataUtils.getAtUserNameStr(atList);
            mEditor.insertText(atUserNameStr);
        } else if (resultCode == 200 && requestCode == 666) {
            //日志设置同步班级和权限后返回
            if (data != null) {
                int privacyStatus = data.getIntExtra("privacyStatus", -1);
                if (privacyStatus != -1) {
                    parameterModel.setPrivacyStatus(privacyStatus);

                }

                //设置同步班级
                if (data.getSerializableExtra("classIds") != null) {
                    classListModels.clear();
                    classListModels.addAll(((ArrayList) data.getSerializableExtra("classIds")));
                    parameterModel.setClassIds(((ArrayList) data.getSerializableExtra("classIds")));
                }
            }
        } else if (requestCode == TransferKeys.ConstantNum.NUM_1001 && resultCode == TransferKeys.ConstantNum.NUM_1000) {
            //文本编辑框颜色选择返回
            mEditor.setTextColor(data.getIntExtra(TransferKeys.RICH_EDITOR_TEXT_COLOR, 0));
        } else if (resultCode == 400 && requestCode == 666) {//日志发布成功,界面销毁
            //选择草稿返回
            setResult(0);
            finish();
        }
    }

    /**
     * 当选择的图片上传成功后
     * 把其放置于富文本中
     *
     * @param listPath
     */
    private void doUploadSelectImage(List<String> listPath) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                //对原路径图片进行压缩
                List<String> afterCompressPath = ImageUtils.comPressBitmaps(LogActivity.this, listPath, 80);
                Map<String, Object> hashMap = new HashMap<>();
                hashMap.put("batchNumber", mUUID);
                HiStudentHttpUtils.postImageByOKHttp(LogActivity.this, afterCompressPath, hashMap, HistudentUrl.uploadAttachment, new HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            JSONArray photoList = jsonObject.getJSONArray("photoList");
                            for (int i = 0; i < photoList.length(); i++) {
                                mEditor.insertImage(photoList.getString(i), "image" + i);
                            }
                            //对压缩后的照片进行删除
                            ImageUtils.deleteCompressFile();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg) {

                    }
                });
            }
        }.start();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            showBackNotice();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showBackNotice() {

        if (!StringUtil.isEmpty(mEditor.getHtml()) || !StringUtil.isEmpty(mLogTitle.getText().toString())) {
            ReminderHelper.getIntentce().showDialog(LogActivity.this, "提示", "日志还没发布，确定不发布吗？", "确定", new DialogButtonListener() {
                @Override
                public void setOnDialogButtonListener() {
                    finish();
                }
            }, "取消", new DialogButtonListener() {
                @Override
                public void setOnDialogButtonListener() {

                }
            });
        } else {
            finish();
        }
    }

    private TextWatcher mInputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            ViewUtils.changeTitleRightClickable(LogActivity.this,
                    !StringUtil.isEmpty(mEditor.getHtml()) && !StringUtil.isEmpty(mLogTitle.getText().toString()) ? true : false);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private View.OnFocusChangeListener mOnFocusChangeListener = (View view, boolean isFocus) -> {
        if (isFocus) {
            mRichEditorTabLayout.setVisibility(View.GONE);
            mIvKeyBoard.setVisibility(View.GONE);
            mMarginLine.setVisibility(View.GONE);
        } else {
            if (mIsShowSysKeyboard) {
                mMarginLine.setVisibility(View.VISIBLE);
                mRichEditorTabLayout.setVisibility(View.VISIBLE);
                mIvKeyBoard.setVisibility(View.VISIBLE);
            } else {
                mMarginLine.setVisibility(View.GONE);
                mRichEditorTabLayout.setVisibility(View.GONE);
                mIvKeyBoard.setVisibility(View.GONE);
            }
        }
    };


    /**
     * 用于监听系统键盘显示及隐藏
     */
    private View.OnLayoutChangeListener mOnLayoutChangeListener = (View v, int left, int top, int right,
                                                                   int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) -> {
        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > mKeyHeight)) {
            mIsShowSysKeyboard = true;
            mIvKeyBoard.setImageResource(R.mipmap.icon_keyboard_down);
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > mKeyHeight)) {
            mIsShowSysKeyboard = false;
            mIvKeyBoard.setImageResource(R.mipmap.icon_keyboard_up);
        }
    };


    private InputMethodManager inputMethodManager;

    /**
     * 调用android系统键盘
     */
    private void showSysKeyboard() {
        if (mEditor != null) {
            mEditor.setFocusableInTouchMode(true);
            mEditor.requestFocus();
            if (inputMethodManager == null)
                inputMethodManager = (InputMethodManager) mEditor.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);  //得到InputMethodManager的实例
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS); //关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }
    }


}
