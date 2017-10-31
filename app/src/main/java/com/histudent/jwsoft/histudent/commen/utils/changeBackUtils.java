package com.histudent.jwsoft.histudent.commen.utils;

import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ZhangYT on 2016/7/18.
 */
public class changeBackUtils {

    private   static FrameLayout frameLayout;
    private static List<FrameLayout>frameLayouts;

    public static void setFrame(FrameLayout layout,int position){
        frameLayout=layout;

        if(null==frameLayouts){
            frameLayouts=new ArrayList<>();
            for(int i=0;i<3;i++){
                frameLayouts.add(null);
            }
        }
        if(null==frameLayouts.get(position)){
          frameLayouts.add(position,frameLayout);
        }
    }

    public static FrameLayout getFrame(int position){

            return frameLayouts.get(position);
    }
}
