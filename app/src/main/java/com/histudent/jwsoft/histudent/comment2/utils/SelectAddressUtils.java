package com.histudent.jwsoft.histudent.comment2.utils;

import android.app.Activity;
import android.content.Intent;

import com.histudent.jwsoft.histudent.commen.model.AreaCodeModel;
import com.histudent.jwsoft.histudent.commen.view.CharacterParser;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.List;

/**
 * 地址选择器的工具类
 * Created by ZhangYT on 2017/2/24.
 */

public class SelectAddressUtils {

    private static SelectAddressUtils utils;
    private String currentCity, currentArea, changedCity, changdArea;
    private List<Object> areasInCity;
    private static AddressPinYinComparator pinyinComparator;
    private static CharacterParser characterParser;


    private SelectAddressUtils() {
        currentCity=currentArea=changedCity=changdArea="";
    }

    public static SelectAddressUtils getSelectAddressHelper() {

        if (utils == null) {
            utils = new SelectAddressUtils();
        }
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new AddressPinYinComparator();

        return utils;
    }


    public SelectAddressUtils getUtils() {
        return utils;
    }

    public void setUtils(SelectAddressUtils utils) {
        this.utils = utils;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getCurrentArea() {
        return currentArea;
    }

    public void setCurrentArea(String currentArea) {
        this.currentArea = currentArea;
    }

    public String getChangedCity() {
        return changedCity;
    }

    public void setChangedCity(String changedCity) {
        this.changedCity = changedCity;
    }

    public String getChangdArea() {
        return changdArea;
    }

    public void setChangdArea(String changdArea) {
        this.changdArea = changdArea;
    }


    public void setAreasInCity(List<Object> areasInCity) {
        this.areasInCity = areasInCity;
    }


    //判断该地区是否是选择城市下的地区
    public boolean isAreaInCity(String areaName) {
        if (!StringUtil.isEmpty(areaName)) {

            if (areasInCity != null && areasInCity.size() > 0)
                for (Object o : areasInCity) {

                    if (o instanceof AreaCodeModel) {
                        AreaCodeModel area = ((AreaCodeModel) o);
                        if (area.getName().equals(areaName)) {
                            return true;
                        }
                    }

                }
        }

        return false;
    }

    //判断城市否是改变过
    public static boolean cityIsChanged(String currentCity, String changedCity) {

        return StringUtil.isEmpty(changedCity) == true
                ? false : (!changedCity.equals(currentCity));
    }


    //判断地区是否改变
    public boolean areaIsChanged() {

        if (StringUtil.isEmpty(changdArea) == true
                ? false : (!changdArea.equals(currentArea))) {

            return isAreaInCity(changdArea);
        }
        return false;
    }

    public void setResultOnlyCity(Activity context, Intent intent) {

        //如果城市改变，返回改变后的城市否则返回当前城市
        intent.putExtra("city", cityIsChanged(currentCity, changedCity) == true ? changedCity : currentCity);
        intent.putExtra("area", "");
        context.setResult(200, intent);
        context.finish();

    }


    //城市必须有，地区可有可无
    public void setResultCityAndAreaIsNessary(Activity context, Intent intent) {


        if (SelectAddressUtils.cityIsChanged(currentCity, changedCity)) { //改变城市情况
            intent.putExtra("city", changedCity);
            if (areaIsChanged()) {
                intent.putExtra("area", changdArea);

            } else {
                if (!StringUtil.isEmpty(currentArea)) {
                    if (isAreaInCity(currentArea)) {
                        intent.putExtra("area", currentArea);
                    }
                }

            }
            context.setResult(200, intent);
            //没有改变城市的情况
        } else {

            //改变地区的情况
            if (areaIsChanged()) {
                intent.putExtra("area", changdArea);
                intent.putExtra("city", currentCity);
                context.setResult(200, intent);
            }
        }
        context.finish();
    }


    //返回城市和地区
    public void setResultCityAndArea(Activity context, Intent intent) {

        if (SelectAddressUtils.cityIsChanged(currentCity, changedCity)) {
            intent.putExtra("city", changedCity);
            if (areaIsChanged()) {
                intent.putExtra("area", changdArea);
            } else {
                if (!StringUtil.isEmpty(currentArea)) {
                    if (isAreaInCity(currentArea)) {
                        intent.putExtra("area", currentArea);
                    }
                } else {
                    intent.putExtra("area", "");
                }
            }
            context.setResult(200, intent);

        } else {
            //改变地区的情况
            if (areaIsChanged()) {
                intent.putExtra("area", changdArea);
                intent.putExtra("city", currentCity);
                context.setResult(200, intent);
            }
        }
        context.finish();
    }

    //将城市第一个字母获取到转换成大写
    public char getFirstCharcterOfCity(String city) {
        char s = city.charAt(0);
        if (Character.isLowerCase(s)) {
            return Character.toUpperCase(s);
        }
        return s;
    }

    //返回
    public void setResult(int Actiontype, Intent intent, Activity context) {

        switch (Actiontype) {

            //仅需要城市
            case 1:
                setResultOnlyCity(context, intent);
                break;

            //城市和地区都要返回
            case 2:

                setResultCityAndArea(context, intent);
                break;

            //城市必须有，地区可有可无
            case 3:

                setResultCityAndAreaIsNessary(context, intent);
                break;
        }
    }
}
