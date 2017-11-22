package com.histudent.jwsoft.histudent.body.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.account.login.model.CurrentUserInfoModel;
import com.histudent.jwsoft.histudent.body.find.bean.CodeBean;
import com.histudent.jwsoft.histudent.body.find.event.ActivityFinishEvent;
import com.histudent.jwsoft.histudent.body.find.helper.GroupHelper;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.cache.HiWorldCache;
import com.histudent.jwsoft.histudent.commen.helper.GetCodeHelper;
import com.histudent.jwsoft.histudent.commen.listener.HttpRequestCallBack;
import com.histudent.jwsoft.histudent.commen.view.IconView;
import com.histudent.jwsoft.histudent.comment2.activity.CommenSelectActivity;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.comment2.utils.EditGroupOrClassTypeEnum;
import com.histudent.jwsoft.histudent.model.constant.ConstantUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import info.hoang8f.android.segmented.SegmentedGroup;


public class CreateCommunityActivity extends BaseActivity {

    private static final String TAG = CreateCommunityActivity.class.getSimpleName();
    @BindView(R.id.title_left_image)
    IconView titleLeftImage;
    @BindView(R.id.title_left)
    LinearLayout titleLeft;
    @BindView(R.id.title_right_text)
    TextView titleRightText;
    @BindView(R.id.title_right)
    LinearLayout titleRight;
    @BindView(R.id.title_middle_text)
    TextView titleMiddleText;
    @BindView(R.id.type_topic)
    LinearLayout typeTopic;
    @BindView(R.id.community_rdaiobutton_left)
    RadioButton communityRdaiobuttonLeft;
    @BindView(R.id.community_rdaiobutton_middle)
    RadioButton communityRdaiobuttonMiddle;
    @BindView(R.id.community_rdaiobutton_right)
    RadioButton communityRdaiobuttonRight;
    @BindView(R.id.community_tv_one)
    RelativeLayout communityTvOne;
    @BindView(R.id.community_et_two)
    EditText communityEtTwo;
    @BindView(R.id.community_tv_two)
    LinearLayout communityTvTwo;
    @BindView(R.id.community_et_three)
    EditText communityEtThree;
    @BindView(R.id.community_tv_three)
    LinearLayout communityTvThree;
    @BindView(R.id.community_et_four)
    EditText communityEtFour;
    @BindView(R.id.community_tv_four)
    LinearLayout communityTvFour;
    @BindView(R.id.community_et_five)
    EditText communityEtFive;
    @BindView(R.id.community_btn_code)
    TextView communityBtnCode;
    @BindView(R.id.community_tv_clik)
    TextView communityTvClik;
    @BindView(R.id.community_tv_withLine)
    TextView communityTvWithLine;
    @BindView(R.id.community_radioGroup)
    SegmentedGroup mRadioGroup;

    @BindView(R.id.community_tv_person)
    TextView mTextViewperson;
    @BindView(R.id.community_tv_name)
    TextView mTextView;
    @BindView(R.id.input_tv_school_name)
    TextView mSchool_name;

    @BindView(R.id.community_tv_company_one)
    RelativeLayout mRelativeLayout;
    @BindView(R.id.community_voice_code)
    RelativeLayout mRelativeLayoutVoice;

    @BindView(R.id.input_tv_company_name)
    EditText mEditText;

    private String school_name, person_name, phone, code, business_number;
    private int position = R.id.community_rdaiobutton_left;
    private AddressInforBean addressInforBean;
    private CurrentUserInfoModel loginUserInfo;

    private static final int COMMUNITY_CODE = 100;

    @Override
    public int setViewLayout() {
        return R.layout.activity_create_community;
    }

    public static void start(Activity context) {
        Intent intent = new Intent(context, CreateCommunityActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        titleMiddleText.setText(R.string.community_create);
        titleRightText.setText(R.string.community_next);
        titleRightText.setTextColor(getResources().getColor(R.color.click_false));
        titleRightText.setEnabled(false);
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        initUserInfo();
    }

    private void initUserInfo() {
        addressInforBean = HiWorldCache.getUserLocationInfor();
        loginUserInfo = HiCache.getInstance().getLoginUserInfo();
        communityEtThree.setText(loginUserInfo.getRealName());
        communityEtFour.setText(loginUserInfo.getMobile());
        person_name = loginUserInfo.getRealName();
        phone = loginUserInfo.getMobile();
        initListeners();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSubscribe(String num) {
        if ("0".equals(num)) {
            communityBtnCode.setEnabled(true);
            communityBtnCode.setBackgroundResource(R.drawable.green_button_bg);
            communityBtnCode.setText(R.string.verification_getCode);

            mRelativeLayoutVoice.setClickable(true);
            communityTvClik.setVisibility(View.VISIBLE);
            communityTvWithLine.setText("获取语音验证码");
        } else {
            communityBtnCode.setEnabled(false);
            communityBtnCode.setBackgroundResource(R.drawable.gray_button_bg);
            communityBtnCode.setText(num + getResources().getString(R.string.verification_num));

            mRelativeLayoutVoice.setClickable(false);
            communityTvClik.setVisibility(View.INVISIBLE);
            communityTvWithLine.setText(num + getResources().getString(R.string.verification_num));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnActivityFinishEvent(ActivityFinishEvent event) {
        if (event.message == ConstantUtil.FINISH) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    /**
     * 监听事件
     */
    private void initListeners() {
        //主体的选择
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.community_rdaiobutton_left:
                        switchTextColor(checkedId);
                        position = checkedId;
                        controlNextStepStatus();
                        break;
                    case R.id.community_rdaiobutton_middle:
                        switchTextColor(checkedId);
                        position = checkedId;
                        controlNextStepStatus();
                        break;
                    case R.id.community_rdaiobutton_right:
                        switchTextColor(checkedId);
                        position = checkedId;
                        controlNextStepStatus();
                        break;
                    default:
                        break;
                }
            }
        });

        titleLeft.setOnClickListener((View v)->CreateCommunityActivity.this.finish());
        //所属学校
        communityTvOne.setOnClickListener((View v)-> CommenSelectActivity.start(CreateCommunityActivity.this, EditGroupOrClassTypeEnum.AddSchool,
                null, null, null, null, null, addressInforBean.getCity(), addressInforBean.getAreaStr(), COMMUNITY_CODE));
        //机构名称
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                school_name = mEditText.getText().toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {
                controlNextStepStatus();
            }
        });
        //营业执照
        communityEtTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                business_number = communityEtTwo.getText().toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {
                controlNextStepStatus();
            }
        });
        //负责人姓名
        communityEtThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                person_name = communityEtThree.getText().toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {
                controlNextStepStatus();
            }
        });
        //手机号码
        communityEtFour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phone = communityEtFour.getText().toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {
                controlNextStepStatus();
            }
        });
        //验证码
        communityEtFive.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                code = communityEtFive.getText().toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {
                 controlNextStepStatus();
            }
        });
        //获取验证码
        communityBtnCode.setOnClickListener((View v)->GetCodeHelper.getIntentce().sendCode_Community(CreateCommunityActivity.this, phone, GetCodeHelper.TEXT));
        //语音验证
        mRelativeLayoutVoice.setOnClickListener((View v)->GetCodeHelper.getIntentce().sendCodeVoice_Community(CreateCommunityActivity.this, phone, GetCodeHelper.VOICE));
        //下一步
        titleRight.setOnClickListener((View v)->{
            if(controlNextStepStatus())
                GroupHelper.getVerfiyCode(CreateCommunityActivity.this, phone, code, ConstantUtil.Community, callBack);
        });
    }

    /**
     * 获取安全票据异步
     */
    public final HttpRequestCallBack callBack = new HttpRequestCallBack() {
        @Override
        public void onSuccess(String result) {
            Log.e(TAG, "获取安全票据返回的数据信息为--------" + result);
            CodeBean data = JSON.parseObject(result, CodeBean.class);
            switchToActivity(position, data);
        }

        @Override
        public void onFailure(String errorMsg) {

        }
    };

    /**
     * 根据不同的类型传递不同的数值
     *
     * @param position
     * @param data
     */
    private void switchToActivity(int position, CodeBean data) {
        switch (position) {
            case R.id.community_rdaiobutton_left:
                if (data.getResetToken() == null || school_name == null || phone == null || person_name == null) {
                    Toast.makeText(this, "请认真核对信息是否填写正确", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    CreateCommunitySecondActivity.start(CreateCommunityActivity.this, data.getResetToken(), school_name, phone, person_name, 2);
                }
                break;
            case R.id.community_rdaiobutton_middle:
                if (data.getResetToken() == null || school_name == null || phone == null || person_name == null || business_number == null) {
                    Toast.makeText(this, "请认真核对信息是否填写正确", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    CreateCommunitySecondActivity.start(CreateCommunityActivity.this, data.getResetToken(), school_name, business_number, phone, person_name, 3);
                }
                break;
            case R.id.community_rdaiobutton_right:
                if (data.getResetToken() == null || phone == null || person_name == null) {
                    Toast.makeText(this, "请认真核对信息是否填写正确", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    CreateCommunitySecondActivity.start(CreateCommunityActivity.this, data.getResetToken(), phone, person_name, 1);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 不同机构显示不同信息界面的相关处理
     *
     * @param id
     */
    private void switchTextColor(int id) {
        switch (id) {
            case R.id.community_rdaiobutton_left:
                communityRdaiobuttonLeft.setTextColor(getResources().getColor(R.color.white));
                communityRdaiobuttonMiddle.setTextColor(getResources().getColor(R.color.green_color));
                communityRdaiobuttonRight.setTextColor(getResources().getColor(R.color.green_color));
                communityTvOne.setVisibility(View.VISIBLE);
                communityTvTwo.setVisibility(View.GONE);
                mRelativeLayout.setVisibility(View.GONE);
                mTextView.setText(R.string.community_school);
                mTextViewperson.setText(R.string.community_person);
                break;
            case R.id.community_rdaiobutton_middle:
                communityRdaiobuttonMiddle.setTextColor(getResources().getColor(R.color.white));
                communityRdaiobuttonLeft.setTextColor(getResources().getColor(R.color.green_color));
                communityRdaiobuttonRight.setTextColor(getResources().getColor(R.color.green_color));
                communityTvTwo.setVisibility(View.VISIBLE);
                communityTvOne.setVisibility(View.GONE);
                mRelativeLayout.setVisibility(View.VISIBLE);
                mTextView.setText(R.string.community_company);
                mTextViewperson.setText(R.string.community_person);
                break;
            case R.id.community_rdaiobutton_right:
                communityRdaiobuttonMiddle.setTextColor(getResources().getColor(R.color.green_color));
                communityRdaiobuttonLeft.setTextColor(getResources().getColor(R.color.green_color));
                communityRdaiobuttonRight.setTextColor(getResources().getColor(R.color.white));
                communityTvTwo.setVisibility(View.GONE);
                communityTvOne.setVisibility(View.GONE);
                mRelativeLayout.setVisibility(View.GONE);
                mTextViewperson.setText(R.string.community_name);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        controlNextStepStatus();
        if (resultCode == 200 && data != null) {
            switch (requestCode) {
                case COMMUNITY_CODE:
                    school_name = data.getStringExtra("schoolName");
                    mSchool_name.setText(school_name);
                    break;
            }
        }
    }


    /**
     * 根据用户所选择情况去控制下一步状态
     * @return
     */
    private boolean controlNextStepStatus() {
        if (TextUtils.isEmpty(person_name) || TextUtils.isEmpty(code) || TextUtils.isEmpty(phone)) {
            titleRightText.setTextColor(getResources().getColor(R.color.click_false));
            titleRightText.setEnabled(false);
            return false;
        }

        if (position == R.id.community_rdaiobutton_left) {
            //学校
            if (!TextUtils.isEmpty(mSchool_name.getText().toString())) {
                titleRightText.setTextColor(getResources().getColor(R.color.click_true));
                titleRightText.setEnabled(true);
                return true;
            } else {
                titleRightText.setTextColor(getResources().getColor(R.color.click_false));
                titleRightText.setEnabled(false);
                return false;
            }
        } else if (position == R.id.community_rdaiobutton_middle) {
            //培训机构
            if (!TextUtils.isEmpty(mEditText.getText().toString()) && !TextUtils.isEmpty(business_number)) {
                titleRightText.setTextColor(getResources().getColor(R.color.click_true));
                titleRightText.setEnabled(true);
                return true;
            } else {
                titleRightText.setTextColor(getResources().getColor(R.color.click_false));
                titleRightText.setEnabled(false);
                return false;
            }
        }else{
            //个人
            titleRightText.setTextColor(getResources().getColor(R.color.click_true));
            titleRightText.setEnabled(true);
            return true;
        }
    }

}
