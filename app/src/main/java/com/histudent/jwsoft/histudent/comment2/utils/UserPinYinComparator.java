package com.histudent.jwsoft.histudent.comment2.utils;

import com.histudent.jwsoft.histudent.body.hiworld.bean.SimpleUserModel;
import com.histudent.jwsoft.histudent.commen.view.CharacterParser;

import java.util.Comparator;


/**
 * Created by ZhangYT on 2016/12/5.
 */

public class UserPinYinComparator implements Comparator<Object> {

    CharacterParser paraser= CharacterParser.getInstance();
    @Override
    public int compare(Object o1, Object o2) {

        SimpleUserModel model1 = ((SimpleUserModel) o1);
        SimpleUserModel model2 = ((SimpleUserModel) o2);
        String fist=paraser.getSelling(model1.getName()).toLowerCase();
        String second=paraser.getSelling(model2.getName()).toLowerCase();

        if (fist.equals("@")
                || second.equals("#")) {
            return -1;
        } else if (fist.equals("#")
                ||second.equals("@")) {
            return 1;
        } else {
            return fist.compareTo(second);
        }

    }

}

