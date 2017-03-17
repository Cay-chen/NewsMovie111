package com.example.cay.youshi.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.cay.youshi.R;
import com.example.cay.youshi.adapter.YouShiNineAdapter;
import com.example.cay.youshi.base.GlideImageLoader;
import com.example.cay.youshi.base.adapter.BaseFragment;
import com.example.cay.youshi.bean.YouShiFirstDataBean;
import com.example.cay.youshi.bean.YouShiMovieDealisBean;
import com.example.cay.youshi.bean.YouShiNintBean;
import com.example.cay.youshi.databinding.FooterItemEverydayBinding;
import com.example.cay.youshi.databinding.FragmentEverydayBinding;
import com.example.cay.youshi.databinding.HeaderItemEverydayBinding;
import com.example.cay.youshi.http.HttpUtils;
import com.example.cay.youshi.http.RxBus.RxBus;
import com.example.cay.youshi.http.RxBus.RxCodeConstants;
import com.example.cay.youshi.ui.activity.BaiDuMovieDetailActivity;
import com.example.cay.youshi.ui.activity.InvalidMovieUpdateActivity;
import com.example.cay.youshi.ui.activity.RequestMovieUpdateActivity;
import com.example.cay.youshi.ui.activity.ToadyActivity;
import com.example.cay.youshi.utils.ConnMySqlUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 每日推荐
 */
public class EverydayFragment extends BaseFragment<FragmentEverydayBinding> {
    private static final String TAG = "Cay";

    private RecyclerView mRecyclerView;
    private HeaderItemEverydayBinding mHeaderBinding;
    private View mHeaderView;
    private View mFooterView;
    private SharedPreferences sp;
    private FooterItemEverydayBinding mFooterBinding;
    private RotateAnimation animation;
    private YouShiNineAdapter mYouShiNineAdapter;
    private boolean isZiDongGet = false;
    private boolean isGetNetwork = false;  //是否网络获取过Ip


    @Override
    public int setContent() {
        return R.layout.fragment_everyday;
    }

    @Override
    protected void onInvisible() {
        // 不可见时轮播图停止滚动
        if (mHeaderBinding != null && mHeaderBinding.banner != null) {
            mHeaderBinding.banner.stopAutoPlay();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // 失去焦点，否则recyclerview第一个item会回到顶部
        bindingView.xrvEveryday.setFocusable(false);
        // 开始图片请求
        Glide.with(getActivity()).resumeRequests();
    }

    @Override
    public void onPause() {
        super.onPause();
        // 停止全部图片请求 跟随着Activity
        Glide.with(getActivity()).pauseRequests();

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();
        bindingView.llLoading.setVisibility(View.VISIBLE);
        animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(3000);//设置动画持续时间
        animation.setInterpolator(new LinearInterpolator());//不停顿
        animation.setRepeatCount(10);
        bindingView.ivLoading.setAnimation(animation);
        animation.startNow();
        mHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.header_item_everyday, null, false);
        mFooterBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.footer_item_everyday, null, false);
        mHeaderView = mHeaderBinding.getRoot();
        View view = mHeaderView.findViewById(R.id.include_everyday);
        FrameLayout updata = (FrameLayout) view.findViewById(R.id.fl_every_updata);
        FrameLayout today = (FrameLayout) view.findViewById(R.id.fl_every_today);
        FrameLayout shixiao = (FrameLayout) view.findViewById(R.id.fl_every_shi);

        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //RxBus.getDefault().send(RxCodeConstants.JUMP_TYPE_TO_ONE, new RxBusBaseMessage());
                ToadyActivity.start(getContext());
            }
        });
        updata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RequestMovieUpdateActivity.class);
                getContext().startActivity(intent);
            }
        });
        shixiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InvalidMovieUpdateActivity.class);
                getContext().startActivity(intent);
            }
        });
        mFooterView = mFooterBinding.getRoot();
        initRecyulerView();

        if (getSpIp() == null) {
            getNetworkIp();
        } else {
            initFirstData(false);
        }

    }

    @Override
    protected void loadData() {


    }

    private void initRecyulerView() {
        mRecyclerView = bindingView.xrvEveryday;
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
// 需加，不然滑动不流畅
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);
    }

    /**
     * 网络数据库获取Ip,并保存到sp中
     */
    private void getNetworkIp() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext(ConnMySqlUtil.getIp("SELECT * FROM ip"));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String value) {
                        Log.i(TAG, "获取远程Ip成功: " + value);
                        sp.edit().putString("ip", value).commit();
                        isGetNetwork = true;
                        initFirstData(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "获取远程Ip失败 ");

                        isGetNetwork = false;
                        showError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 本地SP获取数据
     *
     * @return ss
     */
    public String getSpIp() {
        sp = getActivity().getSharedPreferences("LOCAL_IP", 0);
        String result = sp.getString("ip", null);
        return result;
    }

    private void initFirstData(boolean isUpdateIp) {
        HttpUtils.getInstance().getYouShiData(isUpdateIp).getYouShiFirstData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<YouShiFirstDataBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(final YouShiFirstDataBean value) {
                        Log.i(TAG, "请求数据成功: ");
                        RxBus.getDefault().send(RxCodeConstants.JUMP_TYPE_TO_ONE, value.getAlmm_url());


                        /**************获取banner数据****************************/
                        String[] bannerTitle = new String[value.getBanners().size()];
                        String[] bannerImg = new String[value.getBanners().size()];
                        String[] bannerId = new String[value.getBanners().size()];
                        String[] bannerType = new String[value.getBanners().size()];
                        for (int i = 0; i < value.getBanners().size(); i++) {
                            bannerTitle[i] = value.getBanners().get(i).getName();
                            bannerImg[i] = value.getBanners().get(i).getImg_url();
                            bannerId[i] = value.getBanners().get(i).getMovie_id();
                            bannerType[i] = value.getBanners().get(i).getType();
                        }
                        initBanner(bannerImg, bannerTitle, bannerId, bannerType);

                        /**************获取rv数据****************************/
                        List<YouShiNintBean> mList = new ArrayList<>();
                        for (int i = 0; i < value.getResult().size(); i++) {
                            YouShiNintBean nintBean = new YouShiNintBean();
                            List<YouShiMovieDealisBean> listItem = value.getResult().get(i);
                            for (int n = 0; n < listItem.size(); n++) {
                                switch (n) {
                                    case 0:
                                        nintBean.setCode_1(listItem.get(n).getCode());
                                        nintBean.setId_1(listItem.get(n).getId());
                                        nintBean.setImg_url_1(listItem.get(n).getImg_url());
                                        nintBean.setNow_num_1(listItem.get(n).getNow_num());
                                        nintBean.setTotal_num_1(listItem.get(n).getTotal_num());
                                        nintBean.setName_1(listItem.get(n).getName());
                                        nintBean.setYear_1(listItem.get(n).getYear());
                                        break;
                                    case 1:
                                        nintBean.setCode_2(listItem.get(n).getCode());
                                        nintBean.setId_2(listItem.get(n).getId());
                                        nintBean.setImg_url_2(listItem.get(n).getImg_url());
                                        nintBean.setNow_num_2(listItem.get(n).getNow_num());
                                        nintBean.setTotal_num_2(listItem.get(n).getTotal_num());
                                        nintBean.setName_2(listItem.get(n).getName());
                                        nintBean.setYear_2(listItem.get(n).getYear());
                                        break;
                                    case 2:
                                        nintBean.setCode_3(listItem.get(n).getCode());
                                        nintBean.setId_3(listItem.get(n).getId());
                                        nintBean.setImg_url_3(listItem.get(n).getImg_url());
                                        nintBean.setNow_num_3(listItem.get(n).getNow_num());
                                        nintBean.setTotal_num_3(listItem.get(n).getTotal_num());
                                        nintBean.setName_3(listItem.get(n).getName());
                                        nintBean.setYear_3(listItem.get(n).getYear());
                                        break;
                                    case 3:
                                        nintBean.setCode_4(listItem.get(n).getCode());
                                        nintBean.setId_4(listItem.get(n).getId());
                                        nintBean.setImg_url_4(listItem.get(n).getImg_url());
                                        nintBean.setNow_num_4(listItem.get(n).getNow_num());
                                        nintBean.setTotal_num_4(listItem.get(n).getTotal_num());
                                        nintBean.setName_4(listItem.get(n).getName());
                                        nintBean.setYear_4(listItem.get(n).getYear());
                                        break;
                                    case 4:
                                        nintBean.setCode_5(listItem.get(n).getCode());
                                        nintBean.setId_5(listItem.get(n).getId());
                                        nintBean.setImg_url_5(listItem.get(n).getImg_url());
                                        nintBean.setNow_num_5(listItem.get(n).getNow_num());
                                        nintBean.setTotal_num_5(listItem.get(n).getTotal_num());
                                        nintBean.setName_5(listItem.get(n).getName());
                                        nintBean.setYear_5(listItem.get(n).getYear());
                                        break;
                                    case 5:
                                        nintBean.setCode_6(listItem.get(n).getCode());
                                        nintBean.setId_6(listItem.get(n).getId());
                                        nintBean.setImg_url_6(listItem.get(n).getImg_url());
                                        nintBean.setNow_num_6(listItem.get(n).getNow_num());
                                        nintBean.setTotal_num_6(listItem.get(n).getTotal_num());
                                        nintBean.setName_6(listItem.get(n).getName());
                                        nintBean.setYear_6(listItem.get(n).getYear());
                                        break;
                                }
                            }
                            mList.add(nintBean);
                        }
                        mYouShiNineAdapter = new YouShiNineAdapter(getContext(), R.layout.item_everyday_nith, mList);
                        mYouShiNineAdapter.addHeaderView(mHeaderView);
                        mYouShiNineAdapter.addFooterView(mFooterView);
                        mRecyclerView.setAdapter(mYouShiNineAdapter);
                        ImageView imageView = mFooterBinding.ivFoodPhoto;
                        Glide.with(getContext()).load(value.getAds().getImg_url()).into(imageView);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (value.getAds().getType().equals("1")) {
                                    BaiDuMovieDetailActivity.start((Activity) getContext(), value.getAds().getMovie_id(), value.getAds().getImg_url(), null);
                                } else {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(value.getAds().getImg_url().trim()));
                                    getContext().startActivity(intent);
                                }
                            }
                        });

                        mFooterBinding.tvFoodPhotoTitle.setText(value.getAds().getName());
                        showRotaLoading(false);

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isZiDongGet) {
                            Log.i(TAG, "请求数据失败: ");
                            showError();
                        } else {
                            Log.i(TAG, "请求数据失败,自动获取IP一次 ");
                            getNetworkIp();
                            isZiDongGet = true;
                        }
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    private void initBanner(final String[] img, final String[] titles, final String[] id, final String[] type) {
        final List<String> idList = Arrays.asList(id);
        Banner mBanner = mHeaderBinding.banner;
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(Arrays.asList(img));
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(Arrays.asList(titles));
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                if (type[position - 1].equals("1")) {
                    BaiDuMovieDetailActivity.start((Activity) getContext(), idList.get(position - 1), Arrays.asList(img).get(position - 1), null);
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(titles[position - 1]));
                    getContext().startActivity(intent);
                }

            }
        });
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();

    }

    private void showRotaLoading(boolean isLoading) {
        if (isLoading) {
            bindingView.llLoading.setVisibility(View.VISIBLE);
            bindingView.xrvEveryday.setVisibility(View.GONE);
            animation.startNow();
        } else {
            bindingView.llLoading.setVisibility(View.GONE);
            bindingView.xrvEveryday.setVisibility(View.VISIBLE);
            animation.cancel();
        }
    }

    @Override
    protected void onRefresh() {
        showContentView();
        showRotaLoading(true);
        if (isGetNetwork) {
            initFirstData(false);
        } else {
            getNetworkIp();
        }

    }
}
