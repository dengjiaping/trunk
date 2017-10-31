package com.histudent.jwsoft.histudent.comment2.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.model.AreaCodeModel;
import com.histudent.jwsoft.histudent.commen.utils.LocationCallBack;
import com.histudent.jwsoft.histudent.commen.utils.LocationUtils;
import com.histudent.jwsoft.histudent.commen.view.CharacterParser;
import com.histudent.jwsoft.histudent.commen.view.EditText_clear;
import com.histudent.jwsoft.histudent.commen.view.SideBar;
import com.histudent.jwsoft.histudent.commen.view.addressSelect.Hz_utils;
import com.histudent.jwsoft.histudent.comment2.adapter.AddressAdapter;
import com.histudent.jwsoft.histudent.comment2.adapter.AddressExpandedAdapter;
import com.histudent.jwsoft.histudent.comment2.utils.AddressPinYinComparator;
import com.histudent.jwsoft.histudent.comment2.utils.SelectAddressUtils;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 地区选择器
 */

public class SelectAddressActivity extends BaseActivity {
    ExpandableListView expandableListView;
    private AddressExpandedAdapter expandedAdapter;
    private List<Object> list_group;
    private List<List<Object>> list_child;
    private List<Object> tmp, tmp2;
    private AddressPinYinComparator pinyinComparator;
    private CharacterParser characterParser;
    private List<Object> list_child2;
    private SideBar sideBar;
    private EditText_clear clearEditText;
    private Intent intent;
    private ListView listView_result;
    private AddressExpandedAdapter.getSelectedCity getSelectedCity;
    private AddressAdapter addressAdapter;
    private int Actiontype;
    private List<Object> list_reuslt;
    private FrameLayout frame;
    private LocationCallBack callBack;
    private SelectAddressUtils helper;

    @Override
    public int setViewLayout() {
        return R.layout.activity_select_address3;
    }

    /**
     * ]
     *
     * @param activity
     * @param city     城市名称
     * @param area     地区名称 在不需要传地区的地方可以传空
     * @param type     类型  ：1 返回值只要城市  2.放回值必须时城市和地区   3返回值必须有城市，地区可有可无
     */

    public static void start(Activity activity, String city, String area, int type) {

        Intent intent = new Intent(activity, SelectAddressActivity.class);
        intent.putExtra("city", city);
        intent.putExtra("area", area);
        intent.putExtra("type", type);
        activity.startActivityForResult(intent, 300);
    }

    @Override
    public void initView() {

        expandableListView = ((ExpandableListView) findViewById(R.id.list_view));
        sideBar = ((SideBar) findViewById(R.id.sidrbar));
        clearEditText = ((EditText_clear) findViewById(R.id.filter_edit));
        listView_result = ((ListView) findViewById(R.id.list_view_reulst));
        frame = ((FrameLayout) findViewById(R.id.frame));
        clearEditText.clearFocus();
        clearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!StringUtil.isEmpty(s.toString())) {
                    if (s.length() > 1) {
                        listView_result.setVisibility(View.VISIBLE);
                        frame.setVisibility(View.GONE);

                        list_reuslt.clear();
                        list_reuslt.addAll(Hz_utils.praseJSONGetCity(SelectAddressActivity.this, s.toString()));
                        if (list_reuslt.size() < 1) {
                            list_reuslt.add("1");
                        }
                        addressAdapter.notifyDataSetChanged();
                    } else {
                        listView_result.setVisibility(View.GONE);
                        frame.setVisibility(View.VISIBLE);
                        expandableListView.setSelection(expandedAdapter.getSelection(
                                helper.getFirstCharcterOfCity(characterParser.getSelling(s.toString()))));
                    }
                } else {
                    frame.setVisibility(View.VISIBLE);
                    listView_result.setVisibility(View.GONE);
                }
            }


            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        intent = getIntent();

        Actiontype = intent.getIntExtra("type", -1);

        helper = SelectAddressUtils.getSelectAddressHelper();
        helper.setChangedCity("");
        helper.setChangdArea("");
        helper.setCurrentArea(intent.getStringExtra("area"));
        helper.setCurrentCity(intent.getStringExtra("city"));

        Log.e("currentCity==>",helper.getCurrentCity());
        initSearchList();
        if (StringUtil.isEmpty(helper.getCurrentCity())) {
            initLocation();
        } else {
            initCityDate();
        }
    }

    //当传递位置为空时，定位
    private void initLocation() {

        callBack = new LocationCallBack() {
            @Override
            public void getLocationInfor(double lat, double lon, String address, String city, String dist, boolean isSuccess) {

                if (isSuccess) {
                    helper.setCurrentCity(city);
                    helper.setCurrentArea(dist);
                } else {
                    helper.setCurrentCity("北京市");
                }

                initCityDate();
            }
        };

        LocationUtils.getLocationUtils().getLocationInfor(this, callBack);
    }

    private void initSearchList() {
        list_reuslt = new ArrayList<>();
        addressAdapter = new AddressAdapter(this, list_reuslt);
        listView_result.setAdapter(addressAdapter);

        listView_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AreaCodeModel selectedCityModel = ((AreaCodeModel) list_reuslt.get(position));
                helper.setChangedCity(selectedCityModel.getName());
                setGetSelectedCityAction(selectedCityModel);
                listView_result.setVisibility(View.GONE);
                frame.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initCityDate() {

        list_child = new ArrayList<>();
        list_child2 = new ArrayList<>();
        list_group = new ArrayList<>();


        //选择城市或地区后的回调
        getSelectedCity = new AddressExpandedAdapter.getSelectedCity() {
            @Override
            public void getSelectedCity(AreaCodeModel areaCodeModel, int type) {

                if (type == 1) {     //选择的城市

                    helper.setChangedCity(areaCodeModel.getName());

                    if (!StringUtil.isEmpty(helper.getChangedCity())) {

                        if (!helper.getChangedCity().equals(helper.getCurrentCity())) {     //城市改变时，地区置空
                            helper.setCurrentArea("");
                        }

                        if (Actiontype != 2) { //点击城市时，如果不是必须返回城市和地区时，直接返回城市
                            helper.setResultOnlyCity(SelectAddressActivity.this, intent);

                        } else {    //当前功能需要城市和地区

                            setGetSelectedCityAction(areaCodeModel);
                        }
                    }


                } else if (type == 0) {  //选择的地区

                    helper.setChangdArea(areaCodeModel.getName());
                    if (!StringUtil.isEmpty(helper.getChangdArea())) {
                        helper.setResult(Actiontype, intent, SelectAddressActivity.this);
                    }

                } else {//点击当前城市时，如果不是必须返回城市和地区时，直接返回城市
                    if (Actiontype != 2) {//当前功能只需要城市
                        helper.setResultOnlyCity(SelectAddressActivity.this, intent);
                    }
                }
            }
        };

        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                if (!StringUtil.isEmpty(s))
                    expandableListView.setSelection(expandedAdapter.getSelection(s.charAt(0)));
            }
        });


        //添加当前所在城市
        List<String> sugcity = new ArrayList<>();
        sugcity.add(helper.getCurrentCity());
        list_group.add(helper.getCurrentCity());

        //添加当前所在城市下面的区县
        list_child2.addAll(Hz_utils.getAreaCodeModel(this, sugcity));
        list_child.add(list_child2);


        list_group.add("展开选择城市区县");
        list_child2 = new ArrayList<>();
        List<AreaCodeModel> areaCodeModels = Hz_utils.getAreaCodeModel(this, sugcity);
        if (areaCodeModels.size() > 0)
            list_child2.addAll(Hz_utils.parseJsonGetDist(this, areaCodeModels.get(0)));
        Hz_utils.setSelectedDis(list_child2, helper.getCurrentArea());

        helper.setAreasInCity(list_child2);

        list_child.add(sortCity(list_child2));


        //添加推荐城市
        list_child2 = new ArrayList<>();
        list_group.add("全国热门的城市");
        sugcity.clear();
        sugcity.add("杭州");
        sugcity.add("温州");
        sugcity.add("上海");
        sugcity.add("北京");
        sugcity.add("广州");
        sugcity.add("深圳");
        list_child2.addAll(Hz_utils.getAreaCodeModel(this, sugcity));
        list_child.add(sortCity(list_child2));


        //添加所有城市
        list_child2 = new ArrayList<>();
        tmp = new ArrayList<>();

        tmp.addAll(Hz_utils.praseJSONGetCitys(this));

        sortCity(tmp);

        addCharacher();

    }


    //城市按照首字母排序
    private List<Object> sortCity(List<Object> tmp) {
        tmp2 = new ArrayList<>();
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new AddressPinYinComparator();

        for (Object o : tmp) {
            AreaCodeModel model = ((AreaCodeModel) o);
            if (model.getName().equals("市辖区") || model.getName().endsWith("省")) {
                continue;
            } else {
                model.setFirstCharacter(helper.getFirstCharcterOfCity(model.getPinyingName()));
                model.setPinYinNameAll(characterParser.getSelling(model.getName()));
                tmp2.add(model);
            }
        }

        Collections.sort(tmp2, pinyinComparator);

        return tmp2;

    }

    //为城市添加首字母
    private void addCharacher() {
        for (int i = 0; i < tmp2.size() - 1; i++) {

            AreaCodeModel model_fir = ((AreaCodeModel) tmp2.get(i));
            AreaCodeModel model_sec = ((AreaCodeModel) tmp2.get(i + 1));
            if (model_fir.getFirstCharacter() == model_sec.getFirstCharacter() - 1) {

                list_child.add(list_child2);
                list_child2 = new ArrayList<>();
                list_group.add(model_sec.getFirstCharacter());
            } else {

                list_child2.add(model_fir);
            }
            if (i == tmp2.size() - 2) {

                list_child2.add(model_sec);
                list_child.add(list_child2);
            }

        }

        list_group.add(3, 'A');


        expandedAdapter = new AddressExpandedAdapter(this, list_group, list_child, getSelectedCity);
        expandableListView.setAdapter(expandedAdapter);

        for (int i = 0; i < list_group.size(); i++) {
            expandableListView.expandGroup(i == 1 ? 0 : i);
            expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    if (groupPosition != 1) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        }
        expandableListView.setGroupIndicator(null);
    }


    //选择每个城市时，对应更新地区
    private void setGetSelectedCityAction(final AreaCodeModel areaCodeModel) {

        //更新当前城市
        list_child2 = new ArrayList<>();
        list_child2.add(areaCodeModel);
        list_child.set(0, list_child2);
        //更新当前城市下的县和地区
        list_child2 = new ArrayList<>();
        list_child2.addAll(Hz_utils.parseJsonGetDist(this, areaCodeModel));

        list_child.set(1, sortCity(list_child2));
        expandedAdapter.notifyDataSetChanged();
        expandableListView.smoothScrollToPosition(0);

        //保存当前城市下的地区，便于判断用户选择地区是否是选择城市下的
        helper.setAreasInCity(list_child2);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.title_left:
                helper.setResult(Actiontype, intent, this);
                break;
        }
    }
}
