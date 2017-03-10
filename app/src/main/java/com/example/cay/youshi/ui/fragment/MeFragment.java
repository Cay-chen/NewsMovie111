package com.example.cay.youshi.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.cay.youshi.R;
import com.example.cay.youshi.utils.CheckNetwork;

/**
 * Created by Cay on 2017/2/28.
 */

public class MeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "Cay";
    private WebView mWebView;
    private SwipeRefreshLayout mRefreshLayout;
    Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_fragment, container, false);
        mWebView = (WebView) view.findViewById(R.id.wv_alimama);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.sr_almama);
        mRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setTextZoom(100);
        if (CheckNetwork.isNetworkConnected(getContext())) {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
        }
        mWebView.loadUrl("https://uland.taobao.com/coupon/elist?e=U%2B3HttE91tybEA6tucNztA%3D%3D&pid=mm_122281857_22124450_73656857");
    }

    @Override
    public void onRefresh() {
        mWebView.loadUrl("https://uland.taobao.com/coupon/elist?e=U%2B3HttE91tybEA6tucNztA%3D%3D&pid=mm_122281857_22124450_73656857");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "run: ");
                mRefreshLayout.setRefreshing(false);
            }
        }, 1000); //每隔1s执行
    }
}
