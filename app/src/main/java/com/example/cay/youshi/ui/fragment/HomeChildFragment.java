package com.example.cay.youshi.ui.fragment;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.cay.youshi.R;
import com.example.cay.youshi.adapter.MovieAdapter;
import com.example.cay.youshi.base.adapter.BaseFragment;
import com.example.cay.youshi.bean.SingleLookupResultBean;
import com.example.cay.youshi.bean.YouShiMovieDealisBean;
import com.example.cay.youshi.bean.YouShiTopbar;
import com.example.cay.youshi.bean.YouShiTopbarResultBean;
import com.example.cay.youshi.databinding.FragmentMovieBinding;
import com.example.cay.youshi.databinding.HeaderMovieItemBinding;
import com.example.cay.youshi.http.HttpUtils;
import com.example.cay.youshi.ui.activity.BaiDuMovieDetailActivity;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * 电影
 */
public class HomeChildFragment extends BaseFragment<FragmentMovieBinding> implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private ImageView mHeadImageView;
    private TextView mHeadTextView1;
    private TextView mHeadTextView2;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private HeaderMovieItemBinding mHeaderBinding;
    private boolean isFirst = true;//是否第一次请求
    private String position = "1000000000";//请求位置记录
    private String FIRST_NUM = "15";//初始请求个数
    private String LAOD_NUM = "9";//上拉加载个数
    private MovieAdapter movieAdapter;
    private String img_url;
    private String nameId;
    private String topbar;
    private String type;
    private String type_ad;
    private String ad_url;
    private static final String TAG = "Cay";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = bindingView.listMovie;
        mHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.header_movie_item, null, false);
        mHeadImageView = mHeaderBinding.ivHeaderItemMovie;
        mHeadTextView1 = mHeaderBinding.tvHeaderItemText1;
        mHeadTextView2 = mHeaderBinding.tvHeaderItemText2;
        mSwipeRefreshLayout = bindingView.swipeLayout;
        mSwipeRefreshLayout.setOnRefreshListener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mHeadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (img_url!=null&&!img_url.equals("")) {
                    if (type_ad.equals("1")) {
                        BaiDuMovieDetailActivity.start(getActivity(), nameId, img_url, null);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(ad_url.trim()));
                        getContext().startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            topbar = getArguments().getString("topbar");
            type = getArguments().getString("type");
        }
    }

    public static HomeChildFragment newInstance(String topbar, String type) {
        HomeChildFragment fragment = new HomeChildFragment();
        Bundle bundle = new Bundle();
        bundle.putString("topbar", topbar);
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int setContent() {
        return R.layout.fragment_movie;
    }

    @Override
    protected void loadData() {
        super.loadData();
        if (!isFirst) {
            return;
        }
        loadTopbarData();
        httpGetData(position, FIRST_NUM, true, false);
    }

    private void loadTopbarData() {
        Log.i(TAG, "loadTopbarData: "+topbar);
        HttpUtils.getInstance().getYouShiData(false).getTopbar(topbar)
                .map(new Function<YouShiTopbarResultBean, YouShiTopbar>() {
                    @Override
                    public YouShiTopbar apply(YouShiTopbarResultBean youShiTopbarResultBean) throws Exception {
                        return youShiTopbarResultBean.getResult();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<YouShiTopbar>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(YouShiTopbar bean) {
                Log.i(TAG, "YouShiTopbar: " + bean);
                img_url = bean.getImg_url();
                nameId = bean.getMovie_id();
                type_ad = bean.getType_ad();
                ad_url = bean.getAd_url();
                Glide.with(getContext()).load(bean.getImg_url()).into(mHeadImageView);
                mHeadTextView1.setText(bean.getName());
                mHeadTextView2.setText(bean.getTitle());
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: ");
            }
        });
    }

    private void initAdapter(List<YouShiMovieDealisBean> data) {
        movieAdapter = new MovieAdapter(getContext(), R.layout.movie_grid_item, data);
        mRecyclerView.setAdapter(movieAdapter);
        movieAdapter.setOnLoadMoreListener(this);
        movieAdapter.addHeaderView(mHeaderBinding.getRoot());
        position = data.get(data.size()-1).getMovie_count();
        movieAdapter.setEnableLoadMore(true);
        isFirst = false;
        showContentView();
        if (data.size() < 15) {
            movieAdapter.loadMoreEnd(true);
        }
    }

    private void httpGetData(final String position1, String num, final boolean first, final boolean isResfres) {
        HttpUtils.getInstance().getYouShiData(false).oneLookupResult(type, position1, num)
                .map(new Function<SingleLookupResultBean, List<YouShiMovieDealisBean>>() {
                    @Override
                    public List<YouShiMovieDealisBean> apply(SingleLookupResultBean singleLookupResultBean) throws Exception {
                        return singleLookupResultBean.getResult();
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<YouShiMovieDealisBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<YouShiMovieDealisBean> list) {
                        if (first) {
                            initAdapter(list);
                        } else {
                            if (isResfres) {
                                movieAdapter.setNewData(list);
                                if (list.size() > 1) {
                                    position = list.get(list.size()-1).getMovie_count();
                                }
                                if (list.size() < 15) {
                                    movieAdapter.loadMoreEnd(true);
                                }
                                mSwipeRefreshLayout.setRefreshing(false);
                                movieAdapter.setEnableLoadMore(true);
                                showContentView();
                            } else {
                                movieAdapter.addData(list);
                                position = movieAdapter.getData().get(movieAdapter.getData().size()-1).getMovie_count();
                                movieAdapter.loadMoreComplete();
                                if (list.size() < 9) {
                                    movieAdapter.loadMoreEnd(false);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (first) {
                            showError();
                        } else {
                            if (isResfres) {
                                showError();
                            } else {
                                movieAdapter.loadMoreFail();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                httpGetData(position, LAOD_NUM, false, false);
            }
        }, 800);
    }

    @Override
    public void onRefresh() {
        showLoading();
        loadTopbarData();
        if (isFirst) {
            httpGetData(position, FIRST_NUM, true, false);
        } else {
            position = "10000000000";
            httpGetData(position, FIRST_NUM, false, true);
        }

    }
}
