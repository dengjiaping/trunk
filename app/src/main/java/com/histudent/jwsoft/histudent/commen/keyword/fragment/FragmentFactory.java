package com.histudent.jwsoft.histudent.commen.keyword.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.histudent.jwsoft.histudent.body.find.fragment.PageFragment;
import com.histudent.jwsoft.histudent.model.constant.TransferKeys;

/**
 * Created by zejian
 * Time  16/1/7 上午11:40
 * Email shinezejian@163.com
 * Description:产生fragment的工厂类
 */
public class FragmentFactory {

    public static final String EMOTION_MAP_TYPE="EMOTION_MAP_TYPE";
    private static FragmentFactory factory;

    private FragmentFactory(){

    }

    /**
     * 双重检查锁定，获取工厂单例对象
     * @return
     */
    public static FragmentFactory getSingleFactoryInstance(){
        if(factory==null){
            synchronized (FragmentFactory.class){
                if(factory==null){
                    factory = new FragmentFactory();
                }
            }
        }
        return factory;
    }

    /**
     * 获取fragment的方法
     * @param emotionType 表情类型，用于判断使用哪个map集合的表情
     */
    public Fragment getFragment(int emotionType){
        Bundle bundle = new Bundle();

        bundle.putInt(FragmentFactory.EMOTION_MAP_TYPE,emotionType);

        EmotiomComplateFragment fragment= EmotiomComplateFragment.newInstance(EmotiomComplateFragment.class,bundle);

        return fragment;
    }


    /**
     * 获取社群搜索sub fragment
     * @param communityId
     * @return
     */
    public PageFragment getCommunityFragment(String communityId){
        Bundle bundle = new Bundle();
        bundle.putString(TransferKeys.COMMUNITY_ID,communityId);
        PageFragment pageFragment = new PageFragment();
        pageFragment.setArguments(bundle);
        return pageFragment;
    }

}
