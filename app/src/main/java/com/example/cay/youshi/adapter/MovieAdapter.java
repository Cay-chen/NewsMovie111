package com.example.cay.youshi.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cay.youshi.R;
import com.example.cay.youshi.bean.YouShiMovieDealisBean;
import com.example.cay.youshi.ui.activity.BaiDuMovieDetailActivity;

import java.util.List;

/**
 * Created by Cay on 2017/2/14.
 */

public class MovieAdapter extends BaseQuickAdapter<YouShiMovieDealisBean,BaseViewHolder> {
    private Context context;
    private ImageView mImageView;
    public MovieAdapter(Context context,int layoutResId, List<YouShiMovieDealisBean> data) {
        super(layoutResId, data);
        this.context =context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final YouShiMovieDealisBean item) {
        helper.setText(R.id.tv_grid_movie, item.getName())
                .setText(R.id.tv_grid_code, item.getCode());
        mImageView =  helper.getView(R.id.iv_grid_img);
        Glide.with(context).load(item.getImg_url()).into(mImageView);
        if (item.getSubtype().equals("1")) {
            if (item.getNow_num().equals(item.getTotal_num())) {
                helper.setText(R.id.tv_grid_count, item.getNow_num() + "集全")
                        .setVisible(R.id.tv_grid_count, true);
            } else {
                    helper.setText(R.id.tv_grid_count,"已更新至" +item.getNow_num() + "集")
                            .setVisible(R.id.tv_grid_count, true);
            }
        }
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaiDuMovieDetailActivity.start((Activity)context,item.getId(),item.getImg_url(),(ImageView) helper.getView(R.id.iv_grid_img));
            }
        });
    }


}
