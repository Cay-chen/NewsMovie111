package com.example.cay.youshi.ui.activity;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cay.youshi.R;
import com.example.cay.youshi.adapter.MovieCountItemAdapter;
import com.example.cay.youshi.base.BaseHeaderActivity;
import com.example.cay.youshi.bean.MovieBean;
import com.example.cay.youshi.bean.MovieDataBean;
import com.example.cay.youshi.bean.UpDdtaBackBean;
import com.example.cay.youshi.bean.YouShiMovieDealisBean;
import com.example.cay.youshi.data.Utils;
import com.example.cay.youshi.databinding.ActivityOneMovieDetailBinding;
import com.example.cay.youshi.databinding.ActivityYoushiMovieDetailBinding;
import com.example.cay.youshi.databinding.HeaderSlideShapeBinding;
import com.example.cay.youshi.http.HttpUtils;
import com.example.cay.youshi.utils.CommonUtils;
import com.example.cay.youshi.utils.ShareUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * 继承基类而写的电影详情页 2016-12-13
 */
public class BaiDuMovieDetailActivity extends BaseHeaderActivity<HeaderSlideShapeBinding, ActivityYoushiMovieDetailBinding> {
    private String movieId;
    private String img_url;
    private String name;
    private String baidu_url;
    private static final String TAG = "Cay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youshi_movie_detail);
        if (getIntent() != null) {
            movieId = getIntent().getStringExtra("id");
            img_url = getIntent().getStringExtra("img_url");
        }
        bindingHeaderView.executePendingBindings();

        onLoadData(movieId);

        Glide.with(this).load(img_url).bitmapTransform(new BlurTransformation(this, 23, 4)).into(bindingHeaderView.imgItemBg);
        Glide.with(this).load(img_url).into(bindingHeaderView.ivOnePhoto);

    }

    private void onLoadData(String id) {
        HttpUtils.getInstance().getYouShiData(false).getYouShiMovieDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<YouShiMovieDealisBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("", "onSubscribe: ");
                    }

                    @Override
                    public void onNext(YouShiMovieDealisBean bean) {
                        setAllData(bean);
                        showContentView();
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

    private void setAllData(final YouShiMovieDealisBean subjectsBean) {
        name = subjectsBean.getName();
        initSlideShapeTheme(setHeaderImgUrl(), setHeaderImageView());
        setTitle(name);
        baidu_url = subjectsBean.getBaidu_url();
        setSubTitle(String.format("主演：%s", subjectsBean.getAct()));
        bindingHeaderView.tvOneRatingRate.setText(this.getResources().getString(R.string.string_rating) + subjectsBean.getCode());
        bindingHeaderView.tvOneCasts.setText(subjectsBean.getAct());
        bindingHeaderView.tvOneDirectors.setText(subjectsBean.getDirector());
        bindingHeaderView.tvOneDay.setText(this.getResources().getString(R.string.movie_year) + subjectsBean.getYear());
        bindingHeaderView.tvOneCity.setText(this.getResources().getString(R.string.movie_city) + subjectsBean.getCountries());
        bindingHeaderView.tvOneGenres.setText(this.getResources().getString(R.string.string_type) + subjectsBean.getGenres());
        bindingContentView.tvOneContiont.setText(subjectsBean.getLog());
        bindingContentView.tvOneTitle.setText(subjectsBean.getOther_name());
        String baidu_url = subjectsBean.getBaidu_url().trim();
        bindingContentView.tvBaiduUrl.setMovementMethod(LinkMovementMethod.getInstance());
        if (baidu_url.contains(" ")) {
            final String[] urls = baidu_url.trim().split(" ");
            SpannableString spannableString = new SpannableString(baidu_url);
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(urls[0].substring(3).trim()));
                    startActivity(intent);
                }
            }, 3, baidu_url.indexOf(" "), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            bindingContentView.tvBaiduUrl.setText(spannableString);


            bindingContentView.btnCopyPassword.setVisibility(View.VISIBLE);
            bindingContentView.btnCopyPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(urls[1].substring(3));
                    Toast.makeText(BaiDuMovieDetailActivity.this, "密码复制成功", Toast.LENGTH_LONG).show();
                }
            });
            bindingContentView.btnCopyUrlPassword.setText("复制链接和密码");
            bindingContentView.btnCopyUrlPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(subjectsBean.getBaidu_url().trim());
                    Toast.makeText(BaiDuMovieDetailActivity.this, "复制成功", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            SpannableString spannableString = new SpannableString(baidu_url);
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(subjectsBean.getBaidu_url().trim()));
                    startActivity(intent);
                }
            }, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            bindingContentView.tvBaiduUrl.setText(spannableString);
            bindingContentView.btnCopyUrlPassword.setText("复制链接");
            bindingContentView.btnCopyUrlPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(subjectsBean.getBaidu_url().trim());
                    Toast.makeText(BaiDuMovieDetailActivity.this, "复制成功", Toast.LENGTH_LONG).show();
                }
            });
        }

    }


    @Override
    protected void setShareItem() {
        ShareUtils.share(this, "正在使用V视观看【" + name + "】 下载V视地址:https://fir.im/vision");

    }

    @Override
    protected void setCopyItem() {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(baidu_url);
        Toast.makeText(this, "复制成功", Toast.LENGTH_LONG).show();
    }

    @Override
    protected int setHeaderLayout() {
        return R.layout.header_slide_shape;
    }

    @Override
    protected String setHeaderImgUrl() {
        if (img_url == null) {
            return "";
        }
        return img_url;
    }

    @Override
    protected ImageView setHeaderImageView() {
        return bindingHeaderView.imgItemBg;
    }

    public static void start(Activity context, String id, String img_url, ImageView imageView) {
        Intent intent = new Intent(context, BaiDuMovieDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("img_url", img_url);
        if (imageView != null) {
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(context,
                            imageView, CommonUtils.getString(R.string.transition_movie_img));//与xml文件对应
            ActivityCompat.startActivity(context, intent, options.toBundle());
        } else {
            context.startActivity(intent);
        }

    }


    @Override
    protected void onRefresh() {
        bindingContentView.activityOneMovieDetail.postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoadData(movieId);

            }
        }, 1000);


    }
}
