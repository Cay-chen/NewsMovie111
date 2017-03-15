package com.example.cay.youshi.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.cay.youshi.R;
import com.example.cay.youshi.adapter.TodayAdapter;
import com.example.cay.youshi.base.BaseActivity;
import com.example.cay.youshi.bean.YouShiTodayBackResultBean;
import com.example.cay.youshi.databinding.ActivityLocalsVideoBinding;
import com.example.cay.youshi.http.HttpUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ToadyActivity extends BaseActivity<ActivityLocalsVideoBinding> {
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locals_video);
        showLoading();
        setTitle("今日更新");
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView = bindingView.rvLocals;
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        initAdapter();
    }

    private void initAdapter() {
        HttpUtils.getInstance().getYouShiData(false).getToday().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<YouShiTodayBackResultBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(YouShiTodayBackResultBean value) {
                        if (value.getResCode().equals("0")) {
                            showError();
                        } else {
                            mRecyclerView.setAdapter( new TodayAdapter(ToadyActivity.this,R.layout.today_date_item, value.getResult()));
                            showContentView();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        showError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, ToadyActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        showLoading();
        initAdapter();
    }
}