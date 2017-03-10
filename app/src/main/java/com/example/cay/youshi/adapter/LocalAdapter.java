package com.example.cay.youshi.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cay.youshi.R;
import com.example.cay.youshi.bean.BitmapEntity;
import com.example.cay.youshi.player.MyJcPlayer;
import com.example.cay.youshi.utils.TimeChange;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Cay on 2017/2/28.
 */

public class LocalAdapter extends BaseQuickAdapter<BitmapEntity,BaseViewHolder> {
    private Context context;
    public LocalAdapter(Context context,int layoutResId, List<BitmapEntity> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final BitmapEntity item) {
        helper.setText(R.id.tv_local_video_name,item.getName())
                .setText(R.id.tv_local_video_long_time, TimeChange.setTime(item.getDuration()))
                .setText(R.id.tv_local_video_size,TimeChange.bytes2kb(item.getSize())+"");
        Glide.with(context).load("file://" + item.getUri_thumb()).into((ImageView) helper.getView(R.id.iv_local_img));
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JCVideoPlayerStandard.startFullscreen(context, MyJcPlayer.class,item.getUri(), item.getName());

               // MyPlayerActivity.star(context,item.getUri(),item.getName());
            }
        });
    }
}
