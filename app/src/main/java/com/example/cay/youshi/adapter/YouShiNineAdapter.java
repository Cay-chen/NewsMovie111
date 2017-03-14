package com.example.cay.youshi.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cay.youshi.R;
import com.example.cay.youshi.bean.YouShiNintBean;
import com.example.cay.youshi.http.RxBus.RxBus;
import com.example.cay.youshi.http.RxBus.RxCodeConstants;
import com.example.cay.youshi.ui.activity.BaiDuMovieDetailActivity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Cay-chen on 2017/3/12.
 */

public class YouShiNineAdapter extends BaseQuickAdapter<YouShiNintBean, BaseViewHolder>{
    private Context context;

    public YouShiNineAdapter(Context context, int layoutResId, List<YouShiNintBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final YouShiNintBean item) {
        helper.setText(R.id.tv_9_1, item.getName_1()).
                setText(R.id.tv_9_2, item.getName_2()).
                setText(R.id.tv_9_3, item.getName_3()).
                setText(R.id.tv_9_4, item.getName_4()).
                setText(R.id.tv_9_5, item.getName_5()).
                setText(R.id.tv_9_6, item.getName_6()).
                setText(R.id.tv_9_code_1, item.getCode_1()).
                setText(R.id.tv_9_code_2, item.getCode_2()).
                setText(R.id.tv_9_code_3, item.getCode_3()).
                setText(R.id.tv_9_code_4, item.getCode_4()).
                setText(R.id.tv_9_code_5, item.getCode_5()).
                setText(R.id.tv_9_code_6, item.getCode_6());
        if (helper.getPosition() == 1) {
            helper.setText(R.id.tv_title_9_type, "最近更新电影")
                    .setImageResource(R.id.iv_title_9_type, R.drawable.everydady_movie);
        }
        if (helper.getPosition() == 3) {
            helper.setText(R.id.tv_title_9_type, "最近更新动漫")
                    .setImageResource(R.id.iv_title_9_type, R.drawable.everydady_manga);
        }
        if (helper.getPosition() == 2) {
            helper.setVisible(R.id.tv_9_count_1, true).
                    setVisible(R.id.tv_9_count_2, true).
                    setVisible(R.id.tv_9_count_3, true).
                    setVisible(R.id.tv_9_count_4, true).
                    setVisible(R.id.tv_9_count_5, true).
                    setVisible(R.id.tv_9_count_6, true)
                    .setText(R.id.tv_title_9_type, "最近更新电视剧")
                    .setImageResource(R.id.iv_title_9_type, R.drawable.everydady_tv);
            if (item.getNow_num_1().equals(item.getTotal_num_1())) {
                helper.setText(R.id.tv_9_count_1, item.getTotal_num_1() + "集全");
            } else {
                helper.setText(R.id.tv_9_count_1, "已更新至" + item.getNow_num_1() + "集");
            }


            if (item.getNow_num_2().equals(item.getTotal_num_2())) {
                helper.setText(R.id.tv_9_count_2, item.getTotal_num_2() + "集全");
            } else {
                helper.setText(R.id.tv_9_count_2, "已更新至" + item.getNow_num_2() + "集");
            }


            if (item.getNow_num_3().equals(item.getTotal_num_3())) {
                helper.setText(R.id.tv_9_count_3, item.getTotal_num_3() + "集全");
            } else {
                helper.setText(R.id.tv_9_count_3, "已更新至" + item.getNow_num_3() + "集");
            }


            if (item.getNow_num_4().equals(item.getTotal_num_4())) {
                helper.setText(R.id.tv_9_count_4, item.getTotal_num_4() + "集全");
            } else {
                helper.setText(R.id.tv_9_count_4, "已更新至" + item.getNow_num_4() + "集");
            }


            if (item.getNow_num_5().equals(item.getTotal_num_5())) {
                helper.setText(R.id.tv_9_count_5, item.getTotal_num_5() + "集全");
            } else {
                helper.setText(R.id.tv_9_count_5, "已更新至" + item.getNow_num_5() + "集");
            }
            if (item.getNow_num_6().equals(item.getTotal_num_6())) {
                helper.setText(R.id.tv_9_count_6, item.getTotal_num_6() + "集全");
            } else {
                helper.setText(R.id.tv_9_count_6, "已更新至" + item.getNow_num_6() + "集");
            }
        }

        Glide.with(context).load(item.getImg_url_1()).into((ImageView) helper.getView(R.id.iv_9_1));
        Glide.with(context).load(item.getImg_url_2()).into((ImageView) helper.getView(R.id.iv_9_2));
        Glide.with(context).load(item.getImg_url_3()).into((ImageView) helper.getView(R.id.iv_9_3));
        Glide.with(context).load(item.getImg_url_4()).into((ImageView) helper.getView(R.id.iv_9_4));
        Glide.with(context).load(item.getImg_url_5()).into((ImageView) helper.getView(R.id.iv_9_5));
        Glide.with(context).load(item.getImg_url_6()).into((ImageView) helper.getView(R.id.iv_9_6));
        helper.getView(R.id.iv_9_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaiDuMovieDetailActivity.start((Activity) context, item.getId_1(), item.getImg_url_1(), null);
            }
        });
        helper.getView(R.id.iv_9_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaiDuMovieDetailActivity.start((Activity) context, item.getId_2(), item.getImg_url_2(), null);
            }
        });
        helper.getView(R.id.iv_9_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaiDuMovieDetailActivity.start((Activity) context, item.getId_3(), item.getImg_url_3(), null);
            }
        });
        helper.getView(R.id.iv_9_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaiDuMovieDetailActivity.start((Activity) context, item.getId_4(), item.getImg_url_4(), null);
            }
        });
        helper.getView(R.id.iv_9_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaiDuMovieDetailActivity.start((Activity) context, item.getId_5(), item.getImg_url_5(), null);
            }
        });
        helper.getView(R.id.iv_9_6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaiDuMovieDetailActivity.start((Activity) context, item.getId_6(), item.getImg_url_6(), null);
            }
        });

        helper.getView(R.id.ll_title_9_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getDefault().send(RxCodeConstants.JUMP_TYPE, helper.getPosition());
            }
        });

    }


}
