package com.histudent.jwsoft.histudent.body.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.mine.fragment.AccountSafeType;
import com.histudent.jwsoft.histudent.body.mine.fragment.PhoneSetFragment_01;
import com.histudent.jwsoft.histudent.body.mine.fragment.PhoneSetFragment_02;
import com.histudent.jwsoft.histudent.body.mine.fragment.PswSetFragment;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;

/**
 * Created by liuguiyu-pc on 2016/11/3.
 */
public class AccountSafeItemActivity extends BaseActivity {

    private TextView title;
    public static int type;

    private FragmentManager fragmentManager;
    private PswSetFragment pswSetFragment;
    private PhoneSetFragment_01 phoneSetFragment_01;
    private PhoneSetFragment_02 phoneSetFragment_02;

    public static String resetToken;

    public static void start(Activity activity, String title, int type) {
        Intent intent = new Intent(activity, AccountSafeItemActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        activity.startActivityForResult(intent, 100);
    }

    @Override
    public int setViewLayout() {
        return R.layout.activity_account_safe_item;
    }

    @Override
    public void initView() {
        title = (TextView) findViewById(R.id.title_middle_text);
        fragmentManager = getSupportFragmentManager();

        pswSetFragment = new PswSetFragment();
        phoneSetFragment_01 = new PhoneSetFragment_01();
        phoneSetFragment_02 = new PhoneSetFragment_02();

        phoneSetFragment_01.setOnlistens(new PhoneSetFragment_01.Gonext() {
            @Override
            public void doaction_phone() {
                title.setText(R.string.exchangePhoneCode);
                fragmentManager.beginTransaction().add(R.id.accountSafe_fragment, phoneSetFragment_02).commit();
            }

            @Override
            public void doaction_email() {
                title.setText(R.string.exchangeEmailCode);
                fragmentManager.beginTransaction().add(R.id.accountSafe_fragment, phoneSetFragment_02).commit();
            }
        });

    }

    @Override
    public void doAction() {
        super.doAction();

        type = getIntent().getIntExtra("type", -1);
        title.setText(getIntent().getStringExtra("title"));

        switch (type) {
            case AccountSafeType.PSWSET:
                fragmentManager.beginTransaction().add(R.id.accountSafe_fragment, pswSetFragment).commit();
                break;

            case AccountSafeType.PHONE:
            case AccountSafeType.EMAIL:

                fragmentManager.beginTransaction().add(R.id.accountSafe_fragment, phoneSetFragment_01).commit();

                break;

            case AccountSafeType.PHONE_NULL:
            case AccountSafeType.EMAIL_NULL:

                fragmentManager.beginTransaction().add(R.id.accountSafe_fragment, phoneSetFragment_02).commit();

                break;
        }

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

            case R.id.title_left:
                finish();
                break;

            case R.id.title_right:

                break;

        }
    }

}
