package com.histudent.jwsoft.histudent.comment2.utils;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Created by ZhangYT on 2016/8/6.
 */
public class PullToRefreshUtils {



    public static void initPullToRefreshListView(PullToRefreshListView refreshListView) {

        //上拉加载更多设置
        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        refreshListView.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在刷新，请耐心等待。。");
        refreshListView.getLoadingLayoutProxy(true, false).setReleaseLabel("释放以刷新");
        refreshListView.getLoadingLayoutProxy(true, false).setPullLabel("下来刷新");

        //下拉刷新设置
        refreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载，请耐心等待。。");
        refreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放以加载");
        refreshListView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载");
    }


    public static void initPullToRefreshListView2(PullToRefreshListView refreshListView) {

        //上拉加载更多设置
        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        refreshListView.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在刷新，请耐心等待。。");
        refreshListView.getLoadingLayoutProxy(true, false).setReleaseLabel("释放以刷新");
        refreshListView.getLoadingLayoutProxy(true, false).setPullLabel("下来刷新");

        //下拉刷新设置
        refreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载，请耐心等待。。");
        refreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放以加载");
        refreshListView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载");
    }


    public static void initPullToRefreshGridView(PullToRefreshGridView pullToRefreshGridView){
        //上拉加载更多设置
        pullToRefreshGridView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshGridView.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在刷新，请耐心等待。。");
        pullToRefreshGridView.getLoadingLayoutProxy(true, false).setReleaseLabel("释放以刷新");
        pullToRefreshGridView.getLoadingLayoutProxy(true, false).setPullLabel("下来刷新");

        //下拉刷新设置
        pullToRefreshGridView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载，请耐心等待。。");
        pullToRefreshGridView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放以加载");
        pullToRefreshGridView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载");

    }


}
