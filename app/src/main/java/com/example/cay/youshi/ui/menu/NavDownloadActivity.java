package com.example.cay.youshi.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cay.youshi.R;
import com.example.cay.youshi.base.BaseActivity;
import com.example.cay.youshi.databinding.ActivityNavDownloadBinding;
import com.example.cay.youshi.utils.PerfectClickListener;
import com.example.cay.youshi.utils.QRCodeUtil;
import com.example.cay.youshi.utils.ShareUtils;


public class NavDownloadActivity extends BaseActivity<ActivityNavDownloadBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_download);
        showContentView();
        setTitle("扫码下载");
        QRCodeUtil.showThreadImage(this, getResources().getString(R.string.app_url), bindingView.ivErweima, R.mipmap.icon);
        bindingView.tvShare.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                ShareUtils.share(v.getContext(), R.string.string_share_text);
            }
        });
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, NavDownloadActivity.class);
        mContext.startActivity(intent);
    }
}
