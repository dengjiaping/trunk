package com.histudent.jwsoft.histudent.commen.utils.imageloader;

import android.graphics.Point;
import android.text.TextUtils;

/**
 * Created by huyg on 2017/9/19.
 */

public class ImageUtil {

    public static Point getImageSize(String str) {
        int defaultWidth = 400;
        int defaultHeight = 400;
        Point sizeData = new Point(defaultWidth,defaultHeight);
        if (TextUtils.isEmpty(str) || str.equals("null")) {
            return sizeData;
        }
        if (str.startsWith("file://") ) {
            return sizeData;
        }
        if (str.contains("http://img.hitongx.com")) {
            int indexStart = str.indexOf("wh=")+3;
            String sizeSrc = str.substring(indexStart,str.length());
            String widthAndHeight[] = sizeSrc.split("_");
            if (widthAndHeight.length == 2) {
                try {
                    int widthSrc = Integer.parseInt(widthAndHeight[0]);
                    int heightSrc = Integer.parseInt(widthAndHeight[1]);
                    if (widthSrc==0){
                        widthSrc = defaultWidth;
                    }
                    if (heightSrc==0){
                        heightSrc =defaultHeight;
                    }
                    if (heightSrc >= widthSrc) {
                        sizeData.y = defaultHeight;
                        sizeData.x = defaultWidth * widthSrc / heightSrc;
                    } else {
                        sizeData.x = defaultWidth;
                        sizeData.y = defaultHeight * heightSrc / widthSrc;
                    }

                    return sizeData;
                } catch (NumberFormatException e) {

                }
            }
        }
        return sizeData;
    }
}
