package com.histudent.jwsoft.histudent.comment2.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.InfoWindowAdapter;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.overlay.PoiOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Inputtips.InputtipsListener;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.model.listener.IPermissionCallBackListener;
import com.histudent.jwsoft.histudent.commen.activity.BaseActivity;
import com.histudent.jwsoft.histudent.commen.enums.LoactionType;
import com.histudent.jwsoft.histudent.commen.helper.ReminderHelper;
import com.histudent.jwsoft.histudent.comment2.adapter.AutoCompleteTextAdapter;
import com.histudent.jwsoft.histudent.comment2.adapter.AutoCompleteTipAdapter;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.comment2.utils.AMapUtil;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * 带有地图界面的定位
 */
public class MapActivity extends BaseActivity implements
        OnMarkerClickListener, InfoWindowAdapter, TextWatcher,
        OnPoiSearchListener, OnClickListener, InputtipsListener {
    private AMap aMap;
    private String keyWord = "";// 要输入的poi搜索关键字
    private ProgressDialog progDialog = null;// 搜索时进度条
    private PoiResult poiResult; // poi返回的结果
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索
    private GeocodeSearch geocodeSearch;
    private AMapLocationClient client;
    private AutoCompleteTextAdapter autoCompleteTextAdapter;
    private AutoCompleteTipAdapter tipAdapter;
    private List<PoiItem> sugestAddressList;
    private AutoCompleteTextView autoCompleteTextView;
    private ListView listView;
    private List<Tip> autoCompleteList;
    private String CurrentCity;
    private boolean isNeedCurrentLocation;//是否需要定位服务
    private double Lat, Lon;
    private Intent intent;
    private TextView tv;
    private String District;
    private boolean isSuggesCity;
    private MapView mapView;
    private boolean tag, isNeedEmpty, isSearch;
    private LoactionType locationType;
    private TextView title_middle;

    private AddressInforBean addressInforBean, model;

    public static void start(Activity activity, AddressInforBean model, LoactionType type) {
        Intent intent = new Intent(activity, MapActivity.class);
        intent.putExtra("locationType", type);
        intent.putExtra("model", model);
        activity.startActivity(intent);
    }

    /**
     * @param activity
     * @param model
     * @param isNeedCurrentLocation 是否需要定位
     * @param isNeedEmpty           是否需要添加不选择位置条目
     */

    public static void startForResult(Activity activity, AddressInforBean model, LoactionType type,
                                      boolean isNeedCurrentLocation, boolean isNeedEmpty) {

        Intent intent = new Intent(activity, MapActivity.class);
        intent.putExtra("locationType", type);
        intent.putExtra("model", model);
        intent.putExtra("isNeedLocation", isNeedCurrentLocation);
        intent.putExtra("isNeedEmpty", isNeedEmpty);
        activity.startActivityForResult(intent, 200);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView.onCreate(savedInstanceState);

    }

    @Override
    public int setViewLayout() {
        return R.layout.poikeywordsearch_activity;
    }


    @Override
    public void initView() {

        //定位权限检查
        checkGetLocationPermission(new IPermissionCallBackListener() {
            @Override
            public void doAction() {
                mapView = ((MapView) findViewById(R.id.map));
                if (aMap == null) {
                    aMap = mapView.getMap();
                }

                initMap();
            }
        });
    }

    /**
     * 初始化AMap对象
     */
    private void initMap() {
        intent = getIntent();

        isNeedEmpty = intent.getBooleanExtra("isNeedEmpty", false);
        isNeedCurrentLocation = intent.getBooleanExtra("isNeedLocation", false);
        locationType = ((LoactionType) intent.getSerializableExtra("locationType"));
        initTitle();

        model = ((AddressInforBean) getIntent().getSerializableExtra("model"));
        if (model != null) {

            Lat = model.getLatitude();
            Lon = model.getLongitude();
            if (Lat > 90) {
                Lon = Lat;
                Lat = model.getLongitude();
            }
            CurrentCity = model.getCity();
        }

        sugestAddressList = new ArrayList<>();
        autoCompleteList = new ArrayList<>();
        autoCompleteTextAdapter = new AutoCompleteTextAdapter(this, sugestAddressList);
        autoCompleteTextView = ((AutoCompleteTextView) findViewById(R.id.keyWord));
        listView = ((ListView) findViewById(R.id.list_view));

        listView.setAdapter(autoCompleteTextAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tip tip = autoCompleteList.get(position);


                //如果获取到坐标，用坐标进行搜索
                if (tip.getPoint() != null && !isSuggesCity) {
                    keyWord = tip.getAddress();
                    autoCompleteTextView.setText(tip.getName());
                    Lon = tip.getPoint().getLongitude();
                    Lat = tip.getPoint().getLatitude();

                    doRegeocodeSearch(tip.getPoint());

                    //没有获取到坐标则进行关键字搜索
                } else {
                    CurrentCity = tip.getAddress();
                    keyWord = tip.getName();
                    autoCompleteTextView.setText(keyWord);
                    doSearchQuery();
                    isSuggesCity = false;
                }

            }
        });

        autoCompleteTextView.addTextChangedListener(this);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PoiItem poiItem = sugestAddressList.get(position);
                addressInforBean = new AddressInforBean();
                if (poiItem.getLatLonPoint() == null && isNeedEmpty) {
                    addressInforBean.setShowAddress(false);
                } else {
                    updateWithNewLocation(poiItem.getLatLonPoint());
                    addressInforBean.setCity(CurrentCity);
                    addressInforBean.setAreaStr(District);
                    addressInforBean.setDetailStr(poiItem.getTitle());
                    addressInforBean.setLatitude(poiItem.getLatLonPoint().getLatitude());
                    addressInforBean.setLongitude(poiItem.getLatLonPoint().getLongitude());
                    addressInforBean.setName(poiItem.getTitle());
                }

                LinearLayout linearLayout = ((LinearLayout) view);
                for (int i = 0; i < linearLayout.getChildCount(); i++) {
                    if (linearLayout.getChildAt(i) instanceof TextView) {
                        tv = ((TextView) linearLayout.getChildAt(i));
                        break;
                    }
                }
                tv.setTextColor(Color.BLUE);


                //将位置数据返回给
                intent.putExtra("address", addressInforBean);
                MapActivity.this.setResult(300, intent);
                MapActivity.this.finish();

            }
        });


        setUpMap();

        setUpGeocodeSearch();

        if (isNeedCurrentLocation)

            initMyLocation();

        if (model != null) {
            tag = true;
            updateWithNewLocation(new LatLonPoint(Lat, Lon));
        }
    }


    //初始化标题
    private void initTitle() {
        title_middle = ((TextView) findViewById(R.id.title_middle_text));

        if (locationType != null)

            switch (locationType) {
                case DYNAMIC:

                    title_middle.setText("选择地址");
                    break;

                case HUODONG:
                    title_middle.setText("活动地址");
                    break;
            }

    }

    //激活定位功能
    private void initMyLocation() {

        client = new AMapLocationClient(this);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);

        aMap.setLocationSource(new LocationSource() {
            @Override
            public void activate(final OnLocationChangedListener onLocationChangedListener) {
                AMapLocationClientOption option = new AMapLocationClientOption();
                option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                option.setInterval(2000);
                option.setOnceLocation(true);
                client.setLocationListener(new AMapLocationListener() {
                    @Override
                    public void onLocationChanged(AMapLocation aMapLocation) {

                        if (aMapLocation != null) {
                            onLocationChangedListener.onLocationChanged(aMapLocation);
                            addMarkersToMap(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()));
                            Lat = aMapLocation.getLatitude();
                            Lon = aMapLocation.getLongitude();
                            keyWord = aMapLocation.getAddress();
                            doRegeocodeSearch(new LatLonPoint(Lat, Lon));

                            CurrentCity = aMapLocation.getCity();
                            District = aMapLocation.getDistrict();
                        }
                    }
                });
                client.setLocationOption(option);
                client.startLocation();
            }

            @Override
            public void deactivate() {

            }
        });

        aMap.setMyLocationEnabled(true);
    }


    private void setUpGeocodeSearch() {

        geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {


                dissmissProgressDialog();
                if (regeocodeResult != null) {
                    if (regeocodeResult.getRegeocodeAddress() != null) {
                        if (!StringUtil.isEmpty(regeocodeResult.getRegeocodeAddress().getCity()))
                            CurrentCity = regeocodeResult.getRegeocodeAddress().getCity();

                        if (!StringUtil.isEmpty(regeocodeResult.getRegeocodeAddress().getDistrict())) {
                            District = regeocodeResult.getRegeocodeAddress().getDistrict();
                        }
                    }


                    List<PoiItem> poiItems = regeocodeResult.getRegeocodeAddress().getPois();
                    if (poiItems != null && poiItems.size() > 0) {
                        sugestAddressList.clear();

                        if (isNeedEmpty) {
                            sugestAddressList.add(new PoiItem("不显示位置", null, "", ""));
                        }

                        sugestAddressList.addAll(poiItems);

                    }
                    autoCompleteTextAdapter.notifyDataSetChanged();

                    //显示搜索范围内的所有地址


                    aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                            AMapUtil.convertToLatLng(new LatLonPoint(Lat, Lon)), 15));
                    addMarkersToMap(new LatLng(Lat, Lon));
                }
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });


    }

    private void doRegeocodeSearch(LatLonPoint point) {

        setUpGeocodeSearch();

        RegeocodeQuery regeocodeQuery = new RegeocodeQuery(point, 500, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(regeocodeQuery);

        showProgressDialog();

    }

    /**
     * 设置页面监听
     */
    private void setUpMap() {
        TextView searButton = (TextView) findViewById(R.id.searchButton);
        searButton.setOnClickListener(this);
        aMap.setInfoWindowAdapter(this);// 添加显示infowindow监听事件
        if (!isNeedCurrentLocation)
            aMap.moveCamera(CameraUpdateFactory.zoomTo(19l));

        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (latLng != null) {
                    Lat = latLng.latitude;
                    Lon = latLng.longitude;
                    keyWord = "";
                    doRegeocodeSearch(new LatLonPoint(Lat, Lon));

                }

            }
        });
    }

    /**
     * 点击搜索按钮
     */
    public void searchButton() {
        keyWord = AMapUtil.checkEditText(autoCompleteTextView);
        if ("".equals(keyWord)) {
            ReminderHelper.getIntentce().ToastShow_with_image(this,
                    "请输入搜索关键字", R.string.icon_remind);
            return;
        } else {
            isSearch = true;
            doSearchQuery();
        }
    }


    /*
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage("正在搜索:\n" + keyWord);
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
        showProgressDialog();// 显示进度框
        currentPage = 0;
        query = new PoiSearch.Query(keyWord, "", CurrentCity);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        query.setCityLimit(true);

        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return false;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public View getInfoWindow(final Marker marker) {
        View view = getLayoutInflater().inflate(R.layout.poikeywordsearch_uri, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(marker.getTitle());

        TextView snippet = (TextView) view.findViewById(R.id.snippet);
        snippet.setText(marker.getSnippet());
        ImageButton button = (ImageButton) view
                .findViewById(R.id.start_amap_app);
        button.setVisibility(View.GONE);
        return view;
    }


    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
    private void showSuggestCity(List<SuggestionCity> cities) {

        Tip tip;
        if (cities != null && cities.size() > 0) {
            isSuggesCity = true;
            for (SuggestionCity s : cities) {

                tip = new Tip();
                tip.setName(keyWord);
                tip.setAddress(s.getCityName());
                autoCompleteList.add(tip);
            }
            tipAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newText = s.toString().trim();
        if (!AMapUtil.IsEmptyOrNullString(newText)) {
            findViewById(R.id.image).setVisibility(View.GONE);
            InputtipsQuery inputquery = new InputtipsQuery(newText, CurrentCity);
            Inputtips inputTips = new Inputtips(MapActivity.this, inputquery);
            inputTips.setInputtipsListener(this);
            inputTips.requestInputtipsAsyn();
        } else {
            findViewById(R.id.image).setVisibility(View.VISIBLE);
        }
    }


    /**
     * POI信息查询回调方法
     */
    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        dissmissProgressDialog();// 隐藏对话框
        if (rCode == 1000) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    // 取得搜索到的poiitems有多少页

                    addMarkersToMap(new LatLng(Lat, Lon));
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                    if (poiItems != null && poiItems.size() > 0) {
                        sugestAddressList.clear();
                        sugestAddressList.addAll(poiItems);
                        aMap.clear();// 清理之前的图标
                        if (isNeedEmpty) {
                            sugestAddressList.add(0, new PoiItem("不显示位置", null, "", ""));
                        }
                        if (!isSearch) {

                            PoiItem posResult = null;
                            for (PoiItem i : poiItems) {

                                if (i.getTitle().equals(keyWord)) {
                                    posResult = i;
                                    break;
                                }
                            }
                            if (posResult != null) {
                                poiItems.clear();
                                poiItems.add(posResult);
                            } else {
                                poiItems = poiItems.subList(0, 1);
                            }

                        }
                        autoCompleteTextAdapter.notifyDataSetChanged();
                        PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
                        poiOverlay.removeFromMap();
                        poiOverlay.addToMap();
                        poiOverlay.zoomToSpan();

                    } else if (suggestionCities != null
                            && suggestionCities.size() > 0) {
                        showSuggestCity(suggestionCities);
                    }
                }
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem item, int rCode) {
        // TODO Auto-generated method stub

    }

    /**
     * Button点击事件回调方法
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * 点击搜索按钮
             */
            case R.id.searchButton:
                searchButton();
                break;

            case R.id.title_left:
                this.finish();
                break;
            default:
                break;
        }
    }


    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        if (tipList != null) {
            autoCompleteList.clear();
            autoCompleteList.addAll(tipList);
            tipAdapter = new AutoCompleteTipAdapter(MapActivity.this, autoCompleteList);
            autoCompleteTextView.setAdapter(tipAdapter);
            tipAdapter.notifyDataSetChanged();
        }

    }


    private void addMarkersToMap(LatLng LatLng) {

        aMap.clear();
        MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .position(LatLng)
                .draggable(true);
        aMap.addMarker(markerOption);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (client != null) {
            client.stopLocation();
            client = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    //根据经纬度获取城市和地区
    private void updateWithNewLocation(LatLonPoint location) {

        if (location != null) {

            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(latitude,
                        longitude, 1);

                if (addresses.size() > 0) {
                    Address address = addresses.get(0);
                    CurrentCity = address.getLocality();
                    District = address.getSubLocality();
                    if (!StringUtil.isEmpty(CurrentCity)) {
                        if (tag) {
                            keyWord = model.getDetailStr();
                            doSearchQuery();
                            tag = false;
                        }
                    }
                }

            } catch (IOException e) {
                if (model != null) {
                    CurrentCity = model.getCity();
                    keyWord = model.getDetailStr();
                    if (tag) {
                        keyWord = model.getDetailStr();
                        doSearchQuery();
                        tag = false;
                    }
                }

                e.printStackTrace();
            }
        }
    }
}
