package com.histudent.jwsoft.histudent.model.manage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lichaojie on 2017/9/20.
 * desc:
 */

public class ParamsManager {

    private static final Map<String, Object> PARAMS_MAP = new HashMap<>();


    private static final class Holder{
        private static final ParamsManager INSTANCE = new ParamsManager();
    }

    public static final ParamsManager getInstance(){
        PARAMS_MAP.clear();
        return Holder.INSTANCE;
    }

    public ParamsManager setParams(String key, Object value){
        PARAMS_MAP.put(key,value);
        return this;
    }

    public Map<String,Object> getParamsMap(){
        return PARAMS_MAP;
    }


}
