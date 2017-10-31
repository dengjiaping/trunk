package com.histudent.jwsoft.histudent.commen.utils;

import android.util.Log;

import com.netease.nim.uikit.common.util.string.StringUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhangYT on 2016/9/22.
 */
public class SHA1Utils {

    public static String SHA1(String str) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-1"); //如果是SHA加密只需要将"SHA-1"改成"SHA"即可
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexStr = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexStr.append(0);
                }
                hexStr.append(shaHex);
            }
            return hexStr.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String GetSHA1Date(Map<String, Object> map) {

        List<String> keyList = new ArrayList<>();

        if (map!=null){
            for (String key : map.keySet()) {
                if (!key.contains("File")){
                    keyList.add(key);
                }

            }

            List<String> keyListOrder = lexicographicOrder(keyList);

            String result = splitParams(keyListOrder, map);
            if (result.length()!=0){
                result = result.substring(0, result.length() - 1);
                result = SHA1(result);
            }


            if (!StringUtil.isEmpty(result)) {
                return result;
            }
        }else {
            Log.e("SHA1Date","参数为空！");
        }


        return null;
    }

    public static String GetSHA1Date2(Map<String, String> map) throws UnsupportedEncodingException {

        List<String> keyList = new ArrayList<>();

        if (map!=null){
            for (String key : map.keySet()) {
                if (!key.contains("File")){
                    keyList.add(key);
                }

            }

            List<String> keyListOrder = lexicographicOrder(keyList);

            String result = splitParams2(keyListOrder, map);
            if (result.length()!=0){
                result = result.substring(0, result.length() - 1);
                result = SHA1(result);
            }


            if (!StringUtil.isEmpty(result)) {
                return result;
            }
        }else {
            Log.e("SHA1Date","参数为空！");
        }


        return null;
    }

    /**
     * 获取参数的字典排序
     *
     * @param maps 参数key-value map集合
     * @return String 排序后的字符串
     */
    private static String getOrderByLexicographic(Map<String, Object> maps) {
        return splitParams(lexicographicOrder(getParamsName(maps)), maps);
    }

    /**
     * 获取参数名称 key
     *
     * @param maps 参数key-value map集合
     * @return
     */
    private static List<String> getParamsName(Map<String, Object> maps) {
        List<String> paramNames = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : maps.entrySet()) {
            paramNames.add(entry.getKey());
        }
        return paramNames;
    }

    /**
     * 参数名称按字典排序
     *
     * @param paramNames 参数名称List集合
     * @return 排序后的参数名称List集合
     */
    private static List<String> lexicographicOrder(List<String> paramNames) {
        Collections.sort(paramNames);
        return paramNames;
    }

    /**
     * 拼接排序好的参数名称和参数值
     *
     * @param paramNames 排序后的参数名称集合
     * @param maps       参数key-value map集合
     * @return String 拼接后的字符串
     */
    private static String splitParams(List<String> paramNames, Map<String, Object> maps) {
        StringBuilder paramStr = new StringBuilder();
        for (String paramName : paramNames) {
            paramStr.append(paramName).append(":");
            for (Map.Entry<String, Object> entry : maps.entrySet()) {
                if (paramName.equals(entry.getKey())) {
                    paramStr.append(String.valueOf(entry.getValue())).append(",");
                }
            }
        }
        return paramStr.toString();
    }

    /**
     * 拼接排序好的参数名称和参数值
     *
     * @param paramNames 排序后的参数名称集合
     * @param maps       参数key-value map集合
     * @return String 拼接后的字符串
     */
    private static String splitParams2(List<String> paramNames, Map<String, String> maps) throws UnsupportedEncodingException {
        StringBuilder paramStr = new StringBuilder();
        for (String paramName : paramNames) {
            paramStr.append(paramName).append(":");
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                if (paramName.equals(entry.getKey())) {
                    paramStr.append(URLDecoder.decode((String)entry.getValue(),"utf-8")).append(",");
                }
            }
        }
        return paramStr.toString();
    }
}
