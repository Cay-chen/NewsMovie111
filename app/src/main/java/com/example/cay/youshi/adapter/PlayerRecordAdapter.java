package com.example.cay.youshi.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cay.youshi.R;
import com.example.cay.youshi.bean.PlayeRecordBean;
import com.example.cay.youshi.ui.activity.MovieDetailActivity;

import java.util.List;

/**
 * Created by Cay on 2017/3/1.
 */

public class PlayerRecordAdapter extends BaseItemDraggableAdapter<PlayeRecordBean,BaseViewHolder> {
    private Context context;
    public PlayerRecordAdapter(Context context,int layoutResId, List<PlayeRecordBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper,final PlayeRecordBean item) {
        helper.setText(R.id.tv_record_name, item.getName())
               .setText(R.id.tv_record_time, item.getPlay_time());
        Glide.with(context).load(item.getImg_url()).into((ImageView) helper.getView(R.id.iv_record_img));
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetailActivity.start((Activity)context,item.getMovie_id(),item.getImg_url(),null);
            }
        });

    }
}
