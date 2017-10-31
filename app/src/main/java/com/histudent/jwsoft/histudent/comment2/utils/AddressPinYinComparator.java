package com.histudent.jwsoft.histudent.comment2.utils;

import com.histudent.jwsoft.histudent.commen.model.AreaCodeModel;
import com.histudent.jwsoft.histudent.commen.model.SortModel;

import java.util.Comparator;


/**
 * Created by ZhangYT on 2016/12/5.
 */

public class AddressPinYinComparator  implements Comparator<Object> {

    public int compare(Object o1, Object o2) {
        AreaCodeModel model1= ((AreaCodeModel) o1);
        AreaCodeModel model2= ((AreaCodeModel) o2);
        if (model1.getPinYinNameAll().equals("@")
                || model2.getPinYinNameAll().equals("#")) {
            return -1;
        } else if (model1.getPinYinNameAll().equals("#")
                || model2.getPinYinNameAll().equals("@")) {
            return 1;
        } else {
            return model1.getPinYinNameAll().compareTo(model2.getPinYinNameAll());
        }
    }

}