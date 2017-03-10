package com.example.cay.youshi;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cay.youshi.VerUpdata.VersionUpdateManager;
import com.example.cay.youshi.adapter.MyFragmentPagerAdapter;
import com.example.cay.youshi.bean.UpDdtaBackBean;
import com.example.cay.youshi.bean.VersionUpdataBean;
import com.example.cay.youshi.databinding.ActivityMainBinding;
import com.example.cay.youshi.http.HttpUtils;
import com.example.cay.youshi.http.RxBus.RxBus;
import com.example.cay.youshi.http.RxBus.RxBusBaseMessage;
import com.example.cay.youshi.http.RxBus.RxCodeConstants;
import com.example.cay.youshi.statusbar.StatusBarUtil;
import com.example.cay.youshi.ui.activity.SearchMovieActivity;
import com.example.cay.youshi.ui.fragment.MeFragment;
import com.example.cay.youshi.ui.fragment.MovieHomeFragment;
import com.example.cay.youshi.ui.menu.NavAboutActivity;
import com.example.cay.youshi.ui.menu.NavDeedBackActivity;
import com.example.cay.youshi.ui.menu.NavDownloadActivity;
import com.example.cay.youshi.ui.menu.NavHomePageActivity;
import com.example.cay.youshi.utils.CommonUtils;
import com.example.cay.youshi.utils.ImgLoadUtil;

import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    // 一定需要对应的bean
    private ActivityMainBinding mBinding;
    private TextView llTitleGank;
    private TextView llTitleOne;
    private Toolbar toolbar;
    private long time = 0;
    private NavigationView navView;
    private FrameLayout llTitleMenu;
    private DrawerLayout drawerLayout;
    private ViewPager vpContent;
    private CompositeDisposable disposable;
    private static final String TAG = "Cay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initVivws();
        StatusBarUtil.setColorNoTranslucentForDrawerLayout(MainActivity.this, drawerLayout, CommonUtils.getColor(R.color.colorTheme));
        initContentFragment();
        initDrawerLayout();
        initListener();
        initRxBus();
        Connector.getDatabase();
        versionUpdateJianCe();
       // MiPushClient.setAlias(this,"0510016",null);
        upCountLogin();
      //  RefWatcher refWatcher = LeakCanary.install(MainActivity.class);

    }

    private void initVivws() {
        navView = mBinding.navView;
        llTitleMenu = mBinding.include.llTitleMenu;
        drawerLayout = mBinding.drawerLayout;
        toolbar = mBinding.include.toolbar;
        llTitleGank = mBinding.include.ivTitleGank;
        llTitleOne = mBinding.include.ivTitleOne;
        vpContent = mBinding.include.vpContent;
    }

    private void initListener() {
        llTitleMenu.setOnClickListener(this);
        llTitleGank.setOnClickListener(this);
        llTitleOne.setOnClickListener(this);
    }

    /**
     * inflateHeaderView 进来的布局要宽一些
     */
    private void initDrawerLayout() {
        navView.inflateHeaderView(R.layout.nav_header_main);
        View headerView = navView.getHeaderView(0);
        ImageView ivAvatar = (ImageView) headerView.findViewById(R.id.iv_avatar);
        ImgLoadUtil.displayCircle(ivAvatar, R.drawable.ic_avatar);
        LinearLayout llNavHomepage = (LinearLayout) headerView.findViewById(R.id.ll_nav_homepage);
        LinearLayout llNavScanDownload = (LinearLayout) headerView.findViewById(R.id.ll_nav_scan_download);
        LinearLayout llNavDeedback = (LinearLayout) headerView.findViewById(R.id.ll_nav_deedback);
        LinearLayout llNavAbout = (LinearLayout) headerView.findViewById(R.id.ll_nav_about);
        llNavHomepage.setOnClickListener(this);
        llNavScanDownload.setOnClickListener(this);
        llNavDeedback.setOnClickListener(this);
        llNavAbout.setOnClickListener(this);
    }

    private void initContentFragment() {
        ArrayList<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(new MovieHomeFragment());
        mFragmentList.add(new MeFragment());
        // 注意使用的是：getSupportFragmentManager
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        vpContent.setAdapter(adapter);
        // 设置ViewPager最大缓存的页面个数(cpu消耗少)
        vpContent.setOffscreenPageLimit(1);
        vpContent.addOnPageChangeListener(this);
        mBinding.include.ivTitleGank.setSelected(true);
        vpContent.setCurrentItem(0);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_title_menu:// 开启菜单
                drawerLayout.openDrawer(GravityCompat.START);
                // 关闭
//                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.iv_title_gank:// 电影栏
                if (vpContent.getCurrentItem() != 0) {//不然cpu会有损耗
                    vpContent.setCurrentItem(0);
                }
                break;
            case R.id.iv_title_one:// 所有电影栏
                if (vpContent.getCurrentItem() != 1) {
                    Log.i(TAG, "iv_title_one: ");
                    vpContent.setCurrentItem(1);
                }
                break;

            case R.id.ll_nav_homepage:// 主页
                mBinding.drawerLayout.closeDrawer(GravityCompat.START);
                mBinding.drawerLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NavHomePageActivity.startHome(MainActivity.this);
                    }
                }, 360);

                break;

            case R.id.ll_nav_scan_download://扫码下载
                mBinding.drawerLayout.closeDrawer(GravityCompat.START);
                mBinding.drawerLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NavDownloadActivity.start(MainActivity.this);
                    }
                }, 360);
                break;
            case R.id.ll_nav_deedback:// 问题反馈
                mBinding.drawerLayout.closeDrawer(GravityCompat.START);
                mBinding.drawerLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NavDeedBackActivity.start(MainActivity.this);
                    }
                }, 360);
                break;
            case R.id.ll_nav_about:// 关于V视
                mBinding.drawerLayout.closeDrawer(GravityCompat.START);
                mBinding.drawerLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NavAboutActivity.start(MainActivity.this);
                    }
                }, 360);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                llTitleOne.setBackgroundResource(R.drawable.baidu_top_right_false);
                llTitleOne.setTextColor(getResources().getColor(R.color.white));
                llTitleGank.setBackgroundResource(R.drawable.baidu_top_left_true);
                llTitleGank.setTextColor(getResources().getColor(R.color.colorTheme));
                break;
            case 1:
                llTitleOne.setBackgroundResource(R.drawable.baidu_top_right_true);
                llTitleOne.setTextColor(getResources().getColor(R.color.colorTheme));
                llTitleGank.setBackgroundResource(R.drawable.baidu_top_left_fals);
                llTitleGank.setTextColor(getResources().getColor(R.color.white));
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent = new Intent(this, SearchMovieActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

   /**
     * 每日推荐点击"新电影热映榜"跳转
     */
    private void initRxBus() {
        disposable = new CompositeDisposable();
        disposable.add(RxBus.getDefault().toObservable(RxCodeConstants.JUMP_TYPE_TO_ONE, RxBusBaseMessage.class)
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mBinding.include.vpContent.setCurrentItem(1);
                    }
                }));
    }

    /**
     * 双击返回桌面
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - time > 1000)) {
                Toast.makeText(this, "再按一次返回桌面", Toast.LENGTH_SHORT).show();
                time = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }


    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();

    }

    /**
     * 版本更新数据监测
     */

    private void versionUpdateJianCe() {
        HttpUtils.getInstance().getMyObservableClient().verJianCe()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<VersionUpdataBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<VersionUpdataBean> list) {
                        responseVersionUpdate(list);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
    /**
     * 请求版本更新的响应
     */
    public void responseVersionUpdate(List<VersionUpdataBean> responses) {
        if (responses.size() < 1) {
            return;
        }
        VersionUpdataBean versionUpdate = responses.get(0);
        VersionUpdateManager update = new VersionUpdateManager(this,
                versionUpdate.getVersion(), versionUpdate.getURLaddress());
        // 强制更新
        if (versionUpdate.getForcedUpdate() == 1) {
            update.setForcedUpdate(true);
            update.setTitle(this.getResources().getString(
                    R.string.version_update_tips_force));
        }
        update.setShowResult(false);
        update.startUpdate();
    }

    /**
     * 登录统计
     */
    public void upCountLogin() {
        HttpUtils.getInstance().getMyObservableClient().upCountLogin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpDdtaBackBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UpDdtaBackBean value) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}