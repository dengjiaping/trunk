package com.histudent.jwsoft.histudent.commen.view.addressSelect;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.commen.model.AreaCodeModel;

import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Hz_utils {

    private static File file_updateText = new File(
            Environment.getExternalStorageDirectory() + ("/Update/updata")); // 保存数据源的文件夹路径

    // 从assets 文件夹中获取文件并读取数据
    public static String getFromAssets(Context c, String fileName) {
        String result = null;
        try {
            InputStream in = c.getResources().getAssets().open(fileName);
            // 获取文件的字节数
            int lenght = in.available();
            // 创建byte数组
            byte[] buffer = new byte[lenght];
            // 将文件中的数据读到byte数组中
            in.read(buffer);
            result = EncodingUtils.getString(buffer, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 从本地内存文件夹中获取文件并读取数据
    public static String getDataFromFile(Context c, String fileName) {
        String result = null;
        try {

            FileInputStream fin = new FileInputStream(file_updateText + "/"
                    + fileName + ".txt");

            int length = fin.available();

            byte[] buffer = new byte[length];

            fin.read(buffer);

            result = EncodingUtils.getString(buffer, "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static List<AreaCodeModel> list_area_all = null;

    /**
     * 解析地址总数据
     *
     * @param context
     * @param parentCode 上一级的编号（“0”表示顶级）
     * @return
     */
    public static List<AreaCodeModel> praseJSON(Context context, String parentCode) {

        List<AreaCodeModel> list_area = new ArrayList<>();

        if (list_area_all == null) {

            String string = Hz_utils.getFromAssets(context, "AreaCode.txt");

            if (string != null) {

                list_area_all = JSON.parseArray(string, AreaCodeModel.class);

            } else {
                HiStudentLog.e("==========================从assets文件中获取数据源失败！");
            }
        }

        for (AreaCodeModel model : list_area_all) {

            if ("0".equals(parentCode)) {
                if (model.getDepth().equals("1")) {

                    list_area.add(model);
                }
            } else {
                if (model.getParentCode().equals(parentCode)) {
                    list_area.add(model);
                }
            }

        }

        return list_area;
    }


    public static List<AreaCodeModel> praseJSONGetCitys(Context context) {

        List<AreaCodeModel> list_area = new ArrayList<>();

        if (list_area_all == null) {

            String string = Hz_utils.getFromAssets(context, "AreaCode.txt");

            if (string != null) {

                list_area_all = JSON.parseArray(string, AreaCodeModel.class);

            } else {
                HiStudentLog.e("==========================从assets文件中获取数据源失败！");
            }
        }

        for (AreaCodeModel model : list_area_all) {

            if ((model.getDepth().equals("2") && model.getName().endsWith("市"))
                    || model.getParentCode().equals("A1560000")) {

                list_area.add(model);
            }
        }

        return list_area;
    }

    public static List<AreaCodeModel> parseJsonGetDist(Context context, AreaCodeModel areaCodeModel1) {


        List<AreaCodeModel> list_area = new ArrayList<>();

        if (list_area_all == null) {

            String string = Hz_utils.getFromAssets(context, "AreaCode.txt");

            if (string != null) {

                list_area_all = JSON.parseArray(string, AreaCodeModel.class);

            } else {
                HiStudentLog.e("==========================从assets文件中获取数据源失败！");
            }
        }

        for (AreaCodeModel model : list_area_all) {
            if (model.getParentCode().equals(areaCodeModel1.getAreaCode())) {

                String areaCode = model.getAreaCode();
                if (areaCodeModel1.getDepth().equals("1")) {
                    for (AreaCodeModel model2 : list_area_all) {
                        if (model2.getParentCode().equals(areaCode)) {
                            model2.setSelected(false);
                            list_area.add(model2);
                        }
                    }

                } else {
                    model.setSelected(false);
                    list_area.add(model);
                }
            }
        }


//        Log.e("parentCode" + parentCode, list_area.toString() + parentCode);
        return list_area;

    }

    public static List<AreaCodeModel> getAreaCodeModel(Activity context, List<String> citys) {


        List<AreaCodeModel> list = new ArrayList<>();
        if (list_area_all == null) {

            String string = Hz_utils.getFromAssets(context, "AreaCode.txt");

            if (string != null) {

                list_area_all = JSON.parseArray(string, AreaCodeModel.class);

            } else {
                HiStudentLog.e("==========================从assets文件中获取数据源失败！");
            }
        }

        for (int i = 0; i < citys.size(); i++) {

            for (AreaCodeModel model : list_area_all) {
                if (model.getName().contains(citys.get(i)) && model.getDepth().equals("2") ||
                        model.getName().contains(citys.get(i)) && model.getDepth().equals("1")) {
                    list.add(model);
                    break;
                }
            }
        }

        return list;
    }

    public static List<AreaCodeModel> praseJSONGetCity(Context context, String cityName) {

        List<AreaCodeModel> list_area = new ArrayList<>();

        if (list_area_all == null) {

            String string = Hz_utils.getFromAssets(context, "AreaCode.txt");

            if (string != null) {

                list_area_all = JSON.parseArray(string, AreaCodeModel.class);

            } else {
                HiStudentLog.e("==========================从assets文件中获取数据源失败！");
            }
        }

        for (AreaCodeModel model : list_area_all) {

            if (model.getName().equals(cityName) || (model.getName().substring(0, model.getName().length() - 1).equals(cityName))) {
                list_area.add(model);
            }
        }

        return list_area;
    }


    public static void setSelectedDis(List<Object> list_area, String areaName) {

        for (int i = 0; i < list_area.size(); i++) {

            AreaCodeModel a = ((AreaCodeModel) list_area.get(i));
            if (a.getName().equals(areaName)&&!a.getAreaCode().equals("1")) {
                a.setSelected(true);
            }else {
                a.setSelected(false);
            }
        }
    }

}
